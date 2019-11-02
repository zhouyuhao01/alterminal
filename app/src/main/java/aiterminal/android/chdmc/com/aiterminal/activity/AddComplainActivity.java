package aiterminal.android.chdmc.com.aiterminal.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.OSSResult;


import java.util.ArrayList;

import aiterminal.android.chdmc.com.aiterminal.R;
import aiterminal.android.chdmc.com.aiterminal.bean.ComplainBean;
import aiterminal.android.chdmc.com.aiterminal.network.OSSManager;
import aiterminal.android.chdmc.com.aiterminal.network.RequestUtil;
import aiterminal.android.chdmc.com.aiterminal.utils.FileUtils;
import aiterminal.android.chdmc.com.aiterminal.utils.Utils;
import butterknife.ButterKnife;

import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * 爆料Activity
 */
public class AddComplainActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final int UPLOAD_FINISH = 0;
    private static final int ADD_COMPLAIN_FINISH = 1;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPLOAD_FINISH:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            uploadComplain();
                        }
                    });
                case ADD_COMPLAIN_FINISH:
                    mLoadingLayout.setVisibility(View.INVISIBLE);
                    showAddComplainSuccessDialog();
                    break;
            }
        }
    };
    private boolean changeGroup = false;

    private void showAddComplainSuccessDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("爆料成功");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();

    }

    private final int ACTIVITY_RESULT_TAKE_PHOTO = 0;
    private final int ACTIVITY_RESULT_TAKE_PHOTO_NEXT = 1;
    private final int ACTIVITY_RESULT_TAKE_PHOTO_MORE = 2;

    private Button mAddComplainConfirm;
    private LinearLayout mComplainPhotoContainer;
    private EditText mComplainMessageTextView;
    private RadioGroup mComplainTypeRadioGroup1;
    private RadioGroup mComplainTypeRadioGroup2;
    private String mComplainType;

    private String mCloseBitmapName;
    private Bitmap mCloseBitmap;
    private String mRemoteBitmapName;
    private Bitmap mRemoteBitmap;
    private ArrayList<String> mMoreBitmapNameList = new ArrayList<>();
    private ArrayList<Bitmap> mMoreBitmapList = new ArrayList<>();

    RelativeLayout mLoadingLayout;

    ProgressBar mProgressBar;

    private volatile int mUploadImageTaskCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complain);

        ButterKnife.bind(this);
        mAddComplainConfirm = (Button) findViewById(R.id.button_complain_confirm);
        findViewById(R.id.button_take_photo).setOnClickListener(this);
        findViewById(R.id.button_take_photo_next).setOnClickListener(this);
        findViewById(R.id.button_take_photo_more).setOnClickListener(this);

        mLoadingLayout = findViewById(R.id.complain_upload_loadingLayout);
        mProgressBar = findViewById(R.id.complain_upload_progress);

        mAddComplainConfirm.setOnClickListener(this);

        mComplainPhotoContainer = findViewById(R.id.complain_photo_container);

        mComplainMessageTextView = findViewById(R.id.edittext_complain);

        mComplainTypeRadioGroup1 = findViewById(R.id.radiogroup_complain_type1);
        mComplainTypeRadioGroup2 = findViewById(R.id.radiogroup_complain_type2);

        mComplainTypeRadioGroup1.setOnCheckedChangeListener(this);

        mComplainTypeRadioGroup2.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group != null && checkedId > -1 && changeGroup == false) {
            if (group == mComplainTypeRadioGroup1) {
                changeGroup = true;
                mComplainTypeRadioGroup2.clearCheck();
                changeGroup = false;
            } else {
                changeGroup = true;
                mComplainTypeRadioGroup1.clearCheck();
                changeGroup = false;
            }
            RadioButton radioButton = findViewById(checkedId);
            mComplainType = radioButton.getText().toString();
        }

    }


    public void takePhoto(int requestCode) {
        int hasCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasCamera == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(captureIntent, requestCode);
        }else{
            //没有权限，向用户请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mComplainPhotoContainer.setVisibility(View.VISIBLE);
            if (data != null) {
                if (data.hasExtra("data")) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    switch (requestCode) {
                        case ACTIVITY_RESULT_TAKE_PHOTO:
                            ImageView imageViewClose = findViewById(R.id.complain_photo_close);
                            imageViewClose.setImageBitmap(bitmap);
                            mCloseBitmap = bitmap;
                            break;
                        case ACTIVITY_RESULT_TAKE_PHOTO_NEXT:
                            ImageView imageViewRemote = findViewById(R.id.complain_photo_remote);
                            imageViewRemote.setImageBitmap(bitmap);
                            mRemoteBitmap = bitmap;
                            break;
                        case ACTIVITY_RESULT_TAKE_PHOTO_MORE:
                            LinearLayout imgMoreContainer = findViewById(R.id.complain_photo_more_container);
                            ImageView imageView = new ImageView(getBaseContext());
                            imageView.setImageBitmap(bitmap);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.dip2px(60), Utils.dip2px(60));
                            params.setMarginEnd(Utils.dip2px(5));
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            imgMoreContainer.addView(imageView, params);
                            mMoreBitmapList.add(bitmap);
                            break;
                    }
                }
            }

        }
    }


    OSSCompletedCallback ossCompletedCallback = new OSSCompletedCallback() {

        @Override
        public void onSuccess(OSSRequest request, OSSResult result) {
            mUploadImageTaskCount--;
            if (mUploadImageTaskCount == 0) {
                handler.sendEmptyMessage(UPLOAD_FINISH);
            }
            mProgressBar.setProgress(mProgressBar.getProgress() - 20);
        }

        @Override
        public void onFailure(OSSRequest request, ClientException clientException, ServiceException serviceException) {
            mUploadImageTaskCount--;
            if (mUploadImageTaskCount == 0) {
                handler.sendEmptyMessage(UPLOAD_FINISH);
            }
        }
    };

    private void uploadComplain() {
        ComplainBean complainBean = new ComplainBean();
        complainBean.setComplainMessage(mComplainMessageTextView.getText().toString());
        complainBean.setComplainType(mComplainType);

        if (mCloseBitmapName != null) {
            complainBean.setImgCloseUrl(OSSManager.getInstance().getPicHost() + "/" + mCloseBitmapName);
        }

        if (mRemoteBitmapName != null) {
            complainBean.setImgRemoteUrl(OSSManager.getInstance().getPicHost() + "/" + mRemoteBitmapName);
        }


        if (mMoreBitmapNameList.size() > 0) {
            ArrayList<String> arrayList = new ArrayList<>();
            for (String picUrl : mMoreBitmapNameList) {
                arrayList.add(OSSManager.getInstance().getPicHost() + "/" + picUrl);
            }
            complainBean.setImgMoreUrlList(arrayList);
        }

        RequestUtil.addComplain(complainBean, new RequestUtil.OnResultListner() {

            @Override
            public void onSuccess(JSONObject result) {
                handler.sendEmptyMessage(ADD_COMPLAIN_FINISH);
            }

            @Override
            public void onFailed(String result) {
//                handler.sendEmptyMessage(ADD_COMPLAIN_FINISH);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_complain_confirm:

                if (mComplainType == null) {
                    Toast.makeText(this, "请选择投诉类型", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mComplainMessageTextView.getText().toString())) {
                    Toast.makeText(this, "请输入投诉内容", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mCloseBitmap == null && mRemoteBitmap == null && mMoreBitmapList.size() == 0) {
                    Toast.makeText(this, "请拍摄照片", Toast.LENGTH_SHORT).show();
                    return;
                }

                long nowTime = System.currentTimeMillis();

                mLoadingLayout.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(100);

                if (mCloseBitmap != null) {
                    String filepath = FileUtils.saveBitmap(mCloseBitmap, "closeBitmap.png");
                    mUploadImageTaskCount++;
                    mCloseBitmapName = nowTime + "close.png";
                    OSSManager.getInstance().uploadFile(mCloseBitmapName, filepath, ossCompletedCallback);
                }

                if (mRemoteBitmap != null) {
                    String filepath = FileUtils.saveBitmap(mRemoteBitmap, "remoteBitmap.png");
                    mUploadImageTaskCount++;
                    mRemoteBitmapName = nowTime + "remote.png";
                    OSSManager.getInstance().uploadFile(mRemoteBitmapName, filepath, ossCompletedCallback);
                }

                if (mMoreBitmapList.size() > 0) {
                    mUploadImageTaskCount += mMoreBitmapList.size();
                    for (int i = 0; i < mMoreBitmapList.size(); i++) {
                        String filepath = FileUtils.saveBitmap(mRemoteBitmap, "mMoreBitmap" + i +".png");
                        String moreBitmapName = nowTime + "more"+i+".png";
                        mMoreBitmapNameList.add(moreBitmapName);
                        OSSManager.getInstance().uploadFile(moreBitmapName, filepath, ossCompletedCallback);
                    }
                }

                break;
            case R.id.button_take_photo:
                takePhoto(ACTIVITY_RESULT_TAKE_PHOTO);
                break;
            case R.id.button_take_photo_next:
                takePhoto(ACTIVITY_RESULT_TAKE_PHOTO_NEXT);
                break;
            case R.id.button_take_photo_more:
                takePhoto(ACTIVITY_RESULT_TAKE_PHOTO_MORE);
                break;
        }
    }
}

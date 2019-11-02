package aiterminal.android.chdmc.com.aiterminal.activity;

/**
 * Created by zhouyuhao on 2019/10/30.
 */

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.OSSResult;
import com.sevenheaven.segmentcontrol.SegmentControl;

import java.util.ArrayList;

import aiterminal.android.chdmc.com.aiterminal.R;
import aiterminal.android.chdmc.com.aiterminal.bean.Order;
import aiterminal.android.chdmc.com.aiterminal.network.OSSManager;
import aiterminal.android.chdmc.com.aiterminal.network.RequestUtil;
import aiterminal.android.chdmc.com.aiterminal.utils.FileUtils;
import aiterminal.android.chdmc.com.aiterminal.utils.Utils;

/**
 * 发布垃圾回收订单
 */
public class AddOrderActivity extends BaseActivity implements View.OnClickListener{

    private static final int ORDER_TYPE_COLLECT = 0;
    private static final int ORDER_TYPE_RABISH = 1;

    private SegmentControl segmentControl;
    private EditText mOrderInfo;
    private EditText mOrderCallNum;
    private LinearLayout mPhotoContainer;

    private Button mAddOrder;

    private int mOrderType = 0;

    private ArrayList<Bitmap> mBitmapList = new ArrayList<>();
    private ArrayList<String> mBimapNames = new ArrayList<>();

    private int mUploadImageTaskCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        segmentControl = findViewById(R.id.segment_control);
        segmentControl.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                mOrderType = index;
            }
        });

        mOrderInfo = findViewById(R.id.order_info);
        mOrderCallNum = findViewById(R.id.order_call_num);

        findViewById(R.id.button_order_take_photo).setOnClickListener(this);

        mPhotoContainer = findViewById(R.id.order_photo_more_container);

        mAddOrder = findViewById(R.id.button_add_order);
        mAddOrder.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_order_take_photo:
                takePhoto(0);
                break;
            case R.id.button_add_order:
                if (TextUtils.isEmpty(mOrderCallNum.getText().toString())) {
                    Toast.makeText(this, "请填写联系方式", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(mOrderInfo.getText().toString())) {
                    Toast.makeText(this, "请填写回收内容", Toast.LENGTH_LONG).show();
                    return;
                }

                if (mBitmapList.size() <= 0) {
                    Toast.makeText(this, "请拍摄回收物品的照片", Toast.LENGTH_LONG).show();
                    return;
                }

                mUploadImageTaskCount += mBitmapList.size();
                long nowTime = System.currentTimeMillis();
                for (int i = 0; i < mBitmapList.size(); i++) {
                    String bitmapName = nowTime + i + "order.png";
                    String filepath = FileUtils.saveBitmap(mBitmapList.get(i), bitmapName);
                    mBimapNames.add(bitmapName);
                    OSSManager.getInstance().uploadFile(bitmapName, filepath, ossCompletedCallback);
                }

                break;
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPLOAD_FINISH:
                    addOrder();
                    break;
                case ADD_ORDER_FAIL:
                    AlertDialog.Builder successbuilder = new AlertDialog.Builder(AddOrderActivity.this);
                    successbuilder.setMessage("订单发布失败");
                    successbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    successbuilder.show();
                    break;
                case ADD_ORDER_SUCCESS:
                    AlertDialog.Builder failbuilder = new AlertDialog.Builder(AddOrderActivity.this);
                    failbuilder.setMessage("订单发布成功");
                    failbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    failbuilder.show();

                    break;
            }
        }
    };
    private static final int UPLOAD_FINISH = 0;
    private static final int ADD_ORDER_SUCCESS = 1;
    private static final int ADD_ORDER_FAIL = 2;
    OSSCompletedCallback ossCompletedCallback = new OSSCompletedCallback() {

        @Override
        public void onSuccess(OSSRequest request, OSSResult result) {
            mUploadImageTaskCount -= 1;
            if (mUploadImageTaskCount == 0) {
                handler.sendEmptyMessage(UPLOAD_FINISH);
            }
        }

        @Override
        public void onFailure(OSSRequest request, ClientException clientException, ServiceException serviceException) {
            mUploadImageTaskCount -= 1;
            if (mUploadImageTaskCount == 0) {
                handler.sendEmptyMessage(UPLOAD_FINISH);
            }
        }
    };


    private void addOrder() {
        Order order = new Order();
        order.setOrderType(mOrderType);

        String orderNum = mOrderCallNum.getText().toString();
        String orderMessage= mOrderInfo.getText().toString();
        order.setOrderMessage(orderMessage);
        order.setOrderNum(orderNum);

        ArrayList<String> arrayList = new ArrayList<>();
        for (String picUrl : mBimapNames) {
            arrayList.add(OSSManager.getInstance().getPicHost() + "/" + picUrl);
        }
        order.setImgUrls(arrayList);

        RequestUtil.addOrder(order, new RequestUtil.OnResultListner() {
            @Override
            public void onSuccess(JSONObject result) {
                handler.sendEmptyMessage(ADD_ORDER_SUCCESS);
            }

            @Override
            public void onFailed(String result) {
                handler.sendEmptyMessage(ADD_ORDER_FAIL);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mPhotoContainer.setVisibility(View.VISIBLE);
            if (data != null) {
                if (data.hasExtra("data")) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    ImageView imageView = new ImageView(getBaseContext());
                    imageView.setImageBitmap(bitmap);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.dip2px(60), Utils.dip2px(60));
                    params.setMarginEnd(Utils.dip2px(5));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    mPhotoContainer.addView(imageView, params);
                    mBitmapList.add(bitmap);
                }
            }
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

}

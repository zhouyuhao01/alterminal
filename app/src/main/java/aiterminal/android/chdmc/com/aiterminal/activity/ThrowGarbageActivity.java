package aiterminal.android.chdmc.com.aiterminal.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import aiterminal.android.chdmc.com.aiterminal.R;
import aiterminal.android.chdmc.com.aiterminal.utils.Utils;

public class ThrowGarbageActivity extends BaseActivity implements View.OnClickListener{

    private Button mThrowFinishButton;

    private Button mTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throw_garbage);
        findViewById(R.id.button_throw_garbage).setOnClickListener(this);
        findViewById(R.id.button_take_photo).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (data.hasExtra("data")) {
                Bitmap bitmap = data.getParcelableExtra("data");
                LinearLayout imgMoreContainer = findViewById(R.id.garbage_photo_container);
                ImageView imageView = new ImageView(getBaseContext());
                imageView.setImageBitmap(bitmap);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.dip2px(60), Utils.dip2px(60));
                params.setMarginEnd(Utils.dip2px(5));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imgMoreContainer.addView(imageView, params);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_take_photo:
                Utils.takePhoto(0, this);
                break;
            case R.id.button_throw_garbage:
                finish();
                Toast.makeText(this, "完成打卡", Toast.LENGTH_LONG).show();
                break;
        }
    }
}

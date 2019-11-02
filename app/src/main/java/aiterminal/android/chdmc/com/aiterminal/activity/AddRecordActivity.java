package aiterminal.android.chdmc.com.aiterminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import aiterminal.android.chdmc.com.aiterminal.R;

/**
 * Created by zhouyuhao on 2019/10/30.
 */

public class AddRecordActivity extends BaseActivity implements View.OnClickListener{



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        findViewById(R.id.button_goto_throw_garbage).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_goto_throw_garbage:
                Intent intent = new Intent(this, ThrowGarbageActivity.class);
                startActivity(intent);
                break;
        }
    }
}

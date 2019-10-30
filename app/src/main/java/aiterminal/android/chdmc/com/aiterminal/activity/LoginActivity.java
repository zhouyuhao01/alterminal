package aiterminal.android.chdmc.com.aiterminal.activity;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import aiterminal.android.chdmc.com.aiterminal.R;
import aiterminal.android.chdmc.com.aiterminal.manager.LoginManager;
import aiterminal.android.chdmc.com.aiterminal.network.RequestUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private static final int MSG_DISMISS_PROGRESS = 0;

    private Button mLoginButton;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private ProgressBar mProgressBar;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_DISMISS_PROGRESS) {
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.button_login).setOnClickListener(this);

        mUsernameEditText = findViewById(R.id.edittext_username);
        mPasswordEditText = findViewById(R.id.edittext_paassword);

        mProgressBar = findViewById(R.id.login_progressbar);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:

                String userName = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);
                LoginManager.getInstance().login(userName, password, new LoginManager.LoginCallback() {
                    @Override
                    public void onResult(boolean success) {
                        handler.sendEmptyMessage(MSG_DISMISS_PROGRESS);
                        Looper.prepare();
                        if (success) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                        }
                        Looper.loop();
                    }
                });
                break;
        }
    }
}

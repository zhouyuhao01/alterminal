package aiterminal.android.chdmc.com.aiterminal.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import aiterminal.android.chdmc.com.aiterminal.R;
import aiterminal.android.chdmc.com.aiterminal.component.X5WebView;

/**
 * Created by zhouyuhao on 2019/9/28.
 */

public class NewsDetailActivity extends BaseActivity {

    private static final String TAG = NewsDetailActivity.class.getName();

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        String newsUrl = getIntent().getExtras().getString("newsUrl");
        X5WebView mWebView = findViewById(R.id.news_detail_webview);

        mWebView.loadUrl(newsUrl);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

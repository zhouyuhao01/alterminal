package aiterminal.android.chdmc.com.aiterminal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import aiterminal.android.chdmc.com.aiterminal.activity.LoginActivity;
import aiterminal.android.chdmc.com.aiterminal.component.BottomBar;
import aiterminal.android.chdmc.com.aiterminal.fragments.ChartFragment;
import aiterminal.android.chdmc.com.aiterminal.fragments.MyFragment;
import aiterminal.android.chdmc.com.aiterminal.fragments.NewsFragment;
import aiterminal.android.chdmc.com.aiterminal.fragments.OperationFragment;
import aiterminal.android.chdmc.com.aiterminal.manager.LoginManager;

public class MainActivity extends AppCompatActivity
        implements ChartFragment.OnFragmentInteractionListener ,
        MyFragment.OnFragmentInteractionListener,
        OperationFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener, View.OnClickListener{

    private TextView mToolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        findViewById(R.id.toolbar_login).setOnClickListener(this);
        initViews();
    }


    private void initViews() {
        BottomBar bottomBar = findViewById(R.id.navigation);
        bottomBar.setContainer(R.id.content)
                .setTitleBeforeAndAfterColor("#999999", "#999999")
                .addItem(NewsFragment.class,
                        "新闻",
                        R.drawable.news,
                        R.drawable.news_pressed)
                .addItem(ChartFragment.class,
                        "商城",
                        R.drawable.chart,
                        R.drawable.chart_pressed)
                .addItem(OperationFragment.class,
                        "首页",
                        R.drawable.action,
                        R.drawable.action_pressed)
                .addItem(MyFragment.class,
                        "消息",
                        R.drawable.my,
                        R.drawable.my_pressed)
                .addItem(MyFragment.class,
                        "我的",
                        R.drawable.my,
                        R.drawable.my_pressed)
                .setFirstChecked(2)
                .build();
        bottomBar.setOnFragmentChangedListener(whichFragment -> {
            switch (whichFragment) {
                case 0:
                    mToolbarTitle.setText("新闻");
                    break;
                case 1:
                    mToolbarTitle.setText("商城");
                    break;
                case 2:
                    mToolbarTitle.setText("首页");
                    break;
                case 3:
                    mToolbarTitle.setText("消息");
                    break;
                case 4:
                    mToolbarTitle.setText("我的");
                    break;
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(MainActivity.this,"this is："+uri,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        if (!LoginManager.getInstance().hasLogin()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }
}

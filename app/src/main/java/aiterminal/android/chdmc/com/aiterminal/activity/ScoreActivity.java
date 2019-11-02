package aiterminal.android.chdmc.com.aiterminal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aiterminal.android.chdmc.com.aiterminal.R;
import aiterminal.android.chdmc.com.aiterminal.bean.NewsListItemBean;
import aiterminal.android.chdmc.com.aiterminal.fragments.NewsFragment;
import aiterminal.android.chdmc.com.aiterminal.network.RequestUtil;

/**
 * 积分页面
 */
public class ScoreActivity extends BaseActivity{


    private RecyclerView mScoreRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        mScoreRecyclerView = findViewById(R.id.score_recycler);

    }

}

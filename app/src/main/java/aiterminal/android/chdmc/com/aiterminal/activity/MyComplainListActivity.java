package aiterminal.android.chdmc.com.aiterminal.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.w3c.dom.Text;

import java.util.ArrayList;

import aiterminal.android.chdmc.com.aiterminal.R;
import aiterminal.android.chdmc.com.aiterminal.bean.ComplainBean;
import aiterminal.android.chdmc.com.aiterminal.fragments.NewsFragment;
import aiterminal.android.chdmc.com.aiterminal.network.RequestUtil;

/**
 * Created by zhouyuhao on 2019/10/30.
 */

public class MyComplainListActivity extends BaseActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
        }
    };

    private static final String TAG = MyComplainListActivity.class.getName();
    private RecyclerView recyclerView;
    private ArrayList<ComplainBean> complainBeans = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_complainlist);

        RequestUtil.getComplains(onResultListner);

        recyclerView = findViewById(R.id.recycleview_my_complains);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    RequestUtil.OnResultListner onResultListner = new RequestUtil.OnResultListner() {
        @Override
        public void onSuccess(JSONObject result) {
            JSONArray data = result.getJSONArray("result");
            for (int i = 0; i < data.size(); i++) {
                JSONObject jsonObject = data.getJSONObject(i);
                ComplainBean complainBean = new ComplainBean();
                complainBean.setComplainType(jsonObject.getString("type"));
                complainBean.setComplainMessage(jsonObject.getString("message"));
                complainBean.setImgCloseUrl(jsonObject.getString("imgCloseUrl"));
                complainBeans.add(complainBean);
            }
            handler.sendEmptyMessage(0);

            Log.d(TAG, "onSuccess: " + result);
        }

        @Override
        public void onFailed(String result) {

        }
    };

    private RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view;
            MyViewHolder viewHolder = null;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_complain, parent, false);
            viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ComplainBean complainBean = complainBeans.get(position);
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.titleView.setText(complainBean.getComplainType());
            myViewHolder.contentView.setText(complainBean.getComplainMessage());

            myViewHolder.imgContainer.removeAllViews();

        }

        @Override
        public int getItemCount() {
            return complainBeans.size();
        }
    };

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView contentView;
        LinearLayout imgContainer;
        public MyViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.textview_complain_type);
            contentView = view.findViewById(R.id.textview_complain_message);
            imgContainer = view.findViewById(R.id.complain_photo_container);
        }

    }
}

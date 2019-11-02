package aiterminal.android.chdmc.com.aiterminal.network;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;

import aiterminal.android.chdmc.com.aiterminal.R;
import aiterminal.android.chdmc.com.aiterminal.bean.ComplainBean;
import aiterminal.android.chdmc.com.aiterminal.bean.Order;
import aiterminal.android.chdmc.com.aiterminal.manager.LoginManager;
import aiterminal.android.chdmc.com.aiterminal.manager.SPManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by zhouyuhao on 2019/10/15.
 */

public class RequestUtil {

    private static final String TAG = RequestUtil.class.getName();

    private static final String HOST = "http://121.40.150.25:8080";
//    private static final String HOST = "http://30.55.209.71:8080";

    public static void addOrder(Order order, OnResultListner listner) {
        String url = HOST + "/addCollectionOrder";
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("orderMessage", order.getOrderMessage());
        builder.add("orderCallNum", order.getOrderNum());

        StringBuilder stringBuilder = new StringBuilder();
        for (String imgUrl : order.getImgUrls()) {
            stringBuilder.append(imgUrl + ",");
        }

        builder.add("imgUrls", stringBuilder.toString());
        builder.add("orderType", order.getOrderType()+"");
        builder.add("userName", SPManager.getString(SPManager.KEY_USERNAME));
        doPost(url, builder.build(), listner);
    }

    public static void getComplains(OnResultListner listner) {
        String url = HOST + "/getComplain";
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("username", SPManager.getString(SPManager.KEY_USERNAME));
        doPost(url, builder.build(), listner);
    }


    public interface OnResultListner {
        void onSuccess(JSONObject result);
        void onFailed(String result);
    }

    public static void getNews(Callback callback) {
        String url = "";
        doGet(url, null);
    }

    public static void login(String username, String password, OnResultListner listner) {
        String url = HOST + "/login";
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("userName", username);
        builder.add("password", password);
        doPost(url, builder.build(), listner);
    }


    public static void addComplain(ComplainBean complainBean, OnResultListner listner) {
        String url = HOST + "/addComplain";

        FormBody.Builder builder = new FormBody.Builder();

        builder.add("type", complainBean.getComplainType());
        builder.add("ownerId", SPManager.getString(SPManager.KEY_USERNAME));
        if (!TextUtils.isEmpty(complainBean.getImgCloseUrl())) {
            builder.add("imgCloseUrl", complainBean.getImgCloseUrl());
        }

        if (!TextUtils.isEmpty(complainBean.getImgRemoteUrl())) {
            builder.add("imgRemoteUrl", complainBean.getImgRemoteUrl());
        }

        if (!TextUtils.isEmpty(complainBean.getComplainMessage())) {
            builder.add("message", complainBean.getComplainMessage());
        }

        if (complainBean.getImgMoreUrlList() != null
                && complainBean.getImgMoreUrlList().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String imgUrl : complainBean.getImgMoreUrlList()) {
                stringBuilder.append(imgUrl + ",");
            }
            builder.add("imgMoreUrlList", stringBuilder.toString());
        }

        doPost(url, builder.build(), listner);
    }

    public void getComplainList(OnResultListner listner) {
        String url = HOST + "/getComplain";
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("ownerId", SPManager.getString(SPManager.KEY_USERNAME));
        doPost(url, builder.build(), listner);
    }


    private static void doGet(String url, OnResultListner listner) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new MyCallback(listner));

    }

    private static void doPost(String url, RequestBody body, OnResultListner listner) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new MyCallback(listner));

    }


    private static class  MyCallback implements Callback {

        private OnResultListner onResultListner;

        public MyCallback(OnResultListner onResultListner) {
            this.onResultListner = onResultListner;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            Log.d(TAG, "onFailure: " + call.toString());
            if (onResultListner != null) {
                onResultListner.onFailed("failed");
            }

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            JSONObject result = JSONObject.parseObject(response.body().string());
            Log.d(TAG, "onResponse: " + result);
            if (result != null) {
                if (result.getBoolean("success")) {
                    onResultListner.onSuccess(result);
                } else {
                    onResultListner.onFailed(result.getString("message"));
                }
            }

        }
    };


    public static void loadImageByUrl(Context context, String imgUrl, ImageView imageView) {
        try {
            Glide.with(context)
                    .load(imgUrl)
                    .crossFade()
                    .error(R.drawable.ic_hot)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//开启缓存
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

package aiterminal.android.chdmc.com.aiterminal.manager;

import com.alibaba.fastjson.JSONObject;

import aiterminal.android.chdmc.com.aiterminal.database.DbUtil;
import aiterminal.android.chdmc.com.aiterminal.network.RequestUtil;

import static aiterminal.android.chdmc.com.aiterminal.manager.SPManager.KEY_ISLOGIN;
import static aiterminal.android.chdmc.com.aiterminal.manager.SPManager.KEY_PASSWORD;
import static aiterminal.android.chdmc.com.aiterminal.manager.SPManager.KEY_USERICON_URL;
import static aiterminal.android.chdmc.com.aiterminal.manager.SPManager.KEY_USERNAME;

/**
 * Created by zhouyuhao on 2019/10/27.
 */

public class LoginManager {

    private static LoginManager mInstance = new LoginManager();


    public interface LoginCallback {
        void onResult (boolean success);
    }

    public static LoginManager getInstance() {
        return mInstance;
    }

    public void logout() {
        SPManager.saveBoolean(KEY_ISLOGIN, false);
        SPManager.saveString(KEY_USERNAME, "");
        SPManager.saveString(KEY_PASSWORD, "");
    }

    public boolean hasLogin() {
        return SPManager.getBoolean(KEY_ISLOGIN);
    }

    public void login(String username, String password, LoginCallback callback) {
        RequestUtil.login(username, password, new RequestUtil.OnResultListner() {

            @Override
            public void onSuccess(JSONObject result) {
                if (result.getBoolean("success")) {
                    SPManager.saveBoolean(KEY_ISLOGIN, true);
                    JSONObject userInfo = result.getJSONObject("data");
                    SPManager.saveString(KEY_USERNAME, userInfo.getString("userName"));
                    SPManager.saveString(KEY_PASSWORD, userInfo.getString("password"));
                    SPManager.saveString(KEY_USERICON_URL,
                            "https://iconfont.alicdn.com/t/1570687774132.jpeg@200h_200w.jpg");

                    if (callback != null) {
                        callback.onResult(true);
                    }
                }
            }

            @Override
            public void onFailed(String result) {
                SPManager.saveBoolean(KEY_ISLOGIN, false);
                if (callback != null) {
                    callback.onResult(false);
                }
            }
        });
    }
}

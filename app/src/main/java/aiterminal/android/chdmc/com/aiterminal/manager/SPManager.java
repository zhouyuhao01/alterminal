package aiterminal.android.chdmc.com.aiterminal.manager;

import android.content.SharedPreferences;

import aiterminal.android.chdmc.com.aiterminal.MainApplication;

/**
 *
 * sharedPreference 管理类
 *
 * Created by zhouyuhao on 2019/10/30.
 */

public class SPManager {

    public static final String KEY_ISLOGIN = "islogin";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERICON_URL = "usericon_url";

    static SharedPreferences sharedPreferences = MainApplication.getContext()
            .getSharedPreferences("date", MainApplication.getContext().MODE_PRIVATE);
    static SharedPreferences.Editor editor = sharedPreferences.edit();

    public static void saveBoolean(String key, boolean value) {
        editor.putBoolean(key, value).apply();
    }

    public static void saveString(String key, String value) {
        editor.putString(key, value).apply();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }


    public static boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void saveFloat(String key, float value) {
        editor.putFloat(key, value).apply();
    }



}

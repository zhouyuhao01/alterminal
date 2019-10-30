package aiterminal.android.chdmc.com.aiterminal;

import android.app.Application;
import android.content.Context;

import com.speedystone.greendaodemo.db.DaoMaster;
import com.speedystone.greendaodemo.db.DaoSession;
import com.tencent.smtt.sdk.QbSdk;

import org.greenrobot.greendao.database.Database;

/**
 * Created by zhouyuhao on 2019/10/15.
 */

public class MainApplication extends Application {

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    private static DaoSession daoSession;
    private static Context context;


    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
        context = getApplicationContext();
        QbSdk.initX5Environment(getApplicationContext(),  null);
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mydb");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }


}

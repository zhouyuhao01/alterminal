package aiterminal.android.chdmc.com.aiterminal.manager;

import android.content.Context;
import android.content.Intent;

import aiterminal.android.chdmc.com.aiterminal.activity.AddComplainActivity;

/**
 * 打开Activity管理页面
 *
 * Created by zhouyuhao on 2019/10/31.
 */

public class ActivityManager {

    public static final int ACTIVITY_ADD_COMPLAIN = 0;
    public static final int ACTIVITY_ADD_ORDER = 1;
    public static final int ACTIVITY_ADD_RECORD = 2;
    public static final int ACTIVITY_MY_COMPLAIN_LIST= 3;
    public static final int ACTIVITY_NEWS_DETAIL = 4;
    public static final int ACTIVITY_SCORE = 5;
    public static final int ACTIVITY_THROW_GARBAGE = 6;

    public void startActivity(Context context, int activityCode) {
        Intent intent = new Intent();
        switch (activityCode) {
            case ACTIVITY_ADD_COMPLAIN:
                intent.setClass(context, AddComplainActivity.class);

                break;
            case ACTIVITY_ADD_ORDER:
                intent.setClass(context, AddComplainActivity.class);
                break;
            case ACTIVITY_ADD_RECORD:
                intent.setClass(context, AddComplainActivity.class);
                break;
            case ACTIVITY_MY_COMPLAIN_LIST:
                intent.setClass(context, AddComplainActivity.class);
                break;
            case ACTIVITY_NEWS_DETAIL:
                intent.setClass(context, AddComplainActivity.class);
                break;
            case ACTIVITY_SCORE:
                intent.setClass(context, AddComplainActivity.class);
                break;
            case ACTIVITY_THROW_GARBAGE:
                intent.setClass(context, AddComplainActivity.class);
                break;
        }
        if (context != null) {
            context.startActivity(intent);
        }

    }
}

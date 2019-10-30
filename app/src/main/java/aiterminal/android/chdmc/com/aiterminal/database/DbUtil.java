package aiterminal.android.chdmc.com.aiterminal.database;

import com.speedystone.greendaodemo.db.NewsListItemBeanDao;
import com.speedystone.greendaodemo.db.UserInfoDao;

import java.util.ArrayList;
import java.util.List;

import aiterminal.android.chdmc.com.aiterminal.MainApplication;
import aiterminal.android.chdmc.com.aiterminal.bean.NewsListItemBean;
import aiterminal.android.chdmc.com.aiterminal.bean.UserInfo;

/**
 * Created by zhouyuhao on 2019/10/15.
 */

public class DbUtil {

    public static List<NewsListItemBean> getNewsList() {
        NewsListItemBeanDao newsListItemBeanDao = MainApplication.getDaoSession().getNewsListItemBeanDao();
        return newsListItemBeanDao.loadAll();
    }

    public static void insertNewsList(List<NewsListItemBean> newsList) {
        NewsListItemBeanDao newsListItemBeanDao = MainApplication.getDaoSession().getNewsListItemBeanDao();
        newsListItemBeanDao.insertInTx(newsList);
    }

    public static UserInfo getUserInfo() {
        UserInfoDao userInfoDao = MainApplication.getDaoSession().getUserInfoDao();
        return userInfoDao.loadAll().get(0);
    }

    public static void updateUserInfo(String userName, String password) {
        UserInfoDao userInfoDao = MainApplication.getDaoSession().getUserInfoDao();
        UserInfo userInfo = getUserInfo();
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setUserName(userName);
            userInfo.setPassword(password);
            userInfoDao.insert(userInfo);
        } else {
            userInfo.setUserName(userName);
            userInfo.setPassword(password);
            userInfoDao.update(userInfo);
        }

    }

}

package aiterminal.android.chdmc.com.aiterminal.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhouyuhao on 2019/10/30.
 */
@Entity
public class UserInfo {
    public String userName;
    public String userId;
    public String userIconUrl;
    public String password;
    @Generated(hash = 841671451)
    public UserInfo(String userName, String userId, String userIconUrl,
            String password) {
        this.userName = userName;
        this.userId = userId;
        this.userIconUrl = userIconUrl;
        this.password = password;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserIconUrl() {
        return this.userIconUrl;
    }
    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

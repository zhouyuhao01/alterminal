package aiterminal.android.chdmc.com.aiterminal.bean;

import java.util.ArrayList;

/**
 * Created by zhouyuhao on 2019/10/27.
 */

public class ComplainBean {
    private String ownerId;
    private String complainType;
    private String complainMessage;
    private String imgCloseUrl;
    private String imgRemoteUrl;
    private ArrayList<String> imgMoreUrlList = new ArrayList<>();

    public String getComplainType() {
        return complainType;
    }

    public void setComplainType(String complainType) {
        this.complainType = complainType;
    }

    public String getComplainMessage() {
        return complainMessage;
    }

    public void setComplainMessage(String complainMessage) {
        this.complainMessage = complainMessage;
    }

    public String getImgCloseUrl() {
        return imgCloseUrl;
    }

    public void setImgCloseUrl(String imgCloseUrl) {
        this.imgCloseUrl = imgCloseUrl;
    }

    public String getImgRemoteUrl() {
        return imgRemoteUrl;
    }

    public void setImgRemoteUrl(String imgRemoteUrl) {
        this.imgRemoteUrl = imgRemoteUrl;
    }

    public ArrayList<String> getImgMoreUrlList() {
        return imgMoreUrlList;
    }

    public void setImgMoreUrlList(ArrayList<String> imgMoreUrlList) {
        this.imgMoreUrlList = imgMoreUrlList;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}

package aiterminal.android.chdmc.com.aiterminal.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhouyuhao on 2019/9/28.
 */
@Entity
public class NewsListItemBean {

    public String newsId;
    public String newsContent;
    public String newsTitle;
    public String newsImage;
    public String newsUrl;

    public NewsListItemBean() {

    }

    public NewsListItemBean( String newsTitle,String newsContent, String newsImage, String newsUrl) {
        this.newsContent = newsContent;
        this.newsTitle = newsTitle;
        this.newsImage = newsImage;
        this.newsUrl = newsUrl;
    }

    @Generated(hash = 212446040)
    public NewsListItemBean(String newsId, String newsContent, String newsTitle, String newsImage,
            String newsUrl) {
        this.newsId = newsId;
        this.newsContent = newsContent;
        this.newsTitle = newsTitle;
        this.newsImage = newsImage;
        this.newsUrl = newsUrl;
    }



    public String getNewsContent() {
        return this.newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsTitle() {
        return this.newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsImage() {
        return this.newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsUrl() {
        return this.newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsId() {
        return this.newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}

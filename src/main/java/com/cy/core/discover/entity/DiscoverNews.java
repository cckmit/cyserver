package com.cy.core.discover.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Mr.wu on 2017/3/14.
 */
public class DiscoverNews implements Serializable {
    private static final long serialVersionUID = 1L;
    private long newsId;
    private String title;
    private String pic;
    private String pic_xd;
    private Date createTime;
    private String channelName;
    private String introduction;
    private String newsUrl;//新闻网址
    private String newsUrl_xd;
    private String createDate;
    private String updateDate;

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getPic_xd() {
        if(StringUtils.isBlank(pic_xd) && StringUtils.isNotBlank(pic) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(pic.indexOf(Global.URL_DOMAIN) == 0) {
                pic_xd = pic.substring(Global.URL_DOMAIN.length()) ;
                if (!pic_xd.trim().startsWith("/")){
                    pic_xd="/"+pic_xd;
                }
            }else{
                pic_xd=pic;
            }
        }
        return pic_xd;
    }

    public void setPic_xd(String pic_xd) {
        this.pic_xd = pic_xd;
    }

    public String getNewsUrl_xd() {
        if(StringUtils.isBlank(newsUrl_xd) && StringUtils.isNotBlank(newsUrl) && StringUtils.isNotBlank(Global.cy_server_url)) {
            if(newsUrl.indexOf(Global.cy_server_url) == 0) {
                newsUrl_xd = newsUrl.substring(Global.cy_server_url.length()) ;
                if (!newsUrl_xd.trim().startsWith("/")){
                    newsUrl_xd="/"+newsUrl_xd;
                }
            }else{
                newsUrl_xd = newsUrl;
            }
        }
        return newsUrl_xd;
    }
    public void setNewsUrl_xd(String newsUrl_xd) {
        this.newsUrl_xd = newsUrl_xd;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}

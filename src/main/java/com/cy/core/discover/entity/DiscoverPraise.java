package com.cy.core.discover.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/3/14.
 */
public class DiscoverPraise implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * contentId 当前动态id
    */
    private String contentId;

    /*
    * createTime 发表时间
    */
    private String createTime;
    /*
    * userId 用户id
    */
    private String userId;

    /*
    * discoverUserId 被点赞者的id
    */
    private String discoverUserId;

    /*
    * userPic 用户图像
    */
    private String picture;

    /*
    * userName 用户姓名
    */
    private String name;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDiscoverUserId() {
        return discoverUserId;
    }

    public void setDiscoverUserId(String discoverUserId) {
        this.discoverUserId = discoverUserId;
    }

}

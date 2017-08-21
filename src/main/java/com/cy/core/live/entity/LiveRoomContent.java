package com.cy.core.live.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/4/7.
 */
public class LiveRoomContent implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * liveContentId 直播内容ID
    */
    private String liveContentId;

    /*
    * liveRoomId 直播间ID
    */
    private String liveRoomId;

    /*
    * liveTopicId 话题ID
    */
    private String liveTopicId;

    /*
    * liveContent 直播内容
    */
    private String liveContent;

    /*
    * liveContentType 直播内容标志
    */
    private String liveContentType;

    /*
    * liveContentUserId 发言人的ID
    */
    private String liveContentUserId;

    /*
    * liveContentCreateTime 直播内容发表时间
    */
    private String liveContentCreateTime;

    /*
    * picture 用户图像
    */
    private String picture;

    /*
    * name 用户姓名
    */
    private String name;


    public String getLiveContentId() {
        return liveContentId;
    }

    public void setLiveContentId(String liveContentId) {
        this.liveContentId = liveContentId;
    }

    public String getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(String liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public String getLiveContent() {
        return liveContent;
    }

    public void setLiveContent(String liveContent) {
        this.liveContent = liveContent;
    }

    public String getLiveContentType() {
        return liveContentType;
    }

    public void setLiveContentType(String liveContentType) {
        this.liveContentType = liveContentType;
    }

    public String getLiveContentCreateTime() {
        return liveContentCreateTime;
    }

    public void setLiveContentCreateTime(String liveContentCreateTime) {
        this.liveContentCreateTime = liveContentCreateTime;
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

    public String getLiveContentUserId() {
        return liveContentUserId;
    }

    public void setLiveContentUserId(String liveContentUserId) {
        this.liveContentUserId = liveContentUserId;
    }

    public String getLiveTopicId() {
        return liveTopicId;
    }

    public void setLiveTopicId(String liveTopicId) {
        this.liveTopicId = liveTopicId;
    }

}

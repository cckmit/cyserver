package com.cy.core.live.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.wu on 2017/4/7.
 */
public class LiveRoom implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * liveRoomId 直播间ID
    */
    private String liveRoomId;
    /*
    * liveRoomUserId 直播人的ID
    */
    private String liveRoomUserId;

    /*
    * liveRoomName 直播间名称
    */
    private String liveRoomName;

    /*
    * liveRoomPic 直播间头像
    */
    private String liveRoomPic;

    /*
    * liveRoomWallPhoto 直播间背景
    */
    private String liveRoomWallPhoto;

    /*
    * liveRoomDescription 直播间介绍
    */
    private String liveRoomDescription;

    /*
    * liveRoomCreateTime 直播间创建时间
    */
    private String liveRoomCreateTime;

    /*
    * picture 用户图像
    */
    private String picture;

    /*
    * name 用户姓名
    */
    private String name;

    public String getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(String liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public String getLiveRoomUserId() {
        return liveRoomUserId;
    }

    public void setLiveRoomUserId(String liveRoomUserId) {
        this.liveRoomUserId = liveRoomUserId;
    }

    public String getLiveRoomName() {
        return liveRoomName;
    }

    public void setLiveRoomName(String liveRoomName) {
        this.liveRoomName = liveRoomName;
    }

    public String getLiveRoomPic() {
        return liveRoomPic;
    }

    public void setLiveRoomPic(String liveRoomPic) {
        this.liveRoomPic = liveRoomPic;
    }

    public String getLiveRoomWallPhoto() {
        return liveRoomWallPhoto;
    }

    public void setLiveRoomWallPhoto(String liveRoomWallPhoto) {
        this.liveRoomWallPhoto = liveRoomWallPhoto;
    }

    public String getLiveRoomDescription() {
        return liveRoomDescription;
    }

    public void setLiveRoomDescription(String liveRoomDescription) {
        this.liveRoomDescription = liveRoomDescription;
    }

    public String getLiveRoomCreateTime() {
        return liveRoomCreateTime;
    }

    public void setLiveRoomCreateTime(String liveRoomCreateTime) {
        this.liveRoomCreateTime = liveRoomCreateTime;
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

}

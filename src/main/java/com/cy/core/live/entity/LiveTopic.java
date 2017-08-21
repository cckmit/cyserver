package com.cy.core.live.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/4/7.
 */
public class LiveTopic implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * liveTopicId 话题ID
    */
    private String liveTopicId;

    /*
    * liveRoomId 直播间ID
    */
    private String liveRoomId;

    /*
    * liveTopicUserId 创建话题人的ID
    */
    private String liveTopicUserId;

    /*
    * liveTopicName 话题名称
    */
    private String liveTopicName;

    /*
    * liveTopicPersonName 话题主讲人姓名
    */
    private String liveTopicPersonName;

    /*
    * liveTopicWallPhoto 话题背景
    */
    private String liveTopicWallPhoto;

    /*
    * liveTopicDescription 话题描述
    */
    private String liveTopicDescription;

    /*
    * liveTopicNumOfPeople 话题人数
    */
    private String liveTopicNumOfPeople;

    /*
    * liveTopicPhoto 相关话题图片
    */
    private String liveTopicPhoto;

    /*
    * liveIn 直播状态0：结束直播 1：正在直播中
    */
    private String liveIn;

    /*
    * liveTopicCreateTime 话题创建时间
    */
    private String liveTopicCreateTime;

    /*
    * picture 用户图像
    */
    private String picture;

    /*
    * name 用户姓名
    */
    private String name;

    /*
    * liveRoomName 直播间用户图像
    */
    private String liveRoomName;

    /*
    * liveRoomPic 直播间用户姓名
    */
    private String liveRoomPic;

    /*
    * liveRoomTopicUrl 直播间话题详情url
    */
    private String liveRoomTopicUrl;

    /*
    * liveRoomTopicUrl_xd 直播间话题详情相对url
    */
    private String liveRoomTopicUrl_xd;

    public String getLiveTopicId() {
        return liveTopicId;
    }

    public void setLiveTopicId(String liveTopicId) {
        this.liveTopicId = liveTopicId;
    }

    public String getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(String liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public String getLiveTopicUserId() {
        return liveTopicUserId;
    }

    public void setLiveTopicUserId(String liveTopicUserId) {
        this.liveTopicUserId = liveTopicUserId;
    }

    public String getLiveTopicName() {
        return liveTopicName;
    }

    public void setLiveTopicName(String liveTopicName) {
        this.liveTopicName = liveTopicName;
    }

    public String getLiveTopicPersonName() {
        return liveTopicPersonName;
    }

    public void setLiveTopicPersonName(String liveTopicPersonName) {
        this.liveTopicPersonName = liveTopicPersonName;
    }

    public String getLiveTopicWallPhoto() {
        return liveTopicWallPhoto;
    }

    public void setLiveTopicWallPhoto(String liveTopicWallPhoto) {
        this.liveTopicWallPhoto = liveTopicWallPhoto;
    }

    public String getLiveTopicDescription() {
        return liveTopicDescription;
    }

    public void setLiveTopicDescription(String liveTopicDescription) {
        this.liveTopicDescription = liveTopicDescription;
    }

    public String getLiveTopicPhoto() {
        return liveTopicPhoto;
    }

    public void setLiveTopicPhoto(String liveTopicPhoto) {
        this.liveTopicPhoto = liveTopicPhoto;
    }

    public String getLiveTopicCreateTime() {
        return liveTopicCreateTime;
    }

    public void setLiveTopicCreateTime(String liveTopicCreateTime) {
        this.liveTopicCreateTime = liveTopicCreateTime;
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

    public String getLiveTopicNumOfPeople() {
        return liveTopicNumOfPeople;
    }

    public void setLiveTopicNumOfPeople(String liveTopicNumOfPeople) {
        this.liveTopicNumOfPeople = liveTopicNumOfPeople;
    }

    public String getLiveIn() {
        return liveIn;
    }

    public void setLiveIn(String liveIn) {
        this.liveIn = liveIn;
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

    public String getLiveRoomTopicUrl() {
        return liveRoomTopicUrl;
    }

    public void setLiveRoomTopicUrl(String liveRoomTopicUrl) {
        this.liveRoomTopicUrl = liveRoomTopicUrl;
    }

    public String getLiveRoomTopicUrl_xd() {
        return liveRoomTopicUrl_xd;
    }

    public void setLiveRoomTopicUrl_xd(String liveRoomTopicUrl_xd) {
        this.liveRoomTopicUrl_xd = liveRoomTopicUrl_xd;
    }
}

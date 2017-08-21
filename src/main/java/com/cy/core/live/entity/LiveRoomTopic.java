package com.cy.core.live.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.wu on 2017/4/7.
 */
public class LiveRoomTopic implements Serializable {
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
    * liveRoomAttentionNum 关注直播间人数
    */
    private long liveRoomAttentionNum;


    /*
    * liveTopicId 话题ID
    */
    private String liveTopicId;

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
    * liveIn 直播状态 0：结束直播 1：正在直播中
    */
    private String liveIn;

    /*
    * liveTopicPhoto 相关话题图片
    */
    private String liveTopicPhoto;

    /*
    * liveTopicCreateTime 话题创建时间
    */
    private String liveTopicCreateTime;

    /*
    * attentionFlag 是否关注直播0：未关注1：已关注
    */
    private String attentionFlag;

    /*
    * picture 用户图像
    */
    private String picture;

    /*
    * name 用户姓名
    */
    private String name;

    //话题列表
    private List<LiveTopic> liveTopic;

    //话题详情
    private LiveTopic liveTopicInfo;

    //话题总数
    private long liveTopicCnt;

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

    public long getLiveRoomAttentionNum() {
        return liveRoomAttentionNum;
    }

    public void setLiveRoomAttentionNum(long liveRoomAttentionNum) {
        this.liveRoomAttentionNum = liveRoomAttentionNum;
    }

    public List<LiveTopic> getLiveTopic() {
        return liveTopic;
    }

    public void setLiveTopic(List<LiveTopic> liveTopic) {
        this.liveTopic = liveTopic;
    }

    public long getLiveTopicCnt() {
        return liveTopicCnt;
    }

    public void setLiveTopicCnt(long liveTopicCnt) {
        this.liveTopicCnt = liveTopicCnt;
    }

    public String getAttentionFlag() {
        return attentionFlag;
    }

    public void setAttentionFlag(String attentionFlag) {
        this.attentionFlag = attentionFlag;
    }

    public LiveTopic getLiveTopicInfo() {
        return liveTopicInfo;
    }

    public void setLiveTopicInfo(LiveTopic liveTopicInfo) {
        this.liveTopicInfo = liveTopicInfo;
    }

    public String getLiveTopicId() {
        return liveTopicId;
    }

    public void setLiveTopicId(String liveTopicId) {
        this.liveTopicId = liveTopicId;
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

    public String getLiveTopicNumOfPeople() {
        return liveTopicNumOfPeople;
    }

    public void setLiveTopicNumOfPeople(String liveTopicNumOfPeople) {
        this.liveTopicNumOfPeople = liveTopicNumOfPeople;
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

    public String getLiveIn() {
        return liveIn;
    }

    public void setLiveIn(String liveIn) {
        this.liveIn = liveIn;
    }
}

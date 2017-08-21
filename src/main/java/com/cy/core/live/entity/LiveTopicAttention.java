package com.cy.core.live.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/4/7.
 */
public class LiveTopicAttention implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * liveTopicAttentionId 参与话题ID
    */
    private String liveTopicAttentionId;
    /*
    * liveTopicId 话题ID
    */
    private String liveTopicId;

    /*
    * liveTopicAttentionUserId 参与话题人的ID
    */
    private String liveTopicAttentionUserId;

    /*
    * liveUserId 发表话题的人
    */
    private String liveUserId;

    /*
    * liveRoomId 当前话题所属直播间ID
    */
    private String liveRoomId;

    /*
    * liveTopicAttentionCreateTime 参与话题的时间
    */
    private String liveTopicAttentionCreateTime;


    public String getLiveTopicAttentionId() {
        return liveTopicAttentionId;
    }

    public void setLiveTopicAttentionId(String liveTopicAttentionId) {
        this.liveTopicAttentionId = liveTopicAttentionId;
    }

    public String getLiveTopicId() {
        return liveTopicId;
    }

    public void setLiveTopicId(String liveTopicId) {
        this.liveTopicId = liveTopicId;
    }

    public String getLiveTopicAttentionUserId() {
        return liveTopicAttentionUserId;
    }

    public void setLiveTopicAttentionUserId(String liveTopicAttentionUserId) {
        this.liveTopicAttentionUserId = liveTopicAttentionUserId;
    }

    public String getLiveUserId() {
        return liveUserId;
    }

    public void setLiveUserId(String liveUserId) {
        this.liveUserId = liveUserId;
    }

    public String getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(String liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public String getLiveTopicAttentionCreateTime() {
        return liveTopicAttentionCreateTime;
    }

    public void setLiveTopicAttentionCreateTime(String liveTopicAttentionCreateTime) {
        this.liveTopicAttentionCreateTime = liveTopicAttentionCreateTime;
    }
}

package com.cy.core.live.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/4/7.
 */
public class LiveRoomAttention implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * liveRoomAttentionId 关注ID
    */
    private String liveRoomAttentionId;
    /*
    * liveRoomAttentionUserId 关注人的ID
    */
    private String liveRoomAttentionUserId;

    /*
    * liveUserId 被关注的人的ID
    */
    private String liveUserId;

    /*
    * liveRoomId 关注的直播间ID
    */
    private String liveRoomId;

    /*
    * liveRoomAttentionCreateTime 关注时间
    */
    private String liveRoomAttentionCreateTime;

    public String getLiveRoomAttentionId() {
        return liveRoomAttentionId;
    }

    public void setLiveRoomAttentionId(String liveRoomAttentionId) {
        this.liveRoomAttentionId = liveRoomAttentionId;
    }

    public String getLiveRoomAttentionUserId() {
        return liveRoomAttentionUserId;
    }

    public void setLiveRoomAttentionUserId(String liveRoomAttentionUserId) {
        this.liveRoomAttentionUserId = liveRoomAttentionUserId;
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

    public String getLiveRoomAttentionCreateTime() {
        return liveRoomAttentionCreateTime;
    }

    public void setLiveRoomAttentionCreateTime(String liveRoomAttentionCreateTime) {
        this.liveRoomAttentionCreateTime = liveRoomAttentionCreateTime;
    }
}

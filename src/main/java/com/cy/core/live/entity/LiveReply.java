package com.cy.core.live.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/4/7.
 */
public class LiveReply implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
    * liveReplyId 回复ID
     */
    private String liveReplyId;

    /*
    * liveCommentId 留言ID
    */
    private String liveCommentId;
    /*
    * liveReplyUserId 回复人ID
    */
    private String liveReplyUserId;

    /*
    * liveRoomId 直播间ID
    */
    private String liveRoomId;

    /*
    * liveReply 回复内容
    */
    private String liveReply;

    /*
    * liveReplyCreateTime 回复时间
    */
    private String liveReplyCreateTime;

    /*
    * picture 用户图像
    */
    private String picture;

    /*
    * name 用户姓名
    */
    private String name;


    public String getLiveReplyId() {
        return liveReplyId;
    }

    public void setLiveReplyId(String liveReplyId) {
        this.liveReplyId = liveReplyId;
    }

    public String getLiveCommentId() {
        return liveCommentId;
    }

    public void setLiveCommentId(String liveCommentId) {
        this.liveCommentId = liveCommentId;
    }

    public String getLiveReplyUserId() {
        return liveReplyUserId;
    }

    public void setLiveReplyUserId(String liveReplyUserId) {
        this.liveReplyUserId = liveReplyUserId;
    }

    public String getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(String liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public String getLiveReply() {
        return liveReply;
    }

    public void setLiveReply(String liveReply) {
        this.liveReply = liveReply;
    }

    public String getLiveReplyCreateTime() {
        return liveReplyCreateTime;
    }

    public void setLiveReplyCreateTime(String liveReplyCreateTime) {
        this.liveReplyCreateTime = liveReplyCreateTime;
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

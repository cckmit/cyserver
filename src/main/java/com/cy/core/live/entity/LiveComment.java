package com.cy.core.live.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.wu on 2017/4/7.
 */
public class LiveComment implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * liveCommentId 留言ID
    */
    private String liveCommentId;
    /*
    * liveCommentUserId 留言人ID
    */
    private String liveCommentUserId;

    /*
    * liveUserId 被留言人的ID
    */
    private String liveUserId;

    /*
    * liveRoomId 直播间ID
    */
    private String liveRoomId;

    /*
    * liveComment 留言内容
    */
    private String liveComment;

    /*
    * liveCommentCreateTime 留言时间
    */
    private String liveCommentCreateTime;

    /*
    * picture 用户图像
    */
    private String picture;

    /*
    * name 用户姓名
    */
    private String name;

    /*
    * liveReply 回复列表
    */
    private List<LiveReply> liveReply;


    public String getLiveCommentId() {
        return liveCommentId;
    }

    public void setLiveCommentId(String liveCommentId) {
        this.liveCommentId = liveCommentId;
    }

    public String getLiveCommentUserId() {
        return liveCommentUserId;
    }

    public void setLiveCommentUserId(String liveCommentUserId) {
        this.liveCommentUserId = liveCommentUserId;
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

    public String getLiveComment() {
        return liveComment;
    }

    public void setLiveComment(String liveComment) {
        this.liveComment = liveComment;
    }

    public String getLiveCommentCreateTime() {
        return liveCommentCreateTime;
    }

    public void setLiveCommentCreateTime(String liveCommentCreateTime) {
        this.liveCommentCreateTime = liveCommentCreateTime;
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

    public List<LiveReply> getLiveReply() {
        return liveReply;
    }

    public void setLiveReply(List<LiveReply> liveReply) {
        this.liveReply = liveReply;
    }
}

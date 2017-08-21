package com.cy.core.discover.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/3/14.
 */
public class DiscoverReply implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * replyId 回复id
    */
    private String replyId;

    /*
    * replyCommentId 回复的那条评论id
    */
    private String replyCommentId;

    /*
    * replyContent 回复内容
    */
    private String replyContent;

    /*
    * replyUserId 回复的人的id
    */
    private String replyUserId;

    /*
    * replyCreateTime 发表时间
    */
    private String replyCreateTime;

    /*
    * discoverUserId 被评论者的id
    */
    private String discoverUserId;

    /*
    * replyPicture 用户图像
    */
    private String replyPicture;

    /*
    * replyName 用户姓名
    */
    private String replyName;

    /*
    * flag 回复标识(0：回复的是评论 1：回复的是回复)
    */
    private String flag;

    /*
    * messageId 当前发布的这条朋友圈的ID
    */
    private String messageId;

    public String getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(String replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getDiscoverUserId() {
        return discoverUserId;
    }

    public void setDiscoverUserId(String discoverUserId) {
        this.discoverUserId = discoverUserId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyCreateTime() {
        return replyCreateTime;
    }

    public void setReplyCreateTime(String replyCreateTime) {
        this.replyCreateTime = replyCreateTime;
    }

    public String getReplyPicture() {
        return replyPicture;
    }

    public void setReplyPicture(String replyPicture) {
        this.replyPicture = replyPicture;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}

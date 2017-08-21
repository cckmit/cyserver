package com.cy.core.discover.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/3/14.
 */
public class DiscoverReplyComment implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * commentId 评论的内容的ID
    */
    private String commentId;

    /*
    * comment 用户评论内容
    */
    private String commentContent;

    /*
    * createTime 用户评论的时间
    */
    private String commentCreateTime;

    /*
    * name 评论人的姓名
    */
    private String commentName;

    /*
    * userId 评论人的ID
    */
    private String commentUserId;

    /*
    * picture 评论人的头像
    */
    private String commentPicture;

    /*
    * replyId 回复的内容的ID
    */
    private String replyId;

    /*
    * replyContent 回复的内容
    */
    private String replyContent;

    /*
    * replyUserId 回复人的ID
    */
    private String replyUserId;

    /*
    * replyName 回复人的姓名
    */
    private String replyName;

    /*
    * replyPicture 回复人的头像
    */
    private String replyPicture;

    /*
    * replyCreateTime 回复的时间
    */
    private String replyCreateTime;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentCreateTime() {
        return commentCreateTime;
    }

    public void setCommentCreateTime(String commentCreateTime) {
        this.commentCreateTime = commentCreateTime;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public String getCommentPicture() {
        return commentPicture;
    }

    public void setCommentPicture(String commentPicture) {
        this.commentPicture = commentPicture;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getReplyPicture() {
        return replyPicture;
    }

    public void setReplyPicture(String replyPicture) {
        this.replyPicture = replyPicture;
    }

    public String getReplyCreateTime() {
        return replyCreateTime;
    }

    public void setReplyCreateTime(String replyCreateTime) {
        this.replyCreateTime = replyCreateTime;
    }
}

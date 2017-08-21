package com.cy.core.discover.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.wu on 2017/3/14.
 */
public class DiscoverComment implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * contentId 当前朋友圈id
    */
    private String contentId;

    /*
    * commentContent 用户评论内容
    */
    private String commentContent;

    /*
    * commentId 当前评论的id
    */
    private String commentId;

    /*
    * commentCreateTime 发表时间
    */
    private String commentCreateTime;

    /*
    * commentUserId 用户id
    */
    private String commentUserId;

    /*
    * commentPicture 用户图像
    */
    private String commentPicture;

    /*
    * commentName 用户姓名
    */
    private String commentName;


    /*
    * discoverUserId 被评论者的id
    */
    private String discoverUserId;

    /*
    * discoverPicture 被评论者的图像
    */
    private String discoverPicture;

    /*
    * discoverName 被评论者的姓名
    */
    private String discoverName;

    /*
    * discoverReply 回复列表
    */
//    private List<DiscoverReply> discoverReply;//回复列表

    public String getDiscoverUserId() {
        return discoverUserId;
    }

    public void setDiscoverUserId(String discoverUserId) {
        this.discoverUserId = discoverUserId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getDiscoverPicture() {
        return discoverPicture;
    }

    public void setDiscoverPicture(String discoverPicture) {
        this.discoverPicture = discoverPicture;
    }

    public String getDiscoverName() {
        return discoverName;
    }

    public void setDiscoverName(String discoverName) {
        this.discoverName = discoverName;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
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

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

//    public List<DiscoverReply> getDiscoverReply() {
//        return discoverReply;
//    }
//
//    public void setDiscoverReply(List<DiscoverReply> discoverReply) {
//        this.discoverReply = discoverReply;
//    }
}

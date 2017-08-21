package com.cy.core.discover.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.wu on 2017/5/26.
 */
public class DiscoverMsgPush implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * id 推送消息ID
    */
    private String id;

    /*
    * pushUserId 推送人ID
    */
    private String pushUserId;

    /*
    * bePushUserId 被推送人的ID
    */
    private String bePushUserId;

    /*
    * message 推送内容
    */
    private String message;

    /*
    * contentId 当条朋友圈的ID
    */
    private String contentId;

    /*
    * contentId 当条评论的ID
    */
    private String commentId;

    /*
    * contentId 当条评论的ID
    */
    private String replyId;

    /*
    * isRead 是否已读 已读1 未读0
    */
    private String isRead;

    /*
    * messageType 消息类型1点赞2评论3回复
    */
    private String messageType;

    /*
    * createTime 推送时间
    */
    private String createTime;

    /*
    * name 姓名
    */
    private String name;

    /*
    * picture 头像
    */
    private String picture;

    /*
    * total 推送消息总数
    */
    private String total;

    /*
    * notReadCount 未读消息推送消息总数
    */
    private String notReadCount;

    /*
    * discoverPushMsgList 推送消息列表
    */
    private List<DiscoverMsgPush> discoverPushMsgList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPushUserId() {
        return pushUserId;
    }

    public void setPushUserId(String pushUserId) {
        this.pushUserId = pushUserId;
    }

    public String getBePushUserId() {
        return bePushUserId;
    }

    public void setBePushUserId(String bePushUserId) {
        this.bePushUserId = bePushUserId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<DiscoverMsgPush> getDiscoverPushMsgList() {
        return discoverPushMsgList;
    }

    public void setDiscoverPushMsgList(List<DiscoverMsgPush> discoverPushMsgList) {
        this.discoverPushMsgList = discoverPushMsgList;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNotReadCount() {
        return notReadCount;
    }

    public void setNotReadCount(String notReadCount) {
        this.notReadCount = notReadCount;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }
}

package com.cy.core.express.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.wu on 2017/6/7.
 */
public class ExpressContent implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * expressId 表白内容ID
    */
    private String expressId;
    /*
    * accountNum 用户ID
    */
    private String accountNum;
    /*
     * name 用户姓名
     */
    private String name;
    /*
    * expressContent 表白内容
    */
    private String expressContent;
    /*
     * pictureUrl 表白图片Url
     */
    private String pictureUrl;
    /*
    * expressTo 被表白的人
    */
    private String expressTo;

    /*
    * expressFrom 表白的人
    */
    private String expressFrom;

    /*
    * createTime 表白时间
    */
    private String createTime;

    /*
    * expressComment 表白评论列表
    */
    private List<ExpressComment> expressCommentList;
    /*
     * upvoteState 点赞状态（0：未点赞   1：已点赞）
     */
    private String upvoteState;
    /*
     * upvoteCount 点赞次数
     */
    private long upvoteCount;
    /*
     * commentCount 评论次数
    */
    private long commentCount;
    /*
    * picture 用户图像
    */
    private String picture;
    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getExpressContent() {
        return expressContent;
    }

    public void setExpressContent(String expressContent) {
        this.expressContent = expressContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpressTo() {
        return expressTo;
    }

    public void setExpressTo(String expressTo) {
        this.expressTo = expressTo;
    }

    public String getExpressFrom() {
        return expressFrom;
    }

    public void setExpressFrom(String expressFrom) {
        this.expressFrom = expressFrom;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public List<ExpressComment> getExpressCommentList() {
        return expressCommentList;
    }

    public void setExpressCommentList(List<ExpressComment> expressCommentList) {
        this.expressCommentList = expressCommentList;
    }
    public String getUpvoteState() {
        return upvoteState;
    }

    public void setUpvoteState(String upvoteState) {
        this.upvoteState = upvoteState;
    }

    public long getUpvoteCount() {
        return upvoteCount;
    }

    public void setUpvoteCount(long upvoteCount) {
        this.upvoteCount = upvoteCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

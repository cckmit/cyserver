package com.cy.core.express.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/6/7.
 */
public class ExpressUpvote implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * upvoteId 点赞ID
    */
    private String upvoteId;
    /*
    * expressId 表白墙ID
    */
    private String expressId;
    /*
    * accountNum 用户ID
    */
    private String accountNum;
    /*
     * upvoteState 点赞状态
     */
    private String upvoteState;
    /*
    * createTime 评论时间
    */
    private String createTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUpvoteId() {
        return upvoteId;
    }

    public void setUpvoteId(String upvoteId) {
        this.upvoteId = upvoteId;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getUpvoteState() {
        return upvoteState;
    }

    public void setUpvoteState(String upvoteState) {
        this.upvoteState = upvoteState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
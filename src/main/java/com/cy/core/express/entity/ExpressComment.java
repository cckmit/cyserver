package com.cy.core.express.entity;

import java.io.Serializable;

/**
 * Created by Mr.wu on 2017/6/7.
 */
public class ExpressComment implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
    * commentId 评论ID
    */
    private String commentId;
    /*
    * accountNum 用户ID
    */
    private String accountNum;
    /*
    * name 用户名
    */
    private String name;
    /*
    * expressId 表白内容ID
    */
    private String expressId;

    /*
    * commentContent 评论内容
    */
    private String commentContent;

    /*
    * createTime 评论时间
    */
    private String createTime;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getAccountNum() {return accountNum;}

    public void setAccountNum(String accountNum) {this.accountNum = accountNum;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

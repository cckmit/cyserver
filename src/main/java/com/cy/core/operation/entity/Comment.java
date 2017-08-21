package com.cy.core.operation.entity;

import com.cy.base.entity.DataEntity;
import com.cy.common.utils.StringUtils;
import com.cy.system.Global;

import java.util.List;

/**
 * Created by cha0res on 12/27/16.
 */
public class Comment extends DataEntity<Comment> {
    private String userId;          //评论人ID
    private String goalUserId;      //被评论人ID
    private String bussId;          //评论业务ID
    private String content;         //评论内容
    private String star;            //打星
    private String praise;          //点赞
    private String bussType;        //评论业务      10活动20社团30返校计划40校友企业50校友产品
    private String userPic;         //评论人头像
    private String userPicUrl;     //评论人头像绝对路径
    private String userName;        //评论人姓名
    private String type;            //业务类型      10主页务评论20对评论评论25删除评论30主业务点赞40对评论点赞50主业务打星60对评论打星
    private List<Comment> child;    //子评论
    private String praiseNumber;    //点赞数
    private String commentNumber;   //评论数


    private String isOwn;           //是否当前用户发起
    private String hasPraised;      //是否点赞
    private String currentUserId;   //当前用户ID
    private long begin;
    private long end;
    private String isNoLimit;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoalUserId() {
        return goalUserId;
    }

    public void setGoalUserId(String goalUserId) {
        this.goalUserId = goalUserId;
    }

    public String getBussId() {
        return bussId;
    }

    public void setBussId(String bussId) {
        this.bussId = bussId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getBussType() {
        return bussType;
    }

    public void setBussType(String bussType) {
        this.bussType = bussType;
    }

    public String getUserPic() {
        if(StringUtils.isNotBlank(userPic) && StringUtils.isNotBlank(userPicUrl) && userPic.indexOf("http") < 0){
            if(userPicUrl.trim().indexOf(Global.URL_DOMAIN) == 0) {
                userPic = userPicUrl.trim().substring(Global.URL_DOMAIN.length()) ;
                if (!userPic.trim().startsWith("/")){
                    userPic="/"+userPic;
                }
            }else{
                userPic=userPicUrl;
            }
        }
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Comment> getChild() {
        return child;
    }

    public void setChild(List<Comment> child) {
        this.child = child;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public long getBegin() {
        return begin;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getIsNoLimit() {
        return isNoLimit;
    }

    public void setIsNoLimit(String isNoLimit) {
        this.isNoLimit = isNoLimit;
    }

    public String getIsOwn() {
        return isOwn;
    }

    public void setIsOwn(String isOwn) {
        this.isOwn = isOwn;
    }

    public String getHasPraised() {
        return hasPraised;
    }

    public void setHasPraised(String hasPraised) {
        this.hasPraised = hasPraised;
    }

    public String getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(String praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public String getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(String commentNumber) {
        this.commentNumber = commentNumber;
    }

    public String getUserPicUrl() {
        if(StringUtils.isBlank(userPicUrl) && StringUtils.isNotBlank(userPic) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(userPic.indexOf("http") < 0) {
                if (!userPic.trim().startsWith("/")){
                    userPic="/"+userPic;
                }
                userPicUrl = Global.URL_DOMAIN + userPic ;
            }else{
                userPicUrl=userPic;
            }
        }
        return userPicUrl;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }
}

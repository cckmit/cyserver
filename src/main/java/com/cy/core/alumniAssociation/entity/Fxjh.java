package com.cy.core.alumniAssociation.entity;

import com.cy.base.entity.DataEntity;
import com.cy.common.utils.StringUtils;
import com.cy.core.schoolServ.entity.BackSchoolSign;
import com.cy.system.Global;

import java.util.List;

/**
 * Created by drx on 16/8/9.
 */
public class Fxjh extends DataEntity<Fxjh> {

    private String topic;                   //计划主题
    private String number;                  //计划人数
    private String time;                    //返校时间
    private String classinfo;               //班级信息
    private String other;                   //描述
    private String userId;                  //创建者ID
    private String status;                  //审核状态（10待审核，20审核通过，30审核不通过）
    private String countMember;             //报名人数
    private String userPic;                 //创建者头像
    private String userPicUrl;              //创建者头像相对路径
    private String userPic_xd;
    private String userName;                //创建者姓名
    private String favoriteNumber;          //收藏人数
    private String clickNumber;             //点击人数
    private String commentNumber;           //评论人数
    private String praiseNumber;            //点赞人数
    private List<BackSchoolSign> signList;  //报名人数
    private String isOwner;                 //是否当前用户创建
    private String hasPraised;              //是否已点赞
    private String isSigned;                //是否报名
    private String hasFavorited;            //是否收藏
    private String endTime;                 //结束时间
    private String timeState;
    private String content;
    private String classInfoName;
    private String poster;
    private String posterUrl;
    private String poster_xd;
    private String userCommentCount;
    private String type;
    private String fxjhUrl;//返校计划详情url
    private String fxjhUrl_xd;//返校计划详情相对url
    //3.0追加字段
    private String place;//返校地点
    private String name;//发起人
    private String auditOpinion;//审核意见
    private String auditUserId;// 审核人
    private String auditTime;//审核时间
    private String isFree;//（0：免费；1：收费）
    private String cost;//费用
    private String costComment;//费用说明
    private String qrCodeUrl;//签到二维码图片路径
    private String signupStartTime;//报名开始时间
    private String signupEndTime;//报名结束时间
    private String needSignIn;//是否需要签到（0=不需要， 1=需要）
    private String signInCode;//签到码
    private String userInfoId;//前台创建个人活动的用户前台创建个人活动的用户（实际保存accountNum字段）
    private String needAuth;//活动是否需要认证
    private String nowStatus;   //根据审核、状态、当前时间等各种因素所得的当前活动状态
    private String eventStatus;//当前活动状态（50：'未开始' 60：'进行中' 70：'已结束' 80：'报名中' 90： '报名已截止'）
    private String services;//返校计划提供的服务


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassinfo() {
        return classinfo;
    }

    public void setClassinfo(String classinfo) {
        this.classinfo = classinfo;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountMember() {
        return countMember;
    }

    public void setCountMember(String countMember) {
        this.countMember = countMember;
    }

    public String getUserPic() {
        if(StringUtils.isBlank(userPic) && StringUtils.isNotBlank(userPicUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(userPicUrl.indexOf("http") < 0) {
                userPic = Global.URL_DOMAIN + userPicUrl ;
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

    public String getFavoriteNumber() {
        return favoriteNumber;
    }

    public void setFavoriteNumber(String favoriteNumber) {
        this.favoriteNumber = favoriteNumber;
    }

    public String getClickNumber() {
        return clickNumber;
    }

    public void setClickNumber(String clickNumber) {
        this.clickNumber = clickNumber;
    }

    public String getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(String commentNumber) {
        this.commentNumber = commentNumber;
    }

    public String getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(String praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public List<BackSchoolSign> getSignList() {
        return signList;
    }

    public void setSignList(List<BackSchoolSign> signList) {
        this.signList = signList;
    }

    public String getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(String isOwner) {
        this.isOwner = isOwner;
    }

    public String getHasPraised() {
        return hasPraised;
    }

    public void setHasPraised(String hasPraised) {
        this.hasPraised = hasPraised;
    }

    public String getIsSigned() {
        return isSigned;
    }

    public void setIsSigned(String isSigned) {
        this.isSigned = isSigned;
    }

    public String getHasFavorited() {
        return hasFavorited;
    }

    public void setHasFavorited(String hasFavorited) {
        this.hasFavorited = hasFavorited;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTimeState() {
        return timeState;
    }

    public void setTimeState(String timeState) {
        this.timeState = timeState;
    }

    public String getPoster() {
        if(StringUtils.isBlank(poster) && StringUtils.isNotBlank(posterUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(posterUrl.indexOf("http") < 0) {
                poster = Global.URL_DOMAIN + posterUrl ;
            }else{
                poster=posterUrl;
            }
        }
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPosterUrl() {
        if(StringUtils.isBlank(posterUrl) && StringUtils.isNotBlank(poster) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(poster.indexOf(Global.URL_DOMAIN) == 0) {
                posterUrl = poster.substring(Global.URL_DOMAIN.length()) ;
            }else{
                posterUrl=poster;
            }
        }
        return posterUrl;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassInfoName() {
        return classInfoName;
    }

    public void setClassInfoName(String classInfoName) {
        this.classInfoName = classInfoName;
    }

    public String getUserPicUrl() {
        if(StringUtils.isBlank(userPicUrl) && StringUtils.isNotBlank(userPic) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(userPic.indexOf(Global.URL_DOMAIN) == 0) {
                userPicUrl = userPic.substring(Global.URL_DOMAIN.length()) ;
            }else{
                userPicUrl=userPic;
            }
        }
        return userPicUrl;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }


    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getUserCommentCount() {
        return userCommentCount;
    }

    public void setUserCommentCount(String userCommentCount) {
        this.userCommentCount = userCommentCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserPic_xd() {
        if(StringUtils.isBlank(userPic_xd) && StringUtils.isNotBlank(userPic) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(userPic.indexOf(Global.URL_DOMAIN) == 0) {
                userPic_xd = userPic.substring(Global.URL_DOMAIN.length()) ;
            }else{
                userPic_xd=userPic;
            }
        }
        return userPic_xd;
    }

    public void setUserPic_xd(String userPic_xd) {
        this.userPic_xd = userPic_xd;
    }

    public String getPoster_xd() {
        if(StringUtils.isBlank(poster_xd) && StringUtils.isNotBlank(poster) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(poster.indexOf(Global.URL_DOMAIN) == 0) {
                poster_xd = poster.substring(Global.URL_DOMAIN.length()) ;
            }else{
                poster_xd=poster;
            }
        }
        return poster_xd;
    }

    public void setPoster_xd(String poster_xd) {
        this.poster_xd = poster_xd;
    }

    public String getFxjhUrl() {
        return fxjhUrl;
    }

    public void setFxjhUrl(String fxjhUrl) {
        this.fxjhUrl = fxjhUrl;
    }

    public String getFxjhUrl_xd() {
        return fxjhUrl_xd;
    }

    public void setFxjhUrl_xd(String fxjhUrl_xd) {
        this.fxjhUrl_xd = fxjhUrl_xd;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCostComment() {
        return costComment;
    }

    public void setCostComment(String costComment) {
        this.costComment = costComment;
    }

    public String getSignupStartTime() {
        return signupStartTime;
    }

    public void setSignupStartTime(String signupStartTime) {
        this.signupStartTime = signupStartTime;
    }

    public String getSignupEndTime() {
        return signupEndTime;
    }

    public void setSignupEndTime(String signupEndTime) {
        this.signupEndTime = signupEndTime;
    }

    public String getNeedSignIn() {
        return needSignIn;
    }

    public void setNeedSignIn(String needSignIn) {
        this.needSignIn = needSignIn;
    }

    public String getSignInCode() {
        return signInCode;
    }

    public void setSignInCode(String signInCode) {
        this.signInCode = signInCode;
    }

    public String getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getNeedAuth() {
        return needAuth;
    }

    public void setNeedAuth(String needAuth) {
        this.needAuth = needAuth;
    }

    public String getNowStatus() {
        return nowStatus;
    }

    public void setNowStatus(String nowStatus) {
        this.nowStatus = nowStatus;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
}

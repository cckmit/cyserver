package com.cy.core.schoolServ.entity;

import com.cy.common.utils.StringUtils;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.system.Global;

import java.util.Date;
import java.io.Serializable;
import java.util.List;
/**
 * Created by Yang on 2017/7/10.
 */
public class BackschoolBoard implements Serializable{
    private static final long serialVersionUID = 1135103216590099327L;
    private long id;
    private String backschoolId;  			//活动ID
    private String userInfoId;			//留言用户

    //YS 2017/7/13
    private String userName;//前台用户名称
    private String userAvatar;//前台用户头像
    private String userAvatarUrl;//用户头像路径
    private String userSex;//前台用户性别 0 代表男，1代表女

    //YS 2017/7/15
    private String praiseOrNot;//是否点赞 0表示没有点赞 1表示点赞了

    //YS 2017/7/17
    private String isOwner;//是否为自己创建 0代表不是 1代表是

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        if(StringUtils.isBlank(userAvatar) && StringUtils.isNotBlank(userAvatarUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(userAvatarUrl.indexOf("http") < 0) {
                userAvatar = Global.URL_DOMAIN + userAvatarUrl ;
            }else{
                userAvatar=userAvatarUrl;
            }
        }
        return userAvatar;
    }
    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserAvatarUrl() {
        if(StringUtils.isBlank(userAvatarUrl) && StringUtils.isNotBlank(userAvatar) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
            if(userAvatar.indexOf(Global.URL_DOMAIN) == 0) {
                userAvatarUrl = userAvatar.substring(Global.URL_DOMAIN.length()) ;
            }else{
                userAvatarUrl=userAvatar;
            }
        }
        return userAvatarUrl;
    }
    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getUserSex() {
        return userSex;
    }
    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }
    //以上 YS 2017/7/13

    private String comment;				//留言
    private Date createTime;			//创建时间
    private int status;					//状态（0=正常，1=投诉处理-花絮正常，2=投诉处理-花絮违规，3=用户自己删除，4=管理员删除）

    private UserProfile userProfile;	// --> userInfoId = 实际保存user_profile.accountNum
    private int commentNum;				//评论数量
    private int praiseNum;				//点赞数量
    private int complaintNum;			//投诉数量

    private List<BackschoolBoardPic> backschoolBoardPicList;//花絮图片列表

    //YS 2017/7/10
    private List<BackschoolBoardComment> backschoolBoardCommentList;//花絮评论列表

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getBackschoolId() {
        return backschoolId;
    }
    public void setBackschoolId(String backschoolId) {
        this.backschoolId = backschoolId;
    }

    public String getUserInfoId() {
        return userInfoId;
    }
    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
    public UserProfile getUserProfile() {
        return userProfile;
    }
    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }
    public int getCommentNum() {
        return commentNum;
    }
    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }
    public int getPraiseNum() {
        return praiseNum;
    }
    public void setComplaintNum(int complaintNum) {
        this.complaintNum = complaintNum;
    }
    public int getComplaintNum() {
        return complaintNum;
    }

    public List<BackschoolBoardPic> getBackschoolBoardPicList() {
        return backschoolBoardPicList;
    }

    //YS 2017/7/10
    public void setBackschoolBoardCommentList(
            List<BackschoolBoardComment> backschoolBoardCommentList) {
        this.backschoolBoardCommentList = backschoolBoardCommentList;
    }
    public void setBackschoolBoardPicList(List<BackschoolBoardPic> backschoolBoardPicList) {
        this.backschoolBoardPicList = backschoolBoardPicList;
    }

    //YS 2017/7/14
    public List<BackschoolBoardComment> getBackschoolBoardCommentList() {
        return backschoolBoardCommentList;
    }


    public String getPraiseOrNot() {
        return praiseOrNot;
    }

    public void setPraiseOrNot(String praiseOrNot) {
        this.praiseOrNot = praiseOrNot;
    }


    public String getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(String isOwner) {
        this.isOwner = isOwner;
    }
}

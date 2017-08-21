package com.cy.core.event.entity;

import java.io.Serializable;
import java.util.Date;

import com.cy.core.userProfile.entity.UserProfile;

public class EventBoard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1135103216590099327L;
	private long id;
	private String eventId;  			//活动ID
	private String userInfoId;			//留言用户
	private String comment;				//留言
	private Date createTime;			//创建时间
	private int status;					//状态（0=正常，1=投诉处理-花絮正常，2=投诉处理-花絮违规，3=用户自己删除，4=管理员删除）
	
	private UserProfile userProfile;	// --> userInfoId = 实际保存user_profile.accountNum
	private int commentNum;				//评论数量
	private int praiseNum;				//点赞数量
	private int complaintNum;			//投诉数量
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
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

}

package com.cy.core.event.entity;

import java.io.Serializable;
import java.util.Date;

import com.cy.core.userProfile.entity.UserProfile;

public class Complaint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8225456105288212215L;
	private long id;
	private long boardId;  			//花絮ID
	private String userInfoId;  	//投诉人ID
	private String reason;			//投诉内容
	private Date createTime;		//投诉时间
	
	private UserProfile userProfile;	//  --> userInfoId = 实际保存user_profile.accountNum

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public String getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	

}

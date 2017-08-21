package com.cy.core.event.entity;

import java.io.Serializable;
import java.util.Date;

import com.cy.core.userProfile.entity.UserProfile;

public class EventBoardComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7702925099024212379L;
	private long id;
	private long boardId;  			//花絮ID
	private String userInfoId;		//评论用户
	private String comment;			//评论
	private Date createTime;		//创建时间
	
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

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	
	

}

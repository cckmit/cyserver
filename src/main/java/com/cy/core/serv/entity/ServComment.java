package com.cy.core.serv.entity;

import java.io.Serializable;
import java.util.Date;

import com.cy.core.user.entity.User;
import com.cy.core.userProfile.entity.UserProfile;

public class ServComment implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7811768364862891754L;
	private long id;
	private long serviceId;  		//服务ID
	private String content;			//评论
	private int type;				//性质： 0=官方， 9=个人
	private long accountNum;  		//个人评论人ID（对应UserProfile.accountNum字段）
	private long userId;			//官方评论人ID（对应user.userId）
	private Date createTime;		//创建时间
	private int status;				//活动状态（0=正常，1=用户自己删除，2=管理员删除）
	
	private User user;					//  --> userId
	private UserProfile userProfile;	//  --> accountNum 
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getServiceId() {
		return serviceId;
	}
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(long accountNum) {
		this.accountNum = accountNum;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	
	
	

}

package com.cy.core.serv.entity;

import java.io.Serializable;
import java.util.Date;

import com.cy.core.userProfile.entity.UserProfile;

public class ServComplaint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 669790840168508980L;
	private long id;
	private long serviceId;  		//服务ID
	private long accountNum;  		//投诉人ID
	private String reason;			//投诉内容
	private Date createTime;		//投诉时间
	
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

	public void setAccountNum(long accountNum) {
		this.accountNum = accountNum;
	}

	public long getAccountNum() {
		return accountNum;
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

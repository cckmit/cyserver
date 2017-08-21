package com.cy.core.mobevent.entity;

import com.cy.common.utils.StringUtils;
import com.cy.system.Global;

import java.io.Serializable;
import java.util.Date;

public class CyEventSign implements Serializable{


	private static final long serialVersionUID = 1L;
	
	private long id;
	//活动ID
	private String eventId;
	
	//报名用户
	private String userInfoId;
	
	private String userName;//前台用户名称
	private String userAvatar;//前台用户头像
	private String userTel;//前台用户电话号码
	private String userSex;//前台用户性别 0代表男，1代表女
	private String userAvatarUrl;
	
	//报名时间
	private Date signupTime;
	
	//是否已查看通知（0=未查看， 1=已查看）
	private int viewNotification;
	
	//是否签到（0=未签到， 1=已签到）
	private int isSignIn;
	
	//签到时间
	private Date signinTime;

	private long currentRow;//当前行数
	
	private int incremental = 10;//每次拉取数据的增量

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

	public Date getSignupTime() {
		return signupTime;
	}

	public void setSignupTime(Date signupTime) {
		this.signupTime = signupTime;
	}

	

	public Date getSigninTime() {
		return signinTime;
	}

	public void setSigninTime(Date signinTime) {
		this.signinTime = signinTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getViewNotification() {
		return viewNotification;
	}

	public int getIsSignIn() {
		return isSignIn;
	}

	public void setViewNotification(int viewNotification) {
		this.viewNotification = viewNotification;
	}

	public void setIsSignIn(int isSignIn) {
		this.isSignIn = isSignIn;
	}

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyEventSign [id=<");
		builder.append(id);
		builder.append(">, eventId=<");
		builder.append(eventId);
		builder.append(">, userInfoId=<");
		builder.append(userInfoId);
		builder.append(">, userName=<");
		builder.append(userName);
		builder.append(">, userAvatar=<");
		builder.append(userAvatar);
		builder.append(">, userAvatarUrl=<");
		builder.append(userAvatarUrl);
		builder.append(">, signupTime=<");
		builder.append(signupTime);
		builder.append(">, viewNotification=<");
		builder.append(viewNotification);
		builder.append(">, isSignIn=<");
		builder.append(isSignIn);
		builder.append(">, signinTime=<");
		builder.append(signinTime);
		builder.append(">]");
		return builder.toString();
	}

	public long getCurrentRow() {
		return currentRow;
	}

	public int getIncremental() {
		return incremental;
	}

	public void setCurrentRow(long currentRow) {
		this.currentRow = currentRow;
	}

	public void setIncremental(int incremental) {
		this.incremental = incremental;
	}

	public String getUserTel() {
		return userTel;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
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
}

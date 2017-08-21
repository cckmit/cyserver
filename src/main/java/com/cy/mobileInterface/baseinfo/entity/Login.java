package com.cy.mobileInterface.baseinfo.entity;

import java.io.Serializable;

public class Login implements Serializable {
	private static final long serialVersionUID = 1L;
	private String accountNum;
	private String password;
	private String verificationCode;
	private String name;
	private String openId;
	private String accountAppId;

	
	private String meid; //机器码
	private String macid; //网卡MAC
	private String sysid; //操作系统
	private String fromid; //登陆来源0-APP,1-微信,2-网页
	private String token; //token登陆
	
	
	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAccountAppId() {
		return accountAppId;
	}

	public void setAccountAppId(String accountAppId) {
		this.accountAppId = accountAppId;
	}

	public String getMeid() {
		return meid;
	}

	public void setMeid(String meid) {
		this.meid = meid;
	}

	public String getMacid() {
		return macid;
	}

	public void setMacid(String macid) {
		this.macid = macid;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getFromid() {
		return fromid;
	}

	public void setFromid(String fromid) {
		this.fromid = fromid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

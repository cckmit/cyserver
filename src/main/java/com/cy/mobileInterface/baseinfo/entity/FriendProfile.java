package com.cy.mobileInterface.baseinfo.entity;

import java.io.Serializable;

public class FriendProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accountNum;
	private String password;
	private String friendAccount;
	private String baseInfoId;
	private String phoneNum;

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

	public String getFriendAccount() {
		return friendAccount;
	}

	public void setFriendAccount(String friendAccount) {
		this.friendAccount = friendAccount;
	}

	public String getBaseInfoId() {
		return baseInfoId;
	}

	public void setBaseInfoId(String baseInfoId) {
		this.baseInfoId = baseInfoId;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

}

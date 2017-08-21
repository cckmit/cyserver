package com.cy.mobileInterface.password.entity;

import java.io.Serializable;

public class PassWord implements Serializable {

	private static final long serialVersionUID = 1L;

	private String phoneNum;
	private String password;
	private String checkCode;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

}

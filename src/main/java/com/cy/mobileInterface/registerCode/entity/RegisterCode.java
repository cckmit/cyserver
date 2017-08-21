package com.cy.mobileInterface.registerCode.entity;

import java.io.Serializable;

public class RegisterCode implements Serializable {
	private static final long serialVersionUID = 1L;
	private String phoneNum;
	private String secretKey;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}

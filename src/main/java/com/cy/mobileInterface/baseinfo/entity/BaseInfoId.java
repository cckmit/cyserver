package com.cy.mobileInterface.baseinfo.entity;

import java.io.Serializable;

public class BaseInfoId implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String accountNum;
	@Deprecated
	private String schoolNum;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	@Deprecated
	public String getSchoolNum() {
		return schoolNum;
	}

	@Deprecated
	public void setSchoolNum(String schoolNum) {
		this.schoolNum = schoolNum;
	}

}

package com.cy.mobileInterface.group.entity;

import java.io.Serializable;

public class GroupMembers implements Serializable {

	private static final long serialVersionUID = 1L;
	private String accountNum;
	private String password;
	private String accountNums;

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

	public String getAccountNums() {
		return accountNums;
	}

	public void setAccountNums(String accountNums) {
		this.accountNums = accountNums;
	}

}

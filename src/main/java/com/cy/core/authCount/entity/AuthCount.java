package com.cy.core.authCount.entity;

import java.io.Serializable;

public class AuthCount implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accountNum;
	private int authCount;
	private String createTime;

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public int getAuthCount() {
		return authCount;
	}

	public void setAuthCount(int authCount) {
		this.authCount = authCount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}

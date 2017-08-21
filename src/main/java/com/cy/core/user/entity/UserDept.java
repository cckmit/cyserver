package com.cy.core.user.entity;

import java.io.Serializable;

public class UserDept implements Serializable {
	private static final long serialVersionUID = 1L;
	private long userDeptId;
	private long userId;
	private String deptId;

	public long getUserDeptId() {
		return userDeptId;
	}

	public void setUserDeptId(long userDeptId) {
		this.userDeptId = userDeptId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}

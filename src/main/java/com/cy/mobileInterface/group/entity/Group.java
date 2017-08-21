package com.cy.mobileInterface.group.entity;

import java.io.Serializable;

public class Group implements Serializable {
	private static final long serialVersionUID = 1L;
	private String accountNum;
	private String password;
	private String groupId;
	private String type;
	private String createrAccount;
	private String groupName;
	private String description;
	private String subject;
	private String membersAccount;
	private String adminsAccount;

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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreaterAccount() {
		return createrAccount;
	}

	public void setCreaterAccount(String createrAccount) {
		this.createrAccount = createrAccount;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMembersAccount() {
		return membersAccount;
	}

	public void setMembersAccount(String membersAccount) {
		this.membersAccount = membersAccount;
	}

	public String getAdminsAccount() {
		return adminsAccount;
	}

	public void setAdminsAccount(String adminsAccount) {
		this.adminsAccount = adminsAccount;
	}

}

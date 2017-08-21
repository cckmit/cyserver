package com.cy.mobileInterface.datamining.entity;

import java.io.Serializable;

public class Datamining implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accountNum;
	private String password;
	private String Gmid;
	private String Name;
	private String telId;
	private String useTime;

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

	public String getGmid() {
		return Gmid;
	}

	public void setGmid(String gmid) {
		Gmid = gmid;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getTelId() {
		return telId;
	}

	public void setTelId(String telId) {
		this.telId = telId;
	}

	public String getUseTime() {
		return useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}

}

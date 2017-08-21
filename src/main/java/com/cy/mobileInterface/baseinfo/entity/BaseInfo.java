package com.cy.mobileInterface.baseinfo.entity;

import java.io.Serializable;

public class BaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String accountNum;
	private String password;
	private String sex;
	private String profession;
	private String professionId;
	private String address;
	private String cityId;
	private String currUserId ;		// 当前用户编号 (= accountNum)
	private String userId ;			// 查询用户编号
	private String entranceTime ;			// 当前用户入学时间
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

	public String getCurrUserId() {
		return currUserId;
	}

	public void setCurrUserId(String currUserId) {
		this.currUserId = currUserId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) { this.password = password;}

	public String getProfessionId() { return professionId; }

	public void setProfessionId(String professionId) { this.professionId = professionId; }

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityId() {	return cityId; }

	public void setCityId(String cityId) { this.cityId = cityId; }

	public String getEntranceTime() {return entranceTime;}

	public void setEntranceTime(String entranceTime) {this.entranceTime = entranceTime;}
}

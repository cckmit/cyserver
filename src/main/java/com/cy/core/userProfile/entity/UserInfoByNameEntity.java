package com.cy.core.userProfile.entity;

import java.io.Serializable;

public class UserInfoByNameEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * id 数据库索引id picture 用户图像地址 accountNum 用户账号 name 昵称 phoneNum 电话号码 sex 性别 0
	 * 代表男，1代表女 _class 班级 address 通讯地址 sign 签名 intrestType 兴趣类型 channels 选择监听的频道
	 * email 邮箱 authenticated 0没有认证，1已经认证 baseInfoId 用户在基础信息数据库中的id idNumber用户身份证号
	 */
	//private int id;
	//private String picture;
	private String accountNum;
	//private String name;
	//private String phoneNum;
	//private String sex;
	//private String address;
	//private String sign;
	//private String intrestType;
	//private String channels;
	//private String email;
	private String idNumber;
	//private String authenticated;
	private String baseInfoId;
	
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getPicture() {
//		return picture;
//	}
//
//	public void setPicture(String picture) {
//		this.picture = picture;
//	}
//
//	public String getPhoneNum() {
//		return phoneNum;
//	}
//
//	public void setPhoneNum(String phoneNum) {
//		this.phoneNum = phoneNum;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

//	public String getAuthenticated() {
//		return authenticated;
//	}
//
//	public void setAuthenticated(String authenticated) {
//		this.authenticated = authenticated;
//	}

	public String getBaseInfoId() {
		return baseInfoId;
	}

	public void setBaseInfoId(String baseInfoId) {
		this.baseInfoId = baseInfoId;
	}

//	public String getIntrestType() {
//		return intrestType;
//	}
//
//	public void setIntrestType(String intrestType) {
//		this.intrestType = intrestType;
//	}
//
//	public String getChannels() {
//		return channels;
//	}
//
//	public void setChannels(String channels) {
//		this.channels = channels;
//	}
//
//	public String getSex() {
//		return sex;
//	}
//
//	public void setSex(String sex) {
//		this.sex = sex;
//	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public String getSign() {
//		return sign;
//	}
//
//	public void setSign(String sign) {
//		this.sign = sign;
//	}
}

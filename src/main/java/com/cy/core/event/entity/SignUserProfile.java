package com.cy.core.event.entity;

import java.io.Serializable;

public class SignUserProfile implements Serializable
{
	
	private static final long serialVersionUID = -2755054718300313845L;
	
	private String signAccountNum;  //在报名表中的accountNum
	
	private String picture;
    private String accountNum;
    private String password;
    private String name;
    private String phoneNum;
    private String sex;
    private String address;
    private String sign;
    private String intrestType;
    private String channels;
    private String email;
    private String authenticated;
    private String baseInfoId;
    
    private String fullName;  //学习经历

	private String isSignIn;
	private String signInTime;
    
    
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getIntrestType() {
		return intrestType;
	}
	public void setIntrestType(String intrestType) {
		this.intrestType = intrestType;
	}
	public String getChannels() {
		return channels;
	}
	public void setChannels(String channels) {
		this.channels = channels;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAuthenticated() {
		return authenticated;
	}
	public void setAuthenticated(String authenticated) {
		this.authenticated = authenticated;
	}
	public String getBaseInfoId() {
		return baseInfoId;
	}
	public void setBaseInfoId(String baseInfoId) {
		this.baseInfoId = baseInfoId;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setSignAccountNum(String signAccountNum) {
		this.signAccountNum = signAccountNum;
	}
	public String getSignAccountNum() {
		return signAccountNum;
	}

	public String getIsSignIn() {
		return isSignIn;
	}

	public void setIsSignIn(String isSignIn) {
		this.isSignIn = isSignIn;
	}

	public String getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(String signInTime) {
		this.signInTime = signInTime;
	}
}
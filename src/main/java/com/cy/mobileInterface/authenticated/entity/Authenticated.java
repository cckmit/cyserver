package com.cy.mobileInterface.authenticated.entity;

import java.io.Serializable;
import java.util.List;

public class Authenticated implements Serializable {

	private static final long serialVersionUID = 1L;
	private String phoneNum;
	private String password;
	private String name;
	private List<String> classmates;
	private List<String> baseInfoId;
	private String studentnumber;	//学号
	private String card;			//身份证号
	private String invitecode;		//邀请码
	private String baseInfoIdx;		//基础Id
	private String accountNum;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
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

	public List<String> getClassmates() {
		return classmates;
	}

	public void setClassmates(List<String> classmates) {
		this.classmates = classmates;
	}

	public List<String> getBaseInfoId() {
		return baseInfoId;
	}

	public void setBaseInfoId(List<String> baseInfoId) {
		this.baseInfoId = baseInfoId;
	}

	public String getStudentnumber() {
		return studentnumber;
	}

	public void setStudentnumber(String studentnumber) {
		this.studentnumber = studentnumber;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getInvitecode() {
		return invitecode;
	}

	public void setInvitecode(String invitecode) {
		this.invitecode = invitecode;
	}

	public String getBaseInfoIdx() {
		return baseInfoIdx;
	}

	public void setBaseInfoIdx(String baseInfoIdx) {
		this.baseInfoIdx = baseInfoIdx;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
}

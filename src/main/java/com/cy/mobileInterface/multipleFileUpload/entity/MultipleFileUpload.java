package com.cy.mobileInterface.multipleFileUpload.entity;

import java.io.Serializable;

/**
 * 
 * <p>Title: MultipleFileUpload</p>
 * <p>Description: 文件上传实体类</p>
 * 
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-7-4 上午9:22:45
 */
public class MultipleFileUpload implements Serializable {
	private static final long serialVersionUID = 1L;
	private String accountNum;	// 校友用户账号
	private String password;	// 校友用户密码
	private String type;		// 上传文件类型

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

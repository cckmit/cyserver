package com.cy.core.email.entity;

import java.io.Serializable;

public class EmailRecipient  implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 电子邮件模板变量实体
	 * 
	 * 
	 */
	
	private long user_id;
	private String user_name;
	private String email;
	private String birthday;
	private String full_name;
	
	public long getUser_id() {
		return user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public String getEmail() {
		return email;
	}
	public String getBirthday() {
		return birthday;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	
	
	
	
}

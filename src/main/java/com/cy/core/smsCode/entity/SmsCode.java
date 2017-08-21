package com.cy.core.smsCode.entity;

import java.io.Serializable;
import java.util.Date;

public class SmsCode implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long codeId;
	private String telId;
	private Date createTime;
	private String smsCode;

	public Long getCodeId() {
		return codeId;
	}

	public void setCodeId(Long codeId) {
		this.codeId = codeId;
	}

	public String getTelId() {
		return telId;
	}

	public void setTelId(String telId) {
		this.telId = telId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

}

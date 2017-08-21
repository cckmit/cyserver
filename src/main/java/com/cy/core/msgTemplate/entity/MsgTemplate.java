package com.cy.core.msgTemplate.entity;

import java.io.Serializable;

public class MsgTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long msgTemplateId;			// 模板编号
	private String msgTemplateTitle;	// 模板标题
	private String msgTemplateContent;	// 模板内容
	private int msgTemplateParamNumber;	// 模板参数个数
	private String msgTemplateStatus;   //模版状态
	
	private String msgTemplateType ; 	// 模板类型（0：普通模板；1：系统模板） 

	public long getMsgTemplateId() {
		return msgTemplateId;
	}

	public void setMsgTemplateId(long msgTemplateId) {
		this.msgTemplateId = msgTemplateId;
	}

	public String getMsgTemplateContent() {
		return msgTemplateContent;
	}

	public void setMsgTemplateContent(String msgTemplateContent) {
		this.msgTemplateContent = msgTemplateContent;
	}

	public int getMsgTemplateParamNumber() {
		return msgTemplateParamNumber;
	}

	public void setMsgTemplateParamNumber(int msgTemplateParamNumber) {
		this.msgTemplateParamNumber = msgTemplateParamNumber;
	}

	public String getMsgTemplateTitle() {
		return msgTemplateTitle;
	}

	public void setMsgTemplateTitle(String msgTemplateTitle) {
		this.msgTemplateTitle = msgTemplateTitle;
	}

	public String getMsgTemplateType() {
		return msgTemplateType;
	}

	public void setMsgTemplateType(String msgTemplateType) {
		this.msgTemplateType = msgTemplateType;
	}

	public String getMsgTemplateStatus() {
		return msgTemplateStatus;
	}

	public void setMsgTemplateStatus(String msgTemplateStatus) {
		this.msgTemplateStatus = msgTemplateStatus;
	}
}

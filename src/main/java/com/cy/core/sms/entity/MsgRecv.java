package com.cy.core.sms.entity;

import java.io.Serializable;
import java.util.Date;

public class MsgRecv implements Serializable{
	private static final long serialVersionUID = 1L;
	private int recvId;
	private String telphone;
	private String content;
	private Date recvtime;

	public int getRecvId() {
		return recvId;
	}

	public void setRecvId(int recvId) {
		this.recvId = recvId;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRecvtime() {
		return recvtime;
	}

	public void setRecvtime(Date recvtime) {
		this.recvtime = recvtime;
	}

}

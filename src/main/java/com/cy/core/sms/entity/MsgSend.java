package com.cy.core.sms.entity;

import java.io.Serializable;
import java.util.Date;

public class MsgSend implements Serializable {
	private static final long serialVersionUID = 1L;
	private int msgId;
	private String deptId;
	private long staffId;
	private String telphone;
	private String content;
	private int statues;
	private Date sendtime;
	private int msgType;
	private int countNumber;
	private long messagegroup;
	private Date receivetime;
	private String errorCode;

	private String userAccount;

	private long smsID;

	private String sentStatus ;		// 发送状态（0未发送；1，已发送；）
	private String msgTypeQuery ;	// 查询消息类型（0普通短信；1，系统短信；）
	private String sentStartTime ;	// 发送开始时间
	private String sentEndTime ;	// 发送结束时间

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
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

	public int getStatues() {
		return statues;
	}

	public void setStatues(int statues) {
		this.statues = statues;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public int getCountNumber() {
		return countNumber;
	}

	public void setCountNumber(int countNumber) {
		this.countNumber = countNumber;
	}

	public long getMessagegroup() {
		return messagegroup;
	}

	public void setMessagegroup(long messagegroup) {
		this.messagegroup = messagegroup;
	}

	public Date getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public long getSmsID() {
		return smsID;
	}

	public void setSmsID(long smsID) {
		this.smsID = smsID;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getSentStatus() {
		return sentStatus;
	}

	public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
	}

	public String getSentStartTime() {
		return sentStartTime;
	}

	public void setSentStartTime(String sentStartTime) {
		this.sentStartTime = sentStartTime;
	}

	public String getSentEndTime() {
		return sentEndTime;
	}

	public void setSentEndTime(String sentEndTime) {
		this.sentEndTime = sentEndTime;
	}

	public String getMsgTypeQuery() {
		return msgTypeQuery;
	}

	public void setMsgTypeQuery(String msgTypeQuery) {
		this.msgTypeQuery = msgTypeQuery;
	}

}

package com.cy.core.email.entity;

import java.io.Serializable;
import java.util.Date;

public class Email implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 电子邮件实体
	 * 
	 * 
	 */

	private long emailId; // 序列ID

	private String fromAddress; // 发件人
	private String toAddress; // 收件人，多个收件人用英文半角逗号（,）隔开
	private String toAddressStr;
	private int emailTemplateId; // 模板ID
	private String emailTemplateIdStr;
	private String emailSubject; // 发送主题
	private String emailText; // 发送内容

	private String immediate; // 0，立即发送；1，定时发送；
	private int sent; // 0未发送；1，已发送；

	private Date sendDateTime; // 发送时间
	private Date createDateTime;// 创建时间
	private String[] fj;
	private String fromPassword;
	private String bccAddress;
	private String ccAddress;
	private long staffId;
	private String userAccount;

	private String sentStatus ;		// 发送状态（0未发送；1，已发送；）
	private String sentStartTime ;	// 发送开始时间
	private String sentEndTime ;	// 发送结束时间
	public long getEmailId() {
		return emailId;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public String getEmailText() {
		return emailText;
	}

	public String getImmediate() {
		return immediate;
	}

	public int getSent() {
		return sent;
	}

	public Date getSendDateTime() {
		return sendDateTime;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setEmailId(long emailId) {
		this.emailId = emailId;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}

	public void setImmediate(String immediate) {
		this.immediate = immediate;
	}

	public void setSent(int sent) {
		this.sent = sent;
	}

	public void setSendDateTime(Date sendDateTime) {
		this.sendDateTime = sendDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public int getEmailTemplateId() {
		return emailTemplateId;
	}

	public void setEmailTemplateId(int emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Email [emailId=<");
		builder.append(emailId);
		builder.append(">, fromAddress=<");
		builder.append(fromAddress);
		builder.append(">, toAddress=<");
		builder.append(toAddress);
		builder.append(">, emailTemplateId=<");
		builder.append(emailTemplateId);
		builder.append(">, emailSubject=<");
		builder.append(emailSubject);
		builder.append(">, emailText=<");
		builder.append(emailText);
		builder.append(">, immediate=<");
		builder.append(immediate);
		builder.append(">, sent=<");
		builder.append(sent);
		builder.append(">, sendDateTime=<");
		builder.append(sendDateTime);
		builder.append(">, createDateTime=<");
		builder.append(createDateTime);
		builder.append("]");
		return builder.toString();
	}

	public String getToAddressStr() {
		return toAddressStr;
	}

	public void setToAddressStr(String toAddressStr) {
		this.toAddressStr = toAddressStr;
	}

	public String getEmailTemplateIdStr() {
		return emailTemplateIdStr;
	}

	public void setEmailTemplateIdStr(String emailTemplateIdStr) {
		this.emailTemplateIdStr = emailTemplateIdStr;
	}

	public String[] getFj() {
		return fj;
	}

	public void setFj(String[] fj) {
		this.fj = fj;
	}

	public String getFromPassword() {
		return fromPassword;
	}

	public void setFromPassword(String fromPassword) {
		this.fromPassword = fromPassword;
	}

	public String getBccAddress() {
		return bccAddress;
	}

	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}

	public String getCcAddress() {
		return ccAddress;
	}

	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

	public long getStaffId() {
		return staffId;
	}

	public void setStaffId(long staffId) {
		this.staffId = staffId;
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

}

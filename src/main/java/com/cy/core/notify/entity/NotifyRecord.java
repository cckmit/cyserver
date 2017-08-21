package com.cy.core.notify.entity;

import com.cy.base.entity.DataEntity;

/**
 * 
 * <p>Title: NotifyRecord</p>
 * <p>Description: 通知推送记录实体类</p>
 * 
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-10-27
 */
public class NotifyRecord extends DataEntity<NotifyRecord>
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//--------------数据库字段---------------//
	private String channel     ; // 推送方式（10：通过标签tag 推送；20：通过别名alias 推送；30：通过手机号推送）
	private String marking     ; // 接收推送标示
	private String notifyId   	; // 通知编号
	private String userId     ; // 接收用户编号
	private String status      ; // 发送状态（10：未发送；20：已发送）
	private String readFlag      ; // 阅读标示（0：未读；1：已读）
	private String readDate      ; // 阅读时间

	//--------------查询关联字段---------------//
	private Notify notify ;	// 推送内容实体类


	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getMarking() {
		return marking;
	}

	public void setMarking(String marking) {
		this.marking = marking;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getReadDate() {
		return readDate;
	}

	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}

	public Notify getNotify() {
		return notify;
	}

	public void setNotify(Notify notify) {
		this.notify = notify;
	}
}
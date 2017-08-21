package com.cy.core.chatContacts.entity;

import com.cy.base.entity.DataEntity;

/**
 * 
 * <p>Title: ChatContacts</p>
 * <p>Description: 通讯录联系人实体类（好友实体类）</p>
 * 
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-7-13 下午4:44:10
 */
public class ChatContacts extends DataEntity<ChatContacts>
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//--------------数据库字段---------------//
	private String userId       ;	// 用户ID
	private String friendId     ;	// 好友ID
	private String alias        ;	// 备注名称
	private String letter       ;	// 字母组
	private String status       ;	// 好友状态（0：待验证，1：好友，2：黑名单）
	private String type         ;	// 好友类型（0：普通好友；1：系统好友）
	private String applicantFlag;	// 是否是申请者（0：不是申请者；1：是申请者）                       
	private String refereeId    ;	// 推荐人ID
	private String starFlag     ;	// 是否星标
	private String background   ;	// 聊天背景
	private String noticeFlag   ;	// 是否新消息通知

	//--------------查询关联字段---------------//
	private String userName		;	// 用户名称
	private String contactId     ;	// 好友ID ( = friendId )
	private String friendName 	;	// 好友名称
	private String friendpicture	;	// 好友用户头像路径（相对路径）
	private String friendPictureUrl ;	// 好友用户头像路径（网络路径）
	private String friendSex ;			// 好友性别

	//--------------查询条件附加字段---------------//
	private String isNotStatus 	;	// 好友状态不等于

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApplicantFlag() {
		return applicantFlag;
	}

	public void setApplicantFlag(String applicantFlag) {
		this.applicantFlag = applicantFlag;
	}

	public String getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(String refereeId) {
		this.refereeId = refereeId;
	}

	public String getStarFlag() {
		return starFlag;
	}

	public void setStarFlag(String starFlag) {
		this.starFlag = starFlag;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getNoticeFlag() {
		return noticeFlag;
	}

	public void setNoticeFlag(String noticeFlag) {
		this.noticeFlag = noticeFlag;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getFriendpicture() {
		return friendpicture;
	}

	public void setFriendpicture(String friendpicture) {
		this.friendpicture = friendpicture;
	}

	public String getFriendPictureUrl() {
		return friendPictureUrl;
	}

	public void setFriendPictureUrl(String friendPictureUrl) {
		this.friendPictureUrl = friendPictureUrl;
	}

	public String getFriendSex() {
		return friendSex;
	}

	public void setFriendSex(String friendSex) {
		this.friendSex = friendSex;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getIsNotStatus() {
		return isNotStatus;
	}

	public void setIsNotStatus(String isNotStatus) {
		this.isNotStatus = isNotStatus;
	}
}
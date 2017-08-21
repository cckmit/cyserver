package com.cy.core.chatGroupUser.entity;

import com.cy.base.entity.DataEntity;

/**
 * 
 * <p>Title: ChatGroupUser</p>
 * <p>Description: 通讯录群组用户实体类</p>
 * 
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-7-15 下午11:07
 */
public class ChatGroupUser extends DataEntity<ChatGroupUser>
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//--------------数据库字段---------------//
	private String groupId       	;	// 群组ID
	private String userId       	;	// 成员ID
	private String nickName   		;	// 成员群内昵称
	private String nickNameFlag     ;	// 是否显示昵称
	private String userPhoto     	;	// 成员群内头像
	private String background       ;	// 聊天背景
	private String noticeFlag		;	// 是否新消息通知

	//--------------查询关联字段---------------//
	private String userName		;	// 用户名称
	private String friendName 	;	// 好友名称
	private String friendpicture	;	// 好友用户头像路径（相对路径）
	private String friendPictureUrl ;	// 好友用户头像路径（网络路径）
	private String friendSex ;			// 好友性别
	private String hasAccountNum ;		// 查询是否验证过了(0:未验证;1:已验证)
	// add by jiangling
	private String memberId; // 成员ID(接口的入参conten.memberId)

	public String getGroupId() {
		return groupId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickNameFlag() {
		return nickNameFlag;
	}

	public void setNickNameFlag(String nickNameFlag) {
		this.nickNameFlag = nickNameFlag;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
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

	public String getHasAccountNum() {
		return hasAccountNum;
	}

	public void setHasAccountNum(String hasAccountNum) {
		this.hasAccountNum = hasAccountNum;
	}
}
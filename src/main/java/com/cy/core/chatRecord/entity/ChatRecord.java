package com.cy.core.chatRecord.entity;

import com.cy.base.entity.DataEntity;

/**
 * 
 * <p>Title: ChatGroupUser</p>
 * <p>Description: 聊天记录</p>
 * 
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-7-15 下午13:15
 */
public class ChatRecord extends DataEntity<ChatRecord>
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//--------------数据库字段---------------//
	private String chatType			;	// 聊天类型（单聊、群聊）
	private String fromId     		;	// 发送人ID
	private String toId        		;	// 接收人ID
	private String type         	;	// 消息类型（文本、图片、位置、语音）
	private String msg       		;	// 消息内容
	private String fileName       	;	// 文件名称
	private String fileUrl			;	// 文件URL
	private String fileLength    	;	// 文件长度
	private String addrName     	;	// 位置名称
	private String lng   			;	// 位置经度
	private String lat   			;	// 位置纬度
	private String sendTime   		;	// 消息发送时间
	private String ext   			;	// 扩展属性

	//--------------查询关联字段---------------//
	private String userName		;	// 用户名称
	private String friendName 	;	// 好友名称
	private String friendpicture	;	// 好友用户头像路径（相对路径）
	private String friendPictureUrl ;	// 好友用户头像路径（网络路径）
	private String friendSex ;			// 好友性别

	public String getChatType() {
		return chatType;
	}

	public void setChatType(String chatType) {
		this.chatType = chatType;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileLength() {
		return fileLength;
	}

	public void setFileLength(String fileLength) {
		this.fileLength = fileLength;
	}

	public String getAddrName() {
		return addrName;
	}

	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getUserName() {
		return userName;
	}

	public ChatRecord setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getFriendName() {
		return friendName;
	}

	public ChatRecord setFriendName(String friendName) {
		this.friendName = friendName;
		return this;
	}

	public String getFriendpicture() {
		return friendpicture;
	}

	public ChatRecord setFriendpicture(String friendpicture) {
		this.friendpicture = friendpicture;
		return this;
	}

	public String getFriendPictureUrl() {
		return friendPictureUrl;
	}

	public ChatRecord setFriendPictureUrl(String friendPictureUrl) {
		this.friendPictureUrl = friendPictureUrl;
		return this;
	}

	public String getFriendSex() {
		return friendSex;
	}

	public ChatRecord setFriendSex(String friendSex) {
		this.friendSex = friendSex;
		return this;
	}
}
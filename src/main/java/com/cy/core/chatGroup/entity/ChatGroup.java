package com.cy.core.chatGroup.entity;

import com.cy.base.entity.DataEntity;

/**
 * 
 * <p>Title: ChatGroup</p>
 * <p>Description: 通讯录群组实体类</p>
 * 
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-7-15
 */
public class ChatGroup extends DataEntity<ChatGroup>
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//--------------数据库字段---------------//
	private String userId       	;	// 拥有人ID
	private String easemobGroupId   ;	// 环信群ID
	private String photo        	;	// 群头像
	private String name       		;	// 群名称
	private String introduction     ;	// 群简介
	private String type       		;	// 群类型(0:普通群; 1:系统群)
	private String total			;	// 成员数
	private String maxusers       	;	// 最大用户数




	//--------------查询关联字段---------------//
	private String groupId		;	// 群组编号(=id)
	private String userName		;	// 用户名称

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	private String memberUserId 	;	// 群组成员编号

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEasemobGroupId() {
		return easemobGroupId;
	}

	public void setEasemobGroupId(String easemobGroupId) {
		this.easemobGroupId = easemobGroupId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaxusers() {
		return maxusers;
	}

	public void setMaxusers(String maxusers) {
		this.maxusers = maxusers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMemberUserId() {
		return memberUserId;
	}

	public void setMemberUserId(String memberUserId) {
		this.memberUserId = memberUserId;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
}
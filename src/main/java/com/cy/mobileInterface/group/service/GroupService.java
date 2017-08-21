package com.cy.mobileInterface.group.service;

import com.cy.base.entity.Message;

public interface GroupService {
	void getGroupMembersInfo(Message message, String content);
	
	void getGroupInfo(Message message, String content);
	
	void updateGroupInfo(Message message, String content);
	
	/**
	 * 根据群组ID获取所有成员信息
	 * 
	 * @param message
	 * @param content
	 */
	void selectUserByGroupId(Message message,String content);
}

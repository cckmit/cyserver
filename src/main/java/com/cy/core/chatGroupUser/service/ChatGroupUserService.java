package com.cy.core.chatGroupUser.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.chatGroupUser.entity.ChatGroupUser;

public interface ChatGroupUserService {
	
	DataGrid<ChatGroupUser> dataGrid(ChatGroupUser chatGroupUser);

	ChatGroupUser getById(String id);

    void delete(String id);

	/**
	 * 根据ID 集合删除 联系人信息
	 */
	public void deleteByIdList(String ids) ;


	/**
	 * add by jiangling
	 * 删除群组成员
	 * @param message
	 * @param content
     */
	public void removeChatGroupUser(Message message, String content);


}

package com.cy.core.chatContacts.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.chatContacts.entity.ChatContacts;

import java.util.List;

public interface ChatContactsService {
	
	DataGrid<ChatContacts> dataGrid(ChatContacts chatContacts);

	List<ChatContacts> findList(ChatContacts chatContacts);

	ChatContacts getById(String id);

    void delete(String id,String updateBy);

	/**
	 * 根据ID 集合删除 联系人信息
	 */
	public void deleteByIdList(String ids) ;

	/***********************************************************************
	 *
	 * 【联系人】相关API（以下区域）
	 *
	 * 注意事项：
	 * 1、方法名-格式要求
	 * 创建方法：save[Name]
	 * 撤销方法：remove[Name]
	 * 查询分页列表方法：find[Name]ListPage
	 * 查询列表方法：find[Name]List
	 * 查询详细方法：find[Name]
	 *
	 ***********************************************************************/

	/**
	 * 查询联系人列表
	 * @param message
	 * @param content
	 */
	void findContactsList(Message message, String content) ;

	/**
	 * 添加联系人
	 * @param message
	 * @param content
	 */
	void saveContacts(Message message, String content) ;

	/**
	 * 解除好友关系
	 * @return
	 */
	void removeContacts(Message message, String content) ;
	/**
	 * 搜索好友(满足条件)
	 * @param message
	 * @param content
	 */
	void searchUserProfile(Message message, String content) ;

	/**
	 * 查询好友详情/用户详情
	 * @param message
	 * @param content
	 */
	void findUserProfile(Message message, String content) ;

	/**
	 * 查询我的好友列表
	 * @param message
	 * @param content
     */
	void findFriendsList(Message message, String content);

	/**
	 * 是否好友新的
	 * @param userId
	 * @param contactId
	 * @return
	 */
	String isFriendNew(String userId ,String contactId);

	/**
	 * 查询我的推荐好友
	 * @param message
	 * @param content
	 */
	void getCommendFriendList(Message message, String content);
}

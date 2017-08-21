package com.cy.core.chatGroup.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.chatGroupUser.entity.ChatGroupUser;
import com.cy.util.DateUtils;
import com.cy.util.PairUtil;
import com.cy.util.easemob.comm.utils.EasemobUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;


public interface ChatGroupService {
	
	DataGrid<ChatGroup> dataGrid(ChatGroup chatGroup);

	ChatGroup getById(String id);

    void delete(String id);

	/**
	 * 比较学习经历和群组列表
	 * @param userId : (为userProfile 表中的accountNum)
	 */
	public void checkClassesAndGroup(String userId) ;
	/**
	 * 根据ID 集合删除 联系人信息
	 */
	public void deleteByIdList(String ids) ;

	/**
	 * 检测群组下是否有成员,如没有则删除群组
	 * @param groupId
	 */
	public void checkAndDeleteEmptyGroup(String groupId) ;
	/**
	 * 创建群组
	 * @param chatGroup
	 */
	public void insert(ChatGroup chatGroup) ;

	/**
	 * 添加成员到群组中
	 * @param groupId
	 * @param members
	 */
	public PairUtil<Boolean,String> addMemberToGroup(String groupId , String members) ;

	/**
	 * 删除成员从群组中(单个)
	 * @param groupId
	 * @param memberId
	 */
	public PairUtil<Boolean,String> removeMemberFromGroup(String groupId , String memberId) ;

	/**
	 * 删除成员从群组中(多个)
	 * @param groupId
	 * @param members
	 */
	public PairUtil<Boolean,String> removeMultiMemberFromGroup(String groupId ,String members) ;

	/**
	 * 删除群组
	 * add by jiangling
	 * @param message
	 * @param content
	 */
	public void removeChatGroup(Message message, String content);


	/**
	 * 初始化创建班级群组
	 * @param classId 班级编号
	 */
	public void initInsertClassGroup(String classId) ;
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
	 * 查询用户参与的群组列表
	 * @param message
	 * @param content
	 */
	public void findChatGroupList(Message message, String content) ;


	/**
	 * 查询群组详情
	 * @param message
	 * @param content
	 */
	public void findChatGroup(Message message, String content) ;

	/**
	 * 创建群组
	 * @param message
	 * @param content
	 */
	public void saveChatGroup(Message message, String content) ;

	/**
	 * 添加成员
	 * @param message
	 * @param content
	 */
	public void addMemberToGroup(Message message, String content) ;

	/**
	 * 删除成员
	 * @param message
	 * @param content
	 */
	public void removeMemberFromGroup(Message message, String content) ;

	/**
	 * 添加多个成员到群组
	 * @auther jiangling
	 * @date 2016-08-06
	 * @param message
	 * @param content
     */
	public void addMemberListToGroup(Message message, String content);


	/**
	 * 修改群组信息【只可管理员或群主修改】
	 */
	public void updateChatGroup(Message message, String content) ;

	/**
	 * 成员退群
	 */
	public void quitChatGroup(Message message, String content) ;

	/**
	 * 加入群组
	 */
	public void requestInsertChatGroup(Message message, String content) ;

	/**
	 * 修复图片
	 * @param groupUserList
	 * @return
	 */
	List<Map<String, String>> fixPic(List<Map<String,String>> groupUserList);
}

package com.cy.core.notify.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.notify.entity.Notify;
import com.cy.core.notify.entity.NotifyRecord;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.util.PairUtil;


public interface NotifyService {
	
	DataGrid<Notify> dataGrid(Notify notify);
	void update(Notify notify);
	Notify getById(String id);

    void delete(String id);
	DataGrid<UserProfile> dataGridUser(NotifyRecord notifyRecord);
	/**
	 * 根据ID 集合删除推送内容
	 */
	public void deleteByIdList(String ids) ;

	/**
	 * 创建推送通知
	 * @param notify
	 */
	public void insert(Notify notify) ;


	/***********************************************************************
	 *
	 * 【推送内容】相关API（以下区域）
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
	 * 推送接口
	 */
	void findNotify(Message message , String content);
	void findMynotify(Message message , String content);
	void setRead(Message message , String content);
	void noReadCount(Message message,String content);
	void setAllReaded(Message message, String content);
	void deleteNotify(Message message, String content);

}

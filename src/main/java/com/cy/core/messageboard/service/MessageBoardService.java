package com.cy.core.messageboard.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.messageboard.entity.MessageBoard;

public interface MessageBoardService {
	
	/**
	 * 赞
	 * 
	 * @param messageBoard 
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean praise(MessageBoard messageBoard);
	
	/**
	 * 获得赞数
	 * 
	 * @param messageBoard 
	 * @return long
	 * 
	 */
	long praiseCount(MessageBoard messageBoard);
	
	
	/**
	 * 收藏
	 * 
	 * @param messageBoard 
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean collect(MessageBoard messageBoard);
	
	
	/**
	 * 获得收藏数
	 * 
	 * @param messageBoard 
	 * @return long
	 * 
	 */
	long collectCount(MessageBoard messageBoard);
	
	/**
	 * 获取热门信息集合
	 * 
	 * @param messageBoard 
	 * @return List<MessageBoard>
	 * 
	 */
	List<MessageBoard> getHotMessageList(MessageBoard messageBoard);
	
	
	/**
	 * 获取收藏信息集合
	 * 
	 * @param messageBoard 
	 * @return List<MessageBoard>
	 * 
	 */
	List<MessageBoard> getCollectMessageList(MessageBoard messageBoard);
	
	/**
	 * 拉取联系XX信息集合
	 * 
	 * @param messageBoard 
	 * @return List<MessageBoard>
	 * 
	 */
	List<MessageBoard> pullContactMessageList(MessageBoard messageBoard);
	
	
	/**
	 * 拉取信息集合
	 * 
	 * @param messageBoard 
	 * @return List<MessageBoard>
	 * 
	 */
	List<MessageBoard> pullMessageList(MessageBoard messageBoard);
	
	/**
	 * 拉取回复信息集合
	 * 
	 * @param messageBoard 
	 * @return List<MessageBoard>
	 * 
	 */
	List<MessageBoard> pullReplyMessageList(MessageBoard messageBoard);
	
	
	/**
	 * 获取联系XX信息集合
	 * 
	 * @param messageBoard
	 * @return List<MessageBoard>
	 * 
	 */
	List<MessageBoard> getContactList(MessageBoard messageBoard);
	
	/**
	 * 获取信息集合
	 * 
	 * @param messageBoard 
	 * @return List<MessageBoard>
	 * 
	 */
	List<MessageBoard> getMessageList(MessageBoard messageBoard);
	
	/**
	 * 获取信息总数
	 * 
	 * @param messageBoard 
	 * @return long
	 * 
	 */
	long getMessageCount(MessageBoard messageBoard);
	
	/**
	 * 获取信息详情
	 * 
	 * @param messageBoard
	 * @return MessageBoard
	 * 
	 */
	MessageBoard getMessageDetail(MessageBoard messageBoard);
	
	
	/**
	 * 存储信息
	 * 
	 * @param messageBoard 
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean saveMessage(MessageBoard messageBoard);
	
	
	/**
	 * 更新信息
	 * 
	 * @param messageBoard 
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean updateMessage(MessageBoard messageBoard);
	
	/**
	 * 所有信息列表(带分页)
	 * dengqiao
	 * @param map
	 * @return
	 */
	DataGrid<MessageBoard> dataGrid(Map<String, Object> map);
	
	/**
	 * 删除
	 * dengqiao
	 * @param ids
	 */
	void delete(String ids);
	
	MessageBoard selectById(long id);


	/**
	 * 反馈信息接口
	 */
	void saveFeedBack(Message message, String content);
}

package com.cy.core.messageboard.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.messageboard.entity.MessageBoard;

public interface MessageBoardMapper {

	/**
	 * 赞
	 * 
	 * @param messageBoard
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean praise(MessageBoard messageBoard);

	/**
	 * 取消赞
	 * 
	 * @param messageBoard
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean deletePraise(MessageBoard messageBoard);

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
	 * 取消收藏
	 * 
	 * @param messageBoard
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean deleteCollect(MessageBoard messageBoard);

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
	 * 获取联系XX信息集合
	 * 
	 * @param messageBoard
	 * @return List<MessageBoard>
	 * 
	 */
	List<MessageBoard> getContactList(MessageBoard messageBoard);

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
	 * 获取消息总条数 dengqiao
	 * 
	 * @param map
	 * @return
	 */
	long countMessage(Map<String, Object> map);

	/**
	 * 获取消息列表 dengqiao
	 * 
	 * @param map
	 * @return
	 */
	List<MessageBoard> selectMessageBoardList(Map<String, Object> map);

	/**
	 * 逻辑删除 dengqiao
	 * 
	 * @param list
	 */
	void updateDeleteStatus(List<Long> list);

	MessageBoard selectById(long id);
}

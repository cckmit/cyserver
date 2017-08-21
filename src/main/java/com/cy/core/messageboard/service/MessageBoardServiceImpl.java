package com.cy.core.messageboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.messageboard.dao.MessageBoardMapper;
import com.cy.core.messageboard.entity.MessageBoard;
import com.cy.system.WebUtil;

@Service("messageBoardService")
public class MessageBoardServiceImpl implements MessageBoardService {

	@Autowired
	private MessageBoardMapper messageBoardMapper;

	@Autowired
	private UserProfileMapper userProfileMapper;

	/**
	 * 赞
	 * 
	 * @param messageBoard
	 * @return true，成功；false，失败；
	 * 
	 */
	public boolean praise(MessageBoard messageBoard) {

		if (messageBoard.getPraise() == 0) {
			return messageBoardMapper.deletePraise(messageBoard);
		} else if (messageBoard.getPraise() == 1) {
			return messageBoardMapper.praise(messageBoard);
		}

		return false;
	}

	/**
	 * 获得赞的数量
	 * 
	 * @param messageBoard
	 * @return long
	 * 
	 */
	public long praiseCount(MessageBoard messageBoard) {

		return messageBoardMapper.praiseCount(messageBoard);
	}

	/**
	 * 收藏
	 * 
	 * @param messageBoard
	 * @return true，成功；false，失败；
	 * 
	 */
	public boolean collect(MessageBoard messageBoard) {

		if (messageBoard.getCollect() == 0) {
			return messageBoardMapper.deleteCollect(messageBoard);
		} else if (messageBoard.getCollect() == 1) {
			return messageBoardMapper.collect(messageBoard);
		}

		return false;

	}

	/**
	 * 获得收藏的数量
	 * 
	 * @param messageBoard
	 * @return long
	 * 
	 */
	public long collectCount(MessageBoard messageBoard) {

		return messageBoardMapper.collectCount(messageBoard);
	}

	/**
	 * 获取热门信息集合
	 * 
	 * @param messageBoard
	 * @return List<MessageBoard>
	 * 
	 */
	public List<MessageBoard> getHotMessageList(MessageBoard messageBoard) {

		return messageBoardMapper.getHotMessageList(messageBoard);
	}

	/**
	 * 获取收藏信息集合
	 * 
	 * @param messageBoard
	 * @return List<MessageBoard>
	 * 
	 */
	public List<MessageBoard> getCollectMessageList(MessageBoard messageBoard) {
		ArrayList<MessageBoard> messageBoardList = (ArrayList<MessageBoard>) messageBoardMapper
		        .getCollectMessageList(messageBoard);

		ArrayList<MessageBoard> reassembleMessageBoardList = new ArrayList<MessageBoard>();

		for (MessageBoard msgBoard : messageBoardList) {

			long messageId = msgBoard.getMessageId();

			long messageBrowseQuantity = msgBoard.getMessageBrowseQuantity();
			long messagePraiseCount = this.praiseCount(new MessageBoard(messageId));
			long messageReplyCount = this.getMessageCount(new MessageBoard(0, messageId, 0, 0));
			long messageCollectCount = this.collectCount(new MessageBoard(messageId));

			String pastTime = WebUtil.pastTime(msgBoard.getMessageTime());

			msgBoard.setMessageBrowseQuantity(messageBrowseQuantity);
			msgBoard.setMessagePraiseCount(messagePraiseCount);
			msgBoard.setMessageReplyCount(messageReplyCount);
			msgBoard.setMessageCollectCount(messageCollectCount);

			msgBoard.setPastTime(pastTime);

			reassembleMessageBoardList.add(msgBoard);

		}

		return reassembleMessageBoardList;
	}
	
	/**
	 * 拉取联系XX信息集合
	 * 
	 * @param messageBoard 
	 * @return List<MessageBoard>
	 * 
	 */
	public List<MessageBoard> pullContactMessageList(MessageBoard messageBoard) {


		ArrayList<MessageBoard> messageBoardList = (ArrayList<MessageBoard>) messageBoardMapper
		        .pullMessageList(messageBoard);

		ArrayList<MessageBoard> reassembleMessageBoardList = new ArrayList<MessageBoard>();

		for (MessageBoard msgBoard : messageBoardList) {

			String pastTime = WebUtil.pastTime(msgBoard.getMessageTime());

			msgBoard.setPastTime(pastTime);

			//add by ContactReply
			MessageBoard replyMessage = new MessageBoard();
			replyMessage.setMessageFatherId(msgBoard.getMessageId());
			replyMessage.setMessageType(msgBoard.getMessageType());
			replyMessage.setMessageUserId(msgBoard.getMessageUserId());
			
			msgBoard.setReplyMessageList(messageBoardMapper.getContactList(replyMessage));
			//end add by ContactReply
			

			reassembleMessageBoardList.add(msgBoard);

		}

		return reassembleMessageBoardList;
	
	}

	/**
	 * 拉取信息集合
	 * 
	 * @param messageBoard
	 * @return List<MessageBoard>
	 * 
	 */
	public List<MessageBoard> pullMessageList(MessageBoard messageBoard) {

		ArrayList<MessageBoard> messageBoardList = (ArrayList<MessageBoard>) messageBoardMapper
		        .pullMessageList(messageBoard);

		ArrayList<MessageBoard> reassembleMessageBoardList = new ArrayList<MessageBoard>();

		for (MessageBoard msgBoard : messageBoardList) {

			long messageId = msgBoard.getMessageId();

			long messageBrowseQuantity = msgBoard.getMessageBrowseQuantity();
			long messagePraiseCount = this.praiseCount(new MessageBoard(messageId));
			long messageReplyCount = this.getMessageCount(new MessageBoard(0, messageId, 0, 0));
			long messageCollectCount = this.collectCount(new MessageBoard(messageId));

			String pastTime = WebUtil.pastTime(msgBoard.getMessageTime());

			msgBoard.setMessageBrowseQuantity(messageBrowseQuantity);
			msgBoard.setMessagePraiseCount(messagePraiseCount);
			msgBoard.setMessageReplyCount(messageReplyCount);
			msgBoard.setMessageCollectCount(messageCollectCount);

			msgBoard.setPastTime(pastTime);

			reassembleMessageBoardList.add(msgBoard);

		}

		return reassembleMessageBoardList;
	}

	/**
	 * 拉取回复信息集合
	 * 
	 * @param messageBoard
	 * @return List<MessageBoard>
	 * 
	 */
	public List<MessageBoard> pullReplyMessageList(MessageBoard messageBoard) {

		ArrayList<MessageBoard> messageBoardList = (ArrayList<MessageBoard>) messageBoardMapper
		        .pullReplyMessageList(messageBoard);

		ArrayList<MessageBoard> reassembleMessageBoardList = new ArrayList<MessageBoard>();

		boolean isFirst = true;

		for (MessageBoard msgBoard : messageBoardList) {
			if (isFirst && messageBoard.getRefresh() == 0) {
				long messageId = msgBoard.getMessageId();

				long messageBrowseQuantity = msgBoard.getMessageBrowseQuantity();
				long messagePraiseCount = this.praiseCount(new MessageBoard(messageId));
				long messageReplyCount = this.getMessageCount(new MessageBoard(0, messageId, 0, 0));
				long messageCollectCount = this.collectCount(new MessageBoard(messageId));

				msgBoard.setMessageBrowseQuantity(messageBrowseQuantity);
				msgBoard.setMessagePraiseCount(messagePraiseCount);
				msgBoard.setMessageReplyCount(messageReplyCount);
				msgBoard.setMessageCollectCount(messageCollectCount);

				isFirst = false;
			}

			String pastTime = WebUtil.pastTime(msgBoard.getMessageTime());
			msgBoard.setPastTime(pastTime);

			reassembleMessageBoardList.add(msgBoard);

		}

		return reassembleMessageBoardList;

	}
	
	/**
	 * 获取联系XX信息集合
	 * 
	 * @param messageBoard
	 * @return List<MessageBoard>
	 * 
	 */
	public List<MessageBoard> getContactList(MessageBoard messageBoard) {

		return messageBoardMapper.getMessageList(messageBoard);
	}

	/**
	 * 获取信息集合
	 * 
	 * @param messageBoard
	 * @return List<MessageBoard>
	 * 
	 */
	public List<MessageBoard> getMessageList(MessageBoard messageBoard) {

		return messageBoardMapper.getMessageList(messageBoard);
	}

	/**
	 * 获取信息总数
	 * 
	 * @param messageBoard
	 * @return long
	 * 
	 */
	public long getMessageCount(MessageBoard messageBoard) {

		return messageBoardMapper.getMessageCount(messageBoard);
	}

	/**
	 * 获取信息详情
	 * 
	 * @param messageBoard
	 * @return MessageBoard
	 * 
	 */
	public MessageBoard getMessageDetail(MessageBoard messageBoard) {

		return messageBoardMapper.getMessageDetail(messageBoard);
	}

	/**
	 * 存储信息
	 * 
	 * @param messageBoard
	 * @return true，成功；false，失败；
	 * 
	 */
	public boolean saveMessage(MessageBoard messageBoard) {

		return messageBoardMapper.saveMessage(messageBoard);
	}

	/**
	 * 更新信息
	 * 
	 * @param messageBoard
	 * @return true，成功；false，失败；
	 * 
	 */
	public boolean updateMessage(MessageBoard messageBoard) {

		return messageBoardMapper.updateMessage(messageBoard);
	}

	public DataGrid<MessageBoard> dataGrid(Map<String, Object> map) {
		DataGrid<MessageBoard> dataGrid = new DataGrid<MessageBoard>();
		long total = messageBoardMapper.countMessage(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<MessageBoard> list = messageBoardMapper.selectMessageBoardList(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public void delete(String ids) {
		String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array) {
			list.add(Long.parseLong(id));
		}

		messageBoardMapper.updateDeleteStatus(list);
	}

	public MessageBoard selectById(long id) {
		return messageBoardMapper.selectById(id);
	}


	/**
	 * 反馈信息接口
	 * @param message
	 * @param content
     */
	public void saveFeedBack(Message message, String content){
		try{
			if(StringUtils.isBlank(content)){
				message.setSuccess(false);
				message.setMsg("服务器无任何内容");
				return;
			}
			MessageBoard messageBoard = JSON.parseObject(content, MessageBoard.class);

			if(StringUtils.isBlank(String.valueOf(messageBoard.getMessageUserId()))){
				message.setMsg("用户Id不能为空");
				message.setSuccess(false);
				return;
			}
			if(StringUtils.isBlank(messageBoard.getMessageTitle())){
				message.setMsg("请输入标题");
				message.setSuccess(false);
				return;
			}
			if(StringUtils.isBlank(messageBoard.getMessageContent())){
				message.setMsg("请输入反馈内容");
				message.setSuccess(false);
				return;
			}

			messageBoard.setMessageType(404);
			messageBoard.setDeleteStatus(0);

			if(messageBoardMapper.saveMessage(messageBoard)){
				message.setSuccess(true);
				message.setMsg("反馈成功");
				return;
			}else{
				message.setSuccess(false);
				message.setMsg("反馈失败");
				return;
			}
		}catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}

package com.cy.core.messageboard.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.messageboard.entity.MessageBoard;
import com.cy.core.messageboard.service.MessageBoardService;
import com.cy.system.WebUtil;

@Namespace("/mobile/messageBoard")
@Action(value = "messageBoardAction", results = { 
		@Result(name = "initFeedBack", location = "/mobile/messageBoard/feed_back.jsp"),
		@Result(name = "initMessageList", location = "/mobile/messageBoard/list.jsp"),
		@Result(name = "initMyMessageList", location = "/mobile/messageBoard/myMessageList.jsp"),
		@Result(name = "initMessageDetail", location = "/mobile/messageBoard/detail.jsp"),
		@Result(name = "initMessageSend", location = "/mobile/messageBoard/messageSend.jsp"),
		@Result(name = "view", location = "/page/admin/messageBoard/viewMessageBoard.jsp"),
		@Result(name = "reply", location = "/page/admin/messageBoard/replyMessageBoard.jsp")
		})
public class MessageBoardAction extends AdminBaseAction
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MessageBoardAction.class);

	@Autowired
	private MessageBoardService messageBoardService;

	private MessageBoard messageBoard;

	private String checkRemark;

	private int checkStatus;

	private String checkPage;

	private String replyMessageContent;
	
	/**
	 * 拉取信息列表
	 * 
	 * 
	 */
	public void pullMessageList()
	{
		logger.info("pullMessageList");
		JSONObject json = new JSONObject();
		
		if(messageBoard.getReply() == 1)
		{
			json.put("list", messageBoardService.pullReplyMessageList(new MessageBoard(messageBoard.getMessageId(), messageBoard.getMessageId(), messageBoard.getCurrentRow(), messageBoard.getIncremental(), messageBoard.getRefresh())));
		}
		else if(messageBoard.getMessageType() != 0 && messageBoard.getHot() == 0)//拉取当前类别的信息列表
		{
			json.put("list", messageBoardService.pullMessageList(new MessageBoard(messageBoard.getMessageType(), messageBoard.getCurrentRow(), messageBoard.getIncremental())));
		}
		else if(messageBoard.getReply() == 0 && messageBoard.getMessageUserId() != 0 && messageBoard.getCollect() == 0)//拉取我发布的信息列表
		{
			json.put("list", messageBoardService.pullMessageList(new MessageBoard(messageBoard.getMessageUserId(), messageBoard.getCurrentRow(), messageBoard.getIncremental())));
		}
		else if(messageBoard.getMessageId() != 0)//拉取当前信息的回复列表
		{
			json.put("list", messageBoardService.pullMessageList(new MessageBoard(0, messageBoard.getMessageId(), messageBoard.getCurrentRow(), messageBoard.getIncremental())));
		}
		else if(messageBoard.getMessageType() != 0 && messageBoard.getHot() == 1)//拉取当前类别的热门信息的列表
		{
			json.put("list", messageBoardService.getHotMessageList(new MessageBoard(messageBoard.getMessageType(), messageBoard.getCurrentRow())));
		}
		else if(messageBoard.getCollect() == 1)//拉取我收藏信息的列表
		{
			json.put("list", messageBoardService.getCollectMessageList(new MessageBoard(messageBoard.getMessageUserId(), messageBoard.getCollect(), messageBoard.getCurrentRow(), messageBoard.getIncremental())));
		}
		
		
		
		super.writeJson(json);
	}
	
	
	/**
	 * 拉取联系XX信息列表
	 * 
	 * 
	 */
	public void pullContactList()
	{
		logger.info("pullContactList");
		JSONObject json = new JSONObject();
		
		if(messageBoard.getMessageType() != 0 && messageBoard.getMessageUserId() != 0)//拉取当前类别中我发送的消息
		{
			json.put("list", messageBoardService.pullContactMessageList(new MessageBoard(messageBoard.getMessageType(), messageBoard.getMessageUserId(), messageBoard.getCurrentRow(), messageBoard.getIncremental())));
		}
		
		
		super.writeJson(json);
	}
	
	/**
	 * 点赞
	 * 
	 */
	public void praise()
	{
		logger.info("praise");
		
		JSONObject json = new JSONObject();
		
		int status = -1;
		
		if(messageBoardService.praiseCount(messageBoard) > 0)
		{
			messageBoard.setPraise(0);//取消赞
			status = 0;
		}
		else
		{
			messageBoard.setPraise(1);//点赞
			status = 1;
		}

		if (!messageBoardService.praise(messageBoard))
		{
			status = -1;
		} 
		
		
		json.put("status", status);

		super.writeJson(json);
	}
	
	
	/**
	 * 收藏
	 * 
	 */
	public void collect()
	{
		logger.info("collect");
		
		JSONObject json = new JSONObject();
		
		int status = -1;
		
		if(messageBoardService.collectCount(messageBoard) > 0)
		{
			messageBoard.setCollect(0);//取消收藏
			status = 0;
		}
		else
		{
			messageBoard.setCollect(1);//收藏信息
			status = 1;
		}

		if (!messageBoardService.collect(messageBoard))
		{
			status = -1;
		} 
		
		
		json.put("status", status);

		super.writeJson(json);
	}
	
	
	/**
	 * 初始化意见反馈
	 * 
	 * @return 列表页的URL
	 * 
	 */
	public String initFeedBack()
	{
		logger.info("initFeedBack");
		super.getRequest().setAttribute("messageBoard", messageBoard);
		return "initFeedBack";
	}

	

	/**
	 * 初始化当前类别的信息列表
	 * 
	 * @return 列表页的URL
	 * 
	 */
	public String initMessageList()
	{
		logger.info("initMessageList");

		//super.getRequest().setAttribute("messageList", messageBoardService.getMessageList(new MessageBoard(messageBoard.getMessageType(),messageBoard.getCurrentRow())));
		//super.getRequest().setAttribute("messageBoardService", messageBoardService);
		super.getRequest().setAttribute("messageBoard", messageBoard);
		return "initMessageList";
	}

	/**
	 * 初始化当前用户所发布的信息列表
	 * 
	 * @return 列表页的URL
	 * 
	 */
	public String initMyMessageList()
	{
		logger.info("initMyMessageList");

		super.getRequest().setAttribute("messageList", messageBoardService.getMessageList(new MessageBoard(messageBoard.getMessageUserId(), messageBoard.getCurrentRow())));
		super.getRequest().setAttribute("messageBoardService", messageBoardService);
		super.getRequest().setAttribute("messageBoard", messageBoard);
		return "initMyMessageList";
	}

	/**
	 * 初始化当前信息的详情与回复
	 * 
	 * @return 内容详情页的URL
	 * 
	 */
	public String initMessageDetail()
	{
		logger.info("initMessageDetail");

		//super.getRequest().setAttribute("messageDetail", messageBoardService.getMessageDetail(new MessageBoard(messageBoard.getMessageId(), 0, -1)));
		//super.getRequest().setAttribute("messageList", messageBoardService.getMessageList(new MessageBoard(0, messageBoard.getMessageId(), messageBoard.getCurrentRow(), messageBoard.getIncremental())));
		//super.getRequest().setAttribute("messageBoardService", messageBoardService);
		MessageBoard mBoard = messageBoardService.getMessageDetail(new MessageBoard(messageBoard.getMessageId()));
		messageBoardService.updateMessage(new MessageBoard(messageBoard.getMessageId(),mBoard.getMessageBrowseQuantity()));
		super.getRequest().setAttribute("messageBoard", messageBoard);
		return "initMessageDetail";
	}

	/**
	 * 初始化信息发布
	 * 
	 * @return 信息发布页的URL
	 * 
	 */
	public String initMessageSend()
	{
		logger.info("initMessageSend");

		super.getRequest().setAttribute("messageBoard", messageBoard);

		return "initMessageSend";
	}

	/**
	 * 信息发布
	 * 
	 */
	public void messageSend()
	{
		logger.info("messageSend");
		messageBoard.setMessageUserIP(WebUtil.getIpAddr(super.getRequest()));
		logger.info(messageBoard);
		JSONObject json = new JSONObject();
		
		//messageBoard.setCheckStatus(1);

		if (messageBoardService.saveMessage(messageBoard))
		{
			json.put("status", 0);// 成功
		} else
		{
			json.put("status", 1);// 失败
		}

		super.writeJson(json);
	}
	
	
	/**
	 * 回复信息
	 * 
	 */
	public void replyMessage()
	{
		logger.info("replyMessage");
		messageBoard.setMessageUserIP(WebUtil.getIpAddr(super.getRequest()));
		messageBoard.setCheckStatus(1);
		
		logger.info(messageBoard);

		Message message = new Message();
		try
		{
			messageBoardService.saveMessage(messageBoard);
			message.setMsg("回复成功");
			message.setSuccess(true);

		} catch (Exception e)
		{
			logger.error(e, e);
			message.setMsg("回复失败");
			message.setSuccess(false);
		}
		super.writeJson(message);

	}

	public void dataGrid()
	{
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("rows", rows);
		map.put("page", page);
		if(messageBoard != null) {
			map.put("messageType", messageBoard.getMessageType());
			map.put("messageTitle",messageBoard.getMessageTitle()) ;
			map.put("messageUserName",messageBoard.getMessageUserName());
			map.put("checkStatus",messageBoard.getCheckStatus());
		}
		if(StringUtils.isNotBlank(checkPage) && checkPage.equals("1")){
			map.put("checkStatus", "-1");
		}
 		super.writeJson(messageBoardService.dataGrid(map));
	}

	public void delete()
	{
		Message message = new Message();
		try
		{
			messageBoardService.delete(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);

		} catch (Exception e)
		{
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void updateCheck()
	{
		Message message = new Message();
		try
		{
			messageBoard = new MessageBoard();
			messageBoard.setMessageId(id);
			messageBoard.setCheckStatus(checkStatus);
			messageBoard.setCheckTime(new Date());
			messageBoard.setCheckUserId(getUser().getUserId());
			messageBoard.setCheckRemark(checkRemark);
			messageBoard.setReplyMessageContent(replyMessageContent);
			messageBoardService.updateMessage(messageBoard);
			message.setMsg("操作成功");
			message.setSuccess(true);

		} catch (Exception e)
		{
			logger.error(e, e);
			message.setMsg("操作失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	
	public String initReplyMessageById()
	{
		messageBoard = messageBoardService.selectById(id);
		
		return "reply";
	}

	public String getById()
	{
		messageBoard = messageBoardService.selectById(id);
		return "view";
	}

	public MessageBoard getMessageBoard()
	{
		return messageBoard;
	}

	public void setMessageBoard(MessageBoard messageBoard)
	{
		this.messageBoard = messageBoard;
	}

	public String getCheckRemark()
	{
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark)
	{
		this.checkRemark = checkRemark;
	}

	public int getCheckStatus()
	{
		return checkStatus;
	}

	public void setCheckStatus(int checkStatus)
	{
		this.checkStatus = checkStatus;
	}


	public String getReplyMessageContent() {
		return replyMessageContent;
	}


	public void setReplyMessageContent(String replyMessageContent) {
		this.replyMessageContent = replyMessageContent;
	}


	public String getCheckPage() {
		return checkPage;
	}

	public void setCheckPage(String checkPage) {
		this.checkPage = checkPage;
	}
}

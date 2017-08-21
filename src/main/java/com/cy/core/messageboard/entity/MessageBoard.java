package com.cy.core.messageboard.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MessageBoard  implements Serializable {

	/**
	 * 留言板实体类
	 * 
	 * 
	 */
	private static final long serialVersionUID = 7521057458963517828L;
	
	
	private long messageId;//信息ID
	private String messageTitle;//信息标题
	private String messageContent;//信息内容
	private String replyMessageContent;//管理员回复的信息内容
	private int messageType;
	//老的信息类别:1 = 招聘信息, 2 = 求职信息, 3 = 资金信息, 4   = 项目信息, 99  = 其他信息, 404 = 意见反馈 
	//新的信息类别:1 = 求职招聘, 2 = 项目合作, 3 = 互帮互助, 404 = 意见反馈, 501 = 联系会长, 502 = 联系学院, 503 = 联系总会
	
	private Date messageTime;//信息时间
	private long messageUserId;//信息发送者ID
	private String messageUserName;//信息发送者姓名
	private String messageUserImageURL;//信息发送者头像
	
	private long messageFatherId;//父节点ID（回复信息时使用）
	
	private int checkStatus;//审核状态（0，未核审；1，已通过；2，未通过）
	private Date checkTime;//审核时间
	private long checkUserId;//审核者ID
	private String checkUserName;//审核者姓名
	private String checkRemark;//审核备注
	
	private long messageBrowseQuantity;//消息浏览数量
	
	private String messageUserIP;//消息发布者的IP地址
	
	private int deleteStatus;//软删除标志（0，正常；1，已删除；）
	
	private int hot;//热门信息（1，热门信息）
	
	private int praise;//点赞（0，取消赞；1，点赞；）
	
	private int collect;//收藏信息（0，取消收藏；1，收藏信息）
	
	private long messageCollectCount;//得到收藏数量
	
	private long messageReplyCount;//得到回复数量
	
	private long messagePraiseCount;//点赞数量
	
	
	private String pastTime;//已失去的时间

	
	private long currentRow;//当前行数
	
	private int incremental = 10;//每次拉取数据的增量
	
	private int reply;//是否查看回复（0，否；1，是；）
	
	private int refresh;
	
	
	private List<MessageBoard> replyMessageList;//回复消息列表
	
	public MessageBoard()
	{
		
	}
	
	
	/**
	 * 构造器
	 * 
	 * @param messageId 消息ID
	 * 
	 */
	public MessageBoard(long messageId)
	{
		this.messageId = messageId;
	}
	
	/**
	 * 构造器
	 * 
	 * @param messageUserId 用户ID
	 * @param currentRow 当前行数
	 * @param incremental 增量值
	 * @param collect 收藏信息（0，否；1，是）
	 * 
	 */
	public MessageBoard(long messageUserId, int collect, long currentRow, int incremental)
	{
		this.messageUserId = messageUserId;
		this.currentRow = currentRow;
		this.incremental = incremental;
		this.collect = collect;
	}
	
	
	/**
	 * 构造器
	 * 
	 * @param messageUserId 用户ID
	 * @param currentRow 当前行数
	 * @param incremental 增量值
	 * 
	 */
	public MessageBoard(long messageUserId, long currentRow, int incremental)
	{
		this.messageUserId = messageUserId;
		this.currentRow = currentRow;
		this.incremental = incremental;
	}
	
	/**
	 * 构造器
	 * 
	 * @param messageId 消息ID，不用时填数字0
	 * @param messageFatherId 父节点ID，不用时填数字0
	 * @param currentRow 当前行数
	 * @param incremental 增量值
	 * @param refresh 
	 * 
	 */
	public MessageBoard(long messageId, long messageFatherId, long currentRow, int incremental, int refresh)
	{
		this.messageId = messageId;
		this.messageFatherId = messageFatherId;
		this.currentRow = currentRow;
		this.incremental = incremental;
		this.refresh = refresh;
	}
	
	/**
	 * 构造器
	 * 
	 * @param messageId 消息ID，不用时填数字0
	 * @param messageFatherId 父节点ID，不用时填数字0
	 * @param currentRow 当前行数
	 * @param incremental 增量值
	 * 
	 */
	public MessageBoard(long messageId, long messageFatherId, long currentRow, int incremental)
	{
		this.messageId = messageId;
		this.messageFatherId = messageFatherId;
		this.currentRow = currentRow;
		this.incremental = incremental;
	}
	
	/**
	 * 构造器
	 * 
	 * @param messageId 消息ID
	 * @param messageBrowseQuantity 原浏览量
	 * 
	 */
	public MessageBoard(long messageId, long messageBrowseQuantity)
	{
		this.messageId = messageId;
		this.messageBrowseQuantity =messageBrowseQuantity + 1;
	}
	
	/**
	 * 构造器
	 * 
	 * @param messageType 消息类别
	 * @param currentRow 当前行数
	 * @param incremental 增量值
	 * 
	 */
	public MessageBoard(int messageType, long currentRow, int incremental)
	{
		this.messageType = messageType;
		this.currentRow = currentRow;
		this.incremental = incremental;
	}
	
	
	/**
	 * 构造器
	 * 
	 * @param messageType 消息类别
	 * @param messageUserId 用户ID
	 * @param currentRow 当前行数
	 * @param incremental 增量值
	 * 
	 */
	public MessageBoard(int messageType, long messageUserId, long currentRow, int incremental)
	{
		this.messageType = messageType;
		this.currentRow = currentRow;
		this.incremental = incremental;
		this.messageUserId = messageUserId;
	}
	
	public MessageBoard(
			long messageId,
			String messageTitle,
			String messageContent,
			int messageType,
			Date messageTime,
			long messageUserId,
			long messageFatherId,
			int checkStatus,
			Date checkTime,
			long checkUserId,
			String checkRemark,
			long messageBrowseQuantity,
			String messageUserIP,
			int deleteStatus
			)
	{
		this.messageId = messageId;
		this.messageTitle = messageTitle;
		this.messageContent = messageContent;
		this.messageType = messageType;
		this.messageTime = messageTime;
		this.messageUserId = messageUserId;
		this.messageFatherId = messageFatherId;
		this.checkStatus = checkStatus;
		this.checkTime = checkTime;
		this.checkUserId = checkUserId;
		this.checkRemark = checkRemark;
		this.messageBrowseQuantity = messageBrowseQuantity;
		this.messageUserIP = messageUserIP;
		this.deleteStatus = deleteStatus;
	}
	
	
	public long getMessageId() {
		return messageId;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public int getMessageType() {
		return messageType;
	}
	public Date getMessageTime() {
		return messageTime;
	}
	public long getMessageUserId() {
		return messageUserId;
	}
	public long getMessageFatherId() {
		return messageFatherId;
	}
	public int getCheckStatus() {
		return checkStatus;
	}
	public String getCheckRemark() {
		return checkRemark;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}
	public void setMessageUserId(long messageUserId) {
		this.messageUserId = messageUserId;
	}
	public void setMessageFatherId(long messageFatherId) {
		this.messageFatherId = messageFatherId;
	}
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}
	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}
	public long getMessageBrowseQuantity() {
		return messageBrowseQuantity;
	}
	public void setMessageBrowseQuantity(long messageBrowseQuantity) {
		this.messageBrowseQuantity = messageBrowseQuantity;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public long getCheckUserId() {
		return checkUserId;
	}
	public void setCheckUserId(long checkUserId) {
		this.checkUserId = checkUserId;
	}
	public int getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	public String getMessageUserIP() {
		return messageUserIP;
	}
	public void setMessageUserIP(String messageUserIP) {
		this.messageUserIP = messageUserIP;
	}

	public String getMessageUserName() {
		return messageUserName;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setMessageUserName(String messageUserName) {
		this.messageUserName = messageUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	
	public String getMessageUserImageURL() {
		return messageUserImageURL;
	}

	public void setMessageUserImageURL(String messageUserImageURL) {
		this.messageUserImageURL = messageUserImageURL;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageBoard [messageId=");
		builder.append(messageId);
		builder.append(", messageTitle=");
		builder.append(messageTitle);
		builder.append(", messageContent=");
		builder.append(messageContent);
		builder.append(", messageType=");
		builder.append(messageType);
		builder.append(", messageTime=");
		builder.append(messageTime);
		builder.append(", messageUserId=");
		builder.append(messageUserId);
		builder.append(", messageUserImageURL=");
		builder.append(messageUserImageURL);
		builder.append(", deleteStatus=");
		builder.append(deleteStatus);
		builder.append(", messageFatherId=");
		builder.append(messageFatherId);
		builder.append(", checkStatus=");
		builder.append(checkStatus);
		builder.append(", checkTime=");
		builder.append(checkTime);
		builder.append(", checkUserId=");
		builder.append(checkUserId);
		builder.append(", checkUserName=");
		builder.append(checkUserName);
		builder.append(", checkRemark=");
		builder.append(checkRemark);
		builder.append(", messageBrowseQuantity=");
		builder.append(messageBrowseQuantity);
		builder.append(", messageUserIP=");
		builder.append(messageUserIP);
		builder.append(", deleteStatus=");
		builder.append(deleteStatus);
		builder.append("]");
		return builder.toString();
	}

	public long getCurrentRow() {
		return currentRow;
	}

	public int getIncremental() {
		return incremental;
	}

	public void setCurrentRow(long currentRow) {
		this.currentRow = currentRow;
	}

	public void setIncremental(int incremental) {
		this.incremental = incremental;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

	public int getCollect() {
		return collect;
	}

	public void setCollect(int collect) {
		this.collect = collect;
	}

	public long getMessageReplyCount() {
		return messageReplyCount;
	}

	public long getMessagePraiseCount() {
		return messagePraiseCount;
	}

	public void setMessageReplyCount(long messageReplyCount) {
		this.messageReplyCount = messageReplyCount;
	}

	public void setMessagePraiseCount(long messagePraiseCount) {
		this.messagePraiseCount = messagePraiseCount;
	}

	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
	}

	public String getPastTime() {
		return pastTime;
	}

	public void setPastTime(String pastTime) {
		this.pastTime = pastTime;
	}


	public long getMessageCollectCount() {
		return messageCollectCount;
	}


	public void setMessageCollectCount(long messageCollectCount) {
		this.messageCollectCount = messageCollectCount;
	}


	public int getReply() {
		return reply;
	}


	public void setReply(int reply) {
		this.reply = reply;
	}


	public int getRefresh() {
		return refresh;
	}


	public void setRefresh(int refresh) {
		this.refresh = refresh;
	}


	public List<MessageBoard> getReplyMessageList() {
		return replyMessageList;
	}


	public void setReplyMessageList(List<MessageBoard> replyMessageList) {
		this.replyMessageList = replyMessageList;
	}


	public String getReplyMessageContent() {
		return replyMessageContent;
	}


	public void setReplyMessageContent(String replyMessageContent) {
		this.replyMessageContent = replyMessageContent;
	}

	

}

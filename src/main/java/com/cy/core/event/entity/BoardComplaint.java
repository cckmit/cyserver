package com.cy.core.event.entity;

import java.io.Serializable;
import java.util.Date;

public class BoardComplaint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7612841526129436340L;
	private long boardId;  			//花絮ID
	private String eventId;  		//花絮所属活动ID
	private int complaintNum;		//投诉数量
	
	private String boardComment;	//花絮内容
	private Date boardCreateTime;	//花絮创建时间
	private int boardStatus;		//花絮当前状态
	private String boardUserName;	//花絮发表人
	private String eventTitle;		//所属活动标题
	private int eventType;			//所属活动性质（ 0=官方， 1=个人）
	private String reasons;   //
	private String createTime;
	private String status;
	private String boardsex;
	private String boardpicture;
	
	public long getBoardId() {
		return boardId;
	}
	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public int getComplaintNum() {
		return complaintNum;
	}
	public void setComplaintNum(int complaintNum) {
		this.complaintNum = complaintNum;
	}
	public String getBoardComment() {
		return boardComment;
	}
	public void setBoardComment(String boardComment) {
		this.boardComment = boardComment;
	}
	public Date getBoardCreateTime() {
		return boardCreateTime;
	}
	public void setBoardCreateTime(Date boardCreateTime) {
		this.boardCreateTime = boardCreateTime;
	}
	public void setBoardStatus(int boardStatus) {
		this.boardStatus = boardStatus;
	}
	public int getBoardStatus() {
		return boardStatus;
	}
	public String getBoardUserName() {
		return boardUserName;
	}
	public void setBoardUserName(String boardUserName) {
		this.boardUserName = boardUserName;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public int getEventType() {
		return eventType;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBoardsex() {
		return boardsex;
	}
	public void setBoardsex(String boardsex) {
		this.boardsex = boardsex;
	}
	public String getBoardpicture() {
		return boardpicture;
	}
	public void setBoardpicture(String boardpicture) {
		this.boardpicture = boardpicture;
	}

	
	

}

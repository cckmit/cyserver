package com.cy.core.mobevent.entity;

import java.io.Serializable;
import java.util.Date;

public class CyEventBoardComment implements Serializable{

	/**
	 * 花絮评论
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;//评论编号
	private long boardId;//花絮编号
	private String comment;//评论文字
	private String userInfoId;//评论人
	private String userName;//前台用户名称
	private String userAvatar;//前台用户头像
	private String userTel;//前台用户电话号码
	private String userSex;//前台用户性别 0代表男，1代表女
	private String userAvatarUrl;
	
	private Date createTime;//评论时间
	private String createTimeStr;//评论时间(格式化)
	
	private long currentRow;//当前行数
	
	private int incremental = 10;//每次拉取数据的增量
	
	public long getId() {
		return id;
	}
	public long getBoardId() {
		return boardId;
	}
	public String getComment() {
		return comment;
	}
	public String getUserInfoId() {
		return userInfoId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setUserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	
	public String getUserName() {
		return userName;
	}
	public String getUserAvatar() {
		return userAvatar;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
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

	public String getUserAvatarUrl() {
		return userAvatarUrl;
	}

	public void setUserAvatarUrl(String userAvatarUrl) {
		this.userAvatarUrl = userAvatarUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyEventBoardComment [id=<");
		builder.append(id);
		builder.append(">, boardId=<");
		builder.append(boardId);
		builder.append(">, comment=<");
		builder.append(comment);
		builder.append(">, userInfoId=<");
		builder.append(userInfoId);
		builder.append(">, userName=<");
		builder.append(userName);
		builder.append(">, userAvatar=<");
		builder.append(userAvatar);
		builder.append(">, userAvatarUrl=<");
		builder.append(userAvatarUrl);
		builder.append(">, createTime=<");
		builder.append(createTime);
		builder.append(">, createTimeStr=<");
		builder.append(createTimeStr);
		builder.append(">, currentRow=<");
		builder.append(currentRow);
		builder.append(">, incremental=<");
		builder.append(incremental);
		builder.append(">]");
		return builder.toString();
	}
	public String getUserTel() {
		return userTel;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	
}

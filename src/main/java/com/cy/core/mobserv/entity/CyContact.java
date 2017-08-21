package com.cy.core.mobserv.entity;

import java.io.Serializable;
import java.util.Date;

public class CyContact extends CyServExtension implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;// 标题
	private String content;//内容
	private int category;//信息类别（1=联系总会，2=联系学院，3=联系会长）
	private String replyContent;//回复内容
	private long replyUserId;//回复人（对应user.userId）
	private String replyUserName;//回复姓名（对应user.userName）
	private Date replyTime;//回复时间
	private String replyTimeStr;
	private int status;//状态（0=正常，1=用户自己删除，2=管理员删除）

	private String alumniId ;	// 分会编号
	private int page ;	// 页数
	private int rows ;	// 每页行数
	private String userPicture ;	// 用户图像
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public long getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(long replyUserId) {
		this.replyUserId = replyUserId;
	}
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
	public String getReplyTimeStr() {
		return replyTimeStr;
	}
	public void setReplyTimeStr(String replyTimeStr) {
		this.replyTimeStr = replyTimeStr;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getAlumniId() {
		return alumniId;
	}

	public void setAlumniId(String alumniId) {
		this.alumniId = alumniId;
	}

	public int getPage() {return page;}

	public void setPage(int page) {this.page = page;}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getReplyUserName() {return replyUserName;}

	public void setReplyUserName(String replyUserName) {this.replyUserName = replyUserName;}

	public String getUserPicture() {return userPicture;}

	public void setUserPicture(String userPicture) {this.userPicture = userPicture;}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyContact [title=");
		builder.append(title);
		builder.append(", content=");
		builder.append(content);
		builder.append(", category=");
		builder.append(category);
		builder.append(", replyContent=");
		builder.append(replyContent);
		builder.append(", replyUserId=");
		builder.append(replyUserId);
		builder.append(", replyTime=");
		builder.append(replyTime);
		builder.append(", replyTimeStr=");
		builder.append(replyTimeStr);
		builder.append(", status=");
		builder.append(status);
		builder.append(", getCurrentRow()=");
		builder.append(getCurrentRow());
		builder.append(", getIncremental()=");
		builder.append(getIncremental());
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getAccountNum()=");
		builder.append(getAccountNum());
		builder.append(", getCreateTime()=");
		builder.append(getCreateTime());
		builder.append(", getCreateTimeStr()=");
		builder.append(getCreateTimeStr());
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
}

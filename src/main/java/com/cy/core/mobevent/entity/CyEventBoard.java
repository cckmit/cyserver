package com.cy.core.mobevent.entity;

import com.cy.common.utils.StringUtils;
import com.cy.system.Global;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CyEventBoard implements Serializable{

	/**
	 * 花絮
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private long id; //花絮编号
	
	private String eventId; //活动编号
	
	private String userInfoId;  //创建花絮的用户编号
	
	private String userName;//前台用户名称
	
	private String userAvatar;//前台用户头像
	private String userAvatar_xd;
	private String userTel;//前台用户电话号码
	private String userSex;//前台用户性别 0 代表男，1代表女
	
	private String comment; //花絮简介
	
	private Date createTime; //花絮创建时间
	
	private String createTimeStr;//格式化创建时间
	
	private long praiseNum; //花絮点赞数
	
	private boolean parise;//true 已赞， false 未赞
	
	private long commentNum; //花絮评论数
	
	private int status; //0=正常，1=投诉处理-花絮正常，2=投诉处理-花絮违规，3=用户自己删除
	
	private long currentRow;//当前行数
	
	private int incremental = 10;//每次拉取数据的增量

	private String userAvatarUrl;

	private List<CyEventBoardPic> cyEventBoardPicList;//图片列表
	
	private List<CyEventBoardComment> cyEventBoardCommentList;//评论列表
	
	private String address; //GPS定位地址

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public long getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(long praiseNum) {
		this.praiseNum = praiseNum;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(long commentNum) {
		this.commentNum = commentNum;
	}

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAvatar() {
		if(StringUtils.isBlank(userAvatar) && StringUtils.isNotBlank(userAvatarUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(userAvatarUrl.indexOf("http") < 0) {
				userAvatar = Global.URL_DOMAIN + userAvatarUrl ;
			}else{
				userAvatar=userAvatarUrl;
			}
		}
		return userAvatar;
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
	

	public List<CyEventBoardPic> getCyEventBoardPicList() {
		return cyEventBoardPicList;
	}

	public void setCyEventBoardPicList(List<CyEventBoardPic> cyEventBoardPicList) {
		this.cyEventBoardPicList = cyEventBoardPicList;
	}

	public List<CyEventBoardComment> getCyEventBoardCommentList() {
		return cyEventBoardCommentList;
	}

	public void setCyEventBoardCommentList(
			List<CyEventBoardComment> cyEventBoardCommentList) {
		this.cyEventBoardCommentList = cyEventBoardCommentList;
	}
	
	public boolean isParise() {
		return parise;
	}

	public void setParise(boolean parise) {
		this.parise = parise;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyEventBoard [id=<");
		builder.append(id);
		builder.append(">, eventId=<");
		builder.append(eventId);
		builder.append(">, userInfoId=<");
		builder.append(userInfoId);
		builder.append(">, userName=<");
		builder.append(userName);
		builder.append(">, userAvatar=<");
		builder.append(userAvatar);
		builder.append(">, userAvatarUrl=<");
		builder.append(userAvatarUrl);
		builder.append(">, comment=<");
		builder.append(comment);
		builder.append(">, createTime=<");
		builder.append(createTime);
		builder.append(">, createTimeStr=<");
		builder.append(createTimeStr);
		builder.append(">, praiseNum=<");
		builder.append(praiseNum);
		builder.append(">, commentNum=<");
		builder.append(commentNum);
		builder.append(">, status=<");
		builder.append(status);
		builder.append(">, currentRow=<");
		builder.append(currentRow);
		builder.append(">, incremental=<");
		builder.append(incremental);
		builder.append(">, cyEventBoardPicList=<");
		builder.append(cyEventBoardPicList);
		builder.append(">, cyEventBoardCommentList=<");
		builder.append(cyEventBoardCommentList);
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

	public String getUserAvatarUrl() {
		if(StringUtils.isBlank(userAvatarUrl) && StringUtils.isNotBlank(userAvatar) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(userAvatar.indexOf(Global.URL_DOMAIN) == 0) {
				userAvatarUrl = userAvatar.substring(Global.URL_DOMAIN.length()) ;
			}else{
				userAvatarUrl=userAvatar;
			}
		}
		return userAvatarUrl;
	}

	public String getUserAvatar_xd() {
		if(StringUtils.isNotBlank(userAvatar)){
			if(userAvatar.indexOf(Global.URL_DOMAIN) == 0) {
				userAvatar_xd= userAvatar.substring(Global.URL_DOMAIN.length());
			}else{
				userAvatar_xd = userAvatar ;
			}
		}
		return userAvatar_xd;
	}

	public void setUserAvatarUrl(String userAvatarUrl) {
		this.userAvatarUrl = userAvatarUrl;
	}
	public void setUserAvatar_xd(String userAvatar_xd) {
		this.userAvatar_xd = userAvatar_xd;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

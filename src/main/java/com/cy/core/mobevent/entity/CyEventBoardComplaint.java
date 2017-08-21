package com.cy.core.mobevent.entity;

import com.cy.common.utils.StringUtils;
import com.cy.system.Global;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CyEventBoardComplaint implements Serializable{
	
	/**
	 * 花絮举报
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;//举报编号
	private long boardId;//花絮编号
	private String userInfoId;//举报人
	private String userName;//前台用户名称
	private String userAvatar;//前台用户头像
	private String userAvatarUrl;//相对路径
	private String reason;//举报理由
	private Date createTime;//举报时间
	private String createTimeStr;//举报时间(格式化)

	private String bussId;       //投诉举报业务编号
	private String bussType;     //投诉举报业务类型（10：活动；20：花絮）

	private List<String> imageList; //举报图片列表
	
	public long getId() {
		return id;
	}
	public long getBoardId() {
		return boardId;
	}
	public String getUserInfoId() {
		return userInfoId;
	}
	public String getReason() {
		return reason;
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
	public void setUserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
		if(StringUtils.isBlank(userAvatar) && StringUtils.isNotBlank(userAvatarUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if (userAvatarUrl.indexOf("http") < 0) {
				userAvatar = Global.URL_DOMAIN + userAvatarUrl;
			} else {
				userAvatar = userAvatarUrl;
			}
		}
		return userAvatar;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
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

	public void setUserAvatarUrl(String userAvatarUrl) {
		this.userAvatarUrl = userAvatarUrl;
	}

	public String getBussId() {
		return bussId;
	}

	public void setBussId(String bussId) {
		this.bussId = bussId;
	}

	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyEventBoardComplaint [id=<");
		builder.append(id);
		builder.append(">, boardId=<");
		builder.append(boardId);
		builder.append(">, userInfoId=<");
		builder.append(userInfoId);
		builder.append(">, userName=<");
		builder.append(userName);
		builder.append(">, userAvatar=<");
		builder.append(userAvatar);
		builder.append(">, userAvatarUrl=<");
		builder.append(userAvatarUrl);
		builder.append(">, reason=<");
		builder.append(reason);
		builder.append(">, createTime=<");
		builder.append(createTime);
		builder.append(">, createTimeStr=<");
		builder.append(createTimeStr);
		builder.append("]");
		return builder.toString();
	}
	
}

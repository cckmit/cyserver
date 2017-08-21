package com.cy.core.mobserv.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class CyServExtension implements Serializable{

	/**
	 * 
	 * 帮帮忙扩展字段
	 */
	private static final long serialVersionUID = 1L;
	
	private long id;
	private long serviceId; //'帮帮忙ID',
	private long userId; //官方回复人（对应user.userId）
	private long accountNum; //'评论人（对应UserProfile.accountNum字段）',
	private Date createTime; //'创建时间',
	private String createTimeStr;//格式化创建时间
	
	private String userName;//前台用户名称
	private String userAvatar;//前台用户头像
	private String userAvatarUrl; //用户头像链接修复
	private String userTel;//前台用户电话号码
	private String userSex;//前台用户性别 0代表男，1代表女
	
	private long commentNum;//评论数
	private long praiseNum;//点赞数
	private long favoriteNum;//收藏数
	
	
	private long currentRow;//当前行数
	
	private int incremental = 10;//每次拉取数据的增量

	public String getUserName() {
		return userName;
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

	public String getUserTel() {
		return userTel;
	}

	public String getUserSex() {
		return userSex;
	}

	public long getCurrentRow() {
		return currentRow;
	}

	public int getIncremental() {
		return incremental;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public void setCurrentRow(long currentRow) {
		this.currentRow = currentRow;
	}

	public void setIncremental(int incremental) {
		this.incremental = incremental;
	}

	public long getId() {
		return id;
	}

	

	

	public Date getCreateTime() {
		return createTime;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getServiceId() {
		return serviceId;
	}

	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}


	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public long getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(long commentNum) {
		this.commentNum = commentNum;
	}
	
	public long getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(long praiseNum) {
		this.praiseNum = praiseNum;
	}

	public long getFavoriteNum() {
		return favoriteNum;
	}

	public void setFavoriteNum(long favoriteNum) {
		this.favoriteNum = favoriteNum;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyServExtension [id=<");
		builder.append(id);
		builder.append(">, serviceId=<");
		builder.append(serviceId);
		builder.append(">, userId=<");
		builder.append(userId);
		builder.append(">, accountNum=<");
		builder.append(accountNum);
		builder.append(">, createTime=<");
		builder.append(createTime);
		builder.append(">, userName=<");
		builder.append(userName);
		builder.append(">, userAvatar=<");
		builder.append(userAvatar);
		builder.append(">, userTel=<");
		builder.append(userTel);
		builder.append(">, userSex=<");
		builder.append(userSex);
		builder.append(">, commentNum=<");
		builder.append(commentNum);
		builder.append(">, praiseNum=<");
		builder.append(praiseNum);
		builder.append(">, favoriteNum=<");
		builder.append(favoriteNum);
		builder.append(">, currentRow=<");
		builder.append(currentRow);
		builder.append(">, incremental=<");
		builder.append(incremental);
		builder.append(">]");
		return builder.toString();
	}

	public long getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(long accountNum) {
		this.accountNum = accountNum;
	}

	
	

		
}

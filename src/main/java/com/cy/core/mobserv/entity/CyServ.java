package com.cy.core.mobserv.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class CyServ extends CyServExtension implements Serializable{

	/**
	 * 
	 * 帮帮忙主表
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;//'标题'
	private String content;//'内容',
	private int category;//信息类别（1=互帮互助，2=项目合作，3=求职招聘）
	private String region;//'地域',
	private int type;//性质（0=官方 ，5=校友会，9=个人）
	private int auditStatus;//'审核状态（0=未审核，1=通过，2=不通过）',
	private String auStatu;	//审核状态
	private String auditOpinion;//'审核意见',
	private long auditUserId;//'审核人（对应user.userId）',
	private Date auditTime;//'审核时间',
	private int status;//'状态（0=正常，1=投诉处理-信息正常，2=投诉处理-信息违规，3=用户自己删除，4=管理员删除）',
	private String sevStatu; //状态

	private String viewType;//空为查看所有，文字 'favorite' 为查看我的收藏
	
	private boolean parise;//true 已赞， false 未赞
	
	private boolean favorite;//true 已收藏，false 未收藏
	
	private List<CyServPic> cyServPicList;//图片列表
	
	private List<CyServComment> cyServCommentList;//评论列表
	
	private int isWhat;//0:本地，1:所有，2:我的收藏，3:我的发帖
	
	
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
	public String getRegion() {
		return region;
	}
	public int getType() {
		return type;
	}
	
	public int getAuditStatus() {
		return auditStatus;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public long getAuditUserId() {
		return auditUserId;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public int getStatus() {
		return status;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public void setAuditUserId(long auditUserId) {
		this.auditUserId = auditUserId;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isParise() {
		return parise;
	}
	public void setParise(boolean parise) {
		this.parise = parise;
	}
	
	public List<CyServPic> getCyServPicList() {
		return cyServPicList;
	}
	public List<CyServComment> getCyServCommentList() {
		return cyServCommentList;
	}
	public void setCyServPicList(List<CyServPic> cyServPicList) {
		this.cyServPicList = cyServPicList;
	}
	public void setCyServCommentList(List<CyServComment> cyServCommentList) {
		this.cyServCommentList = cyServCommentList;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyServ [title=");
		builder.append(title);
		builder.append(", content=");
		builder.append(isWhat);
		builder.append(", isWhat=");
		builder.append(content);
		builder.append(", category=");
		builder.append(category);
		builder.append(", region=");
		builder.append(region);
		builder.append(", type=");
		builder.append(type);
		builder.append(", auditStatus=");
		builder.append(auditStatus);
		builder.append(", auditOpinion=");
		builder.append(auditOpinion);
		builder.append(", auditUserId=");
		builder.append(auditUserId);
		builder.append(", auditTime=");
		builder.append(auditTime);
		builder.append(", status=");
		builder.append(status);
		builder.append(", parise=");
		builder.append(parise);
		builder.append(", favorite=");
		builder.append(favorite);
		builder.append(", getUserName()=");
		builder.append(getUserName());
		builder.append(", getUserAvatar()=");
		builder.append(getUserAvatar());
		builder.append(", getUserTel()=");
		builder.append(getUserTel());
		builder.append(", getUserSex()=");
		builder.append(getUserSex());
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
		builder.append(", getCommentNum()=");
		builder.append(getCommentNum());
		builder.append(", getPraiseNum()=");
		builder.append(getPraiseNum());
		builder.append(", getFavoriteNum()=");
		builder.append(getFavoriteNum());
		builder.append("]");
		return builder.toString();
	}
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	public int getIsWhat() {
		return isWhat;
	}
	public void setIsWhat(int isWhat) {
		this.isWhat = isWhat;
	}
	
	
	
	

}

package com.cy.core.mobevent.entity;

import com.cy.common.utils.StringUtils;
import com.cy.system.Global;

import java.io.Serializable;
import java.util.Date;

public class CyEvent implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String title;//活动标题
	private int type;//活动性质： 0=官方， 1=个人
	private String category;//活动类别（聚餐、旅游、讲座...）
	private String place;//活动地点
	private String content;//活动内容介绍
	private String pic;//活动封面图片
	private String organizer;//主办方（官方活动）
	
	private Date startTime;//活动开始时间
	private Date endTime;//活动结束时间
	private Date signupStartTime;//报名开始时间
	private Date signupEndTime;//报名截止时间
	
	private String startShortTime;//活动开始短时间
	private String endShortTime;//活动结束短时间
	private String signupStartShortTime;//报名开始短时间
	private String signupEndShortTime;//报名截止短时间
	
	private int minPeople;//人数下限（最少报名人数）
	private int maxPeople;//人数上限（最多报名人数， 0=无上限）
	private int needSignIn;//是否需要签到（0=不需要， 1=需要）
	private String signInCode;//签到码
	private Date createTime;//创建时间
	private long userId;//后台创建官方活动的管理员
	private String userInfoId;//前台创建个人活动的用户
	private String userName;//前台用户名称
	private String userAvatar;//前台用户头像
	private String userAvatarUrl;
	private String userTel;//前台用户电话号码
	private String userSex;//前台用户性别  0代表男，1代表女
	
	private int needNotification;//是否有通知
	private String notification;//通知文本
	private int auditStatus;//审核状态（0=未审核，1=通过，2=不通过）
	private String auditOpinion;//审核意见
	private long auditUserId;//审核人
	private Date auditTime;//审核时间
	private int status;//活动状态（0=正常， 1=取消， 2=删除）
	private double score;//评价平均得分
	//参与人是否已查看通知（0=未查看， 1=已查看）
	private int viewNotification;
	//参与人是否签到（0=未签到， 1=已签到）
	private int isSignIn;
	//参与人数
	private int joinedPeople;
	//辅助显示活动当前状态
	private int eventStatus;
	//活动数据相关的链接地址
	private String link;
	private String startTimeStr;//格式化活动开始时间
	private String endTimeStr;//格式化活动结束时间
	private String signupStartTimeStr;//格式化报名开始时间
	private String signupEndTimeStr;//格式化报名结束时间
	
	private String region;
	
	private long currentRow;//当前行数
	
	private int incremental = 10;//每次拉取数据的增量
	
	
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getEndTimeStr() {
		return endTimeStr;
	}
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	public String getSignupStartTimeStr() {
		return signupStartTimeStr;
	}
	public void setSignupStartTimeStr(String signupStartTimeStr) {
		this.signupStartTimeStr = signupStartTimeStr;
	}
	public String getSignupEndTimeStr() {
		return signupEndTimeStr;
	}
	public void setSignupEndTimeStr(String signupEndTimeStr) {
		this.signupEndTimeStr = signupEndTimeStr;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(int eventStatus) {
		this.eventStatus = eventStatus;
	}
	public String getStartTimeStr() {
		return startTimeStr;
	}
	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}
	public int getJoinedPeople() {
		return joinedPeople;
	}
	public void setJoinedPeople(int joinedPeople) {
		this.joinedPeople = joinedPeople;
	}
	public long getCurrentRow() {
		return currentRow;
	}
	public void setCurrentRow(long currentRow) {
		this.currentRow = currentRow;
	}
	public int getIncremental() {
		return incremental;
	}
	public void setIncremental(int incremental) {
		this.incremental = incremental;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getSignupStartTime() {
		return signupStartTime;
	}
	public void setSignupStartTime(Date signupStartTime) {
		this.signupStartTime = signupStartTime;
	}
	public Date getSignupEndTime() {
		return signupEndTime;
	}
	public void setSignupEndTime(Date signupEndTime) {
		this.signupEndTime = signupEndTime;
	}
	public int getMinPeople() {
		return minPeople;
	}
	public void setMinPeople(int minPeople) {
		this.minPeople = minPeople;
	}
	public int getMaxPeople() {
		return maxPeople;
	}
	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	}
	
	public String getSignInCode() {
		return signInCode;
	}
	public void setSignInCode(String signInCode) {
		this.signInCode = signInCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserInfoId() {
		return userInfoId;
	}
	public void setUserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}
	
	public String getNotification() {
		return notification;
	}
	public void setNotification(String notification) {
		this.notification = notification;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public long getAuditUserId() {
		return auditUserId;
	}
	public void setAuditUserId(long auditUserId) {
		this.auditUserId = auditUserId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	public int getNeedSignIn() {
		return needSignIn;
	}
	public int getNeedNotification() {
		return needNotification;
	}
	public int getViewNotification() {
		return viewNotification;
	}
	public int getIsSignIn() {
		return isSignIn;
	}
	public void setNeedSignIn(int needSignIn) {
		this.needSignIn = needSignIn;
	}
	public void setNeedNotification(int needNotification) {
		this.needNotification = needNotification;
	}
	public void setViewNotification(int viewNotification) {
		this.viewNotification = viewNotification;
	}
	public void setIsSignIn(int isSignIn) {
		this.isSignIn = isSignIn;
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
	public String getStartShortTime() {
		return startShortTime;
	}
	public String getEndShortTime() {
		return endShortTime;
	}
	public String getSignupStartShortTime() {
		return signupStartShortTime;
	}
	public String getSignupEndShortTime() {
		return signupEndShortTime;
	}
	public void setStartShortTime(String startShortTime) {
		this.startShortTime = startShortTime;
	}
	public void setEndShortTime(String endShortTime) {
		this.endShortTime = endShortTime;
	}
	public void setSignupStartShortTime(String signupStartShortTime) {
		this.signupStartShortTime = signupStartShortTime;
	}
	public void setSignupEndShortTime(String signupEndShortTime) {
		this.signupEndShortTime = signupEndShortTime;
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

	public void setUserAvatarUrl(String userAvatarUrl) {
		this.userAvatarUrl = userAvatarUrl;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyEvent [id=");
		builder.append(id);
		builder.append(", title=");
		builder.append(title);
		builder.append(", type=");
		builder.append(type);
		builder.append(", category=");
		builder.append(category);
		builder.append(", place=");
		builder.append(place);
		builder.append(", content=");
		builder.append(content);
		builder.append(", pic=");
		builder.append(pic);
		builder.append(", organizer=");
		builder.append(organizer);
		builder.append(", startTime=");
		builder.append(startTime);
		builder.append(", endTime=");
		builder.append(endTime);
		builder.append(", signupStartTime=");
		builder.append(signupStartTime);
		builder.append(", signupEndTime=");
		builder.append(signupEndTime);
		builder.append(", startShortTime=");
		builder.append(startShortTime);
		builder.append(", endShortTime=");
		builder.append(endShortTime);
		builder.append(", signupStartShortTime=");
		builder.append(signupStartShortTime);
		builder.append(", signupEndShortTime=");
		builder.append(signupEndShortTime);
		builder.append(", minPeople=");
		builder.append(minPeople);
		builder.append(", maxPeople=");
		builder.append(maxPeople);
		builder.append(", needSignIn=");
		builder.append(needSignIn);
		builder.append(", signInCode=");
		builder.append(signInCode);
		builder.append(", createTime=");
		builder.append(createTime);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", userInfoId=");
		builder.append(userInfoId);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", userAvatar=");
		builder.append(userAvatar);
		builder.append(", userTel=");
		builder.append(userTel);
		builder.append(", userSex=");
		builder.append(userSex);
		builder.append(", needNotification=");
		builder.append(needNotification);
		builder.append(", notification=");
		builder.append(notification);
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
		builder.append(", score=");
		builder.append(score);
		builder.append(", viewNotification=");
		builder.append(viewNotification);
		builder.append(", isSignIn=");
		builder.append(isSignIn);
		builder.append(", joinedPeople=");
		builder.append(joinedPeople);
		builder.append(", eventStatus=");
		builder.append(eventStatus);
		builder.append(", link=");
		builder.append(link);
		builder.append(", startTimeStr=");
		builder.append(startTimeStr);
		builder.append(", endTimeStr=");
		builder.append(endTimeStr);
		builder.append(", signupStartTimeStr=");
		builder.append(signupStartTimeStr);
		builder.append(", signupEndTimeStr=");
		builder.append(signupEndTimeStr);
		builder.append(", region=");
		builder.append(region);
		builder.append(", currentRow=");
		builder.append(currentRow);
		builder.append(", incremental=");
		builder.append(incremental);
		builder.append("]");
		return builder.toString();
	}
	
	
		
	
	

}

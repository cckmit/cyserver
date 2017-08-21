package com.cy.core.event.entity;

import java.io.Serializable;
import java.util.Date;

import com.cy.base.entity.DataEntity;
import com.cy.core.user.entity.User;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

public class Event extends DataEntity<Event>
{

	private static final long serialVersionUID = 8995887982157489814L;
	
//	private long id;
	private String title;  		//活动标题
	private int type;			//活动性质： 0=官方， 5=校友会， 9=个人
	private String category;	//活动类别（聚餐、旅游、讲座...）
	private String place;		//活动地点
	private String content;		//活动介绍
	private String pic;     	//活动图片
	private String organizer;  	//主办方（官方活动）
	private String startTime;		//开始时间
	private String endTime;		//结束时间
	private String signupStartTime;	//报名开始时间
	private String signupEndTime;		//报名截止时间
	private int minPeople;		//人数下限（最少报名人数）
	private int maxPeople;		//人数上限（最多报名人数， 0=无上限）
	private boolean needSignIn;	//是否需要签到（0=不需要， 1=需要）
	private String signInCode;	//签到码
	private Date createTime;	//创建时间
	private long userId;		//后台创建官方、校友会活动的管理员
	private String userInfoId;	//前台创建个人活动的用户
	private String isFree;		//是否免费
	private String cost;		//花费金额
	private String costMemo;    //收费说明
	private String perAlumniId;	//个人活动可见分会
	
	private boolean needNotification;	//是否有通知（0=没有， 1=有）
	private String notification;		//通知文本
	
	private int auditStatus;	//审核状态（0=未审核，1=通过，2=不通过）
	private String auditOpinion;//审核意见
	private long auditUserId;	//审核人
	private Date auditTime;		//审核时间
	
	private int status;			//活动状态（0=正常， 1=取消， 2=删除）
	private double score;		//评价平均得分
	private String region;		//地域
	
	private String dept_id;		//所属院系
	private String dept_name;	//所属院系名称
	
	private User user;					//  --> userId
	private UserProfile userProfile;	//  --> userInfoId = 实际保存user_profile.accountNum
	
	private int signupNum;		//活动的报名人数
	private int signInNum;		//签到人数
	private String signUpAndSignIn; //签到报名人
	private int eventStatus;	//状态标识
	private String nowStatus;   //根据审核、状态、当前时间等各种因素所得的当前活动状态
	private String alumniName;  //校友会活动的发起校友会(通过userId.dept_id --> cy_alumni表)

	private String handlCount;//处理的总数
	private String reportCount; //举报的总数
	private String reportCountAndHandlCount;


	private String picUrl ;		// 图片相对路径

	private String authorityAlumniId ;	 //审核组织编号

	private String isJoined;
	private String qrCodeUrl;
	private String pageView;
	private String associationId;		//社团编号
	private String associationName;		//社团名称
	private String pic_xd ;

	private String needAuth;			//该活动是否需要认证
	private String groupId; //返校计划群ID
	private String groupEasemobId;//环信对应的群ID
	private String groupTitle;//群名称

	private String reasons; //投诉原因
	
	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getPageView() {
		return pageView;
	}

	public void setPageView(String pageView) {
		this.pageView = pageView;
	}

	//	public long getId() {
//		return id;
//	}
//	public void setId(long id) {
//		this.id = id;
//	}
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
	public String getOrganizer() {
		return organizer;
	}
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSignupStartTime() {
		return signupStartTime;
	}

	public void setSignupStartTime(String signupStartTime) {
		this.signupStartTime = signupStartTime;
	}

	public String getSignupEndTime() {
		return signupEndTime;
	}

	public void setSignupEndTime(String signupEndTime) {
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
	public boolean isNeedSignIn() {
		return needSignIn;
	}
	public void setNeedSignIn(boolean needSignIn) {
		this.needSignIn = needSignIn;
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
	public boolean isNeedNotification() {
		return needNotification;
	}
	public void setNeedNotification(boolean needNotification) {
		this.needNotification = needNotification;
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
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRegion() {
		return region;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory() {
		return category;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public void setSignupNum(int signupNum) {
		this.signupNum = signupNum;
	}
	public int getSignupNum() {
		return signupNum;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setNowStatus(String nowStatus) {
		this.nowStatus = nowStatus;
	}
	public String getNowStatus() {
		return nowStatus;
	}
	public void setAlumniName(String alumniName) {
		this.alumniName = alumniName;
	}
	public String getAlumniName() {
		return alumniName;
	}
	
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getDept_name() {
		return dept_name;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getPerAlumniId() {
		return perAlumniId;
	}

	public void setPerAlumniId(String perAlumniId) {
		this.perAlumniId = perAlumniId;
	}

	public String getAuthorityAlumniId() {
		return authorityAlumniId;
	}

	public void setAuthorityAlumniId(String authorityAlumniId) {
		this.authorityAlumniId = authorityAlumniId;
	}

	public int getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(int eventStatus) {
		this.eventStatus = eventStatus;
	}

	public String getIsJoined() {
		return isJoined;
	}

	public void setIsJoined(String isJoined) {
		this.isJoined = isJoined;
	}

	public String getAssociationId() {
		return associationId;
	}

	public void setAssociationId(String associationId) {
		this.associationId = associationId;
	}

	public String getAssociationName() {
		return associationName;
	}

	public void setAssociationName(String associationName) {
		this.associationName = associationName;
	}

	/**
	 * 相对路径
	 * @return
     */
	public String getPicUrl() {
		if(StringUtils.isBlank(picUrl) && StringUtils.isNotBlank(pic) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(pic.indexOf(Global.URL_DOMAIN) == 0) {
				picUrl = pic.substring(Global.URL_DOMAIN.length()) ;
			}else{
				picUrl=pic;
			}
		}
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPic() {
		if(StringUtils.isBlank(pic) && StringUtils.isNotBlank(picUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(picUrl.indexOf("http") < 0) {
				pic = Global.URL_DOMAIN + picUrl ;
			}else{
				pic=picUrl;
			}
		}
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}




	public String getPic_xd() {
		if(StringUtils.isBlank(pic_xd) && StringUtils.isNotBlank(picUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(picUrl.indexOf("http") == 0) {
				pic_xd =picUrl.substring(Global.URL_DOMAIN.length())   ;
			}else{
				pic_xd =  picUrl;
			}
		}
		return pic_xd;
	}

	public void setPic_xd(String pic_xd) {
		this.pic_xd = pic_xd;
	}

	public String getNeedAuth() {
		return needAuth;
	}

	public void setNeedAuth(String needAuth) {
		this.needAuth = needAuth;
	}

	public int getSignInNum() {
		return signInNum;
	}

	public void setSignInNum(int signInNum) {
		this.signInNum = signInNum;
	}

	public String getSignUpAndSignIn() {
		signUpAndSignIn = String.valueOf(signInNum)+"/"+String.valueOf(signupNum);
		return signUpAndSignIn;
	}

	public void setSignUpAndSignIn(String signUpAndSignIn) {
		this.signUpAndSignIn = signUpAndSignIn;
	}

	public String getHandlCount() {
		return handlCount;
	}

	public void setHandlCount(String handlCount) {
		this.handlCount = handlCount;
	}

	public String getReportCount() {
		return reportCount;
	}

	public void setReportCount(String reportCount) {
		this.reportCount = reportCount;
	}

	public String getReportCountAndHandlCount() {

		reportCountAndHandlCount = String.valueOf(handlCount)+"/"+String.valueOf(reportCount);

		return reportCountAndHandlCount;
	}

	public void setReportCountAndHandlCount(String reportCountAndHandlCount) {
		this.reportCountAndHandlCount = reportCountAndHandlCount;
	}

	public String getCostMemo() {
		return costMemo;
	}

	public void setCostMemo(String costMemo) {
		this.costMemo = costMemo;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupEasemobId() {
		return groupEasemobId;
	}

	public void setGroupEasemobId(String groupEasemobId) {
		this.groupEasemobId = groupEasemobId;
	}

	public String getGroupTitle() {
		return groupTitle;
	}

	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
}
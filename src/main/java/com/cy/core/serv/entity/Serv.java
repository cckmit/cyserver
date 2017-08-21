package com.cy.core.serv.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.cy.core.user.entity.User;
import com.cy.core.userProfile.entity.UserProfile;

public class Serv implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7117258764019435507L;
	private long id;
	private String title;  		//标题
	private String content;		//内容
	private String region;		//地域
	private int type;			//性质： 0=官方， 5=校友会， 9=个人
	private String category;	//信息类别（1=互帮互助，2=项目合作，3=求职招聘）
	private long accountNum;	//个人创建人（对应UserProfile.accountNum）
	private long userId;		//官方创建人（对应User.userId）
	private Date createTime;	//创建时间
	private int auditStatus;	//审核状态（0=未审核，1=通过，2=不通过）
	private String auditOpinion;//审核意见
	private long auditUserId;	//审核人（对应User.userId）
	private Date auditTime;		//审核时间
	private int status;			//状态（0=正常，1=投诉处理-信息正常，2=投诉处理-信息违规，3=用户自己删除，4=管理员删除）
	
	private int replyNum;		//回复数量
	private int complaintNum;		//投诉数量
	private List<ServPic> picList;	//所含图片
	
	private User user;					//  --> userId
	private UserProfile userProfile;	//  --> accountNum
	private String alumniName;  //校友会服务的发起校友会(通过userId.dept_id --> cy_alumni表)
	
	private String dept_id;		//所属院系
	private String dept_name;	//所属院系名称
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
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
	public long getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(long accountNum) {
		this.accountNum = accountNum;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getUserId() {
		return userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public int getReplyNum() {
		return replyNum;
	}
	public void setComplaintNum(int complaintNum) {
		this.complaintNum = complaintNum;
	}
	public int getComplaintNum() {
		return complaintNum;
	}
	public void setPicList(List<ServPic> picList) {
		this.picList = picList;
	}
	public List<ServPic> getPicList() {
		return picList;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserProfile getUserProfile() {
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
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

}
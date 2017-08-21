package com.cy.core.contact.entity;

import java.io.Serializable;
import java.util.Date;

import com.cy.core.user.entity.User;
import com.cy.core.userProfile.entity.UserProfile;

public class Contact implements Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2852450445926550453L;
	private long id;
	private String title;  		//标题
	private String content;		//内容
	private String category;	//信息类别（1=联系总会，2=联系学院，3=联系会长）
	private long accountNum;	//个人创建人（对应UserProfile.accountNum）
	private Date createTime;	//创建时间
	private String replyContent;//审核意见
	private long replyUserId;	//审核人（对应User.userId）
	private Date replyTime;		//审核时间
	private int status;			//状态（0=正常，1=用户自己删除，2=管理员删除）
    private String userId;
    private String sex;
    private String replyPicture; //回复图片
    private String replyName; //回复人姓名
    
	public String getAlumniId() {
		return alumniId;
	}

	public void setAlumniId(String alumniId) {
		this.alumniId = alumniId;
	}

	private String alumniId;
	private User user;					//  --> userId
	private UserProfile userProfile;	//  --> accountNum
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getReplyPicture() {
		return replyPicture;
	}

	public void setReplyPicture(String replyPicture) {
		this.replyPicture = replyPicture;
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	
	


}
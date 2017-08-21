package com.cy.core.backschoolOnlineSign.entity;

import com.cy.base.entity.DataEntity;
import com.cy.common.utils.StringUtils;
import com.cy.core.alumnicard.entity.AlumniCardExt;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BackschoolOnlineSign extends DataEntity<BackschoolOnlineSign> {

	/**
	 * 校友卡
	 */
	private static final long serialVersionUID = 1L;

	private String accountNum;
	private String activityId;
	private String deletion;
	private String name;// 姓名
	private String sex;// 性别 0：男， 1：女
	private String birthday;// 生日
	private String location;// 所在地
	private String industry;// 所属行业
	private String unit;// 工作单位
	private String position;// 职务、昵称
	private int status;// 审核状态，0未审核，1通过，2未通过
	private long check;// 审核者
	private Date checkTime;// 审核时间
	private String suggest;// 建议
	private String opinion;// 审核意见
	private String cardNumber;// 卡片编号

	private String contactAddress;// 通讯地址
	private String postCode;// 邮编
	private String officePhone;// 办公电话
	private String homePhone;// 家庭电话
	private String mobilePhone;// 手机号
	private String emailBox;// 邮箱

	private List<AlumniCardExt> cardExtList; // 学位

	private Boolean needVisit;
	private Boolean needLookSchool;
	private Boolean needDriving;
	private String wagonNumber;
	private Boolean needCarryingFamily;

	private String visitPlace;
	private String adultNumber;
	private String childrenNumber;

	public String getVisitPlace() {
		return visitPlace;
	}

	public void setVisitPlace(String visitPlace) {
		this.visitPlace = visitPlace;
	}

	public String getAdultNumber() {
		return adultNumber;
	}

	public void setAdultNumber(String adultNumber) {
		this.adultNumber = adultNumber;
	}

	public String getChildrenNumber() {
		return childrenNumber;
	}

	public void setChildrenNumber(String childrenNumber) {
		this.childrenNumber = childrenNumber;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getCheck() {
		return check;
	}

	public void setCheck(long check) {
		this.check = check;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmailBox() {
		return emailBox;
	}

	public void setEmailBox(String emailBox) {
		this.emailBox = emailBox;
	}

	public List<AlumniCardExt> getCardExtList() {
		return cardExtList;
	}

	public void setCardExtList(List<AlumniCardExt> cardExtList) {
		this.cardExtList = cardExtList;
	}

	public Boolean getNeedVisit() {
		return needVisit;
	}

	public void setNeedVisit(Boolean needVisit) {
		this.needVisit = needVisit;
	}

	public Boolean getNeedLookSchool() {
		return needLookSchool;
	}

	public void setNeedLookSchool(Boolean needLookSchool) {
		this.needLookSchool = needLookSchool;
	}

	public Boolean getNeedDriving() {
		return needDriving;
	}

	public void setNeedDriving(Boolean needDriving) {
		this.needDriving = needDriving;
	}


	public Boolean getNeedCarryingFamily() {
		return needCarryingFamily;
	}

	public void setNeedCarryingFamily(Boolean needCarryingFamily) {
		this.needCarryingFamily = needCarryingFamily;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getDeletion() {
		if(StringUtils.isBlank(deletion)){
			deletion = "0";
		}
		return deletion;
	}

	public void setDeletion(String deletion) {
		this.deletion = deletion;
	}

	public String getWagonNumber() {
		return wagonNumber;
	}

	public void setWagonNumber(String wagonNumber) {
		this.wagonNumber = wagonNumber;
	}
}

package com.cy.core.activity.entity;

import com.cy.base.entity.DataEntity;
import com.cy.core.backschoolOnlineSign.entity.BackschoolOnlineSign;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Activity extends DataEntity<Activity> {

	private String department;
	private String grade;
	private String clazz;
	private String major;
	private Date backStartime;
	private Date backEndtime;
	private int backNumber;
	private String contactPerson;
	private String contactPhone;
	private boolean needMeeting;
	private String meetingArea;
	private Date meetingTime;
	private Date meetingEndTime;
	private int meetingNumber;
	private boolean needProjector;
	private boolean needVisit;
	private String visitPlace;
	private Date visitTime;
	private Date visitEndTime;
	private int visitNumber;
	private boolean needDinner;
	private String dinnerArea;
	private Date dinnerTime;
	private Date dinnerEndTime;
	private int dinnerNumber;
	private double dinnerStandard;
	private boolean needSubscription;
	private String userId;
	private String status;
	private List<BackschoolOnlineSign> signList;  //报名人数

	private String countMember;
	private String opinion;
	private String signStatus;
	private Date checkTime;

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getCountMember() {
		return countMember;
	}

	public void setCountMember(String countMember) {
		this.countMember = countMember;
	}

	public String getStatus() {
		if(StringUtils.isBlank(status)){
			status = "10";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Date getBackEndtime() {
		return backEndtime;
	}

	public void setBackEndtime(Date backEndtime) {
		this.backEndtime = backEndtime;
	}

	public Date getBackStartime() {
		return backStartime;
	}

	public void setBackStartime(Date backStartime) {
		this.backStartime = backStartime;
	}

	public int getBackNumber() {
		return backNumber;
	}

	public void setBackNumber(int backNumber) {
		this.backNumber = backNumber;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public boolean isNeedMeeting() {
		return needMeeting;
	}

	public void setNeedMeeting(boolean needMeeting) {
		this.needMeeting = needMeeting;
	}

	public String getMeetingArea() {
		return meetingArea;
	}

	public void setMeetingArea(String meetingArea) {
		this.meetingArea = meetingArea;
	}

	public Date getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(Date meetingTime) {
		this.meetingTime = meetingTime;
	}

	public int getMeetingNumber() {
		return meetingNumber;
	}

	public void setMeetingNumber(int meetingNumber) {
		this.meetingNumber = meetingNumber;
	}

	public boolean isNeedProjector() {
		return needProjector;
	}

	public void setNeedProjector(boolean needProjector) {
		this.needProjector = needProjector;
	}

	public boolean isNeedVisit() {
		return needVisit;
	}

	public void setNeedVisit(boolean needVisit) {
		this.needVisit = needVisit;
	}

	public String getVisitPlace() {
		return visitPlace;
	}

	public void setVisitPlace(String visitPlace) {
		this.visitPlace = visitPlace;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public int getVisitNumber() {
		return visitNumber;
	}

	public void setVisitNumber(int visitNumber) {
		this.visitNumber = visitNumber;
	}

	public boolean isNeedDinner() {
		return needDinner;
	}

	public void setNeedDinner(boolean needDinner) {
		this.needDinner = needDinner;
	}

	public String getDinnerArea() {
		return dinnerArea;
	}

	public void setDinnerArea(String dinnerArea) {
		this.dinnerArea = dinnerArea;
	}

	public Date getDinnerTime() {
		return dinnerTime;
	}

	public void setDinnerTime(Date dinnerTime) {
		this.dinnerTime = dinnerTime;
	}

	public int getDinnerNumber() {
		return dinnerNumber;
	}

	public void setDinnerNumber(int dinnerNumber) {
		this.dinnerNumber = dinnerNumber;
	}

	public double getDinnerStandard() {
		return dinnerStandard;
	}

	public void setDinnerStandard(double dinnerStandard) {
		this.dinnerStandard = dinnerStandard;
	}

	public boolean isNeedSubscription() {
		return needSubscription;
	}

	public void setNeedSubscription(boolean needSubscription) {
		this.needSubscription = needSubscription;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getMeetingEndTime() {
		return meetingEndTime;
	}

	public void setMeetingEndTime(Date meetingEndTime) {
		this.meetingEndTime = meetingEndTime;
	}

	public Date getVisitEndTime() {
		return visitEndTime;
	}

	public void setVisitEndTime(Date visitEndTime) {
		this.visitEndTime = visitEndTime;
	}

	public Date getDinnerEndTime() {
		return dinnerEndTime;
	}

	public void setDinnerEndTime(Date dinnerEndTime) {
		this.dinnerEndTime = dinnerEndTime;
	}

	public List<BackschoolOnlineSign> getSignList() {
		return signList;
	}

	public void setSignList(List<BackschoolOnlineSign> signList) {
		this.signList = signList;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
}
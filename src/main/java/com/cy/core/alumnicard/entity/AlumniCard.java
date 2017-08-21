package com.cy.core.alumnicard.entity;

import com.cy.base.entity.DataEntity;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AlumniCard extends DataEntity<AlumniCard> {

	/**
	 * 校友卡
	 */
	private static final long serialVersionUID = 1L;

	private String accountNum              ; // 姓名
	private String takeWay                 ; // 取卡方式 0
	private String applyTime               ; // 使用时间
	private int status                     ; // 审核状态，0未审核，1通过，2未通过
	private long checker                   ; // 审核者
	private String checkTime               ; // 审核时间
	private String suggest                 ; // 建议
	private String opinion                 ; // 审核意见
	private String personalPic             ; // 个人照片
	private String deletion                ; // 逻辑删除，0正常，1删除
	private String name                    ; // 姓名
	private String phone                   ; // 联系电话
	private String email                   ; // 联系邮箱
	private String address                 ; // 联系地址
	private String sex                     ; // 性别 0:男,1:女
	private String workUnit                ; // 工作单位
	private String position                ; // 职位
	private String cardNumber              ; // 卡号
	private String payType                 ; // 支付方式
	private int payStatus                  ; // 支付状态
	private String payTime                 ; // 支付时间
	private String credentialsCourier      ; // 快递公司
	private String credentialsCourierNumber; // 快递单号
	private String industry                ; // 所属行业
	private String postalcode              ; // 邮编
	private String officeTel               ; // 公司电话
	private String location                ; // 所在地
	private String homeTel                 ; // 家庭电话
	private String credentialsPic		   ; // 证件照片
	private String birthday				   ; // 生日
	private List<AlumniCardExt> cardExtList; // 学位们


	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getTakeWay() {
		return takeWay;
	}

	public void setTakeWay(String takeWay) {
		this.takeWay = takeWay;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getChecker() {
		return checker;
	}

	public void setChecker(long checker) {
		this.checker = checker;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
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

	public String getPersonalPic() {
		if(StringUtils.isNotBlank(personalPic) && StringUtils.isNotBlank(Global.URL_DOMAIN) && personalPic.indexOf(Global.URL_DOMAIN) == 0) {
			personalPic = personalPic.substring(Global.URL_DOMAIN.length());
			if(!"/".equals(personalPic.substring(0,1))){
				personalPic = "/"+personalPic;
			}
		}
		return personalPic;
	}

	public void setPersonalPic(String personalPic) {
		this.personalPic = personalPic;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getCredentialsCourier() {
		return credentialsCourier;
	}

	public void setCredentialsCourier(String credentialsCourier) {
		this.credentialsCourier = credentialsCourier;
	}

	public String getCredentialsCourierNumber() {
		return credentialsCourierNumber;
	}

	public void setCredentialsCourierNumber(String credentialsCourierNumber) {
		this.credentialsCourierNumber = credentialsCourierNumber;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getCredentialsPic() {
		if(StringUtils.isNotBlank(credentialsPic) && StringUtils.isNotBlank(Global.URL_DOMAIN) && credentialsPic.indexOf(Global.URL_DOMAIN) == 0) {
			credentialsPic = credentialsPic.substring(Global.URL_DOMAIN.length());
			if(!"/".equals(credentialsPic.substring(0,1))){
				credentialsPic = "/"+credentialsPic;
			}
		}
		return credentialsPic;
	}

	public void setCredentialsPic(String credentialsPic) {
		this.credentialsPic = credentialsPic;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public List<AlumniCardExt> getCardExtList() {
		return cardExtList;
	}

	public void setCardExtList(List<AlumniCardExt> cardExtList) {
		this.cardExtList = cardExtList;
	}
}

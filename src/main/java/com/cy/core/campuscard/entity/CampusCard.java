package com.cy.core.campuscard.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class CampusCard implements Serializable {

	/**
	 * 校园卡
	 */
	private static final long serialVersionUID = 1L;

	private long id;// 自增长序号

	private String name;// 姓名
	private String businessScope;// 营业范围
	private String location;// 所在地
	private String industry;// 所属行业
	private String legal;// 法人
	private String unitTel;// 单位联系电话
	private String unitAddress;// 单位所在地
	private String registrationAuthority;// 登记机关
	private String businessLicenseNo;// 营业执照号
	private String takeWay;// 取卡方式
	private Date applyTime;// 申请时间
	private int status;// 审核状态，0未审核，1通过，2未通过
	private long check;// 审核者
	private Date checkTime;// 审核时间
	private String suggest;// 建议
	private String opinion;// 审核意见

	private String discountPreferential;// 折扣优惠
	private String giftPreferential;// 礼品优惠
	private String otherPreferential;// 其它优惠
	private String vipPreferential;// 会员优惠

	private String contact;// 联系人
	private String contactTel;// 联系电话
	private String fax;// 传真
	private String emailBox;// 邮箱

	private String personalPic;// 个人照片
	private String credentialsPic;// 证件照片

	private Date createDate;// 创建日期

	private int deletion;// 逻辑删除，0正常，1删除

	private String cardNumber;	//卡号

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CampusCard [id=<");
		builder.append(id);
		builder.append(">, name=<");
		builder.append(name);
		builder.append(">, businessScope=<");
		builder.append(businessScope);
		builder.append(">, location=<");
		builder.append(location);
		builder.append(">, industry=<");
		builder.append(industry);
		builder.append(">, legal=<");
		builder.append(legal);
		builder.append(">, unitTel=<");
		builder.append(unitTel);
		builder.append(">, unitAddress=<");
		builder.append(unitAddress);
		builder.append(">, registrationAuthority=<");
		builder.append(registrationAuthority);
		builder.append(">, businessLicenseNo=<");
		builder.append(businessLicenseNo);
		builder.append(">, takeWay=<");
		builder.append(takeWay);
		builder.append(">, applyTime=<");
		builder.append(applyTime);
		builder.append(">, status=<");
		builder.append(status);
		builder.append(">, check=<");
		builder.append(check);
		builder.append(">, checkTime=<");
		builder.append(checkTime);
		builder.append(">, suggest=<");
		builder.append(suggest);
		builder.append(">, opinion=<");
		builder.append(opinion);
		builder.append(">, discountPreferential=<");
		builder.append(discountPreferential);
		builder.append(">, giftPreferential=<");
		builder.append(giftPreferential);
		builder.append(">, otherPreferential=<");
		builder.append(otherPreferential);
		builder.append(">, vipPreferential=<");
		builder.append(vipPreferential);
		builder.append(">, contact=<");
		builder.append(contact);
		builder.append(">, contactTel=<");
		builder.append(contactTel);
		builder.append(">, fax=<");
		builder.append(fax);
		builder.append(">, emailBox=<");
		builder.append(emailBox);
		builder.append(">, personalPic=<");
		builder.append(personalPic);
		builder.append(">, credentialsPic=<");
		builder.append(credentialsPic);
		builder.append(">, createDate=<");
		builder.append(createDate);
		builder.append(">, deletion=<");
		builder.append(deletion);
		builder.append(">, cardNumber=<");
		builder.append(cardNumber);
		builder.append("]");
		return builder.toString();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public String getLocation() {
		return location;
	}

	public String getIndustry() {
		return industry;
	}

	public String getLegal() {
		return legal;
	}

	public String getUnitTel() {
		return unitTel;
	}

	public String getUnitAddress() {
		return unitAddress;
	}

	public String getRegistrationAuthority() {
		return registrationAuthority;
	}

	public String getBusinessLicenseNo() {
		return businessLicenseNo;
	}

	public String getTakeWay() {
		return takeWay;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public String getSuggest() {
		return suggest;
	}

	public String getOpinion() {
		return opinion;
	}

	public String getGiftPreferential() {
		return giftPreferential;
	}

	public String getOtherPreferential() {
		return otherPreferential;
	}

	public String getContact() {
		return contact;
	}

	public String getContactTel() {
		return contactTel;
	}

	public String getEmailBox() {
		return emailBox;
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

	public String getCredentialsPic() {
		if(StringUtils.isNotBlank(credentialsPic) && StringUtils.isNotBlank(Global.URL_DOMAIN) && credentialsPic.indexOf(Global.URL_DOMAIN) == 0) {
			credentialsPic = credentialsPic.substring(Global.URL_DOMAIN.length());
			if(!"/".equals(credentialsPic.substring(0,1))){
				credentialsPic = "/"+credentialsPic;
			}
		}
		return credentialsPic;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public void setLegal(String legal) {
		this.legal = legal;
	}

	public void setUnitTel(String unitTel) {
		this.unitTel = unitTel;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	public void setRegistrationAuthority(String registrationAuthority) {
		this.registrationAuthority = registrationAuthority;
	}

	public void setBusinessLicenseNo(String businessLicenseNo) {
		this.businessLicenseNo = businessLicenseNo;
	}

	public void setTakeWay(String takeWay) {
		this.takeWay = takeWay;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public void setGiftPreferential(String giftPreferential) {
		this.giftPreferential = giftPreferential;
	}

	public void setOtherPreferential(String otherPreferential) {
		this.otherPreferential = otherPreferential;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public void setEmailBox(String emailBox) {
		this.emailBox = emailBox;
	}

	public void setPersonalPic(String personalPic) {
		this.personalPic = personalPic;
	}

	public void setCredentialsPic(String credentialsPic) {
		this.credentialsPic = credentialsPic;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getDeletion() {
		return deletion;
	}

	public void setDeletion(int deletion) {
		this.deletion = deletion;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getVipPreferential() {
		return vipPreferential;
	}

	public void setVipPreferential(String vipPreferential) {
		this.vipPreferential = vipPreferential;
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

	public String getDiscountPreferential() {
		return discountPreferential;
	}

	public void setDiscountPreferential(String discountPreferential) {
		this.discountPreferential = discountPreferential;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
}

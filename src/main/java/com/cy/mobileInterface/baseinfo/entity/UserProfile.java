package com.cy.mobileInterface.baseinfo.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accountNum;
	private String password;
	private List<String> baseInfoId;
	private List<String> classmates;
	private String picture;
	private String phoneNum;
	private String sex;
	private String name;
	private String sign;
	private String email;
	private String address;
	private String newPassword;
	private List<String> intrestType;
	private String workUtil;
	private String profession;
	private String hobby;
	private String position;
	private long alumni_id;
	private List<String> channels;
    private String isChangedSex;
    private String birthday;			//生日
	private String pictureUrl;     //上传头像相对路径

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIsChangedSex() {
		return isChangedSex;
	}

	public void setIsChangedSex(String isChangedSex) {
		this.isChangedSex = isChangedSex;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getBaseInfoId() {
		return baseInfoId;
	}

	public void setBaseInfoId(List<String> baseInfoId) {
		this.baseInfoId = baseInfoId;
	}

	public String getPicture() {
		if(StringUtils.isBlank(picture) && StringUtils.isNotBlank(pictureUrl) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(pictureUrl.indexOf("http") < 0) {
				picture = Global.URL_DOMAIN + pictureUrl ;
			}else{
				picture=pictureUrl;
			}
		}
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
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

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getWorkUtil() {
		return workUtil;
	}

	public void setWorkUtil(String workUtil) {
		this.workUtil = workUtil;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public long getAlumni_id() {
		return alumni_id;
	}

	public void setAlumni_id(long alumni_id) {
		this.alumni_id = alumni_id;
	}

	public List<String> getClassmates() {
		return classmates;
	}

	public void setClassmates(List<String> classmates) {
		this.classmates = classmates;
	}

	public List<String> getIntrestType() {
		return intrestType;
	}

	public void setIntrestType(List<String> intrestType) {
		this.intrestType = intrestType;
	}

	public List<String> getChannels() {
		return channels;
	}

	public void setChannels(List<String> channels) {
		this.channels = channels;
	}

	/**
	 * 相对路径
	 * @return
	 */
	public String getPictureUrl() {
		if(StringUtils.isBlank(pictureUrl) && StringUtils.isNotBlank(picture) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(picture.indexOf(Global.URL_DOMAIN) == 0) {
				pictureUrl = picture.substring(Global.URL_DOMAIN.length()) ;
			}else{
				pictureUrl=picture;
			}
		}
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	@Override
	public String toString() {
		return "UserProfile [accountNum=" + accountNum + ", password=" + password + ", baseInfoId=" + baseInfoId + ", classmates=" + classmates + ", picture="
				+ picture + ", phoneNum=" + phoneNum + ", sex=" + sex + ", name=" + name + ", sign=" + sign + ", email=" + email + ", address=" + address
				+ ", newPassword=" + newPassword + ", intrestType=" + intrestType + ", workUtil=" + workUtil + ", profession=" + profession + ", hobby="
				+ hobby + ", position=" + position + ", alumni_id=" + alumni_id + ", channels=" + channels + "]";
	}

}

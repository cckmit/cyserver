package com.cy.core.userProfile.entity;

import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * picture 用户图像地址 accountNum 用户账号 password 用户密码 name 昵称 phoneNum 电话号码 sex 性别
	 * 0 代表男，1代表女 address 通讯地址 sign 签名 intrestType 兴趣类型 channels 选择监听的频道 email
	 * 邮箱 authenticated 0没有认证，1已经认证 baseInfoId 用户在基础信息数据库中的id groupName
	 * 用户所在群信息，逗号隔开
	 */
	private String picture;
	private String accountNum;
	private String password;
	private String name;
	private String phoneNum;
	private String sex;
	private String address;
	private String sign;
	private String intrestType;
	private String channels;
	private String email;
	private String authenticated;
	private String baseInfoId;
	private String newBaseInfoId;
    private String isChangedSex;
    private String birthday;		//	生日

	private String isRead;
	private String date;
	private String pictureUrl;     //上传头像相对路径
	private String memberCount;
	private String picture_xd;
	private String pictureUrl_xd;
	private String isActivated;		//邮件是否激活
	private String isOneKeyAuth;	//是否一键认证创建
	
	private String createTime;
	private String[] groupStud;
	
	
	
	
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIsChangedSex() {
		return isChangedSex;
	}

	public void setIsChangedSex(String isChangedSex) {
		this.isChangedSex = isChangedSex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	private String number;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	/** --新增班级字段-- **/
	private String classes;
	/** --新增工作单位字段-- **/
	private String workUtil;
	/** --新增行业字段-- **/
	private String profession;
	/** --兴趣爱好-- **/
	private String hobby;
	/** --职务-- **/
	private String position;
	
	/**--经度--**/
	private double mu_longitud;
	/**--纬度--**/
	private double mu_latitude;
	/**--定位时间--**/
	private Timestamp gps_time;
	/**--地方校友会字段id--**/
	private long alumni_id;
	private String alumniName;	//校友会名称
	private String status;		//组织成员审核状态
	private String userAlumniId;

	private String currAlumniId;
	

	private String groupName;

	private int authErrNum;	//认证错误次数

	private Date authErrTime;	//认证错误时间

	public String getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(String authenticated) {
		this.authenticated = authenticated;
	}

	public String getBaseInfoId() {
		return baseInfoId;
	}

	public void setBaseInfoId(String baseInfoId) {
		this.baseInfoId = baseInfoId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIntrestType() {
		return intrestType;
	}

	public void setIntrestType(String intrestType) {
		this.intrestType = intrestType;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
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
	public String getNewBaseInfoId() {
		return newBaseInfoId;
	}
	public void setNewBaseInfoId(String newBaseInfoId) {
		this.newBaseInfoId = newBaseInfoId;
	}
	public double getMu_longitud() {
		return mu_longitud;
	}
	public void setMu_longitud(double mu_longitud) {
		this.mu_longitud = mu_longitud;
	}
	public double getMu_latitude() {
		return mu_latitude;
	}
	public void setMu_latitude(double mu_latitude) {
		this.mu_latitude = mu_latitude;
	}
	public Timestamp getGps_time() {
		return gps_time;
	}
	public void setGps_time(Timestamp gps_time) {
		this.gps_time = gps_time;
	}
	public long getAlumni_id() {
		return alumni_id;
	}
	public void setAlumni_id(long alumni_id) {
		this.alumni_id = alumni_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAlumniName() {
		return alumniName;
	}

	public void setAlumniName(String alumniName) {
		this.alumniName = alumniName;
	}

	public String getUserAlumniId() {
		return userAlumniId;
	}

	public void setUserAlumniId(String userAlumniId) {
		this.userAlumniId = userAlumniId;
	}

	public String getCurrAlumniId()  {
		return currAlumniId;
	}

	public void setCurrAlumniId(String currAlumniId) {
		this.currAlumniId = currAlumniId;
	}

	public int getAuthErrNum() {
		return authErrNum;
	}

	public void setAuthErrNum(int authErrNum) {
		this.authErrNum = authErrNum;
	}

	public Date getAuthErrTime() {
		return authErrTime;
	}

	public void setAuthErrTime(Date authErrTime) {
		this.authErrTime = authErrTime;
	}



	/**
	 * 相对路径
	 * @return
	 */
	public String getPictureUrl() {
		if(StringUtils.isBlank(pictureUrl) && StringUtils.isNotBlank(picture) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(picture.indexOf(Global.URL_DOMAIN) == 0) {
				pictureUrl = picture.substring(Global.URL_DOMAIN.length()) ;
				if("/".equals(Global.URL_DOMAIN.substring(Global.URL_DOMAIN.length() - 1, Global.URL_DOMAIN.length()))) {
					pictureUrl = "/" + pictureUrl;
				}
			}else{
				pictureUrl=picture;
			}
		}
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
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

	public String getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(String memberCount) {
		this.memberCount = memberCount;
	}

	public String getPicture_xd() {
		if(StringUtils.isBlank(picture_xd) && StringUtils.isNotBlank(picture) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(picture.indexOf(Global.URL_DOMAIN) == 0) {
				picture_xd = picture.substring(Global.URL_DOMAIN.length()) ;
				if("/".equals(Global.URL_DOMAIN.substring(Global.URL_DOMAIN.length() - 1, Global.URL_DOMAIN.length()))){
					picture_xd = "/" + picture_xd;
				}
			}else{
				picture_xd=picture;
			}
		}
		return picture_xd;
	}

	public void setPicture_xd(String picture_xd) {
		this.picture_xd = picture_xd;
	}

	public String getPictureUrl_xd() {
		if(StringUtils.isBlank(pictureUrl_xd) && StringUtils.isNotBlank(picture) && StringUtils.isNotBlank(Global.URL_DOMAIN)) {
			if(picture.indexOf(Global.URL_DOMAIN) == 0) {
				pictureUrl_xd = picture.substring(Global.URL_DOMAIN.length()) ;
				if("/".equals(Global.URL_DOMAIN.substring(Global.URL_DOMAIN.length() - 1, Global.URL_DOMAIN.length()))){
					pictureUrl_xd = "/" + pictureUrl_xd;
				}
			}else{
				pictureUrl_xd=picture;
			}
		}
		return pictureUrl_xd;
	}

	public void setPictureUrl_xd(String pictureUrl_xd) {

		this.pictureUrl_xd = pictureUrl_xd;
	}

	public String getIsActivated() {
		if(StringUtils.isBlank(isActivated))
			isActivated = "1";

		return isActivated;
	}

	public void setIsActivated(String isActivated) {
		this.isActivated = isActivated;
	}

	public String getIsOneKeyAuth() {
		if(StringUtils.isBlank(isOneKeyAuth))
			isOneKeyAuth = "0";
		return isOneKeyAuth;
	}

	public void setIsOneKeyAuth(String isOneKeyAuth) {
		this.isOneKeyAuth = isOneKeyAuth;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String[] getGroupStud() {
		return groupStud;
	}

	public void setGroupStud(String[] groupStud) {
		this.groupStud = groupStud;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}

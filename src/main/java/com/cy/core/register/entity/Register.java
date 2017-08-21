package com.cy.core.register.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 返校报名实体类
 * 
 * @author Administrator
 * 
 */
public class Register implements Serializable {

	private static final long serialVersionUID = -4044978024629212927L;

	private long id;

	// 以下为用户学生基本信息
	private String name; // 姓名
	private String gender; // 性别
	private Date birthday; // 生日
	private String provice; // 所在地,省份
	private String city; // 所在地（市）
	private String industry;// 行业
	private String work; // 工作单位
	private String post; // 职位
	private String degree; // 学历
	private String number; // 学号
	private String school; // 学校， 机构第一级
	private String department; // 院系， 机构第二级
	private String gradeName; // 年级
	private String className; // 班级

	private String inYear; // 入校年份
	private String major; // 专业
	private String leaveYear; // 离校年份
	private String address; // 通讯地址
	private String zipCode; // 邮编
	private String officePhone; // 办公电话
	private String familyPhone; // 家庭电话
	private String mobile; // 手机
	private String email; // 邮箱

	private String x_name;
	private String x_phone;
	private String x_email;
	private String x_address;
	private String x_school;
	private String x_depart;
	private String x_grade;
	private String x_clazz;
	private String x_sex;
	private String x_major;
	private String x_workUnit;
	private String x_position;

	// 以下为活动信息
	private boolean visitHistoryMuseum; // 是否参观校史馆
	private boolean visitMoneyMuseum; // 是否参观钱币博物馆
	private boolean visitCollege; // 是否游园
	private boolean selfDrive; // 是否自驾
	private String licensePlate; // 车牌
	private boolean withFamily; // 是否有家人随行
	private int adultNumber; // 成人人数
	private int childrenNumber; // 儿童人数
	private String userId; // 关联user_info表

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getProvice() {
		return provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getInYear() {
		return inYear;
	}

	public void setInYear(String inYear) {
		this.inYear = inYear;
	}

	public String getLeaveYear() {
		return leaveYear;
	}

	public void setLeaveYear(String leaveYear) {
		this.leaveYear = leaveYear;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getFamilyPhone() {
		return familyPhone;
	}

	public void setFamilyPhone(String familyPhone) {
		this.familyPhone = familyPhone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isVisitHistoryMuseum() {
		return visitHistoryMuseum;
	}

	public void setVisitHistoryMuseum(boolean visitHistoryMuseum) {
		this.visitHistoryMuseum = visitHistoryMuseum;
	}

	public boolean isVisitMoneyMuseum() {
		return visitMoneyMuseum;
	}

	public void setVisitMoneyMuseum(boolean visitMoneyMuseum) {
		this.visitMoneyMuseum = visitMoneyMuseum;
	}

	public boolean isVisitCollege() {
		return visitCollege;
	}

	public void setVisitCollege(boolean visitCollege) {
		this.visitCollege = visitCollege;
	}

	public boolean isSelfDrive() {
		return selfDrive;
	}

	public void setSelfDrive(boolean selfDrive) {
		this.selfDrive = selfDrive;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public boolean isWithFamily() {
		return withFamily;
	}

	public void setWithFamily(boolean withFamily) {
		this.withFamily = withFamily;
	}

	public int getAdultNumber() {
		return adultNumber;
	}

	public void setAdultNumber(int adultNumber) {
		this.adultNumber = adultNumber;
	}

	public int getChildrenNumber() {
		return childrenNumber;
	}

	public void setChildrenNumber(int childrenNumber) {
		this.childrenNumber = childrenNumber;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getX_name() {
		return x_name;
	}

	public void setX_name(String x_name) {
		this.x_name = x_name;
	}

	public String getX_phone() {
		return x_phone;
	}

	public void setX_phone(String x_phone) {
		this.x_phone = x_phone;
	}

	public String getX_email() {
		return x_email;
	}

	public void setX_email(String x_email) {
		this.x_email = x_email;
	}

	public String getX_address() {
		return x_address;
	}

	public void setX_address(String x_address) {
		this.x_address = x_address;
	}

	public String getX_school() {
		return x_school;
	}

	public void setX_school(String x_school) {
		this.x_school = x_school;
	}

	public String getX_depart() {
		return x_depart;
	}

	public void setX_depart(String x_depart) {
		this.x_depart = x_depart;
	}

	public String getX_grade() {
		return x_grade;
	}

	public void setX_grade(String x_grade) {
		this.x_grade = x_grade;
	}

	public String getX_clazz() {
		return x_clazz;
	}

	public void setX_clazz(String x_clazz) {
		this.x_clazz = x_clazz;
	}

	public String getX_sex() {
		return x_sex;
	}

	public void setX_sex(String x_sex) {
		this.x_sex = x_sex;
	}

	public String getX_major() {
		return x_major;
	}

	public void setX_major(String x_major) {
		this.x_major = x_major;
	}

	public String getX_workUnit() {
		return x_workUnit;
	}

	public void setX_workUnit(String x_workUnit) {
		this.x_workUnit = x_workUnit;
	}

	public String getX_position() {
		return x_position;
	}

	public void setX_position(String x_position) {
		this.x_position = x_position;
	}

}

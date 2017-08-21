package com.cy.core.docount.entity;

import java.io.Serializable;


public class DoCount implements Serializable
{
	private static final long serialVersionUID = 1L;

	
	private int countUsers;
	
	private String school;
	
	private String deptId;
	
	private String fullName;
	
	private String deptName;
	
	private String className;
	
	private String ageName;
	
	private String collegeName;
	
	private String provinceId;
	
	private String provinceName;
	
	private String birthDate;//出生年份
	
	private String sex;//性别
	
	private String location;//所在地
	
	private String joinYear;//入校年份
	private String outYear;//离校年份
	
	private String department;//所在院系
	private String specialty;//专业
	
	private String background;//学历层次
	
	private String countType;
	
	private long majorId;
	


	@Override
	public String toString() {
		return "DoCount [countUsers=" + countUsers + ", school=" + school
				+ ", deptId=" + deptId + ", fullName=" + fullName
				+ ", deptName=" + deptName + ", className=" + className
				+ ", ageName=" + ageName + ", collegeName=" + collegeName
				+ ", provinceId=" + provinceId + ", provinceName="
				+ provinceName + ", birthDate=" + birthDate + ", sex=" + sex
				+ ", location=" + location + ", joinYear=" + joinYear
				+ ", outYear=" + outYear + ", department=" + department
				+ ", specialty=" + specialty + ", background=" + background
				+ ", countType=" + countType + ", majorId=" + majorId + "]";
	}

	public long getMajorId() {
		return majorId;
	}

	public void setMajorId(long majorId) {
		this.majorId = majorId;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCountType() {
		return countType;
	}

	public void setCountType(String countType) {
		this.countType = countType;
	}


	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getJoinYear() {
		return joinYear;
	}

	public void setJoinYear(String joinYear) {
		this.joinYear = joinYear;
	}

	public String getOutYear() {
		return outYear;
	}

	public void setOutYear(String outYear) {
		this.outYear = outYear;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getAgeName() {
		return ageName;
	}

	public void setAgeName(String ageName) {
		this.ageName = ageName;
	}

	
	
	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public int getCountUsers() {
		return countUsers;
	}

	public void setCountUsers(int countUsers) {
		this.countUsers = countUsers;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	

}

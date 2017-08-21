package com.cy.core.majormng.entity;

import java.io.Serializable;
import java.util.Date;


public class MajorMng implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6881910635904070933L;
	
	private long id;
	private String schoolName;
	private String facultyName;
	private String gradeName;
	private String className;
	private String specialtyName;
	private long accountNum;
	private Date createDate;//创建时间
	
	
	@Override
	public String toString() {
		return "MajorMng [id=" + id + ", schoolName=" + schoolName
				+ ", facultyName=" + facultyName + ", gradeName=" + gradeName
				+ ", className=" + className + ", specialtyName="
				+ specialtyName + ", accountNum=" + accountNum
				+ ", createDate=" + createDate + "]";
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
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
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	public long getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(long accountNum) {
		this.accountNum = accountNum;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
}

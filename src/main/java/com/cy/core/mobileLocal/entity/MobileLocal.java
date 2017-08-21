package com.cy.core.mobileLocal.entity;

import java.io.Serializable;

public class MobileLocal implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String mobileNumber;
	private String mobileArea;
	private String mobileType;
	private String areaCode;
	private String postCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getMobileArea() {
		return mobileArea;
	}

	public void setMobileArea(String mobileArea) {
		this.mobileArea = mobileArea;
	}

	public String getMobileType() {
		return mobileType;
	}

	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

}

package com.cy.core.donation.entity;

import java.io.Serializable;
import java.util.Date;

public class DonateHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String donateUrl;
	private String projectName;
	private double money;
	private int payStatus;
	private Date confirmTime;

	public String getDonateUrl() {
		return donateUrl;
	}

	public void setDonateUrl(String donateUrl) {
		this.donateUrl = donateUrl;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

}

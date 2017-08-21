package com.cy.core.donation.entity;

import java.io.Serializable;

public class NewDonateItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String donateItemUrl;
	private String projectName;
	private String x_name;
	private double money;

	public String getDonateItemUrl() {
		return donateItemUrl;
	}

	public void setDonateItemUrl(String donateItemUrl) {
		this.donateItemUrl = donateItemUrl;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getX_name() {
		return x_name;
	}

	public void setX_name(String x_name) {
		this.x_name = x_name;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

}

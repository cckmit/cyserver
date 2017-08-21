package com.cy.mobileInterface.around.entity;

import java.io.Serializable;

public class Around implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accountNum;
	private String password;
	private double mu_longitud;
	private double mu_latitude;
	private int radius;

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

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

}

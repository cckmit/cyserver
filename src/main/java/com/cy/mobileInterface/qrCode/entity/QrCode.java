package com.cy.mobileInterface.qrCode.entity;

import java.io.Serializable;

public class QrCode implements Serializable {

	private static final long serialVersionUID = 1L;

	private String accountNum;

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

}

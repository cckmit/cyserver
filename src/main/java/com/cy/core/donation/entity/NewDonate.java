package com.cy.core.donation.entity;

import java.io.Serializable;
import java.util.List;

public class NewDonate implements Serializable {
	private static final long serialVersionUID = 1L;
	private long countDonateList;
	private List<NewDonateItem> newDonateList;

	public long getCountDonateList() {
		return countDonateList;
	}

	public void setCountDonateList(long countDonateList) {
		this.countDonateList = countDonateList;
	}

	public List<NewDonateItem> getNewDonateList() {
		return newDonateList;
	}

	public void setNewDonateList(List<NewDonateItem> newDonateList) {
		this.newDonateList = newDonateList;
	}

}

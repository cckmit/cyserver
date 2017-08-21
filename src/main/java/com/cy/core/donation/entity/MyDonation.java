package com.cy.core.donation.entity;

import java.io.Serializable;
import java.util.List;

public class MyDonation implements Serializable {

	private static final long serialVersionUID = 1L;
	private long countDonateHistory;
	private List<DonateHistory> donateHistoryList;

	public long getCountDonateHistory() {
		return countDonateHistory;
	}

	public void setCountDonateHistory(long countDonateHistory) {
		this.countDonateHistory = countDonateHistory;
	}

	public List<DonateHistory> getDonateHistoryList() {
		return donateHistoryList;
	}

	public void setDonateHistoryList(List<DonateHistory> donateHistoryList) {
		this.donateHistoryList = donateHistoryList;
	}

}

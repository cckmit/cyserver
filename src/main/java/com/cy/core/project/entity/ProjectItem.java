package com.cy.core.project.entity;

import java.io.Serializable;
import java.util.List;

public class ProjectItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private long countDonateItem;
	private List<Item> donateItemList;

	public long getCountDonateItem() {
		return countDonateItem;
	}

	public void setCountDonateItem(long countDonateItem) {
		this.countDonateItem = countDonateItem;
	}

	public List<Item> getDonateItemList() {
		return donateItemList;
	}

	public void setDonateItemList(List<Item> donateItemList) {
		this.donateItemList = donateItemList;
	}

}

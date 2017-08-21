package com.cy.core.campuscard.entity;

import java.io.Serializable;

public class CampusCardStatistical implements Serializable{

	/**
	 * 校园卡统计
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String itemName;//项目名称
	private String industry;//所属行业
	private long results;//统计结果
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CampusCardStatistical [itemName=<");
		builder.append(itemName);
		builder.append(">, industry=<");
		builder.append(industry);
		builder.append(">, results=<");
		builder.append(results);
		builder.append("]");
		return builder.toString();
	}

	public String getItemName() {
		return itemName;
	}

	public long getResults() {
		return results;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setResults(long results) {
		this.results = results;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	
	
}

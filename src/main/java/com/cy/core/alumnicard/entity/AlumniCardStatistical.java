package com.cy.core.alumnicard.entity;

import java.io.Serializable;

public class AlumniCardStatistical implements Serializable{

	/**
	 * 校友卡统计
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private String itemName;//项目名称
	private long results;//统计结果
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AlumniCardStatistical [itemName=<");
		builder.append(itemName);
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
	
	
	
}

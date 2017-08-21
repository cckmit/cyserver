package com.cy.core.mobserv.entity;

import java.io.Serializable;

public class CyServPraise extends CyServExtension implements Serializable{

	/**
	 * 
	 * 帮帮忙点赞表
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyServPraise [getId()=<");
		builder.append(getId());
		builder.append(">, getServiceId()=<");
		builder.append(getServiceId());
		builder.append(">, getAccountNum()=<");
		builder.append(getAccountNum());
		builder.append(">, getCreateTime()=<");
		builder.append(getCreateTime());
		builder.append(">]");
		return builder.toString();
	}

}

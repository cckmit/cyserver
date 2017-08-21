package com.cy.core.mobserv.entity;

import java.io.Serializable;

public class CyServComplaint extends CyServExtension implements Serializable{

	/**
	 * 帮帮忙举报表
	 */
	private static final long serialVersionUID = 1L;
	
	private String reason; //'举报理由'

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyServComplaint [reason=<");
		builder.append(reason);
		builder.append(">, getId()=<");
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

package com.cy.core.mobserv.entity;

import java.io.Serializable;

public class CyServPic extends CyServExtension implements Serializable{

	/**
	 * 
	 * 帮帮忙图片表
	 */
	private static final long serialVersionUID = 1L;
	
	private String pic; //'图片地址'
	
	private String thumbnail;//花絮缩略图地址
	
	private String xemanhdep;//花絮高清图地址
	
	private String picStr;//花絮图片说明

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getXemanhdep() {
		return xemanhdep;
	}

	public void setXemanhdep(String xemanhdep) {
		this.xemanhdep = xemanhdep;
	}

	public String getPicStr() {
		return picStr;
	}

	public void setPicStr(String picStr) {
		this.picStr = picStr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CyServPic [pic=");
		builder.append(pic);
		builder.append(", thumbnail=");
		builder.append(thumbnail);
		builder.append(", xemanhdep=");
		builder.append(xemanhdep);
		builder.append(", picStr=");
		builder.append(picStr);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getServiceId()=");
		builder.append(getServiceId());
		builder.append("]");
		return builder.toString();
	}

	
	
}

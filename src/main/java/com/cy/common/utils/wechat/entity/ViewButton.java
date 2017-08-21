package com.cy.common.utils.wechat.entity;

public class ViewButton extends ComplexButton{
	private String type;
	private String url;
	 
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ViewButton{" +
				"type='" + type + '\'' +
				", url='" + url + '\'' +
				'}';
	}
}

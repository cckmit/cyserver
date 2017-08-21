package com.cy.util.msgPushUtil;

public class Channel {
	private int channelId2;
	private String channelName;//频道名称
	private String channelIcon;
	private String channelRemark;//信道介绍
	private String channelId;
	
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getChannelRemark() {
		return channelRemark;
	}
	public void setChannelRemark(String channelRemark) {
		this.channelRemark = channelRemark;
	}
	public int getChannelId2() {
		return channelId2;
	}
	public void setChannelId2(int channelId2) {
		this.channelId2 = channelId2;
	}
	public String getChannelIcon() {
		return channelIcon;
	}
	public void setChannelIcon(String channelIcon) {
		this.channelIcon = channelIcon;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}

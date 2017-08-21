package com.cy.util.jms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PushedMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String PMId;// 推送消息id
	private String icon;
	private String channelName;// 频道名称
	private String newsSummary;// 头条新闻的摘要
	private Date time;
	private List<SingleNewsMessage> newsList;// 存放新闻的list
	private String channelId;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getNewsSummary() {
		return newsSummary;
	}

	public void setNewsSummary(String newsSummary) {
		this.newsSummary = newsSummary;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public List<SingleNewsMessage> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<SingleNewsMessage> newsList) {
		this.newsList = newsList;
	}

	public String getPMId() {
		return PMId;
	}

	public void setPMId(String pMId) {
		PMId = pMId;
	}

}

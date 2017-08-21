package com.cy.core.channel.entity;

import java.io.Serializable;

public class NewsChannel implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int channelId;
	private String channelName;// 频道名称
	private String channelRemark;// 信道介绍
	private String channelIcon;

	private String subscribe ;	// 0:未订阅；1：已订阅
	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}



	public String getChannelRemark()
	{
		return channelRemark;
	}

	public void setChannelRemark(String channelRemark)
	{
		this.channelRemark = channelRemark;
	}

	public int getChannelId()
	{
		return channelId;
	}

	public void setChannelId(int channelId)
	{
		this.channelId = channelId;
	}

	public String getChannelName()
	{
		return channelName;
	}

	public void setChannelName(String channelName)
	{
		this.channelName = channelName;
	}

	public String getChannelIcon()
	{
		return channelIcon;
	}

	public void setChannelIcon(String channelIcon)
	{
		this.channelIcon = channelIcon;
	}

}

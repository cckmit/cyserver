package com.cy.util.jms;

import java.util.ArrayList;
import java.util.List;

public class ChannelList {
	private List<Channel> channelList;

	public ChannelList() {
		this.channelList = new ArrayList<Channel>();
	}

	public void add(Channel e) {
		channelList.add(e);
	}

	public ChannelList(List<Channel> list) {
		this.channelList = list;
	}

	public List<Channel> getList() {
		return channelList;
	}

	public void setList(List<Channel> list) {
		this.channelList = list;
	}

}

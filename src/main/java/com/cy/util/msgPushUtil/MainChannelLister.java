package com.cy.util.msgPushUtil;
import javax.jms.Message;
import javax.jms.MessageListener;


// 所有的客户端都应该监听通用通道
	public class MainChannelLister implements MessageListener {
		private MsgPushClient client;

		public MainChannelLister(MsgPushClient client) {
			super();
			this.client = client;
		}

		public void onMessage(Message msg) {
			this.client.updateChannelsData(msg);
		}

	}
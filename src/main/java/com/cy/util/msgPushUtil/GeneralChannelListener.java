package com.cy.util.msgPushUtil;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

// 监听某个信道上的推送信息
public class GeneralChannelListener implements MessageListener {
		private String channelName;

		public GeneralChannelListener(String channelName) {
			super();
			this.channelName = channelName;
		}

		public void onMessage(Message message) {
			TextMessage txtMsg = (TextMessage) message;
			String msg;
			try {
				msg = txtMsg.getText();
//				System.out.println("onMessage receive msg : " + msg);
			} catch (JMSException e) {
				e.printStackTrace();
			}			
		}
	}
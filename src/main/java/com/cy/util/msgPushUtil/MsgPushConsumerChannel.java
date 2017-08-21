package com.cy.util.msgPushUtil;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

// 信道定义
public class MsgPushConsumerChannel {
	private Channel channel;// 信道id用于连接JMS服务器

	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Destination destination;
	private MessageConsumer consumer;
	// 是否持久化消息
	private boolean isPersistent = DEFAULT_IS_PERSISTENT;
	public final static boolean DEFAULT_IS_PERSISTENT = true;

	public MsgPushConsumerChannel(ConnectionFactory connectionFactory,
			String channelName) {
		this.connectionFactory = connectionFactory;
		this.channel = new Channel();
		this.channel.setChannelName(channelName);
		this.connection = null;
		this.session = null;
		this.destination = null;
		this.consumer = null;
	}

	public MsgPushConsumerChannel(ConnectionFactory connectionFactory, Channel channel) {
		this.connectionFactory = connectionFactory;
		this.channel = channel;
		this.connection = null;
		this.session = null;
		this.destination = null;
		this.consumer = null;
	}

	// 创建一个会话
	public void startSession() {
//		System.out
//		.println("start session : " + this.channel.getChannelName());
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);

				destination = session.createTopic(this.channel.getChannelName());
				consumer = session.createConsumer(destination);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	// 关闭Session
	public void closeSession() {
		try {
//			System.out
//					.println("close session : " + this.channel.getChannelName());
			if (connection != null) {
				connection.close();
				connection = null;
			}
			if (session != null) {
				session.close();
				session = null;
			}

			if (consumer != null) {
				consumer.close();
				consumer = null;
			}

			destination = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 设置该信道的监听器
	public void setMessageListener(MessageListener listener) {
		try {
			if (this.consumer != null) {
				this.consumer.setMessageListener(listener);
			} else {
				System.out.println("Session not started");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getChannelName() {
		return channel.getChannelName();
	}

	public void setChannelName(String channelName) {
		this.channel.setChannelName(channelName);
	}

	public String getChannelId() {
		return channel.getChannelId();
	}
	public void setChannelId(String channelId) {
		this.channel.setChannelId(channelId);
	}
	public int getChannelId2() {
		return channel.getChannelId2();
	}
	public void setChannelId2(int channelId2) {
		this.channel.setChannelId2(channelId2);
	}
	public String getIcon() {
		return this.channel.getChannelIcon();
	}

	public void setIcon(String channelIcon) {
		this.channel.setChannelIcon(channelIcon);
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
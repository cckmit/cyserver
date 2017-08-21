package com.cy.util.msgPushUtil;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

// 信道定义
public class MsgPushProducerChannel {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MsgPushProducerChannel.class);

	private Channel channel;// 信道id用于连接JMS服务器

	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Destination destination;
	private MessageProducer producer;

	// 是否持久化消息
	private boolean isPersistent = DEFAULT_IS_PERSISTENT;
	public final static boolean DEFAULT_IS_PERSISTENT = true;

	public MsgPushProducerChannel(ConnectionFactory connectionFactory,
			String channelName) {
		this.connectionFactory = connectionFactory;
		this.channel = new Channel();
		this.channel.setChannelName(channelName);
		this.connection = null;
		this.session = null;
		this.destination = null;
		this.producer = null;
	}

	public MsgPushProducerChannel(ConnectionFactory connectionFactory,
			Channel channel) {
		this.connectionFactory = connectionFactory;
		this.channel = channel;
		this.connection = null;
		this.session = null;
		this.destination = null;
		this.producer = null;
	}

	// 创建一个会话
	public void start() {
//		System.out.println("start session : " + this.channel.getChannelName());
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);

			destination = session.createTopic(this.channel.getChannelName());
			producer = session.createProducer(destination);
			producer.setDeliveryMode(this.isPersistent ? DeliveryMode.PERSISTENT
					: DeliveryMode.NON_PERSISTENT);
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error(e, e);
		}
	}

	// 关闭Session
	public void close() {
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

			if (producer != null) {
				producer.close();
				producer = null;
			}

			destination = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send(Message msg) {
		try {
			producer.send(msg);
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error(e, e);
		}
	}

	// 发送文本信息
	public void sendTextMessage(String msg) {
//		System.out.println("sendTextMessage msg : " + msg.toString());
		
		try {
			TextMessage message = session.createTextMessage(msg);
			producer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error(e, e);
		}
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public String getChannelId() {
		return channel.getChannelId();
	}
	public void setChannelId(String channelId) {
		this.channel.setChannelId(channelId);
	}
	public int getChannelId2() {
		return this.channel.getChannelId2();
	}
	public void setChannelId2(int channelId2) {
		this.channel.setChannelId2(channelId2);
	}

	public String getChannelName() {
		return channel.getChannelName();
	}

	public void setChannelName(String channelName) {
		this.channel.setChannelName(channelName);
	}

	public String getChannelIcon() {
		return this.channel.getChannelIcon();
	}

	public void setChannelIcon(String channelIcon) {
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

	public MessageProducer getProducer() {
		return producer;
	}

	public void setProducer(MessageProducer producer) {
		this.producer = producer;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
package com.cy.util.jms;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.alibaba.fastjson.JSON;
import com.cy.system.Global;

public class MsgPushServer {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MsgPushServer.class);

	private static MsgPushServer msgPushServer;

	private ConnectionFactory connectionFactory;

	private MsgPushServer() {
		connectionFactory = new ActiveMQConnectionFactory(
				Global.activemq_connection_admin_user,
				Global.activemq_connection_admin_password, "tcp://"
						+ Global.activemq_server_ip + ":"
						+ Global.activemq_server_port
						+ "?wireFormat.maxInactivityDurationInitalDelay=30000");
	}

	public synchronized static MsgPushServer getInstance() {
		if (msgPushServer == null) {
			msgPushServer = new MsgPushServer();
		}
		return msgPushServer;
	}

	private void sendTextMessage(String topicName, String msg) {
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createTopic(topicName);
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			TextMessage message = session.createTextMessage(msg);
			producer.send(message);
		} catch (JMSException e) {
			logger.error(e, e);
		} finally {
			try {
				if (producer != null) {
					producer.close();
					producer = null;
				}
				if (session != null) {
					session.close();
					session = null;

				}
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (JMSException e) {
				logger.error(e, e);
			}
		}
	}

	/**
	 * 广播信道更新信息
	 * 
	 * @param channel
	 */
	public void broadcastChannelUpdateMessage(List<Channel> channel) {
		sendTextMessage(Global.activemq_server_general_channel_name,
				JSON.toJSONString(channel));
	}

	/**
	 * 新闻消息推送
	 * 
	 * @param pushedMessage
	 */
	public void sendPushedMessage(PushedMessage pushedMessage) {
		String channelName = pushedMessage.getChannelName();
		if (channelName == null || channelName.equals("")) {
			logger.error("channel name is null error");
			return;
		}
		// 设置javabean中日期转换时的格式
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));

		JSONObject jsonObject = JSONObject
				.fromObject(pushedMessage, jsonConfig);

		sendTextMessage(channelName, jsonObject.toString());
	}

}

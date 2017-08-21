package com.cy.util.msgPushUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MsgPushClient {
	public final static String TAOLIHUI_GENERAL_CHANNEL_NAME = "GeneralChannelName";
	// 是否持久化消息
	private boolean isPersistent = DEFAULT_IS_PERSISTENT;
	public final static boolean DEFAULT_IS_PERSISTENT = true;
	private ConnectionFactory connectionFactory;
	private String ip;
	private String port;
	private String user;
	private String passwd;
	private MsgPushConsumerChannel mainChannel;
	private HashMap<String, MsgPushConsumerChannel> channels;

	public MsgPushClient(String ip, String port) {
		this.ip = ip;
		this.port = port;
		user = "system_hust";//ActiveMQConnection.DEFAULT_USER;
		passwd = "manager_hust";//ActiveMQConnection.DEFAULT_PASSWORD;
//		user = ActiveMQConnection.DEFAULT_USER;
//		passwd = ActiveMQConnection.DEFAULT_PASSWORD;
		connectionFactory = new ActiveMQConnectionFactory(user, passwd,
				"tcp://" + ip + ":" + port);// url
											// "tcp://219.140.177.108:61616"
		mainChannel = new MsgPushConsumerChannel(connectionFactory,
				TAOLIHUI_GENERAL_CHANNEL_NAME);
		mainChannel.startSession();
		mainChannel.setMessageListener(new MainChannelLister(this));
		
		channels = new HashMap<String, MsgPushConsumerChannel>();
	}

	// 创建一个普通信道
	public MsgPushConsumerChannel createChannel(String channelName) {
		MsgPushConsumerChannel channel = new MsgPushConsumerChannel(
				connectionFactory, channelName);
		channel.startSession();
		// 设置消息监听器
		channel.setMessageListener(new GeneralChannelListener(channelName));
		return channel;
	}

	// 有信道更新信息到来
	public void updateChannelsData(Message message) {
		System.out.println("get update channels msg : " + message.toString());
		if (message instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) message;
			String msg;
			try {
				msg = txtMsg.getText();
				System.out.println("receive msg : " + msg);
				JSONObject jsonObject = JSONObject.fromObject(msg);
				JSONArray jsonArray = jsonObject.getJSONArray("list");
				for (int i = 0; i < jsonArray.size(); ++i) {
					String channelName = (String) jsonArray.getJSONObject(i).get(
							"channelName");
					System.out.println("get channle name : " + channelName);

					String icon = (String) jsonArray.getJSONObject(i).get(
							"channelIcon");
					String iconurl = "";
					try {
						iconurl = URLDecoder.decode(icon, "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					System.out.println("get channle icon : " + iconurl);
					System.out.println("get channle id : "
							+ jsonArray.getJSONObject(i).get("channelId"));

					MsgPushConsumerChannel channel = createChannel(channelName);
					channels.put(channelName, channel);
					channel.setMessageListener(new GeneralChannelListener(
							channelName));
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}

			System.out.println();
			System.out.println();
		}
	}

//	public static void main(String[] args) {
//		MsgPushClient client = new MsgPushClient("172.16.1.244", "61616");
//		MsgPushClient client = new MsgPushClient("219.140.177.108", "61616");

//		BufferedReader lineOfText = new BufferedReader(new InputStreamReader(
//				System.in));
//
//		 String channelID = "母校新闻";
//		 client.createChannel(channelID);
//		 String channelID1 = "总会快递";
//		 client.createChannel(channelID1);
//		 String channelID2 = "时代之音";
//		 client.createChannel(channelID2);
//		try {
//			String textLine = "";
//			while (textLine.equals("quit") == false) {
//				textLine = lineOfText.readLine();
//			}
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//	}
}
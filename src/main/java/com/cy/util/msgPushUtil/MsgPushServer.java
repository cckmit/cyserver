package com.cy.util.msgPushUtil;

import java.util.Date;

import javax.jms.ConnectionFactory;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import com.cy.system.Global;

public class MsgPushServer {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MsgPushServer.class);

//	public final static String TAOLIHUI_GENERAL_CHANNEL_NAME = "GeneralChannelName";
	// 是否持久化消息
	private boolean isPersistent = DEFAULT_IS_PERSISTENT;
	public final static boolean DEFAULT_IS_PERSISTENT = true;
	private ConnectionFactory connectionFactory;
	private String ip;
	private int port;
	private String user;
	private String passwd;

	static class MsgPushServerSingletonHolder {

//		static MsgPushServer instance = new MsgPushServer("172.16.1.244",
//				"61616");
//		static MsgPushServer instance = new MsgPushServer("219.140.177.108",
//				"61616");
		static MsgPushServer instance = null;

	}

	public static MsgPushServer getInstance() {

		if (MsgPushServerSingletonHolder.instance == null) {
			MsgPushServerSingletonHolder.instance = new MsgPushServer(Global.activemq_server_ip,
					Global.activemq_server_port);
		}
		
		return MsgPushServerSingletonHolder.instance;
	}

	private MsgPushServer(String ip, int port) {
		this.ip = ip;
		this.port = port;
//		user = "system_hust";//ActiveMQConnection.DEFAULT_USER;
//		passwd = "manager_hust";//ActiveMQConnection.DEFAULT_PASSWORD;
		user = Global.activemq_connection_admin_user;
		passwd = Global.activemq_connection_admin_password;
		connectionFactory = new ActiveMQConnectionFactory(user, passwd,
				"tcp://" + ip + ":" + port+"?wireFormat.maxInactivityDurationInitalDelay=30000");// url
											// "tcp://219.140.177.108:61616"
	}

	// 广播信道更新信息
	public void broadcastChannelUpdateMessage(ChannelList channel) {
		// 获取信道最新信息
		JSONObject jsonArray = JSONObject.fromObject(channel);
		MsgPushProducerChannel msgPushProducerChannel = createChannel(Global.activemq_server_general_channel_name);
		msgPushProducerChannel.start();
		msgPushProducerChannel.sendTextMessage(jsonArray.toString());
		msgPushProducerChannel.close();
	}

	// 对外接口，将POJO转换为JSON格式，创建出相应信道，创建一个普通信道
	public MsgPushProducerChannel createChannel(Channel channel) {
		MsgPushProducerChannel msgPushChannel = new MsgPushProducerChannel(
				connectionFactory, channel);
		return msgPushChannel;
	}

	public MsgPushProducerChannel createChannel(String channelName) {
		Channel channel = new Channel();
		channel.setChannelName(channelName);
		MsgPushProducerChannel msgPushChannel = new MsgPushProducerChannel(
				connectionFactory, channel);
		return msgPushChannel;
	}

	public void sendPushedMessage(PushedMessage pushedMessage) {
		String channelName = pushedMessage.getChannelName();
		if (channelName == null || channelName.equals("")) {
			logger.error("channel name null error " );
			return;
		}
		// 设置javabean中日期转换时的格式
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));

		JSONObject jsonObject = JSONObject
				.fromObject(pushedMessage, jsonConfig);
		
		Channel channel = new Channel();
		channel.setChannelName(channelName);	
		MsgPushProducerChannel msgPushProducerChannel = createChannel(channel);
		msgPushProducerChannel.start();
		msgPushProducerChannel.sendTextMessage(jsonObject.toString());
		msgPushProducerChannel.close();
	}

//	public static void main(String[] args) {
//
//		MsgPushServer server = MsgPushServer.getInstance();
//		BufferedReader lineOfText = new BufferedReader(new InputStreamReader(
//				System.in));
//		String line = "";
//
//		// test create Channel and send message
//		String channelName = "华工科技";
//		Channel channel1 = new Channel();
//		channel1.setChannelName(channelName);
//		String URL = "http://taolihui.com/icon/2";
//		String ENCODING = "utf-8";
//		String strURL = "";
//		try {
//			strURL = URLEncoder.encode(URL, ENCODING).replace("*", "*")
//					.replace("~", "~").replace("+", " ");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//
//		channel1.setChannelIcon(strURL);
//		server.createChannel(channel1);
//
//		ChannelList channelList = new ChannelList();
//		channelList.add(channel1);
//		server.broadcastChannelUpdateMessage(channelList);
//
//		PushedMessage pushedMessage = new PushedMessage();
//		pushedMessage.setChannelName(channelName);
//		String URL2 = "http://taolihui.com/icon/23";
//		String strURL2 = "";
//		try {
//			strURL2 = URLEncoder.encode(URL2, ENCODING).replace("*", "*")
//					.replace("~", "~").replace("+", " ");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		pushedMessage.setIcon(strURL2);
//		pushedMessage.setNewsSummary("主席来我校视察，赞扬窗友项目!");
//		pushedMessage.setPMId("2345");
//		pushedMessage.setTime(new Date());
//
//		List<SingleNewsMessage> list = new ArrayList<SingleNewsMessage>();
//		SingleNewsMessage smsg = new SingleNewsMessage();
//		smsg.setChannelName(channelName);
//		String URL3 = "http://taolihui.com/icon/2341";
//		String strURL3 = "";
//		try {
//			strURL3 = URLEncoder.encode(URL3, ENCODING).replace("*", "*")
//					.replace("~", "~").replace("+", " ");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		smsg.setIcon(strURL3);
//
//		String URL4 = "http://taolihui.com/icon/2341";
//		String strURL4 = "";
//		try {
//			strURL4 = URLEncoder.encode(URL4, ENCODING).replace("*", "*")
//					.replace("~", "~").replace("+", " ");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		smsg.setNewsUrl(strURL4);
//		smsg.setNid(2);
//		smsg.setPMId("12");
//		smsg.setTime(new Date());
//		smsg.setTitle("华科排名全国第一");
//		list.add(smsg);
//		smsg.setNid(3);
//		list.add(smsg);
//		smsg.setNid(4);
//		list.add(smsg);
//		smsg.setNid(5);
//		list.add(smsg);
//		pushedMessage.setNewsList(list);
//		while (line.equals("quit") == false) {
//			try {
//				server.sendPushedMessage(pushedMessage);
//				line = lineOfText.readLine();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		String channelName2 = "华工风采";
//		Channel channel2 = new Channel();
//		channel2.setChannelName(channelName2);
//		String URL5 = "http://taolihui.com/icon/22";
//		String strURL5 = "";
//		try {
//			strURL5 = URLEncoder.encode(URL5, ENCODING).replace("*", "*")
//					.replace("~", "~").replace("+", " ");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		channel2.setChannelIcon(strURL5);
//		server.createChannel(channel2);
//
//		ChannelList channelList2 = new ChannelList();
//		channelList2.add(channel2);
//		server.broadcastChannelUpdateMessage(channelList2);
//
//		smsg.setChannelName(channelName2);
//		// pushedMessage.setChannelName(channelName2);
//		pushedMessage.setChannelName(null);
//		list.add(smsg);
//		pushedMessage.setNewsList(list);
//
//		line = "";
//		while (line.equals("quit") == false) {
//			try {
//				line = lineOfText.readLine();
//				server.sendPushedMessage(pushedMessage);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
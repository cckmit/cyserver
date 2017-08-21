package com.cy.core.sms.util;

import java.util.concurrent.LinkedBlockingQueue;

import com.cy.core.sms.entity.MsgSend;

import cn.emay.sdk.client.api.MO;
import cn.emay.sdk.client.api.StatusReport;

/**
 * 
 * @author dengqiao
 * 
 */
public class Global {
	/**
	 * 客户端序列号
	 */
	public static final String softwareSerialNo = "9SDK-EMY-0999-JBRPM";
	/**
	 * 注册关键字
	 */
	public static final String key = "123123";

	/**
	 * 密码
	 */
	public static final String serialpass = "265782";

	/**
	 * 数字5
	 */
	public static final int NUMBER5 = 5;

	/**
	 * 数字1
	 */
	public static final int NUMBER1 = 1;

	/**
	 * 短信发送速率
	 */
	public static final int FLOW_LIMIT = 10;

	/**
	 * 每批短信的最大ID
	 */
	public static int maxMsgId = 0;

	public static LinkedBlockingQueue<MO> moQueue = new LinkedBlockingQueue<MO>();
	public static LinkedBlockingQueue<StatusReport> rptQueue = new LinkedBlockingQueue<StatusReport>();
	public static LinkedBlockingQueue<MsgSend> sendQueue = new LinkedBlockingQueue<MsgSend>();
	public static LinkedBlockingQueue<MsgSend> rSendQueue = new LinkedBlockingQueue<MsgSend>();

}

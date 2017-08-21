package com.cy.core.sms.manager;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.core.sms.entity.MsgRecv;
import com.cy.core.sms.entity.MsgSend;
import com.cy.core.sms.service.MsgRecvService;
import com.cy.core.sms.service.MsgSendService;
import com.cy.core.sms.util.Global;

import cn.emay.sdk.client.api.MO;
import cn.emay.sdk.client.api.StatusReport;

/**
 * 短消息处理类
 * 
 * @author dengqiao
 * 
 */
//@Component
public class Message {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Message.class);
	@Autowired
	private MsgSendService msgSendService;
	@Autowired
	private MsgRecvService msgRecvService;
	@Autowired
	private SmsManager smsManager;
	private int sendCount = 1;
	private long startTime = 0;
	private long estimatedTime = 0;

	public Message() {

		/**
		 * 处理上行短消息线程
		 */
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						MO mo = Global.moQueue.take();
						// 上行短消息
						MsgRecv msgRecv = new MsgRecv();
						msgRecv.setContent(mo.getSmsContent());
						msgRecv.setTelphone(mo.getMobileNumber());
						msgRecv.setRecvtime(new Date());
						msgRecvService.insertMsg(msgRecv);
					} catch (InterruptedException e) {
						logger.error("处理上行短消息线程中断异常：", e);
					} catch (Exception e) {
						logger.error("处理上行短消息线程执行异常：", e);
					}
				}
			}

		}.start();

		/**
		 * 处理状态报告线程
		 */
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						StatusReport statusReport = Global.rptQueue.take();
						MsgSend msgSend = new MsgSend();
						msgSend.setErrorCode(statusReport.getErrorCode());
						String mobile = statusReport.getMobile();
						// 联通电信的会在号码前+86
						if (mobile.substring(0, 2).equals("86")) {
							mobile = mobile.substring(2);
						}
						msgSend.setTelphone(mobile);
						msgSend.setMessagegroup(statusReport.getSeqID());
						msgSend.setReceivetime(new Date());
						msgSend.setStatues(statusReport.getReportStatus());
						msgSendService.updateByPM(msgSend);
					} catch (InterruptedException e) {
						logger.error("处理状态报告线程中断异常：", e);
					} catch (Exception e) {
						logger.error("处理状态报告线程执行异常：", e);
					}
				}
			}

		}.start();

		/**
		 * 处理已发送响应线程
		 * 
		 */
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						MsgSend msgSend = Global.rSendQueue.take();
						msgSendService.updateByPM(msgSend);
					} catch (InterruptedException e) {
						logger.error("处理已发送响应线程中断异常", e);
					} catch (Exception e) {
						logger.error("处理已发送响应线程执行异常", e);
					}
				}
			}

		}.start();

		/**
		 * 短信发送线程
		 */
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						MsgSend msgSend = Global.sendQueue.take();
						if (sendCount <= 1) {
							startTime = System.currentTimeMillis();
							// logger.info("one round sending start. msgLimit = "
							// + Global.FLOW_LIMIT);
						}
						// logger.info("telphone:" + msgSend.getTelphone());
						int result = smsManager.sendSMSEx(new String[] { msgSend.getTelphone() }, msgSend.getContent(),
						        1, msgSend.getMessagegroup());
						if (result == 0) {
							msgSend.setStatues(2);// 已送达第三方
							Global.rSendQueue.put(msgSend);
						} else {
							// 发送失败重发
							// logger.info("sms send result:" + result);
							Global.sendQueue.put(msgSend);

						}
						if (sendCount >= Global.FLOW_LIMIT) {
							estimatedTime = System.currentTimeMillis() - startTime;
							// logger.info("one round sending end. estimatedTime = "
							// + estimatedTime);
							sendCount = 0;
							if (estimatedTime < 1000) {
								try {
									Thread.sleep(1000 - estimatedTime);
								} catch (InterruptedException e) {
									logger.info("Thread.sleep exception! ", e);
								}
							}
						}
						sendCount++;
					} catch (InterruptedException e) {
						try {
							Thread.sleep(30000);
						} catch (InterruptedException e1) {
						}
						logger.error(e);
					} catch (Exception e) {
						try {
							Thread.sleep(30000);
						} catch (InterruptedException e1) {
						}
						logger.error(e);
					}
				}
			}
		}.start();

		/**
		 * 获取数据库未发送短消息线程
		 */
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(10000);
				} catch (InterruptedException e1) {
					logger.error("获取数据库未发送短消息线程中断异常：", e1);
				}
				while (true) {
					try {
						List<MsgSend> list = msgSendService.selectTopMsgId(Global.maxMsgId);
						for (MsgSend msgSend : list) {
							Global.maxMsgId = msgSend.getMsgId() > Global.maxMsgId ? msgSend.getMsgId()
							        : Global.maxMsgId;
							try {
								Global.sendQueue.put(msgSend);
							} catch (InterruptedException e) {
								logger.error("短消息入队列异常", e);
							}
						}
						sleep(30000);
					} catch (Exception e) {
						logger.error("获取数据库未发送短消息线程执行异常：", e);
					}
				}
			}
		}.start();

		/**
		 * 收取上行短消息线程
		 */
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(10000);// 延迟10秒启动
				} catch (InterruptedException e) {
					logger.error("收取上行短消息线程睡眠中断异常：", e);
				}
				while (true) {
					try {
						List<MO> list = smsManager.getMO();
						if (list != null) {
							for (int i = 0; i < list.size(); i++) {
								MO mo = list.get(i);
								Global.moQueue.put(mo);
							}
						}
						sleep(3000);
					} catch (Exception e) {
						logger.error("收取上行短消息线程执行异常：", e);
					}

				}
			}
		}.start();

		/**
		 * 收取状态报告线程
		 */
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(10000);// 延迟10秒启动
				} catch (InterruptedException e) {
					logger.error("收取状态报告线程睡眠中断异常：", e);
				}
				while (true) {
					try {
						List<StatusReport> list = smsManager.getReport();
						if (list != null) {
							for (int i = 0; i < list.size(); i++) {
								StatusReport statusReport = list.get(i);
								logger.info("mobile:" + statusReport.getMobile() + "   statues:"
								        + statusReport.getReportStatus() + "   code:" + statusReport.getErrorCode());
								Global.rptQueue.put(statusReport);
							}
						}
						sleep(3000);
					} catch (Exception e) {
						logger.error("收取状态报告线程睡眠执行异常：", e);
					}
				}
			}
		}.start();

	}
}

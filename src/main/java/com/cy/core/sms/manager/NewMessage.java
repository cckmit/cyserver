package com.cy.core.sms.manager;

import com.cy.core.sms.entity.MsgSend;
import com.cy.core.sms.service.MsgSendService;
import com.cy.core.smsAccount.service.SmsAccountService;
import com.cy.shortmessage.SingletonClient;
import com.cy.shortmessage.entity.StatusReport;
import com.cy.shortmessage.exception.ShortMessageException;
import com.cy.shortmessage.http.HttpSendAdaptor;
import com.cy.shortmessage.sdk.SdkSendAdaptor;
import com.cy.smscloud.http.SmsCloudHttpUtils;
import com.cy.system.Global;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class NewMessage {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NewMessage.class);
	@Autowired
	private MsgSendService msgSendService;
	@Autowired
	private SmsAccountService smsAccountService;

	private int sendCount = 1;
	private long startTime = 0;
	private long estimatedTime = 0;

	private Map<String, Object> map = new HashMap<String, Object>();

	public NewMessage() {
		// 状态报告
		new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("NewMessage=================================状态报告");
					sleep(30000);
				} catch (InterruptedException e1) {
				}
				while (true) {
					try {
						List<StatusReport> list = null;
						if (Global.sendType != null && Global.sendType.equals("HTTP")) {
							list = SingletonClient.getInstance(HttpSendAdaptor.class, Global.sendType).recvStatusReport(Global.userAccount, Global.password);
						} else if (Global.sendType != null && Global.sendType.equals("SDK")) {
							list = SingletonClient.getInstance(SdkSendAdaptor.class, Global.sendType).recvStatusReport(Global.userAccount, Global.password);
						} else {

						}
						if (list != null && list.size() > 0) {
							for (StatusReport statusReport : list) {
								MsgSend msgSend = new MsgSend();
								msgSend.setErrorCode(statusReport.getErrorCode());
								String mobile = statusReport.getMobile();
								// 联通电信的会在号码前+86
								if (mobile.substring(0, 2).equals("86")) {
									mobile = mobile.substring(2);
								}
								msgSend.setTelphone(mobile);
								msgSend.setMessagegroup(statusReport.getSeqId());
								msgSend.setReceivetime(new Date());
								msgSend.setStatues(statusReport.getStatus());
								msgSendService.updateStatusReport(msgSend);
							}
						}
						try {
							sleep(3000);
						} catch (InterruptedException e1) {
						}
					} catch (InterruptedException e) {
						logger.error("处理状态报告线程中断异常：", e);
						try {
							sleep(3000);
						} catch (InterruptedException e1) {
						}
					} catch (Exception e) {
						logger.error("处理状态报告线程执行异常：", e);
						try {
							sleep(3000);
						} catch (InterruptedException e1) {
						}
					}
				}
			}

		}.start();
		/*****************************************************************************************************/


		/*new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("NewMessage=================================");
					sleep(1000);
				} catch (InterruptedException e1) {
					logger.error("获取数据库未发送短消息线程中断异常：", e1);
				}
				while (true) {
					try {
						UserInfo userInfo = new UserInfo();

						List<Map<String, String>> list =  userInfoMapper.findUserInfoMap(analysisMap);
						JSONArray jsonArr = JSONArray.fromObject(list.toArray());

						List<Map<String, String>> list1 =  userInfoService.chartOfMining();
						JSONArray jsonArr1 = JSONArray.fromObject(list1.toArray());

						List<Map<String, String>> list2 =  userInfoService.userInfoSummary(analysisMap) ;;
						JSONArray jsonArr2 = JSONArray.fromObject(list2.toArray());

						userInfo.setProvienceName(jsonArr.toString());
						userInfo.setValue(jsonArr1.toString());
						userInfo.setContext(jsonArr2.toString());
						userInfoMapper.saveUser(userInfo);

						sleep(3600000);
					} catch (Exception e) {
						logger.error("获取数据库未发送短消息线程执行异常：", e);
						try {
							sleep(3000);
						} catch (InterruptedException e1) {
						}
					}
				}
			}
		}.start();*/

		/*****************************************************************************************************/

		// 短信发送线程
		new Thread() {
			@Override
			public void run() {
				try {
					System.out.println("NewMessage=================================短信发送");
					sleep(30000);
				} catch (InterruptedException e1) {
				}
				while (true) {
					MsgSend msgSend = null;
					try {
						msgSend = com.cy.core.sms.util.Global.sendQueue.take();
						if (sendCount <= 1) {
							startTime = System.currentTimeMillis();
						}
						if (Global.sendType != null && Global.sendType.equals("HTTP")) {
							map.clear();
							map.put("mobile", msgSend.getTelphone());
							map.put("needstatus", String.valueOf(true));
							map.put("msg", msgSend.getContent());
							map.put("product", "");
							map.put("extno", "");
							String result = SingletonClient.getInstance(HttpSendAdaptor.class, Global.sendType).sendSMS(Global.smsUrl, Global.userAccount,
									Global.password, map);
							if (result != null && result.length() > 0) {
								String[] s0 = result.split("\n");
								String[] s1 = s0[0].split(",");
								if (s1[1].equals("0")) {
									msgSend.setStatues(2);
									msgSend.setSmsID(Long.parseLong(s0[1]));
								} else {
									msgSend.setStatues(Integer.parseInt(s1[1]));
									msgSend.setSmsID(Long.parseLong(s0[1]));
								}
								// 更新短信的状态
								msgSendService.updateStatus(msgSend);
							} else {
								com.cy.core.sms.util.Global.sendQueue.put(msgSend);
								sleep(1000);
							}

						} else if (Global.sendType != null && Global.sendType.equals("SDK")) {
							map.clear();
							map.put("mobile", msgSend.getTelphone());
							map.put("msg", msgSend.getContent());
							map.put("smsID", msgSend.getMessagegroup());
							map.put("smsPriority", 0);
							String result = SingletonClient.getInstance(SdkSendAdaptor.class, Global.sendType).sendSMS(Global.smsUrl, Global.userAccount,
									Global.password, map);
							if (result != null && result.equals("0")) {
								msgSend.setStatues(2);// 已送达第三方
								msgSend.setSmsID(msgSend.getMessagegroup());
								// 更新短信状态
								msgSendService.updateStatus(msgSend);
							} else {
								// 发送失败重发
								logger.info("sms send faild result:" + result);
								com.cy.core.sms.util.Global.sendQueue.put(msgSend);
								sleep(1000);
							}

						} else if (Global.sendType != null && Global.sendType.equals("CLOUD")) {
							map.clear();
							map.put("mobile", msgSend.getTelphone());
							map.put("msg", msgSend.getContent());
							map.put("smsID", msgSend.getMessagegroup());
							map.put("smsPriority", 0);
//							String result = SingletonClient.getInstance(SdkSendAdaptor.class, Global.sendType).sendSMS(Global.smsUrl, Global.userAccount,
//									Global.password, map);

//							String result =  smsAccountService.sendSms(msgSend.getDeptId(), msgSend.getTelphone(), msgSend.getContent());
							String result = SmsCloudHttpUtils.sendSms(Global.smsUrl, Global.userAccount, Global.password, msgSend.getTelphone(), msgSend.getContent()) ;

							if (result != null && result.equals("0")) {
								msgSend.setStatues(2);// 已送达第三方
								msgSend.setSmsID(msgSend.getMessagegroup());
								// 更新短信状态
								msgSendService.updateStatus(msgSend);
							} else if("3009".equals(result)) {
								msgSend.setStatues(1);
								msgSend.setSmsID(msgSend.getMessagegroup());
								// 更新短信状态
								msgSendService.updateStatus(msgSend);
							} else {
								// 发送失败重发
								logger.info("sms send faild result:" + result);
								com.cy.core.sms.util.Global.sendQueue.put(msgSend);
								sleep(1000);
							}
						} else {
							com.cy.core.sms.util.Global.sendQueue.put(msgSend);
							sleep(10000);
						}
						if (sendCount >= com.cy.core.sms.util.Global.FLOW_LIMIT) {
							estimatedTime = System.currentTimeMillis() - startTime;
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
							Thread.sleep(10000);
						} catch (InterruptedException e1) {
						}
						logger.error(e);
					} catch (ShortMessageException e) {
						try {
							if (msgSend != null) {
								com.cy.core.sms.util.Global.sendQueue.put(msgSend);
							}
						} catch (InterruptedException e2) {
						}
						logger.error(e, e);
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e1) {
						}
					} catch (Exception e) {
						logger.error(e, e);
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e1) {
						}
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
					System.out.println("NewMessage=================================获取数据库中未发送短消息");
					sleep(30000);
				} catch (InterruptedException e1) {
					logger.error("获取数据库未发送短消息线程中断异常：", e1);
				}
				while (true) {
					try {
						if (Global.sendType != null && Global.sendType.length() > 0) {
							List<MsgSend> list = msgSendService.selectTopMsgId(com.cy.core.sms.util.Global.maxMsgId);
							for (MsgSend msgSend : list) {
								com.cy.core.sms.util.Global.maxMsgId = msgSend.getMsgId() > com.cy.core.sms.util.Global.maxMsgId ? msgSend.getMsgId()
										: com.cy.core.sms.util.Global.maxMsgId;
								try {
									com.cy.core.sms.util.Global.sendQueue.put(msgSend);
								} catch (InterruptedException e) {
									logger.error("短消息入队列异常", e);
								}
							}
						}
						sleep(10000);
					} catch (Exception e) {
						logger.error("获取数据库未发送短消息线程执行异常：", e);
						try {
							sleep(5000);
						} catch (InterruptedException e1) {
						}
					}
				}
			}
		}.start();
	}

}

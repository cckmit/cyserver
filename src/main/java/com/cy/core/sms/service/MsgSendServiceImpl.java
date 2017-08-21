package com.cy.core.sms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cy.smscloud.entity.Header;
import com.cy.base.entity.Message;
import com.cy.core.smsAccount.service.SmsAccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.AsynTreeNode;
import com.cy.base.entity.DataGrid;
import com.cy.core.sms.dao.MsgSendMapper;
import com.cy.core.sms.entity.MsgSend;
import com.cy.core.smsCode.dao.SmsCodeMapper;
import com.cy.core.smsCode.entity.SmsCode;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.shortmessage.SingletonClient;
import com.cy.shortmessage.http.HttpSendAdaptor;
import com.cy.shortmessage.sdk.SdkSendAdaptor;
import com.cy.smscloud.http.SmsCloudHttpUtils;
import com.cy.system.Global;
import com.cy.system.SystemUtil;
import com.cy.system.UUID;
import com.cy.util.shortmsg.Send;

@Service("msgSendService")
public class MsgSendServiceImpl implements MsgSendService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MsgSendServiceImpl.class);

	private MsgSendMapper msgSendMapper;
	@Autowired
	private SmsAccountService smsAccountService;

	private UserInfoMapper userInfoMapper;
	@Autowired
	private SmsCodeMapper smsCodeMapper;

	public MsgSendMapper getMsgSendMapper() {
		return msgSendMapper;
	}

	@Autowired
	public void setMsgSendMapper(MsgSendMapper msgSendMapper) {
		this.msgSendMapper = msgSendMapper;
	}

	public UserInfoMapper getUserInfoMapper() {
		return userInfoMapper;
	}

	@Autowired
	public void setUserInfoMapper(UserInfoMapper userInfoMapper) {
		this.userInfoMapper = userInfoMapper;
	}

	public int updateByPM(MsgSend msgSend) {
		return msgSendMapper.updateByPM(msgSend);
	}

	public List<MsgSend> selectTopMsgId(int msgId) {
		return msgSendMapper.selectTopMsgId(msgId);
	}

	public List<AsynTreeNode> getAddrBookNode(String deptId, String level, String poolId) {
		List<AsynTreeNode> treeNodeList = new ArrayList<AsynTreeNode>();
		// if (level == null || "".equals(level))
		// {
		// if (deptId.length() == 16)
		// {
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("tableName", "tbl_user_info_" + deptId.substring(0, 5));
		// map.put("classId", deptId);
		// List<UserInfo> list = userInfoMapper.selectByClassId(map);
		// for (UserInfo userInfo : list)
		// {
		// AsynTreeNode synTreeNode = new AsynTreeNode();
		// synTreeNode.setName(userInfo.getUserName());
		// synTreeNode.setId(userInfo.getUserId());
		// synTreeNode.setTelphone(userInfo.getTelId());
		// synTreeNode.setIsParent(false);
		// synTreeNode.setPlevel("");
		// treeNodeList.add(synTreeNode);
		// }
		// } else
		// {
		// List<DeptInfo> list = deptMapper.selectByParentId(deptId);
		// for (DeptInfo deptInfo : list)
		// {
		// AsynTreeNode synTreeNode = new AsynTreeNode();
		// synTreeNode.setName(deptInfo.getDeptName());
		// synTreeNode.setId(deptInfo.getDeptId());
		// synTreeNode.setIsParent(true);
		// synTreeNode.setPlevel("");
		// synTreeNode.setTypeId(deptInfo.getTypeId());
		// synTreeNode.setFullName(deptInfo.getFullName().replace(",", ""));
		// treeNodeList.add(synTreeNode);
		// }
		// }
		// } else if ("1".equals(level))
		// {
		// List<TalentPool> list = talentPoolMapper.selectByDeptId(deptId);
		// for (TalentPool talentPool : list)
		// {
		// AsynTreeNode synTreeNode = new AsynTreeNode();
		// synTreeNode.setId(talentPool.getPoolId().toString());
		// synTreeNode.setIsParent(true);
		// synTreeNode.setPlevel("2");
		// synTreeNode.setName(talentPool.getPoolName());
		// treeNodeList.add(synTreeNode);
		// }
		//
		// } else if ("2".equals(level))
		// {
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("tableName", "tbl_user_info_" + poolId.substring(0, 5));
		// map.put("poolId", Integer.parseInt(deptId));
		// List<UserInfo> list = userInfoMapper.selectByUserId(map);
		// for (UserInfo userInfo : list)
		// {
		// AsynTreeNode synTreeNode = new AsynTreeNode();
		// synTreeNode.setName(userInfo.getUserName());
		// synTreeNode.setId(userInfo.getUserId());
		// synTreeNode.setTelphone(userInfo.getTelId());
		// synTreeNode.setIsParent(false);
		// synTreeNode.setPlevel("");
		// treeNodeList.add(synTreeNode);
		// }
		// }
		return treeNodeList;
	}

	public void insertMsgSend(MsgSend msgSend) {
		try {
			String[] str = msgSend.getTelphone().split(",");
			String content = msgSend.getContent();
			if (Global.sign != null && Global.sign.length() > 0) {
//				content = "【" + Global.sign + "】"+content ;
//				content = "(" + Global.sign + ")" ;
				if (Global.sendType != null && Global.sendType.equals("CLOUD")) {
					content += "（" + Global.sign + "）";

				} else {
					content = "【" + Global.sign + "】" + content;
				}
			}
			int countNumber = 0;
			if (content.length() % 67 == 0) {
				countNumber = content.length() / 67;
			} else {
				countNumber = content.length() / 67 + 1;
			}
			long group = UUID.getMsgGroup();

			// 号码去掉重复的
			Set<String> set = new HashSet<String>();
			for (String tel : str) {
				set.add(tel);
			}

			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String tel = it.next();
				MsgSend msgSend2 = new MsgSend();
				msgSend2.setContent(content);
				msgSend2.setMessagegroup(group);
				msgSend2.setMsgType(0);
				msgSend2.setStatues(9);
				msgSend2.setSendtime(new Date());
				msgSend2.setCountNumber(countNumber);
				msgSend2.setTelphone(tel.trim());
				msgSend2.setStaffId(msgSend.getStaffId());
				msgSend2.setDeptId(msgSend.getDeptId());
				msgSendMapper.insertMsg(msgSend2);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public DataGrid<MsgSend> dataGridOutBox(Map<String, Object> map) {
		long total = msgSendMapper.countOutBox(map);
		DataGrid<MsgSend> dataGrid = new DataGrid<MsgSend>();
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<MsgSend> list = msgSendMapper.selectOutBox(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public Map<String, List<MsgSend>> viewReport(String messagegroup) {
		List<MsgSend> sucList = new ArrayList<MsgSend>();
		List<MsgSend> failList = new ArrayList<MsgSend>();
		List<MsgSend> noReportList = new ArrayList<MsgSend>();
		List<MsgSend> readyList = new ArrayList<MsgSend>();
		Map<String, List<MsgSend>> map = new HashMap<String, List<MsgSend>>();
		List<MsgSend> list = msgSendMapper.selectByMsgGroup(messagegroup);
		for (MsgSend msgSend : list) {
			if (msgSend.getStatues() == 0) {
				sucList.add(msgSend);
			} else if (msgSend.getStatues() == 9) {
				readyList.add(msgSend);
			} else if (msgSend.getStatues() == 2) {
				noReportList.add(msgSend);
			} else {
				failList.add(msgSend);
			}
		}
		map.put("sucList", sucList);
		map.put("failList", failList);
		map.put("noReportList", noReportList);
		map.put("readyList", readyList);
		return map;
	}

	public MsgSend selectByMGAndTel(Map<String, Object> map) {
		return msgSendMapper.selectByMGAndTel(map);
	}

	public List<MsgSend> selectByDate(Map<String, Object> map) {
		return msgSendMapper.selectByDate(map);
	}

	public int deleteByDate(Map<String, Object> map) {
		return msgSendMapper.deleteByDate(map);
	}

//	public int sendSmsCode(String mobile, String content, String code) {
//		try {
//			long group = SystemUtil.getOnlyNumber();
//			int result = smsManager.sendSMSEx(new String[] { mobile }, content, 5, group);
//			if (result == 0) {
//				MsgSend msgSend = new MsgSend();
//				msgSend.setContent(content);
//				int countNumber = 0;
//				if (content.length() % 70 == 0) {
//					countNumber = content.length() / 70;
//				} else {
//					countNumber = content.length() / 70 + 1;
//				}
//				msgSend.setTelphone(mobile);
//				msgSend.setStatues(2);// 已经达到亿美
//				msgSend.setSendtime(new Date());
//				msgSend.setMsgType(1);
//				msgSend.setCountNumber(countNumber);
//				msgSend.setMessagegroup(group);
//				msgSendMapper.insertMsg(msgSend);
//
//				SmsCode smsCode = new SmsCode();
//				smsCode.setCreateTime(new Date());
//				smsCode.setTelId(mobile);
//				smsCode.setSmsCode(code);
//				smsCodeMapper.addSmsCode(smsCode);
//				return 1;
//			}
//
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		return 0;
//	}

	public int sendRegisterCode(String mobile, String content, String code) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
//			Global.sendType = "SDK" ;
			System.out.println("----------> sendType : " + Global.sendType);
			if (Global.sendType != null && Global.sendType.equals("HTTP")) {
				map.clear();
				map.put("mobile", mobile);
				map.put("needstatus", String.valueOf(true));
				map.put("msg", content);
				map.put("product", "");
				map.put("extno", "");
				String result = SingletonClient.getInstance(HttpSendAdaptor.class, Global.sendType).sendSMS(Global.smsUrl, Global.userAccount, Global.password,
						map);
				if (result != null && result.length() > 0) {
					MsgSend msgSend = new MsgSend();
					msgSend.setContent(content);
					int countNumber = 0;
					if (content.length() % 67 == 0) {
						countNumber = content.length() / 67;
					} else {
						countNumber = content.length() / 67 + 1;
					}
					msgSend.setTelphone(mobile);
					msgSend.setStatues(2);
					msgSend.setSendtime(new Date());
					msgSend.setMsgType(1);
					msgSend.setCountNumber(countNumber);
					String[] s0 = result.split("\n");
					msgSend.setMessagegroup(Long.parseLong(s0[1]));
					msgSendMapper.insertMsg(msgSend);
				} else {
					return -500;
				}
			} else if (Global.sendType != null && Global.sendType.equals("SDK")) {
				long group = SystemUtil.getOnlyNumber();
				map.clear();
				map.put("mobile", mobile);
				map.put("msg", content);
				map.put("smsID", group);
				map.put("smsPriority", 5);
				String result = SingletonClient.getInstance(SdkSendAdaptor.class, Global.sendType).sendSMS(Global.smsUrl, Global.userAccount, Global.password,
						map);
				
//				String result = Send.sendSms(mobile, content, Global.userAccount, Global.password) ;
				if (result != null && result.equals("0")) {
					MsgSend msgSend = new MsgSend();
					msgSend.setContent(content);
					int countNumber = 0;
					if (content.length() % 67 == 0) {
						countNumber = content.length() / 67;
					} else {
						countNumber = content.length() / 67 + 1;
					}
					msgSend.setTelphone(mobile);
					msgSend.setStatues(2);// 已经达到亿美
					msgSend.setSendtime(new Date());
					msgSend.setMsgType(1);
					msgSend.setCountNumber(countNumber);
					msgSend.setMessagegroup(group);
					msgSendMapper.insertMsg(msgSend);

					SmsCode smsCode = new SmsCode();
					smsCode.setCreateTime(new Date());
					smsCode.setTelId(mobile);
					smsCode.setSmsCode(code);
					smsCodeMapper.addSmsCode(smsCode);
				}
				if (result == null) {
					result = "-500";
				}
				return Integer.parseInt(result);
			} else if(Global.sendType != null && Global.sendType.equals("CLOUD")) {
				// 访问短信平台接口发送短信
				long group = SystemUtil.getOnlyNumber();
				map.clear();
				map.put("mobile", mobile);
				map.put("msg", content);
				map.put("smsID", group);
				map.put("smsPriority", 5);
//				String result = SingletonClient.getInstance(SdkSendAdaptor.class, Global.sendType).sendSMS(Global.smsUrl, Global.userAccount, Global.password,
//						map);
				
//				String result = Send.sendSms(mobile, content, Global.userAccount, Global.password) ;


				Header header = new Header() ;
				header.setAppAccount(Global.userAccount);
				header.setAppKey(Global.password);
				Map<String,Object> body = new HashMap<String,Object>() ;
				body.put("code","0") ;
				body.put("mobile",mobile) ;
//				body.put("content","【窗友】您的验证码是12314，在5分钟内有效，如非本人操作请忽略本短信。(窗友大学)") ;
				body.put("content",content) ;
				Map<String,String> params = new HashMap<String,String>() ;
				params.put("0",code) ;
				body.put("params",params) ;
				String result = SmsCloudHttpUtils.sendSmsBySubmail(Global.smsUrl,header,body) ;

//				String result = SmsCloudHttpUtils.sendSms(Global.smsUrl, Global.userAccount, Global.password, mobile, content) ;
				if (result != null && result.equals("0")) {
					MsgSend msgSend = new MsgSend();
					msgSend.setContent(content);
					int countNumber = 0;
					if (content.length() % 67 == 0) {
						countNumber = content.length() / 67;
					} else {
						countNumber = content.length() / 67 + 1;
					}
					msgSend.setDeptId("1");
					msgSend.setTelphone(mobile);
					msgSend.setStatues(2);// 已经发送到短信云平台
					msgSend.setSendtime(new Date());
					msgSend.setMsgType(1);
					msgSend.setCountNumber(countNumber);
					msgSend.setMessagegroup(group);
					msgSendMapper.insertMsg(msgSend);

					SmsCode smsCode = new SmsCode();
					smsCode.setCreateTime(new Date());
					smsCode.setTelId(mobile);
					smsCode.setSmsCode(code);
					smsCodeMapper.addSmsCode(smsCode);
				}
				if (result == null) {
					result = "-500";
				}
				return Integer.parseInt(result);
				
			} else {
				return -404;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			throw new RuntimeException(e);
		}
		return 0;
	}

//	public int sendSmsAppInvite(String mobile, String content) {
//		try {
//			long group = UUID.getMsgGroup();
//			int result = smsManager.sendSMSEx(new String[] { mobile }, content, 5, group);
//			if (result == 0) {
//				MsgSend msgSend = new MsgSend();
//				msgSend.setContent(content);
//				int countNumber = 0;
//				if (content.length() % 70 == 0) {
//					countNumber = content.length() / 70;
//				} else {
//					countNumber = content.length() / 70 + 1;
//				}
//				msgSend.setTelphone(mobile);
//				msgSend.setStatues(2);// 已经达到亿美
//				msgSend.setSendtime(new Date());
//				msgSend.setMsgType(1);
//				msgSend.setCountNumber(countNumber);
//				msgSend.setMessagegroup(group);
//				msgSendMapper.insertMsg(msgSend);
//				return 1;
//			}
//		} catch (Exception e) {
//			logger.error(e, e);
//		}
//		return 0;
//	}

	@Override
	public void updateStatusReport(MsgSend msgSend) {
		msgSendMapper.updateStatusReport(msgSend);
	}

	@Override
	public void updateStatus(MsgSend msgSend) {
		msgSendMapper.updateStatus(msgSend);
	}

}

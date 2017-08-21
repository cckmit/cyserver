package com.cy.mobileInterface.datamining.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.sms.dao.MsgSendMapper;
import com.cy.core.sms.entity.MsgSend;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.mobileInterface.datamining.entity.Datamining;
import com.cy.system.Global;
import com.cy.system.SystemUtil;
import com.cy.util.WebUtil;

@Service("dataminingService")
public class DataminingServiceImpl implements DataminingService {

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private MsgSendMapper msgSendMapper;

	@Override
	public void updateClassmateTel(Message message, String content) {
		Datamining datamining = JSON.parseObject(content, Datamining.class);
		if (WebUtil.isEmpty(datamining.getAccountNum()) || WebUtil.isEmpty(datamining.getPassword()) || WebUtil.isEmpty(datamining.getGmid())
				|| WebUtil.isEmpty(datamining.getName()) || WebUtil.isEmpty(datamining.getTelId())) {// 协议检查
			message.setMsg("数据格式错误,帐号,密码,班级Id,姓名,手机号不能为空!");
			message.setSuccess(false);
			return;
		}

		try {
			UserProfile userProfile = userProfileMapper.selectByAccountNum(datamining.getAccountNum());
			if (userProfile == null) {
				message.setMsg("账号不存在!");
				message.setSuccess(false);
				return;
			}

			if (!datamining.getPassword().equals(userProfile.getPassword())) {// 检查密码
				message.setMsg("密码错误!");
				message.setSuccess(false);
				return;
			}

			// 检查班级是否有此人
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gmid", datamining.getGmid().substring(0, 16));
			map.put("name", datamining.getName());
			UserInfo userinfo = userInfoMapper.selectUserInfo(map);
			if (userinfo == null) {
				message.setSuccess(false);
				message.setMsg("查询不到此人");
				return;
			}

			if (!SystemUtil.isMobileNO(datamining.getTelId())) {
				message.setMsg("手机号码错误!");
				message.setSuccess(false);
				return;
			}

			if (Global.smsVisitTemplate != null && Global.smsVisitTemplate.length() > 0) {
				// 给该同学发送邀请短信
				String msg = Global.smsVisitTemplate;
				msg = msg.replace("${0}", userProfile.getName());
				if (Global.sign != null && Global.sign.length() > 0) {
//					msg = "【" + Global.sign + "】" + msg;
					if (Global.sendType != null && Global.sendType.equals("CLOUD")) {
						msg += "（" + Global.sign + "）";

					} else {
						msg = "【" + Global.sign + "】" + msg;
					}
				}
				MsgSend msgSend = new MsgSend();
				msgSend.setContent(msg);
				int countNumber = 0;
				if (msg.length() % 67 == 0) {
					countNumber = msg.length() / 67;
				} else {
					countNumber = msg.length() / 67 + 1;
				}
				msgSend.setTelphone(datamining.getTelId());
				msgSend.setStatues(9);
				msgSend.setSendtime(new Date());
				msgSend.setMsgType(1);
				msgSend.setCountNumber(countNumber);
				msgSend.setMessagegroup(SystemUtil.getOnlyNumber());
				msgSendMapper.insertMsg(msgSend);
				message.setMsg("发送成功!");
				message.setSuccess(true);
				return;
			} else {
				message.setMsg("发送失败,模板未设置!");
				message.setSuccess(false);
				return;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

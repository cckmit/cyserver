package com.cy.mobileInterface.registerCode.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.sms.service.MsgSendService;
import com.cy.mobileInterface.registerCode.entity.RegisterCode;
import com.cy.system.Global;
import com.cy.system.SystemUtil;

@Service("registerCodeService")
public class RegisterCodeServiceImpl implements RegisterCodeService {

	@Autowired
	private MsgSendService msgSendService;

	@Override
	public void sendRegisterCode(Message message, String content) {
		try {
			System.out.println("---------0");
			RegisterCode registerCode = JSON.parseObject(content, RegisterCode.class);
			if (!SystemUtil.isMobileNO(registerCode.getPhoneNum())) {// 协议检查
				message.setMsg("电话号码错误!");
				message.setSuccess(false);
				return;
			}

			if (!Global.phone_secret_key.equals(registerCode.getSecretKey())) {
				message.setMsg("secretKey错误!");
				message.setSuccess(false);
				return;
			}
//			Global.smsCodeTemplate = "您的验证码是${0}，在5分钟内有效，如非本人操作请忽略本短信。" ;
//			System.out.println("---------2 smsCodeTemplate : " + Global.smsCodeTemplate);
			if (Global.smsCodeTemplate != null && Global.smsCodeTemplate.length() > 0) {
				String code = SystemUtil.getSixNumber();
				String msg = Global.smsCodeTemplate;
				msg = msg.replace("${0}", code);
				msg = msg.replace("${@}", code);
				if (Global.sign != null && Global.sign.length() > 0) {
					if (Global.sendType != null && Global.sendType.equals("CLOUD")) {
						msg += "（" + Global.sign + "）";

					} else {
						msg = "【" + Global.sign + "】" + msg;
					}
//					msg = "【窗友】" + msg;
				}
				System.out.println("------> 准备发送短信");
				int result = msgSendService.sendRegisterCode(registerCode.getPhoneNum(), msg, code);
				if (result == 0) {
					message.setMsg("发送成功");
					message.setSuccess(true);
					return;
				} else {
					message.setMsg("发送失败，错误码:" + result);
					message.setSuccess(false);
					return;
				}
			} else {
				message.setMsg("发送失败，验证码模板未设置。");
				message.setSuccess(false);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace() ;
			throw new RuntimeException(e);
		}
	}
}

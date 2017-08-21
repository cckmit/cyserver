package com.cy.core.sms.manager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cy.core.sms.util.Global;

import cn.emay.sdk.client.api.Client;
import cn.emay.sdk.client.api.MO;
import cn.emay.sdk.client.api.StatusReport;

/**
 * 短消息管理类
 * 
 * @author dengqiao
 * 
 */
//@Component
public class SmsManager {

//	private Client client = null;
	
	private Client client = SingletonClient.getClient(Global.softwareSerialNo, Global.key);

	/**
	 * 注册序列号，只需注册一次
	 * 
	 * @return
	 */
	public int registEx() {
		return client.registEx(Global.serialpass);
	}

	/**
	 * 注销序列号
	 * 
	 * @return
	 */
	public int logout() {
		return client.logout();
	}

	/**
	 * 查询单价
	 * 
	 * @return
	 */
	public double getEachFee() {
		return client.getEachFee();
	}

	/**
	 * 查询余额
	 * 
	 * @return
	 * @throws Exception
	 */
	public double getBalance() throws Exception {
		return client.getBalance();
	}

	/**
	 * 序列号充值
	 * 
	 * @param cardNo
	 * @param cardPass
	 * @return
	 */
	public int chargeUp(String cardNo, String cardPass) {
		return client.chargeUp(cardNo, cardPass);
	}

	/**
	 * 短信发送
	 * 
	 * @param mobiles
	 * @param smsContent
	 * @param smsPriority
	 * @return
	 */
	public int sendSMS(String[] mobiles, String smsContent, int smsPriority) {
		return client.sendSMS(mobiles, smsContent, smsPriority);
	}

	/**
	 * 获取上行
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<MO> getMO() throws Exception {
		return client.getMO();
	}

	/**
	 * 获取状态报告
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<StatusReport> getReport() throws Exception {
		return client.getReport();
	}

	/**
	 * 修改密码
	 * 
	 * @param serialPwd
	 * @param serialPwdNew
	 * @return
	 */
	public int serialPwdUpd(String serialPwd, String serialPwdNew) {
		return client.serialPwdUpd(serialPwd, serialPwdNew);
	}

	/**
	 * @param mobiles
	 * @param smsContent
	 * @param smsPriority
	 * @param smsID
	 * @return
	 */
	public int sendSMSEx(String[] mobiles, String smsContent, int smsPriority, long smsID) {
		return client.sendSMSEx(mobiles, smsContent, "", "GBK", smsPriority, smsID);
	}

}

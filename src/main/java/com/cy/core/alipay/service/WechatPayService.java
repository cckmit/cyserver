package com.cy.core.alipay.service;


import com.cy.base.entity.Message;

public interface WechatPayService {
	/**
	 * 手机APP微信支付统一下单接口
	 * @param message
	 * @param content
	 */
	public void getAppPayDate(Message message, String content) ;

	/**
	 * 手机APP微信支付
	 * @param orderNo
	 */
	public void wechatAppDonationPayBack(String orderNo) ;


}

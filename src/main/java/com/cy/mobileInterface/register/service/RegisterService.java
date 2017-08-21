package com.cy.mobileInterface.register.service;

import com.cy.base.entity.Message;

public interface RegisterService {
	/**
	 * 4号接口，用户注册
	 * 
	 * @param message
	 * @param content
	 */
	void register(Message message,String content);


	/**
	 * 微信上隐晦的注册接口
	 * @param message
	 * @param content
	 */
	void registAndBindWechat(Message message, String content);

	/**
	 * 发送激活链接
	 * @param message
	 * @param content
	 */
	void sendActiveEmail(Message message, String content);
}

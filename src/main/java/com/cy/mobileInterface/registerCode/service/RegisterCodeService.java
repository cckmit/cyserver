package com.cy.mobileInterface.registerCode.service;

import com.cy.base.entity.Message;

public interface RegisterCodeService {
	/**
	 * 3号接口,发送验证码
	 * 
	 * @param message
	 * @param content
	 */
	void sendRegisterCode(Message message, String content);
}

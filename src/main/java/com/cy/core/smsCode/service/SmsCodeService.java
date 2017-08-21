package com.cy.core.smsCode.service;

import com.cy.core.smsCode.entity.SmsCode;


public interface SmsCodeService {
	/**
	 * 通过电话号码和验证码获取短信验证码对象
	 * 
	 * @param telId
	 * @param code
	 * @return
	 */
	public SmsCode selectByTelId(String telId,String code);
}

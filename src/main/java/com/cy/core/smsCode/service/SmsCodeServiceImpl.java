package com.cy.core.smsCode.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.core.smsCode.dao.SmsCodeMapper;
import com.cy.core.smsCode.entity.SmsCode;




@Service("smsCodeService")
public class SmsCodeServiceImpl implements SmsCodeService {
	private SmsCodeMapper smsCodeMapper;
	
	
	public SmsCodeMapper getSmsCodeMapper() {
		return smsCodeMapper;
	}

	@Autowired
	public void setSmsCodeMapper(SmsCodeMapper smsCodeMapper) {
		this.smsCodeMapper = smsCodeMapper;
	}

	/**
	 * 通过电话号码和验证码获取短信验证码对象
	 * 
	 * @param telId
	 * @param code
	 * @return
	 */
	public SmsCode selectByTelId(String telId,String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("telId", telId);
		map.put("code", code);
		return smsCodeMapper.selectByTelId(map);
	}



}

package com.cy.core.mobileLocal.dao;

import com.cy.core.mobileLocal.entity.MobileLocal;

public interface MobileLocalMapper {
	/**
	 * 新增
	 * 
	 * @param mobileLocal
	 */
	void insert(MobileLocal mobileLocal);

	/**
	 * 通过手机号前7位获取手机号码归属地
	 * 
	 * @param mobileNumber
	 * @return
	 */
	MobileLocal selectByMobileNumber(String mobileNumber);
	
}

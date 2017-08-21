package com.cy.core.mobileLocal.dao;

import com.cy.core.mobileLocal.entity.MobileScratch;

public interface MobileScratchMapper {
	/**
	 * 新增
	 * 
	 * @param mobileLocal
	 */
	void insert(MobileScratch mobileScratch);

	/**
	 * 找不到归属地的手机号前7位暂存在MobileScratch里面
	 * 
	 * @param mobileNumber
	 * @return
	 */
	MobileScratch selectByMobileNumber(String mobileNumber);
	
	/**
	 * 删除
	 * 
	 * @param mobileNumber
	 */
	void delete(String mobileNumber);
}

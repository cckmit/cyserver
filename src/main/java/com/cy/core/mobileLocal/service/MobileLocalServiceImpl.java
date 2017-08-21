package com.cy.core.mobileLocal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.core.mobileLocal.dao.MobileLocalMapper;
import com.cy.core.mobileLocal.entity.MobileLocal;

@Service("mobileLocalService")
public class MobileLocalServiceImpl implements MobileLocalService {

	@Autowired
	private MobileLocalMapper mobileLocalMapper;

	@Override
	public void insert(MobileLocal mobileLocal) {
		mobileLocalMapper.insert(mobileLocal);
	}

	@Override
	public MobileLocal selectByMobileNumber(String mobileNumber) {
		return mobileLocalMapper.selectByMobileNumber(mobileNumber);
	}

}

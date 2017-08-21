package com.cy.core.authCount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.core.authCount.dao.AuthCountMapper;
import com.cy.core.authCount.entity.AuthCount;

@Service("authCountService")
public class AuthCountServiceImpl implements AuthCountService {
	@Autowired
	private AuthCountMapper authCountMapper;

	public void save(AuthCount authCount) {
		authCountMapper.save(authCount);
	}

	public void delete(String accountNum) {
		authCountMapper.delete(accountNum);
	}

	public AuthCount selectByAccountNum(String accountNum) {
		return authCountMapper.selectByAccountNum(accountNum);
	}

	public void update(AuthCount authCount) {
		authCountMapper.update(authCount);
	}
}

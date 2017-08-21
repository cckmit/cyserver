package com.cy.core.authCount.service;

import com.cy.core.authCount.entity.AuthCount;

public interface AuthCountService {
	AuthCount selectByAccountNum(String accountNum);

	void save(AuthCount authCount);

	void update(AuthCount authCount);

	void delete(String ids);
}

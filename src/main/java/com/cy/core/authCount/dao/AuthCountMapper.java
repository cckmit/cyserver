package com.cy.core.authCount.dao;

import com.cy.core.authCount.entity.AuthCount;

public interface AuthCountMapper {
	AuthCount selectByAccountNum(String accountNum);

	void save(AuthCount authCount);

	void update(AuthCount authCount);

	void delete(String accountNum);

}

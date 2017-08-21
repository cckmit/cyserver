package com.cy.mobileInterface.password.service;

import com.cy.base.entity.Message;

public interface PassWordService {
	void updatePassWord(Message message,String content);
	void updateEasemobPassword(Message message,String content);
	void userPasswordInit();
}

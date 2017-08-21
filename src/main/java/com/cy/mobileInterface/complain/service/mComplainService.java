package com.cy.mobileInterface.complain.service;

import com.cy.base.entity.Message;

public interface mComplainService {

     void QueryList(Message message, String content);
	 
	 void getById(Message message, String content);
	 
	 void delete(Message message, String content);
	 
	 void update(Message message, String content);
}

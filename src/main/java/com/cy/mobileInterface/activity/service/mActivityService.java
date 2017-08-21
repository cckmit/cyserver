package com.cy.mobileInterface.activity.service;

import com.cy.base.entity.Message;

public interface mActivityService {
 void QueryList(Message message, String content);
	 
	 void getById(Message message, String content);
	 
	 void delete(Message message, String content);
	 
	 void update(Message message, String content);
	 
	 void add(Message message, String content);
}

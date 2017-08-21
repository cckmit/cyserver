package com.cy.mobileInterface.contact.service;


import com.cy.base.entity.Message;


public interface mContactService {
	
	 void QueryList(Message message, String content);
	 
	 void getById(Message message, String content);
	 
	 void reply(Message message, String content);
	 
	 void delete(String ids);
	 
}

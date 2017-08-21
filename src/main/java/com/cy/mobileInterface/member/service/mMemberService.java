package com.cy.mobileInterface.member.service;

import com.cy.base.entity.Message;

public interface mMemberService {

 void QueryList(Message message, String content);
	 
	 void getById(Message message, String content);
	 
	 void mDelete(Message message, String content);
	 
	 void mUupdate(Message message, String content);
	 

	 void mClean(Message message, String content);
	 
	 void QueryChat(Message message, String content);
}

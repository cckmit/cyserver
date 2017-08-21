package com.cy.shortmessage;

import java.util.List;
import java.util.Map;

public abstract class ShortMessage {
	
	public abstract String sendSMS(String url,String account,String pswd,Map<String, Object> map) throws Exception;
	
	public abstract List<com.cy.shortmessage.entity.StatusReport> recvStatusReport(String account, String pswd) throws Exception;
}

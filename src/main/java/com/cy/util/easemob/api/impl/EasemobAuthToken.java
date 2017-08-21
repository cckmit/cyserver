package com.cy.util.easemob.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cy.util.easemob.api.AuthTokenAPI;
import com.cy.util.easemob.api.EasemobRestAPI;
import com.cy.util.easemob.comm.wrapper.BodyWrapper;
import com.cy.util.easemob.comm.constant.HTTPMethod;
import com.cy.util.easemob.comm.helper.HeaderHelper;
import com.cy.util.easemob.comm.wrapper.HeaderWrapper;
import com.cy.util.easemob.comm.body.AuthTokenBody;

public class EasemobAuthToken extends EasemobRestAPI implements AuthTokenAPI{
	
	public static final String ROOT_URI = "/token";
	
	private static final Logger log = LoggerFactory.getLogger(EasemobAuthToken.class);
	
	@Override
	public String getResourceRootURI() {
		return ROOT_URI;
	}

	public Object getAuthToken(String clientId, String clientSecret) {
		String url = getContext().getSeriveURL() + getResourceRootURI();
		BodyWrapper body = new AuthTokenBody(clientId, clientSecret);
		HeaderWrapper header = HeaderHelper.getDefaultHeader();
		
		return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
	}
}

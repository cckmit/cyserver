package com.cy.util.easemob.api;

import com.cy.util.easemob.comm.ClientContext;

public abstract class EasemobRestAPI implements RestAPI {
	
	private ClientContext context;
	
	private RestAPIInvoker invoker;

	public abstract String getResourceRootURI();
	
	public ClientContext getContext() {
		return context;
	}

	public void setContext(ClientContext context) {
		this.context = context;
	}

	public RestAPIInvoker getInvoker() {
		return invoker;
	}

	public void setInvoker(RestAPIInvoker invoker) {
		this.invoker = invoker;
	}
	
	
}

package com.cy.core.clientrelease.action;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.clientrelease.entity.Client;
import com.cy.core.clientrelease.entity.ClientModel;
import com.cy.core.clientrelease.service.ClientService;
import com.cy.system.Global;

@Namespace("/clientRelease")
@Action("clientReleaseAction")
public class ClientReleaseAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ClientReleaseAction.class);
	
	@Autowired
	private ClientService clientService;

	/*
	 * 访问地址
	 * http://127.0.0.1:8080/clientRelease/clientReleaseAction!doNotNeedSessionAndSecurity_getNewestVersion.action
	 *
	 * 返回最新版本信息
	 */
	public void doNotNeedSessionAndSecurity_getNewestVersion()
	{	
		Message message = new Message();
		String checkcode = getRequest().getParameter("checkcode");
		
		if (checkcode == null || !checkcode.equals(Global.client_release_checkcode)) {
			message.setMsg("checkcode错误!");
			message.setSuccess(false);
			super.writeJson(message);
			return;
		}
		
		ClientModel clientModel = new ClientModel();
		clientModel.setStart(0);
		clientModel.setEnd(1000);
		List<Client> list = clientService.selectClient(clientModel);
		if(list==null||list.size()==0){
			message.setMsg("数据库无最新版本!");
			message.setSuccess(false);
			super.writeJson(message);
			return;
		}

		Collections.sort(list, new Comparator<Client>() {
			public int compare(Client user1, Client user2) {
				 return user2.getCreateTime().compareTo(user1.getCreateTime());
			}		
		});
		
		//url utf-8 encoding
		try {
			list.get(0).setUrl(URLEncoder.encode(list.get(0).getUrl(), "utf-8").replace("*", "*").replace("~", "~").replace("+", " "));
		} catch (Exception e) {
			
			logger.error(e, e);
			message.setMsg("no version to get");
			message.setSuccess(false);
			super.writeJson(message);
		}
		
		super.writeJson(list.get(0));
	}
}

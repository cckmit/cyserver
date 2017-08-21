package com.cy.core.clientrelease.action;

import com.cy.core.clientrelease.entity.Client;
import com.cy.core.share.entity.Share;
import com.cy.core.share.service.ShareService;
import com.cy.system.Global;
import com.cy.util.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;
import com.cy.base.action.BaseAction;
import com.cy.core.clientrelease.entity.ClientModel;
import com.cy.core.clientrelease.entity.Json;
import com.cy.core.clientrelease.service.ClientService;

import java.io.IOException;

@Namespace("/client")
@Action(value = "clientAction",results={@Result(name="showupdate",location="/page/admin/clientrelease/editClient.jsp"),
		@Result(name = "505", location = "/error/500.jsp")})
public class ClientAction extends BaseAction implements ModelDriven<ClientModel> {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ClientAction.class);

	private ClientModel clientModel = new ClientModel();
	private ClientService clientService;

	public ClientModel getModel() {
		return clientModel;
	}

	public ClientModel getClientModel() {
		return clientModel;
	}

	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
	}

	public ClientService getClientService() {
		return clientService;
	}

	@Autowired
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	@Autowired
	private ShareService shareService;

	public void getClient(){
		super.writeJson(clientService.dataGridClient(clientModel));
	}
	
	public void addClient(){
		Json j = new Json();
		try{
			if(clientService.addClient(clientModel)>0){
				j.setMsg("新增成功");
				j.setSuccess(true);
			}else{
				j.setMsg("新增失败");
				j.setSuccess(false);
			}
		}catch (Exception e) {
			logger.error(e, e);
			j.setMsg("新增失败");
			j.setSuccess(false);
		}
		super.writeJson(j);
	}
	
	public String showUpdate(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("id");
		clientModel=clientService.selectById(Integer.parseInt(id));
		return "showupdate";
	}
	
	public void updateClient(){
		Json j = new Json();
		try{
			if(clientService.updateClient(clientModel)>0){
				j.setMsg("修改成功");
				j.setObj(clientModel);
				j.setSuccess(true);
			}else{
				j.setMsg("修改失败");
				j.setSuccess(false);
			}
		}catch (Exception e) {
			logger.error(e, e);
			j.setMsg("修改失败");
			j.setSuccess(false);
		}
		super.writeJson(j);
	}

	/**
	 * 下载最新或当前版本APP
     */
	public void doNotNeedSessionAndSecurity_downloadApp(){
		Client client = clientService.selectNewAppVersion() ;
		try {
			if(client != null && StringUtils.isNotBlank(client.getUrl())) {
				//访问下载地址，下载量加一

				//总下载量加一
				shareService.updateAndroidDownloads();

				//版本下载量加一
				clientService.updateDownloadsAddOne(client);

				getResponse().sendRedirect(client.getUrl()) ;
			} else {
				getResponse().sendRedirect("/error/500.jsp") ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

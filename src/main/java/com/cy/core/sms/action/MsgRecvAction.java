package com.cy.core.sms.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.core.sms.entity.MsgRecv;
import com.cy.core.sms.service.MsgRecvService;

@Namespace("/msgRecv")
@Action(value = "msgRecvAction")
public class MsgRecvAction extends AdminBaseAction {
	@Autowired
	private MsgRecvService msgRecvService;

	private MsgRecv msgRecvModel;

	public MsgRecv getMsgRecvModel() {
		return msgRecvModel;
	}

	public void setMsgRecvModel(MsgRecv msgRecvModel) {
		this.msgRecvModel = msgRecvModel;
	}

	public void getMsgRecv() {
		HttpServletRequest request = ServletActionContext.getRequest();
//		String messagegroup = request.getParameter("messagegroup");
		String sendtime = request.getParameter("sendtime");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date starttime = format.parse(sendtime);
			msgRecvModel.setRecvtime(starttime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
//		msgRecvModel.setMessagegroup(messagegroup);
		super.writeJson(msgRecvService.dataGridMsgRecv(msgRecvModel));
	}

	public MsgRecvService getMsgRecvService() {
		return msgRecvService;
	}

	@Autowired
	public void setMsgRecvService(MsgRecvService msgRecvService) {
		this.msgRecvService = msgRecvService;
	}

}

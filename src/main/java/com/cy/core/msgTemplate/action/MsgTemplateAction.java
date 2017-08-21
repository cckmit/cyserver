package com.cy.core.msgTemplate.action;

import com.cy.common.utils.StringUtils;
import com.cy.core.smsAccount.entity.SmsAccount;
import com.cy.core.smsAccount.service.SmsAccountService;
import com.cy.smscloud.entity.Header;
import com.cy.smscloud.http.HttpClientBase;
import com.cy.smscloud.http.SmsCloudHttpUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.msgTemplate.entity.MsgTemplate;
import com.cy.core.msgTemplate.service.MsgTemplateService;
import com.cy.core.user.entity.User;
import com.opensymphony.xwork2.ActionContext;

@Namespace("/msgTemplate")
@Action(value = "msgTemplateAction", results = {
		@Result(name = "initMsgTemplateUpdate", location = "/page/admin/msgTemplate/editMsgTemplate.jsp"),
		@Result(name = "viewMsgTemplate", location = "/page/admin/msgTemplate/viewMsgTemplate.jsp") })
public class MsgTemplateAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(MsgTemplateAction.class);

	@Autowired
	private MsgTemplateService msgTemplateService;

	@Autowired
	private SmsAccountService smsAccountService;

	private MsgTemplate msgTemplate;

	public MsgTemplate getMsgTemplate() {
		return msgTemplate;
	}

	public void setMsgTemplate(MsgTemplate msgTemplate) {
		this.msgTemplate = msgTemplate;
	}

	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		if (msgTemplate != null) {
			map.put("msgTemplateTitle", msgTemplate.getMsgTemplateTitle());
		}

//		User user = (User) ActionContext.getContext().getSession().get("user");
//		if(user.getRole().getSystemAdmin() != 1 ) {
//			map.put("msgTemplateType", "0") ;
//		}
		super.writeJson(msgTemplateService.dataGrid(map));
	}

	public void save() {
		Message message = new Message();
		try {
			MsgTemplate check = msgTemplateService.selectByTitle(msgTemplate);
			if (check == null) {
				Pattern pattern = Pattern.compile("\\$\\{@\\}");
//				Pattern pattern = Pattern.compile("\\$\\{\\d+\\}");
				Matcher matcher = pattern.matcher(msgTemplate.getMsgTemplateContent());
				int number = 0;
				while (matcher.find()) {
					number++;
				}
//				User user = getUser();
//				if(user != null && user.getDeptId() > 0){
//					SmsAccount smsAccount = smsAccountService.getByAlumniId(String.valueOf(user.getDeptId()));
//					if(smsAccount != null && StringUtils.isNotBlank(smsAccount.getAccount()) && StringUtils.isNotBlank(smsAccount.getPassword())){
//						Map<String,String> paramMap = new HashMap<String,String>() ;
//						paramMap.put("code","122");
//						paramMap.put("title",msgTemplate.getMsgTemplateTitle());
//						paramMap.put("templateValue",msgTemplate.getMsgTemplateContent());
//						paramMap.put("status",msgTemplate.getMsgTemplateStatus());
//						paramMap.put("notifyUrl","www.baidu.com") ;
//						paramMap.put("resultUrl","fanyi.baidu.com") ;
//						Header header = new Header() ;
//						header.setAppAccount(smsAccount.getAccount());
//						header.setAppKey(smsAccount.getPassword());
//						String sendUrl = HttpClientBase.SMS_API_URL;
//						Map<String, String> resultMap = SmsCloudHttpUtils.saveAppTemplate(sendUrl,header,paramMap);
//						if( resultMap != null && resultMap.size() > 0){
							msgTemplate.setMsgTemplateParamNumber(number);
							msgTemplateService.save(msgTemplate);
							message.setMsg("保存成功");
							message.setSuccess(true);
//						}else{
//							message.setMsg("保存到短信服务器失败");
//							message.setSuccess(false);
//						}
//					}else{
//						message.setMsg("当前组织没有短信服务帐号");
//						message.setSuccess(false);
//					}
//				}else{
//					message.setMsg("无法获取用户信息");
//					message.setSuccess(false);
//				}

			} else {
				message.setMsg("保存失败,短信模板名称重复");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void update() {
		Message message = new Message();
		try {
			MsgTemplate check = msgTemplateService.selectByTitle(msgTemplate);
			if (check == null) {
				Pattern pattern = Pattern.compile("\\$\\{@\\}");
//				Pattern pattern = Pattern.compile("\\$\\{\\d+\\}");
				Matcher matcher = pattern.matcher(msgTemplate
						.getMsgTemplateContent());
				int number = 0;
				while (matcher.find()) {
					number++;
				}
				msgTemplate.setMsgTemplateParamNumber(number);
				msgTemplateService.update(msgTemplate);
				message.setMsg("保存成功");
				message.setSuccess(true);
			} else {
				message.setMsg("保存失败,短信模板名称重复");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete() {
		Message message = new Message();
		try {
			msgTemplateService.delete(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public String doNotNeedSessionAndSecurity_initMsgTemplateUpdate() {
		msgTemplate = msgTemplateService.selectById(id);
		return "initMsgTemplateUpdate";
	}

	public void doNotNeedSessionAndSecurity_getAll() {
		super.writeJson(msgTemplateService.selectAll());
	}

	public String getById() {
		msgTemplate = msgTemplateService.selectById(id);
		return "viewMsgTemplate";
	}

	/**
	 * 通过Id 获取模板信息（JSON字符串）
	 */
	public void getTemplateById() {
		msgTemplate = msgTemplateService.selectById(id);
		super.writeJson(msgTemplate) ;
 	}
	
	/**
	 * 获取系统模板
	 */
	public void findSystemTemplate() {
		Map<String, Object> map = new HashMap<String, Object>() ;
		map.put("msgTemplateType", "1") ;
		List<MsgTemplate> list = msgTemplateService.selectList(map) ;
		super.writeJson(list) ;
	}
}

package com.cy.core.emailTemplate.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.dict.entity.Dict;
import com.cy.core.emailTemplate.entity.EmailTemplate;
import com.cy.core.emailTemplate.service.EmailTemplateService;

@Namespace("/page/admin/emailTemplate")
@Action(value = "emailTemplateAction", results = { @Result(name = "add", location = "/page/admin/emailTemplate/add.jsp"),
		@Result(name = "view", location = "/page/admin/emailTemplate/view.jsp"), @Result(name = "edit", location = "/page/admin/emailTemplate/edit.jsp") })
public class EmailTemplateAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmailTemplateAction.class);

	@Autowired
	private EmailTemplateService emailTemplateService;

	private EmailTemplate emailTemplate;

	private ArrayList<Dict> templateVariableList;

	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("page", page);
		if (emailTemplate != null) {
			map.put("templateName", emailTemplate.getTemplateName());
		}

		super.writeJson(emailTemplateService.dataGrid(map));
	}

	public void save() {
		Message message = new Message();
		try {
			EmailTemplate emailTemplateCheck = emailTemplateService.selectByTemplateName(emailTemplate);
			if (emailTemplateCheck == null) {
				Pattern pattern = Pattern.compile("\\$\\{@\\}");
//				Pattern pattern = Pattern.compile("\\$\\{\\d+\\}");
				Matcher matcher = pattern.matcher(emailTemplate.getTemplateContent());
				int number = 0;
				while (matcher.find()) {
					number++;
				}
				emailTemplate.setTemplateType(number);
				emailTemplateService.save(emailTemplate);
				message.setMsg("操作成功");
				message.setSuccess(true);
			} else {
				message.setMsg("保存失败,邮件模板名称重复");
				message.setSuccess(false);
			}

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("操作失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete() {
		Message message = new Message();
		try {
			emailTemplateService.delete(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void update() {
		Message message = new Message();
		try {
			EmailTemplate emailTemplateCheck = emailTemplateService.selectByTemplateName(emailTemplate);
			if (emailTemplateCheck == null) {
				Pattern pattern = Pattern.compile("\\$\\{@\\}");
//				Pattern pattern = Pattern.compile("\\$\\{\\d+\\}");
				Matcher matcher = pattern.matcher(emailTemplate.getTemplateContent());
				int number = 0;
				while (matcher.find()) {
					number++;
				}
				emailTemplate.setParamNum(number+"");
//				emailTemplate.setTemplateType(number);
				emailTemplateService.update(emailTemplate);
				message.setMsg("操作成功");
				message.setSuccess(true);
			} else {
				message.setMsg("保存失败,邮件模板名称重复");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("操作失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void getAllTemplateVariable() {

		super.writeJson(emailTemplateService.getAllTemplateVariable());

	}

	public void getById() {
		emailTemplate = emailTemplateService.selectById(id);
		super.writeJson(emailTemplate);
	}

	public String initAdd() {
		templateVariableList = (ArrayList<Dict>) emailTemplateService.getAllTemplateVariable();
		return "add";
	}

	public void doNotNeedSessionAndSecurity_initUpdate() {
		emailTemplate = emailTemplateService.selectById(id);
		super.writeJson(emailTemplate);
	}

	/**
	 * 得到所有的模板
	 * 
	 */
	public void doNotNeedSecurity_getAllTemplate() {
		List<EmailTemplate> emailTemplate = emailTemplateService.getAllTemplate();
		super.writeJson(emailTemplate);
	}

	/**
	 * 通过ID得到指定模板
	 * 
	 */
	public void doNotNeedSecurity_getTemplateById() {
		emailTemplate = emailTemplateService.selectById(id);
		logger.info(JSON.toJSONString(emailTemplate));
		super.writeJson(emailTemplate);
	}

	public EmailTemplateService getEmailTemplateService() {
		return emailTemplateService;
	}

	public EmailTemplate getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplateService(EmailTemplateService emailTemplateService) {
		this.emailTemplateService = emailTemplateService;
	}

	public void setEmailTemplate(EmailTemplate emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	public ArrayList<Dict> getTemplateVariableList() {
		return templateVariableList;
	}

	public void setTemplateVariableList(ArrayList<Dict> templateVariableList) {
		this.templateVariableList = templateVariableList;
	}

}

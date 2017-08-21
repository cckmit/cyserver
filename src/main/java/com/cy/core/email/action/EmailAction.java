package com.cy.core.email.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.email.entity.Email;
import com.cy.core.email.entity.EmailRecipient;
import com.cy.core.email.service.EmailService;
import com.cy.util.RoleUtil;

@Namespace("/page/admin/email")
@Action(value = "emailAction", results = { @Result(name = "view", location = "/page/admin/email/view.jsp"),
		@Result(name = "edit", location = "/page/admin/email/edit.jsp") })
public class EmailAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(EmailAction.class);

	@Autowired
	private EmailService emailService;

	private Email email;

	private String emailType1;

	private String[] emailParam;

	private String emailTemplateContent;

	private String fromPassword;

	private String[] fj;

	private String bccAddress;

	private String ccAddress;

	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("page", page);
		if (email != null) {
			map.put("emailSubject", email.getEmailSubject());
			map.put("sentStatus", email.getSentStatus());
			map.put("sentStartTime", email.getSentStartTime());
			map.put("sentEndTime", email.getSentEndTime());
		}
//		if(getUser().getRole().getSystemAdmin()!=1){
		if(getUser().getDeptId() != 0 && getUser().getDeptId() != 1){
			map.put("staffId", getUser().getUserId());
		}
		super.writeJson(emailService.dataGrid(map));
	}

	public void saveOld() {
		Message message = new Message();
		try {
			if (emailType1.equals("模板邮件")) {
				if (emailParam != null) {
					for (int i = 0; i < emailParam.length; i++) {
						emailTemplateContent = emailTemplateContent.replace("${" + i + "}", emailParam[i]);
					}
				}
				email.setEmailText(emailTemplateContent);
			}
			email.setFj(fj);
			email.setStaffId(getUser().getUserId());
			email.setFromPassword(fromPassword);
			email.setCcAddress(ccAddress);
			email.setBccAddress(bccAddress);
			emailService.save(email);
			message.setMsg("发送成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("发送失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	
	public void save() {
		Message message = new Message();
		try {
			if (emailType1.equals("模板邮件")) {
				if (emailParam != null) {
					for (int i = 0; i < emailParam.length; i++) {
						emailTemplateContent = emailTemplateContent.replaceFirst("\\$\\{@\\}", emailParam[i]);
//						emailTemplateContent = emailTemplateContent.replace("${" + i + "}", emailParam[i]);
					}
				}
				email.setEmailText(emailTemplateContent);
			}
			email.setFj(fj);
			email.setStaffId(getUser().getUserId());
			email.setFromPassword(fromPassword);
			email.setCcAddress(ccAddress);
			email.setBccAddress(bccAddress);
			emailService.save(email);
			message.setMsg("发送成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("发送失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete() {
		Message message = new Message();
		try {
			emailService.delete(ids);
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
			emailService.update(email);
			message.setMsg("操作成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("操作失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void getById() {
		email = emailService.selectById(id);
		super.writeJson(email);
	}

	public String initUpdate() {
		email = emailService.selectById(id);
		return "edit";
	}

	/**
	 * 得到所有的标签
	 * 
	 */
	public void doNotNeedSecurity_getAllUserList() {
		// List<Map<String, String>> userList =
		// emailService.selectAllUsersList();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("page", page);

		DataGrid<EmailRecipient> dataGrid = emailService.selectAllUsersList(map);

		super.writeJson(dataGrid);
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getEmailType1() {
		return emailType1;
	}

	public void setEmailType1(String emailType1) {
		this.emailType1 = emailType1;
	}

	public String[] getEmailParam() {
		return emailParam;
	}

	public void setEmailParam(String[] emailParam) {
		this.emailParam = emailParam;
	}

	public String getEmailTemplateContent() {
		return emailTemplateContent;
	}

	public void setEmailTemplateContent(String emailTemplateContent) {
		this.emailTemplateContent = emailTemplateContent;
	}

	public String getFromPassword() {
		return fromPassword;
	}

	public void setFromPassword(String fromPassword) {
		this.fromPassword = fromPassword;
	}

	public String[] getFj() {
		return fj;
	}

	public void setFj(String[] fj) {
		this.fj = fj;
	}

	public String getBccAddress() {
		return bccAddress;
	}

	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}

	public String getCcAddress() {
		return ccAddress;
	}

	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

}

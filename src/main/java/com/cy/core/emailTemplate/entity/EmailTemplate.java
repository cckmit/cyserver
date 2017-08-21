package com.cy.core.emailTemplate.entity;

import java.io.Serializable;
import java.util.Date;

public class EmailTemplate  implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 电子邮件模板实体
	 * 
	 * 
	 */
	
	private long templateId; //序列ID
	
	private int templateType; //模板类型
	private String templateName; //模板名称
	private String templateContent; //模板内容
	private Date createDate; //创建时间
	

	private String paramNum; //参数个数
	public long getTemplateId() {
		return templateId;
	}
	public int getTemplateType() {
		return templateType;
	}
	public String getTemplateName() {
		return templateName;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}
	public void setTemplateType(int templateType) {
		this.templateType = templateType;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getParamNum() {
		return paramNum;
	}
	public void setParamNum(String paramNum) {
		this.paramNum = paramNum;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmailTemplate [templateId=<");
		builder.append(templateId);
		builder.append(">, templateType=<");
		builder.append(templateType);
		builder.append(">, templateName=<");
		builder.append(templateName);
		builder.append(">, templateContent=<");
		builder.append(templateContent);
		builder.append(">, createDate=<");
		builder.append(createDate);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}

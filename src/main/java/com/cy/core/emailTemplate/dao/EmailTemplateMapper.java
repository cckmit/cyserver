package com.cy.core.emailTemplate.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.dict.entity.Dict;
import com.cy.core.emailTemplate.entity.EmailTemplate;


public interface EmailTemplateMapper 
{
	/**
	 * 存储
	 * 
	 * @param EmailTemplate emailTemplate 
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean save(EmailTemplate email);
	
	
	/**
	 * 更新
	 * 
	 * @param EmailTemplate emailTemplate 
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean update(EmailTemplate email);
	
	/**
	 * 获取消息总条数
	 * 
	 * @param Map<String, Object> map
	 * @return long
	 */
	long count(Map<String, Object> map);
	
	/**
	 * 获取列表
	 * 
	 * @param Map<String, Object> map
	 * @return List<Email>
	 */
	List<EmailTemplate> list(Map<String, Object> map);
	
	/**
	 * 删除
	 * 
	 * @param List<Long> list
	 */
	void delete(List<Long> list);
	
	
	/**
	 * 获取详情
	 * 
	 * @param map
	 * @return List<EmailTemplate>
	 */
	EmailTemplate selectById(long id);
	
	
	/**
	 * 获取所有模板
	 * 
	 * @return List<EmailTemplate>
	 */
	List<EmailTemplate> getAllTemplate();
	
	
	
	/**
	 * 获取所有模板变量
	 * 
	 * @return List<Dict>
	 */
	List<Dict> getAllTemplateVariable();
	
	EmailTemplate selectByTemplateName(EmailTemplate emailTemplate);
}

package com.cy.core.emailTemplate.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.dict.entity.Dict;
import com.cy.core.emailTemplate.entity.EmailTemplate;



public interface EmailTemplateService {
	

	/**
	 * 存储
	 * 
	 * @param EmailTemplate emailTemplate 
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean save(EmailTemplate emailTemplate);
	
	
	/**
	 * 更新
	 * 
	 * @param EmailTemplate emailTemplate 
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean update(EmailTemplate emailTemplate);
	
	/**
	 * 获取消息总条数
	 * 
	 * @param Map<String, Object> map
	 * @return long
	 */
	long count(Map<String, Object> map);
	
	
	/**
	 * 所有信息列表(带分页)
	 * 
	 * @param Map<String, Object> map
	 * @return DataGrid<EmailTemplate>
	 */
	DataGrid<EmailTemplate> dataGrid(Map<String, Object> map);
	
	/**
	 * 删除
	 * 
	 * @param String ids
	 */
	void delete(String ids);
	
	
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
	
	/**
	 * 检查模板名称的重复性
	 * 
	 * @param emailTemplate
	 * @return
	 */
	EmailTemplate selectByTemplateName(EmailTemplate emailTemplate);

}

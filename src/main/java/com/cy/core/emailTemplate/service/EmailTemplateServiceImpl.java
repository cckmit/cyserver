package com.cy.core.emailTemplate.service;


import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.dict.entity.Dict;
import com.cy.core.emailTemplate.dao.EmailTemplateMapper;
import com.cy.core.emailTemplate.entity.EmailTemplate;


@Service("emailTemplateService")
public class EmailTemplateServiceImpl implements EmailTemplateService {

	@Autowired
	private EmailTemplateMapper emailTemplateMapper;

	
	/**
	 * 存储
	 * 
	 * @param EmailTemplate emailTemplate 
	 * @return true，成功；false，失败；
	 * 
	 */
	public boolean save(EmailTemplate emailTemplate) {
		
		
		return emailTemplateMapper.save(emailTemplate);
	}

	/**
	 * 更新
	 * 
	 * @param EmailTemplate emailTemplate 
	 * @return true，成功；false，失败；
	 * 
	 */
	public boolean update(EmailTemplate emailTemplate) {
		return emailTemplateMapper.update(emailTemplate);
	}

	/**
	 * 获取消息总条数
	 * 
	 * @param Map<String, Object> map
	 * @return long
	 */
	public long count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return emailTemplateMapper.count(map);
	}

	
	/**
	 * 获取列表
	 * 
	 * @param Map<String, Object> map
	 * @return List<EmailTemplate>
	 */
	public DataGrid<EmailTemplate> dataGrid(Map<String, Object> map) {
		DataGrid<EmailTemplate> dataGrid = new DataGrid<EmailTemplate>();
		long total = emailTemplateMapper.count(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<EmailTemplate> list = emailTemplateMapper.list(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	
	/**
	 * 删除
	 * 
	 * @param String ids
	 */
	public void delete(String ids) {
		String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
		
		emailTemplateMapper.delete(list);
	}

	
	/**
	 * 获取详情
	 * 
	 * @param map
	 * @return List<EmailTemplate>
	 */
	public EmailTemplate selectById(long id) {
		return emailTemplateMapper.selectById(id);
	}

	
	/**
	 * 获取所有模板
	 * 
	 * @return List<EmailTemplate>
	 */
	public List<EmailTemplate> getAllTemplate() {
		List<EmailTemplate> emailTemplateList = emailTemplateMapper.getAllTemplate();
		return emailTemplateList;
	}

	

	/**
	 * 获取所有模板变量
	 * 
	 * @return List<Dict>
	 */
	public List<Dict> getAllTemplateVariable() {
		return emailTemplateMapper.getAllTemplateVariable();
	}

	@Override
	public EmailTemplate selectByTemplateName(EmailTemplate emailTemplate) {
		return emailTemplateMapper.selectByTemplateName(emailTemplate);
	}

	
}

package com.cy.core.msgTemplate.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.msgTemplate.entity.MsgTemplate;

public interface MsgTemplateService {
	void save(MsgTemplate msgTemplate);
	
	void update(MsgTemplate msgTemplate);
	
	void delete(String ids);
	
	MsgTemplate selectById(long msgTemplateId);
	
	List<MsgTemplate> selectAll();
	
	DataGrid<MsgTemplate> dataGrid(Map<String, Object> map);
	
	MsgTemplate selectByTitle(MsgTemplate msgTemplate);

	/**
	 * 通过条件查询短信模板
	 * @param map
	 * @return
	 */
	public List<MsgTemplate> selectList(Map<String, Object> map) ;
}

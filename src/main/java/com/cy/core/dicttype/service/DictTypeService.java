package com.cy.core.dicttype.service;

import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.dicttype.entity.DictType;


public interface DictTypeService {
	DataGrid<DictType> dataGridDictType(Map<String, Object> map);
	
	void addDictType(DictType dictType);
	
	void deleteDictType(long id);
	
	DictType selectById(String id);
	
	int updateDictType(DictType dictType);

	void findDictTypeList(Message message, String content);

	void findDictList(Message message, String content);

	String isRepetition(DictType dictType);
}

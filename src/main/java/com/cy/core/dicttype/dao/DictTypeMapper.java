package com.cy.core.dicttype.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.dicttype.entity.DictType;

public interface DictTypeMapper {
	int addDictType(DictType dictType);

	List<DictType> selectDictType(Map<String, Object> map);

	long countDcitType(Map<String, Object> map);

	DictType selectById(int id);

	int updateDictType(DictType dictType);

	int deleteDictType(long id);
	
	List<DictType> selectAll();
}

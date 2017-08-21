package com.cy.core.dict.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.dict.entity.Dict;

public interface DictService {
	DataGrid<Dict> dataGridDict(Map<String, Object> map);

	void addDict(Dict Dict);

	void deleteDict(long id);

	Dict selectDictById(String id);

	int updateDict(Dict Dict);

	List<Dict> selectByDictTypeValue(String dictTypeValue);
	
	List<Dict> selectByDictTypeId(long dictTypeId);

	String isRepetition(Dict dict);

	public String selectDictTypeIdByDictTypeValue(String dictTypeValue);

	List<Dict> getDictByDictTypeId(long dictTypeId);
}

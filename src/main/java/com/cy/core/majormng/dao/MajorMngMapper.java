package com.cy.core.majormng.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.majormng.entity.MajorMng;


public interface MajorMngMapper {
	
	List<MajorMng> queryMajMng(Map<String, Object> map);
	
	long rowsForQueryMajMng(Map<String, Object> map);

    void delMajMng(List<Long> list);
    
    
    
	/**
	 * 专业收集（APP端使用）
	 * 
	 * @param majorMng
	 * 
	 */
    void specialtyCollectionForApp(MajorMng majorMng);
    
}

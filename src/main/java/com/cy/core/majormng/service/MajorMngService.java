package com.cy.core.majormng.service;

import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.majormng.entity.MajorMng;

public interface MajorMngService {
	
	DataGrid<MajorMng> dataGrid(Map<String, Object> map);

    void delete(String ids);
    
    
	/**
	 * 专业收集（APP端使用）
	 * 
	 * @param majorMng
	 * 
	 */
    void specialtyCollectionForApp(MajorMng majorMng);
}

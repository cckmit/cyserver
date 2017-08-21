package com.cy.core.major.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.major.entity.Major;

public interface MajorService {
	
	DataGrid<Major> dataGrid(Map<String, Object> map);

	Major getById(long id);
	
    long countByName(String majorName);
    
    long countByIdName(Major major);

    void save(Major major);
    
    void update(Major major);

    void delete(String ids);
    
    List<Major> getMajor(String deptId);
}

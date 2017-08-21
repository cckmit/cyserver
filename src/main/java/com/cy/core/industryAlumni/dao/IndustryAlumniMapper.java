package com.cy.core.industryAlumni.dao;

import java.util.List;

import com.cy.core.industryAlumni.entity.IndustryAlumni;

public interface IndustryAlumniMapper {
	
	 /**
     * 查询所有行业校友会组织
     * 
     * @return
     */
    List<IndustryAlumni> selectAll();
    
    
}

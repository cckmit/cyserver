package com.cy.core.docount.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.docount.entity.DoCount;



public interface DoCountService
{
	
	DataGrid<DoCount> countClassByDept(Map<String, Object> map);
	
	DataGrid<DoCount> countCollegeByDept(Map<String, Object> map);
	
	List<DoCount>  getAllProvince();
	
	List<DoCount>  getAllCollege();
	
	List<DoCount>  getAllAge();
	
	List<DoCount>  getAllClass();
	
	List<DoCount> getAllBirthDate();
	
}

package com.cy.core.docount.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.docount.entity.DoCount;

public interface DoCountMapper
{
	List<DoCount> countByClass(Map<String, Object> map);
	
	long rowsForCountByClass(Map<String, Object> map);
	
	List<DoCount> countByCollege(Map<String, Object> map);
	
	long rowsForCountByCollege(Map<String, Object> map);

	List<DoCount> getAllProvince();
	
	List<DoCount> getAllCollege();
	
	List<DoCount> getAllAge();
	
	List<DoCount> getAllClass();
	
	List<DoCount> getAllBirthDate();
	
}

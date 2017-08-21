package com.cy.core.docount.service;


import java.util.List;
import java.util.Map;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.docount.dao.DoCountMapper;
import com.cy.core.docount.entity.DoCount;



@Service("doCountService")
public class DoCountServiceImpl implements DoCountService
{

	@Autowired
	private DoCountMapper doCountMapper;
	

	public DataGrid<DoCount> countClassByDept(Map<String, Object> map) {

		
		DataGrid<DoCount> dataGrid = new DataGrid<DoCount>();
		
		long total = doCountMapper.rowsForCountByClass(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		
		
		List<DoCount> list = doCountMapper.countByClass(map);
		
		dataGrid.setRows(list);
		return dataGrid;

		
	}


	public DataGrid<DoCount> countCollegeByDept(Map<String, Object> map) {
		
		DataGrid<DoCount> dataGrid = new DataGrid<DoCount>();
		
		long total = doCountMapper.rowsForCountByCollege(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		
		List<DoCount> list = doCountMapper.countByCollege(map);
		
		dataGrid.setRows(list);
		return dataGrid;
	}


	public List<DoCount>  getAllProvince() {
		
		
		List<DoCount> list = doCountMapper.getAllProvince();
		
		return list;
		
		
	}


	public List<DoCount> getAllCollege() {
		
		List<DoCount> list = doCountMapper.getAllCollege();
		
		return list;
		
	}


	public List<DoCount> getAllAge() {
		List<DoCount> list = doCountMapper.getAllAge();
		
		return list;
	}


	public List<DoCount> getAllClass() {
		List<DoCount> list = doCountMapper.getAllClass();
		
		return list;
	}


	public List<DoCount> getAllBirthDate() {
		
		List<DoCount> list = doCountMapper.getAllBirthDate();
		
		return list;
	}

	

}

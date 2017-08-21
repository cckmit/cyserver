package com.cy.core.majormng.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.majormng.dao.MajorMngMapper;
import com.cy.core.majormng.entity.MajorMng;

@Service("majorMngService")
public class MajorMngServiceImpl implements MajorMngService {
	@Autowired
    private MajorMngMapper majorMapper;
	
    public DataGrid<MajorMng> dataGrid(Map<String, Object> map) {
        DataGrid<MajorMng> dataGrid = new DataGrid<MajorMng>();
        long total = majorMapper.rowsForQueryMajMng(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<MajorMng> list = majorMapper.queryMajMng(map);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public void delete(String ids) {
    	String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
    	majorMapper.delMajMng(list);
    }


	/**
	 * 专业收集（APP端使用）
	 * 
	 * @param majorMng
	 * 
	 */
	@Override
	public void specialtyCollectionForApp(MajorMng majorMng) {
		majorMapper.specialtyCollectionForApp(majorMng);
	}

}

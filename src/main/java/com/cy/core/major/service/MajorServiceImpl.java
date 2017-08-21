package com.cy.core.major.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.major.dao.MajorMapper;
import com.cy.core.major.entity.Major;

@Service("majorService")
public class MajorServiceImpl implements MajorService {
	@Autowired
    private MajorMapper majorMapper;
	
    public DataGrid<Major> dataGrid(Map<String, Object> map) {
        DataGrid<Major> dataGrid = new DataGrid<Major>();
        long total = majorMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<Major> list = majorMapper.query(map);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public Major getById(long id) {
        return majorMapper.getById(id);
    }


	public long countByName(String majorName) {
		return majorMapper.countByName(majorName);
	}

	public long countByIdName(Major major) {
		return majorMapper.countByIdName(major);
	}

    public void save(Major major) {
        if (major == null)
            throw new IllegalArgumentException("major cannot be null!");

        majorMapper.addMajor(major);
        Major newMajor = majorMapper.getByName(major.getMajorName());
        String[] ids = major.getDeptIds().split(",");
		for (String deptId : ids)
		{
			if (deptId != null && !"".equals(deptId) && deptId.length() == 10)
			{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("majorId", newMajor.getMajorId());
				map.put("deptId", deptId);
				majorMapper.addMajorDept(map);
			}
		}
    }


    public void update(Major major) {
        if (major == null)
            throw new IllegalArgumentException("major cannot be null!");

        majorMapper.updateMajor(major);
        
        List<Long> list = new ArrayList<Long>();
        list.add(major.getMajorId());
        majorMapper.deleteMajorDept(list);
        
        String[] ids = major.getDeptIds().split(",");
		for (String deptId : ids)
		{
			if (deptId != null && !"".equals(deptId) && deptId.length() == 10)
			{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("majorId", major.getMajorId());
				map.put("deptId", deptId);
				majorMapper.addMajorDept(map);
			}
		}
        
    }


    public void delete(String ids) {
    	String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
    	majorMapper.deleteMajor(list);
    	majorMapper.deleteMajorDept(list);
    }


	@Override
    public List<Major> getMajor(String deptId) {
	    return majorMapper.getMajor(deptId);
    }

}

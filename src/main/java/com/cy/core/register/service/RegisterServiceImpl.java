package com.cy.core.register.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.register.dao.RegisterMapper;
import com.cy.core.register.entity.Register;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 2015/3/8.
 */
@Service("registerService")
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterMapper registerMapper;
    
    @Autowired
    private UserInfoMapper userInfoMapper;

    /*
    public DataGrid<Register> dataGrid(Map<String, Object> map) {
        DataGrid<Register> dataGrid = new DataGrid<Register>();
        long total = registerMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<Register> list = registerMapper.query(map);
        dataGrid.setRows(list);
        return dataGrid;
    }
    */
    
    
    public DataGrid<Register> dataGrid(Map<String, Object> map) {
        DataGrid<Register> dataGrid = new DataGrid<Register>();
        long total = registerMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<Register> list = registerMapper.query(map);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public Register getById(long id) {
    	Register item = registerMapper.getById(id);
        return item;
    }


    public void save(Register register) {
    	if (register.getUserId() != null && register.getUserId().length() > 0) {
			UserInfo userInfo = userInfoMapper.selectByUserId(register.getUserId());
			register.setX_school(userInfo.getSchoolName());
			register.setX_depart(userInfo.getDepartName());
			register.setX_grade(userInfo.getGradeName());
			register.setX_clazz(userInfo.getClassName());
			register.setX_major(userInfo.getMajorName());
			register.setX_name(userInfo.getUserName());
			register.setX_sex(userInfo.getSex());
		} else {
			register.setX_grade(register.getX_grade() + "级");
		}
        registerMapper.add(register);
    }


    public void update(Register register) {
        if (register == null)
            throw new IllegalArgumentException("register cannot be null!");

        registerMapper.update(register);
    }


    public void delete(String ids) {
    	String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
        registerMapper.delete(list);
    }


    public List<Register> selectAll() {
        return registerMapper.queryAll();
    }
}

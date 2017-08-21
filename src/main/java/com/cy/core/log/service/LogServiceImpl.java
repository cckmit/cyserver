package com.cy.core.log.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.log.dao.LogMapper;
import com.cy.core.log.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 12/20/16.
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;

    public DataGrid<Log> dataGrid(Map<String, Object> map){
        DataGrid<Log> dataGrid = new DataGrid<>();
        long total = logMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<Log> list = logMapper.selectLogs(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public Log getById(String id){
        Log log = logMapper.getById(id);
        return log;
    }

    public void delete(String ids){
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String id : array)
        {
            list.add(id);
        }
        logMapper.delete(list);
    }
}

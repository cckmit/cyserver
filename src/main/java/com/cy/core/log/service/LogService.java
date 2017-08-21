package com.cy.core.log.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.log.entity.Log;

import java.util.Map;

/**
 * Created by cha0res on 12/20/16.
 */
public interface LogService {
    DataGrid<Log> dataGrid(Map<String, Object> map);
    Log getById(String id);
    void delete(String ids);
}

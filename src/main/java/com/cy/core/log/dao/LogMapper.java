package com.cy.core.log.dao;

import com.cy.core.log.entity.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 12/19/16.
 */
public interface LogMapper {
    void saveLog(Log log);
    List<Log> selectLogs(Map<String, Object> map);
    Log getById(String id);
    long count(Map<String, Object> map);
    void delete(List<String> list);
}

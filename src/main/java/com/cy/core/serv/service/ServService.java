package com.cy.core.serv.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.serv.entity.ServComplaint;
import com.cy.core.serv.entity.Serv;
import com.cy.core.serv.entity.ServComment;


public interface ServService {
	
	DataGrid<Serv> dataGrid(Map<String, Object> map);

	Serv getById(long id);

    void save(Serv serv);
    
    long getNewId();
    
    void update(Serv serv);
    
    void savePic(Map<String, Object> map);
    
    void deletePic(long serviceId);
    
    List<String> getPicByServId(long serviceId);
    
    void saveReply(Map<String, Object> map);

    /**
     * 批量删除
     * @param id
     */
    void delete(String id);
    
    void deletex(String id);
    
    void audit(Serv serv);
    
    DataGrid<ServComment> dataGridForServComment(Map<String, Object> map);
    
    DataGrid<ServComplaint> dataGridForServComplaint(Map<String, Object> map);
    
    void handleStatus(Map<String, Object> map);
    
    String getRegionOfUser(long userId);
}

package com.cy.core.serv.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.serv.entity.ServComplaint;
import com.cy.core.serv.entity.Serv;
import com.cy.core.serv.entity.ServComment;



public interface ServMapper {
	
	
	List<Serv> query(Map<String, Object> map);

    long count(Map<String, Object> map);

    Serv getById(long id);

    void add(Serv serv);
    
    long getNewId();
    
    void update(Serv serv);
    
    void addPic(Map<String, Object> map);
    
    void deletePic(long serviceId);    

    void delete(List<Long> list);
    
    void deletex(List<Long> list);
    
    void audit(Serv serv);
    
    void addReply(Map<String, Object> map);
    
    List<ServComment> queryServComment(Map<String, Object> map);
    
    long countServComment(Map<String, Object> map);
    
    List<ServComplaint> queryServComplaint(Map<String, Object> map);
    
    long countServComplaint(Map<String, Object> map);
    
    void handleStatus(Map<String, Object> map);

    List<String> getPicByServId(long serviceId);
    
    String getRegionOfUser(long userId);
}

package com.cy.core.event.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.event.entity.*;
import com.cy.core.mobevent.entity.CyEvent;

public interface EventMapper {
	
	List<Event> query(Map<String, Object> map);

    long count(Map<String, Object> map);

    Event getById(String id);

    void add(Event event);

    void update(Event event);
    
    void resetNotification(Event event);

    void delete(List<String> list);

    void deleteBussAuthority(List<String> list);

    void undoDelete(String id);
    
    void audit(Event event);
    
    List<SignUserProfile> querySignUser(Map<String, Object> map);
    
    long countSignUser(Map<String, Object> map);
    
    List<EventBoard> queryEventBoard(Map<String, Object> map);
    
    long countEventBoard(Map<String, Object> map);
    
    List<EventBoardComment> queryEventBoardComment(Map<String, Object> map);
    
    long countEventBoardComment(Map<String, Object> map);
    
    List<Complaint> queryEventBoardComplaint(Map<String, Object> map);
    
    long countEventBoardComplaint(Map<String, Object> map);
      
    
    
    List<BoardComplaint> queryComplaint(Map<String, Object> map);
    
    long countComplaint(Map<String, Object> map);
    
    int handleBoardStatus(Map<String, Object> map);

    List<String> getPicByBoardId(long boardId);
    
    String getCommentByBoardId(long boardId);
    
    String getRegionOfUser(long userId);

    void saveBussAuthority(BussAuthority bussAuthority);

    //报名活动
    void saveSigner(Map<String, Object> map);

    //删除活动接口
    void removeEvent(Map<String, String> map);

    //获取名单
    List<Map<String, Object>> getMembers(Map<String, Object> map);

    //获取报名人数
    int getMemberCount(Map<String,String> map);

    //获取举报人数
    String getReportCount(Map<String,String> map);

    String selectLastInsertId();

    void setIsSignIn(Map<String, String> map);

    /**
     * 返校计划群ID更新
     */
    void updateEventGroupId(Map<String, String> map);

}

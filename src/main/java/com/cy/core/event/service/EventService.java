package com.cy.core.event.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.event.entity.BoardComplaint;
import com.cy.core.event.entity.Complaint;
import com.cy.core.event.entity.Event;
import com.cy.core.event.entity.EventBoard;
import com.cy.core.event.entity.EventBoardComment;
import com.cy.core.event.entity.SignUserProfile;

public interface EventService {
	
	DataGrid<Event> dataGrid(Map<String, Object> map);

	Event getById(String id);

    void save(Event event);

    void update(Event event);

    void updateEventGroupId(Map<String, String> map);

    void updateMobEventGroupId(Message message, String content);

    /**
     * 批量删除
     * @param id
     */
    void delete(String id);
   
    void undoDelete(String id);
    
    void audit(Event event);
    
    DataGrid<SignUserProfile> dataGridForSignUser(Map<String, Object> map);
    
    DataGrid<EventBoard> dataGridForEventBoard(Map<String, Object> map);
    
    DataGrid<EventBoardComment> dataGridForEventBoardComment(Map<String, Object> map);
    
    DataGrid<Complaint> dataGridForEventBoardComplaint(Map<String, Object> map);
    
    
    
    DataGrid<BoardComplaint> dataGridForComplaint(Map<String, Object> map);
    
    int handleBoardStatus(Map<String, Object> map);
    
    List<String> getPicByBoardId(long boardId);
    
    String getCommentByBoardId(long boardId);
    
    String getRegionOfUser(long userId);


    /**
     * 查询校友活动列表
     * @param message
     * @param content
     */
    void findEventList(Message message, String content);

    /**
     * 个人活动接口
     */
    void savePersonalEvent(Message message, String content);

    /**
     * 创建社团活动接口
     * @param message
     * @param content
     */
    void saveAssociationEvent(Message message, String content);
    /**
     * 活动详情接口+报名人列表
     */
    void showEventInfo(Message message, String content);

    /**
     * 加入活动接口
     */
    void joinEvent(Message message, String content);

    /**
     * 删除活动接口
     */
    void removeEvent(Message message, String content);

    /**
     * 获取活动报名人信息接口
     */
    void getInfoOfSigner(Message message, String content);

    /**
     * 获取活动报名人列表接口
     */
    void showEventMembers(Message message, String content);

    /**
     * 获取活动类型列表
     */
    void showEventTypeList(Message message, String content);

    /**
     * 退出活動接口
     * @param message
     * @param content
     */
    void cancelJoinedEvent(Message message, String content);

    void setIsSing(Message message,String content);
    
    /**
     * 填充活动列表信息
     * @param list
     */
    void fillEventList(List<Event> list) ;
    /**
     * 填充活动信息
     * @param event
     */
    void fillEvent(Event event) ;
}

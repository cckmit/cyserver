package com.cy.core.notify.dao;

import com.cy.core.notify.entity.NotifyRecord;

import java.util.List;
import java.util.Map;

public interface NotifyRecordMapper {
	
	List<NotifyRecord> query(NotifyRecord notifyRecord);

    long count(NotifyRecord notifyRecord);

    NotifyRecord getById(String id);

    void insert(NotifyRecord notifyRecord);

    void update(NotifyRecord notifyRecord);

    /**
     * 插入通知记录
     * @param notifyRecordList
     * @return
     */
    int insertAll(List<NotifyRecord> notifyRecordList);

    /**
     * 根据通知ID删除通知记录
     * @param notifyId 通知ID
     * @return
     */
    int deleteByNotifyId(String notifyId);

    List<NotifyRecord> getByUserId(Map map);
    NotifyRecord getByIdAndUserId(Map map);

    void setAllReaded(Map<String, Object> map);
    void deleteSystemNotify(Map<String, Object> map);

}

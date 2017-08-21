package com.cy.core.chatRecord.dao;

import com.cy.core.chatRecord.entity.ChatRecord;

import java.util.List;

public interface ChatRecordMapper {
	
	List<ChatRecord> query(ChatRecord chatRecord);

    long count(ChatRecord chatRecord);

    ChatRecord getById(String id);

    void update(ChatRecord chatRecord);

    /**
     * 根据ID 集合删除 联系人信息
     * @param list
     */
    /**
     * 根据ID 集合删除 联系人信息
     * @param list      删除id 集合
     */
    void deleteByIdList(List<String> list);
    
    /**
     * 根据ID 删除联系人信息
     * @param chatRecord
     */
    void delete(ChatRecord chatRecord);
}

package com.cy.core.chatDeptGroup.dao;

import com.cy.core.chatDeptGroup.entity.ChatDeptGroup;

import java.util.List;

public interface ChatDeptGroupMapper {
	
	List<ChatDeptGroup> query(ChatDeptGroup chatDeptGroup);

    long count(ChatDeptGroup chatDeptGroup);

    ChatDeptGroup getById(String id);

    void insert(ChatDeptGroup chatDeptGroup);

    void update(ChatDeptGroup chatDeptGroup);

    /**
     * 根据ID 集合删除 机构群组
     * @param list
     */
    /**
     * 根据ID 集合删除 机构群组
     * @param list      删除id 集合
     */
    void deleteByIdList(List<String> list);
    
    /**
     * 根据ID 删除机构群组
     * @param chatDeptGroup
     */
    void delete(ChatDeptGroup chatDeptGroup);

}

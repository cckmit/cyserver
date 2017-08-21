package com.cy.core.chatGroup.dao;

import com.cy.core.chatGroup.entity.ChatGroup;

import java.util.List;
import java.util.Map;

public interface ChatGroupMapper {
	
	List<ChatGroup> query(ChatGroup chatGroup);

    long count(ChatGroup chatGroup);

    ChatGroup getById(String id);

    void insert(ChatGroup chatGroup);

    void update(ChatGroup chatGroup);

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
     * @param chatGroup
     */
    void delete(ChatGroup chatGroup);

    /**
     * 查询满足条件的群组
     * @param chatGroup
     * @return
     */
    List<Map<String,Object>> queryGroupInterface(ChatGroup chatGroup) ;


    /**
     * 根据Id, 查询群组
     *
     */
    ChatGroup queryChatGroupById(String id);


    /**
     * 根据群组id, 更新del_flag,(删除)
     * @param id
     */
    void updateDelFlag(String id);

    /**
     * 更新GroupUser去除群组与用户的关联
     */
    void updateGroupUserDelFlag(Map<String,String> map);

    /**
     * 判断用户是否群组的群主或管理员
     * @param map
     * @return
     */
    String isGroupAdmin(Map<String,String> map) ;
}

package com.cy.core.chatContacts.dao;

import com.cy.core.chatContacts.entity.ChatContacts;
import com.cy.core.userProfile.entity.UserProfile;

import java.util.List;
import java.util.Map;

public interface ChatContactsMapper {
	
	List<ChatContacts> query(ChatContacts chatContacts);

    long count(ChatContacts chatContacts);

    ChatContacts getById(String id);

    void insert(ChatContacts chatContacts);

    void update(ChatContacts chatContacts);

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
     * @param chatContacts
     */
    void delete(ChatContacts chatContacts);

    /**
     * 查询好友信息数据
     * @param chatContacts
     * @return
     */
    List<Map<String,String>> queryContactsInterface(ChatContacts chatContacts) ;

    /**
     * 查询普通好友信息for241
     * @param map
     * @return
     */
    List<Map<String,String>> findContactsInfo(Map<String, String> map);

    /**
     * 真刪除好友
     * @param chatContacts
     */
    void theRealDelete(ChatContacts chatContacts);

    List<UserProfile> queryUserList(Map<String, Object> map);
    /**
     * 查询推荐好友信息
     * @param map
     * @return
     */
    List<String> getCommendFriendList(Map<String, Object> map);
}

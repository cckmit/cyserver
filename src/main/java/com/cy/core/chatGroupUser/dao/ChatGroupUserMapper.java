package com.cy.core.chatGroupUser.dao;

import com.cy.core.chatGroupUser.entity.ChatGroupUser;

import java.util.List;
import java.util.Map;

public interface ChatGroupUserMapper {
	
	List<ChatGroupUser> query(ChatGroupUser chatGroupUser);

    long count(ChatGroupUser chatGroupUser);

    ChatGroupUser getById(String id);

    void update(ChatGroupUser chatGroupUser);

    void insert(ChatGroupUser chatGroupUser);

    void insertMemeber(ChatGroupUser chatGroupUser);

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
     * @param chatGroupUser
     */
    void delete(ChatGroupUser chatGroupUser);

    /**
     * 查询普通群组好友信息数据
     * @param chatGroupUser
     * @return
     */
    List<Map<String,String>> queryGroupUserInterface(ChatGroupUser chatGroupUser) ;

    /**
     * 查询班级群组好友好友信息数据
     * @param chatGroupUser
     * @return
     */
    List<Map<String,String>> queryClassGroupUserInterface(ChatGroupUser chatGroupUser) ;


    /**
     * 查询班级好友信息4App
     */
    List<Map<String,String>> queryClassGroupUserForApp(ChatGroupUser chatGroupUser);

    /**
     * 查询普通群组好友信息4App
     */
    List<Map<String,String>> queryGroupUserForApp(ChatGroupUser chatGroupUser);

    /**
     * add by jiangling
     * @param chatGroupUser
     * @return
     */
    ChatGroupUser queryGroupUser(ChatGroupUser chatGroupUser);


    /**
     * add by jiangling
     * @param chatGroupUser
     */
    void updateDelFlag(ChatGroupUser chatGroupUser);

}

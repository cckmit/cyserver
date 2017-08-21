package com.cy.core.notify.dao;

import com.cy.core.notify.entity.Notify;

import java.util.List;
import java.util.Map;

public interface NotifyMapper {
	
	List<Notify> query(Notify notify);

    long count(Notify notify);

    Notify getById(String id);

    void insert(Notify notify);

    void update(Notify notify);

    /**
     * 根据ID 集合删除 推送内容信息
     * @param list      删除id 集合
     */
    void deleteByIdList(List<String> list);

    /**
     * 根据ID 删除推送内容信息
     * @param notify
     */
    void delete(Notify notify);

    /**
     * 查詢用戶的推送消息
     * @param map
     * @return
     */
    List<Notify> selectPersonalNotifies( Map<String, String> map );

    long countPersonalNotifies( Map<String, String> map );

}

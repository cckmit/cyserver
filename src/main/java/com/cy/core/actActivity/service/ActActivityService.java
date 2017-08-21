package com.cy.core.actActivity.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.actActivity.entity.ActActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2017/6/6.
 */
public interface ActActivityService {

    /**
     * 获取列表
     * @param map
     * @return
     */
    List<ActActivity> findList(Map<String, Object> map) ;
    /**
     * 获取总数
     * @param map
     * @return
     */
    Long findCount(Map<String, Object> map) ;

    DataGrid<ActActivity> dataGrid(Map<String, Object> map);

    List<ActActivity> activityList();

    ActActivity getById(String id);
    ActActivity save(ActActivity activity,String musics);
    ActActivity update(ActActivity activity,String musics);
    void delete(String ids);

    void activityDetail(Message message ,String content);
}

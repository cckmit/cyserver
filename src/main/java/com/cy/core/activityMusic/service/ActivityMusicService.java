package com.cy.core.activityMusic.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.activityMusic.entity.ActivityMusic;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2017/6/6.
 */
public interface ActivityMusicService {

    /**
     * 获取列表
     * @param map
     * @return
     */
    List<ActivityMusic> findList(Map<String, Object> map) ;
    /**
     * 获取总数
     * @param map
     * @return
     */
    Long findCount(Map<String, Object> map) ;

    DataGrid<ActivityMusic> dataGrid(Map<String, Object> map);

    List<ActivityMusic> activityMusicList();

    ActivityMusic getById(String id);
    ActivityMusic save(ActivityMusic activityMusic);
    ActivityMusic update(ActivityMusic activityMusic);
    void delete(String ids);
    //根据活动id删除
    void deleteByActivityId(@Param("activityId") String activityId);
}

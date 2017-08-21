package com.cy.core.activityMusic.dao;

import com.cy.core.actActivity.entity.ActActivity;
import com.cy.core.activityMusic.entity.ActivityMusic;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2017/6/6.
 */
public interface ActivityMusicMapper {

    //查出总数
    long selectCount(Map<String, Object> map);
    //查出所有
    List<ActivityMusic> selectAll();
    //limit查询
    List<ActivityMusic> selectList(Map<String, Object> map);
    //根据ID查询单个的详细信息
    ActivityMusic selectById(String id);

    //添加一条
    void insert(ActivityMusic activityMusic);

    //修改
    void update(ActivityMusic activityMusic);

    //批量修改del_flag
    void delete(List<String> list);

    //根据活动id删除
    void deleteByActivityId(@Param("activityId") String activityId);
}

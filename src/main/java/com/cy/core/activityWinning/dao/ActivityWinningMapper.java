package com.cy.core.activityWinning.dao;

import com.cy.core.activityWinning.entity.ActivityWinning;

import java.util.List;
import java.util.Map;

/**
 * Created by hp on 2017/6/7.
 */
public interface ActivityWinningMapper {

    //查出总数
    long selectCount(Map<String, Object> map);
    //查出所有
    List<ActivityWinning> selectAll();
    //limit查询
    List<ActivityWinning> selectList(Map<String, Object> map);
    //根据ID查询单个的详细信息
    ActivityWinning selectById(String id);

    //添加一条
    void insert(ActivityWinning activityWinning);

    //修改
    void update(ActivityWinning activityWinning);

    //批量修改del_flag
    void delete(List<String> list);
}

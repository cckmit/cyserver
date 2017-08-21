package com.cy.core.actActivity.dao;

import com.cy.core.actActivity.entity.ActActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2017/6/6.
 */
public interface ActActivityMapper {

    //查出总数
    long selectCount(Map<String, Object> map);
    //查出所有
    List<ActActivity> selectAll();
    //limit查询
    List<ActActivity> selectList(Map<String, Object> map);
    //根据ID查询单个的详细信息
    ActActivity selectById(String id);

    //添加一条
    void insert(ActActivity activity);

    //修改
    void update(ActActivity activity);

    //批量修改del_flag
    void delete(List<String> list);

}

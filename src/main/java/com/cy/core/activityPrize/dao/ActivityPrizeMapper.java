package com.cy.core.activityPrize.dao;

import com.cy.core.activityPrize.entity.ActivityPrize;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2017/6/6.
 */
public interface ActivityPrizeMapper {

    public long count(Map<String, Object> map);

    void insert(ActivityPrize activityPrize);

    void update(ActivityPrize activityPrize);

    ActivityPrize selectById(String id);

    List<ActivityPrize> selectList(Map<String, Object> map);

    //批量修改del_flag
    void delete(List<String> list);
}

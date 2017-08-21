package com.cy.core.activity.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.activity.entity.Activity;

public interface ActivityMapper {

    List<Activity> query(Map<String, Object> map);

    List<Activity> queryAll();

    long count(Map<String, Object> map);

    Activity getById(String id);

    void add(Activity activity);

    void update(Activity activity);

    void delete(List<Long> list);
}

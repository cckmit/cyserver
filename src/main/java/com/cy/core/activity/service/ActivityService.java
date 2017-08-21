package com.cy.core.activity.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.activity.entity.Activity;

public interface ActivityService {

    DataGrid<Activity> dataGrid(Map<String, Object> map);

    Activity getById(String id);

    void save(Activity activity);

    void update(Activity activity);

    void delete(String id);

    List<Activity> selectAll();

    void saveActivity(Message message, String content);

    void findActivityList(Message message, String content);

    void deleteActivity(Message message, String content);

    void getActivityById(Message message, String content);

    //DataGrid<Activity> getBackSchoolSign(Map<String, Object> map);

}

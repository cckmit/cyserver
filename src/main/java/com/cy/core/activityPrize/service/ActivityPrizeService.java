package com.cy.core.activityPrize.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.activityPrize.entity.ActivityPrize;

import java.util.Map;

/**
 * Created by niu on 2017/6/6.
 */
public interface ActivityPrizeService {

    DataGrid<ActivityPrize> dataGrid(Map<String, Object> map);
    void save(ActivityPrize activityPrize);

    ActivityPrize getById(String id);

    void delete(String ids);
}

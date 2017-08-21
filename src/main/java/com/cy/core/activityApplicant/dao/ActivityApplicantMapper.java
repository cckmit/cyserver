package com.cy.core.activityApplicant.dao;

import com.cy.core.activityApplicant.entity.ActivityApplicant;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2017/6/6.
 */
public interface ActivityApplicantMapper {

    public long count(Map<String, Object> map);

    void insert(ActivityApplicant activityApplicant);

    void update(ActivityApplicant activityApplicant);

    ActivityApplicant selectApplicantDetail(Map<String,Object> map);

    List<ActivityApplicant> selectList(Map<String, Object> map);

    void  updateIsWinning(String id);
}

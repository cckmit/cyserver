package com.cy.core.activityApplicant.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.activityApplicant.entity.ActivityApplicant;
import com.sun.tools.corba.se.idl.StringGen;

import java.util.Map;

/**
 * Created by niu on 2017/6/6.
 */
public interface ActivityApplicantService {

    DataGrid<ActivityApplicant> dataGrid(Map<String, Object> map);

    void save(ActivityApplicant activityApplicant);

    //报名接口
    void applicant(Message message, String content);

    //报名人列表
    void applicantList(Message message,String content);

    //获取报名人接口
    void applicantDetail(Message message, String content);
}

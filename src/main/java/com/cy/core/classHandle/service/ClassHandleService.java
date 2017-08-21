package com.cy.core.classHandle.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.classHandle.entity.ClassHandle;

import java.util.Map;

/**
 * Created by Cha0res on 2016/8/17.
 */
public interface ClassHandleService {
    //班级管理员修改申请
    void classAdminEditClassMates(Message message, String content);

    //班機管理員添加成員
    void classAdminAddClassmates(Message message, String content);

    //申请清单
    DataGrid<ClassHandle> selectByDeptId(Map<String, Object> map);

    ClassHandle selectById(String checkId);

    void updateUserInfo(Map<String, Object> map);

    void updateUserBaseInfo(Map<String, Object> map);

    void updateClassHandle(Map<String, Object> map);

}

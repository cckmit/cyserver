package com.cy.core.classHandle.dao;

import com.cy.core.classHandle.entity.ClassHandle;

import java.util.List;
import java.util.Map;

public interface ClassHandleMapper {
    void saveClassHandle(ClassHandle classHandle);

    long countByDeptId(Map<String, Object> map);

    List<ClassHandle> selectByDeptId(Map<String, Object> map);

    ClassHandle selectById(String id);

    void updateUserInfo(Map<String, Object> map);

    void updateUserBaseInfo(Map<String, Object> map);

    void updateClassHandle(Map<String, Object> map);
}

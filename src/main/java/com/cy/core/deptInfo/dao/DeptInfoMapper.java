package com.cy.core.deptInfo.dao;

import com.cy.core.dept.entity.Dept;
import com.cy.core.deptInfo.entity.DeptInfo;

import java.util.List;

/**
 * Created by jiangling on 7/14/16.
 */
public interface DeptInfoMapper {

    List<DeptInfo> selectAll(List<DeptInfo> list);

    void delete(String deptId);

    List<DeptInfo> selectAll2();

    void delete(List<String> list);

    void updateAliasName(String aliasName);

    List<DeptInfo> selectAlterTreeByUser(long userId);//lixun

    String getAcademyId(long deptId);

    List<DeptInfo> selectAcademyInfo(String academyId);

    List<DeptInfo> selectSchools();

    List<DeptInfo> selectElseList(String schoolId);

    /**
     * 通过院系编号获取院系数据
     * @param deptId
     * @return
     */
    DeptInfo findById(String deptId);

}

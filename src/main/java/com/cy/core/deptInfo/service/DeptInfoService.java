package com.cy.core.deptInfo.service;

import com.cy.base.entity.Message;
import com.cy.core.deptInfo.entity.DeptInfo;
import com.cy.util.PairUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangling on 7/14/16.
 */

public interface DeptInfoService {

    /**
     * 查询登录用户能查看的机构
     * @param list
     * @return
     */
    List<DeptInfo> selectAll(List<DeptInfo> list);

    void delete(String deptId);

    List<DeptInfo> selectAlterDeptTreeByUser( long userId );//lixun

    String getAcademyId(long deptId);

    List<DeptInfo> selectAcademyInfo(String academyId);

    void selectDepts(Message message,String content);

    /**
     * 判断两班级中相同的数据
     */
    PairUtil<String,PairUtil<List<PairUtil<String, PairUtil<String, String>>>,List<PairUtil<String, PairUtil<String, String>>>>> isSameOfTwoDept(String oldDeptId , String newDeptId) throws Exception ;

    /**
     * 迁移班级下校友数据到另一个班级下
     * @param oldDeptId 需要迁移数据的班级
     * @param newDeptId 数据迁移到的班级
     * @return
     */
    public String moveUserInfoFromDeptToOtherDept(String oldDeptId ,String newDeptId) throws Exception ;
}



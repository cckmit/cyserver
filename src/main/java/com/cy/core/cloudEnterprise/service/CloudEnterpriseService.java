package com.cy.core.cloudEnterprise.service;

import com.cy.base.entity.DataGrid;

import com.cy.core.cloudEnterprise.entity.CloudEnterprise;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 企业信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2016-1-6
 */
public interface CloudEnterpriseService {

    /**
     * 获取列表
     * @param map
     * @return
     */
    List<CloudEnterprise> findList(Map<String, Object> map) ;
    /**
     * 获取总数
     * @param map
     * @return
     */
    Long findCount(Map<String, Object> map) ;

    DataGrid<CloudEnterprise> dataGrid(Map<String, Object> map);

    List<CloudEnterprise> enterprisesList();

    CloudEnterprise getByCloudId(String id);
    CloudEnterprise save(CloudEnterprise cloudEnterprise);
    CloudEnterprise update(CloudEnterprise cloudEnterprise);

    Integer updateByCloudId(CloudEnterprise cloudEnterprise);
//    void auditEnterprise(String enterpriseId);
    void delete(String ids);

    void auditEnterprise(String enterpriseId);

    void syncTeamToSchoolByQuartz();

   void pushAuditFailByQuartz();

   void findApplyEnterpriseByQuartz();

    void findAuditEnterpriseByQuartz();

}

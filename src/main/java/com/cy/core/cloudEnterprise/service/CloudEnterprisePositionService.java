package com.cy.core.cloudEnterprise.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.cloudEnterprise.entity.CloudEnterprisePosition;
import com.cy.core.enterpriseJob.entity.EnterpriseJob;

import java.util.List;
import java.util.Map;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 招聘岗位</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-05-24
 */
public interface CloudEnterprisePositionService {

    /**
     * 获取列表
     * @param map
     * @return
     */
    List<CloudEnterprisePosition> findList(Map<String, Object> map) ;
    /**
     * 获取总数
     * @param map
     * @return
     */
    Long findCount(Map<String, Object> map) ;

    DataGrid<CloudEnterprisePosition> dataGrid(Map<String, Object> map);

    List<CloudEnterprisePosition> enterpriseJobList();

    CloudEnterprisePosition getById(String id);
    CloudEnterprisePosition save(CloudEnterprisePosition cloudEnterprisePosition);
    CloudEnterprisePosition update(CloudEnterprisePosition cloudEnterprisePosition);
    void delete(String ids);

}

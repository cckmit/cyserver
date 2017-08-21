package com.cy.core.cloudEnterprise.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.cloudEnterprise.entity.CloudEntrepreneur;

import java.util.Map;

/**
 * Created by niu on 2017/8/11.
 */
public interface CloudEntrepreneurService {

    DataGrid<CloudEntrepreneur> dataGrid(Map<String,Object> map);

    void update(CloudEntrepreneur cloudEntrepreneur);

    Integer audit(String userId,CloudEntrepreneur cloudEntrepreneur);

    Integer delete(String ids);

    /**
     * 获取校友家信息
     * @param id
     * @return
     */
    CloudEntrepreneur getById(String id) ;

    /**
     * 审核企业家
     * @param entrepreneur	企业家
     * @return
     */
    void auditEntrepreneur(CloudEntrepreneur entrepreneur) ;


    /**
     * 同步企业家审核给云平台
     * @param entrepreneur
     * @return
     */
    void syncEntrepreneur(CloudEntrepreneur entrepreneur) ;

    /**
     * 解除校友企业家【成未审核状态】
     * @param entrepreneur
     */
    void relieveEntrepreneur(CloudEntrepreneur entrepreneur) ;

    /**
     * 从企业家群组中移除
     * @param entrepreneur
     */
    void relieveEntrepreneurGroup(CloudEntrepreneur entrepreneur) ;
}

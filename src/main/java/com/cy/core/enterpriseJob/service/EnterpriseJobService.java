package com.cy.core.enterpriseJob.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
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
public interface EnterpriseJobService {

    /**
     * 获取列表
     * @param map
     * @return
     */
    List<EnterpriseJob> findList(Map<String,Object> map) ;
    /**
     * 获取总数
     * @param map
     * @return
     */
    Long findCount(Map<String,Object> map) ;

    DataGrid<EnterpriseJob> dataGrid(Map<String, Object> map);

    List<EnterpriseJob> enterpriseJobList();

    EnterpriseJob getById(String id);
    EnterpriseJob save(EnterpriseJob enterpriseJob);
    EnterpriseJob update(EnterpriseJob enterpriseJob);
    void delete(String ids);


    /***********************************************************************
     * 【校友企业】相关API（以下区域）
     * <p>
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     ***********************************************************************/

    /**
     * 查詢招聘岗位列表
     * @param message
     * @param content
     */
    void findEnterpriseJobList(Message message, String content);

    /**
     * 查詢招聘岗位詳情
     * @param message
     * @param content
     */
    void findEnterpriseJob(Message message, String content);
}

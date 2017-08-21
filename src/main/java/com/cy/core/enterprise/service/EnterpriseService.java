package com.cy.core.enterprise.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.enterprise.entity.Enterprise;

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
public interface EnterpriseService {

    /**
     * 获取列表
     * @param map
     * @return
     */
    List<Enterprise> findList(Map<String,Object> map) ;
    /**
     * 获取总数
     * @param map
     * @return
     */
    Long findCount(Map<String,Object> map) ;

    DataGrid<Enterprise> dataGrid(Map<String, Object> map);

    List<Enterprise> enterprisesList();

    Enterprise getById(String id);
    Enterprise save(Enterprise enterprise);
    Enterprise update(Enterprise enterprise);
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
     * 查詢企業列表
     * @param message
     * @param content
     */
    void findEnterpriseList(Message message, String content);

    /**
     * 查詢企業詳情包括產品列表
     * @param message
     * @param content
     */
    void findEnterprise(Message message, String content);


    /**
     * 查詢企業列表按照距离的长短倒叙排序
     * @param message
     * @param content
     */
    void findEnterpriseListAndOrderByDistenceDesc(Message message, String content);




}

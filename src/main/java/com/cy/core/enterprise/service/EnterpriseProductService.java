package com.cy.core.enterprise.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.enterprise.entity.EnterpriseProduct;

import java.util.Map;

/**
 * Created by cha0res on 1/6/17.
 */
public interface EnterpriseProductService {
    DataGrid<EnterpriseProduct> dataGridProduct(Map<String, Object> map);
    EnterpriseProduct getById(String id);
    int saveProduct(EnterpriseProduct enterpriseProduct);
    int updateProduct(EnterpriseProduct enterpriseProduct);
    int delete(String ids);

    /***********************************************************************
     * 【校友企业產品】相关API（以下区域）
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
     * 查询校友企业产品列表
     * @param message
     * @param content
     */
    void findEnterpriseProductList(Message message, String content);
    /**
     * 查询产品详情
     * @param message
     * @param content
     */
    void findEnterpriseProduct(Message message, String content);
}

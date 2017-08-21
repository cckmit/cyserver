package com.cy.core.enterprise.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.enterprise.entity.EnterpriseProduct;
import com.cy.core.enterprise.entity.EnterpriseTeam;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/15.
 */
public interface EnterpriseTeamService {

    DataGrid<EnterpriseTeam> dataGridProduct(Map<String, Object> map);
    EnterpriseTeam getById(String id);
    int saveProduct(EnterpriseTeam enterpriseTeam);
    int updateProduct(EnterpriseTeam enterpriseTeam);
    int delete(String ids);

    /***********************************************************************
     * 【校友企业成员】相关API（以下区域）
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
     * 查询校友企业成员列表
     * @param message
     * @param content
     */
    void findEnterpriseTeamList(Message message, String content);
}

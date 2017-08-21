package com.cy.core.dataMining.service;

import com.cy.base.entity.Message;
import com.cy.core.dataMining.entity.DataMining;

/**
 * Created by jiangling on 8/4/16.
 */
public interface DataMiningService {


    /***********************************************************************
     *
     * 【联系人】相关API（以下区域）
     *
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     *
     ***********************************************************************/

    /**
     *
     * @param message
     * @param content
     */
    public void savePhone(Message message, String content);
}

package com.cy.core.smsbuywater.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.smsAccount.entity.SmsAccount;
import com.cy.core.smsbuywater.entity.SmsBuyWater;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/17.
 */


public interface SmsBuyWaterService {

    /**
     * 获取列表
     * @param map
     * @return
     */
    public List<SmsBuyWater> findList(Map<String,Object> map) ;
    /**
     * 获取总数
     * @param map
     * @return
     */
    public Long findCount(Map<String,Object> map) ;

    DataGrid<SmsBuyWater> dataGrid(Map<String, Object> map);
    void saveSmsBuyWater(SmsBuyWater smsBuyWater);
    void deleteSmsBuyWater(String ids);
    void updateSmsBuyWater(SmsBuyWater smsBuyWater);
    SmsBuyWater getSmsBuyWaterById(String id);
    SmsBuyWater getSmsBuyWaterByOrderNum(String orderNo);

}

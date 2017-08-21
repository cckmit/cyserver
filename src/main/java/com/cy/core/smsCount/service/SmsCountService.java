package com.cy.core.smsCount.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.smsCount.entity.SmsCount;

import java.util.Map;

/**
 * Created by Cha0res on 2016/8/28.
 */
public interface SmsCountService {
    DataGrid<SmsCount> countSmsByTimeLine(Map<String, Object> map);
    Map<String, Object> countBuyTimeLine(Map<String, Object> map);
}

package com.cy.core.smsCount.dao;

import com.cy.core.smsCount.entity.SmsCount;

import java.util.List;
import java.util.Map;

/**
 * Created by Cha0res on 2016/8/28.
 */
public interface SmsCountMapper {
    //总数
    long countTotalByTimeLine(Map<String, Object> map);

    //成功数
    long countSuccessByTimeLine(Map<String, Object> map);
}

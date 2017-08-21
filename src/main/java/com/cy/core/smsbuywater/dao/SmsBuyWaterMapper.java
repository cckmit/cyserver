package com.cy.core.smsbuywater.dao;

import com.cy.core.smsbuywater.entity.SmsBuyWater;

import java.util.List;
import java.util.Map;
/**
 * Created by Administrator on 2016/11/17.
 */
public interface SmsBuyWaterMapper {

    //查出总数
    public Long countSmsBuyWater(Map<String, Object> map);
    //查出列表
    public List<SmsBuyWater> selectSmsBuyWater(Map<String, Object> map);
    //添加一条
    Long insert(SmsBuyWater smsBuyWater);
    //修改
    void update(SmsBuyWater smsBuyWater);
    //批量删除（逻辑）
    void delete(List<String> list);
    //批量删除（物理）
    void remove(List<String> list);
    //根据ID查询单个的详细信息
    public SmsBuyWater getSmsBuyWaterById(String id);

    SmsBuyWater getSmsBuyWaterByOrderNum(String orderNo);
}

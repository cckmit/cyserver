package com.cy.core.serviceNews.dao;

import com.cy.core.serviceNews.entity.ServiceNews;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public interface ServiceNewsMapper {
    //查出新闻的总数
    public Long countServiceNews(Map<String, Object> map);
    //查出新闻列表
    public List<ServiceNews> selectServiceNews(Map<String, Object> map);
    //添加一条新闻
    Long insert(ServiceNews serviceNews);
    //修改新闻
    void update(ServiceNews serviceNews);
    //批量删除新闻
    void delete(List<String> list);
    //根据ID查询单个新闻的详细信息
    public ServiceNews getServiceNewsById(String id);
    //设置幻灯片
    void setMobTypeList(Map<String, Object> map);

}

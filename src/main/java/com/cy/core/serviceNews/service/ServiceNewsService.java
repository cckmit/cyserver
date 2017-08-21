package com.cy.core.serviceNews.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.serviceNews.entity.ServiceNews;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public interface ServiceNewsService {

    /**
     * 获取新闻列表
     * @param map
     * @return
     */
    public List<ServiceNews> findList(Map<String,Object> map) ;
    /**
     * 获取新闻总数
     * @param map
     * @return
     */
    public Long findCount(Map<String,Object> map) ;

    DataGrid<ServiceNews> dataGrid(Map<String, Object> map);
    void saveServiceNews(ServiceNews serviceNews);
    void deleteServiceNews(String ids);
    void updateServiceNews(ServiceNews serviceNews);
    ServiceNews getServiceNewsById(String id);
    void setMobTypeList(String ids, String controlStr);
}

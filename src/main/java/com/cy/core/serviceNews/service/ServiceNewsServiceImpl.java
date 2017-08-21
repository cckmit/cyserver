package com.cy.core.serviceNews.service;

import com.cy.base.entity.DataGrid;
import com.cy.common.utils.StringUtils;
import com.cy.core.serviceNews.dao.ServiceNewsMapper;
import com.cy.core.serviceNews.entity.ServiceNews;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务新闻业务类
 */
@Service("serviceNewsService")
public class ServiceNewsServiceImpl implements ServiceNewsService {

    @Autowired
    private ServiceNewsMapper serviceNewsMapper;

    /**
     * 获取新闻列表
     * @param map
     * @return
     */
    public List<ServiceNews> findList(Map<String,Object> map) {
        List<ServiceNews> list=serviceNewsMapper.selectServiceNews(map);
        return list ;
    }
    /**
     * 获取新闻总数
     * @param map
     * @return
     */
    public Long findCount(Map<String,Object> map) {
        Long total=serviceNewsMapper.countServiceNews(map);
        return total ;
    }

    public  DataGrid<ServiceNews> dataGrid(Map<String, Object> map){
        DataGrid<ServiceNews> dataGrid=new  DataGrid<ServiceNews>();
        Long total=serviceNewsMapper.countServiceNews(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<ServiceNews> list=serviceNewsMapper.selectServiceNews(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public void saveServiceNews(ServiceNews serviceNews){
        serviceNews.preInsert();

        if(StringUtils.isBlank(serviceNews.getNewsUrl())) {
            // 自动生成URL
            HttpServletRequest request = ServletActionContext.getRequest();
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
            String url = basePath + "serviceNews/serviceNewsAction!doNotNeedSessionAndSecurity_getMobNew?newsId=" + serviceNews;
            serviceNews.setNewsUrl(url);
        }

        serviceNewsMapper.insert(serviceNews);
    }

    public void deleteServiceNews(String ids){
        String[] array=ids.split(",");
        List<String> list=new ArrayList<>();
        for (String id : array) {
            list.add(id);
        }
        serviceNewsMapper.delete(list);
    }
    public void updateServiceNews(ServiceNews serviceNews){
        serviceNews.preUpdate();
        serviceNewsMapper.update(serviceNews);
    }
    public ServiceNews getServiceNewsById(String id){
     return serviceNewsMapper.getServiceNewsById(id);
    }

    public void setMobTypeList(String ids, String controlStr) {
        Map<String, Object> map = new HashMap<String, Object>();

        String[] array = ids.split(",");

        map.put("list", array);
        map.put("controlStr", controlStr);

        serviceNewsMapper.setMobTypeList(map);
    }

}

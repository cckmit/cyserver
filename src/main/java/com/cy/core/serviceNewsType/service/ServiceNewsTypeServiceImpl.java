package com.cy.core.serviceNewsType.service;
import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import org.apache.commons.lang3.StringUtils;
import com.cy.core.serviceNews.dao.ServiceNewsMapper;
import com.cy.core.serviceNews.entity.ServiceNews;
import com.cy.core.serviceNewsType.dao.ServiceNewsTypeMapper;
import com.cy.core.serviceNewsType.entity.ServiceNewsType;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("serviceNewsTypeService")
public class ServiceNewsTypeServiceImpl implements ServiceNewsTypeService {

    @Autowired
    private ServiceNewsTypeMapper serviceNewsTypeMapper;

    @Autowired
    private ServiceNewsMapper serviceNewsMapper;

    //查询服务新闻栏目
    public List<ServiceNewsType> query(Map<String, Object> map){
        return serviceNewsTypeMapper.query(map);
    }

    //查询服务新闻栏目详情
    public ServiceNewsType getById(String id){
        return serviceNewsTypeMapper.getById(id);
    }

    //通过栏目名查询新闻栏目
    public ServiceNewsType getByName(String name){
        return serviceNewsTypeMapper.getByName(name);
    }

    //新增新闻栏目
    public void save(ServiceNewsType serviceNewsType){
        serviceNewsTypeMapper.save(serviceNewsType);
    }

    //更新新闻栏目
    public void update(ServiceNewsType serviceNewsType){
        serviceNewsTypeMapper.update(serviceNewsType);
    }


    /***
     * 服务新闻栏目接口
     */
    public void findServiceNewsTypeList(Message message, String content){
        if(StringUtils.isBlank(content)){
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);

        String serviceId = (String) map.get("serviceId");

        if(StringUtils.isBlank(serviceId)){
            message.setMsg("请提供服务id");
            message.setSuccess(false);
            return;
        }

        List<ServiceNewsType> list = serviceNewsTypeMapper.query(map);
//        if(list == null || list.size() < 1){
//            message.setMsg("该服务没有新闻栏目");
//            message.setSuccess(false);
//            return;
//        }
        message.setMsg("成功获取服务新闻栏目");
        message.setSuccess(true);
        message.setObj(list);
    }

    /***
     * 服务新闻列表接口
     */
    public void findServiceNewsList(Message message, String content){
        if(StringUtils.isBlank(content)){
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);

        String serviceId = (String) map.get("serviceId");
//        String channel = (String) map.get("channel");
//        String topnews = (String) map.get("topnews");

        if(StringUtils.isBlank(serviceId)){
            message.setMsg("服务编号不能为空");
            message.setSuccess(false);
            return;
        }
//
//        if(StringUtils.isNotBlank(serviceId)){
//            message.setMsg("not finished yet");
//            message.setSuccess(false);
//            return;
//        }else if(StringUtils.isNotBlank(channel)){
//            message.setMsg("not finished yet");
//            message.setSuccess(false);
//            return;
//        }else{
//            message.setMsg("请提供服务id或栏目id");
//            message.setSuccess(false);
//            return;
//        }


//        Long total=serviceNewsMapper.countServiceNews(map);
//        dataGrid.setTotal(total);
//        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
//        map.put("start", start);
//
//        List<ServiceNews> list = serviceNewsMapper.selectServiceNews(map);


        DataGrid<ServiceNews> dataGrid=new  DataGrid<ServiceNews>();
        Long total=serviceNewsMapper.countServiceNews(map);
        dataGrid.setTotal(total);
        String page = (String) map.get("page") ;
        String rows = (String) map.get("rows") ;
        if(StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
        } else {
            map.put("isNotLimit","1") ;
        }
        List<ServiceNews> list=serviceNewsMapper.selectServiceNews(map);
        dataGrid.setRows(list);

        message.setMsg("成功获取服务新闻");
        message.setSuccess(true);
        message.setObj(dataGrid);

    }
}

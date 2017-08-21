package com.cy.core.serviceNewsType.dao;

import com.cy.core.serviceNewsType.entity.ServiceNewsType;

import java.util.List;
import java.util.Map;

public interface ServiceNewsTypeMapper {

    //查询服务新闻栏目
    List<ServiceNewsType> query(Map<String, Object> map);

    //查询服务新闻栏目详情
    ServiceNewsType getById(String id);

    //通过栏目名查询栏目
    ServiceNewsType getByName(String name);

    //新增服务新闻栏目
    void save(ServiceNewsType serviceNewsType);

    //更新服务新闻栏目
    void update(ServiceNewsType serviceNewsType);

}

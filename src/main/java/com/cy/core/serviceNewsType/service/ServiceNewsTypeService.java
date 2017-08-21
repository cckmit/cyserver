package com.cy.core.serviceNewsType.service;
import com.cy.base.entity.Message;
import com.cy.core.serviceNewsType.entity.ServiceNewsType;

import java.util.List;
import java.util.Map;

public interface ServiceNewsTypeService {

    List<ServiceNewsType> query(Map<String, Object> map);

    ServiceNewsType getById(String id);

    ServiceNewsType getByName(String name);

    void save(ServiceNewsType serviceNewsType);

    void update(ServiceNewsType serviceNewsType);


    /***
     * 服务新闻栏目接口
     */
    void findServiceNewsTypeList(Message message, String content);

    /**
     * 服务新闻列表接口
     */
    void findServiceNewsList(Message message, String content);

}

package com.cy.core.cloudEnterprise.dao;

import com.cy.core.cloudEnterprise.entity.CloudEnterprise;

import java.util.List;
import java.util.Map;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 云平台企业信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-08-05
 */
public interface CloudEnterpriseMapper {
    //查出总数
    public long count(Map<String, Object> map);
    //查出列表
    public List<CloudEnterprise> selectEnterprise(Map<String, Object> map);
    //根据ID查询单个的详细信息
    public CloudEnterprise getByCloudId(String id);

    //添加一条
    void insert(CloudEnterprise cloudEnterprise);
    //修改
    void update(CloudEnterprise cloudEnterprise);
    Integer updateByCloudId(CloudEnterprise cloudEnterprise);

    void delete(List<String> list);

    void deleteByCloudIdAndStatus(Map<String,Object> map);

}

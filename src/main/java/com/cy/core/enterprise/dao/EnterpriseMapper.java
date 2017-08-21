package com.cy.core.enterprise.dao;

import com.cy.core.enterprise.entity.Enterprise;

import java.util.List;
import java.util.Map;
/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 企业信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2016-1-6
 */
public interface EnterpriseMapper {
    //查出总数
    public long count(Map<String, Object> map);
    //查出列表
    public List<Enterprise> selectEnterprise(Map<String, Object> map);
    //根据ID查询单个的详细信息
    public Enterprise getById(String id);

    //添加一条
    void insert(Enterprise enterprise);
    //修改
    void update(Enterprise enterprise);

    void delete(List<String> list);

    void updateByCloudId(Enterprise enterprise);

    void deleteBycloudId(String cloudId);

}

package com.cy.core.enterpriseJob.dao;

import com.cy.core.enterpriseJob.entity.EnterpriseJob;

import java.util.List;
import java.util.Map;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 招聘岗位</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-05-24
 */
public interface EnterpriseJobMapper {

    //查出总数
    public long count(Map<String, Object> map);
    //查出列表
    public List<EnterpriseJob> selectEnterpriseJob(Map<String, Object> map);
    //根据ID查询单个的详细信息
    public EnterpriseJob getById(String id);

    //添加一条
    void insert(EnterpriseJob enterpriseJob);
    //修改
    void update(EnterpriseJob enterpriseJob);

    void delete(List<String> list);

    void updateByCloudId(EnterpriseJob enterpriseJob);
}

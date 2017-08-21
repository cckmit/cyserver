package com.cy.core.cloudEnterprise.dao;


import com.cy.core.cloudEnterprise.entity.CloudEnterprisePosition;

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
public interface CloudEnterprisePositionMapper {

    //查出总数
    public long count(Map<String, Object> map);
    //查出列表
    public List<CloudEnterprisePosition> selectEnterpriseJob(Map<String, Object> map);
    //根据ID查询个细信息
    public CloudEnterprisePosition getById(String id);

    //添加一条
    void insert(CloudEnterprisePosition cloudEnterprisePosition);
    //修改
    void update(CloudEnterprisePosition cloudEnterprisePosition);

    void delete(List<String> list);
    //更新
    void updateByCloudId(CloudEnterprisePosition cloudEnterprisePosition);
}

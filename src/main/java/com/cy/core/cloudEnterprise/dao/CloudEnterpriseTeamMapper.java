package com.cy.core.cloudEnterprise.dao;



import com.cy.core.cloudEnterprise.entity.CloudEnterprise;
import com.cy.core.cloudEnterprise.entity.CloudEnterpriseTeam;;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/15.
 */
public interface CloudEnterpriseTeamMapper {
    void save(CloudEnterpriseTeam cloudEnterpriseTeam);
    void update(CloudEnterpriseTeam cloudEnterpriseTeam);
    List<CloudEnterpriseTeam> findEnterPriseTeamList(Map<String, Object> map);
    long count(Map<String, Object> map);
    void updateByCloudTeamId(CloudEnterpriseTeam cloudEnterpriseTeam);
    void delete(String id);
}

package com.cy.core.enterprise.dao;



import com.cy.core.enterprise.entity.EnterpriseTeam;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/15.
 */
public interface EnterpriseTeamMapper {
    void save(EnterpriseTeam enterpriseTeam);
    void update(EnterpriseTeam enterpriseTeam);
    List<EnterpriseTeam> findEnterpriseTeamList(Map<String, Object> map);
    long count(Map<String, Object> map);

    void updateByCloudId(EnterpriseTeam enterpriseTeam);

    void delete(String id);
    List<EnterpriseTeam> findTeamList(Map<String,Object> map);
}

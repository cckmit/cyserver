package com.cy.core.cloudEnterprise.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.cloudEnterprise.entity.CloudEnterpriseTeam;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/15.
 */
public interface CloudEnterpriseTeamService {

    DataGrid<CloudEnterpriseTeam> dataGridTeam(Map<String, Object> map);
    CloudEnterpriseTeam getById(String id);
    int saveTeam(CloudEnterpriseTeam cloudEnterpriseTeam);
    int updateTeam(CloudEnterpriseTeam cloudEnterpriseTeam);
    int delete(String ids);
}

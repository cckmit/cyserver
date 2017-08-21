package com.cy.core.cloudEnterprise.service;


import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.cloudEnterprise.dao.CloudEnterpriseTeamMapper;
import com.cy.core.cloudEnterprise.entity.CloudEnterpriseTeam;
import com.cy.core.enterprise.dao.EnterpriseMapper;
import com.cy.core.enterprise.dao.EnterpriseTeamMapper;
import com.cy.core.enterprise.entity.Enterprise;
import com.cy.core.enterprise.entity.EnterpriseTeam;
import com.cy.core.enterprise.service.EnterpriseTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/15.
 */
@Service("cloudEnterpriseTeamService")
public class CloudEnterpriseTeamServiceImpl implements CloudEnterpriseTeamService{

    @Autowired
    private CloudEnterpriseTeamMapper cloudEnterpriseTeamMapper;

    public DataGrid<CloudEnterpriseTeam> dataGridTeam(Map<String, Object> map){
        long count = cloudEnterpriseTeamMapper.count(map);
        DataGrid<CloudEnterpriseTeam> dataGrid = new DataGrid<>();
        dataGrid.setTotal(count);
        int page = (Integer) map.get("page");
        int rows = (Integer) map.get("rows");
        if(page >0 && rows > 0)
        {
            int start = ( (page-1)*rows);
            map.put("start", start);
        }
        else
            map.put("isNotLimit", "1");

        List<CloudEnterpriseTeam> list = cloudEnterpriseTeamMapper.findEnterPriseTeamList(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public CloudEnterpriseTeam getById(String id){
        Map<String, Object> map = new HashMap<>();
        map.put("cloudTeamId", id);
        map.put("isNotLimit", "1");
        List<CloudEnterpriseTeam> list = cloudEnterpriseTeamMapper.findEnterPriseTeamList(map);
        CloudEnterpriseTeam cloudEnterpriseTeam = new CloudEnterpriseTeam();
        if(list != null && list.size()>0){
            cloudEnterpriseTeam = list.get(0);
        }
        return cloudEnterpriseTeam;
    }

    public int saveTeam(CloudEnterpriseTeam cloudEnterpriseTeam){
        Map<String, Object> map = new HashMap<>();
        map.put("enterpriseId",cloudEnterpriseTeam.getEnterpriseId());
        long count = cloudEnterpriseTeamMapper.count(map);
        if(count > 5){
            return 1;
        }
        cloudEnterpriseTeam.preInsert();
        cloudEnterpriseTeamMapper.save(cloudEnterpriseTeam);
        return 0;
    }

    public int updateTeam(CloudEnterpriseTeam cloudEnterpriseTeam){
        cloudEnterpriseTeam.preUpdate();
        cloudEnterpriseTeamMapper.update(cloudEnterpriseTeam);
        return 0;
    }

    public int delete(String ids){
        String[] array = ids.split(",");
        for(String id : array){
            CloudEnterpriseTeam cloudEnterpriseTeam = getById(id);
            cloudEnterpriseTeam.preUpdate();
            cloudEnterpriseTeam.setDelFlag("1");
            cloudEnterpriseTeamMapper.update(cloudEnterpriseTeam);
        }
        return 0;
    }

}

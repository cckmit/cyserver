package com.cy.core.cloudEnterprise.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.cloudEnterprise.dao.CloudEnterprisePositionMapper;
import com.cy.core.cloudEnterprise.entity.CloudEnterprisePosition;
import com.cy.core.enterpriseJob.dao.EnterpriseJobMapper;
import com.cy.core.enterpriseJob.entity.EnterpriseJob;
import com.cy.core.enterpriseJob.service.EnterpriseJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 简历基本信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-05-24
 */
@Service("cloudEnterprisePositionService")
public class CloudEnterprisePositionServiceImpl implements CloudEnterprisePositionService {

    @Autowired
    private CloudEnterprisePositionMapper cloudEnterprisePositionMapper;


    @Override
    public List<CloudEnterprisePosition> findList(Map<String, Object> map) {
        List<CloudEnterprisePosition> list = cloudEnterprisePositionMapper.selectEnterpriseJob(map);
        return list;
    }

    @Override
    public Long findCount(Map<String, Object> map) {
        return null;
    }

    @Override
    public DataGrid<CloudEnterprisePosition> dataGrid(Map<String, Object> map) {
        DataGrid<CloudEnterprisePosition> dataGrid = new DataGrid<CloudEnterprisePosition>();
        long total = cloudEnterprisePositionMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<CloudEnterprisePosition> list = cloudEnterprisePositionMapper.selectEnterpriseJob(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public List<CloudEnterprisePosition> enterpriseJobList(){
        Map<String, Object> map = new HashMap<>();
        map.put("isNoLimit", "1");
        List<CloudEnterprisePosition> list = cloudEnterprisePositionMapper.selectEnterpriseJob(map);
        return list;
    }

    @Override
    public CloudEnterprisePosition save(CloudEnterprisePosition cloudEnterprisePosition) {
        cloudEnterprisePosition.preInsert();
        cloudEnterprisePositionMapper.insert(cloudEnterprisePosition);
        return cloudEnterprisePosition;
    }
    @Override
    public CloudEnterprisePosition update(CloudEnterprisePosition cloudEnterprisePosition) {
        cloudEnterprisePosition.preUpdate();
        cloudEnterprisePositionMapper.update(cloudEnterprisePosition);
        return cloudEnterprisePosition;
    }

    @Override
    public void delete(String ids){
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String id : array){
            list.add(id);
        }
        cloudEnterprisePositionMapper.delete(list);
    }

    public CloudEnterprisePosition getById(String id) {
        CloudEnterprisePosition cloudEnterprisePosition = cloudEnterprisePositionMapper.getById(id) ;
        return cloudEnterprisePosition;
    }


}

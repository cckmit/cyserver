package com.cy.core.smsbuywater.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.serviceNews.entity.ServiceNews;
import com.cy.core.smsAccount.entity.SmsAccount;
import com.cy.core.smsbuywater.dao.SmsBuyWaterMapper;
import com.cy.core.smsbuywater.entity.SmsBuyWater;
import com.cy.smscloud.http.SmsCloudHttpUtils;
import com.cy.system.Global;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/17.
 */
@Service("smsBuyWaterService")
public class SmsBuyWaterServiceImpl implements SmsBuyWaterService{

    @Autowired
    private SmsBuyWaterMapper smsBuyWaterMapper;

    @Override
    public List<SmsBuyWater> findList(Map<String, Object> map) {
        List<SmsBuyWater> list=smsBuyWaterMapper.selectSmsBuyWater(map);
        return list ;
    }

    @Override
    public Long findCount(Map<String, Object> map) {
        return null;
    }

    @Override
    public DataGrid<SmsBuyWater> dataGrid(Map<String, Object> map) {
        DataGrid<SmsBuyWater> dataGrid=new  DataGrid<SmsBuyWater>();
        Long total=smsBuyWaterMapper.countSmsBuyWater(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<SmsBuyWater> list=smsBuyWaterMapper.selectSmsBuyWater(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    @Override
    public void saveSmsBuyWater(SmsBuyWater smsBuyWater) {
        smsBuyWaterMapper.insert(smsBuyWater);
    }

    @Override
    public void deleteSmsBuyWater(String ids) {
        String[] array=ids.split(",");
        List<String> list=new ArrayList<>();
        for (String id : array) {
            list.add(id);
        }
        smsBuyWaterMapper.delete(list);
    }

    @Override
    public void updateSmsBuyWater(SmsBuyWater smsBuyWater) {
        smsBuyWater.preUpdate();
        smsBuyWaterMapper.update(smsBuyWater);
    }


    @Override
    public SmsBuyWater getSmsBuyWaterById(String id) {
        return smsBuyWaterMapper.getSmsBuyWaterById(id);
    }


    public SmsBuyWater getSmsBuyWaterByOrderNum(String orderNo){
        return smsBuyWaterMapper.getSmsBuyWaterByOrderNum(orderNo);
    }

}

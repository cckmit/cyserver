package com.cy.core.smsCount.service;

import com.cy.base.entity.DataGrid;
import com.cy.common.utils.HttpclientUtils;
import com.cy.common.utils.JsonUtils;

import com.cy.core.smsCount.dao.SmsCountMapper;
import com.cy.core.smsCount.entity.SmsCount;
import com.cy.smscloud.entity.Body;
import com.cy.smscloud.entity.Header;
import com.cy.smscloud.entity.RequestEntity;


import com.cy.smscloud.entity.Body;
import com.cy.smscloud.entity.Header;
import com.cy.smscloud.entity.RequestEntity;
import com.cy.common.utils.HttpclientUtils;
import com.cy.common.utils.JsonUtils;
import com.cy.smscloud.http.HttpClientBase;
import com.cy.smscloud.http.SmsCloudHttpUtils;
import com.cy.system.Global;
import org.apache.commons.collections.map.HashedMap;
import org.apache.http.NameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Cha0res on 2016/8/28.
 */
@Service("smsCountService")
public class SmsCountServiceImpl implements SmsCountService{
    private static final Logger logger = Logger.getLogger(SmsCountServiceImpl.class);


    @Autowired
    private SmsCountMapper smsCountMapper;



    public DataGrid<SmsCount> countSmsByTimeLine(Map<String, Object> map){
        DataGrid<SmsCount> dataGrid = new DataGrid<SmsCount>();

        long total = smsCountMapper.countTotalByTimeLine(map);
        long success = smsCountMapper.countSuccessByTimeLine(map);
        long failed = total - success;
        List<SmsCount> list = new ArrayList<SmsCount>();

        SmsCount smsCount1 = new SmsCount();
        smsCount1.setTitle("时间范围");
        if(map.get("minTime") != null && map.get("maxTime") != null){
            smsCount1.setValue(map.get("minTime")+"~"+map.get("maxTime"));
        }else{
            SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");
            smsCount1.setValue("开始~"+sf.format(new Date()));
        }

        list.add(smsCount1);

        SmsCount smsCount2 = new SmsCount();
        smsCount2.setTitle("总发送条数");
        smsCount2.setValue(String.valueOf(total));
        list.add(smsCount2);

        SmsCount smsCount3 = new SmsCount();
        smsCount3.setTitle("发送成功");
        smsCount3.setValue(String.valueOf(success));
        list.add(smsCount3);

        SmsCount smsCount4 = new SmsCount();
        smsCount4.setTitle("发送失败");
        smsCount4.setValue(String.valueOf(failed));
        list.add(smsCount4);

        SmsCount smsCount5 = new SmsCount();
        smsCount5.setTitle("剩余短信条数");
        smsCount5.setValue(getLeftSms());
        list.add(smsCount5);

        dataGrid.setRows(list);

        return dataGrid;
    }


    public String getLeftSms(){
        String code = "获取短信剩余条数失败！";
        Map<String, String> resultMap = SmsCloudHttpUtils.findSmsAppCount(Global.smsUrl, Global.userAccount, Global.password);
        //System.out.println(resultMap);
        if(resultMap != null){
            code = resultMap.get("surplusNum");
        }
        return code;
    }

    public Map<String, Object> countBuyTimeLine(Map<String, Object> map){
        long total = smsCountMapper.countTotalByTimeLine(map);
        long success = smsCountMapper.countSuccessByTimeLine(map);
        long failed = total - success;

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", String.valueOf(total));
        resultMap.put("success", String.valueOf(success));
        resultMap.put("failed", String.valueOf(failed));
        return resultMap;
    }
}

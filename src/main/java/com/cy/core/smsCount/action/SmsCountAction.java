package com.cy.core.smsCount.action;

/**
 * Created by Cha0res on 2016/8/28.
 */

import com.cy.base.action.AdminBaseAction;
import java.text.SimpleDateFormat;
import com.cy.base.entity.DataGrid;
import com.cy.core.smsCount.entity.SmsCount;
import com.cy.core.smsCount.service.SmsCountService;

import java.util.*;

import com.cy.core.user.entity.User;
import com.mysql.fabric.xmlrpc.base.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

@Namespace("/smsCount")
@Action("smsCountAction")
public class SmsCountAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(SmsCountAction.class);

    @Autowired
    private SmsCountService smsCountService;

    private String timeLine;

    public void dataGrid(){
        Map<String, Object> map = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(timeLine) ){

            GregorianCalendar gc = new GregorianCalendar();

            SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");

            gc.setTime(new Date());

            map.put("maxTime", sf.format(gc.getTime()));
            switch (timeLine){
                case "1":
                    gc.add(3, -1);  //一周
                    break;
                case "2":
                    gc.add(2, -1);  //一个月
                    break;
                case "3":
                    gc.add(2, -3);  //三个月
                    break;
                case "4":
                    gc.add(2, -6);  //六个月
                    break;
                case "5":
                    gc.add(1, -1);  //一年
                    break;
            }
            gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
            map.put("minTime", sf.format(gc.getTime()));

        }

        User user = getUser();
        if(user != null && user.getDeptId() > 0){
            map.put("alumniId", user.getDeptId());
        }

        super.writeJson(smsCountService.countSmsByTimeLine(map));
    }

    public void doNotNeedSecurity_getCount(){
        Map<String, Object> map = new HashMap<String, Object>();

        if(StringUtils.isNotBlank(timeLine) ){

            GregorianCalendar gc = new GregorianCalendar();

            SimpleDateFormat sf  =new SimpleDateFormat("yyyy-MM-dd");

            gc.setTime(new Date());

            map.put("maxTime", sf.format(gc.getTime()));
            switch (timeLine){
                case "1":
                    gc.add(3, -1);  //一周
                    break;
                case "2":
                    gc.add(2, -1);  //一个月
                    break;
                case "3":
                    gc.add(2, -3);  //三个月
                    break;
                case "4":
                    gc.add(2, -6);  //六个月
                    break;
                case "5":
                    gc.add(1, -1);  //一年
                    break;
            }
            gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));
            map.put("minTime", sf.format(gc.getTime()));

        }

        User user = getUser();
        if(user != null && user.getDeptId() > 0){
            map.put("alumniId", user.getDeptId());
        }
        super.writeJson(smsCountService.countBuyTimeLine(map));
    }

    public String getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }
}



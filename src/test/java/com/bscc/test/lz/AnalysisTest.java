package com.bscc.test.lz;

import com.alibaba.fastjson.JSON;
import com.cy.core.chatGroup.service.ChatGroupService;
import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dept.service.DeptService;
import com.cy.core.userinfo.service.UserInfoService;
import com.cy.mobileInterface.alumni.service.AlumniService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: DeptTest</p>
 * <p>Description: </p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-09-07 17:07
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring*.xml"})
public class AnalysisTest {

    @Autowired
    UserInfoService userInfoService ;

    @Test
    public void userInfoSummary(){
//        List<Map<String,String>> list = userInfoService.userInfoSummary(null);
//        String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
//        System.out.println("\n\n"+json+"\n\n");
    }
    @Test
    public void countAnalysisUserInfo(){
//        Map<String,String> map = Maps.newHashMap() ;
//        map.put("groupType","2") ;
//        List<Map<String,String>> list = userInfoService.countAnalysisUserInfo(map);
//        String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
//        System.out.println("\n\n"+json+"\n\n");
    }

    @Test
    public void chartOfDeptUser() {
//        List<Map<String,Object>> list = userInfoService.chartOfDeptUser() ;
//        String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
//        System.out.println("\n\n"+json+"\n\n");
    }
    @Test
    public void chartOfMining() {
//        List<Map<String,String>> list = userInfoService.chartOfMining() ;
//        String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
//        System.out.println("\n\n"+json+"\n\n");
    }

    public static void main(String[] args) {

//        Calendar calendar=Calendar.getInstance();
//        calendar.setTime(new Date());
//        System.out.println(calendar.get(Calendar.MONTH)+1);//今天的日期
//        calendar.set(Calendar.MONTH,calendar.get(Calendar.MONTH) - 10);//让日期加1
//        Date date =calendar.getTime();
//
//        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));//加1之后的日期Top
    }
}

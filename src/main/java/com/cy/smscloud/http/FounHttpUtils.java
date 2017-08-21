package com.cy.smscloud.http;

import com.cy.common.utils.TimeZoneUtils;
import com.cy.smscloud.entity.Header;
import com.cy.smscloud.entity.RequestEntity;
import com.cy.smscloud.utils.FounMd5Utils;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PengLv on 2017/4/14.
 */
public class FounHttpUtils {
    private static final Logger logger = Logger.getLogger(FounHttpUtils.class);
    public final static String USER_ID = "1" ;
    public final static String USER_TOKEN = "123456" ;
    public final static String FOUN_URL1="http://127.0.0.1:8181/app/api";
    public final static String FOUN_URL=Global.founUrl;
    public static String saveFounIncome(Map<String , Object> map){
        String code = "500" ;
        try {

        Header header = new Header() ;
        header.setModule("income");
        header.setAction("FmIncomeService");
        header.setMethod("saveIncome");
        //userId 为1 验证UserToken  0；不验证UserToken
        String userId = "1";
        String userToken = FounMd5Utils.getFounMd5(map);
        header.setUserId(StringUtils.isNotBlank(userId) ? userId : USER_ID);
        header.setUserToken(StringUtils.isNotBlank(userToken)?userToken:USER_TOKEN);
        header.setDeviceType("3");
        header.setDeviceId("0000000");
        HttpClientBase httpClientBase = new HttpClientBase() ;
        logger.info("---------> 基金会请求 URL : " + FOUN_URL);
        RequestEntity entity = httpClientBase.post(header, map, FOUN_URL) ;
        logger.info("---------> 基金会请求 结果 : " + entity);
            if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
                code = entity.getBody().getResult().getBussinessCode() ;
                String msg = entity.getBody().getResult().getMsg() ;
                logger.info("【基金会平台】发送：" + msg) ;
                if("200".equals(code)) {
                    code = "0" ;
                }
            }
            return code ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code ;
    }
    //窗友对基金会项目保存接口
    public static String saveFounProject(Map<String , Object> map){
        String code = "500" ;
        try {

            Header header = new Header() ;
            header.setModule("project.projectmanage");
            header.setAction("ProjectService");
            header.setMethod("saveProject");
            //userId 为1 验证UserToken  0；不验证UserToken
            String userId = "0";
            String userToken = FounMd5Utils.getFounMd5(map);
            header.setUserId(StringUtils.isNotBlank(userId) ? userId : USER_ID);
            header.setUserToken(StringUtils.isNotBlank(userToken)?userToken:USER_TOKEN);
            header.setDeviceType("3");
            header.setDeviceId("0000000");
            HttpClientBase httpClientBase = new HttpClientBase() ;
            logger.info("---------> 基金会请求 URL : " + FOUN_URL);
            RequestEntity entity = httpClientBase.post(header, map, FOUN_URL) ;
            logger.info("---------> 基金会请求 结果 : " + entity);
            if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
                code = entity.getBody().getResult().getBussinessCode() ;
                String msg = entity.getBody().getResult().getMsg() ;
                logger.info("【基金会平台】发送：" + msg) ;
                if("200".equals(code)) {
                    code = "0" ;
                }
            }
            return code ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code ;
    }
    //窗友对基金会项目修改接口
    public static String updateFounProject(Map<String , Object> map){
        String code = "500" ;
        try {

            Header header = new Header() ;
            header.setModule("project.projectmanage");
            header.setAction("ProjectService");
            header.setMethod("updateProject");
            //userId 为1 验证UserToken  0；不验证UserToken
            String userId = "0";
            String userToken = FounMd5Utils.getFounMd5(map);
            header.setUserId(StringUtils.isNotBlank(userId) ? userId : USER_ID);
            header.setUserToken(StringUtils.isNotBlank(userToken)?userToken:USER_TOKEN);
            header.setDeviceType("3");
            header.setDeviceId("0000000");
            HttpClientBase httpClientBase = new HttpClientBase() ;
            logger.info("---------> 基金会请求 URL : " + FOUN_URL);
            RequestEntity entity = httpClientBase.post(header, map, FOUN_URL) ;
            logger.info("---------> 基金会请求 结果 : " + entity);
            if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
                code = entity.getBody().getResult().getBussinessCode() ;
                String msg = entity.getBody().getResult().getMsg() ;
                logger.info("【基金会平台】发送：" + msg) ;
                if("200".equals(code)) {
                    code = "0" ;
                }
            }
            return code ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code ;
    }
    public static void  main (String[] a){

        Map<String , Object> map = new HashMap();
        map.put("projectIdIncome", "1234");
        map.put("personLiable","赵武");
        map.put("personNum","12341235456");
        map.put("paymentCount","1234");
        map.put("createDate", "2017-10-13");
        map.put("incomeType", "微信");
        map.put("orderNum", "3122342");
        FounHttpUtils.saveFounIncome(map);
   /* System.out.print(FOUN_URL1);*/
    }


}

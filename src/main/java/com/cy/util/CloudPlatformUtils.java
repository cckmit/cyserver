package com.cy.util;

import com.cy.common.utils.*;
import com.cy.smscloud.exception.NetServiceException;
import com.cy.system.Global;
import com.google.common.collect.Maps;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 云平台工具类
 *
 * @author niu
 * @create 2017-07-26 下午 12:24
 **/
@Component("cloudPlatformUtils")
public class CloudPlatformUtils {
    protected static long THOUSAND = 1000L;
    private static final Logger logger = Logger.getLogger(CloudPlatformUtils.class);
    /**
     * 获取云平台accesstoken
     * @param grantType 获取类型
     * @return
     */
    public static Map<String,Object> getAccessToken(String grantType){
        Map<String,Object> resultMap = Maps.newHashMap();
        if (StringUtils.isNotBlank(grantType)){
            String clientId = Global.client_id;
            String clientSecret = Global.client_secret;
            String scope = "read write";
            if (StringUtils.isNotBlank(clientId) && StringUtils.isNotBlank(clientSecret)&& StringUtils.isNotBlank(grantType)&& StringUtils.isNotBlank(scope)){
                List<NameValuePair> formparams= new ArrayList<NameValuePair>();
                formparams.add(new BasicNameValuePair("client_id",clientId));
                formparams.add(new BasicNameValuePair("client_secret",clientSecret));
                formparams.add(new BasicNameValuePair("grant_type",grantType));
                formparams.add(new BasicNameValuePair("scope",scope));
                String data ="";
                try {
                    switch (grantType){
                        //客戶端模式
                        case "client_credentials":
                                data = HttpclientUtils.post(Global.oauth_server_url,formparams);
//                                data = post(url,formparams);
                            logger.info("====token获取结果："+data);
                            resultMap = JsonUtils.json2map(data);
                                CacheUtils.put("accessToken",resultMap.get("access_token"));
                            break;
                        //密码模式
                        case "password":
                            String username = Global.cloud_user_name;
                            String password = Global.cloud_user_password;
                            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
                                formparams.add(new BasicNameValuePair("username",username));
                                formparams.add(new BasicNameValuePair("password",password));
                                data = HttpclientUtils.post(Global.oauth_server_url,formparams);
                                logger.info("====token获取结果："+data);
                                resultMap = JsonUtils.json2map(data);
                                CacheUtils.put("accessToken",resultMap.get("access_token"));
                                CacheUtils.put("refreshToken",resultMap.get("refresh_token"));
                                if (resultMap.get("expires_in")!=null && resultMap.get("expires_in")!=""){
                                    CacheUtils.put("expiresTime",expiredTime((Integer) resultMap.get("expires_in")));
                                }
                            }
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    logger.error(e,e);
                    e.printStackTrace();
                }
            }
        }
        return resultMap;
    }

    /**
     * 刷新token
     * @param refreshToken
     */
    public static void refreshToken(String refreshToken){

        String clientId = Global.client_id;
        String clientSecret = Global.client_secret;
        String scope = "read write";
        if (StringUtils.isNotBlank(clientId) && StringUtils.isNotBlank(clientSecret)&& StringUtils.isNotBlank(refreshToken)&& StringUtils.isNotBlank(scope)){
            List<NameValuePair> formparams= new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("client_id",clientId));
            formparams.add(new BasicNameValuePair("client_secret",clientSecret));
            formparams.add(new BasicNameValuePair("grant_type","refresh_token"));
            formparams.add(new BasicNameValuePair("scope",scope));
            formparams.add(new BasicNameValuePair("refresh_token",refreshToken));
            String data ="";
            try {
                    Map<String,Object> resultMap = Maps.newHashMap();
                    data = HttpclientUtils.post(Global.oauth_server_url, formparams);
                    logger.info("====刷新token获取结果：" + data);
                    resultMap = JsonUtils.json2map(data);
                    CacheUtils.put("accessToken", resultMap.get("access_token"));
                    CacheUtils.put("refreshToken",resultMap.get("refresh_token"));
                    if (resultMap.get("expires_in")!=null && resultMap.get("expires_in")!=""){
                        CacheUtils.put("expiresTime",expiredTime((Integer) resultMap.get("expires_in")));
                    }
            } catch (Exception e) {
                logger.error(e,e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取token过期时间戳
     * @param tokenExpiredSeconds
     * @return
     */
    public static long expiredTime(Integer tokenExpiredSeconds) {
        long expiredTime = DateUtils.getUnixTime() +tokenExpiredSeconds * THOUSAND;
        return expiredTime;
    }

    /**
     * 通过token 或 客户端ID获取学校配置信息 并放入缓存
     * 注：若通过客户端ID（clientId）获取 ，则需要在请求路径后拼接 client_id=123123123
     * @return
     */
    public static Map<String,Object> getSchoolByToken(){
        Map<String,Object> dataMap = Maps.newHashMap();
        Map<String,Object> map = get(Global.cloud_server_url+"/school_config/school",0);
        try {
            if (map !=null && !map.isEmpty()&& map.get("code")!=null && (Integer)map.get("code")==200){
                dataMap = (Map<String, Object>) map.get("data");
                if (dataMap!=null && StringUtils.isNotBlank((String)dataMap.get("id"))){
                    CacheUtils.put("cloudSchoolId",dataMap.get("id"));
                }
                if (dataMap!=null && StringUtils.isNotBlank((String)dataMap.get("code"))){
                    CacheUtils.put("schoolCode",dataMap.get("code"));
                }
            }
        } catch (Exception e) {
            logger.error(e,e);
            e.printStackTrace();
        }
        return dataMap;
    }

    /**
     * 通过token 或 客户端ID获取学校配置信息 并放入缓存
     * 注：若通过客户端ID（clientId）获取 ，则需要在请求路径后拼接 client_id=123123123
     * @return
     */
    public static Map<String,Object> getSchoolByClient(){
        Map<String,Object> dataMap = Maps.newHashMap();
        Map<String,Object> map  = get(Global.cloud_server_url+"/school_config/school?client_id="+Global.client_id,0);
        try {
            if (map !=null && !map.isEmpty()&& map.get("code")!=null && (Integer)map.get("code")==200){
                dataMap = (Map<String, Object>) map.get("data");
                if (dataMap!=null && StringUtils.isNotBlank((String)dataMap.get("id"))){
                    CacheUtils.put("cloudSchoolId",dataMap.get("id"));
                }
            }
        } catch (Exception e) {
            logger.error(e,e);
            e.printStackTrace();
        }
        return dataMap;
    }

    /**
     * 云平台get请求方式
     * @param url  请求路径
     * @param count token异常重复请求次数
     * @return String content ：-1：表示access_token 为空；-2：请求路径为空
     */
    public static Map<String,Object> get(String url,Integer count){
        String urlTemp = url;
        //判断token 是否过期
        if (StringUtils.isNotBlank((String)CacheUtils.get("refreshToken"))&&CacheUtils.get("expiresTime")!=null){
            if ((Long)CacheUtils.get("expiresTime")<DateUtils.getUnixTime()){
                refreshToken((String) CacheUtils.get("refreshToken"));
            }
        }
        //判断token是否存在
        if (StringUtils.isBlank((String)CacheUtils.get("accessToken"))){
//          testGetToken();
            getAccessToken("password");
        }
        if (StringUtils.isNotBlank((String)CacheUtils.get("accessToken")) && StringUtils.isNotBlank(urlTemp)){
            urlTemp = urlTemp.indexOf("?")>-1?urlTemp+"&":urlTemp+"?";
            urlTemp +="access_token="+ (String)CacheUtils.get("accessToken");
            try {
                String content = HttpclientUtils.get(urlTemp);
                logger.info("--------> 出参："+content);
                if(StringUtils.isNotBlank(content)&&(content.contains("code") || content.contains("error"))){
                    Map<String,Object> map = JsonUtils.json2map(content);
                    if (map!=null&& !map.isEmpty()){
                        if ("invalid_token".equals(map.get("error")) && count<3){
                           refreshToken((String)CacheUtils.get("refreshToken"));
                           if (count == 2){
                               CacheUtils.remove("accessToken");
                           }
                            count++;
                            return get(url,count);
                        }else if (map.get("code")!=null){
                            return map;
                        }
                    }
                }
            } catch (NetServiceException e) {
                logger.error(e,e);
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *
     * @param url  请求地址
     * @param params 请求参数
     * @param count token异常重复请求次数
     * @param count  token过期请求次数 最多请求三次
     * @return
     */
    public static Map<String,Object> post(String url,List<NameValuePair> params,Integer count){
        //判断token 是否过期
        if (StringUtils.isNotBlank((String)CacheUtils.get("refreshToken"))&& CacheUtils.get("expiresTime")!=null){
            if ((Long)CacheUtils.get("expiresTime")<DateUtils.getUnixTime()){
                refreshToken((String) CacheUtils.get("refreshToken"));
            }
        }

        if (StringUtils.isBlank((String)CacheUtils.get("accessToken"))){
           getAccessToken("client_credentials");
//            testGetToken();
        }
        if (StringUtils.isNotBlank((String)CacheUtils.get("accessToken"))&& StringUtils.isNotBlank(url)){
           url = url.indexOf("?")>-1?url+"&":url+"?";
           url +="access_token="+ (String)CacheUtils.get("accessToken");
            try {
                 String content = HttpclientUtils.post(url,params);
                logger.info("--------> 出参："+content);
                if(StringUtils.isNotBlank(content)&&(content.contains("code") || content.contains("error"))){
                    Map<String,Object> map = JsonUtils.json2map(content);
                    if (map!=null&& !map.isEmpty()){
                        if ("invalid_token".equals(map.get("error")) && count<3){
                            refreshToken((String)CacheUtils.get("refreshToken"));
                            if (count == 2){
                                CacheUtils.remove("accessToken");
                            }
                            count++;
                            return post(url,params,count);
                        }else if (map.get("code")!=null){
                            return map;
                        }
                    }
                }
                System.out.println("--------> 出参：\n" + content);
            } catch (NetServiceException e) {
                logger.error(e,e);
                e.printStackTrace();
            } catch (Exception e) {
                logger.error(e,e);
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void testGetToken(){
        List<NameValuePair> formparams= new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("client_id","123123"));
        formparams.add(new BasicNameValuePair("client_secret","123123"));
        formparams.add(new BasicNameValuePair("grant_type","password"));
        formparams.add(new BasicNameValuePair("scope","read"));
        formparams.add(new BasicNameValuePair("username","cy123456"));
        formparams.add(new BasicNameValuePair("password","123456"));
        try {
            String data = HttpclientUtils.post("http://localhost:8080/oauth/token",formparams);
            System.out.println("--------> Token：\n" + data);
        } catch (NetServiceException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //&access_token=16a5fe956955cf80b1853033e34d8e3a
//        get("http://localhost:8090/api/v1/enterprise/getRoleUser/ca82fd5d5de149a986b2bbf9df9fc5a2?roleName=enterprise-user");

        List<NameValuePair> formparams= new ArrayList<NameValuePair>();
//        get("http://localhost:8090/api/v1/enterprise_team/syncTeamToSchool/000990",0);
        testGetToken();
//        formparams.add(new BasicNameValuePair("client_id","123123123"));
//        formparams.add(new BasicNameValuePair("client_secret","123123123"));
//        formparams.add(new BasicNameValuePair("grant_type","client_credentials"));
//        formparams.add(new BasicNameValuePair("scope","read"));
//        post("http://localhost:8080/oauth/token",formparams);
//        post("http://localhost:8090/api/v1/enterprise_position/delete/823359a74dc942ee9524ecf777c1b948",formparams);
        /*String result = get("http://localhost:8090/api/v1/school_audit_enterprise?del_flag=0&schoolId=0183dd339ec8406a8632967d5d300f47");
        try {
            Map<String,Object> map = JsonUtils.json2map(result);
            System.out.println("--------> 待认证：\n" + map);
            if (!map.isEmpty()){
                List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("data");
                if (!list.isEmpty()){
                       for (Map<String,Object> map1:list){
                           if (!map1.isEmpty()){
                               String enterpriseId = (String) map1.get("enterprise_id");
                               if (StringUtils.isNotBlank(enterpriseId)){
                                   String enterprise = get("http://localhost:8090/api/v1/enterprise/"+enterpriseId);
                                   Map<String,Object> enterpriseMap = JsonUtils.json2map(enterprise);
                                   System.out.println("--------> 企業詳情：\n" + enterpriseMap);
                               }
                           }
                       }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}

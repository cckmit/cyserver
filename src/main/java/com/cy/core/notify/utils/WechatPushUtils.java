package com.cy.core.notify.utils;

import com.alibaba.fastjson.JSON;
import com.cy.common.utils.HttpclientUtils;
import com.cy.common.utils.SpringContextHolder;
import com.cy.core.weiXin.dao.WeiXinAccountMapper;
import com.cy.core.weiXin.entity.WeiXinAccount;
import com.cy.core.weiXin.entity.WeiXinUser;
import com.cy.core.weiXin.service.WeiXinAccountService;
import com.cy.core.weiXin.service.WeiXinUserService;
import com.cy.smscloud.exception.NetServiceException;
import com.cy.system.Global;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信推送工具类
 * Created by niu on 2017/1/16.
 */
public class WechatPushUtils {

    public static WeiXinUserService weiXinUserService = SpringContextHolder.getBean("weiXinUserService");
    public static WeiXinAccountMapper weiXinAccountMapper = SpringContextHolder.getBean("weiXinAccountMapper");

    //上传文件资源地址
    public final static String fileUrl = Global.wechat_api_url + "uploadFile/";
    //推送微信地址
    public final static String imageTextUrl = Global.wechat_api_url + "send/";

    /**
     * 检查文件路径
     *
     * @param url 文件相对路径
     * @return
     */
    public static boolean checkUrl(String url) {
        boolean isExists = false;
        String fileUrl = Global.DISK_PATH + url;
        System.out.println("-----------> 文件存放路径：" + fileUrl);
        File file = new File(fileUrl);
        if (file.exists()) {
            isExists = true;
        }
        return isExists;
    }

    //获取微信公众号
    public static WeiXinAccount getWeiXinAccount(){
        Map<String,Object> map =new HashMap<>();
        map.put("accountType","10");
        List<WeiXinAccount> weiXinAccountList = weiXinAccountMapper.selectList(map);
        if (weiXinAccountList != null && weiXinAccountList.size()>0){
            return weiXinAccountList.get(0);
        }
        return null;
    }

    /**
     * @author niu
     * @date 2017-1-16
     * @description 推送到微信
     * @param map
     */
    public static void pushWechat(Map<String, Object> map) {
        WeiXinAccount weiXinAccount = getWeiXinAccount();
        if (weiXinAccount!=null && StringUtils.isNotBlank(weiXinAccount.getAccountAppId())) {
            if (map != null) {
                String msgType = (String) map.get("msgType");
                if (StringUtils.isNotBlank(msgType)) {
                    //获取appId
                    String accountNums = (String) map.get("accountNums");
                    //String accountNums = "508,506";

                    if (StringUtils.isNotBlank(accountNums)) {
                        String[] accountNumArray = accountNums.split(",");
//                    String[] accountNumArray =new String[]{"508","506"};
                        String openIds = "";
                        //统计openId 的个数
                        int count = 0;
                        Map<String, Object> accountMap = new HashMap<>();
                        for (String accountNum : accountNumArray) {
                            map.put("accountNum", accountNum);
                            List<WeiXinUser> weiXinUserList = weiXinUserService.findList(map);
                            if (weiXinUserList != null && weiXinUserList.size() > 0 && weiXinUserList.get(0) != null) {
                                for (WeiXinUser weiXinUser : weiXinUserList) {
                                    if (StringUtils.isNotBlank(weiXinUser.getAccountType()) && "10".equals(weiXinUser.getAccountType())) {
                                        openIds = openIds + weiXinUser.getOpenid() + ",";
                                        count++;
                                    }
                                }

                            }
                        }
                        if (StringUtils.isNotBlank(openIds) && count >= 2) {
                            if ("10".equals(msgType)) {//文本
                                try {
                                    Map<String, Object> paramMap = new HashMap<>();
                                    paramMap.put("msgType", map.get("msgType"));
                                    paramMap.put("content", map.get("content"));
                                    paramMap.put("openIds", openIds);
                                    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                                    formparams.add(new BasicNameValuePair("data", JSON.toJSONString(paramMap)));
                                    String imageTextResult = HttpclientUtils.post(imageTextUrl+weiXinAccount.getAccountAppId(), formparams);
                                    Map<String, Object> imageTextResultMap = JSON.parseObject(imageTextResult, Map.class);
                                    Integer errCode = (Integer) imageTextResultMap.get("errcode");
                                    if (errCode != null && errCode != 0) {
                                        System.out.print("出错--------" + imageTextResult);
                                    }
                                } catch (NetServiceException e) {
                                    e.printStackTrace();
                                }
                            } else if ("20".equals(msgType)) {//图文
                                String picUrl = "/" + (String) map.get("picUrl");
                                boolean isPicUrl = checkUrl(picUrl);
                                if (isPicUrl) {
                                    //请求参数列表
                                    List<NameValuePair> formparamsPic = new ArrayList<NameValuePair>();
                                    formparamsPic.add(new BasicNameValuePair("url", Global.URL_DOMAIN + picUrl));
                                    String result = null;
                                    try {
                                        result = HttpclientUtils.post(fileUrl+weiXinAccount.getAccountAppId(), formparamsPic);
                                        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
                                        Integer errcode = (Integer) resultMap.get("errcode");
                                        if (errcode != null && errcode != 0) {
                                        } else {
                                            Map<String, Object> paramMap = new HashMap<>();
                                            paramMap.put("msgType", map.get("msgType"));
                                            paramMap.put("title", map.get("title"));
                                            paramMap.put("content", map.get("content"));
                                            paramMap.put("author", map.get("author"));
                                            paramMap.put("url", map.get("url"));
                                            paramMap.put("mediaId", (String) resultMap.get("media_id"));
                                            paramMap.put("openIds", openIds);
                                            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                                            formparams.add(new BasicNameValuePair("data", JSON.toJSONString(paramMap)));
                                            String imageTextResult = HttpclientUtils.post(imageTextUrl+weiXinAccount.getAccountAppId(), formparams);
                                            Map<String, Object> imageTextResultMap = JSON.parseObject(imageTextResult, Map.class);
                                            Integer errCode = (Integer) imageTextResultMap.get("errcode");
                                            if (errCode != null && errCode != 0) {
                                                System.out.print("出错--------" + imageTextResult);
                                            }
                                        }
                                    } catch (NetServiceException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void wechatPush(Map<String, Object> map){
        String appId = (String) map.get("appId");
        String openIds = (String) map.get("openIds");
        String msgType = (String) map.get("msgType");
        if (StringUtils.isNotBlank(appId)&& StringUtils.isNotBlank(openIds)&& StringUtils.isNotBlank(msgType)){
            if ("10".equals(msgType)){
                try {
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put("msgType", msgType);
                    paramMap.put("content", map.get("content"));
                    paramMap.put("openIds", openIds);
                    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                    formparams.add(new BasicNameValuePair("data", JSON.toJSONString(paramMap)));
                    String imageTextResult = HttpclientUtils.post(imageTextUrl+appId, formparams);
                    Map<String, Object> imageTextResultMap = JSON.parseObject(imageTextResult, Map.class);
                    Integer errCode = (Integer) imageTextResultMap.get("errcode");
                    if (errCode != null && errCode != 0) {
                        System.out.print("出错--------" + imageTextResult);
                    }
                } catch (NetServiceException e) {
                    e.printStackTrace();
                }
            }else if ("20".equals(msgType)){
                String picUrl = "/" + (String) map.get("picUrl");
                boolean isPicUrl = checkUrl(picUrl);
                if (isPicUrl) {
                    //请求参数列表
                    List<NameValuePair> formparamsPic = new ArrayList<NameValuePair>();
                    formparamsPic.add(new BasicNameValuePair("url", Global.URL_DOMAIN + picUrl));
                    String result = null;
                    try {
                        result = HttpclientUtils.post(fileUrl+appId, formparamsPic);
                        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
                        Integer errcode = (Integer) resultMap.get("errcode");
                        if (errcode != null && errcode != 0) {
                        } else {
                            Map<String, Object> paramMap = new HashMap<>();
                            paramMap.put("msgType", map.get("msgType"));
                            paramMap.put("title", map.get("title"));
                            paramMap.put("content", map.get("content"));
                            paramMap.put("author", map.get("author"));
                            paramMap.put("url", map.get("url"));
                            paramMap.put("mediaId", (String) resultMap.get("media_id"));
                            paramMap.put("openIds", openIds);
                            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                            formparams.add(new BasicNameValuePair("data", JSON.toJSONString(paramMap)));
                            String imageTextResult = HttpclientUtils.post(imageTextUrl+appId, formparams);
                            Map<String, Object> imageTextResultMap = JSON.parseObject(imageTextResult, Map.class);
                            Integer errCode = (Integer) imageTextResultMap.get("errcode");
                            if (errCode != null && errCode != 0) {
                                System.out.print("出错--------" + imageTextResult);
                            }
                        }
                    } catch (NetServiceException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}

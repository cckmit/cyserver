package com.cy.core.weiXin.service;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Maps;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.EditorUtils;
import com.cy.common.utils.HttpclientUtils;
import com.cy.common.utils.StringUtils;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.weiXin.dao.WeiXinAccountMapper;
import com.cy.core.weiXin.dao.WeiXinUserMapper;
import com.cy.core.weiXin.entity.WeiXinAccount;
import com.cy.core.weiXin.entity.WeiXinUser;
import com.cy.smscloud.exception.NetServiceException;
import com.cy.system.Global;
import com.cy.util.Collections3;
import com.cy.util.DateUtils;
import com.jfinal.weixin.sdk.utils.HttpUtils;
import com.jfinal.weixin.sdk.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by WangAoHui on 2017/1/3.
 */
@Service("weiXinUserService")
public class WeiXinUserServiceImpl implements WeiXinUserService {
    @Autowired
    private WeiXinUserMapper weiXinUserMapper;

    @Autowired
    private WeiXinAccountMapper weiXinAccountMapper;

    @Autowired
    private UserProfileMapper   userProfileMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public List<WeiXinUser> findList(Map<String, Object> map) {
        List<WeiXinUser> list = weiXinUserMapper.selectWeiXinUser(map);
        return list;
    }

    @Override
    public Long findCount(Map<String, Object> map) {
        return null;
    }

    @Override
    public DataGrid<WeiXinUser> dataGrid(Map<String, Object> map) {
        DataGrid<WeiXinUser> dataGrid = new DataGrid<WeiXinUser>();
        long total = weiXinUserMapper.countWeiXinUser(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        map.put("isNotLimit",0);
        List<WeiXinUser> list = weiXinUserMapper.selectWeiXinUser(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    @Override
    public WeiXinUser save(WeiXinUser weiXinUser) {

        weiXinUserMapper.insert(weiXinUser);
        return weiXinUser;
    }
    @Override
    public WeiXinUser update(WeiXinUser weiXinUser) {
        weiXinUserMapper.update(weiXinUser);
        return weiXinUser;
    }



    public WeiXinUser getById(String id) {
        WeiXinUser weiXinUser = weiXinUserMapper.getById(id);

        return weiXinUser;
    }

    /**
     * 方法countWechatAccount 的功能描述：获取公众号关注人数
     * @createAuthor niu
     * @createDate 2017-05-15 12:16:15
     * @param map
     * @return long
     * @throw
     *
     */
    public long countWechatAccount(Map<String,Object> map){

        long alumniWechatAccount  = 0;
        List<WeiXinAccount> weiXinAccountList = weiXinAccountMapper.selectList(map);
        if (weiXinAccountList !=null && !weiXinAccountList.isEmpty()){
            WeiXinAccount weiXinAccount = weiXinAccountList.get(0);
            if (weiXinAccount !=null && StringUtils.isNotBlank(weiXinAccount.getAccountAppId())){
                Map<String,Object> map2 =Maps.newHashMap();
                map2.put("accountId",weiXinAccount.getId());
                map2.put("accountAppId",weiXinAccount.getAccountAppId());
                map2.put("isFollow",1);
                alumniWechatAccount = weiXinUserMapper.countWeiXinUser(map2);
            }
        }
        return alumniWechatAccount;
    }

    public String headImage(String headImgUrl) throws Exception {
        // 文件保存目录路径
        String savePath = Global.DISK_PATH + "image/" + DateUtils.getDate("yyyyMMdd") + "/";
        // 数据库中保存的路径（相对路径）
        String saveURL = "/image/" + DateUtils.getDate("yyyyMMdd") + "/";

        String headImagename = DateUtils.getDate("yyyyMMddHHmmss") + "_"+ new Random().nextInt(1000) + ".jpg";

        EditorUtils.download(headImgUrl,savePath,headImagename);

        return saveURL+headImagename;
    }


    public WeiXinUser saveUserInfoByOpenId(String openId, String appId){

         // 查询公众号的ID
        Map<String,Object> map = Maps.newHashMap();
        String accountId;
        map.put("appId",appId) ;
        List<WeiXinAccount> weiXinAccountList = weiXinAccountMapper.selectList(map) ;
        if(weiXinAccountList != null || !weiXinAccountList.isEmpty()) {
            accountId = weiXinAccountList.get(0).getId();
        }else{
            return null;
        }

         // 查询用户是否存在
        Map<String, Object> tmp = new HashMap<>();
        tmp.put("openid", openId);
        tmp.put("accountAppId", appId);
        WeiXinUser weiXinUser = new WeiXinUser();

        List<WeiXinUser> list = weiXinUserMapper.selectWeiXinUser(tmp);
        System.out.println("查询微信用户的入参---------------->>>>>>>>>>>>>"+tmp.toString());
        System.out.println("查询结果---------------->>>>>>>>>>>>>"+list.toString());
        if(list != null && list.size() > 0 && list.get(0) != null){
            weiXinUser = list.get(0);
            if(StringUtils.isBlank(weiXinUser.getNickname())){
                //有該openId紀錄,但是沒有用戶信息
                weiXinUser.setOpenid(openId);
                weiXinUser.setAccountAppId(appId);
                weiXinUser.setAccountId(accountId);
                WeiXinUser weiXinUserFind = getWeiChatUserThrowBsweb(weiXinUser);
                if(weiXinUserFind != null){
                    weiXinUser.setIsFollow("0");
                    weiXinUser = weiXinUserFind;
                }

                weiXinUser.preUpdate();
                weiXinUserMapper.update(weiXinUser);
            }
        }else{
            // 微信用戶表裡沒有該用戶的紀錄保存用戶數據
            weiXinUser.setOpenid(openId);
            weiXinUser.setAccountAppId(appId);
            weiXinUser.setAccountId(accountId);
            WeiXinUser weiXinUserFind = getWeiChatUserThrowBsweb(weiXinUser);
            if(weiXinUserFind != null){
                weiXinUser.setIsFollow("0");
                weiXinUser = weiXinUserFind;
            }

            weiXinUser.preInsert();
            weiXinUserMapper.insert(weiXinUser);

        }

        return weiXinUser;
    }


    private WeiXinUser getWeiChatUserThrowBsweb(WeiXinUser weiXinUser){
        // 用戶信息沒有存儲
        try {
            if(StringUtils.isBlank(weiXinUser.getAccountAppId()) || StringUtils.isBlank(weiXinUser.getOpenid())){
                return null;
            }
            System.out.println("appId-------->>>>"+weiXinUser.getAccountAppId() + "");
            String infoStr  = HttpclientUtils.get(Global.wechat_api_url+ "getUserInfo/"+weiXinUser.getAccountAppId()+"?openId="+weiXinUser.getOpenid());
            if(StringUtils.isBlank(infoStr)){
                return null;
            }
            System.out.println("获取的微信用户信息------>>>>>"+infoStr);
            Map<String, Object> resultMap = JSON.parseObject(infoStr, Map.class);

            if(resultMap.get("nickname") == null){
                return null;
            }
            weiXinUser.setNickname(resultMap.get("nickname").toString());
            weiXinUser.setSex(resultMap.get("sex").toString());
            weiXinUser.setLanguage(resultMap.get("language").toString());
            weiXinUser.setCity(resultMap.get("city").toString());
            weiXinUser.setProvince(resultMap.get("province").toString());
            weiXinUser.setCountry(resultMap.get("country").toString());
            weiXinUser.setHeadimgurl(resultMap.get("headimgurl").toString());
            weiXinUser.setAccountAppId(weiXinUser.getAccountAppId());

            String localHeadimage = headImage(weiXinUser.getHeadimgurl());
            weiXinUser.setLocalHeadImage(localHeadimage);
            return weiXinUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取媒体文件
     * */
    private String downloadMedia(String mediaId,String openId,String appId){
        String resultStr = "";
        System.out.println("------------------------------------------");
        System.out.println("mediaId:" + mediaId + "\nopenId:" + openId+ "\nappId:" + appId);

        try{
            String asccessToken = HttpclientUtils.get(Global.wechat_api_url + "getAccessToken/" + appId);
            System.out.println(asccessToken);
            String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+asccessToken+"&media_id="+mediaId;
            System.out.println("requestUrl:"+ requestUrl);
            System.out.println("------------------------------------------");

            URL url = new URL(requestUrl);
            // 打开连接
            URLConnection con = url.openConnection();
            //设置请求超时为20s
            con.setConnectTimeout(20*1000);
            // 输入流
            InputStream is = con.getInputStream();

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            if(!"/".equals(Global.DISK_PATH.substring(Global.DISK_PATH.length() - 1 , Global.DISK_PATH.length()))){
                resultStr += "/";
            }
            resultStr += "image/";
            // 判断image目录是否存在
            String imgStr = Global.DISK_PATH + resultStr;
            File imgPath = new File(imgStr);
            if(!imgPath.exists()  || !imgPath.isDirectory()){
                imgPath.mkdir();
            }
            System.out.println("------------------------------------------");
            System.out.println(imgStr);

            // 判断当日目录是否存在
            resultStr += TimeZoneUtils.getDateString()+"/";
            String daliyStr = Global.DISK_PATH + resultStr;
            File daliyPath = new File(daliyStr);
            if(!daliyPath.exists() || !daliyPath.isDirectory()){
                daliyPath.mkdir();
            }
            System.out.println("------------------------------------------");
            System.out.println(daliyStr);

            resultStr += openId +"/";
            String personalStr = Global.DISK_PATH + resultStr;
            File savePath = new File(personalStr);
            if(!savePath.exists() || !savePath.isDirectory()){
                savePath.mkdir();
            }
            System.out.println("------------------------------------------");
            System.out.println(personalStr);

            resultStr += UUID.randomUUID()+".jpg";
            String targetFilePath = Global.DISK_PATH + resultStr;
            System.out.println("------------------------------------------");
            System.out.println(targetFilePath);

            OutputStream os = new FileOutputStream(targetFilePath);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();

            if(!"/".equals(resultStr.substring(0, 1))){
                resultStr = "/" +resultStr;
            }
        }catch (Exception e){
            resultStr = "";
            throw new RuntimeException(e);
        }
        return  resultStr;
    }

    /**
     * 方法updateWeChatUser 的功能描述：
     *
     *       获取公众号，得到关注该公众号的用户openid,
     *        再通过openId比较本地微信用户是否存在，不存在插入一条记录
     *
     * @createAuthor niu
     * @createDate 2017-05-15 22:03:48
     * @param map
     * @return void
     * @throw
     *
     */
    public void updateWeChatUser(Map<String,Object> map) throws NetServiceException {


        List<WeiXinAccount> weiXinAccountList = weiXinAccountMapper.selectList(map);
        if (weiXinAccountList !=null && !weiXinAccountList.isEmpty()){
            WeiXinAccount weiXinAccount = weiXinAccountList.get(0);
            if (weiXinAccount !=null && StringUtils.isNotBlank(weiXinAccount.getAccountAppId())){
                String url = Global.wechat_api_url + "getFollowers/"+weiXinAccount.getAccountAppId();
                String opendIds = HttpclientUtils.get(url);
                if (opendIds !=null && opendIds !=""){
                    Map<String,Object> openIdMap = JSON.parseObject(opendIds,Map.class);
                    Map<String,Object> dataMap = (Map<String, Object>) openIdMap.get("data");
                    List<String> openIdList = (List<String>) dataMap.get("openid");
                    //获取该公众号本地用户
                    Map<String,Object> mapUser = Maps.newHashMap();
                    mapUser.put("accountId",weiXinAccount.getId());
                    mapUser.put("accountAppId",weiXinAccount.getAccountAppId());
                    List<WeiXinUser> weiXinUsers = weiXinUserMapper.selectWeiXinUser(mapUser);
                    String userOpenIds ="";
                    if (weiXinUsers !=null && !weiXinUsers.isEmpty()) {
                        userOpenIds = Collections3.extractToString(weiXinUsers, "openid", ",");
                    }
                    for (String openId :openIdList){
                        if (!userOpenIds.contains(openId)){
                            WeiXinUser weiXinUser = new WeiXinUser();
                            weiXinUser.setAccountId(weiXinAccount.getId());
                            weiXinUser.setAccountAppId(weiXinAccount.getAccountAppId());
                            weiXinUser.setOpenid(openId);
                            weiXinUser = getWeiChatUserThrowBsweb(weiXinUser);
                            weiXinUser.setIsFollow("1");
                            weiXinUser.preInsert();
                            weiXinUserMapper.insert(weiXinUser);
                        }
                    }

                }
            }
        }
    }

    /***********************************************************************
     * 【微信用户信息】相关API（以下区域）
     * <p>
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     ***********************************************************************/


    /**
     * 方法updateLocalWeChatUser 的功能描述：定时器——更新微信用户
     * @createAuthor niu
     * @createDate 2017-05-15 22:02:38
     * @param
     * @return void
     * @throw
     *
     */
   public void updateLocalWeChatUser(){


       try {
            //更新校友会公众号用户
           Map<String,Object> map = Maps.newHashMap();
           map.put("accountType",10);
           updateWeChatUser(map);

           //更新基金会公众号用户
           Map<String,Object> map1 = Maps.newHashMap();
           map.put("accountType",20);
           updateWeChatUser(map1);

       } catch (NetServiceException e) {
           e.printStackTrace();
       }
   }


    /**
     *创建微信用户相关信息接口
     *
     * @update_author niu
     * @update_date 2017-1-9
     * @description 判断用户是否已存在，存在只修改关注标志
     */
    public void saveWeiXinUser(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        WeiXinUser weiXinUser = JSON.parseObject(content, WeiXinUser.class);

        if(StringUtils.isBlank(weiXinUser.getOpenid())) {
            message.setMsg("请传入用户微信号");
            message.setSuccess(false);
            return;
        }
        if(StringUtils.isBlank(weiXinUser.getAccountAppId())){
            message.setMsg("请传入微信公共号信息的accountAppId");
            message.setSuccess(false);
            return;
        }


        //关联公众号信息
        Map<String, Object> weiXinAccountMap = new HashMap<>();
        weiXinAccountMap.put("appId",weiXinUser.getAccountAppId());
        List<WeiXinAccount> weiXinAccountList = weiXinAccountMapper.selectList(weiXinAccountMap);
        if (weiXinAccountList !=null && weiXinAccountList.size()>0 && weiXinAccountList.get(0) != null){
            weiXinUser.setAccountId(weiXinAccountList.get(0).getId());
        }else {
            message.setMsg("公众号不存在");
            message.setSuccess(false);
            return;
        }

        //微信头像
        if (StringUtils.isNotBlank(weiXinUser.getHeadimgurl())) {
            try {
                String localHeadImage = headImage(weiXinUser.getHeadimgurl());
                weiXinUser.setLocalHeadImage(localHeadImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        Map<String, Object> tmp = new HashMap<>();
        tmp.put("openid",weiXinUser.getOpenid());
        tmp.put("accountAppId",weiXinUser.getAccountAppId());
        //查询是否已存在该公众号用户
        List<WeiXinUser> list = weiXinUserMapper.selectWeiXinUser(tmp);
        if(list != null && list.size()>0 && list.get(0) != null){
            WeiXinUser weiXinUser1=list.get(0);
            if(StringUtils.isNotBlank(weiXinUser.getAccountNum()) && weiXinUser.getAccountNum().equals(weiXinUser1.getAccountNum())){
                message.setMsg("已绑定");
                message.setSuccess(true);
                return;
            }
            weiXinUser.setId(weiXinUser1.getId());
            weiXinUser.preUpdate();
            weiXinUserMapper.update(weiXinUser);
            message.setMsg("修改成功");
            message.setSuccess(true);
        }else{

            weiXinUser.preInsert();
            weiXinUserMapper.insert(weiXinUser);
            message.setMsg("新增成功");
            message.setSuccess(true);
        }

        try{
            if(StringUtils.isNotBlank(weiXinUser.getAccountNum())){
                UserProfile userProfile = userProfileMapper.selectByAccountNum(weiXinUser.getAccountNum());
                if(userProfile != null){
                    if(StringUtils.isBlank(userProfile.getPicture())){
                        userProfile.setPicture(weiXinUser.getHeadimgurl());
                    }
                    if(StringUtils.isBlank(userProfile.getSex()) && StringUtils.isNotBlank(weiXinUser.getSex())){
                        if("1".equals(weiXinUser.getSex())){
                            userProfile.setSex("0");
                        }

                        if("2".equals(weiXinUser.getSex())){
                            userProfile.setSex("1");
                        }
                    }
                    userProfileMapper.update(userProfile);
                }
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     *根据用户微信号(openid)获取用户信息
     *
     */
    public void findWeiXinUser(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, String> map = JSON.parseObject(content, Map.class);
        String openid = map.get("openid");
        if(StringUtils.isBlank(openid)) {
            message.setMsg("请传入用户微信号");
            message.setSuccess(false);
            return;
        }
        String accountAppId = map.get("accountAppId");
        if(StringUtils.isBlank(accountAppId)) {
            message.setMsg("请传入公共号的APPID");
            message.setSuccess(false);
            return;
        }

        Map<String, Object> tmp = new HashMap<>();
        tmp.put("openid",openid);
        tmp.put("accountAppId",accountAppId);
        List<WeiXinUser> list = weiXinUserMapper.selectWeiXinUser(tmp);

        // 若微信用户不存在，通过微信平台获取微信用户信息
        if(list == null || list.isEmpty()) {
            WeiXinUser weiXinUser = saveUserInfoByOpenId(openid,accountAppId) ;
            list.add(weiXinUser) ;
        }
        message.init(true,"查询成功",list);

    }

    /**
     *解除绑定微信用户接口
     *
     * @update_author niu
     * @update_date 2017-1-9
     * @description 添加修改accountNum标志
     */

    public void removeWeiXinUser(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, String> map = JSON.parseObject(content, Map.class);
        String openid = map.get("openid");
        String accountNum = map.get("accountNum");

        if(StringUtils.isBlank(accountNum)) {
            message.setMsg("请传入手机号");
            message.setSuccess(false);
            return;
        }

        if(StringUtils.isBlank(openid)) {
            message.setMsg("请传入用户微信号");
            message.setSuccess(false);
            return;
        }

        Map<String, Object> tmp = new HashMap<>();
        tmp.put("openid",openid);
        tmp.put("accountNum",accountNum);
        List<WeiXinUser> list = weiXinUserMapper.selectWeiXinUser(tmp);
        if (list !=null && list.size()>0){
            WeiXinUser weiXinUser=list.get(0);
            weiXinUser.setAccountNum("");
            weiXinUser.setIsRemoveBinding("1");
            weiXinUser.preUpdate();
            weiXinUserMapper.update(weiXinUser);
        }

        message.init(true, "解除绑定成功", list);
    }


    /**
     * 通过code获取微信用户账户信息
     * @param message
     * @param content
     */
    public void findWeiXinUserAuthor(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, String> map = JSON.parseObject(content, Map.class);
        String code = map.get("code");
        String type = map.get("type");// 公众号类型（10：校友会；20：基金会）

        if(StringUtils.isBlank(code)) {
            message.setMsg("微信授权code参数不存在");
            message.setSuccess(false);
            return;
        }

        if(StringUtils.isBlank(type)) {
            message.setMsg("公众号类型不能为空");
            message.setSuccess(false);
            return;
        }

        // 通过type获取公众号信息
        Map<String, Object> weiXinAccountMap = new HashMap<>();
        weiXinAccountMap.put("accountType",type);
        List<WeiXinAccount> weiXinAccountList = weiXinAccountMapper.selectList(weiXinAccountMap);
        if (weiXinAccountList !=null && weiXinAccountList.size()>0 && weiXinAccountList.get(0) != null){
            String appId = weiXinAccountList.get(0).getAccountAppId();

            // 通过code获取openId
            String url = Global.wechat_api_url + "getOpenId/APP_ID?code=CODE";

            url = url.replace("APP_ID",appId);
            url = url.replace("CODE",code);

            String openId = HttpUtils.get(url);
            if (StringUtils.isNotBlank(openId)) {
                Map<String,Object> resultMap = new HashMap<String, Object>();

                Map<String, Object> tmp = new HashMap<>();
                tmp.put("openid",openId);
                List<WeiXinUser> list = weiXinUserMapper.selectWeiXinUser(tmp);
                if (list != null && list.size() > 0) {
                    resultMap.put("accountNum", list.get(0).getAccountNum());
                }

                resultMap.put("openId", openId);
                resultMap.put("appId", appId);

                message.init(true, "查询成功", resultMap);
            }
            else{
                message.setMsg("openid获取失败");
                message.setSuccess(false);
                return;
            }
        }else {
            message.setMsg("公众号不存在");
            message.setSuccess(false);
            return;
        }

    }

    /**
     * 保存图片到微信服务器接口
     * @param message
     * @param content
     */
    public void saveWeiXinPic(Message message, String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, String> map = JSON.parseObject(content, Map.class);
        String appId = map.get("appId");
        String openId = map.get("openId");
        String ids = map.get("ids");
        String accountNum = map.get("accountNum");
        String fromWhere = map.get("fromWhere");
        if(StringUtils.isBlank(appId)){
            message.init(false,"请提供appId", null);
            return;
        }
        if(StringUtils.isBlank(openId)){
            message.init(false,"请提供openId", null);
            return;
        }
        if(StringUtils.isBlank(ids)){
            message.init(false,"请提供媒体文件ID", null);
            return;
        }
        System.out.println("------------------------------------------");
        System.out.println("ids:" + ids + "\nopenId:" + openId+ "\nappId:" + appId);
        System.out.println("------------------------------------------");


        String[] picIds = ids.split(",");
        ArrayList<String> resultList = new ArrayList<>();
        if(picIds.length > 0){
            for(String picId:picIds){
                String resultPath = downloadMedia(picId, openId, appId);
                if(StringUtils.isNotBlank(resultPath)){
                    resultList.add(resultPath);
                }
            }
        }

        if(resultList.size() > 0){

            if(StringUtils.isNotBlank(accountNum) && "1".equals(fromWhere)){
                //更新用户头像
                UserProfile userProfile = userProfileMapper.selectByAccountNum(accountNum);
                userProfile.setPictureUrl(resultList.get(0));
                userProfileMapper.updatePhoto(userProfile);
                System.out.println(accountNum+"==========>"+resultList.get(0));
                //更新校友数据头像
                Map<String, Object> tmp = new HashMap<String, Object>();
                tmp.put("picUrl", resultList.get(0));
                tmp.put("accountNum", accountNum);
                userInfoMapper.updatePhoto(tmp);
                message.init(true, "头像上传成功",resultList.get(0));
            }else if("3".equals(fromWhere)){
                //值年返校图片只有一张
                message.init(true, "图片上传成功",resultList.get(0));
            }else{
                message.init(true, "图片上传成功",resultList);
            }
        }else{
            message.init(false, "保存失败",null);
        }
    }
}

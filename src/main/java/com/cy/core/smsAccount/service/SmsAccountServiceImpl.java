package com.cy.core.smsAccount.service;

import com.cy.base.entity.Message;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.alumni.service.AlumniService;
import com.cy.core.smsbuywater.dao.SmsBuyWaterMapper;
import com.cy.core.smsbuywater.entity.SmsBuyWater;
import com.cy.core.systemsetting.entity.SystemSetting;
import com.cy.core.systemsetting.service.SystemSettingService;
import com.cy.smscloud.http.HttpClientBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.cy.core.smsAccount.dao.SmsAccountMapper;
import com.cy.core.smsAccount.entity.AppUser;
import com.cy.core.smsAccount.entity.SmsAccount;
import com.cy.smscloud.http.SmsCloudHttpUtils;
import com.cy.system.Global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by cha0res on 11/17/16.
 */
@Service("smsAccountService")
public class SmsAccountServiceImpl implements SmsAccountService
{
    private static final Logger logger = Logger.getLogger(SmsAccountServiceImpl.class);

    @Autowired
    SmsAccountMapper smsAccountMapper;

    @Autowired
    SmsBuyWaterMapper smsBuyWaterMapper;

    @Autowired
    AlumniService alumniService;

    @Autowired
    SystemSettingService systemSettingService;

    public SmsAccount getByAlumniId( String alumniId )
    {
        SmsAccount smsAccount = smsAccountMapper.getByAlumniId( alumniId );
        if(smsAccount != null)
        {
            AppUser appUser = smsAccountMapper.getAppUser(smsAccount.getAppUserId());
            smsAccount.setAppUser(appUser);
        }
        return  smsAccount;
    }

    @Transactional(rollbackFor=Exception.class)
    public Message saveAccount(SmsAccount smsAccount )
    {
        Message message = new Message();

        if( smsAccount != null && smsAccount.getAppUser() != null)
        {
            boolean saveUserSuc = false;

            if (smsAccount.isSync()) {
                try {
                    //創建帳號
                    Map<String, String> resultMap = SmsCloudHttpUtils.saveAccount(Global.smsUrl, smsAccount.getAccount(), smsAccount.getPassword(), smsAccount.getSign(), smsAccount.getAppUser().getName(), smsAccount.getAppUser().getPhone(), smsAccount.getAppUser().getEmail());
                    if (resultMap == null || resultMap.size() == 0) {
                        message.setMsg("提交账号信息失败");
                        message.setSuccess(false);
                    } else {
                        saveUserSuc = true;
                    }
                } catch (Exception e) {
                    logger.error(e, e);
                    message.setMsg("服务器异常");
                    message.setSuccess(false);
                }
            }
            else {
                saveUserSuc = true;
            }

            if(saveUserSuc){
                try{
                    // 清除就数据
                    smsAccountMapper.clearAccount(smsAccount.getAlumniId());
                    smsAccountMapper.clearAppUser(smsAccount.getAlumniId());

                    // 保存账号信息
                    smsAccount.setAppUserId(smsAccount.getAppUser().getId());
                    smsAccountMapper.saveAccount( smsAccount );

                    // 保存联系人信息
                    smsAccountMapper.saveAppUser(smsAccount.getAppUser());

                    // 保存购买流水
                    if (smsAccount.getAppBuyWaterList() != null && smsAccount.getAppBuyWaterList().size() > 0) {

                        // 清除该账户下的购买流水列表
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("accountId",smsAccount.getId());
                        List<SmsBuyWater> smsBuyWaterList = smsBuyWaterMapper.selectSmsBuyWater(params);
                        List<String> ids = new ArrayList<String>();
                        for (SmsBuyWater smsBuyWater : smsBuyWaterList) {
                            ids.add(smsBuyWater.getId());
                        }
                        if (ids != null && ids.size() > 0) {
                            smsBuyWaterMapper.remove(ids);
                        }

                        // 同步插入该账户下的购买流水列表
                        for (SmsBuyWater smsBuyWater : smsAccount.getAppBuyWaterList()) {
                            smsBuyWaterMapper.insert(smsBuyWater);
                        }
                    }

                    message.setSuccess(true);
                    message.setMsg("创建短信账号成功");
                }
                catch (Exception e){
                    message.setMsg("保存联系人信息失败");
                    message.setSuccess(false);
                }
            }
        }
        else
        {
            message.setSuccess(false);
            message.setMsg("未传入任何信息");
        }
        return message;

    }

    @Transactional(rollbackFor=Exception.class)
    public Message updateAccount( SmsAccount smsAccount ) {
        Message message = new Message();

        if(smsAccount != null && smsAccount.getAppUser() != null) {
            try {
                SmsAccount smsAccountOld = getByAlumniId(String.valueOf(smsAccount.getAlumniId()));

                // 同步修改短信云账号
                Map<String, String> resultMap = SmsCloudHttpUtils.updateAccount(Global.smsUrl , smsAccountOld.getAccount(), smsAccountOld.getPassword() ,smsAccount.getAccount(), smsAccount.getPassword(),smsAccount.getSign(),smsAccount.getAppUser().getName(),smsAccount.getAppUser().getPhone(),smsAccount.getAppUser().getEmail(), smsAccount.getCurrBuyWaterId(), smsAccount.getNextBuyWaterId());

                if(resultMap == null || resultMap.size() == 0) {
                    message.setMsg("同步到服务器失败");
                    message.setSuccess(false);
                }
                else {
                    smsAccountMapper.updateAccount( smsAccount );
                    smsAccountMapper.updateAppUser( smsAccount.getAppUser());

                    // 如果是总会，修改系统设置中的短信设置账号和密码
                    Alumni alumni = alumniService.getByAlumniId(Long.parseLong(smsAccount.getAlumniId()));
                    if (alumni != null && alumni.isLeader()) {
                        List<SystemSetting> systemSettingList = systemSettingService.selectAll();
                        if (systemSettingList != null && systemSettingList.size() > 0) {
                            SystemSetting systemSetting = systemSettingList.get(0);
                            systemSetting.setSmsAccount(smsAccount.getAccount());
                            systemSetting.setSmsPassword(smsAccount.getPassword());
                            systemSettingService.update(systemSetting);
                        }
                    }

                    message.setMsg("修改成功");
                    message.setSuccess(true);
                    message.setObj(resultMap);
                }
            }
            catch (Exception e)
            {
                logger.error(e, e);
                message.setMsg("修改失敗");
                message.setSuccess(false);
            }

        }
        else
        {
            message.setSuccess(false);
            message.setMsg("未传入任何信息");
        }

        return message;
    }


    public Message findSmsAccount( SmsAccount smsAccount )
    {
        Message message = new Message();

        if(smsAccount != null)
        {
            if( StringUtils.isNotBlank(smsAccount.getAccount()) && StringUtils.isNotBlank(smsAccount.getPassword()) )
            {
                Map<String, String> resultMap = SmsCloudHttpUtils.findSmsAppCount(Global.smsUrl ,smsAccount.getAccount(), smsAccount.getPassword() );
                System.out.println(resultMap);
                if(resultMap == null || resultMap.size() == 0)
                {
                    message.setMsg("不存在此账号");
                    message.setSuccess(false);
                }
                else
                {
                    message.setMsg("查询成功");
                    message.setSuccess(true);
                    message.setObj(resultMap);
                }
            }
            else
            {
                message.setMsg("数据异常，请联系管理员");
                message.setSuccess(false);
            }
        }
        else
        {
            message.setMsg("账号不存在");
            message.setSuccess(false);
        }

        return message;
    }

    //發送短信
    public String sendSms( String alumniId, String mobile, String content )
    {
        String result = "999";
        SmsAccount smsAccount = new SmsAccount();
        if(StringUtils.isNotBlank(alumniId))
        {
            smsAccount = getByAlumniId(alumniId);
        }
        else
        {
            smsAccount = getByAlumniId("1");
        }

        if(smsAccount != null)
        {
            if(StringUtils.isNotBlank(smsAccount.getCurrBuyWaterId()))
            {
                SmsBuyWater smsBuyWater = smsBuyWaterMapper.getSmsBuyWaterByOrderNum(smsAccount.getCurrBuyWaterId());
                if(smsBuyWater != null){
                    if( Long.parseLong(smsBuyWater.getSurplusNum()) > 0 )
                    {

                        //發送短信
                        if(isMobile(mobile)){
                            result = "101";       //手機號格式不正確
                            return result;
                        }
                        String code = SmsCloudHttpUtils.sendSms(Global.smsUrl,smsAccount.getAccount(), smsAccount.getPassword(), mobile, content);
                        if("0".equals(code))
                        {
                            int maxNumber = 50;
                            //查詢單條最大限制
                            Map<String, String> resultMap = SmsCloudHttpUtils.findCurrentCloudFlowPacketDetail(Global.smsUrl, smsAccount.getAccount(), smsAccount.getPassword(), "");
                            if(resultMap != null && resultMap.get("maxNumber") != null)
                                maxNumber = Integer.parseInt(String.valueOf(resultMap.get("maxNumber")));

                            int countNumber = 0;
                            if (content.length() % maxNumber == 0) {
                                countNumber = content.length() / maxNumber;
                            } else {
                                countNumber = content.length() / maxNumber + 1;
                            }

                            //發送成功並扣除餘額
                            smsBuyWater.setSurplusNum(String.valueOf(Integer.parseInt(smsBuyWater.getSurplusNum()) - countNumber));
                            smsBuyWaterMapper.update(smsBuyWater);
                            smsAccount.setSurplusNum(String.valueOf(Integer.parseInt(smsBuyWater.getSurplusNum()) - countNumber));
                            smsAccount.preUpdate();
                            smsAccountMapper.updateAccount(smsAccount);
                        }
                        result = code;
                    }
                    else
                    {
                        if(StringUtils.isNotBlank(smsAccount.getNextBuyWaterId()))
                        {
                            SmsBuyWater smsBuyWater1 = smsBuyWaterMapper.getSmsBuyWaterByOrderNum(smsAccount.getNextBuyWaterId());
                            if( Long.parseLong(smsBuyWater1.getSurplusNum()) > 0 )
                            {
                                //備選包設為當前
                                smsAccount.setCurrBuyWaterId(smsAccount.getNextBuyWaterId());
                                smsAccount.setNextBuyWaterId("");
                                smsAccount.preUpdate();
                                smsAccountMapper.updateAccount(smsAccount);

                                //發送短信
                                String code = SmsCloudHttpUtils.sendSms(Global.smsUrl,smsAccount.getAccount(), smsAccount.getPassword(), mobile, content);
                                result = code;
                                if("0".equals(code))
                                {
                                    //發送成功並扣除餘額
                                    smsBuyWater1.setSurplusNum(String.valueOf(Long.parseLong(smsBuyWater1.getSurplusNum()) - 1));
                                    smsBuyWaterMapper.update(smsBuyWater1);

                                }

                            }
                            else
                            {
                                result = "102"; //備選短信包已無剩餘條數
                            }
                        }
                        else
                        {
                            result = "103"; //當前流量包已用完，未找到備選短信包
                        }
                    }
                }
                else
                {
                    result = "104"; //當前流量包無效

                }

            }
            else
            {
                result = "105"; //未設置當前短信包
            }
        }
        else
        {
            result = "106"; //短信帳號不存在，請開通帳號
        }

        return result;

    }

    private boolean isMobile(String str){

        Pattern p = Pattern.compile("^[1][3,4,5,8][0-9][9]$");
        Matcher m = p.matcher(str);
        boolean b = m.matches();
        return b;
    }
}

package com.cy.core.smsAccount.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.common.utils.JsonUtils;
import com.cy.common.utils.StringUtils;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.alumni.service.AlumniService;

import com.cy.core.sms.util.Global;
import com.cy.core.smsAccount.entity.SmsAccount;
import com.cy.core.smsAccount.service.SmsAccountService;
import com.cy.core.smsbuywater.entity.SmsBuyWater;
import com.cy.core.smsbuywater.service.SmsBuyWaterService;
import com.cy.core.systemsetting.entity.SystemSetting;
import com.cy.core.systemsetting.service.SystemSettingService;
import com.cy.core.user.entity.User;
import com.cy.smscloud.http.SmsCloudHttpUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * Created by cha0res on 11/17/16.
 * 短信平台帳號
 */
@Namespace("/smsAccount")
@Action("smsAccountAction")
public class SmsAccountAction extends AdminBaseAction
{
    private static final Logger logger = Logger.getLogger(SmsAccountAction.class);

    @Autowired
    AlumniService alumniService;
    @Autowired
    SmsAccountService smsAccountService;
    @Autowired
    SystemSettingService systemSettingService;
    @Autowired
    SmsBuyWaterService smsBuyWaterService;

    private SmsAccount smsAccount;

    private User user = getUser();

    private String currBuyWaterId;

    private String nextBuyWaterId;

    /**
     * 獲取當前組織信息
     */
    public void doNotNeedSecurity_getCurrentDeptInfo()
    {
        Alumni alumni = new Alumni();
        if(user != null && user.getDeptId() > 0)
        {
            alumni = alumniService.getByAlumniId(user.getDeptId());
            if(user.getDeptId() > 1){
                smsAccount = smsAccountService.getByAlumniId(String.valueOf(user.getDeptId()));
                if (smsAccount != null) {
                    alumni.setAdmin(smsAccount.getAccount());
                }
            }
        }

        super.writeJson(alumni);
    }


    /**
     * 獲取當前組織的帳號信息
     */
    public void getAccountInfoByCurrentDept(){

        Message message = new Message();
        SmsAccount smsAccount = new SmsAccount();
        if(user != null && user.getDeptId() > 0)
        {
            smsAccount = smsAccountService.getByAlumniId(String.valueOf(user.getDeptId()));
        }
        if(smsAccount == null){
            message.setSuccess(false);
            message.setMsg("未获取任何信息");
        }else{
            message.setSuccess(true);
            message.setMsg("获取成功");
            message.setObj(smsAccount);
        }
        super.writeJson(message);
    }

    /**
     * 保存帳號
     */
    public void save()
    {

        Message message = new Message();
        if(user != null && user.getDeptId() > 0 )
        {
            if(user.getDeptId() >1 && StringUtils.isNotBlank(smsAccount.getAccountFronts()) ){
                smsAccount.setAccount(smsAccount.getAccountFronts()+smsAccount.getAccount());
            }
            smsAccount.setAlumniId(String.valueOf(user.getDeptId()));
            smsAccount.preInsert();
            smsAccount.getAppUser().setAlumniId(String.valueOf(user.getDeptId()));
            smsAccount.getAppUser().preInsert();
            message = smsAccountService.saveAccount(smsAccount);
        }
        else
        {
            message.setMsg("未获取当前用户信息");
            message.setSuccess(false);
        }

        super.writeJson(message);
    }

    /**
     * 修改帳號
     */
    public void update(){

        Message message = new Message();

        if(user != null && user.getDeptId() > 0)
        {
            SmsAccount smsAccountOld = smsAccountService.getByAlumniId(String.valueOf(user.getDeptId()));
            if( smsAccountOld != null ){
                smsAccount.setId(smsAccountOld.getId());
                smsAccount.setAlumniId(smsAccountOld.getAlumniId());
                smsAccount.preUpdate();

                smsAccount.getAppUser().setId(smsAccountOld.getAppUser().getId());
                smsAccount.getAppUser().setAlumniId(smsAccountOld.getAlumniId());
                smsAccount.getAppUser().preUpdate();
                message = smsAccountService.updateAccount(smsAccount);
            }
        }else{
            message.setMsg("未获取当前用户信息");
            message.setSuccess(false);
        }

        super.writeJson(message);
    }


    /**
     * 設置流量包
     */
    public void setPackets(){
        Message message = new Message();
        if(user != null && user.getDeptId() > 0)
        {
            SmsAccount smsAccountOld = smsAccountService.getByAlumniId(String.valueOf(user.getDeptId()));
            if( smsAccountOld != null )
            {
                smsAccountOld.setCurrBuyWaterId(currBuyWaterId);
                smsAccountOld.setNextBuyWaterId(nextBuyWaterId);

                SmsBuyWater smsBuyWater = smsBuyWaterService.getSmsBuyWaterById(currBuyWaterId);
                if (smsBuyWater != null) {
                    smsAccountOld.setSurplusNum(smsBuyWater.getSurplusNum());
                }

                smsAccountOld.preUpdate();
                message = smsAccountService.updateAccount(smsAccountOld);
            }
        }
        else
        {
            message.setMsg("未获取当前用户信息");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    /**
     * 根据当前登录用户所在组织ID，获取短信云账号信息
     * @return
     */
    public void findSmsCloudAccount(){
        Message message = new Message();

        smsAccount = new SmsAccount();

        if(user != null && user.getDeptId() > 0) {
            smsAccount = smsAccountService.getByAlumniId(String.valueOf(user.getDeptId()));

            // 如果查询不存在，获取系统参数配置中的短信云账号，前往云平台查询账号信息并同步至本地
            if (smsAccount == null || StringUtils.isBlank(smsAccount.getId())) {
                // 如果是总会，从系统参数设置中查询短信云账号
                Alumni alumni = alumniService.getByAlumniId(user.getDeptId());
                if (alumni != null && alumni.isLeader()) {
                    List<SystemSetting> list = systemSettingService.selectAll();
                    if (list != null && list.size() > 0) {
                        // 短信云账号
                        String smsAccountStr = list.get(0).getSmsAccount();
                        if (StringUtils.isNotBlank(smsAccountStr)) {
                            // 查询短信云平台，获取账号信息
                            Map<String, String> resultMap = SmsCloudHttpUtils.findSmsAppccountAndUser(SmsCloudHttpUtils.SMS_CLOUD_URL, smsAccountStr, list.get(0).getSmsPassword());

                            smsAccount  = JsonUtils.map2pojo(resultMap, "yyyy-MM-dd HH:mm:ss", SmsAccount.class);

                            // 同步账号（包括联系人信息、购买流水）至本地库
                            smsAccount.setSync(false);
                            smsAccount.setAlumniId(alumni.getAlumniId() + "");
                            if (smsAccount.getAppUser() != null) {
                                smsAccount.getAppUser().setAlumniId(alumni.getAlumniId() + "");
                            }

                            smsAccountService.saveAccount(smsAccount);

                            message = smsAccountService.findSmsAccount(smsAccount);
                            message.setSuccess(true);
                        }
                        else {
                            message.setMsg("获取短信账号参数失败，请管理员先设置参数设置");
                            message.setSuccess(false);
                        }
                    }
                    else {
                        message.setMsg("获取系统参数失败，请管理员先设置系统参数");
                        message.setSuccess(false);
                    }
                }
                else {
                    message.setMsg("当前用户没有所属组织");
                    message.setSuccess(false);
                }
            }
            else {
                message = smsAccountService.findSmsAccount(smsAccount);
                message.setSuccess(true);
            }
        }
        else {
            message.setMsg("用户未登录，退出登录");
            message.setSuccess(false);
        }

        super.writeJson(message);
    }

    public SmsAccount getSmsAccount() {
        return smsAccount;
    }

    public void setSmsAccount(SmsAccount smsAccount) {
        this.smsAccount = smsAccount;
    }

    public String getCurrBuyWaterId() {
        return currBuyWaterId;
    }

    public void setCurrBuyWaterId(String currBuyWaterId) {
        this.currBuyWaterId = currBuyWaterId;
    }

    public String getNextBuyWaterId() {
        return nextBuyWaterId;
    }

    public void setNextBuyWaterId(String nextBuyWaterId) {
        this.nextBuyWaterId = nextBuyWaterId;
    }
}

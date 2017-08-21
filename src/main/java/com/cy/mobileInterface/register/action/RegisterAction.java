package com.cy.mobileInterface.register.action;

import com.alibaba.fastjson.JSON;
import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.activity.action.ActivityAction;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userProfile.service.UserProfileService;
import com.cy.util.Base64Utils;
import com.cy.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Created by cha0res on 4/7/17.
 */
@Namespace("/mRegister")
@Action(value = "registerAction", results = {@Result(name = "returnPage", location = "/foundation/activation_email.jsp")})
public class RegisterAction  extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(ActivityAction.class);

    @Autowired
    private UserProfileService userProfileService;

    private String activationCode;      //激活码

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String doNotNeedSessionAndSecurity_activation(){
        logger.info("-------------doNotNeedSessionAndSecurity_activation------------------");
        Message message = new Message();
        try{
            if(StringUtils.isNotBlank(activationCode)){
                activationCode = Base64Utils.getFromBase64(Base64Utils.getFromBase64(activationCode));
                Map<String, String> map = JSON.parseObject(activationCode, Map.class);
                String accountNum = map.get("uId");
                long time = Long.parseLong(map.get("time"));
                UserProfile userProfile = userProfileService.selectByAccountNum(accountNum);
                if(userProfile == null){
                    message.init(false, "用户不存在", null);
                }else if("1".equals(userProfile.getIsActivated())){
                    message.init(false, "已激活，无需重复激活", null);

                }else if((time - new Date().getTime()) > (24*60*60*1000)){
                    message.init(false, "激活链接已失效", null);
                }else{
                    userProfile.setIsActivated("1");
                    userProfileService.update(userProfile);
                    message.init(true, "恭喜您，激活成功", null);
                }
            }else{
                message.init(true, "无效激活链接", null);
            }
        }catch (Exception e){
            logger.error(e, e);
            message.init(true, "无效激活链接", null);
        }
        this.getRequest().setAttribute("msg", message);
        return "returnPage";
    }
}

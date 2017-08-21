package com.cy.core.inviteSms.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.inviteSms.dao.InviteSmsMapper;
import com.cy.core.inviteSms.entity.InviteSms;
import com.cy.core.sms.entity.MsgSend;
import com.cy.core.sms.service.MsgSendService;
import com.cy.core.smsCode.entity.SmsCode;
import com.cy.smscloud.entity.Header;
import com.cy.smscloud.http.SmsCloudHttpUtils;
import com.cy.system.Global;
import com.cy.system.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("inviteSmsService")
public class InviteSmsServiceImpl  implements InviteSmsService{
    @Autowired
    private InviteSmsMapper inviteSmsMapper;

    @Autowired
    private MsgSendService msgSendService;

    @Override
    public void sendInviteSms(Message message, String content){
        try {
            InviteSms inviteSms = JSON.parseObject(content, InviteSms.class);

            //验证邀请人
            if(inviteSms.getInviterId() == null){
                message.setMsg("邀请人有误");
                message.setSuccess(false);
                return;
            }
            //验证被邀请人
            if(inviteSms.getInviteeId() == null){
                message.setMsg("被邀请人有误");
                message.setSuccess(false);
                return;
            }
            //验证是否同班
            if(inviteSms.getInviterId().substring(16).equals(inviteSms.getInviteeId().substring(16))){
                message.setMsg("被邀请人不在此班级");
                message.setSuccess(false);
                return;
            }
            //验证邀请人是否班级管理员
            String isClassAdmin = inviteSmsMapper.isClassAdmin(inviteSms.getInviterId());
            if(StringUtils.isBlank(isClassAdmin) || !isClassAdmin.equals("1")){
                message.setMsg("邀请人不是班级管理员");
                message.setSuccess(false);
                return;
            }

            Map<String, String> map = new HashMap<String, String>();
            map.put("inviterId", inviteSms.getInviterId());
            map.put("inviteeId", inviteSms.getInviteeId());

            InviteSms inviteSms1 = inviteSmsMapper.selectByTerAndTee(map);

            if( inviteSms1!=null ){

                if(org.apache.commons.lang.StringUtils.isBlank(inviteSms1.getInviteeTelephone())){
                    message.setMsg("系统未查询到被邀请者手机号");
                    message.setSuccess(false);
                    return;
                }
                inviteSms1.preInsert();
                inviteSms1.setInviterId(inviteSms.getInviterId());
                inviteSms1.setInviteeId(inviteSms.getInviteeId());

                    String X36 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    String code = "";
                    long val = new Date().getTime() - 1000000000000L;
                        while (val >= 36)
                        {
                            code = X36.charAt((int)(val % 36)) + code;
                            val /= 36;
                        }
                        if (val >= 0) code = X36.charAt((int)val) + code;


                    inviteSms1.setInviteCode(code);

                    String msg=inviteSms1.getInviterName()+"邀请您加入"+inviteSms1.getClassName()+"，您的邀请码是"+code+"。"+Global.schoolSign+"校友社交服务APP:"+Global.cy_server_url+"mobile/services/share/share.html";
                    if (Global.sign != null && Global.sign.length() > 0) {
                        if (Global.sendType != null && Global.sendType.equals("CLOUD")) {
                            msg += "（" + Global.sign + "）";
                        } else {
                            msg = "【" + Global.sign + "】" + msg;
                        }
                    }
                    String result = SmsCloudHttpUtils.sendSms(Global.smsUrl, Global.userAccount, Global.password, inviteSms1.getInviteeTelephone(), msg);
                    if("0".equals(result)){
                        inviteSmsMapper.saveInviteEvent(inviteSms1);
                        message.setMsg("发送邀请成功！");
                        message.setObj(inviteSms.getInviteeId());
                        message.setSuccess(true);
                    }else {
                        message.setMsg("发送邀请失败了：错误码" + result);
                        message.setSuccess(false);
                        message.setReturnId("500");
                        return;
                    }
            }else{
                message.setMsg("信息有误，请核对双方信息！");
                message.setSuccess(false);
                return;
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


//    public void sendInviteSms(MsgSend msgSend , String inviteeId , String inviterId) {
//
//        Header header = new Header() ;
//        header.setAppAccount(Global.userAccount);
//        header.setAppKey(Global.password);
//        Map<String,Object> body = new HashMap<String,Object>() ;
//        body.put("code","0") ;
//        body.put("mobile",msgSend.getTelphone()) ;
//        body.put("content",msgSend.getContent()) ;
//        Map<String,String> params = new HashMap<String,String>() ;
//
//
//        params.put("0",code) ;
//        body.put("params",params) ;
//        String result = SmsCloudHttpUtils.sendSmsBySubmail(Global.smsUrl,header,body) ;
//
////				String result = SmsCloudHttpUtils.sendSms(Global.smsUrl, Global.userAccount, Global.password, mobile, content) ;
//        if (result != null && result.equals("0")) {
//            MsgSend msgSend = new MsgSend();
//            msgSend.setContent(content);
//            int countNumber = 0;
//            if (content.length() % 67 == 0) {
//                countNumber = content.length() / 67;
//            } else {
//                countNumber = content.length() / 67 + 1;
//            }
//            msgSend.setDeptId("1");
//            msgSend.setTelphone(mobile);
//            msgSend.setStatues(2);// 已经发送到短信云平台
//            msgSend.setSendtime(new Date());
//            msgSend.setMsgType(1);
//            msgSend.setCountNumber(countNumber);
//            msgSend.setMessagegroup(group);
//            msgSendMapper.insertMsg(msgSend);
//
//            SmsCode smsCode = new SmsCode();
//            smsCode.setCreateTime(new Date());
//            smsCode.setTelId(mobile);
//            smsCode.setSmsCode(code);
//            smsCodeMapper.addSmsCode(smsCode);
//        }
//        if (result == null) {
//            result = "-500";
//        }
//        return Integer.parseInt(result);
//    }
}

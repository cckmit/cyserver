package com.cy.core.dataMining.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.dataMining.dao.DataMiningMapper;
import com.cy.common.utils.StringUtils;
import com.cy.core.sms.dao.MsgSendMapper;
import com.cy.core.sms.entity.MsgSend;
import com.cy.core.smsCode.entity.SmsCode;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.smscloud.entity.Header;
import com.cy.smscloud.http.SmsCloudHttpUtils;
import com.cy.system.Global;
import com.cy.system.SystemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cy.core.dataMining.entity.DataMining;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiangling on 8/4/16.
 */
@Service("dataMiningService")
public class DataMiningServiceImpl implements DataMiningService {

    @Autowired
    private DataMiningMapper dataMiningMapper;

    @Autowired
    private MsgSendMapper msgSendMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private UserInfoMapper userInfoMapper ;

    /***********************************************************************
     *
     * 【联系人】相关API（以下区域）
     *
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     *
     ***********************************************************************/

    /**
     * 添加手机号
     * @auther jiangling
     * @date 2016-08-04
     * @param message
     * @param content
     */
    public void savePhone(Message message, String content) {

        if(StringUtils.isBlank(content)) {
            message.setMsg("content is null");
            message.setSuccess(false);
            return;
        }
        DataMining potentialUser = JSON.parseObject(content,DataMining.class);

        if(potentialUser.getMinedUserId()==null || potentialUser.getMinedUserId().length()==0) {
            message.setMsg("被挖掘用户为空");
            message.setSuccess(false);
            return;
        }
        if(!potentialUser.getMinedUserId().matches("[0-9]+")){
            message.setMsg("错误的用户编号");
            message.setSuccess(false);
            return;
        }
        if(potentialUser.getMiningUserId()==null || potentialUser.getMiningUserId().length()==0) {
            message.setMsg("挖掘用户为空");
            message.setSuccess(false);
            return;
        }
        if(potentialUser.getPhone()==null || potentialUser.getPhone().length()==0) {
            message.setMsg("被挖掘手机号为空");
            message.setSuccess(false);
            return;
        }

        if(!SystemUtil.isMobileNO(potentialUser.getPhone())){
            message.setMsg("手机号格式不正确");
            message.setSuccess(false);
            return;
        }

        UserInfo userInfo = userInfoMapper.selectUserInfoByUserId(potentialUser.getMinedUserId()) ;

        if(userInfo == null){
            message.setMsg("錯誤的被邀請者編號");
            message.setSuccess(false);
            return;
        }

        if(userInfo.getAccountNum() != null || StringUtils.isNotBlank(userInfo.getAccountNum())){
            message.setMsg("被邀請者已認證，無需邀請");
            message.setSuccess(false);
            return;
        }


        //判断该用户是否已经存在在cy_data_mining表中
        //根据手机号在表中查找这个用
        long count = dataMiningMapper.countPotentialUser(potentialUser);
        //比较表中查找用户的 挖掘人和被挖掘人的id,如果相同,就覆盖同样潜在用户的信息(update)
        if(count>0) {
//            dataMiningMapper.updatePotentialUser();
            message.setMsg("该潜在用户已经存在,您无需添加");
            message.setSuccess(false);
            return;
        }
        potentialUser.setPhone(potentialUser.getPhone().replaceAll("\\s*", ""));
        //保存该信息到cy_data_mining表中
        potentialUser.preInsert();
        dataMiningMapper.insertPotentialUser(potentialUser);

        //发送邀请短信
        UserProfile userProfile = userProfileMapper.selectByAccountNum(potentialUser.getMiningUserId());


        if (Global.smsVisitTemplate != null && Global.smsVisitTemplate.length() > 0) {
            // 给该同学发送邀请短信
            String msg = Global.smsVisitTemplate;
            msg = msg.replace("${0}", userInfo.getUserName());
            msg = msg.replace("${1}", userProfile.getName());
            if (Global.sign != null && Global.sign.length() > 0) {
                if (Global.sendType != null && Global.sendType.equals("CLOUD")) {
                    msg += "（" + Global.sign + "）";

                } else {
                    msg = "【" + Global.sign + "】" + msg;
                }
            }
            MsgSend msgSend = new MsgSend();
            msgSend.setContent(msg);
            int countNumber = 0;
            if (msg.length() % 67 == 0) {
                countNumber = msg.length() / 67;
            } else {
                countNumber = msg.length() / 67 + 1;
            }
            msgSend.setTelphone(potentialUser.getPhone());
            msgSend.setStatues(9);
            msgSend.setSendtime(new Date());
            msgSend.setMsgType(1);
            msgSend.setCountNumber(countNumber);
            msgSend.setMessagegroup(SystemUtil.getOnlyNumber());
            //msgSendMapper.insertMsg(msgSend);
            message.setMsg("成功保存潜在用户,发送邀请成功!");
            message.setSuccess(true);

            sendDataMining(msgSend,userInfo.getUserName(),userProfile.getName(),message) ;
            return;
        } else {
            message.setMsg("发送邀请失败,模板未设置!");
            message.setSuccess(false);
            return;
        }

    }

    public void sendDataMining(MsgSend msgSend ,String minedUserName ,String miningUserName ,Message message) {

        Header header = new Header() ;
        header.setAppAccount(Global.userAccount);
        header.setAppKey(Global.password);
        Map<String,Object> body = new HashMap<String,Object>() ;
        body.put("code","1") ;
        body.put("mobile",msgSend.getTelphone()) ;
        body.put("content",msgSend.getContent()) ;
        Map<String,String> params = new HashMap<String,String>() ;


        params.put("0",minedUserName) ;
        params.put("1",miningUserName) ;
        params.put("2",Global.school_web_url) ;
        body.put("params",params) ;
        String result = SmsCloudHttpUtils.sendSmsBySubmail(Global.smsUrl,header,body) ;

//				String result = SmsCloudHttpUtils.sendSms(Global.smsUrl, Global.userAccount, Global.password, mobile, content) ;
        if (result != null && result.equals("0")) {
            msgSend.setStatues(2);// 已经发送到短信云平台
            msgSendMapper.insertMsg(msgSend);
            message.setMsg("成功保存潜在用户,发送邀请成功!");
            message.setSuccess(true);
        }
        if (result == null) {
            result = "-500";
            message.setMsg("短信发送失败!");
            message.setSuccess(false);
        }
//        return Integer.parseInt(result);
    }
}

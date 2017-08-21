package com.cy.core.classHandle.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.classHandle.entity.ClassHandle;
import com.cy.core.classHandle.service.ClassHandleService;
import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.core.deptInfo.service.DeptInfoService;
import com.cy.core.notify.utils.PushUtils;
import com.cy.core.sms.service.MsgSendService;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.core.userinfo.service.UserInfoService;
import com.cy.smscloud.http.SmsCloudHttpUtils;
import com.cy.system.Global;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cha0res on 2016/8/17.
 */
@Namespace("/classHandle")
@Action(value = "classHandleAction")
public class ClassHandleAction extends AdminBaseAction{
    @Autowired
    private ClassHandleService classHandleService;

    @Autowired
    private DeptInfoService deptInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MsgSendService msgSendService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private DeptMapper deptMapper;


    private String checkId;
    private String checkStatus;
    private String deptId;
    private ClassHandle classHandle;
    private String isFromCheckPage;

    //查看修改申请
    public void dataGridByDept(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);

        if(StringUtils.isNotBlank(deptId)){
            map.put("deptId",deptId);
        }
        if(classHandle != null){
            map.put("classAdmin",classHandle.getClassAdmin());
            map.put("status",classHandle.getStatus());
            map.put("type",classHandle.getType());
        }
        if(StringUtils.isNotBlank(isFromCheckPage) && isFromCheckPage.equals("1")){
            map.put("status", "10");
        }
        if(getUser().getDeptId()>1){
            map.put("currdeptId",deptInfoService.getAcademyId(getUser().getDeptId()));
        }

        super.writeJson(classHandleService.selectByDeptId(map));
    }

    //审核修改
    public void checkChanges(){
        Message message = new Message();
        try{
            if(checkStatus != null){
                if(checkStatus.equals("20")){
                    Map<String ,Object> map1 = new HashMap<String, Object>();
                    ClassHandle classHandle = classHandleService.selectById(checkId);

                    if(classHandle == null) {
                        message.setMsg("不存在此记录");
                        message.setSuccess(false);
                    }else{
                        if(classHandle.getBaseInfoId()==null){
                            message.setMsg("用户基础ID为空");
                            message.setSuccess(false);
                        }else{
                            if(classHandle.getType().equals("1")){
                                //被删除者
                                String accountNum1 = userInfoMapper.selectAccountNumByUserId(classHandle.getBaseInfoId());
                                if(accountNum1 == null || accountNum1 == ""){
                                    message.setMsg("无此用户");
                                    message.setSuccess(false);
                                }else{

                                    Dept dept = deptMapper.selectOne(classHandle.getDeptId());

                                    if(dept == null){
                                        message.setMsg("该班级已不存在");
                                        message.setSuccess(false);
                                    }else{
                                        String msg= "您被"+dept.getDeptName()+"的班级管理员移除该班";
                                        UserProfile userProfile1 = userProfileMapper.selectByAccountNum(accountNum1);

                                        UserInfo userInfoC = userInfoMapper.selectUserInfoByUserId(classHandle.getClassBaseInfoId());

                                        String accountNum2 = userInfoMapper.selectAccountNumByUserId(classHandle.getClassBaseInfoId());

                                        if( StringUtils.isNotBlank(userInfoC.getTelId())){
                                            msg="您被"+dept.getDeptName()+"的班级管理员移除该班，若有疑问请联系班级管理员("+userInfoC.getTelId()+"）";
                                        }else if(org.apache.commons.lang3.StringUtils.isNotBlank(accountNum2) ){
                                            UserProfile userProfile2 = userProfileMapper.selectByAccountNum(accountNum2);
                                            if(org.apache.commons.lang3.StringUtils.isNotBlank(userProfile2.getPhoneNum())){
                                                msg="您被"+dept.getDeptName()+"的班级管理员移除该班，若有疑问请联系班级管理员("+userProfile2.getPhoneNum()+"）";
                                            }
                                        }

                                        if (Global.sign != null && Global.sign.length() > 0) {
                                            if (Global.sendType != null && Global.sendType.equals("CLOUD")) {
                                                msg += "（" + Global.sign + "）";
                                            } else {
                                                msg = "【" + Global.sign + "】" + msg;
                                            }
                                        }
                                        // 发送短信
                                        try {
                                            SmsCloudHttpUtils.sendSms(Global.smsUrl, Global.userAccount, Global.password, userProfile1.getPhoneNum(), msg);
                                        }catch (Exception e ) {
                                            e.printStackTrace();
                                        }
                                        userInfoService.cancleAuth(classHandle.getBaseInfoId(), message);
                                        Map<String ,Object> map2 = new HashMap<String, Object>();
                                        map2.put("id",checkId);
                                        map2.put("status",checkStatus);
                                        classHandleService.updateClassHandle(map2);
                                        try {
                                            String baseId = classHandle.getBaseInfoId() ;
                                            System.out.println(PushUtils.PUSH_TYPE_ALUMNI_JOIN_AGREE);
                                            // 推送校友处理信息
                                            PushUtils.pushRemoveGroupPersonal(accountNum1, baseId, checkId);
                                            PushUtils.pushGroupAll( baseId, checkId,accountNum1, 0);

                                        } catch (Exception e ) {
                                            e.printStackTrace();
                                        }


                                        message.setMsg("操作完成");
                                        message.setSuccess(true);
                                    }

                                }
                            }else if(classHandle.getType().equals("0")){
                                map1.put("baseInfoId",classHandle.getBaseInfoId());
                                map1.put("name",classHandle.getName());
                                map1.put("telephone",classHandle.getTelephone());
                                classHandleService.updateUserInfo(map1);
                                classHandleService.updateUserBaseInfo(map1);
                                Map<String ,Object> map2 = new HashMap<String, Object>();
                                map2.put("id",checkId);
                                map2.put("status",checkStatus);
                                classHandleService.updateClassHandle(map2);
                                message.setMsg("操作完成");
                                message.setSuccess(true);
                            }else{
                                message.setMsg("操作失败，未知操作类型");
                                message.setSuccess(false);
                            }
                        }
                    }
                }else if(checkStatus.equals("30")){

                    Map<String ,Object> map2 = new HashMap<String, Object>();
                    map2.put("id",checkId);
                    map2.put("status",checkStatus);
                    classHandleService.updateClassHandle(map2);
                    message.setMsg("操作完成");
                    message.setSuccess(true);
                }else{
                    message.setMsg("参数有点问题");
                    message.setSuccess(false);
                }
            }else{
                message.setMsg("参数有点问题");
                message.setSuccess(false);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        super.writeJson(message);
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public ClassHandle getClassHandle() {
        return classHandle;
    }

    public void setClassHandle(ClassHandle classHandle) {
        this.classHandle = classHandle;
    }

    public String getIsFromCheckPage() {
        return isFromCheckPage;
    }

    public void setIsFromCheckPage(String isFromCheckPage) {
        this.isFromCheckPage = isFromCheckPage;
    }
}

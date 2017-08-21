package com.cy.core.classHandle.service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.classHandle.dao.ClassHandleMapper;
import com.cy.core.classHandle.entity.ClassHandle;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;

import com.cy.core.userinfo.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("classHandleService")
public class ClassHandleServiceImpl implements ClassHandleService{
    @Autowired
    private ClassHandleMapper classHandleMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public DataGrid<ClassHandle> selectByDeptId(Map<String, Object> map){
        DataGrid<ClassHandle> dataGrid = new DataGrid<ClassHandle>();

        long total = classHandleMapper.countByDeptId(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<ClassHandle> list = classHandleMapper.selectByDeptId(map);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public ClassHandle selectById(String id){
        return classHandleMapper.selectById(id);
    }

    public void updateUserInfo(Map<String, Object> map){
        classHandleMapper.updateUserInfo(map);
    }

    public void updateUserBaseInfo(Map<String, Object> map){
        classHandleMapper.updateUserBaseInfo(map);
    }

    public void updateClassHandle(Map<String, Object> map){
        classHandleMapper.updateClassHandle(map);
    }


    /**
     * 下面是接口，别TM乱动
     *
     * 班级管理员的接口
     * @param message
     * @param content
     */
    @Override
    public void classAdminEditClassMates(Message message, String content){

        try{
            ClassHandle classHandle = JSON.parseObject(content, ClassHandle.class);
            if(classHandle.getClassBaseInfoId() == null || classHandle.getClassBaseInfoId() == ""){
                message.setMsg("班级管理员不能为空");
                message.setSuccess(false);
                return;
            }

            if(classHandle.getBaseInfoId() == null || classHandle.getBaseInfoId() == ""){
                message.setMsg("班级成员不能为空");
                message.setSuccess(false);
                return;
            }

            if(classHandle.getType() == null || classHandle.getType() == ""){
                message.setMsg("操作类型不能为空");
                message.setSuccess(false);
                return;
            }

            if(!classHandle.getClassBaseInfoId().substring(0,16).equals(classHandle.getBaseInfoId().substring(0,16))){
                message.setMsg("班级管理员与被操作者不同班");
                message.setSuccess(false);
                return;
            }

            UserInfo userInfoC = userInfoMapper.selectUserInfoByUserId(classHandle.getClassBaseInfoId());
            if(userInfoC == null){
                message.setMsg("班级管理员不存在");
                message.setSuccess(false);
                return;
            }

            if(StringUtils.isBlank(userInfoC.getAccountNum())){
                message.setMsg("班级管理员无账号");
                message.setSuccess(false);
                return;
            }

            if(!userInfoC.getIsClassAdmin().equals("1")){
                message.setMsg("不是班级管理员无此权限");
                message.setSuccess(false);
                return;
            }

            UserInfo userInfoM = userInfoMapper.selectUserInfoByUserId(classHandle.getBaseInfoId());
            if(userInfoM == null){
                message.setMsg("被修改者不存在");
                message.setSuccess(false);
                return;
            }


            Map<String, Object> map = new HashMap<>();
            map.put("baseInfoId", classHandle.getBaseInfoId());
            map.put("status", "10");
            long count = classHandleMapper.countByDeptId(map);
            if(count > 0){
                message.init(false, "该校友尚有一条待审核记录，请以后再试", null);
                return;
            }

            if(classHandle.getType().equals("0")){
                if(StringUtils.isNotBlank(userInfoM.getAccountNum())){
                    message.setMsg("不能修改已认证用户");
                    message.setSuccess(false);
                    return;
                }
                if(classHandle.getName() == null){
                    message.setMsg("姓名不能为空");
                    message.setSuccess(false);
                    return;
                }

                classHandle.preInsert();
                classHandle.setStatus("10");
                classHandle.setDeptId(classHandle.getClassBaseInfoId().substring(0, 16));
                classHandle.setNameOld(userInfoM.getUserName());
                classHandle.setTelephoneOld(userInfoM.getTelId());

                classHandleMapper.saveClassHandle(classHandle);
                message.setMsg("更改申请已被成功提交");
                message.setSuccess(true);
                return;

            }else if(classHandle.getType().equals("1")){

                if(StringUtils.isBlank(userInfoM.getAccountNum())){
                    message.setMsg("不能移出未认证成员");
                    message.setSuccess(true);
                    return;
                }

                classHandle.preInsert();
                classHandle.setStatus("10");
                classHandle.setNameOld(userInfoM.getUserName());
                classHandle.setTelephoneOld(userInfoM.getTelId());
                classHandle.setDeptId(classHandle.getClassBaseInfoId().substring(0, 16));
                classHandleMapper.saveClassHandle(classHandle);
                message.setMsg("移出申请已被成功提交");
                message.setSuccess(true);

            }else{
                classHandleMapper.saveClassHandle(classHandle);
                message.setMsg("未知的操作类型");
                message.setSuccess(false);
                return;
            }


        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void classAdminAddClassmates(Message message, String content){
        try{

            UserInfo userInfo = JSON.parseObject(content, UserInfo.class);
            if(userInfo.getUserId() == null || userInfo.getUserId().equals("")){
                message.setMsg("班级管理员不能为空");
                message.setSuccess(false);
                return;
            }


            //驗證是否班級管理員
            UserInfo userInfoCheck = userInfoMapper.selectUserInfoByUserId(userInfo.getUserId());

            if(userInfoCheck == null || userInfoCheck.getIsClassAdmin() == null || !userInfoCheck.getIsClassAdmin().equals("1")){
                message.setMsg("不是班級管理員，無法進行此操作");
                message.setSuccess(false);
                return;
            }

            //驗證基本信息是否存在
            if(StringUtils.isBlank(userInfo.getUserName())){
                message.setMsg("姓名不能为空");
                message.setSuccess(false);
                return;
            }

            if(StringUtils.isBlank(userInfo.getSex())){
                message.setMsg("性別不能为空");
                message.setSuccess(false);
                return;
            }

            if(userInfo.getSex().equals("0")) {
                userInfo.setSex("男");
            }else if(userInfo.getSex().equals("1")){
                userInfo.setSex("女");
            }else{
                message.setMsg("非法的性別類型");
                message.setSuccess(false);
                return;
            }

            userInfo.setMajorId(userInfoCheck.getMajorId());
            userInfo.setStudentType(StringUtils.isNotBlank(userInfoCheck.getStudentType())? userInfoCheck.getStudentType():"");
            userInfo.setCheckFlag("0");

            userInfo.setClassId(userInfo.getUserId().substring(0, 16));


            userInfo.setRemarks("由班級管理員"+userInfoCheck.getUserName()+"創建");
            userInfoService.save(userInfo, null, 0);

            message.setMsg("操作成功");
            message.setSuccess(true);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

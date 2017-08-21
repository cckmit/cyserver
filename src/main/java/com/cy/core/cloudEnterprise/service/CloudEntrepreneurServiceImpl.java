package com.cy.core.cloudEnterprise.service;

import com.beust.jcommander.internal.Lists;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.CacheUtils;
import com.cy.common.utils.JsonUtils;
import com.cy.core.chatDeptGroup.dao.ChatDeptGroupMapper;
import com.cy.core.chatDeptGroup.entity.ChatDeptGroup;
import com.cy.core.chatGroup.entity.ChatGroup;
import com.cy.core.chatGroup.service.ChatGroupService;
import com.cy.core.cloudEnterprise.dao.CloudEntrepreneurMapper;
import com.cy.core.cloudEnterprise.entity.CloudEntrepreneur;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dept.service.DeptService;
import com.cy.core.notify.utils.PushUtils;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.core.userinfo.service.UserInfoService;
import com.cy.mobileInterface.authenticated.service.AuthenticatedService;
import com.cy.system.Global;
import com.cy.util.CloudPlatformUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 企业家业务逻辑实现
 *
 * @author niu
 * @create 2017-08-11 上午 11:16
 **/

@Service("cloudEntrepreneurService")
public class CloudEntrepreneurServiceImpl implements CloudEntrepreneurService  {
    private Logger logger = Logger.getLogger(CloudEntrepreneurServiceImpl.class) ;

    @Resource
    private CloudEntrepreneurMapper cloudEntrepreneurMapper;

    @Resource
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoMapper userInfoMapper ;

    @Autowired
    private UserProfileMapper userProfileMapper ;

    @Autowired
    private DeptService deptService ;

    @Autowired
    private ChatDeptGroupMapper chatDeptGroupMapper ;

    @Autowired
    private ChatGroupService chatGroupService ;

    @Autowired
    private AuthenticatedService authenticatedService ;

    @Override
    public DataGrid<CloudEntrepreneur> dataGrid(Map<String, Object> map) {
        DataGrid<CloudEntrepreneur> dataGrid = new DataGrid<CloudEntrepreneur>();

        long total = cloudEntrepreneurMapper.countEntrepreneur(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        map.put("isNotLimit",0);
        List<CloudEntrepreneur> list = cloudEntrepreneurMapper.findList(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    @Override
    public void update(CloudEntrepreneur cloudEntrepreneur) {
        cloudEntrepreneurMapper.update(cloudEntrepreneur);
    }

    @Override
    public Integer audit(String userId, CloudEntrepreneur cloudEntrepreneur) {
        Integer integer =0;
        UserInfo userInfo = userInfoService.selectByUserId(userId);
        if (userInfo !=null){
            if (StringUtils.isNotBlank(userInfo.getAccountNum())){
//                userInfoService.oneKeyAuth(userId);
            }
        }
        return integer;
    }

    @Override
    public Integer delete(String ids) {
        Integer integer =0;
        if (StringUtils.isNotBlank(ids)){
            String[] idArray = ids.split(",");
            List<String> list = new ArrayList<>();
            for (String s :idArray){
                list.add(s);
            }
           integer = cloudEntrepreneurMapper.delete(list);

        }
        return integer;
    }

    /**
     * 获取校友家信息
     * @param id
     * @return
     */
    public CloudEntrepreneur getById(String id) {
        return cloudEntrepreneurMapper.getById(id) ;
    }

    /**
     * 审核企业家
     * @param entrepreneur	企业家
     * @return
     */
    public void auditEntrepreneur(CloudEntrepreneur entrepreneur) {
        if (entrepreneur != null && StringUtils.isNotBlank(entrepreneur.getId()) && StringUtils.isNotBlank(entrepreneur.getStatus())) {
            // 判断审核状态
            String status = entrepreneur.getStatus() ;
            if (! "30".equals(status)) {
                UserProfile userProfile = null ;
                // 1. 审核通过
                if ("20".equals(status)) {
                    // 1.1 关联正式校友
                    userProfile = relationUserInfo(entrepreneur) ;
                    if (userProfile != null) {
                        entrepreneur.setAccountNum(userProfile.getAccountNum());
                    }

                } else if ("25".equals(status)) {
                    // 1.2 关联非正式校友
                    userProfile = new UserProfile() ;
                    userProfile.setPhoneNum(entrepreneur.getTelephone());
                    userProfile.setName(entrepreneur.getTeamName());
                    userProfile.setSex(entrepreneur.getSex());
                    userProfile.setPassword(entrepreneur.getTelephone().substring(0,6));

                    userProfile = checkAndSaveUserProfile(userProfile) ;
                    entrepreneur.setAccountNum(userProfile.getAccountNum());
                }
                if ("20".equals(status) || "25".equals(status)) {
                    update(entrepreneur);

                    // 3. 加入企业家班级（申请一条企业家班级校友信息）
                    checkAndJoinEntrepreneurDept(entrepreneur);
                }
            }

            if ("20".equals(status) || "25".equals(status) || "30".equals(status)) {
                // 将审核信息同步到云平台中
                syncEntrepreneur(entrepreneur) ;
            }
        }
    }

    /**
     * 同步企业家审核给云平台
     * @param entrepreneur
     * @return
     */
    public void syncEntrepreneur(CloudEntrepreneur entrepreneur) {
        String status = entrepreneur.getStatus() ;
        entrepreneur.setSyncStatus("10");
        if ("15".equals(status) ||"20".equals(status) || "25".equals(status) || "30".equals(status)) {
            // 将审核信息同步到云平台中
//            String url = "http://localhost:8090/api/v1" + "/enterprise_school/auth_team_school" ;
            String url = Global.cloud_server_url + "/enterprise_school/auth_team_school" ;
            List<NameValuePair> formparams= Lists.newArrayList();

            try {
                formparams.add(new BasicNameValuePair("bussId",entrepreneur.getTeamId()));
                formparams.add(new BasicNameValuePair("bussType","10"));
                formparams.add(new BasicNameValuePair("status",entrepreneur.getStatus()));
                formparams.add(new BasicNameValuePair("opinion",entrepreneur.getOpinion()));
                formparams.add(new BasicNameValuePair("schoolCode", Global.deptNo));
                Map<String,Object> resultMap = CloudPlatformUtils.post(url,formparams,0) ;
                System.out.println("--------> result : " + resultMap);
                if (resultMap !=null && (Integer)resultMap.get("code") == 200) {
                    entrepreneur.setSyncStatus("20");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                update(entrepreneur);
            }
        }
    }

    /**
     * 关联正式校友
     * @param entrepreneur
     * @return
     */
    public UserProfile relationUserInfo(CloudEntrepreneur entrepreneur ) {
        UserProfile userProfile = null ;
        if (entrepreneur != null && StringUtils.isNotBlank(entrepreneur.getRelationUserInfoId()) && StringUtils.isNotBlank(entrepreneur.getId())) {
            // 1. 检测关联的校友记录是否已被认证
            UserInfo userInfo = userInfoMapper.selectByUserId(entrepreneur.getRelationUserInfoId());
            if (userInfo != null) {
                if (StringUtils.isNotBlank(userInfo.getAccountNum())) {
                    // 1.1 如已认证，获取认证的用户信息
                    userProfile = userProfileMapper.selectByAccountNum(userInfo.getAccountNum()) ;
                } else {
                    // 1.2 如未认证，进行一键认证操作
                    Message message = userInfoService.oneKeyAuth(entrepreneur.getRelationUserInfoId(),entrepreneur.getTelephone()) ;
                    if (message.isSuccess()) {
                        userInfo = userInfoMapper.selectByUserId(entrepreneur.getRelationUserInfoId());
                        if (userInfo != null && StringUtils.isNotBlank(userInfo.getAccountNum())) {
                            userProfile = userProfileMapper.selectByAccountNum(userInfo.getAccountNum());
                        }
                    }
                }

            }
        }
        return userProfile ;
    }

    /**
     * 检测并创建用户
     * @param userProfile
     * @return
     */
    public UserProfile checkAndSaveUserProfile(UserProfile userProfile) {
        if (userProfile != null && StringUtils.isNotBlank(userProfile.getPhoneNum())) {
            UserProfile temp = userProfileMapper.selectByPhoneNum(userProfile.getPhoneNum()) ;
            if (temp != null) {
                return temp ;
            } else {
                userProfileMapper.save(userProfile) ;
                return userProfile ;
            }
        } else return null ;
    }

    /**
     * 检测并加入企业家班级
     * @param entrepreneur
     * @return
     */
    public UserInfo checkAndJoinEntrepreneurDept(CloudEntrepreneur entrepreneur) {
        // 检测企业家班级是否存在，不如存在创建
        Dept dept = deptService.checkAndCreateEntrepreneurDept() ;
        UserInfo userInfo = new UserInfo();
        // 判断企业家在校友企业家群中是否已存在
        Map<String,Object> params = Maps.newHashMap() ;
        params.put("deptId",dept.getDeptId()) ;
        params.put("accountNum",entrepreneur.getAccountNum()) ;
        params.put("isNotOrder","1") ;
        params.put("isNotLimit","1") ;
        List<UserInfo> tempList = userInfoMapper.selectByDeptIdNew(params) ;
        if (tempList == null || tempList.isEmpty()) {
            // 收集用户
            userInfo.setUserName(entrepreneur.getTeamName());
            userInfo.setSex(entrepreneur.getSex());
            userInfo.setTelId(entrepreneur.getTelephone());
//        userInfo.setWorkUnit(entrepreneur.getEnterpriseName());
//        userInfo.setPosition(entrepreneur.getPosition());
            userInfo.setCheckFlag("1");
//        userInfo.setAccountNum(entrepreneur.getAccountNum());


            UserProfile userProfile = userProfileMapper.selectByAccountNum(entrepreneur.getAccountNum());
            if (dept != null && userProfile != null) {
                userInfo.setClassId(dept.getDeptId());
                userInfo.setClassName(dept.getDeptName());
                userInfo.setClassFullName(dept.getFullName());
                userInfoService.save(userInfo, null, 0);
                authenticatedService.authSuccess(userProfile, userInfo);
            }

            try {
                PushUtils.pushGroupAll(userInfo.getUserId(), userInfo.getUserId(), userInfo.getAccountNum(), 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            userInfo = tempList.get(0) ;
        }

        return userInfo ;
    }

    /**
     * 解除校友企业家【成未审核状态】
     * @param entrepreneur
     */
    public void relieveEntrepreneur(CloudEntrepreneur entrepreneur) {
        // 1. 检测当前企业家是否是正式校友或名誉校友
        if (entrepreneur == null || StringUtils.isBlank(entrepreneur.getId())
                || "20".equals(entrepreneur.getStatus()) || "25".equals(entrepreneur.getStatus())) {
            logger.debug("企业家信息不能为空或企业家不为正式校友或名誉校友");
            return;
        }

//        if ("20".equals(entrepreneur.getStatus())) {
//            // 2. 如是正式校友
//            // 2.1 检测企业家关联用户，是否校友认证
//        } else if ("25".equals(entrepreneur.getStatus())) {
//            // 3. 如是名誉校友
//        }

        // 4. 退出企业家班级和聊天群组
        relieveEntrepreneurGroup(entrepreneur);

        entrepreneur.setStatus("15");
        entrepreneur.setSyncStatus("10");
        entrepreneur.setAccountNum(null);
        entrepreneur.setIsNullAccountNum("1");
        update(entrepreneur);

        // 将解除信息同步到云平台中
        if ("15".equals(entrepreneur.getStatus()) ) {
            // 将审核信息同步到云平台中
            syncEntrepreneur(entrepreneur) ;
        }


    }

    /**
     * 解除正式校友
     * @param entrepreneur
     */
    /*public void relieveFormalEntrepreneur(CloudEntrepreneur entrepreneur) {
        // 获取用户关联的校友信息
        if (entrepreneur != null && StringUtils.isNotBlank(entrepreneur.getAccountNum())) {
            String accountNum = entrepreneur.getAccountNum() ;
            Map<String ,Object> map = Maps.newHashMap() ;
            map.put("accountNum",accountNum) ;
            List<UserInfo> list = userInfoService.selectByAccountNum(map) ;
            if (list != null && !list.isEmpty()) {
                for (UserInfo userInfo : list) {
                    if ("1".equals(userInfo.getIsOneKeyAuth())) {

                    }
                }
            }

        }
    }*/
    /**
     * 从企业家群组中移除
     * @param entrepreneur
     */
    public void relieveEntrepreneurGroup(CloudEntrepreneur entrepreneur) {
        // 获取用户关联的校友信息
        if (entrepreneur != null && StringUtils.isNotBlank(entrepreneur.getAccountNum())) {
            Dept dept = deptService.checkAndCreateEntrepreneurDept() ;
            String accountNum = entrepreneur.getAccountNum() ;
            Map<String ,Object> map = Maps.newHashMap() ;
            map.put("accountNum",accountNum) ;
            map.put("deptId",dept.getDeptId()) ;
            map.put("isNotLimit","1") ;
            map.put("isNotOrder","1") ;
            List<UserInfo> list = userInfoMapper.selectByDeptIdNew(map) ;
            if (list != null && !list.isEmpty()) {
                for (UserInfo userInfo : list) {
                    userInfoService.delete(userInfo.getUserId()) ;
                }
            }

            ChatDeptGroup deptGroup = new ChatDeptGroup() ;
            deptGroup.setDeptId(dept.getDeptId());
            List<ChatDeptGroup> deptGroupList = chatDeptGroupMapper.query(deptGroup);
            if (deptGroupList != null) {
                deptGroup = deptGroupList.get(0) ;
                // 将企业家从班级群组中移除
                chatGroupService.removeMemberFromGroup(deptGroup.getGroupId(),accountNum) ;
            }

        }
    }
}

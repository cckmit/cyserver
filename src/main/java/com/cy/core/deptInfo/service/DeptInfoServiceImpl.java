package com.cy.core.deptInfo.service;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.cy.base.entity.Message;
import com.cy.base.entity.TreeString;
import com.cy.core.chatDeptGroup.dao.ChatDeptGroupMapper;
import com.cy.core.chatDeptGroup.entity.ChatDeptGroup;
import com.cy.core.chatGroup.service.ChatGroupService;
import com.cy.core.deptInfo.dao.DeptInfoMapper;
import com.cy.core.deptInfo.entity.DeptInfo;
import com.cy.core.deptInfo.entity.GetDetpList;
import com.cy.core.major.dao.MajorMapper;
import com.cy.common.utils.easyui.TreeStringUtil;
import com.cy.core.userbaseinfo.dao.UserBaseInfoMapper;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.util.PairUtil;
import com.cy.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangling on 7/14/16.
 */
@Service("deptInfoService")
public class DeptInfoServiceImpl implements DeptInfoService{
    Logger logger = Logger.getLogger(DeptInfoServiceImpl.class) ;

    @Autowired
    private DeptInfoMapper deptInfoMapper;

    @Autowired
    private MajorMapper majorMapper;

    @Override
    public List<DeptInfo> selectAll(List<DeptInfo> list) {
        return deptInfoMapper.selectAll(list);
    }

    @Override
    public void delete(String deptId) {
        try {
            List<DeptInfo> list = deptInfoMapper.selectAll2(); // 获得所有的机构
            List<TreeString> allList = new ArrayList<TreeString>(); //设立一个allist来存储所有的节点
            if (list != null && list.size() > 0) {
                for (DeptInfo deptInfo : list) {
                    TreeString node = new TreeString();
                    node.setId(deptInfo.getDeptId());
                    node.setPid(deptInfo.getParentId());
                    node.setText(deptInfo.getDeptName());
                    Map<String, Integer> attributes = new HashMap<String, Integer>();
                    attributes.put("level", deptInfo.getLevel());
                    node.setAttributes(attributes);
                    allList.add(node);
                }
            }
            List<String> deptIdList = new ArrayList<String>();
            deptIdList.add(deptId);
            TreeStringUtil.getChildren(deptId, allList, deptIdList);
            deptInfoMapper.delete(deptIdList);
            // 删除与专业的关系表
            majorMapper.deleteMajorByDeptId(deptIdList);
            // 清空归属关系
            for (String aliasName : deptIdList) {
                deptInfoMapper.updateAliasName(aliasName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<DeptInfo> selectAlterDeptTreeByUser(long userId )
    {
        return deptInfoMapper.selectAlterTreeByUser( userId );
    }

    @Override
    public String getAcademyId(long deptId){
        return deptInfoMapper.getAcademyId(deptId);
    }

    @Override
    public List<DeptInfo> selectAcademyInfo(String academyId){
        return deptInfoMapper.selectAcademyInfo(academyId);
    }

    public void selectDepts(Message message, String content){
        try{
            GetDetpList getDetpList = JSON.parseObject(content, GetDetpList.class);
            if(getDetpList == null){
                message.setMsg("无任何参数!");
                message.setSuccess(false);
                return;
            }

            if(getDetpList.getLevel() == null){
                message.setMsg("缺少查询参数level!");
                message.setSuccess(false);
                return;
            }
            //学校列表
            if(getDetpList.getLevel().equals("0")){
                List<DeptInfo> list = deptInfoMapper.selectSchools();
                message.setMsg("查询成功!");
                message.setObj(list);
                message.setSuccess(true);
                return;
            }

            //学院列表
            if(getDetpList.getLevel().equals("1")){
                if(getDetpList.getSchoolId() == null){
                    message.setMsg("请提供学校ID!");
                    message.setSuccess(false);
                    return;
                }
                List<DeptInfo> list = deptInfoMapper.selectElseList(getDetpList.getSchoolId());
                message.setMsg("查询成功!");
                message.setObj(list);
                message.setSuccess(true);
                return;
            }


            //年级列表
            if(getDetpList.getLevel().equals("2")){
                if(getDetpList.getCollegeId() == null){
                    message.setMsg("请提供学院ID!");
                    message.setSuccess(false);
                    return;
                }
                List<DeptInfo> list = deptInfoMapper.selectElseList(getDetpList.getCollegeId());
                message.setMsg("查询成功!");
                message.setObj(list);
                message.setSuccess(true);
                return;
            }

            //班级列表
            if(getDetpList.getLevel().equals("3")){
                if(getDetpList.getGradeId() == null){
                    message.setMsg("请提供年级ID!");
                    message.setSuccess(false);
                    return;
                }

                List<DeptInfo> list = deptInfoMapper.selectElseList(getDetpList.getGradeId());
                message.setMsg("查询成功!");
                message.setObj(list);
                message.setSuccess(true);
                return;
            }

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private UserInfoMapper userInfoMapper ;
    @Autowired
    private UserBaseInfoMapper userBaseInfoMapper ;
    @Autowired
    private ChatGroupService chatGroupService ;
    @Autowired
    private ChatDeptGroupMapper chatDeptGroupMapper ;

    /**
     * 判断两班级中相同的数据
     * @param oldDeptId 需要迁移数据的班级
     * @param newDeptId 数据迁移到的班级
     * @return
     */
    public PairUtil<String,PairUtil<List<PairUtil<String, PairUtil<String, String>>>,List<PairUtil<String, PairUtil<String, String>>>>> isSameOfTwoDept(String oldDeptId , String newDeptId) throws Exception {
        PairUtil<String,PairUtil<List<PairUtil<String, PairUtil<String, String>>>,List<PairUtil<String, PairUtil<String, String>>>>> result = new PairUtil<String,PairUtil<List<PairUtil<String, PairUtil<String, String>>>,List<PairUtil<String, PairUtil<String, String>>>>>() ;
        String code = "9" ;  // 0:没有相同校友数据；1：有相同校友数据；2：有相似校友数据；3：旧班级下无校友数据；9：其他错误
        // 判断校友数据相同条件
        // 1) 学号相同且不为空
        // 2) 身份证相同且不为空
        // 3) 姓名、手机号相同且不为空
        // 4) 姓名、邮箱相同且不为空
        // 判断校友数据相似条件
        // 1）名称相同，学号／身份证号／手机号／邮箱至少有一方为空


        List<PairUtil<String, PairUtil<String, String>>> sameMapList = Lists.newArrayList();      // 相同校友数据
        List<PairUtil<String, PairUtil<String, String>>> similarMapList = Lists.newArrayList();   // 相似校友数据
        // map key：原班级下校友数据编号
        // map value one : 原班级下校友数据校友名称+校友学号
        // map value two : 错误信息
        if(StringUtils.isNotBlank(oldDeptId) && StringUtils.isNotBlank(newDeptId)) {
            // 1. 判断判断新旧班级是否存在
            DeptInfo oldDept = deptInfoMapper.findById(oldDeptId) ;
            DeptInfo newDept = deptInfoMapper.findById(newDeptId) ;
            if(oldDept != null && newDept != null) {
                // 2.1 获取旧班级下所有校友数据 oldUserList
                List<UserInfo> oldUserInfoList = userInfoMapper.selectAllByDeptId(oldDeptId) ;
                if (oldUserInfoList != null && !oldUserInfoList.isEmpty())  {
                    code = "0" ;
                    List<UserInfo> newUserInfoList = userInfoMapper.selectAllByDeptId(newDeptId) ;
                    if(newUserInfoList != null && !newUserInfoList.isEmpty()) {
                        for (UserInfo oldUser : oldUserInfoList) {
                            for (UserInfo temp : newUserInfoList) {
                                PairUtil<String, PairUtil<String, String>> sameMap = null;
                                PairUtil<String, PairUtil<String, String>> similarMap = null;   // 相似校友数据

                                // 1 判断相同
                                // 1.1 学号相同且不为空
                                if(StringUtils.isNotBlank(oldUser.getStudentnumber()) &&StringUtils.isNotBlank(temp.getStudentnumber())
                                        && oldUser.getStudentnumber().equals(temp.getStudentnumber())) {
                                    code = "1";
                                    sameMap = new PairUtil(oldUser.getUserId(),
                                            new PairUtil<String, String>(oldUser.getUserName()+(StringUtils.isNotBlank(oldUser.getStudentnumber())?"-"+oldUser.getStudentnumber():""),"在【"+newDept.getFullName()+"】中具有相同学号【"+ oldUser.getStudentnumber()+"】的校友")) ;
                                    sameMapList.add(sameMap) ;
                                    break ;
                                }
                                // 1.2 身份证相同且不为空
                                else if(StringUtils.isNotBlank(oldUser.getCard()) &&StringUtils.isNotBlank(temp.getCard())
                                        && oldUser.getCard().equals(temp.getCard())) {
                                    code = "1";
                                    sameMap = new PairUtil(oldUser.getUserId(),
                                            new PairUtil<String, String>(oldUser.getUserName()+(StringUtils.isNotBlank(oldUser.getStudentnumber())?"-"+oldUser.getStudentnumber():""),"在【"+newDept.getFullName()+"】中具有相同身份证号【"+oldUser.getCard()+"】的校友")) ;
                                    sameMapList.add(sameMap) ;
                                    break ;
                                }
                                // 1.3 姓名、手机号相同且不为空
                                else if(StringUtils.isNotBlank(oldUser.getTelId()) &&StringUtils.isNotBlank(temp.getTelId())
                                        && oldUser.getTelId().equals(temp.getTelId()) && oldUser.getUserName().trim().equals(temp.getUserName().trim())) {
                                    code = "1";
                                    sameMap = new PairUtil(oldUser.getUserId(),
                                            new PairUtil<String, String>(oldUser.getUserName()+(StringUtils.isNotBlank(oldUser.getStudentnumber())?"-"+oldUser.getStudentnumber():""),"在【"+newDept.getFullName()+"】中具有相同姓名【"+oldUser.getUserName()+"】和相同手机号【"+oldUser.getTelId()+"】的校友")) ;
                                    sameMapList.add(sameMap) ;
                                    break ;
                                }
                                // 1.4 姓名、邮箱相同且不为空
                                else if(StringUtils.isNotBlank(oldUser.getEmail()) &&StringUtils.isNotBlank(temp.getEmail())
                                        && oldUser.getEmail().equals(temp.getEmail()) && oldUser.getUserName().trim().equals(temp.getUserName().trim())) {
                                    code = "1";
                                    sameMap = new PairUtil(oldUser.getUserId(),
                                            new PairUtil<String, String>(oldUser.getUserName()+(StringUtils.isNotBlank(oldUser.getStudentnumber())?"-"+oldUser.getStudentnumber():""),"在【"+newDept.getFullName()+"】中具有相同姓名【"+oldUser.getUserName()+"】和相同邮箱【"+oldUser.getEmail()+"】的校友")) ;
                                    sameMapList.add(sameMap) ;
                                    break ;
                                }
                                // 2 判断相似：名称相同，学号／身份证号／手机号／邮箱至少有一方为空
                                else if (oldUser.getUserName().trim().equals(temp.getUserName().trim())) {
                                    code = "1".equals(code) ? code : "2" ;
                                    similarMap = new PairUtil(oldUser.getUserId(),
                                            new PairUtil<String, String>(
                                                    oldUser.getUserName()+(StringUtils.isNotBlank(oldUser.getStudentnumber())?"-"+oldUser.getStudentnumber():""),
                                                    "在【"+newDept.getFullName()+"】中具有相同姓名【"+oldUser.getUserName()+"】的校友")
                                    ) ;
                                    similarMapList.add(similarMap) ;
                                    break ;
                                }
                            }
                        }
                    }
                } else code = "3";

            } else {
                if(oldDept == null ) logger.info("旧班级不存在");
                if(newDept == null ) logger.info("新班级不存在");
            }
        } else {
            logger.info("旧班级或新班级编号为空");
        }

        result.setOne(code);
        result.setTwo(new PairUtil<List<PairUtil<String, PairUtil<String, String>>>,List<PairUtil<String, PairUtil<String, String>>>>(sameMapList,similarMapList));
        return result ;
    }

    /**
     * 迁移班级下校友数据到另一个班级下
     * @param oldDeptId 需要迁移数据的班级
     * @param newDeptId 数据迁移到的班级
     * @return
     */
    public String moveUserInfoFromDeptToOtherDept(String oldDeptId ,String newDeptId) throws Exception {
        String code = "0" ;    // 0: 迁移成功；5: 未传需要迁移数据的班级或数据迁移到的班级；10：需要迁移数据的班级不存在；20：数据迁移到的班级不存在；30：需要迁移数据的班级下没有校友数据；40：两班级具有相同校友不能迁移；99：其他错误

        try {
            if (StringUtils.isNotBlank(oldDeptId) && StringUtils.isNotBlank(newDeptId)) {
                // 1. 判断判断新旧班级是否存在
                DeptInfo oldDept = deptInfoMapper.findById(oldDeptId);
                if (oldDept == null) return "10";
                DeptInfo newDept = deptInfoMapper.findById(newDeptId);
                if (newDept == null) return "20";

                ChatDeptGroup oldDeptGroup = new ChatDeptGroup();
                ChatDeptGroup newDeptGroup = new ChatDeptGroup();
                oldDeptGroup.setDeptId(oldDeptId);
                newDeptGroup.setDeptId(newDeptId);
                List<ChatDeptGroup> oldDgList = chatDeptGroupMapper.query(oldDeptGroup);
                oldDeptGroup = oldDgList != null && !oldDgList.isEmpty() ? oldDgList.get(0) : null;

                // 2. 将旧班级下的校友数据「cy_user_info」下的数据迁移到新班级下的数据中
                // 2.1 获取旧班级下所有校友数据 oldUserList
                List<UserInfo> oldUserInfoList = userInfoMapper.selectAllByDeptId(oldDeptId);
                if (oldUserInfoList == null || oldUserInfoList.isEmpty()) return "30";

                // 2.2 判断旧班级下的校友在新班级下是否已存在
                PairUtil<String,PairUtil<List<PairUtil<String, PairUtil<String, String>>>,List<PairUtil<String, PairUtil<String, String>>>>> pair = isSameOfTwoDept(oldDeptId, newDeptId);
                if ("1".equals(pair.getOne())) return "40";

                // 2.2 获取新班级下校友数据最大编号 maxNo
                List<UserInfo> userInfoList = userInfoMapper.selectAllByDeptId(newDeptId);
                int maxNo = 0;
                if (userInfoList != null && !userInfoList.isEmpty()) {
                    UserInfo userInfo = userInfoList.get(userInfoList.size() - 1);
                    maxNo = Integer.valueOf(userInfo.getUserId().substring(newDeptId.length()));
                }
                // 2.3 获取oldUserList 中的编号调整成新的编号，即「新编号 = 班级编号+原序号+最大编号」
                List<Map<String, String>> list = Lists.newArrayList();
                for (UserInfo temp : oldUserInfoList) {
                    Map<String, String> map = Maps.newHashMap();
                    map.put("oldUserId", temp.getUserId());
                    map.put("accountNum", temp.getAccountNum());
                    int userNo = Integer.valueOf(temp.getUserId().substring(newDeptId.length())) ;
                    String newUserId = newDeptId + new DecimalFormat("000").format((userNo + maxNo + 1));
                    map.put("newUserId", newUserId);
                    System.out.println("----------> 变更校友数据编号 map : " + map);
                    // 2.4 将旧校友数据调整成新校友数据
                    userInfoMapper.updateUserId(map);
                    // 3. 将旧班级下的校友档案数据「cy_user_info」下的数据迁移到新班级下的数据中
                    userBaseInfoMapper.updateUserId(map);
                    // 2.5 将已认证的校友在认证旧校友数据信息修改成新校友数据
                    UserUtils.changeUserBaseInfo(temp.getAccountNum());

                    // 2.6 将校友数据所对应的认证用户从旧班级环信群组中移除
                    if (oldDeptGroup != null)
                        chatGroupService.removeMemberFromGroup(oldDeptGroup.getGroupId(), temp.getAccountNum());
                }
                // 2.7 将原数据中的已认证的校友数据的手机用户加入到新班级环信班级群中「初始化新班级下的环信群组」
                chatGroupService.initInsertClassGroup(newDeptId);
                // 2.8 监测旧班级群组
                if (oldDeptGroup != null) chatGroupService.checkAndDeleteEmptyGroup(oldDeptGroup.getGroupId());

            } else {
                code = "5";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = "99" ;
        }
        return code ;
    }
}

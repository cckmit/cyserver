package com.cy.core.association.service;
import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.association.dao.AssociationMapper;
import com.cy.core.association.dao.AssociationMemberMapper;
import com.cy.core.association.entity.Association;
import com.cy.core.association.entity.AssociationMember;
import com.cy.core.dict.entity.Dict;
import com.cy.core.notify.utils.PushUtils;
import com.cy.core.user.entity.User;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 12/13/16.
 */
@Service("associationMemberService")
public class AssociationMemberServiceImpl implements AssociationMemberService {

    @Autowired
    AssociationMemberMapper associationMemberMapper;

    @Autowired
    AssociationMapper associationMapper;

    @Autowired
    UserProfileMapper userProfileMapper;

    public DataGrid<AssociationMember> selectAssociationMember(Map<String, Object> map){
        DataGrid<AssociationMember> dataGrid = new DataGrid<>();
        long total = associationMemberMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<AssociationMember> list = associationMemberMapper.selectAssociationMember(map);
        if(list != null && list.size() >0){
            for(int i=0; i < list.size(); i++){
                if(StringUtils.isNotBlank(list.get(i).getAllPath())){
                    list.get(i).setAllPath(list.get(i).getAllPath().replaceAll(",","</br>"));
                }
            }
        }
        dataGrid.setRows(list);
        return dataGrid;
    }

    public AssociationMember getById(String id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("isNoLimit","1");
        AssociationMember associationMember = new AssociationMember();
        List<AssociationMember> list = associationMemberMapper.selectAssociationMember(map);
        if(list != null && list.size() >0){
            associationMember = list.get(0);
        }
        return associationMember;
    }

    public AssociationMember getByAccountNum(String accountNum, String associationId){
        Map<String, Object> map = new HashMap<>();
        map.put("associationId", associationId);
        map.put("accountNum", accountNum);
        map.put("isNoLimit", "1");
        AssociationMember associationMember = new AssociationMember();
        List<AssociationMember> list = associationMemberMapper.selectAssociationMember(map);
        if(list != null && list.size() >0){
            associationMember = list.get(0);
        }
        return associationMember;
    }

    public void save(AssociationMember associationMember){
        associationMemberMapper.save(associationMember);
    }

    public void update(AssociationMember associationMember){
        associationMember.preUpdate();
        associationMemberMapper.update(associationMember);
    }

    public void delete(String ids){
        String[] array = ids.split(",");
        List<String> list = new ArrayList<>();
        for (String id : array){
            list.add(id);
        }
        associationMemberMapper.delete(list);
    }

    //获取社团成员类型标签
    public List<Dict> getAssociationMemberType(){
        return associationMapper.getAssociationType("24");
    }


    /***********************************************************************
     *
     * 【社团成员】相关API（以下区域）
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

    public void apiForAssociation(Message message, String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,Object> map = JSON.parseObject(content, Map.class);

        String accountNum = (String) map.get("accountNum");                     //成员ID
        String associationId = (String) map.get("associationId");               //社团ID
        String type = (String) map.get("type");                                 //操作类型      1:主动加入；2：主动退出；3：邀请加入；4：同意邀请；5：拒绝邀请；6：剔除
        String inviteAccountNum = (String) map.get("inviteAccountNum");         //邀请者ID
        String remarks = (String)map.get("remarks");


        if (StringUtils.isBlank(accountNum)) {
            message.init(false, "请提供成员ID", null);
            return;
        }

        if (StringUtils.isBlank(associationId)) {
            message.init(false, "请提供社团ID", null);
            return;
        }

        Association association = associationMapper.getAssociationById(associationId);
        if(association == null){
            message.init(false, "社团不存在", null);
            return;
        }

        if (StringUtils.isBlank(type)) {
            message.init(false, "请提供操作类型", null);
            return;
        }

        UserProfile userProfile = userProfileMapper.selectByAccountNum(accountNum);

        if(userProfile == null){
            message.init(false, "用户ID不存在", null);
            return;
        }

        if(("3".equals(type) || "6".equals(type)) && StringUtils.isBlank(inviteAccountNum)){
            message.init(false, "该操作需要提供管理员编号", null);
            return;
        }

        AssociationMember associationMember = new AssociationMember();
        associationMember.setAssociationId(associationId);
        associationMember.setAccountNum(accountNum);
        associationMember.setTelephone(userProfile.getPhoneNum());
        associationMember.setPosition("3");

        AssociationMember associationMemberTmp = getByAccountNum(accountNum, associationId);

        AssociationMember associationMemberAdmin = new AssociationMember();
        if(StringUtils.isNotBlank(inviteAccountNum)){
            associationMemberAdmin = getByAccountNum(inviteAccountNum, associationId);
            if( associationMemberAdmin == null || !"1".equals(associationMemberAdmin.getPosition()) && !"2".equals(associationMemberAdmin.getPosition())){
                associationMemberAdmin = null;
            }
        }

        if("1".equals(type)){                                                                       //主动加入的业务逻辑
            if(associationMemberTmp != null  && StringUtils.isNotBlank(associationMemberTmp.getId())){
                associationMember = associationMemberTmp;
                if("10".equals(associationMember.getStatus()) || "55".equals(associationMember.getStatus())){
                    message.init(false, "已经提交过申请，请耐心等待审核", null);
                }else if("15".equals(associationMember.getStatus())){
                    message.init(false, "社团高层已向您发起过邀请，请接受", null);
                }else if("20".equals(associationMember.getStatus())){
                    message.init(false, "已经是社团成员，请勿重复申请加入", null);
                }else if("25".equals(associationMember.getStatus())){
                    associationMember.setStatus("20");
                    associationMember.setRemarks(TimeZoneUtils.getFormatDate()+"撤销了退出社团的申请");
                    associationMember.preUpdate();
                    associationMemberMapper.update(associationMember);
                    message.init(true, "您已撤销退出申请，恢复为正式社员的状态", null );
                }else{
                    associationMember.setStatus("10");
                    associationMember.setRemarks(remarks);
                    associationMember.preUpdate();
                    associationMemberMapper.update(associationMember);
                    message.init(true, "成功申请", null);
                }
            }else{
                associationMember.preInsert();
                associationMember.setStatus("10");
                associationMember.setRemarks(remarks);
                associationMemberMapper.save(associationMember);
                message.init(true, "成功申请", null);
            }
        }else if("2".equals(type)){                                                                //主动退出的业务逻辑
            if(associationMemberTmp != null  && StringUtils.isNotBlank(associationMemberTmp.getId())){
                associationMember = associationMemberTmp;
                associationMember.preUpdate();
                associationMember.setQuitTime(TimeZoneUtils.getFormatDate());
                if("10".equals(associationMember.getStatus()) || "55".equals(associationMember.getStatus()) ){
                    associationMember.setRemarks("取消加入申请");
                    associationMember.setStatus("40");
                    associationMemberMapper.update(associationMember);
                    message.init(true, "你已取消加入该社团的申请", null);
                }else if("20".equals(associationMember.getStatus())){
                    associationMember.setRemarks("主动退出申请中");
                    associationMember.setStatus("25");
                    associationMemberMapper.update(associationMember);
                    message.init(true, "已提交退出社团申请", null);
                }else if("25".equals(associationMember.getStatus())){
                    message.init(true, "已提交过退出社团申请，请勿重复提交", null);
                }else{
                    message.init(false, "您与该社团没有任何关联，不必退出", null);
                }
            }else{
                message.init(false, "您与该社团没有任何关联，不必退出", null);
            }
        }else if("3".equals(type)){                                                                //邀请加入的业务逻辑

            if(associationMemberAdmin == null){
                message.init(false, "不是社长无此权限", null);
                return;
            }

            if(associationMemberTmp != null && StringUtils.isNotBlank(associationMemberTmp.getId()) ){
                if("10".equals(associationMemberTmp.getStatus()) || "55".equals(associationMemberTmp.getStatus())){
                    message.init(false, "该用户已申请加入社团，请前去同意", null);
                }else if("15".equals(associationMemberTmp.getStatus())){
                    message.init(false, "该用户已收到邀请，请等待用户同意加入", null);
                }else if("20".equals(associationMemberTmp.getStatus())){
                    message.init(false, "已经是社团成员，无需邀请", null);
                }else if("25".equals(associationMemberTmp.getStatus())){
                    message.init(false, "该成员正在申请退出社团，待其退出社团才可再次邀请", null);
                }else{
                    associationMember = associationMemberTmp;
                    associationMember.setStatus("15");
                    associationMember.preUpdate();
                    associationMemberMapper.update(associationMember);
                    message.init(true, "成功发出邀请", null);
                    PushUtils.pushJoinAssociation(association,associationMember,inviteAccountNum);
                }
            }else{
                associationMember.preInsert();
                associationMember.setStatus("15");
                associationMemberMapper.save(associationMember);
                message.init(true, "成功发出邀请", null);
                PushUtils.pushJoinAssociation(association,associationMember,inviteAccountNum);
            }
        }else if("4".equals(type)){                                                                //同意邀请业务逻辑
            if(associationMemberTmp != null && StringUtils.isNotBlank(associationMemberTmp.getId()) && "15".equals(associationMemberTmp.getStatus())){
                associationMember = associationMemberTmp;
                associationMember.setJoinTime(TimeZoneUtils.getFormatDate());
                associationMember.preUpdate();
                associationMember.setStatus("20");
                associationMemberMapper.update(associationMember);
                if(com.cy.common.utils.StringUtils.isNotBlank(association.getMemberCount())){
                    association.setMemberCount(String.valueOf(Long.parseLong(association.getMemberCount())+1));
                }else{
                    association.setMemberCount("1");
                }
                associationMapper.update(association);
                message.init(true, "欢迎加入", null);
            }else{
                message.init(false, "不存在的邀请，无法同意加入", null);
            }
        }else if("5".equals(type)){                                                                //拒绝邀请业务逻辑
            if(associationMemberTmp != null && StringUtils.isNotBlank(associationMemberTmp.getId()) && "15".equals(associationMemberTmp.getStatus())){
                associationMember = associationMemberTmp;
                associationMember.preUpdate();
                associationMember.setStatus("35");
                associationMemberMapper.save(associationMember);
                message.init(true, "拒绝操作已提交", null);
            }else{
                message.init(false, "不存在的邀请，无法拒绝加入", null);
            }
        }else if("6".equals(type)){                                                                //剔除
            if(associationMemberAdmin == null){
                message.init(false, "不是社长无此权限", null);
                return;
            }
            if(associationMemberTmp != null  && StringUtils.isNotBlank(associationMemberTmp.getId())){
                associationMember = associationMemberTmp;
                associationMember.preUpdate();
                associationMember.setQuitTime(TimeZoneUtils.getFormatDate());
                if("10".equals(associationMember.getStatus()) || "55".equals(associationMember.getStatus())){
                    associationMember.setStatus("30");
                    associationMemberMapper.save(associationMember);
                    message.init(true, "该用户正在申请加入社团，已拒绝", null);
                }else if("15".equals(associationMember.getStatus())){
                    if(associationMemberAdmin.getAccountNum().equals(associationMember.getInviteAccountNum())){
                        message.init(false, "非邀请者无法撤销邀请", null);
                        return;
                    }
                    associationMember.setDelFlag("1");
                    associationMember.setRemarks("撤销邀请");
                    associationMemberMapper.save(associationMember);
                    message.init(true, "已经撤除对该用户的邀请", null);
                }else if("20".equals(associationMember.getStatus())){
                    associationMember.setStatus("50");
                    associationMember.setQuitTime(TimeZoneUtils.getFormatDate());
                    associationMember.setRemarks("由"+inviteAccountNum+"剔除");
                    associationMemberMapper.save(associationMember);
                    if(com.cy.common.utils.StringUtils.isNotBlank(association.getMemberCount())){
                        association.setMemberCount(String.valueOf(Long.parseLong(association.getMemberCount())-1));
                    }else{
                        association.setMemberCount("0");
                    }
                    associationMapper.update(association);
                    message.init(true, "成功剔除该成员", null);
                }else if("25".equals(associationMember.getStatus())){
                    associationMember.setStatus("40");
                    associationMember.setQuitTime(TimeZoneUtils.getFormatDate());
                    associationMemberMapper.save(associationMember);
                    if(com.cy.common.utils.StringUtils.isNotBlank(association.getMemberCount())){
                        association.setMemberCount(String.valueOf(Long.parseLong(association.getMemberCount())-1));
                    }else{
                        association.setMemberCount("0");
                    }
                    associationMapper.update(association);
                    message.init(true, "您通过了该成员退社申请", null);
                }else{
                    message.init(true, "不存在该成员，无法剔除", null);
                }
            }else{
                message.init(true, "不存在该成员，无法剔除", null);
            }
        }else{
            message.init(false, "非法的操作类型", null);
        }
    }

    public void findAssociationMember(Message message, String content){
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String,String> map = JSON.parseObject(content, Map.class);
        String accountNum = map.get("accountNum");                     //成员ID
        String associationId = map.get("associationId");               //社团ID
        String page = map.get("page");
        String rows = map.get("rows");

        if(StringUtils.isBlank(associationId)){
            message.init(false,"请传入社团ID",null);
            return;
        }

        Map<String, Object> tmp = new HashMap<>();
        tmp.put("associationId", associationId);
        tmp.put("status", "20");
        if(StringUtils.isNotBlank(accountNum)){
            tmp.put("currentUserId", accountNum);
        }

        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            tmp.put("start", start);
            tmp.put("rows", Integer.valueOf(rows));
        } else {
            tmp.put("isNoLimit", "1");
        }

        List<AssociationMember> list = associationMemberMapper.selectAssociationMember(tmp);
        message.init(true,"查询成功", list);
    }
}

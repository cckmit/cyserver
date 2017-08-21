package com.cy.core.association.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.association.entity.Association;
import com.cy.core.association.entity.AssociationMember;
import com.cy.core.association.service.AssociationMemberService;
import com.cy.core.association.service.AssociationService;
import com.cy.core.user.entity.User;
import com.cy.system.GetDictionaryInfo;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 12/13/16.
 */
@Namespace("/association")
@Action(value = "associationMemberAction")
public class AssociationMemberAction extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(AssociationMemberAction.class);
    @Autowired
    AssociationMemberService associationMemberService;
    @Autowired
    AssociationService associationService;

    private AssociationMember associationMember;

    public void dataGrid(){
        Map<String, Object> map = new HashMap<String, Object>();
        User user = getUser();
        if(user != null){
            if(StringUtils.isNotBlank(user.getAssociationId())){
                map.put("associationId", user.getAssociationId());
            }else{
                map.put("status", "20");
                if ( user.getDeptId() > 1){
                    map.put("alumniId", String.valueOf(user.getDeptId()));
                }
            }
        }
        if(associationMember != null){
            if(StringUtils.isNotBlank(associationMember.getName())){
                map.put("name", associationMember.getName());
            }
            if(StringUtils.isNotBlank(associationMember.getAssociationId())){
                map.put("associationName", associationMember.getAssociationId());
            }
            if(StringUtils.isNotBlank(associationMember.getPosition())){
                map.put("position", associationMember.getPosition());
            }
            if(StringUtils.isNotBlank(associationMember.getStatus())){
                map.put("status", associationMember.getStatus());
            }
        }
        map.put("page", page);
        map.put("rows", rows);
        super.writeJson(associationMemberService.selectAssociationMember(map));
    }

    public void getById(){
        super.writeJson(associationMemberService.getById(associationMember.getId()));
    }

    public void update(){
        Message message = new Message();

        try {
            AssociationMember associationMemberTmp = associationMemberService.getById(associationMember.getId());
            associationMemberTmp.preUpdate();
            if(StringUtils.isNotBlank(associationMember.getStatus())){
                associationMemberTmp.setStatus(associationMember.getStatus());
                Association association = associationService.getAssociationById(associationMemberTmp.getAssociationId());
                if("20".equals(associationMember.getStatus())){
                    associationMemberTmp.setJoinTime(TimeZoneUtils.getFormatDate());
                    if(StringUtils.isNotBlank(association.getMemberCount())){
                        association.setMemberCount(String.valueOf(Long.parseLong(association.getMemberCount())+1));
                    }else{
                        association.setMemberCount("1");
                    }
                    associationMemberTmp.setQuitTime("");
                }else if("40".equals(associationMember.getStatus()) || "50".equals(associationMember.getStatus())){
                    if(StringUtils.isNotBlank(association.getMemberCount())){
                        association.setMemberCount(String.valueOf(Long.parseLong(association.getMemberCount())-1));
                    }else{
                        association.setMemberCount("0");
                    }
                    associationMemberTmp.setQuitTime(TimeZoneUtils.getFormatDate());
                }
                associationService.updateAssociation(association);
            }else if(StringUtils.isNotBlank(associationMember.getPosition())){
                associationMemberTmp.setPosition(associationMember.getPosition());
            }else if(StringUtils.isNotBlank(associationMember.getDelFlag())){
                associationMemberTmp.setDelFlag(associationMember.getDelFlag());
            }

            associationMemberService.update(associationMemberTmp);
            message.setMsg("修改成功");
            message.setSuccess(true);
        }catch (Exception e){
            logger.error(e, e);
            message.setMsg("修改失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }


    public void doNotNeedSecurity_getMemberType(){
        super.writeJson(associationMemberService.getAssociationMemberType());
    }

    public AssociationMember getAssociationMember() {
        return associationMember;
    }

    public void setAssociationMember(AssociationMember associationMember) {
        this.associationMember = associationMember;
    }

    /**
     * 发送短信方法
     */
    public void doNotNeedSecurity_getSmsById_dataGrid() {

        List<Map<String, String>> list = new ArrayList<>();
        if(StringUtils.isNotBlank(ids)){
            String[] tmp = ids.split(",");
            for(int i=0; i< tmp.length; i++){
                Map<String, String> map = new HashMap<>();
                AssociationMember associationMember = associationMemberService.getById(tmp[i]);
                if(associationMember != null && StringUtils.isNotBlank(associationMember.getTelephone()) && StringUtils.isNotBlank(associationMember.getName())){
                    map.put("telId", associationMember.getTelephone());
                    map.put("userName", associationMember.getName());
                    list.add(map);
                }
            }
        }
        super.writeJson(list);
    }
}

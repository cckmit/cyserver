package com.cy.core.association.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.common.utils.TimeZoneUtils;
import com.cy.core.association.entity.Association;
import com.cy.core.association.entity.AssociationHistory;
import com.cy.core.association.service.AssociationHistoryService;
import com.cy.core.association.service.AssociationService;
import com.cy.core.user.entity.User;
import com.cy.system.GetDictionaryInfo;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by cha0res on 12/13/16.
 */
@Namespace("/association")
@Action(value = "associationHistoryAction")
public class AssociationHistoryAction extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(AssociationHistoryAction.class);

    @Autowired
    private AssociationHistoryService associationHistoryService;
    @Autowired
    private AssociationService associationService;

    private AssociationHistory associationHistory;
    private String actionType;                          //操作类型 1：修改姓名；2：取消修改姓名

    public void dataGrid(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("rows", rows);
        User user = getUser();
        if(user != null && user.getDeptId() > 1){
            map.put("oldName", String.valueOf(user.getDeptId()));
        }
        if(associationHistory != null){
            if(StringUtils.isNotBlank(associationHistory.getOldName())){
                map.put("oldName", associationHistory.getOldName());
            }
            if(StringUtils.isNotBlank(associationHistory.getType())){
                map.put("type", associationHistory.getType());
            }
            if(StringUtils.isNotBlank(associationHistory.getStatus())){
                map.put("status", associationHistory.getStatus());
            }
        }
        super.writeJson(associationHistoryService.selectAssociationHistory(map));
    }

    public void getById(){
        super.writeJson(associationHistoryService.getById(associationHistory.getId()));
    }


    public void update(){
        Message message = new Message();
        try {
            if(StringUtils.isNotBlank(associationHistory.getId()) && StringUtils.isNotBlank(associationHistory.getStatus())){
                AssociationHistory associationHistoryTmp = associationHistoryService.getById(associationHistory.getId());
                associationHistoryTmp.setStatus(associationHistory.getStatus());
                Association association = associationService.getAssociationById(associationHistoryTmp.getAssociationOldId());
                if(association == null){
                    message.init(false, "社团已不存在", null);
                    super.writeJson(message);
                    return;
                }
                if("20".equals(associationHistory.getStatus())){
                    //修改生效
                    association.setChangeTime(TimeZoneUtils.getFormatDate());
                    association.preUpdate();
                    //如果是社名变更或类型变更
                    if("10".equals(associationHistoryTmp.getType())){
                        associationHistoryTmp.setAssociationId(associationHistoryTmp.getAssociationOldId());
                        if(StringUtils.isNotBlank(associationHistoryTmp.getNewName())){
                            associationHistoryTmp.setRemarks("社团名由"+associationHistoryTmp.getOldName()+"改为"+associationHistoryTmp.getNewName());
                            association.setName(associationHistoryTmp.getNewName());
                        }
                        if(StringUtils.isNotBlank(associationHistoryTmp.getNewTypeId())){
                            association.setTypeId(associationHistoryTmp.getNewTypeId());
                            associationHistoryTmp.setRemarks( (StringUtils.isNotBlank(associationHistoryTmp.getRemarks())?associationHistoryTmp.getRemarks():"") +"社团类型由"+associationHistoryTmp.getOldTypeName()+"改为"+associationHistoryTmp.getNewTypeName());
                        }
                        associationService.updateAssociation(association);
                    }
                    associationHistoryTmp.setEndTime(TimeZoneUtils.getFormatDate());
                }else if("30".equals(associationHistory.getStatus())){
                    //审核不通过

                }
                associationHistoryTmp.preUpdate();
                associationHistoryService.update(associationHistoryTmp);
                message.setMsg("操作成功");
                message.setSuccess(true);
            }else{
                message.setMsg("参数有误");
                message.setSuccess(false);
            }
        }catch (Exception e){
            logger.error(e, e);
            message.setMsg("操作失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    //提交或取消修改
    public void changeORCancel(){
        Message message=new Message();
        try {
            User user = getUser();
            if(user != null && StringUtils.isNotBlank(user.getAssociationId())){
                Map<String, Object> map = new HashMap<>();
                map.put("associationOldId" , user.getAssociationId());
                map.put("status", "10" );
                List<AssociationHistory> list = associationHistoryService.selectAssociationHistoryList(map);

                if(StringUtils.isBlank(actionType)){
                    message.init(false, "请提供操作类型",null);
                }else{
                    if("1".equals(actionType)){
                        if(list != null && list.size() >0 ){
                            message.init(false, "还有待审核的修改申请，请耐心等待", null);
                        }else{
                            associationHistory.preInsert();
                            associationHistory.setStatus("10");
                            Association associationTmp = associationService.getAssociationById(user.getAssociationId());
                            associationHistory.setOldName(associationTmp.getName());
                            associationHistory.setOldTypeId(associationTmp.getTypeId());
                            associationHistory.setStartTime(associationTmp.getChangeTime());
                            associationHistory.setAssociationOldId(associationTmp.getId());
                            associationHistory.setType("10");
                            int countChange = 0;
                            if(StringUtils.isNotBlank(associationHistory.getNewName()) && !associationTmp.getName().equals(associationHistory.getNewName())){
                                associationHistory.setOldName(associationTmp.getName());
                                countChange++;
                            }
                            if(StringUtils.isNotBlank(associationHistory.getNewTypeId()) && !associationTmp.getTypeId().equals(associationHistory.getNewTypeId())){
                                associationHistory.setOldTypeId(associationTmp.getTypeId());
                                countChange++;
                            }
                            if(countChange == 0){
                                message.init(false, "名称类型不能置为空或没有任何变动", null);

                            }else{
                                associationHistoryService.save(associationHistory);
                                message.init(true, "修改申请已提交", null);
                            }
                        }
                    }else if("2".equals(actionType)){
                        if(list != null && list.size() > 0){
                            associationHistory = list.get(0);
                            associationHistory.setDelFlag("1");
                            associationHistory.preUpdate();
                            associationHistoryService.update(associationHistory);
                            message.init(true, "成功撤销申请", null);
                        }else{
                            message.init(false, "未查询到修改申请、或已被审核", null);
                        }
                    }else{
                        message.init(false, "非法操作类型",null);
                    }
                }
            }else{
                message.init(false, "检测该账号不是社团管理员",null);
            }

        }catch (Exception e){
            logger.error(e, e);
            message.setMsg("提交失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }


    public AssociationHistory getAssociationHistory() {
        return associationHistory;
    }

    public void setAssociationHistory(AssociationHistory associationHistory) {
        this.associationHistory = associationHistory;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}

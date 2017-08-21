package com.cy.core.enterprise.action;


import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.enterprise.entity.EnterpriseTeam;
import com.cy.core.enterprise.service.EnterpriseTeamService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2017/5/15.
 */
@Namespace("/enterprise")
@Action(value = "enterpriseTeamAction")
public class EnterpriseTeamAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(EnterpriseTeamAction.class);

    @Autowired
    private EnterpriseTeamService enterpriseTeamService;

    private EnterpriseTeam enterpriseTeam;

    private String enterpriseId;

    public void getProductDataGrid(){
        Map<String, Object> map= new HashMap<>();
        map.put("page", page);
        map.put("rows", rows);

        map.put("enterpriseId", enterpriseId);

        if(enterpriseTeam != null){
            if(StringUtils.isNotBlank(enterpriseTeam.getEnterpriseId())){
                map.put("enterpriseId", enterpriseTeam.getEnterpriseId());
            }
            if(StringUtils.isNotBlank(enterpriseTeam.getFullName())){
                map.put("fullName", enterpriseTeam.getFullName());
            }
            if(StringUtils.isNotBlank(enterpriseTeam.getIsAlumni())){
                map.put("isAlumni", enterpriseTeam.getIsAlumni());
            }

        }
        DataGrid<EnterpriseTeam> dataGrid = enterpriseTeamService.dataGridProduct(map);
        super.writeJson(dataGrid);
    }


    public void getById(){
        super.writeJson(enterpriseTeamService.getById(enterpriseTeam.getId()));
    }

    public void save(){
        Message message = new Message();
        try {

            int result = enterpriseTeamService.saveProduct(enterpriseTeam);
            if(result == 0){
                message.setMsg("保存成功");
                message.setSuccess(true);
            }else if(result == 1){
                message.setMsg("组织成员不能超过6个");
                message.setSuccess(false);
            }
        } catch (Exception e) {
            message.setMsg("保存失败");
            message.setSuccess(false);
            logger.error(e,e);
        }
        super.writeJson(message);
    }


    public void update(){
        Message message = new Message();
        try {
            enterpriseTeamService.updateProduct(enterpriseTeam);
            message.setMsg("修改成功");
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("修改失败");
            message.setSuccess(false);
            logger.error(e,e);
        }
        super.writeJson(message);
    }

    public void delete() {
        Message message = new Message(); // Msg, Success, error
        try {
            enterpriseTeamService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
            logger.error(e,e);
        }
        super.writeJson(message);
    }

    public EnterpriseTeamService getEnterpriseTeamService() {
        return enterpriseTeamService;
    }

    public void setEnterpriseTeamService(EnterpriseTeamService enterpriseTeamService) {
        this.enterpriseTeamService = enterpriseTeamService;
    }

    public EnterpriseTeam getEnterpriseTeam() {
        return enterpriseTeam;
    }

    public void setEnterpriseTeam(EnterpriseTeam enterpriseTeam) {
        this.enterpriseTeam = enterpriseTeam;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}

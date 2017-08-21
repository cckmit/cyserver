package com.cy.core.cloudEnterprise.action;


import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.cloudEnterprise.entity.CloudEnterpriseTeam;
import com.cy.core.cloudEnterprise.service.CloudEnterpriseTeamService;
import com.cy.core.enterprise.entity.EnterpriseTeam;
import com.cy.core.enterprise.service.EnterpriseTeamService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/15.
 */
@Namespace("/cloudEnterprise")
@Action(value = "cloudEnterpriseTeamAction")
public class CloudEnterpriseTeamAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(CloudEnterpriseTeamAction.class);

    @Autowired
    private CloudEnterpriseTeamService cloudEnterpriseTeamService;

    private CloudEnterpriseTeam cloudEnterpriseTeam;

    private String enterpriseId;

    public void getTeamDataGrid(){
        Map<String, Object> map= new HashMap<>();
        map.put("page", page);
        map.put("rows", rows);

        map.put("enterpriseId", enterpriseId);

        if(cloudEnterpriseTeam != null){
            if(StringUtils.isNotBlank(cloudEnterpriseTeam.getEnterpriseId())){
                map.put("enterpriseId", cloudEnterpriseTeam.getEnterpriseId());
            }
            if(StringUtils.isNotBlank(cloudEnterpriseTeam.getFullName())){
                map.put("fullName", cloudEnterpriseTeam.getFullName());
            }
            if(StringUtils.isNotBlank(cloudEnterpriseTeam.getIsAlumni())){
                map.put("isAlumni", cloudEnterpriseTeam.getIsAlumni());
            }

        }
        DataGrid<CloudEnterpriseTeam> dataGrid = cloudEnterpriseTeamService.dataGridTeam(map);
        super.writeJson(dataGrid);
    }


    public void getById(){
        super.writeJson(cloudEnterpriseTeamService.getById(cloudEnterpriseTeam.getCloudTeamId()));
    }

    public void save(){
        Message message = new Message();
        try {

            int result = cloudEnterpriseTeamService.saveTeam(cloudEnterpriseTeam);
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
            cloudEnterpriseTeamService.updateTeam(cloudEnterpriseTeam);
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
            cloudEnterpriseTeamService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
            logger.error(e,e);
        }
        super.writeJson(message);
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public CloudEnterpriseTeamService getCloudEnterpriseTeamService() {
        return cloudEnterpriseTeamService;
    }

    public void setCloudEnterpriseTeamService(CloudEnterpriseTeamService cloudEnterpriseTeamService) {
        this.cloudEnterpriseTeamService = cloudEnterpriseTeamService;
    }

    public CloudEnterpriseTeam getCloudEnterpriseTeam() {
        return cloudEnterpriseTeam;
    }

    public void setCloudEnterpriseTeam(CloudEnterpriseTeam cloudEnterpriseTeam) {
        this.cloudEnterpriseTeam = cloudEnterpriseTeam;
    }
}

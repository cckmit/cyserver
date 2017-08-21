package com.cy.core.cloudEnterprise.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.cloudEnterprise.entity.CloudEnterprisePosition;
import com.cy.core.cloudEnterprise.service.CloudEnterprisePositionService;
import com.cy.core.dict.utils.DictUtils;
import com.cy.core.enterpriseJob.entity.EnterpriseJob;
import com.cy.core.enterpriseJob.service.EnterpriseJobService;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 简历基本信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-05-24
 */
@Namespace("/cloudEnterprise")
@Action(value = "cloudEnterprisePositionAction")
public class CloudEnterprisePositionAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(CloudEnterprisePositionAction.class);

    private CloudEnterprisePosition cloudEnterprisePosition;
    @Autowired
    private CloudEnterprisePositionService cloudEnterprisePositionService;

    private String EnterpriseId;

    //得到列表并且分页
    public void dataGraid() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rows", rows);
            map.put("page", page);
            if(cloudEnterprisePosition != null){
                if(StringUtils.isNotBlank(cloudEnterprisePosition.getName())){
                    map.put("name", cloudEnterprisePosition.getName());
                }
                if(StringUtils.isNotBlank(cloudEnterprisePosition.getEnterpriseId())){
                    map.put("enterpriseId", cloudEnterprisePosition.getEnterpriseId());
                }
                if(StringUtils.isNotBlank(cloudEnterprisePosition.getStatus())){
                    map.put("status", cloudEnterprisePosition.getStatus());
                }
                if(StringUtils.isNotBlank(cloudEnterprisePosition.getEducation())){
                    map.put("education", cloudEnterprisePosition.getEducation());
                }
            }
            DataGrid<CloudEnterprisePosition> data = cloudEnterprisePositionService.dataGrid(map);
            super.writeJson(data);
        }catch (Exception e){
            logger.error(e, e);

        }


    }
    //新增企业信息
    public void save(){
        Message message=new Message();
        try {
            cloudEnterprisePositionService.save(cloudEnterprisePosition);
            message.setMsg("新增成功");
            message.setSuccess(true);

        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("新增失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }

    //修改企业信息
    public void update(){
        Message message=new Message();
        try {
            cloudEnterprisePositionService.update(cloudEnterprisePosition);
            message.setMsg("修改成功");
            message.setSuccess(true);

        } catch (Exception e) {
            logger.error(e, e);
            message.setMsg("修改失败");
            message.setSuccess(false);
        }
        super.writeJson(message);
    }
    //查看企业详情
    public void getById(){
        CloudEnterprisePosition cloudEnterprisePosition=cloudEnterprisePositionService.getById(EnterpriseId);
        super.writeJson(cloudEnterprisePosition);
    }

    //删除
    public void delete(){
        Message message=new Message();
        try {
            cloudEnterprisePositionService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);

    }

    public String getEnterpriseId() {
        return EnterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        EnterpriseId = enterpriseId;
    }

    public CloudEnterprisePosition getCloudEnterprisePosition() {
        return cloudEnterprisePosition;
    }

    public void setCloudEnterprisePosition(CloudEnterprisePosition cloudEnterprisePosition) {
        this.cloudEnterprisePosition = cloudEnterprisePosition;
    }

    public CloudEnterprisePositionService getCloudEnterprisePositionService() {
        return cloudEnterprisePositionService;
    }

    public void setCloudEnterprisePositionService(CloudEnterprisePositionService cloudEnterprisePositionService) {
        this.cloudEnterprisePositionService = cloudEnterprisePositionService;
    }
}

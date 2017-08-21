package com.cy.core.enterpriseJob.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
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
@Namespace("/enterpriseJob")
@Action(value = "enterpriseJobAction")
public class EnterpriseJobAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(EnterpriseJobAction.class);

    private EnterpriseJob enterpriseJob;
    @Autowired
    private EnterpriseJobService enterpriseJobService;
    private String resourceAreaProvince;
    private String	resourceAreaCity;

    private String EnterpriseId;

    //得到列表并且分页
    public void dataGraid() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rows", rows);
            map.put("page", page);
            if(enterpriseJob != null){
                if(StringUtils.isNotBlank(enterpriseJob.getName())){
                    map.put("name", enterpriseJob.getName());
                }
                if(StringUtils.isNotBlank(enterpriseJob.getEnterpriseId())){
                    map.put("enterpriseId", enterpriseJob.getEnterpriseId());
                }
                if(StringUtils.isNotBlank(enterpriseJob.getAuditStatus())){
                    map.put("auditStatus", enterpriseJob.getAuditStatus());
                }
                if(StringUtils.isNotBlank(enterpriseJob.getStatus())){
                    map.put("status", enterpriseJob.getStatus());
                }
                if(StringUtils.isNotBlank(enterpriseJob.getEducation())){
                    map.put("education", enterpriseJob.getEducation());
                }
            }
            DataGrid<EnterpriseJob> data = enterpriseJobService.dataGrid(map);
            super.writeJson(data);
        }catch (Exception e){
            logger.error(e, e);

        }


    }
    //新增企业信息
    public void save(){
        Message message=new Message();
        try {
            String city = "";
            if (resourceAreaProvince != null && resourceAreaProvince.length() > 0) {
                city += resourceAreaProvince;
            }
            if (resourceAreaCity != null && resourceAreaCity.length() > 0) {
                city += " " + resourceAreaCity;
            }

            if (city != null && city.length() > 0) {
                enterpriseJob.setCity(city);
            }
            enterpriseJobService.save(enterpriseJob);
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
            String city = "";
            if (resourceAreaProvince != null && resourceAreaProvince.length() > 0) {
                city += resourceAreaProvince;
            }
            if (resourceAreaCity != null && resourceAreaCity.length() > 0) {
                city += " " + resourceAreaCity;
            }

            if (city != null && city.length() > 0) {
                enterpriseJob.setCity(city);
            }
            enterpriseJobService.update(enterpriseJob);
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
        EnterpriseJob enterpriseJob=enterpriseJobService.getById(EnterpriseId);
        super.writeJson(enterpriseJob);
    }

    //删除
    public void delete(){
        Message message=new Message();
        try {
            enterpriseJobService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);

    }

    public void doNotNeedSecurity_getType(){
        super.writeJson(DictUtils.getDictByTypeId(42));
    }
    public EnterpriseJob getEnterpriseJob() {
        return enterpriseJob;
    }

    public void setEnterpriseJob(EnterpriseJob enterpriseJob) {
        this.enterpriseJob = enterpriseJob;
    }

    public EnterpriseJobService getEnterpriseJobService() {
        return enterpriseJobService;
    }

    public void setEnterpriseJobService(EnterpriseJobService enterpriseJobService) {
        this.enterpriseJobService = enterpriseJobService;
    }

    public String getResourceAreaProvince() {
        return resourceAreaProvince;
    }

    public void setResourceAreaProvince(String resourceAreaProvince) {
        this.resourceAreaProvince = resourceAreaProvince;
    }

    public String getEnterpriseId() {
        return EnterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        EnterpriseId = enterpriseId;
    }

    public String getResourceAreaCity() {
        return resourceAreaCity;
    }

    public void setResourceAreaCity(String resourceAreaCity) {
        this.resourceAreaCity = resourceAreaCity;
    }
}

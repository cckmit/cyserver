package com.cy.core.cloudEnterprise.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.cloudEnterprise.entity.CloudEnterprise;
import com.cy.core.cloudEnterprise.service.CloudEnterpriseService;
import com.cy.system.Global;
import com.cy.util.Base64Utils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 云平台企业信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-08-05
 */
@Namespace("/cloudEnterprise")
@Action(value = "cloudEnterpriseAction")
public class CloudEnterpriseAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(CloudEnterpriseAction.class);

    private CloudEnterprise cloudEnterprise;
    @Autowired
    private CloudEnterpriseService cloudEnterpriseService;

    private String enterpriseId;
    private String statuses; //状态集合
    private String flag; //审核标志(1:通过/不通过；2：解除关系 )

    private String resourceAreaProvince;
    private String resourceAreaCity;
    private String area;
    private String orderFlag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    //得到列表并且分页
    public void dataGraid() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rows", rows);
            map.put("page", page);
            map.put("orderFlag", orderFlag);
            if (StringUtils.isNotBlank(statuses)){
                map.put("statuses",statuses);
            }
            if(cloudEnterprise != null){

                if(StringUtils.isNotBlank(cloudEnterprise.getAddress())){
                    map.put("address", cloudEnterprise.getAddress());
                }
                if(StringUtils.isNotBlank(cloudEnterprise.getName())){
                    map.put("name", cloudEnterprise.getName());
                }
                if(StringUtils.isNotBlank(cloudEnterprise.getIndustry())){
                    map.put("industry", cloudEnterprise.getIndustry());
                }
                if(StringUtils.isNotBlank(cloudEnterprise.getCity())){
                    map.put("area",area );
                }
                if(StringUtils.isNotBlank(cloudEnterprise.getType())){
                    map.put("type", cloudEnterprise.getType());
                }
                if(StringUtils.isNotBlank(cloudEnterprise.getContactNumber())){
                    map.put("contactNumber", cloudEnterprise.getContactNumber());
                }
                if(StringUtils.isNotBlank(cloudEnterprise.getLinkman())){
                    map.put("linkman", cloudEnterprise.getLinkman());
                }
                if(StringUtils.isNotBlank(cloudEnterprise.getLatitude())){
                    map.put("lat", Double.valueOf(cloudEnterprise.getLatitude()));
                }
                if(StringUtils.isNotBlank(cloudEnterprise.getLongitude())){
                    map.put("lng", Double.valueOf(cloudEnterprise.getLongitude()));
                }
                if (StringUtils.isNotBlank(cloudEnterprise.getProvince())){
                    map.put("province",cloudEnterprise.getProvince());
                }
                if (StringUtils.isNotBlank(cloudEnterprise.getCity())){
                    map.put("city",cloudEnterprise.getCity());
                }
                if (StringUtils.isNotBlank(cloudEnterprise.getStatus())){
                    map.put("status",cloudEnterprise.getStatus());
                }
            }
            DataGrid<CloudEnterprise> data = cloudEnterpriseService.dataGrid(map);
            super.writeJson(data);
        }catch (Exception e){
            logger.error(e, e);

        }


    }
    //新增企业信息
    public void save(){
        Message message=new Message();
        try {
            //富文本是否转换 Lixun 2017.5.5
            if( Global.IS_RICH_TEXT_CONVERT == 1 ) {
                String content = Base64Utils.getFromBase64(cloudEnterprise.getDescription().replaceAll("</?[^>]+>", ""));
                cloudEnterprise.setDescription(content);
            }
            String city = "";
            if (resourceAreaProvince != null && resourceAreaProvince.length() > 0) {
                city += resourceAreaProvince;
            }
            if (resourceAreaCity != null && resourceAreaCity.length() > 0) {
                city += " " + resourceAreaCity;
            }

            if (city != null && city.length() > 0) {
                cloudEnterprise.setCity(city);
            }
            cloudEnterpriseService.save(cloudEnterprise);
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
            //富文本是否转换 Lixun 2017.5.5
            if( Global.IS_RICH_TEXT_CONVERT == 1 ) {
                String content = Base64Utils.getFromBase64(cloudEnterprise.getDescription().replaceAll("</?[^>]+>", ""));
                cloudEnterprise.setDescription(content);
            }

            String city = "";
            String status = "";
            if (resourceAreaProvince != null && resourceAreaProvince.length() > 0) {
                city += resourceAreaProvince;
            }
            if (resourceAreaCity != null && resourceAreaCity.length() > 0) {
                city += " " + resourceAreaCity;
            }

            if (city != null && city.length() > 0) {
                cloudEnterprise.setCity(city);
            }

            cloudEnterpriseService.update(cloudEnterprise);
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
        try{
            CloudEnterprise cloudEnterprise=cloudEnterpriseService.getByCloudId(enterpriseId);
            super.writeJson(cloudEnterprise);
        }catch (Exception e){
            logger.error(e,e);
        }

    }
    //校企认证
    public void certification(){
        Message message=new Message();
        try{
            if ("1".equals(flag)){
                cloudEnterpriseService.updateByCloudId(cloudEnterprise);
                cloudEnterpriseService.auditEnterprise(cloudEnterprise.getCloudId());
                message.setMsg("审核成功！");
                message.setSuccess(true);
            }else if ("2".equals(flag)){
                cloudEnterpriseService.updateByCloudId(cloudEnterprise);
                cloudEnterpriseService.auditEnterprise(cloudEnterprise.getCloudId());
                message.setMsg("解除校企关系成功！");
                message.setSuccess(true);
            }else {
                message.setMsg("数据异常！");
                message.setSuccess(false);
            }
            super.writeJson(message);
        }catch (Exception e){
            message.setMsg("失败！");
            message.setSuccess(false);
            logger.error(e,e);
        }
    }

    //删除
    public void delete(){
        Message message=new Message();
        try {
            cloudEnterpriseService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);

    }


    public void doNotNeedSessionAndSecurity_test(){
        cloudEnterpriseService.syncTeamToSchoolByQuartz();
    }

    public void doNotNeedSecurity_getEnterpriseList(){
        super.writeJson(cloudEnterpriseService.enterprisesList());
    }

    public CloudEnterprise getCloudEnterprise() {
        return cloudEnterprise;
    }

    public void setCloudEnterprise(CloudEnterprise cloudEnterprise) {
        this.cloudEnterprise = cloudEnterprise;
    }

    public CloudEnterpriseService getCloudEnterpriseService() {
        return cloudEnterpriseService;
    }

    public void setCloudEnterpriseService(CloudEnterpriseService cloudEnterpriseService) {
        this.cloudEnterpriseService = cloudEnterpriseService;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getResourceAreaProvince() {
        return resourceAreaProvince;
    }

    public void setResourceAreaProvince(String resourceAreaProvince) {
        this.resourceAreaProvince = resourceAreaProvince;
    }

    public String getResourceAreaCity() {
        return resourceAreaCity;
    }

    public void setResourceAreaCity(String resourceAreaCity) {
        this.resourceAreaCity = resourceAreaCity;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        this.statuses = statuses;
    }
}

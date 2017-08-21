package com.cy.core.enterprise.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.dict.entity.Dict;
import com.cy.core.dict.service.DictService;
import com.cy.core.dict.utils.DictUtils;
import com.cy.core.enterprise.entity.Enterprise;
import com.cy.core.enterprise.service.EnterpriseService;
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
 * <p>Description: 企业信息</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2016-1-6
 */
@Namespace("/enterprise")
@Action(value = "enterpriseAction")
public class EnterpriseAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(EnterpriseAction.class);

    private Enterprise enterprise;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private DictService dictService;

    private String enterpriseId;

    private String resourceAreaProvince;
    private String	resourceAreaCity;
    private String orderFlag;
    private String dictTypeValue;

    //得到列表并且分页
    public void dataGraid() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("rows", rows);
            map.put("page", page);
            map.put("orderFlag", orderFlag);

            if(enterprise != null){
                if(StringUtils.isNotBlank(enterprise.getAddress())){
                    map.put("address", enterprise.getAddress());
                }
                if(StringUtils.isNotBlank(enterprise.getName())){
                    map.put("name", enterprise.getName());
                }
                if(StringUtils.isNotBlank(enterprise.getType())){
                    map.put("type", enterprise.getType());
                }
                if(StringUtils.isNotBlank(enterprise.getContactNumber())){
                    map.put("contactNumber", enterprise.getContactNumber());
                }
                if(StringUtils.isNotBlank(enterprise.getLinkman())){
                    map.put("linkman", enterprise.getLinkman());
                }
                if(StringUtils.isNotBlank(enterprise.getLatitude())){
                    map.put("lat", Double.valueOf(enterprise.getLatitude()));
                }
                if(StringUtils.isNotBlank(enterprise.getLongitude())){
                    map.put("lng", Double.valueOf(enterprise.getLongitude()));
                }
            }
            DataGrid<Enterprise> data = enterpriseService.dataGrid(map);
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
                String content = Base64Utils.getFromBase64(enterprise.getDescription().replaceAll("</?[^>]+>", ""));
                enterprise.setDescription(content);
            }
            String city = "";
            if (resourceAreaProvince != null && resourceAreaProvince.length() > 0) {
                city += resourceAreaProvince;
            }
            if (resourceAreaCity != null && resourceAreaCity.length() > 0) {
                city += " " + resourceAreaCity;
            }

            if (city != null && city.length() > 0) {
                enterprise.setCity(city);
            }
            enterpriseService.save(enterprise);
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
                String content = Base64Utils.getFromBase64(enterprise.getDescription().replaceAll("</?[^>]+>", ""));
                enterprise.setDescription(content);
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
                enterprise.setCity(city);
            }
            /*if(status != null && status.length() > 0){
                enterpriseService.updateEnterpriseByAduit();
            }else{
                enterpriseService.save(enterprise);
            }*/

            enterpriseService.update(enterprise);
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
        Message message=new Message();
        try{
            Enterprise enterprise=enterpriseService.getById(enterpriseId);
            super.writeJson(enterprise);
        }catch (Exception e){
            logger.error(e,e);
        }

    }

    //删除
    public void delete(){
        Message message=new Message();
        try {
            enterpriseService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);

        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
        }
        super.writeJson(message);

    }

    public void certification(){
        Message message=new Message();
        enterpriseService.update(enterprise);
        message.setMsg("认证成功！");
        message.setSuccess(true);
        super.writeJson(message);
    }

    public void doNotNeedSecurity_getFinancingStage(){
        String id =dictService.selectDictTypeIdByDictTypeValue(dictTypeValue);
        List<Dict> dicts=dictService.getDictByDictTypeId(Long.parseLong(id));
        super.writeJson(dicts);
    }

    public void doNotNeedSecurity_getType(){
        super.writeJson(DictUtils.getDictByTypeId(25));
    }

    public void doNotNeedSecurity_getEnterpriseList(){
        super.writeJson(enterpriseService.enterprisesList());
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public EnterpriseService getEnterpriseService() {
        return enterpriseService;
    }

    public void setEnterpriseService(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    public void setResourceAreaProvince(String resourceAreaProvince) {
        this.resourceAreaProvince = resourceAreaProvince;
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

    public DictService getDictService() {
        return dictService;
    }

    public void setDictService(DictService dictService) {
        this.dictService = dictService;
    }

    public String getDictTypeValue() {
        return dictTypeValue;
    }

    public void setDictTypeValue(String dictTypeValue) {
        this.dictTypeValue = dictTypeValue;
    }
}

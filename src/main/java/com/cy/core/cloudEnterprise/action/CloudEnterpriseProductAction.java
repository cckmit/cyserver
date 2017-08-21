package com.cy.core.cloudEnterprise.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.cloudEnterprise.entity.CloudEnterpriseProduct;
import com.cy.core.cloudEnterprise.service.CloudEnterpriseProductService;
import com.cy.core.dict.utils.DictUtils;
import com.cy.core.enterprise.entity.EnterpriseProduct;
import com.cy.core.enterprise.service.EnterpriseProductService;
import com.cy.system.Global;
import com.cy.util.Base64Utils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cha0res on 1/6/17.
 */
@Namespace("/cloudEnterprise")
@Action(value = "cloudEnterpriseProductAction")
public class CloudEnterpriseProductAction extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(CloudEnterpriseProductAction.class);

    @Autowired
    private CloudEnterpriseProductService cloudEnterpriseProductService;

    private CloudEnterpriseProduct cloudEnterpriseProduct;

    private String enterpriseId;

    public void getProductDataGrid(){
        Map<String, Object> map= new HashMap<>();
        map.put("page", page);
        map.put("rows", rows);

        map.put("enterpriseId", enterpriseId);

        if(cloudEnterpriseProduct != null){
            if(StringUtils.isNotBlank(cloudEnterpriseProduct.getEnterpriseId())){
                map.put("enterpriseId", cloudEnterpriseProduct.getEnterpriseId());
            }
            if(StringUtils.isNotBlank(cloudEnterpriseProduct.getName())){
                map.put("name", cloudEnterpriseProduct.getName());
            }
            if(StringUtils.isNotBlank(cloudEnterpriseProduct.getType())){
                map.put("type", cloudEnterpriseProduct.getType());
            }
        }
        DataGrid<CloudEnterpriseProduct> dataGrid = cloudEnterpriseProductService.dataGridProduct(map);
        super.writeJson(dataGrid);
    }


    public void getById(){
        super.writeJson(cloudEnterpriseProductService.getById(cloudEnterpriseProduct.getId()));
    }

    public void save(){
        Message message = new Message();
        try {
            int result = cloudEnterpriseProductService.saveProduct(cloudEnterpriseProduct);
            if(result == 0){
                message.setMsg("保存成功");
                message.setSuccess(true);
            }else if(result == 1){
                message.setMsg("企业产品不能超过5个");
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
            cloudEnterpriseProductService.updateProduct(cloudEnterpriseProduct);
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
            cloudEnterpriseProductService.delete(ids);
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

    public CloudEnterpriseProductService getCloudEnterpriseProductService() {
        return cloudEnterpriseProductService;
    }

    public void setCloudEnterpriseProductService(CloudEnterpriseProductService cloudEnterpriseProductService) {
        this.cloudEnterpriseProductService = cloudEnterpriseProductService;
    }

    public CloudEnterpriseProduct getCloudEnterpriseProduct() {
        return cloudEnterpriseProduct;
    }

    public void setCloudEnterpriseProduct(CloudEnterpriseProduct cloudEnterpriseProduct) {
        this.cloudEnterpriseProduct = cloudEnterpriseProduct;
    }
}

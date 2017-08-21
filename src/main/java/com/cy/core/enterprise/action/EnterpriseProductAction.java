package com.cy.core.enterprise.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataEntity;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.dict.utils.DictUtils;
import com.cy.core.enterprise.entity.Enterprise;
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
@Namespace("/enterprise")
@Action(value = "enterpriseProductAction")
public class EnterpriseProductAction extends AdminBaseAction {
    private static final Logger logger = Logger.getLogger(EnterpriseProductAction.class);

    @Autowired
    private EnterpriseProductService enterpriseProductService;

    private EnterpriseProduct enterpriseProduct;

    private String enterpriseId;

    public void getProductDataGrid(){
        Map<String, Object> map= new HashMap<>();
        map.put("page", page);
        map.put("rows", rows);

        map.put("enterpriseId", enterpriseId);

        if(enterpriseProduct != null){
            if(StringUtils.isNotBlank(enterpriseProduct.getEnterpriseId())){
                map.put("enterpriseId", enterpriseProduct.getEnterpriseId());
            }
            if(StringUtils.isNotBlank(enterpriseProduct.getName())){
                map.put("name", enterpriseProduct.getName());
            }
            if(StringUtils.isNotBlank(enterpriseProduct.getType())){
                map.put("type", enterpriseProduct.getType());
            }
        }
        DataGrid<EnterpriseProduct> dataGrid = enterpriseProductService.dataGridProduct(map);
        super.writeJson(dataGrid);
    }


    public void getById(){
        super.writeJson(enterpriseProductService.getById(enterpriseProduct.getId()));
    }

    public void save(){
        Message message = new Message();
        try {
            //富文本是否转换 Lixun 2017.5.5
            if( Global.IS_RICH_TEXT_CONVERT == 1 ) {
                String content = Base64Utils.getFromBase64(enterpriseProduct.getDescription().replaceAll("</?[^>]+>", ""));
                enterpriseProduct.setDescription(content);
            }
            int result = enterpriseProductService.saveProduct(enterpriseProduct);
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
            //富文本是否转换 Lixun 2017.5.5
            if( Global.IS_RICH_TEXT_CONVERT == 1 ) {
                String content = Base64Utils.getFromBase64(enterpriseProduct.getDescription().replaceAll("</?[^>]+>", ""));
                enterpriseProduct.setDescription(content);
            }
            enterpriseProductService.updateProduct(enterpriseProduct);
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
            enterpriseProductService.delete(ids);
            message.setMsg("删除成功");
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("删除失败");
            message.setSuccess(false);
            logger.error(e,e);
        }
        super.writeJson(message);
    }

    public void doNotNeedSecurity_getProductType(){
        super.writeJson(DictUtils.getDictByTypeId(26));
    }

    public EnterpriseProduct getEnterpriseProduct() {
        return enterpriseProduct;
    }

    public void setEnterpriseProduct(EnterpriseProduct enterpriseProduct) {
        this.enterpriseProduct = enterpriseProduct;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}

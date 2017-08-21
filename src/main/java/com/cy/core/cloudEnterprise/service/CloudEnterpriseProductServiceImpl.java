package com.cy.core.cloudEnterprise.service;

/**
 * Created by cha0res on 1/6/17.
 */

import com.cy.base.entity.DataGrid;
import com.cy.common.utils.EditorUtils;
import com.cy.common.utils.StringUtils;
import com.cy.core.cloudEnterprise.dao.CloudEnterpriseMapper;
import com.cy.core.cloudEnterprise.dao.CloudEnterpriseProductMapper;

import com.cy.core.cloudEnterprise.entity.CloudEnterpriseProduct;
import com.cy.core.share.dao.FileMapper;
import com.cy.core.share.entity.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("cloudEnterpriseProductService")
public class CloudEnterpriseProductServiceImpl implements CloudEnterpriseProductService {

    @Autowired
    private CloudEnterpriseProductMapper cloudEnterpriseProductMapper;

    @Autowired
    private FileMapper fileMapper;

    public DataGrid<CloudEnterpriseProduct> dataGridProduct(Map<String, Object> map){
        long count = cloudEnterpriseProductMapper.count(map);
        DataGrid<CloudEnterpriseProduct> dataGrid = new DataGrid<>();
        dataGrid.setTotal(count);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<CloudEnterpriseProduct> list = cloudEnterpriseProductMapper.findEnterPriseProductList(map);
        if (list != null && list.size() > 0) {
            for (CloudEnterpriseProduct enterpriseProduct : list) {
                //关联分享图片
                if (enterpriseProduct != null && StringUtils.isNotBlank(enterpriseProduct.getId())) {
                    Map<String, Object> tmp = new HashMap<String, Object>();
                    tmp.put("fileGroup", enterpriseProduct.getId());
                    tmp.put("bussType","50");
                    List<File> fileList = fileMapper.selectList(tmp);
                    enterpriseProduct.setFileList(fileList);
                }

            }
        }
        dataGrid.setRows(list);
        return dataGrid;
    }

    public CloudEnterpriseProduct getById(String id){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("isNotLimit", "1");
        List<CloudEnterpriseProduct> list = cloudEnterpriseProductMapper.findEnterPriseProductList(map);
        CloudEnterpriseProduct enterpriseProduct = new CloudEnterpriseProduct();

        if(enterpriseProduct != null && org.apache.commons.lang3.StringUtils.isNotBlank(enterpriseProduct.getDescription())) {
            enterpriseProduct.setDescription(EditorUtils.changeSrcFromRelativeToAbsolute(enterpriseProduct.getDescription()));
        }
        if(list != null && list.size()>0){
            enterpriseProduct = list.get(0);
        }
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("fileGroup", enterpriseProduct.getId());
        map2.put("bussType","50");
        List<File> fileList = fileMapper.selectList(map2);
        enterpriseProduct.setFileList(fileList);
        return enterpriseProduct;
    }

    public int saveProduct(CloudEnterpriseProduct enterpriseProduct){
        Map<String, Object> map = new HashMap<>();
        map.put("enterpriseId",enterpriseProduct.getEnterpriseId());
        long count = cloudEnterpriseProductMapper.count(map);
        if(count > 5){
            return 1;
        }
        enterpriseProduct.preInsert();
        cloudEnterpriseProductMapper.save(enterpriseProduct);
        return 0;
    }

    public int updateProduct(CloudEnterpriseProduct enterpriseProduct){
        enterpriseProduct.preUpdate();
        cloudEnterpriseProductMapper.update(enterpriseProduct);
        return 0;
    }

    public int delete(String ids){
        String[] array = ids.split(",");
        for(String id : array){
            CloudEnterpriseProduct enterpriseProduct = getById(id);
            enterpriseProduct.preUpdate();
            enterpriseProduct.setDelFlag("1");
            cloudEnterpriseProductMapper.update(enterpriseProduct);
        }
        return 0;
    }

}

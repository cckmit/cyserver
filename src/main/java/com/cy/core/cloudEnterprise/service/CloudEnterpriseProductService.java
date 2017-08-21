package com.cy.core.cloudEnterprise.service;

import com.cy.base.entity.DataGrid;

import com.cy.core.cloudEnterprise.entity.CloudEnterpriseProduct;


import java.util.Map;

/**
 * Created by cha0res on 1/6/17.
 */
public interface CloudEnterpriseProductService {
    DataGrid<CloudEnterpriseProduct> dataGridProduct(Map<String, Object> map);
    CloudEnterpriseProduct getById(String id);
    int saveProduct(CloudEnterpriseProduct cloudEnterpriseProduct);
    int updateProduct(CloudEnterpriseProduct cloudEnterpriseProduct);
    int delete(String ids);

}

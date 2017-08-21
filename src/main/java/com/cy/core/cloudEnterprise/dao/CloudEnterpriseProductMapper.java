package com.cy.core.cloudEnterprise.dao;


import com.cy.core.cloudEnterprise.entity.CloudEnterpriseProduct;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 1/6/17.
 */
public interface CloudEnterpriseProductMapper {
    void save(CloudEnterpriseProduct cloudEnterpriseProduct);
    void update(CloudEnterpriseProduct cloudEnterpriseProduct);
    List<CloudEnterpriseProduct> findEnterPriseProductList(Map<String, Object> map);
    long count(Map<String, Object> map);
    void delete(String id);
    void updateByCloudId(CloudEnterpriseProduct cloudEnterpriseProduct);
}

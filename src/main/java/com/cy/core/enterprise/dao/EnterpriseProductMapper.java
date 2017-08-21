package com.cy.core.enterprise.dao;

import com.cy.core.enterprise.entity.EnterpriseProduct;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 1/6/17.
 */
public interface EnterpriseProductMapper {
    void save(EnterpriseProduct enterpriseProduct);
    void update(EnterpriseProduct enterpriseProduct);
    List<EnterpriseProduct> findEnterPriseProductList(Map<String, Object> map);
    long count(Map<String, Object> map);
    void updateByCloudId(EnterpriseProduct enterpriseProduct);
    void delete(String id);
}

package com.cy.core.enterprise.dao;

import com.cy.core.enterprise.entity.EnterpriseMatch;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2017/7/29.
 */
public interface EnterpriseMatchMapper {

    List<EnterpriseMatch> findList(Map<String,Object> map);

    void insert(EnterpriseMatch enterpriseMatch);
    void update(EnterpriseMatch enterpriseMatch);

    void deleteByBussIdAndType(Map<String,Object> map);
}


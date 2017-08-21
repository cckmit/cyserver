package com.cy.core.cloudEnterprise.dao;

import com.cy.core.cloudEnterprise.entity.CloudEntrepreneur;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2017/8/11.
 */
public interface CloudEntrepreneurMapper {

    long countEntrepreneur(Map<String,Object> map);
    List<CloudEntrepreneur> findList(Map<String,Object> map);

    CloudEntrepreneur getById(String id);

    Integer insert(CloudEntrepreneur cloudEntrepreneur);

    Integer update(CloudEntrepreneur cloudEntrepreneur);

    Integer delete(List<String> ids);
}

package com.cy.core.dataMining.dao;

import com.cy.core.dataMining.entity.DataMining;

import java.util.List;

/**
 * Created by jiangling on 8/4/16.
 */
public interface DataMiningMapper {

    /**
     * @auther:jiangling
     * @date: 2016-08-04
     * 保存潜在用户
     * @param dataMining
     */
    void insertPotentialUser(DataMining dataMining);

    /**
     * @auther:jiangling
     * @date: 2016-08-04
     * 根据电话找到潜在用户
     * @param dataMining
     */
    long countPotentialUser(DataMining dataMining);

    /**
     * @auther jiangling
     * @date 2016-08-05
     * 根据userId获得潜在用户信息,主要是手机号
     * @param userId
     * @return
     */
    List<DataMining> getPotentialUser(String userId);
}

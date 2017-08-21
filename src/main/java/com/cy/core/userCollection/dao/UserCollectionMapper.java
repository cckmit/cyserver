package com.cy.core.userCollection.dao;

import com.cy.core.userCollection.entity.UserCollection;

/**
 * Created by Administrator on 2017/8/2.
 */
public interface UserCollectionMapper {
    //查出收集用户的总数
     Long userCollectionCount();
    //插入收集用户表
     void insertUserCollection(UserCollection userCollection);
}

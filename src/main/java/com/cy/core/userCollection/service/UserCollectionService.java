package com.cy.core.userCollection.service;

import com.cy.base.entity.Message;

/**
 * Created by Administrator on 2017/8/2.
 */
public interface UserCollectionService {
    /**
     * 收集用户信息
     *
     * @param message
     */
    void saveUserCollection(Message message, String content);
}

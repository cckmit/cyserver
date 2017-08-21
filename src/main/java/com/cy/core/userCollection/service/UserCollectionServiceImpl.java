package com.cy.core.userCollection.service;


import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.userCollection.dao.UserCollectionMapper;
import com.cy.core.userCollection.entity.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("UserCollectionService")
public class UserCollectionServiceImpl implements UserCollectionService {
    @Autowired
    private UserCollectionMapper userCollectionMapper;

    @Override
    public void saveUserCollection(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("请求数据不能为空");
            message.setSuccess(false);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        //用户ID
        String accountNum = (String) map.get("accountNum");
        //地址
        String address = (String) map.get("address");
        if (StringUtils.isBlank(accountNum)) {
            message.setMsg("用户ID不能为空");
            message.setSuccess(false);
            return;
        }
        if (StringUtils.isBlank(address)) {
            message.setMsg("用户收集地址不能为空");
            message.setSuccess(false);
            return;
        }
        UserCollection userCollection = new UserCollection();
        userCollection.setAccountNum(accountNum);
        userCollection.setAddress(address);
        userCollectionMapper.insertUserCollection(userCollection);
        Long total = userCollectionMapper.userCollectionCount();
        message.setMsg("收集用户信息成功");
        message.setObj(total);
        message.setSuccess(true);
    }
}
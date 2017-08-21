package com.cy.core.weiXin.dao;

import com.cy.core.weiXin.entity.WeiXinAccount;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2016/12/26.
 */
public interface WeiXinAccountMapper {

    void insert(WeiXinAccount weiXinAccount);

    void update(WeiXinAccount weiXinAccount);

    WeiXinAccount selectById(String id);

    List<WeiXinAccount> selectList(Map<String,Object>map);
}

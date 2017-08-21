package com.cy.core.weiXin.service;

import com.cy.base.entity.Message;
import com.cy.core.weiXin.entity.WeiXinAccount;

import java.util.List;
import java.util.Map;

/**
 * Created by niu on 2016/12/26.
 */
public interface WeiXinAccountService {

    /**
     * 保存公众号配置信息
     * @param weiXinAccount
     */
    void  save(WeiXinAccount weiXinAccount);

    /**
     * 根据accountId(公众号编号)得到公众号信息
     * @param accountId
     * @return
     */
    WeiXinAccount selectById(String accountId);

    /**
     * 通过accountType获取公众号列表
     * @param map
     * @return
     */
    List<WeiXinAccount> getList(Map<String,Object> map);

    /**
     * 通过accountType获取公众号配置详情接口
     * @param message
     * @param content
     * @return
     */
    void findWeiXinAccountSetting(Message message, String content);

}

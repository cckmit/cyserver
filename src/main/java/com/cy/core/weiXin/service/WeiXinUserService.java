package com.cy.core.weiXin.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.weiXin.entity.WeiXinUser;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/3.
 */
public interface WeiXinUserService {
    /**
     * 获取列表
     * @param map
     * @return
     */
    public List<WeiXinUser> findList(Map<String,Object> map) ;
    /**
     * 获取总数
     * @param map
     * @return
     */
    public Long findCount(Map<String,Object> map) ;

    DataGrid<WeiXinUser> dataGrid(Map<String, Object> map);

    WeiXinUser getById(String id);
    WeiXinUser save(WeiXinUser weiXinUser);
    WeiXinUser update(WeiXinUser weiXinUser);

    long countWechatAccount(Map<String,Object> map);

    WeiXinUser saveUserInfoByOpenId(String openId, String appId);

    void saveWeiXinUser(Message message, String content);
    void findWeiXinUser(Message message, String content);
    void removeWeiXinUser(Message message, String content);

    /**
     * 通过code获取微信用户账户信息
     * @param message
     * @param content
     */
    void findWeiXinUserAuthor(Message message, String content);


    /**
     * 通过微信服务器保存图片
     */
    void saveWeiXinPic(Message message, String content);

    void updateLocalWeChatUser();
}

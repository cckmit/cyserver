package com.cy.core.weiXin.service;

import com.cy.base.entity.Message;
import com.cy.common.utils.wechat.entity.Button;
import com.cy.core.weiXin.entity.WeiXinMenu;
import com.cy.util.PairUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/24.
 */
public interface WeiXinMenuService {

    List<WeiXinMenu> query(Map<String, Object> map);

    WeiXinMenu getById(String id);

    WeiXinMenu getByName(String name);

    void save(WeiXinMenu weiXinMenu);

    void update(WeiXinMenu weiXinMenu);

    PairUtil<String,Button[]> getButton(Map<String,Object> map);

    /**
     * 通过accountAppId获取公众帐号APPID所对应的菜单
     * @param message
     * @param content
     * @return
     */
    void findWeiXinMenu(Message message, String content);

    void findWeiXinMenuButton(Message message,String content);

    void getAuthUrl(Message message,String content);
}

package com.cy.core.weiXin.dao;

import com.cy.core.weiXin.entity.WeiXinMenu;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 12/13/16.
 */
public interface WeiXinMenuMapper {

    //查询微信菜单
    List<WeiXinMenu> query(Map<String, Object> map);

    //查询微信菜单详情
    WeiXinMenu getById(String id);

    //通过菜单名查询微信菜单
    WeiXinMenu getByName(String name);

    //新增微信菜单
    void save(WeiXinMenu weiXinMenu);

    //更新微信菜单
    void update(WeiXinMenu weiXinMenu);

}

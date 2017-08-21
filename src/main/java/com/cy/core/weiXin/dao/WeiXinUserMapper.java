package com.cy.core.weiXin.dao;

import com.cy.core.weiXin.entity.WeiXinUser;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/3.
 */
public interface WeiXinUserMapper {
    //查出总数
    public long countWeiXinUser(Map<String, Object> map);
    //查出列表
    public List<WeiXinUser> selectWeiXinUser(Map<String, Object> map);
    //根据ID查询单个的详细信息
    public WeiXinUser getById(String id);

    //添加一条
    void insert(WeiXinUser weiXinUser);
    //修改
    void update(WeiXinUser weiXinUser);


}

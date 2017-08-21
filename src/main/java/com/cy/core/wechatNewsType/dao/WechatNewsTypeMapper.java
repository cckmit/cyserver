package com.cy.core.wechatNewsType.dao;

import com.cy.core.wechatNewsType.entity.WechatNewsType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * USER jiangling
 * DATE 8/9/16
 * To change this template use File | Settings |File Templates.
 */
public interface WechatNewsTypeMapper {

    long countWechatNewsType(Map<String, Object> map);

    List<WechatNewsType> selectWechatNews(Map<String,Object> map);

    public WechatNewsType getById(String id);

    public void save(WechatNewsType wechatNewsType);

    public void deleteById(String id);

    public void deleteByPid(String id);

    public void update(WechatNewsType wechatNewsType);

}

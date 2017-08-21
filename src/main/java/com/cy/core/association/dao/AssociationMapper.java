package com.cy.core.association.dao;

import com.cy.core.association.entity.Association;
import com.cy.core.dict.entity.Dict;
import com.cy.core.user.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 12/13/16.
 */
public interface AssociationMapper {

    //查出总数
    public Long countAssociation(Map<String, Object> map);
    //查出列表
    public List<Association> selectAssociation(Map<String, Object> map);
    //添加一条
    Long insert(Association association);
    //修改
    void update(Association association);
    //批量删除
    void delete(List<String> list);
    //根据ID查询单个的详细信息
    public Association getAssociationById(String id);
    //查询字典标签
    List<Dict> getAssociationType(String type);
    List<Association> getAssociationList(String name);
    Association getAssociationInfoByAssociationAndUserId(Map<String,String> map);
}

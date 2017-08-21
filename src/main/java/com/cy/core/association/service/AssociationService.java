package com.cy.core.association.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.association.entity.Association;
import com.cy.core.dict.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 12/13/16.
 */
public interface AssociationService {

    /**
     * 获取列表
     * @param map
     * @return
     */
    public List<Association> findList() ;
    /**
     * 获取总数
     * @param map
     * @return
     */
    public Long findCount(Map<String,Object> map) ;

    DataGrid<Association> dataGrid(Map<String, Object> map);
    int saveAssociation(Association association);
    int deleteAssociation(String ids);
    int updateAssociation(Association association);
    Association getAssociationById(String id);
    List<Dict> getAssociationType();
    void findAssociationList(Message message, String content);

    /**
     * 社团详情
     * @param message
     * @param content
     */
    void findAssociationInfo(Message message, String content);
}

package com.cy.core.association.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.association.entity.AssociationHistory;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 12/13/16.
 */
public interface AssociationHistoryService {

    DataGrid<AssociationHistory> selectAssociationHistory(Map<String, Object> map);

    List<AssociationHistory> selectAssociationHistoryList(Map<String, Object> map);


    AssociationHistory getById(String id);

    void save(AssociationHistory associationHistory);

    void update(AssociationHistory associationHistory);

    void delete(String ids);

}

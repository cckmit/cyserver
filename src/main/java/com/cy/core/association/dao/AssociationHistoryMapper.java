package com.cy.core.association.dao;

import com.cy.core.association.entity.AssociationHistory;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 12/13/16.
 */
public interface AssociationHistoryMapper {
    List<AssociationHistory> selectAssociationHistory(Map<String, Object> map);

    long count(Map<String, Object> map);

    void save(AssociationHistory associationHistory);

    void update(AssociationHistory associationHistory);

    void delete(List<String> list);
}

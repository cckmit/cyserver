package com.cy.core.association.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.association.entity.Association;
import com.cy.core.association.entity.AssociationMember;
import com.cy.core.dict.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 12/13/16.
 */
public interface AssociationMemberService {
    DataGrid<AssociationMember> selectAssociationMember(Map<String, Object> map);

    AssociationMember getById(String id);

    AssociationMember getByAccountNum(String accountNum, String associationId);

    void save(AssociationMember associationMember);

    void update(AssociationMember associationMember);

    void delete(String ids);

    void apiForAssociation(Message message, String content);

    List<Dict> getAssociationMemberType();

    void findAssociationMember(Message message, String content);
}

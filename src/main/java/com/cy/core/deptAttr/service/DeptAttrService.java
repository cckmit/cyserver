package com.cy.core.deptAttr.service;

import com.cy.core.deptAttr.entity.DeptAttr;
import com.cy.core.deptInfo.entity.DeptInfo;

import java.util.List;

/**
 * Created by Cha0res on 2016/7/25.
 */
public interface DeptAttrService {

    List<DeptInfo> selectAlterDeptTreeByUser( long userId );
}

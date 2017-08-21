package com.cy.core.deptAttr.service;

import com.cy.core.deptAttr.dao.DeptAttrMapper;
import com.cy.core.deptAttr.entity.DeptAttr;
import com.cy.core.deptInfo.entity.DeptInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cha0res on 2016/7/25.
 */
@Service("deptAttrService")
public class DeptAttrServiceImpl implements DeptAttrService {

    @Autowired
    private DeptAttrMapper deptAttrMapper;

    @Override
    public List<DeptInfo> selectAlterDeptTreeByUser(long userId )
    {
        return deptAttrMapper.selectAlterDeptTreeByUser( userId );
    }
}

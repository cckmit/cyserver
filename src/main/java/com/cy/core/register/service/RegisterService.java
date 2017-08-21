package com.cy.core.register.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.register.entity.Register;

import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 2015/3/8.
 */
public interface RegisterService {

    DataGrid<Register> dataGrid(Map<String, Object> map);

    Register getById(long id);

    void save(Register register);

    void update(Register register);

    void delete(String id);

    List<Register> selectAll();
}

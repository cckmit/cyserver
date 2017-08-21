package com.cy.core.register.dao;

import com.cy.core.register.entity.Register;

import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 2015/3/8.
 */
public interface RegisterMapper {

    List<Register> query(Map<String, Object> map);

    List<Register> queryAll();

    long count(Map<String, Object> map);

    Register getById(long id);

    void add(Register register);

    void update(Register register);

    void delete(List<Long> list);

}

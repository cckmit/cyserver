package com.cy.core.electronicBook.dao;


import com.cy.core.electronicBook.entity.ElectronicBook;

import java.util.List;
import java.util.Map;
/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 电子刊物</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-3-14
 */

public interface ElectronicBookMapper {

    List<ElectronicBook> query(Map<String, Object> map);

    List<ElectronicBook> queryAll();

    long count(Map<String, Object> map);

    ElectronicBook getById(String id);

    void add(ElectronicBook electronicBook);

    void update(ElectronicBook electronicBook);

    void delete(List<String> list);
}

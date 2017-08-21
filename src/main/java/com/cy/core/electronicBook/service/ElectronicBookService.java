package com.cy.core.electronicBook.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.activity.entity.Activity;
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

public interface ElectronicBookService {

    DataGrid<ElectronicBook> dataGrid(Map<String, Object> map);

    ElectronicBook getById(String id);

    void save(ElectronicBook electronicBook);

    void update(ElectronicBook electronicBook);

    void delete(String id);

    List<ElectronicBook> selectAll();

    void findElectronicBookList(Message message, String content);

    /*void deleteActivity(Message message, String content);*/




}

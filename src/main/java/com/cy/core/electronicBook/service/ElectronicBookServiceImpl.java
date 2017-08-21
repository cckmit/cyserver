package com.cy.core.electronicBook.service;


import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.electronicBook.dao.ElectronicBookMapper;
import com.cy.core.electronicBook.entity.ElectronicBook;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * <p>Title: ChatContacts</p>
 * <p>Description: 电子刊物</p>
 *
 * @author 王傲辉
 * @Company 博视创诚
 * @data 2017-3-14
 */

@Service("electronicBookService")
public class ElectronicBookServiceImpl implements ElectronicBookService {
    @Autowired
    private ElectronicBookMapper electronicBookMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    public DataGrid<ElectronicBook> dataGrid(Map<String, Object> map) {
        DataGrid<ElectronicBook> dataGrid = new DataGrid<ElectronicBook>();
        long total = electronicBookMapper.count(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<ElectronicBook> list = electronicBookMapper.query(map);
        dataGrid.setRows(list);
        return dataGrid;
    }


    public ElectronicBook getById(String id) {
        return electronicBookMapper.getById(id);
    }


    public void save(ElectronicBook electronicBook) {
        electronicBook.preInsert();
        electronicBookMapper.add(electronicBook);
    }


    public void update(ElectronicBook electronicBook) {
        if (electronicBook == null)
            throw new IllegalArgumentException("ElectronicBook cannot be null!");

        electronicBook.preUpdate();
        electronicBookMapper.update(electronicBook);
    }


    public void delete(String ids) {
        String[] array = ids.split(",");
        List<String> list = new ArrayList<String>();
        for (String id : array) {
            list.add(id);
        }
        electronicBookMapper.delete(list);
    }


    public List<ElectronicBook> selectAll() {
        return electronicBookMapper.queryAll();
    }

    public ElectronicBookMapper getElectronicBookMapper() {
        return electronicBookMapper;
    }

    public void setElectronicBookMapper(ElectronicBookMapper electronicBookMapper) {
        this.electronicBookMapper = electronicBookMapper;
    }


    /***********************************************************************
     *
     * 【返校计划】相关API（以下区域）
     *
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     *
     ***********************************************************************/

    /**
     * 获取书刊列表接口
     *
     * @param message
     * @param content
     */
    public void findElectronicBookList(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, Object> map = JSON.parseObject(content, Map.class);
        String page = (String) map.get("page");
        String rows = (String) map.get("rows");
        if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
            int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
            map.put("start", start);
            map.put("rows", Integer.valueOf(rows));
        } else {
            map.put("isNotLimit", "1");
        }

        long total = electronicBookMapper.count(map);
        List<ElectronicBook> list = electronicBookMapper.query(map);
        List<ElectronicBook> rlist = new ArrayList<ElectronicBook>();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                ElectronicBook electronicBook = list.get(i);
                if (electronicBook != null && StringUtils.isNotBlank(electronicBook.getUploadUrl())) {
                    boolean flag = WebUtil.checkUrl(electronicBook.getUploadUrl());
                    if (flag) {
                        rlist.add(list.get(i));
                    }
                }
            }
        }
        DataGrid<ElectronicBook> dataGrid = new DataGrid<>();
        dataGrid.setRows(rlist);
        dataGrid.setTotal(total);

        message.init(true, "查询成功", dataGrid);

    }


}

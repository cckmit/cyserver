package com.cy.core.wechatNewsType.service;


import com.cy.base.entity.DataGrid;
import com.cy.core.wechatNewsType.entity.WechatNewsType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * USER jiangling
 * DATE 8/9/16
 * To change this template use File | Settings |File Templates.
 */

public interface WechatNewsTypeService {
    /**
     * @desc 查询所有的微信新闻
     * @author jiangling
     * @date 8/9/16 4:58 PM
     * @param
     * @return
     */
//    DataGrid<WechatNewsType> dataGrid(Map<String, Object> map);

    List<WechatNewsType> wechatNewsTypes(Map<String, Object> map);

    WechatNewsType getById(String id);

    String save(WechatNewsType wechatNewsType);

    void delete(String id);

    void update(WechatNewsType wechatNewsType);

}

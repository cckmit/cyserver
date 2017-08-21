package com.cy.core.wechatNewsType.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.wechatNewsType.dao.WechatNewsTypeMapper;
import com.cy.core.wechatNewsType.entity.WechatNewsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * USER jiangling
 * DATE 8/9/16
 * To change this template use File | Settings |File Templates.
 */
@Service("wechatNewsTypeService")
public class WechatNewsTypeServiceImpl implements WechatNewsTypeService {

    @Autowired
    private WechatNewsTypeMapper wechatNewsTypeMapper;

/*    @Override
    public DataGrid<WechatNewsType> dataGrid(Map<String, Object> map) {
        DataGrid<WechatNewsType> dataGrid = new DataGrid<WechatNewsType>();
        long total = wechatNewsTypeMapper.countWechatNewsType(map);
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<WechatNewsType> list = wechatNewsTypeMapper.selectWechatNews(map);
        dataGrid.setRows(list);
        return dataGrid;

    }*/


    public List<WechatNewsType> wechatNewsTypes(Map<String, Object> map){
        return wechatNewsTypeMapper.selectWechatNews(map);
    }

    public WechatNewsType getById(String id){
        return wechatNewsTypeMapper.getById(id);
    }

    public String save(WechatNewsType wechatNewsType){
        long parentId = wechatNewsType.getParentId();
        if(parentId > 0){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("parentId", parentId);
            long count = wechatNewsTypeMapper.countWechatNewsType(map);
            if(count > 5){
                return "2";
            }
        }
        wechatNewsTypeMapper.save(wechatNewsType);
        return "1";
    }

    public void delete(String id){
        WechatNewsType wechatNewsType = wechatNewsTypeMapper.getById(id);
        long parentId = wechatNewsType.getParentId();
        wechatNewsTypeMapper.deleteById(id);
        if(parentId == 0){
            wechatNewsTypeMapper.deleteByPid(id);
        }
    }

    public void update(WechatNewsType wechatNewsType){
        wechatNewsTypeMapper.update(wechatNewsType);
    }
}

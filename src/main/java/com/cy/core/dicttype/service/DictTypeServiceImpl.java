package com.cy.core.dicttype.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.CacheUtils;
import com.cy.common.utils.StringUtils;
import com.cy.core.dict.entity.Dict;
import com.cy.core.dict.utils.DictUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.dict.dao.DictMapper;
import com.cy.core.dicttype.dao.DictTypeMapper;
import com.cy.core.dicttype.entity.DictType;
import com.cy.system.GetDictionaryInfo;

@Service("dictTypeService")
public class DictTypeServiceImpl implements DictTypeService {
    @Autowired
    private DictTypeMapper dictTypeMapper;
    @Autowired
    private DictMapper dictMapper;

    public DataGrid<DictType> dataGridDictType(Map<String, Object> map) {
        long total = dictTypeMapper.countDcitType(map);
        DataGrid<DictType> dataGrid = new DataGrid<DictType>();
        dataGrid.setTotal(total);
        int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
        map.put("start", start);
        List<DictType> list = dictTypeMapper.selectDictType(map);
        dataGrid.setRows(list);
        return dataGrid;
    }

    public void addDictType(DictType dictType) {
        dictTypeMapper.addDictType(dictType);
        GetDictionaryInfo.getInstance().loadDict();
    }

    public void deleteDictType(long id) {
        try {
            dictMapper.deleteByDictTypeId(id);
            dictTypeMapper.deleteDictType(id);
            GetDictionaryInfo.getInstance().loadDict();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DictType selectById(String id) {
        return dictTypeMapper.selectById(Integer.parseInt(id));
    }

    public int updateDictType(DictType dictType) {
        int flag = dictTypeMapper.updateDictType(dictType);
        if (flag > 0) {
            GetDictionaryInfo.getInstance().loadDict();
        }
        return flag;
    }

    /**
     * 方法isRepetition 的功能描述：判断字典类别的名称或值是否有重复
     * @createAuthor niu
     * @createDate 2017-04-17 18:40:47
     * @param dictType
     * @return java.lang.String
     * @throw
     *
     */
    public String isRepetition(DictType dictType) {

        //flag :0 表示没有重复的，1 表示有重复的
        String flag = "0";
        List<DictType> dictTypeList = dictTypeMapper.selectAll();
        if (dictTypeList != null && !dictTypeList.isEmpty()) {
            for (DictType dictType1 : dictTypeList) {
                if (dictType1.getDictTypeId() != dictType.getDictTypeId() && (dictType.getDictTypeName().equals(dictType1.getDictTypeName()) || dictType.getDictTypeValue().equals(dictType1.getDictTypeValue()))) {
                    flag = "1";
                    return flag;
                }
            }
        }
        return flag;
    }

    /***********************************************************************
     * 【字典】相关API（以下区域）
     * <p>
     * 注意事项：
     * 1、方法名-格式要求
     * 创建方法：save[Name]
     * 撤销方法：remove[Name]
     * 查询分页列表方法：find[Name]ListPage
     * 查询列表方法：find[Name]List
     * 查询详细方法：find[Name]
     ***********************************************************************/


    /**
     * 查询字典类型接口
     *
     * @param message
     * @param content
     */
    public void findDictTypeList(Message message, String content) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(content)) {
            Map<String, Object> tmp = JSON.parseObject(content, Map.class);
            String page = (String) tmp.get("page");
            String rows = (String) tmp.get("rows");
            if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
                int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
                map.put("start", start);
                map.put("rows", Integer.valueOf(rows));
            } else {
                map.put("isNoLimit", "1");
            }
        } else {
            map.put("isNoLimit", "1");
        }
        List<DictType> list = dictTypeMapper.selectDictType(map);
        long total = dictTypeMapper.countDcitType(map);
        DataGrid<DictType> dataGrid = new DataGrid<>();
        dataGrid.setTotal(total);
        dataGrid.setRows(list);
        message.init(true, "查询成功", dataGrid);
    }

    /**
     * 查询字典信息接口
     *
     * @param message
     * @param content
     *
     * update_des :添加通过字典类别值查询字典
     */
    public void findDictList(Message message, String content) {
        if (StringUtils.isBlank(content)) {
            message.setMsg("未传入参数");
            message.setSuccess(false);
            return;
        }
        Map<String, String> map = JSON.parseObject(content, Map.class);
        //先判断是否有字典类别值 ：有根据字典类别值获取字典类别在根据类别编号获取字典列表，不存在再判断是否有字典类别编号
        String dictTypeValue = map.get("dictTypeValue");
        long dictTypeId = 0;
        Boolean code = true;
        String typeId = null;
        if (StringUtils.isNotBlank(dictTypeValue)){
            Map<String, Object> objectMap = Maps.newHashMap();
            objectMap.put("dictTypeValue",dictTypeValue);
            objectMap.put("isNoLimit",1);
            List<DictType> dictTypeList = dictTypeMapper.selectDictType(objectMap);
            if (dictTypeList !=null && !dictTypeList.isEmpty()){
                dictTypeId = dictTypeList.get(0).getDictTypeId();
            }
        }else {
            typeId = map.get("dictTypeId");
            try {
                dictTypeId  = Long.parseLong(typeId);
                code = true;
            } catch (NumberFormatException e) {
                code = false;
            }
        }
        if (code) {
            List<Dict> list = DictUtils.getDictByTypeId(dictTypeId);
            if (list != null && list.size() > 0) {
                message.init(true, "查询成功", list);
            } else {
                message.init(false, "未查询到该字典数据", null);
            }
        } else {
            message.init(false, "非法的字典类型", null);
        }
    }
}

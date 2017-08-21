package com.cy.core.dict.utils;

import com.beust.jcommander.internal.Maps;
import com.cy.common.utils.SpringContextHolder;
import com.cy.common.utils.StringUtils;
import com.cy.core.dict.dao.DictMapper;
import com.cy.core.dict.entity.Dict;
import com.cy.core.dicttype.dao.DictTypeMapper;
import com.cy.core.dicttype.entity.DictType;

import java.util.List;
import java.util.Map;

/**
 * Created by cha0res on 1/6/17.
 */
public class DictUtils {
    private static DictMapper dictMapper = SpringContextHolder.getBean("dictMapper");
    private static DictTypeMapper dictTypeMapper = SpringContextHolder.getBean("dictTypeMapper");

    public static List<Dict> getDictByTypeId(long id){
        List<Dict> list = dictMapper.selectByDictTypeId(id);
        return list;
    }

    /**
     * 方法findDictList 的功能描述：通过字典类别编号或值查询字典列表
     * @createAuthor niu
     * @createDate 2017-04-18 15:20:19
     * @param map
     * @return java.util.List<com.cy.core.dict.entity.Dict>
     * @throw
     *
     */

    public static List<Dict> findDictList(Map<String,String> map) {
        //如果有字典类型值，先根据类型值查
        String dictTypeValue = map.get("dictTypeValue");
        long dictTypeId = 0;
        String typeId = null;
        //先判断是否有字典类别值 ：有根据字典类别值获取字典类别在根据类别编号获取字典列表，不存在再判断是否有字典类别编号
        if (StringUtils.isNotBlank(dictTypeValue)) {
            Map<String, Object> objectMap = Maps.newHashMap();
            objectMap.put("dictTypeValue", dictTypeValue);
            objectMap.put("isNoLimit", 1);
            List<DictType> dictTypeList = dictTypeMapper.selectDictType(objectMap);
            if (dictTypeList != null && !dictTypeList.isEmpty()) {
                dictTypeId = dictTypeList.get(0).getDictTypeId();
            }
        } else {
            //如果没有字典类型值，再根据类型编号查
            typeId = map.get("dictTypeId");
            if (StringUtils.isNotBlank(typeId)) {
                try {
                    dictTypeId = Long.parseLong(typeId);
                } catch (NumberFormatException e) {

                }
            }
        }
        List<Dict> list = DictUtils.getDictByTypeId(dictTypeId);
        return list;
    }
}

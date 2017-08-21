package com.cy.core.dict.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cy.common.utils.StringUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.dict.dao.DictMapper;
import com.cy.core.dict.entity.Dict;
import com.cy.system.GetDictionaryInfo;

@Service("dictService")
public class DictServiceImpl implements DictService {
	private DictMapper dictMapper;

	public DictMapper getDictMapper() {
		return dictMapper;
	}

	@Autowired
	public void setDictMapper(DictMapper dictMapper) {
		this.dictMapper = dictMapper;
	}

	public DataGrid<Dict> dataGridDict(Map<String, Object> map) {
		long count = dictMapper.countDict(map);
		DataGrid<Dict> dataGrid = new DataGrid<Dict>();
		dataGrid.setTotal(count);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Dict> list = dictMapper.selectDict(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public void addDict(Dict dictModel) {
		dictMapper.addDict(dictModel);
		GetDictionaryInfo.getInstance().loadDict();
	}

	public void deleteDict(long id) {
		dictMapper.deleteDict(id);
		GetDictionaryInfo.getInstance().loadDict();
	}

	public Dict selectDictById(String id) {
		return dictMapper.selectDictById(Integer.parseInt(id));
	}

	public int updateDict(Dict dictModel) {
		return dictMapper.updateDict(dictModel);
	}

	public List<Dict> selectByDictTypeValue(String dictTypeValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dictTypeValue", dictTypeValue);
		return dictMapper.selectByDictTypeValue(map);
	}

	@Override
	public List<Dict> selectByDictTypeId(long dictTypeId) {
		return dictMapper.selectByDictTypeId(dictTypeId);
	}
	/**
	 * 方法isRepetition 的功能描述：判断该字典类型的字典的名称或值是否有重复
	 * @createAuthor niu
	 * @createDate 2017-04-17 16:00:45
	 * @param dict
	 * @return java.lang.String
	 * @throw
	 *
	 * updateData：04-24
	 */

	public String isRepetition(Dict dict){
		//flag :0 表示没有重复的，1 表示有重复的
		String flag = "0";
		List<Dict> dictList = Lists.newArrayList();
		if (dict != null && dict.getDictTypeId()>0) {
			dictList = selectByDictTypeId(dict.getDictTypeId());
			if (dictList !=null && !dictList.isEmpty()){
				for (Dict dict1:dictList){
					//只判断值是否重复
					if (dict1.getDictId()!=dict.getDictId()&&(dict.getDictValue().equals(dict1.getDictValue()))){
						flag = "1";
						return flag;
					}
				}
			}
		}
		return flag;
	}



	public String selectDictTypeIdByDictTypeValue(String dictTypeValue){
		return dictMapper.selectDictTypeIdByDictTypeValue(dictTypeValue);
	}
	public List<Dict> getDictByDictTypeId(long dictTypeId){
		return dictMapper.getDictByDictTypeId(dictTypeId);
	};
}

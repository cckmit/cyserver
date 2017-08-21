package com.cy.core.roster.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.dict.entity.Dict;
import com.cy.core.roster.dao.RosterMapper;
import com.cy.core.roster.entity.Roster;
import com.cy.util.WebUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service("rosterService")
public class RosterServiceImpl implements RosterService {

	@Autowired
	private RosterMapper rosterMapper;


	/**--列表数据--**/
	public DataGrid<Roster> dataGrid(Map<String, Object> map) {
		DataGrid<Roster> dataGrid = new DataGrid<Roster>();
		long total = rosterMapper.count(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Roster> list = rosterMapper.query(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	/**--添加的保存--**/
	public boolean save(Map<String, Object> map) {
		Roster query = new Roster();
		query.setRef_id((String)map.get("ref_id"));
		query.setDict_id((Integer)map.get("dict_id"));
		query.setType((Integer)map.get("type"));
		List<Roster> list = rosterMapper.getRoster(query);
		if(list!=null && list.size() > 0){
			return false;
		}else{
			rosterMapper.add(map);
			return true;
		}
	}

	/**--删除--**/
	public void delete(int id) {
		rosterMapper.deleteById(id);
	}
	
	/**--批量删除--**/
	public void delete(String ids,int type) {
		String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (int i=0;i<array.length;i++)
		{
			list.add(WebUtil.toLong(array[i]) );
		}
		if(list.size()> 0){
			Map map = new HashMap();
			map.put("list", list);
			map.put("type", type);
			rosterMapper.delete(map);
		}
	}

	/**--数据行数--**/
	public long count(Map<String, Object> map){
		return rosterMapper.count(map);
	}
	
	/**--根据字典名称查询--**/
	public Map getDictByName(String dictName){
		return rosterMapper.getDictByName(dictName);
	}
	
}

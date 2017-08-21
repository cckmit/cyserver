package com.cy.core.roster.service;

import com.cy.base.entity.DataGrid;
import com.cy.core.dict.entity.Dict;
import com.cy.core.roster.entity.Roster;

import java.util.*;

public interface RosterService {

	/**--数据分业借口--**/
	public DataGrid<Roster> dataGrid(Map<String, Object> map);

	/**--添加的保存--**/
	public boolean save(Map<String, Object> map);

	/**--删除--**/
	public void delete(int id);
	
	/**--查询数据行数--**/
	public long count(Map<String, Object> map);
	
	/**--批量删除--**/
	public void delete(String ids,int type);
	
	/**--根据字典名称查询--**/
	public Map getDictByName(String dictName);
	
	
}

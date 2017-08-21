package com.cy.core.roster.dao;

import com.cy.core.dict.entity.Dict;
import com.cy.core.roster.entity.Roster;
import java.util.List;
import java.util.Map;

public interface RosterMapper {
	
	/**--分页集合数据--**/
	public List<Roster> query(Map<String, Object> map);
	
	/**--查询数据行数--**/
	public long count(Map<String, Object> map);
	
	/**--条件查询--**/
	public List<Roster> getRoster(Roster item);
	
	/**--添加--**/
	public void add(Map<String, Object> map);
	
	/**--删除--**/
	public void deleteById(int id);
	
	/**--批量删除--**/
	public void delete(Map map);
	
	/**--根据字典名称查询--**/
	public Map getDictByName(String dictName);
}

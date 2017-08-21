package com.cy.core.news.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.news.entity.News;
import com.cy.core.news.entity.NewsType;

public interface MobNewsTypeMapper
{

	public List<NewsType> query(Map<String, Object> map);

	public List<NewsType> selectNewsType(Map<String, Object> map);

	public int count(Map<String, Object> map);

	public NewsType getById(String id);

	public void save(NewsType type);
	
	long getNewId();
	
	/**--新闻栏目删除,根据id--**/
	public void deleteById(List<Long> list);
	/**--新闻栏目删除,根据父id--**/
	public void deleteByPid(List<Long> list);

	public void update(NewsType type);

	void updateSubType( Map<String,Long> map );//lixun
	
	public void saveNews(News news);
	
	public void updateNews(News news);
	
	public void deleteNews(String typeId);
	
	NewsType getByName(String name);

	NewsType getByCode (String code);

	/**--获取栏目列表--**/
	List<NewsType> findNewsType( Map<String, Object> map );

	void setMobTypeList(Map<String, Object> map);

	void setMobTypePage(Map<String, Object> map);

}

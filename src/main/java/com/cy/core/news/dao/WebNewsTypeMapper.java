package com.cy.core.news.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.news.entity.News;
import com.cy.core.news.entity.NewsType;

public interface WebNewsTypeMapper
{

	public List<NewsType> query(Map<String, Object> map);

	public int count(Map<String, Object> map);

	public NewsType getById(String id);

	public void save(NewsType type);
	
	long getNewId();
	
	/**--新闻栏目删除,根据id--**/
	public void deleteById(List<Long> list);
	/**--新闻栏目删除,根据父id--**/
	public void deleteByPid(List<Long> list);

	public void update(NewsType type);
	
	public void saveNews(News news);
	
	public void updateNews(News news);
	
	public void deleteNews(String typeId);
	
	NewsType getByName(String name);
	
}

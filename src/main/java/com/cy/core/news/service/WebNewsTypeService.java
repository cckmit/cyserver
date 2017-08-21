package com.cy.core.news.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.news.entity.News;
import com.cy.core.news.entity.NewsType;

public interface WebNewsTypeService {
	List<NewsType> treeGird(Map<String, Object> map);
	
	DataGrid<NewsType> dataGrid(Map<String, Object> map);

	NewsType getById(String id);

	String save(NewsType type);

	long getNewId();
	
	void delete(List<Long> list, long parent_id);

	void update(NewsType type);
	
	/**
	 * 添加“单页面”栏目时添加对应新闻
	 */
	void saveNews(News news);
	
	/**
	 * 修改“单页面”栏目时修改对应新闻
	 */
	void updateNews(News news);
	
	/**
	 * 删除“单页面”栏目时删除对应新闻
	 */
	void deleteNews(String typeId);
	
	NewsType getByName(String name);
	
}

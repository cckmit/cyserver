package com.cy.core.news.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.news.entity.News;
import com.cy.core.news.entity.NewsType;

public interface MobNewsTypeService {
	
	public DataGrid<NewsType> dataGrid(Map<String, Object> map);

	public List<NewsType> mobNewsTypeTree(Map<String, Object> map);

	public NewsType getById(String id);

	public String save(NewsType type);

	long getNewId();
	
	public void delete(List<Long> list, long parent_id);

	public void update(NewsType type);
	
	/**
	 * 添加“单页面”栏目时添加对应新闻
	 */
	public void saveNews(News news);
	
	/**
	 * 修改“单页面”栏目时修改对应新闻
	 */
	public void updateNews(News news);
	
	/**
	 * 删除“单页面”栏目时删除对应新闻
	 */
	public void deleteNews(String typeId);
	
	/**
	 * 根据名称获取新闻栏目
	 * 
	 * @param name
	 * @return
	 */
	NewsType getByName(String name);
	
	public Message addTowebpageDB();


	/**
	 * 获取栏目列表
	 * @param message
	 * @param content
	 */
	public void getAllLinkOfCategorys(Message message, String content) ;

	/**
	 * 获取栏目列表新
	 */
	void findNewsType(Message message, String content);

	/**
	 * 頻道列表
	 * @param message
	 * @param content
     */
	void findNewsChannelList(Message message, String content);
	/**
	 *是否上导航
	 */
	void setMobTypeList(String ids, byte controlStr);

	/**
	 *是否上首页
	 */
	void setMobTypePage(String ids, byte controlStr);
}

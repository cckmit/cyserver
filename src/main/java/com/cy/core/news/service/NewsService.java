package com.cy.core.news.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.dict.entity.Dict;
import com.cy.core.news.entity.News;
import com.cy.core.news.entity.NewsCheck;
import com.cy.core.news.entity.NewsType;

public interface NewsService
{
	DataGrid<News> dataGrid(Map<String, Object> map);

	List<News> newsList(String[] ids);
	
	List<News> selectNews(Map<String, Object> map);

	List<News> selectNewsNew(Map<String, Object> map);

	News selectById(long activityId);

	void save(News news, List<String> cateList);

	/**--新闻添加，同时生成URL--**/
	void insertNewsAndsetUrl(News news,String url, List<String> cateList);

	void update(News news, List<String> lst );

	void delete(String ids);
	
	List<Dict> getAllCategorys(Map<String, Object> map);
	
	void setMobTypeList(String ids, String controlStr);
	void setWebTypeList(String ids, String controlStr);
	
	List<News> listMobTopNews(Map<String, Object> map);
	
	List<News> listMobNews(Map<String, Object> map);
	
	/**--查询新闻的数量--**/
	public int listMobNewsCount(Map<String, Object> map);
	
	
	public List<NewsType> selectTypeList(Map<String, Object> map);
	public List<NewsType> selectWebTypeList(Map<String, Object> map);
	List<NewsType> selectWechatTypeList(Map<String, Object> map);

	
	/**--查询2级栏目集合--**/
	public List<NewsType> selectLeveList(String id);
	
	/**--查询返回所有的新闻1级栏目,提供给外部web页面--**/
	public List<NewsType> selectMobileNewsType(long category);
	
	
	News selectWebNewFromWebType(NewsType newsType);
	public Message addTowebpageDB();

	void saveCheck(NewsCheck newsCheck);

	String selectNewTypeOpen();	//lixun 分会专栏是否开通

	/**--查询手机要展示的新闻栏目列表--**/
	List<NewsType> selectTypeListForMobile(Map<String, Object> map);

	/**--手机新闻栏目列表--**/
	void findNewsListForMobile(Message message, String content);

	/**--获取新闻详情--**/
	void findNewsForMobile(Message message, String content);

	/**--Web新闻栏目列表--**/
	void findNewsTypeListForWeb(Message message, String content);
	/**--Web新闻列表--**/
	void findNewsListForWeb(Message message, String content);
	/**--Web新闻详情--**/
	void findNewsForWeb(Message message, String content);

	boolean checkUrl(String url);
}


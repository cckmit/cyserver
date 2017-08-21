package com.cy.core.news.dao;

import java.util.List;
import java.util.Map;




import com.cy.core.dict.entity.Dict;
import com.cy.core.news.entity.News;
import com.cy.core.news.entity.NewsCheck;
import com.cy.core.news.entity.NewsType;

public interface NewsMapper
{
	long countNews(Map<String, Object> map);
	long countNewsNew(Map<String, Object> map);	//lixun 新的



	List<News> selectNews(Map<String, Object> map);
	List<News> selectNewsNew(Map<String, Object> map);	//lixun 新的

	News selectById(long activityId);
	News selectByIdNew(long activityId);	//lixun 新的

	void save(News news);

	void insertNewsTypes( Map<String,Object> map );	//lixun
	void deleteNewsTypes( long newsid ); //lixun
	
	Long insert(News news);

	void update(News news);

	void delete(List<Long> list);

	List<News> selectForMicro(Map<String, Object> map);

	List<News> selectByIds(List<Long> list);
	
	List<Dict> getAllCategorys(Map<String, Object> map);
	
	void setMobTypeList(Map<String, Object> map);
	void setWebTypeList(Map<String, Object> map);
	
	List<News> listMobTopNews(Map<String, Object> map);
	
	List<News> listMobNews(Map<String, Object> map);
	/**--查询新闻的数量--**/
	int listMobNewsCount(Map<String, Object> map);
	
	/**--查询新闻类别并分页--**/
	public List<NewsType> selectTypeList(Map<String, Object> map);
	public List<NewsType> selectWebTypeList(Map<String, Object> map);
	List<NewsType> selectWechatTypeList(Map<String, Object> map);
	
	News selectWebNewFromWebType(NewsType newsType);

	List<News> selectAllForWebApi(String approveDeptId);

	void saveCheck(NewsCheck newsCheck);

	void updateStatus(Map<String, Object> map);

	String selectNewTypeOpen();	//lixun 分会专栏是否开通

	public List<NewsType> queryNewsType(Map<String, Object> map) ;
	/**--为App提供新闻栏目列表--**/
	List<NewsType> selectTypeListforMobile(Map<String, Object> map);

	void deleteMiddle(List<Long> list);

	News findMobNews(String newsId);

	void updateClickRate(Map<String, Object> map);


	/**--网页新闻列表--**/
	List<News> listWebNews(Map<String, Object> map);

	/**--网页新闻数量-**/
	int listWebNewsCount(Map<String, Object> map);

	News findWebNews(Map<String, Object> map);

	List<News> selectNewsForNew(Map<String, Object> map);
}

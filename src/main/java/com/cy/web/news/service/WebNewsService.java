package com.cy.web.news.service;

import java.util.List;
import java.util.Map;


import com.cy.web.news.entity.WebNews;
import com.cy.web.news.entity.WebNewsType;

public interface WebNewsService {

	/**
	 * 获取校友会网站栏目
	 * 
	 * @param WebNewsType webNewsType
	 * @return List<WebNewsType>
	 * 
	 */
	public List<WebNewsType> getWebNewsTypeList(WebNewsType webNewsType);
	
	/**
	 * 获取全部的校友会分会
	 * 
	 * @param WebNewsType webNewsType
	 * @return List<WebNewsType>
	 * 
	 */
	public List<WebNewsType> getAlumniLocList(WebNewsType webNewsType);
	
	
	/**
	 * 获取新闻列表（带分页）
	 * 
	 * @param WebNews webNews
	 * @return Map<String, Object>
	 * 
	 */
	public Map<String, Object> getWebNewsListWithPaging(WebNews webNews);
	
	
	/**
	 * 获取新闻列表
	 * 
	 * @param WebNews webNews
	 * @return List<WebNews>
	 * 
	 */
	public List<WebNews> getWebNewsList(WebNews webNews);
	
	
	
	/**
	 * 获取新闻列表总数
	 * 
	 * @param WebNews webNews
	 * @return long
	 * 
	 */
	public long getWebNewsCount(WebNews webNews);
	
	
	/**
	 * 获取新闻内容
	 * 
	 * @param WebNews webNews
	 * @return WebNews
	 * 
	 */
	public WebNews getWebNews(WebNews webNews);
	
	
	/**
	 * 获取分页字符串
	 * 
	 * @param long totalRows, long pagination, String argument, String url
	 
	 * 
	 */
	public void bulidPagination(long totalRows, long pagination, String argument, String url);
	
	
	/**
	 * 通过新闻类别ID获得新闻类别
	 * 
	 * @param WebNewsType webNewsType
	 * @return WebNewsType
	 * 
	 */
	public WebNewsType getWebNewsTypeById(WebNewsType webNewsType);
	
	
	public long getCurrentRow();
	
	public long getIncremental();
	
	public String getPaging();
	
	/**
	 * 通过栏目类别ID获得所有子栏目
	 * 
	 * @param WebNewsType webNewsType
	 * @return List<WebNewsType>
	 * 
	 */
	public List<WebNewsType> getWebTypeByParentId(WebNewsType webNewsType);
	
	/**
	 * 通过栏目类别ID获得父栏目
	 * 
	 * @param WebNewsType webNewsType
	 * @return List<WebNewsType>
	 * 
	 */
	public List<WebNewsType> getWebParentsByTypeId(WebNewsType webNewsType); 
	
	
	/**
	 * 获得主页栏目
	 * @param WebNewsType webNewsType
	 * @return List<WebNewsType>
	 * 
	 */
	public List<WebNewsType> getMainWebNewsType(WebNewsType newsType);
	
	/**
	 * 获取名字为捐赠的栏目
	 * 
	 * @param name
	 * @return
	 */
	public List<WebNewsType> getByName(String name);
}

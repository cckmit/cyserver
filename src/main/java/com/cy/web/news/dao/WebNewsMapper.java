package com.cy.web.news.dao;

import java.util.List;


import com.cy.web.news.entity.WebNews;
import com.cy.web.news.entity.WebNewsType;

public interface WebNewsMapper {

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
	 * 通过新闻类别ID获得新闻类别
	 * 
	 * @param WebNewsType webNewsType
	 * @return WebNewsType
	 * 
	 */
	public WebNewsType getWebNewsTypeById(WebNewsType webNewsType);
	
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
	public List<WebNewsType> getMainWebNewsType(WebNewsType webNewsType);
	
	/**
	 * 获取名字为捐赠的栏目
	 * 
	 * @param name
	 * @return
	 */
	public List<WebNewsType> getByName(String name);
}

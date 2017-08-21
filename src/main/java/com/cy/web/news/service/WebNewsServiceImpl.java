package com.cy.web.news.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.util.WebUtil;
import com.cy.web.news.dao.WebNewsMapper;
import com.cy.web.news.entity.WebNews;
import com.cy.web.news.entity.WebNewsType;

@Service("webNewsService")
public class WebNewsServiceImpl implements WebNewsService{

	
	@Autowired
	private WebNewsMapper webNewsMapper;
	
	//private static int EACH_PAGE_NUMBER = 10;// 每页显示记录数
	
	private long currentRow;// 当前页
	private long incremental = 10;// 每页显示记录数
	
	private String paging;//分页字符串

	@Override
	public List<WebNewsType> getWebNewsTypeList(WebNewsType webNewsType) {
		// TODO Auto-generated method stub
		return webNewsMapper.getWebNewsTypeList(webNewsType);
	}

	@Override
	public List<WebNewsType> getAlumniLocList(WebNewsType webNewsType) {
		// TODO Auto-generated method stub
		return webNewsMapper.getAlumniLocList(webNewsType);
	}
	
	
	/**
	 * 获取新闻列表（带分页）
	 * 
	 * @param WebNews webNews
	 * @return Map<String, Object>
	 * 
	 */
	@Override
	public Map<String, Object> getWebNewsListWithPaging(WebNews webNews) {
		// TODO Auto-generated method stub
		
		String argument = "";
		
		int categoryWeb = webNews.getCategoryWeb();
		
		String topnews = webNews.getTopnews();
		
		int origin = webNews.getOrigin();
		
		if(categoryWeb != 0)
		{
			argument += "&categoryWeb=" + categoryWeb;
		}
		
		if(!WebUtil.isEmpty(topnews))
		{
			argument += "&topnews=" + topnews;
		}
		
		argument += "&origin=" + origin;
		
		if(!WebUtil.isEmpty(argument))
		{
			argument = argument.replaceFirst("&", "?");
		}
		
		
		
		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		//long totalRows, long pagination, String argument, String url
		long totalRows = getWebNewsCount(webNews);
		long pagination = webNews.getPagination();
		String url = webNews.getActionUrl();
		
		bulidPagination(totalRows, pagination, argument, url);
		
		webNews.setCurrentRow(getCurrentRow());
		webNews.setIncremental(getIncremental());
		
		List<WebNews> list = getWebNewsList(webNews);
		
		
		map.put("list", list);
		
		map.put("paging", getPaging());
		
		return map;
	}
	
	/**
	 * 获取新闻列表
	 * 
	 * @param WebNews webNews
	 * @return List<WebNews>
	 * 
	 */
	@Override
	public List<WebNews> getWebNewsList(WebNews webNews) {
		// TODO Auto-generated method stub
		return webNewsMapper.getWebNewsList(webNews);
	}

	/**
	 * 获取新闻列表总数
	 * 
	 * @param WebNews webNews
	 * @return long
	 * 
	 */
	@Override
	public long getWebNewsCount(WebNews webNews) {
		// TODO Auto-generated method stub
		return webNewsMapper.getWebNewsCount(webNews);
	}

	/**
	 * 获取新闻内容
	 * 
	 * @param WebNews webNews
	 * @return WebNews
	 * 
	 */
	@Override
	public WebNews getWebNews(WebNews webNews) {
		// TODO Auto-generated method stub
		return webNewsMapper.getWebNews(webNews);
	}

	
	/**
	 * 获取分页字符串
	 * 
	 * @param long totalRows, long pagination, String argument, String url
	 
	 * 
	 */
	@Override
	public void bulidPagination(long totalRows, long pagination, String argument, String url) {
		// TODO Auto-generated method stub
		
		String pageStr = "";
		
		if(pagination == 0)
		{
			pagination = 1;
			long eachPageNumber = incremental;
			long start = 0;
			long end = 1 * eachPageNumber;
			
			setCurrentRow(start);
			setIncremental(end);
			
			//userList = userMBean.list(start, end, parentid);
			
			long totalPage = ((totalRows <= eachPageNumber) ? 1 : (totalRows / eachPageNumber) + (totalRows % eachPageNumber > 0 ? 1:0));
			
			pageStr = "<a class='cpx12lan1' href='"+url+argument+"'>首页</a>&nbsp;&nbsp; " +
					  "<a class='cpx12lan1' href='"+url+argument+"'>上页</a>&nbsp;&nbsp;" +
					  "<a class='cpx12lan1' href='"+url+argument+"&pagination="+(totalRows <= eachPageNumber ? 1:2)+"'>下页</a>&nbsp;&nbsp;" +
					  "<a class='cpx12lan1' href='"+url+argument+"&pagination="+totalPage+"'>尾页</a> &nbsp;&nbsp;" +
					  "第 1 页，共 "+totalPage+" 页 "+totalRows+" 条 &nbsp;&nbsp;" +
					  "<input type='text' id='txtPageNum' value='"+pagination+"' size='3' maxLength='7' class='inputtype'>" +
					  "<a class='cpx12lan1' href='JavaScript:doGO("+totalPage+",\""+url+argument+"\")'>GO</a>";
		}
		else
		{
			long ipage = pagination;
			long eachPageNumber = incremental;
			long start = ipage * eachPageNumber - eachPageNumber;
			long end = ipage * eachPageNumber;
			
			setCurrentRow(start);
			setIncremental(end);
			
			//userList = userMBean.list(start, end, parentid);
			
			long totalPage = ((totalRows <= eachPageNumber) ? 1 : (totalRows / eachPageNumber) + (totalRows % eachPageNumber > 0 ? 1:0));
			
			pageStr = "<a class='cpx12lan1' href='"+url+argument+"'>首页</a>&nbsp;&nbsp; " +
					  "<a class='cpx12lan1' href='"+url+argument+"&pagination="+(totalRows <= eachPageNumber ? 1:(ipage - 1)<1?1:ipage - 1)+"'>上页</a>&nbsp;&nbsp;" +
					  "<a class='cpx12lan1' href='"+url+argument+"&pagination="+(totalRows <= eachPageNumber ? 1:(ipage + 1) > totalPage?totalPage:(ipage + 1))+"'>下页</a>&nbsp;&nbsp;" +
					  "<a class='cpx12lan1' href='"+url+argument+"&pagination="+totalPage+"'>尾页</a> &nbsp;&nbsp;" +
					  "第 "+ipage+" 页，共 "+totalPage+" 页 "+totalRows+" 条 &nbsp;&nbsp;" +
					  "<input type='text' id='txtPageNum' value='"+pagination+"' size='3' maxLength='7' class='inputtype'>" +
					  "<a class='cpx12lan1' href='JavaScript:doGO("+totalPage+",\""+url+argument+"\")'>GO</a>";
		
		}
		
		setPaging(pageStr);
		
		//return pageStr;
	}
	
	/**
	 * 通过新闻类别ID获得新闻类别
	 * 
	 * @param WebNewsType webNewsType
	 * @return WebNewsType
	 * 
	 */
	@Override
	public WebNewsType getWebNewsTypeById(WebNewsType webNewsType) {
		// TODO Auto-generated method stub
		return webNewsMapper.getWebNewsTypeById(webNewsType);
	}

	@Override
	public long getCurrentRow() {
		return currentRow;
	}

	@Override
	public long getIncremental() {
		return incremental;
	}

	@Override
	public String getPaging() {
		return paging;
	}

	public void setCurrentRow(long currentRow) {
		this.currentRow = currentRow;
	}

	public void setIncremental(long incremental) {
		this.incremental = incremental;
	}

	public void setPaging(String paging) {
		this.paging = paging;
	}

	@Override
	public List<WebNewsType> getWebTypeByParentId(WebNewsType webNewsType) {
		// TODO Auto-generated method stub
		return webNewsMapper.getWebTypeByParentId(webNewsType);
	}

	@Override
	public List<WebNewsType> getWebParentsByTypeId(WebNewsType webNewsType) {
		// TODO Auto-generated method stub
		return webNewsMapper.getWebParentsByTypeId(webNewsType);
	}

	/**
	 * 获得主页栏目
	 * @param WebNewsType webNewsType
	 * @return List<WebNewsType>
	 * 
	 */
	@Override
	public List<WebNewsType> getMainWebNewsType(WebNewsType webNewsType)
	{
		// TODO Auto-generated method stub
		return webNewsMapper.getMainWebNewsType(webNewsType);
	}

	@Override
	public List<WebNewsType> getByName(String name) {
		return webNewsMapper.getByName(name);
	}

	

	

	
	

	
	
	
	

}

package com.cy.core.news.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cy.base.entity.TreeString;
import com.cy.common.utils.EditorUtils;
import com.cy.common.utils.easyui.TreeStringUtil;
import com.cy.core.news.dao.MobNewsTypeMapper;
import com.cy.core.news.dao.NewsMapper;
import com.cy.core.news.entity.DataGridNews;
import com.cy.core.news.entity.NewsCheck;
import com.cy.core.user.entity.User;
import com.cy.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.dict.entity.Dict;
import com.cy.core.news.entity.News;
import com.cy.core.news.entity.NewsType;
import com.cy.system.Global;
import com.cy.util.HttpPost;
import com.cy.util.WebUtil;

@Service("newsService")
public class NewsServiceImpl implements NewsService
{
	private static final Logger logger = Logger.getLogger(HttpPost.class);
	
	@Autowired
	private NewsMapper newsMapper;

	@Autowired
	private MobNewsTypeMapper mobNewsTypeMapper;

	/**--后台管理新闻列表--**/
	public DataGrid<News> dataGrid(Map<String, Object> map)
	{
		DataGrid<News> dataGrid = new DataGrid<News>();
		long total = newsMapper.countNewsNew(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<News> list = newsMapper.selectNewsNew(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public List<News> newsList(String[] ids){
		List<Long> list = new ArrayList<>();
		if(ids !=null){
			for (String id :ids){
                list.add(Long.parseLong(id));
			}
		}
		List<News> newsList = newsMapper.selectByIds(list);
		return newsList;
	}

	public News selectById(long activityId)
	{
		News news = newsMapper.selectByIdNew(activityId);
		if(news != null && StringUtils.isNotBlank(news.getContent())) {
			news.setContent(EditorUtils.changeSrcFromRelativeToAbsolute(news.getContent()));
		}
		/*if(news !=null && StringUtils.isNotBlank(news.getPic()) ){
			news.setPic(Global.URL_DOMAIN + news.getPic());
		}*/

		if( news.getOpinions() != null ) {
			news.setOpinions(news.getOpinions().replaceAll("--=-,", "<br>"));    //lixun 替换
			news.setOpinions(news.getOpinions().replace("--=-", ""));    //lixun 替换
		}
		
		String separator = ",";
		
		if(news.getType() != null && news.getType().length() > 0)
		{
			if(news.getType().indexOf(separator) != -1)
			{
				news.setTypes(news.getType().split(separator));
			}
			else
			{
				String types[] = new String[1];
				types[0] = news.getType();
				news.setTypes(types);
			}
		}
		
		String types = JSON.toJSONString(news.getTypes());
		if(types != null && types.indexOf("\"") != -1)
		{
			types = types.replaceAll("\"", "'").replaceAll(" ", "");
		}
			
			
		news.setTypeStr(types);
		
		String alumniid = news.getAlumniid();
		if(StringUtils.isNotBlank(alumniid)){
			String[] alumniidArray=alumniid.split(separator);
			String alumniidStr = JSON.toJSONString(alumniidArray);
			if(alumniidStr != null && alumniidStr.indexOf("\"") != -1)
			{
				alumniidStr = alumniidStr.replaceAll("\"", "'").replaceAll(" ", "");
			}
			news.setAlumniidStr(alumniidStr);
		}
		
		
		String profession = news.getProfession();
		if(StringUtils.isNotBlank(profession)){
			String[] professionArray=profession.split(separator);
			String professionStr = JSON.toJSONString(professionArray);
			if(professionStr != null && professionStr.indexOf("\"") != -1)
			{
				professionStr = professionStr.replaceAll("\"", "'").replaceAll(" ", "");
			}
			news.setProfessionStr(professionStr);
		}
		
		
		return news;
	}

	public void save(News news, List<String> cateList)
	{
//		if(StringUtils.isNotBlank(news.getContent())) {
//			news.setContent(EditorUtils.edictorContent(news.getContent()));
//		}
		// 过滤富文本编辑器内容中的网络图片，下载至服务器，并将引用的地址变更为所在服务器上的地址-----开始
		if(StringUtils.isNotBlank(news.getContent())) {
			news.setContent(EditorUtils.filterContentForNetImg(news.getContent()));
		}
		// 过滤富文本编辑器内容中的网络图片，下载至服务器，并将引用的地址变更为所在服务器上的地址-----结束
		newsMapper.save(news);
		if( cateList != null ) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("newsid", "" + news.getNewsId());
			map.put("channels", cateList);
			newsMapper.insertNewsTypes( map );
		}
//		webapiForAdd(news);
	}
	
	/**--新闻添加，同时生成URL--**/
	public void insertNewsAndsetUrl(News news,String url, List<String> cateList){
		/*if(news.getPic() != null ){
			news.setPic(news.getPic().replace(Global.URL_DOMAIN, ""));
		}*/

		// 过滤富文本编辑器内容中的网络图片，下载至服务器，并将引用的地址变更为所在服务器上的地址-----开始
		if(StringUtils.isNotBlank(news.getContent())) {
			news.setContent(EditorUtils.filterContentForNetImg(news.getContent()));
		}
		// 过滤富文本编辑器内容中的网络图片，下载至服务器，并将引用的地址变更为所在服务器上的地址-----结束

		newsMapper.insert(news);

		String newsId = news.getNewsId()+"";
		news.setNewsUrl(url+newsId);
		if( cateList != null ) {
			Map<String, Object> map = new HashMap<>();
			map.put("newsid", "" + newsId);
			map.put("channels", cateList);
			newsMapper.insertNewsTypes(map);
		}
		newsMapper.update(news);
		
//		webapiForAdd(news);
	}
	
	private void webapiForAdd(News news) {
		String[][] header = new String[][] { { "taskOId", "article_add" },
				{ "timestamp", new Timestamp(new Date().getTime()).toString() } };
				
		Date createTime = news.getCreateTime();
		String createTimeStr = "";
		if (createTime != null) {
			try {
				createTimeStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(createTime);
			} catch (Exception e) {
				createTimeStr = "";
			}
		}
		String json = "{\"taskOId\":\"article_add\",\"content\":{\"id\":\"" + news.getNewsId() + "\",\"category_id\":\""
				+ news.getCategory() + "\",\"title\":\"" + news.getTitle() + "\",\"photo_url\": \"" + news.getPic()
				+ "\",\"content\": \"" + news.getContent() + "\",\"add_time\": \"" + createTimeStr + "\"}}";

		try {
			HttpPost.sendHttpJsonPost(Global.web_homepage_api_url, header, json);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error("web api error!", e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("web api error!", e);
		}
	}
	

	public void update(News news, List<String> lst )
	{
		/*if(news.getPic() != null ){
			news.setPic(news.getPic().replace(Global.URL_DOMAIN, ""));
		}*/
		/*if(StringUtils.isNotBlank(news.getContent())) {
			news.setContent(EditorUtils.edictorContent(news.getContent()));
		}*/
		if(WebUtil.isEmpty(news.getNewsUrl())){
			news.setNewsUrl(Global.cy_server_url+"mobile/news/newsAction!doNotNeedSessionAndSecurity_getMobNew.action?id="+news.getNewsId());
		}
		// 过滤富文本编辑器内容中的网络图片，下载至服务器，并将引用的地址变更为所在服务器上的地址-----开始
		if(StringUtils.isNotBlank(news.getContent())) {
			news.setContent(EditorUtils.filterContentForNetImg(news.getContent()));
		}
		// 过滤富文本编辑器内容中的网络图片，下载至服务器，并将引用的地址变更为所在服务器上的地址-----结束
		newsMapper.update(news);

		if( lst != null )
		{
			newsMapper.deleteNewsTypes( news.getNewsId() );
			Map<String,Object> map = new HashMap<String,Object>();
			map.put( "newsid", String.valueOf( news.getNewsId() ) );
			map.put( "channels", lst );
			newsMapper.insertNewsTypes( map );
		}
//		webapiForUpd(news);
	}
	
	private void webapiForUpd(News news) {
		String[][] header = new String[][] { { "taskOId", "article_update" },
				{ "timestamp", new Timestamp(new Date().getTime()).toString() } };

		Date createTime = news.getCreateTime();
		String createTimeStr = "";
		if (createTime != null) {
			try {
				createTimeStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(createTime);
			} catch (Exception e) {
				createTimeStr = "";
			}
		}
		String json = "{\"taskOId\":\"article_update\",\"content\":{\"id\":\"" + news.getNewsId()
				+ "\",\"category_id\":\"" + news.getCategory() + "\",\"title\":\"" + news.getTitle()
				+ "\",\"photo_url\": \"" + news.getPic() + "\",\"content\": \"" + news.getContent() + "\",\"add_time\": \"" + createTimeStr + "\"}}";

		try {
			HttpPost.sendHttpJsonPost(Global.web_homepage_api_url, header, json);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.error("web api error!", e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("web api error!", e);
		}
	}

	public void delete(String ids)
	{
		String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
		newsMapper.delete(list);
		newsMapper.deleteMiddle(list);
		
		webapiForDel(list);
	}
	
	private void webapiForDel(List<Long> list) {
		String[][] header = new String[][] { { "taskOId", "article_delete" },
				{ "timestamp", new Timestamp(new Date().getTime()).toString() } };

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String json = "{\"taskOId\":\"article_delete\",\"content\":{\"id\":\"" + String.valueOf(list.get(i))
						+ "\"}}";
				try {
					HttpPost.sendHttpJsonPost(Global.web_homepage_api_url, header, json);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					logger.error("web api error!", e);
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("web api error!", e);
				}
			}
		}
	}

	public List<News> selectNews(Map<String, Object> map) {
		List<News> list = newsMapper.selectNews(map);
		return this.selectNewsImgeURL(list);
		//return newsMapper.selectNews(map);
	}

	public List<News> selectNewsNew(Map<String, Object> map) {
		List<News> list = newsMapper.selectNewsNew(map);
		return this.selectNewsImgeURL(list);
		//return newsMapper.selectNews(map);
	}
	
	public List<Dict> getAllCategorys(Map<String, Object> map) {
		
		return newsMapper.getAllCategorys(map);
	}

	
	public void setMobTypeList(String ids, String controlStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
		
		map.put("list", list);
		map.put("controlStr", controlStr);
		
		newsMapper.setMobTypeList(map);
		
		// web api
		String taskOId = "";
		if ("100".equals(controlStr)) {
			taskOId = "slide_add";
		} else if (controlStr == null) {
			taskOId = "slide_delete";

		}else{
			return ;
		}
		
		String[][] header = new String[][] { { "taskOId", taskOId },
				{ "timestamp", new Timestamp(new Date().getTime()).toString() } };

		String json = "{\"taskOId\":\"" + taskOId + "\",\"content\":{\"ids\":\"" + ids + "\"}}";
		try {
			HttpPost.sendHttpJsonPost(Global.web_homepage_api_url, header, json);
		} catch (Exception e) {
			logger.error("(setMobTypeList) web api ERROR!", e);
		}
		
	}
	
	public void setWebTypeList(String ids, String controlStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array)
		{
			list.add(Long.parseLong(id));
		}
		
		map.put("list", list);
		map.put("controlStr", controlStr);
		
		newsMapper.setWebTypeList(map);
	}
	
	public List<News> listMobTopNews(Map<String, Object> map) {
		List<News> list = newsMapper.listMobTopNews(map);
		return this.selectNewsImgeURL(list);
		//return newsMapper.listMobTopNews(map);
	}

	
	public List<News> listMobNews(Map<String, Object> map) {
		List<News> list = newsMapper.listMobNews(map);
		return this.selectNewsImgeURL(list);
	}
	
	/**--查询新闻的数量--**/
	public int listMobNewsCount(Map<String, Object> map){
		return this.newsMapper.listMobNewsCount(map);
	}
	
	
	/**--整理新闻图片的链接--**/
	private List<News> selectNewsImgeURL(List<News> list){
		for(int i=0;i<list.size();i++){
			News news = list.get(i);
			//图片URL
			String pic = news.getPic();
			if(!WebUtil.isEmpty(pic)){
				//图片后缀
				String fileExt =  pic.substring(pic.lastIndexOf(".")) ;
				String temp = pic.substring(0, pic.lastIndexOf("."));
				if("100".equals(news.getTopnews()) ){
					//该新闻为滚动新闻，需要调用大图
					news.setPic(temp+"_MAX"+fileExt);
				}else if(temp.indexOf("_MIN")== -1){
					news.setPic(temp+"_MIN"+fileExt);
				}
			}	
		}
		return list;
	}

	
	/**--查询手机要展示的新闻栏目列表--**/
	public List<NewsType> selectTypeListForMobile(Map<String, Object> map){
		return newsMapper.selectTypeListforMobile(map);
	}

	/**--查询列表--**/
	public List<NewsType> selectTypeList(Map<String, Object> map){
		return newsMapper.selectTypeList(map);
	}

	public List<NewsType> selectWebTypeList(Map<String, Object> map) {
		return newsMapper.selectWebTypeList(map);
	}
	@Override	//lixun
	public List<NewsType> selectWechatTypeList(Map<String, Object> map)
	{
		return newsMapper.selectWechatTypeList(map);
	}
	
	/**--查询返回所有的新闻1级栏目,提供给外部web页面--**/
	public List<NewsType> selectMobileNewsType(long category){
		//查询所有
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channel","10") ;
		List<NewsType> list = newsMapper.selectTypeList(map);
		List<NewsType> list1 = new ArrayList<NewsType>();			//1级栏目
		List<NewsType> list2 = new ArrayList<NewsType>();			//2级栏目
		List<NewsType> returnList = new ArrayList<NewsType>();		//返回的数据
		if(list!=null&&list.size()>0){
			for(NewsType type:list){
				if(type.getParent_id()==0){
					list1.add(type);
				}else if(type.getParent_id()>0){
					list2.add(type);
				}
			}
			//便利1级栏目
			for(NewsType type1:list1){
				//便利2级栏目
				List<NewsType> leveList = new ArrayList<NewsType>();	//1级栏目的子栏目
				for(NewsType type2:list2){
					if(type2.getParent_id()==type1.getId()){
						leveList.add(type2);
					}
				}
				type1.setLeveList(leveList);
			}
		}
		
		if(category==0){
			returnList = list1;
		}else{
			List<NewsType> datalist = new ArrayList<NewsType>();
			for(NewsType type:list1){
				if(type.getId()==category){
					datalist.add(type);
					returnList = datalist;
					break;
				}
			}
		}
		return returnList;
	}
	
	
	
	/**--查询2级栏目集合--**/
	public List<NewsType> selectLeveList(String id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parent_id", id);
		return this.newsMapper.selectTypeList(map);
	}
	
	
	
	public static void main(String[] args) {
//		String s = "http://122.205.9.115:8088/image/20150417/20150417131824_639.jpg";
//		String fileExt =  s.substring(s.lastIndexOf(".")) ;
//		System.out.println(fileExt);
		String str="<br/><text>'name'/r/n</text>";
		System.out.println(JSON.toJSONString(str));
	}

	@Override
	public News selectWebNewFromWebType(NewsType newsType) {
		
		return this.newsMapper.selectWebNewFromWebType(newsType);
	}
	
	@Override
	public Message addTowebpageDB() {
		logger.debug("addTowebpageDB for News: start......");
		Message message = new Message();
		int cgjs = 0;
		int sbjs = 0;
		User user = UserUtils.getUser() ;

		List<News> newsList = this.newsMapper.selectAllForWebApi(String.valueOf(user.getDeptId()));
		if (newsList == null || newsList.size() <= 0) {
			message.setSuccess(true);
			message.setMsg("没有新闻数据！");
			logger.debug("addTowebpageDB for News: end(no data!)");
			return message;
		}

		String[][] header = new String[][] { { "taskOId", "article_add" },
				{ "timestamp", new Timestamp(new Date().getTime()).toString() } };

		for (int i = 0; i < newsList.size(); i++) {
			News news = newsList.get(i);
			String alumniid = news.getAlumniid();
			String profession = news.getProfession();
			String category = news.getChannelId();
//			long category = news.getCategory();
			String topnews = news.getTopnews();

			if (StringUtils.isBlank(category)) {
				continue;
			}

			long id = news.getNewsId();
			String title = news.getTitle();
			if (title != null && title.trim().length() > 0)
				title = string2Json(title);
			String content = news.getContent();
			if (content != null && content.trim().length() > 0)
				content = string2Json(content);
			String pic = news.getPic();
			//if (pic != null && pic.trim().length() > 0)
				//pic = JSON.toJSONString(pic);
			// String url = news.getNewsUrl();
			
			Date createTime = news.getCreateTime();
			String createTimeStr = "";
			if (createTime != null) {
				try {
					createTimeStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(createTime);
				} catch (Exception e) {
					createTimeStr = "";
				}
			}

			String json = "{\"taskOId\":\"article_add\",\"content\":{\"id\":\"" + id + "\",\"category_id\":\""
					+ category + "\",\"title\":\"" + title + "\",\"photo_url\": \"" + pic + "\",\"content\": \""
					+ content + "\",\"add_time\": \"" + createTimeStr + "\"}}";

			try {
				boolean rs = HttpPost.sendHttpJsonPost(Global.web_homepage_api_url, header, json);
				if (rs) {
					cgjs++;
				} else {
					sbjs++;
				}
				
				// --
				if ("100".equals(topnews)) {
					header = new String[][] { { "taskOId", "slide_add" },
							{ "timestamp", new Timestamp(new Date().getTime()).toString() } };

					json = "{\"taskOId\":\"slide_add\",\"content\":{\"ids\":\"" + id + "\"}}";

					HttpPost.sendHttpJsonPost(Global.web_homepage_api_url, header, json);
				}
			
			} catch (ClientProtocolException e) {
				logger.error("addTowebpageDB for News ERROR!", e);
				sbjs++;
			} catch (IOException e) {
				logger.error("addTowebpageDB for News ERROR!", e);
				sbjs++;
			}catch (Exception e) {
				logger.error("addTowebpageDB for News ERROR", e);
			}

		}

		message.setSuccess(true);
		message.setMsg("成功件数：" + cgjs + "; 失败件数：" + sbjs);
		logger.debug("addTowebpageDB for News:end(OK:"+cgjs+";NG:"+sbjs+")");
		return message;
	}
	
	public String string2Json(String s) {        
        StringBuffer sb = new StringBuffer();        
        for (int i=0; i<s.length(); i++) {  
            char c = s.charAt(i);    
             switch (c){  
             case '\"':        
                 sb.append("\\\"");        
                 break;        
             case '\\':        
                 sb.append("\\\\");        
                 break;        
             case '/':        
                 sb.append("\\/");        
                 break;        
             case '\b':        
                 sb.append("\\b");        
                 break;        
             case '\f':        
                 sb.append("\\f");        
                 break;        
             case '\n':        
                 sb.append("\\n");        
                 break;        
             case '\r':        
                 sb.append("\\r");        
                 break;        
             case '\t':        
                 sb.append("\\t");        
                 break;        
             default:        
                 sb.append(c);     
             }  
         }      
        return sb.toString();     
    }

	public void saveCheck(NewsCheck newsCheck){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", newsCheck.getBussId());
		map.put("status", newsCheck.getStatus());
		newsMapper.updateStatus(map);
		newsMapper.saveCheck(newsCheck);
	}

	@Override
	public String selectNewTypeOpen()	//lixun 分会专栏是否开通
	{
		return newsMapper.selectNewTypeOpen();
	}


	/**
	 * 查询手机新闻列表接口
	 * @param message
	 * @param content
     */
	public void findNewsListForMobile(Message message, String content){
		if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}

		Map<String,Object> map = JSON.parseObject(content, Map.class);
		String page = (String) map.get("page");
		String rows = (String) map.get("rows");
		String category = (String) map.get("category");
		String topnews = (String) map.get("topnews");

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("category", category);
		map2.put("topnews", topnews);

		if (org.apache.commons.lang3.StringUtils.isNotBlank(page) && org.apache.commons.lang3.StringUtils.isNotBlank(rows)) {
			int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
			map2.put("start", start);
			map2.put("rows", Integer.valueOf(rows));
		}

		List<News> list = listMobNews(map2);
		DataGrid<News> dataGrid = new DataGrid<News>();
		long total = listMobNewsCount(map2);
		dataGrid.setTotal(total);
		dataGrid.setRows(list);

		message.init(true ,"查询成功",dataGrid,null);
	}

	public void findNewsForMobile(Message message, String content) {
		if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String,Object> map = JSON.parseObject(content, Map.class);

		String id = (String)map.get("id");

		News news = newsMapper.findMobNews(id);
		if(news != null) {
			String clickRate = news.getClickRate();
			if (StringUtils.isBlank(news.getClickRate())) {
				clickRate = "0";
			}
			map.put("clickRate", Long.parseLong(clickRate) + 1);
			newsMapper.updateClickRate(map);
			message.init(true ,"查询成功",news);
		} else {
			message.init(false ,"新闻不存在" ,null);
		}

	}

	/**
	 * WEB新闻栏目接口
	 * 16-09-13
	 */
	public void findNewsTypeListForWeb(Message message, String content){

		Map<String, Object> map = new HashMap<>();
		map.put( "channel", "20" );
		map.put("deptId", "1");
		List<NewsType> list = selectTypeList(map);
		List<Map<String, Object>> result = new ArrayList<>();
		List<TreeString> treeList = new ArrayList<TreeString>();
		List<TreeString> rootTrees = new ArrayList<TreeString>();
		for( NewsType alu : list ) {
			TreeString node = new TreeString();
			long pid = alu.getParent_id();
			node.setIconCls(alu.getTypePic());
			node.setId( String.valueOf( alu.getId() ) );
			node.setPid( String.valueOf( pid ) );
			node.setText( alu.getName() );
			if( pid == 0 ) {
				if (alu.getIsNavigation() == 1) {
					node.setChecked(true);
				}
				rootTrees.add(node);
			}
			else
				treeList.add( node );
		}
		TreeStringUtil.parseTreeString( rootTrees, treeList );
		message.setMsg("成功获取栏目列表");
		message.setObj(rootTrees);
		message.setSuccess(true);
	}

	/**
	 * WEB新闻列表接口
	 */
	public void findNewsListForWeb(Message message, String content){
		if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}

		Map<String,Object> map = JSON.parseObject(content, Map.class);
		String page = (String) map.get("page");
		String rows = (String) map.get("rows");
		String category = (String) map.get("category");
		String topnews = (String) map.get("topnews");
		String channel = (String) map.get("channel");
		String channelId = (String) map.get("channelId");
		String code = (String) map.get("code");

		List<NewsType>  leveList = selectLeveList(category + "");
		Map<String, Object> map2 = new HashMap<String, Object>();

		if(StringUtils.isNotBlank(topnews) && ("100".equals(topnews) || "0".equals(topnews))){
			map2.put("topnews", topnews);
		}

		if(StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)){
			int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
			map2.put("start", start);
			map2.put("rows", Integer.valueOf(rows));
		}
		if(StringUtils.isNotBlank(category)){
			map2.put("category", category);
		}
		if(StringUtils.isNotBlank(channel)){
			map2.put("channel", channel);
		}
		if(StringUtils.isBlank(channelId)){
			map2.put("channelId", "20");
		}else{
			map2.put("channelId", channelId);
		}

		if(StringUtils.isNotBlank(code)){
			map2.put("code", code);
		}
		List<News> list = newsMapper.listWebNews(map2);
		DataGridNews<News> dataGrid = new DataGridNews<>();
		long total = newsMapper.listWebNewsCount(map2);
		dataGrid.setTotal(total);
		dataGrid.setRows(list);
		NewsType newsType = null;
		if(StringUtils.isNotBlank(category)){
			newsType = mobNewsTypeMapper.getById(category);
		}else if(StringUtils.isNotBlank(code)){
			newsType = mobNewsTypeMapper.getByCode(code);
		}
		if(newsType != null){
			dataGrid.setCategoryCode(newsType.getCode());
			dataGrid.setCategoryId(String.valueOf(newsType.getId()));
			dataGrid.setCategoryName(newsType.getName());
		}


		message.init(true ,"查询成功",dataGrid,null);
	}

	public void findNewsForWeb(Message message, String content) {
		if (org.apache.commons.lang3.StringUtils.isBlank(content)) {
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String,Object> map = JSON.parseObject(content, Map.class);


		if( map.get("id") == null || ((String) map.get("id")).length() == 0 ){
			message.init(false ,"请传入新闻Id" ,null);
			return;
		}

		if(map.get("channel") == null || StringUtils.isBlank(String.valueOf(map.get("channel"))) || !( "10".equals(map.get("channel")) || "20".equals(map.get("channel")) || "30".equals(map.get("channel")) ))
			map.put("channel", "20");

		News news = newsMapper.findWebNews(map);
		if(news == null){
			message.init(false ,"新闻不存在" ,null);
			return;
		}
		String clickRate = news.getClickRate();
		if (StringUtils.isBlank(news.getClickRate())) {
			clickRate = "0";
		}
		map.put("clickRate", Long.parseLong(clickRate) + 1);
		newsMapper.updateClickRate(map);
		if(StringUtils.isNotBlank(news.getPic())){
			int length = news.getPic().length();
			int tmp = news.getPic().lastIndexOf(".");
			news.setPic(news.getPic().substring(0, tmp) + "_MIN"+ news.getPic().substring(tmp, length));
		}
		//查詢相關新聞
		Map<String, Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("category", news.getCategory());
		mapTmp.put("start", 0);
		mapTmp.put("rows", 3);
		mapTmp.put("butNew", news.getNewsId());
		List<News> newslist = listMobNews(mapTmp);
		if(newslist != null && newslist.size() > 0)
			news.setNewsList(newslist);

		message.init(true ,"查询成功",news);

	}

	/**
	 * 检查文件路径
	 * @param url 文件相对路径
	 * @return
	 */
	public boolean checkUrl(String url){
		boolean isExists = false ;
		String fileUrl = Global.DISK_PATH + url ;
		System.out.println("-----------> 文件存放路径："+fileUrl);
		File file = new File(fileUrl) ;
		if(file.exists()) {
			isExists = true ;
		}
		return isExists;
	}

}

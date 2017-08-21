package com.cy.core.news.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.TreeString;
import com.cy.common.utils.easyui.TreeStringUtil;
import com.cy.core.channel.dao.NewsChannelMapper;
import com.cy.core.channel.entity.NewsChannel;
import com.cy.core.news.dao.NewsMapper;
import com.cy.core.news.entity.TreeForNewsType;
import com.cy.core.user.entity.User;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.mobileInterface.qrCode.entity.QrCode;
import com.cy.util.UserUtils;
import com.cy.util.WebUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.news.dao.MobNewsTypeMapper;
import com.cy.core.news.entity.News;
import com.cy.core.news.entity.NewsType;
import com.cy.system.Global;
import com.cy.util.HttpPost;

@Service("mobNewsTypeService")
public class MobNewsTypeServiceImpl implements MobNewsTypeService
{
	private static final Logger logger = Logger.getLogger(HttpPost.class);

	@Autowired
	private MobNewsTypeMapper mobNewsTypeMapper;

	@Autowired
	private UserProfileMapper userProfileMapper ;

	@Autowired
	private NewsChannelMapper newsChannelMapper;

	@Autowired
	private NewsMapper newsMapper ;

	private Object rows;

	private NewsType type;


	public DataGrid<NewsType> dataGrid(Map<String, Object> map){
		DataGrid<NewsType> dataGrid = new DataGrid<NewsType>();

		List<NewsType> list = mobNewsTypeMapper.query(map);
		long parent_id = (Long) map.get("parent_id");
		if(parent_id>0){
			NewsType webNewsType = this.getById(parent_id+"");
			for(NewsType type:list){
				type.setParent_name(webNewsType.getName());
			}
		}
		List<NewsType> list2 = new ArrayList<NewsType>();

		for(NewsType type:list)
		{
			Map<String, Object> tmap = new HashMap<String, Object>();
			tmap.put("parent_id", type.getId());
			List<NewsType> list3 = mobNewsTypeMapper.query(tmap);
			list2.add(type);
			list2.addAll(list3);
		}

		dataGrid.setRows(list2);
		return dataGrid;
	}

	public List<NewsType> mobNewsTypeTree(Map<String, Object> map){
		return mobNewsTypeMapper.selectNewsType(map);
	}

	public NewsType getById(String id){

		NewsType type =  this.mobNewsTypeMapper.getById(id);
		if(type!=null && StringUtils.isNotBlank(type.getTypePic()))
			type.setTypePic(Global.URL_DOMAIN + type.getTypePic());
		return type;
	}


	public String save(NewsType type){
		long parent_id = type.getParent_id();
		if(parent_id>0 ){
			//添加子栏目
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parent_id", parent_id);
			/*if("30".equals(type.getChannel())){
				map.put("channel", "30");
				int count  = mobNewsTypeMapper.count(map);
				*//*if(count > 4){
					return "4";
				}*//*
			}else {*/
				int count  = mobNewsTypeMapper.count(map);
				if(count > 5){
					return "2";
				}
			}
		/*else if("30".equals(type.getChannel())) {
			// 查询一级栏目个数，最多3个
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parent_id", '0');
			map.put("channel", "30");
			int count  = mobNewsTypeMapper.count(map);
			if(count > 2){
				return "3";
			}
		}*/

		Map<String, Object> mapTmp = new HashMap<String, Object>();
		mapTmp.put("code", type.getCode());
		int count  = mobNewsTypeMapper.count(mapTmp);
		if(count > 0){
			return "5";
		}

		if(type.getTypePic() != null){
			type.setTypePic(type.getTypePic().replace(Global.URL_DOMAIN,""));
		}
		this.mobNewsTypeMapper.save(type);
		
		// web API
//		if (type.getType() == 1) {
//
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("parent_id", type.getParent_id());
//			map.put("name", type.getName());
//			map.put("type", type.getType());
//
//			List<NewsType> list = mobNewsTypeMapper.query(map);
//			if (list != null && list.size() > 0) {
//				NewsType newsType = list.get(0);
//				String[][] header = new String[][] { { "taskOId", "category_add" },
//						{ "timestamp", new Timestamp(new Date().getTime()).toString() } };
//				String json = "{\"taskOId\":\"category_add\",\"content\":{\"id\":\"" + newsType.getId()
//						+ "\",\"title\":\"" + newsType.getName() + "\",\"parent_id\":\"" + newsType.getParent_id()
//						+ "\"}}";
//				try {
//					HttpPost.sendHttpJsonPost(Global.web_homepage_api_url, header, json);
//				} catch (ClientProtocolException e) {
//					e.printStackTrace();
//					logger.error("web api error!", e);
//				} catch (IOException e) {
//					e.printStackTrace();
//					logger.error("web api error!", e);
//				}
//			}
//		}
		//--
		
		return "1";
	}

	public void delete(List<Long> list,long parent_id){
		if(parent_id==0){								//该栏目为父栏目
			//父栏目删除
			this.mobNewsTypeMapper.deleteById(list);
			//对应子栏目删除
			this.mobNewsTypeMapper.deleteByPid(list);
		}else if(parent_id > 0){
			//子栏目删除									//该栏目为子栏目
			this.mobNewsTypeMapper.deleteById(list);
		}
	}
	

	public void update(NewsType type){
		NewsType nt =  mobNewsTypeMapper.getById( String.valueOf( type.getId() ) );	//lixun 搜更新之前的

		if(type.getTypePic() != null){
			type.setTypePic(type.getTypePic().replace(Global.URL_DOMAIN,""));
		}
		this.mobNewsTypeMapper.update(type);
		if( !nt.getChannel().equals(type.getChannel()) )
		{
			//改变了channel,将子栏目全部改变
			Map<String,Long> map = new HashMap<String,Long>();
			map.put( "id", type.getId() );
			map.put( "channel", Long.valueOf( type.getChannel() ) );
			mobNewsTypeMapper.updateSubType( map );
		}
		
		// web API
//		if (type.getType() == 1) {
//
//			String[][] header = new String[][] { { "taskOId", "category_update" },
//					{ "timestamp", new Timestamp(new Date().getTime()).toString() } };
//
//			String json = "{\"taskOId\":\"category_update\",\"content\":{\"id\":\"" + type.getId()
//					+ "\",\"parent_id\":\"" + type.getParent_id() + "\",\"title\":\"" + type.getName() + "\"}}";
//			try {
//				HttpPost.sendHttpJsonPost(Global.web_homepage_api_url, header, json);
//			} catch (ClientProtocolException e) {
//				e.printStackTrace();
//				logger.error("web api error!", e);
//			} catch (IOException e) {
//				e.printStackTrace();
//				logger.error("web api error!", e);
//			}
//		}
		// --
	}


	public void saveNews(News news) {
		this.mobNewsTypeMapper.saveNews(news);
	}

	public long getNewId() {
		return this.mobNewsTypeMapper.getNewId();
	}

	public void updateNews(News news) {
		this.mobNewsTypeMapper.updateNews(news);
	}
	
	public void deleteNews(String typeId) {
		this.mobNewsTypeMapper.deleteNews(typeId);
	}


	@Override
	public NewsType getByName(String name) {
		return mobNewsTypeMapper.getByName(name);
	}
	
	@Override
	public Message addTowebpageDB() {
		Message message = new Message();

		int cgjs = 0;
		int sbjs = 0;
		Map<String,Object> map = Maps.newHashMap() ;
		map.put("channel","20") ;
		User user = UserUtils.getUser() ;
		map.put("alumniId",user.getDeptId()) ;
		List<NewsType> list = mobNewsTypeMapper.query(map);
		List<NewsType> subNewsType = new ArrayList<NewsType>();
		if (list != null && list.size() > 0) {

			for (int i = 0; i < list.size(); i++) {
				NewsType newsi = list.get(i);
				int type = newsi.getType();
				int isNavigation = newsi.getIsNavigation();

				if (type != 1 || isNavigation != 1) {
					continue;
				}

				long id = newsi.getId();
				long parent_id = newsi.getParent_id();
				String name = newsi.getName();

				if (parent_id == 0) {
					String[][] header = new String[][] { { "taskOId", "category_add" },
							{ "timestamp", new Timestamp(new Date().getTime()).toString() } };
					String json = "{\"taskOId\":\"category_add\",\"content\":{\"id\":\"" + id + "\",\"title\":\""
							+ name + "\",\"parent_id\":\"0\"}}";
					try {
						boolean rs = HttpPost.sendHttpJsonPost(Global.web_homepage_api_url, header, json);
						if (rs) {
							cgjs++;
						} else {
							sbjs++;
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
						sbjs++;
					} catch (IOException e) {
						e.printStackTrace();
						sbjs++;
					}
				} else if (parent_id > 0) {
					subNewsType.add(newsi);
				}
			}
		}

		if (subNewsType.size() > 0) {
			for (int i = 0; i < subNewsType.size(); i++) {
				NewsType newsTypei = subNewsType.get(i);

				long id = newsTypei.getId();
				long parentid = newsTypei.getParent_id();
				String name = newsTypei.getName();

				String[][] header = new String[][] { { "taskOId", "category_add" },
						{ "timestamp", new Timestamp(new Date().getTime()).toString() } };
				String json = "{\"taskOId\":\"category_add\",\"content\":{\"id\":\"" + id + "\",\"title\":\"" + name
						+ "\",\"parent_id\":\"" + parentid + "\"}}";

				try {
					boolean rs = HttpPost.sendHttpJsonPost(Global.web_homepage_api_url, header, json);
					if (rs) {
						cgjs++;
					} else {
						sbjs++;
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					sbjs++;
				} catch (IOException e) {
					e.printStackTrace();
					sbjs++;
				}
			}
		}
		message.setSuccess(true);
		message.setMsg("成功件数：" + cgjs + "; 失败件数：" + sbjs);

		return message;
	}

	/**
	 * 获取栏目列表
	 * @param message
	 * @param content
     */
	public void getAllLinkOfCategorys(Message message, String content) {
		QrCode qrCode = JSON.parseObject(content, QrCode.class) ;
//		if (WebUtil.isEmpty(qrCode.getAccountNum())) {// 检测用户
//			message.setMsg("数据格式错误!");
//			message.setSuccess(false);
//			return;
//		}
//		UserProfile userProfile = userProfileMapper.selectByAccountNum(qrCode.getAccountNum());

		String basePath = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath();
		String URL = basePath + "/mobile/news/newsList.jsp?category=";
		String url = "/mobile/news/newsList.jsp?category=";
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parent_id", 0);
		//是否上导航（0：不上导航， 1：上导航）
		queryMap.put("isNavigation", 1);
		queryMap.put("deptId","1") ;
		queryMap.put("channel","10") ;

		List<NewsType> list = newsMapper.queryNewsType(queryMap);
		List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
		if(StringUtils.isNotBlank(Global.is_new_type_open) && !"0".equals(Global.is_new_type_open)){
			queryMap.put("accountNum",qrCode.getAccountNum()) ;
			queryMap.put("deptId",null) ;
			List<NewsType> alumniTypelist = newsMapper.queryNewsType(queryMap);
			if(alumniTypelist != null && !alumniTypelist.isEmpty()) {

				if("1".equals(Global.is_new_type_open)) {
					list.addAll(alumniTypelist);
				} else if("2".equals(Global.is_new_type_open)) {
					NewsType newsType = new NewsType() ;
					newsType.setId(0);
					newsType.setName("总会");
					newsType.setType(1);
				}
			}
		}
		//组织数据
		if(!WebUtil.isEmpty(list)){
			for(NewsType type:list){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("dictId", type.getId());
				map.put("dictTypeId", "0");
				map.put("dictName", type.getName());
				if(type.getType()==1){
					map.put("dictUrl", URL+type.getId());
					map.put("dictUrl_xd", url+type.getId());
				}else if(type.getType()==2){
					map.put("dictUrl", basePath+type.getUrl());
					map.put("dictUrl_xd",type.getUrl_xd());
				}else if(type.getType()==3){
					map.put("dictUrl", basePath + "/mobile/news/newsShow.jsp?category="+type.getId());
					map.put("dictUrl_xd", "/mobile/news/newsShow.jsp?category="+type.getId());
				}else{
					map.put("dictUrl", "#");
				}
				if(type.getId() == 0) {
					map.put("dictUrl", URL+"&account="+qrCode.getAccountNum());
				}

				map.put("dictValue", "0");
				listMap.add(map);
			}
		}
		message.init(true,"获取栏目列表成功",listMap);

	}

	/**
	 * 获取栏目列表新新
	 * @param message
	 * @param content
     */
	public void findNewsType(Message message, String content){
		if(com.cy.common.utils.StringUtils.isBlank(content)){
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}
		Map<String, Object> map = JSON.parseObject(content, Map.class);
		String channel = (String) map.get("channel");
		String isMain = (String) map.get("isMain");
		String parentId = (String) map.get("parentId");
		String isNavigation = (String) map.get("isNavigation");
		String level = (String) map.get("level");
		String deptId = (String) map.get("deptId");

		//栏目渠道
		if(StringUtils.isBlank(channel) || ( !channel.equals("10") && !channel.equals("20") && !channel.equals("30")&& !channel.equals("40"))){
			map.put("channel", "");
		}

		//判断是否上首页
		if(StringUtils.isBlank(isMain) || !isMain.equals("1")){
			map.put("isMain", "");
		}

		//判断等级，目前只有两级栏目，暂时只有1可选
		if(StringUtils.isBlank(level) || !level.equals("1")){
			map.put("level", "");
		}

		//是否上导航
		if(StringUtils.isBlank(isNavigation) || !isNavigation.equals("1")){
			map.put("isNavigation", "");
		}

		//組織
		if(StringUtils.isBlank(deptId) || deptId.equals("1")){
			map.put("deptId", "1");
		}else{
			map.put("deptId", deptId );
		}

		List<NewsType> list = mobNewsTypeMapper.findNewsType(map);
		List<TreeString> treeList = new ArrayList<>();
		List<TreeString> rootTrees = new ArrayList<>();

		for( NewsType alu : list ) {
			TreeForNewsType node = new TreeForNewsType();
			long pid = alu.getParent_id();
			node.setIconCls(alu.getTypePic());
			node.setId( String.valueOf( alu.getId() ) );
			node.setPid( String.valueOf( pid ) );
			node.setType(String.valueOf(alu.getType()));
			node.setNewsId(alu.getNewsId());
			node.setUrl(alu.getUrl());
			node.setText( alu.getName() );
			node.setCode( alu.getCode() );
			if( pid == 0 ) {
				rootTrees.add(node);
			}
			else
				treeList.add( node );
		}

		TreeStringUtil.parseTreeString( rootTrees, treeList );

		if( StringUtils.isNotBlank(parentId) && !parentId.equals("0") )
			message.setObj(treeList);
		else
			message.setObj(rootTrees);

		message.setMsg("成功获取栏目列表");
		message.setSuccess(true);
	}

	/**
	 * 获取新闻列表接口
	 * @param message
	 * @param content
     */
	public void findNewsList(Message message, String content){
		if(StringUtils.isBlank(content)){
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}

		Map<String, Object> map = JSON.parseObject(content, Map.class);

		String channel = (String) map.get("channel");
		if(StringUtils.isBlank(channel) || ( !channel.equals("10") && !channel.equals("20") && !channel.equals("30")&& !channel.equals("40"))){
			message.setMsg("请提供正确的渠道编号");
			message.setSuccess(false);
			return;
		}

		String category = (String) map.get("category");
		String channelName = (String) map.get("channelName");
		String type = (String) map.get("type");
		String topnews = (String) map.get("topnews");
		String page = (String) map.get("page");
		String rows = (String) map.get("rows");
		String alumniId = (String) map.get("alumniId");

		if(StringUtils.isNotBlank(channelName)){
			String channelNames[] = channelName.split(",");
			map.put("channelNames", channelNames);
		}

		if(StringUtils.isNotBlank(type)){
			String types[] = type.split(",");
			map.put("types", types);
		}

		if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
			int start = (Integer.valueOf(page) - 1) * Integer.valueOf(rows);
			map.put("start", start);
			map.put("rows", Integer.valueOf(rows));
		}

		List<News> list = newsMapper.selectNewsForNew(map);
		message.setMsg("获取成功");
		message.setObj(list);
		message.setSuccess(true);
	}

	public void findNewsChannelList(Message message, String content){
		Map<String, Object> map = new HashMap<>();
		List<NewsChannel> list = newsChannelMapper.selectNewsChannelList(map);
		message.setObj(list);
		message.setMsg("获取频道成功");
		message.setSuccess(true);
	}


	public static void main(String[] args) {
		String str = "{\"taskOId\":\"article_delete\",\"content\":{\"id\":\"11\"}}";
		System.out.println(str) ;
	}
	/**
	 *是否上导航
	 */
	public void setMobTypeList(String ids, byte controlStr) {
		Map<String, Object> map = new HashMap<String, Object>();

		String[] array = ids.split(",");

		map.put("list", array);
		map.put("controlStr", controlStr);

		mobNewsTypeMapper.setMobTypeList(map);
	}

	/**
	 *是否上首页
	 */
	public void setMobTypePage(String ids, byte controlStr) {
		Map<String, Object> map = new HashMap<String, Object>();

		String[] array = ids.split(",");

		map.put("list", array);
		map.put("controlStr", controlStr);

		mobNewsTypeMapper.setMobTypePage(map);
	}
}

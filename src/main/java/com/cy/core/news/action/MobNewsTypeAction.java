package com.cy.core.news.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cy.core.news.service.NewsService;
import com.cy.core.user.entity.User;
import com.cy.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.news.entity.News;
import com.cy.core.news.entity.NewsType;
import com.cy.core.news.service.MobNewsTypeService;
import com.cy.util.WebUtil;

@Namespace("/mobNewsType")
@Action(value = "mobNewsTypeAction")
public class MobNewsTypeAction extends AdminBaseAction {

	@Autowired
	private MobNewsTypeService mobNewsTypeService;

	@Autowired
	private NewsService newsService;

	private NewsType type;
	private String province;
	private String city;
	private String isRmv;
	private String isUp;
	
	
	/**--新闻类型管理--**/
/*	public void dataGridNewsType(){
		if(type==null){
			type = new NewsType();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parent_id", type.getParent_id());
		map.put("name", type.getName());
		map.put("type", type.getType());
		map.put("origin", type.getOrigin());
		map.put("cityName", type.getCityName());
		DataGrid<NewsType> data = mobNewsTypeService.dataGrid(map);
		super.writeJson(data.getRows());
	}*/
	public void dataGridNewsType(){
		if(type==null){
			type = new NewsType();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", type.getName());
		map.put("type", type.getType());
		map.put( "channel", type.getChannel() );	//lixun

		// 获取当前用户
		User user = UserUtils.getUser();
		if(user != null && user.getDeptId() > 0) {
			map.put("deptId",user.getDeptId());
		}
		List<NewsType> mobNewsType = new ArrayList<NewsType>();
		mobNewsType = mobNewsTypeService.mobNewsTypeTree(map);
		super.writeJson(mobNewsType);
	}
	
	/**--新闻类型添加--**/
	public void saveNewsType(){
		Message message = new Message();
		if((province==null && city==null) || province.isEmpty()) {
			type.setCityName("");
		} else {
			type.setCityName(province + " " + city);
		}
		type.setDeptId(String.valueOf(getUser().getDeptId()));
		String result = mobNewsTypeService.save(type);
		if("1".equals(result)){
			//如果是“单页面”栏目添加对应新闻
			if(type.getType()==3) {
				long newTypeId = mobNewsTypeService.getNewId();
				News news = new News();
				news.setTitle(type.getNewsTitle());
				news.setContent(type.getNewsContent());
				news.setChannelName("");
				news.setCategory(newTypeId);
				mobNewsTypeService.saveNews(news);
			}
			message.setMsg("添加成功");
			message.setSuccess(true);
		}else if("2".equals(result)){
			message.setMsg("子栏目不能超过6个");
			message.setSuccess(false);
		/*}else if("3".equals(result)){
			message.setMsg("微信一级栏目只能增3个");
			message.setSuccess(false);
		}else if("4".equals(result)){
			message.setMsg("微信二级栏目只能增5个");
			message.setSuccess(false);*/
		}else if("5".equals(result)){
			message.setMsg("标识重复，请重新输入");
			message.setSuccess(false);
		}
		else{
			message.setMsg("添加失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}
	/**--新闻栏目删除--**/
	public void deleteNewsType(){
		Message message = new Message();
		long parent_id = WebUtil.toLong(this.getRequest().getParameter("parent_id"));
		if(!WebUtil.isEmpty(ids)){
			String[] array = ids.split(",");
			List<Long> list = new ArrayList<Long>();
			for (String id : array){
				mobNewsTypeService.deleteNews(id);
				list.add(Long.parseLong(id));
			}
			mobNewsTypeService.delete(list, parent_id);
			message.setSuccess(true);
			message.setMsg("删除成功");
			super.writeJson(message);
		}
	}
	
	/**--查看新闻栏目--**/
	public void getNewsType(){
		NewsType t = this.mobNewsTypeService.getById(Long.toString(type.getId()));
		super.writeJson(t);
	}
	
	
	/**--编辑新闻栏目--**/
	public void updateNewsType(){
		Message message = new Message();
		if((province==null && city==null) || province.isEmpty()) {
			type.setCityName("");
		} else {
			type.setCityName(province + " " + city);
		}
		System.out.println("Name"+type.getName());

		if(StringUtils.isBlank(type.getDeptId())) {
			type.setDeptId(String.valueOf(getUser().getDeptId()));
		}
		this.mobNewsTypeService.update(type);
		if(type.getType()==3) {
			News news = new News();
			news.setTitle(type.getNewsTitle());
			news.setContent(type.getNewsContent());
			news.setCategory(type.getId());
			if (StringUtils.isNotBlank(type.getNewsId())){
				news.setNewsId(Long.parseLong(type.getNewsId()));
				mobNewsTypeService.updateNews(news);
			} else{
				news.setChannelName("");
               mobNewsTypeService.saveNews(news);
			}
		}
		message.setSuccess(true);
		message.setMsg("更新成功");
		super.writeJson(message);
	}
	
	public void addToWebpageDB() {
		Message message = mobNewsTypeService.addTowebpageDB();
		super.writeJson(message);
	}
	public void setMobTypeList() {
		Message message = new Message();
		byte controlStr;
		if (isRmv.equals("true")) {
			// 100 is for topnews of mobile
			controlStr = 1;
		} else {
			controlStr = 0;
		}
		try {
			mobNewsTypeService.setMobTypeList(ids, controlStr);
			message.setMsg("设置成功");
			message.setSuccess(true);

		} catch (Exception e) {
			message.setMsg("设置失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void setMobTypePage() {
		Message message = new Message();
		byte controlStr;
		if (isUp.equals("true")) {
			// 100 is for topnews of mobile
			controlStr = 1;
		} else {
			controlStr = 0;
		}
		try {
			mobNewsTypeService.setMobTypePage(ids, controlStr);
			message.setMsg("设置成功");
			message.setSuccess(true);

		} catch (Exception e) {
			message.setMsg("设置失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public NewsType getType() {
		return type;
	}

	public void setType(NewsType type) {
		this.type = type;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIsRmv() {
		return isRmv;
	}

	public void setIsRmv(String isRmv) {
		this.isRmv = isRmv;
	}

	public String getIsUp() {
		return isUp;
	}

	public void setIsUp(String isUp) {
		this.isUp = isUp;
	}
}

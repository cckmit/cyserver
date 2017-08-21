package com.cy.core.news.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cy.core.user.entity.User;
import com.cy.util.UserUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.news.entity.News;
import com.cy.core.news.entity.NewsType;
import com.cy.core.news.service.WebNewsTypeService;
import com.cy.util.WebUtil;

@Namespace("/webNewsType")
@Action(value = "webNewsTypeAction")
public class WebNewsTypeAction extends AdminBaseAction {

	@Autowired
	private WebNewsTypeService webNewsTypeService;

	private NewsType type;

//	private String country;
	private String province;
	private String city;
	
	
	/**--新闻类型管理--**/
	public void dataGridNewsType(){
		if(type==null){
			type = new NewsType();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		/*map.put("rows", rows);
		map.put("page", page);
		map.put("parent_id", type.getParent_id());*/
		map.put("name", type.getName());
		map.put("type", type.getType());
		map.put("origin", type.getOrigin());
		map.put("cityName", type.getCityName());
		User user = UserUtils.getUser();
		if(user != null&& user.getDeptId()>0) {
			map.put("deptId",user.getDeptId());
		}
		List<NewsType> list = webNewsTypeService.treeGird(map);
		super.writeJson(list);
	}
	
	/**--新闻类型添加--**/
	public void saveNewsType(){
		Message message = new Message();
		if((province==null && city==null) || province.isEmpty()) {
			type.setCityName("");
		} else {
			type.setCityName(province + " " + city);
		}
		if(getUser().getDeptId()>0){
			type.setDeptId(String.valueOf(getUser().getDeptId()));
		}else{
			type.setDeptId("");
		}
		String result = webNewsTypeService.save(type);
		if("1".equals(result)){
			//如果是“单页面”栏目添加对应新闻
			if(type.getType()==3) {
				long newTypeId = webNewsTypeService.getNewId();
				News news = new News();
				news.setTitle(type.getNewsTitle());
				news.setContent(type.getNewsContent());
				news.setChannelName("");
				news.setCategoryWeb(newTypeId);
				webNewsTypeService.saveNews(news);
			}
			message.setMsg("添加成功");
			message.setSuccess(true);
		}else if("2".equals(result)){
			message.setMsg("子栏目不能超过6个");
			message.setSuccess(false);
		}else{
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
				webNewsTypeService.deleteNews(id);
				list.add(Long.parseLong(id));
			}
			webNewsTypeService.delete(list, parent_id);
			message.setSuccess(true);
			message.setMsg("删除成功");
			super.writeJson(message);
		}
	}
	
	/**--查看新闻栏目--**/
	public void getNewsType(){
		NewsType t = this.webNewsTypeService.getById(Long.toString(type.getId()));
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
		if(getUser().getDeptId()>0){
			type.setDeptId(Long.toString(getUser().getDeptId()));
		}else{
			type.setDeptId("");
		}
		this.webNewsTypeService.update(type);
		if(type.getType()==3) {
			News news = new News();
			news.setTitle(type.getNewsTitle());
			news.setContent(type.getNewsContent());
			news.setCategoryWeb(type.getId());
			webNewsTypeService.updateNews(news);
		}
		message.setSuccess(true);
		message.setMsg("更新成功");
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


}

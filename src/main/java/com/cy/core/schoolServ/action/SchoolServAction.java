package com.cy.core.schoolServ.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.cy.core.user.entity.UserRole;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.schoolServ.entity.SchoolServ;
import com.cy.core.schoolServ.service.SchoolServService;


@Namespace("/mobile/schoolServ")
@Action(value = "schoolServAction", results = { 
		@Result(name = "add", location = "/page/admin/schoolServ/add.jsp"),
		@Result(name = "view", location = "/page/admin/schoolServ/view.jsp"),
		@Result(name = "edit", location = "/page/admin/schoolServ/edit.jsp")
		})
public class SchoolServAction extends AdminBaseAction
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchoolServAction.class);

	@Autowired
	private SchoolServService service;

	private SchoolServ formData;




	public void dataGrid()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("page", page);
		if (formData != null)
		{
			if(formData != null)
			{
				map.put("serviceName", formData.getServiceName());
				map.put("systemService", formData.getSystemService());
				map.put("provideService", formData.getProvideService());
				map.put("needAuthentication", formData.getNeed_authentication() );
			}
		}


		List<UserRole> list = getUser().getUserRoles();
		for(UserRole ur:list){
			if(ur.getRoleId() == 6)
				map.put("deptId","0");
		}

		if(map.get("deptId") == null){
			map.put("deptId","1");
		}

		super.writeJson(service.dataGrid(map));
	}
	
	public void save()
	{
		Message message = new Message();
		try
		{
			com.cy.core.user.entity.User user = (com.cy.core.user.entity.User)getSession().get("user");
			formData.setCreateBy(user.getUserName());
			service.save(formData);
			message.setMsg("操作成功");
			message.setSuccess(true);

		} 
		catch (Exception e)
		{
			logger.error(e, e);
			message.setMsg("操作失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete()
	{
		Message message = new Message();
		try
		{
			service.delete(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);

		} 
		catch (Exception e)
		{
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void update()
	{
		Message message = new Message();
		try
		{
			com.cy.core.user.entity.User user = (com.cy.core.user.entity.User)getSession().get("user");
			formData.setCreateBy(user.getUserName());
			service.update(formData);
			message.setMsg("操作成功");
			message.setSuccess(true);

		} 
		catch (Exception e)
		{
			logger.error(e, e);
			message.setMsg("操作失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public String getById()
	{
		formData = service.selectById(id);
		return "view";
	}
	
	public String initUpdate()
	{
		String returnStr ;
		if(id == 0) {
			returnStr = "add" ;
		} else {
			formData = service.selectById(id);
			returnStr = "edit";
		}
		List<UserRole> list = getUser().getUserRoles();
		for(UserRole ur:list){
			if(ur.getRoleId() == 6) {
				if (formData == null) {
					formData = new SchoolServ();
				}
				formData.setDeptId("0");
				break ;
			}
		}
		return returnStr ;
	}
	

	public void doNotNeedSessionAndSecurity_getServiceList()
	{
		List<SchoolServ> schoolServList = service.getServiceList();

		super.writeJson(schoolServList);
	}

	public void doNotNeedSessionAndSecurity_getServiceListNew()
	{
		Map<String,SchoolServ> map = new HashMap<String,SchoolServ>();

		List<SchoolServ> schoolServList = service.getServiceList();
		if(schoolServList!=null && schoolServList.size()>0){

			for(int i=0;i<schoolServList.size();i++){
				SchoolServ schoolServ=schoolServList.get(i);
				String name = schoolServ.getServiceName();

				if("值年返校".equals(name)){
					map.put("t1in1",schoolServ);
				}else if("爱心捐赠".equals(name)){
					map.put("t1in2",schoolServ);
				}else if("求职招聘".equals(name)){
					map.put("t1in3",schoolServ);
				}else if("互帮互助".equals(name)){
					map.put("t1in4",schoolServ);
				}else if("校友活动".equals(name)){
					map.put("t1in5",schoolServ);
				}else if("项目合作".equals(name)){
					map.put("t1in6",schoolServ);
				}else if("金融服务".equals(name)){
					map.put("t2in1",schoolServ);
				}else if("证书补办".equals(name)){
					map.put("t2in2",schoolServ);
				}else if("档案查询".equals(name)){
					map.put("t2in3",schoolServ);
				}else if("资料查询".equals(name)){
					map.put("t2in4",schoolServ);
				}else if("稿件投递".equals(name)){
					map.put("t3in1",schoolServ);
				}else if("联系学校".equals(name)){
					map.put("t3in2",schoolServ);
				}

			}
		}

		super.writeJson(map);
	}
	

	public SchoolServ getFormData() {
		return formData;
	}

	public void setFormData(SchoolServ formData) {
		this.formData = formData;
	}
	
	
	

}

package com.cy.core.campuscard.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.campuscard.entity.CampusCard;
import com.cy.core.campuscard.service.CampusCardService;

/** --商户校园卡-- **/
@Namespace("/page/admin/campusCard")
@Action(value = "campusCardAction", results = { @Result(name = "view", location = "/page/admin/campusCard/view.jsp"),
		@Result(name = "edit", location = "/page/admin/campusCard/edit.jsp") })
public class CampusCardAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CampusCardAction.class);

	@Autowired
	private CampusCardService service;

	private CampusCard formData;

	private String province;

	private String city;

	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("page", page);
		if (formData != null) {
			if (formData.getName() != null && formData.getName().length() > 0) {
				map.put("name", formData.getName());
			}

			if (formData.getIndustry() != null && formData.getIndustry().length() > 0) {
				map.put("industry", formData.getIndustry());
			}
			if (formData.getStatus() != -1) {
				map.put("status", formData.getStatus());
			}
		}
		String location = "";
		if (province != null && province.length() > 0) {
			location += province;
		}

		if (city != null && city.length() > 0) {
			location += " " + city;
		}
		map.put("location", location);
		super.writeJson(service.dataGrid(map));
	}

	public void statisticalDataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("page", page);
		if (formData != null) {
			if (formData.getIndustry() != null && formData.getIndustry().length() > 0) {
				map.put("industry", formData.getIndustry());
			}

		}
		
		String location = "";
		if (province != null && province.length() > 0) {
			location += province;
		}

		if (city != null && city.length() > 0) {
			location += " " + city;
		}
		map.put("location", location);

		super.writeJson(service.statisticalDataGrid(map));
	}

	public void save() {
		Message message = new Message();
		try {
			formData.setStatus(1);
			formData.setCheckTime(new Date());

			service.save(formData);
			message.setMsg("操作成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("操作失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete() {
		Message message = new Message();
		try {
			service.delete(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void update() {
		Message message = new Message();
		try {
			formData.setCheck(getUser().getUserId());
			formData.setCheckTime(new Date());
			service.update(formData);
			message.setMsg("操作成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("操作失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public String getById() {
		formData = service.selectById(id);
		return "view";
	}

	public String doNotNeedSecurity_initUpdate() {
		formData = service.selectById(id);
		return "edit";
	}

	public CampusCard getFormData() {
		return formData;
	}

	public void setFormData(CampusCard formData) {
		this.formData = formData;
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

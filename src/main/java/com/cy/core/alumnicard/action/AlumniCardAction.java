package com.cy.core.alumnicard.action;

import java.util.HashMap;
import java.util.Map;

import com.cy.common.utils.TimeZoneUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.alumnicard.entity.AlumniCard;
import com.cy.core.alumnicard.service.AlumniCardService;
import com.cy.system.WebUtil;

/**
 * 校友卡功能
 * 
 * @author Administrator
 * 
 */
@Namespace("/page/admin/alumniCard")
@Action(value = "alumniCardAction", results = { @Result(name = "view", location = "/page/admin/alumniCard/view.jsp"),
		@Result(name = "edit", location = "/page/admin/alumniCard/edit.jsp") })
public class AlumniCardAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AlumniCardAction.class);

	@Autowired
	private AlumniCardService service;

	private AlumniCard formData;


	private String alumniCardId;

	/** --校友卡列表数据查询-- **/
	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("page", page);
		if(formData != null){

			if (formData.getName() != null && formData.getName().length() > 0) {
				map.put("name", formData.getName());
			}

			if (formData.getSex() != null && formData.getSex().length() > 0) {
				map.put("sex", formData.getSex());
			}
			if (formData.getAddress() != null && formData.getAddress().length() > 0) {
				map.put("address", formData.getAddress());
			}
			if (formData.getStatus() != -1) {
				map.put("status", formData.getStatus());
			}
		}


		super.writeJson(service.dataGrid(map));
	}


	/** --保存操作-- **/
	public void save() {
		Message message = new Message();
		try {
			formData.setStatus(1);
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

	/** --更新操作-- **/
	public void update() {
		Message message = new Message();
		try {
			formData.setChecker(getUser().getUserId());
			formData.setCheckTime(TimeZoneUtils.getFormatDate());
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
		formData = service.selectById(alumniCardId);
		return "view";
	}

	/** --点击编辑后跳转的页面,这里不需要验证权限(程辉,方法加上前缀doNotNeedSecurity)-- **/
	public String doNotNeedSecurity_initUpdate() {
		formData = service.selectById(alumniCardId);
		return "edit";
	}

	public AlumniCard getFormData() {
		return formData;
	}

	public void setFormData(AlumniCard formData) {
		this.formData = formData;
	}

	public String getAlumniCardId() {
		return alumniCardId;
	}

	public void setAlumniCardId(String alumniCardId) {
		this.alumniCardId = alumniCardId;
	}
}

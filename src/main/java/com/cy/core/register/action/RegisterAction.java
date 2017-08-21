package com.cy.core.register.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.register.entity.Register;
import com.cy.core.register.service.RegisterService;
import com.cy.system.WebUtil;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron on 2015/3/8.
 */
@Namespace("/register")
@Action(value = "registerAction")
public class RegisterAction extends AdminBaseAction {
	private static final Logger logger = Logger.getLogger(RegisterAction.class);

	private Register register = new Register();

	@Autowired
	private RegisterService registerService;

	public void save() {
		Message message = new Message();
		try {
			registerService.save(register);
			message.setMsg("新增成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("新增失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void getList() {
		Map<String, Object> map = new HashMap<String, Object>();
		String name = getRequest().getParameter("name");
		String gender = getRequest().getParameter("gender");
		// String smallAge = getRequest().getParameter("smallAge");
		// String largeAge = getRequest().getParameter("largeAge");
		String city = getRequest().getParameter("x_address");
		// String degree = getRequest().getParameter("degree");

		String schoolId = getRequest().getParameter("schoolId"); // 学校
		String departId = getRequest().getParameter("departId"); // 院系
		String majorId = getRequest().getParameter("majorId"); // 专业
		String gradeId = getRequest().getParameter("gradeId"); // 年级
		String classId = getRequest().getParameter("classId"); // 班级

		if (!WebUtil.isEmpty(schoolId)) {
			map.put("deptId", schoolId);
		}
		if (!WebUtil.isEmpty(departId)) {
			map.put("deptId", departId);
		}
		if (!WebUtil.isEmpty(gradeId)) {
			map.put("deptId", gradeId);
		}
		if (!WebUtil.isEmpty(classId)) {
			map.put("deptId", classId);
		}
		map.put("page", page);
		map.put("rows", rows);
		map.put("name", name);
		map.put("gender", gender);
		// map.put("smallAge", smallAge);
		// map.put("largeAge", largeAge);
		map.put("city", city);
		// map.put("degree", degree);
		map.put("majorId", majorId);

		super.writeJson(registerService.dataGrid(map));
	}

	public void getById() {
		super.writeJson(registerService.getById(register.getId()));
	}

	public void update() {
		Message message = new Message();
		try {
			registerService.update(register);
			message.setMsg("修改成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete() {
		Message message = new Message();
		try {
			registerService.delete(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public Register getRegister() {
		return register;
	}

	public void setRegister(Register Register) {
		this.register = Register;
	}
}

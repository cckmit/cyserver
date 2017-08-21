package com.cy.core.major.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.base.entity.TreeString;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dept.service.DeptService;
import com.cy.core.major.entity.Major;
import com.cy.core.major.service.MajorService;

@Namespace("/major")
@Action(value = "majorAction")
public class MajorAction extends AdminBaseAction {

	private static final Logger logger = Logger.getLogger(MajorAction.class);

	private Major major;

	private String deptId;

	@Autowired
	private MajorService majorService;

	@Autowired
	private DeptService deptService;

	public void doNotNeedSecurity_getDeptTree() {
		List<Dept> list = deptService.selectDepart(getUser().getDepts());
		List<TreeString> allList = new ArrayList<TreeString>();
		if (list != null && list.size() > 0) {
			for (Dept dept : list) {
				TreeString node = new TreeString();
				node.setId(dept.getDeptId());
				node.setState("open");
				node.setPid(dept.getParentId());
//				node.setText(dept.getDeptName());
				node.setText(dept.getFullName());
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("level", dept.getLevel());
				attributes.put("fullName", dept.getFullName());
				node.setAttributes(attributes);
				allList.add(node);
			}
		}
		super.writeJson(allList);
	}

	public void save() {
		Message message = new Message();
		try {
			long count = majorService.countByName(major.getMajorName());
			if (count > 0) {
				message.setMsg("该专业名称已经存在");
				message.setSuccess(false);
			} else {
				majorService.save(major);
				message.setMsg("保存成功");
				message.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void getList() {
		Map<String, Object> map = new HashMap<String, Object>();
		String majorName = getRequest().getParameter("majorName");
		String school = getRequest().getParameter("school");
		String department = getRequest().getParameter("department");
		map.put("page", page);
		map.put("rows", rows);
		map.put("majorName", majorName);
		map.put("school", school);
		map.put("department", department);
		map.put("deptList", getUser().getDepts());
		super.writeJson(majorService.dataGrid(map));
	}

	public void getById() {
		super.writeJson(majorService.getById(id));
	}

	public void doNotNeedSecurity_getById() {
		super.writeJson(majorService.getById(id));
	}

	public void update() {
		Message message = new Message();
		try {
			long count = majorService.countByIdName(major);
			if (count > 0) {
				message.setMsg("该专业名称已经存在");
				message.setSuccess(false);
			} else {
				majorService.update(major);
				message.setMsg("保存成功");
				message.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void delete() {
		Message message = new Message();
		try {
			majorService.delete(ids);
			message.setMsg("删除成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	public void doNotNeedSecurity_getMajor() {
		super.writeJson(majorService.getMajor(deptId));
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

}

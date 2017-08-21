package com.cy.mobileInterface.department.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.dept.entity.Dept;
import com.cy.mobileInterface.department.entity.Department;
import com.cy.util.WebUtil;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DeptMapper deptMapper;

	@Override
	public void getDepartName(Message message, String content) {
		Department department = JSON.parseObject(content, Department.class);
		if (WebUtil.isEmpty(department.getBaseInfoId())) { // 协议检查
			message.setMsg("输入基础id");
			message.setSuccess(false);
			return;
		}
		String deptId = "";
		if (department.getBaseInfoId().length() > 16) {
			deptId = department.getBaseInfoId().substring(0, 16);
		}
		Dept dept = deptMapper.getById(deptId);
		if (dept == null) {
			message.setMsg("系统查不到此机构");
			message.setSuccess(false);
			return;
		}
		message.setMsg("查询成功!");
		message.setObj(dept);
		message.setSuccess(true);

	}

}

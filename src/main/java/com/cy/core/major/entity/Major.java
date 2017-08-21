package com.cy.core.major.entity;

import java.io.Serializable;
import java.util.List;

import com.cy.core.dept.entity.Dept;

public class Major implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5921718162715129697L;
	private long majorId;
	private String majorName;
	private List<Dept> deptList;
	private String deptIds;  		//"deptId1,deptId2,deptId3"
	
	public void setMajorId(long majorId) {
		this.majorId = majorId;
	}
	public long getMajorId() {
		return majorId;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}
	public List<Dept> getDeptList() {
		return deptList;
	}
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	public String getDeptIds() {
		return deptIds;
	}
}

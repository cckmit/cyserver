package com.cy.core.major.entity;

import java.io.Serializable;

public class MajorDept implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5921718162715129697L;
	private long majorId;
	private String deptId;
	public long getMajorId() {
    	return majorId;
    }
	public void setMajorId(long majorId) {
    	this.majorId = majorId;
    }
	public String getDeptId() {
    	return deptId;
    }

	public void setDeptId(String deptId) {
    	this.deptId = deptId;
    }
	
	
	
	
}

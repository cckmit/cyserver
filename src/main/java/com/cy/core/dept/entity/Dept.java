package com.cy.core.dept.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Dept implements Serializable {
	private static final long serialVersionUID = 1L;
	private String deptId;
	private String deptName;
	private String parentId;
	private Date createTime;
	private String fullName;
	private int level;
	private String aliasName;

	private String schoolId;
	private String departId;
	
	private String resourceName;

	private String isCurrent ;		// 是否现有
	private String hasBelong ;		// 是否具有归属
	private String belongDeptId ;	// 归属部门
	private String belongDeptName ;	// 归属部门名称

//    查询条件 add by jiangling
	private String schoolName; 		// 学校名称
    private String departName;      // 院系名称
    private String grade;           // 年级名称
    private String classes;         // 班级名称

	private String deptPid ;        // 查询该院系下所有的院系信息

	private String iconCls;	// 图标

	private String status;		//16-08-18	状态

	private String sort;		//排序序号

	private String resourceSort;		//之前排序序号
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    private String deptIds ;		// 多个deptId 组合的字符串,已","隔开


	private boolean checkable = false;

	public boolean isCheckable() {return checkable; }

	public void setCheckable(boolean checkable) {this.checkable = checkable; }

	private List<Dept> depts;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getResourceName() {
    	return resourceName;
    }

	public void setResourceName(String resourceName) {
    	this.resourceName = resourceName;
    }

	public List<Dept> getDepts() {
    	return depts;
    }

	public void setDepts(List<Dept> depts) {
    	this.depts = depts;
    }

	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

	public String getBelongDeptId() {
		return belongDeptId;
	}

	public void setBelongDeptId(String belongDeptId) {
		this.belongDeptId = belongDeptId;
	}

	public String getBelongDeptName() {
		return belongDeptName;
	}

	public void setBelongDeptName(String belongDeptName) {
		this.belongDeptName = belongDeptName;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getHasBelong() {
		return hasBelong;
	}

	public void setHasBelong(String hasBelong) {
		this.hasBelong = hasBelong;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getResourceSort() {
		return resourceSort;
	}

	public void setResourceSort(String resourceSort) {
		this.resourceSort = resourceSort;
	}

	public String getDeptPid() {
		return deptPid;
	}

	public void setDeptPid(String deptPid) {
		this.deptPid = deptPid;
	}

	@Override
	public String toString() {
		return "Dept{" +
				"deptId='" + deptId + '\'' +
				", deptName='" + deptName + '\'' +
				", parentId='" + parentId + '\'' +
				", createTime=" + createTime +
				", fullName='" + fullName + '\'' +
				", level=" + level +
				", aliasName='" + aliasName + '\'' +
				", schoolId='" + schoolId + '\'' +
				", departId='" + departId + '\'' +
				", resourceName='" + resourceName + '\'' +
				", isCurrent='" + isCurrent + '\'' +
				", hasBelong='" + hasBelong + '\'' +
				", belongDeptId='" + belongDeptId + '\'' +
				", belongDeptName='" + belongDeptName + '\'' +
				", schoolName='" + schoolName + '\'' +
				", departName='" + departName + '\'' +
				", grade='" + grade + '\'' +
				", classes='" + classes + '\'' +
				", deptPid='" + deptPid + '\'' +
				", iconCls='" + iconCls + '\'' +
				", status='" + status + '\'' +
				", deptIds='" + deptIds + '\'' +
				", checkable=" + checkable +
				", depts=" + depts +
				'}';
	}
}

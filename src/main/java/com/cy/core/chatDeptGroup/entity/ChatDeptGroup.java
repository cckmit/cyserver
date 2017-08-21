package com.cy.core.chatDeptGroup.entity;

import com.cy.base.entity.DataEntity;

/**
 * 
 * <p>Title: ChatDeptGroup</p>
 * <p>Description: 机构群组实体类</p>
 * 
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-7-13 下午4:44:10
 */
public class ChatDeptGroup extends DataEntity<ChatDeptGroup>
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//--------------数据库字段---------------//
	private String deptId       ;	// 机构ID(班级编号)
	private String groupId      ;	// 好友ID(群组编号)

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
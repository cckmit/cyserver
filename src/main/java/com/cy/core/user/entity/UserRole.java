package com.cy.core.user.entity;

import com.cy.core.role.entity.Role;

import java.io.Serializable;

/**
 * <p>Title: UserRole</p>
 * <p>Description: 用户表与角色表之间的映射表cy_user_role的实体类</p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月7日 上午11:51:05
 */
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private long userId;	//用户ID
	private int roleId;		//角色ID
	private Role role;		//角色
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
}
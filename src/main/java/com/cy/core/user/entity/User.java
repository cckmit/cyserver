package com.cy.core.user.entity;

import java.io.Serializable;
import java.util.List;

import com.cy.core.alumni.entity.Alumni;
import com.cy.core.dept.entity.Dept;
import com.cy.core.deptInfo.entity.DeptInfo;
import com.cy.core.role.entity.Role;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private long userId;
	private String userAccount;
	private String userPassword;
	private String userName;
	private String email;
	private String telephone;
	private long deptId;
	private int flag;
	private long roleId;
	private Role role;
	private List<Dept> depts;
	private Alumni alumni;
	private String roleIds;		//角色Ids
	private List<UserRole> userRoles;
	private List<Role> roles;
	private String roleNames;
	private String alumniRoleId;
	private String deptShit;

	private String isFirstLogin ;		// 是否首次登陆（0或空：首次登陆；1：不是首次登陆）


	private String lastLoginTime;		//最后一登陆时间

	private String associationId;		//社团组织

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	//add by jiangling
	private List<DeptInfo> deptInfos;

	//add by jiangling
	public List<DeptInfo> getDeptInfos() {
		return deptInfos;
	}

	public void setDeptInfos(List<DeptInfo> deptInfos) {
		this.deptInfos = deptInfos;
	}

	public void setDeptShit(String deptShit) {this.deptShit = deptShit;}

	public String getDeptShit() {return deptShit;}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public long getDeptId() {
		return deptId;
	}

	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Alumni getAlumni() {
		return alumni;
	}

	public void setAlumni(Alumni alumni) {
		this.alumni = alumni;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	
	public String getAlumniRoleId() {
		return alumniRoleId;
	}

	public void setAlumniRoleId(String alumniRoleId) {
		this.alumniRoleId = alumniRoleId;
	}

	public String getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(String isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public String getAssociationId() {
		return associationId;
	}

	public void setAssociationId(String associationId) {
		this.associationId = associationId;
	}
}

package com.cy.core.role.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.role.entity.Role;
import com.cy.core.user.entity.UserRole;

public interface RoleMapper {
	List<Role> selectRole(Map<String, Object> map);

	long countRole(Map<String, Object> map);

	Role getById(long roleId);

	void add(Role role);

	void update(Role role);

	void delete(long id);

	void deleteRoleAndResource(long id);

	Role selectResource(long roleId);

	void insertRoleAndResource(Map<String, Object> map);

	List<Role> selectAll();

	List<Role> getMenu(long roleId);

	Role selectSystemAdmin();
	
	List<Role> selectAllNoAdmin();
	
	List<Role> selectxAllNoAdmin();
	
	List<Role> getMenuByUserRole(List<UserRole> userRoles);
	
	List<Role> getUserRoleByUserId(long userId);

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-25
	 * 描述：查询角色授权菜单
	 * @param roleId
	 * @return
	 */
	Role selectMenu(long roleId);

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-25
	 * 描述：查询角色授权功能
	 * @param roleId
	 * @return
	 */
	Role selectAction(long roleId);

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-25
	 * 描述：批量插入角色资源关系
	 * @param roleResources
	 */
	void insertRoleAndResource1(List<Map<String,Long>> roleResources);

	/**
	 * 创建人：kent
	 * 创建时间：2016-07-26
	 * 描述：通过功能查询该功能拥有的角色
	 *
	 * @param actionName
	 * @return 角色列表
	 */
	List<Role> selectRolesByAction(String actionName);

	//List<Role> getrRolesByUserId(long userId);

	/**
	 * 创建人：jiangling
	 * 创建时间：2016-08-03
	 * 描述：通过id查询删除标志位
	 * @param
	 * @return 删除标志位 Can_Del_Flag
	 */
	int getDelFlag(long id);
}

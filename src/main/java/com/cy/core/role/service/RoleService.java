package com.cy.core.role.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.role.entity.Role;
import com.cy.core.user.entity.UserRole;

public interface RoleService
{
	DataGrid<Role> dataGrid(Map<String, Object> map);

	Role getById(long roleId);

	void save(Role role);

	void update(Role role);

	void delete(long id);
	
	Role selectResource(long roleId);

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-26
	 * 描述：授权菜单和功能
	 * @param ids 授权菜单IDs
	 * @param actionIds 授权功能IDs
	 * @param id 角色ID
	 */
	void updateGrant(String ids, String actionIds, long id);
	
	List<Role> selectAll();
	
	List<Role> selectAllNoAdmin();
	
	List<Role> getMenu(long roleId);
	
	List<Role> selectxAllNoAdmin();
	
	List<Role> getMenuByUserRole(List<UserRole> userRoles);
	
	List<Role> getUserRoleByUserId(long userId);

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-25
	 * 描述：查询角色授权菜单
	 * @param roleId
	 * @return 角色
	 */
	Role selectMenu(long roleId);

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-25
	 * 描述：查询角色授权功能
	 * @param roleId
	 * @return 角色
	 */
	Role selectAction(long roleId);

	/**
	 * 创建人：kent
	 * 创建时间：2016-07-26
	 * 描述：通过功能查询该功能拥有的角色
	 * @param actionName
	 * @return
	 */
	List<Role> getRolesByAction(String actionName);

	/**
	 * 创建人：jiangling
	 * 创建时间：2016-08-03
	 * 描述：通过id查询删除标志位
	 * @param
	 * @return 删除标志位 Can_Del_Flag
	 */
	int getDelFlag(long id);
}

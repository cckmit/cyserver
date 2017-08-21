package com.cy.core.resource.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.resource.entity.Resource;
import com.cy.core.role.entity.Role;

public interface ResourceMapper
{
	void save(Resource resource);
	
	void save2Id(Resource resource);

	Resource getById(long id);

	void update(Resource resource);

	void delete(List<Long> list);
	
	void deleteRoleAndResource(List<Long> list);
	
	List<Resource> selectAll();
	
	Resource selectByNameOrUrl(Map<String, Object> map);
	
	List<Resource> selectAllOrderById();

	List<Resource> getResourcesByRoles(List<Role> roles);

	List<Resource> getMenuByRoles(List<Role> roles);

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-26
	 * 描述：获取全部菜单
	 * @return 所有菜单
	 */
	List<Resource> selectAllMenu();

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-26
	 * 描述：通过Ids获取菜单列表
	 * @return 所有菜单
	 */
	List<Resource> selectMenuByIds(String ids);

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-26
	 * 描述：通过Ids获取功能列表
	 * @return 所有功能
	 */
	List<Resource> selectActionByIds(String ids);

	/**
	 * 创建人: jiangling
	 * 创建时间：2016-08-02
	 * 描述：计算菜单下的子菜单数目
	 * @param id
	 * @return 整数
	 */
	int getCountById(long id);


}

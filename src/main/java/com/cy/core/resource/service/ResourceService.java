package com.cy.core.resource.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.cy.base.entity.Tree;
import com.cy.core.resource.entity.Resource;
import com.cy.core.role.entity.Role;

public interface ResourceService
{
	void parseTree(List<Tree> tree,List<Tree> allList);
	
	void save(Resource resource);
	
	Resource getById(long id);
	
	void update(Resource resource);
	
	void delete(long Id);
	
	List<Resource> selectAll();
	
	Resource selectByNameOrUrl(Map<String, Object> map);
	
	String export() throws IOException;
	
	void importData(String url);

	/**
	 * 根据RoleIds获取角色拥有的资源
	 * @param roles
	 * @return 资源列表
	 */
	List<Resource> getResourcesByRoles(List<Role> roles);
	/**
	 * 根据RoleIds获取角色拥有的菜单
	 * @param roles
	 * @return 菜单列表
	 */
	List<Resource> getMenuByRoles(List<Role> roles);

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-25
	 * 描述：获取全部菜单
	 * @return 全部菜单树
	 */
	List<Resource> selectAllMenu();

	/**
	 * 创建人: jiangling
	 * 创建时间：2016-08-02
	 * 描述：计算菜单下的子菜单数目
	 * @param id
	 * @return 整数
     */
	int getCountById(long id);

}

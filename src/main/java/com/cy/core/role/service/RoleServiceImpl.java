package com.cy.core.role.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cy.core.resource.dao.ResourceMapper;
import com.cy.core.resource.entity.Resource;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cy.base.entity.DataGrid;
import com.cy.core.role.dao.RoleMapper;
import com.cy.core.role.entity.Role;
import com.cy.core.user.entity.UserRole;
import com.cy.system.GetDictionaryInfo;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private ResourceMapper resourceMapper;

	public DataGrid<Role> dataGrid(Map<String, Object> map) {
		DataGrid<Role> dataGrid = new DataGrid<Role>();
		long total = roleMapper.countRole(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Role> list = roleMapper.selectRole(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	public Role getById(long roleId) {
		return roleMapper.getById(roleId);
	}

	public void save(Role role) {
		role.setSystemAdmin(0);
		roleMapper.add(role);
	}

	public void update(Role role) {
		roleMapper.update(role);
	}

	public void delete(long id) {
		try {
			roleMapper.deleteRoleAndResource(id);
			roleMapper.delete(id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Role selectResource(long roleId) {
		return roleMapper.selectResource(roleId);
	}

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-26
	 * 描述：授权菜单和功能
	 * @param ids 授权菜单IDs
	 * @param actionIds 授权功能IDs
	 * @param id 角色ID
	 */
	public void updateGrant(String ids, String actionIds, long id) {
		try {
			roleMapper.deleteRoleAndResource(id);
			if(!StringUtils.isBlank(ids)) {
				List<Resource> menus = resourceMapper.selectMenuByIds(ids);
				if(!StringUtils.isBlank(actionIds)){
					List<Resource> actions = resourceMapper.selectActionByIds(actionIds);
					menus.addAll(actions);
				}
				List<Map<String,Long>> roleResources = Lists.newArrayList();
				for(Resource r : menus){
					Map<String,Long> map = Maps.newHashMap();
					map.put("roleId",id);
					map.put("id",r.getId());
					roleResources.add(map);
				}
				if(roleResources.size()>0) {
					roleMapper.insertRoleAndResource1(roleResources);
				}
			}else if(!StringUtils.isBlank(actionIds)){
				List<Resource> actions = resourceMapper.selectActionByIds(actionIds);
				List<Map<String,Long>> roleResources = Lists.newArrayList();
				for(Resource r : actions){
					Map<String,Long> map = Maps.newHashMap();
					map.put("roleId",id);
					map.put("id",r.getId());
					roleResources.add(map);
				}
				if(roleResources.size()>0) {
					roleMapper.insertRoleAndResource1(roleResources);
				}
			}


			/*String[] idAttr = ids.split(",");
			for (String idStr : idAttr) {
				if (idStr != null && !"".equals(idStr)) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("roleId", id);
					map.put("id", Long.parseLong(idStr));
					roleMapper.insertRoleAndResource(map);
				}
			}
			// 刷新内存
			GetDictionaryInfo.getInstance().reloadDictionaryInfoMap();*/
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<Role> selectAll() {
		return roleMapper.selectAll();
	}

	public List<Role> getMenu(long roleId) {
		return roleMapper.getMenu(roleId);
	}

	@Override
	public List<Role> selectAllNoAdmin() {
		return roleMapper.selectAllNoAdmin();
	}
	
	@Override
	public List<Role> selectxAllNoAdmin() {
		return roleMapper.selectxAllNoAdmin();
	}

	public List<Role> getMenuByUserRole(List<UserRole> userRoles) {
		return roleMapper.getMenuByUserRole(userRoles);
	}
	
	@Override
	public List<Role> getUserRoleByUserId(long userId) {
		return roleMapper.getUserRoleByUserId(userId);
	}

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-25
	 * 描述：查询角色授权菜单
	 *
	 * @param roleId 角色ID
	 * @return 角色
	 */
	@Override
	public Role selectMenu(long roleId) {
		return roleMapper.selectMenu(roleId);
	}

	/**
	 * 创建人：Kent
	 * 创建时间：2016-07-25
	 * 描述：查询角色授权功能
	 *
	 * @param roleId
	 * @return 角色
	 */
	@Override
	public Role selectAction(long roleId) {
		return roleMapper.selectAction(roleId);
	}

	/**
	 * 创建人：kent
	 * 创建时间：2016-07-26
	 * 描述：通过功能查询该功能拥有的角色
	 *
	 * @param actionName
	 * @return
	 */
	@Override
	public List<Role> getRolesByAction(String actionName) {
		return roleMapper.selectRolesByAction(actionName);
	}

	/**
	 * 创建人：jiangling
	 * 创建时间：2016-08-03
	 * 描述：通过id查询删除标志位
	 * @param
	 * @return 删除标志位 Can_Del_Flag
	 */
	@Override
	public int getDelFlag(long id) {
		return roleMapper.getDelFlag(id);
	}
}

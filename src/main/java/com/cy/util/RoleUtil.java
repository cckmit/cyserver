package com.cy.util;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cy.core.role.entity.Role;
import com.cy.core.role.service.RoleService;
import com.cy.core.user.entity.User;


/**
 * <p>Title: RoleUtil</p>
 * <p>Description: </p>
 * 
 * @author OuGuiYuan
 * @Company 博视创诚
 * @data 2016年7月8日 下午7:41:53
 */
@Service
public class RoleUtil {

	private static RoleService roleService;

    @Resource
    public void setDataService(RoleService roleService) {
    	RoleUtil.roleService = roleService;
    }
    
	/**
	 * 
	 * @param user
	 * @return true 普通用户
	 *			false 超级用户
	 */
	public static boolean isNotSystemAdmin(User user){
		List<Role> roles = roleService.getUserRoleByUserId(user.getUserId());
		boolean flag = true;
		for (Role role : roles) {
			if(role.getSystemAdmin()==1){
				flag = false;
				break;
			}
		}
		return flag;
	}
}

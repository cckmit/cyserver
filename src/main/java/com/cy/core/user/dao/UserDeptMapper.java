package com.cy.core.user.dao;

import com.cy.core.user.entity.UserDept;
import java.util.Map;


public interface UserDeptMapper
{
	void save(UserDept userDept);
	
	void deleteByUserId(long userId);
	
	void deleteByUserIdAndDeptId(UserDept userDept);

	/* lixun 设置现有自动带入组织机构权限 */
	void addUserPermission( Map<String,String> params );
	void delUserPermission( String deptId );
}

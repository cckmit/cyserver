package com.cy.core.user.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.user.entity.User;
import com.cy.core.user.entity.UserRole;

/**
 * @author Administrator
 *
 */
public interface UserService
{
	DataGrid<User> dataGrid(Map<String, Object> map);
	
	void save(User user,String ids,long type);	//lixun modify
	
	long countByUserAccount(Map<String, Object> map);
	
	User selectByUserId(String userId);
	
	void update(User user,String ids, long type);

	/**
	 * 修改用户信息
	 * @param user
     */
	void update(User user);
	
	void delete(long userId);
	
	User selectRole(long userId);
	
	void updateGrant(String ids,long id);
	
	User selectByUserAccount(String userAccount);
	
	void selectByTelephone(Message message, String content);
	
	void updatePassword(User user);
	
	List<UserRole> getUserRoleIdsByUserId(long userId);

	User selectByUserAccountNoSys(String userAccount);

	long countByTelphone(Map<String, Object> map);

	/**
	 * 修改学院分会管理员数据权限
	 * @param deptId 学院编号
	 * @param aluId	 学院分会编号
	 * @param type	 现有标识(0:取消现有;1:设置现有)
	 */
	void modifyUserPermission(  String deptId, String aluId, String type  );	//lixun 同步加权限
	long saveUserDept( long userid );	//lixun


	/**
	 * 查询满足条件的用户列表
	 * @param map
	 * @return
	 */
	List<User> queryUserList(Map<String, Object> map);

	List<User> queryChoiceUserList(Map<String, Object> map);

}

package com.cy.core.user.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.role.entity.Role;
import com.cy.core.user.entity.User;
import com.cy.core.user.entity.UserRole;

public interface UserMapper {
	long countUser(Map<String, Object> map);

	/**
	 * 分页查询满足条件的用户列表
	 * @param map
	 * @return
     */
	List<User> selectUserList(Map<String, Object> map);

	/**
	 * 分页查询满足条件的用户列表
	 * @param map
	 * @return
	 */
	List<User> selectUserListx(Map<String, Object> map);

	/**
	 * 查询满足条件的用户列表
	 * @param map
	 * @return
     */
	List<User> queryUserList(Map<String, Object> map);

	List<User> queryChoiceUserList(Map<String, Object> map);

	void save(User user);

	long countByUserAccount(Map<String, Object> map);

	User selectByUserId(String userId);

	void update(User user);

	void delete(long userId);

	void updatePassword(User user);

	User selectRole(long userId);

	User selectByUserAccountNoSys(String userAccount);

	User selectByUserAccount(String userAccount);
	
	User selectByTelephone(String userAccount);
	
	List<User> selectByTelephoneForApp(String userAccount);
	
	User selectAdminUser();
	
	void insertUserAndRole(UserRole userRole);
	
	void deleteUserAndRoleByUserId(long userId);
	
	void deleteUserAndRoleByRoleId(long roleId);

	List<UserRole> getUserRoleIdsByUserId(long userId);

	long countByTelphone(Map<String, Object> map);

	String selectDeptIdByAlumniId( long id );	//lixun
	String selectAcademyid( long userid );	//lixun
	void saveUserDept( Map<String, String> map );	//lixun
}

package com.cy.core.userProfile.service;

import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.userProfile.entity.GroupInfoEntity;
import com.cy.core.userProfile.entity.SchoolConfigEntity;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.entity.UserInfo;

public interface UserProfileService
{
	long countUserProfileByBaseInfoId(Map<String, Object> map);
	
	DataGrid<UserProfile> dataGrid(Map<String, Object> map);
	
	List<UserProfile> selectAll();
	
	/**
	 * 用户注册
	 * 
	 * @param userProfile
	 * @return
	 */
	int save(UserProfile userProfile);
	
	int savecheck(UserProfile userProfile); //带检测手机号功能新增
	 
	int updateposition(UserProfile userProfile);
	
	void delete(String ids);
	
	UserProfile selectById(String id);

	void update(UserProfile userProfile);
	
	void updateNew(UserProfile userProfile,UserInfo info);
	
	void updateAuthenticated(Map<String, Object> map);

	long countByUserAccount(Map<String, Object> map);
	
	long countByPhoneNum(String phoneNum);

	long countByEmail(String email);
	/**
	 * 使用帐号或者手机号查询用户信息，手机端可以使用帐号登录，也可以使用手机号登录
	 * 
	 * @param accountNum
	 * @return
	 */
	UserProfile selectByAccountNum(String accountNum);
	
	List<UserProfile> selectGroupInfoByAccountNumList(List<String> accountList);
	
	UserProfile selectByBaseInfoId(String baseInfoId);
	
	UserProfile selectByPhoneNum(String phoneNum);
	
	List<UserProfile> selectUserByName(Map<String, Object> map);
	
	/**--新增模糊查询--**/
	public List<UserProfile> selectUserByQuery(Map<String, Object> map);

	List<GroupInfoEntity> selectByGroupId(List<String> groupIdList);
	
	long countGroupInfoEntity(Map<String, Object> map);

	int save(GroupInfoEntity groupInfoEntity);
	
	void deleteByGroupId(List<String> groupIdList);
	
	void deleteByCreatorAccount(String createrAccount);
	
	void update(GroupInfoEntity groupInfoEntity);
	
	List<SchoolConfigEntity> getByBaseId(List<String> baseIdList);

	List<SchoolConfigEntity> getAllSupportedSchools();
	
	long countSchoolConfigEntity(Map<String, Object> map);

	int save(SchoolConfigEntity schoolConfigEntity);

	void deleteByBaseId(List<String> baseIdList);

	void update(SchoolConfigEntity schoolConfigEntity);
	
	/**--模糊查询通用方法，map为传入的模糊查询条件--**/
	public List<Map<String,String>> getUserProfileSearchEntity(Map<String,Object> map);
	
	/**--查询该用户所在的所有群信息--**/
	public List<GroupInfoEntity> selectGroupByAccountNum(String accountNum);
	
	/**--根据群的ID,查询群内所有的联系人详细信息--**/
	public List<UserProfile> getUserProfileByGroupId(String groupId);
	
	/**--查询群信息--**/
	public GroupInfoEntity selectGroupByGroupId(String groupId);
	
	/**--将一个accountNum的用户加入到某个群里面--**/
	public void updateGroupAddUser(String accountNum,String groupId);
	
	/**--删除群操作--**/
	public void deleteGroupByGroupId(String groupId,String accountNum) throws Exception;
	
	/**--将一个accountNum的用户移除某个群,踢人或者退群操作--**/
	public void updateGroupRemoveUser(String accountNum,String groupId)  throws Exception;
	
	/**--创建群--*
	 * @throws Exception */
	public void insertGroup(String accountNum,GroupInfoEntity group) throws Exception;

	/**
	 * 获取用户组织中间表信息
	 * @param map
	 * @return
	 */
	List<Map<String,String>> queryUserAlumni(Map<String,String> map) ;


	/**
	 * 变更手机用户认证信息
	 * @param accountNum
	 */
	public void changeUserBaseInfo(String accountNum) ;
}

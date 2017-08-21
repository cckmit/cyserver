package com.cy.core.userProfile.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.alumni.entity.UserAlumni;
import com.cy.core.userProfile.entity.UserProfile;

public interface UserProfileMapper {
	long countUserProfile(Map<String, Object> map);

	long countUserProfileByBaseInfoId(Map<String, Object> map);

	List<UserProfile> selectUserProfileList(Map<String, Object> map);

	List<UserProfile> selectUserByName(Map<String, Object> map);

	/** --新增模糊查询-- **/
	List<UserProfile> selectUserByQuery(Map<String, Object> map);

	List<UserProfile> selectAll();

	int save(UserProfile userProfile);

	int savecheck(UserProfile userProfile);
	
	UserProfile selectById(String id);

	int updatePosition(UserProfile userProfile);
	
	void delete(List<Long> list);

	void deleteByAccountNum(String accountNum);

	void update(UserProfile userProfile);
	
	void updateGps(UserProfile userProfile);

	void updatePhoto(UserProfile userProfile);

	void updateAuthenticated(Map<String, Object> map);

	UserProfile selectByAccountNum(String accountNum);
	List<UserProfile> selectUnauthorizedClass(String accountNum);	//lixun

	List<UserProfile> selectGroupInfoByAccountNumList(List<String> accountList);

	UserProfile selectByBaseInfoId(String baseInfoId);

	UserProfile selectByPhoneNum(String phoneNum);

	UserProfile selectByPhoneNumDetail(Map<String, String> map);
	
	long countByPhoneNum(String phoneNum);

	long countByEmail(String email);

	void clearBaseInfoId(Map<String, String> map);

	void updateBase(UserProfile userProfile);

	List<UserProfile> selectNearPeople(Map<String, Object> map);

	void updatePassword(UserProfile userProfile);

	List<UserProfile> selectByAccountNums(List<Long> list);
	
	void updateGroup(UserProfile userProfile);
	
	List<UserProfile> getUserProfileByGroupId(String groupId);
	
	List<UserProfile> selectAlumni(Map<String, Object> map);

	long countByDeptFormUserProfile(Map<String, Object> map);
	
	long countByDeptFormAlumni(Map<String, Object> map);
	
	List<UserProfile> selectByDeptFormUserProfile(Map<String, Object> map);

	List<UserProfile> selectByDeptFormAlumni(Map<String, Object> map);
	
	void checkInitiate(Map<String, String> map);
	
	
	int updatealumnistatus(Map<String, Object> map);
	
	int updatealumnistatusbatch(Map<String, Object> map);
	
	/**
	 * 获取用户组织中间表信息
	 * @param map
	 * @return
     */
	List<Map<String,String>> queryUserAlumni(Map<String,String> map) ;

	/**
	 * 更新认证错误信息
	 * @param userProfile
     */
	void updateAuthErr(UserProfile userProfile);

	Map<String,String> selectBaseInfo(String accountNum) ;

	/**
	 * 通过分会的ID来获取在这个分会组织下的人
	 * @param
	 * @return
	 */
	List<UserProfile>  getUsersByAlumniId(String alumniId);
	/**
	 * 若分会的ID是1,2,3,4那么就查出来所有的人
	 * @param
	 * @return
	 */

	List<UserProfile>  getAllUsers();

	//得到分会下所有成员

//	List<UserProfile> getUserProfileByAlumniId(String alumniId);

	UserAlumni selectAlumniStatusById(String id);

	List<UserProfile> selectByPhoneNumOrEmail(Map<String, String> map);

	long countAuthenticatedUserProfile(String baseInfoIdSub);
}

package com.cy.core.userbaseinfo.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.userbaseinfo.entity.UserBaseInfo;

public interface UserBaseInfoMapper {
	long countByDeptId(Map<String, Object> map);

	List<UserBaseInfo> selectByDeptId(Map<String, Object> map);
	
	long countByDeptIdForImport(Map<String, Object> map);

	List<UserBaseInfo> selectByDeptIdForImport(Map<String, Object> map);
	
	List<UserBaseInfo> selectByDeptIdForImportAll(Map<String, Object> map);

	void delete(List<String> list);

	void deleteById(String userId);

	void save(UserBaseInfo userInfo);

	void multisave( List<UserBaseInfo> lub );	//Lixun 2017-5-11

	void update(UserBaseInfo userInfo);
	
	void updateOthers(UserBaseInfo userInfo);

	List<UserBaseInfo> selectAllByDeptId(String deptId);

	UserBaseInfo selectByUserId(String userId);

	List<UserBaseInfo> selectUserToGetTel();

	List<UserBaseInfo> selectUserByClassIdAndName(Map<String, Object> map);

	void updateUserAccountNum(Map<String, Object> map);

	UserBaseInfo selectAllProByUserId(Map<String, Object> map);

	List<UserBaseInfo> selectAllUserByClassId(Map<String, Object> map);

	List<UserBaseInfo> selectCard(Map<String, Object> map);

	List<UserBaseInfo> selectByUserName(Map<String, Object> map);

	long selectUserInClass(Map<String, Object> map);

	UserBaseInfo selectUserInfo(Map<String, Object> map);

	int updateTelId(Map<String, Object> map);

	List<UserBaseInfo> selectAll();

	List<UserBaseInfo> selectListFor(Map<String, Object> map);

	long countFor(Map<String, Object> map);
	
	List<UserBaseInfo> selectByUserIds(List<String> list);
	
	void updateImport(UserBaseInfo userBaseInfo);
	
	List<UserBaseInfo> selectByDeptIdAll(Map<String, Object> map);
	
	void deleteByDeptIdAll(Map<String, Object> map);
	
	List<UserBaseInfo> selectByDeptIdForExport(Map<String, Object> map);
	
	List<UserBaseInfo> selectByMobile();
	
	void updateMobile(UserBaseInfo userBaseInfo);


	/**
	 * 设置班级管理员
	 * @param map
     */
	void updateClassAdmin(Map<String,String> map);


	/**
	 * 修改数据编号
	 * @param map
	 */
	void updateUserId(Map<String, String> map) ;
}

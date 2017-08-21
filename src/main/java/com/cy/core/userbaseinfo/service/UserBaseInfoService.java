package com.cy.core.userbaseinfo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.cy.base.entity.DataGrid;
import com.cy.core.user.entity.User;
import com.cy.core.userbaseinfo.entity.UserBaseInfo;

public interface UserBaseInfoService {

	DataGrid<UserBaseInfo> selectByDeptId(Map<String, Object> map);

	DataGrid<UserBaseInfo> selectByDeptIdForImport(Map<String, Object> map);

	void delete(String ids);

	void save(UserBaseInfo UserBaseInfo, User user,int isInput);

	void update(UserBaseInfo UserBaseInfo, User user,int isInput);

	UserBaseInfo selectByUserId(String userId);

	String importData(String url, User user);

	List<UserBaseInfo> selectAllUserList();

	List<UserBaseInfo> selectUserByClassIdAndName(String userName, String classId);

	void updateUserAccountNum(UserBaseInfo UserBaseInfo);

	UserBaseInfo selectAllProByUserId(String userId);

	List<UserBaseInfo> selectUserByClassId(String classId);

	UserBaseInfo selectUserBaseInfoByGmidAndName(Map<String, Object> map);

	int updateUserTelId(Map<String, Object> map, UserBaseInfo UserBaseInfo);

	boolean selectUserInClass(String classId, List<String> list);

	List<UserBaseInfo> selectByUserName(String userName, String deptId);

	List<UserBaseInfo> selectCard(List<String> list);

	DataGrid<UserBaseInfo> dataGridFor(Map<String, Object> map);

	void deleteAll(Map<String, Object> map);

	String export(Map<String, Object> map) throws IOException;

	String importDataNew(String url, User user );

	String importDataBeforeThread( String url, User user );	//Lixun 2017-5-11

	/**
	 * 设置班级管理员
	 * @param map
	 */
	void updateClassAdmin(Map<String,String> map);

}

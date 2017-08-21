package com.cy.core.userinfo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.alumni.entity.UserAlumni;
import com.cy.core.dataMining.entity.DataMining;
import com.cy.core.user.entity.User;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.entity.UserInfo;

public interface UserInfoService {
	DataGrid<UserInfo> selectByDeptId(Map<String, Object> map);

	DataGrid<UserProfile> selectByDeptIdFormUserProfile(Map<String, Object> map);
	
	DataGrid<UserProfile> selectByDeptFormAlumni(Map<String, Object> map);

	void checkInitiate(Map<String, String> map);

	DataGrid<UserInfo> selectByDeptIdForAlumni(Map<String, Object> map);
	
	List<UserInfo> selectUserInfoByName(String name);

	boolean delete(String ids);

	void save(UserInfo userInfo, User user,int isInput);

	void saveFromBase(Map<String, Object> map);

	void update(UserInfo userInfo, User user,int isInput);
	
	void update(UserInfo info);

	void updateIdea(UserInfo userInfo);

	UserInfo selectByUserId(String userId);

	List<UserInfo> getUserInfoByUserIdForAlumni(String userId);

	String importData(String url, User user);

	List<UserInfo> selectAllUserList();
	
	/**--查询所有有手机号的用户并且分页--**/
	public List<UserInfo> selectUserToGetTelPage(Map<String, Object> map);

	List<UserInfo> selectUserByClassIdAndName(String userName, String classId);

	void updateUserAccountNum(UserInfo userInfo);

	UserInfo selectAllProByUserId(String userId);

	List<UserInfo> selectUserByClassId(String classId);

	UserInfo selectUserInfoByGmidAndName(Map<String, Object> map);

	int updateUserTelId(Map<String, Object> map, UserInfo userinfo);

	boolean selectUserInClass(String classId, List<String> list);

	List<UserInfo> selectByUserName(String userName);

	List<UserInfo> selectCard(List<String> list);

	DataGrid<UserInfo> dataGridFor(Map<String, Object> map);

	boolean deleteAll(Map<String, Object> map);

	String export(Map<String, Object> map) throws IOException;

	List<UserInfo> selectByAccountNum(Map<String, Object> map);

	/**
	 * 轮询，更新用户手机归属地
	 */
	void updateMobileLocal();
	
	void sendBirthdaySms();
	
	List<UserInfo> selectByAccountNum2FullName(String accountNum);
	
	void updateFromUserProfile();
	
	void updateTwoWay();

	/**
	 * 设置班级管理员
	 * @param map
	 */
	void updateClassAdmin(Map<String,String> map);

	Map<String, Long> countEveryThing();

	/**
	 * 通过userId获得潜在用户信息(手机号)
	 * @auther jiangling
	 * @date 2016-08-05
	 * @param userId
	 * @return
     */
	List<DataMining> getPotentialUser(String userId);

	/**
	 * 取消用户的认证
	 */
	void cancleAuth(String userId, Message message);

	/**
	 * 新增userInfo接口
	 */
	void addUserInfo(Message message,String content);

	/**
	 * 校友数据汇总
	 */
	List<Map<String,String>> countAnalysisUserInfo(Map<String,String> map);

	/**
	 * 校友统计分析数据(分页)
	 */
	public DataGrid<Map<String,String>> countAnalysisUserInfoDataGrid(Map<String, String> map) ;
	/**
	 * 校友数据汇总
	 */
	List<Map<String, String>> userInfoSummary(Map<String, String> map);
	Map<String, String> userInfoSummaryAnalysis(Map<String, String> map) ;

	/**
	 * 获取院系用户报表图
	 */
	List<Map<String, Object>> chartOfDeptUser() ;
	/**
	 * 获取被认证校友报表图
	 */
	List<Map<String, Object>> chartOfRegistered() ;

	/**
	 * 获取挖掘校友报表图
	 */
	public List<Map<String, String>> chartOfMining() ;


	/**
	 * 获取省份地区校友分布
	 */
	List<Map<String,String>> findUserInfoMap(Map<String,String> map) ;

	/**
	 * 获取省份校友会分布
	 */
	List<Map<String,String>> findAlumniCountMap(Map<String,String> map) ;

	/**
	 * 同级生
	 * @param message
	 * @param content
     */
	void findGradeMates(Message message, String content);

	UserAlumni selectAlumniStatusById(String id);

	boolean checkAccountInClass(UserInfo userInfo);

	/**
	 * 查询最新认证校友接口
	 * @param message
	 * @param content
	 */
	void showNewsAuth(Message message, String content);

	/**
	 * 查询新加入校友列表
	 * @param content
	 * @param message
	 */
	void userCheckList(Message message, String content);

//	List<Map<String,String>> findUserInfoMapChatUser(Map<String,String> map);
//	List<Map<String,String>> finduserInfoSummary(Map<String,String> map);

	/**
	 * 获取省份地区校友会分布
	 * @param message
	 * @param content
	 */
	void chartOfAlumniCountMap(Message message, String content);


	/**
	 * 方法 chartOfUserInfoMap的功能描述：获取省份地区校友分布接口
	 * @createAuthor niu
	 * @createDate 2017-04-14 13:47:04
	 * @param
	 * @return
	 * @throw
	 *
	 */
	void chartOfUserInfoMap(Message message, String content);


	Map<String,Object> usingStatistics();

	/**
	 * 查询同名列表
	 * @param map
	 * @return
	 */
	DataGrid<UserInfo> selectSameNameList(Map<String, Object> map);


	/**
	 * 校友数据迁移到新班级
	 * @param map
	 * @return
	 */
	Map<String, Object> moveData(Map<String, String> map);

	/**
	 * 一键认证
	 * @param userId
	 * @return
	 */
	Message oneKeyAuth(String userId);

	/**
	 * 一键认证
	 * @param userId
	 * @return
	 */
	Message oneKeyAuth(String userId,String phoneNum);

	/**
	 * 獲取同班同學
	 * @param userId
	 * @return
	 */
	List<Map<String, String>> findClassMates(String userId);


	List<UserInfo> selectUserByTelePhone(String phoneNumber);

	int updateProfileStatus(Map<String, Object> map);

	int updateProfileStatusBatch(Map<String, Object> map);
	 
}

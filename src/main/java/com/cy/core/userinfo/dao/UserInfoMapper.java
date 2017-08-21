package com.cy.core.userinfo.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.userinfo.entity.UserInfo;


public interface UserInfoMapper {
	long countByDeptId(Map<String, Object> map);

	List<UserInfo> selectByDeptId(Map<String, Object> map);

	/**
	 * 查询满足条件的校友数据总数
	 * @param map
	 * @return
     */
	long countByDeptIdNew(Map<String, Object> map);

	/**
	 * 查询满足条件的校友数据
	 * @param map
	 * @return
     */
	List<UserInfo> selectByDeptIdNew(Map<String, Object> map);

	long countByDeptIdForAlumni(Map<String, Object> map);

	List<UserInfo> selectByDeptIdForAlumni(Map<String, Object> map);
	
	List<UserInfo> selectUserInfoByName(String name);

	UserInfo selectUserInfoByUserId(String userId);

	void delete(List<String> list);

	void deleteById(String userId);

	void save(UserInfo userInfo);

	void multisave( List<UserInfo> lui );	//Lixun 2017-5-11

	void update(UserInfo userInfo);

	void updateOthers(UserInfo userInfo);

	void updateIdea(UserInfo userInfo);

	/** --根据accountNum更新-- **/
	void updateByAccountNum(UserInfo userInfo);

	List<UserInfo> selectAllByDeptId(String deptId);

	UserInfo selectByUserId(String userId);

	UserInfo selectByUserIdLess(String userId);

	List<UserInfo> selectUserToGetTel();

	/** --查询所有有手机号的用户集合,分页-- **/
	List<UserInfo> selectUserToGetTelPage(Map<String, Object> map);

	/** --查询所有有手机号的用户数量-- **/
	long selectUserToGetTelCount(Map<String, Object> map);

	List<UserInfo> selectUserByClassIdAndName(Map<String, Object> map);

	List<UserInfo> selectUserByClassId(String classId);

	//通过用户的ID来查出用户所在班级内的所有的人 郭亚斌 2016-8-22
	List<UserInfo> selectUserByUserId(String userId);


	void updateUserAccountNum(Map<String, Object> map);

	UserInfo selectAllProByUserId(Map<String, Object> map);

	List<UserInfo> selectAllUserByClassId(Map<String, Object> map);

	List<UserInfo> selectCard(Map<String, Object> map);

	List<UserInfo> selectByUserName(Map<String, Object> map);

	long selectUserInClass(Map<String, Object> map);

	UserInfo selectUserInfo(Map<String, Object> map);

	int updateTelId(Map<String, Object> map);

	List<UserInfo> selectAll();

	List<UserInfo> selectListFor(Map<String, Object> map);

	long countFor(Map<String, Object> map);

	void deleteByDeptIdAll(Map<String, Object> map);

	List<UserInfo> selectByDeptIdForExport(Map<String, Object> map);

	List<UserInfo> getUserInfoByUserIdForAlumni(List<String> list);

	List<UserInfo> selectByAccountNum(Map<String, Object> map);

	List<UserInfo> selectByAccountNums(List<String> list);

	List<UserInfo> selectByAccountNum2FullName(String accountNum);

	/**
	 * 查询手机号码不为空and归属地为空的学生记录
	 * 
	 * @return
	 */
	List<UserInfo> selectMobileLocalIsNull();

	/**
	 * 更新手机号码归属地
	 * 
	 * @param userInfo
	 */
	void updateMobileLocal(UserInfo userInfo);

	/**
	 * 
	 * @param userInfo
	 */
	void updateBase(UserInfo userInfo);

	/**
	 * 通过多个用户ID查询多个用户
	 * 
	 * @param list
	 * @return
	 */
	List<UserInfo> selectByIds(List<String> list);

	List<UserInfo> selectByDeptIdAll(Map<String, Object> map);

	/**
	 * 查询今天过生日的同学
	 * 
	 * @return
	 */
	List<UserInfo> selectBirthday();

	List<UserInfo> selectByName(String name);

	/**
	 * 查询校友
	 * 
	 * @param map
	 * @return
	 */
	List<UserInfo> selectAlumni(Map<String, Object> map);

	/**
	 * 更新个人头像
	 * 
	 * @param map
	 */
	void updatePhoto(Map<String, Object> map);

	/**
	 * 认证通过后信息同步
	 * 
	 */
	void updateAuthen2User(UserInfo userInfo);

	void updateAccountNum(UserInfo userInfo);

	void updateFromUserProfile(UserInfo userInfo);

	List<UserInfo> selectUserByClassIdLess(String classId);
	
	List<UserInfo> selectByMobile();
	
	void updateMobile(UserInfo userInfo);

	/**
	 * 查询已注册用户、已取得联系的以及未取得联系的用户
	 */
	//已注册用户数量
	long countByAccountNum();
	//已取得联系用户数量
	long countByCheckFlag();
	//未取得联系的用户数量
	long countByNoCheckFlag();

	/**
	 * 设置班级管理员
	 * @param map
	 */
	void updateClassAdmin(Map<String,String> map);

	/*lixun 查询组织mainType*/
	long selectMainInAlumni( long id );

	//QPF 16-08-04

	/**
	 * 通过学号认证
	 */
	List<UserInfo> selectUserByStudentNum(String studentnumber);

	/**
	 * 通过身份证号认证
	 */
	List<UserInfo> selectUserByCard(String card);

	//16-08-05

	/**
	 * 通过邀请码认证
	 */
	List<UserInfo> selectUserByInviteCode(String inviteCode);

	//16-08-06

	/**
	 * 查询账号
	 */
	String selectAccountNumByUserId(String userId);

	/**
	 * 删除user_info与userbase_info下的accountNum
	 */
	void deleteAccountNumByUserId(String userId);
	void deleteAccountNumByUserIdBase(String userId);

	/**
	 * 获取班级全称
	 */
	String selectClassNameByUserId(String userId);

	/**
	 * 在班级群组删除该成员
	 */
	void deleteGroupUser(Map<String, Object> map);

	/**
	 * 通过用户Id获取用户的学习经历
	 */
	List<Map<String, Object>> getLearningExp(String accountNum);

	/**
	 * 随机抽取3名自己同班的同学
	 */
	List<String> getClassMates(String strStudyPathId);

	/**
	 * 随机获取6名虚拟名称
	 */
	List<String> findDummyName6(Map<String ,Object> map);


	//QPF END

	String getUserInfosByClassId(String classId) ;

	/**
	 * 校友数据汇总
	 */
	Long countUserInfo(Map<String,String> map) ;

	/**
	 * 统计分析校友数据
	 */
	List<Map<String,String>> countAnalysisUserInfo(Map<String,String> map) ;
	/**
	 * 统计分析校友数据
	 */
	Long countByCountAnalysisUserInfo(Map<String,String> map) ;

	/**
	 * 获取省份地区校友分布
	 */
	List<Map<String,String>> findUserInfoMap(Map<String,String> map) ;
	/**
	 * 获取省份校友会分布
	 */
	List<Map<String,String>> findAlumniCountMap(Map<String,String> map) ;

	List<UserInfo> selectClassmates(String userId);

	List<Map<String, String>> selectFriendsStatusInClassmates(Map<String, Object> map);

	List<Map<String, String>> findGradeMates(Map<String, Object> map);

	List<String> findAccountByPinyin(String pinyin);

	long countAccountInClass(UserInfo userInfo);

	/**
	 * 查询新注册校友列表
	 * @param map
	 * @return
	 */
	List<UserInfo> selectAuthMember(Map<String, Object> map);
	long countAuthMember(Map<String, Object> map);

	/**
	 * 查询用户申请加入的班级
	 * @param map
	 * @return
	 */
	List<Map<String, String>> userCheckList(Map<String, Object> map);
	long userCheckCount(Map<String, Object> map);


	/**
	 * 方法 countUserInfoRegister 的功能描述：挖掘用户数
	 * @createAuthor niu
	 * @createDate 2017-04-11 19:48:05
	 * @param
	 * @return
	 * @throw
	 *
	 */
	long countUserInfoMining();


	/**
	 * 方法 countUserInfoRegister 的功能描述：挖掘注册用户数
	 * @createAuthor niu
	 * @createDate 2017-04-11 19:48:05
	 * @param
	 * @return
	 * @throw
	 *
	 */
	long countUserInfoRegister();

	/**
	 * 方法 countUserInfoAuthenticate 的功能描述：挖掘认证用户数
	 * @createAuthor niu
	 * @createDate 2017-04-11 19:48:05
	 * @param
	 * @return
	 * @throw
	 *
	 */
	long countUserInfoAuthenticate();

	/**
	 * 修改数据编号
	 * @param map
     */
	void updateUserId(Map<String, String> map) ;


	/**
	 * 查询同名人
	 * @param map
	 * @return
	 */
	List<UserInfo> selectSameNameList(Map<String, Object> map);

	long countSameName(Map<String, Object> map);
	//根据手机号查询用户并做一些提示和警告
	List<UserInfo>  selectUserByTelePhone(String phoneNumber);
}

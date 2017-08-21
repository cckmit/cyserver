package com.cy.util;

import com.cy.common.utils.SpringContextHolder;
import com.cy.core.alumni.dao.AlumniMapper;
import com.cy.core.user.dao.UserMapper;
import com.cy.core.user.entity.User;
import com.cy.core.user.entity.UserRole;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.system.SpringManager;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户工具类
 * @author LiuZhen
 * @version 2016-7-14
 */
@Component("userUtils")
public class UserUtils {

	private static UserMapper userMapper = SpringContextHolder.getBean("userMapper");			// 系统用户
	private static UserProfileMapper userProfileMapper = SpringContextHolder.getBean("userProfileMapper"); // APP用户
	private static AlumniMapper alumniMapper = SpringContextHolder.getBean("alumniMapper") ;	// 校友组织


	public static void getUserRoles() {
		List<UserRole> userRoles = userMapper.getUserRoleIdsByUserId(36) ;
		System.out.println(userRoles);
	}

//	public static void main(String[] args ) {
//		List<UserRole> userRoles = userMapper.getUserRoleIdsByUserId(36) ;
//		System.out.println(userRoles);
//	}

	/*********************用户工具方法-系统管理员****************************/
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id){
		User user = userMapper.selectByUserId(id) ;
		return user;
	}

	/**
	 * 更新用户
	 * @param u
	 * @return 取不到返回null
	 */
	public static void update(User u){
		userMapper.update(u);
	}
	
	/**
	 * 根据登录名获取用户
	 * @param userAccount
	 * @return 取不到返回null
	 */
	public static User getByUserAccount(String userAccount){
		User user = userMapper.selectByUserAccount(userAccount) ;
		return user;
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static User getUser(){
		Object obj =  null ;
		obj = ActionContext.getContext() != null && ActionContext.getContext().getSession() != null ? ActionContext.getContext().getSession().get("user") : null;
		if(obj != null) {
			User user = (User) obj ;
			return user ;
		}

		// 如果没有登录，则返回实例化空的User对象。
		return null;
//		return new User();
	}

	/**
	 * 获取当前组织机构下级所有组织结构编号
	 * @param deptId
	 * @return
     */
	public static String getChildDeptIdsOfDeptId(String deptId) {
		String childDeptIds = null ;
		Map<String,Object> map = Maps.newHashMap() ;
		map.put("alumniParentId",deptId) ;
		childDeptIds = alumniMapper.queryAlumniIds(map) ;
		return childDeptIds ;
	}


	/*********************用户工具方法-APP 用户管理员****************************/

	/**
	 * 根据用户编号获取用户信息
	 * @param accountNum
	 * @return 取不到返回null
	 */
	public static UserProfile getUserProfileByAccountNum(String accountNum){
		UserProfile user = userProfileMapper.selectByAccountNum(accountNum) ;
		return user;
	}

	/**
	 * 判断用户编号是否存在
	 * @param accountNum
	 * @return 取不到返回null
	 */
	public static boolean isExistsByUserProfile(String accountNum){
		boolean success = false ;
		UserProfile user = userProfileMapper.selectByAccountNum(accountNum) ;
		if(user != null && StringUtils.isNotBlank(user.getAccountNum())) {
			success = true ;
		}
		return success;
	}

	/**
	 * 判断校友是否在APP中注册账号并认证
	 * @param userId
	 * @return 取不到返回null
	 */
	public static PairUtil<Boolean,String> isRegisterAndAuthenticationOfUserBaseInfo(String userId){
		boolean success = false ;
		String accountNum = null ;
		if(StringUtils.isNotBlank(userId)) {
			String userBaseInfoId = userId ;
			Map<String,Object> map = new HashMap<String,Object>() ;
			map.put("userBaseInfoId",userId) ;
			List<UserProfile> userProfiles = userProfileMapper.selectUserByQuery(map) ;
			if (userProfiles != null && !userProfiles.isEmpty()) {
				success = true ;
				accountNum = userProfiles.get(0).getAccountNum() ;
			}
		}
		return new PairUtil<Boolean,String>(success,accountNum);
	}

	/**
	 * 变更手机用户认证信息
	 * @param accountNum
	 */
	public static void changeUserBaseInfo(String accountNum) {
		if(StringUtils.isNotBlank(accountNum)) {
			Map<String, String> map = userProfileMapper.selectBaseInfo(accountNum);
			if(map == null) {
				map = Maps.newHashMap() ;
			}
			map.put("accountNum",accountNum) ;
			userProfileMapper.clearBaseInfoId(map);
		}
	}
}

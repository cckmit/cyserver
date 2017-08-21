package com.cy.mobileInterface.authenticated.service;

import com.cy.base.entity.Message;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.entity.UserInfo;

import java.util.Map;

public interface AuthenticatedService {
	/**
	 * 同班同学姓名认证
	 * @param message
	 * @param content
     */
	void updateAuthenticated(Message message,String content);

	/**
	 * 通过学号认证
	 * @param message
	 * @param content
     */
	void updateAuthenticatedByStuNum(Message message, String content);

	/**
	 * 通过身份证认证
	 * @param message
	 * @param content
	 */
	void updateAuthenticatedByCard(Message message, String content);

	/**
	 * 通过邀请码认证
	 * @param message
	 * @param content
     */
	void updateAuthenticatedByInviteCode(Message message, String content);

	/**
	 * 手机端取消认证接口
	 * @param message
	 * @param content
	 */
	void cancleUserAuthentication(Message message, String content);

	/**
	 * 获取认证所需的姓名
	 * @param message
	 * @param content
	 */
	void getAuthenticatedName(Message message, String content);

	/**
	 * 一键入会认证后所要做的操作
	 */
	void autoJoinAlumni(String userId, String accountNum);


	/**
	 * 认证成功后所执行的操作
	 * @param userProfile
	 * @param userInfo
	 * @return
	 */
	Map<String,Object> authSuccess(UserProfile userProfile, UserInfo userInfo) ;
}

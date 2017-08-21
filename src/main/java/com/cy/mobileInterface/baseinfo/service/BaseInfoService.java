package com.cy.mobileInterface.baseinfo.service;

import java.io.File;

import com.cy.base.entity.Message;

public interface BaseInfoService {
	/**
	 * 2号接口根据姓名获取该姓名对应的学生基础ID和班级
	 * 
	 * @param message
	 * @param content
	 */
	void getBaseInfoIdByName(Message message, String content);

	/**
	 * 个人头像上传
	 * 
	 * @param message
	 * @param content
	 * @param upload
	 * @param uploadFileName
	 */
	void uploadPhoto(Message message, String content, File[] upload, String[] uploadFileName);

	/**
	 * 获取同班所有同学名单
	 * 
	 * @param message
	 * @param content
	 */
	void getClassmates(Message message, String content);

	/**
	 * 查询对应姓名的详细信息
	 * 
	 * @param content
	 * @param message
	 */
	void searchUserInfo(Message message, String content);

	/**
	 * 搜索好友(满足条件)
	 * @param message
	 * @param content
	 */
	void searchUserProfile(Message message, String content) ;

	/**
	 * 手机登陆，返回个人详细信息
	 * 
	 * @param message
	 * @param content
	 */
	void selectAppLogin(Message message, String content);

	/**
	 * 获取好友信息
	 * 
	 * @param message
	 * @param content
	 */
	void getFriendProfile(Message message, String content);

	/**
	 * 5(23)号接口修改个人信息
	 * 
	 * @param message
	 * @param content
	 */
	void updateProfile(Message message, String content);


	/**
	 * 201号接口改绑手机号
	 * @param message
	 * @param content
	 */
	void resetUserPhoneNum(Message message, String content);

	/**
	 * 101号接口查询用户的学习路径
	 *
	 * @param message
	 * @param content
	 */
	void getClasses(Message message, String content);


	void getUnauthorizedClass( Message message, String content );

	/**
	 * 查询班级所有成员列表
	 */
	void findClassmates(Message message, String content);

	void selectAppLogin(Message message, String content, String token);
}

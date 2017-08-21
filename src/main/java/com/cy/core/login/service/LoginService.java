/**
 * 
 */
package com.cy.core.login.service;

import com.cy.base.entity.Message;
import com.cy.core.user.entity.User;

import java.util.Map;

public interface LoginService {

	/**
	 * 登陆后发送手机验证码
	 * @param message
	 * @param user
	 */
	void sendSMSCodeForWebLogin(Message message, User user);

	/**
	 * 通过手机号发送手机验证码
	 * @param message
	 * @param telephone
	 */
	void getSMSCodeByTelephone(Message message, String telephone);

	/**
	 * 校验手机验证码是否正确
	 * @param message
	 * @param phoneNumber
	 * @param smscode
     * @return
     */
	boolean checkSMSCode(Message message, String phoneNumber, String smscode);


	/**
	 * 登录手机信息提醒
	 * @param message
	 * @param user
	 */
	void sendSMSRemindForWebLogin(Message message, User user, String userAgent);

	/**
	 * 查询待办事项数量
	 */
	Map<String, Object> toDoList(User user);
}

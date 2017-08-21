package com.cy.core.email.dao;

import java.util.List;
import java.util.Map;

import com.cy.core.email.entity.Email;
import com.cy.core.email.entity.EmailRecipient;

public interface EmailMapper 
{
	/**
	 * 存储
	 * 
	 * @param Email email 
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean save(Email email);
	
	
	/**
	 * 更新
	 * 
	 * @param Email email 
	 * @return true，成功；false，失败；
	 * 
	 */
	boolean update(Email email);
	
	/**
	 * 获取消息总条数
	 * 
	 * @param Map<String, Object> map
	 * @return long
	 */
	long count(Map<String, Object> map);
	
	/**
	 * 获取列表
	 * 
	 * @param Map<String, Object> map
	 * @return List<Email>
	 */
	List<Email> list(Map<String, Object> map);
	
	/**
	 * 删除
	 * 
	 * @param List<Long> list
	 */
	void delete(List<Long> list);
	
	
	/**
	 * 获取详情
	 * 
	 * @param map
	 * @return List<Email>
	 */
	Email selectById(long id);
	
	/**
	 * 获取未发送邮件列表
	 * 
	 * @return List<Email>
	 */
	List<Email> selectNotSendMail();
	
	/**
	 * 获取所有有邮箱的用户信息
	 * 
	 * @return List<Map<String, String>>
	 */
	List<Map<String, String>> selectAllUserList();
	
	
	/**
	 * 获取所有有邮箱的用户信息
	 * 
	 * @param  map
	 * @return List<EmailRecipient>
	 */
	List<EmailRecipient> selectAllUsersList(Map<String, Object> map);
	
	
	/**
	 * 获取所有有邮箱的用户信息总条数
	 * 
	 * @param Map<String, Object> map
	 * @return long
	 */
	long countForUsersList(Map<String, Object> map);
	
	
	/**
	 * 获取个人信息
	 * 
	 * @return Map<String, String>
	 */
	Map<String, String> selectPersonalInfo(Email email);
	
}

package com.cy.core.email.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.DataGrid;
import com.cy.core.email.dao.EmailMapper;
import com.cy.core.email.entity.Email;
import com.cy.core.email.entity.EmailRecipient;
import com.cy.system.ClassPathResource;
import com.cy.system.Global;
import com.cy.util.mail.SimpleMail;
import com.cy.util.mail.SimpleMailSender;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailMapper emailMapper;

	/**
	 * 存储
	 * 
	 * @param Email
	 *            email
	 * @return true，成功；false，失败；
	 * 
	 */
	public void save(Email email) {
		try {
			SimpleMailSender mailSender = null;
			email.setFromPassword((StringUtils.isBlank(email.getFromPassword()) ? Global.email_password:email.getFromPassword())) ;
			email.setFromAddress(StringUtils.isBlank(email.getFromAddress()) ? Global.email_account:email.getFromAddress()) ;
			if (Global.smtpHost.length() != 0 && Global.smtpPort.length() != 0) {
				mailSender = new SimpleMailSender(email.getFromAddress(), email.getFromPassword(), Global.smtpHost, Global.smtpPort, "smtps");
			} else {
				mailSender = new SimpleMailSender(email.getFromAddress(), email.getFromPassword());
			}
			List<String> list = new ArrayList<String>();
			// 号码去掉重复的
			Set<String> set = new HashSet<String>();
			for (String to : email.getToAddress().split(",")) {
				if (ClassPathResource.isEmail(to)) {
					if (!set.contains(to)) {
						list.add(to);
					}
					set.add(to);
				}
			}
			SimpleMail mail = new SimpleMail();
			mail.setSubject(email.getEmailSubject());
			mail.setContent(email.getEmailText());
			String files = "";
			List<String> fileList = new ArrayList<String>();
			if (email.getFj() != null && email.getFj().length > 0) {
				for (String fj : email.getFj()) {
					fileList.add(fj);
					files += fj + ',';
				}
			}
			if (files.length() > 0) {
				files = files.substring(0, files.length() - 1);
				email.setImmediate(files);
			}
			List<String> cclist = new ArrayList<String>();
			if (email.getCcAddress() != null && email.getCcAddress().length() > 0) {
				for (String cc : email.getCcAddress().split(",")) {
					cclist.add(cc);
				}
			}
			List<String> bcclist = new ArrayList<String>();
			if (email.getBccAddress() != null && email.getBccAddress().length() > 0) {
				for (String cc : email.getBccAddress().split(",")) {
					bcclist.add(cc);
				}
			}
			mailSender.send(list, cclist, bcclist, mail, fileList);
			email.setSent(1);

			emailMapper.save(email);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 更新
	 * 
	 * @param Email
	 *            email
	 * @return true，成功；false，失败；
	 * 
	 */
	public boolean update(Email email) {
		// TODO Auto-generated method stub
		return emailMapper.update(email);
	}

	/**
	 * 获取消息总条数
	 * 
	 * @param Map
	 *            <String, Object> map
	 * @return long
	 */
	public long count(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return emailMapper.count(map);
	}

	/**
	 * 获取列表
	 * 
	 * @param Map
	 *            <String, Object> map
	 * @return List<Email>
	 */
	public DataGrid<Email> dataGrid(Map<String, Object> map) {
		DataGrid<Email> dataGrid = new DataGrid<Email>();
		long total = emailMapper.count(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<Email> list = emailMapper.list(map);
		dataGrid.setRows(list);
		return dataGrid;
	}

	/**
	 * 删除
	 * 
	 * @param String
	 *            ids
	 */
	public void delete(String ids) {
		String[] array = ids.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String id : array) {
			list.add(Long.parseLong(id));
		}

		emailMapper.delete(list);
	}

	/**
	 * 获取详情
	 * 
	 * @param map
	 * @return List<Email>
	 */
	public Email selectById(long id) {
		// TODO Auto-generated method stub

		Email email = emailMapper.selectById(id);

		String separator = ",";

		String[] toAddress = null;

		if (email.getToAddress() != null && email.getToAddress().length() > 0) {
			if (email.getToAddress().indexOf(separator) != -1) {
				toAddress = email.getToAddress().split(separator);
			} else {
				toAddress = new String[1];
				toAddress[0] = email.getToAddress();

			}
		}

		email.setToAddressStr(replaceAllDoubleQuotesToSingleQuotes(JSON.toJSONString(toAddress)));

		String[] emailTemplateId = new String[1];
		emailTemplateId[0] = String.valueOf(email.getEmailTemplateId());

		email.setEmailTemplateIdStr(replaceAllDoubleQuotesToSingleQuotes(JSON.toJSONString(emailTemplateId)));

		return email;
	}

	/**
	 * 获取未发送邮件列表
	 * 
	 * @return List<Email>
	 */
	public List<Email> selectNotSendMail() {
		// TODO Auto-generated method stub
		return emailMapper.selectNotSendMail();
	}

	/**
	 * 获取所有有邮箱的用户信息总条数
	 * 
	 * @param Map
	 *            <String, Object> map
	 * @return long
	 */
	public long countForUsersList(Map<String, Object> map) {

		return emailMapper.countForUsersList(map);
	}

	/**
	 * 获取所有有邮箱的用户信息
	 * 
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> selectAllUserList() {
		// TODO Auto-generated method stub
		return emailMapper.selectAllUserList();
	}

	/**
	 * 获取所有有邮箱的用户信息
	 * 
	 * @param map
	 * @return DataGrid<EmailRecipient>
	 */
	public DataGrid<EmailRecipient> selectAllUsersList(Map<String, Object> map) {

		DataGrid<EmailRecipient> dataGrid = new DataGrid<EmailRecipient>();
		long total = emailMapper.countForUsersList(map);
		dataGrid.setTotal(total);
		int start = ((Integer) map.get("page") - 1) * (Integer) map.get("rows");
		map.put("start", start);
		List<EmailRecipient> list = emailMapper.selectAllUsersList(map);
		dataGrid.setRows(list);
		return dataGrid;

		// return emailMapper.selectAllUsersList(map);
	}

	private String replaceAllDoubleQuotesToSingleQuotes(String str) {
		if (str != null && str.indexOf("\"") != -1) {
			str = str.replaceAll("\"", "'").replaceAll(" ", "");
		}

		return str;
	}

	/**
	 * 获取个人信息
	 * 
	 * @param Email
	 *            email
	 * @return Map<String, String>
	 */
	public Map<String, String> selectPersonalInfo(Email email) {
		// TODO Auto-generated method stub
		return emailMapper.selectPersonalInfo(email);
	}

}

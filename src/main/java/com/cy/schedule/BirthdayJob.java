package com.cy.schedule;

import org.apache.log4j.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cy.core.userinfo.service.UserInfoService;

public class BirthdayJob extends QuartzJobBean {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BirthdayJob.class);

	private UserInfoService userInfoService;

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		// 发送生日
		try {
			userInfoService.sendBirthdaySms();
		} catch (Exception e) {
			logger.error(e);
		}
	}

}

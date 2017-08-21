package com.cy.schedule;

import org.apache.log4j.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cy.core.userinfo.service.UserInfoService;

public class UserInfoSynchronousJob extends QuartzJobBean {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserInfoSynchronousJob.class);

	private UserInfoService userInfoService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			userInfoService.updateFromUserProfile();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

}

package com.cy.schedule;

import org.apache.log4j.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cy.core.logger.service.LoggerService;
import com.cy.core.userinfo.service.UserInfoService;

public class MobileLocalJob extends QuartzJobBean {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MobileLocalJob.class);

	private UserInfoService userInfoService;

	private LoggerService loggerService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			userInfoService.updateMobileLocal();
			loggerService.sendLogger2Mail();
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

	public LoggerService getLoggerService() {
		return loggerService;
	}

	public void setLoggerService(LoggerService loggerService) {
		this.loggerService = loggerService;
	}

}

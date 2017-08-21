package com.cy.schedule;

import com.cy.common.utils.CacheUtils;
import com.cy.core.analysis.action.AnalysisAction;
import com.cy.core.analysis.utils.AnalysisUtils;
import com.cy.core.logger.service.LoggerService;
import com.cy.core.userinfo.service.UserInfoService;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Map;

/**
 * 统计缓存更新定时器
 */
public class AnalysisJob extends QuartzJobBean {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AnalysisJob.class);

	private UserInfoService userInfoService;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			AnalysisUtils.init();

			logger.info("****************************** 初始化统计分析缓存定时器 start****************************************");
			CacheUtils.remove(AnalysisAction.ANALYSIS_CACHE_USERINFOSUMMARY,"all");
			CacheUtils.remove(AnalysisAction.ANALYSIS_CACHE_COUNTANALYSISUSERINFODATAGRID);
			CacheUtils.remove(AnalysisAction.ANALYSIS_CACHE_CHARTOFDEPTUSERCHART);
			CacheUtils.remove(AnalysisAction.ANALYSIS_CACHE_CHARTOFREGISTERED);
			CacheUtils.remove(AnalysisAction.ANALYSIS_CACHE_CHARTOFMINING);
			CacheUtils.remove(AnalysisAction.ANALYSIS_CACHE_CHARTOFUSERINFOMAP,"all");

			//统计校友数据(总)
			userInfoService.userInfoSummary(null);
			//统计分析校友数据
			userInfoService.countAnalysisUserInfo(null);

			Map<String,String> map = Maps.newHashMap() ;
			map.put("groupType","1") ;
			map.put("page","1") ;
			map.put("rows","10") ;
			CacheUtils.put(AnalysisAction.ANALYSIS_CACHE_COUNTANALYSISUSERINFODATAGRID,userInfoService.countAnalysisUserInfoDataGrid(map));

			//获取院系校友报表图
			userInfoService.chartOfDeptUser() ;

			//获取被认证校友报表图
			userInfoService.chartOfRegistered() ;

			//获取挖掘校友报表图
			userInfoService.chartOfMining() ;

			//获取省份地区校友分布
			userInfoService.findUserInfoMap(null) ;

			//获取省份地区校友会成员分布
			userInfoService.findAlumniCountMap(null) ;
			logger.info("****************************** 初始化统计分析缓存定时器  end ****************************************");



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

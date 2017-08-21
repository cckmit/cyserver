package com.cy.core.analysis.utils;

import com.cy.base.entity.DataGrid;
import com.cy.common.utils.CacheUtils;
import com.cy.common.utils.SpringContextHolder;
import com.cy.core.alumni.dao.AlumniMapper;
import com.cy.core.analysis.action.AnalysisAction;
import com.cy.core.region.service.ProvinceService;
import com.cy.core.user.dao.UserMapper;
import com.cy.core.user.entity.User;
import com.cy.core.user.entity.UserRole;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userinfo.service.UserInfoService;
import com.cy.util.PairUtil;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户工具类
 * @author LiuZhen
 * @version 2016-7-14
 */
@Component("analysisUtils")
public class AnalysisUtils {

	private static UserInfoService userInfoService = SpringContextHolder.getBean("userInfoService") ;

	public static void init() {
		System.out.println("****************************** 初始化统计分析缓存定时器 start****************************************");
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
		System.out.println("****************************** 初始化统计分析缓存定时器  end ****************************************");
	}
}

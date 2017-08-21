package com.cy.core.analysis.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.action.BaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.common.utils.CacheUtils;
import com.cy.common.utils.StringUtils;
import com.cy.core.region.entity.Province;
import com.cy.core.region.service.CityService;
import com.cy.core.region.service.ProvinceService;
import com.cy.core.userinfo.service.UserInfoService;
import com.cy.system.Global;
import com.cy.util.BMapUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: AnalysisAction</p>
 * <p>Description: 统计分析Action</p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-09-14 16:09
 */

@Namespace("/analysis")
@Action(value = "analysisAction")
public class AnalysisAction extends AdminBaseAction {
    public final static String ANALYSIS_CACHE_USERINFOSUMMARY = "analysis_Cache_userInfoSummary" ;  // 统计校友数据(总)
    public final static String ANALYSIS_CACHE_COUNTANALYSISUSERINFO = "analysis_Cache_countAnalysisUserInfo" ;  // 统计分析校友数据
    public final static String ANALYSIS_CACHE_COUNTANALYSISUSERINFODATAGRID = "analysis_Cache_countAnalysisUserInfoDataGrid" ;  // 统计分析校友数据(表)
    public final static String ANALYSIS_CACHE_CHARTOFDEPTUSERCHART = "analysis_Cache_chartOfDeptUserChart" ;  // 获取院系校友报表图
    public final static String ANALYSIS_CACHE_CHARTOFREGISTERED = "analysis_Cache_chartOfRegistered" ;  // 获取被认证校友报表图
    public final static String ANALYSIS_CACHE_CHARTOFMINING = "analysis_Cache_chartOfMining" ;  // 获取挖掘校友报表图
    public final static String ANALYSIS_CACHE_CHARTOFUSERINFOMAP = "analysis_Cache_chartOfUserInfoMap" ;  // 获取省份地区校友分布
    public final static String ANALYSIS_CACHE_CHARTOFALUMNICOUNTMAP = "analysis_Cache_chartOfAlumniCountMap" ;  // 获取省份地区校友会成员分布
    public final static String ANALYSIS_CACHE_GETSCHOOLPOINT = "analysis_Cache_getSchoolPoint" ;  // 获取学校坐标地址
    public final static String ANALYSIS_CACHE_FINDPROVINCEPOINT = "analysis_Cache_findProvincePoint" ;  // 获取各省份坐标

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(AnalysisAction.class);

    @Autowired
    private UserInfoService userInfoService ;

    @Autowired
    private ProvinceService provinceService ;

    private String groupType ;              // 分组类型(1:学校;2:学院;3:年级;4:班级;5:专业)
    private Map<String,String> analysisMap ;   // 分析参数

    /**
     * 统计校友数据(总)
     */
    public void userInfoSummary()
    {
        List<Map<String,String>> list = userInfoService.userInfoSummary(analysisMap);
        super.writeJson(list);
    }
    /**
     * 统计校友数据(总)
     */
    public void doNotNeedSecurity_userInfoSummary()
    {
        List<Map<String,String>> list = userInfoService.userInfoSummary(analysisMap);
        super.writeJson(list);
    }

    /**
     * 统计分析校友数据
     */
    public void countAnalysisUserInfo()
    {
        List<Map<String,String>> list = userInfoService.countAnalysisUserInfo(analysisMap);
        super.writeJson(list);
    }
    /**
     * 统计分析校友数据
     */
    public void doNotNeedSecurity_countAnalysisUserInfo() throws Exception
    {
        List<Map<String,String>> list = userInfoService.countAnalysisUserInfo(analysisMap);
        super.writeJson(list);
    }
    /**
     * 统计分析校友数据
     */
    public void doNotNeedSecurity_countAnalysisUserInfoDataGrid()
    {
        try {
            analysisMap = analysisMap == null ? Maps.<String, String>newHashMap() : analysisMap;
            String groupType = analysisMap.get("groupType") ;
            groupType = StringUtils.isBlank(groupType) ? "1":groupType ;
            analysisMap.put("groupType",groupType) ;
            analysisMap.put("page", String.valueOf(page));
            analysisMap.put("rows", String.valueOf(rows));
            DataGrid<Map<String, String>> dataGrid = null ;
            if("1".equals(groupType.trim()) && StringUtils.isBlank(analysisMap.get("deptId")) && "1".equals(analysisMap.get("page")) && "10".equals(analysisMap.get("rows"))) {
                dataGrid = (DataGrid<Map<String,String>>) CacheUtils.get(ANALYSIS_CACHE_COUNTANALYSISUSERINFODATAGRID) ;
                if(dataGrid == null) {
                    dataGrid = userInfoService.countAnalysisUserInfoDataGrid(analysisMap);
                    CacheUtils.put(ANALYSIS_CACHE_COUNTANALYSISUSERINFODATAGRID,dataGrid);
                }
            } else {
                dataGrid = userInfoService.countAnalysisUserInfoDataGrid(analysisMap);
            }
            super.writeJson(dataGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取院系校友报表图
     */
    public void doNotNeedSecurity_chartOfDeptUserChart() {
        List<Map<String, Object>> list = userInfoService.chartOfDeptUser() ;
        super.writeJson(list);
    }

    /**
     * 获取被认证校友报表图
     */
    public void doNotNeedSecurity_chartOfRegistered() {
        List<Map<String, Object>> list = userInfoService.chartOfRegistered() ;
        super.writeJson(list);
    }


    /**
     * 获取挖掘校友报表图
     */
    public void doNotNeedSecurity_chartOfMining() {
        List<Map<String, String>> list = userInfoService.chartOfMining() ;
//        List<Map<String, String>> list = userInfoService.findUserInfoMapChatUser(analysisMap) ;
        super.writeJson(list);
    }
    /**
     * 获取省份地区校友分布
     */
    public void doNotNeedSecurity_chartOfUserInfoMap() {
//        Map<String,String> map = Maps.newHashMap() ;
        List<Map<String, String>> list = userInfoService.findUserInfoMap(analysisMap) ;
        super.writeJson(list);
    }

    /**
     * 获取省份地区校友会成员分布
     */
    public void doNotNeedSecurity_chartOfAlumniCountMap() {
//        Map<String,String> map = Maps.newHashMap() ;
        List<Map<String, String>> list = userInfoService.findAlumniCountMap(analysisMap) ;
        super.writeJson(list);
    }
    /**
     * 获取学校坐标地址
     */
    public void doNotNeedSecurity_getSchoolPoint() throws IOException {
        Map<String,Object> map = Maps.newHashMap() ;
        map.put("school",Global.schoolSign) ;

        Map<String,Double> schoolPointMap=BMapUtils.getLngAndLat(Global.schoolSign);
        map.putAll(schoolPointMap);
        String schoolCity = BMapUtils.getAddress(schoolPointMap.get("lng")+"",schoolPointMap.get("lat")+"").get("city") ;
        if(StringUtils.isNotBlank(schoolCity)) {
            schoolCity = schoolCity.lastIndexOf("市") == schoolCity.length() - 1?schoolCity.substring(0,schoolCity.length()-1):schoolCity ;
        }
        map.put("schoolCity",schoolCity) ;
        super.writeJson(map);
    }

    /**
     * 获取各省份坐标
     */
    public void doNotNeedSecurity_findProvincePoint() throws IOException {
        Map<String,Object> map = Maps.newHashMap() ;

        List<String> nameList = Lists.newArrayList() ;
        List<Province> provinceList = provinceService.selectByCountryId(1) ;
        Map<String,Double[]> pointMap = Maps.newHashMap() ;
        if(provinceList != null) {
            for(Province province : provinceList) {
                Map<String,Double> temp=BMapUtils.getLngAndLat(province.getProvincialCapital());
                Double[] porintArr = {(Double)temp.get("lng"),(Double)temp.get("lat")} ;
                pointMap.put(province.getProvinceName(),porintArr) ;

                nameList.add(province.getProvinceName());
            }
        }
        map.put("nameList",nameList) ;
        map.put("pointMap",pointMap) ;
        super.writeJson(map);
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public Map<String, String> getAnalysisMap() {
        return analysisMap;
    }

    public void setAnalysisMap(Map<String, String> analysisMap) {
        this.analysisMap = analysisMap;
    }
}

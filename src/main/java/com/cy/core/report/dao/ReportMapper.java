package com.cy.core.report.dao;

import com.cy.core.enterprise.entity.EnterpriseProduct;
import com.cy.core.report.entity.Report;


import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
public interface ReportMapper {

    // 保存举报（包括所举报的活动和所举报的花絮）
    void savereportActivityOrHuaxu(Report report);
    //获取举报列表
    List<Report> selectReportList(Map<String,String> map );

    long count(Map<String, Object> map);

    List<Report> findReportList(Map<String, Object> map);

    void update(Report report);



}

package com.cy.core.report.action;

import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.common.utils.StringUtils;
import com.cy.core.report.entity.Report;
import com.cy.core.report.service.ReportService;
import com.cy.core.user.entity.User;
import com.cy.util.UserUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
@Namespace("/report")
@Action(value = "reportAction")
public class ReportAction extends AdminBaseAction {

    private static final Logger logger = Logger.getLogger(ReportAction.class);
    @Autowired
    private ReportService reportService;


    private String bussId;
    private Report report;
    private String reportId;
    public void getReportDataGrid(){
        Map<String, Object> map= new HashMap<>();
        map.put("page", page);
        map.put("rows", rows);
        map.put("bussId", bussId);
        if(report != null){
            if(StringUtils.isNotBlank(report.getReportPerson())){
                map.put("reportPerson", report.getReportPerson());
            }
            if(StringUtils.isNotBlank( report.getHandleStatus())){
                map.put("handleStatus", report.getHandleStatus());
            }
        }
        DataGrid<Report> dataGrid = reportService.dataGridReport(map);
        super.writeJson(dataGrid);
    }


    public void getById(){
        super.writeJson(reportService.getById(reportId));
    }

    public void update(){
        Message message = new Message();
        try {
            String isTrue = report.getIsTrue();
            if(isTrue != null && !("").equals(isTrue) && ("1").equals(isTrue)){
                report.setHandleStatus("20");
            }
            if(isTrue != null && !("").equals(isTrue) && ("0").equals(isTrue)){
                report.setHandleStatus("30");
            }
            User user = UserUtils.getUser();
            if (user != null ){
                report.setHandleUserId(String.valueOf(user.getUserId()));
            }
            reportService.updateReport(report);
            message.setMsg("修改成功");
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg("修改失败");
            message.setSuccess(false);
            logger.error(e,e);
        }
        super.writeJson(message);
    }


    public String getBussId() {
        return bussId;
    }

    public void setBussId(String bussId) {
        this.bussId = bussId;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}

package com.cy.core.report.service;

import com.cy.base.entity.DataGrid;
import com.cy.base.entity.Message;
import com.cy.core.enterprise.entity.EnterpriseProduct;
import com.cy.core.report.entity.Report;
import com.sun.tools.javac.util.List;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
public interface ReportService {

    void report(Message message, String content);

    void viewReport(Message message, String content);

    DataGrid<Report> dataGridReport(Map<String, Object> map);

    Report getById(String id);

    int updateReport(Report report);
}

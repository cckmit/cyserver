package com.cy.shortmessage.http;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cy.shortmessage.ShortMessageConst;
import com.cy.shortmessage.entity.StatusReport;

public class StatusReportServlert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msgid = req.getParameter("msgid");
		String reportTime = req.getParameter("reportTime");
		String mobile = req.getParameter("mobile");
		String status = req.getParameter("status");
		String receiver = req.getParameter("receiver");
		String pswd = req.getParameter("pswd");
		if (receiver != null && pswd != null && receiver.equals(ShortMessageConst.account) && pswd.equals(ShortMessageConst.pswd)) {
			StatusReport report = new StatusReport();
			report.setMobile(mobile);
			report.setErrorCode(status);
			report.setSeqId(Long.parseLong(msgid));
			try {
				report.setDate(dateFormat.parse(reportTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (status != null && status.equals("DELIVRD")) {
				report.setStatus(0);
			} else {
				report.setStatus(-1);
			}
			if (ShortMessageConst.rptQueue.size() > ShortMessageConst.rptQueueMaxSize) {
				ShortMessageConst.rptQueue.clear();
			} else {
				ShortMessageConst.rptQueue.add(report);
			}

		}
	}

}

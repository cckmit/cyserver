package com.cy.shortmessage.sdk;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.emay.sdk.client.api.Client;
import cn.emay.sdk.client.api.StatusReport;

import com.cy.shortmessage.ShortMessage;
import com.cy.shortmessage.SingletonClient;
import com.cy.shortmessage.exception.ShortMessageException;

public class SdkSendAdaptor extends ShortMessage {

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	@Override
	public String sendSMS(String url, String account, String pswd, Map<String, Object> map) throws ShortMessageException  {
		try {
			Client client = SingletonClient.getClient(account, pswd);
			if (client == null) {
				return null;
			}
			String[] mobiles = ((String) map.get("mobile")).split(",");
			int result = client.sendSMSEx(mobiles, (String) map.get("msg"), "", "GBK", (Integer) map.get("smsPriority"), (Long) map.get("smsID"));
			return String.valueOf(result);
		} catch (Exception e) {
			throw new ShortMessageException(e);
		}
	}

	@Override
	public List<com.cy.shortmessage.entity.StatusReport> recvStatusReport(String account, String pswd) throws Exception {
		Client client = SingletonClient.getClient(account, pswd);
		if (client == null) {
			return null;
		}
		List<StatusReport> list = client.getReport();
		List<com.cy.shortmessage.entity.StatusReport> nvps = new ArrayList<com.cy.shortmessage.entity.StatusReport>();
		if (list != null && list.size() > 0) {
			for (StatusReport report : list) {
				com.cy.shortmessage.entity.StatusReport statusReport = new com.cy.shortmessage.entity.StatusReport();
				statusReport.setErrorCode(report.getErrorCode());
				statusReport.setMobile(report.getMobile());
				statusReport.setSeqId(report.getSeqID());
				statusReport.setStatus(report.getReportStatus());
				statusReport.setDate(dateFormat.parse(report.getReceiveDate()));
				nvps.add(statusReport);
			}
		}
		return nvps;
	}

	public void closeSdk(String account, String pswd) {
		Client client = SingletonClient.getClient(account, pswd);
		try {
			if (client != null) {
				client.closeSocketConnect();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

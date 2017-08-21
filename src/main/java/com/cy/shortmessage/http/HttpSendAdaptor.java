package com.cy.shortmessage.http;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.cy.shortmessage.ShortMessage;
import com.cy.shortmessage.ShortMessageConst;
import com.cy.shortmessage.entity.StatusReport;
import com.cy.shortmessage.exception.ShortMessageException;

public class HttpSendAdaptor extends ShortMessage {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HttpSendAdaptor.class);

	@Override
	public String sendSMS(String url, String account, String pswd, Map<String, Object> map) throws ShortMessageException {
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost method = new HttpPost(url);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			list.add(new BasicNameValuePair("account", account));
			list.add(new BasicNameValuePair("pswd", pswd));
			Iterator<String> it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = (String) map.get(key);
				list.add(new BasicNameValuePair(key, value));
			}
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(list, "UTF-8");
			method.setEntity(formEntiry);
			CloseableHttpResponse response = client.execute(method);
			try {
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity httpEntity = response.getEntity();
					return EntityUtils.toString(httpEntity);
				} else {
					logger.info("HTTP ERROR Status: " + response.getStatusLine().getStatusCode() + ":" + response.getStatusLine().getReasonPhrase());
					return "";
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			throw new ShortMessageException(e);
		}
	}

	@Override
	public List<com.cy.shortmessage.entity.StatusReport> recvStatusReport(String account, String pswd) {
		List<StatusReport> list = new ArrayList<StatusReport>();
		while (ShortMessageConst.rptQueue.poll() != null) {
			list.add(ShortMessageConst.rptQueue.poll());
		}
		return list;
	}

}

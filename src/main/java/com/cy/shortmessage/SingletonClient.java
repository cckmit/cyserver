package com.cy.shortmessage;

import java.util.HashMap;
import java.util.Map;

import cn.emay.sdk.client.api.Client;

public class SingletonClient {
	private static ShortMessage shortMessage = null;
	private static Client client = null;
	private static Map<String, ShortMessage> map = new HashMap<String, ShortMessage>();

	private static String account = "";

	private static String password = "";

	private SingletonClient() {

	}

	public synchronized static ShortMessage getInstance(Class cls, String type) {
		if (type != null && type.equals("HTTP")) {
			shortMessage = map.get(type);
			if (shortMessage == null) {
				shortMessage =(ShortMessage) ShortMessageFactory.createInstance(cls);
				map.put(type, shortMessage);
			}
		} else if (type != null && type.equals("SDK")) {
			shortMessage = map.get(type);
			if (shortMessage == null) {
				shortMessage =(ShortMessage) ShortMessageFactory.createInstance(cls);
				map.put(type, shortMessage);
			}
		}
		return shortMessage;
	}

	public synchronized static Client getClient(String softwareSerialNo, String key) {
		if (client == null || (softwareSerialNo != null && !softwareSerialNo.equals(account)) || (key != null && !key.equals(password))) {
			try {
				account = softwareSerialNo;
				password = key;
				client = new Client(softwareSerialNo, key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}

}

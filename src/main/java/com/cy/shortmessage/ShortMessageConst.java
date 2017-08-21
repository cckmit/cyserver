package com.cy.shortmessage;


import java.util.concurrent.ConcurrentLinkedQueue;

import com.cy.shortmessage.entity.StatusReport;

public class ShortMessageConst {
	public static long rptQueueMaxSize = 100000;
	public static ConcurrentLinkedQueue<StatusReport> rptQueue = new ConcurrentLinkedQueue<StatusReport>();
	public static String account = "";
	public static String pswd = "";
}

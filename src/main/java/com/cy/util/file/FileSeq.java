package com.cy.util.file;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 产生文件序列
 * 
 * @author joe
 * 
 */
public class FileSeq {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static long count = 0;
	private final static long maxCount = 9999;
	private static DecimalFormat format = new DecimalFormat("0000");

	public synchronized static String getSeq() {
		count++;
		String str = dateFormat.format(new Date()) + format.format(count);
		if (count == maxCount) {
			count = 0;
		}
		return str;
	}
}

package com.cy.system;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class IdUtil {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	private static long count = 0;
	private final static long maxCount = 9999;
	private static DecimalFormat format = new DecimalFormat("0000");

	private static String StuId = "0";

	public synchronized static String getClassId(Set<String> set) {
		String id = "";
		for (int i = 1; i < 100; i++) {
			DecimalFormat format = new DecimalFormat("00");
			id = format.format(i);
			if (!set.contains(id)) {
				break;
			}
		}
		return id;
	}

	public synchronized static String getDepart(Set<String> set) {
		String id = "";
		for (int i = 1; i < 1000; i++) {
			DecimalFormat format = new DecimalFormat("000");
			id = format.format(i);
			if (!set.contains(id)) {
				break;
			}
		}
		return id;
	}

	public synchronized static String getExtend(Set<String> set) {
		String id = "";
		String[] s = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < s.length - 1; i++) {
			if (!set.contains(s[i])) {
				id = s[i];
				break;
			}
		}
		return id;
	}

	public synchronized static String getUserId(Set<String> set) {
		String id = "";
		for (int i = 0; i < 1000; i++) {
			id = String.format( "%03d", i );
			if (!set.contains(id)) {
				break;
			}
		}
		return id;
	}

	public synchronized static String getOrderNo() {
		count++;
		String str = dateFormat.format(new Date()) + "-" + format.format(count);
		if (count == maxCount) {
			count = 0;
		}
		return str;
	}

}

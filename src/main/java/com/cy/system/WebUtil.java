package com.cy.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class WebUtil {

	private static final Logger logger = Logger.getLogger(WebUtil.class);

	public static String YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static String YMD = "yyyy-MM-dd";

	public static String trim(Object obj) {

		if (String.class.isInstance(obj)) {
			String str = String.valueOf(obj);

			if (str != null) {
				return str.trim();
			} else {
				return "";
			}
		} else {
			Date date = (Date) obj;

			if (date == null) {
				return "";
			} else {
				return formatDateByPattern(date, YMDHMS);
			}
		}

	}

	public static boolean isEmpty(String str) {
		return trim(str).isEmpty();
		//return str==null?true:str.isEmpty();
	}

	public static <E> boolean isEmpty(List<E> list) {
		if (list == null) {
			return true;
		} else if (list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static String formatDateByPattern(String date, String pattern) {
		try {
			if (isEmpty(date)) {
				return "";
			}

			SimpleDateFormat sdf = new SimpleDateFormat(pattern);

			return sdf.format(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static String formatDateByPattern(Date date, String pattern) {
		if (date == null) {
			return "";
		} else {
			return new SimpleDateFormat(pattern).format(date);
		}
	}

	/**
	 * 计算输入的日期与当前时间已失去的时长
	 * 
	 * @param date
	 *            输入的一个已过去的时间
	 * @return 描述已失去的时长的字符串
	 */
	public static String pastTime(Date date) {

		long day = 0;
		long hour = 0;
		long min = 0;

		String pastTime = "";

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS);

			Date d1 = sdf.parse(sdf.format(new Date()));
			Date d2 = sdf.parse(sdf.format(date));

			Calendar cal = Calendar.getInstance();
			cal.setTime(d1);
			long time1 = cal.getTimeInMillis();// System.out.println("time1=" +
			                                   // time1);
			cal.setTime(d2);
			long time2 = cal.getTimeInMillis();// System.out.println("time2=" +
			                                   // time2);

			long diff = 0;

			/*
			 * 
			 * if(time1<time2) { diff = time2 - time1; } else { diff = time1 -
			 * time2; }
			 */

			diff = time1 - time2;

			// System.out.println("diff=" + diff);
			day = (diff) / (1000 * 3600 * 24);
			// System.out.println("day=" + day);
			if (day == 0) {
				hour = (diff / (60 * 60 * 1000) - day * 24);
				// System.out.println("hour=" + hour);
				if (hour == 0) {
					min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
					// System.out.println("min=" + min);

					if (min <= 0) {
						pastTime = "刚刚";
					} else {
						pastTime = min + "分钟前";
					}

				} else if (hour < 24) {
					pastTime = hour + "小时前";
				}
			}
			if (day == 1) {
				pastTime = "昨天";
			} else if (day > 1) {
				pastTime = day - 1 + "天前";
			}

		} catch (ParseException e) {

			e.printStackTrace();
		}

		return pastTime;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 */
	public static int daysBetween(Date smdate, Date bdate) {

		long between_days = 0;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date d1 = sdf.parse(sdf.format(smdate));
			Date d2 = sdf.parse(sdf.format(bdate));

			Calendar cal = Calendar.getInstance();
			cal.setTime(d1);
			long time1 = cal.getTimeInMillis();
			cal.setTime(d2);
			long time2 = cal.getTimeInMillis();

			between_days = (time2 - time1) / (1000 * 3600 * 24);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) {
		long between_days = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			between_days = (time2 - time1) / (1000 * 3600 * 24);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static String doubleTrans(double d) {
		if (Math.round(d) - d == 0) {
			return String.valueOf((long) d);
		}
		return String.valueOf(d);
	}

	public static String getIpAddr(HttpServletRequest request) {
		logger.info("**************************************************");

		logger.info(new SimpleDateFormat("yyyy-MM-dd H:m:s.S").format(new java.util.Date()));

		String ip = request.getHeader("X-Forwarded-For");

		logger.info("X-Forwarded-For=" + ip);

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.info("Proxy-Client-IP=" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.info("WL-Proxy-Client-IP=" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			logger.info("HTTP_CLIENT_IP=" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			logger.info("HTTP_X_FORWARDED_FOR=" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			logger.info("RemoteAddr=" + ip);
		}

		logger.info("**************************************************");
		return ip;
	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = sdf.parse("2012-09-15 10:10:10");
		Date d2 = sdf.parse("2012-09-15 00:00:00");
		System.out.println(daysBetween(d1, d2));

		Date d3 = sdf.parse("2014-11-30 23:22:00");
		System.out.println(pastTime(d3));

		System.out.println(daysBetween("2012-09-08 10:10:10", "2012-09-15 00:00:00"));
	}

}

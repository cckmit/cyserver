package com.cy.system;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemUtil {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	/**
	 * 电话号码校验
	 * 
	 * @param telephone
	 * @return
	 */
	public static boolean isMobileNO(String telephone) {
		if (telephone == null) {
			return false;
		}
		telephone = telephone.trim().replace(" ", "");
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(telephone);
		return m.matches();
	}
	
	/**
	 * 6位随机数
	 * 
	 * @return
	 */
	public static synchronized String getSixNumber()
	{
		String uuid = "";
		Random random = new Random();
		for (int i = 0; i < 6; i++)
		{
			uuid += random.nextInt(10);
		}
		return uuid;
	}
	
	public synchronized static String getOrderNo() {
		return dateFormat.format(new Date()) + getSixNumber();
	}
	
	/**
	 * 取唯一编号
	 * 
	 * @return
	 */
	public static synchronized long getOnlyNumber() {
		return System.currentTimeMillis() + Math.abs(new Random().nextLong());
	}
	
	public static void main(String[] args) {
		System.out.println(getOnlyNumber());
	}
}

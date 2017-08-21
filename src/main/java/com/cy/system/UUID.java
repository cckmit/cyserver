package com.cy.system;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 用户ID工具类
 * 
 * @author dengqiao
 * 
 */
public class UUID {
	/**
	 * 获取用户ID后三位序列
	 * 
	 * @param set
	 * @return
	 */
	public static String getUuid(Set<String> set) {
		if (set.size() >= 998) {
		}
		Random random = new Random();
		while (true) {
			String uuid = "";
			for (int i = 0; i < 3; i++) {
				uuid += random.nextInt(10);
			}
			if (!set.contains(uuid) && !uuid.equals("000")) {
				set.add(uuid);
				return uuid;
			}
		}
	}

	public static String getTeacherUuid(Set<String> set) {
		if (set.size() >= 99998) {
		}
		Random random = new Random();
		while (true) {
			String uuid = "";
			for (int i = 0; i < 5; i++) {
				uuid += random.nextInt(10);
			}
			if (!set.contains(uuid) && !uuid.equals("00000")) {
				set.add(uuid);
				return uuid;
			}
		}
	}

	/**
	 * 6位随机密码
	 * 
	 * @return
	 */
	public static String getPassord() {
		String uuid = "";
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			uuid += random.nextInt(10);
		}
		return uuid;
	}

	public static synchronized long getMsgGroup() {
		return System.currentTimeMillis() + Math.abs(new Random().nextLong());
	}

	public static String getGradeKey() {
		String[] str = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
		Random random = new Random();
		String uuid = str[random.nextInt(26)] + str[random.nextInt(26)] + str[random.nextInt(26)];
		for (int i = 0; i < 8; i++) {
			uuid += random.nextInt(10);
		}
		return uuid;
	}

	/**
	 * 判断UUid
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isUuid(String str) {
		if (str.length() != 32) {
			return false;
		}
		char[] ch = str.toCharArray();
		for (char c : ch) {
			if (!Character.isDigit(c) && !Character.isLetter(c)) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Set<Long> set = new HashSet<Long>();
		for (;;) {
			long s = getMsgGroup();
			System.out.println(s);
			if (set.contains(s)) {
				break;
			}
			set.add(s);
		}
	}
}

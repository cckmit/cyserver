package com.hdos.test;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class Test1 {

	@Test
	public void md5Test() {
		String pwd = DigestUtils.md5Hex("hello");
		System.out.println(pwd);
	}
	
	@Test
	public void uuidTest() {
		String uuid = UUID.randomUUID().toString();
		System.out.println(uuid);
	}
}

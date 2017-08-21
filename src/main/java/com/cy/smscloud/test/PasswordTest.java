package com.cy.smscloud.test;

import com.alibaba.druid.filter.config.ConfigTools;

import java.util.Date;


public class PasswordTest {
	public static void main(String[] args) throws Exception{
		System.out.println(ConfigTools.decrypt("nKS2W1Ffv5t9qIJ/q5ik4XMXiVoHdFRGT2DiBtAN2z8LUdBks6YM7SEXAVIR9rkVsafcBiyUz/eCmremU6mDWw=="));
		System.out.println(ConfigTools.decrypt("jnFtJisThsHLIyiPi/BQODzsLTXoJzd6rJVth/uw8awPV7kfmjc/UTpkEr8lvOQCb9pK1Y4A0oQgWnqGQhpRgA=="));
		System.out.println(ConfigTools.decrypt("lj6QvoIgP316fFEg3NKfJWbj3VLFUWcTCcxD4ajD46ozXQwsicCVVtzlDWZ3hJ4b2s2v049v9NH90P6h+JI2yg=="));
		System.out.println(ConfigTools.decrypt("Hm8k9KFAhKVA/3jcXASfWJ/PsKdDnPUqXao1Eh28NKrDVaLe8u9TXH0YWmeUQYF3FEOW/xDi/I/oV4IsWEheIg=="));
		System.out.println(ConfigTools.encrypt("bscc2016"));
		System.out.println(ConfigTools.encrypt("123456"));
		System.out.println(ConfigTools.encrypt("cy199cn1816"));
		System.out.println(ConfigTools.encrypt("cy20172017"));

		String number = null ;
		number = Long.toHexString(new Date().getTime() - 1000000000000L)  ;
		number = number.toUpperCase() ;
		System.out.println(number);
	}
}

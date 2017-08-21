package com.cy.common.utils.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */
public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 商家支付宝账号
	//public static String seller_email = "";

//    //******************************************* 动态账号 *******************************************//
//	// 支付宝应用编号
//	public static String app_id = "";
//	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
//	public static String partner = "";
//	// 应用公钥
//	public static String app_public_key = "";
//	// 支付宝公钥
//	public static String alipay_public_key = "" ;
//	// 支付宝应用私钥
//	public static String app_private_key = "" ;
//
//	// 网关地址
//	public static String serverUrl = "" ;

    //******************************************* 窗友账号 *******************************************//
	//商家支付宝账号
	public static String seller_email = "yue.zhao@cymobi.com";
	//支付宝应用编号
	public static String app_id = "2016101002070979";
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088221525346022";
	//---------------------------------------------------------------------------------------
	public static String app_public_key ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMxEs2Uswkq6F8+CuzVjpdI80ToR0a297ZcXTvknfJR+T6wXHfbgXMmOjV+SXW4/0i2PqmzWjCy7C3fpKkPEseDZhPmmsTrkQ2cR9Rly8QOmZBcVmLbPNFjLUt1hzbr+G+PIWsrMUK+4zeGSFoYjXsFdd2cA1nUpS/R6oI9ZtuuQIDAQAB";
	// 支付宝公钥
	public static String alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	// 支付宝应用私钥
	public static String app_private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIzESzZSzCSroXz4K7NWOl0jzROhHRrb3tlxdO+Sd8lH5PrBcd9uBcyY6NX5Jdbj/SLY+qbNaMLLsLd+kqQ8Sx4NmE+aaxOuRDZxH1GXLxA6ZkFxWYts80WMtS3WHNuv4b48haysxQr7jN4ZIWhiNewV13ZwDWdSlL9Hqgj1m265AgMBAAECgYB4TBxWnWWWhRJp4RA/n5YbGMheWRataG9G9l3PiyVRX16BKSYuojDUi2yXx+cdgRNIX5O9TteTeU1ZUxLwxhIOmutumgwF1/OZUqCd22OSoEn4i0DcOCElbFyx6qe8wF2Fq1ExVm6T4PHwCBO/r5676OEuureNNczeS0VJkRNbgQJBAPSpcd52IDeZpj3ThRXKxZsE7/UQ5GGKdxeG6g38ByDcoUaIQYguNndn0ZXO8xDRVuF7I8HJBKLzYy/onKiMC+kCQQCTSkkVSph+lLofjN4LNdVOvjCTBHQC0IRGZJQ5AKikMzgd+fXAVkLDS+kQ35f9o6BRa/YvcV4dFEHHGaHXphpRAkEAuI+yJFz7xLWVoIIVnPDPzDcXRMkvXx1y5T6TBWWfgvysdGaI+M/TqpOYmOf6mC8y2Plpad/YLYxowFU7CDJNMQJAVH/DmcFn5wawGLWaFVhW/YvDFjTx0UOK/09ocaNdHIZ95USnpiZWuu/rz9BWxB6BNPT5EZK6aXhbnDuqS6txYQJBAOitwrKkgJAJPk3k9yuXTPepqFbwf2sMcWXWJTxkHmRpMMRZaXOz21Lpdsjk0DsYd9D7GsCnThJDOqoH74OuZfg=";
	//----------------------------------------------------------------------------------------

//	// 应用公钥
//	public static String app_public_key =
//			"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDjHe0E9JKss0HvQXP7ifshhwimkzBog7VT0JkLuHMCmsJ3gtumld4FVomHAUxlybgF85si61tPWi5DZvaFuCnTDJ25RFodV3nCp1CNFM0cJspeJuHdl5IgQJhqiqb4SM7iDVQ1tVhSpeaf0XQJsHd1vxzMosUr2uY6rFlrhsHxPQIDAQAB" ;
//	// 支付宝公钥
//	//public static String alipay_public_key =
//	//		"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB" ;
//	// 支付宝公钥
//	public static String alipay_public_key =
//			"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB" ;
//	// 支付宝应用私钥
//	public static String app_private_key =
//					"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOMd7QT0kqyzQe9Bc/uJ+yGHCKaTMGiDtVPQmQu4cwKawneC26aV3gVWiYcBTGXJuAXzmyLrW09aLkNm9oW4KdMMnblEWh1XecKnUI0UzRwmyl4m4d2XkiBAmGqKpvhIzuINVDW1WFKl5p/RdAmwd3W/HMyixSva5jqsWWuGwfE9AgMBAAECgYAPbMHjO0UW65nHyRc6dzWSIlypC4gXIRLYnWs3i5BsFdytRzrpxnXswOLYQ3NP2nJgHRplNbze184aj8ygalCUvxxseCNp1s6HWPOhXzSnnswpPGbBk8TYzgMzCsq75JO3DB5SLZKg4ynUE7OTIsuDjES9s6S614dnYeKf2/PPtQJBAPbz0tX4p+Iox5fEuhgOUfs4plljnInL+HAj2n5wf9cGRy0UshGi3OAnJaK9wvJnVreDUYpkvJqTWa6XCKsGhG8CQQDrcBBiihDaARVRSYxPghBQC+DWEDqU98IT+lb+P90T/7ni32Oqy1jL+ddiUroYuh3G4NvfAl6Olnbb8KSfXDMTAkEAiYeMeWF7jbYMQfdrdA41leUcr1eMTClt+HXfLt9UMtnqNa765FuBCsyCZzmrIfhvENBvTtV/O86n+VVfnG+KvQJBALv+DXdBW1dXyhfQLHNIHZJtCaij6MMOWi/tkw0NHZBcf6dCTKkSGeM1qd/ewq3/3srwvWAC2lHsnxfbvPdqfXUCQCx8l1PW0w7ja66ukEZqCiSag6/W3BYDBsGt6EjimdaSaEGuvjeGcw1usWJaagEdu9ev1XBlathrm6KJ8VEJ0wk=" ;
	// 网关地址
	public static String serverUrl = "https://openapi.alipay.com/gateway.do" ;
    //******************************************* 刘振账号 *******************************************//
	// 支付宝应用编号
//	public static String app_id = "2016101002071774";
//	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
//	public static String partner = "2088402684640024";
    //******************************************* 沙箱测试账号 *******************************************//
	// 支付宝应用编号
//	public static String app_id = "2016091000478555";
//	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
//	public static String partner = "2088102174974449";
//	// 应用公钥
//	public static String app_public_key ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDfFhSq1XvoEEgZY8twdsTgo2FZFbs+/m7oYCgWnXVgFDxBxx3AM5dL5EiNoskZsFW7A/Lz7Jjk0jniwKFACl6RPEIs9q8+pa7S0x2PB3VAKa2efAtZsxOzcPvMhqsk13BKY03M0u0my2mgOWJccm5HAH56kUh496ebEeatGKYOsQIDAQAB" ;
//	// 支付宝公钥
//	public static String alipay_public_key =
//			"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB" ;
//	// 支付宝应用私钥
//	public static String app_private_key =
//			"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN8WFKrVe+gQSBljy3B2xOCjYVkVuz7+buhgKBaddWAUPEHHHcAzl0vkSI2iyRmwVbsD8vPsmOTSOeLAoUAKXpE8Qiz2rz6lrtLTHY8HdUAprZ58C1mzE7Nw+8yGqyTXcEpjTczS7SbLaaA5YlxybkcAfnqRSHj3p5sR5q0Ypg6xAgMBAAECgYEAmq00pz5eKwke9Hu9Er1GxHqDhMEMkyDWxUfmg8epGnTtUq48codS38vogkvcI3Of/Ys/aOEjIYShnhbxtvV5mu4Z+lgxE11xY++wKFWqKTCZPj1CIwe73rcAD0vaQGW2YNjSGQxitQ7zxMuF4evMzFy20S91jr6xw62nNcEgbgECQQD2/CuPUsU7w8Cv0PN+9K250XP1P1Jikm0wpXlkEz1Iib5H6civsApUxlEKz+4pBrFC7UyIh8M8hsneuKsqJb9DAkEA5zqZo7D2Fj2h0PpDhd/EZ42o9SCUPTiVpjQJJwvv2fMhS2tOmj+ggqrFFxNBqFSE67OJCoSBg/WJRVOqzwHY+wJAMaXjQ75Js4fYFf+U0vJwcafu/V+rOfFhTaQV0M4lRY2a2G3gT6C9kukCpX/CyjB0NZXqCo/v6RzXO5Q3pBNObQJATA7NfLd3qscpE+lODpoVK47ANak6uYyERQA2xn45rfI4UGuClmA5duGfJMDzxt/OPQ14FVqSk4pPVdt4gtDzwwJBAMpvUJ4g79edRhs3Vmew5CHuLd78UgV9jQsIU8OFHm2uL7WWsHN04HR1QckbGdM2lCyWH2ej0VLCCmxwVlF9Eqs=" ;
//	public static String key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOMd7QT0kqyzQe9B\n" +
//			"c/uJ+yGHCKaTMGiDtVPQmQu4cwKawneC26aV3gVWiYcBTGXJuAXzmyLrW09aLkNm\n" +
//			"9oW4KdMMnblEWh1XecKnUI0UzRwmyl4m4d2XkiBAmGqKpvhIzuINVDW1WFKl5p/R\n" +
//			"dAmwd3W/HMyixSva5jqsWWuGwfE9AgMBAAECgYAPbMHjO0UW65nHyRc6dzWSIlyp\n" +
//			"C4gXIRLYnWs3i5BsFdytRzrpxnXswOLYQ3NP2nJgHRplNbze184aj8ygalCUvxxs\n" +
//			"eCNp1s6HWPOhXzSnnswpPGbBk8TYzgMzCsq75JO3DB5SLZKg4ynUE7OTIsuDjES9\n" +
//			"s6S614dnYeKf2/PPtQJBAPbz0tX4p+Iox5fEuhgOUfs4plljnInL+HAj2n5wf9cG\n" +
//			"Ry0UshGi3OAnJaK9wvJnVreDUYpkvJqTWa6XCKsGhG8CQQDrcBBiihDaARVRSYxP\n" +
//			"ghBQC+DWEDqU98IT+lb+P90T/7ni32Oqy1jL+ddiUroYuh3G4NvfAl6Olnbb8KSf\n" +
//			"XDMTAkEAiYeMeWF7jbYMQfdrdA41leUcr1eMTClt+HXfLt9UMtnqNa765FuBCsyC\n" +
//			"ZzmrIfhvENBvTtV/O86n+VVfnG+KvQJBALv+DXdBW1dXyhfQLHNIHZJtCaij6MMO\n" +
//			"Wi/tkw0NHZBcf6dCTKkSGeM1qd/ewq3/3srwvWAC2lHsnxfbvPdqfXUCQCx8l1PW\n" +
//			"0w7ja66ukEZqCiSag6/W3BYDBsGt6EjimdaSaEGuvjeGcw1usWJaagEdu9ev1XBl";

	// 沙箱测试网关地址
//	public static String serverUrl = "https://openapi.alipaydev.com/gateway.do" ;

	/*// 商家支付宝账号
	public static String seller_email = "gongkaow@163.com";
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088121484361060";
	// 商户的私钥
	public static String key = "26ztcqsj4f3reg0s6zwuy35m0sj0wvlk";

	*/

	/**
	 * 参数编码字符集
	 */
	public static String input_charset = "utf-8";

	/**
	 * 签名方式 不需修改
	 */
//	public static String sign_type = "MD5";
	public static String sign_type = "RSA";

	/**
	 * 服务器异步通知页面路径
	 */
	public static String notify_url = "";

	/**
	 * 页面跳转同步通知页面路径
	 */
	public static String return_url = "";

	// 调用的接口名，无需修改  暂时没用，程序中写死
	public static String service = "create_direct_pay_by_user";
	// 支付类型 ，无需修改
	public static String payment_type = "";
	/**
	 * 服务器异步通知页面路径
	 */
//	public static String notify_url = "http://bsapp.net:8091/cy/alipay/alipayAction!doNotNeedSessionAndSecurity_notify.action";
//
//	/**
//	 * 页面跳转同步通知页面路径
//	 */
//	public static String return_url = "http://bsapp.net:8091/cy/alipay/alipayAction!doNotNeedSessionAndSecurity_result.action";

	/**
	 * 客户端IP地址
	 */
	public static String exter_invoke_ip = "";

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
	public static String anti_phishing_key = "";
}

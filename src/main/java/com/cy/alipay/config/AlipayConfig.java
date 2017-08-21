package com.cy.alipay.config;

public class AlipayConfig {
	/**
	 * 合作者身份ID
	 */
	public static String partner = "";
	/**
	 * 参数编码字符集
	 */
	public static String _input_charset = "utf-8";

	/**
	 * 签名方式
	 */
	public static String sign_type = "MD5";

	/**
	 * 收款支付宝账号
	 */
	public static String seller_email = "";

	/**
	 * 商户的私钥
	 */
	public static String key = "";

	/**
	 * 服务器异步通知页面路径
	 */
	public static String notify_url = "";

	/**
	 * 页面跳转同步通知页面路径
	 */
	public static String return_url = "";
	
	/**
	 * 客户端IP地址
	 */
	public static String exter_invoke_ip = "";

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

}

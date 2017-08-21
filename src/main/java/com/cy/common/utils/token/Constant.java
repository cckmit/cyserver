package com.cy.common.utils.token;

public class Constant {

	/**
	 * 数据请求返回码
	 */
	public static final int RESCODE_SUCCESS = 1000;				//成功
	public static final int RESCODE_SUCCESS_MSG = 1001;			//成功(有返回信息)
	public static final int RESCODE_EXCEPTION = 1002;			//请求抛出异常
	public static final int RESCODE_NOLOGIN = 1003;				//未登陆状态
	public static final int RESCODE_NOEXIST = 1004;				//查询结果为空
	public static final int RESCODE_NOAUTH = 1005;				//无操作权限
	public static final int RESCODE_EXPTIME = 1006;				//TOKEN过期
	
	/**
	 * jwt
	 */
	public static final String JWT_ID = "jwt";
	//public static final String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";
	public static final String JWT_SECRET = "8998234swe89d828dfwe8f8dswe888wv";
	public static final int JWT_TTL = 60;  //millisecond  //60分钟
	public static final int JWT_REFRESH_INTERVAL = 1;   //1分钟
	public static final int JWT_REFRESH_TTL = 720;  //millisecond   //12小时=720分钟
	
}

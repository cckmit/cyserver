package com.cy.smscloud.entity;

/**
 * Created by Kentun on 2015-08-28.
 */
public class Result {

	/***************************************************************
	 * HTTP请求状态码(过期)
	 ***************************************************************/

	public static final String CODE_REQUEST_WRONG="103";
	public static final String MSG_REQUEST_WRONG="请求参数不合法";

	public static final String CODE_ACTION_NULL="104";
	public static final String MSG_ACTION_NULL="请求控制器不存在";

	public static final String CODE_METHOD_NULL="105";
	public static final String MSG_METHOD_NULL="请求方法不存在";

	public static final String CODE_USER_ERROR="106";
	public static final String MSG_USER_ERROR="用户验证失败";

	public static final String MSG_USER_NULL="用户不存在";

	public static final String CODE_USER_TOKEN_WRONG="107";
	public static final String MSG_USER_TOKEN_WRONG="UserToken不正确，请重新登录";

	public static final String CODE_DEVICE_INCOMPLETE="108";
	public static final String MSG_DEVICE_INCOMPLETE="设备信息不完整";

	public static final String CODE_USER_DEVICE_ERROR="109";
	public static final String MSG_USER_DEVICE_ERROR="用户已在其他设备登录";


	/***************************************************************
	 * HTTP请求状态码
	 * 格式：1xxx
	 ***************************************************************/
	public static final String CODE_REQUEST_FAILED = "404";
	public static final String MSG_REQUEST_FAILED = "请求失败";

	public static final String CODE_SUCCESS = "200";
	public static final String MSG_SUCCESS = "请求成功，执行成功";

	public static final String CODE_REQUEST_RIGHT="201";
	public static final String MSG_REQUEST_RIGHT="请求参数合法";

	public static final String CODE_FAILED = "500";
	public static final String MSG_FAILED = "请求成功，执行失败";

	/***************************************************************
	 * APP接口：请求相关错误码
	 * 格式：1xxx
	 ***************************************************************/

//jsonData、header、body、body.data为空或缺少

	public static final String CODE_REQUEST_REPORT_FORMAT_ERROR = "1100";
	public static final String MSG_REQUEST_REPORT_FORMAT_ERROR = "报文格式错误";

	public static final String CODE_REQUEST_HEADER_NULL = "1101";
	public static final String MSG_REQUEST_HEADER_NULL = "报文header为空或不存在";

	public static final String CODE_REQUEST_BODY_NULL = "1102";
	public static final String MSG_REQUEST_BODY_NULL = "报文body为空或不存在";

	public static final String CODE_REQUEST_DATA_NULL = "1103";
	public static final String MSG_REQUEST_DATA_NULL = "报文body.data为空或不存在";

//module、action、method、apiToken、deviceId、deviceType、userId、userToken为空或缺少

	public static final String CODE_REQUEST_HEADER_PARAM_NULL = "1200";
	public static final String MSG_REQUEST_HEADER_PARAM_NULL = "请求头缺少参数";

//module、action、method、apiToken、deviceId、deviceType、userId、userToken不合法

	public static final String CODE_REQUEST_HEADER_PARAM_ERROR = "1300";
	public static final String MSG_REQUEST_HEADER_PARAM_ERROR = "请求头参数不合法";

	public static final String CODE_REQUEST_HEADER_METHOD_ERROR = "1301";
	public static final String MSG_REQUEST_HEADER_METHOD_ERROR = "请求头module 或 action 或 method不合法";

	public static final String CODE_REQUEST_HEADER_APITOKEN_ERROR = "1302";
	public static final String MSG_REQUEST_HEADER_APITOKEN_ERROR = "请求头apiToken不合法";

	public static final String CODE_REQUEST_HEADER_DEVICE_ERROR = "1303";
	public static final String MSG_REQUEST_HEADER_DEVICE_ERROR = "请求头deviceId不合法，请先绑定设备";

	public static final String CODE_REQUEST_HEADER_USER_ERROR = "1304";
	public static final String MSG_REQUEST_HEADER_USER_ERROR = "请求头userId不合法，请重新登录";

	public static final String CODE_REQUEST_HEADER_USERTOKEN_ERROR = "1305";
	public static final String MSG_REQUEST_HEADER_USERTOKEN_ERROR = "请求头userToken不合法，请重新登录";

	public static final String CODE_REQUEST_HEADER_USERTOKEN_TIMEOUT = "1306";
	public static final String MSG_REQUEST_HEADER_USERTOKEN_TIMEOUT = "请求头userToken已过期，请重新登录";

	public static final String CODE_REQUEST_HEADER_APPACCOUNT_WRONG = "1401";
	public static final String MSG_REQUEST_HEADER_APPACCOUNT_WRONG = "请求头应用账号不正确或不存在";

	public static final String CODE_REQUEST_HEADER_APPKEY_WRONG = "1402";
	public static final String MSG_REQUEST_HEADER_APPKEY_WRONG = "请求头应用密钥不正确";

	public static final String CODE_REQUEST_HEADER_APPACCOUNT_STOP = "1403";
	public static final String MSG_REQUEST_HEADER_APPACCOUNT_STOP = "请求头应用账号已停用";

	public static final String CODE_REQUEST_HEADER_APPACCOUNT_BALANCE_NOT_ENOUGH = "1404";
	public static final String MSG_REQUEST_HEADER_APPACCOUNT_BALANCE_NOT_ENOUGH = "请求头应用账号余额不足";

	/***************************************************************
	 * APP接口：请求业务参数相关错误码
	 * 格式：2xxx or 3xxx
	 ***************************************************************/

	public static final String CODE_REQUEST_DATA_PARAM_NULL = "2000";
	public static final String MSG_REQUEST_DATA_PARAM_NULL = "请求参数为空或不存在";

	public static final String CODE_REQUEST_DATA_PARAM_ERROR = "3000";
	public static final String MSG_REQUEST_DATA_PARAM_ERROR = "请求参数不合法";

	public static final String CODE_MOBILE_FORMAT_ERROR = "3001";
	public static final String MSG_MOBILE_FORMAT_ERROR = "手机号格式错误";

	public static final String CODE_VERIFY_CODE_ERROR = "3002";
	public static final String MSG_VERIFY_CODE_ERROR = "验证码错误";

	public static final String CODE_VERIFY_CODE_TIMEOUT = "3003";
	public static final String MSG_VERIFY_CODE_TIMEOUT = "验证码过期,请重新发送验证码";

	public static final String CODE_MOBILE_REGISTERED = "3004";
	public static final String MSG_MOBILE_REGISTERED = "手机号已注册，请直接登录";

	public static final String CODE_MOBILE_NO_REGISTER = "3005";
	public static final String MSG_MOBILE_NO_REGISTER = "手机号未注册，请先注册";

	public static final String CODE_PUBLISH_DATE_ERROR = "3006";
	public static final String MSG_PUBLISH_DATE_ERROR = "发布时间不合法，发布时间应在当前时间之后";

	public static final String CODE_RESULT_NULL = "3007";
	public static final String MSG_RESULT_NULL = "返回结果为空";

	public static final String CODE_REQUEST_APPACCOUNT_BALANCE_NOT_ENOUGH = "3008";
	public static final String MSG_REQUEST_APPACCOUNT_BALANCE_NOT_ENOUGH = "应用账号余额不足";

	/***************************************************************
	 * APP接口：请求结果相关错误码
	 * 格式：4xxx
	 ***************************************************************/
	public static final String CODE_VERIFY_CODE_SEND_FAILED = "4001";
	public static final String MSG_VERIFY_CODE_SEND_FAILED = "验证码发送失败";

	public static final String CODE_USER_NO_EXIST = "4002";
	public static final String MSG_USER_NO_EXIST = "用户不存在";

	public static final String CODE_LOGIN_PWD_FAILED = "4003";
	public static final String MSG_LOGIN_PWD_FAILED = "登录失败，账号或密码错误";

	public static final String CODE_OPEN_AREA_NULL = "4004";
	public static final String MSG_OPEN_AREA_NULL = "没有查到开通城市列表";

	public static final String CODE_EXISTS = "4005";
	public static final String MSG_EXISTS = "已存在";

	public static final String CODE_REQUEST_NULL_EXISTS = "4006";
	public static final String MSG_REQUEST_NULL_EXISTS = "请求数据不存在存在";

	/***************************************************************
	 * APP接口：其它错误码
	 * 格式：9xxx
	 ***************************************************************/
	public static final String CODE_EXCEPTION = "9001";
	public static final String MSG_EXCEPTION = "其他异常错误，请打印报文联系服务开发者";

	/***************************************************************
	 * Result对象属性 和 方法
	 ***************************************************************/


	private String bussinessCode;//编码
    private String msg;//消息
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBussinessCode() {
		return bussinessCode;
	}

	public void setBussinessCode(String bussinessCode) {
		this.bussinessCode = bussinessCode;
	}

	public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取Result
     * @param code 编码
     * @param msg 消息
     * @return
     */
    public static Result getResult(String code,String msg ){
        Result result = new Result();
        result.setBussinessCode(code);
        result.setMsg(msg);
        return result;
    }

	@Override
	public String toString() {
		return "Result [bussinessCode=" + bussinessCode + ", msg=" + msg + "]";
	}

}

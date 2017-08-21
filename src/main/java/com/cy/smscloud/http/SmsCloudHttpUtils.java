package com.cy.smscloud.http;

import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.Maps;
import com.google.common.collect.ObjectArrays;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.cy.smscloud.entity.Header;
import com.cy.smscloud.entity.RequestEntity;

/**
 * 短信平台HTTP访问
 * @author 刘振
 *
 */
public class SmsCloudHttpUtils {
	private static final Logger logger = Logger.getLogger(SmsCloudHttpUtils.class);
	public final static String SMS_CLOUD_URL = HttpClientBase.SMS_API_URL ;
	public final static String SMS_SERVER_URL = HttpClientBase.SMS_CLOUD_URL;
	public final static String APP_ACCOUNT = "KED123121" ;
	public final static String APP_KEY = "123456" ;
	public final static String APP_SIGN = "窗友大学" ;


	/**
	 * 发送即时短信
	 * @param sendUrl		短信发送路径
	 * @param appAccount	云平台应用账号
	 * @param appKey		云平台应用密钥
	 * @param mobile		发送短信的手机号
	 * @param content		发送短信的内容
	 * @return
	 */
	public static String sendSms(String sendUrl ,String appAccount ,String appKey ,String mobile ,String content) {
		String code = "500" ;
		Header header = new Header() ;
		header.setModule("cloud");
		header.setAction("SmsCloudService");
		header.setMethod("sendSms");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
//		header.setAppAccount(Global.userAccount);
//		header.setAppKey(Global.password);
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		Map<String ,String> map = new HashMap<String, String>() ;
		map.put("mobile", mobile) ;
		map.put("content", content) ;
		HttpClientBase httpClientBase = new HttpClientBase() ;
		logger.info("---------> 短信请求 URL : " + sendUrl);
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		logger.info("---------> 短信请求 结果 : " + entity);
//		code = entity.getCode() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("【短信平台】发送即时短信：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
			}
		}
		return code ;
	}
	// TODO 临时发送验证码
	/**
	 * 通过Submail 发送即时短信
	 * @param sendUrl		短信发送路径
	 * @param appAccount	云平台应用账号
	 * @param appKey		云平台应用密钥
	 * @param mobile		发送短信的手机号
	 * @param content		发送短信的内容
	 * @return
	 */
	public static String sendSmsBySubmail(String sendUrl ,Header header ,Map<String,Object> params) {
		String code = "500" ;
		header.setModule("cloud");
		header.setAction("SmsCloudService");
		header.setMethod("sendSmsBySubmail");
		header.setAppAccount(StringUtils.isNotBlank(header.getAppAccount()) ? header.getAppAccount() : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(header.getAppKey()) ? header.getAppKey() : APP_KEY);
//		header.setAppAccount(Global.userAccount);
//		header.setAppKey(Global.password);
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		HttpClientBase httpClientBase = new HttpClientBase() ;
		logger.info("---------> 短信请求 URL : " + sendUrl);
		RequestEntity entity = httpClientBase.post(header, params, sendUrl) ;
		logger.info("---------> 短信请求 结果 : " + entity);
//		code = entity.getCode() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("【短信平台】发送即时短信：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
			}
		}
		return code ;
	}

	/**
	 * 获取账号信息查询
	 * @param sendUrl		短信发送路径
	 * @param appAccount	云平台应用账号
	 * @param appKey		云平台应用密钥
	 * @return
	 */
	public static Map<String,String> findSmsAppCount(String sendUrl ,String appAccount ,String appKey ) {
		String code = "500" ;
		Header header = new Header() ;
		header.setModule("cloud");
		header.setAction("SmsCloudService");
		header.setMethod("findSmsAppCount");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
//		header.setAppAccount(Global.userAccount);
//		header.setAppKey(Global.password);
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		Map<String ,String> map = new HashMap<String, String>() ;
//		map.put("mobile", mobile) ;
//		map.put("content", content) ;
		HttpClientBase httpClientBase = new HttpClientBase() ;
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		System.out.println("---------> entity : " + entity);
		Map<String,String> resultMap = Maps.newHashMap() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("【短信平台】发送即时短信：" + msg) ;
			if("200".equals(code)) {
//				code = "0" ;
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap ;
	}

	/**
	 * 查询应用 账户信息 + 联系人信息
	 *
	 * @param sendUrl
	 * @param appAccount
	 * @param appKey
	 * @return
	 *
	 * @author llr
	 */
	public static Map<String,String> findSmsAppccountAndUser(String sendUrl ,String appAccount ,String appKey ) {
		Map<String,String> resultMap = Maps.newHashMap() ;

		Header header = new Header() ;
		header.setModule("cloud");
		header.setAction("SmsCloudService");
		header.setMethod("findSmsAppAccountAndUser");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
		header.setDeviceType("3");
		header.setDeviceId("0000000");

		Map<String ,String> map = new HashMap<String, String>() ;

		HttpClientBase httpClientBase = new HttpClientBase() ;

		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;

		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			String code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("查询应用 账户信息 + 联系人信息：" + msg) ;
			if("200".equals(code)) {
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap ;
	}

	/**
	 * 开通账号信息
	 * @param sendUrl		短信发送路径
	 * @param //appAccount	云平台应用账号
	 * @param //appKey		云平台应用密钥
	 * @param sign          学校签名
	 * @param userName      联系人姓名
	 * @param userPhone     联系人电话
	 * @param userEmail    联系人邮箱
	 * @return
	 */
	public static Map<String,String> saveAccount(String sendUrl ,String account ,String password,String sign,String userName,String userPhone ,String userEmail) {
		String code = "500" ;
		Header header = new Header() ;
		header.setModule("app");
		header.setAction("AppAccountService");
		header.setMethod("saveAccount");
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		Map<String ,Object> map = new HashMap<String, Object>() ;
		map.put("account", account) ;
		map.put("password", password) ;
		map.put("sign", sign) ;
		Map<String ,String> userMap = new HashMap<String, String>() ;
		userMap.put("name",userName) ;
		userMap.put("phone",userPhone) ;
		userMap.put("email",userEmail) ;
		map.put("appUser",userMap) ;
		HttpClientBase httpClientBase = new HttpClientBase() ;
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		System.out.println("---------> entity : " + entity);
		Map<String,String> resultMap = Maps.newHashMap() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("开通帐号信息：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap ;
	}

	/**
	 * 修改帐号+联系人信息
	 * @param sendUrl		短信发送路径
	 * @param //appAccount	云平台应用账号
	 * @param //appKey		云平台应用密钥
	 * @param sign          学校签名
	 * @param userName      联系人姓名
	 * @param userPhone     联系人电话
	 * @param userEmail    联系人邮箱
	 * @return
	 */
	public static Map<String,String> updateAccount(String sendUrl ,String appAccount ,String appKey ,String account ,String password,String sign,String userName,String userPhone ,String userEmail, String currBuyWaterId, String nextBuyWaterId) {
		String code = "500" ;
		Header header = new Header() ;
		header.setModule("app");
		header.setAction("AppAccountService");
		header.setMethod("updateAccount");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		Map<String ,Object> map = new HashMap<String, Object>() ;
		map.put("account", account) ;
		map.put("password", password) ;
		map.put("sign", sign) ;
		map.put("currBuyWaterId", currBuyWaterId);
		map.put("nextBuyWaterId", nextBuyWaterId);
		Map<String ,String> userMap = new HashMap<String, String>() ;
		userMap.put("name",userName);
		userMap.put("phone",userPhone);
		userMap.put("email",userEmail);
		map.put("appUser",userMap) ;
		HttpClientBase httpClientBase = new HttpClientBase() ;
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		System.out.println("---------> entity : " + entity);
		Map<String,String> resultMap = Maps.newHashMap() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("修改帐号信息 ：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap ;
	}


	/**
	 * 短信发送记录
	 * @param sendUrl		短信发送路径
	 * @param appAccount	云平台应用账号
	 * @param appKey		云平台应用密钥
	 * @return
	 */
	public static Map<String,String> findSendRecordPage(String sendUrl ,String appAccount ,String appKey,String page,String rows) {
		String code = "500" ;
		Header header = new Header() ;
		header.setModule("app");
		header.setAction("AppUseWaterService");
		header.setMethod("findSendRecordPage");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		Map<String ,String> map = new HashMap<String, String>() ;
		map.put("page", page) ;
		map.put("rows", rows) ;
		HttpClientBase httpClientBase = new HttpClientBase() ;
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		System.out.println("---------> entity : " + entity);
		Map<String,String> resultMap = Maps.newHashMap() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("短信发送记录：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap ;
	}



	/**
	 * 应用购买流水记录
	 * @param sendUrl		短信发送路径
	 * @param appAccount	云平台应用账号
	 * @param appKey		云平台应用密钥
	 * @return
	 */
	public static Map<String,String> findAppBuyWaterPage(String sendUrl ,String appAccount ,String appKey,String page,String rows, String buyTime, String buyNum,String sign,String status) {
		String code = "500" ;
		Header header = new Header() ;
		header.setModule("app");
		header.setAction("AppBuyWaterService");
		header.setMethod("findAppBuyWaterPage");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		Map<String ,String> map = new HashMap<String, String>() ;
		map.put("page", page) ;
		map.put("rows", rows) ;
		map.put("buyTime", buyTime) ;
		map.put("buyNum", buyNum) ;
		map.put("sign", sign) ;
		map.put("status", status) ;
		HttpClientBase httpClientBase = new HttpClientBase() ;
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		System.out.println("---------> entity : " + entity);
		Map<String,String> resultMap = Maps.newHashMap() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("应用购买流水记录：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap;
	}


	/**
	 * 查找所有模版
	 * @param sendUrl		短信发送路径
	 * @param appAccount	云平台应用账号
	 * @param appKey		云平台应用密钥
	 * @return
	 */
	public static Map<String,String> findTemplatePage(String sendUrl ,String appAccount ,String appKey,String page,String rows) {
		String code = "500" ;
		Header header = new Header() ;
		header.setModule("app");
		header.setAction("AppTemplateService");
		header.setMethod("findTemplatePage");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		Map<String ,String> map = new HashMap<String, String>() ;
		map.put("page", page) ;
		map.put("rows", rows) ;
		HttpClientBase httpClientBase = new HttpClientBase() ;
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		System.out.println("---------> entity : " + entity);
		Map<String,String> resultMap = Maps.newHashMap() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("查找所有模版：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap;
	}

	/**
	 * 申请模版
	 * @param sendUrl		短信发送路径
	 * @param appAccount	云平台应用账号
	 * @param appKey		云平台应用密钥
	 * @param thirdId       标题
	 * @param template      模版
	 * @param ifUse         是否使用
	 * @return
	 */
	public static Map<String,String> saveTemplate(String sendUrl ,String appAccount ,String appKey,String thirdId,String template,String ifUse) {
		String code = "500" ;
		Header header = new Header() ;
		header.setModule("app");
		header.setAction("AppTemplateService");
		header.setMethod("saveTemplate");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		Map<String ,String> map = new HashMap<String, String>() ;
		map.put("thirdId", thirdId) ;
		map.put("template", template) ;
		map.put("ifUse", ifUse) ;
		HttpClientBase httpClientBase = new HttpClientBase() ;
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		System.out.println("---------> entity : " + entity);
		Map<String,String> resultMap = Maps.newHashMap() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("申请模版：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap;
	}



	/**
	 * 修改模版
	 * @param sendUrl		短信发送路径
	 * @param appAccount	云平台应用账号
	 * @param appKey		云平台应用密钥
	 * @param thirdId       标题
	 * @param template      模版
	 * @param ifUse         是否使用
	 * @return
	 */
	public static Map<String,String> updateTemplate(String sendUrl ,String appAccount ,String appKey,String thirdId,String template,String ifUse) {
		String code = "500" ;
		Header header = new Header() ;
		header.setModule("app");
		header.setAction("AppTemplateService");
		header.setMethod("updateTemplate");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		Map<String ,String> map = new HashMap<String, String>() ;
		map.put("thirdId", thirdId) ;
		map.put("template", template) ;
		map.put("ifUse", ifUse) ;
		HttpClientBase httpClientBase = new HttpClientBase() ;
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		System.out.println("---------> entity : " + entity);
		Map<String,String> resultMap = Maps.newHashMap() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("修改模版：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap;
	}

	/**
	 * 根据购买条数，以及短信云后台设置的梯度单价，计算出总金额
	 *
	 * @param sendUrl
	 * @param appAccount
	 * @param appKey
	 * @param buyNum
	 * @return
	 */
	public static Map<String, String> findCloudFlowPacketOrderByNum(String sendUrl, String appAccount, String appKey, String buyNum) {
		Header header = new Header();
		header.setModule("cloud");
		header.setAction("CloudFlowPacketDetailService");
		header.setMethod("findCloudFlowPacketOrderByNum");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
		header.setDeviceType("3");
		header.setDeviceId("0000000");

		Map<String ,String> map = new HashMap<String, String>();
		map.put("buyNum", buyNum);

		HttpClientBase httpClientBase = new HttpClientBase();
		RequestEntity entity = httpClientBase.post(header, map, sendUrl);

		logger.info("---------> entity : " + entity) ;

		Map<String,String> resultMap = Maps.newHashMap();
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			String code = entity.getBody().getResult().getBussinessCode();
			String msg = entity.getBody().getResult().getMsg();

			logger.info("查询流量包明細：" + msg) ;

			if("200".equals(code)) {
				resultMap = (Map<String,String>)entity.getBody().getData();
			}
		}
		return resultMap;
	}
	/**
	 * 獲取當前短信包信息
	 * @param sendUrl
	 * @param appAccount
	 * @param appKey
	 * @param price
	 * @return
	 */
	public static Map<String, String > findCurrentCloudFlowPacketDetail( String sendUrl, String appAccount, String appKey, String price ){
		String code = "500" ;
		Header header = new Header() ;
		header.setModule("cloud");
		header.setAction("CloudFlowPacketDetailService");
		header.setMethod("findCurrentCloudFlowPacket");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		Map<String ,String> map = new HashMap<String, String>() ;
		map.put("price", price);
		HttpClientBase httpClientBase = new HttpClientBase() ;
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		System.out.println("---------> entity : " + entity);

		Map<String,String> resultMap = Maps.newHashMap() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("查詢流量包明細成功：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap;
	}

	/**
	 * 下單工具
	 * @param sendUrl
	 * @param appAccount
	 * @param appKey
	 * @param price
	 * @return
	 */
	public static Map<String, String> saveAnOrder( String sendUrl, String appAccount, String appKey, String price, String resultUrl, String notifyUrl ){
		String code = "500" ;
		Header header = new Header() ;
		header.setModule("app");
		header.setAction("AppBuyWaterService");
		header.setMethod("saveAnOrder");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		Map<String ,String> map = new HashMap<String, String>() ;
		map.put("price", price);
		map.put("resultUrl",resultUrl);
		map.put("notifyUrl",notifyUrl);
		HttpClientBase httpClientBase = new HttpClientBase() ;
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		System.out.println("---------> entity : " + entity);

		Map<String,String> resultMap = Maps.newHashMap() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("下單成功：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap;
	}

	/**
	 * 申请模版
	 * @param //sendUrl		短信发送路径
	 * @param //appAccount	云平台应用账号
	 * @param //appKey		云平台应用密钥
	 * @param //thirdId       标题
	 * @param //template      模版
	 * @param //ifUse         是否使用
	 * @return
	 */
	public static Map<String,String> saveAppTemplate(String sendUrl ,Header header ,Object body) {
		String code = "500" ;
//		Header header = new Header() ;
		header.setModule("app");
		header.setAction("AppTemplateService");
		header.setMethod("saveAppTemplate");

		Map<String,String> map = (Map<String,String>) body ;
//		String sendUrl = headMap.get("sendUrl") ;
//		String appAccount = headMap.get("appAccount") ;
//		String appKey= headMap.get("appKey") ;

		/*String ifUse = map.get("ifUse") ;*/
		header.setAppAccount(StringUtils.isNotBlank(header.getAppAccount()) ? header.getAppAccount() : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(header.getAppKey()) ? header.getAppKey() : APP_KEY);
		header.setDeviceType("3");
		header.setDeviceId("0000000");

		/*map.put("ifUse", ifUse) ;*/
		HttpClientBase httpClientBase = new HttpClientBase() ;
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		System.out.println("---------> entity : " + entity);
		Map<String,String> resultMap = Maps.newHashMap() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("申请模版：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap;
	}

	/**
	 * 获取模版详情
	 * @param sendUrl		短信发送路径
	 * @param appAccount	云平台应用账号
	 * @param appKey		云平台应用密钥
	 * @return
	 */
	public static Map<String,String> findAppTemplatePage(String sendUrl ,String appAccount ,String appKey,String page,String rows) {
		String code = "500" ;
		Header header = new Header() ;
		header.setModule("cloud");
		header.setAction("CloudTemplateService");
		header.setMethod("findCloudTemplatePage");
		header.setAppAccount(StringUtils.isNotBlank(appAccount) ? appAccount : APP_ACCOUNT);
		header.setAppKey(StringUtils.isNotBlank(appKey) ? appKey : APP_KEY);
		header.setDeviceType("3");
		header.setDeviceId("0000000");
		Map<String ,String> map = new HashMap<String, String>() ;
		map.put("page", page) ;
		map.put("rows", rows) ;
		HttpClientBase httpClientBase = new HttpClientBase() ;
		RequestEntity entity = httpClientBase.post(header, map, sendUrl) ;
		System.out.println("---------> entity : " + entity);
		Map<String,String> resultMap = Maps.newHashMap() ;
		if(entity != null && "200".equals(entity.getCode()) && entity.getBody() != null && entity.getBody().getResult() != null) {
			code = entity.getBody().getResult().getBussinessCode() ;
			String msg = entity.getBody().getResult().getMsg() ;
			logger.info("获取模版详情：" + msg) ;
			if("200".equals(code)) {
				code = "0" ;
				resultMap = (Map<String,String>)entity.getBody().getData() ;
			}
		}
		return resultMap;
	}

	public static void main(String[] args) {
//		System.out.println(SmsCloudHttpUtils.sendSms(SMS_CLOUD_URL ,APP_ACCOUNT , APP_KEY ,"18734136405", "你在干什么？（窗友大学）") );
		System.out.println(SmsCloudHttpUtils.findSmsAppCount(SMS_CLOUD_URL ,APP_ACCOUNT , APP_KEY ) );
//		System.out.println(SmsCloudHttpUtils.saveAccount(SMS_CLOUD_URL ,/*APP_ACCOUNT , APP_KEY,*/"zonghui", "123456","Testing","BOOS","10086","123456@qq.com") );
//		System.out.println(SmsCloudHttpUtils.updateAccount(SMS_CLOUD_URL ,APP_ACCOUNT , APP_KEY ,"XXSFXYSMS110", "123456","北京大学","王五","18811043311","13@456.com") );
//		System.out.println(SmsCloudHttpUtils.findSendRecordPage(SMS_CLOUD_URL ,APP_ACCOUNT , APP_KEY , "1" , "2") );
//		System.out.println(SmsCloudHttpUtils.findAppBuyWaterPage(SMS_CLOUD_URL ,APP_ACCOUNT , APP_KEY , "1" , "10","2016-11-1,2016-11-9","1000","窗友大学","20") );
//		System.out.println(SmsCloudHttpUtils.findTemplatePage(SMS_CLOUD_URL ,APP_ACCOUNT , APP_KEY , "1" , "2") );
//		System.out.println(SmsCloudHttpUtils.addTemplate(resultMap ));
//		System.out.println(SmsCloudHttpUtils.updateTemplate(SMS_CLOUD_URL ,APP_ACCOUNT , APP_KEY , "18612241910" , "【窗友】您的验证码是32314，在5分钟内有效，如非本人操作请忽略本短信。(武汉科技大学)","1") );
//		System.out.println(SmsCloudHttpUtils.findCurrentCloudFlowPacketDetail(SMS_CLOUD_URL, "Testing", "123456", "23"));
//		System.out.println(SmsCloudHttpUtils.saveAnOrder(SMS_CLOUD_URL, "zonghui", "123456","20"));
//		Header header = new Header() ;
//		header.setAppAccount(APP_ACCOUNT);
//		header.setAppKey(APP_KEY);
//		Map<String,Object> body = new HashMap<String,Object>() ;
//		body.put("code","0") ;
//		body.put("mobile","13011012564") ;
//		body.put("content","【窗友】您的验证码是12314，在5分钟内有效，如非本人操作请忽略本短信。(窗友大学)") ;
//		Map<String,String> params = new HashMap<String,String>() ;
//		params.put("0","123141") ;
//		params.put("1","窗友大学") ;
//		body.put("params",params) ;
//		System.out.println(SmsCloudHttpUtils.sendSmsBySubmail(SMS_CLOUD_URL,header,body));
	}
}

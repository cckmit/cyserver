package com.cy.core.userProfile.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.aspectj.util.FileUtil;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.cy.base.action.AdminBaseAction;
import com.cy.base.entity.Message;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.alumni.service.AlumniService;
import com.cy.core.authCount.entity.AuthCount;
import com.cy.core.authCount.service.AuthCountService;
import com.cy.core.clientrelease.entity.Client;
import com.cy.core.clientrelease.entity.ClientModel;
import com.cy.core.clientrelease.service.ClientService;
import com.cy.core.dept.entity.Dept;
import com.cy.core.dept.service.DeptService;
import com.cy.core.sms.service.MsgSendService;
import com.cy.core.smsCode.entity.SmsCode;
import com.cy.core.smsCode.service.SmsCodeService;
import com.cy.core.userProfile.entity.GroupInfoEntity;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userProfile.entity.UserProfileSearchEntity;
import com.cy.core.userProfile.service.AppService;
import com.cy.core.userProfile.service.UserProfileService;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.core.userinfo.service.UserInfoService;
import com.cy.system.Global;
import com.cy.system.TigaseUtils;
import com.cy.system.UUID;
import com.cy.util.*;


@Namespace("/userProfilesss")
@Action(value = "userProfilesssAction", results = { @Result(name = "qrcode", location = "/qrcode/user_info.jsp") //type="redirect"
})
public class UserProfilesssAction extends AdminBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(UserProfilesssAction.class);

	/*
	 * 命令列表
	 */
	public static final String USER_PROFILE_UPLOAD_FILE = "1"; // 文件上传
	public static final String USER_PROFILE_GET_USER_BASE_INFO_ID_LIST = "2"; // 根据姓名获取与此姓名对应的基础信息数据库id编号列表
	public static final String USER_PROFILE_GET_REGISTER_CODE = "3"; // 根据手机号获取验证码
	public static final String USER_PROFILE_REGISTER = "4"; // 提交注册信息
	public static final String USER_PROFILE_UPDATE_USER_PROFILE = "5"; // 用户信息更改
	public static final String USER_PROFILE_IMAGE_FILE_UPLOAD = "6"; // 用户图像上传
	public static final String USER_PROFILE_USER_AUTHENTICATED = "7"; // 后台用于通知某用户获得认证
	public static final String USER_PROFILE_GET_CLASSMATES_INFO_LIST = "8"; // 获取班级同学id、姓名、账号列表
	public static final String USER_PROFILE_SEARCH_FOR_USERS = "9"; // 搜索用户
	public static final String USER_PROFILE_GET_USER_SELF_PROFILE = "10"; // 根据账号和密码获取用户自己的基本信息
	public static final String USER_PROFILE_GET_FRIEND_PROFILE = "11"; // 根据账号和密码，好友的账号，手机号或者基础id，获取用户好友的基本信息
	public static final String USER_PROFILE_UPDATE_GROUP_INFO = "12"; // 修改群表信息
	public static final String USER_PROFILE_GET_GROUP_INFO = "13"; // 根据账号、密码、群名获取群成员账号信息
	public static final String USER_PROFILE_UPDATE_CLASSMATE_TEL = "14"; // 数据挖掘更新班级同学手机号，并发送注册邀请短信
	public static final String USER_PROFILE_AUTHENTICATED = "15"; // 用户认证
	public static final String USER_PROFILE_CHANGE_PASSWORD = "16"; // 修改密码
	public static final String USER_PROFILE_GET_GROUP_MEMBERS_INFO = "17";// 批量获取群组非好友成员的信息
	// 账号，姓名，图片地址
	// 根据班级id获取班级名称
	public static final String USER_PROFILE_GET_CLASS_NAME = "18";

	// 搜索用户，新的接口
	public static final String USER_PROFILE_SEARCH_FOR_USERS_NEW = "22"; // 搜索用户2015-05-27新开发

	public static final String USER_PROFILE_UPDATE_USER_PROFILE_NEW = "23"; // 用户个人资料修改,2015-05-28新开发

	public static final String TIGASE_GROUP_OPERA = "24"; // tigase上的群组操作

	public static final String USER_QR_CODE_ADDRESS = "25"; // 为用户生成二维码的地址

	public static final String GET_NEARBY_USER = "26"; // 查询附近的校友

	public static final String GET_USER_BY_ACCOUNT_NUM = "27"; // 根据accountNum查询用户信息
	
	public static final String GET_ALNMNI = "28";	//查询所有的校友会
	
	public static final String GET_USER_BY_GROUP = "29";		//根据群ID返回群内所有用户信息
	
	public static final String GET_USER_BY_BASEINFO = "30";		//根据baseInfoIds查询用户

	/*
	 * 发送http请求的时候，ajax的name要设置为upload, httpclient的
	 * HttpEntity的addPart第一参数设置为upload同名，这样才能匹配到upload对象
	 * 同时需要定义好set,get方法，structs会自动封装
	 */
	private File upload;
	private String uploadFileName;
	private String fileNameUtf8;
	private String accountNum;
	private String password;
	private String jsonStr;
	private UserProfile userProfile;
	
	@Autowired
	private AppService appService;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private DeptService deptService;

	@Autowired
	private AuthCountService authCountService;

	@Autowired
	private MsgSendService msgSendService;

	@Autowired
	private SmsCodeService smsCodeService;

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private AlumniService alumniService;

	/**
	 * 访问地址http://127.0.0.1/cy_v1/userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action?jsonStr=提交用户信息
	 */
	public void doNotNeedSessionAndSecurity_userProfileHandler() {
		
		Message message = new Message();
		if (WebUtil.isNull(jsonStr)) {
			message.setMsg("jsonStr is empty!");
			message.setSuccess(false);
			super.writeJson(message);
			return;
		}

		JSONObject jsonObject = null;
		try {
			jsonObject = JSONObject.fromObject(jsonStr);
		} catch (Exception ex) {
			message.setMsg("jsonStr 解析错误");
			message.setSuccess(false);
			super.writeJson(message);
			return;
		}

		String command = jsonObject.containsKey("command") ? jsonObject.getString("command") : null;
		String contentStr = jsonObject.containsKey("content") ? jsonObject.getString("command") : null;

		if (jsonObject == null) {
			message.setMsg("jsonStr格式错误!");
			message.setSuccess(false);
			super.writeJson(message);
			return;
		}
		if (WebUtil.isNull(command)) {
			message.setMsg("command is empty!");
			message.setSuccess(false);
			super.writeJson(message);
			return;
		}

		if (WebUtil.isNull(contentStr)) {
			message.setMsg("content is empty!");
			message.setSuccess(false);
			super.writeJson(message);
			return;
		}
		JSONObject content = null;
		try {
			content = jsonObject.getJSONObject("content");
		} catch (Exception ex) {
			message.setMsg("json content 解析错误");
			message.setSuccess(false);
			super.writeJson(message);
			return;
		}

		try {
			if (command.equals(USER_PROFILE_UPLOAD_FILE)) {// 文件上传
				multipleFileUpload(content, message);
			} else if (command.equals(USER_PROFILE_GET_USER_BASE_INFO_ID_LIST)) {// id列表
				getUserBaseInfoIdList(content, message);
			} else if (command.equals(USER_PROFILE_GET_REGISTER_CODE)) {// 验证码
				getRegisterCode(content, message);
			} else if (command.equals(USER_PROFILE_REGISTER)) {// 注册信息
				register(content, message);
			} else if (command.equals(USER_PROFILE_UPDATE_USER_PROFILE)) {// 信息更新
				updateUserProfile(content, message);
			} else if (command.equals(USER_PROFILE_IMAGE_FILE_UPLOAD)) {// 图像上传
				imageFileUpload(content, message);
			} else if (command.equals(USER_PROFILE_USER_AUTHENTICATED)) {// 后台认证通知
				userAuthenticated(content, message);
			} else if (command.equals(USER_PROFILE_GET_CLASSMATES_INFO_LIST)) {// 班级同学列表
				getClassmatesInfoList(content, message);
			} else if (command.equals(USER_PROFILE_SEARCH_FOR_USERS)) {// 搜索用户
				searchForUsersNew(content, message);
			} else if (command.equals(USER_PROFILE_GET_USER_SELF_PROFILE)) {// 获取用户自己的基本信息
				//getUserSelfProfile(content, message);
				appService.selectAppLogin(content, message);
			} else if (command.equals(USER_PROFILE_GET_FRIEND_PROFILE)) {// 获取好友的基本信息
				getFriendProfile(content, message);
			} else if (command.equals(USER_PROFILE_UPDATE_GROUP_INFO)) {// 修改群表信息
				appService.updateGroupInfo(content, message);
			} else if (command.equals(USER_PROFILE_GET_GROUP_INFO)) {// 根据账号、密码、群名获取群成员账号信息
				getGroupInfo(content, message);
			} else if (command.equals(USER_PROFILE_UPDATE_CLASSMATE_TEL)) {// 数据挖掘更新班级同学手机号，并发送注册邀请短信
				updateClassmateTel(content, message);
			} else if (command.equals(USER_PROFILE_AUTHENTICATED)) {// 用户认证
				userSelfAuthenticated(content, message);
			} else if (command.equals(USER_PROFILE_CHANGE_PASSWORD)) {// 修改密码
				userChangePassword(content, message);
			} else if (command.equals(USER_PROFILE_GET_GROUP_MEMBERS_INFO)) {// 批量获取群组非好友成员的信息
				getGroupMembersInfo(content, message);
			} else if (command.equals(USER_PROFILE_GET_CLASS_NAME)) {// 根据班级id获取班级名称
				getDepartName(content, message);
			} else if (command.equals(USER_PROFILE_SEARCH_FOR_USERS_NEW)) {// 搜索用户2015-05-27新开发
				searchForUsersNew(content, message);
			} else if (command.equals(USER_PROFILE_UPDATE_USER_PROFILE_NEW)) {// 用户个人资料修改,2015-05-28新开发
				updateUserProfile(content, message);
			} else if (command.equals(TIGASE_GROUP_OPERA)) {//
				tigaseGroupOpera(content, message);
			} else if (command.equals(USER_QR_CODE_ADDRESS)) {// 为用户生成二维码的地址
				getUserQRCodeAddress(content, message);
			} else if (command.equals(GET_NEARBY_USER)) {// 获得附近人
				getNearbyUser(content, message);
			} else if (command.equals(GET_USER_BY_ACCOUNT_NUM)) {// 根据accountNum查询用户信息
				getUserInfoByAccountNum(content, message);
			} else if(command.equals(GET_ALNMNI)){		//查询所有校友会
				getAlumni(content, message);
			} else if(command.equals(GET_USER_BY_GROUP)){
				appService.selectUserByGroupId(content, message);	//根据群ID查询群内成员信息
			}else if(command.equals(GET_USER_BY_BASEINFO)){
				appService.selectUserByBaseInfoId(content, message);
			}	
			else {
				message.setMsg("command not found!");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("server exception!");
			message.setSuccess(false);
		}
		String json = JSON.toJSONStringWithDateFormat(message, "yyyy-MM-dd HH:mm:ss");
		
		HttpServletRequest request = this.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		String url = basePath + "userProfile/userProfileAction!doNotNeedSessionAndSecurity_userProfileHandler.action?jsonStr="+jsonStr;
		logger.info(url+"################returnStr:"+json.toString());
		super.writeJson(message);
	}

	/**
	 * 根据机构id获取机构名称
	 * 
	 * @param content
	 * @param message
	 */
	private void getDepartName(JSONObject content, Message message) {
		String baseInfoId = content.has("baseInfoId")?content.getString("baseInfoId"):null;
		if(WebUtil.isNull(baseInfoId)){		//协议检查
			message.setMsg("输入基础id");
			message.setSuccess(false);
			return;
		}
		try {
			// 根据基础id获取
			// 班级为最小单位
			if (baseInfoId.length() > 16) {
				baseInfoId = baseInfoId.substring(0, 16);
			}
			List<Dept> deptList = deptService.selectAllClass(baseInfoId);
			if (deptList == null || deptList.size() == 0) {
				message.setMsg("系统查不到此机构");
				message.setSuccess(false);
				return;
			}
			message.setMsg("查询成功!");
			message.setObj(deptList.get(0).getFullName());
			message.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			message.setMsg("系统错误");
			message.setSuccess(false);
		}
	}

	/**
	 * 批量获取群组非好友的信息
	 * 
	 * @param content
	 * @param message
	 */
	private void getGroupMembersInfo(JSONObject content, Message message) {
		
		String accountNum = content.has("accountNum")?content.getString("accountNum"):null;
		String password = content.has("password")?content.getString("password"):null;
		String accountNums = content.has("accountNums")?content.getString("accountNums"):null;
		if(WebUtil.isNull(accountNum)||WebUtil.isNull(password)||WebUtil.isNull(accountNums)){	//协议检查
			message.setMsg("账号,密码，联系人帐号列表不能为空!");
			message.setSuccess(false);
			return;
		}
		//自己
		UserProfile userProfileOwner = userProfileService.selectByAccountNum(accountNum);;
		if(userProfileOwner==null){
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}
		if(!password.equals(userProfileOwner.getPassword())){
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}
		List<UserProfileSearchEntity> userProfileSearchEntityList = new ArrayList<UserProfileSearchEntity>();
		Set<String> accountNumSet = new HashSet<String>();
		String accountNumArray[] = accountNums.split(",");
		for (int i = 0; i < accountNumArray.length; ++i) {
			if (!accountNumSet.contains(accountNumArray[i])) {
				UserProfile userProfile = userProfileService.selectByAccountNum(accountNumArray[i]);
				if (userProfile == null) {
					continue;
				}
				UserProfileSearchEntity userProfileSearchEntity = new UserProfileSearchEntity();
				// userProfileSearchEntity.setId(userProfile.getId());
				userProfileSearchEntity.setAccountNum(userProfile.getAccountNum());
				// userProfileSearchEntity.setPhoneNum(userProfile
				// .getPhoneNum());
				// userProfileSearchEntity.setEmail(userProfile
				// .getEmail());
				// userProfileSearchEntity.setAddress(userProfile
				// .getAddress());
				// userProfileSearchEntity.setBaseInfoId(userProfile
				// .getBaseInfoId());
				// userProfileSearchEntity.setChannels(userProfile
				// .getChannels());
				// userProfileSearchEntity.setIntrestType(userProfile
				// .getIntrestType());
				userProfileSearchEntity.setName(userProfile.getName());
				userProfileSearchEntity.setSex(userProfile.getSex());
				// userProfileSearchEntity.setSign(userProfile
				// .getSign());
				userProfileSearchEntity.setPicture(userProfile.getPicture());
				// userProfileSearchEntity
				// .setAuthenticated(userProfile
				// .getAuthenticated());
				userProfileSearchEntityList.add(userProfileSearchEntity);
			}
		}
		
		if (userProfileSearchEntityList.size() > 0) {
			message.setMsg("查询成功!");
			message.setObj(userProfileSearchEntityList);
			message.setSuccess(true);
		} else {
			message.setMsg("没有联系人信息!");
			message.setSuccess(false);
		}
		
	}

	/** --修改密码-- * */
	private void userChangePassword(JSONObject content, Message message) {
		try {
			// 协议检查
			if (!content.containsKey("phoneNum")
					|| !content.containsKey("password")
					|| !content.containsKey("checkCode")) {
				message.setMsg("数据格式错误!,手机号，新密码，验证码不能为空");
				message.setSuccess(false);
				return;
			}
			String phoneNum = content.getString("phoneNum");
			String password = content.getString("password");
			String checkCode = content.getString("checkCode");

			if (WebUtil.isNull(phoneNum)) {
				message.setMsg("请输入手机号!");
				message.setSuccess(false);
				return;
			}
			if (WebUtil.isNull(password)) {
				message.setMsg("请输入密码!");
				message.setSuccess(false);
				return;
			}
			if (WebUtil.isNull(checkCode)) {
				message.setMsg("请输入验证码!");
				message.setSuccess(false);
				return;
			}

			// 查询用户是否存在
			UserProfile userProfileTemp = userProfileService
					.selectByPhoneNum(phoneNum);
			if (userProfileTemp == null) {
				// message.setMsg("数据格式错误!");
				message.setMsg("该用户不存在!");
				message.setSuccess(false);
				return;
			}

			// 核对短信验证码
			SmsCode smsCode = smsCodeService.selectByTelId(phoneNum, checkCode);
			if (smsCode == null || !checkCode.equals(smsCode.getSmsCode())) {
				message.setMsg("验证码错误!");
				message.setSuccess(false);
				return;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(smsCode.getCreateTime());
			calendar.add(Calendar.MINUTE, 10); // 10分钟
			Date valid = calendar.getTime();
			if (valid.before(new Date())) {
				message.setMsg("验证码过期!");
				message.setSuccess(false);
				return;
			}

			// 修改密码
			boolean flag = true;
			String authenticated = userProfileTemp.getAuthenticated();
			// 认证用户需要修改tigase服务器上的密码
			if (authenticated != null && !authenticated.equals("0")) {
				TigaseUtils tigaseUtils = TigaseUtils.getInstance();
				flag = tigaseUtils.changePassword(userProfileTemp
						.getAccountNum(),
				/* userProfileTemp.getPassword(), */password);
			}
			/*
			 * if (flag) { userProfileTemp.setPassword(password);
			 * userProfileService.update(userProfileTemp);
			 * 
			 * message.setMsg("修改成功!"); message.setSuccess(true); } else {
			 * message.setMsg("系统错误!"); message.setSuccess(true); }
			 */
			userProfileTemp.setPassword(password);
			userProfileService.update(userProfileTemp);
			message.setMsg("修改成功!");
			message.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
			message.setMsg("服务器发生异常!，可能是json格式错误");
			message.setSuccess(false);
			return;
		}
	}

	/**
	 * 用户自己填写数据认证
	 * 
	 * @param content
	 * @param message
	 */
	private void userSelfAuthenticated(JSONObject content, Message message) {

		String phoneNum = content.containsKey("phoneNum") ? content.getString("phoneNum") : null;
		String password = content.containsKey("password") ? content.getString("password") : null;
		if (WebUtil.isNull(phoneNum) || WebUtil.isNull(password)) {// //
			// 协议检查
			message.setMsg("数据格式错误,手机号和密码不能为空!");
			message.setSuccess(false);
			return;
		}
		userProfile = userProfileService.selectByPhoneNum(phoneNum);
		if (userProfile == null) {
			message.setMsg("手机号错误,或该账号不存在!");
			message.setSuccess(false);
			return;
		}
		// 判断是否认证过
		String authenticated = userProfile.getAuthenticated();
		if (!"0".equals(authenticated)) {
			message.setMsg("该账号已经认证过!");
			message.setSuccess(false);
			return;
		}
		// 核对密码
		if (password == null || !password.equals(userProfile.getPassword())) {
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}
		userSelfAuthenticatedInner(content, message);

	}

	/**
	 * 认证用户
	 * 
	 * @param content
	 * @param message
	 */
	private void userSelfAuthenticatedInner(JSONObject content, Message message) {
		try {
			/*
			 * 根据姓名，同学,基础信息数据库id，进行身份鉴别
			 */
			boolean checkRes = checkUserIdentification(content, message);
			// 带有认证数据，则认证
			if (checkRes) {
				if (userProfile == null) {
					userProfile = new UserProfile();
					userProfile.setAccountNum(content.getString("accountNum"));
					userProfile.setPassword(content.getString("password"));
				}
				if (content2UserProfile(content, message)) {// 获取content内容
					UserInfo userInfoTemp = null;
					/*
					 * 同班同名校验
					 */
					List<UserInfo> userInfoListTemp = userInfoService.selectUserByClassIdAndName(userProfile.getName(),userProfile.getBaseInfoId().substring(0, 16));
					if (userInfoListTemp == null) {
						message.setMsg("系统错误!");
						message.setSuccess(false);
						return;
					} else if (userInfoListTemp.size() >= 1) {
						
						boolean flag = false;
						for (int i = 0; i < userInfoListTemp.size(); ++i) {
							userInfoTemp = userInfoListTemp.get(i);
							if (userInfoTemp.getAccountNum() == null|| userInfoTemp.getAccountNum().equals("")) {
								// String cardNum = userInfoTemp.getCard();
								// if (cardNum != null
								// && cardNum.substring(10).equals(
								// idNumberTemp)) {
								flag = true;
								userProfile.setBaseInfoId(userInfoTemp.getUserId());
								break;
								// }
							}
						}
					}
					// ##
					List<String> baseInfoIdList = new ArrayList<String>();
					JSONArray jsonArrayBaseInfoId = content
							.getJSONArray("baseInfoId");
					Set<String> baseInfoIdSet = new HashSet<String>();
					for (int i = 0; i < jsonArrayBaseInfoId.size(); ++i) {
						if (!baseInfoIdSet.contains(jsonArrayBaseInfoId.getString(i))) {
							baseInfoIdList.add(jsonArrayBaseInfoId.getString(i));
							baseInfoIdSet.add(jsonArrayBaseInfoId.getString(i));
						}
					}

					userSelfAuthenticatedInner(userProfile, baseInfoIdList,userInfoTemp, message);
					return;
				}
			}
			return;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
			message.setMsg("数据格式错误!");
			message.setSuccess(false);
			return;
		}
	}

	/**
	 * 认证用户
	 * 
	 * @param userProfile
	 * @param baseInfoIdList
	 * @param userInfoTemp
	 * @param message
	 */
	private void userSelfAuthenticatedInner(UserProfile userProfile,
			List<String> baseInfoIdList, UserInfo userInfoTemp,
			Message message) {
		try {

			/*
			 * tigase里面的创建用户账号和密码
			 */
			boolean ret = createChatAccount(userProfile);
			if (!ret) {
				// 尝试删除创建的聊天账号
				TigaseUtils tigaseUtils = TigaseUtils.getInstance();
				tigaseUtils.deleteAccount(userProfile.getAccountNum());
				message.setMsg("系统错误，聊天账号已经存在或其他异常");
				message.setSuccess(false);
				return;
			} else {
				/*
				 * 认证该用户 更新用户账号到基础数据表
				 */
				// for (int i = 0; i < userInfoList.size(); ++i) {
				// UserInfo userInfo = userInfoList.get(i);
				try {
					userInfoTemp.setAccountNum(userProfile.getAccountNum());
					userInfoService.updateUserAccountNum(userInfoTemp);
				} catch (Exception e) {
					logger.error(e, e);
					// 尝试删除创建的聊天账号
					TigaseUtils tigaseUtils = TigaseUtils.getInstance();
					tigaseUtils.deleteAccount(userProfile.getAccountNum());
					message.setMsg("系统错误或其他异常");
					message.setSuccess(false);
					return;
				}

				// tigase上创建节点
				try {
					appService.createInitClass(userProfile);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				try {
					// 设置默认性别
					userProfile.setAuthenticated("1");

					// 先获取性别 0 男， 1 女
					String sex = "";
					sex = userInfoTemp.getSex();
					if (sex.equals("男")) {
						sex = "0";
					} else if (sex.equals("女")) {
						sex = "1";
					}
					userProfile.setSex(sex);

					// 随机生成默认图像
					/*
					 * 1-20使用系统自带图片，空串或者非法字符，随机设置为默认图片
					 */


					/*
					String picture = userProfile.getPicture();
					int i = 0;
					if (picture != null && !picture.equals("")) {
						try {
							i = Integer.parseInt(picture) % 10;
						} catch (Exception e) {
							e.printStackTrace();
							i = new Random().nextInt(10);
						}
					} else {
						i = new Random().nextInt(10);
					}

					if (i < 0) {
						i = -i;
					}
					if (!sex.equals("0"))
						i += 10;// 女性图片
					userProfile.setPicture("" + i);
					*/

					userProfileService.update(userProfile);
				} catch (Exception e) {
					logger.error(e, e);
					// userInfoTemp.setAccountNum("");
					// userInfoService.updateUserAccountNum(userInfoTemp);

					// 尝试删除创建的聊天账号
					TigaseUtils tigaseUtils = TigaseUtils.getInstance();
					tigaseUtils.deleteAccount(userProfile.getAccountNum());
					message.setMsg("系统错误或其他异常");
					message.setSuccess(false);
					return;
				}
			}

			message.setMsg("恭喜你，认证成功!");
			JSONArray jsonobj = JSONArray.fromObject(baseInfoIdList);
			message.setObj(jsonobj);
			message.setSuccess(true);

			// ##############################//
			// 修改userInfo的手机号
			String phone = this.userProfile.getPhoneNum();
			String accountNum = this.userProfile.getAccountNum();
			// 根据accountNum查询userInfo信息
			Map map = new HashMap();
			map.put("accountNum", accountNum);
			List<UserInfo> list = this.userInfoService.selectByAccountNum(map);
			if (list != null && list.size() > 0) {
				for (UserInfo info : list) {
					info.setTelId(phone);
					this.userInfoService.update(info);
				}
			}
			// ##############################//

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("系统错误，其他异常");
			message.setSuccess(false);
			return;
		}
	}

	/**
	 * 数据挖掘更新同学手机号
	 * 
	 * @param content
	 * @param j
	 */
	private void updateClassmateTel(JSONObject content, Message j) {
		
		String accountNum = content.has("accountNum")?content.getString("accountNum"):null;
		String password = content.has("password")?content.getString("password"):null;
		String gmid = content.has("Gmid")?content.getString("Gmid"):null;
		String name = content.has("Name")?content.getString("Name"):null;
		String telId = content.has("telId")?content.getString("telId"):null;
		if(WebUtil.isNull(accountNum)||WebUtil.isNull(password)||WebUtil.isNull(gmid)||WebUtil.isNull(name)||WebUtil.isNull(telId)){//协议检查
			j.setMsg("数据格式错误,帐号,密码,班级Id,姓名,手机号不能为空!");
			j.setSuccess(false);
			return;
		}
		
		
		try {
			userProfile = userProfileService.selectByAccountNum(accountNum);
			if (userProfile == null) {
				j.setMsg("账号不存在!");
				j.setSuccess(false);
				return;
			}
			
			if (!password.equals(userProfile.getPassword())) {//检查密码
				j.setMsg("密码错误!");
				j.setSuccess(false);
				return;
			}

			// 检查班级是否有此人
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("gmid", gmid.substring(0, 16));
			map.put("name", name);
			UserInfo userinfo = userInfoService.selectUserInfoByGmidAndName(map);
			if (userinfo == null) {
				j.setSuccess(false);
				j.setMsg("查询不到此人");
				return;
			}

			if ( !isMobileNO(telId)) {
				j.setMsg("手机号码错误!");
				j.setSuccess(false);
				return;
			}

			// 获取最新apk版本地址
			ClientModel clientModel = new ClientModel();
			clientModel.setStart(0);
			clientModel.setEnd(1000);
			List<Client> list = clientService.selectClient(clientModel);

			Collections.sort(list, new Comparator<Client>() {
				public int compare(Client user1, Client user2) {
					return user2.getCreateTime().compareTo(
							user1.getCreateTime());
				}
			});
			String url = "";
			if (WebUtil.isNull(url)) {
				url = "http://xyh.hust.edu.cn/app.html";
			}

			// 给该同学发送邀请短信
			StringBuffer sMsg = new StringBuffer();
			// sMsg.append("【").append(Global.sign).append("】").append(userProfile.getName()).append("正在使用校友联系软件，向您发送使用邀请。").append(url);
			sMsg.append("【").append(Global.sign).append("】").append(
					userProfile.getName()).append("邀请您使用母校社交服务app。")
					.append(url);

			sMsg.append("（").append(Global.schoolSign).append("）");
			logger.error("app url : " + url);

//			int res = msgSendService.sendSmsAppInvite(telId, sMsg.toString());
//			if (res == 0) {
//				res = msgSendService.sendSmsAppInvite(telId, sMsg.toString());
//			}
//			if (res == 0) {
//				logger.error("短信发送失败 : " + accountNum + " update class " + gmid
//						+ " " + name + " with tel:" + telId);
//				j.setMsg("系统错误，短信发送失败!");
//				j.setSuccess(false);
//				return;
//			}

			Date useTime = new Date();
			if (content.containsKey("useTime")) {
				String useTimeStr = content.getString("useTime");
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				// Date date = sdf.parse("2008-08-08 12:10:12");
				try {
					useTime = sdf.parse(useTimeStr);
				} catch (Exception e) {
					e.printStackTrace();
					useTime = new Date();
				}
			}

			// 更新数据库
			map.put("telId", telId);
			map.put("useTime", useTime);
			map.put("userId", userinfo.getUserId());
			int result = userInfoService.updateUserTelId(map, userinfo);
			if (result > 0) {
				// 日志记录更改
				logger.error(accountNum + " update class " + gmid + " " + name
						+ " with tel:" + telId + " at "
						+ useTime.toLocaleString());
				j.setSuccess(true);
				j.setMsg("更新成功");
			} else {
				if (result == -8) {
					j.setSuccess(true);
					j.setMsg("不用更新");
				} else {
					j.setSuccess(true);
					j.setMsg("更新失败");
				}
			}
		} catch (Exception e) {
			logger.error(e, e);
			j.setSuccess(false);
			j.setMsg("更新失败");
		}
	}

	/**--// 根据账号、密码、群名获取群成员账号信息--**/
	private void getGroupInfo(JSONObject content, Message message) {
		String accountNum = content.has("accountNum")?content.getString("accountNum"):null;
		String password = content.has("password")?content.getString("password"):null;
		String groupId = content.has("groupId")?content.getString("groupId"):null;
		if (WebUtil.isNull(accountNum) ||WebUtil.isNull(password)||WebUtil.isNull(groupId)) {// 协议检查
			message.setMsg("数据格式错误,帐号,密码,群id不能为空!");
			message.setSuccess(false);
			return;
		}
		//查询UserProfile
		UserProfile userProfile = userProfileService.selectByAccountNum(accountNum);
		if(userProfile==null){
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}
		//核对密码
		if(!password.equals(userProfile.getPassword())){
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}

		// 去重
		Set<String> groupIdSet = new HashSet<String>();
		List<String> groupIdList = new ArrayList<String>();
		String groupIds[] = groupId.split(",");
		for (int i = 0; i < groupIds.length; ++i) {
			if (!groupIdSet.contains(groupIds[i])) {
				groupIdSet.add(groupIds[i]);
				groupIdList.add(groupIds[i]);
			}
		}

		List<GroupInfoEntity> groupInfoEntityList = userProfileService.selectByGroupId(groupIdList);
		if (groupInfoEntityList == null|| groupInfoEntityList.size() < 1) {
			message.setObj(null);
			message.setMsg("没有此群!");
			message.setSuccess(false);
			return;
		}
		message.setMsg("查询成功!");
		message.setObj(groupInfoEntityList);
		message.setSuccess(true);
	}

	

	private String generateStringFromList(List<String> membersAccountsList) {
		try {
			int size = membersAccountsList.size();
			if (size == 0) {
				return "";
			}

			StringBuffer buf = new StringBuffer();
			buf.append(membersAccountsList.get(0));
			for (int i = 1; i < size; ++i) {
				buf.append(",").append(membersAccountsList.get(i));
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**--获得好友基本信息--**/
	private void getFriendProfile(JSONObject content, Message message) {
		String accountNum = content.has("accountNum")?content.getString("accountNum"):null;
		String password = content.has("password")?content.getString("password"):null;
		
		String friendAccount = content.has("friendAccount")?content.getString("friendAccount"):null;
		String baseInfoId = content.has("baseInfoId")?content.getString("baseInfoId"):null;
		String phoneNum = content.has("phoneNum")?content.getString("phoneNum"):null;
		
		
		if(WebUtil.isNull(accountNum)||WebUtil.isNull(password)){//协议检查
			message.setMsg("账号,密码,好友帐号不能为空!");
			message.setSuccess(false);
			return;
		}
		UserProfile userProfileOwner = userProfileService.selectByAccountNum(accountNum);
		if(userProfileOwner==null){
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}
		//核对密码
		if(!password.equals(userProfileOwner.getPassword())){
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}
		
		if(!WebUtil.isNull(friendAccount)){// 根据账号获取
			//查询好友
			userProfile = userProfileService.selectByAccountNum(friendAccount);
		}
		
		if(!WebUtil.isNull(baseInfoId)){//根据基础id获取
			//查询好友
			userProfile = userProfileService.selectByBaseInfoId(baseInfoId);
		}
		
		if(!WebUtil.isNull(phoneNum)){//根据手机号获取
			//查询好友
			userProfile = userProfileService.selectByPhoneNum(phoneNum);
		}
		
		if (userProfile == null) {
			message.setMsg("系统查不到此人");
			message.setSuccess(false);
			return;
		}
		UserProfileSearchEntity userProfileSearchEntity = new UserProfileSearchEntity();
		userProfileSearchEntity.setAccountNum(userProfile.getAccountNum());
		userProfileSearchEntity.setPhoneNum(userProfile.getPhoneNum());
		userProfileSearchEntity.setEmail(userProfile.getEmail());
		userProfileSearchEntity.setAddress(userProfile.getAddress());
		userProfileSearchEntity.setBaseInfoId(userProfile.getBaseInfoId());
		userProfileSearchEntity.setChannels(userProfile.getChannels());
		userProfileSearchEntity.setIntrestType(userProfile.getIntrestType());
		userProfileSearchEntity.setName(userProfile.getName());
		userProfileSearchEntity.setSex(userProfile.getSex());
		userProfileSearchEntity.setSign(userProfile.getSign());
		userProfileSearchEntity.setPicture(userProfile.getPicture());
		userProfileSearchEntity.setAuthenticated(userProfile.getAuthenticated());

		message.setMsg("查询成功!");
		message.setObj(userProfileSearchEntity);
		message.setSuccess(true);
		
	}

	/**--根据帐号密码获取自己的基本信息--**/
	private void getUserSelfProfile(JSONObject content, Message message) {
		String accountNum = content.has("accountNum")?content.getString("accountNum"):null;
		String password = content.has("password")?content.getString("password"):null;
		if(WebUtil.isNull(accountNum)||WebUtil.isNull(password)){		//协议检查
			message.setMsg("数据格式错误,帐号/手机号和密码不能为空!");
			message.setSuccess(false);
			return;
		}

		//根据帐号或者手机号
		if(isMobileNO(accountNum)){
			userProfile = userProfileService.selectByPhoneNum(accountNum);
		}else{
			userProfile = userProfileService.selectByAccountNum(accountNum);
		}
		if(userProfile==null){
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}
		//核对密码
		if(!password.equals(userProfile.getPassword())){
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}
		
		UserProfileSearchEntity userProfileSearchEntity = new UserProfileSearchEntity();
		userProfileSearchEntity.setAccountNum(userProfile.getAccountNum());
		userProfileSearchEntity.setEmail(userProfile.getEmail());
		userProfileSearchEntity.setPhoneNum(userProfile.getPhoneNum());
		userProfileSearchEntity.setAddress(userProfile.getAddress());
		// channel和intrestType如果为空则设置初始值
		String channel = userProfile.getChannels();
		String intrestType = userProfile.getIntrestType();
		if (WebUtil.isNull(channel)) {
			channel = "母校新闻,总会快递";
		}
		if (WebUtil.isNull(intrestType)) {
			intrestType = "要闻,推荐";
		}
		userProfileSearchEntity.setChannels(channel);
		userProfileSearchEntity.setIntrestType(intrestType);
		userProfileSearchEntity.setSign(userProfile.getSign());
		userProfileSearchEntity.setAuthenticated(userProfile.getAuthenticated());
		userProfileSearchEntity.setGroupName(userProfile.getGroupName());
		userProfileSearchEntity.setName(WebUtil.isNull(userProfile.getName()) ? "" : userProfile.getName()); // 姓名
		userProfileSearchEntity.setSex(WebUtil.isNull(userProfile.getSex()) ? "" : userProfile.getSex()); // 性别
		userProfileSearchEntity.setWorkUtil(WebUtil.isNull(userProfile.getWorkUtil()) ? "" : userProfile.getWorkUtil()); // 工作单位
		userProfileSearchEntity.setProfession(WebUtil.isNull(userProfile.getProfession()) ? "": userProfile.getProfession()); // 行业
		userProfileSearchEntity.setAddress(WebUtil.isNull(userProfile.getAddress()) ? "" : userProfile.getAddress()); // 地点
		userProfileSearchEntity.setHobby(WebUtil.isNull(userProfile.getHobby()) ? "" : userProfile.getHobby()); // 兴趣
		userProfileSearchEntity.setPosition(WebUtil.isNull(userProfile.getPosition()) ? "" : userProfile.getPosition()); // 职务
		userProfileSearchEntity.setPicture(WebUtil.isNull(userProfile.getPicture()) ? "" : userProfile.getPicture());
		userProfileSearchEntity.setAlumni_id(userProfile.getAlumni_id());
		String baseInfoId = userProfile.getBaseInfoId();
		//查询学校,院系，年级，班级，专业，查询user_info
		if(!WebUtil.isNull(baseInfoId)){
			
			//根据accountNum查询user_info得到的专业
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("accountNum", userProfile.getAccountNum());
			List<UserInfo> list = userInfoService.selectByAccountNum(queryMap);
			if(list!=null && list.size()!=0){
				StringBuffer buf = new StringBuffer();
				String baseInfo = "";
				for(int j=0;j< list.size();j++){
					String full_departName = "";
					UserInfo info = list.get(j);
					String schoolName = WebUtil.isNull(info.getSchoolName())?"null":info.getSchoolName() ;//学校
					String departName = WebUtil.isNull(info.getDepartName())?"null":info.getDepartName() ;//院系
					String gradeName = WebUtil.isNull(info.getGradeName())?"null":info.getGradeName();    //年级
					String className = WebUtil.isNull(info.getClassName())?"null":info.getClassName();    //班级
					if(j<list.size()-1){
						full_departName = schoolName+","+departName+","+gradeName+","+className+"_";
						baseInfo = baseInfo + info.getUserId() + ",";
					}else{
						full_departName = schoolName+","+departName+","+gradeName+","+className;
						baseInfo = baseInfo + info.getUserId();
					}
					buf.append(full_departName);
				}
				userProfileSearchEntity.setBaseInfoId(baseInfo);
				userProfileSearchEntity.setDepartName(buf.toString());
			}
		}
		message.setMsg("查询成功!");
		message.setObj(userProfileSearchEntity);
		message.setSuccess(true);
	}

	/** --查询校友,模糊查询条件性别(sex)，年龄(age)，行业(profession)，所在地(address)-- * */
	private void searchForUsersNew(JSONObject content, Message message) {

		String accountNum = content.has("accountNum") ? content.getString("accountNum") : null;
		String password = content.has("password") ? content.getString("password") : null;
		String name = content.has("name") ? content.getString("name").trim(): null;
		if (WebUtil.isNull(accountNum) || WebUtil.isNull(password)
				|| WebUtil.isNull(name)) {// 非空检查
			message.setMsg("帐号,密码,姓名不能为空");
			message.setSuccess(false);
			return;
		}
		// ##
		userProfile = userProfileService.selectByAccountNum(accountNum);
		if (userProfile == null) {
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}

		// 核对密码
		if (!password.equals(userProfile.getPassword())) {
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		// map.put("page", 1);
		// map.put("rows", 15);
		map.put("name", name);
		if (content.has("sex")) {
			map.put("sex", content.getString("sex"));
		}
		if (content.has("profession")) {
			map.put("profession", content.getString("profession"));
		}
		if (content.has("address")) {
			map.put("address", content.getString("address"));
		}
	
		//List<UserProfileSearchEntity> userProfileSearchEntityList = this.userProfileService.getUserProfileSearchEntity(map);
		List<Map<String,String>> mapList = this.userProfileService.getUserProfileSearchEntity(map);
		
		if (mapList == null|| mapList.size() == 0) {
			message.setMsg("找不到结果集!");
			message.setSuccess(false);
			return;
		} else {
			message.setMsg("查询成功!");
			message.setSuccess(true);
			message.setObj(mapList);
		}
	}

	/**--班级同学列表--**/
	private void getClassmatesInfoList(JSONObject content, Message message) {

		String classId = content.has("classId")?content.getString("classId"):null;
		if(WebUtil.isNull(classId)){	//协议检查
			message.setMsg("输入班级id!");
			message.setSuccess(false);
			return;
		}
		//查询班级同学
		List<UserInfo> userInfoList = userInfoService.selectUserByClassId(classId);
		//List<SimpleUserInfo> simpleUserInfoList = new ArrayList<SimpleUserInfo>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		if(userInfoList!=null && userInfoList.size()>0){
			for (int i = 0; i < userInfoList.size(); ++i) {
				UserInfo userInfo = userInfoList.get(i);
				Map<String, String> map = new HashMap<String, String>();
				map.put("userId", userInfo.getUserId());
				map.put("accountNum", userInfo.getAccountNum());
				map.put("userName", userInfo.getUserName());
				list.add(map);
			}
		}
		message.setMsg("查询成功!");
		message.setObj(list);
		message.setSuccess(true);
	}

	/**--注册--**/
	private void register(JSONObject content, Message message) {
		try {
			/*
			 * 核对验证码
			 */
			String checkCode = content.containsKey("checkCode") ? content.getString("checkCode") : null;
			String phoneNum = content.containsKey("phoneNum") ? content.getString("phoneNum") : null;
			String password = content.containsKey("password") ? content.getString("password") : null;
			String name = content.containsKey("name") ? content.getString("name") : null;
			if (WebUtil.isNull(checkCode) || WebUtil.isNull(phoneNum)
					|| WebUtil.isNull(password)) {// 协议检查
				message.setMsg("手机号,验证码,密码不能为空!");
				message.setSuccess(false);
				return;
			}
			if (!isMobileNO(phoneNum)) {
				message.setMsg("手机号错误!");
				message.setSuccess(false);
				return;
			}

			// 手机号是否已经有人使用了
			long count = userProfileService.countByPhoneNum(phoneNum);
			if (count != 0) {
				message.setMsg("手机号已被注册过!");
				message.setSuccess(false);
				return;
			}

			SmsCode smsCode = smsCodeService.selectByTelId(phoneNum, checkCode);
			if (smsCode == null) {
				message.setMsg("验证码不存在!");
				message.setSuccess(false);
				return;
			}
			if (!checkCode.equals(smsCode.getSmsCode())) {
				message.setMsg("验证码错误!");
				message.setSuccess(false);
				return;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(smsCode.getCreateTime());
			calendar.add(Calendar.MINUTE, 10); // 10分钟
			Date valid = calendar.getTime();
			if (valid.before(new Date())) {
				message.setMsg("验证码过期!");
				message.setSuccess(false);
				return;
			}

			UserProfile userProfileTemp = new UserProfile();
			userProfileTemp.setPhoneNum(phoneNum);
			userProfileTemp.setName(name);
			userProfileTemp.setPassword(password);
			userProfileTemp.setAuthenticated("0");
			userProfileService.save(userProfileTemp);

			message.setMsg("注册成功!");
			message.setObj(userProfileTemp.getAccountNum());
			message.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
			message.setMsg("数据格式错误!");
			message.setSuccess(false);
			return;
		}
	}

	/**
	 * 根据用户的班级基础id创建账号，并添加用户到相应的班级
	 * 
	 * @param userProfile
	 *            可以为null，则不添加
	 * @return
	 */
	private boolean createChatAccount(UserProfile userProfile) {
		try {
			TigaseUtils tigaseUtils = TigaseUtils.getInstance();
			String uAccountNum = userProfile.getAccountNum();
			String passwd = userProfile.getPassword();
			if (!tigaseUtils.createAccount(uAccountNum, passwd)) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*private boolean addClassmatesGroup(String uAccountNum, String passwd,
			String name, List<String> baseInfoIdList) {
		try {
			*//*
			 * 获取班级，班级同学
			 *//*
			TigaseUtils tigaseUtils = TigaseUtils.getInstance();
			ConnectionConfiguration config = new ConnectionConfiguration(
					Global.tigase_server_ip, Global.tigase_server_port,
					Global.tigase_server_domain);
			// config.setCompressionEnabled(true);
			// config.setSASLAuthenticationEnabled(true);
			config.setSecurityMode(SecurityMode.disabled);
			XMPPTCPConnection connection = new XMPPTCPConnection(config);// 开启连接
			connection.connect();
			connection.login(uAccountNum, passwd);

			Iterator<String> iterator = baseInfoIdList.iterator();
			while (iterator.hasNext()) {
				String deptId = iterator.next();
				// 班级id
				if (deptId.length() < 16) {
					continue;
				} else if (deptId.length() > 16) {
					deptId = deptId.substring(0, 16);
				}
				List<UserInfo> listUserInfo = userInfoService
						.selectUserByClassId(deptId);
				List<Dept> listDeptInfo = deptService.selectAllClass(deptId);
				String deptName = listDeptInfo.get(0).getDeptName();
				for (int j = 0; j < listUserInfo.size(); ++j) {
					String accountNum = listUserInfo.get(j).getAccountNum();
					if (accountNum != null && !accountNum.equals("")
							&& !accountNum.equals(uAccountNum)) {
						// 服务器目前只做单向添加，强制添加在手机端处理
						// UserProfile u = userProfileService
						// .selectByAccountNum(accountNum);
						// if (u != null) {
						try {
							try {
								// // 添加对方为自己的好友
								// tigaseUtils.addFriend(connection,
								// uAccountNum,
								// passwd, accountNum, *//*
								// * u . getName ( )
								// *//*
								// "", deptName);
							} catch (Exception e) {
								e.printStackTrace();
							}

							// XMPPTCPConnection connection2 = null;
							// try {
							// // 添加自己为对方的好友
							// ConnectionConfiguration config2 = new
							// ConnectionConfiguration(
							// Global.tigase_server_ip,
							// Global.tigase_server_port,
							// Global.tigase_server_domain);
							// // config.setCompressionEnabled(true);
							// // config.setSASLAuthenticationEnabled(true);
							// config2.setSecurityMode(SecurityMode.disabled);
							// connection2 = new XMPPTCPConnection(config2);//
							// 开启连接
							// connection2.connect();
							// } catch (Exception e) {
							// e.printStackTrace();
							// }
							//
							// try {
							// // 该账号有人登陆时就会报错，添加好友的措施得另外设计
							// connection2.login(u.getAccountNum(),
							// u.getPassword());
							// tigaseUtils.addFriend(connection2,
							// u.getAccountNum(), u.getPassword(),
							// uAccountNum, name, deptName);
							// } catch (Exception e) {
							// e.printStackTrace();
							// }
							//
							// try {
							// connection2.disconnect();
							// } catch (Exception e) {
							// e.printStackTrace();
							// connection2 = null;
							// }
						} catch (Exception e) {
							e.printStackTrace();
						}
						// }
					}
				}
			}

			try {
				connection.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				connection = null;
			}
			return true;
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		}

	}
*/
	private boolean checkUserIdentification(JSONObject content, Message message) {
		try {
			/*
			 * 取得基础信息数据库id
			 */
			JSONArray jsonArrayBaseInfoId = content.getJSONArray("baseInfoId");
			if (jsonArrayBaseInfoId == null || jsonArrayBaseInfoId.size() == 0) {
				message.setMsg("数据格式错误，基础信息数据库id!");
				message.setSuccess(false);
				return false;
			}

			/*
			 * 检查姓名
			 */
			String name = content.getString("name");
			if (name == null || name.equals("")) {
				message.setMsg("请输入姓名!");
				message.setSuccess(false);
				return false;
			}

			/*
			 * 检查同学
			 */
			JSONArray jsonArrayClassmates = content.getJSONArray("classmates");
			if (jsonArrayClassmates.size() < 3) {
				message.setMsg("请提供3个本班同学的姓名!");
				message.setSuccess(false);
				return false;
			}

			List<String> nameList = new ArrayList<String>();
			nameList.add(name);
			for (int i = 0; i < jsonArrayClassmates.size(); ++i) {
				nameList.add(jsonArrayClassmates.getString(i));
			}
			// 核对基础数据,查看该班是否有这几名同学
			String deptId = jsonArrayBaseInfoId.getString(0).substring(0, 16);// 截取班级编号
			if (!userInfoService.selectUserInClass(deptId, nameList)) {
				message.setMsg("所提供的同学不在同一班级!");
				message.setSuccess(false);
				return false;
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			message.setMsg("数据格式错误!");
			message.setSuccess(false);
			return false;
		}
	}

	private boolean content2UserProfile(JSONObject content, Message message) {
		try {
			JSONArray jsonArrayBaseInfoId = content.getJSONArray("baseInfoId");
			if (jsonArrayBaseInfoId == null || jsonArrayBaseInfoId.size() == 0) {
				message.setMsg("数据格式错误，基础信息数据库id!");
				message.setSuccess(false);
				return false;
			}
			StringBuffer baseInfoId = new StringBuffer();
			for (int i = 0; i < jsonArrayBaseInfoId.size() - 1; ++i) {
				baseInfoId.append(jsonArrayBaseInfoId.getString(i) + ",");
			}
			if (jsonArrayBaseInfoId.size() > 0) {
				baseInfoId.append(jsonArrayBaseInfoId
						.getString(jsonArrayBaseInfoId.size() - 1));
			}
			userProfile.setBaseInfoId(baseInfoId.toString());// 基础信息数据库id

			// 先获取性别 0 男， 1 女
			String sex = "";
			if (content.containsKey("sex")) {
				sex = content.getString("sex");
				if (sex != null && sex.equals("1")) {
					userProfile.setSex("1");
				} else if (sex != null && sex.equals("0")) {
					userProfile.setSex("0");
				}
			}

			// 随机生成默认图像
			/*
			 * 1-20使用系统自带图片，空串或者非法字符，随机设置为默认图片
			 */

			String picture = null;
			if (content.has("picture")) {
				picture = content.getString("picture");
			}
			int i = 0;
			// if (picture != null && !picture.equals("")) {
			// i = Integer.parseInt(picture) % 10;
			// } else {
			// i = ((int) Math.random()) % 10;
			// }
			// if (!sex.equals("0"))
			// i += 10;// 女性图片
			// userProfile.setPicture("" + i);
			userProfile.setPicture(picture);

			/*
			 * 设置姓名
			 */
			String name = content.getString("name");
			if (name == null || name.equals("")) {
				message.setMsg("请输入姓名!");
				message.setSuccess(false);
				return false;
			}
			userProfile.setName(name);

			String phoneNum = content.getString("phoneNum");
			if (phoneNum == null || phoneNum.equals("")
					|| !isMobileNO(phoneNum)) {
				message.setMsg("手机号错误!");
				message.setSuccess(false);
				return false;
			} else {
				userProfile.setPhoneNum(phoneNum);
			}

			String address = null;
			if (content.has("address")) {
				address = content.getString("address");
			}
			if (address != null) {
				userProfile.setAddress(address);
			} else {
				userProfile.setAddress("");
			}

			String sign = null;
			if (content.has("sign")) {
				sign = content.getString("sign");
			}
			if (sign != null) {
				userProfile.setSign(sign);
			} else {
				userProfile.setSign("");
			}

			String email = null;
			if (content.has("email")) {
				email = content.getString("email");
			}
			if (email != null) {
				userProfile.setEmail(email);
			} else {
				userProfile.setEmail("");
			}

			/*
			 * 兴趣类型
			 */
			JSONArray intrestTypeJsonArray = null;
			if (content.has("intrestType")) {
				intrestTypeJsonArray = content.getJSONArray("intrestType");
			}
			if (intrestTypeJsonArray == null) {
				userProfile.setIntrestType("");
			} else {
				StringBuffer intrestTypes = new StringBuffer();
				for (i = 0; i < intrestTypeJsonArray.size() - 1; ++i) {
					intrestTypes
							.append(intrestTypeJsonArray.getString(i) + ",");
				}
				if (intrestTypeJsonArray.size() > 0) {
					intrestTypes.append(intrestTypeJsonArray
							.getString(intrestTypeJsonArray.size() - 1));
				}
				userProfile.setIntrestType(intrestTypes.toString());
			}

			/*
			 * 频道选择
			 */
			JSONArray channelsJsonArray = null;
			if (content.has("channels")) {
				channelsJsonArray = content.getJSONArray("channels");
			}
			if (channelsJsonArray == null) {
				userProfile.setChannels(null);
			} else {
				StringBuffer channels = new StringBuffer();
				for (i = 0; i < channelsJsonArray.size() - 1; ++i) {
					channels.append(channelsJsonArray.getString(i) + ",");
				}
				if (channelsJsonArray.size() > 0) {
					channels.append(channelsJsonArray
							.getString(channelsJsonArray.size() - 1));
				}
				userProfile.setChannels(channels.toString());
			}

			return true;
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("提交数据格式错误!");
			message.setSuccess(false);
			return false;
		}
	}

	private void updateUserProfile(JSONObject content, Message message) throws SQLException {
		// 帐号,密码为必填项
		String accountNum = content.has("accountNum") ? content.getString("accountNum") : null;
		String password = content.has("password") ? content.getString("password") : null;

		if (WebUtil.isNull(accountNum) || WebUtil.isNull(password)) {// 协议检查
			message.setMsg("请输入账号或密码!");
			message.setSuccess(false);
			return;
		}

		userProfile = userProfileService.selectByAccountNum(accountNum);
		if (userProfile == null) {
			message.setMsg("无法更新，用户账号不存在!");
			message.setSuccess(false);
			return;
		}
		//核对密码
		if (!password.equals(userProfile.getPassword())) {
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}

		// 如果没有认证且带有基础id则做认证处理
		String auth = userProfile.getAuthenticated();
		if (content.has("baseInfoId") && (auth == null || !auth.equals("1"))) {
			this.userSelfAuthenticatedInner(content, message);
			return;
		}
		
		// 根据accountNum查询userInfo信息
		Map map = new HashMap();
		map.put("accountNum", accountNum);
		//List<UserInfo> list = this.userInfoService.selectByAccountNum(map);
		UserInfo userInfo = new UserInfo();
		userInfo.setAccountNum(accountNum);
		//开始更新
		if (content.has("picture")&& !WebUtil.isNull(content.getString("picture"))) {
			userProfile.setPicture("" + content.getString("picture"));
		} else {
			userProfile.setPicture(null);
		}

		if (content.has("phoneNum")
				&& !WebUtil.isNull(content.getString("phoneNum"))
				&& isMobileNO(content.getString("phoneNum"))) {

			userProfile.setPhoneNum(content.getString("phoneNum"));
			// userProfile里面的phoneNum是不能更改的
			userProfile.setPhoneNum(null);
			// 修改手机号同步到cy_user_info
			userInfo.setTelId(content.getString("phoneNum"));
		} else {
			userProfile.setPhoneNum(null);
		}

		if (content.has("sex") && !WebUtil.isNull(content.getString("sex"))) {
			userProfile.setSex(content.getString("sex"));
			// 性别同步到cy_user_info
			if ( "0".equals(content.getString("sex"))) {
				userInfo.setSex("男");
			} else if ("1".equals(content.getString("sex"))) {
				userInfo.setSex("女");
			}
		} else {
			userProfile.setSex(null);
		}

		// 未认证用户可以修改姓名
		if (content.has("name") && !WebUtil.isNull(content.getString("name"))
				&& (auth == null || !auth.equals("1"))) {
			userProfile.setName(content.getString("name"));
			// 姓名同步到cy_user_info
			userInfo.setUserName(content.getString("name"));
		} else {
			userProfile.setName(null);
		}

		if (content.has("sign") && !WebUtil.isNull(content.getString("sign"))) {
			userProfile.setSign(content.getString("sign"));
		} else {
			userProfile.setSign(null);
		}

		// 修改邮箱
		if (content.has("email")
				&& !WebUtil.isNull(content.getString("email"))) {
			userProfile.setEmail(content.getString("email"));
			// 邮箱同步到cy_user_info
			/*
			 * if(info!=null){
			 * info.setEmail(content.getString("email")); }
			 */
			userInfo.setEmail(content.getString("email"));
		} else {
			userProfile.setEmail(null);
		}

		// 修改地址
		if (content.has("address")
				&& !WebUtil.isNull(content.getString("address"))) {
			userProfile.setAddress(content.getString("address"));
			// 地址同步到cy_user_info
			userInfo.setResidentialArea(content.getString("address"));
		} else {
			// 如果地址为空，判断手机号,根据手机号来给用户选择归属地
			if (content.has("phoneNum")
					&& !WebUtil.isNull(content.getString("phoneNum"))
					&& isMobileNO(content.getString("phoneNum"))) {
				// 根据手机号得到归属地

			} else {
				userProfile.setAddress(null);
			}
		}

		String newPassword = null;
		if (content.has("newPassword")) {
			newPassword = content.getString("newPassword");
		}
		// 修改密码
		if (newPassword != null && !newPassword.equals(password)) {
			boolean flag = true;
			if (auth != null && auth.equals("1")) {
				// 认证用户需要修改tigase服务器上的密码
				TigaseUtils tigaseUtils = TigaseUtils.getInstance();
				flag = tigaseUtils.changePassword(accountNum,/* password, */
				newPassword);
			}
			if (flag) {
				userProfile.setPassword(newPassword);
			} else {
				message.setMsg("修改密码失败!");
				message.setSuccess(false);
				return;
			}
		} else {
			userProfile.setPassword(null);
		}

		/*
		 * 更新兴趣类型
		 */
		JSONArray intrestTypeJsonArray = null;
		if (content.has("intrestType")) {
			intrestTypeJsonArray = content.getJSONArray("intrestType");
		}
		if (intrestTypeJsonArray == null) {
			userProfile.setIntrestType(null);
		} else {
			StringBuffer intrestTypes = new StringBuffer();
			for (int i = 0; i < intrestTypeJsonArray.size() - 1; ++i) {
				intrestTypes.append(intrestTypeJsonArray.getString(i) + ",");
			}
			if (intrestTypeJsonArray.size() > 0) {
				intrestTypes.append(intrestTypeJsonArray
						.getString(intrestTypeJsonArray.size() - 1));
			}
			userProfile.setIntrestType(intrestTypes.toString());
		}

		/*
		 * 更新频道
		 */
		JSONArray channelsJsonArray = null;
		if (content.has("channels")) {
			channelsJsonArray = content.getJSONArray("channels");
		}
		if (channelsJsonArray == null) {
			userProfile.setChannels(null);
		} else {
			StringBuffer channels = new StringBuffer();
			for (int i = 0; i < channelsJsonArray.size() - 1; ++i) {
				channels.append(channelsJsonArray.getString(i) + ",");
			}
			if (channelsJsonArray.size() > 0) {
				channels.append(channelsJsonArray.getString(channelsJsonArray
						.size() - 1));
			}
			userProfile.setChannels(channels.toString());
		}

		/*
		 * 更新群信息，全删全建，手机端自己处理
		 */
		String groupName = null;
		if (content.has("groupName")) {
			groupName = content.getString("groupName");
		}
		if (groupName != null) {
			userProfile.setGroupName(groupName);
		} else {
			userProfile.setGroupName(null);
		}

		// 新增的字段
		if (content.has("workUtil")
				&& !WebUtil.isNull(content.getString("workUtil"))) {
			userProfile.setWorkUtil(content.getString("workUtil"));
			// 工作单位同步到cy_user_info
			userInfo.setWorkUnit(content.getString("workUtil"));
		} else {
			userProfile.setWorkUtil(null);
		}
		if (content.has("profession")
				&& !WebUtil.isNull(content.getString("profession"))) {
			userProfile.setProfession(content.getString("profession"));
			// 行业同步到cy_user_info
			userInfo.setIndustryType(content.getString("profession"));
		} else {
			userProfile.setProfession(null);
		}
		if (content.has("hobby")
				&& !WebUtil.isNull(content.getString("hobby"))) {
			userProfile.setHobby(content.getString("hobby"));
			
		} else {
			userProfile.setHobby(null);
		}
		if (content.has("position")&& !WebUtil.isNull(content.getString("position"))) {
			userProfile.setPosition(content.getString("position"));
			// 职务同步到cy_user_info
			userInfo.setPosition(content.getString("position"));
		} else {
			userProfile.setPosition(null);
		}

		// 修改地方校友会字段
		String alumni_id = content.has("alumni_id")?content.getString("alumni_id"):null;
		if(!WebUtil.isNull(alumni_id)&& WebUtil.toLong(alumni_id)>0L){
			userProfile.setAlumni_id(WebUtil.toLong(alumni_id));
			userInfo.setAlumniId(WebUtil.toLong(alumni_id));
		}else{
			userProfile.setAlumni_id(0L);
		}
		

		/*
		 * 添加班级 add 手机端通过添加基础id进行
		 */
		JSONArray jsonArrayBaseInfoId = null;
		if (content.has("baseInfoId")) {
			jsonArrayBaseInfoId = content.getJSONArray("baseInfoId");
		}
		if (jsonArrayBaseInfoId == null) {
			userProfile.setBaseInfoId(null);
			userProfileService.updateNew(userProfile, userInfo);
		} else {
			StringBuffer baseInfoId = new StringBuffer();
			/** 存储新增加的班级id */
			List<String> baseInfoIdList = new ArrayList<String>();
			Set<String> baseInfoIdSet = new HashSet<String>();
			String selfBaseInfoId = userProfile.getBaseInfoId();
			if (WebUtil.isNull(selfBaseInfoId)) {
				message.setMsg("您还未认证，无法添加班级!");
				message.setSuccess(false);
				return;
			}
			String tempBaseInfoIds[] = selfBaseInfoId.split(",");
			for (int i = 0; i < tempBaseInfoIds.length; ++i) {
				if (!baseInfoIdSet.contains(tempBaseInfoIds[i])) {
					baseInfoIdSet.add(tempBaseInfoIds[i]);
				}
			}

			baseInfoId.append(userProfile.getBaseInfoId());
			// 新班级
			String userId = null;
			for (int i = 0; i < jsonArrayBaseInfoId.size(); ++i) {
				if (!baseInfoIdSet.contains(jsonArrayBaseInfoId.getString(i))) {
					userId = jsonArrayBaseInfoId.getString(i);
					baseInfoIdList.add(userId);
					baseInfoId.append("," + userId);
					/*
					 * 更改对应班级用户的账号accountNum
					 */
					UserInfo info = new UserInfo();
					info.setUserId(userId);
					info.setAccountNum(accountNum);
					userInfoService.updateUserAccountNum(info);
					break;
				}
			}

			try {
				// 更新基础id
				userProfile.setBaseInfoId(baseInfoId.toString());
				// userProfileService.update(userProfile);
				userProfileService.updateNew(userProfile,userInfo);
				appService.createInitClass(userProfile);
			} catch (Exception ex) {
				ex.printStackTrace();
				message.setMsg("添加班级失败!");
				message.setSuccess(false);
				return;
			}
		}

		message.setMsg("更新成功!");
		message.setSuccess(true);
	}

	public static boolean isMobileNO(String mobiles) {
		if (mobiles == null) {
			return false;
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/*
	 * 根据手机号获取验证码
	 */
	public void getRegisterCode(JSONObject content, Message message) {
		String phoneNum = content.has("phoneNum")?content.getString("phoneNum"):null;
		String secretKey = content.has("secretKey")?content.getString("secretKey"):null;
		if (WebUtil.isNull(phoneNum)||WebUtil.isNull(secretKey)) {// 协议检查
			message.setMsg("数据格式错误,手机号和验证码不能为空!");
			message.setSuccess(false);
			return;
		}
		if (!isMobileNO(phoneNum)) {
			message.setMsg("手机号错误!");
			message.setSuccess(false);
			return;
		}
		if (!secretKey.equals(Global.phone_secret_key)) {
			message.setMsg("秘钥错误!");
			message.setSuccess(false);
			return;
		}

		StringBuffer sMsg = new StringBuffer();
		String code = UUID.getPassord();
		sMsg.append("【").append(Global.sign).append("】").append("验证码：").append(
				code).append("。若非您本人操作，请忽略此短信!");
		sMsg.append("（").append(Global.schoolSign).append("）");
//		int res = msgSendService.sendSmsCode(phoneNum, sMsg.toString(), code);

//		if (res == 0) {
//			res = msgSendService.sendSmsCode(phoneNum, sMsg.toString(), code);
//		}
//		if (res == 0) {
//			message.setMsg("系统错误，短信发送失败!");
//			message.setSuccess(false);
//			return;
//		}
		/*
		 * 发送验证码
		 */

		message.setMsg("已经发送!");
		message.setObj(code);
		message.setSuccess(true);
	}

	/*
	 * 根据姓名和学校编号获取与此姓名对应的基础信息数据库id编号列表
	 */
	public void getUserBaseInfoIdList(JSONObject content, Message message) {
		// 有账号则以账号为准
		String accountNum = null;
		String name = null;
		if (content.has("accountNum")) {
			accountNum = content.getString("accountNum");
			if (WebUtil.isNull(accountNum)) {
				message.setMsg("账号错误!");
				message.setObj(new Integer(3));
				message.setSuccess(false);
				return;
			}
		} else {
			name = content.getString("name");
			if (WebUtil.isNull(name)) {
				message.setMsg("姓名不能为空!");
				message.setObj(new Integer(1));
				message.setSuccess(false);
				return;
			}
		}

		/*
		 * 按姓名和学校代码查询基础数据库
		 */
		List<String> idList = new ArrayList<String>();
		List<UserInfo> userInfoList = null;
		if (!WebUtil.isNull(accountNum)) {
			UserProfile u = userProfileService.selectByAccountNum(accountNum);
			if(u==null){
				message.setMsg("用户不存在!");
				message.setObj(new Integer(10));
				message.setSuccess(false);
				return;
			}
			
			String baseids = u.getBaseInfoId();
			if (WebUtil.isNull(baseids)) {
				message.setMsg("用户未认证，服务器无法或者基础信息!");
				message.setObj(new Integer(5));
				message.setSuccess(false);
				return;
			}

			String[] baseida = baseids.split(",");
			userInfoList = new ArrayList<UserInfo>();
			for (int i = 0; i < baseida.length; ++i) {
				UserInfo userInfo = userInfoService.selectByUserId(baseida[i]);
				if (userInfo != null) {
					userInfoList.add(userInfo);
				}
			}
		} else if (name != null && !name.equals("")) {
			userInfoList = userInfoService.selectByUserName(name);
		}

		if (userInfoList == null) {
			message.setMsg("查询不到基础信息!");
			message.setObj(new Integer(4));
			message.setSuccess(false);
			return;
		}

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < userInfoList.size(); ++i) {
			UserInfo userInfo = userInfoList.get(i);
			// if (userInfo.getAccountNum() == null
			// || userInfo.getAccountNum().equals("")) {
			if (userInfo == null) {
				continue;
			}
			idList.add(userInfo.getUserId());

			try {
				List<Dept> deptList = deptService.selectAllClass(userInfo
						.getUserId().substring(0, 16));
				if (deptList == null || deptList.size() == 0) {
					continue;
				}
				buf.append(deptList.get(0).getFullName()).append("_");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// }
		}

		if (idList.size() > 0) {
			JSONArray jsonobj = JSONArray.fromObject(idList);
			String ret = buf.toString();
			message.setMsg(ret.substring(0, ret.lastIndexOf("_")));
			message.setObj(jsonobj);
			message.setSuccess(true);
		} else {
			message.setMsg("查询不到此人!");
			message.setSuccess(false);
		}
	}

	/*
	 * 检查账号是否有人已经注册
	 */
	public void checkAccountNum(JSONObject content, Message message) {
		try {
			
			String accountNum = content.has("accountNum")?content.getString("accountNum"):null;
			if(WebUtil.isNull(accountNum)){// 协议检查
				message.setMsg("数据格式错误,accountNum不能为空!");
				message.setSuccess(false);
				return;
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("accountNum", accountNum);
			long count = userProfileService.countByUserAccount(map);
			if (count != 0) {
				message.setMsg("此账号已经被注册!");
				message.setSuccess(false);
			} else {
				message.setMsg("此账号可以注册!");
				message.setSuccess(true);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("查询参数有误，查询出错");
			message.setSuccess(false);
		}
	}

	/**
	 * 用户图像文件上传 accountNum用户账号 password用户密码
	 */
	public void multipleFileUpload(JSONObject content, Message message) {
		String accountNum = content.has("accountNum")?content.getString("accountNum"):null;
		String password = content.has("password")?content.getString("password"):null;
		String type = content.has("type")?content.getString("type"):null;
		if(WebUtil.isNull(accountNum)||WebUtil.isNull(password)||WebUtil.isNull(type)){//协议检查
			message.setMsg("数据格式错误,帐号,密码,type不能为空!");
			message.setSuccess(false);
			return;
		}
		try {
			userProfile = userProfileService.selectByAccountNum(accountNum);
			if (userProfile == null) {
				message.setMsg("账号错误!");
				message.setSuccess(false);
				return;
			}
			if(!password.equals(userProfile.getPassword())){
				message.setMsg("密码错误!");
				message.setSuccess(false);
				return;
			}
			StringBuffer savePath = new StringBuffer(Global.DISK_PATH);
			// 文件保存目录URL
			StringBuffer saveUrl = new StringBuffer(Global.URL_DOMAIN);

			// 最大文件大小 20M
			long maxSize = 20 * 1024 * 1024;

			getResponse().setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(getRequest())) {
				message.setMsg("请选择文件。");
				message.setSuccess(false);
				return;
			}

			// 检查文件大小
			if (new FileInputStream(upload).available() > maxSize) {
				message.setMsg("上传文件大小超过限制。");
				message.setSuccess(false);
				return;
			}

			// 第一级目录
			savePath.append("all_user_files");
			saveUrl.append("all_user_files");
			// 检查目录
			File uploadDir1 = new File(savePath.toString());
			if (!uploadDir1.exists()) {
				uploadDir1.mkdirs();
			}
			// 检查目录写权限
			if (!uploadDir1.canWrite()) {
				message.setMsg("上传目录没有写权限。");
				message.setSuccess(false);
				return;
			}

			// 第二级目录
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			String uploadDate = df.format(new Date());
			savePath.append(File.separator).append(uploadDate);
			saveUrl.append(File.separator).append(uploadDate);
			// 检查目录
			File uploadDir3 = new File(savePath.toString());
			if (!uploadDir3.exists()) {
				uploadDir3.mkdirs();
			}

			// 第三级目录
			savePath.append(File.separator).append(userProfile.getAccountNum());
			saveUrl.append(File.separator).append(userProfile.getAccountNum());
			// 检查目录
			File uploadDir2 = new File(savePath.toString());
			if (!uploadDir2.exists()) {
				uploadDir2.mkdirs();
			}

			// 第四级目录
			savePath.append(File.separator).append(type);
			saveUrl.append(File.separator).append(type);
			// 检查目录
			File uploadDir4 = new File(savePath.toString());
			if (!uploadDir4.exists()) {
				uploadDir4.mkdirs();
			}

			// 文件名合成
			SimpleDateFormat df2 = new SimpleDateFormat("HHmmss");
			String uploadTime = df2.format(new Date());
			String fileName = fileNameUtf8;
			try {
				if (fileName == null) {
					fileName = uploadFileName;
					if (fileName == null || fileName.contains("?")) {
						message.setMsg("您的版本不支持中文文件名!");
						message.setSuccess(false);
						return;
					}
				}
				fileName = URLDecoder.decode(fileName, "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e, e);
				fileName = uploadFileName;
			}
			savePath.append(File.separator).append(uploadTime).append("_")
					.append(fileName);
			saveUrl.append(File.separator).append(uploadTime).append("_")
					.append(fileName);

			// 存放文件
			File uploadedFile = new File(savePath.toString());
			FileUtil.copyFile(upload, uploadedFile);

			StringBuffer log = new StringBuffer();
			log.append(userProfile.getAccountNum()).append(" upload file ")
					.append(" type : ").append(type).append(" filename : ")
					.append(savePath.toString());
			logger.log(Priority.ERROR, log.toString());
			message.setMsg("文件上传成功!");
			message.setObj(saveUrl.toString());
			message.setSuccess(true);
		} catch (Exception e) {
			message.setMsg("文件上传失败!");
			message.setSuccess(false);
			logger.error(e, e);
		}
	}

	/**
	 * 用户图像文件上传 accountNum用户账号 password用户密码
	 */
	public void imageFileUpload(JSONObject content, Message message) {
		
		String accountNum = content.has("accountNum")?content.getString("accountNum"):null;
		String password = content.has("password")?content.getString("password"):null;
		if(WebUtil.isNull(accountNum)||WebUtil.isNull(password)){// 协议检查
			message.setMsg("数据格式错误,帐号和密码不能为空!");
			message.setSuccess(false);
			return;
		}
		
		try {
			userProfile = userProfileService.selectByAccountNum(accountNum);
			if(userProfile==null){
				message.setMsg("账号错误!");
				message.setSuccess(false);
				return;
			}
			if(!password.equals(userProfile.getPassword())){
				message.setMsg("密码错误!");
				message.setSuccess(false);
				return;
			}
			
			String savePath = Global.DISK_PATH;

			// 文件保存目录URL
			String saveUrl = Global.URL_DOMAIN;

			// 定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put(accountNum, "gif,jpg,jpeg,png,bmp");

			// 最大文件大小
			long maxSize = 1048576;

			getResponse().setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(getRequest())) {
				message.setMsg("请选择文件。");
				message.setSuccess(false);
				return;
			}
			// 检查目录
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				message.setMsg("上传目录不存在。");
				message.setSuccess(false);
				return;
			}
			// 检查目录写权限
			if (!uploadDir.canWrite()) {
				message.setMsg("上传目录没有写权限。");
				message.setSuccess(false);
				return;
			}

			// 创建文件夹
			savePath += Global.FACE_FILE_DIR + "/";
			saveUrl += Global.FACE_FILE_DIR + "/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}

			// 创建文件夹
			savePath += accountNum + "/";
			saveUrl += accountNum + "/";
			File saveDirFile = new File(savePath);
			if (!saveDirFile.exists()) {
				saveDirFile.mkdirs();
			}

			// 检查文件大小
			if (new FileInputStream(upload).available() > maxSize) {
				message.setMsg("上传文件大小超过限制。");
				message.setSuccess(false);
				return;
			}
			// 检查扩展名
			String fileExt = uploadFileName.substring(
					uploadFileName.lastIndexOf(".") + 1).toLowerCase();
			if (!Arrays.<String> asList(extMap.get(accountNum).split(","))
					.contains(fileExt)) {
				message.setMsg("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(accountNum)
						+ "格式。");
				message.setSuccess(false);
				return;
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String newFileName = df.format(new Date()) + "_"
					+ new Random().nextInt(1000) + "." + fileExt;
			File uploadedFile = new File(savePath, newFileName);
			FileUtil.copyFile(upload, uploadedFile);

			String oldPicture = userProfile.getPicture();
			userProfile.setPicture(saveUrl + newFileName);
			userProfileService.update(userProfile);

			/*
			 * 删除原图片，非系统图片,以文件名长度区分
			 */
			try {
				// 如果是一个url地址，则为用户自己上传的图像，需要删除，否则为默认图像
				int index = oldPicture.lastIndexOf("/");
				if (index >= 0 && oldPicture.startsWith("http")) {
					String filePath = savePath
							+ oldPicture.substring(saveUrl.length());
					File tpmfile = new File(filePath);
					// 路径为文件且不为空则进行删除
					if (tpmfile.isFile() && tpmfile.exists()) {
						logger.log(Priority.ERROR, "delete picure file : "
								+ filePath);
						tpmfile.delete();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e, e);
			}

			message.setMsg("图片上传成功!");
			message.setObj(userProfile.getPicture());
			message.setSuccess(true);
		} catch (Exception e) {
			message.setMsg("系统异常");
			message.setSuccess(false);
			logger.error(e, e);
		}
	}

	/*
	 * 用户获得认证通知
	 */
	public void userAuthenticated(JSONObject content, Message message) {
		try {
			if (!content.has("accountNum") || !content.has("password")
					|| !content.has("accountNum2")) {// 协议检查
				message.setMsg("数据格式错误!");
				message.setSuccess(false);
				return;
			}
			String accountNum = content.getString("accountNum");
			String password = content.getString("password");

			/*
			 * 检查用户
			 */
			if (accountNum == null || accountNum.equals("")) {
				message.setMsg("请输入账号!");
				message.setSuccess(false);
				return;
			}
			userProfile = userProfileService.selectByAccountNum(accountNum);
			if (userProfile == null
					|| !password.equals(userProfile.getPassword())) {
				message.setMsg("用户不存在或者密码错误!");
				message.setSuccess(false);
				return;
			}

			String accountNum2 = content.getString("accountNum2");
			if (accountNum2 == null || accountNum2.equals("")) {
				message.setMsg("请输入被认证用户账号!");
				message.setSuccess(false);
				return;
			}
			UserProfile userProfile2 = userProfileService
					.selectByAccountNum(accountNum2);
			if (userProfile2 == null) {
				message.setMsg("被认证用户不存在!");
				message.setSuccess(false);
				return;
			}
			boolean flag = false;
			if (accountNum.equals("admin")) {// 后台管理员
				String secretKey = content.getString("secretKey");
				// 可配置
				String AUTHENTICATED_SECRET_KEY = new String(
						Global.authenticated_secret_key);
				if (secretKey != null
						&& secretKey.equals(AUTHENTICATED_SECRET_KEY)) {// 校验认证码
					flag = true;
				} else {
					message.setMsg("认证失败");
					message.setSuccess(false);
					return;
				}
			} else if (userProfile.getAuthenticated().equals("1")
					&& userProfile.getBaseInfoId().subSequence(0, 16).equals(
							userProfile2.getBaseInfoId().subSequence(0, 16))) {// 具有认证权限的用户
				// 且在同一班级
				String secretKey = content.getString("secretKey");
				// 可配置
				String AUTHENTICATED_SECRET_KEY = new String(
						Global.authenticated_secret_key);
				if (secretKey != null
						&& secretKey.equals(AUTHENTICATED_SECRET_KEY)) {// 校验认证码
					flag = true;
				} else {
					message.setMsg("认证失败");
					message.setSuccess(false);
					return;
				}
			} else {// 普通用户计数认证
				AuthCount authCount = authCountService
						.selectByAccountNum(accountNum2);
				if (authCount == null) {
					authCount = new AuthCount();
					authCount.setAccountNum(accountNum2);
					authCount.setAuthCount(1);
					authCountService.save(authCount);
				} else {
					if (authCount.getAuthCount() < 3) {
						authCount.setAuthCount(authCount.getAuthCount() + 1);
						authCountService.save(authCount);
					} else {
						// 认证改用户
						flag = true;
						authCountService.delete(accountNum2);
					}
				}
			}

			if (flag) {
				/*
				 * tigase里面的创建用户账号和密码
				 */
				String baseInfo = userProfile2.getBaseInfoId();
				String baseInfoArray[] = baseInfo.split(",");
				Set<String> baseInfoIdSet = new HashSet<String>();
				List<String> baseInfoIdList = new ArrayList<String>();
				for (int i = 0; i < baseInfoArray.length; ++i) {
					// 去除重复的id
					if (!baseInfoIdSet.contains(baseInfoArray[i])) {
						baseInfoIdSet.add(baseInfoArray[i]);
						baseInfoIdList.add(baseInfoArray[i]);
					}
				}
				boolean ret = createChatAccount(userProfile2);
				if (!ret) {
					message.setMsg("系统错误，聊天账号已经存在或其他异常");
					message.setObj(new Integer(3));
					message.setSuccess(false);
					return;
				} else {
					/*
					 * 认证该用户 更新用户账号到基础数据表
					 */
					List<UserInfo> userInfoList = userInfoService
							.selectCard(baseInfoIdList);
					for (int i = 0; i < userInfoList.size(); ++i) {
						UserInfo userInfo = userInfoList.get(i);
						userInfo.setAccountNum(accountNum2);
						userInfoService.updateUserAccountNum(userInfo);
					}
				}
			}

			/*
			 * 日志记录
			 */
			StringBuffer logString = new StringBuffer();
			logString.append(accountNum).append(" ").append(
					" authenticate user ").append(accountNum);
			logger.log(Priority.INFO, logString.toString());

			message.setMsg("认证通过");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("查询参数有误，查询出错");
			message.setSuccess(false);
		}
	}

	public void dataGrid() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("rows", rows);
		if (userProfile != null) {
			map.put("accountNum", userProfile.getAccountNum());
			map.put("name", userProfile.getName());
			map.put("phoneNum", userProfile.getPhoneNum()); // add by jiangling ,phon
		}
		super.writeJson(userProfileService.dataGrid(map));
	}

	/** --查看用户帐号--* */
	public void doNotNeedSecurity_view() {
		String accountNum = this.getRequest().getParameter("accountNum");
		// 根据accountNum查询用户帐号详情
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountNum", accountNum);
		//List<UserProfileSearchEntity> userProfileSearchEntityList = this.userProfileService.getUserProfileSearchEntity(map);
		List<Map<String,String>> mapList = this.userProfileService.getUserProfileSearchEntity(map);
		
		if (mapList != null&& mapList.size() == 1) {
			Map<String,String> entity = mapList.get(0);
			super.writeJson(entity);
		}
	}

	public void save() {
		Message message = new Message();
		try {
			userProfileService.save(userProfile);
			message.setMsg("保存成功");
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("保存失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/**
	 * 传入UserProfile表的主键id列表删除用户
	 */
	public void delete() {
		Message message = new Message();
		try {
			if (ids != null) {
				// 删除注册信息
				userProfileService.delete(ids);
				message.setMsg("删除成功");
				message.setSuccess(true);
			} else {
				message.setMsg("没有选择用户");
				message.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("删除失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	// void deleteByAccountNum(String accountNum) {
	// Message message = new Message();
	// try {
	// Map<String, Object> map = new HashMap<String, Object>();
	// map.put("accountNum", accountNum);
	// userProfileService.deleteByAccountNum(map);
	// message.setMsg("删除成功");
	// message.setSuccess(true);
	// } catch (Exception e) {
	// logger.error(e, e);
	// message.setMsg("删除失败");
	// message.setSuccess(false);
	// }
	// super.writeJson(message);
	// }

	public void update() {
		Message message = new Message();
		try {
			userProfileService.update(userProfile);
			message.setMsg("修改成功");
			message.setSuccess(true);

		} catch (Exception e) {
			logger.error(e, e);
			message.setMsg("修改失败");
			message.setSuccess(false);
		}
		super.writeJson(message);
	}

	/** --tigase数据库上的群操作-- * */
	public void tigaseGroupOpera(JSONObject content, Message message) {
		if (!content.has("opera") || !content.has("account")
				|| !content.has("groupAccount")) {// 协议检查
			message.setMsg("数据格式错误!");
			message.setSuccess(false);
			return;
		}
		String opera = content.getString("opera");
		String account = content.getString("account");
		String groupAccount = content.getString("groupAccount");

		if (WebUtil.isNull(opera) || WebUtil.isNull(account)
				|| WebUtil.isNull(groupAccount)) {
			message.setMsg("opera,account,groupAccount 不能为空");
			message.setSuccess(false);
			return;
		}
		if (opera == "addUser") { // 群组添加用户

		} else if (opera == "deleteUser") { // 群组删除用户

		} else if (opera == "quit") { // 退群

		} else if (opera == "deleteGroup") { // 删群

		} else if (opera == "creatGroup") { // 建群

		}
	}

	/** --用户生成二维码--* */
	public void getUserQRCodeAddress(JSONObject content, Message message)
			throws Exception {
		if (!content.has("accountNum")|| WebUtil.isNull(content.getString("accountNum"))) {// 协议检查
			message.setMsg("数据格式错误!");
			message.setSuccess(false);
			return;
		}
		String accountNum = content.getString("accountNum");
		// 自动生成URL
		HttpServletRequest request = this.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		String address = basePath
				+ "userProfile/userProfileAction!doNotNeedSessionAndSecurity_getUserInfoByQrCode.action";
		String sign = WebUtil.getBase64(accountNum);
		JSONObject json = new JSONObject();
		json.put("address", address + "?sign=" + sign);
		message.setObj(json);
		message.setSuccess(true);
	}

	/** --扫描2唯码时获得用户信息--* */
	public String doNotNeedSessionAndSecurity_getUserInfoByQrCode()
			throws Exception {
		HttpServletRequest request = this.getRequest();
		String sign = request.getParameter("sign");
		String accountNum = WebUtil.getFromBase64(sign);
		List<UserInfo> list = userInfoService.selectByAccountNum2FullName(accountNum);
//		UserProfile userProfile = this.userProfileService
//				.selectByAccountNum(accountNum);
//		// 查询学校,院系，年级，班级，专业
//		List<UserInfo> list = new ArrayList<UserInfo>();
//		if (userProfile != null) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("accountNum", accountNum);
//			list = userInfoService.selectByAccountNum(map);
//			String sex = userProfile.getSex();
//			userProfile.setSex("1".equals(sex) ? "女" : "男");
//			request.setAttribute("item", userProfile);
//		}
		request.setAttribute("item", list.get(0));
		request.setAttribute("list", list);
//		request.setAttribute("schoolName", list.get(0).getSchoolName());
		return "qrcode";
	}

	/** --通过accountNum返回用户的基本信息，2唯码使用，提供给手机端--* */
	public void getUserInfoByAccountNum(JSONObject content, Message message)
			throws Exception {
		if (!content.has("accountNum")|| WebUtil.isNull(content.getString("accountNum"))) {// 协议检查
			message.setMsg("数据格式错误!");
			message.setSuccess(false);
			return;
		}
		String accountNum = content.getString("accountNum");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountNum", accountNum);
		//List<UserProfileSearchEntity> userProfileSearchEntityList = this.userProfileService.getUserProfileSearchEntity(map);
		List<Map<String,String>> mapList = this.userProfileService.getUserProfileSearchEntity(map);
		if (mapList != null&& mapList.size() > 0) {
			Map<String,String> entity = mapList.get(0);
			message.setObj(entity);
			message.setSuccess(true);
			message.setMsg("查询成功!");
		} else {
			message.setSuccess(false);
			message.setMsg("找不到该用户!");
		}
	}

	/** --查询用户的附近校友信息--* */
	public void getNearbyUser(JSONObject content, Message message) {
		if (!content.has("accountNum")
				|| WebUtil.isNull(content.getString("accountNum"))) {// 协议检查
			message.setMsg("数据格式错误,accountNum不能为空!");
			message.setSuccess(false);
			return;
		}
		if (!content.has("password")
				|| WebUtil.isNull(content.getString("password"))) {
			message.setMsg("数据格式错误,密码不能为空!");
			message.setSuccess(false);
			return;
		}
		if (!content.has("mu_longitud")
				|| WebUtil.isNull(content.getString("mu_longitud"))) {
			message.setMsg("数据格式错误,GPS精度不能为空!");
			message.setSuccess(false);
			return;
		}
		if (!content.has("mu_latitude")
				|| WebUtil.isNull(content.getString("mu_latitude"))) {
			message.setMsg("数据格式错误,GPS纬度不能为空!");
			message.setSuccess(false);
			return;
		}

		String accountNum = content.getString("accountNum"); // 用户帐号
		String password = content.getString("password"); // 用户密码
		double mu_longitud = WebUtil.toDouble(content.getString("mu_longitud")); // 精度
		double mu_latitude = WebUtil.toDouble(content.getString("mu_latitude")); // 纬度

		if (mu_longitud == 0.0 || mu_latitude == 0.0) {
			message.setMsg("GPS经度纬度非法");
			message.setSuccess(false);
			return;
		}

		int radius = WebUtil.toInt(content.getString("radius")); // 半径单位米
		if (radius == 0) {
			// 默认查询10公里范围内校友
			radius = 10000;
		}
		// ##
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountNum", accountNum);
		map.put("password", password);

		// 查询当前用户自己的信息
		UserProfile userProfile = userProfileService
				.selectByAccountNum(accountNum);
		if (userProfile == null) {
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}
		if (!password.equals(userProfile.getPassword())) {
			message.setMsg("密码不正确!");
			message.setSuccess(false);
			return;
		}
		userProfile.setMu_longitud(mu_longitud);
		userProfile.setMu_latitude(mu_latitude);
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		userProfile.setGps_time(stamp);
		// 用户当前使用附近人功能 将当前用户的GPS坐标更新
		this.userProfileService.update(userProfile);
		// return new double[] { minLng,maxLng,minLat, maxLat};
		// min_longitud max_longitud min_latitude max_latitude
		double[] around = WebUtil.getAround(mu_longitud, mu_latitude, radius);
		Map<String, Object> queryMap = new HashMap<String, Object>();
		// queryMap.put("page", 1);
		// queryMap.put("rows", 1000);
		queryMap.put("accountNum", accountNum);
		queryMap.put("mu_longitud", mu_longitud);
		queryMap.put("mu_latitude", mu_latitude);
		queryMap.put("min_longitud", around[0]);
		queryMap.put("max_longitud", around[1]);
		queryMap.put("min_latitude", around[2]);
		queryMap.put("max_latitude", around[3]);
		// 查询附近的人
		//List<UserProfileSearchEntity> userProfileSearchEntityList = this.userProfileService.getUserProfileSearchEntity(queryMap);
		List<Map<String,String>> mapList = this.userProfileService.getUserProfileSearchEntity(queryMap);
		// 分别计算用户距离手机的距离
		if (mapList != null&& mapList.size() != 0) {
			for (Map<String,String> entity : mapList) {
				double targetLon = WebUtil.toDouble(entity.get("mu_longitud"));
				double targetLat = WebUtil.toDouble(entity.get("mu_latitude"));
				double distance = WebUtil.distance(mu_longitud, mu_latitude,targetLon, targetLat);
				entity.put("distance", String.valueOf((int)distance));
			}
		} else {
			mapList = new ArrayList<Map<String,String>>();
		}
		message.setMsg("查询成功!");
		message.setObj(mapList);
		message.setSuccess(true);
	}

	/**--查询所有的校友会集合--**/
	public void getAlumni(JSONObject content, Message message){
		//该方法不需要content传递任何参数
		List<Alumni> list = this.alumniService.selectAll( "地" );
		if(list != null && list.size()>0){
			message.setMsg("查询成功!");
			message.setObj(list);
			message.setSuccess(true);
		}else{
			message.setMsg("没有结果!");
			message.setSuccess(false);
		}
	}
	
	
	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public String getFileNameUtf8() {
		return fileNameUtf8;
	}

	public void setFileNameUtf8(String fileNameUtf8) {
		this.fileNameUtf8 = fileNameUtf8;
	}

	public void getById() {
		super.writeJson(userProfileService.selectById(userProfile
				.getAccountNum()));
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void main(String[] args) throws Exception {
		Object a = null;
	}
}

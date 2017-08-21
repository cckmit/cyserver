package com.cy.mobileInterface.baseinfo.service;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import com.cy.core.dept.dao.DeptMapper;
import com.cy.core.login.service.LoginService;
import com.cy.core.serviceNewsType.entity.ServiceNewsType;
import com.cy.core.smsCode.entity.SmsCode;
import com.cy.core.smsCode.service.SmsCodeService;
import com.cy.core.user.entity.UserAuth;
import com.cy.core.user.service.UserAuthService;
import com.cy.core.userProfile.service.UserProfileService;
import com.cy.core.userinfo.service.UserInfoService;
import com.cy.core.weiXin.entity.WeiXinAccount;
import com.cy.core.weiXin.entity.WeiXinUser;
import com.cy.core.weiXin.service.WeiXinUserService;
import com.cy.system.Global;
import com.cy.system.SecretUtil;
import com.cy.system.SystemUtil;
import com.cy.util.easemob.comm.utils.EasemobUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.common.utils.token.Constant;
import com.cy.common.utils.token.JwtUtil;
import com.cy.core.alumni.dao.AlumniMapper;
import com.cy.core.alumni.entity.Alumni;
import com.cy.core.mobileLocal.dao.MobileLocalMapper;
import com.cy.core.mobileLocal.entity.MobileLocal;
import com.cy.core.userProfile.dao.GroupInfoMapper;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.GroupInfoEntity;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userProfile.entity.UserProfileSearchEntity;
import com.cy.core.userinfo.dao.UserInfoMapper;
import com.cy.core.userinfo.entity.UserInfo;
import com.cy.mobileInterface.baseinfo.entity.BaseInfo;
import com.cy.mobileInterface.baseinfo.entity.BaseInfoId;
import com.cy.mobileInterface.baseinfo.entity.ClassInfo;
import com.cy.mobileInterface.baseinfo.entity.FriendProfile;
import com.cy.mobileInterface.baseinfo.entity.Login;
import com.cy.mobileInterface.baseinfo.entity.ChangePhoneNum;
import com.cy.mobileInterface.multipleFileUpload.entity.MultipleFileUpload;
import com.cy.system.TigaseUtils;
import com.cy.util.WebUtil;
import com.cy.util.file.DefaultFileUpload;
import com.cy.util.file.FileResult;

@Service("baseInfoService")
public class BaseInfoServiceImpl implements BaseInfoService {
	private static final Logger logger = Logger.getLogger(BaseInfoServiceImpl.class);
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private GroupInfoMapper groupInfoMapper;

	@Autowired
	private MobileLocalMapper mobileLocalMapper;

	@Autowired
	private AlumniMapper alumniMapper;

	@Autowired
	private SmsCodeService smsCodeService;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private DeptMapper deptMapper;

	@Autowired
	private LoginService loginService;

	@Autowired
	private WeiXinUserService weiXinUserService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private JwtUtil jwt;
	
	@Autowired
	private UserAuthService userAuthService;

	@Override
	public void getBaseInfoIdByName(Message message, String content) {
		BaseInfoId baseInfoId = JSON.parseObject(content, BaseInfoId.class);
		// 有账号以账号为准
		List<UserInfo> list = null;
		if (baseInfoId.getAccountNum() != null && baseInfoId.getAccountNum().length() != 0) {
			list = userInfoMapper.selectByAccountNum2FullName(baseInfoId.getAccountNum());
		} else {
			if (baseInfoId.getName() != null && baseInfoId.getName().length() != 0) {
				list = userInfoMapper.selectByName(baseInfoId.getName());
			}
		}

		if (list == null || list.size() == 0) {
			message.setMsg("查询不到用户");
			message.setSuccess(false);
			return;
		}
		StringBuffer classBuffer = new StringBuffer();
		List<String> baseIdList = new ArrayList<String>();
		for (UserInfo userInfo : list) {
			baseIdList.add(userInfo.getUserId());
			classBuffer.append(userInfo.getFullName());
			classBuffer.append("_");
		}
		String clazz = classBuffer.deleteCharAt(classBuffer.length() - 1).toString();
		message.setMsg(clazz);
		message.setSuccess(true);
		message.setObj(baseIdList);
		return;
	}

	@Override
	public void uploadPhoto(Message message, String content, File[] upload, String[] uploadFileName) {
		try {
			MultipleFileUpload multipleFileUpload = JSON.parseObject(content, MultipleFileUpload.class);
			if (multipleFileUpload == null) {
				message.setMsg("content is error");
				message.setSuccess(false);
				return;
			}
			UserProfile userProfile = userProfileMapper.selectByAccountNum(multipleFileUpload.getAccountNum());
			if (userProfile == null) {
				message.setMsg("account is not found!");
				message.setSuccess(false);
				return;
			}
			/*if (!userProfile.getPassword().equals(multipleFileUpload.getPassword())) {
				message.setMsg("password is error!");
				message.setSuccess(false);
				return;
			}*/
			DefaultFileUpload defaultFileUpload = new DefaultFileUpload();
			defaultFileUpload.setFileDir(multipleFileUpload.getAccountNum() + "/face");
			defaultFileUpload.setMaxSize(1048576 * 5);
			defaultFileUpload.setCheckSuffix(true);
			defaultFileUpload.setSuffix("gif,jpg,jpeg,png,bmp");
			StringBuffer urlsb = new StringBuffer();
			StringBuffer msgsb = new StringBuffer();
			for (int i = 0; i < upload.length; i++) {
				FileResult fileResult = defaultFileUpload.uploadFile(upload[i], uploadFileName[i]);
				urlsb.append(fileResult.getFileUrl());
				urlsb.append("|");
				msgsb.append(fileResult.getMsg());
				msgsb.append("|");
			}
			if (urlsb.length() > 0) {
				urlsb.deleteCharAt(urlsb.length() - 1);
				message.setObj(urlsb.toString());
			}
			if (msgsb.length() > 0) {
				msgsb.deleteCharAt(msgsb.length() - 1);
			}
			userProfile.setPictureUrl(null);
			userProfile.setPicture(urlsb.toString());
			userProfileMapper.updatePhoto(userProfile);
			// 同步到userInfo表
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("picUrl", userProfile.getPictureUrl());
			map.put("accountNum", multipleFileUpload.getAccountNum());
			userInfoMapper.updatePhoto(map);
			message.setMsg("头像上传成功!");
			message.setSuccess(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	//需要郭亚斌改的代码
	public void getClassmates(Message message, String content) {
		try {
			ClassInfo classInfo = JSON.parseObject(content, ClassInfo.class);
			List<UserInfo> userInfoList = userInfoMapper.selectUserByClassId(classInfo.getClassId());

//			Map<String, String> map = JSON.parseObject(content,Map.class);
//			String userId = map.get("userId");
//			List<UserInfo> userInfoList = userInfoMapper.selectUserByUserId(userId);

			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			if (userInfoList != null && userInfoList.size() > 0) {
				for (int i = 0; i < userInfoList.size(); ++i) {
					UserInfo userInfo = userInfoList.get(i);
					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("userId", userInfo.getUserId());
					if(StringUtils.isNotBlank(userInfo.getAccountNum())){
						map1.put("accountNum", userInfo.getAccountNum());
					}
					map1.put("userName", userInfo.getUserName());

					list.add(map1);
				}
			}
			message.setMsg("查询成功!");
			message.setObj(list);
			message.setSuccess(true);
			return;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void searchUserInfo(Message message, String content) {
		BaseInfo baseInfo = JSON.parseObject(content, BaseInfo.class);

		if (WebUtil.isEmpty(baseInfo.getAccountNum()) || WebUtil.isEmpty(baseInfo.getPassword()) || WebUtil.isEmpty(baseInfo.getName())) {// 非空检查
			message.setMsg("帐号,密码,姓名不能为空");
			message.setSuccess(false);
			return;
		}
		UserProfile userProfile = userProfileMapper.selectByAccountNum(baseInfo.getAccountNum());
		if (userProfile == null) {
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}

		// 核对密码
		if (!baseInfo.getPassword().equals(userProfile.getPassword())) {
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", baseInfo.getName());
		if (baseInfo.getSex() != null && baseInfo.getSex().length() > 0) {
			map.put("sex", baseInfo.getSex());
		}
		if (baseInfo.getProfession() != null && baseInfo.getProfession().length() > 0) {
			map.put("profession", baseInfo.getProfession());
		}
		if (baseInfo.getAddress() != null && baseInfo.getAddress().length() > 0) {
			map.put("address", baseInfo.getAddress());
		}

		List<UserProfile> userInfos = userProfileMapper.selectAlumni(map);

		List<Map<String, String>> mapList = new ArrayList<>();
		for (UserProfile userProfile2 : userInfos) {
			Map<String, String> entityMap = new HashMap<>();
			entityMap.put("name", WebUtil.isEmpty(userProfile2.getName()) ? "" : userProfile2.getName());// 姓名
			entityMap.put("sex", WebUtil.isEmpty(userProfile2.getSex()) ? "" : userProfile2.getSex());// 性别
			entityMap.put("profession", WebUtil.isEmpty(userProfile2.getProfession()) ? "" : userProfile2.getProfession());// 行业
			entityMap.put("address", WebUtil.isEmpty(userProfile2.getAddress()) ? "" : userProfile2.getAddress());// 地点
			entityMap.put("hobby", WebUtil.isEmpty(userProfile2.getHobby()) ? "" : userProfile2.getHobby());// 兴趣
			entityMap.put("picture", WebUtil.isEmpty(userProfile2.getPicture()) ? "" : userProfile2.getPicture()); // 用户图片
			entityMap.put("baseInfoId", userProfile2.getBaseInfoId());
			entityMap.put("accountNum", userProfile2.getAccountNum());
			entityMap.put("workUtil", WebUtil.isEmpty(userProfile2.getWorkUtil()) ? "" : userProfile2.getWorkUtil());
			entityMap.put("departName", WebUtil.isEmpty(userProfile2.getGroupName()) ? "" : userProfile2.getGroupName());
			mapList.add(entityMap);
		}

		if (mapList == null || mapList.size() == 0) {
			message.setMsg("找不到结果集!");
			message.setSuccess(false);
			return;
		} else {
			message.setMsg("查询成功!");
			message.setSuccess(true);
			message.setObj(mapList);
			return;
		}
	}


	/**
	 * 搜索好友(满足条件)
	 * @param message
	 * @param content
     */
	@Override
	public void searchUserProfile(Message message, String content) {
		BaseInfo baseInfo = JSON.parseObject(content, BaseInfo.class);

		if (WebUtil.isEmpty(baseInfo.getUserId()) || WebUtil.isEmpty(baseInfo.getName())) {// 非空检查
			message.setMsg("用户编号、搜索姓名不能为空");
			message.setSuccess(false);
			return;
		}

		UserProfile userProfile = userProfileMapper.selectByAccountNum(baseInfo.getUserId());
		if (userProfile == null) {
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", baseInfo.getName());
		if (baseInfo.getSex() != null && baseInfo.getSex().length() > 0) {
			map.put("sex", baseInfo.getSex());
		}
		if (baseInfo.getProfession() != null && baseInfo.getProfession().length() > 0) {
			map.put("profession", baseInfo.getProfession());
		}
		if (baseInfo.getAddress() != null && baseInfo.getAddress().length() > 0) {
			map.put("address", baseInfo.getAddress());
		}

		List<UserProfile> userInfos = userProfileMapper.selectAlumni(map);

		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (UserProfile userProfile2 : userInfos) {
			Map<String, String> entityMap = new HashMap<String, String>() ;
			entityMap.put("userId", WebUtil.isEmpty(userProfile2.getAccountNum()) ? "" : userProfile2.getAccountNum());// 用户编号
			entityMap.put("userName", WebUtil.isEmpty(userProfile2.getName()) ? "" : userProfile2.getName());// 姓名
			entityMap.put("sex", WebUtil.isEmpty(userProfile2.getSex()) ? "" : userProfile2.getSex());// 性别
			entityMap.put("profession", WebUtil.isEmpty(userProfile2.getProfession()) ? "" : userProfile2.getProfession());// 行业
			entityMap.put("address", WebUtil.isEmpty(userProfile2.getAddress()) ? "" : userProfile2.getAddress());// 地点
			entityMap.put("hobby", WebUtil.isEmpty(userProfile2.getHobby()) ? "" : userProfile2.getHobby());// 兴趣
			entityMap.put("pictureUrl", WebUtil.isEmpty(userProfile2.getPicture()) ? "" : userProfile2.getPicture()); // 用户图片
			entityMap.put("baseInfoId", userProfile2.getBaseInfoId());
			entityMap.put("workUtil", WebUtil.isEmpty(userProfile2.getWorkUtil()) ? "" : userProfile2.getWorkUtil());
			entityMap.put("departName", WebUtil.isEmpty(userProfile2.getGroupName()) ? "" : userProfile2.getGroupName());
			mapList.add(entityMap);
		}

		if (mapList == null || mapList.size() == 0) {
			message.setMsg("找不到结果集!");
			message.setSuccess(false);
			return;
		} else {
			message.setMsg("查询成功!");
			message.setSuccess(true);
			message.setObj(mapList);
			return;
		}
	}

	@Override
	public void selectAppLogin(Message message, String content) {
		try {
			Login login = JSON.parseObject(content, Login.class);
			boolean istoken=false;
						
			if (!WebUtil.isEmpty(login.getToken()))
			{
			    long t=userAuthService.CheckToken(login.getToken());
			    System.out.println("t="+String.valueOf(t));
			    
				if (t==Constant.RESCODE_SUCCESS)
				 {					
				   String usrid=userAuthService.GetAccount(login.getToken());
				   login.setAccountNum(usrid); //根据token取用户ID 访问登陆
				   istoken=true;
				 }
				else
				{   
					istoken=false;
					message.setMsg("token 无效!");
					message.setSuccess(false);
					message.setReturnId(String.valueOf(Constant.RESCODE_EXCEPTION)); //返回异常
					return;								
				}
			}
			
			
			if(!istoken) {
			if (WebUtil.isEmpty(login.getAccountNum()) || (WebUtil.isEmpty(login.getPassword()) && WebUtil.isEmpty(login.getVerificationCode()))) { // 协议检查
				message.setMsg("数据格式错误,帐号/手机号和密码/验证码不能为空!");
				message.setSuccess(false);
				return;
			}}
			
			
			UserProfile userProfile = userProfileMapper.selectByAccountNum(login.getAccountNum());
			if (userProfile == null) {
				message.setMsg("查询不到此账号!");
				message.setSuccess(false);
				return;
			}

			if (StringUtils.isNotBlank(userProfile.getAccountNum())) {
				// 更新用户认证信息
				Map<String, String> baseMap = userProfileMapper.selectBaseInfo(userProfile.getAccountNum());
				if (baseMap == null) {
					baseMap = Maps.newHashMap();
				}
				baseMap.put("accountNum", userProfile.getAccountNum());
				userProfileMapper.clearBaseInfoId(baseMap);
			}


			if (WebUtil.isEmpty(login.getPassword())) {
				// 燕山大学要的姓名验证
				if ("000440".equals(Global.deptNo)) {
					// 核对姓名
					if (StringUtils.isBlank(login.getName())) {
						message.init(false, "请提供姓名", null);
						return;
					} else if (!userProfile.getName().equals(login.getName())) {
						message.init(false, "姓名错误", null);
						return;
					}
				}

				
				if(!istoken) {
				// 核对验证码
				boolean checkRs = loginService.checkSMSCode(message, userProfile.getPhoneNum(), login.getVerificationCode());				
				  if (checkRs == false) {
					message.init(false, "验证码错误", null);
					return;
				  }
				}
				  
				  
			  } else 
			  {
				  
				  if(!istoken) {
					// 核对密码
					if (!login.getPassword().equals(userProfile.getPassword())) {
						message.setMsg("密码错误!");
						message.setSuccess(false);
						return;
					}
				  }
					
			  }
			
			
			
			Map<String, String> entityMap = new HashMap<String, String>();
			
		
			//==============生成token=========================
			
			if (!(WebUtil.isEmpty(login.getFromid()) ||WebUtil.isEmpty(login.getMeid())||
					WebUtil.isEmpty(login.getMacid())||WebUtil.isEmpty(login.getSysid())))
			{ //附加信息不为空，生成token并下发
				
				
				UserAuth userAuth=new UserAuth();
				userAuth.setClientMeid(login.getMeid()); //机器码
				userAuth.setClientMacid(login.getMacid()); //网卡
				userAuth.setClientSys(login.getSysid()); //OS				
				userAuth.setUserid(login.getAccountNum()); //用户账号			
				
				userAuth.setUsername(WebUtil.isEmpty(userProfile.getName()) ? "" : userProfile.getName());//用户姓名
	
				
				if (login.getFromid()!=null)
				    userAuth.setStype(login.getFromid()); //来源类型 0-APP,1-微信,2-网页，
				else
					userAuth.setStype("9"); //9-未知来源				
				
				String subject = JwtUtil.generalSubject(userAuth);				
				
				System.out.println(" subject ="+ subject);
				
						
				Map<String, String> tokenMap=jwt.createJWT(subject, "token");
				String token=tokenMap.get("token");
				String tokenexp=tokenMap.get("exp");
				
				userAuth.setToken(token); //token
				userAuth.setTokenExp(tokenexp); //token 有效期	
				userAuth.setTokenStatus("0");	//token 状态
				
				tokenMap.clear();				
				tokenMap = jwt.createJWT(subject,"refreshToken");	
				
				
				String refreshToken=tokenMap.get("token");  //刷新token
				String refreshTokenexp=tokenMap.get("exp"); //刷新token有效期
				tokenMap.clear();
				
				userAuth.setTokenFh(refreshToken); // 刷新用token
				userAuth.setTokenFhExp(refreshTokenexp); //刷新用token 有效期
				userAuth.setTokenFhStatus("0");			// 刷新用token	状态
				
				//使用Date
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				userAuth.setCreateTime(sdf.format(d));  //创建时间
				userAuth.setUpdateTime(sdf.format(d));  //更新时间
								
				//System.out.println("token="+token);
				//System.out.println("refreshToken="+refreshToken);
				
				entityMap.put("token", token);
				entityMap.put("refreshToken", refreshToken);
				
				//清除旧记录,当前用户+来源+机器码 为条件
				userAuthService.delete(userAuth);
				
				//添加新记录
				userAuthService.insert(userAuth);
			}
			
			//=========================================================================
			
			entityMap.put("accountNum", userProfile.getAccountNum());
			entityMap.put("email", WebUtil.isEmpty(userProfile.getEmail()) ? "" : userProfile.getEmail());
			entityMap.put("phoneNum", userProfile.getPhoneNum());
			String channel = userProfile.getChannels();
			String intrestType = userProfile.getIntrestType();
			// channel和intrestType如果为空则设置初始值
			if (WebUtil.isEmpty(channel)) {
				channel = "母校新闻,总会快递";
			}
			if (WebUtil.isEmpty(intrestType)) {
				intrestType = "要闻,推荐";
			}
			entityMap.put("channels", channel);
			entityMap.put("intrestType", intrestType);
			entityMap.put("sign", WebUtil.isEmpty(userProfile.getSign()) ? "" : userProfile.getSign());
			entityMap.put("authenticated", WebUtil.isEmpty(userProfile.getAuthenticated()) ? "" : userProfile.getAuthenticated());
			entityMap.put("groupName", WebUtil.isEmpty(userProfile.getGroupName()) ? "" : userProfile.getGroupName());
			entityMap.put("name", WebUtil.isEmpty(userProfile.getName()) ? "" : userProfile.getName());// 姓名
			entityMap.put("sex", WebUtil.isEmpty(userProfile.getSex()) ? "" : userProfile.getSex()); // 性别
			entityMap.put("workUtil", WebUtil.isEmpty(userProfile.getWorkUtil()) ? "" : userProfile.getWorkUtil());// 工作单位
			entityMap.put("profession", WebUtil.isEmpty(userProfile.getProfession()) ? "" : userProfile.getProfession());// 行业
			entityMap.put("address", WebUtil.isEmpty(userProfile.getAddress()) ? "" : userProfile.getAddress()); // 地点
			entityMap.put("hobby", WebUtil.isEmpty(userProfile.getHobby()) ? "" : userProfile.getHobby()); // 兴趣
			entityMap.put("position", WebUtil.isEmpty(userProfile.getPosition()) ? "" : userProfile.getPosition());// 职务
			entityMap.put("picture", WebUtil.isEmpty(userProfile.getPicture()) ? "" : userProfile.getPicture());// 图像
			entityMap.put("picture_xd", WebUtil.isEmpty(userProfile.getPicture_xd()) ? "" : userProfile.getPicture_xd());// 图像x相对路径
			entityMap.put("birthday", WebUtil.isEmpty(userProfile.getBirthday()) ? "" : userProfile.getBirthday());// 生日
			entityMap.put("alumni_id", userProfile.getAlumni_id() + "");
			entityMap.put("isChangedSex", userProfile.getIsChangedSex());
			entityMap.put("pictureRT", WebUtil.isEmpty(userProfile.getPictureUrl()) ? "" : userProfile.getPictureUrl());
			String baseInfoId = userProfile.getBaseInfoId();
			if (!WebUtil.isEmpty(baseInfoId)) {
				List<UserInfo> list = userInfoMapper.selectByAccountNum2FullName(userProfile.getAccountNum());
				if (list != null && list.size() != 0) {
					StringBuffer buf = new StringBuffer();
					String baseInfo = "";
					for (int j = 0; j < list.size(); j++) {
						String full_departName = "";
						if (j < list.size() - 1) {
							full_departName = list.get(j).getFullName() + "_";
							baseInfo = baseInfo + list.get(j).getUserId() + ",";
						} else {
							full_departName = list.get(j).getFullName();
							baseInfo = baseInfo + list.get(j).getUserId();
						}
						buf.append(full_departName);
					}
					entityMap.put("baseInfoId", baseInfo);
					entityMap.put("departName", buf.toString());
				}
			}

			// 检测环信用户是否存在,不如存在注册用户
			EasemobUtils.existsUserAndCreate(userProfile.getAccountNum(), userProfile.getPassword(), null);


			if (StringUtils.isNotBlank(login.getOpenId()) && StringUtils.isNotBlank(login.getAccountAppId())) {
				try {
					// 保存微信号信息
					WeiXinUser weiXinUser = weiXinUserService.saveUserInfoByOpenId(login.getOpenId(), login.getAccountAppId());
					weiXinUser.setAccountNum(userProfile.getAccountNum());
					weiXinUser.setIsFollow("1");
					weiXinUser.setUpdateBy(userProfile.getAccountNum());
					weiXinUserService.update(weiXinUser);

					// 保存用户信息
					if (StringUtils.isBlank(userProfile.getPicture()) && StringUtils.isNotBlank(weiXinUser.getHeadimgurl())) {
						userProfile.setPicture(weiXinUser.getHeadimgurl());
					}
					if (StringUtils.isBlank(userProfile.getSex()) && StringUtils.isNotBlank(weiXinUser.getSex())) {
						if ("1".equals(weiXinUser.getSex())) {
							userProfile.setSex("0");
						}else if ("2".equals(weiXinUser.getSex())) {
							userProfile.setSex("1");
						}
					}
					userProfileMapper.update(userProfile);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			if("1".equals(userProfile.getIsOneKeyAuth())){
				/**
				 * 如果刚激活激活一下认证信息们
				 */
				UserInfo userInfo = new UserInfo();
				userInfo.setAccountNum(userProfile.getAccountNum());
				userInfo.setIsOneKeyAuth("0");
				userInfoMapper.updateByAccountNum(userInfo);
				userProfile.setIsOneKeyAuth("0");
				userProfileMapper.update(userProfile);
			}

			message.setMsg("查询成功!");
			message.setObj(entityMap);
			message.setSuccess(true);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
	//重载方法
	@Override
	public void selectAppLogin(Message message, String content,String token) {
		try {
		
			
			Login login = JSON.parseObject(content, Login.class);
			boolean istoken=false;
		
			login.setToken(token);
			
			if (!WebUtil.isEmpty(login.getToken()))
			{
			    long t=userAuthService.CheckToken(login.getToken());
			    System.out.println("t="+String.valueOf(t));
			    
				if (t==Constant.RESCODE_SUCCESS)
				 {					
				   String usrid=userAuthService.GetAccount(login.getToken());
				   login.setAccountNum(usrid); //根据token取用户ID 访问登陆
				   istoken=true;
				 }
				else
				{   
					istoken=false;
					message.setMsg("token 无效!");
					message.setSuccess(false);
					message.setReturnId(String.valueOf(Constant.RESCODE_EXCEPTION)); //返回异常
					return;								
				}
				
			}else
			{   
			    message.setMsg("token 无效!");
				message.setSuccess(false);
				message.setReturnId(String.valueOf(Constant.RESCODE_EXCEPTION)); //返回异常
				return;								
			}
				
			
			
			if(!istoken) {
			if (WebUtil.isEmpty(login.getAccountNum()) || (WebUtil.isEmpty(login.getPassword()) && WebUtil.isEmpty(login.getVerificationCode()))) { // 协议检查
				message.setMsg("数据格式错误,帐号/手机号和密码/验证码不能为空!");
				message.setSuccess(false);
				return;
			}}
			
			
			UserProfile userProfile = userProfileMapper.selectByAccountNum(login.getAccountNum());
			if (userProfile == null) {
				message.setMsg("查询不到此账号!");
				message.setSuccess(false);
				return;
			}

			if (StringUtils.isNotBlank(userProfile.getAccountNum())) {
				// 更新用户认证信息
				Map<String, String> baseMap = userProfileMapper.selectBaseInfo(userProfile.getAccountNum());
				if (baseMap == null) {
					baseMap = Maps.newHashMap();
				}
				baseMap.put("accountNum", userProfile.getAccountNum());
				userProfileMapper.clearBaseInfoId(baseMap);
			}


			if (WebUtil.isEmpty(login.getPassword())) {
				// 燕山大学要的姓名验证
				if ("000440".equals(Global.deptNo)) {
					// 核对姓名
					if (StringUtils.isBlank(login.getName())) {
						message.init(false, "请提供姓名", null);
						return;
					} else if (!userProfile.getName().equals(login.getName())) {
						message.init(false, "姓名错误", null);
						return;
					}
				}

				
				if(!istoken) {
				// 核对验证码
				boolean checkRs = loginService.checkSMSCode(message, userProfile.getPhoneNum(), login.getVerificationCode());				
				  if (checkRs == false) {
					message.init(false, "验证码错误", null);
					return;
				  }
				}
				  
				  
			  } else 
			  {
				  
				  if(!istoken) {
					// 核对密码
					if (!login.getPassword().equals(userProfile.getPassword())) {
						message.setMsg("密码错误!");
						message.setSuccess(false);
						return;
					}
				  }
					
			  }
			
			
			
			Map<String, String> entityMap = new HashMap<String, String>();
			
		
			//==============生成token=========================
	 		
			if (!(WebUtil.isEmpty(login.getFromid()) ||WebUtil.isEmpty(login.getMeid())||
					WebUtil.isEmpty(login.getMacid())||WebUtil.isEmpty(login.getSysid())))
			  { //附加信息不为空，生成token并下发
				
				
				UserAuth userAuth=new UserAuth();
				userAuth.setClientMeid(login.getMeid()); //机器码
				userAuth.setClientMacid(login.getMacid()); //网卡
				userAuth.setClientSys(login.getSysid()); //OS				
				userAuth.setUserid(login.getAccountNum()); //用户账号			
				
				userAuth.setUsername(WebUtil.isEmpty(userProfile.getName()) ? "" : userProfile.getName());//用户姓名
	
				
				if (login.getFromid()!=null)
				    userAuth.setStype(login.getFromid()); //来源类型 0-APP,1-微信,2-网页，
				else
					userAuth.setStype("9"); //9-未知来源				
				
				String subject = JwtUtil.generalSubject(userAuth);				
				
				System.out.println(" subject ="+ subject);
				
						
				Map<String, String> tokenMap=jwt.createJWT(subject, "token");
				token=tokenMap.get("token");
				String tokenexp=tokenMap.get("exp");
				
				userAuth.setToken(token); //token
				userAuth.setTokenExp(tokenexp); //token 有效期	
				userAuth.setTokenStatus("0");	//token 状态
				
				tokenMap.clear();				
				tokenMap = jwt.createJWT(subject,"refreshToken");	
				
				
				String refreshToken=tokenMap.get("token");  //刷新token
				String refreshTokenexp=tokenMap.get("exp"); //刷新token有效期
				tokenMap.clear();
				
				userAuth.setTokenFh(refreshToken); // 刷新用token
				userAuth.setTokenFhExp(refreshTokenexp); //刷新用token 有效期
				userAuth.setTokenFhStatus("0");			// 刷新用token	状态
				
				//使用Date
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				userAuth.setCreateTime(sdf.format(d));  //创建时间
				userAuth.setUpdateTime(sdf.format(d));  //更新时间
								
				//System.out.println("token="+token);
				//System.out.println("refreshToken="+refreshToken);
				
				entityMap.put("token", token);
				entityMap.put("refreshToken", refreshToken);
				
				//清除旧记录,当前用户+来源+机器码 为条件
				userAuthService.delete(userAuth);
				
				//清理token相同, 避免重复添加相同记录
				userAuthService.deletebytoken(token);
				
				//添加新记录
				userAuthService.insert(userAuth);
			}
			
			//=========================================================================
			
			entityMap.put("accountNum", userProfile.getAccountNum());
			entityMap.put("email", WebUtil.isEmpty(userProfile.getEmail()) ? "" : userProfile.getEmail());
			entityMap.put("phoneNum", userProfile.getPhoneNum());
			String channel = userProfile.getChannels();
			String intrestType = userProfile.getIntrestType();
			// channel和intrestType如果为空则设置初始值
			if (WebUtil.isEmpty(channel)) {
				channel = "母校新闻,总会快递";
			}
			if (WebUtil.isEmpty(intrestType)) {
				intrestType = "要闻,推荐";
			}
			entityMap.put("channels", channel);
			entityMap.put("intrestType", intrestType);
			entityMap.put("sign", WebUtil.isEmpty(userProfile.getSign()) ? "" : userProfile.getSign());
			entityMap.put("authenticated", WebUtil.isEmpty(userProfile.getAuthenticated()) ? "" : userProfile.getAuthenticated());
			entityMap.put("groupName", WebUtil.isEmpty(userProfile.getGroupName()) ? "" : userProfile.getGroupName());
			entityMap.put("name", WebUtil.isEmpty(userProfile.getName()) ? "" : userProfile.getName());// 姓名
			entityMap.put("sex", WebUtil.isEmpty(userProfile.getSex()) ? "" : userProfile.getSex()); // 性别
			entityMap.put("workUtil", WebUtil.isEmpty(userProfile.getWorkUtil()) ? "" : userProfile.getWorkUtil());// 工作单位
			entityMap.put("profession", WebUtil.isEmpty(userProfile.getProfession()) ? "" : userProfile.getProfession());// 行业
			entityMap.put("address", WebUtil.isEmpty(userProfile.getAddress()) ? "" : userProfile.getAddress()); // 地点
			entityMap.put("hobby", WebUtil.isEmpty(userProfile.getHobby()) ? "" : userProfile.getHobby()); // 兴趣
			entityMap.put("position", WebUtil.isEmpty(userProfile.getPosition()) ? "" : userProfile.getPosition());// 职务
			entityMap.put("picture", WebUtil.isEmpty(userProfile.getPicture()) ? "" : userProfile.getPicture());// 图像
			entityMap.put("picture_xd", WebUtil.isEmpty(userProfile.getPicture_xd()) ? "" : userProfile.getPicture_xd());// 图像x相对路径
			entityMap.put("birthday", WebUtil.isEmpty(userProfile.getBirthday()) ? "" : userProfile.getBirthday());// 生日
			entityMap.put("alumni_id", userProfile.getAlumni_id() + "");
			entityMap.put("isChangedSex", userProfile.getIsChangedSex());
			entityMap.put("pictureRT", WebUtil.isEmpty(userProfile.getPictureUrl()) ? "" : userProfile.getPictureUrl());
			String baseInfoId = userProfile.getBaseInfoId();
			if (!WebUtil.isEmpty(baseInfoId)) {
				List<UserInfo> list = userInfoMapper.selectByAccountNum2FullName(userProfile.getAccountNum());
				if (list != null && list.size() != 0) {
					StringBuffer buf = new StringBuffer();
					String baseInfo = "";
					for (int j = 0; j < list.size(); j++) {
						String full_departName = "";
						if (j < list.size() - 1) {
							full_departName = list.get(j).getFullName() + "_";
							baseInfo = baseInfo + list.get(j).getUserId() + ",";
						} else {
							full_departName = list.get(j).getFullName();
							baseInfo = baseInfo + list.get(j).getUserId();
						}
						buf.append(full_departName);
					}
					entityMap.put("baseInfoId", baseInfo);
					entityMap.put("departName", buf.toString());
				}
			}

			// 检测环信用户是否存在,不如存在注册用户
			EasemobUtils.existsUserAndCreate(userProfile.getAccountNum(), userProfile.getPassword(), null);


			if (StringUtils.isNotBlank(login.getOpenId()) && StringUtils.isNotBlank(login.getAccountAppId())) {
				try {
					// 保存微信号信息
					WeiXinUser weiXinUser = weiXinUserService.saveUserInfoByOpenId(login.getOpenId(), login.getAccountAppId());
					weiXinUser.setAccountNum(userProfile.getAccountNum());
					weiXinUser.setIsFollow("1");
					weiXinUser.setUpdateBy(userProfile.getAccountNum());
					weiXinUserService.update(weiXinUser);

					// 保存用户信息
					if (StringUtils.isBlank(userProfile.getPicture()) && StringUtils.isNotBlank(weiXinUser.getHeadimgurl())) {
						userProfile.setPicture(weiXinUser.getHeadimgurl());
					}
					if (StringUtils.isBlank(userProfile.getSex()) && StringUtils.isNotBlank(weiXinUser.getSex())) {
						if ("1".equals(weiXinUser.getSex())) {
							userProfile.setSex("0");
						}else if ("2".equals(weiXinUser.getSex())) {
							userProfile.setSex("1");
						}
					}
					userProfileMapper.update(userProfile);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}

			if("1".equals(userProfile.getIsOneKeyAuth())){
				/**
				 * 如果刚激活激活一下认证信息们
				 */
				UserInfo userInfo = new UserInfo();
				userInfo.setAccountNum(userProfile.getAccountNum());
				userInfo.setIsOneKeyAuth("0");
				userInfoMapper.updateByAccountNum(userInfo);
				userProfile.setIsOneKeyAuth("0");
				userProfileMapper.update(userProfile);
			}

			message.setMsg("查询成功!");
			message.setObj(entityMap);
			message.setSuccess(true);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void getFriendProfile(Message message, String content) {
		FriendProfile friendProfile = JSON.parseObject(content, FriendProfile.class);
		if (WebUtil.isEmpty(friendProfile.getAccountNum()) || WebUtil.isEmpty(friendProfile.getPassword())) {// 协议检查
			message.setMsg("账号,密码,不能为空!");
			message.setSuccess(false);
			return;
		}
		UserProfile userProfileOwner = userProfileMapper.selectByAccountNum(friendProfile.getAccountNum());
		if (userProfileOwner == null) {
			message.setMsg("查询不到此账号!");
			message.setSuccess(false);
			return;
		}
		// 核对密码
		if (!friendProfile.getPassword().equals(userProfileOwner.getPassword())) {
			message.setMsg("密码错误!");
			message.setSuccess(false);
			return;
		}

		UserProfile userProfile = null;

		if (!WebUtil.isEmpty(friendProfile.getFriendAccount())) {// 根据账号获取
			// 查询好友
			userProfile = userProfileMapper.selectByAccountNum(friendProfile.getFriendAccount());
		} else if (!WebUtil.isEmpty(friendProfile.getBaseInfoId())) {
			userProfile = userProfileMapper.selectByBaseInfoId(friendProfile.getBaseInfoId());
		} else if (!WebUtil.isEmpty(friendProfile.getPhoneNum())) {
			userProfile = userProfileMapper.selectByPhoneNum(friendProfile.getPhoneNum());
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
		userProfileSearchEntity.setName(userProfile.getName());
		userProfileSearchEntity.setSex(userProfile.getSex());
		userProfileSearchEntity.setSign(userProfile.getSign());
		userProfileSearchEntity.setPicture(userProfile.getPicture());
		userProfileSearchEntity.setAuthenticated(userProfile.getAuthenticated());
		userProfileSearchEntity.setAlumni_id(userProfile.getAlumni_id());
		userProfileSearchEntity.setHobby(userProfile.getHobby());
		userProfileSearchEntity.setProfession(userProfile.getProfession());
		userProfileSearchEntity.setWorkUtil(userProfile.getWorkUtil());
		userProfileSearchEntity.setPosition(userProfile.getPosition());

		message.setMsg("查询成功!");
		message.setObj(userProfileSearchEntity);
		message.setSuccess(true);
	}

	@Override
	public void updateProfile(Message message, String content) {
		try {
			com.cy.mobileInterface.baseinfo.entity.UserProfile userProfile1 = JSON.parseObject(content,
					com.cy.mobileInterface.baseinfo.entity.UserProfile.class);
			if(StringUtils.isNotBlank(userProfile1.getName())){
				userProfile1.setName(userProfile1.getName().replaceAll("\\s*", ""));
			}
			UserProfile userProfileOwner = userProfileMapper.selectByAccountNum(userProfile1.getAccountNum());
			if (userProfileOwner == null) {
				message.setMsg("账号ID不存在!");
				message.setSuccess(false);
				return;
			}

			// 添加班级，认证
			if (userProfile1.getBaseInfoId() != null && userProfile1.getBaseInfoId().size() > 0 && userProfile1.getClassmates() != null
					&& userProfile1.getClassmates().size() > 0) {
				if (userProfile1.getClassmates().size() < 3) {
					message.setMsg("请提供3个本班同学的姓名");
					message.setSuccess(false);
					return;
				}
				// 身份甄别
				String classId = userProfile1.getBaseInfoId().get(0).substring(0, 16);// 截取班级编号
				List<UserInfo> userInfoList = userInfoMapper.selectUserByClassIdLess(classId);
				if (userInfoList == null || userInfoList.size() == 0 || userInfoList.size() < 4) {
					message.setMsg("班级人数小于4人，无法认证");
					message.setSuccess(false);
					return;
				}
				long count = 0;
				UserInfo userInfo = null;
				for (UserInfo user : userInfoList) {
					for (String name : userProfile1.getClassmates()) {
						if (name.equals(user.getUserName())) {
							count++;
							break;
						}
					}
					if (user.getUserId().equals(userProfile1.getBaseInfoId().get(0))) {
						userInfo = user;
					}
				}
				if (count != userProfile1.getClassmates().size()) {
					message.setMsg("所提供的同学不在同一班级!");
					message.setSuccess(false);
					return;
				}
				// 从基础库获取该同学的信息
				if (userInfo == null) {
					message.setMsg("找不到认证用户信息!");
					message.setSuccess(false);
					return;
				}
				if (userInfo.getAccountNum() != null && userInfo.getAccountNum().length() != 0) {
					message.setMsg("该用户已经认证过!");
					message.setSuccess(false);
					return;
				}
				// 班级群是否存在
				GroupInfoEntity groupInfo = groupInfoMapper.selectGroupByGroupId(userProfile1.getBaseInfoId().get(0).substring(0, 16));
				if (groupInfo == null) {
					groupInfo = new GroupInfoEntity();
					groupInfo.setGroupName(userInfo.getClassName());
					groupInfo.setAdminsAccount("admin");
					groupInfo.setCreaterAccount("admin");
					groupInfo.setGroupId(userProfile1.getBaseInfoId().get(0).substring(0, 16));
					groupInfo.setMembersAccount(userProfile1.getAccountNum());
					groupInfo.setSubject("");
					groupInfo.setDescription("");
					groupInfoMapper.save(groupInfo);
				} else {
					String membersAccount = groupInfo.getMembersAccount();
					// 判断accounts数组内是否存在accountNum
					if (membersAccount != null && membersAccount.length() > 0 && membersAccount.indexOf(userProfile1.getAccountNum()) == -1) {
						groupInfo.setMembersAccount(membersAccount + "," + userProfile1.getAccountNum());
						groupInfoMapper.update(groupInfo);
					}
					if (membersAccount == null || membersAccount.length() == 0) {
						groupInfo.setMembersAccount(userProfile1.getAccountNum());
						groupInfoMapper.update(groupInfo);
					}
				}
				userProfileOwner.setAuthenticated("1");
				if (userProfileOwner.getBaseInfoId() != null && userProfileOwner.getBaseInfoId().length() > 0
						&& userProfileOwner.getBaseInfoId().indexOf(userProfile1.getBaseInfoId().get(0)) == -1) {
					// 已经认证过
					userProfileOwner.setBaseInfoId(userProfileOwner.getBaseInfoId() + "," + userProfile1.getBaseInfoId().get(0));
					userProfileOwner.setGroupName(userProfileOwner.getGroupName() + "," + userProfile1.getBaseInfoId().get(0).substring(0, 16));
					userProfileOwner.setClasses(userProfileOwner.getClasses() + "_" + userInfo.getFullName());
					userProfileMapper.update(userProfileOwner);
					userInfo.setAccountNum(userProfile1.getAccountNum());
					userInfoMapper.updateAccountNum(userInfo);
					// 创建节点
					TigaseUtils.getInstance().createGroupNod(userProfile1.getBaseInfoId().get(0).substring(0, 16), userProfile1.getAccountNum());
				}
				if (userProfileOwner.getBaseInfoId() == null || userProfileOwner.getBaseInfoId().length() == 0) {
					// 之前未认证
					if (userInfo.getSex() != null && userInfo.getSex().equals("男")) {
						userProfileOwner.setSex("0");
						userProfileOwner.setPicture(StringUtils.isNotBlank(userProfileOwner.getPicture())? userProfileOwner.getPicture() : "");
					} else {
						userProfileOwner.setSex("1");
						userProfileOwner.setPicture(StringUtils.isNotBlank(userProfileOwner.getPicture())? userProfileOwner.getPicture() : "");
					}
					userProfileOwner.setBaseInfoId(userProfile1.getBaseInfoId().get(0));
					userProfileOwner.setGroupName(userProfile1.getBaseInfoId().get(0).substring(0, 16));
					userProfileOwner.setClasses(userInfo.getFullName());
					MobileLocal mobileLocal = mobileLocalMapper.selectByMobileNumber(userProfileOwner.getPhoneNum().substring(0, 7));
					if (mobileLocal != null) {
						String local = mobileLocal.getMobileArea();
						String[] array = local.split(" ");
						Map<String, Object> alumniMap = new HashMap<String, Object>();
						if (array.length == 2) {
							alumniMap.put("region1", array[0]);
							alumniMap.put("region2", array[1]);
						} else {
							alumniMap.put("region1", array[0]);
						}
						// 获取校友会
						List<Alumni> list = alumniMapper.selectByRegion(alumniMap);
						// 随机选择一个校友会
						if (list != null && list.size() > 0) {
							userProfileOwner.setAlumni_id(list.get(0).getAlumniId());
						}
					}
					userProfileOwner.setName(userProfile1.getName());
					userProfileMapper.update(userProfileOwner);
					userInfo.setAccountNum(userProfile1.getAccountNum());
					userInfoMapper.updateAccountNum(userInfo);
					// 创建tigase账号
					TigaseUtils.getInstance().createAccount(userProfile1.getAccountNum(), userProfile1.getPassword());
					// 在tigase上创建节点
					TigaseUtils.getInstance().createGroupNod(userProfile1.getBaseInfoId().get(0).substring(0, 16), userProfile1.getAccountNum());
				}
				message.setMsg("认证成功!");
				message.setSuccess(true);
				message.setObj(userProfile1.getBaseInfoId());
				return;
			}
			// 消息订阅
			else if (userProfile1.getChannels() != null && userProfile1.getChannels().size() > 0 && userProfile1.getIntrestType() != null
					&& userProfile1.getIntrestType().size() > 0) {
				StringBuffer channelsb = new StringBuffer();
				StringBuffer intrestTypesb = new StringBuffer();
				for (String channel : userProfile1.getChannels()) {
					channelsb.append(channel);
					channelsb.append(",");
				}
				for (String intrestType : userProfile1.getIntrestType()) {
					intrestTypesb.append(intrestType);
					intrestTypesb.append(",");
				}
				if (channelsb.length() > 0) {
					channelsb.deleteCharAt(channelsb.length() - 1);
				}
				if (intrestTypesb.length() > 0) {
					intrestTypesb.deleteCharAt(intrestTypesb.length() - 1);
				}
				userProfileOwner.setChannels(channelsb.toString());
				userProfileOwner.setIntrestType(intrestTypesb.toString());
				userProfileMapper.update(userProfileOwner);
				message.setMsg("订阅成功!");
				message.setSuccess(true);
				return;
			} else {
				// 个人信息
				if(StringUtils.isNotBlank(userProfile1.getEmail())){
					if(!userProfile1.getEmail().equals(userProfileOwner.getEmail())){
						UserProfile tmp = userProfileMapper.selectByAccountNum(userProfile1.getEmail());
						if(tmp != null){
							message.init(false, "邮箱已被使用", null);
							return;
						}
						userProfileOwner.setIsActivated("0");
					}
				}
				userProfileOwner.setAddress(userProfile1.getAddress());
				userProfileOwner.setAlumni_id(userProfile1.getAlumni_id());
				userProfileOwner.setEmail(userProfile1.getEmail());
				userProfileOwner.setHobby(userProfile1.getHobby());
				userProfileOwner.setName(userProfile1.getName());
				userProfileOwner.setWorkUtil(userProfile1.getWorkUtil());
				userProfileOwner.setSign(userProfile1.getSign());
				userProfileOwner.setProfession(userProfile1.getProfession());
				userProfileOwner.setPosition(userProfile1.getPosition());
				userProfileOwner.setPicture(userProfile1.getPicture());
				userProfileOwner.setBirthday(userProfile1.getBirthday());


                String baseInfoId=userProfileOwner.getBaseInfoId();
                String sex=userProfile1.getSex();
                String isChangedSex=userProfileOwner.getIsChangedSex();
                if(StringUtils.isNotBlank(baseInfoId)) {
                    if (StringUtils.isNotBlank(sex) && !sex.equals(userProfileOwner.getSex())) {
                        if ("1".equals(isChangedSex)) {
							message.setMsg("对不起，您已经修改过性别了，不能再次修改!");
							message.setSuccess(false);
							return;
                        }else{
							userProfileOwner.setSex(sex);
							userProfileOwner.setIsChangedSex("1");
                        }
                    }
                }else{
//					userProfileOwner.setIsChangedSex("0");
                    userProfileOwner.setSex(sex);
                }

				userProfileMapper.update(userProfileOwner);
				// 同步到userinfo表
				UserInfo userInfo = new UserInfo();
				userInfo.setAccountNum(userProfile1.getAccountNum());
				userInfo.setResidentialArea(userProfile1.getAddress());
				userInfo.setHobbies(userProfile1.getHobby());
				userInfo.setEmail(userProfile1.getEmail());
				if (userProfile1.getSex() != null && userProfile1.getSex().equals("0")) {
					userInfo.setSex("男");
				} else {
					userInfo.setSex("女");
				}
				userInfo.setAlumniId(userProfile1.getAlumni_id());
				userInfo.setPosition(userProfile1.getPosition());
				userInfo.setWorkUnit(userProfile1.getWorkUtil());
				userInfo.setIndustryType(userProfile1.getProfession());
				userInfo.setTelId(userProfile1.getPhoneNum());
				userInfo.setPicUrl(userProfile1.getPicture());
				userInfoMapper.updateFromUserProfile(userInfo);

//				// 更新用户认证信息
//				Map<String, String> baseMap = userProfileMapper.selectBaseInfo(userProfile1.getAccountNum());
//				if(baseMap == null) {
//					baseMap = Maps.newHashMap() ;
//				}
//				baseMap.put("accountNum", userProfile1.getAccountNum()) ;
//				userProfileMapper.clearBaseInfoId(baseMap);

				message.setMsg("更新成功!");
				message.setSuccess(true);
				return;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 更改手机绑定
	 * @param message
	 * @param content
     */
	@Override
	public void resetUserPhoneNum(Message message, String content){
		try {
			ChangePhoneNum changePhoneNum = JSON.parseObject(content, ChangePhoneNum.class);
			// 校验手机号
			if (!SystemUtil.isMobileNO(changePhoneNum.getPhoneNum())) {
				message.setMsg("手机号码格式错误");
				message.setSuccess(false);
				return;
			}
			long count = userProfileService.countByPhoneNum(changePhoneNum.getPhoneNum());
			if (count != 0) {
				message.setMsg("手机号已被使用!");
				message.setSuccess(false);
				return;
			}
			// 校验验证码
			SmsCode smsCode = smsCodeService.selectByTelId(changePhoneNum.getPhoneNum(), changePhoneNum.getCheckCode());
			if (smsCode == null || !changePhoneNum.getCheckCode().equals(smsCode.getSmsCode())) {
				message.setMsg("验证码错误!");
				message.setSuccess(false);
				return;
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(smsCode.getCreateTime());
			calendar.add(Calendar.MINUTE, 5); // 10分钟
			Date valid = calendar.getTime();
			if (valid.before(new Date())) {
				message.setMsg("验证码过期!");
				message.setSuccess(false);
				return;
			}

			UserProfile userProfileTemp = new UserProfile();
			userProfileTemp.setPhoneNum(changePhoneNum.getPhoneNum());
			userProfileTemp.setAccountNum(changePhoneNum.getAccountNum());
			userProfileService.update(userProfileTemp);

			message.setMsg("改绑手机号成功!");
			message.setObj(userProfileTemp.getAccountNum());
			message.setSuccess(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



	/**
	 * 查找用户的学习路径
	 * add by jiangling
	 * @date 2017-0804
	 * @param message
	 * @param content
     */
	@Override
	public void getClasses(Message message, String content) {

		if(StringUtils.isBlank(content)){
			message.setMsg("未传入参数");
			message.setSuccess(false);
			return;
		}

		//将传入的对象content转成obj
		Map<String, String> map = JSON.parseObject(content, Map.class);

		String accountNum = map.get("accountNum");
		String showClassMates = map.get("showClassMates");

		//校验accountNum 是否有效
		if (StringUtils.isBlank(accountNum)) {
			message.setMsg("用户账号输入有误");
			message.setSuccess(false);
			return;
		}

		//先根据accountNum 来查询数据库中是否存在这个用户
		UserProfile registedUser = userProfileMapper.selectByAccountNum(accountNum);
		//如果从库中查到的用户为空,说明该用户没有注册
		if (registedUser == null) {
			message.setSuccess(false);
			message.setMsg("该用户不存在");
			return;
		}
		List<Map<String, Object>> pathList = userInfoMapper.getLearningExp(accountNum);

		if(pathList == null){
			message.setMsg("该用户没有学习经历");
			message.setSuccess(false);
			return ;
		}

		for(Map<String, Object> pa:pathList){
			String deptId = (String) pa.get("classId");
			if(StringUtils.isNotBlank(deptId)){
				Map<String, String> tmp = deptMapper.selectChatGroupByClassId(deptId);
				if(tmp != null){
					pa.put("groupId", tmp.get("groupId"));
					pa.put("groupEasemobId",tmp.get("groupEasemobId"));
				}

			}
			if("1".equals(showClassMates)){
				String strStudyPathId = (String) pa.get("strStudyPathId");
				List<Map<String, String>> classMates = userInfoService.findClassMates(strStudyPathId);
				pa.put("classMates", classMates);
			}

		}

		message.setMsg("获取学习路径成功");
		message.setSuccess(true);
		message.setObj(pathList);
		return;

	}

	/**lixun 抄的jiangling的代码*/
	@Override
	public void getUnauthorizedClass( Message message, String content )
	{
		//将传入的对象content转成obj
		UserProfile appUser = JSON.parseObject(content, UserProfile.class);
		if(appUser==null) {
			message.setMsg("该用户不存在");
			message.setSuccess(false);
		}

		//校验accountNum 是否有效
		if (appUser.getAccountNum()==null || appUser.getAccountNum().length()==0) {
			message.setMsg("用户账号输入有误");
			message.setSuccess(false);
			return;
		}

		UserProfile userProfile = userProfileMapper.selectByAccountNum(appUser.getAccountNum());
		//如果从库中查到的用户为空,说明该用户没有注册
		if (userProfile == null) {
			message.setSuccess(false);
			message.setMsg("该用户不存在");
			return;
		}

		//先根据accountNum 来查询数据库中是否存在这个用户
		List<UserProfile> registedUsers = userProfileMapper.selectUnauthorizedClass(appUser.getAccountNum());
		System.out.println(registedUsers.size());
		if (registedUsers == null || registedUsers.size() <= 0) {
			message.setSuccess(false);
			message.setMsg("未查询到未认证班级");
			return;
		}

		//get full classes
		List<Map<String, Object>> pathList = new ArrayList<Map<String, Object>>();
		for( UserProfile up : registedUsers ) {
			String fullStudyPath = up.getClasses();
			if( StringUtils.isBlank( fullStudyPath ) ) continue;
			if(StringUtils.isNotBlank(userProfile.getSex()) && StringUtils.isNotBlank(up.getSex()) && !up.getSex().equals(userProfile.getSex()) ) continue;
			//对每个学习路径进行分解,获得 strDepart, strClass
			String[] aStudyPath = fullStudyPath.split(",");
			String strDepart = aStudyPath.length >= 2 ? aStudyPath[1] :""; // 学院
			String strClass = aStudyPath.length >= 4 ? aStudyPath[3] : ""; //班级

			Map<String,Object> map = new HashMap<String,Object>();
			map.put( "fullStudyPath", fullStudyPath );
			map.put( "strDepart", strDepart );
			map.put( "strClass", strClass );
			map.put( "strStudyPathId", up.getBaseInfoId() );
			map.put( "memberCount", up.getMemberCount());
			pathList.add(map);
		}
		if(pathList != null && pathList.size()>0){
			message.init(true,"获取未认证班级成功",pathList);
		}else{
			message.init(false,"未查询到未认证班级",null);
		}

	}

	/**
	 * 获取同班同学接口
	 * @param message
	 * @param content
	 */
	public void findClassmates( Message message, String content ){
		try {
			Map<String, String> map = JSON.parseObject(content,Map.class);
			String userId = map.get("userId");
			UserInfo currentUser = userInfoMapper.selectByUserId(userId);
			if(currentUser== null || StringUtils.isBlank(currentUser.getAccountNum())){
				message.setMsg("请确保该ID已认证!");
				message.setSuccess(false);
				return;
			}
			message.setMsg("查询成功!");
			message.setObj(userInfoService.findClassMates(userId));
			message.setSuccess(true);
			return;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}



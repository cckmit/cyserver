package com.cy.mobileInterface.password.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cy.system.SecretUtil;
import com.cy.util.PairUtil;
import com.cy.util.easemob.comm.utils.EasemobUtils;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.smsCode.entity.SmsCode;
import com.cy.core.smsCode.service.SmsCodeService;
import com.cy.core.userProfile.dao.UserProfileMapper;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.mobileInterface.password.entity.PassWord;
import com.cy.system.TigaseUtils;
import com.cy.util.WebUtil;

@Service("passWordService")
public class PassWordServiceImpl implements PassWordService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PassWordServiceImpl.class);

	@Autowired
	private SmsCodeService smsCodeService;

	@Autowired
	private UserProfileMapper userProfileMapper;

	@Override
	public void updatePassWord(Message message, String content) {
		try {
			PassWord passWord = JSON.parseObject(content, PassWord.class);
			if (WebUtil.isEmpty(passWord.getPhoneNum())) {
				message.setMsg("请输入手机号码!");
				message.setSuccess(false);
				return;
			}

			if (WebUtil.isEmpty(passWord.getPassword())) {
				message.setMsg("请输入密码!");
				message.setSuccess(false);
				return;
			}
			if (WebUtil.isEmpty(passWord.getCheckCode())) {
				message.setMsg("请输入验证码!");
				message.setSuccess(false);
				return;
			}

			SmsCode smsCode = smsCodeService.selectByTelId(passWord.getPhoneNum(), passWord.getCheckCode());
			if (smsCode == null || !passWord.getCheckCode().equals(smsCode.getSmsCode())) {
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

			UserProfile userProfileTemp = userProfileMapper.selectByPhoneNum(passWord.getPhoneNum());
			if (userProfileTemp == null) {
				message.setMsg("该用户不存在!");
				message.setSuccess(false);
				return;
			}

			// 修改密码
//			String authenticated = userProfileTemp.getAuthenticated();
//			// 认证用户需要修改tigase服务器上的密码
//			if (authenticated != null && authenticated.equals("1")) {
//				userProfileTemp.setPassword(passWord.getPassword());
//				userProfileMapper.updatePassword(userProfileTemp);
////				TigaseUtils tigaseUtils = TigaseUtils.getInstance();
////				tigaseUtils.changePassword(userProfileTemp.getAccountNum(), passWord.getPassword());
//				message.setMsg("修改成功!");
//				message.setSuccess(true);
//			} else {
//			}
			com.cy.util.easemob.comm.utils.PairUtil<Boolean,String> pairUtil = null ;
            if(EasemobUtils.existsUser(userProfileTemp.getAccountNum())) {
				// 修改密码是同时修改用户在环信中的密码
				pairUtil = EasemobUtils.resetIMUserPasswordWithAdminToken(userProfileTemp.getAccountNum(), passWord.getPassword());
			} else {
				pairUtil = EasemobUtils.createNewIMUser(userProfileTemp.getAccountNum(),passWord.getPassword(),null) ;
			}

			if(pairUtil == null || !pairUtil.getOne()) {
				logger.debug("环信修改密码失败:"+ pairUtil != null ? pairUtil.getTwo() : "");
				// 环信修改密码失败
				message.setMsg("密码修改失败!");
				message.setSuccess(false);
				return;

			}
			userProfileTemp.setPassword(passWord.getPassword());
			userProfileMapper.updatePassword(userProfileTemp);
			message.setMsg("修改成功!");
			message.setSuccess(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateEasemobPassword(Message message, String content) {
		try {
			UserProfile userProfile = JSON.parseObject(content,UserProfile.class);
			if (WebUtil.isEmpty(userProfile.getAccountNum())){
				message.setMsg("请输入账号!");
				message.setSuccess(false);
				return;
			}
			UserProfile userProfileTemp = userProfileMapper.selectByAccountNum(userProfile.getAccountNum());
			if (userProfileTemp == null) {
				message.setMsg("该用户不存在!");
				message.setSuccess(false);
				return;
			}
			com.cy.util.easemob.comm.utils.PairUtil<Boolean,String> pairUtil = null ;

			if(EasemobUtils.existsUser(userProfileTemp.getAccountNum())) {
				// 修改密码是同时修改用户在环信中的密码
				pairUtil = EasemobUtils.resetIMUserPasswordWithAdminToken(userProfileTemp.getAccountNum(), userProfileTemp.getPassword());
			} else {
				pairUtil = EasemobUtils.createNewIMUser(userProfileTemp.getAccountNum(),userProfileTemp.getPassword(),null) ;
			}
			if(pairUtil == null || !pairUtil.getOne()) {
				logger.debug("环信修改密码失败:"+ pairUtil != null ? pairUtil.getTwo() : "");
				// 环信修改密码失败
				message.setMsg("密码修改失败!");
				message.setSuccess(false);
				return;
			}
			message.setMsg("修改成功!");
			message.setSuccess(true);

		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void userPasswordInit() {
		List<UserProfile> userProfileList = userProfileMapper.selectAll();
		for (UserProfile userProfile:userProfileList){
			if (userProfile.getAccountNum()!=null && userProfile.getPassword()!=null){
				userProfile.setPassword(SecretUtil.encryptToSHA(userProfile.getPassword()));
				userProfileMapper.update(userProfile);
				com.cy.util.easemob.comm.utils.PairUtil<Boolean,String> pairUtil = null ;

				if(EasemobUtils.existsUser(userProfile.getAccountNum())) {
					// 修改密码是同时修改用户在环信中的密码
					pairUtil = EasemobUtils.resetIMUserPasswordWithAdminToken(userProfile.getAccountNum(), userProfile.getPassword());
				} else {
					pairUtil = EasemobUtils.createNewIMUser(userProfile.getAccountNum(),userProfile.getPassword(),null) ;
				}

				if(pairUtil == null || !pairUtil.getOne()) {
					logger.debug("环信修改密码失败:"+ pairUtil != null ? pairUtil.getTwo() : "");
				}
			}
		}
	}

}

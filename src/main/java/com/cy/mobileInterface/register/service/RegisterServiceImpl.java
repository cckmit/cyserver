package com.cy.mobileInterface.register.service;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cy.core.userProfile.entity.UserProfileRegist;
import com.cy.core.weiXin.dao.WeiXinUserMapper;
import com.cy.core.weiXin.entity.WeiXinAccount;
import com.cy.core.weiXin.entity.WeiXinUser;
import com.cy.core.weiXin.service.WeiXinAccountService;
import com.cy.system.Global;
import com.cy.util.Base64Utils;
import com.cy.util.mail.SimpleMail;
import com.cy.util.mail.SimpleMailSender;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cy.base.entity.Message;
import com.cy.core.smsCode.entity.SmsCode;
import com.cy.core.smsCode.service.SmsCodeService;
import com.cy.core.userProfile.entity.UserProfile;
import com.cy.core.userProfile.service.UserProfileService;
import com.cy.mobileInterface.register.entity.Register;
import com.cy.system.SystemUtil;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

@Service("mregisterService")
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private SmsCodeService smsCodeService;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private WeiXinUserMapper weiXinUserMapper;

	@Autowired
	private WeiXinAccountService weiXinAccountService;

	@Override
	public void register(Message message, String content) {
		try {
			Register register = JSON.parseObject(content, Register.class);
			// 校验手机号
			if (!SystemUtil.isMobileNO(register.getPhoneNum())) {
				message.setMsg("手机号码错误");
				message.setSuccess(false);
				return;
			}

			long count = userProfileService.countByPhoneNum(register.getPhoneNum());
			if (count != 0) {
				message.setMsg("手机号已被注册过!");
				message.setSuccess(false);
				return;
			}
			// 校验密码
			if (register.getPassword() == null || register.getPassword().length() == 0) {
				message.setMsg("请填写密码!");
				message.setSuccess(false);
				return;
			}
			// 校验姓名
			if (register.getName() == null || register.getName().length() == 0) {
				message.setMsg("请填写姓名!");
				message.setSuccess(false);
				return;
			}
			// 校验验证码
			SmsCode smsCode = smsCodeService.selectByTelId(register.getPhoneNum(), register.getCheckCode());
			if (smsCode == null || !register.getCheckCode().equals(smsCode.getSmsCode())) {
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
			userProfileTemp.setPhoneNum(register.getPhoneNum());
			userProfileTemp.setName(register.getName().replaceAll("\\s*", ""));

			userProfileTemp.setPassword(register.getPassword());
			userProfileTemp.setAuthenticated("0");
			userProfileService.save(userProfileTemp);

			message.setMsg("注册成功!");
			message.setObj(userProfileTemp.getAccountNum());
			message.setSuccess(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * 微信的注册绑定账号
	 * @param message
	 * @param content
	 */
	public void registAndBindWechat(Message message, String content){

			UserProfileRegist userProfileRegist = JSON.parseObject(content, UserProfileRegist.class);
			UserProfile userProfile = new UserProfile();
			if(StringUtils.isNotBlank(userProfileRegist.getPhoneNum())){
				// 校验手机号
				if (!SystemUtil.isMobileNO(userProfileRegist.getPhoneNum())) {
					message.setMsg("手机号码错误");
					message.setSuccess(false);
					return;
				}

				long count = userProfileService.countByPhoneNum(userProfileRegist.getPhoneNum());
				if (count != 0) {
					message.setMsg("手机号已被注册过!");
					message.setSuccess(false);
					return;
				}

				// 校验验证码
				SmsCode smsCode = smsCodeService.selectByTelId(userProfileRegist.getPhoneNum(), userProfileRegist.getRegistCode());
				if (smsCode == null || (StringUtils.isNotBlank(userProfileRegist.getRegistCode()) && !userProfileRegist.getRegistCode().equals(smsCode.getSmsCode()))) {
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
			}else if(StringUtils.isNotBlank(userProfileRegist.getEmail())){
				String check = "^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
				Pattern regex = Pattern.compile(check);
				Matcher matcher = regex.matcher(userProfileRegist.getEmail());
				boolean isEmail = matcher.matches();
				if(!isEmail){
					message.init(false, "邮箱格式错误", null);
					return;
				}
				long count = userProfileService.countByEmail(userProfileRegist.getEmail());
				if (count != 0) {
					message.setMsg("邮箱已被注册过!");
					message.setSuccess(false);
					return;
				}
				userProfile.setIsActivated("0");
			}


			// 校验姓名
			if (userProfileRegist.getName() == null || userProfileRegist.getName().length() == 0) {
				message.setMsg("请填写姓名!");
				message.setSuccess(false);
				return;
			}
			userProfile.setAuthenticated("0");
			userProfile.setPicture(StringUtils.isNotBlank(userProfileRegist.getPicture())?userProfileRegist.getPicture():"");
			userProfile.setName(StringUtils.isNotBlank(userProfileRegist.getName())?userProfileRegist.getName():"");
			userProfile.setSex(StringUtils.isNotBlank(userProfileRegist.getSex())?userProfileRegist.getSex():"");
			userProfile.setBirthday(StringUtils.isNotBlank(userProfileRegist.getBirthday())?userProfileRegist.getBirthday():"");
			userProfile.setAddress(StringUtils.isNotBlank(userProfileRegist.getAddress())?userProfileRegist.getAddress():"");
			userProfile.setPhoneNum(StringUtils.isNotBlank(userProfileRegist.getPhoneNum())?userProfileRegist.getPhoneNum():"");
			userProfile.setPassword(StringUtils.isNotBlank(userProfileRegist.getPassword())?userProfileRegist.getPassword():"");
			userProfile.setEmail(StringUtils.isNotBlank(userProfileRegist.getEmail())?userProfileRegist.getEmail():"");
			userProfile.setWorkUtil(StringUtils.isNotBlank(userProfileRegist.getWorkUtil())?userProfileRegist.getWorkUtil():"");
			userProfile.setPosition(StringUtils.isNotBlank(userProfileRegist.getPosition())?userProfileRegist.getPosition():"");

			boolean createSuccess = false;
			try{
				userProfileService.save(userProfile);
				createSuccess = true;
			} catch (Exception e) {
				message.init(false,"绑定失败", null );
				throw new RuntimeException(e);
			}


			if(StringUtils.isNotBlank(userProfileRegist.getEmail())){
				Map<String, Object> tmp = new HashMap<>();
				tmp.put("appId", userProfileRegist.getAppId());
				List<WeiXinAccount> list = weiXinAccountService.getList(tmp);
				WeiXinAccount weiXinAccount = list.get(0);
				weiXinAccount.getAccountType();



				int sendResult = sendEmail(userProfile.getAccountNum(),weiXinAccount.getAccountType(),userProfile.getEmail() );
				if(sendResult == 0){
					System.out.println("发往"+userProfile.getEmail()+"的邮箱发送成功");
				}else{
					System.out.println("发往"+userProfile.getEmail()+"的邮箱发送失败");
				}
			}

			//顺带绑定一下openId
			if(createSuccess){
				try{
					Map<String, Object> tmp = new HashMap<>();
					tmp.put("openid", userProfileRegist.getOpenId());
					List<WeiXinUser> list = weiXinUserMapper.selectWeiXinUser(tmp);
					if(list != null && list.size() >0){
						WeiXinUser weiXinUser = list.get(0);
						weiXinUser.setAccountNum(userProfile.getAccountNum());
						weiXinUser.preUpdate();
						weiXinUserMapper.update(weiXinUser);

						//如果用户头像不存在，就用微信的
						if(StringUtils.isNotBlank(weiXinUser.getHeadimgurl()) && StringUtils.isBlank(userProfile.getPicture())){
							userProfile.setPicture(weiXinUser.getHeadimgurl());
						}
						if(StringUtils.isNotBlank(weiXinUser.getSex()) && StringUtils.isBlank(userProfile.getSex())){
							if("1".equals(weiXinUser.getSex())){
								userProfile.setSex("0");
							}else if("2".equals(weiXinUser.getSex())){
								userProfile.setSex("1");
							}else{
								userProfile.setSex("0");
							}
						}
						userProfileService.update(userProfile);
					}
				}catch (Exception e){
					message.init(false,"绑定失败", userProfile.getAccountNum() );
					throw new RuntimeException(e);
				}
			}

			message.setMsg("绑定成功!");
			message.setObj(userProfile.getAccountNum());
			message.setSuccess(true);
	}

	/**
	 * 获取激活邮件接口
	 * @param message
	 * @param content
	 */
	public void sendActiveEmail(Message message, String content){
		try {
			if(StringUtils.isBlank(content)){
				message.init(false, "请传入参数", null);
				return;
			}
			Map<String, String> map = JSON.parseObject(content, Map.class);
			String accountNum = map.get("accountNum");
			String accountType = map.get("accountType");
			if(StringUtils.isBlank(accountNum)){
				message.init(false, "请提供账号", null);
				return;
			}

			UserProfile userProfile = userProfileService.selectByAccountNum(accountNum);
			if(userProfile == null){
				message.init(false, "用户不存在", null);
				return;
			}

			if(StringUtils.isBlank(userProfile.getEmail())){
				message.init(false, "请先设置邮箱再验证", null);
				return;
			}

			int result = sendEmail(accountNum, accountType,userProfile.getEmail());


			if(result == 0){
				message.init(true, "请注意查收邮件，尽快激活绑定邮箱", null);
			}else{
				message.init(false, "发送邮件失败", null);
			}

		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}


	private final static String thisProjectPath = "/home/cysys/tomcat/webapps/ROOT";
	/**
	 * 发送邮件的方法
	 * @param accountNum
	 * @param email
	 * @return
	 */
	private int sendEmail(String accountNum,String accountType, String email){
		int code;
		try {
			// 发送器
			JavaMailSenderImpl sender = new JavaMailSenderImpl();
			if("20".equals(accountType)){

				if(StringUtils.isNotBlank(Global.foundation_smtpHost)){
					sender.setHost(Global.foundation_smtpHost);
				}else{
					sender.setHost("smtp.163.com");
				}

				if(StringUtils.isBlank(Global.foundation_smtpPort)){
					sender.setPort(25); // 默认就是25
				}else{
					sender.setPort(Integer.parseInt(Global.foundation_smtpPort));
				}

				if(StringUtils.isBlank(Global.foundation_email_account) || StringUtils.isBlank(Global.foundation_email_password)){
					System.out.println("未配置发件账号");
					return -1;
				}
				sender.setUsername(Global.foundation_email_account);
				sender.setPassword(Global.foundation_email_password);
				sender.setDefaultEncoding("UTF-8");



			}else{
				if(StringUtils.isNotBlank(Global.foundation_smtpHost)){
					sender.setHost(Global.foundation_smtpHost);

				}else if(StringUtils.isNotBlank(Global.smtpHost)){

				}else{
					sender.setHost("smtp.163.com");
				}


				if(StringUtils.isBlank(Global.smtpPort)){
					sender.setPort(25); // 默认就是25
				}else{
					sender.setPort(Integer.parseInt(Global.smtpPort));
				}

				if(StringUtils.isBlank(Global.email_account) || StringUtils.isBlank(Global.email_password)){
					System.out.println("未配置发件账号");
					return -1;
				}
				sender.setUsername(Global.email_account);
				sender.setPassword(Global.email_password);
				sender.setDefaultEncoding("UTF-8");


			}


			// 配置文件对象
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true"); // 是否进行验证
			Session session = Session.getInstance(props);
			sender.setSession(session); // 为发送器指定会话

			MimeMessage mail = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo(email); // 发送给谁
			helper.setSubject(Global.FOUNDATION_NAME + "账号激活"); // 标题
			helper.setFrom(Global.email_account); // 来自

			// 获取微信基金会公众号二维码
			String evm;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("accountType","20");
			List<WeiXinAccount> weiXinAccountList = weiXinAccountService.getList(map);
			if(weiXinAccountList != null && weiXinAccountList.size()>0 && StringUtils.isNotBlank(weiXinAccountList.get(0).getCodeImage())){

				evm = thisProjectPath + weiXinAccountList.get(0).getCodeImage();
			}else{
				if("000440".equals(Global.deptNo)){
					evm = thisProjectPath + "/foundation/img/ys_evm.jpg";
				}else{
					evm = thisProjectPath + "/mobile/wechat/static/img/email_qrcode.png";
				}
			}

			String activationCode = "{\"uId\":\""+accountNum+"\",\"time\":\""+ new Date().getTime()+"\"}";
			activationCode = Base64Utils.getBase64(Base64Utils.getBase64(activationCode));

			String activationUrl = Global.cy_server_url;
			if(!"/".equals(Global.cy_server_url.substring(Global.cy_server_url.length()-1,Global.cy_server_url.length()))){
				activationUrl += "/";
			}
			activationUrl += "mRegister/registerAction!doNotNeedSessionAndSecurity_activation.action?activationCode=";
			activationUrl +=  activationCode;

			String content = mailMod(activationUrl, "", Global.email_account, Global.schoolSign);

			// 邮件内容，第二个参数指定发送的是HTML格式
			helper.setText(content,true);
			// 增加CID内容
			FileSystemResource evmIcon = new FileSystemResource(new File(evm));

			helper.addInline("evm", evmIcon);

			sender.send(mail); // 发送
			code = 0;
		}catch (Exception e){
			code = 1;
			throw new RuntimeException(e);
		}
		return code;
	}


	/**
	 * 邮件的模版样式
	 * @param url
	 * @param phone
	 * @param email
	 * @param schoolName
	 * @return
	 */
	private String mailMod(String url, String phone, String email, String schoolName){

		return  "<style type=\"text/css\">\n" +
				"    /*css图标*/\n" +
				"    .icono-iphone,.icono-mail, .icono-mail:before{\n" +
				"        border: 2px solid;\n" +
				"    }\n" +
				"    .icono-iphone:after, .icono-iphone:before{\n" +
				"        position: absolute;\n" +
				"        left: 50%;\n" +
				"        -webkit-transform: translateX(-50%);\n" +
				"        -ms-transform: translateX(-50%);\n" +
				"        transform: translateX(-50%);\n" +
				"    }\n" +
				"\n" +
				"    .icono-mail {\n" +
				"        width: 24px;\n" +
				"        height: 16px;\n" +
				"        overflow: hidden;\n" +
				"        margin: 0;\n" +
				"    }\n" +
				"\n" +
				"    .icono-mail:before {\n" +
				"        position: absolute;\n" +
				"        width: 22px;\n" +
				"        height: 24px;\n" +
				"        -webkit-transform: rotate(50deg) skew(-10deg,-20deg);\n" +
				"        -ms-transform: rotate(50deg) skew(-10deg,-20deg);\n" +
				"        transform: rotate(50deg) skew(-10deg,-20deg);\n" +
				"        top: -20px;\n" +
				"        left: -3px;\n" +
				"    }\n" +
				"\n" +
				"    .icono-iphone {\n" +
				"        width: 16px;\n" +
				"        height: 24px;\n" +
				"        border-radius: 3px;\n" +
				"        border-width: 5px 1px;\n" +
				"        border-color: transparent;\n" +
				"        box-shadow: 0 0 0 1px,0 0 0 1px inset;\n" +
				"        margin: 0;\n" +
				"    }\n" +
				"\n" +
				"    .icono-iphone:after, .icono-iphone:before {\n" +
				"        box-shadow: inset 0 0 0 32px;\n" +
				"    }\n" +
				"\n" +
				"    .icono-iphone:before {\n" +
				"        width: 3px;\n" +
				"        height: 1px;\n" +
				"        top: -3px;\n" +
				"    }\n" +
				"\n" +
				"    .icono-iphone:after {\n" +
				"        width: 3px;\n" +
				"        height: 3px;\n" +
				"        bottom: -4px;\n" +
				"        border-radius: 50%;\n" +
				"    }\n" +
				"\n" +
				"    [class*=icono-] {\n" +
				"        display: inline-block;\n" +
				"        vertical-align: middle;\n" +
				"        position: relative;\n" +
				"        font-style: normal;\n" +
				"        color: #ddd;\n" +
				"        text-align: left;\n" +
				"        text-indent: -9999px;\n" +
				"        direction: ltr;\n" +
				"    }\n" +
				"\n" +
				"    [class*=icono-]:after, [class*=icono-]:before {\n" +
				"        content: '';\n" +
				"        pointer-events: none;\n" +
				"    }\n" +
				"\n" +
				"\n" +
				"    [class*=icono-], [class*=icono-] * {\n" +
				"        box-sizing: border-box;\n" +
				"    }\n" +
				"\n" +
				"</style>" +
				"    <div style='background: #f4f5f9;box-sizing: border-box;font-family: sans-serif;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;font-size: 62.5%;-webkit-overflow-scrolling: touch;width: 100%;height: 100%;margin: 40px 15px 15px 15px;background-color: #fff;box-shadow: 0px 1px 1px rgba(0,0,0,0.5);'>\n" +
				"        <ul style='margin:0 auto;padding:20px 10px;text-align: center;border-bottom: 1px dashed #ddd;'>\n" +
				"            <li style='list-style-type:none;font-size:18px;color:#4d99db;margin-bottom:20px;'>来自"+schoolName+"的问候</li>\n" +
				"            <li style='list-style-type:none;font-size:14px;color:#2b2b2b;margin-bottom:20px;'>欢迎您注册"+schoolName+"微信公众号,<br>请点击链接激活您的账号: </li>\n" +
				"            <li style='list-style-type:none;margin-bottom:20px;'><a href='"+url+"'><input type='button' style='height:30px;line-height: 30px;background-color: #4d99db;outline:none; border:none;border-radius:5px;color:#fff;font-size:16px;padding:0 10px;box-shadow: 1px 1px 1px rgba(0,0,0,0.5);cursor: pointer;' value='点击验证'></a></li>\n" +
				"            <li style='list-style-type:none;margin-bottom:20px;'>按钮无效? 请复制黏贴下面的链接至浏览器地址栏打开\n" +
				"            <a href='"+url+"'>"+url+"</a></li>\n" +
				"            <li style='list-style-type:none;margin-bottom:0'>\n" +
				"                <img src='cid:evm'  style='width:80px;'/>\n" +
				"                <div>扫描二维码关注我们</div>\n" +
				"            </li>\n" +
				"        </ul>\n" +
				"        <div style='margin:0 auto;padding:20px 10px;text-align: center;'>\n" +
				"            <p>如有任何问题,可以与我们联系,<br>\n" +
				"                我们将尽快为您解答</p>\n" +
				"            <div style='display:flex;flex-wrap: wrap;'>\n" +
				"                <div style='flex:1;display:flex;'>\n" +
				"                    <span style='margin:0 10px;'><i class='icono-iphone'></i></span>\n" +
				"                    <span style='flex:1;'>"+phone+"</span>\n" +
				"                </div>\n" +
				"                <div style='flex:1;display:flex;'>\n" +
				"                    <span style='margin:0 10px;'><i class='icono-mail'></i></span>\n" +
				"                    <span style='flex:1;'>"+email+"</span>\n" +
				"                </div>\n" +
				"            </div>\n" +
				"        </div>\n" +
				"    </div>\n";
	}

}

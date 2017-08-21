package ltd.moore.ctravel.login.controller;

import com.alibaba.fastjson.JSONObject;
import com.hdos.platform.base.user.model.AccountVO;
import com.hdos.platform.common.util.CacheUtils;
import ltd.moore.ctravel.captcha.service.impl.CaptchaServiceImpl;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.customer.model.CustomerAccountVO;
import ltd.moore.ctravel.login.dto.LoginInfoDto;
import ltd.moore.ctravel.login.dto.Result;
import ltd.moore.ctravel.login.util.ResultUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by Administrator on 2017/5/5 0005.
 * 登陆校验
 */
@Controller
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private CaptchaServiceImpl captchaService;

    /** 缓存存在时间 */
    private final static long TIME_OUT = 60*60*1000;
    /**登陆失效时间 */
    private final static long LOG_TIME_OUT = 60*60*1000;
    private static int LOG_WRONG_LIMIT = 55;
    public static String LOGIN_COUNT_TAG = "loginCount";
    public static String LOGIN_TOKEN_TAG = "loginToken";
    private final static Logger logger = Logger.getLogger(LoginController.class);


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ResponseBody
    public Object login(@RequestParam(value = "account") String account,@RequestParam String password,@RequestParam String captcha,@RequestParam String captchaId) {
        LoginInfoDto loginInfo = new LoginInfoDto();
        loginInfo.setAccount(account);
        loginInfo.setPassword(password);
        loginInfo.setCaptcha(captcha);
        loginInfo.setCaptchaId(captchaId);
        if(loginInfo.getAccount() == null ||loginInfo.getPassword() == null){
            return ResultUtils.generate(ResultCodeInfo.E100001, null,null,"0");
        }
        String phoneNo = loginInfo.getAccount();
        String passwordS = DigestUtils.md5Hex(loginInfo.getPassword());
        //缓存中无登陆信息则生成登陆信息
        if(CacheUtils.get(LOGIN_COUNT_TAG+phoneNo) == null){
            CacheUtils.put(LOGIN_COUNT_TAG+phoneNo,0,TIME_OUT);
        }
        //获取用户登陆次数
        int loginCount = Integer.parseInt(CacheUtils.get(LOGIN_COUNT_TAG+phoneNo).toString())+1;
        JSONObject result = new JSONObject();
        //登陆失败次数为规定次数以上时
        if(loginCount > LOG_WRONG_LIMIT && (captcha == null || captchaId == null)){
            return ResultUtils.generate(ResultCodeInfo.E100002, null,null,"1");
        }else if( loginCount > LOG_WRONG_LIMIT && (!(captchaService.validateCaptcha(captcha, captchaId, true)))){
            return ResultUtils.generate(ResultCodeInfo.E100002, null,null,"1");
        }
        if(loginCount >= LOG_WRONG_LIMIT){
            return loginWithCaptcha(loginInfo);
        }
        //不需要验证码登陆
        UsernamePasswordToken token = new UsernamePasswordToken(phoneNo, password);
        token.setRememberMe(true);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            logger.info("对用户[" + phoneNo + "]进行登录验证..验证开始");
            currentUser.login(token);
            SecurityUtils.getSubject().getSession().setTimeout(LOG_TIME_OUT);
            String userId = ((CustomerAccountVO)currentUser.getPrincipal()).getCustomerId();
            //生成token
            String userToken = UUID.randomUUID().toString();
            //将token放在缓存中
            CacheUtils.put(LOGIN_TOKEN_TAG+userId,userToken,TIME_OUT);
            CacheUtils.delete(LOGIN_COUNT_TAG+phoneNo);
            logger.info("token&session:"+token.toString()+"/"+currentUser.getSession());
            logger.info(currentUser.isAuthenticated());
            return ResultUtils.generate(ResultCodeInfo.E000000, userId,userToken,"0");
        }catch(UnknownAccountException uae){
            logger.info("对用户[" + phoneNo + "]进行登录验证..验证未通过,未知账户");
            //设置验证码错误次数
            setCaptchaCount(phoneNo);
            return ResultUtils.generate(ResultCodeInfo.E100001, null,null,"0");
        }catch(IncorrectCredentialsException ice){
            logger.info("对用户[" + phoneNo + "]进行登录验证..验证未通过,错误的凭证");
            setCaptchaCount(phoneNo);
            return ResultUtils.generate(ResultCodeInfo.E100001, null,null,"0");
        }catch(LockedAccountException lae){
            logger.info("对用户[" + phoneNo + "]进行登录验证..验证未通过,账户已锁定");
            setCaptchaCount(phoneNo);
            return ResultUtils.generate(ResultCodeInfo.E100004, null,null,"0");
        }catch(ExcessiveAttemptsException eae){
            logger.info("对用户[" + phoneNo + "]进行登录验证..验证未通过,错误次数过多");
            setCaptchaCount(phoneNo);
            return ResultUtils.generate(ResultCodeInfo.E100004, null,null,"0");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + phoneNo + "]进行登录验证..验证未通过,堆栈轨迹如下");
            setCaptchaCount(phoneNo);
            ae.printStackTrace();
        }
        return null;
    }

    /**
     * 未登录返回的json
     * @return
     */
    @RequestMapping(value = "/notLogin",method = RequestMethod.GET)
    public String notLogin() {
        JSONObject result = new JSONObject();
        result.put("resultCode", ResultCodeInfo.E888888);
        result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E888888));
        return result.toJSONString();
    }

    /**
     * 设置校验证码计数
     * @param usrPhone
     */
    void setCaptchaCount(String usrPhone){
        CacheUtils.put(LOGIN_COUNT_TAG+usrPhone,Integer.parseInt(CacheUtils.get(LOGIN_COUNT_TAG+usrPhone).toString())+1);
    }

    /**
     * 进行登陆错误临界点
     * @return
     */
    public Object loginWithCaptcha(LoginInfoDto loginInfo){
        String phoneNo = loginInfo.getAccount();
        String password = DigestUtils.md5Hex(loginInfo.getPassword());
        JSONObject result = new JSONObject();
        if(phoneNo == null){
            return ResultUtils.generate(ResultCodeInfo.E999999, null,null,"1");
        }
        UsernamePasswordToken token = new UsernamePasswordToken(phoneNo, password);
        token.setRememberMe(true);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            logger.info("对用户[" + phoneNo + "]进行登录验证..验证开始");
            currentUser.login(token);
            SecurityUtils.getSubject().getSession().setTimeout(LOG_TIME_OUT);
            String userId = ((AccountVO)currentUser.getPrincipal()).getUserId();
            //生成token
            String userToken = UUID.randomUUID().toString();
            CacheUtils.delete(LOGIN_COUNT_TAG+phoneNo);
            //将token放在缓存中
            CacheUtils.put(LOGIN_TOKEN_TAG+userId,userToken,TIME_OUT);
            return ResultUtils.generate(ResultCodeInfo.E000000, userId,userToken,"0");
        }catch(UnknownAccountException uae){
            logger.info("对用户[" + phoneNo + "]进行登录验证..验证未通过,未知账户");
            //设置验证码错误次数
            setCaptchaCount(phoneNo);
            return ResultUtils.generate(ResultCodeInfo.E100001, null,null,"1");
        }catch(IncorrectCredentialsException ice){
            logger.info("对用户[" + phoneNo + "]进行登录验证..验证未通过,错误的凭证");
            setCaptchaCount(phoneNo);
            return ResultUtils.generate(ResultCodeInfo.E100001, null,null,"1");
        }catch(LockedAccountException lae){
            logger.info("对用户[" + phoneNo + "]进行登录验证..验证未通过,账户已锁定");
            setCaptchaCount(phoneNo);
            return ResultUtils.generate(ResultCodeInfo.E100004, null,null,"1");
        }catch(ExcessiveAttemptsException eae){
            logger.info("对用户[" + phoneNo + "]进行登录验证..验证未通过,错误次数过多");
            setCaptchaCount(phoneNo);
            return ResultUtils.generate(ResultCodeInfo.E100004, null,null,"1");
        }catch(AuthenticationException ae){
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            logger.info("对用户[" + phoneNo + "]进行登录验证..验证未通过,堆栈轨迹如下");
            setCaptchaCount(phoneNo);
            ae.printStackTrace();
        }
        return null;
    }
}
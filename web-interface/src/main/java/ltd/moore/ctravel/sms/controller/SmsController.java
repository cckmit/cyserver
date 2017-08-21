package ltd.moore.ctravel.sms.controller;

import com.alibaba.fastjson.JSONObject;
import com.hdos.platform.common.util.CacheUtils;
import ltd.moore.ctravel.captcha.service.CaptchaService;
import ltd.moore.ctravel.constants.ResultCodeInfo;
import ltd.moore.ctravel.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ZhuTail
 * @Date: 2017/5/18.
 */
@Controller
@RequestMapping("/api")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private CaptchaService captchaService;

    /**
     * 获取短息验证码
     *
     * @param phoneNo
     * @param
     * @return
     */
    @RequestMapping(value = "/sms/{captchaId}/{captcha}/{phoneNo}/{smsCaptchaType}", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getSms(@PathVariable(value = "captchaId") String captchaId,@PathVariable(value = "captcha") String captcha,@PathVariable(value = "phoneNo") String phoneNo, @PathVariable(value = "smsCaptchaType") String smsCaptchaType) {

        //验证图片验证码
        if (captchaService.validateCaptcha(captcha,captchaId,false)){
            //1:注册验证码
            if (smsCaptchaType.equals("1")) {
                return smsService.sendRegisterCode(phoneNo);
            } else {
                //2：找回密码验证码
                return smsService.sendFindBackPwdCode(phoneNo);
            }
        }else {
            Map<String,Object> result = new HashMap<>();
            result.put("resultCode", ResultCodeInfo.E100006);
            result.put("errorMsg", ResultCodeInfo.formateCodeMsg(ResultCodeInfo.E100006));
            return JSONObject.toJSONString(result);
        }
    }
}

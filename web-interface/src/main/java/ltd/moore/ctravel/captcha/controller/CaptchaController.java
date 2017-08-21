package ltd.moore.ctravel.captcha.controller;

import com.alibaba.fastjson.JSONObject;
import ltd.moore.ctravel.captcha.service.CaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 获取验证码
 * <p>
 * Created by zhuTail on 2017/5/14.
 */
@Controller
@RequestMapping("/api")
public class CaptchaController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CaptchaService captchaService;

    /**
     * 获取验证码
     *
     * @return
     */
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    @ResponseBody
    public String getCaptcha() {
        return JSONObject.toJSONString(captchaService.getCaptcha());
    }

    /**
     * 验证验证码
     *
     * @return
     */
    @RequestMapping(value = "/captcha/validate/{captcha}/{captchaId}",method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String captchaValidation(@PathVariable String captcha, @PathVariable String captchaId) {
        return JSONObject.toJSONString(captchaService.validateCaptcha(captcha,captchaId,false));
    }


}

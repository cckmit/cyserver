package ltd.moore.ctravel.captcha.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zzhu8 on 2017/5/14.
 */
@Service
public interface CaptchaService {

    /**
     * 获取验证码
     *
     * @return
     */
    Map<String,Object> getCaptcha();

    /**
     * 验证
     *
     * @param captcha
     * @param uuid
     * @param removeCache
     * @return
     */
    boolean validateCaptcha(String captcha, String uuid, boolean removeCache);
}

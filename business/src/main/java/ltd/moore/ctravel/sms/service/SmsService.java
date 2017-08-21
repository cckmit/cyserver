package ltd.moore.ctravel.sms.service;

import org.springframework.stereotype.Service;

/**
 * Created by zhutail on 2017/6/5
 */
@Service
public interface SmsService {

    /**
     * 发送注册验证码
     *
     * @param phoneNo
     * @return 验证码id
     */
    String sendRegisterCode(String phoneNo);

    /**
     * 发送找回密码验证码
     *
     * @param phoneNo
     * @return 验证码id
     */
    String sendFindBackPwdCode(String phoneNo);
}

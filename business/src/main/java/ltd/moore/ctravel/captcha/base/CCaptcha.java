package ltd.moore.ctravel.captcha.base;

import java.awt.*;

/**
 * 短信验证码
 *
 * @author zhutail
 * @date 2017-05-26
 * @version v0.1
 */
public class CCaptcha extends AbstractCaptcha {

    public final static String CODEER_NAME = "CCaptcha";
    private String randString = "0123456789";//用于生产的母字符串
    private Font font = new Font("Fixedsys", Font.CENTER_BASELINE, 18);//验证码字体

    public CCaptcha() {
    }

    @Override
    public String generateCaptchaString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(randString.charAt(random.nextInt(randString.length())));
        }
        return sb.toString();
    }

    @Override
    public Image generateCaptchaImage(String code) {
        return null;
    }

}

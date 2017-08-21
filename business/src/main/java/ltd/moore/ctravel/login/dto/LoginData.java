package ltd.moore.ctravel.login.dto;

/**
 * Created by Cocouzx on 2017-6-16 0016.
 */
public class LoginData {
    /**
     * token
     */
    private String token;

    /**
     * userId
     */
    private String userId;

    /**
     * 下次是否需要验证码
     */
    private String needCaptcha;

    public LoginData(String token, String userId, String needCaptcha) {
        this.token = token;
        this.userId = userId;
        this.needCaptcha = needCaptcha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNeedCaptcha() {
        return needCaptcha;
    }

    public void setNeedCaptcha(String needCaptcha) {
        this.needCaptcha = needCaptcha;
    }
}

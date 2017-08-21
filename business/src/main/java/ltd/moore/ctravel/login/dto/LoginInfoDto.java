package ltd.moore.ctravel.login.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class LoginInfoDto implements Serializable {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String captcha;
    /**
     * 验证码标识
     */
    private String captchaId;

    /**
     * 微信登陆凭证
     */
    private String wechatCode;

    /**
     * 登陆类型
     * 1：手机登陆
     * 2：微信登陆
     */
    private int loginType;

    /**
     * 登陆入口类型
     * 1：普通用户入口
     2：商户用户入口
     */
    private int entryType;


    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public int getEntryType() {
        return entryType;
    }

    public void setEntryType(int entryType) {
        this.entryType = entryType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getWechatCode() {
        return wechatCode;
    }

    public void setWechatCode(String wechatCode) {
        this.wechatCode = wechatCode;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

}

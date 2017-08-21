package ltd.moore.ctravel.login.dto;

/**
        * 登陆返回信息pojo
        * Created by Cocouzx on 2017-5-27 0027.
        */
public class Result {
    /**
     * 状态码
     */
    private String resultCode;

    /**
     * 显示信息
     */
    private String errorMsg;

    /**
     * 是否需要验证码
     */
    private String needCaptcha;

    /**
     * userId
     */
    private String userId;

    /**
     * token
     */
    private String token;

    /**
     * 登陆返回数据
     */
    private LoginData loginData;

    public Result(String resultCode, String errorMsg, String userId,String token, String needCaptcha ) {
        this.resultCode = resultCode;
        this.errorMsg = errorMsg;
        this.loginData = new LoginData(token,userId,needCaptcha);
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getNeedCaptcha() {
        return needCaptcha;
    }

    public void setNeedCaptcha(String needCaptcha) {
        this.needCaptcha = needCaptcha;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}


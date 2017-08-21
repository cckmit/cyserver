package ltd.moore.ctravel.refreshpwd.dto;

/**
 * @Author: youcai
 * @Date: 2017/5/23.
 */
public class RefreshPwdDTO {

    private String phoneNo;
    private String messageId;
    private String smsCaptcha;
    private String password;
    //找回密码验证Id
    private String refreshpwdId;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSmsCaptcha() {
        return smsCaptcha;
    }

    public void setSmsCaptcha(String smsCaptcha) {
        this.smsCaptcha = smsCaptcha;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefreshpwdId() {
        return refreshpwdId;
    }

    public void setRefreshpwdId(String refreshpwdId) {
        this.refreshpwdId = refreshpwdId;
    }

    @Override
    public String toString() {
        return "RefreshPwdDTO{" +
                "phoneNo='" + phoneNo + '\'' +
                ", messageId='" + messageId + '\'' +
                ", smsCaptcha='" + smsCaptcha + '\'' +
                ", password='" + password + '\'' +
                ", refreshpwdId='" + refreshpwdId + '\'' +
                '}';
    }
}
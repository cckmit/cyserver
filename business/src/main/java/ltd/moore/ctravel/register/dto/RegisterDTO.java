package ltd.moore.ctravel.register.dto;

import java.util.Date;

/**
 * @Author: ZhuTail
 * @Date: 2017/5/23.
 */
public class RegisterDTO {
    /*
     * account
     */
    private String account;
    private Integer customerType;
    private String password;
    private String mobile;
    private String email;
    private Integer status;
    private Date createTime;

    /*
     * detail
     */
    private String nickName;
    private Integer gender;
    private String headImg;
    private String imToken;
    private String area;
    private String job;
    private String school;
    private String language;
    private String idcardImg;
    private Integer idcardCheckStatus;
    private String accountNo;

    //短信Id
    private String messageId;
    //验证码
    private String smsCaptcha;
    //注册验证Id
    private String registerId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getImToken() {
        return imToken;
    }

    public void setImToken(String imToken) {
        this.imToken = imToken;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIdcardImg() {
        return idcardImg;
    }

    public void setIdcardImg(String idcardImg) {
        this.idcardImg = idcardImg;
    }

    public Integer getIdcardCheckStatus() {
        return idcardCheckStatus;
    }

    public void setIdcardCheckStatus(Integer idcardCheckStatus) {
        this.idcardCheckStatus = idcardCheckStatus;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "account='" + account + '\'' +
                ", customeType=" + customerType +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", nickName='" + nickName + '\'' +
                ", gender=" + gender +
                ", headImg='" + headImg + '\'' +
                ", imToken='" + imToken + '\'' +
                ", area='" + area + '\'' +
                ", job='" + job + '\'' +
                ", school='" + school + '\'' +
                ", language='" + language + '\'' +
                ", idcardImg='" + idcardImg + '\'' +
                ", idcardCheckStatus=" + idcardCheckStatus +
                ", accountNo='" + accountNo + '\'' +
                ", messageId='" + messageId + '\'' +
                ", smsCaptcha='" + smsCaptcha + '\'' +
                ", registerId='" + registerId + '\'' +
                '}';
    }
}

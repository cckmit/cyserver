package ltd.moore.ctravel.customer.model;

import java.io.Serializable;

/**
 * @Author: ZhuTail
 * @Date: 2017/6/6.
 */
public class CustomerDetailVO implements Serializable {

    private String customerId;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    @Override
    public String toString() {
        return "CustomerDetailVO{" +
                "customerId='" + customerId + '\'' +
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
                '}';
    }
}

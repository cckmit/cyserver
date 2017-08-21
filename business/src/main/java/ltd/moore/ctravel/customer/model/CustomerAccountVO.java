package ltd.moore.ctravel.customer.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: ZhuTail
 * @Date: 2017/6/6.
 */
public class CustomerAccountVO implements Serializable {

    private String customerId;
    private String account;
    private Integer customerType;
    private String password;
    private String mobile;
    private String email;
    private String QQ;
    private String wechat;
    private int idcardCheckStatus;
    private Integer status;
    private Date createTime;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

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

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getIdcardCheckStatus() {
        return idcardCheckStatus;
    }

    public void setIdcardCheckStatus(int idcardCheckStatus) {
        this.idcardCheckStatus = idcardCheckStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "CustomerAccountVO{" +
                "customerId='" + customerId + '\'' +
                ", account='" + account + '\'' +
                ", customerType=" + customerType +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}

package ltd.moore.ctravel.pay.model;


import java.util.Date;

/**
 * TradeOrder对象
 * @author Cocouzx
 * @version 1.0
 */

public class OrderInfoVO  {


    /**
     * /体验ID
     */

    private String experienceId;

    /**
     *  /客户ID
     */

    private String customerId;

    /**
     *  /服务时间ID
     */

    private String serviceTimeId;

    /**
     *  /订单ID
     */

    private String orderId;

    /**
     *     /第三方流水号
     */

    private String thirdOrderId;

    /**
     *   /第四方流水号
     */

    private String fourthOrderId;

    /**
     *   /支付方式
     */

    private int payMethod;

    /**
     * /第三方支付账号
     */
    private String thirdAccountId;

    /**
     * /交易类型
     */
    private int tradeType;

    /**
     * /交易状态.0交易未开始1待支付2支付成功3支付失败4退款成功
     */
    private int tradeStatus;

    /**
     * /交易失败原因
     */
    private String msg;

    /**
     * /交易时间
     */
    private Date tradeTime;

    /**
     * /交易金额
     */
    private double tradeAmount;

    /**
     * 生成时间
     */

    private Date createTime;

    /**
     * 体验名称
     */
    private String title;
    /**
     * 体验时间
     */
    private Date startTime;
    private Date endTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExperienceId() {
        return  experienceId;
    }

    public void setExperienceId(String  experienceId) {
        this.experienceId = experienceId;
    }

    public String getCustomerId() {
        return  customerId;
    }

    public void setCustomerId(String  customerId) {
        this.customerId = customerId;
    }

    public String getServiceTimeId() {
        return  serviceTimeId;
    }

    public void setServiceTimeId(String  serviceTimeId) {
        this.serviceTimeId = serviceTimeId;
    }

    public String getOrderId() {
        return  orderId;
    }

    public void setOrderId(String  orderId) {
        this.orderId = orderId;
    }

    public String getThirdOrderId() {
        return  thirdOrderId;
    }

    public void setThirdOrderId(String  thirdOrderId) {
        this.thirdOrderId = thirdOrderId;
    }

    public String getFourthOrderId() {
        return  fourthOrderId;
    }

    public void setFourthOrderId(String  fourthOrderId) {
        this.fourthOrderId = fourthOrderId;
    }

    public int getPayMethod() {
        return  payMethod;
    }

    public void setPayMethod(int  payMethod) {
        this.payMethod = payMethod;
    }

    public String getThirdAccountId() {
        return  thirdAccountId;
    }

    public void setThirdAccountId(String  thirdAccountId) {
        this.thirdAccountId = thirdAccountId;
    }

    public int getTradeType() {
        return  tradeType;
    }

    public void setTradeType(int  tradeType) {
        this.tradeType = tradeType;
    }

    public int getTradeStatus() {
        return  tradeStatus;
    }

    public void setTradeStatus(int  tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getMsg() {
        return  msg;
    }

    public void setMsg(String  msg) {
        this.msg = msg;
    }

    public Date getTradeTime() {
        return  tradeTime;
    }

    public void setTradeTime(Date  tradeTime) {
        this.tradeTime = tradeTime;
    }

    public double getTradeAmount() {
        return  tradeAmount;
    }

    public void setTradeAmount(double  tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

}
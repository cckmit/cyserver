package com.cy.common.utils.alipay.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: AlipayResult</p>
 * <p>Description: 支付返回类型</p>
 * <p>注: out_biz_no、buyer_logon_id、seller_email、receipt_amount、invoice_amount、buyer_pay_amount、point_amount、fund_bill_list参数暂时不会返回，预计在2016年11月中旬即将开放</p>
 *
 * @author LiuZhen
 * @Company 博视创诚
 * @data 2016-10-14 11:01
 */
public class AlipayResult implements Serializable {
    public final static String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY" ; // 支付状态:交易创建，等待买家付款
    public final static String TRADE_STATUS_TRADE_CLOSED = "TRADE_CLOSED" ; // 支付状态:未付款交易超时关闭，或支付完成后全额退款
    public final static String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS" ; // 支付状态:交易支付成功
    public final static String TRADE_STATUS_TRADE_FINISHED = "TRADE_FINISHED" ; // 支付状态:交易结束，不可退款

    private String notify_time     ;	// 通知时间（通知的发送时间。格式为yyyy-MM-dd HH:mm:ss）
    private String notify_type     ;	// 通知类型（通知的类型）
    private String notify_id       ;	// 通知校验ID（通知校验ID）
    private String sign_type       ;	// 签名类型（签名算法类型，目前支持RSA）
    private String sign            ;	// 签名（请参考异步返回结果的验签）
    private String trade_no        ;	// 支付宝交易号（支付宝交易凭证号）
    private String app_id          ;	// 开发者的app_id（支付宝分配给开发者的应用Id）
    private String out_trade_no    ;	// 商户订单号（原支付请求的商户订单号）
    private String out_biz_no      ;	// 商户业务号（商户业务ID，主要是退款通知中返回退款申请的流水号）****
    private String buyer_id        ;	// 买家支付宝用户号（买家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字）
    private String buyer_logon_id  ;	// 买家支付宝账号（买家支付宝账号）*****
    private String seller_id       ;	// 卖家支付宝用户号（卖家支付宝用户号）
    private String seller_email    ;	// 卖家支付宝账号（卖家支付宝账号）*****
    private String trade_status    ;	// 交易状态（交易目前所处的状态，WAIT_BUYER_PAY:交易创建，等待买家付款;TRADE_CLOSED:未付款交易超时关闭，或支付完成后全额退款;TRADE_SUCCESS:交易支付成功;TRADE_FINISHED:交易结束，不可退款）
    private String total_amount    ;	// 订单金额（本次交易支付的订单金额，单位为人民币（元））
    private String total_fee    ;	// 订单金额（本次交易支付的订单金额，单位为人民币（元））
    private String receipt_amount  ;	// 实收金额（商家在交易中实际收到的款项，单位为元）******
    private String invoice_amount  ;	// 开票金额（用户在交易中支付的可开发票的金额）******
    private String buyer_pay_amount;	// 付款金额（用户在交易中支付的金额）******
    private String point_amount    ;	// 集分宝金额（使用集分宝支付的金额）******
    private String refund_fee      ;	// 总退款金额（退款通知中，返回总退款金额，单位为元，支持两位小数）
    private String subject         ;	// 订单标题（商品的标题/交易标题/订单标题/订单关键字等，是请求时对应的参数，原样通知回来）
    private String body            ;	// 商品描述（该订单的备注、描述、明细等。对应请求时的body参数，原样通知回来）
    private String gmt_create      ;	// 交易创建时间（该笔交易创建的时间。格式为yyyy-MM-dd HH:mm:ss）
    private String gmt_payment     ;	// 交易付款时间（该笔交易的买家付款时间。格式为yyyy-MM-dd HH:mm:ss）
    private String gmt_refund      ;	// 交易退款时间（该笔交易的退款时间。格式为yyyy-MM-dd HH:mm:ss.S）
    private String gmt_close       ;	// 交易结束时间（该笔交易结束时间。格式为yyyy-MM-dd HH:mm:ss）
    private String fund_bill_list  ;	// 支付金额信息（支付成功的各个渠道金额信息，详见资金明细信息说明）******

    public String getNotify_time() {
        return notify_time;
    }

    public void setNotify_time(String notify_time) {
        this.notify_time = notify_time;
    }

    public String getNotify_type() {
        return notify_type;
    }

    public void setNotify_type(String notify_type) {
        this.notify_type = notify_type;
    }

    public String getNotify_id() {
        return notify_id;
    }

    public void setNotify_id(String notify_id) {
        this.notify_id = notify_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_biz_no() {
        return out_biz_no;
    }

    public void setOut_biz_no(String out_biz_no) {
        this.out_biz_no = out_biz_no;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getBuyer_logon_id() {
        return buyer_logon_id;
    }

    public void setBuyer_logon_id(String buyer_logon_id) {
        this.buyer_logon_id = buyer_logon_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getReceipt_amount() {
        return receipt_amount;
    }

    public void setReceipt_amount(String receipt_amount) {
        this.receipt_amount = receipt_amount;
    }

    public String getInvoice_amount() {
        return invoice_amount;
    }

    public void setInvoice_amount(String invoice_amount) {
        this.invoice_amount = invoice_amount;
    }

    public String getBuyer_pay_amount() {
        return buyer_pay_amount;
    }

    public void setBuyer_pay_amount(String buyer_pay_amount) {
        this.buyer_pay_amount = buyer_pay_amount;
    }

    public String getPoint_amount() {
        return point_amount;
    }

    public void setPoint_amount(String point_amount) {
        this.point_amount = point_amount;
    }

    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getGmt_payment() {
        return gmt_payment;
    }

    public void setGmt_payment(String gmt_payment) {
        this.gmt_payment = gmt_payment;
    }

    public String getGmt_refund() {
        return gmt_refund;
    }

    public void setGmt_refund(String gmt_refund) {
        this.gmt_refund = gmt_refund;
    }

    public String getGmt_close() {
        return gmt_close;
    }

    public void setGmt_close(String gmt_close) {
        this.gmt_close = gmt_close;
    }

    public String getFund_bill_list() {
        return fund_bill_list;
    }

    public void setFund_bill_list(String fund_bill_list) {
        this.fund_bill_list = fund_bill_list;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }
}

package com.cy.alipay;

import java.io.Serializable;

public class PayEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String service;// 接口名称
	private String partner;// 合作者身份ID
	private String _input_charset;// 参数编码字符集
	private String sign_type;// 签名方式
	private String sign;// 签名
	private String notify_url;// 服务器异步通知页面路径
	private String return_url;// 页面跳转同步通知页面路径
	private String error_notify_url;// 请求出错时的通知页面路径
	private String out_trade_no;// 商户网站唯一订单号
	private String subject;//商品名称
	private String payment_type;//支付类型
	private String total_fee;//交易金额
	private String seller_id;//卖家支付宝用户号
	private String buyer_id;//买家支付宝用户号
	private String seller_email;//卖家支付宝账号
	private String buyer_email;//买家支付宝账号
	private String seller_account_name;//卖家别名支付宝账号
	private String buyer_account_name;//买家别名支付宝账号
	private String price;//商品单价
	private String quantity;//购买数量
	private String body;//商品描述
	private String show_url;//商品展示网址
	private String paymethod;//默认支付方式
	private String enable_paymethod;//支付渠道
	private String need_ctu_check;//网银支付时是否做CTU校验
	private String royalty_type;//提成类型
	private String royalty_parameters;//分润账号集
	private String anti_phishing_key;//防钓鱼时间戳
	private String exter_invoke_ip;//客户端IP
	private String extra_common_param;//公用回传参数
	private String extend_param;//公用业务扩展参数
	private String it_b_pay;//超时时间
	private String default_login;//自动登录标识
	private String product_type;//商户申请的产品类型
	private String token;//快捷登录授权令牌
	private String item_orders_info;//商户回传业务参数
	private String sign_id_ext;//商户买家签约号
	private String sign_name_ext;//商户买家签约名
	private String qr_pay_mode;//扫码支付方式

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String get_input_charset() {
		return _input_charset;
	}

	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
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

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getReturn_url() {
		return return_url;
	}

	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}

	public String getError_notify_url() {
		return error_notify_url;
	}

	public void setError_notify_url(String error_notify_url) {
		this.error_notify_url = error_notify_url;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public String getSeller_account_name() {
		return seller_account_name;
	}

	public void setSeller_account_name(String seller_account_name) {
		this.seller_account_name = seller_account_name;
	}

	public String getBuyer_account_name() {
		return buyer_account_name;
	}

	public void setBuyer_account_name(String buyer_account_name) {
		this.buyer_account_name = buyer_account_name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getShow_url() {
		return show_url;
	}

	public void setShow_url(String show_url) {
		this.show_url = show_url;
	}

	public String getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	public String getEnable_paymethod() {
		return enable_paymethod;
	}

	public void setEnable_paymethod(String enable_paymethod) {
		this.enable_paymethod = enable_paymethod;
	}

	public String getNeed_ctu_check() {
		return need_ctu_check;
	}

	public void setNeed_ctu_check(String need_ctu_check) {
		this.need_ctu_check = need_ctu_check;
	}

	public String getRoyalty_type() {
		return royalty_type;
	}

	public void setRoyalty_type(String royalty_type) {
		this.royalty_type = royalty_type;
	}

	public String getRoyalty_parameters() {
		return royalty_parameters;
	}

	public void setRoyalty_parameters(String royalty_parameters) {
		this.royalty_parameters = royalty_parameters;
	}

	public String getAnti_phishing_key() {
		return anti_phishing_key;
	}

	public void setAnti_phishing_key(String anti_phishing_key) {
		this.anti_phishing_key = anti_phishing_key;
	}

	public String getExter_invoke_ip() {
		return exter_invoke_ip;
	}

	public void setExter_invoke_ip(String exter_invoke_ip) {
		this.exter_invoke_ip = exter_invoke_ip;
	}

	public String getExtra_common_param() {
		return extra_common_param;
	}

	public void setExtra_common_param(String extra_common_param) {
		this.extra_common_param = extra_common_param;
	}

	public String getExtend_param() {
		return extend_param;
	}

	public void setExtend_param(String extend_param) {
		this.extend_param = extend_param;
	}

	public String getIt_b_pay() {
		return it_b_pay;
	}

	public void setIt_b_pay(String it_b_pay) {
		this.it_b_pay = it_b_pay;
	}

	public String getDefault_login() {
		return default_login;
	}

	public void setDefault_login(String default_login) {
		this.default_login = default_login;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getItem_orders_info() {
		return item_orders_info;
	}

	public void setItem_orders_info(String item_orders_info) {
		this.item_orders_info = item_orders_info;
	}

	public String getSign_id_ext() {
		return sign_id_ext;
	}

	public void setSign_id_ext(String sign_id_ext) {
		this.sign_id_ext = sign_id_ext;
	}

	public String getSign_name_ext() {
		return sign_name_ext;
	}

	public void setSign_name_ext(String sign_name_ext) {
		this.sign_name_ext = sign_name_ext;
	}

	public String getQr_pay_mode() {
		return qr_pay_mode;
	}

	public void setQr_pay_mode(String qr_pay_mode) {
		this.qr_pay_mode = qr_pay_mode;
	}

}

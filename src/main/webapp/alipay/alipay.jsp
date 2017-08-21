<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="com.cy.alipay.util.AlipaySubmit"%>
<%@page import="com.cy.alipay.config.AlipayConfig"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	String sHtmlText = request.getParameter("sHtmlText") ;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%--
<%
	String service = "create_direct_pay_by_user";

	String partner = AlipayConfig.partner;

	String _input_charset = AlipayConfig._input_charset;

	String notify_url = AlipayConfig.notify_url;

	String return_url = AlipayConfig.return_url;

	//订单编号
	String out_trade_no = request.getParameter("out_trade_no");

	//订单名称
	String subject = request.getParameter("subject");

	//支付类型,4为捐赠，1为商品购买
	String payment_type = "4";

	//付款金额
	String total_fee = request.getParameter("total_fee");

	String seller_id = AlipayConfig.seller_email;
	
	String anti_phishing_key = AlipaySubmit.query_timestamp();
	//若要使用请调用类文件submit中的query_timestamp函数

	//客户端的IP地址
	String exter_invoke_ip = AlipayConfig.exter_invoke_ip;

	Map<String, String> sParaTemp = new HashMap<String, String>();
	sParaTemp.put("service", "create_direct_pay_by_user");
	sParaTemp.put("partner", partner);
	sParaTemp.put("_input_charset", _input_charset);
	sParaTemp.put("notify_url", notify_url);
	sParaTemp.put("return_url", return_url);
	sParaTemp.put("out_trade_no", out_trade_no);
	sParaTemp.put("subject", subject);
	sParaTemp.put("payment_type", payment_type);
	sParaTemp.put("total_fee", total_fee);
	sParaTemp.put("seller_email",seller_id);
	sParaTemp.put("anti_phishing_key", anti_phishing_key);
	sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
	//建立请求
	String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "post", "确认");
	out.println(sHtmlText);
%>
</head>
--%>

<body>
<%--11111111--%>
<%--${sHtmlText}--%>
<%--<form id="alipaysubmit" name="alipaysubmit" action="https://mapi.alipay.com/gateway.do?_input_charset=utf-8" method="get">--%>
<form id="alipaysubmit" name="alipaysubmit" action="https://openapi.alipaydev.com/gateway.do?_input_charset=utf-8" method="get">
	<input type="hidden" name="seller_email" value="jwoaah6169@sandbox.com"/>
	<input type="hidden" name="_input_charset" value="utf-8"/>
	<input type="hidden" name="subject" value="捐赠"/>
	<input type="hidden" name="sign" value="ce6d5f146130d04233699a1951296628"/>
	<input type="hidden" name="notify_url" value="http://114.215.196.18/alipay/paynotify.jsp"/>
	<input type="hidden" name="body" value="爱心捐赠"/>
	<input type="hidden" name="payment_type" value="1"/>
	<input type="hidden" name="out_trade_no" value="1111111"/>
	<input type="hidden" name="partner" value="2088102174974449"/>
	<input type="hidden" name="service" value="create_direct_pay_by_user"/>
	<input type="hidden" name="total_fee" value="0.01"/>
	<input type="hidden" name="return_url" value="http://114.215.196.18/alipay/payresult.jsp"/>
	<input type="hidden" name="sign_type" value="MD5"/>
	<input type="hidden" name="show_url" value="http://localhost:8080/cy/project/projectAction!doNotNeedSessionAndSecurity_getById.action?id=2&accountNum=448"/>
	<input type="submit" value="确认" style="display:none;">
</form>
<script>document.forms['alipaysubmit'].submit();</script>

</body>
</html>

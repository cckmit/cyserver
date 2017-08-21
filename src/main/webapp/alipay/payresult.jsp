<%@page import="com.cy.system.Global"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.cy.system.SpringManager"%>
<%@page import="com.cy.core.donation.service.DonationService"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.cy.core.donation.entity.Donation"%>
<%@page import="com.cy.alipay.util.AlipayNotify"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

</head>

<body>
	<%
		String deptNo = Global.deptNo;
		//获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号

		String out_trade_no = request.getParameter("out_trade_no");
		//支付宝交易号

		String trade_no = request.getParameter("trade_no");
		//交易状态
		String trade_status = request.getParameter("trade_status");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		//交易金额
		String  total_fee = request.getParameter("total_fee");
	
		//是否扫码支付
		String business_scene = request.getParameter("business_scene");
	
		//支付时间
		String gmt_payment = request.getParameter("gmt_payment");
		
		//计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);

		if (verify_result) {//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码

			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				Donation donation = new Donation();
				donation.setOrderNo(out_trade_no);
				donation.setPayMoney(Double.parseDouble(total_fee));
				if(business_scene!=null&&business_scene.length()>0&&business_scene.equals("qrpay")){
					donation.setPayMode("支付宝扫码支付");
				}else{
					donation.setPayMode("支付宝");
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(gmt_payment!=null&&gmt_payment.length()>0){
					donation.setPayTime(dateFormat.parse(gmt_payment));
				}else{
					donation.setPayTime(new Date());
				}
				donation.setPayStatus(1);
				donation.setAlipayNumber(trade_no);
				DonationService donationService= SpringManager.getBean("donationService", DonationService.class);
				donationService.updateFromShouXin(donation);
			}

			//该页面可做页面美工编辑
		//	out.println("验证成功<br />");
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
			if("000090".equals(deptNo)){
				request.getRequestDispatcher("../znufe/donation_success.jsp").forward(request, response);
			}else{
				request.getRequestDispatcher("../web/donation_success.jsp").forward(request, response);
			}
			//////////////////////////////////////////////////////////////////////////////////////////
		} else {
			//该页面可做页面美工编辑
			out.println("验证失败");
		}
	%>
</body>
</html>

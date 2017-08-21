<%@page import="com.cy.core.znufe.global.Global"%>
<%@ page language="java" import="java.util.*,com.cy.system.*"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>




<!DOCTYPE html>
<!--[if lt IE 7 ]>
<html class="ie ie6"> <![endif]-->
<!--[if IE 7 ]>
<html class="ie ie7"> <![endif]-->
<!--[if IE 8 ]>
<html class="ie ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html>
<!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>中南财经政法大学校友捐赠</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/jumbotron-narrow.css" rel="stylesheet">

<jsp:include page="../web/webHead1.jsp" flush="true" />
</head>

<body>
<div class="container">
	<div class="page-header">
		<h1>校友捐赠</h1>
	</div>
	
	<div class="jumbotron">
		<h1>感谢您，亲爱的校友</h1>
			<p class="lead">欢迎您进行网上捐赠，中南财经政法大学校友会非常感谢您的捐赠！</p>
	</div>
    <div class="sixteen columns">
        <div class="ajax-contact-form">
            <div class="form">
                <div class="form-holder">
                    <div class="notification canhide"></div>
                    <form id="frm_contact" name="frm_contact" action="../alipay/alipay.jsp" method="post" onsubmit="return check();">
                        <h4 class="headline">在线支付</h4>
                        <p>欢迎您进行网上捐赠，中南财经政法大学校友会非常感谢您的捐赠！</p>
                        <div class="field">
                            <label>订单编号</label>
                            <div class="inputs">
                                <span>${donation.orderNo}</span>
                                <input type="hidden" name="out_trade_no" id="out_trade_no" value="${donation.orderNo}">
                            </div>
                        </div>
                        <div class="field">
                            <label>捐赠人姓名</label>
                            <div class="inputs">
                                <span>${donation.x_name}</span>
                                 <input type="hidden" name="subject" id="subject" value="${donation.project.projectName}">
                            </div>
                        </div>
                         <div class="field">
                            <label>捐赠金额</label>
                            <div class="inputs">
                                <span class="prize">${donation.money}</span> 元
                                <input type="hidden" name="total_fee" id="total_fee" value="${donation.money}">
                            </div>
                        </div>
                        <div class="field">
                            <label for="payment">支付方式</label>
                            <span class="payment alipay"><input type="radio" checked value="alipay" hidden /> <img src="../web/images/alipay.png" alt="支付宝"/></span>
                        </div>

                        <div class="form-submit">
                            <button type="submit" id="submit" name="submit" style="float: left">去支付</button>
                        </div>

                    </form>

                </div>

            </div>

        </div>

    </div>
</div>
</body>
</html>
<script>
	function check(){
		if($('#out_trade_no').val()==''||$('#subject').val()==''||$('#total_fee').val()==''){
			alert('捐赠信息有误');
			return false;
		}
	}
</script>
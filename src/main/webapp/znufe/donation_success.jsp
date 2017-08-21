<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/znufe/";
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
<link href="<%=basePath%>css/bootstrap.min.css" rel="stylesheet">
<link href="<%=basePath%>css/jumbotron-narrow.css" rel="stylesheet">
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
	</div>
	<div class="container">
    <div class="sixteen columns">
        <h2 class="centered error-404"><i class="fa fa-check-circle" style="color: #104d0b"></i></h2>

        <p class="centered p-20" style="margin-bottom: 25px;font-size: 18px">捐赠成功，感谢您的热心捐赠。</p>

        <p class="centered"><a href="../znufe/donation.jsp" class="btn big colored"><i class="fa fa-arrow-circle-left"></i>返回捐赠首页</a></p>
    </div>
</div>
	
</body>
</html>

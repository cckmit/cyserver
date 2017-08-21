<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>校友智能管理与社交服务平台</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
		<jsp:include page="../../inc.jsp"></jsp:include>
		<script type="text/javascript">
	function keyLogin(e)
	{
		var key = e.which || event.keyCode;
		if (key == 13) //回车键的键值为13  
			$("#login1").click(); //调用登录按钮的登录事件  
	}
	$(function()
	{
		$('#kaptchaImage').prop('src', '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_captchaImage.action?date=' + new Date().getTime());
	});
	function loadImage()
	{
		$('#kaptchaImage').prop('src', '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_captchaImage.action?date=' + new Date().getTime());
	}
	function login()
	{
		var userAccount = $('#userAccount').val().trim();
		var userPassword = $('#userPassword').val().trim();
		var validCode = $('#validCode').val().trim();
		if (userAccount == '')
		{
			$('#note').text("请输入帐号");
			$('#userAccount').focus();
			return;
		}
		if (userPassword == '')
		{
			$('#note').text("请输入密码");
			$('#userPassword').focus();
			return;
		}
		if (validCode == '')
		{
			$('#note').text("请输入验证码");
			$('#validCode').focus();
			return;
		}
		$.ajax({
			method : 'POST',
			url : '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_loginx.action?date=' + new Date().getTime(),
			data : {
				'userAccount' : userAccount,
				'userPassword' : userPassword,
				'validCode' : validCode
			},
			dataType : 'json',
			success : function(result)
			{
				if (result.success)
				{
					$('#note').text("登录成功页面跳转中....");
					location.href = "${pageContext.request.contextPath}/page/alumni/mainx.jsp";
				} else
				{
					$('#login1').prop('disabled', false);
					$('#validCode').val('');
					$('#note').text(result.msg);
					$('#kaptchaImage').attr('src', '${pageContext.request.contextPath}/login/loginAction!doNotNeedSessionAndSecurity_captchaImage.action?date=' + new Date().getTime());
				}
			},
			beforeSend : function()
			{
				$('#note').text('登陆中....');
				$('#login1').prop('disabled', 'disabled');
			}
		});
	}
</script>
 <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	<script src="${pageContext.request.contextPath}/jslib/html5.js"></script>
<![endif]-->
	</head>
	<body onkeydown="keyLogin(event)">
	<!--[if lt IE 9]>
	<div style='border: 1px solid #F7941D; background: #FEEFDA; text-align: center; clear: both; height: 75px; position: relative;'>
	    <div style='position: absolute; right: 3px; top: 3px; font-family: courier new; font-weight: bold;'><a href='#' onclick='javascript:this.parentNode.parentNode.style.display="none"; return false;'><img src='images/ie6nomore-cornerx.jpg' style='border: none;' alt='Close this notice'/></a></div>
	    <div style='width: 640px; margin: 0 auto; text-align: left; padding: 0; overflow: hidden; color: black;'>
	        <div style='width: 75px; float: left;'><img src='images/ie6nomore-warning.jpg' alt='Warning!'/></div>
	        <div style='width: 275px; float: left; font-family: Arial, sans-serif;'>
	            <div style='font-size: 14px; font-weight: bold; margin-top: 12px;'>您正在使用已经过时的浏览器！</div>
	            <div style='font-size: 12px; margin-top: 6px; line-height: 12px;'>由于IE的安全问题以及对互联网标准的支持问题，建议您升级您的浏览器，以达到更好的浏览效果！</div>
	        </div>
	        <div style='width: 75px; float: left;'><a href='https://www.mozilla.org/zh-CN/firefox/new/' target='_blank'><img src='images/ie6nomore-firefox.png' style='border: none;' alt='下载Firefox'/></a></div>
	        <div style='width: 75px; float: left;'><a href='http://windows.microsoft.com/zh-cn/internet-explorer/download-ie' target='_blank'><img src='images/ie6nomore-ie.png' style='border: none;' alt='下载 Internet Explorer 11'/></a></div>
	        <div style='width: 73px; float: left;'><a href='http://se.360.cn/' target='_blank'><img src='images/ie6nomore-360.png' style='border: none;' alt='下载360浏览器'/></a></div>
	        <div style='float: left;'><a href='http://www.google.com/chrome' target='_blank'><img src='images/ie6nomore-chrome.png' style='border: none;' alt='下载Google Chrome'/></a></div>
	    </div>
	</div>
	<![endif]-->
		<div class="main">
			<div class="logo">
				<img src="images/logo.png" width="332" alt="">
			</div>
			<div class="box login">
				<form id="loginForm">
					<fieldset class="boxBody">
						<div id="note" style="height: 20px; color: red; font-size: 13px; text-align: center;"></div>
						<label>
							用户名
						</label>
						<input type="text" id="userAccount" tabindex="1" placeholder="请输入用户名" required>
						<label>
							密码
						</label>
						<input type="password" id="userPassword" tabindex="2" required placeholder="请输入密码">
						<label>
							验证码
						</label>
						<input type="text" id="validCode" tabindex="3" class="vcodeinput" required placeholder="请输入验证码">
						<div style="margin-top: 12px;">
							<img title="点击刷新验证码"  id="kaptchaImage" onclick="loadImage()" width="96px" height="24px">
						</div>
					</fieldset>
					<footer>
						<input type="submit" class="btnLogin" id="login1" onclick="login()" value="登 录" tabindex="4">
					</footer>
				</form>
			</div>
		</div>
	</body>
</html>

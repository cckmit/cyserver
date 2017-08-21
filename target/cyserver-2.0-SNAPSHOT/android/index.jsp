<%@page import="com.cy.core.clientrelease.service.ClientService"%>
<%@page import="com.cy.system.SpringManager"%>
<%@page import="com.cy.core.clientrelease.entity.Client"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

	Client client = SpringManager.getBean("clientService", ClientService.class).selectNewOne();
	String url = basePath + "android/SchoolmateChat.apk";
	if (client != null) {
		url = client.getUrl();
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no" />
<meta name="format-detection" content="telephone=no">
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<title>下载</title>
<link rel="stylesheet" type="text/css" href="../css/pure-nr.css">
<!-- icon fonts -->
<link type="text/css" rel="stylesheet"
	href="../css/font-awesome.min.css">
<style type="text/css">
        body {
            background: #E4E4E4;
        }
        #mobile {
            max-width: 768px;
            margin: 0 auto;
            background: #fff;
            padding-bottom: 20px;
        }
        #mobile .logo {
            padding: 5px;
        }
        .download {
            margin-top: 20px;
        }
        .download .btn,.download .btn {
            display: block;
            width: 280px;
            padding: 15px 0;
            margin: 0 auto;
            text-align: center;
            text-decoration: none;
            font-size: 16px;
            background:#4FC1E9;
            border-radius: 5px;
            color: #FFF;
            margin-top: 20px;
        }
        .download .btn .fa{
            font-size: 24px;
        }
        .ios .download-android, .iphone .download-android,.ipad .download-android, .android .download-ios
            ,.ios .android-tips, .iphone .android-tips,.ipad .android-tips, .android .ios-tips
        {
            display: none;
        }
        .download blockquote {
            font-size: 12px;
            color: #a3a6a9;
            line-height: 150%;
        }
        .download blockquote em {
            font-style: normal;
            color: #4FC1E9;
        }
        .weixin {
            position: absolute;
            top: 0;
            bottom: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.7);
        }
        .weixin .arrow {
            text-align: right;
            padding-top: 10px;
            padding-right: 20px;
        }
        .weixin p {
            color: #FFF;
            font-size: 18px;
            line-height: 24px;
            padding: 20px;
            margin: 0;
        }
    </style>
<script type="text/javascript" src="../jslib/device.min.js"></script>
<script type="text/javascript" src="../jslib/jquery-1.11.1.min.js"></script>
</head>
<body>
	<div id="mobile">
		<div class="header">
			<div class="title-pic">
				<img src="../images/download-title.jpg" width="100%">
			</div>
		</div>
		<div class="main">
			<div class="download">
				<div class="download-ios">
					<a
						href="itms-services://?action=download-manifest&url=https://app.hdtht.com/ios/genaeralcy/cyty.plist"
						class="btn"><i class="fa fa-apple"></i>下载iOS版</a>
					<blockquote>请在弹出的提示框点击安装，如果提示无法连接到app.hdtht.com，请更换网络或刷新重试，如一直无法下载,请联系我们的客服热线：400-027-1816,如果您使用的是ios9,请参考<a href="http://mp.weixin.qq.com/s?__biz=MzA3MDc5MTg2Mw==&mid=209028327&idx=1&sn=e2a306aab0fb52edc749344e398df0ea&scene=23&srcid=0923hughPYXaUF9LNAOXtEd1#rd">ios9窗友安装教程</a></blockquote>
				</div>
				<div class="download-android">
					<a href="<%=url%>" class="btn"><i class="fa fa-android"></i>下载Android版</a>
					<blockquote>
						请在弹出的提示框点击下载后安装<br> <em>如果您是在微信中访问此页面，请点击右上角“ <i
							class="fa fa-ellipsis-v"></i> ”图标，选择在浏览器中打开;</em> <br>如果提示无法下载，请更换网络或刷新重试，如一直无法下载,请联系我们的客服热线：400-027-1816
					</blockquote>
				</div>
			</div>
		</div>
		<div class="weixin" style="display: none">
        <div class="arrow"><img src="../images/arrow.png" alt=""/></div>
        <p class="ios-tips">您是在微信中访问此页面，请点击右上角“ <i class="fa fa-ellipsis-h"></i> ”图标，选择在Safari中打开;</p>
        <p class="android-tips">您是在微信中访问此页面，请点击右上角“ <i class="fa fa-ellipsis-v"></i> ”图标，选择在浏览器中打开;</p>
    </div>
	</div>
	<script>
    $(document).ready(function() {
        function isWeiXin(){
            var ua = window.navigator.userAgent.toLowerCase();
            if(ua.match(/MicroMessenger/i) == 'micromessenger'){
                return true;
            }else{
                return false;
            }
        }
        if(isWeiXin()){
            $('.weixin').show();
        }
    });
</script>
</body>
</html>
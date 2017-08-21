<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0">
    <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
    <!-- UC强制全屏 -->
    <meta name="full-screen" content="yes">
    <!-- QQ强制全屏 -->
    <meta name="x5-fullscreen" content="true">
    <title>捐赠</title>
    <link href="../services/static/css/Hui.css" rel="stylesheet" type="text/css">
    <style type="text/css"></style>
</head>
<body>
<header id="header"></header>
<script type="text/javascript" src="../js/H.js" ></script>
<script type="text/javascript" src="../js/B.js" ></script>
<script type="text/javascript">
    // 接收URL中的参数
    var accountNum = B.getUrlParamByName("accountNum");
    //	    	H.alert("服务head：accountNum=" + accountNum);

    // 加载body部分
    H.openFrameNavOrFoot("index_body", "../services/index_body.html?accountNum=" + accountNum, "#header");
</script>
</body>
</html>
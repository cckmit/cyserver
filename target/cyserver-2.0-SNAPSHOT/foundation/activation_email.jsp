<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>激活成功</title>
    <style type="text/css">
        html,body{
            font-family: "微软雅黑", tahoma, verdana, arial, helvetica, sans-serif;
        }
        .myDiv {
            width:300px;
            height:200px;
            position:absolute;
            left:50%;
            top:50%;
            margin:-100px 0 0 -150px;
            text-align: center;
            line-height: 200px;
            font-size: 30px;
            color: #666;
        }
        .secound {
            position: absolute;
            width: 100%;
            bottom: 150px;
            height: 36px;
            z-index: 999;
            text-align: center;
            font-size: 18px;
            color: #a1a1a1;
        }
        .secound span{
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="myDiv"></div>
<div class="secound"></div>
</body>

<script src="${pageContext.request.contextPath}/mobile/js/zepto.min.js" type="text/javascript" ></script>

<script type="text/javascript">

    var s=3;
    $(function () {
        if(${msg.success}){
            $('.myDiv').css('color','#3598db');
        }else{
            $('.myDiv').css('color','#575757');
        }
        $('.myDiv').text('${msg.msg}');
        $('.secound').html(s+'秒后，<span onclick="closeThisWindow()" style="color:#3598db">前往校友会官网</span>');
        window.setInterval(jian,1000);
    });
    function jian() {
        s--;
        $('.secound').html(s+'秒后，<span onclick="closeThisWindow()" style="color:#3598db">前往校友会官网</span>');
        if(s == 0){
            closeThisWindow();
        }
    }

    function closeThisWindow() {
        location.href = "${pageContext.request.contextPath}";
    }
</script>
</html>
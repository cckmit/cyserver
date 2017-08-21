<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <title>捐赠</title>
    <meta name="Description" content="捐赠" />
    <meta name="Keywords" content="窗友,捐赠" />
    <meta name="author" content="Rainly" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="../../mobile/css/cy_core.css">
    <link rel="stylesheet" href="../../mobile/css/font-awesome.min.css">
    <link rel="stylesheet" href="../../mobile/css/wkd_services.css">
</head>
<body>
<section class="ui-container">
    <div class="ui-banner">
        <div style="background-image:url(../wkd_images/jz_top.jpg)"></div>
    </div>
    <h1>筹款项目</h1>
    <div class="ui-row-flex ui-whitespace">
        <div class="ui-col jz-item" data-href="show_1.html">
            <img src="../wkd_images/jz_1.jpg" alt=""/>
            人才培养
        </div>
        <div class="ui-col jz-item" data-href="show_2.html">
            <img src="../wkd_images/jz_2.jpg" alt=""/>
            教师发展
        </div>
        <div class="ui-col jz-item" data-href="show_3.html">
            <img src="../wkd_images/jz_3.jpg" alt=""/>
            基本建设
        </div>
        <div class="ui-col jz-item" data-href="show_4.html">
            <img src="../wkd_images/jz_4.jpg" alt=""/>
            创业创新
        </div>
    </div>
    <div class="ui-row-flex ui-whitespace">
        <div class="ui-col jz-item" data-href="show_5.html">
            <img src="../wkd_images/jz_5.jpg" alt=""/>
            校园文化
        </div>
        <div class="ui-col jz-item" data-href="show_6.html">
            <img src="../wkd_images/jz_6.jpg" alt=""/>
            院部发展
        </div>
        <div class="ui-col jz-item" data-href="show_7.html">
            <img src="../wkd_images/jz_7.jpg" alt=""/>
            志愿服务
        </div>
        <div class="ui-col jz-item" data-href="show_8.html">
            <img src="../wkd_images/jz_8.jpg" alt=""/>
            助力科大
        </div>
    </div>
    <h1>捐赠方法</h1>
    <div class="ui-row-flex ui-whitespace">
        <div class="ui-col" data-href="show_9.html">
            <i class="fa fa-credit-card"></i> 银行汇款
        </div>
        <div class="ui-col" data-href="show_10.html">
            <i class="fa fa-credit-card"></i> 网上支付
        </div>
    </div>
</section>
<script src="../../mobile/js/zepto.js"></script>
<script src="../../mobile/js/cy_core.js"></script>
<script>
    Zepto(function($){
        $(document).on('tap','.ui-col',function(){
            if($(this).data('href')){
                location.href= $(this).data('href');
            }
        });
        var tab = new fz.Scroll('.ui-tab', {
            role: 'tab',
            autoplay: false
        });
    });
</script>
</body>
</html>
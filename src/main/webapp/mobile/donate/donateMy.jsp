<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/mobile/";
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
    <link rel="stylesheet" href="<%=basePath%>css/cy_core.css">
    <link rel="stylesheet" href="<%=basePath%>css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/donation.css">
</head>
<body>
<footer class="ui-footer ui-footer-btn">
    <ul class="ui-tiled ui-border-t">
        <li data-href="donateIndex.jsp?accountNum=${param.accountNum}"><i class="fa fa-newspaper-o"></i>捐赠动态</li>
			<li data-href="donateNew.jsp?accountNum=${param.accountNum}"><i class="fa fa-heart-o"></i>最新捐赠</li>
			<li data-href="donateItem.jsp?accountNum=${param.accountNum}"><i class="fa fa-lightbulb-o"></i>捐赠项目</li>
			<li data-href="donateMy.jsp?accountNum=${param.accountNum}"  class="current"><i class="fa fa-user"></i>我的捐赠</li>
		
    </ul>
</footer>
<section class="ui-container">
    <div class="wrapper">
        <div class="inner">
            <ul class="ui-list ui-list-active ui-list-link ui-border-tb" id="donateHistoryList">
            </ul>
        </div>
    </div>
</section>
<script src="<%=basePath%>js/zepto.js"></script>
<script src="<%=basePath%>js/cy_core.js"></script>
<script src="<%=basePath%>js/dropload.min.js"></script>
<script src="<%=basePath%>js/template_donate.js"></script>
<script src="<%=basePath%>js/custom_donate.js"></script>
<script>
    	var page=1;
    	var rows=10;
    Zepto(function($){
        //下拉刷新
        var dropload = $('.inner').dropload({
            domUp : {
                domClass   : 'dropload-up',
                domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-down"></i>下拉刷新</div>',
                domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-up"></i> 释放更新</div>',
                domLoad    : '<div class="dropload-load"><div class="ui-loading-wrap"><i class="ui-loading"></i><p>加载中</p></div></div>'
            },
            domDown : {
                domClass   : 'dropload-down',
                domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-up"></i> 上拉加载更多</div>',
                domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-down"></i> 释放加载</div>',
                domLoad    : '<div class="dropload-load"><div class="ui-loading-wrap"><i class="ui-loading"></i><p>加载中</p></div></div>'
            },
            loadUpFn : function(me){
            	page=1;
                getPageData("${pageContext.request.contextPath}/donation/donationAction!doNotNeedSessionAndSecurity_listAll.action?page="+page+"&rows="+rows+"&accountNum="+${param.accountNum},"#donateHistoryList","update",donateHistoryListTpl,me);
            },
            loadDownFn : function(me){
            	page=page+1;
                getPageData("${pageContext.request.contextPath}/donation/donationAction!doNotNeedSessionAndSecurity_listAll.action?page="+page+"&rows="+rows+"&accountNum="+${param.accountNum}, "#donateHistoryList", "more", donateHistoryListTpl, me);
            }
        });

        //页面加载
        getPageData("${pageContext.request.contextPath}/donation/donationAction!doNotNeedSessionAndSecurity_listAll.action?page="+page+"&rows="+rows+"&accountNum="+${param.accountNum},"#donateHistoryList","onload",donateHistoryListTpl,null);
    });

</script>
</body>
</html>
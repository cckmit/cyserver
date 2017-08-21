<%@ page language="java" pageEncoding="UTF-8"  import="com.cy.util.*" %>
<%

String eventId = request.getParameter("eventId");
String userInfoId = request.getParameter("userInfoId");


%>
<!DOCTYPE HTML>
<html>
  <head>
    <meta charset="UTF-8">
    <title>活动</title>
    <meta name="Description" content="窗友活动" />
    <meta name="Keywords" content="窗友,活动,讲座,聚会,班会,交友" />
    <meta name="author" content="Rainly" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" type="text/css" href="../css/global.css?v1">
    <link rel="stylesheet" type="text/css" href="../css/events.css?v1">
    <link rel="stylesheet" type="text/css" href="../css/font-awesome.min.css?v1">
  </head>
  <body>
  <div class="wrapper no-header no-footer">
    <div class="inner">
      <div class="event-actor">
        <ul class="ui-list ui-list-function ui-border-tb">
        </ul>
      </div>
    </div>
  </div>
  <script src="../js/zepto.min.js" type="text/javascript"></script>
  <script src="../js/zepto.selector.min.js" type="text/javascript"></script>
  <script src="../js/global.js" type="text/javascript"></script>
  <script src="../js/dropload.min.js" type="text/javascript"></script>
  <script src="../js/template.js" type="text/javascript"></script>

<script type="text/javascript">
var userInfoIdStr = '<%= userInfoId %>';
var totalRows = 0;
// dropload
Zepto(function($){
	
    //页面加载
    getData("mobEventAction!doNotNeedSessionAndSecurity_pullEventSignList.action?eventSignData.eventId=<%= eventId %>",".ui-list","onload",actorListTpl);
	

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
            getData("mobEventAction!doNotNeedSessionAndSecurity_pullEventSignList.action?eventSignData.eventId=<%= eventId %>&eventSignData.currentRow=0&eventSignData.incremental=" + $(".ui-list > li").length,".ui-list","update",actorListTpl,me);
        },
        loadDownFn : function(me){
        	
        	
        	if($(".ui-list > li").length >= totalRows)
			{
				if (me != null) {
			  				
					me.resetload();
				}
			  	$(".inner").append('<div class="dropload-down" style="-webkit-transition: all 300ms; transition: all 300ms; height: 40px;"><div class="dropload-load"><div class="ui-loading-wrap">没有更多了</div></div></div>');
			  			
			  			
			}
			else
			{
				getData("mobEventAction!doNotNeedSessionAndSecurity_pullEventSignList.action?eventSignData.eventId=<%= eventId %>&eventSignData.currentRow=" + $(".ui-list > li").length,".ui-list","more",actorListTpl,me);
			}
        	
            
        }
    });
});
</script>
  </body>
</html>
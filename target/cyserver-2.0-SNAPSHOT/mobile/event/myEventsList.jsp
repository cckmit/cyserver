<%@ page language="java" pageEncoding="UTF-8"  import="com.cy.util.*" %>
<%

	
String userInfoId = request.getParameter("accountNum");

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
  <div class="ui-tab has-footer">
    <ul class="ui-tab-nav ui-border-b">
      <li class="current">我参与的</li>
      <li>我创建的</li>
    </ul>
    <ul class="ui-tab-content" style="width:200%">
      <li class="current" id="joined">
        <div class="inner">
          <ul class="ui-list ui-border-tb">
          </ul>
          <div class="more"><a href="javascript:;" alt="1">点击查看更多</a></div>
          <div class="ui-loading-wrap" style="display:none"><i class="ui-loading"></i><p>加载中</p></div>
        </div>
      </li>
      <li id="created">
        <div class="inner">
          <ul class="ui-list ui-border-tb">
          </ul>
          <div class="more"><a href="javascript:;" alt="3">点击查看更多</a></div>
          <div class="ui-loading-wrap" style="display:none"><i class="ui-loading"></i><p>加载中</p></div>
        </div>
      </li>
    </ul>
  </div>
  <div class="footer">
    <ul class="footer-menu">
        <%-- <li class="local"><a href="index.jsp?accountNum=<%=//userInfoId%>&listType=5"><i class="fa fa-map-marker"></i>本地</a></li> --%>
        <li class="all"><a href="index.jsp?accountNum=<%=userInfoId%>"><i class="fa fa-home"></i>全部</a></li>
        <li class="official"><a href="index.jsp?accountNum=<%=userInfoId%>&listType=0"><i class="fa fa-fire"></i>官方</a></li>
        <li class="mine current"><a href="myEventsList.jsp?accountNum=<%=userInfoId%>"><i class="fa fa-user"></i>我的</a></li>
        
        <%--
        <li class="create"><a href="create.jsp?accountNum=<%=userInfoId%>" class="special-btn"><i class="fa fa-flag"></i>创建</a></li>
        --%>
        
      </ul>
  </div>
  <script src="../js/zepto.min.js" type="text/javascript"></script>
  <script src="../js/zepto.selector.min.js" type="text/javascript"></script>
  <script src="../js/global.js" type="text/javascript"></script>
  <script src="../js/dropload.min.js" type="text/javascript"></script>
  <script src="../js/template.js" type="text/javascript"></script>
  <script type="text/javascript">
// dropload
Zepto(function($){
	
	$.getJSON("mobEventAction!doNotNeedSessionAndSecurity_getNumOfNotifyForMyEvents.action?userInfoId=<%=userInfoId%>", function(items){
		if(items != null){
			
			if(items['joinedPeople'] == 0){
				$( ".mine i" ).removeClass( "ui-reddot" );
			}else{
				$( ".mine i" ).addClass( "ui-reddot" );
			}
			
		}else{
			$( ".mine i" ).removeClass( "ui-reddot" );
		}
    	
    });	
	
  //页面加载
  getData("mobEventAction!doNotNeedSessionAndSecurity_pullEventList.action?eventData.type=1&eventData.userInfoId=<%=userInfoId%>","#joined .ui-list","onload",myEventList);
  getData("mobEventAction!doNotNeedSessionAndSecurity_pullEventList.action?eventData.type=3&eventData.userInfoId=<%=userInfoId%>","#created .ui-list","onload",myEventList);
  
  
  $(document).on('click','.more',function(){
	
	  var tmpUrl = '';
      var target = $(this);
      if(target.children("a").attr("alt")==1){
    	  tmpUrl = 'mobEventAction!doNotNeedSessionAndSecurity_pullEventList.action?eventData.type='+ target.children("a").attr("alt") +'&eventData.userInfoId=<%=userInfoId%>&eventData.currentRow=' + $("#joined .ui-list > li").length;
      }else{
    	  tmpUrl = 'mobEventAction!doNotNeedSessionAndSecurity_pullEventList.action?eventData.type='+ target.children("a").attr("alt") +'&eventData.userInfoId=<%=userInfoId%>&eventData.currentRow=' + $("#created .ui-list > li").length;
      }
     
    	  
      $.ajax({
            type: 'GET',
            url: tmpUrl,
            dataType: 'json',
            beforeSend: function(xhr, settings) {
              target.hide();
              target.next().show();
              console.log(target.next())
            },
            success: function(data){
              var result = '';
              var result=$.tpl(myEventList,data);
              $(target.prev()).append(result);
            },
            error: function(xhr, type){
              //alert('Ajax error!');
            },
            complete: function(xhr, type){
              target.show();
              target.next().hide();
            }
        });
  });
  window.addEventListener('load', function(){
    var tab = new fz.Scroll('.ui-tab', {
        role: 'tab',
        autoplay: false,
        interval: 3000
    });
  })
})
</script>
  </body>
</html>
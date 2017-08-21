<%@ page language="java" pageEncoding="UTF-8"  import="java.util.*, com.cy.util.*" %>
<%

String eventId = request.getParameter("eventId");
String userInfoId = request.getParameter("userInfoId");


String currentServerDate = WebUtil.formatDateByPattern(new Date(), WebUtil.YMDHMSS);

//System.out.println(currentServerDate);

%>

<!DOCTYPE HTML>
<html>
  <head>
    <meta charset="UTF-8">
    <title>活动详情</title>
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

  <script src="../js/zepto.min.js" type="text/javascript"></script>
  <script src="../js/global.js" type="text/javascript"></script>
  <script src="../js/dropload.min.js" type="text/javascript"></script>
  <script src="../js/template.js" type="text/javascript"></script>

  <script type="text/javascript">
  var currentServerDate = '<%= currentServerDate %>';
  var eventIdStr = '<%= eventId %>';
  var userInfoIdStr = '<%= userInfoId %>';
  
  var dia = null;
  
  Zepto(function($){ 
    //页面加载
    getData("mobEventAction!doNotNeedSessionAndSecurity_getEventDetail.action?eventSignData.eventId=<%= eventId %>&eventSignData.userInfoId=<%= userInfoId %>","body","onload",eventDetailTpl);

    $(document).on('click','.show-detail',function(){
      $(".event-detail article").toggle();
      $(".show-detail .fa").toggleClass("fa-chevron-right");
      $(".show-detail .fa").toggleClass("fa-chevron-down");
    });
    
     $(document).on('click','.ui-tooltips',function(){
	      var temContent = $(this).children(".ui-tooltips-cnt").text();
	      //alert(temContent);
	      //console.log(temContent);
	      dia=$.dialog({
	        title:'通知',
	        content: temContent,
	        button:["关闭"]
	      });
	    });
	 $(document).on('click','.ui-tooltips .ui-icon-close',function(){
      $(".wrapper").addClass("no-header");
      $(this).parent().hide();
      doEventViewNotification();
    });
    
    $(document).on('click','.signup',function(){
      dia=$.dialog({
        title:'温馨提示',
        content:'您要报名该活动吗？',
        button:["确认报名","取消"]
      });
      
     
      

      dia.on("dialog:action",function(e){
        console.log(e.index);
        if(e.index == 0){
          //这里写提交数据代码
          doEventJoin();
         // $(".signup").text("已报名").removeClass("ui-btn-primary").removeClass("signup");
        }
      });
      dia.on("dialog:hide",function(e){
        console.log("dialog hide");
      });
    });
    
     $(document).on('click','.signIn',function(){
      dia=$.dialog({
        title:'请输入签到码',
        content:'<div class="sign-in-code"><input type="text" id="signInCode" maxlength="4"><p>签到码请找活动发起人获取</p></div>',
        button:["签到","取消"]
      });

      dia.on("dialog:action",function(e){
        console.log(e.index);
        if(e.index == 0){
          //这里写提交数据代码
        doEventSign();
		//$(".signIn").text("已签到").removeClass("ui-btn-green").removeClass("signIn");
        }
      });
      
       
      
      dia.on("dialog:hide",function(e){
        console.log("dialog hide");
      });
    });
    
    
    $(document).on('click','.deleteEvent',function(){
      dia=$.dialog({
        title:'温馨提示',
        content:'您要删除该活动吗？',
        button:["确认","取消"]
      });
      
     
      

      dia.on("dialog:action",function(e){
        console.log(e.index);
        if(e.index == 0){
          //这里写提交数据代码
          doDeleteEvent();
         
        }
      });
      dia.on("dialog:hide",function(e){
        console.log("dialog hide");
      });
    });
    
    
  });
  
  
  	function doDeleteEvent()
  	{
  		$.ajax({
				url : "mobEventAction!doNotNeedSessionAndSecurity_deleteEvent.action?" + 
						"eventData.id=" + eventIdStr + 
						"&eventData.userInfoId=" + userInfoIdStr,
				
				dataType : 'json',
		        type: "POST",
				
		        success: function(data) 
		        {
		        	
		        	if(data.status == true)
		        	{
		        		dia=$.dialog({
					        title:'温馨提示',
					        content:'操作成功！',
					        button:["确认"]
					      });
					      
					      location.replace("myEventsList.jsp?accountNum=" + userInfoIdStr);
		        	}
		        	else
		        	{
		        		dia=$.dialog({
					        title:'温馨提示',
					        content:'操作失败，请重试！',
					        button:["确认"]
					      });
		        	}
		        	
		        },
		        complete: function()
		        {
		        	dia.on("dialog:hide",function(e){
					        console.log("dialog hide");
					});
		        }
		    });
	
  	
  	}
  
  	function doEventViewNotification()
	{
		$.ajax({
				url : "mobEventAction!doNotNeedSessionAndSecurity_updateEventSign.action?" + 
						"eventSignData.eventId=" + eventIdStr + 
						"&eventSignData.userInfoId=" + userInfoIdStr,
				
				dataType : 'json',
		        type: "POST"
		    });
	}
  
  
  	function doEventSign()
	{
		var signInCode = document.getElementById('signInCode');
		
		
		
		if(signInCode.value == '')
		{
			dia=$.dialog({
		        title:'温馨提示',
		        content:'请输入签到码！',
		        button:["确认"]
		      });
		}
		else	
		{
			
			$.ajax({
				url : "mobEventAction!doNotNeedSessionAndSecurity_eventSign.action?" + 
						"eventSignData.eventId=" + eventIdStr + 
						"&eventSignData.userInfoId=" + userInfoIdStr + 
						"&eventData.signInCode=" + signInCode.value,
				
				dataType : 'json',
		        type: "POST",
				
		        success: function(data) 
		        {
		        	
		        	if(data.status == true)
		        	{
		        		dia=$.dialog({
					        title:'温馨提示',
					        content:'签到成功！',
					        button:["确认"]
					      });
		        		$(".signIn").text("已签到").removeClass("ui-btn-green").removeClass("signIn");
		        	}
		        	else
		        	{
		        		dia=$.dialog({
					        title:'温馨提示',
					        content:'签到失败，请重试！',
					        button:["确认"]
					      });
		        	}
		        	
		        },
		        complete: function()
		        {
		        	dia.on("dialog:hide",function(e){
					        console.log("dialog hide");
					});
		        }
		    });
		}

	}
	
	
	
	function doEventJoin()
	{
		$.ajax({
				url : "mobEventAction!doNotNeedSessionAndSecurity_eventJoin.action?" + 
						"eventSignData.eventId=" + eventIdStr + 
						"&eventSignData.userInfoId=" + userInfoIdStr,

				
				dataType : 'json',
		        type: "POST",
				
		        success: function(data) 
		        {
		        	
		        	if(data.status == true)
		        	{
		        		dia=$.dialog({
					        title:'温馨提示',
					        content:'报名成功！',
					        button:["确认"]
					      });
		        		$(".signup").text("已报名").removeClass("ui-btn-primary").removeClass("signup");
		        		getData("mobEventAction!doNotNeedSessionAndSecurity_getEventDetail.action?eventSignData.eventId=<%= eventId %>&eventSignData.userInfoId=<%= userInfoId %>","body","onload",eventDetailTpl);
		        	}
		        	else
		        	{
		        		dia=$.dialog({
					        title:'温馨提示',
					        content:'报名失败或人数已满！',
					        button:["确认"]
					      });
		        	}
		        	
		        },
		        complete: function()
		        {
		        	dia.on("dialog:hide",function(e){
					        console.log("dialog hide");
					});
		        }
		    });

	}
	
	
	
	
  
  
  </script>
  </body>
</html>
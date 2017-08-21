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
    <link rel="stylesheet" type="text/css" href="../css/global.css">
    <link rel="stylesheet" type="text/css" href="../css/cy_core.css">
    <link rel="stylesheet" type="text/css" href="../css/events.css">
    <link rel="stylesheet" type="text/css" href="../css/font-awesome.min.css">
    
  </head>
  <body>
  <div class="wrapper no-header">
    <div class="inner">
      <div class="create-event">
        <div class="ui-form ui-border-t">
          <form id="saveForm" method="post">
            <div class="ui-form-item ui-border-b">
              <label for="title">活动标题</label>
              <input type="text" name="eventData.title" id="title" maxlength="120" placeholder="请不要超过120个字">
            </div>
            <div class="ui-form-item ui-border-b">
              <label for="category">活动类别</label>
              <select name="eventData.category" id="category">
			  </select>

              <a href="#" class="ui-icon-link"></a>
            </div>
            <div class="ui-border-b">
              <div class="upload-pic"><input type="file" id="pic" name="pic" style="display: none;"><input type="hidden" name="eventData.pic" id="picValue" /><a href="javascript:;">上传活动图片</a></div>
            </div>
            <!-- <div class="ui-form-item ui-form-item-textarea ui-border-b">
              <label for="place">活动地域</label>
              	<div class="ui-form-item">
			      <div class="ui-select-group">
			        <div class="ui-select">
			          <select name="regionProvince" id="regionProvinceId" onchange="javascript:getCityFromProvince();">
			  		  </select>
			        </div>
			        <div class="ui-select">
			          <select name="regionCity" id="regionCityId">
			          </select>
			        </div>
			      </div>
			    </div>
            </div> -->
<!--             <div class="ui-form-item">
              <label>活动地域</label>
              <select name="regionProvince" id="regionProvinceId" onchange="javascript:getCityFromProvince();">
			  		  </select>

              <a href="#" class="ui-icon-link"></a>
              </div> 
              <div class="ui-form-item ui-border-b">
              <label></label>
              <select name="regionCity" id="regionCityId">
			          </select>

              <a href="#" class="ui-icon-link"></a>
            </div>
            -->
            <div class="ui-form-item ui-form-item-textarea ui-border-b">
              <label for="place">活动地址</label>
              <textarea name="eventData.place" id="place" maxlength="120" placeholder="请填写活动的地址"></textarea>
            </div>
            <div class="ui-form-item ui-form-item-textarea ui-border-b">
              <label for="content">活动介绍</label>
              <textarea name="eventData.content" id="content" placeholder="请填写活动介绍"></textarea>
            </div>
            <div class="ui-form-item ui-border-b">
              <label for="signupStartTime">报名开始</label>
              <input type="datetime-local" name="eventData.signupStartTime" id="signupStartTime" placeholder="请选择报名开始时间">
              <a href="#" class="ui-icon-link"></a>
            </div>
            <div class="ui-form-item ui-border-b">
              <label for="signupEndTime">报名截止</label>
              <input type="datetime-local" name="eventData.signupEndTime" id="signupEndTime" placeholder="请选择报名截止时间">
              <a href="#" class="ui-icon-link"></a>
            </div>
            <div class="ui-form-item ui-border-b">
              <label for="startTime">活动开始</label>
              <input type="datetime-local" name="eventData.startTime" id="startTime" placeholder="请选择活动开始时间">
              <a href="#" class="ui-icon-link"></a>
            </div>
            <div class="ui-form-item ui-border-b">
              <label for="endTime">活动结束</label>
              <input type="datetime-local" name="eventData.endTime" id="endTime" placeholder="请选择活动结束时间">
              <a href="#" class="ui-icon-link"></a>
            </div>
            <!-- <div class="ui-form-item ui-border-b">
              <label for="minPeople">人数下限</label>
              <input type="text" name="eventData.minPeople" id="minPeople" placeholder="留空则为不设下限">
            </div> -->
            <div class="ui-form-item ui-border-b">
              <label for="maxPeople">人数上限</label>
              <input type="text" name="eventData.maxPeople" id="maxPeople" placeholder="留空则为不设上限">
          
            </div>
            <div class="ui-form-item ui-form-item-switch ui-border-b">
              <span>需要签到</span>
              <label for="needSignIn" class="ui-switch">
                <input type="checkbox" name="eventData.needSignIn" id="needSignIn" value="1"/>
              </label>
            </div>
            <input type="hidden" name="eventData.userInfoId" value="<%=userInfoId %>" />
            <input type="hidden" name="eventData.region" id="regionId" value="" />
          </form>
        </div>
      </div>
    </div>
  </div>
  <div class="footer">
    <ul class="footer-btn">
      <li><button class="ui-btn-lg ui-btn-primary" id="sumbit"><i class="fa fa-user-plus"></i> 创建活动</button></li>
    </ul>
  </div>
  
  <script src="../js/zepto.min.js" type="text/javascript"></script>
  <script src="../js/global.js" type="text/javascript"></script>
  <script src="../js/dropload.min.js" type="text/javascript"></script>
  <script src="../js/lrz.mobile.min.js"></script>
  

  <script type="text/javascript">
  
  	function getCityFromProvince(){
  				
  		//alert($('#regionProvinceId').val());
  		
  		$.getJSON("${pageContext.request.contextPath}/mobile/serv/mobServAction!doNotNeedSessionAndSecurity_getMobCapitalFromProvince.action?provinceId="+$('#regionProvinceId').val(), function(items){
  			$('#regionCityId').empty();
  	    	for(var i = 0; i < items.length; i++){
  	    		$('#regionCityId').append('<option value="' + items[i]['cityName'] + '">' + items[i]['cityName'] + '</option>');
  	        }
  	    	$('#regionCityId').trigger('change');
  	    	
  	    });
  	
  	}
  	
    $(".upload-pic a").click(function(){
      $("#pic").click();
    })
    var input = document.querySelector('#pic');
    var selectStr = "";
    $.getJSON("mobEventAction!doNotNeedSessionAndSecurity_getDicts.action?dictTypeName="+ encodeURI('活动类别'), function(items){
    	for(var i = 0; i < items.length; i++){
    		$('#category').append('<option value="' + items[i]['dictName'] + '">' + items[i]['dictName'] + '</option>');
        }
    	$('#category').trigger('change');
    });
    
    /* $.getJSON("${pageContext.request.contextPath}/mobile/serv/mobServAction!doNotNeedSessionAndSecurity_getMobProvinceAndId.action", function(items){
    	for(var i = 0; i < items.length; i++){
    		$('#regionProvinceId').append('<option value="' + items[i]['id'] + '">' + items[i]['provinceName'] + '</option>');
        }
    	$('#regionProvinceId').trigger('change');
    	//alert($('#regionProvinceId').value);
    }); */
    
    
    
    
    input.onchange = function () {
    	
    	var fileExt = document.getElementById('pic').value;
        if(fileExt != null && fileExt != ''){
      	  fileExt = fileExt.substr(fileExt.lastIndexOf("."), fileExt.length);
      	  if(fileExt == null || fileExt == ''){
	      	  $.dialog({
	      	       title:'温馨提示',
	      	       content:'请确认上传的文件包含正确的扩展名',
	      	       button:["确认"]
	      	  });
	  		  return;
      	  }
      	  
      	  fileExt = fileExt.toLowerCase();
      	  if(fileExt=='.gif' || fileExt=='.jpg' || fileExt=='.png' || fileExt=='.bmp' || fileExt=='.jpeg'){
      	  }else{
      		$.dialog({
	      	     title:'温馨提示',
	      	     content:'请上传正确的图片文件',
	      	     button:["确认"]
	      	});
	  		return;
      	  }
      	  
        }
        /* else{
        	$.dialog({
	      	     title:'温馨提示',
	      	     content:'请上传正确的图片文件',
	      	     button:["确认"]
	      	});
	  		return;
        } */
    	
        lrz(this.files[0], {
            width: 400,
            quality: 0.8,
            done: function (results) {
              $(".upload-pic a").css({
                "background-image": "url("+results.base64+")",
                "text-indent": "-999em"
              });
              
              document.getElementById('picValue').value = results.base64;
              
              //alert(results.base64Len);
                  // 你需要的数据都在这里，可以以字符串的形式传送base64给服务端转存为图片。
                  //console.log(results);
                  //report = document.querySelector('#report');
                  //report.innerHTML ='<img src="'+results.base64+'"><p>压缩后：'+results.base64.length+'</p><p>压缩前'+results.origin.size+'</p>';

            }
        });
    }
    $('#sumbit').click(function(){
    	
      if($('#title').val().trim()==''){
      	  $.dialog({
      	        title:'温馨提示',
      	        content:'请输入活动标题',
      	        button:["确认"]
      	      });
  		  return;
  	  }
      
      if($('#category').val().trim()=='请选择活动类别'){
      	  $.dialog({
      	        title:'温馨提示',
      	        content:'请输入活动类别',
      	        button:["确认"]
      	      });
  		  return;
  	  }
      
      /* if($('#regionProvinceId').val().trim()=='' || $('#regionCityId').val().trim()==''){
      	  $.dialog({
      	        title:'温馨提示',
      	        content:'请选择活动地域',
      	        button:["确认"]
      	      });
  		  return;
  	  }else{
  		  
  		 var obj = document.getElementById('regionProvinceId');
  		 
  		 document.getElementById("regionId").value  = obj.options[obj.selectedIndex].text.trim() + " " + $('#regionCityId').val().trim();
  		 //alert(document.getElementById("regionId").value);
  	  } */
         
      
      if($('#place').val().trim()==''){
      	  $.dialog({
      	        title:'温馨提示',
      	        content:'请输入活动地址',
      	        button:["确认"]
      	      });
  		  return;
  	  }
    	
      if($('#content').val().trim()==''){
    	  $.dialog({
    	        title:'温馨提示',
    	        content:'请输入活动介绍',
    	        button:["确认"]
    	      });
		  return;
	  }	
      
      if($('#signupStartTime').val().trim()==''){
    	  $.dialog({
    	        title:'温馨提示',
    	        content:'请输入活动报名开始时间',
    	        button:["确认"]
    	      });
		  return;
	  }
      
      if($('#signupEndTime').val().trim()==''){
    	  $.dialog({
    	        title:'温馨提示',
    	        content:'请输入活动报名截止时间',
    	        button:["确认"]
    	      });
		  return;
	  }
      
      if($('#startTime').val().trim()==''){
    	  $.dialog({
    	        title:'温馨提示',
    	        content:'请输入活动开始时间',
    	        button:["确认"]
    	      });
		  return;
	  }
      
      if($('#endTime').val().trim()==''){
    	  $.dialog({
    	        title:'温馨提示',
    	        content:'请输入活动结束时间',
    	        button:["确认"]
    	      });
		  return;
	  }
    	
      var startTime = new Date(Date.parse($('#startTime').val()));
	  var endTime = new Date(Date.parse($('#endTime').val()));
	  var signupStartTime = new Date(Date.parse($('#signupStartTime').val()));
	  var signupEndTime = new Date(Date.parse($('#signupEndTime').val()));
	  
	  if(isNaN(startTime.getTime())){
		  $.dialog({
	  	        title:'温馨提示',
	  	        content:'请输入正确的活动开始时间',
	  	        button:["确认"]
	  	      });
			  return;
		  
	  }
	  
	  if(isNaN(endTime.getTime())){
		  $.dialog({
	  	        title:'温馨提示',
	  	        content:'请输入正确的活动结束时间',
	  	        button:["确认"]
	  	      });
			  return;
		  
	  }
	  
	  if(isNaN(signupStartTime.getTime())){
		  $.dialog({
	  	        title:'温馨提示',
	  	        content:'请输入正确的活动报名开始时间',
	  	        button:["确认"]
	  	      });
			  return;  
	  }
	  
	  if(isNaN(signupEndTime.getTime())){
		  $.dialog({
	  	        title:'温馨提示',
	  	        content:'请输入正确的活动报名截止时间',
	  	        button:["确认"]
	  	      });
			  return;
      }
	  
	  if(signupStartTime > signupEndTime) {
		  $.dialog({
	        title:'温馨提示',
    	    content:'报名开始时间必须早于截止时间',
    	    button:["确认"]
    	  });
		  
		  return;
	  }
	  
	  if(startTime > endTime) {
		  $.dialog({
    	    title:'温馨提示',
    	    content:'活动开始时间必须早于结束时间',
    	    button:["确认"]
    	  });
	      
		  return;
	  }
	  
	  if(signupStartTime > startTime) {
		  
		  $.dialog({
	        title:'温馨提示',
	        content:'报名开始时间必须早于活动开始时间',
	        button:["确认"]
	      });
		  
		  return;
	  }
	  if(signupEndTime > endTime) {
		  $.dialog({
		    title:'温馨提示',
		    content:'报名截止时间必须早于活动结束时间',
		    button:["确认"]
		  });
		  
		  return;
	  }
		
	  var tmpMsg = '';
      
      var dia=$.dialog({
        title:'温馨提示',
        content:'请确认信息填写无误，一旦提交无法修改！',
        button:["确认创建","返回修改"]
      });
      
      dia.on("dialog:action",function(e){
        console.log(e.index);
        if(e.index == 0){
        	
        	$.ajax({
				url : 'mobEventAction!doNotNeedSessionAndSecurity_saveMobEvent.action?isWlight=true',
				data : $('form').serialize(),
				dataType : 'json',
				type: "post",
				success : function(result)
				{
					if (result.success)
					{
						var dia2=$.dialog({
			                title:'温馨提示',
			                content:result.msg,
			                button:["确认"]
			              });
			            
			            dia2.on("dialog:action",function(e){
			                console.log(e.index);
			                if(e.index == 0){
			              	  window.location.href = 'index.jsp?accountNum=<%=userInfoId%>';
			                }
			            });
						
					} else
					{
						var dia2=$.dialog({
			                title:'温馨提示',
			                content:result.msg,
			                button:["确认"]
			              });
					}
				},
				beforeSend : function()
				{
					
				},
				complete : function()
				{	
					
				}
			});
        	
        	
            
        	
        }
      });
      
      
      
      dia.on("dialog:hide",function(e){
        console.log("dialog hide")
      });
      dia2.on("dialog:hide",function(e){
          console.log("dialog hide")
        });
    })
    

    
  </script>
  </body>
</html>
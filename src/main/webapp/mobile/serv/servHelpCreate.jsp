<!DOCTYPE html>
<html>

<%@ page language="java" pageEncoding="UTF-8"  import="com.cy.util.*" %>

<%

String accountNum = request.getParameter("accountNum");

String category = request.getParameter("category")==null?"1":request.getParameter("category");

%>

<head>
  <title></title>
  <meta name="Description" content="校友帮帮忙" />
  <meta name="Keywords" content="窗友,校友帮帮忙" />
  <meta name="author" content="Rainly" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="format-detection" content="telephone=no">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <link rel="stylesheet" href="../css/cy_core.css">
  <link rel="stylesheet" href="../css/font-awesome.min.css">
  <link rel="stylesheet" href="../css/appeal.css">
</head>
<body>

<footer class="ui-footer ui-footer-stable ui-btn-group ui-border-t">
  <button class="ui-btn-lg ui-btn-primary" id="sumbit">发 送</button>
</footer>
<form id="saveForm" method="post">
<section class="ui-container create-post">
  <div class="ui-form-item ui-form-item-pure ui-form-item-textarea ui-border-b">
    <textarea name="cyServ.content" id="content" placeholder="说点什么..."></textarea>
  </div>
  
  
  <%--
  <div class="ui-form-item ui-form-item-textarea ui-border-b">
              <label for="place">地域</label>
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
            </div>
            
      --%>       
    
</section>
<input type="hidden" name="cyServ.region" id="regionId" value="" />
<input type="hidden" name="cyServ.type" id="helpType" value="9" />
</form>

<script src="../js/zepto.min.js" type="text/javascript"></script>

<script src="../js/global.js" type="text/javascript"></script>
<script src="../js/dropload.min.js" type="text/javascript"></script>
<script src="../js/lrz.mobile.min.js"></script>

<script type="text/javascript">
<%--
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


$.getJSON("${pageContext.request.contextPath}/mobile/serv/mobServAction!doNotNeedSessionAndSecurity_getMobProvinceAndId.action", function(items){
	for(var i = 0; i < items.length; i++){
		$('#regionProvinceId').append('<option value="' + items[i]['id'] + '">' + items[i]['provinceName'] + '</option>');
    }
	$('#regionProvinceId').trigger('change');
	//alert($('#regionProvinceId').value);
});
--%>

$('#sumbit').click(function(){
	
	if($('#content').val().trim()==''){
  	  $.dialog({
  	        title:'温馨提示',
  	        content:'请输入相关内容',
  	        button:["确认"]
  	      });
		  return;
	  }
	
	<%--
	if($('#regionProvinceId').val().trim()=='' || $('#regionCityId').val().trim()==''){
    	  $.dialog({
    	        title:'温馨提示',
    	        content:'请选择地域',
    	        button:["确认"]
    	      });
		  return;
	  }else{
		  
		 var obj = document.getElementById('regionProvinceId');
		 
		 document.getElementById("regionId").value  = obj.options[obj.selectedIndex].text.trim() + " " + $('#regionCityId').val().trim();
		 //alert(document.getElementById("regionId").value);
	  }
	  --%>
	
	var tmpMsg = '';
    
    var dia=$.dialog({
      title:'温馨提示',
      content:'请确认信息填写无误',
      button:["确认创建","返回修改"]
    });
    
    dia.on("dialog:action",function(e){
      console.log(e.index);
      if(e.index == 0){
      	
      	$.ajax({
				url : 'mobServAction!doNotNeedSessionAndSecurity_insertServ.action?isWlight=true&cyServ.accountNum=<%=accountNum%>&cyServ.category=<%=category%>',
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
			              	  window.location.href = 'favourList.jsp?accountNum=<%=accountNum%>&isWhat=1&category=<%=category%>';
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
      console.log("dialog hide");
    });
    dia2.on("dialog:hide",function(e){
      console.log("dialog hide");
    });
	
	
});


</script>

</body>
</html>
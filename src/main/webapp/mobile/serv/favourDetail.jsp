<%@ page language="java" pageEncoding="UTF-8"  import="com.cy.util.*" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%

	String id = request.getParameter("id");
	String category = request.getParameter("category");
	String accountNum = request.getParameter("accountNum");
	String region = request.getParameter("region");
	String isWhat =  request.getParameter("isWhat");//0或空:全部，1:所有，2:我的
	String sourceFlag = request.getParameter("sourceFlag");//0:互帮互助，1:项目合作

	JSONObject titleObj = new JSONObject();

	StringBuffer bu = new StringBuffer();

	if("1".equals(sourceFlag)){// 项目合作
		if("1".equals(isWhat)){
			bu.append("/mobile/serv/favourListXmhz.jsp?region=");
		}
		else if("2".equals(isWhat)){
			bu.append("/mobile/serv/favourMyListXmhz.jsp?region=");
		}
		titleObj.put("title", "项目详情");
	}
	else if("0".equals(sourceFlag)){// 互帮互助
		if("1".equals(isWhat)){
			bu.append("/mobile/serv/favourListHbhz.jsp?region=");
		}
		else if("2".equals(isWhat)){
			bu.append("/mobile/serv/favourMyListHbhz.jsp?region=");
		}
		titleObj.put("title", "互助详情");
	}

	bu.append(region);
	bu.append("&accountNum=");
	bu.append(accountNum);
	bu.append("&category=");
	bu.append(category);
	bu.append("&isWhat=");
	bu.append(isWhat);

	titleObj.put("backUrl", bu.toString());
	titleObj.put("backTitle","返回");

	String title = titleObj.toJSONString();

%>

<!DOCTYPE html>
<html>
<head>
  <title><%=title%></title>
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
  <!-- 图片展示 -->
  <link rel="stylesheet" href="../css/photoswipe.css">
  <link rel="stylesheet" href="../css/default-skin/default-skin.css">
</head>
<body>
<footer class="ui-footer ui-footer-btn ui-mini-footer">
  <div id="reply">
    <div class="reply ui-border-radius">
      <input id="commentContent" type="text" maxlength="255" placeholder="我也说一句...">
    </div>
    <button class="ui-btn ui-btn-primary" onclick="doComment();">回复</button>
  </div>
</footer>
<section class="ui-container">
  <div class="wrapper">
    <div class="inner">
      <div class="comment-list">
      </div>
    </div>
  </div>
</section>
<script src="../js/zepto.js"></script>

<script src="../js/cy_core.js"></script>
<script src="../js/dropload.min.js"></script>
<script src="../js/photoswipe.min.js"></script>
<script src="../js/photoswipe-ui-default.min.js"></script>
<script src="../js/photoswipe-appeal-min.js"></script>
<script src="../js/template_appeal.js?201509141146"></script>
<script src="../js/custom_appeal.js"></script>
<script src="../js/B.js"></script>
<script>
	
	var id = '<%= id %>';
	var category = '<%= category %>';
	var accountNum = '<%= accountNum %>';
	var region = '<%= region %>';
	var isWhat =  '<%= isWhat %>';	
	
	var isMyList = false;
	
  var totalRows = 0;


	var jsonStr = {
		"title":"详情",
		"btn1":{
			"imgname":"icon_Back@2x.png",
			"imgversion":"20170214",
			"imgbase64":"iVBORw0KGgoAAAANSUhEUgAAABoAAAAsCAYAAAB7aah+AAAAAXNSR0IArs4c6QAAAN1JREFUWAntlkEOgkAMRRkTVl7EA3gPD+D53MjGa7lyr4vxF2gghkIHqnHxmzQdMp3+6YOkVFWw5ZxP8Cu8Di49lOtFnohiN3i8GIpKJyqCZWuxYig5JdJrdZ3thsbXrUQEJxt4PCa90kIn0tF2dBRR3K5IXC5MmkRcSsIVicuFSZOIS0l4owy+M3xuaF1SSi9vQTMP76aGy4CyTP4DZIpuNxSiGDHaBPiB2GwKdoixAJadSow2m4Kdf8N4x4X2Bfe3U2c6e2DvaJ9csTMhFi+i9xqJfU/kQ+ygzz+Pb1Kohi6oSMRTAAAAAElFTkSuQmCC",
			"name":"返回",
			"action":"fallback"
		},
		"btn2":{
			"imgname":"icon_add@2x.png",
			"imgversion":"20170214",
			"imgbase64":"iVBORw0KGgoAAAANSUhEUgAAABwAAAAcCAYAAAByDd+UAAAAAXNSR0IArs4c6QAAAF5JREFUSA1jZCAR/AcCZC2MQIDMJ8RmIqSA2vKjFlI7RBlGg3Q0SEkOgeGfaBjRy0aSw4hEDcM/SOnuQ5LqMlB0ocf5aH2InojpHoejFqJHAcX80SClOAjRDRj+QQoAhRgMMrhPDrUAAAAASUVORK5CYII=",
			"name":"添加",
			"action":"added"
		},
		"btn3":{
			"imgname":"icon_more_detail@2x.png",
			"imgversion":"20170214",
			"imgbase64":"iVBORw0KGgoAAAANSUhEUgAAAAQAAAASCAYAAAB8fn/4AAAAAXNSR0IArs4c6QAAADBJREFUGBlj+P//vycQP4ZiTwYoA0iBwWMmBnQAFEfVgq6ACD6GGUABkBtgYPBaCwAdi4T3K1E4vwAAAABJRU5ErkJggg==",
			"name":"",
			"droplist":[
				{
					"name":"分享",
					"action":"share"
				},
				{
					"name":"复制",
					"action":"copy"
				},
				{
					"name":"删除",
					"action":"deleted"
				}
			]
		}
	}
	function menuConfig() {
		window.webkit.messageHandlers.AppModel.postMessage({body: jsonStr});
		return jsonStr;

	}


	function jsonConfig() {
		var str = JSON.stringify(jsonStr);
		window.stub.jsMethod(str);
		return jsonStr;

	}

	function fallback() {
		if(category != null && isWhat != null){
			if(category == 1){
				if(isWhat == 1){
					window.location.href = B.serverUrl + "/mobile/serv/favourListHbhz.jsp?accountNum=" + accountNum + "&category=" + category  +"&isWhat=" + isWhat;
				}else{
					window.location.href = B.serverUrl + "/mobile/serv/favourMyListHbhz.jsp?accountNum=" + accountNum + "&category=" + category  +"&isWhat=" + isWhat;
				}
			}else{
				if(isWhat == 1){
					window.location.href = B.serverUrl + "/mobile/serv/favourListXmhz.jsp?accountNum=" + accountNum + "&category=" + category  +"&isWhat=" + isWhat;
				}else{
					window.location.href = B.serverUrl + "/mobile/serv/favourMyListXmhz.jsp?accountNum=" + accountNum + "&category=" + category  +"&isWhat=" + isWhat;
				}
			}
		}else{
			return;
		}
	}

	function added() {
		if(category != null && isWhat != null){
			if(category == 3){
				window.location.href = B.serverUrl + "/mobile/serv/servHelpCreateQzzp.jsp?accountNum=" + accountNum + "&category=" +category+ "&isWhat=" + isWhat + "&id=" + id;
			}else if(category == 1){
				window.location.href = B.serverUrl + "/mobile/serv/servHelpCreateHbhz.jsp?accountNum=" + accountNum + "&category=" +category+ "&isWhat=" + isWhat + "&id=" + id ;
			}else{
				window.location.href = B.serverUrl + "/mobile/serv/servHelpCreateXmhz.jsp?accountNum=" + accountNum + "&category=" +category+ "&isWhat=" + isWhat + "&id=" + id;
			}
		}else{
			return;
		}


	}
	function share() {
		alert("测试而已");
	}
	function copy() {
		alert("测试而已");
	}
	function deleted() {
		alert("测试而已");
	}


	Zepto(function($){
  
  	$(document).on('tap','.show-bot-menu',function(){
  	
	  	var accountNumStr = $(this).attr("id");
	  	var idStr = $(this).attr("rel");
	  	
	  	$('.ui-actionsheet .ui-actionsheet-complaint').attr("rel",idStr);
	  	$('.ui-actionsheet .ui-actionsheet-del').attr("rel",idStr);
	  	
	  	if(accountNumStr == accountNum)//如果是自己
	  	{
	  		$('.ui-actionsheet .ui-actionsheet-complaint').hide();
	  		$('.ui-actionsheet .ui-actionsheet-del').show();
	  	}
	  	else
	  	{
	  		$('.ui-actionsheet .ui-actionsheet-complaint').show();
	  		$('.ui-actionsheet .ui-actionsheet-del').hide();
	  	}
	  
	    $('.ui-actionsheet').addClass('show');
	    
  	});
  	
  	//页面加载
    getPageData("mobServAction!doNotNeedSessionAndSecurity_getServCommentListWithServ.action?cyServ.id=<%= id %>",".comment-list","onload",commentTpl,null,"loadPhotoSwipe");
  
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

        getPageData("mobServAction!doNotNeedSessionAndSecurity_getServCommentListWithServ.action?cyServ.id=<%= id %>&cyServ.currentRow=0&cyServ.incremental=" + $(".post-comment .ui-list > li").length,".comment-list","update",commentTpl,me);
      },
      loadDownFn : function(me){

      	
      	if($(".post-comment .ui-list > li").length >= totalRows)
  		{
  			if (me != null) {
  				
				me.resetload();
			}
  			$(".inner").append('<div class="dropload-down" style="-webkit-transition: all 300ms; transition: all 300ms; height: 40px;"><div class="dropload-load"><div class="ui-loading-wrap">没有更多了</div></div></div>');
  			
  			
  		}
  		else
  		{
      		getPageData("mobServAction!doNotNeedSessionAndSecurity_getServCommentListWithServ.action?cyServ.id=<%= id %>&cyServ.currentRow=" + $(".post-comment .ui-list > li").length,".post-comment .ui-list","more",commentMoreTpl,me);
      	}

      }
    });

    
  });
  
  
function doComment()
{
	
	var commentContent = document.getElementById('commentContent');
	
	if(commentContent.value == '')
	{
		dia=$.dialog({
			 title:'温馨提示',
			 content:'请输入您想要说的话！',
			 button:["确认"]
			});
			
		dia.on("dialog:hide",function(e){
				console.log("dialog hide");
			});
	}
	else
	{
		$.ajax({
			url : "mobServAction!doNotNeedSessionAndSecurity_comment.action?" + 
				  "cyServComment.serviceId=" + id +
				  "&cyServComment.content=" + commentContent.value +
				  "&cyServComment.accountNum=" + accountNum,
			
			dataType : 'json',
	        type: "POST",
			
	        success: function(data) 
	        {
	        	
	        	if(data.status == true)
	        	{
	        		
				    commentContent.value = '';
				    getPageData("mobServAction!doNotNeedSessionAndSecurity_getServCommentListWithServ.action?cyServ.id=<%= id %>",".comment-list","onload",commentTpl,null,"loadPhotoSwipe");
	        		
	        	}
	        	else
	        	{
	        		dia=$.dialog({
				        title:'温馨提示',
				        content:'回复失败，请重试！',
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
  
  
</script>
</body>
</html>
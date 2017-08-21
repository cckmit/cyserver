<%@ page language="java" import="java.util.*,com.cy.core.messageboard.entity.*,com.cy.core.messageboard.service.*,com.cy.util.*" pageEncoding="UTF-8"%>
<%//response.setHeader("Pragma","No-cache");response.setHeader("Cache-Control","no-cache");response.setDateHeader("Expires", 0);%>
<%
String userId = request.getParameter("userId"); 
String messageType = request.getParameter("messageType"); 

String theme = "";
if("501".equals(messageType))
{
	theme = "会长";
}
else if("502".equals(messageType))
{
	theme = "学院";
}
else if("503".equals(messageType))
{
	theme = "总会";
}

%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title>联系<%= theme %></title>
<link rel="stylesheet" type="text/css" href="../css/pure-nr.css">
<link rel="stylesheet" type="text/css" href="../css/main_contact.css">
<link rel="stylesheet" type="text/css" href="../css/font-awesome.min.css">

<script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/iscroll.js"> </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/public.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/schoolServ.js"></script>

<script type="text/javascript">
var theme = '<%= theme %>';
var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;

/**
 * 下拉刷新 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullDownAction () {
	pullDownMessageList();
}

/**
 * 滚动翻页 （自定义实现此方法）
 * myScroll.refresh();		// 数据加载完成后，调用界面更新方法
 */
function pullUpAction () {
	pullUpMessageList();
}

/**
 * 初始化iScroll控件
 */
function loaded () {

  pullDownEl = document.getElementById('pullDown');
	pullDownOffset = pullDownEl.offsetHeight;
	pullUpEl = document.getElementById('pullUp');	
	pullUpOffset = pullUpEl.offsetHeight;
	
	myScroll = new iScroll('wrapper', {
		scrollbarClass: 'myScrollbar', /* 重要样式 */
		useTransition: false, /* 此属性不知用意，本人从true改为false */
		topOffset: pullDownOffset,
		onRefresh: function () {
			if (pullDownEl.className.match('loading')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
			} else if (pullUpEl.className.match('loading')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
			}
		},
		onScrollMove: function () {
			if (this.y > 5 && !pullDownEl.className.match('flip')) {
				pullDownEl.className = 'flip';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '松开加载...';
				this.minScrollY = 0;
			} else if (this.y < 5 && pullDownEl.className.match('flip')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
				this.minScrollY = -pullDownOffset;
			} else if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
				pullUpEl.className = 'flip';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '松手开始更新...';
				this.maxScrollY = this.maxScrollY;
			} else if (this.y > (this.maxScrollY + 5) && pullUpEl.className.match('flip')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
				this.maxScrollY = pullUpOffset;
			}
		},
		onScrollEnd: function () {
			if (pullDownEl.className.match('flip')) {
				pullDownEl.className = 'loading';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';				
				pullDownAction();	// Execute custom function (ajax call?)
			} else if (pullUpEl.className.match('flip')) {
				pullUpEl.className = 'loading';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';				
				pullUpAction();	// Execute custom function (ajax call?)
			}
		}
	});
}

//初始化绑定iScroll控件 
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', loaded, false); 

</script>

<script type="text/javascript">$(document).ready(function(){initMessageList();});</script>
</head>
<body>
<form method="post" >
<input type="hidden" name="messageBoard.messageType" id="messageType" value="<%= messageType %>" >
<input type="hidden" name="messageBoard.messageUserId" id="messageUserId" value="<%= userId %>" >

<input type="hidden" name="messageBoard.currentRow" id="currentRow" value="0" >
<input type="hidden" name="messageBoard.incremental" id="incremental" value="10" >




<div class="header">
  <h1>联系<%= theme %></h1>
</div>
<div id="wrapper">
  <div id="scroller">
  
  	<div id="pullDown" style="display:none;">
			<span class="pullDownIcon"></span><span class="pullDownLabel">松开加载...</span>
	</div>
	
    <ul class="list" id="thelist">
      
    </ul>
    
    <div id="pullUp">
      <span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>
    </div>
    
  </div>
  
  <div id="loading">
    	<div class="loading-icon"><i class="fa fa-spinner animate-spin"></i>数据加载中...</div>
  </div>
  <div id="nodata" class="nodata" style="display:none;">暂时还没有内容哟！</div>
</div>
<div id="posts" style="left:100%;display:none">
  <div class="reply-input">
    <input name="messageBoard.messageTitle" id="messageTitle" type="text" placeholder="请输入主题">
    <textarea name="messageBoard.messageContent" id="messageContent" placeholder="请输入内容"></textarea>
  </div>
</div>
<div class="footer" id="footer">
  <div class="plfooter">
    <a href="javascript:location.replace('schoolServList.jsp?userId=<%= userId %>')" class="btn-back"><i class="fa fa-angle-left"></i></a>
    <a href="javascript:;" class="btn-post go-to-post"><i class="fa fa-pencil-square-o"></i> 撰写</a>
  </div>
  <div class="psfooter" style="display:none">
    <a href="javascript:;" class="btn-back back-to-post"><i class="fa fa-angle-left"></i></a>
    <a href="javascript:sendMessage('<%= messageType %>','<%= userId %>');" class="btn-post"><i class="fa fa-paper-plane-o"></i> 发送</a>
  </div>
</div>
<script type="text/javascript">

$(document).ready(function(){
  $(".go-to-post").click(function(){
    $("#wrapper").animate({
      left:'-100%'
    });
    $("#posts").show().animate({
      left:'0'
    });
    $(".psfooter").show();
    $(".plfooter").hide();
  });
  $(".psfooter .back-to-post").click(function(){
    $("#wrapper").animate({
      left:'0'
    });
    $("#posts").animate({
      left:'100%'
    }).fadeOut(0);
    $(".psfooter").hide();
    $(".plfooter").show();
  });
});
</script>
</form>
</body>
</html>




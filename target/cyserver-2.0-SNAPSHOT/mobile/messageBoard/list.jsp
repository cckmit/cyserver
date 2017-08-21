<%@ page language="java" import="java.util.*,com.cy.core.messageboard.entity.*,com.cy.core.messageboard.service.*,com.cy.util.*" pageEncoding="UTF-8"%>
<%//response.setHeader("Pragma","No-cache");response.setHeader("Cache-Control","no-cache");response.setDateHeader("Expires", 0);%>
<% MessageBoard messageBoard = (MessageBoard)request.getAttribute("messageBoard"); %>
<%

String theme = "";

if(messageBoard != null)
{
	//System.out.println(messageBoard.getMessageType());
	if(messageBoard.getMessageType() == 1)
	{
		theme = "求职招聘";
	}
	else if(messageBoard.getMessageType() == 2)
	{
		theme = "项目合作";
	}
	else if(messageBoard.getMessageType() == 3)
	{
		theme = "互帮互助";
	}
}


%>
<!DOCTYPE html>
<html>
<head>
<title><%= theme %>信息列表</title>
<jsp:include page="../head.jsp"/>

<script type="text/javascript">

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
function loaded() {
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
	
	setTimeout(function () { document.getElementById('wrapper').style.left = '0'; }, 800);
}

//初始化绑定iScroll控件 
document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
document.addEventListener('DOMContentLoaded', loaded, false); 

</script>
</head>
<body>
<form method="post" >
<input type="hidden" name="messageBoard.messageType" id="messageType" value="<%= messageBoard.getMessageType() %>" >
<input type="hidden" name="messageBoard.currentRow" id="currentRow" value="0" >
<input type="hidden" name="messageBoard.incremental" id="incremental" value="<%= messageBoard.getIncremental() %>" >
<input type="hidden" name="messageBoard.messageUserId" id="messageUserId" value="<%= messageBoard.getMessageUserId() %>" >
<input type="hidden" name="messageBoard.hot" id="hot" value="0" >
<input type="hidden" name="messageBoard.collect" id="collect" value="0" >
<input type="hidden" name="messageBoard.reply" id="reply" value="0" >

<div id="post-list">
<div id="header">
    <h1><%= theme %>信息列表</h1>
    <a href="../schoolServ/schoolServList.jsp?userId=<%= messageBoard.getMessageUserId() %>" class="btn-back">返回</a>
    <a href="javascript:;" class="btn-post">发贴</a>
</div>
<div id="wrapper">
	
	<div id="scroller" style="display:none;">
		<div id="pullDown" style="display:none;">
			<span class="pullDownIcon"></span><span class="pullDownLabel">松开加载...</span>
		</div>
		
		<ul id="thelist" class="post-list">
			
		</ul>
		
		<div id="pullUp" style="display:none;">
			<span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>
		</div>
		
		
	</div>
	<div id="loading">
    	<div class="loading-icon"><i class="fa fa-spinner animate-spin"></i>数据加载中...</div>
    </div>
    <div id="nodata" class="nodata" style="display:none;">暂时还没有内容哟！</div>
</div>

<div id="footer">
  <ul class="footer-menu">
    <li class="all current" id="allClass"><a href="javascript:all(<%= messageBoard.getMessageType() %>);"><i class="fa fa-home"></i>全部</a></li>
    <li class="hot" style="display: none;" id="hotClass"><a href="javascript:hot(<%= messageBoard.getMessageType() %>);"><i class="fa fa-fire"></i>热门</a></li>
    <li class="mypost" id="mypostClass"><a href="javascript:my(<%= messageBoard.getMessageUserId() %>);"><i class="fa fa-user"></i>我的</a></li>
    <li class="collect" id="collectClass"><a href="javascript:collect(<%= messageBoard.getMessageUserId() %>);"><i class="fa fa-star"></i>收藏</a></li>
  </ul>
</div>
</div>
<div id="posts" style="display:none;">
  <div id="header">
      <h1>发表主题</h1>
      <a href="javascript:;" class="btn-back back-to-post">返回</a>
      <a href="javascript:sendMessage('<%= messageBoard.getMessageType() %>','<%= messageBoard.getMessageUserId() %>');" class="btn-post">发送</a>
  </div>
  <div class="reply-input">
    <input name="messageBoard.messageTitle" id="messageTitle" type="text" placeholder="请输入主题">
    <textarea placeholder="请输入内容" name="messageBoard.messageContent" id="messageContent"></textarea>
  </div>
</div>
<script>
$(document).ready(function(){
  $(".reply-input textarea").height($(window).height()/2-97);
  $(".btn-post").click(function(){
    $("#post-list").fadeOut();
    $("#posts").fadeIn();
  });
  $(".back-to-post").click(function(){
    $("#posts").fadeOut();
    $("#post-list").fadeIn();
  });
});
</script>

</form>
</body>
</html>
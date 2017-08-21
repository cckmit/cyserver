<%@ page language="java" import="java.util.*,com.cy.core.messageboard.entity.*,com.cy.core.messageboard.service.*,com.cy.util.*" pageEncoding="UTF-8"%>
<%//response.setHeader("Pragma","No-cache");response.setHeader("Cache-Control","no-cache");response.setDateHeader("Expires", 0);%>
<% MessageBoard messageBoard = (MessageBoard)request.getAttribute("messageBoard"); %>
<!DOCTYPE html>
<html>
<head>
<title>信息详情</title>
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
// function pullDownAction () {
// 	setTimeout(function () {	// <-- Simulate network congestion, remove setTimeout from production!
// 		var el, li, i;
// 		el = document.getElementById('thelist');

// 		for (i=0; i<3; i++) {
// 			li = document.createElement('li');
// 			li.innerText = 'Generated row ' + (++generatedCount);
// 			el.insertBefore(li, el.childNodes[0]);
// 		}
		
// 		myScroll.refresh();		//数据加载完成后，调用界面更新方法   Remember to refresh when contents are loaded (ie: on ajax completion)
// 	}, 1000);	// <-- Simulate network congestion, remove setTimeout from production!
// }

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
			/*if (pullDownEl.className.match('loading')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
			} else */if (pullUpEl.className.match('loading')) {
				pullUpEl.className = '';
				pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
			}
		},
		onScrollMove: function () {
			/*if (this.y > 5 && !pullDownEl.className.match('flip')) {
				pullDownEl.className = 'flip';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '松开加载...';
				this.minScrollY = 0;
			} else if (this.y < 5 && pullDownEl.className.match('flip')) {
				pullDownEl.className = '';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '下拉刷新...';
				this.minScrollY = -pullDownOffset;
			} else*/ if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
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
			/*if (pullDownEl.className.match('flip')) {
				pullDownEl.className = 'loading';
				pullDownEl.querySelector('.pullDownLabel').innerHTML = '加载中...';				
				pullDownAction();	// Execute custom function (ajax call?)
			} else */if (pullUpEl.className.match('flip')) {
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
<input type="hidden" name="messageBoard.messageId" id="messageId" value="<%= messageBoard.getMessageId() %>" >
<input type="hidden" name="messageBoard.messageFatherId" id="messageFatherId" value="<%= messageBoard.getMessageId() %>" >
<input type="hidden" name="messageBoard.messageType" id="messageType" value="<%= messageBoard.getMessageType() %>" >
<input type="hidden" name="messageBoard.currentRow" id="currentRow" value="0" >
<input type="hidden" name="messageBoard.incremental" id="incremental" value="<%= messageBoard.getIncremental() %>" >
<input type="hidden" name="messageBoard.messageUserId" id="messageUserId" value="<%= messageBoard.getMessageUserId() %>" >
<input type="hidden" name="messageBoard.hot" id="hot" value="0" >
<input type="hidden" name="messageBoard.collect" id="collect" value="0" >
<input type="hidden" name="messageBoard.messageContent" id="messageContent" value="" >
<input type="hidden" name="messageBoard.messageTitle" id="messageTitle" value="" >
<input type="hidden" name="messageBoard.reply" id="reply" value="1" >
<input type="hidden" name="messageBoard.refresh" id="refresh" value="0" >

<div id="post-content">
  <div id="header">
      <h1>信息详情</h1>
      <a href="messageBoardAction!initMessageList.action?messageBoard.messageUserId=<%= messageBoard.getMessageUserId() %>&messageBoard.messageType=<%= messageBoard.getMessageType() %>" class="btn-back">返回</a>
      <a href="javascript:;" class="btn-post goto-reply">回复</a>
  </div>
  <div id="wrapper">
    <div id="scroller">
      <div id="pullDown">
        <!-- <span class="pullDownIcon"></span><span class="pullDownLabel">松开加载...</span> -->
      </div>
      <ul id="thelist" class="post-show">

      </ul>
      <div id="pullUp">
        <span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>
      </div>  
    </div>
    
    <div id="loading">
    	<div class="loading-icon"><i class="fa fa-spinner animate-spin"></i>数据加载中...</div>
    </div>
  </div>
  <div id="footer">
    <div id="quickreply">
      <input class="quick-reply" id="quickReplyContent" type="text" placeholder="请输入内容">
      <input type="button"  class="btn-reply" value="回复" onclick="sendMessage('<%= messageBoard.getMessageType() %>','<%= messageBoard.getMessageUserId() %>')">
      <i class="fa fa-pencil-square-o reply-icon"></i>
    </div>
  </div>
</div>
<div id="advanced-reply" style="display:none;">
  <div id="header">
      <h1>高级回复</h1>
      <a href="javascript:backReplyList();" class="btn-back back-to-post">返回</a>
      <a href="javascript:sendMessage('<%= messageBoard.getMessageType() %>','<%= messageBoard.getMessageUserId() %>');" class="btn-post">发送</a>
  </div>
  <div class="reply-input">
    <textarea placeholder="请输入内容" id="replyContent"></textarea>
  </div>
</div>

<script>
$(document).ready(function(){
  $(".reply-input textarea").height($(window).height()/2-57);
  $(".goto-reply").click(function(){
    $("#post-content").fadeOut();
    $("#advanced-reply").fadeIn();
  });
  $(".back-to-post").click(function(){
    $("#advanced-reply").fadeOut();
    $("#post-content").fadeIn();
  });
});
</script>
</form>
</body>
</html>
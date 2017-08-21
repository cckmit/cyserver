<%@ page language="java" import="java.util.*,com.cy.core.news.entity.*,com.cy.core.majormng.service.*,com.cy.util.*" pageEncoding="UTF-8"%>

<%//response.setHeader("Pragma","No-cache");response.setHeader("Cache-Control","no-cache");response.setDateHeader("Expires", 0);%>
<% News news = (News)request.getAttribute("news");
List<News> topnewslist = (List<News>)request.getAttribute("topnewslist");
%>
<!doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
  <meta name="format-detection"content="telephone=no">
  <meta name="apple-mobile-web-app-capable" content="yes" />
  <meta name="apple-mobile-web-app-status-bar-style" content="black" />
  <title>新闻列表</title>
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/mobile/css/pure-nr.css">
  <link rel="stylesheet"  href="${pageContext.request.contextPath}/mobile/css/main_news.css">
  
  
  
<script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/bxslider.js"> </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/iscroll.js"> </script>

  
<script type="text/javascript">

window.onload=function()
{
	initMessageList();
};

var myScroll,
	pullDownEl, pullDownOffset,
	pullUpEl, pullUpOffset,
	generatedCount = 0;



function buildHTML(messageList, isRefresh)
{
	if(messageList != null && messageList.length > 0)
	{
		var el = document.getElementById('thelist');
		

		if(isRefresh)
		{
			el.innerHTML = '';
		}

		
		for(var i = 0; i < messageList.length; i++)
		{
			var messageBoard = messageList[i];
			
			var li = document.createElement('dl');
			
			
			
			var html = 
				
			      '<dt>'+messageBoard.title+'</dt>'+
			      '<dd class="news-pic"><img src="'+messageBoard.pic+'" width="100" height="80"></dd>'+
			      '<dd class="description">'+messageBoard.introduction+'</dd>'+
			      '<dd class="postdate">'+messageBoard.fDateTime+'</dd>'+
			      '<dd class="news-link"><a href="${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getMobNew.action?id='+messageBoard.newsId+'">查看详情</a></dd>';
			
			li.innerHTML = html;
			
			el.appendChild(li, el.childNodes[0]);
		}
		
	}
}


var totalRows = 0;

var oldCurrentRow = 0;


function initMessageList()
{
	//alert($('form').serialize());
	//document.getElementById('scroller').style.display = 'none';
	document.getElementById('loading').style.display = 'block';
	
	
	$.ajax({
		url : "${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_listMobNews.action",
		data : $('form').serialize(),
		dataType : 'json',
        //type: "POST",
		
        success: function(data) 
        {
        	
        	var messageList = data.list;
        	var currentRow = document.getElementById('currentRow');
        	
        	//document.getElementById('scroller').style.display = 'block';
        	document.getElementById('loading').style.display = 'none';
        	
    		if(messageList != null && messageList.length > 0)
    		{
    			totalRows = totalRows + messageList.length;
    			
    			currentRow.value = totalRows;
        		
    			var reply = document.getElementById('reply');
    			if(reply != null && reply.value == '1')
    			{
    				buildReplyHTML(messageList);
    			}
    			else
    			{
    				
    				buildHTML(messageList, false);
    			}
    			
    			
    			document.getElementById('pullUp').style.display = 'block';
    			
    		}
    		else
    		{
    			if(reply == null || reply.value != '1')
    			{
        			document.getElementById('nodata').style.display = 'block';
    			}
    			
    			
    			document.getElementById('pullUp').style.display = 'none';
    		}
        },
        
        complete: function()
        {
        	myScroll.refresh();
        }
    });

}


function pullDownMessageList()
{
	
	var currentRow = document.getElementById('currentRow');
	var incremental = document.getElementById('incremental');
	oldCurrentRow = currentRow.value;
	incremental.value = totalRows;
	currentRow.value = 0;
	//alert($('form').serialize());
	$.ajax({
		url : "${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_listMobNews.action",
		data : $('form').serialize(),
		dataType : 'json',
        //type: "POST",
		
        success: function(data) 
        {
        	var messageList = data.list;
        	buildHTML(messageList, true);
        },
        
        complete: function()
        {
        	currentRow.value = oldCurrentRow;
        	myScroll.refresh();
        }
    });

}
function pullUpMessageList()
{
	//alert($('form').serialize());
	
	var refresh = document.getElementById('refresh'); if(refresh != null){refresh.value == '1';}	
	$.ajax({
		url : "${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_listMobNews.action",
		data : $('form').serialize(),
		dataType : 'json',
        //type: "POST",
		
        success: function(data) 
        {
        	var messageList = data.list;
        	var currentRow = document.getElementById('currentRow');
        	
    		if(messageList != null && messageList.length > 0)
    		{
    			totalRows = totalRows + messageList.length;
    			
    			currentRow.value = totalRows;
        		
    			
    			var reply = document.getElementById('reply');
    			if(reply != null && reply.value == '1')
    			{
    				buildReplyHTML(messageList);
    			}
    			else
    			{
    				buildHTML(messageList, false);
    			}
    			
    		}
        },
        
        complete: function()
        {
        	myScroll.refresh();
        }
    });

}

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
	  pullUpEl = document.getElementById('pullUp'); 
	  pullUpOffset = pullUpEl.offsetHeight;
	  myScroll = new iScroll('wrapper', {
	    useTransition: false, 
	    mouseWheel: true,
	    onRefresh: function () {
	      if (pullUpEl.className.match('loading')) {
	        pullUpEl.className = '';
	        pullUpEl.querySelector('.pullUpLabel').innerHTML = '上拉加载更多...';
	      }
	    },
	    onScrollMove: function () {
	      if (this.y < (this.maxScrollY - 5) && !pullUpEl.className.match('flip')) {
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
	      if (pullUpEl.className.match('flip')) {
	        pullUpEl.className = 'loading';
	        pullUpEl.querySelector('.pullUpLabel').innerHTML = '加载中...';        
	        pullUpAction(); // Execute custom function (ajax call?)
	      }
	    }
	  });
	}

	//初始化绑定iScroll控件 
	document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
	document.addEventListener('DOMContentLoaded', loaded, false); 


</script>
  
  
</head>

<body>
<form method="post" >
<input type="hidden" name="news.category" id="category" value="${news.category}" >
<input type="hidden" name="news.currentRow" id="currentRow" value="0" >
<input type="hidden" name="news.incremental" id="incremental" value="${news.incremental }" >

  <div id="wrapper">
	
	<div id="scroller">

<div id="bd">

<%
if(topnewslist != null && topnewslist.size()>0){
	
%>
   <div class="bxslider-wrapper">
    <div class="bxslider">
    <% for(News tmpNew : topnewslist){%>
      <div>
        <a href="${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getMobNew.action?id=<%=tmpNew.getNewsId()%>"><img src="<%=tmpNew.getPic() %>" alt="img" width="100%" height="200" /></a>
        <blockquote class="slider-caption right-caption">
          <p class="slider-title"><%=tmpNew.getTitle() %></p>
        </blockquote>
      </div>
    <% }%>
      
    </div>
  </div>

<%
}
%>
  

	
	
		<div id="thelist" class="list">
			
		</div>
		
		<div id="pullUp">
			<span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>
		</div>
		
		
	</div>
	<div id="loading">
    	<div class="loading-icon"><i class="fa fa-spinner animate-spin"></i>数据加载中...</div>
    </div>
    <div id="nodata" class="nodata" style="display:none;">暂时还没有内容哟！</div>
 </div>
  
</div>
</form>

<script>
$('.bxslider').bxSlider({
    pager:false,
    controls:true,
    touchEnabed:true,
    infiniteLoop: true,
    preventDefaultSwipeX:true
  });
  $('.bx-next').click(function(){
    return false;
  });
  $('.bx-prev').click(function(){
    return false;
  });
</script>
</body>
</html>
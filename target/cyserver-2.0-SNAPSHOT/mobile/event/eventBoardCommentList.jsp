<%@ page language="java" pageEncoding="UTF-8"  import="java.util.*, com.cy.util.*" %>
<%

//String eventId = request.getParameter("eventId");
String boardId = request.getParameter("boardId");
String userInfoId = request.getParameter("userInfoId");
String eventId = request.getParameter("eventId");


String currentServerDate = WebUtil.formatDateByPattern(new Date(), WebUtil.YMDHMSS);

//System.out.println(currentServerDate);

%>

<!DOCTYPE HTML>
<html>
  <head>
    <meta charset="UTF-8">
    <title>活动花絮评论列表</title>
    <meta name="Description" content="窗友活动" />
    <meta name="Keywords" content="窗友,活动,讲座,聚会,班会,交友" />
    <meta name="author" content="Rainly" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" type="text/css" href="../css/global.css">
    <link rel="stylesheet" type="text/css" href="../css/events.css">
    <link rel="stylesheet" type="text/css" href="../css/font-awesome.min.css">
    
    <!-- Core CSS file -->
    <link rel="stylesheet" href="../css/photoswipe.css">
    <link rel="stylesheet" href="../css/default-skin/default-skin.css">
    
    <script src="../js/zepto.min.js" type="text/javascript"></script>
  	<script src="../js/zepto.selector.min.js" type="text/javascript"></script>
  	<script src="../js/global.js" type="text/javascript"></script>
  	<script src="../js/dropload.min.js" type="text/javascript"></script>
  	<script src="../js/template.js" type="text/javascript"></script>
  	<script src="../js/photoswipe.min.js"></script>
    <script src="../js/photoswipe-ui-default.min.js"></script>
    <script src="../js/photoswipe-fn-min.js"></script>
  </head>
  <body>

  <div class="wrapper no-header has-mini-footer">
    <div class="inner">
      <div class="event-board">
        <div class="board-detail">
        </div>
      </div>
      <div class="board-comment">
          <ul class="ui-list ui-list-active ui-border-tb">
          </ul>
          <div class="more"><a href="javascript:;">点击查看更多</a></div>
          <div class="ui-loading-wrap" style="display:none"><i class="ui-loading"></i><p>加载中</p></div>
      </div>
    </div>
  </div>
  <div class="footer mini-footer">
    <div id="reply">
      <div class="reply ui-border-radius">
        <input id="commentContent" type="text" placeholder="我也说一句...">
      </div>
      <button class="ui-btn ui-btn-primary" onclick="doEventBoardComment()">回复</button>
    </div>
  </div>
  
  
  
  
  <div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="pswp__bg"></div>
    <div class="pswp__scroll-wrap">
      <div class="pswp__container">
        <div class="pswp__item"></div>
        <div class="pswp__item"></div>
        <div class="pswp__item"></div>
      </div>
      <div class="pswp__ui pswp__ui--hidden">
        <div class="pswp__top-bar">
          <div class="pswp__counter"></div>
          <button class="pswp__button pswp__button--close" title="Close (Esc)"></button>
          <button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>
          <div class="pswp__preloader">
            <div class="pswp__preloader__icn">
              <div class="pswp__preloader__cut">
                <div class="pswp__preloader__donut"></div>
              </div>
            </div>
          </div>
        </div>
        <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
          <div class="pswp__share-tooltip"></div>
        </div>
        <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)"></button>
        <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)"></button>
        <div class="pswp__caption">
          <div class="pswp__caption__center"></div>
        </div>
      </div>
    </div>
  </div>
  
  
  <script type="text/javascript">
  
  var boardIdStr = <%= boardId %>;
  var userInfoIdStr = <%= userInfoId %>;
  
  var totalRows = 0;
  
  var dia = null;
  
// dropload
Zepto(function($){
  //页面加载
  getData("mobEventAction!doNotNeedSessionAndSecurity_getEventBoardCommentList.action?eventBoard.id=<%= boardId %>&eventBoard.userInfoId=<%= userInfoId %>",".board-detail","",eventBoardDetailTpl,null,"loadPhotoSwipe");
  getData("mobEventAction!doNotNeedSessionAndSecurity_getEventBoardCommentList.action?eventBoard.id=<%= boardId %>&eventBoard.userInfoId=<%= userInfoId %>",".ui-list","",eventBoardCommentListTpl);
  //点赞效果
  $(document).on('click','.action .like',function(){
    var likeNum = $(this).parent().next('.action-status').children('.like-num').text();
   
  	doEventBoardPraise(boardIdStr);
    if($(this).children('i').hasClass("fa-heart")){
      $(this).html('<i class="fa fa-heart-o"></i> 赞');
      likeNum --;
      $(this).parent().next('.action-status').children('.like-num').text(likeNum);
    }else if($(this).children('i').hasClass("fa-heart-o")){
      $(this).html('<i class="fa fa-heart"></i> 取消');
      likeNum ++;
      $(this).parent().next('.action-status').children('.like-num').text(likeNum);
    }
  });
  //底部弹出菜单
  var actionsheetTpl = '<div class="ui-actionsheet">'+
    '<div class="ui-actionsheet-cnt">'+
      '<h4>请选择操作</h4>'+
      '<button class="ui-actionsheet-complaint" rel="" >举报</button>'+
      '<button class="ui-actionsheet-del" rel="" >删除</button>'+
      '<button class="cancel">取消</button>'+
    '</div>'+
  '</div>';
  $("body").append(actionsheetTpl);
  $(document).on('click','.show-bot-menu',function(){
  
  	var userInfoId = $(this).attr("id");
  	var boardId = $(this).attr("rel");
  	
  	$('.ui-actionsheet .ui-actionsheet-complaint').attr("rel",boardId);
  	$('.ui-actionsheet .ui-actionsheet-del').attr("rel",boardId);
  	
  	if(userInfoId == userInfoIdStr)//如果是自己
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
  $(document).on('click','.ui-actionsheet .cancel',function(){
    $('.ui-actionsheet').removeClass('show');
  });

  //查看更多
  $(document).on('click','.more',function(){
  	
  		if($(".ui-list > li").length >= totalRows)
  		{
  			$(".more a").text("没有更多了");
  		}
  		else
  		{
  			getData("mobEventAction!doNotNeedSessionAndSecurity_getEventBoardCommentList.action?eventBoard.id=<%= boardId %>&eventBoard.userInfoId=<%= userInfoId %>&eventBoardComment.currentRow=" + $(".ui-list > li").length,".ui-list","download",eventBoardCommentListTpl);
  		}
  
      
  });
  
  $(document).on('click','.action .like',function(){
  	doEventBoardPraise($(this).attr("id"));
  });
  
  
  
  $(document).on('click','.ui-actionsheet .ui-actionsheet-del',function(){
  var boardId = $(this).attr("rel");
  $('.ui-actionsheet').removeClass('show');

      dia=$.dialog({
        title:'温馨提示',
        content:'您真的要删除该花絮吗？',
        button:["确认","取消"]
      });

      dia.on("dialog:action",function(e){
        console.log(e.index);
        if(e.index == 0){
          //这里写提交数据代码
          doEventBoardDel(boardId);
        }
      });
      dia.on("dialog:hide",function(e){
        console.log("dialog hide");
      });
    });
    
    
    
    $(document).on('click','.ui-actionsheet .ui-actionsheet-complaint',function(){
    var boardId = $(this).attr("rel");
    
    $('.ui-actionsheet').removeClass('show');
      dia=$.dialog({
        title:'温馨提示',
        content:'您真的要举报该花絮吗？',
        button:["确认","取消"]
      });

      dia.on("dialog:action",function(e){
        console.log(e.index);
        if(e.index == 0){
          //这里写提交数据代码
          
          doEventBoardComplaint(boardId);
        }
      });
      dia.on("dialog:hide",function(e){
        console.log("dialog hide");
      });
    });
  
  
});

	function doEventBoardPraise(boardId)
	{
		$.ajax({
				url : "mobEventAction!doNotNeedSessionAndSecurity_eventBoardPraise.action?" + 
						"eventBoardPraise.boardId=" + boardIdStr + 
						"&eventBoardPraise.userInfoId=" + userInfoIdStr,

				dataType : 'json',
		        type: "POST"
		    });

	}

	function doEventBoardComment()
	{
		
		
		
		var commentContent = document.getElementById('commentContent');
		
		if(commentContent.value == '')
		{
			//alert("回复内容不能为空！");
			
			dia=$.dialog({
				title:'温馨提示',
				content:'回复内容不能为空！',
				button:["确认"]
			});
			
			
			return;
		}
		else
		{
			$.ajax({
				url : "mobEventAction!doNotNeedSessionAndSecurity_eventBoardComment.action?" + 
						"eventBoardComment.boardId=" + boardIdStr + 
						"&eventBoardComment.userInfoId=" + userInfoIdStr +
						"&eventBoardComment.comment=" + commentContent.value,
				
				dataType : 'json',
		        type: "POST",
				
		        success: function(data) 
		        {
		        	
		        	if(data.status == true)
		        	{
		        		commentContent.value = "";
		        		
		        		var incremental = $(".ui-list > li").length;
		        		
		        		if(incremental < 10)
		        		{
		        			incremental = 10;
		        		}
		        		
		        		getData("mobEventAction!doNotNeedSessionAndSecurity_getEventBoardCommentList.action?eventBoard.id=<%= boardId %>&eventBoard.userInfoId=<%= userInfoId %>&eventBoardComment.currentRow=0&eventBoardComment.incremental=" + incremental,".ui-list","update",eventBoardCommentListTpl);
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
	
	
	
	
	function doEventBoardComplaint(boardId)
	{
		$.ajax({
				url : "mobEventAction!doNotNeedSessionAndSecurity_eventBoardComplaint.action?" + 
						"eventBoardComplaint.boardId=" + boardId +
						"&eventBoardComplaint.userInfoId=" + userInfoIdStr + 
						"&eventBoardComplaint.reason=手机用户举报",
				
				dataType : 'json',
		        type: "POST",
				
		        success: function(data) 
		        {
		        	
		        	if(data.status == true)
		        	{
		        		dia=$.dialog({
					        title:'温馨提示',
					        content:'举报成功！',
					        button:["确认"]
					      });
		        		
		        	}
		        	else
		        	{
		        		dia=$.dialog({
					        title:'温馨提示',
					        content:'举报失败或您已经举报过！',
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
	
	
	function doEventBoardDel(boardId)
	{
		$.ajax({
				url : "mobEventAction!doNotNeedSessionAndSecurity_deleteEventBoard.action?" + 
						"eventBoard.id=" + boardId +
						"&eventBoard.userInfoId=" + userInfoIdStr,
				
				dataType : 'json',
		        type: "POST",
				
		        success: function(data) 
		        {
		        	
		        	if(data.status == true)
		        	{
		        		dia=$.dialog({
					        title:'温馨提示',
					        content:'删除成功！',
					        button:["确认"]
					      });
					      
					    dia.on("dialog:action",function(e){
					        console.log(e.index);
					        if(e.index == 0){
					          location.replace("eventBoardList.jsp?eventId=<%= eventId %>&userInfoId=<%= userInfoId %>");
					        }
					      });

		        	}
		        	else
		        	{
		        		dia=$.dialog({
					        title:'温馨提示',
					        content:'删除失败，请重试！',
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
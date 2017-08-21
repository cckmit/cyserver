/**
 * 留言板JS
 */

window.onload=function()
{
	initMessageList();
};


function changeCurrentClass(currentClass)
{
	var allClass = document.getElementById('allClass');
	var hotClass = document.getElementById('hotClass');
	var mypostClass = document.getElementById('mypostClass');
	var collectClass = document.getElementById('collectClass');
	
	if(currentClass == 'all')
	{
		allClass.className = 'all current';
		hotClass.className = 'hot';
		mypostClass.className = 'mypost';
		collectClass.className = 'collect';
	}
	else if(currentClass == 'hot')
	{
		allClass.className = 'all';
		hotClass.className = 'hot current';
		mypostClass.className = 'mypost';
		collectClass.className = 'collect';
	}
	else if(currentClass == 'my')
	{
		allClass.className = 'all';
		hotClass.className = 'hot';
		mypostClass.className = 'mypost current';
		collectClass.className = 'collect';
	}
	else if(currentClass == 'collect')
	{
		allClass.className = 'all';
		hotClass.className = 'hot';
		mypostClass.className = 'mypost';
		collectClass.className = 'collect current';
	}
}

var oldMessageUserId = 0;
function all(messageType)
{
	changeCurrentClass('all');
	oldMessageUserId = document.getElementById('messageUserId').value;
	document.getElementById('messageUserId').value = 0;
	document.getElementById('messageType').value = messageType;
	document.getElementById('currentRow').value = 0;
	document.getElementById('incremental').value = 10;
	document.getElementById('hot').value = 0;
	document.getElementById('collect').value = 0;
	totalRows = 0;
	oldCurrentRow = 0;
	

	document.getElementById('thelist').innerHTML = '';
	
	initMessageList();
	document.getElementById('messageUserId').value = oldMessageUserId;

}

function hot(messageType)
{
	changeCurrentClass('hot');
	
	document.getElementById('messageUserId').value = 0;
	document.getElementById('messageType').value = messageType;
	document.getElementById('currentRow').value = 0;
	document.getElementById('incremental').value = 10;
	document.getElementById('hot').value = 1;
	document.getElementById('collect').value = 0;
	totalRows = 0;
	oldCurrentRow = 0;
	
	//alert(document.getElementById('messageUserId').value);
	document.getElementById('thelist').innerHTML = '';
	initMessageList();
}

function my(messageUserId)
{
	changeCurrentClass('my');
	
	document.getElementById('messageUserId').value = messageUserId;
	document.getElementById('messageType').value = 0;
	document.getElementById('currentRow').value = 0;
	document.getElementById('incremental').value = 10;
	document.getElementById('hot').value = 0;
	document.getElementById('collect').value = 0;
	totalRows = 0;
	oldCurrentRow = 0;
	document.getElementById('thelist').innerHTML = '';
	initMessageList();
}

function collect(messageUserId)
{
	changeCurrentClass('collect');
	
	document.getElementById('messageUserId').value = messageUserId;
	document.getElementById('messageType').value = 0;
	document.getElementById('currentRow').value = 0;
	document.getElementById('incremental').value = 10;
	document.getElementById('hot').value = 0;
	document.getElementById('collect').value = 1;
	totalRows = 0;
	oldCurrentRow = 0;
	
	//alert(document.getElementById('messageUserId').value);
	document.getElementById('thelist').innerHTML = '';
	initMessageList();
}

function backReplyList()
{
	var replyContent = document.getElementById('replyContent');
	
	if(replyContent != null)
	{
		replyContent.value = '';
	}
}

var isSendReplyMessage = false;
function sendMessage(messageType,messageUserId)
{
	var title = document.getElementById('messageTitle');
	var content = document.getElementById('messageContent');
	
	var quickReplyContent = document.getElementById('quickReplyContent');
	var replyContent = document.getElementById('replyContent');
	
	var reply = document.getElementById('reply');

	if(reply.value == '1')
	{
		if(replyContent.value != '')
		{
			content.value = replyContent.value;
		}
		else
		{
			content.value = quickReplyContent.value;
		}
	}
	
	if(reply.value != '1' && title.value == '')
	{
		alert('请输入主题');
	}
	else if(content.value == '')
	{
		alert('请输入内容');
	}
	else	
	{
		
		$.ajax({
			url : "messageBoardAction!messageSend.action",
			data : $('form').serialize(),
			dataType : 'json',
	        //type: "POST",
			
	        success: function(data) 
	        {
	        	if(data.status == 0)
	        	{
	        		alert('发布成功，请等待审核');
	        		//alert('发布成功');
	        		
	        		if(reply.value == '1')
	        		{
	        			if(replyContent.value != '')
	        			{
	        				$("#advanced-reply").fadeOut();
	        			    $("#post-content").fadeIn();
	        			}
	        			else
	        			{
	        				quickReplyContent.value = '';
	        			}
	        			
	        			isSendReplyMessage = true;
	        			
	        			pullUpMessageList();
	        		}
	        		else
	        		{
	        			//pullDownMessageList();
	        			location.replace("messageBoardAction!initMessageList.action?messageBoard.messageUserId="+messageUserId+"&messageBoard.messageType="+messageType);
	        		}
	        		
	        	}
	        	else if(data.status == 1)
	        	{
	        		alert('发布失败');
	        	}
	        	
	        }
	    });
	}

}


function sendFeedBack(messageType,messageUserId)
{
	var title = document.getElementById('messageTitle');
	var content = document.getElementById('messageContent');
	
	
	
	if(title.value == '')
	{
		alert('请输入主题');
	}
	else if(content.value == '')
	{
		alert('请输入内容');
	}
	else	
	{
		
		$.ajax({
			url : "messageBoardAction!messageSend.action",
			data : $('form').serialize(),
			dataType : 'json',
	        //type: "POST",
			
	        success: function(data) 
	        {
	        	if(data.status == 0)
	        	{
	        		title.value = '';
	        		content.value = '';
	        		alert('发送成功');
	        	}
	        	else if(data.status == 1)
	        	{
	        		alert('发送失败');
	        	}
	        	
	        }
	    });
	}

}

function clickPraise(messageId)
{
	$.ajax({
		url : "messageBoardAction!praise.action",
		data : $('form').serialize(),
		dataType : 'json',
        //type: "POST",
		
        success: function(data) 
        {
        	var praiseDiv = document.getElementById('praise' + messageId);
        	//alert(praiseDiv.innerText);
        	if(data.status == 0)
        	{
        		//alert('取消赞');
        		praiseDiv.innerText = parseInt(praiseDiv.innerText) - 1;
        	}
        	else if(data.status == 1)
        	{
        		//alert('已赞');
        		praiseDiv.innerText = parseInt(praiseDiv.innerText) + 1;
        	}
        	else if(data.status == -1)
        	{
        		alert('操作失败');
        	}
        	
        }
    });
}


function clickCollect()
{
	$.ajax({
		url : "messageBoardAction!collect.action",
		data : $('form').serialize(),
		dataType : 'json',
        //type: "POST",
		
        success: function(data) 
        {
        	if(data.status == 0)
        	{
        		alert('取消收藏');
        	}
        	else if(data.status == 1)
        	{
        		alert('已收藏');
        	}
        	else if(data.status == -1)
        	{
        		alert('操作失败');
        	}
        	
        }
    });

}


function buildHTML(messageList, isRefresh)
{
	if(messageList != null && messageList.length > 0)
	{
		var el = document.getElementById('thelist');
		
		var messageUserId = document.getElementById('messageUserId');

		if(isRefresh)
		{
			el.innerHTML = '';
		}

		
		for(var i = 0; i < messageList.length; i++)
		{
			var messageBoard = messageList[i];
			
			var li = document.createElement('li');
			
			li.setAttribute("onclick","location.href='messageBoardAction!initMessageDetail.action?messageBoard.messageId="+messageBoard.messageId+"&messageBoard.messageUserId="+messageUserId.value+"&messageBoard.messageType="+messageBoard.messageType + "';");
			
			var html = 
				'<h3>'+ messageBoard.messageTitle + '</h3>' + 
				'<div class="post-attr">' + 
				'	<span class="post-time">发布于：' + messageBoard.pastTime + '</span>' + 
				'	<span><i class="fa fa-comments-o"></i> ' + messageBoard.messageReplyCount + ' | ' + messageBoard.messageBrowseQuantity + '</span>' + 
				'</div>' + 
				'<div class="user-info">' + 
				'	<div class="avatar"><img src="' + getIcons(messageBoard.messageUserImageURL) + '"></div>' + 
				'	<h4>' + messageBoard.messageUserName + '</h4>' + 
				'</div>';
			
			li.innerHTML = html;
			
			el.appendChild(li, el.childNodes[0]);
		}
		
	}
}



var isFrist = true;
function buildReplyHTML(messageList)
{
	
	if(messageList != null && messageList.length > 0)
	{
		var el = document.getElementById('thelist');

		//alert(messageList.length);
		for(var i = 0; i < messageList.length; i++)
		{
			var messageBoard = messageList[i];
			
			var li = document.createElement('li');
			
			var html = '';
			
			if(i == 0 && isSendReplyMessage == false && isFrist)
			{
				isFrist = false;
				html = 
					'<header><h2>'+ messageBoard.messageTitle + '</h2></header>' +
					'<div class="post-thread">' +
					'	<div class="user-info clearfix">' +
					'		<div class="avatar"><img src="' + getIcons(messageBoard.messageUserImageURL) + '"></div>' +
					'		<h4>' + messageBoard.messageUserName + '</h4>' +
					'		<div class="post-time">发表于：' + messageBoard.pastTime + '</div>' +
					'		<div class="who"><span class="glyphicon glyphicon-user"></span>楼主</div>' +
					'	</div>' +
					'	<div class="content">' + messageBoard.messageContent + '</div>' +
					'</div>' +
					'<div class="post-attr">' +
					'	<ul class="clearfix">' +
					'		<li><i></i>&nbsp;</li>' +
					'		<li onclick="clickPraise(' + messageBoard.messageId + ')"><i class="fa fa-thumbs-o-up"></i><span id="praise' + messageBoard.messageId + '">' + messageBoard.messagePraiseCount + '</span></li>' +
					'		<li onclick="clickCollect()"><i class="fa fa-star-o"></i> 收藏</li>' +
					'	</ul>' +
					'</div>';
			}
			else
			{
				
				html = 
					'<div class="post-thread">' +
					'	<div class="user-info clearfix">' +
					'		<div class="avatar"><img src="' + getIcons(messageBoard.messageUserImageURL) + '"></div>' +
					'		<h4>' + messageBoard.messageUserName + '</h4>' +
					'		<div class="post-time">发表于：' + messageBoard.pastTime + '</div>' +
					'	</div>' +
					'	<div class="content">' + messageBoard.messageContent + '</div>' +
					'</div>' +
					'<div class="post-attr">' +
					'	<ul class="clearfix">' +
					'		<li><i></i>&nbsp;</li>' +
					'	</ul>' +
					'</div>';
			}

			
			
			li.innerHTML = html;

			el.appendChild(li, el.childNodes[0]);
		}
		
		isSendReplyMessage = false;
	}
}



var totalRows = 0;

var oldCurrentRow = 0;


function initMessageList()
{
	//alert($('form').serialize());
	document.getElementById('scroller').style.display = 'none';
	document.getElementById('loading').style.display = 'block';
	
	
	$.ajax({
		url : "messageBoardAction!pullMessageList.action",
		data : $('form').serialize(),
		dataType : 'json',
        //type: "POST",
		
        success: function(data) 
        {
        	
        	var messageList = data.list;
        	var currentRow = document.getElementById('currentRow');
        	
        	document.getElementById('scroller').style.display = 'block';
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
		url : "messageBoardAction!pullMessageList.action",
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
		url : "messageBoardAction!pullMessageList.action",
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











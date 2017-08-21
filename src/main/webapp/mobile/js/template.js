//首页模板
    var indexTpl='<% for(var i=0;i<lists.length;i++){ %>'+
                '<li class="ui-border-t">'+
                  '<div class="ui-list-img">'+
                    '<span style="background-image:url(<%=lists[i].pic%>)" <% if(lists[i].type==0){%>class="ui-tag-new"<% } %>></span>'+
                  '</div>'+
                  '<div class="ui-list-info">'+
                    '<h4><%=lists[i].title%></h4>'+
                    '<p><span class="event-addr"><i class="fa fa-map-marker"></i>  <%=lists[i].place%></span></p>'+
                    '<p><span class="event-actor"><i class="fa fa-user"></i> <%=lists[i].joinedPeople%><%  if(lists[i].maxPeople != 0){%> / <%=lists[i].maxPeople%><%}%></span><span class="event-time"><i class="fa fa-clock-o"></i> <%=lists[i].startTimeStr%></span></p>'+
                  '</div>'+
                  '<div class="list-link"><a href="<%=lists[i].link%>"></a></div>'+
                '</li>'+
            '<% } %>';
    //参与者列表模板
    var actorListTpl='<% totalRows = (lists == null?0:lists.length); for(var i=0;i<lists.length;i++){ %>'+
                '<li>'+
                    '<div class="ui-avatar-s">'+
                      '<span style="background-image:url(<%=lists[i].userAvatar%>)"></span>'+
                    '</div>'+
                    '<div class="ui-list-info ui-border-t">'+
                      '<h4><%=lists[i].userName%><% if (lists[i].userSex == "1") {%>&nbsp;&nbsp;<span class="icon-female"><i class="fa fa-venus"></i></span><% } else if (lists[i].userSex == "0"){ %>&nbsp;&nbsp;<span class="icon-male"><i class="fa fa-mars"></i></span><% } %></h4>'+
                      '<% if(isMyCreateEvent(cyEvent)){%>' +
                      //'<p class="ui-nowrap">联系电话:<a href="tel:<%=lists[i].userTel%>">&nbsp;&nbsp;<%=lists[i].userTel%></a></p>'+
                      '<p class="ui-nowrap">联系电话:&nbsp;&nbsp;<%=lists[i].userTel%></p>'+
                      '<% } %>' +
                    '</div>'+
                    '<% if(lists[i].isSignIn){%><div class="ui-badge">已签到</div><% } %>'+
                '</li>'+
                '<% } %>';
    //活动详情模板
    var eventDetailTpl =
				    	'<% if(typeof(eventSign) != "undefined" && eventSign.viewNotification == 0){%>'+
				        '<div class="ui-tooltips ui-tooltips-warn">'+
				        	'<div class="ui-tooltips-cnt ui-border-b">'+
				                '<i></i><%=cyEvent.notification%>'+
				            '</div><a class="ui-icon-close tmp-unselect fa fa-times-circle"></a>'+
				        '</div>'+
				  '<div class="wrapper">'+
				  '<% } else  {%>'+
                  '<div class="wrapper no-header">'+
                  '<% } %>'+
    				  '<div class="inner">'+
                      '<div class="show-event">'+
                        '<div class="event-pic <% if(cyEvent.type == 0){%>ui-tag-new<% } %>" style="background-image:url(<%=cyEvent.pic != "" && cyEvent.pic != null? cyEvent.pic: "../img/nopic.png"%>)">'+
                          '<h1><%=cyEvent.title%></h1>'+
                        '</div>'+
                        '<div class="event-info ui-box">'+
                          '<div class="event-creater">'+
                          '<% if(cyEvent.type == 0){ %>' +
                            '<div class="creater-avatar" style="background-image:url(../img/gflogo.jpg);"><a href=""></a></div>'+
                            '<div class="creater-name"><a href=""><%=cyEvent.organizer%></a></div>'+
                          '<% }else { %>'+
                          	'<div class="creater-avatar" style="background-image:url(<%=cyEvent.userAvatar%>);"><a href=""></a></div>'+
                          	'<div class="creater-name"><a href=""><%=cyEvent.userName%></a></div>'+
                          '<% } %>'+
                          '</div>'+
                          '<div class="event-basic-info">'+
                            '<p><span class="event-addr"><i class="fa fa-map-marker"></i> 地点: <%=cyEvent.place%></span></p>'+
                            '<p><span class="event-time"><i class="fa fa-clock-o"></i> 报名: <%=cyEvent.signupStartShortTime%> - <%=cyEvent.signupEndShortTime%></span></p>'+
                            '<p><span class="event-time"><i class="fa fa-clock-o"></i> 活动: <%=cyEvent.startShortTime%> - <%=cyEvent.endShortTime%></span></p>'+
                            '<p><span class="event-actor"><i class="fa fa-user"></i> <%=countEventSign%>人已报名<%if(cyEvent.maxPeople > 0){%>，共<%=cyEvent.maxPeople%>个名额<%}%></span></p>'+
                          '</div>'+
                        '</div>'+
                        '<% if(isMyCreateEvent(cyEvent) && cyEvent.needSignIn == 1) {%>' +
                        '<div class="ui-box">'+
                        '<div class="ui-link-title">签到码：<span class="sign-in-code"><%=cyEvent.signInCode%></span></div>'+
                        '</div>'+
                        '<% } %>' +
                        '<div class="event-detail ui-box">'+
                          '<div class="show-detail"><a href="javascript:;">查看详情 <i class="fa fa-chevron-right"></i></a></div>'+
                          '<article>'+
                            '<p><%=cyEvent.content%></p>'+
                          '</article>'+
                        '</div>'+
                        '<% if(typeof(eventSignList) != "undefined" && eventSignList.length > 0){%>' +
                        '<div class="actor-list ui-box">'+
                          '<div class="ui-arrowlink ui-link-title"><a href="participateList.jsp?eventId=<%=cyEvent.id%>&userInfoId=<%=userInfoIdStr%>">报名成员</a></div>'+
                          '<ul>'+
                          '<% for(var i=0;i<eventSignList.length;i++){ %>'+
                            '<li>'+
                              '<div class="actor-avatar" style="background-image:url(<%=eventSignList[i].userAvatar%>);"></div>'+
                              '<div class="actor-name"><%=eventSignList[i].userName%></div>'+
                            '</li>'+
                          '<% } %>'+
                          '</ul>'+
                        '</div>'+
                        '<% } %>' +

                        '<% if(compareTo(cyEvent.startTime, currentServerDate) >= 0){%>' +
                        '<div class="event-story ui-box">'+
                        '<div class="ui-arrowlink ui-link-title"><a href="eventBoardList.jsp?eventId=<%=cyEvent.id%>&userInfoId=<%= userInfoIdStr %>">活动花絮</a></div>'+
                        '<ul>'+
                        '<% if(typeof(eventBoardPicList) != "undefined" && eventBoardPicList.length > 0){%>' +
                        '<% for(var i=0;i<eventBoardPicList.length;i++){ %>'+

                          '<li>'+
                            '<div class="story-item" style="background-image:url(<%=eventBoardPicList[i].xemanhdep%>);"></div>'+
                          '</li>'+
                        '<% } %>'+
                        '<% } %>' +
                        '</ul>'+
                        '</div>'+
                        '<% } %>' +

                      '</div>'+




                    '</div></div>'+
                    '<div class="footer">'+
            		'<ul class="footer-btn">'+

                    getEventDetailStatus() +
                    
                    '<% if(isMyCreateEvent(cyEvent) && (cyEvent.auditStatus == 0 || (compareTo(cyEvent.signupStartTime, currentServerDate) < 0))) {%>' +
                    '<li><button class="ui-btn-lg ui-btn-orange deleteEvent" id="deleteEvent"><i class="fa fa-trash-o"></i> 删除</button></li>' +
                    '<% } %>' +
                    
                    '</ul>'+
                	'</div>';




    function getEventDetailStatus()
    {
    	var eventDetailStatusButton =
    		'<% if(cyEvent.auditStatus == 0){%>' +
    			'<li><button class="ui-btn-lg disabled"><i class="fa fa-clock-o"></i>未审核</button></li>'+
        	'<% } else if(cyEvent.auditStatus == 2){ %>' +
        		'<li><button class="ui-btn-lg disabled"><i class="fa fa-clock-o"></i>审核未通过</button></li>'+
        	'<% } else if(cyEvent.status == 1){ %>' +
        		'<li><button class="ui-btn-lg disabled"><i class="fa fa-clock-o"></i>已取消</button></li>'+
        	'<% } else if((compareTo(cyEvent.signupStartTime, currentServerDate) < 0)){%>' +
    			'<li><button class="ui-btn-lg disabled"><i class="fa fa-clock-o"></i>未开始</button></li>'+
    		'<% } else if((typeof(eventSign) != "undefined" && compareTo(cyEvent.startTime, currentServerDate) < 0)){%>' +
    			'<li><button class="ui-btn-lg disabled"><i class="fa fa-check-square-o"></i>已报名</button></li>'+
    		'<% } else if(compareTo(cyEvent.endTime, currentServerDate) > 0){%>' +
    			'<li><button class="ui-btn-lg disabled"><i class="fa fa-clock-o"></i>已结束</button></li>'+
    		'<% } else if(!isMyCreateEvent(cyEvent) && typeof(eventSign) == "undefined" && (compareTo(cyEvent.signupStartTime, currentServerDate) >= 0 && compareTo(cyEvent.signupEndTime, currentServerDate) <= 0)){%>' +
    			'<li><button class="ui-btn-lg ui-btn-primary signup" id="btn"><i class="fa fa-user-plus"></i>报名</button></li>'+

        	'<% } else if(!isMyCreateEvent(cyEvent) && cyEvent.needSignIn == 1 && typeof(eventSign) != "undefined" && eventSign.isSignIn == 0 && (compareTo(cyEvent.startTime, currentServerDate) >= 0 && compareTo(cyEvent.endTime, currentServerDate) <= 0)){ %>' +
        		'<li><button class="ui-btn-lg ui-btn-green signIn" id="signIn"><i class="fa fa-check-square-o"></i>签到</button></li>'+
        	'<% } else if(!isMyCreateEvent(cyEvent) && cyEvent.needSignIn == 1 && typeof(eventSign) != "undefined" && eventSign.isSignIn == 1 && (compareTo(cyEvent.startTime, currentServerDate) >= 0 && compareTo(cyEvent.endTime, currentServerDate) <= 0)){%>' +
				'<li><button class="ui-btn-lg disabled"><i class="fa fa-check-square-o"></i>已签到</button></li>'+
			'<% } else if(compareTo(cyEvent.startTime, currentServerDate) >= 0 && compareTo(cyEvent.endTime, currentServerDate) <= 0){%>' +
    			'<li><button class="ui-btn-lg ui-btn-green"><i class="fa fa-clock-o"></i>进行中...</button></li>'+

    		'<% } else if(compareTo(cyEvent.signupEndTime, currentServerDate) >= 0){%>' +
    			'<li><button class="ui-btn-lg disabled"><i class="fa fa-clock-o"></i>报名已截止</button></li>'+
    		'<% } %>' +
    		"";
    	return eventDetailStatusButton;
    }

    function isMyCreateEvent(cyEvent)
    {
    	if((cyEvent.userInfoId != userInfoIdStr || cyEvent.type == 0 ))
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }



    //我的列表
    var myEventList='<% for(var i=0;i<lists.length;i++){ %>'+
                '<li class="ui-border-t">'+
                  '<div class="ui-list-img<%if(lists[i].viewNotification==0 && lists[i].needNotification==1){%> ui-reddot<%}%>">'+
                    '<span style="background-image:url(<%=lists[i].pic%>)" <% if(lists[i].type == 0){%>class="ui-tag-new"<% } %>></span>'+
                  '</div>'+
                  '<div class="ui-list-info">'+
                    '<h4><%=lists[i].title%></h4>'+
                    '<p><span class="event-addr"><i class="fa fa-map-marker"></i>  <%=lists[i].place%></span></p>'+
                    '<p><span class="event-status">'+
                    '<% if (lists[i].eventStatus == 0) { %>'+
                      '<span class="ui-status-gray">未审核</span>'+
                    '<% } %>'+
                    '<% if (lists[i].eventStatus == 1) { %>'+
                      '<span class="ui-status-green">已审核</span>'+
                    '<% } %>'+
                    '<% if (lists[i].eventStatus == 2) { %>'+
                      '<span class="ui-status-red">审核未通过</span>'+
                    '<% } %>'+
                    '<% if (lists[i].eventStatus == 3) { %>'+
                      '<span class="ui-status-gray">活动已取消</span>'+
                    '<% } %>'+
                    '<% if (lists[i].eventStatus == 4) { %>'+
                      '<span class="ui-status-red">活动未开始</span>'+
                    '<% } %>'+
                    '<% if (lists[i].eventStatus == 5) { %>'+
                      '<span class="ui-status-green">活动进行中</span>'+
                    '<% } %>'+
                    '<% if (lists[i].eventStatus == 6) { %>'+
                      '<span class="ui-status-gray">活动已结束</span>'+
                    '<% } %>'+
                    '<% if (lists[i].eventStatus == 7) { %>'+
                      '<span class="ui-status-green">报名进行中</span>'+
                    '<% } %>'+
                    '<% if (lists[i].eventStatus == 8) { %>'+
                      '<span class="ui-status-red">报名已截止</span>'+
                    '<% } %>'+
                    '<% if (lists[i].eventStatus == 9) { %>'+
                      '<span class="ui-status-red">报名数不足</span>'+
                    '<% } %>'+
                    '</span><span class="event-time"><i class="fa fa-clock-o"></i> <%=lists[i].startTimeStr%></span></p>'+
                  '</div>'+
                  '<div class="list-link"><a href="<%=lists[i].link%>"></a></div>'+
                '</li>'+
            '<% } %>';

















  //花絮列表
    var eventBoardListTpl=''+

    	'<% totalRows = countEventBoard; %>' +
    	'<% for(var i=0;i<eventBoardList.length;i++){ %>'+

          '<li>'+
            '<div class="ui-avatar">'+
              '<span style="background-image:url(<%=eventBoardList[i].userAvatar%>)"></span>'+
            '</div>'+
            '<div class="ui-list-info">'+
              '<h4 class="ui-nowrap"><%=eventBoardList[i].userName%></h4>'+
              '<button class="post-time show-bot-menu" id="<%= eventBoardList[i].userInfoId %>" rel="<%= eventBoardList[i].id %>"><%=eventBoardList[i].createTimeStr%> <i class="fa fa-angle-down"></i></button>'+
              '<p class="ui-nowrap-multi"><%=eventBoardList[i].comment%></p>'+
            '</div>'+
            '<a href="eventBoardCommentList.jsp?eventId=<%= eventBoardList[i].eventId %>&boardId=<%= eventBoardList[i].id %>&userInfoId=<%= userInfoIdStr %>" class="view-event-detail">查看详情</a>'+
            '<ul class="ui-list-pic clearfix">'+
            '<% if(typeof(eventBoardList) != "undefined" && eventBoardList.length > 0){%>' +
            '<% for(var j=0;j<eventBoardList[i].cyEventBoardPicList.length;j++){ %>'+
              '<li><a href="<%=eventBoardList[i].cyEventBoardPicList[j].xemanhdep%>"><span style="background-image:url(<%=eventBoardList[i].cyEventBoardPicList[j].thumbnail%>)"></span></a></li>'+
            '<% } %>'+
            '<% } %>'+
            '</ul>'+
            '<div class="action">'+
              '<% if(eventBoardList[i].parise){ %>'+
              '<button class="like" id="<%= eventBoardList[i].id %>"><i class="fa fa-heart"></i> 取消</button>'+
              '<% } else { %>'+
              '<button class="like" id="<%= eventBoardList[i].id %>"><i class="fa fa-heart-o"></i> 赞</button>'+
              '<% } %>'+
              '<a href="eventBoardCommentList.jsp?eventId=<%= eventBoardList[i].eventId %>&boardId=<%= eventBoardList[i].id %>&userInfoId=<%= userInfoIdStr %>" class="comment"><i class="fa fa-comment"></i> 评论</a>'+
            '</div>'+
            '<div class="action-status">评论 <span class="comment-num"><%=eventBoardList[i].commentNum%></span> 赞 <span class="like-num"><%=eventBoardList[i].praiseNum%></span></div>'+
          '</li>'+
          '<% } %>';

        //花絮详情
    var eventBoardDetailTpl ='' +
    		'<% totalRows = eventBoard.commentNum; %>' +
    		'<div class="ui-avatar">'+
              '<span style="background-image:url(<%=eventBoard.userAvatar%>)"></span>'+
            '</div>'+
            '<div class="ui-list-info">'+
              '<h4 class="ui-nowrap"><%=eventBoard.userName%></h4>'+
              '<button class="post-time show-bot-menu" id="<%= eventBoard.userInfoId %>" rel="<%= eventBoard.id %>"><%=eventBoard.createTimeStr%> <i class="fa fa-angle-down"></i></button>'+
              '<p><%=eventBoard.comment%></p>'+
            '</div>'+
            '<ul class="ui-list-pic clearfix">'+
              '<% for(var i=0;i<eventBoard.cyEventBoardPicList.length;i++){ %>'+
                '<li><a href="<%=eventBoard.cyEventBoardPicList[i].xemanhdep%>"><span style="background-image:url(<%=eventBoard.cyEventBoardPicList[i].thumbnail%>)"></span></a></li>'+
              '<% } %>'+
            '</ul>'+
            '<div class="action">'+
            	'<% if(eventBoard.parise){ %>'+
            	'<button class="like" id="<%= eventBoard.id %>"><i class="fa fa-heart"></i> 取消</button>'+
            	'<% } else { %>'+
            	'<button class="like" id="<%= eventBoard.id %>"><i class="fa fa-heart-o"></i> 赞</button>'+
            	'<% } %>'+
            '</div>'+
            '<div class="action-status">评论 <span class="comment-num"><%=eventBoard.commentNum%></span> 赞 <span class="like-num"><%=eventBoard.praiseNum%></span></div>';

    var eventBoardCommentListTpl = '<% for(var i=0;i<commentList.length;i++){ %>'+
                '<li>'+
                  '<div class="ui-avatar"><span style="background-image:url(<%=commentList[i].userAvatar%>)"></span></div>'+
                  '<div class="ui-list-info ui-border-t">'+
                      '<h4><%=commentList[i].userName%><span class="post-time"><%=commentList[i].createTimeStr%></span></h4>'+
                      '<p><%=commentList[i].comment%></p>'+
                  '</div>'+
              '</li>'+
              '<% } %>';
    
    
  //地域筛选
    var chooseCityTpl = '<section class="choose-city">'+
        '<h3>城市</h3>'+
        '<div class="ui-label-list">'+
        '<button class="ui-label checked" data-href="全部">全部</button>'+
        '<% for(var i=0;i<ProvinceCapital.length;i++){ %>'+
        '<button class="ui-label" data-href="<%=ProvinceCapital[i].cityName%>"><%=ProvinceCapital[i].cityName.replace(/市/, "")%></button>'+
        '<% } %>'+
        '</div>'+
        '<h3>省份</h3>'+
        '<div class="ui-label-list">'+
        '<% for(var i=0;i<Province.length;i++){ %>'+
        '<button class="ui-label" data-href="<%=Province[i].provinceName%>"><%=Province[i].provinceName%></button>'+
        '<% } %>'+
        '</div>'+
        '</section>';
    
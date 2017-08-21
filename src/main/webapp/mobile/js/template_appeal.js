//底部弹出菜单
var actionsheetTpl = '<div class="ui-actionsheet">'+
    '<div class="ui-actionsheet-cnt">'+
    '<h4>请选择操作</h4>'+
    '<button class="ui-actionsheet-del" rel="">删除</button>'+
    '<button class="ui-actionsheet-complaint" rel="">举报</button>'+
    '<button class="cancel">取消</button>'+
    '</div>'+
    '</div>';

//图片浏览模板
var pswpTpl = '<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">'+
             '<div class="pswp__bg"></div>'+
             '<div class="pswp__scroll-wrap">'+
            '<div class="pswp__container">'+
            '<div class="pswp__item"></div>'+
            '<div class="pswp__item"></div>'+
            '<div class="pswp__item"></div>'+
            '</div>'+
            '<div class="pswp__ui pswp__ui--hidden">'+
            '<div class="pswp__top-bar">'+
            '<div class="pswp__counter"></div>'+
            '<button class="pswp__button pswp__button--close" title="Close (Esc)"></button>'+
            '<button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>'+
            '<div class="pswp__preloader">'+
            '<div class="pswp__preloader__icn">'+
            '<div class="pswp__preloader__cut">'+
            '<div class="pswp__preloader__donut"></div>'+
            '</div>'+
            '</div>'+
            '</div>'+
            '</div>'+
            '<div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">'+
            '<div class="pswp__share-tooltip"></div>'+
            '</div>'+
            '<button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">'+
            '</button>'+
            '<button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">'+
            '</button>'+
            '<div class="pswp__caption">'+
            '<div class="pswp__caption__center"></div>'+
            '</div>'+
            '</div>'+
            '</div>'+
            '</div>';

//地域筛选
var chooseCityTpl = '<section class="choose-city">'+
    '<h3>城市</h3>'+
    '<div class="ui-label-list">'+
    '<label class="ui-label checked" data-href="">全部</label>'+
        '<% for(var i=0;i<ProvinceCapital.length;i++){ %>'+
    '<label class="ui-label" data-href="<%=ProvinceCapital[i].cityName%>"><%=ProvinceCapital[i].cityName.replace(/市/, "")%></label>'+
        '<% } %>'+
    '</div>'+
    '<h3>省份</h3>'+
    '<div class="ui-label-list">'+
    '<% for(var i=0;i<Province.length;i++){ %>'+
    '<label class="ui-label" data-href="<%=Province[i].provinceName%>"><%=Province[i].provinceName%></label>'+
    '<% } %>'+
    '</div>'+
    '</section>';

//帖子列表模板
var appealTpl = '<% totalRows = countServ == null? 0 : countServ; %>' +
				'<% if(totalRows == 0){ %>' + '' + '<%}else{%>' +
				'<%for(var i=0;i<servList.length;i++){ %>'+
				'<li>'+
                '<div class="thread ui-border-t">'+
                    '<div class="post-user">'+
                        '<div class="ui-avatar">' +
                        
                        '<% if(servList[i].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
                        '<span style="background-image:url(../img/icon_official.png)"></span>'+
                        '<% }else if(servList[i].type == 5){%>' +
                        '<span style="background-image:url(../img/icon_alumni.png)"></span>'+
                        '<% }else if(servList[i].type == 9){%>' +
                        '<span style="background-image:url(<%=servList[i].userAvatar%>)"></span>'+
                        '<% }%>' +
                        
                    	'</div>'+
                        '<div class="ui-list-info">'+
                            
                        
                            '<% if(servList[i].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
                            '<h4 class="ui-nowrap ui-txt-red">校总会<span class="ui-tag-red">官方</span></h4>'+
                            '<% }else if(servList[i].type == 5){%>' +
                            '<h4 class="ui-nowrap ui-txt-blue">校友会<span class="ui-tag-blue">地方校友会</span></h4>'+
                            '<% }else if(servList[i].type == 9){%>' +
                            '<h4 class="ui-nowrap"><%=servList[i].userName%></h4>'+
                            '<% }%>' +
                            
                        	
                        	
                        
                            '<p class="ui-nowrap"><%=servList[i].createTimeStr%></p>'+
                            '<span class="show-bot-menu" id="<%= servList[i].accountNum %>" rel="<%= servList[i].id %>"><i class="fa fa-angle-down"></i></span>'+
                        '</div>'+
                    '</div>'+
                    '<div class="post-content" data-href="favourDetail.jsp?id=<%= servList[i].id %>&accountNum=<%= accountNum %>&isWhat=<%= isWhat %>&category=<%= category %>&region=<%=region%>">'+
                        '<article><%=servList[i].content%></article>'+
                        '<% if (servList[i].cyServPicList.length >0){ %>'+
                        '<ul class="ui-list-pic clearfix">'+
                        '<% for(var j=0;j<servList[i].cyServPicList.length;j++){ %>'+
                            '<li><a href="<%=servList[i].cyServPicList[j].pic%>"><span style="background-image:url(<%=servList[i].cyServPicList[j].pic%>)"></span></a></li>'+
                        '<% } %>'+
                        '</ul>'+
                        '<% } %>'+
                    '</div>'+
                    '<div class="post-action ui-border-tb">'+
                        '<ul class="ui-row-flex">'+
                        '<% if(servList[i].parise){ %>'+
                            '<li class="ui-col ui-col ui-border-r like clicked" rel="<%=servList[i].id%>"><i class="fa fa-heart"></i> <span class="like-num"><%=servList[i].praiseNum%></span></li>'+
                        '<% } else { %>'+
                            '<li class="ui-col ui-col ui-border-r like" rel="<%=servList[i].id%>"><i class="fa fa-heart-o"></i><% if(servList[i].praiseNum > 0){%><span class="like-num"><%=servList[i].praiseNum%></span><% } else {%> 赞<% } %></li>'+
                        '<% } %>'+
                        '<% if(servList[i].favorite){ %>'+
                            '<li class="ui-col ui-col ui-border-r favorites clicked" rel="<%=servList[i].id%>"><i class="fa fa-star"></i> 已收藏</li>'+
                        '<% } else { %>'+
                        	'<li class="ui-col ui-col ui-border-r favorites" rel="<%=servList[i].id%>"><i class="fa fa-star-o"></i> 收藏</li>'+
                        '<% } %>'+
                        '<% if(servList[i].commentNum > 0){ %>'+
                            '<li class="ui-col ui-col comment"><i class="fa fa-comment"></i> <%=servList[i].commentNum%></li>'+
                        '<% } else { %>'+
                            '<li class="ui-col ui-col comment"><i class="fa fa-comment-o"></i> 评论</li>'+
                        '<% } %>'+
                        '</ul>'+
                    '</div>'+
                '</div>'+
                '</li>'+
                '<% } }%>';

//帖子列表模板
var appealTplQzzp = '<% totalRows = countServ == null? 0 : countServ; %>' +
    '<% if(totalRows == 0){ %>' + '' + '<%}else{%>' +
    '<%for(var i=0;i<servList.length;i++){ %>'+
    '<%var contentstr = servList[i].content;var contentObj=null;try{contentObj=JSON.parse(contentstr);}catch(e){if(contentstr.length>12){contentstr=contentstr.substring(0,11)+"...";}contentObj={"title":contentstr,"type":""};}%>'+
    '<li>'+
    '<div class="thread ui-border-t" style="border-bottom:1px #E9E9EB solid;margin-top:0px;">'+
    '<div class="post-user">'+
    '<div class="ui-avatar">' +

    '<% if(servList[i].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
    '<span style="background-image:url(../img/icon_official.png)"></span>'+
    '<% }else if(servList[i].type == 5){%>' +
    '<span style="background-image:url(../img/icon_alumni.png)"></span>'+
    '<% }else if(servList[i].type == 9){%>' +
    '<span style="background-image:url(<%=servList[i].userAvatar%>)"></span>'+
    '<% }%>' +

    '</div>'+
    '<div class="ui-list-info" style="padding-bottom:0px;">'+
    '<div class="post-content" data-href="favourDetailQzzp.jsp?id=<%= servList[i].id %>&accountNum=<%= accountNum %>&isWhat=<%= isWhat %>&category=<%= category %>&region=<%=region%>">'+
    '<article>'+
    '<h4><%=contentObj.title%><%if(contentObj.type=="全职"){%><img style="width:40px;height:20px;" src="../../images/qz.png"><%}%><%if(contentObj.type=="兼职"){%><img style="width:40px;height:20px;" src="../../images/jz.png"><%}%><%if(contentObj.type=="实习"){%><img style="width:40px;height:20px;" src="../../images/sx.png"><%}%></h4>'+
    '<% if(servList[i].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
    '<h6 class="ui-nowrap ui-txt-red">校总会<span class="ui-tag-red">官方</span></h6>'+
    '<% }else if(servList[i].type == 5){%>' +
    '<h6 class="ui-nowrap ui-txt-blue">校友会<span class="ui-tag-blue">地方校友会</span></h6>'+
    '<% }else if(servList[i].type == 9){%>' +
    '<h6 style="color:#BFBFBF;" class="ui-nowrap"><%=servList[i].userName%></h6>'+
    '<% }%>' +
    '<span style="font-size:10px;color:#CCCCCC;margin-right:5px;"><%=servList[i].createTimeStr%></span></article>'+
    '</div>'+
    '<span class="show-bot-menu" id="<%= servList[i].accountNum %>" rel="<%= servList[i].id %>"><i class="fa fa-angle-down"></i></span>'+
    '</div>'+
    '</div>'+

    '<table style="width:100%;"><tr><td >&nbsp;</td><td style="width:15%;">'+
    '<div class="post-action ui-border-tb" style="background:#ffffff;margin-top:0px;">'+
    '<ul class="ui-row-flex" style="">'+
    '<% if(servList[i].favorite){ %>'+
    '<li class="ui-col ui-col ui-border-r favorites clicked" rel="<%=servList[i].id%>"><i class="fa fa-star"></i></li>'+
    '<% } else { %>'+
    '<li class="ui-col ui-col ui-border-r favorites" rel="<%=servList[i].id%>"><i class="fa fa-star-o"></i></li>'+
    '<% } %>'+
    '</ul>'+
    '</div>'+
    '</tr></table>'+

    '</div>'+
    '</li>'+
    '<% } }%>';

var appealTplQzzp2 = '<% totalRows = countServ == null? 0 : countServ; %>' +
    '<% if(totalRows == 0){ %>' + '' + '<%}else{%>' +
    '<%for(var i=0;i<servList.length;i++){ %>'+
    '<%var contentstr = servList[i].content;var contentObj=null;try{contentObj=JSON.parse(contentstr);}catch(e){if(contentstr.length>12){contentstr=contentstr.substring(0,11)+"...";}contentObj={"title":contentstr,"type":""};}%>'+
    '<li>'+
    '<div class="thread ui-border-t" style="border-bottom:1px #E9E9EB solid;margin-top:0px;">'+
    '<div class="post-user">'+
    '<div class="ui-list-info" style="padding-bottom:0px;">'+
    '<div class="post-content" data-href="favourDetailQzzp.jsp?id=<%= servList[i].id %>&accountNum=<%= accountNum %>&isWhat=<%= isWhat %>&category=<%= category %>&region=<%=region%>">'+
    '<article style="background:url(../../images/icon_chevron_right.png) no-repeat 94% 10px;">'+
    '<span style="font-size:10px;color:#CCCCCC;margin-right:5px;"><%=servList[i].createTimeStr%></span>'+
    '<h4><%=contentObj.title%></h4>'+
    '</article>'+
    '</div>'+

    '</div>'+
    '</div>'+

    '</div>'+
    '</li>'+
    '<% } }%>';

var commentTplQzzp = '<div class="thread ui-border-t">'+
    '<div class="post-user">'+
    '<%var contentstr = serv.content;var contentObj=null;try{contentObj=JSON.parse(contentstr);}catch(e){if(contentstr.length>12){var contentstr2=contentstr.substring(0,11)+"...";}contentObj={"title":contentstr2,"type":"","address":"","description":contentstr,"requirement":""};}%>'+

    '<div class="ui-list-info" style="padding-left: 15px;">'+
    '<h3><%=contentObj.title%></h3>'+
    '<ul class="commentTplQzzpUl"><li><img src="../../images/icon_time.png">&nbsp;<%=contentObj.type%></li><li><img src="../../images/icon_lacation.png">&nbsp;<%=contentObj.address%></li><li><img src="../../images/icon_number.png">&nbsp;'+
    '<% if(serv.type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
    '<span class="ui-nowrap ui-txt-red">校总会<span class="ui-tag-red">官方</span></span>'+
    '<% }else if(serv.type == 5){%>' +
    '<span class="ui-nowrap ui-txt-blue">校友会<span class="ui-tag-blue">地方校友会</span></span>'+
    '<% }else if(serv.type == 9){%>' +
    '<span class="ui-nowrap"><%=serv.userName%></span>'+
    '<% }%>' +
    '</li><li><%=serv.createTimeStr%></li></ul>'+
    '</div>'+

    '</div>'+
    '</div>'+

    '<div class="post-content" style="margin-top: 10px;background: #ffffff;">'+
    '<div style="font-size: 14px;border-bottom: 1px solid #EFEFF4;margin:15px 0px 15px 15px;padding: 15px 0px 10px 0px;">岗位描述</div>'+
    '<article style="font-size: 14px;color:#BBBBBB;border-bottom: 0px solid #EFEFF4;margin:0px 0px 15px 15px;padding: 0px 0px 10px 0px;"><pre style="white-space: pre-wrap;word-wrap: break-word;"><%=contentObj.description%></pre></article>'+
    '</div>'+

    '<div class="post-content" style="margin-top: 10px;background: #ffffff;">'+
    '<div style="font-size: 14px;border-bottom: 1px solid #EFEFF4;margin:15px 0px 15px 15px;padding: 15px 0px 10px 0px;">岗位要求</div>'+
    '<article style="font-size: 14px;color:#BBBBBB;border-bottom: 0px solid #EFEFF4;margin:0px 0px 15px 15px;padding: 0px 0px 10px 0px;"><pre style="white-space: pre-wrap;word-wrap: break-word;"><%=contentObj.requirement%></pre></article>'+
    '</div>'+

    '<div class="post-action ui-border-tb" style="background: #ffffff;margin-top:10px;border-bottom: 1px #F2F2F2 solid;">'+
    '<ul class="ui-row-flex">'+
    '<li class="ui-col ui-col comment" style="text-align: left;"><i style="font-size:14px;color:#000000;padding-left: 15px;">评论 &nbsp;<%=serv.commentNum%></i> </li>'+
    '</ul>'+
    '</div>'+


    '<div class="post-comment">'+
    '<ul class="ui-list ui-list-active ui-border-tb">'+
    '<% totalRows = (serv.commentNum == null ? 0 : serv.commentNum); for(var j=0;j < commentList.length;j++){%>'+
    '<li class="ui-border-t">'+
    '<div class="ui-avatar">'+

    '<% if(commentList[j].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
    '<span style="background-image:url(../img/icon_official.png)"></span>'+
    '<% }else if(commentList[j].type == 5){%>' +
    '<span style="background-image:url(../img/icon_alumni.png)"></span>'+
    '<% }else if(commentList[j].type == 9){%>' +
    '<span style="background-image:url(<%=commentList[j].userAvatar%>)"></span>'+
    '<% }%>' +

    //'<span style="background-image:url(<%=commentList[j].userAvatar%>)"></span>'+

    '</div>'+
    '<div class="ui-list-info">'+

    '<% if(commentList[j].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
    '<h4 class="ui-nowrap ui-txt-red">校总会<span class="ui-tag-red">官方</span><span class="post-time"><%=commentList[j].createTimeStr%></span></h4>'+
    '<% }else if(commentList[j].type == 5){%>' +
    '<h4 class="ui-nowrap ui-txt-blue">校友会<span class="ui-tag-blue">地方校友会</span><span class="post-time"><%=commentList[j].createTimeStr%></span></h4>'+
    '<% }else if(commentList[j].type == 9){%>' +
    '<h4 class="ui-nowrap"><%=commentList[j].userName%><span class="post-time"><%=commentList[j].createTimeStr%></span></h4>'+
    '<% }%>' +

    //'<h4><%=commentList[j].userName%><span class="post-time"><%=commentList[j].createTimeStr%></span></h4>'+

    '<p><%=commentList[j].content%></p>'+
    '</div>'+
    '</li>'+
    '<% } %>';
'</ul>'+
'</div>';

//帖子列表模板
var appealTplHbhz = '<% totalRows = countServ == null? 0 : countServ; %>' +
                '<% if(totalRows == 0){ %>' + '' + '<%}else{%>' +
                '<%for(var i=0;i<servList.length;i++){ %>'+
                '<li>'+
                '<div class="thread ui-border-t">'+
                    '<div class="post-user">'+
                        '<div class="ui-avatar">' +
                        
                        '<% if(servList[i].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
                        '<span style="background-image:url(../img/icon_official.png)"></span>'+
                        '<% }else if(servList[i].type == 5){%>' +
                        '<span style="background-image:url(../img/icon_alumni.png)"></span>'+
                        '<% }else if(servList[i].type == 9){%>' +
                        '<span style="background-image:url(<%=servList[i].userAvatar%>)"></span>'+
                        '<% }%>' +
                        
                        '</div>'+
                        '<div class="ui-list-info">'+
                            
                        
                            '<% if(servList[i].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
                            '<h4 class="ui-nowrap ui-txt-red">校总会<span class="ui-tag-red">官方</span></h4>'+
                            '<% }else if(servList[i].type == 5){%>' +
                            '<h4 class="ui-nowrap ui-txt-blue">校友会<span class="ui-tag-blue">地方校友会</span></h4>'+
                            '<% }else if(servList[i].type == 9){%>' +
                            '<h4 class="ui-nowrap"><%=servList[i].userName%></h4>'+
                            '<% }%>' +

                            '<p class="ui-nowrap"><%=servList[i].createTimeStr%></p>'+
                            '<span class="show-bot-menu" id="<%= servList[i].accountNum %>" rel="<%= servList[i].id %>"><i class="fa fa-angle-down"></i></span>'+
                        '</div>'+
                    '</div>'+
                    '<div class="post-content" data-href="favourDetail.jsp?region=<%=region%>&sourceFlag=0&id=<%= servList[i].id %>&accountNum=<%= accountNum %>&isWhat=<%= isWhat %>&category=<%= category %>">'+
                        '<article style="font-size:14px;"><%=servList[i].content%></article>'+
                        '<% if (servList[i].cyServPicList.length >0){ %>'+
                        '<ul class="ui-list-pic clearfix">'+
                        '<% for(var j=0;j<servList[i].cyServPicList.length;j++){ %>'+
                            '<li><a href="<%=servList[i].cyServPicList[j].pic%>"><span style="background-image:url(<%=servList[i].cyServPicList[j].pic%>)"></span></a></li>'+
                        '<% } %>'+
                        '</ul>'+
                        '<% } %>'+
                    '</div>'+
                    '<div class="post-action ui-border-tb" style="border:1px solid #EEEEEE;border-width:1px 0px 1px 0px;">'+
                        '<ul class="ui-row-flex">'+
                        '<% if(servList[i].favorite){ %>'+
                            '<li class="ui-col ui-col ui-border-r favorites clicked" rel="<%=servList[i].id%>"><i class="fa fa-star"></i> 已收藏</li>'+
                        '<% } else { %>'+
                            '<li class="ui-col ui-col ui-border-r favorites" rel="<%=servList[i].id%>"><i class="fa fa-star-o"></i> 收藏</li>'+
                        '<% } %>'+
                        '<% if(servList[i].commentNum > 0){ %>'+
                            '<li class="ui-col ui-col comment"><i class="fa fa-comment"></i> <%=servList[i].commentNum%></li>'+
                        '<% } else { %>'+
                            '<li class="ui-col ui-col comment"><i class="fa fa-comment-o"></i> 评论</li>'+
                        '<% } %>'+
                        '</ul>'+
                    '</div>'+
                '</div>'+
                '</li>'+
                '<% } }%>';

var appealTplHbhz2 = '<% totalRows = countServ == null? 0 : countServ; %>' +
    '<% if(totalRows == 0){ %>' + '' + '<%}else{%>' +
    '<%for(var i=0;i<servList.length;i++){ %>'+
    '<li>'+
    '<div class="thread ui-border-t" style="margin-top:0px;padding-top:0px;">'+

    '<div class="ui-list-info" style="margin-top:0px;padding-top:0px;background:url(../../images/icon_chevron_right.png) no-repeat 90% 10px;">'+
    '<p class="ui-nowrap" style="font-size:10px;margin:10px 0px 0px 15px;"><%=servList[i].createTimeStr%></p>'+
    '</div>'+

    '<div class="post-content" data-href="favourDetail.jsp?region=<%=region%>&sourceFlag=0&id=<%= servList[i].id %>&accountNum=<%= accountNum %>&isWhat=<%= isWhat %>&category=<%= category %>" style="font-size:14px;margin:0px 0px 0px 5px;">'+
    '<article style="font-size:14px;"><%=servList[i].content%></article>'+

    '<% if (servList[i].cyServPicList.length >0){ %>'+
    '<ul class="ui-list-pic clearfix">'+
    '<% for(var j=0;j<servList[i].cyServPicList.length;j++){ %>'+
    '<li><a href="<%=servList[i].cyServPicList[j].pic%>"><span style="background-image:url(<%=servList[i].cyServPicList[j].pic%>)"></span></a></li>'+
    '<% } %>'+
    '</ul>'+
    '<% } %>'+

    '</div>'+

    '</div>'+
    '</li>'+
    '<% } }%>';

//帖子列表模板
var appealTplXmhz = '<% totalRows = countServ == null? 0 : countServ; %>' +
    '<% if(totalRows == 0){ %>' + '' + '<%}else{%>' +
    '<%for(var i=0;i<servList.length;i++){ %>'+
    '<li>'+
    '<div class="thread ui-border-t">'+
    '<div class="post-user">'+
    '<div class="ui-avatar">' +

    '<% if(servList[i].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
    '<span style="background-image:url(../img/icon_official.png)"></span>'+
    '<% }else if(servList[i].type == 5){%>' +
    '<span style="background-image:url(../img/icon_alumni.png)"></span>'+
    '<% }else if(servList[i].type == 9){%>' +
    '<span style="background-image:url(<%=servList[i].userAvatar%>)"></span>'+
    '<% }%>' +

    '</div>'+
    '<div class="ui-list-info">'+


    '<% if(servList[i].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
    '<h4 class="ui-nowrap ui-txt-red">校总会<span class="ui-tag-red">官方</span></h4>'+
    '<% }else if(servList[i].type == 5){%>' +
    '<h4 class="ui-nowrap ui-txt-blue">校友会<span class="ui-tag-blue">地方校友会</span></h4>'+
    '<% }else if(servList[i].type == 9){%>' +
    '<h4 class="ui-nowrap"><%=servList[i].userName%></h4>'+
    '<% }%>' +

    '<p class="ui-nowrap"><%=servList[i].createTimeStr%></p>'+
    '<span class="show-bot-menu" id="<%= servList[i].accountNum %>" rel="<%= servList[i].id %>"><i class="fa fa-angle-down"></i></span>'+
    '</div>'+
    '</div>'+
    '<div class="post-content" data-href="favourDetail.jsp?region=<%=region%>&sourceFlag=1&id=<%= servList[i].id %>&accountNum=<%= accountNum %>&isWhat=<%= isWhat %>&category=<%= category %>">'+
    '<article style="font-size:14px;"><%=servList[i].content%></article>'+
    '<% if (servList[i].cyServPicList.length >0){ %>'+
    '<ul class="ui-list-pic clearfix">'+
    '<% for(var j=0;j<servList[i].cyServPicList.length;j++){ %>'+
    '<li><a href="<%=servList[i].cyServPicList[j].pic%>"><span style="background-image:url(<%=servList[i].cyServPicList[j].pic%>)"></span></a></li>'+
    '<% } %>'+
    '</ul>'+
    '<% } %>'+
    '</div>'+
    '<div class="post-action ui-border-tb" style="border:1px solid #EEEEEE;border-width:1px 0px 1px 0px;">'+
    '<ul class="ui-row-flex">'+
    '<% if(servList[i].favorite){ %>'+
    '<li class="ui-col ui-col ui-border-r favorites clicked" rel="<%=servList[i].id%>"><i class="fa fa-star"></i> 已收藏</li>'+
    '<% } else { %>'+
    '<li class="ui-col ui-col ui-border-r favorites" rel="<%=servList[i].id%>"><i class="fa fa-star-o"></i> 收藏</li>'+
    '<% } %>'+
    '<% if(servList[i].commentNum > 0){ %>'+
    '<li class="ui-col ui-col comment"><i class="fa fa-comment"></i> <%=servList[i].commentNum%></li>'+
    '<% } else { %>'+
    '<li class="ui-col ui-col comment"><i class="fa fa-comment-o"></i> 评论</li>'+
    '<% } %>'+
    '</ul>'+
    '</div>'+
    '</div>'+
    '</li>'+
    '<% } }%>';

var appealTplXmhz2 = '<% totalRows = countServ == null? 0 : countServ; %>' +
    '<% if(totalRows == 0){ %>' + '' + '<%}else{%>' +
    '<%for(var i=0;i<servList.length;i++){ %>'+
    '<li>'+
    '<div class="thread ui-border-t" style="margin-top:0px;padding-top:0px;">'+

    '<div class="ui-list-info" style="margin-top:0px;padding-top:0px;background:url(../../images/icon_chevron_right.png) no-repeat 90% 10px;">'+
    '<p class="ui-nowrap" style="font-size:10px;margin:10px 0px 0px 15px;"><%=servList[i].createTimeStr%></p>'+
    '</div>'+

    '<div class="post-content" data-href="favourDetail.jsp?region=<%=region%>&sourceFlag=1&id=<%= servList[i].id %>&accountNum=<%= accountNum %>&isWhat=<%= isWhat %>&category=<%= category %>" style="font-size:14px;margin:0px 0px 0px 5px;">'+
    '<article style="font-size:14px;"><%=servList[i].content%></article>'+

    '<% if (servList[i].cyServPicList.length >0){ %>'+
    '<ul class="ui-list-pic clearfix">'+
    '<% for(var j=0;j<servList[i].cyServPicList.length;j++){ %>'+
    '<li><a href="<%=servList[i].cyServPicList[j].pic%>"><span style="background-image:url(<%=servList[i].cyServPicList[j].pic%>)"></span></a></li>'+
    '<% } %>'+
    '</ul>'+
    '<% } %>'+

    '</div>'+

    '</div>'+
    '</li>'+
    '<% } }%>';

//帖子列表模板bak
var appealTpl_bak = '<% totalRows = countServ == null? 0 : countServ; %>' +
                '<% if(totalRows == 0){ %>' + '' + '<%}else{%>' +
                '<%for(var i=0;i<servList.length;i++){ %>'+
                '<li>'+
                '<div class="thread ui-border-t">'+
                    '<div class="post-user">'+
                        '<div class="ui-avatar">' +
                        
                        '<% if(servList[i].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
                        '<span style="background-image:url(../img/icon_official.png)"></span>'+
                        '<% }else if(servList[i].type == 5){%>' +
                        '<span style="background-image:url(../img/icon_alumni.png)"></span>'+
                        '<% }else if(servList[i].type == 9){%>' +
                        '<span style="background-image:url(<%=servList[i].userAvatar%>)"></span>'+
                        '<% }%>' +
                        
                        '</div>'+
                        '<div class="ui-list-info">'+
                            
                        
                            '<% if(servList[i].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
                            '<h4 class="ui-nowrap ui-txt-red">校总会<span class="ui-tag-red">官方</span></h4>'+
                            '<% }else if(servList[i].type == 5){%>' +
                            '<h4 class="ui-nowrap ui-txt-blue">校友会<span class="ui-tag-blue">地方校友会</span></h4>'+
                            '<% }else if(servList[i].type == 9){%>' +
                            '<h4 class="ui-nowrap"><%=servList[i].userName%></h4>'+
                            '<% }%>' +
                            
                            
                            
                        
                            '<p class="ui-nowrap"><%=servList[i].createTimeStr%></p>'+
                            '<span class="show-bot-menu" id="<%= servList[i].accountNum %>" rel="<%= servList[i].id %>"><i class="fa fa-angle-down"></i></span>'+
                        '</div>'+
                    '</div>'+
                    '<div class="post-content" data-href="favourDetail.jsp?id=<%= servList[i].id %>&accountNum=<%= accountNum %>&isWhat=<%= isWhat %>&category=<%= category %>&region=<%=region%>">'+
                        '<article><%=servList[i].content%></article>'+
                        '<% if (servList[i].cyServPicList.length >0){ %>'+
                        '<ul class="ui-list-pic clearfix">'+
                        '<% for(var j=0;j<servList[i].cyServPicList.length;j++){ %>'+
                            '<li><a href="<%=servList[i].cyServPicList[j].pic%>"><span style="background-image:url(<%=servList[i].cyServPicList[j].pic%>)"></span></a></li>'+
                        '<% } %>'+
                        '</ul>'+
                        '<% } %>'+
                    '</div>'+
                    '<div class="post-action ui-border-tb">'+
                        '<ul class="ui-row-flex">'+
                        '<% if(servList[i].parise){ %>'+
                            '<li class="ui-col ui-col ui-border-r like clicked" rel="<%=servList[i].id%>"><i class="fa fa-heart"></i> <span class="like-num"><%=servList[i].praiseNum%></span></li>'+
                        '<% } else { %>'+
                            '<li class="ui-col ui-col ui-border-r like" rel="<%=servList[i].id%>"><i class="fa fa-heart-o"></i><% if(servList[i].praiseNum > 0){%><span class="like-num"><%=servList[i].praiseNum%></span><% } else {%> 赞<% } %></li>'+
                        '<% } %>'+
                        '<% if(servList[i].favorite){ %>'+
                            '<li class="ui-col ui-col ui-border-r favorites clicked" rel="<%=servList[i].id%>"><i class="fa fa-star"></i> 已收藏</li>'+
                        '<% } else { %>'+
                            '<li class="ui-col ui-col ui-border-r favorites" rel="<%=servList[i].id%>"><i class="fa fa-star-o"></i> 收藏</li>'+
                        '<% } %>'+
                        '<% if(servList[i].commentNum > 0){ %>'+
                            '<li class="ui-col ui-col comment"><i class="fa fa-comment"></i> <%=servList[i].commentNum%></li>'+
                        '<% } else { %>'+
                            '<li class="ui-col ui-col comment"><i class="fa fa-comment-o"></i> 评论</li>'+
                        '<% } %>'+
                        '</ul>'+
                    '</div>'+
                '</div>'+
                '</li>'+
                '<% } }%>';

var commentTpl = '<div class="thread ui-border-t">'+
                    '<div class="post-user">'+
                        '<div class="ui-avatar">'+
                        
                        '<% if(serv.type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
                        '<span style="background-image:url(../img/icon_official.png)"></span>'+
                        '<% }else if(serv.type == 5){%>' +
                        '<span style="background-image:url(../img/icon_alumni.png)"></span>'+
                        '<% }else if(serv.type == 9){%>' +
                        '<span style="background-image:url(<%=serv.userAvatar%>)"></span>'+
                        '<% }%>' +
                        
                        '</div>'+
                        '<div class="ui-list-info">'+
                        
	                        '<% if(serv.type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
	                        '<h4 class="ui-nowrap ui-txt-red">校总会<span class="ui-tag-red">官方</span></h4>'+
	                        '<% }else if(serv.type == 5){%>' +
	                        '<h4 class="ui-nowrap ui-txt-blue">校友会<span class="ui-tag-blue">地方校友会</span></h4>'+
	                        '<% }else if(serv.type == 9){%>' +
	                        '<h4 class="ui-nowrap"><%=serv.userName%></h4>'+
	                        '<% }%>' +
                        	
                        
                           
                            
                            '<p class="ui-nowrap"><%=serv.createTimeStr%></p>'+
                            '<span class="show-bot-menu" id="<%= serv.accountNum %>" rel="<%= serv.id %>"><i class="fa fa-angle-down"></i></span>'+
                        '</div>'+
                    '</div>'+
                    '<div class="post-content">'+
                        '<article><%=serv.content%></article>'+
                        '<% if (serv.cyServPicList.length >0){ %>'+
                        '<ul class="ui-list-pic clearfix">'+
                            '<% for(var i=0;i<serv.cyServPicList.length;i++){ %>'+
                            '<li><a href="<%=serv.cyServPicList[i].pic%>"><span style="background-image:url(<%=serv.cyServPicList[i].pic%>)"></span></a></li>'+
                            '<% } %>'+
                        '</ul>'+
                        '<% } %>'+
                    '</div>'+
                    '<div class="post-action ui-border-tb">'+
                        '<ul class="ui-row-flex">'+
                            '<% if(serv.parise){ %>'+
                            	'<li class="ui-col ui-col ui-border-r like clicked" rel="<%=serv.id%>"><i class="fa fa-heart"></i> <span class="like-num"><%=serv.praiseNum%></span></li>'+
                            '<% } else { %>'+
                            	'<li class="ui-col ui-col ui-border-r like" rel="<%=serv.id%>"><i class="fa fa-heart-o"></i><% if(serv.praiseNum > 0){%><span class="like-num"><%=serv.praiseNum%></span><% } else {%> 赞<% } %></li>'+
                            '<% } %>'+
                            '<% if(serv.favorite){ %>'+
                            	'<li class="ui-col ui-col ui-border-r favorites clicked" rel="<%=serv.id%>"><i class="fa fa-star"></i> 已收藏</li>'+
	                        '<% } else { %>'+
	                        	'<li class="ui-col ui-col ui-border-r favorites" rel="<%=serv.id%>"><i class="fa fa-star-o"></i> 收藏</li>'+
	                        '<% } %>'+
                            '<li class="ui-col ui-col comment"><i class="fa fa-comment-o"></i> <%=serv.commentNum%></li>'+
                        '</ul>'+
                    '</div>'+
                '</div>'+
                '<div class="post-comment">'+
                    '<ul class="ui-list ui-list-active ui-border-tb">'+
                        '<% totalRows = (serv.commentNum == null ? 0 : serv.commentNum); for(var j=0;j < commentList.length;j++){%>'+
                        '<li class="ui-border-t">'+
                            '<div class="ui-avatar">'+
                            
                            '<% if(commentList[j].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
                            '<span style="background-image:url(../img/icon_official.png)"></span>'+
                            '<% }else if(commentList[j].type == 5){%>' +
                            '<span style="background-image:url(../img/icon_alumni.png)"></span>'+
                            '<% }else if(commentList[j].type == 9){%>' +
                            '<span style="background-image:url(<%=commentList[j].userAvatar%>)"></span>'+
                            '<% }%>' +
                            
                            //'<span style="background-image:url(<%=commentList[j].userAvatar%>)"></span>'+
                            
                            '</div>'+
                            '<div class="ui-list-info">'+
                            
	                            '<% if(commentList[j].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
		                        '<h4 class="ui-nowrap ui-txt-red">校总会<span class="ui-tag-red">官方</span><span class="post-time"><%=commentList[j].createTimeStr%></span></h4>'+
		                        '<% }else if(commentList[j].type == 5){%>' +
		                        '<h4 class="ui-nowrap ui-txt-blue">校友会<span class="ui-tag-blue">地方校友会</span><span class="post-time"><%=commentList[j].createTimeStr%></span></h4>'+
		                        '<% }else if(commentList[j].type == 9){%>' +
		                        '<h4 class="ui-nowrap"><%=commentList[j].userName%><span class="post-time"><%=commentList[j].createTimeStr%></span></h4>'+
		                        '<% }%>' +
                            
                                //'<h4><%=commentList[j].userName%><span class="post-time"><%=commentList[j].createTimeStr%></span></h4>'+
                                
                                '<p><%=commentList[j].content%></p>'+
                            '</div>'+
                        '</li>'+
                        '<% } %>';
                    '</ul>'+
                '</div>';
                    
                    
                    var commentMoreTpl = '<% totalRows = (commentList == null?0:commentList.length);  for(var i = 0;i < commentList.length;i++){%>'+
                    '<li class="ui-border-t">'+
                    '<div class="ui-avatar">'+
                    
                    '<% if(commentList[i].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
                    '<span style="background-image:url(../img/icon_official.png)"></span>'+
                    '<% }else if(commentList[i].type == 5){%>' +
                    '<span style="background-image:url(../img/icon_alumni.png)"></span>'+
                    '<% }else if(commentList[i].type == 9){%>' +
                    '<span style="background-image:url(<%=commentList[i].userAvatar%>)"></span>'+
                    '<% }%>' +
                    
                    //'<span style="background-image:url(<%=commentList[i].userAvatar%>)"></span>'+
                    
                    '</div>'+
                    '<div class="ui-list-info">'+
                    
                    
                    '<% if(commentList[i].type == 0){ %>' + //性质（0=官方 ，5=校友会，9=个人）
                    '<h4 class="ui-nowrap ui-txt-red">校总会<span class="ui-tag-red">官方</span><span class="post-time"><%=commentList[i].createTimeStr%></span></h4>'+
                    '<% }else if(commentList[i].type == 5){%>' +
                    '<h4 class="ui-nowrap ui-txt-blue">校友会<span class="ui-tag-blue">地方校友会</span><span class="post-time"><%=commentList[i].createTimeStr%></span></h4>'+
                    '<% }else if(commentList[i].type == 9){%>' +
                    '<h4 class="ui-nowrap"><%=commentList[i].userName%><span class="post-time"><%=commentList[i].createTimeStr%></span></h4>'+
                    '<% }%>' +
                    
                    //'<h4><%=commentList[i].userName%><span class="post-time"><%=commentList[i].createTimeStr%></span></h4>'+
                    
                    '<p><%=commentList[i].content%></p>'+
                    '</div>'+
                    '</li>'+
                    '<% } %>';
                    
                    
                  
                    
var noData = '<section class="ui-notice"> <p>暂无数据</p> </section>';
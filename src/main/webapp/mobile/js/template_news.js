//slider图片新闻切换
var sliderNews = 
	'<% if(typeof(newsList) != "undefined" && newsList != null && newsList.length != 0){ $(".ui-slider").show(); %>' +
	'<ul class="ui-slider-content" style="width:<%=newsList.length*100%>%">'+
    '<% for(var i = 0;i < newsList.length; i++){ %>'+
    '<li <% if (i == 0){ %>class="current"<% } %>  data-href="<%=newsList[i].newsUrl%>"><span style="background-image:url(<%=newsList[i].pic%>)"><h2 class="ui-nowrap"><%=newsList[i].title%></h2></span></li>'+
    '<% } %>'+
    '</ul>' + 
    '<% }else { $(".ui-slider").hide(); }%>';


//新闻列表
var newsList = '<% for(var i = 0;i < newsList.length; i++){ %>'+
                '<li class="ui-border-t" data-href="<%=newsList[i].newsUrl%>" >'+
                /*'<li class="ui-border-t" data-href="../news/newsShow.jsp?newsId=<%=newsList[i].newsId%>" >'+*/
                '<div class="ui-list-img"><span style="background-image:url(<%=newsList[i].pic%>)"></span></div>'+
    '<div class="ui-list-info">'+
        '<h4 class="ui-nowrap"><%=newsList[i].title%></h4>'+
        '<p class="ui-nowrap-multi"><%=newsList[i].introduction%></p>'+
    '</div></li>'+
                '<% } %>';

//tab列表
var tabNav =    '<ul class="ui-tab-nav ui-border-b">'+
                '<% for(var i = 0;i < leveList.length; i++){ %>'+
                    '<li <% if (i == 0){ %>class="current"<% } %> id="nav_<%=i%>" data-href="<%=leveList[i].json_news_url%>"><%=leveList[i].name%></li>'+
                '<% } %>'+
                '</ul><ul class="ui-tab-content" style="width:<%=leveList.length*100%>%">'+
                '<% for(var i = 0;i < leveList.length; i++){ %>'+
                '<li <% if (i == 0){ %>class="current"<% } %> id="content_<%=i%>">'+
                    '<ul class="ui-list ui-border-tb"></ul>'+
                    '<div class="more" style="display: none;"><a href="javascript:" alt="">点击查看更多</a></div>'+
                    '<div class="ui-loading-wrap" style="display:none"><i class="ui-loading"></i><p>加载中</p></div>'+
                '</li>'+
                '<% } %>'+
                '</ul>';
                
                
 //新闻详情
var newsDetailTpl = '<header>'+
                    '<h2><%=title%></h2>'+
                    '<div class="meta">'+
                        '<span class="time"><%=createDate%></span>'+
                        '<span class="author"><%=channelName%></span>'+
                    '</div>'+
                 '</header>'+
                 '<article><%=content%></article>';
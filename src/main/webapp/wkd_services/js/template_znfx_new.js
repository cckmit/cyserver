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
                '<li class="ui-border-t" data-href="../news/newsShow.jsp?newsId=<%=newsList[i].newsId%>" >'+
                '<div class="ui-list-img"><span style="background-image:url(<%=newsList[i].pic%>)"></span></div>'+
    '<div class="ui-list-info">'+
        '<h4 class="ui-nowrap"><%=newsList[i].title%></h4>'+
        '<p class="ui-nowrap-multi"><%=newsList[i].introduction%></p>'+
    '</div></li>'+
                '<% } %>';
var znFxjhPage = '<form id="saveForm" method="post">'
    +'<section class="ui-container create-post">'

    +'  <div class="ui-form-item ui-border-b">'
    +'    <label for="topic" style="color: #777777;font-size: 14px;">组织主题:</label>'
    +'    <input type="text" id="topic" name="topic" maxlength="120">'
    +'  </div>'

    +'  <div class="ui-form-item ui-border-b">'
    +'    <label for="number" style="color: #777777;font-size: 14px;">计划人数:</label>'
    +'    <input type="number" id="number" name="number" maxlength="8" oninput="this.value=this.value.replace(/[\D.]/g,\'\')" onkeyup="this.value=this.value.replace(/[\D.]/g,\'\')" onafterpaste="this.value=this.value.replace(/[\D.]/g,\'\')">'
    +'  </div>'

    +'  <div class="ui-form-item ui-border-b">'
    +'    <label for="time" style="color: #777777;font-size: 14px;">返校时间:</label>'
    // +'    <input type="datetime-local" name="time" id="time" style="border-width: 0px;background-color: white;">'
    +'    <input id="time" name="time" type="date" style="outline: none; width: 100%;height:40px; background: transparent;" placeholder="请选择返校时间">'
    // +'    <input id="time" name="time" type="datetime-local" style="outline: none; width: 100%;height:40px; background: transparent;" placeholder="请选择报名开始时间">'
    +'  </div>'

    +'  <div class="ui-form-item ui-border-b">'
    +'    <label for="classinfo" style="color: #777777;font-size: 14px;">班级:</label>'
    +'    <span><%=deptName%></span>'
    +'  </div>'

    +'  <div class="ui-form-item ui-border-b" style="background: #F8F8F8;">'
    +'    <label style="color: #777777;font-size: 14px;">活动描述</label>'
    +'  </div>'

    +'  <div class="ui-form-item ui-form-item-pure ui-form-item-textarea ui-border-b">'
    +'    <textarea id="other" placeholder="(可选) 请简单描述详情" name="other"></textarea>'
    +'  </div>'

    +'  <div style="text-align:center;border: 0px;background: #F8F8F8;padding: 20px 0px;">'
    +'  <input type="button" id="submitid" class="ui-btn-lg ui-btn-primary" style="position:relative;left: 15%;width: 70%;text-align:center;" value="提 交"></input>'
    +'  </div>'
    +'</section>'
    +'</form>'
    +'<script>'
    +'$("#submitid").click(function(){'
    +'        if($("#topic").val().trim()==""){'
+'            $.dialog({'
+'                title:"温馨提示",'
+'                content:"请输入组织主题",'
+'                button:["确认"]'
+'            });'
+'            return;'
+'        }'
+''
+'        if($("#number").val().trim()==""){'
+'            $.dialog({'
+'                title:"温馨提示",'
+'                content:"请输入计划人数",'
+'                button:["确认"]'
+'            });'
+'            return;'
+'        }'
+'        if($("#time").val()==""){'
+'            $.dialog({'
+'                title:"温馨提示",'
+'                content:"请输入返校时间",'
+'                button:["确认"]'
+'            });'
+'            return;'
+'        }'
+'        var dia=$.dialog({'
+'            title:"温馨提示",'
+'            content:"请确认信息填写无误",'
+'            button:["确认","返回"]'
+'        });'
+'        dia.on("dialog:action",function(e){'
+'            console.log(e.index);'
+'            if(e.index == 0){'

+'                $.ajax({'
+'                    url : pathZnfx+"/mobile/serv/znfxAction!doNotNeedSessionAndSecurity_addFxjh.action?accountNum="+accountNumZnfx,'
    +'                    data : $("form").serialize(),'
+'                    dataType : "json",'
+'                    type: "post",'
+'                    success : function(result)'
+'                    {'

+'                            var dia2=$.dialog({'
+'                                title:"温馨提示",'
+'                                content:result.msg,'
+'                                button:["确认"]'
+'                            });'
+'                            var category = $("#category").val();'
+'                            dia2.on("dialog:action",function(e){'
+'                                console.log(e.index);'
+'                                if(e.index == 0){'
+'                                    window.location.href = pathZnfx+"/wkd_services/zn/index.jsp?category="+category+"&accountNum="+accountNumZnfx;'
    +'                                }'
    +'                            });'
    +'                    }'
    +'                });'
    +'            }'
    +'        });'
    +'    });'
    +'$(".more").hide();'
    +'$(".dropload-refresh").hide();'
    +'$(".dropload-update").hide();'
    +'$(".dropload-load").hide();'
    +'</script>';

//tab列表
var tabNav =    '<ul class="ui-tab-nav ui-border-b" style="margin-bottom: 10px;">'+
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
                        '<span class="time"><%=createTime%></span>'+
                        '<span class="author"><%=channelName%></span>'+
                    '</div>'+
                 '</header>'+
                 '<article><%=content%></article>';
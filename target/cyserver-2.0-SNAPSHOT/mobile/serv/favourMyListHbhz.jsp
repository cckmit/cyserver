<%@ page language="java" pageEncoding="UTF-8"  import="com.cy.util.*" %>
<%

String id = request.getParameter("id");
String category = request.getParameter("category");//category:1 互帮互助
String accountNum = request.getParameter("accountNum");
String region = request.getParameter("region");
String isWhat =  request.getParameter("isWhat");//0或空:本地，1:所有，2:我的收藏，3:我的发帖

if(WebUtil.isEmpty(isWhat))
{
	isWhat = "1";
}

String listExtensionParameters = "&cyServ.isWhat=" + isWhat + "&isWhat=" + isWhat;

//if("2".equals(isWhat) || "3".equals(isWhat))
//{
	listExtensionParameters += "&cyServ.accountNum=" + accountNum ;
//}

if(region == null)
{
	region = "";
}

%>

<!DOCTYPE html>
<html>
<head>
    <title>互帮互助</title>
    <meta name="Description" content="校友帮帮忙" />
    <meta name="Keywords" content="窗友,校友帮帮忙" />
    <meta name="author" content="Rainly" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="../css/cy_core.css">
    <link rel="stylesheet" href="../css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/appeal.css">
    <!-- 图片展示 -->
    <link rel="stylesheet" href="../css/photoswipe.css">
    <link rel="stylesheet" href="../css/default-skin/default-skin.css">
    <style>
        .labelheader_hbhz{font-size:14px;color:#4AA7DE;background: #ffffff;border-top: 1px solid #F9F9FB;height: 25px;padding:10px 0px 0px 15px;margin: 15px 0px 0px 0px;}
    </style>
</head>
<body>
<%--<footer class="ui-footer ui-footer-btn">--%>
    <%--<ul class="ui-tiled ui-border-t">--%>
    
      <%--
      <li data-href="favourList.jsp?isWhat=0&category=<%= category %>&accountNum=<%= accountNum %>&region=<%= region %>" <% if("0".equals(isWhat) || "".equals(isWhat) || isWhat == null){%>class="current"<%}%>><i class="fa fa-map-marker"></i>本地</li>
      --%>
      <%--<li data-href="favourList.jsp?isWhat=1&category=<%= category %>&accountNum=<%= accountNum %>" <% if("1".equals(isWhat)){%>class="current"<%}%>><i class="fa fa-home"></i>全部</li>--%>
      <%--<li data-href="favourMyList.jsp?isWhat=2&category=<%= category %>&accountNum=<%= accountNum %>" <% if("2".equals(isWhat)){%>class="current"<%}%>><i class="fa fa-user"></i>我的</li>--%>
      <%--<li data-href="servHelpCreate.jsp?category=<%= category %>&accountNum=<%= accountNum %>"><i class="fa fa-pencil-square-o"></i>发帖</li>--%>
    <%--</ul>--%>
<%--</footer>--%>

<section class="ui-container my-post">
    <div class="ui-tab">
        <ul class="ui-tab-nav ui-border-b">
            <li data-href="favourListHbhz.jsp?isWhat=1&category=<%= category %>&accountNum=<%= accountNum %>" <% if("1".equals(isWhat)){%>class="current"<%}%>>全部</li>
            <li data-href="favourMyListHbhz.jsp?isWhat=2&category=<%= category %>&accountNum=<%= accountNum %>" <% if("2".equals(isWhat)){%>class="current"<%}%>>我的</li>
        </ul>
        <ul class="ui-tab-content" style="width:200%">
            <li class="current">
                <div class="labelheader_hbhz">
                    <img src="../img/icon_comments.png" style="margin:0px 5px 0px 0px;"/>我的求助
                </div>
                <div class="thread-list" id="myPost" style="margin-top: 0px;">
                </div>

                <div class="labelheader_hbhz"><img src="../img/Star4.png" style="margin:0px 5px 0px 0px;"/>我的收藏</div>
                <div class="thread-list" id="myFavorites">
                </div>
                <div class="more"><a href="javascript:;" alt="myFavorites" id="favoritesMore">点击查看更多</a></div>
                <div class="ui-loading-wrap" style="display:none"><i class="ui-loading"></i><p>加载中</p></div>
            </li>
        </ul>
    </div>
</section>
<script src="../js/zepto.js"></script>
<script src="../js/cy_core_hbhz.js"></script>
<script src="../js/dropload.min.js"></script>
<script src="../js/photoswipe.min.js"></script>
<script src="../js/photoswipe-ui-default.min.js"></script>
<script src="../js/photoswipe-appeal-min.js"></script>
<script src="../js/template_appeal.js?201509141146"></script>
<script src="../js/custom_appeal.js"></script>
<script src="../js/B.js" type="text/javascript" ></script>
<script>
	var category = '<%= category %>';
	var accountNum = '<%= accountNum %>';
	var region = '<%= region %>';
	var isWhat =  '<%= isWhat %>';
	var listExtensionParameters = '<%= listExtensionParameters %>';
	var totalRows = 0;
	
	var me = null;
	
	var isMyList = true;


    var jsonStr = {
        "title":"互帮互助",
        "btn1":{
            "imgname":"icon_Back@2x.png",
            "imgversion":"20170214",
            "imgbase64":"iVBORw0KGgoAAAANSUhEUgAAABoAAAAsCAYAAAB7aah+AAAAAXNSR0IArs4c6QAAAN1JREFUWAntlkEOgkAMRRkTVl7EA3gPD+D53MjGa7lyr4vxF2gghkIHqnHxmzQdMp3+6YOkVFWw5ZxP8Cu8Di49lOtFnohiN3i8GIpKJyqCZWuxYig5JdJrdZ3thsbXrUQEJxt4PCa90kIn0tF2dBRR3K5IXC5MmkRcSsIVicuFSZOIS0l4owy+M3xuaF1SSi9vQTMP76aGy4CyTP4DZIpuNxSiGDHaBPiB2GwKdoixAJadSow2m4Kdf8N4x4X2Bfe3U2c6e2DvaJ9csTMhFi+i9xqJfU/kQ+ygzz+Pb1Kohi6oSMRTAAAAAElFTkSuQmCC",
            "name":"返回",
            "action":"fallback"
        },
        "btn2":{
            "imgname":"icon_add@2x.png",
            "imgversion":"20170214",
            "imgbase64":"iVBORw0KGgoAAAANSUhEUgAAABwAAAAcCAYAAAByDd+UAAAAAXNSR0IArs4c6QAAAF5JREFUSA1jZCAR/AcCZC2MQIDMJ8RmIqSA2vKjFlI7RBlGg3Q0SEkOgeGfaBjRy0aSw4hEDcM/SOnuQ5LqMlB0ocf5aH2InojpHoejFqJHAcX80SClOAjRDRj+QQoAhRgMMrhPDrUAAAAASUVORK5CYII=",
            "name":"添加",
            "action":"added"
        },
        "btn3":{
            "imgname":"icon_more_detail@2x.png",
            "imgversion":"20170214",
            "imgbase64":"iVBORw0KGgoAAAANSUhEUgAAAAQAAAASCAYAAAB8fn/4AAAAAXNSR0IArs4c6QAAADBJREFUGBlj+P//vycQP4ZiTwYoA0iBwWMmBnQAFEfVgq6ACD6GGUABkBtgYPBaCwAdi4T3K1E4vwAAAABJRU5ErkJggg==",
            "name":"",
            "droplist":[
                {
                    "name":"分享",
                    "action":"share"
                },
                {
                    "name":"复制",
                    "action":"copy"
                },
                {
                    "name":"删除",
                    "action":"deleted"
                }
            ]
        }
    }
    function menuConfig() {
        window.webkit.messageHandlers.AppModel.postMessage({body: jsonStr});
        return jsonStr;

    }
    function jsonConfig() {
        var str = JSON.stringify(jsonStr);
        window.stub.jsMethod(str);
        return jsonStr;

    }
    function fallback() {

        window.location.href = B.serverUrl + "/mobile/services/index_body.html?accountNum=" + accountNum;

    }

    var isWhat = 2;
    function added() {

        window.location.href = B.serverUrl + "/mobile/serv/servHelpCreateHbhz.jsp?accountNum=" + accountNum + "&category=" +category+ "&isWhat=" + isWhat;

    }
    function share() {
        alert("测试而已");
    }
    function copy() {
        alert("测试而已");
    }
    function deleted() {
        alert("测试而已");
    }


    //页面加载
    getPageData("mobServAction!doNotNeedSessionAndSecurity_getServList.action?cyServ.category=<%=category%>&cyServ.viewType=favorite<%= listExtensionParameters %>&cyServ.region=<%=region%>","#myFavorites","onload",appealTplXmhz2,null,"loadPhotoSwipe");
    getPageData("mobServAction!doNotNeedSessionAndSecurity_getServList.action?cyServ.category=<%=category%><%= listExtensionParameters %>&cyServ.region=<%=region%>","#myPost","onload",appealTplHbhz2,null,"loadPhotoSwipe");
    //查看更多
    $(document).on('tap','.more',function(){
    
    	
        var target = $(this);
        var altType = target.children('a').attr('alt');

        console.log(totalRows);

        var temUrl1 = "mobServAction!doNotNeedSessionAndSecurity_getServList.action?cyServ.category=<%=category%>&cyServ.viewType=favorite<%= listExtensionParameters %>&cyServ.region=<%=region%>&cyServ.currentRow=" + $("#myFavorites > li").length;

        var temUrl2 = "mobServAction!doNotNeedSessionAndSecurity_getServList.action?cyServ.category=<%=category%><%= listExtensionParameters %>&cyServ.region=<%=region%>&cyServ.currentRow=" + $("#myPost > li").length;

        //console.log(temUrl);
        $.ajax({
            type: 'GET',
            url: temUrl1,
            dataType: 'json',
            beforeSend: function(xhr, settings) {
                target.hide();
                target.next().show();
                //console.log(target.next())
            },
            success: function(data){
                if(data.servList.length == 0){
                    $('.more >a').text("没有更多了");
                    return;
                }
                var result=$.tpl(appealTplHbhz2,data);
                $(target.prev()).append(result);
            },
            error: function(xhr, type){
                alert('Ajax error!');
            },
            complete: function(xhr, type){
                target.show();
                target.next().hide();
                initPhotoSwipeFromDOM('.ui-list-pic');
            }
        });

        $.ajax({
            type: 'GET',
            url: temUrl2,
            dataType: 'json',
            beforeSend: function(xhr, settings) {
                target.hide();
                target.next().show();
                //console.log(target.next())
            },
            success: function(data){
                var result=$.tpl(appealTplHbhz2,data);
                $(target.prev()).append(result);
            },
            error: function(xhr, type){
                alert('Ajax error!');
            },
            complete: function(xhr, type){
                target.show();
                target.next().hide();
                initPhotoSwipeFromDOM('.ui-list-pic');
            }
        });
    });

    (function (){
        var tab = new fz.Scroll('.ui-tab', {
            role: 'tab',
            autoplay: false
        });
        /* 滑动开始前 */
        tab.on('scrollEnd', function(curPage) {
            if($('#myPost').children().length == 0) {
                getPageData("mobServAction!doNotNeedSessionAndSecurity_getServList.action?cyServ.category=<%=category%><%= listExtensionParameters %>&cyServ.region=<%=region%>","#myPost","",appealTplHbhz2,null,"loadPhotoSwipe");
            }
            //console.log(fromIndex,toIndex);// from 为当前页，to 为下一页
        });
    })();
    
    
    
    $(document).on('tap','.show-bot-menu',function(){
  	
	  	var accountNumStr = $(this).attr("id");
	  	var idStr = $(this).attr("rel");
	  	$('.ui-actionsheet .ui-actionsheet-complaint').attr("rel",idStr);
	  	$('.ui-actionsheet .ui-actionsheet-del').attr("rel",idStr);
	  	
	  	if(accountNumStr == accountNum)//如果是自己
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

    $(document).on('click','.ui-tab-nav li',function(){
        if($(this).data('href')){
            location.href= $(this).data('href');
        }
    });
</script>
</body>
</html>
<%@ page language="java" pageEncoding="UTF-8"  import="com.cy.util.*" %>
<%

  String category = request.getParameter("category");//category:2 项目合作
  String accountNum = request.getParameter("accountNum");
  String region = request.getParameter("region");
  String isWhat =  request.getParameter("isWhat");//0或空:本地，1:所有，2:我的收藏，3:我的发帖

  String title = "" ;

  if("1".equals(category)) {
    title = "互帮互助" ;
  } else if("2".equals(category)) {
    title = "项目合作" ;
  } else if("3".equals(category)) {
    title = "求职招聘" ;
  }

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


boolean isOpen = false;


%>

<!DOCTYPE html>
<html>
<head>
  <title><%=title%></title>
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

    .post-action .favorites ,.post-action .comment {
      color: #464646;background-color: #ffffff;
    }
  </style>
</head>
<body>
<%--<footer class="ui-footer ui-footer-btn">--%>
  <%--<ul class="ui-tiled ui-border-t">--%>
  	<%--
    <li data-href="favourList.jsp?isWhat=0&category=<%= category %>&accountNum=<%= accountNum %>&region=<%= region %>" <% if("0".equals(isWhat) || "".equals(isWhat) || isWhat == null){%>class="current"<%}%>><i class="fa fa-map-marker"></i>本地</li>
    --%>
    <%--<li data-href="favourListXmhz.jsp?isWhat=1&category=<%= category %>&accountNum=<%= accountNum %>" <% if("1".equals(isWhat)){%>class="current"<%}%>><i class="fa fa-home"></i>全部</li>--%>
    <%--<li data-href="favourMyListXmhz.jsp?isWhat=2&category=<%= category %>&accountNum=<%= accountNum %>" <% if("2".equals(isWhat)){%>class="current"<%}%>><i class="fa fa-user"></i>我的</li>--%>
  	<%--<li data-href="servHelpCreate.jsp?category=<%= category %>&accountNum=<%= accountNum %>"><i class="fa fa-pencil-square-o"></i>发帖</li>--%>
  <%--</ul>--%>
<%--</footer>--%>
<%--##############################################################################################################################drx start######################################--%>
<section class="ui-container my-post">
  <div class="ui-tab">
    <ul class="ui-tab-nav ui-border-b">
      <li data-href="favourListXmhz.jsp?isWhat=1&category=<%= category %>&accountNum=<%= accountNum %>" <% if("1".equals(isWhat)){%>class="current"<%}%>>全部</li>
      <li data-href="favourMyListXmhz.jsp?isWhat=2&category=<%= category %>&accountNum=<%= accountNum %>" <% if("2".equals(isWhat)){%>class="current"<%}%>>我的</li>
    </ul>

  </div>
</section>
<%--##################################################################################################################################drx end######################################--%>
<section class="ui-container<% if("1".equals(isWhat)){ %> all-post<%} %>">
  <div class="wrapper" style="bottom: 0px;">
    <div class="inner">
      <div style="height: 45px;"></div>
      <div class="thread-list">
      </div>
    </div>
  </div>
  
  <% if("1".equals(isWhat) && isOpen){ %>
  <div class="custom ui-border-b" data-opened="false"><i class="fa fa-bars"></i> 筛选</div>
  <%} %>
  
</section>
<script src="../js/zepto.js"></script>
<script src="../js/cy_core_xmhz.js"></script>
<script src="../js/dropload.min.js"></script>
<script src="../js/photoswipe.min.js"></script>
<script src="../js/photoswipe-ui-default.min.js"></script>
<script src="../js/photoswipe-appeal-min.js"></script>
<script src="../js/template_appeal.js?201509141146"></script>
<script src="../js/custom_appeal.js"></script>
<script src="../js/B.js" type="text/javascript"></script>
<script>

	var category = '<%= category %>';
	var accountNum = '<%= accountNum %>';
	var region = '<%= region %>';
	var isWhat =  '<%= isWhat %>';
	var listExtensionParameters = '<%= listExtensionParameters %>';
	var totalRows = 0;
	
	var isMyList = false;

    var jsonStr = {
      "title":"项目合作",
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
    var accountNum = '<%= accountNum %>';
    function fallback() {

      window.location.href = B.serverUrl + "/mobile/services/index_body.html?accountNum=" + accountNum;

    }
    function added() {

      window.location.href = B.serverUrl + "/mobile/serv/servHelpCreateXmhz.jsp?accountNum=" + accountNum + "&category=" +category+ "&isWhat=" + isWhat;
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


    Zepto(function($){

  	 //页面加载
    getPageData("mobServAction!doNotNeedSessionAndSecurity_getServList.action?cyServ.category=<%=category%><%= listExtensionParameters %>&cyServ.region=<%=region%>",".thread-list","onload",appealTplXmhz,null,"loadPhotoSwipe");
  
  
  
    //下拉刷新
    var dropload = $('.inner').dropload({
      domUp : {
        domClass   : 'dropload-up',
        domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-down"></i>下拉刷新</div>',
        domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-up"></i> 释放更新</div>',
        domLoad    : '<div class="dropload-load"><div class="ui-loading-wrap"><i class="ui-loading"></i><p>加载中</p></div></div>'
      },
      domDown : {
        domClass   : 'dropload-down',
        domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-up"></i> 上拉加载更多</div>',
        domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-down"></i> 释放加载</div>',
        domLoad    : '<div class="dropload-load"><div class="ui-loading-wrap"><i class="ui-loading"></i><p>加载中</p></div></div>'
      },
      loadUpFn : function(me){
        getPageData("mobServAction!doNotNeedSessionAndSecurity_getServList.action?cyServ.category=<%=category%><%= listExtensionParameters %>&cyServ.region=<%=region%>&cyServ.currentRow=0&cyServ.incremental=" + $(".thread-list > li").length,".thread-list","update",appealTplXmhz,me);
      },
      loadDownFn : function(me){
      	if($(".thread-list > li").length >= totalRows)
  		{
  			if (me != null) {
  				
				me.resetload();
			}
  			$(".inner").append('<div class="dropload-down" style="-webkit-transition: all 300ms; transition: all 300ms; height: 40px;"><div class="dropload-load"><div class="ui-loading-wrap">没有更多了</div></div></div>');
  			
  			
  		}
  		else
  		{
  			getPageData("mobServAction!doNotNeedSessionAndSecurity_getServList.action?cyServ.category=<%=category%><%= listExtensionParameters %>&cyServ.region=<%=region%>&cyServ.currentRow=" + $(".thread-list > li").length,".thread-list","more",appealTplXmhz,me);
  		}
      }
    });

   
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
  
  
  	<% if("1".equals(isWhat) && isOpen){ %>
  	//筛选
    getPageData("mobServAction!doNotNeedSessionAndSecurity_getMobProvinceAndCapital.action","body","",chooseCityTpl);
    $('.custom').click(function(){
      //console.log($(this).data('opened'));
      if(!$(this).data('opened')){
        $(".choose-city").show();
        $(".thread-list").hide();
        $(this).html('<i class="fa fa-times"></i> 关闭');
        $(this).data('opened',true);
      } else {
        $(".choose-city").hide();
        $(".thread-list").show();
        if($('.choose-city .checked').text() == '全部'){
          $('.custom').html('<i class="fa fa-bars"></i> 筛选');
        } else {
          $('.custom').html('<i class="fa fa-bars"></i> '+$('.choose-city .checked').text());
        }
        $(this).data('opened',false);
      }
    });
    
    $(document).on('tap','.choose-city .ui-label',function(){
      $('.choose-city .ui-label').removeClass("checked");
      $(this).addClass("checked");
      var city = $(this).data('href');//city 为带市字的城市全名
      var city_str = $(this).text();//city_str 为不带市字的城市名
      $(".choose-city").hide();
      $(".thread-list").show();
      if(city_str == '全部') {
        $('.custom').html('<i class="fa fa-bars"></i> 筛选');
      } else {
        $('.custom').html('<i class="fa fa-bars"></i> '+city_str);
      }
      $('.custom').data('href',city);
      $('.custom').data('opened',false);
      
      
      
      getPageData("mobServAction!doNotNeedSessionAndSecurity_getServList.action?cyServ.category=<%=category%><%= listExtensionParameters %>&cyServ.region=" + city,".thread-list","onload",appealTplXmhz,null,"loadPhotoSwipe");
  
    });
    
    
  	<%} %>

  });


    $(document).on('tap','.ui-tab-nav li',function(){
      if($(this).data('href')){
        location.href= $(this).data('href');
      }
    });
</script>
</body>
</html>
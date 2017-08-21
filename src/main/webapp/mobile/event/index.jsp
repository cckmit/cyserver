<%@ page language="java" pageEncoding="UTF-8"  import="com.cy.util.*" %>
<%	

String userInfoId = request.getParameter("accountNum");

String listType = request.getParameter("listType")==null?"2":request.getParameter("listType");

%>



<!DOCTYPE HTML>
<html>
  <head>
    <meta charset="UTF-8">
    <title>活动</title>
    <meta name="Description" content="窗友活动" />
    <meta name="Keywords" content="窗友,活动,讲座,聚会,班会,交友" />
    <meta name="author" content="Rainly" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    
    <link rel="stylesheet" type="text/css" href="../css/global.css">
    <link rel="stylesheet" type="text/css" href="../css/events-addon.css">
    <link rel="stylesheet" type="text/css" href="../css/events.css">
    <link rel="stylesheet" type="text/css" href="../css/font-awesome.min.css">
  </head>
  <body>
  <div class="wrapper no-header">
    <div class="inner">
      <ul class="ui-list ui-border-tb">
      </ul>
    </div>
  </div>
  <%-- 只在全部列表下显示刷选 --%>
  <%if(listType.equals("2")){%>
  <!-- <div class="custom ui-border-b" data-opened="false" data-href="全部"><i class="fa fa-bars"></i> 筛选</div> -->
  <%} %>
  <div class="footer">
    <ul class="footer-menu">
    	<%-- <li class="local<%//if(listType.equals("5")){ %> current<%//}%>"><a href="index.jsp?accountNum=<%=//userInfoId%>&listType=5"><i class="fa fa-map-marker"></i>本地</a></li> --%>
        <li class="all<%if(listType.equals("2")){ %> current<%}%>"><a href="index.jsp?accountNum=<%=userInfoId%>"><i class="fa fa-home"></i>全部</a></li>
        <li class="official<%if(listType.equals("0")){ %> current<%}%>"><a href="index.jsp?accountNum=<%=userInfoId%>&listType=0"><i class="fa fa-fire"></i>官方</a></li>
        <li class="mine"><a href="myEventsList.jsp?accountNum=<%=userInfoId%>"><i class="fa fa-user"></i>我的</a></li>
        
        <%--
        
        <li class="create"><a href="create.jsp?accountNum=<%=userInfoId%>" class="special-btn"><i class="fa fa-flag"></i>创建</a></li>
        
        --%>
      </ul>
  </div>
  
  <script src="../js/zepto.min.js" type="text/javascript"></script>
  <script src="../js/zepto.selector.min.js" type="text/javascript"></script>
  <script src="../js/global.js" type="text/javascript"></script>
  <script src="../js/dropload.min.js" type="text/javascript"></script>
  <script src="../js/template.js" type="text/javascript"></script>
  
  
  <script type="text/javascript">
// dropload
Zepto(function($){

	$.getJSON("mobEventAction!doNotNeedSessionAndSecurity_getNumOfNotifyForMyEvents.action?userInfoId=<%=userInfoId%>", function(items){
		if(items != null){
			
			if(items['joinedPeople'] == 0){
				$( ".mine i" ).removeClass( "ui-reddot" );
			}else{
				$( ".mine i" ).addClass( "ui-reddot" );
			}
			
		}else{
			$( ".mine i" ).removeClass( "ui-reddot" );
		}
    	
    });
	
    //页面加载
    
    getData("mobEventAction!doNotNeedSessionAndSecurity_pullEventList.action?eventData.type=<%=listType%>&eventData.userInfoId=<%=userInfoId%>",".ui-list","onload",indexTpl);

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
            var city_str = $('.custom').data('href');//city为传的城市名参数
            //console.log(city);
            
            if(city_str!=null){
            	//alert(city_str);
            	var tmpUrl = "mobEventAction!doNotNeedSessionAndSecurity_pullEventList.action?eventData.type=<%=listType%>&eventData.userInfoId=<%=userInfoId%>&eventData.currentRow=0&eventData.region="+city_str;
            	getData(tmpUrl,".ui-list","update",indexTpl,me);
                
            }else{
            	getData("mobEventAction!doNotNeedSessionAndSecurity_pullEventList.action?eventData.type=<%=listType%>&eventData.userInfoId=<%=userInfoId%>&eventData.currentRow=0",".ui-list","update",indexTpl,me);
            }
            
        },
        loadDownFn : function(me){
            var city_str = $('.custom').data('href');
            
            if(city_str!=null){
            	//alert(city_str);
            	var tmpUrl = "mobEventAction!doNotNeedSessionAndSecurity_pullEventList.action?eventData.type=<%=listType%>&eventData.userInfoId=<%=userInfoId%>&eventData.currentRow=" + $('.ui-list > li').length;
            	tmpUrl = tmpUrl + "&eventData.region="+city_str;
                getData(tmpUrl,".ui-list","more",indexTpl,me);
                
            }else{
            	getData("mobEventAction!doNotNeedSessionAndSecurity_pullEventList.action?eventData.type=<%=listType%>&eventData.userInfoId=<%=userInfoId%>&eventData.currentRow=" + $('.ui-list > li').length,".ui-list","more",indexTpl,me);
            }

        }
        
       
    });
    
    <%if(listType.equals("2")){%>
    
    //筛选
    //$("body").append(chooseCityTpl);
    getData("${pageContext.request.contextPath}/mobile/serv/mobServAction!doNotNeedSessionAndSecurity_getMobProvinceAndCapital.action","body","",chooseCityTpl);
    $('.custom').click(function(){
        //console.log($(this).data('opened'));
        if(!$(this).data('opened')){
            $(".choose-city").show();
            $(".ui-list").hide();
            $(this).html('<i class="fa fa-times"></i> 关闭');
            $(this).data('opened',true);
        } else {
            $(".choose-city").hide();
            $(".ui-list").show();
            if($('.choose-city .checked').text() == '全部'){
                $('.custom').html('<i class="fa fa-bars"></i> 筛选');
            } else {
                $('.custom').html('<i class="fa fa-bars"></i> '+$('.choose-city .checked').text());
            }
            $(this).data('opened',false);
        }
    });
    
    $(document).on('click','.choose-city .ui-label',function(){
    	
        $('.choose-city .ui-label').removeClass("checked");
        $(this).addClass("checked");
        var city = $(this).data('href');//city 为带市字的城市全名
        var city_str = $(this).text();//city_str 为不带市字的城市名
        $(".choose-city").hide();
        $(".ui-list").show();
        if(city_str == '全部') {
            $('.custom').html('<i class="fa fa-bars"></i> 筛选');
        } else {
            $('.custom').html('<i class="fa fa-bars"></i> '+city_str);
        }
        $('.custom').data('href',city);
        $('.custom').data('opened',false);
        getData("mobEventAction!doNotNeedSessionAndSecurity_pullEventList.action?eventData.type=<%=listType%>&eventData.userInfoId=<%=userInfoId%>&eventData.currentRow=0&eventData.region="+city_str,".ui-list","update",indexTpl);
    });

    <%}%>
});
</script>
  </body>
</html>
<%@ page language="java" import="java.util.*,com.cy.core.messageboard.entity.*,com.cy.core.messageboard.service.*,com.cy.util.*" pageEncoding="UTF-8"%>
<%//response.setHeader("Pragma","No-cache");response.setHeader("Cache-Control","no-cache");response.setDateHeader("Expires", 0);%>
<%String userId = request.getParameter("userId"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title>我们的服务</title>
<link rel="stylesheet" type="text/css" href="../css/pure-nr.css">
<link rel="stylesheet" type="text/css" href="../css/service.css">
<link rel="stylesheet" type="text/css" href="../css/font-awesome.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/bxslider.js"> </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/public.js"> </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/schoolServ.js"> </script>
<script type="text/javascript">$(document).ready(function(){initSchoolServ();});</script>
</head>
<body>
<input type="hidden" id="userId" value="<%= userId %>">
<div id="mainboard">

<!-- 
  <div class="bxslider-wrapper">
    <div class="bxslider">
      <div>
        <a href=""><img src="temp/1.jpg" alt="img" width="100%" height="200" /></a>
        <blockquote class="slider-caption right-caption">
          <p class="slider-title">围棋比赛开始报名！</p>
        </blockquote>
      </div>
      <div>
        <a href=""><img src="temp/2.jpg" alt="img" width="100%" height="200"/></a>
        <blockquote class="slider-caption right-caption">
          <p class="slider-title">围棋比赛开始报名！</p>
        </blockquote>
      </div>
      <div>
        <a href=""><img src="temp/3.jpg" alt="img" width="100%" height="200"/></a>
        <blockquote class="slider-caption right-caption">
          <p class="slider-title">围棋比赛开始报名！</p>
        </blockquote>
      </div>
    </div>
  </div>
-->

	<div class="top-pic">
    <img src="../img/schoolServLogoz.jpg" alt="img" width="100%" height="200" />
  </div>

  <div class="service-list">
    <ul class="pure-g" id="servList">
    
    <!-- 
      <li class="pure-u-1-4">
      <a href="">
          <img src="img/space.png" width="48" height="48" alt="">
          <h4>校友注册</h4>
      </a>
      </li>
     --> 
      
      
    </ul>
  </div>
</div>


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
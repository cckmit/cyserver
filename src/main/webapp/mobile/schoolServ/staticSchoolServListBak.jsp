<%@ page language="java" import="java.util.*,com.cy.core.messageboard.entity.*,com.cy.core.messageboard.service.*,com.cy.util.*" pageEncoding="UTF-8"%>
<%//response.setHeader("Pragma","No-cache");response.setHeader("Cache-Control","no-cache");response.setDateHeader("Expires", 0);%>
<%String userId = request.getParameter("userid"); %>
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
  <style>
    body{background-color:#EFEFF4;}
    #servtable1,#servtable2,#servtable3{
      width:100%;
      margin:18px 0px 22px 0px;
      background-color:#ffffff;
      border-collapse: collapse;
      border: 0px solid #EFEFF4;
    }
    #servtable2 tr td,#servtable3 tr td{padding-left: 25px;}
    #servtable1 td,#servtable2 td,#servtable3 td{
      text-align: left;
      vertical-align: middle;
      border-top: 0;
      border-right: 1px solid #EFEFF4;
      border-bottom: 1px solid #EFEFF4;
      border-left: 0;
    }
    #servtable1 td{height:70px;}
    #servtable2 td,#servtable3 td{height:46px;padding-left:0px;background:url(../../images/icon_chevron_right.png) no-repeat 90% 15px;}
    #servtable1 a,#servtable2 a,#servtable3 a{
      display: block;
      vertical-align: middle;
      text-decoration:none;
    }
    #servtable1 a{height:66px;padding-top:15px;}
    #servtable2 a,#servtable3 a{height:41px;padding-top:10px;}
    #servtable1 a:link,#servtable2 a:link,#servtable3 a:link{ color:#5E5E5E;text-decoration:none;}
    #servtable1 a:visited,#servtable2 a:visited,#servtable3 a:visited{color:#5E5E5E;text-decoration:none;}
    #servtable1 a:hover,#servtable2 a:hover,#servtable3 a:hover{color:#5E5E5E;text-decoration:none;cursor:hand;}
    #servtable1 a:active,#servtable2 a:active,#servtable3 a:active{color:#5E5E5E;text-decoration:none;}
    #servtable1 td span,#servtable2 td span,#servtable3 td span{display: inline-block;vertical-align: middle;padding-bottom: 10px;}
    #servtable1 td img{margin-left: 25px;margin-right: 20px;}
    #servtable2 td img,#servtable3 td img{margin-right: 20px;}

    .servdiv1{background-color:#ffffff;border-width: 1px 0px 1px 0px;border-color:#E2E2E3;}

  </style>
  <script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/jquery-1.11.1.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/bxslider.js"> </script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/public.js"> </script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/schoolServ.js"> </script>
  <script type="text/javascript">$(document).ready(function(){initSchoolServNew();});</script>
</head>
<body>
<input type="hidden" id="userId" value="<%= userId %>">
<div id="mainboard">
  <table id="servtable1">
    <tr style="display: none;"><td></td><td></td></tr>
  </table>

  <div class="servdiv1">
    <table id="servtable2">
      <tr style="display: none;"><td></td></tr>
    </table>
  </div>

  <div class="servdiv1">
    <table id="servtable3">
      <tr style="display: none;"><td></td></tr>
    </table>
  </div>

</div>


<script>
  $(function(){
    $('#servtable2 tr:last').find('td').css({"border-width":"0px"});
    $('#servtable3 tr:last').find('td').css({"border-width":"0px"});
  });


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
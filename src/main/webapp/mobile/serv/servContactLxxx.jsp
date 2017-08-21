<!DOCTYPE html>
<html>

<%@ page language="java" pageEncoding="UTF-8"  import="com.cy.util.*" %>
<%

String accountNum = request.getParameter("accountNum");
String category = request.getParameter("category");
//String region = request.getParameter("region");
//String isAll =  request.getParameter("isAll");

%>


<head>
  <title>联系学校</title>
  <meta name="Description" content="联系校友会" />
  <meta name="Keywords" content="窗友,联系校友会" />
  <meta name="author" content="Rainly" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="format-detection" content="telephone=no">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <link rel="stylesheet" href="../css/cy_core.css">
  <link rel="stylesheet" href="../css/font-awesome.min.css">
  <link rel="stylesheet" href="../css/contact.css">
</head>
<body>
<footer class="ui-footer ui-footer-btn">
  <ul class="ui-tiled ui-border-t">
  	<li data-href="servContactCreate.jsp?category=<%= category %>&accountNum=<%= accountNum %>"><i class="fa fa-pencil-square-o"></i>发帖</li>
  </ul>
</footer>

<section class="ui-container">
  <ul class="contact-list" id="id-contact-list">
  </ul>
  <div class="more"><a href="javascript:">点击查看更多</a></div>
  <div class="ui-loading-wrap" style="display:none"><i class="ui-loading"></i><p>加载中</p></div>
</section>
<script src="../js/zepto.js"></script>
<script src="../js/cy_core.js"></script>
<script src="../js/template_contact.js"></script>
<script>
  Zepto(function($){
    //页面加载
    getPageData("mobServAction!doNotNeedSessionAndSecurity_getContactList.action?cyContact.accountNum=<%=accountNum%>&cyContact.category=<%=category%>",".contact-list","onload",contactListTpl);

    //查看更多
    $(document).on('click','.more',function(){
      var target = $(this);
      
      var tmpUrl = 'mobServAction!doNotNeedSessionAndSecurity_getContactList.action?cyContact.accountNum=<%=accountNum%>&cyContact.category=<%=category%>&cyContact.currentRow=' + $("#id-contact-list > li").length;
      
      $.ajax({
        type: 'GET',
        url: tmpUrl,
        dataType: 'json',
        beforeSend: function(xhr, settings) {
          target.hide();
          target.next().show();
          //console.log(target.next())
        },
        success: function(data){
          var result=$.tpl(contactListTpl,data);
          $(target.prev()).append(result);
        },
        error: function(xhr, type){
          //console.log('Ajax error!');
        },
        complete: function(xhr, type){
          target.show();
          target.next().hide();
        }
      });
    });
    
    $(document).on('tap','.ui-footer-btn li',function(){
        if($(this).data('href')){
            location.href= $(this).data('href');
        }
    });
  });
</script>
</body>
</html>
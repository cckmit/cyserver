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
<script src="../js/B.js"></script>
<script>

  var jsonStr = {
    "title":"联系学校",
    "btn1":{
      "imgname":"icon_Back@2x.png",
      "imgversion":"20170214",
      "imgbase64":"iVBORw0KGgoAAAANSUhEUgAAABoAAAAsCAYAAAB7aah+AAAAAXNSR0IArs4c6QAAAN1JREFUWAntlkEOgkAMRRkTVl7EA3gPD+D53MjGa7lyr4vxF2gghkIHqnHxmzQdMp3+6YOkVFWw5ZxP8Cu8Di49lOtFnohiN3i8GIpKJyqCZWuxYig5JdJrdZ3thsbXrUQEJxt4PCa90kIn0tF2dBRR3K5IXC5MmkRcSsIVicuFSZOIS0l4owy+M3xuaF1SSi9vQTMP76aGy4CyTP4DZIpuNxSiGDHaBPiB2GwKdoixAJadSow2m4Kdf8N4x4X2Bfe3U2c6e2DvaJ9csTMhFi+i9xqJfU/kQ+ygzz+Pb1Kohi6oSMRTAAAAAElFTkSuQmCC",
      "name":"返回",
      "action":"fallback"
    }/*,
     "btn2":{
     "imgname":"icon_add@2x.png",
     "imgversion":"20170214",
     "imgbase64":"iVBORw0KGgoAAAANSUhEUgAAADgAAAA4CAYAAACohjseAAAAAXNSR0IArs4c6QAAAMRJREFUaAXt2UsKwzAAA9E4979zPhfwLIxhMJOtaKroGQrNuDZfz3fNvmJ81yxfze7VG9g/3wPahahfgrSQPU/QLkT9EqSF7HmCdiHqlyAtZM8TtAtRvwRpIXueoF2I+iVIC9nzBO1C1G/Q/5Z0A3veEbULUb8EaSF7nqBdiPodL7j13dy/Lv3O9n6QziDkxx/RHhBOgD5OUE8EBROEgfRxgnoiKJggDKSPE9QTQcEEYSB9nKCeCAomCAPp4wT1RFDweMEXDEkMapUbW8YAAAAASUVORK5CYII=",
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
     }*/
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
          if(data.lists.length == 0){
            $('.more >a').text("没有更多了");
            return;
          }
          var result=$.tpl(contactListTpl,data);
          $(target.prev()).append(result);
        },
        error: function(xhr, type){
          $('.more >a').text("没有更多了");
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
            location.href= $(this).data('href') + "&accountNum=<%=accountNum%>&category=<%=category%>";
        }
    });
  });
</script>
</body>
</html>
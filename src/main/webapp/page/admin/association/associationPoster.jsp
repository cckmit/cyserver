<%@ page language="java" import="java.util.*,com.cy.core.news.entity.*,com.cy.core.majormng.service.*,com.cy.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cy.core.association.entity.Association" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%//response.setHeader("Pragma","No-cache");response.setHeader("Cache-Control","no-cache");response.setDateHeader("Expires", 0);%>

<%

%>

<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
    <meta name="format-detection"content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <title>社团海报</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/mobile/css/pure-nr.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/mobile/css/main_news.css">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        $(function () {
            var id = '${param.id}';
            if(id == undefined || id == "" || id ==null){
                id = "";
            }
            $.ajax({
                url : '${pageContext.request.contextPath}/association/associationAction!doNotNeedSessionAndSecurity_getPoster.action',
                data: {"association.id":id},
                dataType : 'json',
                success : function(result)
                {
                    $('.introduction').append(result);
                },
                beforeSend : function()
                {
                   $.messager.progress({
                        text : '数据提交中....'
                    });
                },
                complete : function()
                {
                    $.messager.progress('close');
                }
            });
        });
    </script>
</head>
<body>
<div id="bd">
    <div class="article">
        <div class="introduction">

        </div>
    </div>
</div>
<div id="ft">
    <p class="copyright">Copyright © 2014 - 2016 窗友科技. All Rights Reserved</p>
</div>
</body>
</html>
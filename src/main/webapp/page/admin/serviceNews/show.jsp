<%@ page language="java" import="java.util.*,com.cy.core.news.entity.*,com.cy.core.majormng.service.*,com.cy.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%//response.setHeader("Pragma","No-cache");response.setHeader("Cache-Control","no-cache");response.setDateHeader("Expires", 0);%>

<%
List<News> newslist = (List<News>)request.getAttribute("newslist");

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
  <title>窗友新闻</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/mobile/css/pure-nr.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/mobile/css/main_news.css">
  
</head>
<body>
<div id="bd">
  <div class="article">
    <h2><c:out value="${serviceNews.title}"></c:out></h2>
    <div class="meta">
      <span class="time"><fmt:formatDate value="${serviceNews.createDate}" pattern="yyyy-MM-dd"></fmt:formatDate></span>
      <!-- <span class="author">总会速递</span> -->
    </div>
    <div class="content">
      ${serviceNews.content}
    </div>
  </div>
  <c:if test="${newslist != null && newslist.size() > 0}">
  <div class="related">
    <div class="head"><h2>相关内容</h2></div>
    
    <div class="content">
      <ul class="relatedlist">
	    <c:forEach items="${newslist}" var="tmpNews">
		   <li><a href="${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getMobNew.action?id=${tmpNews.newsId}"><c:out value="${tmpNews.title}"></c:out></a></li>
		</c:forEach>
      </ul>
    </div>
    
  </div>
  </c:if>
</div>
<div id="ft">
  <p class="copyright">Copyright © 2014 - 2016 窗友科技. All Rights Reserved</p>
</div>
</body>
</html>
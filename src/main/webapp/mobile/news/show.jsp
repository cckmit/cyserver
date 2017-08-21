<%@ page language="java" import="java.util.*,com.cy.core.news.entity.*,com.cy.core.majormng.service.*,com.cy.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cy.system.Global" %>
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
  <title><c:out value="${news.title}"></c:out></title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/mobile/css/pure-nr.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/mobile/css/main_news.css">
  <script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/H.js" ></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/xdomain.min.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/B.js" ></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/zepto.min.js" ></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/jbase64.js" ></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/mobile/js/wechat.js" ></script>
  <script type="text/javascript">
      var shareData = {
          title: '${news.title}',
          desc: '${news.introduction}',
          link: location.href.split('?')[0]+'?id='+'${news.newsId}',
          imgUrl: '${news.pic}'
      };
      mkShareInfo(location.href.split('#')[0], shareData, 0);
  </script>
</head>
<body>
<div id="bd">
  <div class="article">
    <h2><c:out value="${news.title}"></c:out>${mynews.title}</h2>
    <div class="meta">
      <span class="time">${news.createDate}${mynews.createDate}</span>
      <!-- <span class="author">总会速递</span> -->
    </div>
    <div class="content">
      ${news.content}
      ${mynews.content}
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
  <p class="copyright">Copyright © 2014 - 2017 <%=Global.schoolSign%>版权所有. All Rights Reserved </p>
  <p>武汉窗友科技有限公司提供技术支持</p>
</div>
</body>
</html>
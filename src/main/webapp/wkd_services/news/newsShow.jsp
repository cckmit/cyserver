<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@page import="com.cy.core.news.service.NewsService"%>
<%@page import="com.cy.system.SpringManager"%>
<%@page import="com.cy.core.news.entity.News"%>
<%@page import="com.cy.core.news.entity.NewsType"%>
<%@ page import="javax.sound.sampled.SourceDataLine" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    long newsId = WebUtil.toLong(request.getParameter("newsId"));
    long categoryId = WebUtil.toLong(request.getParameter("category"));
    int typeId = WebUtil.toInt(request.getParameter("type"));

    if(newsId==0 && categoryId!=0){
        NewsService newsSer = SpringManager.getBean("newsService", NewsService.class);

        NewsType tmpNewsType = new NewsType();
        tmpNewsType.setId(categoryId);

        News tmpNews = newsSer.selectWebNewFromWebType(tmpNewsType);
        if(tmpNews!=null){
            newsId = tmpNews.getNewsId();
        }

    }

    String accountNum = request.getParameter("accountNum");
    String category = request.getParameter("category");
    String sourceFlag = request.getParameter("sourceFlag");

    String title = "";

    if ("zlcx".equals(sourceFlag)) {
        title = "{\"title\":\"详细\",\"backUrl\":\"/wkd_services/zlcx/index.jsp\",\"backTitle\":\"返回\"}";
    }
    else if ("dacx".equals(sourceFlag)) {
        title = "{\"title\":\"详细\",\"backUrl\":\"/wkd_services/dacx/index.jsp\",\"backTitle\":\"返回\"}";
    }
    else if ("zsbb".equals(sourceFlag)) {
        title = "{\"title\":\"详细\",\"backUrl\":\"/wkd_services/zsbb/index.jsp\",\"backTitle\":\"返回\"}";
    }
    else if ("jrfw".equals(sourceFlag)) {
        title = "{\"title\":\"详细\",\"backUrl\":\"/wkd_services/jrfw/index.jsp\",\"backTitle\":\"返回\"}";
    }
    else if ("zn".equals(sourceFlag)) {
        title = "{\"title\":\"详细\",\"backUrl\":\"/wkd_services/zn/index.jsp?accountNum=" + accountNum + "&category=" + category + "\",\"backTitle\":\"返回\"}";
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title><%=title%></title>
    <meta name="Description" content="" />
    <meta name="Keywords" content="窗友" />
    <meta name="author" content="Rainly" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="<%=path %>/mobile/css/cy_core.css">
    <link rel="stylesheet" href="<%=path %>/mobile/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=path %>/mobile/css/news.css">
</head>
<body>
<section class="ui-container news-show">
    <div class="article-show">

    </div>
    <!--<div class="related">-->
        <!--<div class="head"><h2>相关内容</h2></div>-->
        <!--<div class="content">-->
            <!--<ul class="relatedlist">-->
                <!--<li><a href="http://xw.qq.com//ent/20141124004920/ENT2014112400492005">柴静老公赵嘉：她是好妻子</a></li>-->
                <!--<li><a href="http://xw.qq.com//ent/20141013009486/ENT2014101300948611">曝柴静离职或与赴美产女有关</a></li>-->
                <!--<li><a href="http://xw.qq.com//ent/20140219009929/ENT2014021900992905">揭柴静家世：住300年大宅</a></li>-->
            <!--</ul>-->
        <!--</div>-->
    <!--</div>-->
    <footer>
        <p class="copyright">Copyright © 2014 - 2016 窗友科技. All Rights Reserved</p>
    </footer>
</section>
<script src="<%=path %>/mobile/js/zepto.min.js"></script>
<script src="<%=path %>/mobile/js/cy_core.js"></script>
<script src="<%=path %>/mobile/js/dropload.min.js"></script>
<script src="<%=path %>/mobile/js/template_news.js"></script>
<script>
    Zepto(function ($) {
        getPageData("<%=path %>/mobile/news/newsAction!doNotNeedSessionAndSecurity_getMobileNews.action?newsId=<%=newsId%>", ".article-show", "", newsDetailTpl);
    });
</script>
</body>
</html>
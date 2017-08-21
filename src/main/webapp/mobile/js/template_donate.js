//我的捐赠历史
var donateHistoryListTpl ='<% for(var i=0;i<donateHistoryList.length;i++){ %>'+
    '<li class="ui-border-t" data-href="<%=donateHistoryList[i].donateUrl%>">'+
    '<div class="ui-list-info">'+
        '<h4 class="ui-txt-blue"><%=donateHistoryList[i].projectName%></h4>'+
        '<p>捐赠金额：<%=donateHistoryList[i].money%></p>'+
        '<p class="order-status"><% if (donateHistoryList[i].payStatus == 1){ %><span class="ui-txt-green">已完成</span><% }else{ %><span class="ui-txt-red">待支付</span><% } %><span class="post-time"><%=donateHistoryList[i].confirmTime%></span></p>'+
    '</div>'+
'</li>'+
    '<% } %>';

//捐赠项目列表
var donateItemListTpl ='<% for(var i=0;i<donateItemList.length;i++){ %>'+
    '<li class="ui-border-t" data-href="<%=donateItemList[i].donateItemUrl%>">'+
    '<div class="ui-list-img">'+
    '<span style="background-image:url(<%=donateItemList[i].projectPic%>)"></span>'+
    '</div>'+
    '<div class="ui-list-info">'+
    '<h4 class="ui-nowrap"><%=donateItemList[i].projectName%></h4>'+
'<p class="ui-nowrap-multi"><%=donateItemList[i].introduction%></p>'+
'</div>'+
'</li>'+
    '<% } %>';

//最新捐赠列表
var newDonateListTpl ='<% for(var i=0;i<newDonateList.length;i++){ %>'+
    '<li class="ui-border-t">'+
    '<div class="ui-list-info">'+
    '<h4 class="ui-txt-blue">感谢校友 <%=newDonateList[i].x_name%></h4>'+
'<p>捐赠 <strong><a href="<%=newDonateList[i].donateItemUrl%>"><%=newDonateList[i].projectName%></a></strong> <em><%=newDonateList[i].money%>元</em></p>'+
'</div>'+
'</li>'+
    '<% } %>';
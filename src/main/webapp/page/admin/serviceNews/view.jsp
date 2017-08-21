<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">

    <title></title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('#content',{
                fontSizeTable:['9px', '10px', '11px', '12px', '13px', '14px', '15px', '16px', '17px', '18px', '19px', '20px', '22px', '24px', '28px', '32px'],
                uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
                readonlyMode : true,
                afterChange:function(){
                    this.sync();
                }
            });
        });

        $(function () {
            var id = $('#serviceNewsId').val();
            $.ajax({
                url: '${pageContext.request.contextPath}/serviceNews/serviceNewsAction!getServiceNewsById.action',
                data: { 'serviceNewsId' : id },
                dataType: 'json',
                success: function (result) {

                    if (result.id != undefined) {

                        $('form').form('load', {

                            'serviceNews.title': result.title,
                            'serviceNews.introduction': result.introduction,
                            'serviceNews.newsUrl': result.newsUrl,
                            'serviceNews.serviceName': result.serviceName,
                            'serviceNews.content': result.content,
                            'serviceNews.type': result.type
                        });
                        if(result.channelName){
                            $('#lanMu').show();
                            $('#channel').val(result.channelName);
                        }
                        if(result.pic != undefined && result.pic !=''){
                            $('#newsPic').append('<img src="'+ result.pic +'" width="150px" height="150px"/>');
                        }
                        if( result.status!= undefined && result.status!='' ){
                            var tmp;
                            switch (result.status){
                                case '10':
                                    tmp="新增";
                                    break;
                                case '20':
                                    tmp="审核通过";
                                    break;
                                case '30':
                                    tmp="审核不通过";
                                    break;
                                case '40':
                                    tmp="下线";
                                    break;
                            }
                            $('#status').text(tmp);
                        }
                        editor.html(result.content);

                    }
                },

                complete: function () {

                    parent.$.messager.progress('close');

                }
            });
        });


    </script>
</head>

<body>
<form method="post" id="viewNewsForm">
    <fieldset>
        <legend>
            栏目信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    标题
                </th>
                <td colspan="3">
                    <input name="serviceNews.id" type="hidden" id="serviceNewsId" value="${param.id}">
                    <input name="serviceNews.title" class="easyui-validatebox"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" value=""  disabled="disabled"/>
                </td>
            </tr>

            <tr>
                <th>
                    新闻简介
                </th>
                <td colspan="3">
					<textarea id="introduction" rows="7" cols="100" disabled="disabled"
                              name="serviceNews.introduction"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                    网页链接
                </th>
                <td colspan="3">
                    <textarea id="newsUrl" rows="3" cols="100" disabled="disabled" name="serviceNews.newsUrl"></textarea>
                </td>
            </tr>

           <%-- <tr>
                <th>
                    兴趣标签
                </th>
                <td colspan="3">
                    <input name="serviceNews.type" class="easyui-validatebox"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" value=""  disabled="disabled"/>
                </td>
            </tr>--%>

            <tr>
                <th>
                    所属服务
                </th>
                <td colspan="3">
                 <input name="serviceNews.serviceName" class="easyui-validatebox"
                         data-options="required:true,validType:'customRequired'"
                         maxlength="30" value=""  disabled="disabled"/>
                </td>
            </tr>

            <tr style="display: none" id="lanMu">
                <th>
                    服务栏目
                </th>
                <td colspan="3">
                    <input name="serviceNews.channel" id="channel" class="easyui-validatebox"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" value=""  disabled="disabled"/>
                </td>
            </tr>

            <tr>
                <th>
                    新闻内容
                </th>
                <td colspan="3">
					<textarea id="content" name="serviceNews.content"
                              style="width: 700px; height: 300px;" disabled="disabled"></textarea>
                </td>
            </tr>


            <tr>
                <th>
                    新闻封面图片
                </th>
                <td colspan="3">
                    <div id="newsPic">
                    </div>
                </td>
            </tr>

        </table>
    </fieldset>
</form>
</body>
</html>
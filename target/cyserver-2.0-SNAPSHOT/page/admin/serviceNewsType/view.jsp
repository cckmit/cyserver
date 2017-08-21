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
            editor = K.create('#newscontent',{
                fontSizeTable:['9px', '10px', '11px', '12px', '13px', '14px', '15px', '16px', '17px', '18px', '19px', '20px', '22px', '24px', '28px', '32px'],
                uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
                readonlyMode : true,
                afterChange:function(){
                    this.sync();
                }
            });
        });

        $(function () {
            var id = $('#typeId').val();
            $.ajax({
                url: '${pageContext.request.contextPath}/serviceNewsType/serviceNewsTypeAction!getById.action',
                data: { 'serviceNewsTypeId' : id },
                dataType: 'json',
                success: function (result) {

                    if (result.id != undefined) {

                        $('form').form('load', {
                            'type.id': result.id,
                            'type.name': result.name,
                            'type.type': result.type,
                            'type.url': result.url,
                            'type.serviceId': result.serviceId,
                            'type.isNavigation': result.isNavigation,
                            'type.orderNum': result.orderNum,
                            'type.newsTitle': result.newsTitle,
                            'type.parent_name': result.parent_name,
                            'type.channel': result.channel
                        });
                        editor.html(result.newsContent);

                        if(result.type == 2) {
                            $("#trUrl").css("display","");
                        } else if(result.type == 3) {
                            $("#trNewsTitle").css("display","");
                            $("#trNewsContent").css("display","");
                        }
                        if(result.origin == 2) {
                            $("#trArea").css("display","");
                        }

                        if(result.parent_id == 0) {
                            $("#level").html('一级栏目');
                            $("#shangjilanmu").hide();
                        } else if(result.parent_id > 0) {
                            $("#level").html('二级栏目');
                            $("#trOrigin").css("display","none");
                            $("#trArea").css("display","none");

                            $("#shangjilanmutd").html(result.parent_name);
                        }

                        var address = "";
                        if(result.type == 1) {
                            address = "<%=basePath%>mobile/news/newsList.jsp?category=" + result.id;
                        } else if(result.type == 2) {
                            address = result.url;
                        } else if(result.type == 3) {
                            address = "<%=basePath%>mobile/news/newsShow.jsp?category=" + result.id;
                        }
                        $("#address").val(address);


                    }
                },
                beforeSend: function () {
                    parent.$.messager.progress({
                        text: '数据加载中....'
                    });
                },
                complete: function () {

                    parent.$.messager.progress('close');

                }
            });
        });


    </script>
</head>

<body>
<form method="post" id="addNewsForm">
    <input name="type.id" type="hidden" id="typeId" value="${param.id}">
    <fieldset>
        <legend>
            栏目信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    栏目名称
                </th>
                <td colspan="3">
                    <input id="name" name="type.name" class="easyui-validatebox"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" disabled="disabled"/>
                </td>
            </tr>
            <%--<tr>
                <th>
                    栏目级别
                </th>
                <td colspan="3" id="level">

                </td>
            </tr>--%>
            <%--<tr>
                <th>
                    栏目类型
                </th>
                <td colspan="3">
                    <select id="type" name="type.type" onchange="selectType();" disabled="disabled">
                        <option value="1">新闻类别</option>
                        <option value="2">链接</option>
                        <option value="3">单页面</option>
                    </select>
                </td>
            </tr>--%>
            <tr>
                <th>
                    渠道
                </th>
                <td colspan="3">
                    <select id="channel" name="type.channel" class="easyui-combobox" disabled="disabled">
                        <option value="10">手机</option>
                        <option value="20">网页</option>
                        <option value="30">微信</option>
                    </select>
                </td>
            </tr>
           <%-- <tr id="shangjilanmu">
                <th>
                    上级栏目
                </th>
                <td colspan="3" id="shangjilanmutd">

                </td>
            </tr>--%>
            <tr id="trUrl" style="display:none;">
                <th>
                    URL(可为空)
                </th>
                <td colspan="3">
                    <input id="url" style="width: 700px;" name="type.url" type="text" disabled="disabled">
                </td>
            </tr>

            <tr id="trNewsTitle" style="display:none;">
                <th>
                    新闻标题
                </th>
                <td colspan="3">
                    <input id="newstitle" style="width: 700px;" name="type.newsTitle" type="text" value="" disabled="disabled">
                </td>
            </tr>
            <tr id="trNewsContent" style="display:none;">
                <th>
                    新闻内容
                </th>
                <td colspan="3">
					<textarea id="newscontent" name="type.newsContent"
                              style="width: 700px; height: 300px;"></textarea>
                </td>
            </tr>

            <tr id="trOrigin" style="display:none;">
                <th>
                    新闻来源
                </th>
                <td colspan="3">
                    <select id="origin" name="type.origin" disabled="disabled">
                        <option value="1">总会</option>
                        <option value="2">地方</option>
                    </select>
                </td>
            </tr>

            <tr id="trArea" style="display:none;">
                <th>
                    地域
                </th>
                <td colspan="3">
                    <input name="type.cityName" style="width: 500px;" disabled="disabled" />
                </td>
            </tr>

            <tr>
                <th>
                    导航显示
                </th>
                <td colspan="3">
                    <select id="isNavigation" class="easyui-combobox" name="type.isNavigation" disabled="disabled">
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                    所属服务
                </th>
                <td>
                    <input name="type.serviceId" id="serviceId" disabled="disabled" class="easyui-combobox" style="width: 150px;" value=""
                           data-options="editable:false,required:true,
							        valueField: 'id',
							        textField: 'serviceName',
							        url: '${pageContext.request.contextPath}/mobile/schoolServ/schoolServAction!doNotNeedSessionAndSecurity_getServiceList.action'" />
                </td>
            </tr>
            <tr>
                <th>
                    排序编号
                </th>
                <td colspan="3">
                    <input id="orderNum" name="type.orderNum" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="required:true,validType:'tel'"
                           maxlength="20" value="" disabled="disabled" />&nbsp;&nbsp;&nbsp;&nbsp;( 数字越小越靠前)
                </td>
            </tr>

            <tr>
                <th>
                    栏目地址
                </th>
                <td colspan="3">
                    <input id="address" class="easyui-validatebox"
                           style="width: 700px;" disabled="disabled"/>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
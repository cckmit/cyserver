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
        //var id = "${param.id}";
        $(function () {
            var id =$("#weiXinMenuId").val();
            $.ajax({
                url: '${pageContext.request.contextPath}/weiXin/weiXinMenuAction!getById.action',
                data: { 'weiXinMenuId' : id },
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (result) {
//                        $('form').form('load', {
//                            'weiXinMenu.name': result.name,
//                            'weiXinMenu.parentId': result.parentId,
//                            'weiXinMenu.parentName': result.parentName,
//                            'weiXinMenu.type': result.type,
//                            'weiXinMenu.url': result.url,
//                            'weiXinMenu.accountId': result.accountId,
//                            'weiXinMenu.menuKey': result.menuKey,
//                            'weiXinMenu.catalogId': result.catalogId,
//                            'weiXinMenu.msgType': result.msgType
//                        });

                        $('#name').text(result.name);
                        $('#menuKey').text(result.menuKey);
                        $('#accountName').text(result.accountName);
                        $('#sort').text(result.sort);
                        if(result.parentId == '0') {
                            $('#trparentName').hide();
                            $("#shangjilanmu").hide();
                        } else {
                            $('#trparentName').show();
                            $('#parentName').text(result.parentName);
                        }
                        if (result.type == '10') {
                            $('#type').text("消息触发类");
                            $('#trMsgType').show();
                            $('#trUrl').hide();
                            $('#trOutSide').hide();
                            if (result.msgType == '10'){
                                $('#msgType').text("文本");
                                $('#cataloag').hide();
                            }else if (result.msgType == '20'){
                                $('#msgType').text("图文");
                                $('#cataloag').show();
                                $.ajax({
                                    url:'${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action',
                                    data:{ 'channel' : '30' },
                                    dataType: 'json',
                                    success: function (obj) {
                                        if(obj != null && obj !=''){
                                            for(var i in obj){
                                                if (result.catalogId == obj[i].id){
                                                    $('#catalogId').text(obj[i].text);
                                                }
                                            }

                                        }
                                    }
                                })
                            }
                        } else if (result.type == '20') {
                            $('#type').text("网页链接类");
                            $('#trMsgType').hide();
                            $('#trUrl').show();
                            $('#trOutSide').show();
                            if(result && result.isOutSide){
                                if(result.isOutSide == '1'){
                                    $('#isOutSide').text("是");
                                }else{
                                    $('#isOutSide').text("否");
                                }
                            }else{
                                $('#isOutSide').text("否");
                            }
                            $('#url').text(result.url)
                            $('#cataloag').hide();
                        }else if (result.type == '30') {
                            $('#type').text("父级菜单类");
                            $('#trMsgType').hide();
                            $('#trUrl').hide();
                            $('#cataloag').hide();
                        }
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
    <input name="weiXinMenu.id" id="weiXinMenuId"  type="hidden"  value="${param.id}">
    <input id="parent_id" type="hidden" name="weiXinMenu.parentId" value="">
    <input id="weiXinMenu" type="hidden" name="weiXinMenu.type" value="">
    <fieldset>
        <legend>
            微信菜单
        </legend>
        <table class="ta001">
            <tr id="trparentName" style="display: none">
                <th>
                    上级菜单
                </th>
                <td colspan="3">
                    <div id="parentName"></div>
                </td>
            </tr>
            <tr>
                <th>
                    名称
                </th>
                <td colspan="3">
                    <%--<input id="name" name="weiXinMenu.name" readonly="readonly" class="easyui-validatebox"--%>
                           <%--style="width: 150px;"--%>
                           <%--data-options="required:true,validType:'customRequired'"--%>
                           <%--maxlength="30" value=""/>--%>
                        <div id="name"></div>
                </td>
            </tr>
            <tr>
                <th>
                    唯一标识
                </th>
                <td colspan="3">
                    <%--<input id="menuKey" name="weiXinMenu.menuKey" readonly="readonly" class="easyui-validatebox"--%>
                           <%--style="width: 150px;"--%>
                           <%--data-options="required:true,validType:'customRequired'"--%>
                           <%--maxlength="20" value=""/>--%>
                        <div id="menuKey" ></div>
                </td>
            </tr>
            <tr>
                <th>
                    类型
                </th>
                <td colspan="3">
                    <%--<select id="type" class="easyui-combobox" readonly="readonly" name="weiXinMenu.type" data-options="" >--%>
                        <%--<option value="10" selected="selected">消息触发类</option>--%>
                        <%--<option value="20">网页链接类</option>--%>
                        <%--<option value="30">父级菜单类</option>--%>
                    <%--</select>--%>
                        <div id="type" ></div>
                </td>
            </tr>
            <tr id="trOutSide" style="display:none;">
                <th>
                    是否外部链接
                </th>
                <td colspan="3">
                    <div id="isOutSide"></div>
                </td>
            </tr>
            <tr id="trUrl" style="display:none;">
                <th>
                    URL
                </th>
                <td colspan="3">
                    <%--<input id="url" style="width: 500px;" readonly="readonly" name="weiXinMenu.url" type="text"   value="">--%>
                    <div id="url"></div>
                </td>
            </tr>
            <tr  id="trMsgType">
                <th>
                    消息类型
                </th>
                <td colspan="3">
                    <%--<select id="msgType" class="easyui-combobox" readonly="readonly" name="weiXinMenu.msgType"  data-options="--%>
                        <%--onSelect: function(value){--%>
                        <%--value = value.value;--%>
                        <%--if (value == '10') {--%>
                            <%--$('#cataloag').hide();--%>
                        <%--} else if (value == '20') {--%>
                            <%--$('#cataloag').show();--%>
                        <%--}--%>
						<%--}--%>
                    <%--">--%>
                        <%--<option value="10" >文本</option>--%>
                        <%--<option value="20" selected="selected">图文</option>--%>
                    <%--</select>--%>
                    <div id="msgType"></div>
                </td>
            </tr>
            <tr id="cataloag">
                <th>微信新闻栏目</th>
                <td colspan="3">
                    <%--<select name="weiXinMenu.catalogId"  readonly="readonly" id="catalogId" class="easyui-combotree"--%>
                            <%--data-options="--%>
								<%--editable:false,--%>
								<%--idField:'id',--%>
								<%--state:'open',--%>
								<%--textField:'text',--%>
								<%--parentField:'pid',--%>
								<%--url: '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?channel=10',--%>
								<%--"--%>
                            <%--style="width: 150px;">--%>
                    <%--</select>--%>
                    <div id="catalogId"></div>
                </td>
            </tr>
            <tr  id="trAccountId">
                <th>
                    公众号配置
                </th>
                <td colspan="3">
                    <%--<select id="accountId" class="easyui-combobox" readonly="readonly" name="weiXinMenu.accountId"  data-options="--%>
                            <%--editable:false,--%>
                            <%--valueField:'id',--%>
                            <%--state:'open',--%>
                            <%--textField:'accountName',--%>
                            <%--url: '${pageContext.request.contextPath}/weiXin/weiXinAccountAction!doNotNeedSessionAndSecurity_getList.action',--%>
                            <%--"--%>
                            <%--style="width: 150px;">--%>
                    <%--</select>--%>
                    <div id="accountName"></div>
                </td>
            </tr>
            <tr >
                <th>
                    排序
                </th>
                <td colspan="3">

                    <div id="sort"></div>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
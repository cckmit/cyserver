<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String parentId = request.getParameter("parentId");
    String accountId = request.getParameter("accountId");
    String accountName = request.getParameter("accountName");
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
                    if(result.parentId == '0') {
                        var typeJson=[{"id":10,"text":"消息触发类"},{"id":20,"text":"网页链接类"},{"id":30,"text":"父级菜单类"}];
                        $('#type').combobox('loadData', typeJson);
                        $('#trparentName').hide();
                        $("#shangjilanmu").hide();
                    } else {
                        var typeJson=[{"id":10,"text":"消息触发类"},{"id":20,"text":"网页链接类"}];
                        $('#type').combobox('loadData', typeJson);
                        $('#trparentName').show();
                        $('#parentName').text(result.parentName);
                    }

                    if (result) {
                        $('form').form('load', {
                            'weiXinMenu.name': result.name,
                            'weiXinMenu.parentId': result.parentId,
                            'weiXinMenu.parentName': result.parentName,
                            'weiXinMenu.type': result.type,
                            'weiXinMenu.isOutSide': result.isOutSide,
                            'weiXinMenu.url': result.url,
                            'weiXinMenu.accountId': result.accountId,
                            'weiXinMenu.menuKey': result.menuKey,
                            'weiXinMenu.catalogId':( result.catalogId ? result.catalogId :'') ,
                            'weiXinMenu.sort': result.sort,
                            'weiXinMenu.msgType': result.msgType,
                            'weiXinMenu.content': result.content
                        });


                        if (result.type == '10') {
                            $('#trMsgType').show();
                            $('#trUrl').hide();
                            $('#trOutSide').hide();
                            $('#catalog').show();
                            if (result.msgType == '10'){
                                $('#trContent').show();
                                $('#catalog').hide();
                            }else if (result.msgType == '20'){
                                $('#trContent').hide();
                                $('#catalog').show();
                            }
                        } else if (result.type == '20') {
                            $('#trMsgType').hide();
                            $('#trUrl').show();
                            $('#catalog').hide();
                            $('#trOutSide').show();
                        }else if (result.type == '30') {
                            $('#trMsgType').hide();
                            $('#trUrl').hide();
                            $('#trOutSide').hide();
                            $('#catalog').hide();
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

        function submitForm($dialog, $grid, $pjq)
        {
            if ($('form').form('validate')){
                var type = $('#type').combobox("getValue");

                if (!type || type == ''){
                    parent.$.messager.alert('提示', '请选择菜单类型', 'error');
                    return false;
                }
                if (type == 10) {
                    var msgType = $('#msgType').combobox("getValue");

                    if (!msgType || msgType == '') {
                        parent.$.messager.alert('提示', '请选择消息类型', 'error');
                        return false;
                    }
                    if (msgType == 10) {
                        var content = $('#content').val();
                        if (!content || content == '') {
                            parent.$.messager.alert('提示', '请输入文本内容', 'error');
                            return false;
                        }else {
                            $('#url').remove();
                            $('#catalog').remove();
                        }
                    }
                    if (msgType == 20) {
                        var catalogId = $('#catalogId').combotree("getValue");
                        if (!catalogId && catalogId == '') {
                            parent.$.messager.alert('提示', '请选择微信新闻栏目', 'error');
                            return false;
                        }else {
                            $('#url').remove();
                            $('#content').remove();
                        }
                    }
                }
                if (type == 20){
                    var url = $('#url').val();
                    if (!url || url == ''){
                        parent.$.messager.alert('提示', '请输入网页链接地址', 'error');
                        return false;
                    }else {
                        $('#catalog').remove();
                        $('#content').remove();
                    }
                }
                $.ajax({
                    url : '${pageContext.request.contextPath}/weiXin/weiXinMenuAction!update.action',
                    data : $('form').serialize(),
                    dataType : 'json',
                    success : function(result)
                    {
                        if (result.success)
                        {
                            $grid.treegrid('reload');
                            $dialog.dialog('destroy');
                            $pjq.messager.alert('提示', result.msg, 'info');
                        } else
                        {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    },
                    beforeSend : function()
                    {
                        parent.$.messager.progress({
                            text : '数据提交中....'
                        });
                    },
                    complete : function()
                    {
                        parent.$.messager.progress('close');
                    }
                });
            }
        };
    </script>
</head>

<body>
<form method="post" id="addNewsForm">
    <input name="weiXinMenu.id" id="weiXinMenuId" type="hidden"  value="${param.id}">
    <%--<input id="weiXinMenu" type="hidden" name="weiXinMenu.type" value="">--%>
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
                    <%--<input id="parentName" name="weiXinMenu.parentName" class="easyui-validatebox"--%>
                           <%--style="width: 150px;"--%>
                           <%--data-options="validType:'customRequired'"--%>
                           <%--maxlength="20" value="0" readonly/>--%>
                    <div id="parentName"></div>

                </td>
            </tr>
            <tr>
                <th>
                    名称
                </th>
                <td colspan="3">
                    <input id="name" name="weiXinMenu.name" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" value=""/>
                </td>
            </tr>
            <tr>
                <th>
                    唯一标识
                </th>
                <td colspan="3">
                    <input id="menuKey" name="weiXinMenu.menuKey" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="20" value=""/>
                </td>
            </tr>
            <tr>
                <th>
                    类型
                </th>
                <td colspan="3">
                    <select id="type" class="easyui-combobox" name="weiXinMenu.type" data-options="
                        value:'',
                        prompt:'--请选择菜单类型--',
                        valueField:'id',
                        textField:'text',
                        editable:false,
                        onSelect: function(value){
                        value = value.id;
                        if (value == '10') {
                            $('#trMsgType').show();
                            $('#trUrl').hide();
                            $('#trContent').hide();
                            $('#catalog').hide();
                            $('#trOutSide').hide();
                        } else if (value == '20') {
                            $('#trMsgType').hide();
                            $('#trUrl').show();
                            $('#catalog').hide();
                            $('#trContent').hide();
                            $('#trOutSide').show();
                        }else if (value == '30') {
                            $('#trMsgType').hide();
                            $('#trUrl').hide();
                            $('#catalog').hide();
                            $('#trContent').hide();
                            $('#trOutSide').hide();
                        }
						}
                    " >
                    </select>
                </td>
            </tr>
            <tr id="trOutSide" style="display:none;">
                <th>
                    是否外部链接
                </th>
                <td colspan="3">
                    <select id="isOutSide" class="easyui-combobox" data-options="editable:false" name="weiXinMenu.isOutSide">
                        <option value="0">否</option>
                        <option value="1">是</option>
                    </select>
                </td>
            </tr>
            <tr id="trUrl" style="display:none;">
                <th>
                    URL(可为空)
                </th>
                <td colspan="3">
                    <input id="url" style="width: 500px;" name="weiXinMenu.url" type="text"   value="">
                </td>
            </tr>
            <tr  id="trMsgType">
                <th>
                    消息类型
                </th>
                <td colspan="3">
                    <select id="msgType" class="easyui-combobox" name="weiXinMenu.msgType"  data-options="
                        value:'',
                        editable:false,
                        prompt:'--请选择消息类型--',
                        onSelect: function(value){
                        value = value.value;
                        if (value == '10') {
                            $('#catalog').hide();
                            $('#trContent').show();
                        } else if (value == '20') {
                            $('#catalog').show();
                            $('#trContent').hide();
                        }
						}
                    ">
                        <option value="10">文本</option>
                        <option value="20">图文</option>
                    </select>
                </td>
            </tr>
            <tr id="catalog" hidden>
                <th>微信新闻栏目</th>
                <td colspan="3">
                    <select name="weiXinMenu.catalogId"  id="catalogId" class="easyui-combotree"
                            data-options="
                             value:'',
                             prompt:'--请选择微信新闻栏目--',
								editable:false,
								idField:'id',
								state:'open',
								textField:'text',
								parentField:'pid',
								url: '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?channel=30',
								"
                            style="width: 150px;">
                    </select>
                </td>
            </tr>
            <tr id="trContent" hidden>
                <th>
                    文本内容
                </th>
                <td colspan="3">
                    <textarea rows="3" cols="100" id="content" name="weiXinMenu.content" class="easyui-validatebox"></textarea>
                </td>
            </tr>
            <tr  id="trAccountId">
                <th>
                    公众号配置
                </th>
                <td colspan="3">
                    <% if("0".equals(parentId)){ %>
                    <select id="accountId" class="easyui-combobox easyui-validatebox" name="weiXinMenu.accountId"  data-options="
                                editable:false,
                                valueField:'id',
                                state:'open',
                                textField:'accountName',
                                url: '${pageContext.request.contextPath}/weiXin/weiXinAccountAction!doNotNeedSessionAndSecurity_getList.action',
                                "
                            style="width: 150px;" required>
                    </select>
                    <%}%>
                    <% if(!"0".equals(parentId)){ %>
                    <input id="accountId" name="weiXinMenu.accountId" type="hidden" value="<%=accountId %>" />
                    <%=accountName%>
                    <%}%>
                </td>
            </tr>
            <tr>
                <th>
                    排序
                </th>
                <td colspan="3">
                    <input id="sort" name="weiXinMenu.sort" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" value=""/>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
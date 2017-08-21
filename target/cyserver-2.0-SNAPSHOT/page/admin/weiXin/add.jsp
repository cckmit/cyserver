<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String parentId = request.getParameter("parentId");
    String parentName = request.getParameter("parentName");
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
    <meta http-equiv="keywords" content="keyword1,keyword2$('form').serialize(),keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
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
                            $('#cataloag').remove();
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
                        $('#cataloag').remove();
                        $('#content').remove();
                    }
                }
                $.ajax({
                    url : '${pageContext.request.contextPath}/weiXin/weiXinMenuAction!save.action',
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
    <input id="parentId" name="weiXinMenu.parentId" type="hidden" value="<%=parentId %>" />
    <fieldset>
        <legend>
            栏目信息
        </legend>
        <table class="ta001">
            <% if(!"0".equals(parentId)){ %>
                <tr>
                    <th>
                        上级菜单
                    </th>
                    <td colspan="3">
                        <%=parentName%>
                    </td>
                </tr>
            <%}%>

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
                        editable:false,
                        onSelect: function(value){
                        value = value.value;
                        if (value == '10') {
                            $('#trMsgType').show();
                            $('#trUrl').hide();
                            $('#trContent').hide();
                            $('#cataloag').hide();
                            $('#trOutSide').hide();
                        } else if (value == '20') {
                            $('#trMsgType').hide();
                            $('#trUrl').show();
                            $('#cataloag').hide();
                            $('#trContent').hide();
                            $('#trOutSide').show();
                        }else if (value == '30') {
                            $('#trMsgType').hide();
                            $('#trUrl').hide();
                            $('#cataloag').hide();
                            $('#trContent').hide();
                            $('#trOutSide').hide();
                        }
						}
                    " >
                        <option value="10">消息触发类</option>
                        <option value="20">网页链接类</option>
                        <% if("0".equals(parentId)){ %>
                        <option value="30">父级菜单类</option>
                        <%}%>

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
                    URL
                </th>
                <td colspan="3">
                    <input id="url" style="width: 500px;" name="weiXinMenu.url" type="text"   value="">
                </td>
            </tr>
            <tr id="trMsgType" hidden>
                <th>
                    消息类型
                </th>
                <td colspan="3">
                    <select id="msgType" class="easyui-combobox" name="weiXinMenu.msgType"  data-options="
                        value:'',
                        prompt:'--请选择消息类型--',
                        editable:false,
                        onSelect: function(value){
                        value = value.value;
                        if (value == '10') {
                            $('#cataloag').hide();
                            $('#trContent').show();
                        } else if (value == '20') {
                            $('#cataloag').show();
                            $('#trContent').hide();
                        }
						}
                    ">
                        <option value="10" >文本</option>
                        <option value="20" >图文</option>
                    </select>
                </td>
            </tr>
            <tr id="cataloag" hidden>
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
<%@ page language="java" pageEncoding="UTF-8" import="com.cy.util.WebUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">

    <title>新增黑白名单</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var submitForm = function ($dialog, $grid, $pjq) {
            if ($('form').form('validate')) {
                var url;
                url = '${pageContext.request.contextPath}/roster/rosterAction!save.action';
                $.ajax({
                    url: url,
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.success) {
                            $grid.datagrid('reload');
                            $dialog.dialog('destroy');
                            $pjq.messager.alert('提示', result.msg, 'info');
                        } else {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    },
                    beforeSend: function () {
                        parent.$.messager.progress({
                            text: '数据提交中....'
                        });
                    },
                    complete: function () {
                        parent.$.messager.progress('close');
                    }
                });
            }
        };


    </script>
</head>

<body>
<form method="post" class="form">
    <fieldset>
        <legend>
            基本信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    参数
                </th>
                <td>
                    <input name="roster.ref_id" class="easyui-validatebox" style="width: 155px"
                           data-options="required:true">
                    </input>
                </td>
            </tr>         
            <tr>
            	<th>
                    名单类型
                </th>
                <td>
                	<select name="roster.type" style="width: 155px;" class="easyui-combobox"  data-options="editable:false">
			       		<option value="1">黑名单</option>
			        	<option value="2">白名单</option>
			        </select>
                </td>
            </tr>
            <tr>
                <th>
                   所属类型
                </th>
                <td>
                	<input name="roster.dict_id" class="easyui-combobox" style="width: 155px;" 
							data-options="  
							valueField: 'dictId',  
							textField: 'dictName',  
							editable:false,
							url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('黑白名单类别') " />
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>

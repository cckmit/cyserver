<%--
  Created by IntelliJ IDEA.
  User: cha0res
  Date: 2/14/17
  Time: 5:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
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
        var type =  '${param.type}';
        var id =  '${param.id}';
        var postUrl = '';

        if(type == '0'){
            postUrl = '${pageContext.request.contextPath}/project/projectAction!saveCost.action';
        }else if(type == '2'){
            postUrl = '${pageContext.request.contextPath}/project/projectAction!updateCost.action';
        }

        $(function () {
            if(type == '1'){
                $('input').attr('readonly', true);
                $("#costTime").datetimebox({ disabled: true });
                $('textarea').attr('readonly', true);
            }

           if(type != '0' && id != '0'){
               $.ajax({
                   url:'${pageContext.request.contextPath}/project/projectAction!selectCostById.action',
                   data :{'projectCost.id':id},
                   dataType:'json',
                   success : function(result){
                       $('form').form('load', {
                           'projectCost.id' : result.id,
                           'projectCost.costMoney' : result.costMoney,
                           'projectCost.costTime' : result.costTime,
                           'projectCost.description': result.description
                       });
                   },
                   beforeSend:function(){
                       parent.$.messager.progress({
                           text : '数据加载中....'
                       });
                   },
                   complete:function(){
                       parent.$.messager.progress('close');
                   }
               });
           }
        });

        function submitForm($dialog, $grid, $pjq){
            if ($('form').form('validate')){
                $.ajax({
                    url : postUrl,
                    data : $('form').serialize(),
                    dataType : 'json',
                    success : function(result)
                    {
                        if (result.success)
                        {
                            $grid.datagrid('reload');
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

        }
    </script>
</head>
<body>
    <form method="post">
        <fieldset>
            <table class="ta001">
                <tr>
                    <th>
                        支出金额
                    </th>
                    <td>
                        <input name="projectCost.id" type="hidden" value="${param.id}">
                        <input name="projectCost.projectId" type="hidden" value="${param.projectId}">
                        <input type="number" id="costMoney" class="easyui-validatebox" name="projectCost.costMoney" value="0.00" step="1.00" data-options="required:true"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        时间
                    </th>
                    <td>
                        <input id="costTime" name="projectCost.costTime" class="easyui-datetimebox" data-options="editable:false" />
                    </td>
                </tr>
                <tr>
                    <th>
                        支出描述
                    </th>
                    <td>
                        <textarea id="description" name="projectCost.description"
                                  style="width: 700px; height: 200px;"></textarea>
                    </td>
                </tr>
            </table>
        </fieldset>
    </form>
</body>
</html>

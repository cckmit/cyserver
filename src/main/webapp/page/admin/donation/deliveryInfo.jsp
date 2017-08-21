<%--
  Created by IntelliJ IDEA.
  User: cha0res
  Date: 1/11/17
  Time: 12:03 PM
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
        var donationId='${param.id}';
        $(function() {
            if (donationId > 0) {
                $.ajax({
                    url:'${pageContext.request.contextPath}/donation/donationAction!doNotNeedSecurity_getById.action',
                    data :{'id':donationId},
                    dataType:'json',
                    success : function(result){
                        if(result.confirmStatus && (result.confirmStatus == 40 || result.confirmStatus == 45)){
                            $('#confirmStatus').val(result.confirmStatus);
                        }
                        result.credentialsCourier?$('#credentialsCourier').val(result.credentialsCourier):"";
                        result.credentialsCourierNumber?$('#credentialsCourierNumber').val(result.credentialsCourierNumber):"";
                    }
                });
            }
        });

        function submitForm($pdialog,$grid, $pjq, $dialog) {
            parent.$.messager.confirm('确定', '确保信息填写无误？', function(r)
            {
                if(r){
                    if ($('form').form('validate')){
                        $.ajax({
                            url : '${pageContext.request.contextPath}/donation/donationAction!update.action',
                            data : $('form').serialize(),
                            dataType : 'json',
                            success : function(result)
                            {
                                if (result.success)
                                {
                                    $grid.datagrid('reload');
                                    window.parent.refreshMsgNum();
                                    $dialog.dialog('destroy');
                                    $pdialog.dialog('destroy');
                                    $pjq.messager.alert('提示', result.msg, 'info');
                                } else
                                {
                                    $pjq.messager.alert('提示', result.msg, 'error');
                                }
                            },
                            beforeSend : function()
                            {
                                $pjq.messager.progress({
                                    text : '数据提交中....'
                                });
                            },
                            complete : function()
                            {
                                $pjq.messager.progress('close');
                            }
                        });
                    }
                }
            });

        }
    </script>
</head>
<body>
<form method="post">
    <input name="donation.donationId" type="hidden" value="${param.id}">
    <input name="donation.certificatePic" id="certificatePic" type="hidden" value="${param.certificatePic}" />
    <input name="donation.deliveryAddress" id="deliveryAddress" type="hidden" value="${param.deliveryAddress}" />
    <table class="ta001">
        <tr>
            <th>
                邮寄内容
            </th>
            <td>
                <select data-options="editable:false" id="confirmStatus" name="donation.confirmStatus">
                    <option value="40">证书和发票</option>
                    <option value="45">证书</option>
                </select>
            </td>
        </tr>
        <tr>
            <th>
                快递公司
            </th>
            <td>
                <input name="donation.credentialsCourier" id="credentialsCourier" type="text" class="easyui-validatebox" />
            </td>
        </tr>
        <tr>
            <th>
                快递单号
            </th>
            <td>
                <input name="donation.credentialsCourierNumber" id="credentialsCourierNumber" type="text" class="easyui-validatebox" />
            </td>
        </tr>
    </table>
</form>
</body>
</html>

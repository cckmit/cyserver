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
        $(function () {
            var id = $('#smsBuyWaterId').val();
            $.ajax({
                url: '${pageContext.request.contextPath}/smsBuyWater/smsBuyWaterAction!getSmsBuyWaterById.action',
                data: { 'smsBuyWaterId' : id },
                dataType: 'json',
                success: function (result) {

                    if (result.id != undefined) {

                        $('form').form('load', {

                            'smsBuyWater.account': result.account,
                            'smsBuyWater.orderNo': result.orderNo,
                            'smsBuyWater.buyPrice': result.buyPrice,
                            'smsBuyWater.surplusNum': result.surplusNum
                        });
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
<form method="post" id="viewSmsBuyWaterForm">
    <fieldset>
        <legend>
            栏目信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    应用帐号
                </th>
                <td colspan="3">
                    <input name="smsBuyWater.id" type="hidden" id="smsBuyWaterId" value="${param.id}">
                    <input name="smsBuyWater.account" class="easyui-validatebox"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" value=""  disabled="disabled"/>
                </td>
            </tr>
            <tr>
                <th>
                    订单号
                </th>
                <td colspan="3">
                    <input name="smsBuyWater.orderNo" class="easyui-validatebox"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" value=""  disabled="disabled"/>
                </td>
            </tr><tr>
            <th>
                购买金额
            </th>
            <td colspan="3">
                <input name="smsBuyWater.buyPrice" class="easyui-validatebox"
                       data-options="required:true,validType:'customRequired'"
                       maxlength="30" value=""  disabled="disabled"/>
            </td>
        </tr>
            <tr>
                <th>
                    剩余条数
                </th>
                <td colspan="3">
                    <input name="smsBuyWater.surplusNum" class="easyui-validatebox"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" value=""  disabled="disabled"/>
                </td>
            </tr>

        </table>
    </fieldset>
</form>
</body>
</html>
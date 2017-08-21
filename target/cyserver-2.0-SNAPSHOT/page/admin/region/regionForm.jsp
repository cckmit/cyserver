<%--
  Created by IntelliJ IDEA.
  User: jiangling
  Date: 7/22/16
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        $(function() {
            // 编辑 edit
            if ($('#id').val() != '') {
                $.ajax({
                    url : '${pageContext.request.contextPath}/region/regionAction!view.action?sid='+$('#id').val(),
                    dataType : 'json',
                    success : function(result) {
                        if (result.success) {
                            $('#regionName').val(result.obj.name);
                            $('#orderId').val(result.obj.orderId);
                            $('#level').val(result.obj.level);
                            $('#areaCode').val(result.obj.areaCode);
                            $('#postCode').val(result.obj.postCode);

                            switch(result.obj.level){
                                case '1':
                                case '2':
                                    $('#areaCode').attr("readonly","readonly");
                                    $('#postCode').attr("readonly","readonly");
                                    break;
                                case '3':
                                    $('#postCode').attr("readonly","readonly");
                                    break;
                                case '4':
                                    $('#areaCode').attr("readonly","readonly");
                            }

                        } else {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    }
                });
            }
            //查看
            if ($('#action').val() == 'view') {
                $('#regionName').attr("readonly","readonly");
                $('#orderId').attr("readonly","readonly");
                //$('#level').attr("readonly","readonly");
                $('#areaCode').attr("readonly","readonly");
                $('#postCode').attr("readonly","readonly");
            }
            $('#level').attr("readonly","readonly");

        });

        var submitForm = function($dialog, $grid, $pjq) {
            var  strId = $('#id').val();
//            alert(strId);
            // 对国家编辑 + 对省份编辑
            if (strId.substr(0,2)=='3.' || strId.substr(0,2)=='2.') {
                if($('#regionName').val()==''){
                    $pjq.messager.alert('提示', '请设置地区名称', 'info');
                    return false;
                }
                if($('#orderId').val()==''){
                    $pjq.messager.alert('提示', '请设置排序', 'info');
                    return false;
                }
                if($('#level').val()==''){
                    $pjq.messager.alert('提示', '请设置层级', 'info');
                    return false;
                }

            }

            //对城市编辑
            else if (strId.substr(0,2)=='1.') {
                if ($('#regionName').val() == '') {
                    $pjq.messager.alert('提示', '请设置地区名称', 'info');
                    return false;
                }
                if ($('#orderId').val() == '') {
                    $pjq.messager.alert('提示', '请设置排序', 'info');
                    return false;
                }
                if ($('#level').val() == '') {
                    $pjq.messager.alert('提示', '请设置层级', 'info');
                    return false;
                }
                if ($('#areaCode').val() == '') {
                    $pjq.messager.alert('提示', '请设置城市区号', 'info');
                    return false;

                }
            }
                //对区县编辑
                else {

                    if ($('#regionName').val() == '') {
                        $pjq.messager.alert('提示', '请设置地区名称', 'info');
                        return false;
                    }
                    if ($('#orderId').val() == '') {
                        $pjq.messager.alert('提示', '请设置排序', 'info');
                        return false;
                    }
                    if ($('#level').val() == '') {
                        $pjq.messager.alert('提示', '请设置层级', 'info');
                        return false;
                    }
                    if ($('#postCode').val() == '') {
                        $pjq.messager.alert('提示', '请设置邮编', 'info');
                        return false;
                    }

                }


//            alert(JSON.stringify($('form').serialize())) ;
//            return;
            if ($('form').form('validate')) {
                var url = '${pageContext.request.contextPath}/region/regionAction!update.action?sid='+$('#id').val()
                $.ajax({
                    url : url,
                    data :$('form').serialize(),
                    dataType : 'json',
                    success : function(result) {
                        if (result.success) {
                            $dialog.dialog('destroy');
                            $grid.treegrid('reload');
                            $pjq.messager.alert('提示', result.msg, 'info');
                        } else {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    }
                });
            }

        }

    </script>
</head>

<body>
<form method="post">
<input type="hidden" id="id" name="region.id" value="${param.id}" />
<input type="hidden" id="action" value="${param.action}" />
<fieldset>
    <legend>
        地区信息
    </legend>
    <table class="ta001">
        <tr>
            <th>
                地区名称
            </th>
            <td>
                <input name="region.name" id="regionName"/>
            </td>
            <th>
                排序
            </th>
            <td>
                <input name="region.orderId" id="orderId" />
            </td>
        </tr>
        <tr>
            <th>
                级别
            </th>
            <td>
                <input name="region.level" id="level" />
            </td>
            <th>
                城市区号
            </th>
            <td>
                <input name="region.areaCode" id="areaCode" />
            </td>
        </tr>
        <tr>
            <th>
                邮编
            </th>
            <td><input name="region.postCode" id="postCode" />
            </td>
        </tr>
    </table>
</fieldset>
</form>
</body>
</html>


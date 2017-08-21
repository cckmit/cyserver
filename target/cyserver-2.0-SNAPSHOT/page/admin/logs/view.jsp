<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
        $(function () {
            if ($('#id').val() > 0) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/schoolInfo/schoolInfoAction!getById.action',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result['id']) {
                            $('form').form('load', {
                                'schoolInfo.id': result['id'],
                                'schoolInfo.schoolId': result['schoolId'],
                                'schoolInfo.name': result['name'],
                                'schoolInfo.key': result['key']
                            });
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
            }



            $('#schoolInfoForm :input').attr('disabled', true);
        });
    </script>
</head>

<body>
<form method="post" id="schoolInfoForm" class="form">
<input id="id" value="${param.id}" name="schoolInfo.id" type="hidden">
    <fieldset>
        <legend>
            学校信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    学校编号
                </th>
                <td>
                    <input id="schoolId" name="schoolInfo.schoolId">
                    </input>
                </td>
           	</tr>
           	<tr>
                <th>
                    学校名称
                </th>
                <td>
                    <input name="schoolInfo.name">
                    </input>
                </td>
            </tr>
            <tr>
                <th>
                    学校密钥
                </th>
                <td>
                    <input name="schoolInfo.key">
                    </input>
                </td>
            </tr>
        </table>
    </fieldset>
    <br>
    
</form>
</body>
</html>

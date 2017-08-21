<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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
            var logId = '${param.id}';
            if (logId != null && $.trim(logId) != '') {
                $.ajax({
                    url: '${pageContext.request.contextPath}/log/logAction!getById.action?log.id='+logId,
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            console.log(result);
                            $('#title').text(result.title?result.title:"");
                            $('#userAccount').text(result.userAccount?result.userAccount:"");
                            $('#userName').text(result.userName?result.userName:"");
                            $('#createDate').text(result.createDate?result.createDate:"");
                            $('#remoteAddr').text(result.remoteAddr?result.remoteAddr:"");
                            $('#requestUri').text(result.requestUri?result.requestUri:"");
                            $('#method').text(result.method?result.method:"");
                            $('#params').text(result.params?result.params:"");
                            $('#userAgent').text(result.userAgent?result.userAgent:"");
                            if(result.type){
                                var txt = "";
                                switch (result.type){
                                    case "1": txt="接入日志";
                                    break;
                                    case "2": txt="错误日志";
                                    break;
                                    default: txt="";
                                }
                                $('#type').text(txt);
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
            }
         });
    </script>
</head>

<body>
<form method="post" class="form">

    <table class="ta001">
        <tr>
            <th>日志标题</th>
            <td id="title"></td>
        </tr>
        <tr>
            <th>用户账号</th>
            <td id="userAccount"></td>
        </tr>
        <tr>
            <th>用户名</th>
            <td id="userName"></td>
        </tr>
        <tr>
            <th>时间</th>
            <td id="createDate"></td>
        </tr>
        <tr>
            <th>日志类型</th>
            <td id="type"></td>
        </tr>
        <tr>
            <th>操作用户的IP地址</th>
            <td id="remoteAddr"></td>
        </tr>
        <tr>
            <th>操作的URI</th>
            <td id="requestUri"></td>
        </tr>
        <tr>
            <th>操作的方式</th>
            <td id="method"></td>
        </tr>
        <tr>
            <th>操作提交的数据</th>
            <td id="params"></td>
        </tr>
        <tr>
            <th>操作用户代理信息</th>
            <td id="userAgent"></td>
        </tr>
    </table>

</form>
</body>
</html>

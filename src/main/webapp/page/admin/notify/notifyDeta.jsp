<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String notifyId = request.getParameter("notifyId");
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
</head>
<script type="text/javascript">

    var btnname = request.getParameter("notifyId");
    alert(btnname);
    function viewNews() {
        var dialog = parent.modalDialog({
            title : '查看推送',
            iconCls:'ext-icon-note_add',
            url :'${pageContext.request.contextPath}/page/admin/notify/view.jsp?notifyId=<%=notifyId %>',
        });
    }
</script>
<body>
<form method="post" id="editnotifyForm">
    <fieldset>
        <legend>
            推送记录
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    记录标题
                </th>
                <td colspan="3">

                    <input name="notify.title" class="easyui-validatebox"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="1000" value="${notify.title}"/>
                </td>
            </tr>
            <tr>
                <th>
                    推送方式
                </th>
                <td colspan="3">
                    <input name="notify.type" class="easyui-validatebox"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="1000" value="${notify.channel==10?'群推':'个推'}"/>
                </td>
            </tr>
            <tr>
                <th>
                    推送手段
                </th>
                <td colspan="3">
                    <input name="notify.type" class="easyui-validatebox" id="way"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="1000" value="${notify.way==10?'app':'短信'}"/>
                </td>
            </tr>
            <tr>
                <th>
                    推送内容
                </th>
                <td colspan="3">
                    <textarea name="notify.Content" rows="6" cols="70" class="easyui-validatebox" data-options="required:true,validType:'customRequired'">${notify.content}</textarea>
                </td>
            </tr>




            <tr>
                <td colspan="3">
                    操作
                </td>
                <td  style="text-align: center">

                    <authority:authority authorizationCode="查看推送" userRoles="${sessionScope.user.userRoles}">
                        <a href="javascript:void(0)" onclick="viewNews();"><img class="iconImg ext-icon-note"/>查看推送目标</a>&nbsp;
                    </authority:authority>
                </td>
            </tr>



        </table>
    </fieldset>
</form>
</body>
</html>
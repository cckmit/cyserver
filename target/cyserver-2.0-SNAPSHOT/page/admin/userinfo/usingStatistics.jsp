<%@ taglib prefix="c" uri="/struts-tags" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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

        $(function () {
            $.ajax({
                url: '${pageContext.request.contextPath}/userInfo/userInfoAction!usingStatistics.action',
                dataType: 'json',
                success: function (result) {
//                    alert(JSON.stringify(result));
                    if (result && result.obj){
                        $('#countAndroidDownloads').text(result.obj.countAndroidDownloads);
                        $('#iOSDownloads').text(result.obj.iOSDownloads);
                        $('#countUserProfile').text(result.obj.countUserProfile);
                        $('#countAuthenticateUser').text(result.obj.countAuthenticateUser);
                        $('#countUserInfoMining').text(result.obj.countUserInfoMining);
                        $('#countUserInfoRegister').text(result.obj.countUserInfoRegister);
                        $('#countUserInfoAuthenticate').text(result.obj.countUserInfoAuthenticate);
                        $('#alumniWechatAccount').text(result.obj.alumniWechatAccount);
                        $('#foundationWechatAccount').text(result.obj.foundationWechatAccount);
                        $('#countWechatUser').text(result.obj.countWechatUser);

                    }

                },
                beforeSend: function () {
                    parent.$.messager.progress({
                        text: '数据加载中....'
                    });
                },
                complete: function () {
                    // window.location.reload();
                    parent.$.messager.progress('close');

                }
            });
        });

    </script>
</head>

<body>
   <%-- <fieldset style="width: 50%">
        <legend style="text-align: center">使用统计</legend>--%>
        <table class="ta001" style="width: 300px">
            <tr style="width: 100px">
                <th colspan="2" style="text-align: center">使用统计详情</th>
            </tr>
            <tr style="text-align: center">
                <th style="text-align: center;width: 150px">Android 下载量</th>
                <td id="countAndroidDownloads"></td>
            </tr>
            <tr style="text-align: center">
                <th style="text-align: center">ios 下载量</th>
                <td id="iOSDownloads"></td>
            </tr>
            <tr style="text-align: center">
                <th style="text-align: center">注册校友数</th>
                <td id="countUserProfile"></td>
            </tr>
            <tr style="text-align: center">
                <th style="text-align: center">认证校友数</th>
                <td id="countAuthenticateUser"></td>
            </tr>
            <tr style="text-align: center">
                <th style="text-align: center">挖掘校友数</th>
                <td id="countUserInfoMining"></td>
            </tr>
            <tr style="text-align: center">
                <th style="text-align: center">挖掘注册校友数</th>
                <td id="countUserInfoRegister"></td>
            </tr>
            <tr style="text-align: center">
                <th style="text-align: center">挖掘认证校友数</th>
                <td id="countUserInfoAuthenticate"></td>
            </tr>
            <tr style="text-align: center">
                <th style="text-align: center">校友会公众号关注人数</th>
                <td id="alumniWechatAccount"></td>
            </tr>
            <tr style="text-align: center">
                <th style="text-align: center">基金会公众号关注人数</th>
                <td id="foundationWechatAccount"></td>
            </tr>
            <tr style="text-align: center">
                <th style="text-align: center">微信关注总人数</th>
                <td id="countWechatUser"></td>
            </tr>
        </table>
    <%--</fieldset>--%>
</body>
</html>

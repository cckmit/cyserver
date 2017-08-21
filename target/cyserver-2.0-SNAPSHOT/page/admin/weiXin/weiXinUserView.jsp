<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/13
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@page import="com.cy.util.WebUtil" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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

    <title></title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var id = "${param.id}";
        $(function () {

            $.ajax({
                url: '${pageContext.request.contextPath}/weiXin/weiXinUserAction!getById.action',
                data: {'weiXinUserId': id},
                dataType: 'json',
                success: function (result) {
                    console.log(result);
                    if (result.id != undefined) {

                        console.log(result);
                        $('#nickname').text(result.nickname ? result.nickname : "");
                        $('#openid').text(result.openid ? result.openid : "");
                        $('#username').text(result.username ? result.username : "");
                        var allPath ='';
                        if (result.allPath){
                            var path = result.allPath.split("_");
                            for(var i in path){
                                allPath=allPath+path[i]+"</br>";
                                $('#allPath').html(allPath);
                            }
                        }
                        if (result.sex == 1) {
                            $('#sex').text("男");
                        } else if (result.sex == 2) {
                            $('#sex').text("女");
                        } else {
                            $('#sex').text("其它");
                        }
                        $('#language').text(result.language ? result.language : "");
                        $('#city').text(result.city ? result.city : "");
                        $('#country').text(result.country ? result.country : "");
                        $('#isFollow').text(result.isFollow ? result.isFollow : "");
                        if (result.isFollow == 0) {
                            $('#isFollow').text("未关注");
                        } else if (result.sex == 1) {
                            $('#isFollow').text("关注");
                        } else {
                            $('#isFollow').text("数据异常");
                        }
                        $('#accountName').text(result.accountName ? result.accountName : "");
                        $('#accountAppId').text(result.accountAppId ? result.accountAppId : "");
                        $('#headimgurl').attr("src",result.headimgurl ? result.headimgurl : "");
                        $('#localHeadImage').attr("src",result.localHeadImage ? result.localHeadImage : "");
                        $('#longitude').text(result.longitude ? result.longitude : "");
                        $('#latitude').text(result.latitude ? result.latitude : "");
                        $('#positionDesc').text(result.positionDesc ? result.positionDesc : "");
                        $('#updateDate').text(result.updateDate ? result.updateDate : "");


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
<form method="post" id="viewAssociationForm">
    <fieldset>
        <legend>
            微信用户信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    微信头像
                </th>
                <td >
                    <img id="headimgurl" width="50" height="50" style="margin-bottom: 5px">
                </td>
                <th>
                    服务器微信头像
                </th>
                <td >
                    <img id="localHeadImage" width="50" height="50" style="margin-bottom: 5px">
                </td>
            </tr>
            <tr>
                <th width="300">
                    微信昵称
                </th>
                <td colspan="3" id="nickname">

                </td>
            </tr>

            <tr>
                <th>
                    微信用户唯一标识
                </th>
                <td colspan="3" id="openid">

                </td>
            </tr>

            <tr>
                <th>
                    手机用户
                </th>
                <td colspan="3" id="userName">

                </td>
            </tr>
            <tr>
                <th>
                    学习经历
                </th>
                <td colspan="3" id="allPath">

                </td>
            </tr>



            <tr>
                <th>
                    性别
                </th>
                <td colspan="3" id="sex">

                </td>
            </tr>
            <tr>
                <th>
                    语言
                </th>
                <td colspan="3" id="language">

                </td>
            </tr>
            <tr>
                <th>
                    城市
                </th>
                <td colspan="3" id="city">

                </td>
            </tr>
            <tr>
                <th>
                    国家
                </th>
                <td colspan="3" id="country">

                </td>
            </tr>
            <tr>
                <th>
                    是否关注
                </th>
                <td colspan="3" id="isFollow">

                </td>
            </tr>
            <tr>
                <th>
                    微信公众号名称
                </th>
                <td colspan="3" id="accountName">

                </td>
            </tr>
            <tr>
                <th>
                    微信公共号APPID
                </th>
                <td colspan="3" id="accountAppId">

                </td>
            </tr>
            <tr>
                <th>
                    经度
                </th>
                <td colspan="3" id="longitude">

                </td>
            </tr>
            <tr>
                <th>
                    纬度
                </th>
                <td colspan="3" id="latitude">

                </td>
            </tr>
            <tr>
                <th>
                    位置描述
                </th>
                <td colspan="3" id="positionDesc">

                </td>
            </tr>
            <tr>
                <th>
                    关注/取消时间
                </th>
                <td colspan="3" id="updateDate">

                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>

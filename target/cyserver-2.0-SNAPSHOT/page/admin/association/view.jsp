<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/13
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
                $(function () {
                    var id = $('#associationId').val();
                    $('#checkPoster').click(function () {
                        window.open('${pageContext.request.contextPath}/page/admin/association/associationPoster.jsp?id='+id);
                    });
                    $.ajax({
                        url: '${pageContext.request.contextPath}/association/associationAction!getAssociationById.action',
                        data: { 'associationId' : id },
                        dataType: 'json',
                        success: function (result) {

                            if (result.id != undefined) {

                                /*$('form').form('load', {

                                 'association.alumniId': result.alumniName,
                                 'association.typeId': result.typeId,
                                 'association.name': result.name,
                                 'association.concatName': result.concatName,
                                 'association.telephone': result.telephone,
                                 'association.address': result.address,
                                 'association.email': result.email,
                                 'association.introduction': result.introduction,
                                 'association.userAccount': result.userAccount,
                                 'association.userPassword': result.userPassword,
                                 'association.tel': result.tel
                                 /!*'association.qrCodeUrl':result.qrCodeUrl*!/
                                 });*/
                                console.log(result);
                                $('#alumniName').text(result.alumniName?result.alumniName:"");
                                $('#typeName').text(result.typeName?result.typeName:"");
                                $('#name').text(result.name?result.name:"");
                                $('#concatName').text(result.concatName?result.concatName:"");
                                $('#telephone').text(result.telephone?result.telephone:"");
                                $('#address').text(result.address?result.address:"");
                                $('#email').text(result.email?result.email:"");
                                $('#introduction').text(result.introduction?result.introduction:"");
                                $('#userAccount').text(result.userAccount?result.userAccount:"");
                                $('#tel').text(result.tel?result.tel:"");
                                $('#top').text(result.top?(result.top=='100'?"是":"否"):"否");


                                $('#introduction').html(result.introduction);
                                // 二维码
                                var src = '${pageContext.request.contextPath}/association/associationAction!doNotNeedSessionAndSecurity_getErWeiMa.action?associationId='+ result.id;
                                $('#event_qrCodeUrl').attr('src', src );
                                if(result.poster){
                                    $('#posterPic').append('<img src="'+ result.poster +'" width="150px" style="margin:10px" height="150px"/>');
                                }
                                if(result.logo){
                                    $('#logoPic').append('<img src="'+ result.logo +'" width="150px" style="margin:10px" height="150px"/>');
                                }


                            }
                        },
                        /*beforeSend: function () {
                         parent.$.messager.progress({
                         text: '数据加载中....'
                         });
                         },*/

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
            社团信息
        </legend>
        <table class="ta001">

            <tr>
                <th>
                    所属院系
                </th>
                <td colspan="3" id="alumniName" >
                    <input name="association.id"  id="associationId" type="hidden" value="${param.id}">

                </td>
            </tr>

            <tr>
                <th>
                    社团类型
                </th>
                <td colspan="3" id="typeName">

                </td>
            </tr>

            <tr>
                <th>
                    社团名称
                </th>
                <td colspan="3" id="name">

                </td>
            </tr>

            <tr>
                <th>
                    总部地址
                </th>
                <td colspan="3" id="address">

                </td>
            </tr>

            <tr>
                <th>
                    是否上幻灯片
                </th>
                <td colspan="3" id="top">

                </td>
            </tr>
        </table>
    </fieldset>

    <fieldset>
        <legend>
            会长信息
        </legend>
            <table class="ta001">
                <tr>
                    <th>
                        会长姓名
                    </th>
                    <td colspan="3" id="concatName">

                    </td>
                </tr>

                <tr>
                    <th>
                        联系电话
                    </th>
                    <td colspan="3" id="telephone">

                    </td>
                </tr>

                <tr>
                    <th>
                        电子邮箱
                    </th>
                    <td colspan="3" id="email">

                    </td>
                </tr>
            </table>
    </fieldset>

    <fieldset>
        <legend>
            社团简介
        </legend>
            <table class="ta001">
                <tr>
                    <th>社团简介</th>
                    <td>
                        <textarea id="introduction" rows="5" cols="100" name="association.introduction" style="width: 700px; height: 150px;" disabled></textarea>
<%--
                        <input type="button"  id="checkPoster" value="查看海报" />
--%>
                    </td>
                </tr>
                <tr>
                    <th>Logo</th>
                    <td>
                        <div style="float:left;width:180px;" id="logoPic">
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>海报图片</th>
                    <td>
                        <div style="float:left;width:180px;" id="posterPic">
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>
                        社团二维码
                    </th>
                    <td colspan="3">
                        <div id="qrCodeUrl">

                            <div style="padding: 5px 5px 12px;"><img id="event_qrCodeUrl" width="150px" height="150px"/></div>

                        </div>
                    </td>
                </tr>
            </table>
    </fieldset>

    <fieldset>
        <legend>
            管理员信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    帐号
                </th>
                <td colspan="3" id="userAccount">

                </td>
            </tr>


            <tr>
                <th>
                    手机号
                </th>
                <td colspan="3" id="tel">

                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html></title>
</head>
<body>

</body>
</html>

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
                var editor;
                KindEditor.ready(function(K) {
                    editor =K.create('#content',{
                        fontSizeTable:['9px', '10px', '11px', '12px', '13px', '14px', '15px', '16px', '17px', '18px', '19px', '20px', '22px', '24px', '28px', '32px'],
                        uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
                        readonlyMode : true,
                        afterChange:function(){
                            this.sync();
                        }
                    });
                });
                var id = '${param.id}';
                $(function () {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/mobile/serv/znfxAction!getFxjhById.action',
                        data: { 'fxjh.id' : id },
                        dataType: 'json',
                        success: function (result) {
                            if (result.id != undefined) {
                                console.log(result);
                                $('#topic').text(result.topic?result.topic:"");
                                $('#number').text(result.number?result.number+'      (0表示无限制)':"");
                                $('#name').text(result.name?result.name:"");
                                $('#place').text(result.place?result.place:"");
                                $('#signupStartTime').text(result.signupStartTime?result.signupStartTime:"");
                                $('#signupEndTime').text(result.signupEndTime?result.signupEndTime:"");
                                $('#time').text(result.time?result.time:"");
                                $('#endTime').text(result.endTime?result.endTime:"");
                                $('#other').html(result.other?result.other:"");
                                if (result.needSignIn ==1) {
                                    $('#needSignIn').text("是")
                                } else {
                                    $('#needSignIn').text("否")
                                }
                                $('#signInCode').text(result.signInCode?result.signInCode:"");
                                var classinfo = '';
                                if(result.classInfoName){
                                    var array = result.classInfoName.split(',');
                                    for(var i = 0; i < array.length; i++){
                                        classinfo += array[i] +' '
                                    }
                                }
                                $('#classinfo').text(classinfo);
                                var arr=[];
                                var str;
                                var sercont = '';
                                if(result.services){
                                    str=result.services.split("|");

                                    for(var i=0;i<str.length;i++){
                                        arr.push(str[i].split("-"))
                                    }
                                    for(var k=0;k<arr.length;k++){
                                        if(arr[k][2]=="true"){
                                            sercont+='<p style="height: 20px;line-height: 20px;font-size: 12px">'+arr[k][1]+'</p>'

                                        }
                                    }
                                    $('#services').html(sercont?sercont:"");
                                }

                                editor.html(result.content);
                                if(result.poster){
                                    $('#posterPic').append('<img style="padding: 5px" id="posterPic" width="150px" height="150px" src="'+result.poster+'"/>');
                                }
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
            返校计划
        </legend>
        <table class="ta001">

            <tr>
                <th>
                    组织主题
                </th>
                <td colspan="3" id="topic" >

                </td>
            </tr>

            <tr>
                <th>
                   地点
                </th>
                <td colspan="3" id="place" >

                </td>
            </tr>

            <tr>
                <th>
                    主办方
                </th>
                <td colspan="3" id="name" >

                </td>
            </tr>

            <tr>
                <th>
                    计划人数
                </th>
                <td colspan="3" id="number">
                </td>
            </tr>

            <tr>
                <th>
                    报名开始
                </th>
                <td colspan="3" id="signupStartTime">
                </td>
            </tr>

            <tr>
                <th>
                    报名截止
                </th>
                <td colspan="3" id="signupEndTime">

                </td>
            </tr>

            <tr>
                <th>
                    开始时间
                </th>
                <td colspan="3" id="time">

                </td>
            </tr>
            <tr>
                <th>
                    结束时间
                </th>
                <td colspan="3" id="endTime">

                </td>
            </tr>

            <tr>
                <th>
                    班级信息
                </th>
                <td colspan="3" id="classinfo">

                </td>
            </tr>

            <tr>
                <th>
                    需要签到
                </th>
                <td colspan="3" name="fxjh.needSignIn" id="needSignIn">
                </td>
            </tr>
            <tr>
                <th>
                 签到码
                 </th>
                <td colspan="3" id="signInCode" name="fxjh.signInCode">
                </td>
            </tr>

            <tr>
                <th>
                    描述
                </th>
                <td colspan="3">
                    <textarea id="other" name="fxjh.other" style="width: 700px; height: 120px;" data-options="editable:false" disabled="disabled"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                    详情
                </th>
                <td colspan="3">
                    <textarea id="content" name="fxjh.content" style="width: 700px; height: 300px;" data-options="editable:false" disabled="disabled"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                    计划海报图片
                </th>
                <td colspan="3">
                    <div id="posterPic">

                    </div>
                </td>
            </tr>
            <tr>
                <th>
                   提供的服务
                </th>
                <td colspan="3" id="services">

                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>

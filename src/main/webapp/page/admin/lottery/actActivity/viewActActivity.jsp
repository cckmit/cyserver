<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    long check = WebUtil.toLong(request.getParameter("check")) ;
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
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=j5bQGIAXNd5rrnfu83is90HhX1n3xvMd"></script>
    <jsp:include page="../../../../inc.jsp"></jsp:include>


    <style>
        .ta001 th{
            width: 107px;
        }
        .ta001 td{
            height: 33px;
        }
        #bdMap{
            height: 394px;
        }

    </style>
    <script type="text/javascript">
        var picCount;
        var editor;
        KindEditor.ready(function(K) {
            editor =K.create('#introduction',{
                fontSizeTable:['9px', '10px', '11px', '12px', '13px', '14px', '15px', '16px', '17px', '18px', '19px', '20px', '22px', '24px', '28px', '32px'],
                uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
                readonlyMode : true,
                afterChange:function(){
                    this.sync();
                }
            });
        });
        $(function () {
            var id = $('#actActivityId').val();
            $.ajax({
                url: '${pageContext.request.contextPath}/actActivity/actActivityAction!getById.action',
                data: { 'actActivityId' : id },
                dataType: 'json',
                success: function (result) {

                    if (result.id != undefined) {

                        $('form').form('load', {

                            '.type': result.type
                        });
                        console.log(result);
                        $('#name').val(result.name?result.name:"");
                        $('#type').val(result.type?result.type:"");
                        $('#startTime').val(result.startTime?result.startTime:"");
                        $('#endTime').val(result.endTime?result.endTime:"");
                        $('#sStartTime').val(result.signUpStartTime?result.signUpStartTime:"");
                        $('#sEndTime').val(result.signUpEndTime?result.signUpEndTime:"");
                        $('#organizer').val(result.organizer?result.organizer:"");
                        $('#sort').val(result.sort?result.sort:"");

                        editor.html(result.introduction);

                        if(result.picSny){
                            $('#logoPic').append('<img src="'+ result.picSny +'" width="80px" style="margin:10px" height="80px"/>');
                        }
                        if(result.fileList != null || $.trim(result.fileList) != '' ) {
                            var pics = result.fileList
                            for (var i in pics) {
                                $('#pagePic').append('<div style="float:left;width:180px;"><img src="' + pics[i].picUrl + '" width="150px" height="150px"/><div class="bb001" onclick="removeSchoolLogo(this)"></div><input type="hidden" name="actActivity.pictureUrls" value="' + pics[i].picUrl + '"/></div>');
                                picCount++;
                            }
                        }

                    }
                },

                complete: function () {

                    parent.$.messager.progress('close');

                }
            });
        });


        $(document).ready(function () {

        });


    </script>
</head>

<body>
<form method="post" class="form" style="position: relative">
    <fieldset>
        <legend>
            企业信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    活动名称
                </th>
                <td colspan="3">
                    <input id="actActivityId" name="actActivity.id" type="hidden" value="${param.id}"/>
                    <input id="name" name="actActivity.name" class="easyui-validatebox"
                           style="width: 250px;"
                           maxlength="40" value="" readonly/>
                </td>
            </tr>

            <tr>
                <th>活动类型</th>
                <td>
                    <input id="type" name="actActivity.type" class="easyui-validatebox" style="width: 250px;" value="" readonly/>
                </td>
            </tr>

            <tr>
                <th>活动开始时间</th>
                <td>
                    <input id="startTime" name="actActivity.startTime" class="easyui-validatebox" style="width: 150px;" data-options="editable:false" value=""readonly/>
                </td>
            </tr>

            <tr>
                <th>
                    活动结束时间
                </th>
                <td colspan="3">
                    <input id="endTime" name="actActivity.endTime" class="easyui-validatebox" style="width: 150px;" data-options="editable:false" value=""readonly/>
                </td>
            </tr>

            <tr>
                <th>报名开始时间</th>
                <td colspan="3">
                    <input id="sStartTime" name="actActivity.signUpStartTime" class="easyui-validatebox" style="width: 150px;" data-options="editable:false" value=""readonly/>
                </td>
            </tr>


            <tr>
                <th>
                    报名结束时间
                </th>
                <td colspan="3">
                    <input id="sEndTime" name="actActivity.signUpEndTime" class="easyui-validatebox" style="width: 150px;" data-options="editable:false" value=""readonly/>
                </td>

            </tr>

            <tr>
                <th>
                    活动介绍
                </th>
                <td colspan="3">
                    <textarea id="introduction" rows="10" cols="100" name="actActivity.introduction" style="width: 700px; height: 250px;" readonly></textarea>
                </td>
            </tr>


            <tr>
                <th>
                    主办单位
                </th>
                <td colspan="3">
                    <input  id="organizer" name="actActivity.organizer" class="easyui-validatebox"
                            style="width: 250px;"
                            data-options="required:true"
                            maxlength="40" value=""readonly/>
                </td>
            </tr>
            <tr>
                <th>
                    排序
                </th>
                <td colspan="3">
                    <input id="sort" name="actActivity.sort" class="easyui-validatebox" style="width: 250px;" value="" readonly/>
                </td>
            </tr>
            <tr>
                <th>活动图标</th>
                <td>
                    <%--<input type="button" id="logo_upload_button" value="上传活动图片">(Logo大小为：80px x 80px)--%>

                    <div id="logoPic"></div>
                </td>
            </tr>
            <tr>
                <th>图片上传</th>
                <td>
                    <input type="button"  id="pic_upload_button" value="请选择图片" />
                    <span style="color: red">(最少3张，最多6张)</span>
                </td>
            </tr>
            <tr>
                <th>
                    轮播图片
                </th>
                <td colspan="3">
                    <div id="pagePic" class="container" style="padding: 20px"></div>
                    <%--<input type="hidden" name="share.pagePictures" id="pagePic" value="" />--%>
                </td>
            </tr>

        </table>
    </fieldset>
</form>
</body>
</html>
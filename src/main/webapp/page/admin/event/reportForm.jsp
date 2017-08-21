<%--
  Created by IntelliJ IDEA.
  User: cha0res
  Date: 1/6/17
  Time: 5:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/authority" prefix="authority"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('#description',{
                fontSizeTable:['9px', '10px', '11px', '12px', '13px', '14px', '15px', '16px', '17px', '18px', '19px', '20px', '22px', '24px', '28px', '32px'],
                uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
                afterChange:function(){
                    this.sync();
                }
            });
        });

        var action = '${param.action}';
        var id = '0';

        $(function(){
            if(action != '0'){
                id = '${param.id}';
               /* $('#clickTr').show();*/
                if(action == '1'){
                    $('input').attr('readonly', true);
                    $('.easyui-combobox').combobox({'readonly': true});
                    $('textarea').attr('readonly', true);
                    $('#jubao1').attr('disabled', true);
                    $('#jubao2').attr('disabled', true);
                }
            }

            if(action != '0'){
                id = '${param.id}';
                if(action == '2'){
                    $('#reportPerson').attr('readonly', true);
                    $('#bussName').attr('readonly', true);
                    $('#content').attr('readonly', true);
                    $('#bussType').attr('readonly', true);
                    $('#handleStatus').attr('readonly', true);
                }
            }


        /*    var button = $("#pic_upload_button"), interval;
            new AjaxUpload(button, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
                name : 'upload',
                onSubmit : function(file, ext) {
                    if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext))) {
                        $.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
                        return false;
                    }
                    $.messager.progress({
                        text : '图片正在上传,请稍后....'
                    });
                },
                onComplete : function(file, response) {
                    $.messager.progress('close');
                    var msg = $.parseJSON(response);
                    if (msg.error == 0) {
                        $('#productPic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removePic(this, \'#pic_upload_button\')"></div><input type="hidden" id="channelIcon" name="enterpriseProduct.posterPic" value="'+msg.url+'"/></div>');
                        $("#pic_upload_button").prop('disabled', 'disabled');
                    } else {
                        $.messager.alert('提示', msg.message, 'error');
                    }
                }
            });*/

            if(id != '0'){

                $.ajax({
                    url: '${pageContext.request.contextPath}/report/reportAction!getById.action',
                    data: {
                        'reportId': id
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'report.id': result.id,
                                'report.userId': result.userId,
                                'report.bussId': result.bussId,
                                'report.reportPerson': result.reportPerson,
                                'report.bussName': result.bussName,
                                'report.content': result.content,
                                'report.bussType': result.bussType,
                                'report.handleStatus': result.handleStatus,
                                'report.handleUserId': result.handleUserId,
                                'report.handerName': result.handerName,
                                'report.isTrue': result.isTrue,
                                'report.pictureUrls':result.pictureUrls,
                                'report.handleDesc': result.handleDesc
                            });
                            var bussType = result.bussType;
                            var handleStatus = result.handleStatus;
                            if(bussType == 10){
                                $('#bussType').val("活动");
                            }else if(bussType == 15){
                                $('#bussType').val("花絮");
                            }else if(bussType == 20){
                                $('#bussType').val("社团");
                            }else if(bussType == 30){
                                $('#bussType').val("返校计划");
                            }else if(bussType == 40){
                                $('#bussType').val("校友企业");
                            }else if(bussType == 50){
                                $('#bussType').val("校友产品");
                            }
                            if(handleStatus == 10){
                                $('#handleStatus').val("处理中");
                            }else if(handleStatus == 20){
                                $('#handleStatus').val("已处理");
                            }else if(handleStatus == 30){
                                $('#handleStatus').val("举报不实");
                            }
                            if(handleStatus == 10 && action == '1'){
                                $('#handleUserId1').hide();
                                $('#handleDesc1').hide();
                                $('#isTrue1').hide();
                            }
                            if( action == '2'&& handleStatus == 10){
                                $('#handleUserId1').hide();
                            }



                          /*  $('#clickNumber').text(result.clickNumber);
                            var productPic = result.posterPic;
                            uploadPic("#pic_upload_button", "enterpriseProduct.posterPic", "#productPic");
                            var upload_button_name = "#pic_upload_button";
                            var pic_name = "enterpriseProduct.posterPic";
                            var pic_div_name = "#productPic";
                            uploadPic(upload_button_name, pic_name, pic_div_name);
                            if(productPic != null && productPic != ''){
                                buildPicDIV(upload_button_name, pic_name, pic_div_name, productPic);
                                $(upload_button_name).prop('disabled', 'disabled');
                            }*/

                           /* editor.html(result.description);
                            if(action == '1'){
                                editor.readonly();
                                $(".bb001").removeAttr("onclick");
                            }*/
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

        function submitForm($dialog, $grid, $pjq) {
            var url;
            if(id != '0'){
                url = '${pageContext.request.contextPath}/report/reportAction!update.action'
            }else {
               /* url = '${pageContext.request.contextPath}/enterprise/enterpriseProductAction!save.action'*/
            }
            if ($('form').form('validate')) {
                $.ajax({
                    url : url,
                    data : $('form').serialize(),
                    dataType : 'json',
                    success : function(result)
                    {
                        if (result.success)
                        {
                            $grid.datagrid('reload');
                            $dialog.dialog('destroy');
                            $pjq.messager.alert('提示', result.msg, 'info');
                        } else
                        {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    },
                    beforeSend : function()
                    {
                        $pjq.messager.progress({
                            text : '数据提交中....'
                        });
                    },
                    complete : function()
                    {
                        $pjq.messager.progress('close');
                    }
                });
            }
        }

        function buildPicDIV(upload_button_name, picName, picDivName, picURL)
        {
            $(picDivName).append(
                    '<div style="float:left;width:180px;">'+
                    '<img src="'+picURL+'" width="150px" height="150px"/>'+
                    '<div class="bb001" onclick="removePic(this,\'' + upload_button_name + '\')">'+
                    '</div>'+
                    '<input type="hidden" name="'+picName+'" value="'+picURL+'"/></div>'
            );
        }
        function uploadPic(upload_button_name, picName, picDivName)
        {
            var button = $(upload_button_name), interval;
            new AjaxUpload(button,
                    {
                        action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
                        name : 'upload',
                        onSubmit : function(file, ext)
                        {
                            if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext)))
                            {
                                $.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
                                return false;
                            }
                            $.messager.progress({text : '图片正在上传,请稍后....'});
                        },
                        onComplete : function(file, response)
                        {
                            $.messager.progress('close');
                            var resp = $.parseJSON(response);

                            if (resp.error == 0)
                            {
                                $(picDivName).append(
                                        '<div style="float:left;width:180px;">'+
                                        '<img src="'+resp.url+'" width="150px" height="150px"/>'+
                                        '<div class="bb001" onclick="removePic(this,\'' + upload_button_name + '\')">'+
                                        '</div>'+
                                        '<input type="hidden" name="'+picName+'" value="'+resp.url+'"/></div>'
                                );


                                $(upload_button_name).prop('disabled', 'disabled');
                            }
                            else
                            {
                                $.messager.alert('提示', response, 'error');
                            }
                        }
                    });

        }


        function removePic(pic, upload_button_name)
        {
            $(pic).parent().remove();
            $(upload_button_name).prop('disabled', false);
        }

    </script>
</head>
<body>
<form method="post" id="editProcutForm">
    <table class="ta001">
        <input type="hidden" name="report.id" />
        <input type="hidden" name="report.userId" />
        <input type="hidden" name="report.bussId" />
        <tr>
            <th>举报人</th>
            <td>
                <input id="reportPerson" name="report.reportPerson" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>活动名称</th>
            <td>
                <input id="bussName" name="report.bussName" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>举报内容</th>
            <td>
                <input id="content" name="report.content" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>举报类型</th>
            <td>
                <input type="hidden" name="report.bussType" />
                <input id="bussType"  class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>处理状态</th>
            <td>
                <input type="hidden" name="report.handleStatus" />
                <input id="handleStatus"  class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>

        <tr id="handleUserId1">
            <th>处理人</th>
            <td>
                <input type="hidden" name="report.handleUserId" />
                <input id="handleUserId"  name="report.handerName" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>



      <%--  <tr>
            <th>
                新闻封面图片
            </th>
            <td colspan="3">
                <div id="newsPic">
                    <c:if test="${report.pictureUrls !=null and report.pictureUrls != ''}">
                        <div style="float:left;width:180px;">
                            &lt;%&ndash;<img src="${report.pictureUrls}" width="150px" height="150px"/>
                            <div class="bb001" onclick="removeNewsPic(this)"></div>&ndash;%&gt;
                            <input type="" name="report.pictureUrls" value="${report.pictureUrls}"/>
                        </div>
                    </c:if>
                </div>
            </td>
        </tr>--%>

        <tr id="isTrue1">
            <th>举报是否属实：</th>
            <td>
                <label><input name="report.isTrue" id="jubao1" type="radio" value="0" />否 </label>
                <label><input name="report.isTrue" id="jubao2" type="radio" value="1" />是 </label>
            </td>
        </tr>



        <tr id="handleDesc1">
            <th>结果描述</th>
            <td>
                <textarea id="handleDesc" rows="5" cols="100" maxlength="500" name="report.handleDesc" style="width: 700px; height: 50px;" ></textarea>
            </td>
        </tr>

    </table>
</form>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: cha0res
  Date: 1/6/17
  Time: 5:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/authority" prefix="authority"%>
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


        var action = '${param.action}';
        var id = '0';

        var enterpriseId = '${param.enterpriseId}';
        $(function(){
            if(action != '0'){
                id = '${param.id}';
                $('#clickTr').show();
                if(action == '1'){
                    $('input').attr('readonly', true);
                    $('.easyui-combobox').combobox({'readonly': true});
                }else{
                    $('#enterpriseId').combobox({'readonly': true});
                }

            }else{
                if(enterpriseId){
                    $('#enterpriseId').combobox({'readonly': true});
                    $('#enterpriseId').combobox('setValue', enterpriseId);
                }
            }

            var button = $("#pic_upload_button"), interval;
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
                        $('#productPic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="80px" height="80px"/><div class="bb001" style="top: -84px;left: 62px;" onclick="removePic(this, \'#pic_upload_button\')"></div><input type="hidden" id="channelIcon" name="enterpriseTeam.pic" value="'+msg.url+'"/></div>');
                        $("#pic_upload_button").prop('disabled', 'disabled');
                    } else {
                        $.messager.alert('提示', msg.message, 'error');
                    }
                }
            });


            if(id != '0'){
                $.ajax({
                    url: '${pageContext.request.contextPath}/enterprise/enterpriseTeamAction!getById.action',
                    data: {
                        'enterpriseTeam.id': id
                    },
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'enterpriseTeam.id': result.id,
                                'enterpriseTeam.fullName': result.fullName,
                                'enterpriseTeam.position': result.position,
                                'enterpriseTeam.isAlumni': result.isAlumni,
                                'enterpriseTeam.classinfo': result.classinfo,
                                'enterpriseTeam.enterpriseId': result.enterpriseId,
                                'enterpriseTeam.sort': result.sort
                            });

                            var productPic = result.pic;
                            uploadPic("#pic_upload_button", "enterpriseTeam.pic", "#productPic");
                            var upload_button_name = "#pic_upload_button";
                            var pic_name = "enterpriseTeam.pic";
                            var pic_div_name = "#productPic";
                            uploadPic(upload_button_name, pic_name, pic_div_name);
                            if(productPic != null && productPic != ''){
                                buildPicDIV(upload_button_name,pic_name, pic_div_name, productPic);
                                $(upload_button_name).prop('disabled', 'disabled');
                            }


                            if(action == '1'){
                                $(".bb001").removeAttr("onclick");
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
            }else{
                $('#sort').val(0);
            }
        });

        function submitForm($dialog, $grid, $pjq) {
            var url;
            if(id != '0'){
                url = '${pageContext.request.contextPath}/enterprise/enterpriseTeamAction!update.action'
            }else {
                url = '${pageContext.request.contextPath}/enterprise/enterpriseTeamAction!save.action'
            }
            if($('input[name="enterpriseTeam.pic"]').val()==undefined || $('input[name="enterpriseTeam.pic"]').val() == ''){
                parent.$.messager.alert('提示', '请上传头像', 'error');
                return false;
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
                    '<img src="'+picURL+'" width="80px" height="80px"/>'+
                    '<div class="bb001" style="top: -84px;left: 62px;" onclick="removePic(this,\'' + upload_button_name + '\')">'+
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
                                        '<img src="'+resp.url+'" width="80px" height="80px"/>'+
                                        '<div class="bb001" style="top: -84px;left: 62px;" onclick="removePic(this,\'' + upload_button_name + '\')">'+
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
        <input type="hidden" name="enterpriseTeam.id" />
        <tr>
            <th>姓名</th>
            <td>
                <input id="fullName" name="enterpriseTeam.fullName" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="required:true,validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>职位</th>
            <td>
                <input id="position" name="enterpriseTeam.position" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="required:true,validType:'customRequired'"
                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>校友企业</th>
            <td id="enterpriseTd">
                <input id="enterpriseId" name="enterpriseTeam.enterpriseId" class="easyui-combobox" style="width: 150px;" value=""
                       data-options="editable:false,required:true,
							        valueField: 'id',
							        textField: 'name',
							        url: '${pageContext.request.contextPath}/enterprise/enterpriseAction!doNotNeedSecurity_getEnterpriseList.action'" />
            </td>
        </tr>
        <th>
            是否为校友
        </th>
        <td id="xiaoyou">
            <select id="isAlumni" class="easyui-combobox" data-options="editable:false,
            onSelect: function(isAlumni){
            if ((isAlumni.value)=='1' )
                                            {
                                                $('#Tclass').show();
                                            }else{
                                                $('#Tclass').hide();
                                            }
            }"  name="enterpriseTeam.isAlumni" style="width: 150px;">
                <option value="1" >是</option>
                <option value="0" >否</option>
            </select>

        </td>
        <tr>
            <th>班级信息</th>
            <td id="Tclass">
                <input id="classinfo" name="enterpriseTeam.classinfo" class="easyui-validatebox"
                       style="width: 150px;"

                       maxlength="40" />
            </td>
        </tr>
        <tr>
            <th>排序</th>
            <td id="Tsort">
                <input id="sort" name="enterpriseTeam.sort" class="easyui-validatebox"
                       style="width: 150px;" type="number"/>
            </td>
        </tr>
        <tr>
            <th>头像</th>
            <td>
                <input type="button" id="pic_upload_button" value="上传头像">(头像大小为：80px x 80px)
                <div id="productPic">
                </div>
            </td>
        </tr>
    </table>
</form>
</body>
</html>

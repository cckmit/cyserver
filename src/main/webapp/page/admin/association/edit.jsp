<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

                        $('form').form('load', {

                            /*'association.alumniId': result.alumniName,
                             'association.typeId': result.typeId,
                             'association.name': result.name,*/
                            'association.concatName': result.concatName,
                            'association.telephone': result.telephone,
                            'association.address': result.address,
                            'association.email': result.email,
                            'association.introduction': result.introduction,
                            'association.userAccount': result.userAccount,
                            'association.userPassword': result.userPassword,
                            'association.tel': result.tel,
                            'association.userId': result.userId,
                            'association.top': result.top
                        });
                        $('#alumniName').text(result.alumniName?result.alumniName:"");
                        $('#typeName').text(result.typeName?result.typeName:"");
                        $('#name').text(result.name?result.name:"");

                        $('#introduction').html(result.introduction);
                        // 二维码
                        var src = '${pageContext.request.contextPath}/association/associationAction!doNotNeedSessionAndSecurity_getErWeiMa.action?associationId='+ result.id;
                        $('#event_qrCodeUrl').attr('src', src );
                        var associationLogo = result.logo;
                        var associationPoster = result.poster;

                        uploadPic("#logo_upload_button", "association.logo", "#logoPic", "logo");
                        uploadPic("#poster_upload_button", "association.poster", "#posterPic", "poster");
                        var upload_button_name_logo = "#logo_upload_button";
                        var pic_name_logo = "association.logo";
                        var pic_div_name_logo = "#logoPic";
                        var pic_id_logo = "logo";

                        var upload_button_name_poster = "#poster_upload_button";
                        var pic_name_poster = "association.poster";
                        var pic_div_name_poster = "#posterPic";
                        var pic_id_poster = "poster";

                        uploadPic(upload_button_name_logo, pic_name_logo, pic_div_name_logo, pic_id_logo);
                        uploadPic(upload_button_name_poster, pic_name_poster, pic_div_name_poster, pic_id_poster);

                        if(associationLogo != null && associationLogo != '' && !$('#logo').val()){
                            buildPicDIV(upload_button_name_logo, pic_name_logo, pic_div_name_logo, associationLogo,pic_id_logo);
                            $(upload_button_name_logo).prop('disabled', 'disabled');
                        }
                        if(associationPoster != null && associationPoster != '' && !$('#poster').val()){
                            buildPicDIV(upload_button_name_poster, pic_name_poster, pic_div_name_poster, associationPoster, pic_id_poster);
                            $(upload_button_name_poster).prop('disabled', 'disabled');
                        }

                    }
                },

                complete: function () {

                    parent.$.messager.progress('close');

                }
            });
        });

        function submitForm($dialog, $grid, $pjq)
        {
            if ($('form').form('validate'))
            {
                $.ajax({
                    url : '${pageContext.request.contextPath}/association/associationAction!updateAssociation.action',
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
                        parent.$.messager.progress({
                            text : '数据提交中....'
                        });
                    },
                    complete : function()
                    {
                        parent.$.messager.progress('close');
                    }
                });
            }
        }
        function buildPicDIV(upload_button_name, picName, picDivName, picURL, picId)
        {
            $(picDivName).append(
                '<div style="float:left;width:180px;">'+
                '<img src="'+picURL+'" width="150px" height="150px"/>'+
                '<div class="bb001" onclick="removePic(this,\'' + upload_button_name + '\')">'+
                '</div>'+
                '<input type="hidden" id="'+picId+'" name="'+picName+'" value="'+picURL+'"/></div>'
            );
        }
        function uploadPic(upload_button_name, picName, picDivName, picId)
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
                                '<input type="hidden" id="'+picId+'" name="'+picName+'" value="'+resp.url+'"/></div>'
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
<form method="post" class="form">
    <fieldset>
        <legend>
            社团信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    所属院系
                </th>
                <td colspan="3">
                    <input name="association.id"  id="associationId" type="hidden" value="${param.id}">

                    <span id="alumniName"></span>
                </td>
            </tr>



            <tr>
                <th>
                    社团类型
                </th>
                <td colspan="3" id="typeName">
                    <input name="association.typeId" class="easyui-combobox" style="width: 150px;" value=""
                           data-options="editable:false,required:true,
							        valueField: 'dictValue',
							        textField: 'dictName',
							        url: '${pageContext.request.contextPath}/association/associationAction!doNotNeedSecurity_getAssociationType.action'" />

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
                <td colspan="3">

                    <input name="association.address" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="validType:'customRequired'"
                           maxlength="30"/>
                </td>
            </tr>
            <tr>
                <th>
                    是否重点社团
                </th>
                <td colspan="3">

                    <select name="association.top" class="easyui-combobox"
                            style="width: 150px;" data-options="editable:false">
                        <option value="0">否</option>
                        <option value="100">是</option>
                    </select>
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
                <td colspan="3">

                    <input name="association.concatName" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" />
                </td>
            </tr>

            <tr>
                <th>
                    联系电话
                </th>
                <td colspan="3">

                    <input name="association.telephone" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="required:true,validType:'telePhone'"
                           maxlength="30" />
                </td>
            </tr>

            <tr>
                <th>
                    电子邮箱
                </th>
                <td colspan="3">

                    <input name="association.email" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="validType:'customRequired'"
                           maxlength="30" />
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
                    <textarea id="introduction" rows="5" cols="100" name="association.introduction" style="width: 700px; height: 150px;" ></textarea>
                </td>
            </tr>
            <tr>
                <th>Logo</th>
                <td>
                    <input type="button" id="logo_upload_button" value="上传log">
                    <div id="logoPic">
                    </div>
                </td>
            </tr>
            <tr>
                <th>海报图片</th>
                <td>
                    <input type="button" id="poster_upload_button" value="上传海报图片">
                    <div id="posterPic">
                    </div>
                </td>
            </tr>
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
                    <td colspan="3">
                        <input name="association.userId" type="hidden"/>
                        <input name="association.userAccount" class="easyui-validatebox"
                               readonly="readonly"
                               data-options="required:true,validType:'customRequired'"
                               style="width: 150px;"
                               maxlength="30" />
                    </td>
                </tr>

                <tr>
                    <th>
                        密码
                    </th>
                    <td colspan="3">

                        <input name="association.userPassword" type="password" class="easyui-validatebox"
                               data-options="required:true,validType:'customRequired'"
                               style="width: 150px;"
                               maxlength="30"/>
                    </td>
                </tr>

                <tr>
                    <th>
                        手机号
                    </th>
                    <td colspan="3">

                        <input name="association.tel" class="easyui-validatebox"
                               data-options="required:true,validType:'customRequired'"
                               style="width: 150px;"
                               maxlength="30" value=""/>
                    </td>
                </tr>
            </table>
    </fieldset>
</form>
</body>
</html>

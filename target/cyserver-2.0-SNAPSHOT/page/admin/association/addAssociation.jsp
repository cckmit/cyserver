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

        function submitForm($dialog, $grid, $pjq)
        {
            if ($('form').form('validate'))
            {
                $.ajax({
                    url : '${pageContext.request.contextPath}/association/associationAction!saveAssociation.action',
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

        $(function () {
            uploadPic("#logo_upload_button", "association.logo", "#logoPic", "logo");
            uploadPic("#poster_upload_button", "association.poster", "#posterPic", "poster");
        });
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
                            $.messager.alert('提示', response.message, 'error');
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
                    社团类型
                </th>
                <%--<td colspan="3">
                    <input name="association.typeId" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" value=""/>
                </td>--%>
                <td>
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
                <td colspan="3">

                    <input name="association.name" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" value=""/>
                </td>
            </tr>
            <tr>
                <th>
                    总部地址
                </th>
                <td colspan="3">

                    <input name="association.address" class="easyui-validatebox"
                           style="width: 150px;"
                           maxlength="30" value=""/>
                </td>
            </tr>
            <tr>
                <th>
                    是否上幻灯片
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
                        <div id="logoPic"></div>
                    </td>
                </tr>
                <tr>
                    <th>海报图片</th>
                    <td>
                        <input type="button" id="poster_upload_button" value="上传海报图片">
                        <div id="posterPic"></div>
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
                       maxlength="30" value=""/>
            </td>
        </tr>

        <%--<tr>
            <th>
                成员人数
            </th>
            <td colspan="3">

                <input name="association.memberCount" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="required:true,validType:'customRequired'"
                       maxlength="30" value=""/>
            </td>
        </tr>--%>

        <tr>
            <th>
                联系电话
            </th>
            <td colspan="3">

                <input name="association.telephone" class="easyui-validatebox"
                       style="width: 150px;"
                       data-options="required:true,validType:'telePhone'"
                       maxlength="30" value=""/>
            </td>
        </tr>

        <tr>
            <th>
                电子邮箱
            </th>
            <td colspan="3">

                <input name="association.email" class="easyui-validatebox"
                       style="width: 150px;"
                       maxlength="30" value=""/>
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

                        <input name="association.userAccount" class="easyui-validatebox"
                               style="width: 150px;"
                               data-options="required:true,validType:'customRequired'"
                               maxlength="30" value=""/>
                    </td>
                </tr>

                <tr>
                    <th>
                        密码
                    </th>
                    <td colspan="3">

                        <input name="association.userPassword" type="password" class="easyui-validatebox"
                               style="width: 150px;"
                               data-options="required:true,validType:'customRequired'"
                               maxlength="30" value=""/>
                    </td>
                </tr>

                <tr>
                    <th>
                        手机号
                    </th>
                    <td colspan="3">

                        <input name="association.tel" class="easyui-validatebox"
                               style="width: 150px;"
                               data-options="required:true,validType:'customRequired'"
                               maxlength="30" value=""/>
                    </td>
                </tr>
            </table>
    </fieldset>
</form>
</body>
</html>

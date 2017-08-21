<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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
        var submitForm = function ($dialog, $grid, $pjq) {
            if ($('form').form('validate')) {
                var url;
                url = '${pageContext.request.contextPath}/electronicBook/electronicBookAction!save.action';

                $.ajax({
                    url: url,
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.success) {
                            $grid.datagrid('reload');
                            $dialog.dialog('destroy');
                            $pjq.messager.alert('提示', result.msg, 'info');
                        } else {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    },
                    beforeSend: function () {
                        parent.$.messager.progress({
                            text: '数据提交中....'
                        });
                    },
                    complete: function () {
                        parent.$.messager.progress('close');
                    }
                });
            }
        };

        $(function () {
            uploadPic("#logo_upload_button", "electronicBook.logo", "#logoUrl", "logo");
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
                                        '<img src="'+resp.url+'" width="200px" height="260px"/>'+
                                        '<div class="bb001" style="top: -265px;left: 178px" onclick="removePic(this,\'' + upload_button_name + '\')">'+
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



        $(function()
        {
            var button = $("#file_upload_button"), interval;
            new AjaxUpload(button, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action?dir=file',
                name : 'upload',
                onSubmit : function(file, ext)
                {
                    if (!(ext && /^(pdf|txt|doc|docx|html|ppt)$/.test(ext)))
                    {
                        $.messager.alert('提示', '您上传的文件格式不对，请重新选择！', 'info');
                        return false;
                    }
                    $.messager.progress({
                        text : '文件正在上传,请稍后....'
                    });
                },
                onComplete : function(file, response)
                {
                    $.messager.progress('close');
                    if (response.indexOf('您还没有登录或登录已超时，请重新登录！') == 0)
                    {
                        $.messager.alert('提示', '您还没有登录或登录已超时，请重新登录！', 'error');
                    } else
                    {
                        var msg = $.parseJSON(response);
                        if (msg.error == 0)
                        {
                            $('#uploadUrl').html(
                                    "<div id='div'><a href='"+msg.url+"'>" + msg.url
                                    + "</a>&nbsp;<img id='img' class='bb001' onclick='clearUrl()' /><input name='electronicBook.upload' id='url' type='hidden' value='"+msg.url+"'/></div>");
                            $('#file_upload_button').prop('disabled', 'disabled')
                        } else
                        {
                            $.messager.alert('提示', msg.message, 'error');
                        }
                    }
                }
            });
        });
        function clearUrl()
        {
            $('#div').remove();
            $('#file_upload_button').prop('disabled', false);
        }
    </script>
</head>

<body>
<form method="post" id="activityForm" class="form">

    <fieldset>
        <legend>
            书籍信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    书籍名称
                </th>
                <td colspan="3">
                    <input name="electronicBook.name" class="easyui-validatebox" style="width: 150px;" />
                </td>

            </tr>
            <tr>
                <th>
                    作者
                </th>
                <td>
                    <input name="electronicBook.author" class="easyui-validatebox" style="width: 150px;" />
                </td>
            </tr>
            <tr>
                <th>
                    版本
                </th>
                <td>
                    <input name="electronicBook.version" class="easyui-validatebox" style="width: 150px;" />
                </td>
            </tr>
            <tr>
                <th>
                    书籍简介
                </th>
                <td colspan="3">
					<textarea id="introduction" rows="7" cols="100"
                              name="electronicBook.introduction"></textarea>
                </td>
            </tr>
            <tr>
                <th>Logo</th>
                <td>
                    <input type="button" id="logo_upload_button" value="上传log">(图片大小为：400px x 520)
                    <div id="logoUrl"></div>
                </td>
            </tr>
            <tr>
                <th>附件上传</th>
                <td>
                    <input type="button" id="file_upload_button" value="上传文件">(<span style="color: red">附件大小：20M之内</span>，附件格式：pdf，txt,html，doc,docx,ppt)
                </td>
            </tr>
            <tr>
                <th>
                    上传的文件
                </th>
                <td>
                    <span id="uploadUrl"></span>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>

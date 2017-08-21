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
        $(function () {
            var id = $('#enterpriseId').val();
                $.ajax({
                    url: '${pageContext.request.contextPath}/electronicBook/electronicBookAction!getById.action',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'electronicBook.id': result.id,
                                'electronicBook.name': result.name,
                                'electronicBook.author': result.author,
                                'electronicBook.version': result.version,
                                'electronicBook.introduction': result.introduction,
                                'electronicBook.uploadOrganization': result.uploadOrganization,
                                'electronicBook.createDate': result.createDate
                            });
                            var electronicBookLogo = result.logo;
                            var electronicBookUpload = result.uploadUrl;

                            uploadPic("#logo_upload_button", "electronicBook.logo", "#logoUrl", "logo");
                            var upload_button_name_logo = "#logo_upload_button";
                            var pic_name_logo = "electronicBook.logo";
                            var pic_div_name_logo = "#logoUrl";
                            var pic_id_logo = "logo";


                            uploadPic(upload_button_name_logo, pic_name_logo, pic_div_name_logo, pic_id_logo);

                            if(electronicBookLogo != null && electronicBookLogo != '' && !$('#logo').val()){
                                buildPicDIV(upload_button_name_logo, pic_name_logo, pic_div_name_logo, electronicBookLogo,pic_id_logo);
                                $(upload_button_name_logo).prop('disabled', 'disabled');
                            }

                            if(electronicBookUpload && electronicBookUpload != 'null' && electronicBookUpload != ''){
                                buildFile(electronicBookUpload);
                                $("#file_upload_button").prop('disabled', 'disabled');
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

        });



        var submitForm = function ($dialog, $grid, $pjq) {
            if ($('form').form('validate')) {
                $.ajax({
                    url : '${pageContext.request.contextPath}/electronicBook/electronicBookAction!update.action',
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

        function buildPicDIV(upload_button_name, picName, picDivName, picURL, picId)
        {
            $(picDivName).append(
                    '<div style="float:left;width:180px;">'+
                    '<img src="'+picURL+'" width="200px" height="260px"/>'+
                    '<div class="bb001" style="top: -265px;left: 178px" onclick="removePic(this,\'' + upload_button_name + '\')">'+
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
                                    + "</a>&nbsp;<img class='iconImg ext-icon-cross' onclick='clearUrl()'><input name='electronicBook.upload' id='url' type='hidden' value='"+msg.url+"'/></div>");
                            $('#file_upload_button').prop('disabled', 'disabled')
                        } else
                        {
                            $.messager.alert('提示', msg.message, 'error');
                        }
                    }
                }
            });
        });

        function buildFile(url) {
            $('#uploadUrl').html(
                    "<div id='div'><a href='"+url+"'>" + url
                    + "</a>&nbsp;<img class='iconImg ext-icon-cross' onclick='clearUrl()'><input name='electronicBook.upload'  id='url' type='hidden' value='"+url+"'/></div>");
            $('#file_upload_button').prop('disabled', 'disabled')
        }
        function clearUrl()
        {
            $('#div').remove();
            $('#file_upload_button').prop('disabled', false);
        }
    </script>
</head>

<body>
<form method="post" id="activityForm" class="form">
    <input name="electronicBook.id" type="hidden"  id="electronicBookId" value="${param.id}">
    <fieldset>
        <legend>
            书籍信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    书籍名称
                </th>
                <td >
                    <input id="name" name="electronicBook.name"   style="width: 200px;" />
                </td>
            </tr>
            <tr>
                <th>
                    作者
                </th>
                <td>
                    <input name="electronicBook.author" style="width: 200px;" />
                </td>
            </tr>
            <tr>
                <th>
                    版本
                </th>
                <td>
                    <input name="electronicBook.version"  style="width: 200px;" />
                </td>
            </tr>
            <tr>
                <th>
                    发布时间
                </th>
                <td>
                    <input name="electronicBook.createDate"  style="width: 200px;" disabled="disabled"/>
                </td>
            </tr>
            <tr>
                <th>
                    书籍简介
                </th>
                <td colspan="3">
					<textarea rows="7" cols="100" name="electronicBook.introduction"></textarea>
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
            <tr>
                <th>Logo</th>
                <td>
                    <input type="button" id="logo_upload_button" value="上传log">(图片大小为：400px x 520)
                </td>
            </tr>
            <tr>
                <th>Logo</th>
                <td>
                    <div style="float:left;width:180px;" id="logoUrl">
                    </div>
                </td>
            </tr>

        </table>
    </fieldset>
</form>
</body>
</html>

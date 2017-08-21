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
            var id = $('#resumeBaseId').val();
            $.ajax({
                url: '${pageContext.request.contextPath}/resumeBase/resumeBaseAction!getById.action',
                data: { 'resumeBaseId' : id },
                dataType: 'json',
                success: function (result) {

                    if (result.id != undefined) {
                        $('form').form('load', {

                            'resumeBase.name': result.name,
                            'resumeBase.birthday': result.birthday,
                            'resumeBase.sex': result.sex,
                            'resumeBase.experience': result.experience,
                            'resumeBase.city': result.city,
                            'resumeBase.telephone': result.telephone,
                            'resumeBase.email': result.email,
                            'resumeBase.placeOrigin': result.placeOrigin
                        });

                        if(result.headPic!=undefined){
                            $('#headPic').append('<div style="float:left;margin-bottom: 5px; margin-left: 5px; position:relative"><img src="'+result.headPic+'" width="80px" height="80px"/><div class="bb001" style="top:-10px; left:65px; position:absolute"  onclick="removeProjectPic(this)"></div><input type="hidden" name="resumeBase.headPic" value="'+result.headPic+'"/></div>');
                            $("#news_upload_button").prop('disabled', 'disabled');
                        }


                    }
                },
                complete: function () {

                    parent.$.messager.progress('close');

                }
            });
            var button = $("#news_upload_button"), interval;
            new AjaxUpload(button, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadNews.action',
                name : 'upload',
                onSubmit : function(file, ext) {
                    if (!(ext && /^(jpg|png|gif|bmp)$/.test(ext))) {
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
                        $('#headPic').append('<div style="float:left;margin-bottom: 5px; margin-left: 5px; position:relative"><img src="'+msg.url+'" width="80px" height="80px"/><div class="bb001" style="top:-10px; left:90px; position:absolute"  onclick="removeProjectPic(this)"></div><input type="hidden" name="resumeBase.headPic" value="'+msg.url+'"/></div>');
                        $("#news_upload_button").prop('disabled', 'disabled');
                    } else {
                        $.messager.alert('提示', msg.message, 'error');
                    }
                }
            });
        });


        function removeProjectPic(newsPic) {
            $(newsPic).parent().remove();
            $("#news_upload_button").prop('disabled', false);
        }


        function submitForm($dialog, $grid, $pjq)
        {
            if ($('form').form('validate'))
            {
                $.ajax({
                    url : '${pageContext.request.contextPath}/resumeBase/resumeBaseAction!update.action',
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

    </script>
</head>

<body>
<form method="post" class="form" style="position: relative">
    <fieldset>
        <legend>
            简历信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    姓名
                </th>
                <td colspan="3">
                    <input name="resumeBase.id"  id="resumeBaseId" type="hidden" value="${param.id}">
                    <input name="resumeBase.name" class="easyui-validatebox"
                           style="width: 250px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="40" value=""/>
                </td>
            </tr>

            <tr>
                <th>性别</th>
                <td>
                    <input type="radio" name="resumeBase.sex" value="男" style="width: 20px;">男
                    <input type="radio" name="resumeBase.sex" value="女" style="width: 20px;">女
                </td>
            </tr>



            <tr>
                <th>
                    手机号
                </th>
                <td colspan="3">
                    <input  name="resumeBase.telephone" class="easyui-validatebox"
                            style="width: 250px;"
                            data-options="required:true,validType:'telePhone'"
                            maxlength="20" value=""/>
                </td>
            </tr>

            <tr>
                <th>
                    邮箱
                </th>
                <td colspan="3">
                    <input name="resumeBase.email" class="easyui-validatebox"
                           style="width: 250px;"
                           data-options="required:true,validType:'email'"
                           maxlength="40" value=""/>
                </td>
            </tr>

            <tr>
                <th>
                    出生日期
                </th>
                <td colspan="3">
                    <input  name="resumeBase.birthday" class="easyui-datetimebox"
                            style="width: 250px;"
                            data-options="required:true,validType:'customRequired'"
                            maxlength="40" value=""/>
                </td>

            </tr>
            <tr>
                <th>
                    工作经验
                </th>
                <td colspan="3">
                    <input name="resumeBase.experience" class="easyui-validatebox"
                           style="width: 250px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="40" value=""/>
                </td>
            </tr>
            <tr>
                <th>
                    所在城市
                </th>
                <td colspan="3">
                    <input name="resumeBase.city" class="easyui-validatebox"
                           style="width: 250px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="40" value=""/>
                </td>
            </tr>

            <tr>
                <th>
                    籍贯
                </th>
                <td colspan="3">
                    <input name="resumeBase.placeOrigin" class="easyui-validatebox"
                           style="width: 250px;"
                           maxlength="40" value=""/>
                </td>
            </tr>

            <tr>
                <th>
                    封面上传
                </th>
                <td colspan="3">
                    <input type="button" id="news_upload_button" value="上传图片">
                </td>
            </tr>
            <tr>
                <th>
                    头像
                </th>
                <td colspan="3">
                    <div id="headPic"></div>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>


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
                    url : '${pageContext.request.contextPath}/resumeBase/resumeBaseAction!save.action',
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
            uploadPic("#logo_upload_button", "enterprise.logo", "#logoPic", "logo");
            uploadPic("#poster_upload_button", "enterprise.posterPic", "#posterPic", "poster");
        });
        function uploadPic(upload_button_name, picName, picDivName, picId)
        {
            var w = 80;
            var h = 80;
            if(picId == "poster"){
                w = 200;
                h = 80;
            }

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
                                        '<div style="float:left; margin-bottom: 5px; margin-left: 5px; position:relative">'+
                                        '<img src="'+resp.url+'" width="'+w+'px" height="'+h+'px" />'+
                                        '<div class="bb001" style="left:'+(w-15)+'px; top:0;   position:absolute"  onclick="removePic(this,\'' + upload_button_name + '\')">'+
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
                    <input name="resumeBase.name" class="easyui-validatebox"
                           style="width: 250px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="40" value=""/>
                </td>
            </tr>
            <tr>
                <th>
                    手机用户
                </th>
                <td colspan="3">
                    <input  name="resumeBase.accountNum" class="easyui-validatebox"
                            style="width: 250px;"
                            data-options="required:true,validType:'customRequired'"
                            maxlength="40" value=""/>
                </td>
            </tr>


            <tr>
                <th>
                    出生日期
                </th>
                <td colspan="3">
                    <input  name="resumeBase.birthday" class="easyui-validatebox"
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
                           data-options="required:true,validType:'email'"
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
                           data-options="required:true,validType:'email'"
                           maxlength="40" value=""/>
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
                    联系人名称
                </th>
                <td colspan="3">
                    <input name="enterprise.linkman" class="easyui-validatebox"
                           style="width: 250px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="40" value=""/>
                </td>
            </tr>

            <tr>
                <th>
                    联系电话
                </th>
                <td colspan="3">
                    <input  name="enterprise.contactNumber" class="easyui-validatebox"
                            style="width: 250px;"
                            data-options="required:true,validType:'telePhone'"
                            maxlength="20" value=""/>
                </td>
            </tr>
            <tr>
                <th>
                    标语
                </th>
                <td colspan="3">
                    <input  name="enterprise.slogan" class="easyui-validatebox"
                            style="width: 250px;"
                            data-options="required:true,validType:'customRequired'"
                            maxlength="40" value=""/>
                </td>
            </tr>

            <tr>
                <th>
                    公司简介
                </th>
                <td colspan="3">
					<textarea id="summary" rows="7" cols="100"
                              name="enterprise.summary"></textarea>
                </td>
            </tr>

            <tr>
                <th>图文详情</th>
                <td>
                    <textarea id="description" rows="10" cols="100" name="enterprise.description" style="width: 700px; height: 250px;" ></textarea>
                </td>
            </tr>
            <tr>
                <th>Logo</th>
                <td>
                    <input type="button" id="logo_upload_button" value="上传log">(Logo大小为：80px x 80px)

                    <div id="logoPic"></div>
                </td>
            </tr>
            <tr>
                <th>海报图片</th>
                <td>
                    <input type="button" id="poster_upload_button" value="上传海报图片">(图片大小为：200px x 80px)
                    <div id="posterPic"></div>
                </td>
            </tr>

        </table>
    </fieldset>
</form>
</body>
</html>

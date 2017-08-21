
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
        //编辑器里面的内容图片上传
        KindEditor.ready(function(K) {
            K.create('#introduction',{
                items : [
                    'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                    'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                    'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                    'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
                    'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                    'anchor', 'link', 'unlink', '|', 'about'
                ],
                uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
                afterChange:function(){
                    this.sync();
                }
            });
        });


        function submitForm($dialog, $grid, $pjq)
        {
            if ($('form').form('validate'))
            {
                $.ajax({
                    url : '${pageContext.request.contextPath}/activityMusic/activityMusicAction!save.action',
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

        function createFile(url){
            $('#uploadUrl').html(
                "<div id='div'><a href='"+url+"'>" + msg.url
                + "</a>&nbsp;<img id='img' class='bb001' onclick='clearUrl()' /></div>");
            $('#file_upload_button').prop('disabled', 'disabled');
            $('#filePath').val(url);
        }

        $(function()
        {
            var button = $("#file_upload_button"), interval;
            new AjaxUpload(button, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action?dir=media',
                name : 'upload',
                onSubmit : function(file, ext)
                {
                    if (!(ext && /^(swf|flv|mp3|wav|wma|wmv|mid|avi|mpg|asf|rm|rmvb)$/.test(ext)))
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
                                + "</a>&nbsp;<img id='img' class='bb001' onclick='clearUrl()' /></div>");
                            $('#file_upload_button').prop('disabled', 'disabled');
                            $('#filePath').val(msg.url);
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
<form method="post" class="form" style="position: relative">
    <fieldset>
        <legend>
            活动音乐信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    活动名称
                </th>
                <td colspan="3">
                    <input name="activityMusic.activityId" class="easyui-combobox"
                           style="width: 250px;"
                           data-options="editable:false,required:true,
							        valueField: 'id',
							        textField: 'name',
							        url: '${pageContext.request.contextPath}/actActivity/actActivityAction!doNotNeedSessionAndSecurity_findList.action'"
                           maxlength="40" value=""/>
                </td>
            </tr>

            <tr>
                <th>活动音乐类型</th>
                <td>
                    <input id="type" name="activityMusic.type" class="easyui-validatebox" style="width: 250px;" value="10" />
                </td>
            </tr>

            <tr>
                <th>是否重复播放</th>
                <td>
                    <select name="activityMusic.isRepeatPlay">
                        <option value="0">是</option>
                        <option value="1">否</option>
                    </select>
                    <%--<input name="actActivity.isRepeatPlay" class="easyui-combobox" style="width: 150px;" data-options="editable:false,data:[{'label':'是','value':'0'},{'label':'否','value':'1'}]" />--%>
                </td>
            </tr>
            <tr>
                <th>附件上传</th>
                <td>
                    <input type="button" id="file_upload_button" value="上传文件">(<span style="color: red">附件大小：20M之内</span>，附件格式：(swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb)
                    <input id="filePath" name="activityMusic.filePath" type="hidden" value=""/>
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

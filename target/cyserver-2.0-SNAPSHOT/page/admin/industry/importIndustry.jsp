<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title></title>
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        $(function()
        {
            var button = $("#file_upload_button"), interval;
            new AjaxUpload(button, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action?dir=file',
                name : 'upload',
                onSubmit : function(file, ext)
                {
                    if (!(ext && /^(xls|xlsx)$/.test(ext)))
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
                            $('#file').html(
                                "<div id='div'><a href='"+msg.url+"'>" + msg.url
                                + "</a>&nbsp;<img id='img' class='iconImg ext-icon-cross' onclick='clearUrl()' title='清空'/><input name='url' id='url' type='hidden' value='"+msg.url+"'/></div>");
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
        function submitForm($dialog, $pjq) {
            if($('#url').val()==undefined){
                parent.parent.$.messager.alert('提示','请上传文件','error');
                return false;
            }
            if ($('form').form('validate')) {
                $.ajax({
                    url : '${pageContext.request.contextPath}/industry/industryAction!importData.action',
                    data : $('form').serialize(),
                    dataType : 'json',
                    success : function(result) {
                        if (result.success) {
                            debugger;
                            /*
                             if(result.msg==""){
                             $pjq.messager.alert('提示', "数据导入成功", 'info');
                             }else{
                             $('#importResult').html("<a id='result' href='"+result.msg+"'>导入失败结果下载</a>")
                             $pjq.messager.alert('提示', "数据导入成功,有部分数据导入失败，请在导入失败结果处下载导入失败的数据", 'info');
                             }
                             */
                            if(result.msg=="")
                            {


                                var MyInterval1 = window.setInterval(function () {
                                    $.ajax({
                                        url : '${pageContext.request.contextPath}/industry/industryAction!importState.action',
                                        dataType : 'json',
                                        success : function(result) {
                                            if ( result.state == 0 ) {
                                                parent.parent.$.messager.progress('close');
                                                window.clearInterval( MyInterval1 );
                                                if( result.url == "" )
                                                    $pjq.messager.alert('提示', "数据导入成功", 'info');
                                                else if( result.url.substring(0,1) == "1" )
                                                {
                                                    $pjq.messager.alert('提示', "导入失败"+(result.url), 'info');
                                                }

                                                else
                                                {
                                                    $('#importResult').html("<a id='result' href='" + result.url + "'>导入失败结果下载</a>")
                                                    $pjq.messager.alert('提示', "数据导入成功,有部分数据导入失败，请在导入失败结果处下载导入失败的数据", 'info');
                                                }


                                            } else if( result.state == 1 )
                                            {
                                                parent.parent.$.messager.progress('close');
                                                parent.parent.$.messager.progress({
                                                    text : '正在整理数据,请稍后...'
                                                });
                                            }
                                            else {
                                                parent.parent.$.messager.progress('close');
                                                parent.parent.$.messager.progress({
                                                    text : '正在导入数据... 进度:' + result.now + '/' + result.total
                                                });
                                            }
                                        }
                                    });

                                },3000);
                                parent.parent.$.messager.progress({
                                    text : '正在整理数据,请稍后...'
                                });
                            }
                            else
                            {
                                $pjq.messager.alert('提示', '其他用户正在导入数据，请稍后再试', 'error');
                            }
                        } else {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    },

                    complete:function(){
                        //parent.parent.$.messager.progress('close');
                    }
                });
            }
        };
    </script>
</head>

<body>
<form id="deptImport" method="post" enctype="multipart/form-data">
    <input name="dept.parentId" type="hidden" value="${param.id}">
    <fieldset>
        <legend>
            行业导入
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    模板下载
                </th>
                <td>
                    <a href="${pageContext.request.contextPath}/template/industry_template.xls">模板下载</a>
                </td>
            </tr>
            <tr>
                <th>
                    文件上传
                </th>
                <td>
                    <input type="button" id="file_upload_button" value="文件上传">
                </td>
            </tr>
            <tr>
                <th>
                    上传的文件
                </th>
                <td>
                    <span id="file"></span>
                </td>
            </tr>
        </table>
    </fieldset>
    <fieldset>
        <legend>
            导入信息
        </legend>
        <table class="ta001">
            <tr>
                <th>失败结果</th><td><span id="importResult"></span></td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>

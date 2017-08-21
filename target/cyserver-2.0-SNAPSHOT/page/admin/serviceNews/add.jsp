<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
        var isNewTypeOpen = "0";
        var isNewType = "0";
        var sChannel = "" ;	//lixun
        var sSource = "" ;	//lixun
        //编辑器里面的内容图片上传
        KindEditor.ready(function(K) {
            K.create('#content',{
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
        //封面图上传
        $(function() {
            var button = $("#news_upload_button"), interval;
            new AjaxUpload(button, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadNews.action',
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
                        $('#newsPic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeNewsPic(this)"></div><input type="hidden" name="serviceNews.pic" value="'+msg.url+'"/></div>');
                        $("#news_upload_button").prop('disabled', 'disabled');
                    } else {
                        $.messager.alert('提示', msg.message, 'error');
                    }
                }
            });

        });

        function removeNewsPic(newsPic) {
            $(newsPic).parent().remove();
            $("#news_upload_button").prop('disabled', false);
        }


        function submitForm($dialog, $grid, $pjq)
        {
            if($('#introduction').val()==''){
                parent.$.messager.alert('提示', '请输入新闻简介', 'error');
                return false;
            }

            if($('input[name="serviceNews.pic"]').val()==undefined){
                parent.$.messager.alert('提示', '请上传新闻封面图片', 'error');
                return false;
            }

            if ($('form').form('validate'))
            {
                $.ajax({
                    url : '${pageContext.request.contextPath}/serviceNews/serviceNewsAction!saveServiceNews.action',
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
<form method="post" id="addNewsForm">
    <fieldset>
        <legend>
            新闻基本信息
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    标题
                </th>
                <td colspan="3">
                    <input name="serviceNews.id"  id="newsId" type="hidden" value="">
                    <input name="serviceNews.title" class="easyui-validatebox"
                           style="width: 700px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" value=""/>
                </td>
            </tr>
            <tr>
                <th>
                    新闻简介
                </th>
                <td colspan="3">
					<textarea id="introduction" rows="7" cols="100"
                              name="serviceNews.introduction"></textarea>
                </td>
            </tr>
            <tr>
                <th>
                    网页链接
                </th>
                <td colspan="3">
                    <textarea id="newsUrl" rows="3" cols="100" name="serviceNews.newsUrl"></textarea>
                </td>
            </tr>
            <%--<tr>
                <th>
                    兴趣标签
                </th>
                <td colspan="3">
                    <select id="serviceNews.type" name="serviceNews.type"  class="easyui-combogrid" style="width:150px" data-options="
							panelWidth: 200,
							multiple: true,
							required:true,
							idField: 'channelName',
							textField: 'channelRemark',
							url: '${pageContext.request.contextPath}/newsChannel/newsChannelAction!doNotNeedSecurity_getALLLabelList.action',
							method: 'get',
							columns: [[
								{field:'channelName',checkbox:true},
								{field:'channelRemark',title:'标签名称',width:80}
							]],
							fitColumns: true,
							editable:false,
							onLoadSuccess : function( data )
							{
								//alert( JSON.stringify( data ) );
							}
						">
                    </select>
                </td>
            </tr>--%>
                <th>
                    所属服务
                </th>
                <td>
                    <input name="serviceNews.serviceId" id="serviceId" class="easyui-combobox" style="width: 150px;" value=""
                           data-options="editable:false,required:true,
							        valueField: 'id',
							        textField: 'serviceName',
							        url: '${pageContext.request.contextPath}/mobile/schoolServ/schoolServAction!doNotNeedSessionAndSecurity_getServiceList.action',
							        onSelect: function(value){
                                    $('#channel').combobox('clear');
                                    $('#channel').combobox('loadData', []);
                                    var url = '';
                                    var serviceId=value.id;
                                    url = '${pageContext.request.contextPath}/serviceNewsType/serviceNewsTypeAction!doNotNeedSessionAndSecurity_serviceNewsTypeTree.action?serviceId='+serviceId;
                                     $.ajax({
                                        url: url,
                                        success : function(result)
                                        {
                                            if (result != '[]' )
                                            {
                                                $('#channel').combobox('reload',url);
                                                $('#lanMu').show();
                                                $('#channel').combobox({'required': true});
                                            }else{
                                                $('#lanMu').hide();
                                                $('#channel').combobox({'required': false});
                                            }
                                        }
                                     });
						}">
                </td>
            </tr>
            <tr style="display: none" id="lanMu">
                <th>
                    所属栏目
                </th>
                <td>
                    <input name="serviceNews.channel" id="channel" class="easyui-combobox" style="width: 150px;" value=""
                           data-options="editable:false,
							        valueField: 'id',
							        textField: 'name',
							        ">
                </td>
            </tr>

            <tr>
                <th>
                    新闻内容
                </th>
                <td colspan="3">
					<textarea id="content" name="serviceNews.content"
                              style="width: 700px; height: 300px;"></textarea>
                </td>
            </tr>

            <tr>
                <th>
                    新闻封面上传
                </th>
                <td colspan="3">
                    <input type="button" id="news_upload_button" value="上传图片">
                </td>
            </tr>
            <tr>
                <th>
                    新闻封面图片
                </th>
                <td colspan="3">
                    <div id="newsPic"></div>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
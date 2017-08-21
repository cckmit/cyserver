<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<jsp:include page="../../../inc.jsp"></jsp:include>
<script type="text/javascript">
    var dictId='${param.id}';
   $(function() {
        if (dictId > 0) {
            $.ajax({
                url:'${pageContext.request.contextPath}/dict/dictAction!getByDictId.action',
                data :{'dictId':dictId},
                dataType:'json',
                success : function(result){

                    if (result.dictId != undefined) {
                        $('form').form('load', {
                            'dictObj.dictTypeId' : result.dictTypeId,
                            'dictObj.dictName' : result.dictName,
                            'dictObj.dictValue' : result.dictValue,
                            'dictObj.dictImage' : result.dictImage
                        });

                        if(result.dictImage!=undefined){
                            $('#pagePic').append('<div style="float:left;width:180px;"><img src="' + result.dictImage + '" width="150px" height="150px"/><div class="bb001" onclick="removeSchoolLogo(this)"></div><input type="hidden" name="dictObj.dictImage" value="' + result.dictImage + '"/></div>');
                        }

                    }
                },
                beforeSend:function(){
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                complete:function(){
                    parent.$.messager.progress('close');
                }
            });
        }
    });

    //图片上传
    $(function() {
        var button = $("#pic_upload_button"), interval;
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
                    $('#pagePic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001 ob1" onclick="removeSchoolLogo(this)"></div><input type="hidden" name="dictObj.dictImage" value="'+msg.url+'"/></div>');
                    $("#pic_upload_button").prop('disabled', 'disabled');
                } else {
                    $.messager.alert('提示', msg.message, 'error');
                }
            }
        });
    });
    function removeSchoolLogo(pagePic) {
        $(pagePic).parent().remove();
        $("#pic_upload_button").prop('disabled', false);
    }

    function submitForm ($dialog, $grid, $pjq) {

        if ($('form').form('validate')) {
            $.ajax({
                url : '${pageContext.request.contextPath}/dict/dictAction!updateDict.action',
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
    };
</script>
<body>
<form method="post">

	<fieldset >
		<legend>新增字典表单</legend>
		<table class="ta001" >
			<tr>
				<th>
					字典名称：
				</th>
				<td>
					<input id="id" name="dictObj.dictId" type="hidden" value="${param.id}">
					<input id="dictTypeId" name="dictObj.dictTypeId" type="hidden">
					<input name="dictObj.dictName" id="dictName" style="width: 150px;" class="easyui-validatebox"
						   data-options="required:true,validType:'customRequired'"/>
				</td>
			</tr>
			<tr>
				<th>
					字典值：
				</th>
				<td >
					<input name="dictObj.dictValue" id="dictValue" style="width: 150px;" class="easyui-validatebox"
						   data-options="required:true,validType:'customRequired'"/>
				</td>
			</tr>

			<tr>
				<th>
					上传图标：
				</th>
				<td >
					<input type="button"  id="pic_upload_button" value="请选择图标" />
				</td>

			</tr>
			<tr>
				<th>
					图标：
				</th>
				<td>
					<div id="pagePic" class="container"></div>
				</td>
			</tr>

		</table>
		<%--<authority:authority authorizationCode="新增属性值" userRoles="${sessionScope.user.userRoles}">
			<a href="javascript:void(0);" class="easyui-linkbutton"
			   data-options="iconCls:'ext-icon-note_add',plain:true"
			   onclick="addDictValue();">新增属性值</a>
		</authority:authority>--%>

	</fieldset>

</form>
</body>
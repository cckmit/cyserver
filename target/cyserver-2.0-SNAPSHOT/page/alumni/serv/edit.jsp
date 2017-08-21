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
	var imgNum = 0;
	$(function() {
		if ($('#serviceId').val() > 0) {	
            $.ajax({
                url: '${pageContext.request.contextPath}/serv/servAction!getByIdx.action',
                data: $('form').serialize(),
                dataType: 'json',
                success: function (result) {
                    if (result.id != undefined) {
                        $('form').form('load', {
                            'serv.id': result.id,
                            'serv.content': result.content
                        });
                        
                        if(result.region != null && result.region != '') {
                        	var rArr = result.region.split(' ');
                        	if(rArr.length >= 1) {
                        		$('#province').combobox('select',rArr[0]);
                        	}
                        	if(rArr.length == 2) {
                        		$('#city').combobox('setValue',rArr[1]);
                        	}
                        }
                        
                        for(var i=0;i<result.picList.length;i++){ 
                        	var p = result.picList[i];
                        	$('#servPic').append('<div style="float:left;width:180px;"><img src="'+p.pic+'" width="150px" height="150px"/><div class="bb001" onclick="removeeventPic(this)"></div><input type="hidden" name="pics" id="eu" value="'+p.pic+'"/></div>');
                        }
                        
                        imgNum = result.picList.length;
                        if(imgNum >= 3) {
							$("#event_upload_button").prop('disabled', 'disabled');
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
        }
        
		var button = $("#event_upload_button"), interval;
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
					$('#servPic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeeventPic(this)"></div><input type="hidden" name="pics" value="'+msg.url+'"/></div>');
					imgNum = imgNum + 1;
					if(imgNum >= 3) {
						$("#event_upload_button").prop('disabled', 'disabled');
					}
				} else {
					$.messager.alert('提示', msg.message, 'error');
				}
			}
		});
	});

	function removeeventPic(eventPic) {
		$(eventPic).parent().remove();
		imgNum = imgNum - 1;
		if(imgNum < 3) {
			$("#event_upload_button").prop('disabled', false);
		}
	}	
	
	function submitForm($dialog, $grid, $pjq)
	{		
		if ($('form').form('validate'))
		{
			if($('#content').val().trim()==''){
				parent.$.messager.alert('提示', '请输入内容', 'error');
				return false;
			}			
			
			$.ajax({
				url : '${pageContext.request.contextPath}/serv/servAction!updatex.action',
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
	};
</script>
</head>
  
<body>
<form method="post" id="addServForm">
<input name="serv.id" type="hidden" id="serviceId" value="${param.id}">
	<fieldset>
		<legend>
			基本信息
		</legend>
		<table class="ta001">
			<tr>
				<th>
					内容
				</th>
				<td colspan="3">
					<textarea id="content" name="serv.content"
						style="width: 700px; height: 200px;"></textarea>
				</td>
			</tr>
			
			<tr>
				<th>
					图片上传
				</th>
				<td colspan="3">
					<input type="button" id="event_upload_button" value="上传图片">
				</td>
			</tr>
			<tr>
				<th>
					图片
				</th>
				<td colspan="3">
					<div id="servPic"></div>
				</td>
			</tr>
			
		</table>
	</fieldset>
</form>
  </body>
</html>
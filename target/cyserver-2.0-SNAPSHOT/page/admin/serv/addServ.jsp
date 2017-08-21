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
					$('#eventPic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeeventPic(this)"></div><input type="hidden" name="pics" value="'+msg.url+'"/></div>');
					imgNum = imgNum + 1;
					if(imgNum >= 3) {
						$("#event_upload_button").prop('disabled', 'disabled');
					}
				} else {
					$.messager.alert('提示', msg.message, 'error');
				}
			}
		});
		/*if(${sessionScope.user.role.systemAdmin} == 1) {
			$("#dept").hide();
		}*/
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
				url : '${pageContext.request.contextPath}/serv/servAction!save.action',
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
<input name="serv.type" type="hidden" value="0">
<input name="serv.category" type="hidden" value="${param.category}">
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
					<div id="eventPic"></div>
				</td>
			</tr>
			<%--<tr id="dept">
				<th>
					所属院系
				</th>
				<td colspan="3">
					<input name="serv.dept_id" class="easyui-combobox" style="width: 200px;" id="select_dept"
						data-options="  
						valueField: 'deptId',  
						textField: 'deptName',  
						editable:false,
						url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getUserDepts.action',
						onLoadSuccess: function (data) {
                            if (data.length > 0) {
                                $('#select_dept').combobox('select', data[0].deptId);
                            }
                        }
					" />
				</td>
			</tr>--%>
			<tr>
				<th>
					地域
				</th>
				<td colspan="3">
					<input class="easyui-combobox" name="province" id="province"
						data-options="
	                    method:'post',
	                    url:'${pageContext.request.contextPath}/province/provinceAction!doNotNeedSecurity_getProvince2ComboBox.action?countryId=1',
	                    valueField:'provinceName',
	                    textField:'provinceName',
	                    editable:false,
	                    prompt:'省',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#province').combobox('clear');
			                	$('#city').combobox('clear');
			                	$('#city').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/city/cityAction!doNotNeedSecurity_getCity2ComboBox.action?provinceId='+rec.id; 
							$('#city').combobox('clear');	
							$('#city').combobox('reload', url);
						}
                    	">
                    	&nbsp; <input class="easyui-combobox" name="city" id="city"
						data-options="
	                    method:'post',
	                    valueField:'cityName',
	                    textField:'cityName',
	                    editable:false,
	                    prompt:'市',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#city').combobox('clear');
			                }
			            }]
                    	">&nbsp;&nbsp;&nbsp;&nbsp;( 地域为空表示全国 )
				</td>
			</tr>
		</table>
	</fieldset>
</form>
  </body>
</html>
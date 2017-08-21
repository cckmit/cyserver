<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cy.system.Global" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int isRichTextConvert = Global.IS_RICH_TEXT_CONVERT;
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
		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('#content',{
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
		$(function() {
            $('#startTime').datetimebox('setValue', formatterDate(new Date()));
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
						$('#projectPic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeProjectPic(this)"></div><input type="hidden" name="project.projectPic" value="'+msg.url+'"/></div>');
						$("#news_upload_button").prop('disabled', 'disabled');
					} else {
						$.messager.alert('提示', msg.message, 'error');
					}
				}
			});
		});
        function formatterDate (date) {
            var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
            var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
                + (date.getMonth() + 1);
            var hor = date.getHours();
            var min = date.getMinutes();
            var sec = date.getSeconds();
            return date.getFullYear() + '-' + month + '-' + day+" "+hor+":"+min+":"+sec;
        };
		function removeProjectPic(newsPic) {
			$(newsPic).parent().remove();
			$("#news_upload_button").prop('disabled', false);
		}
		function submitForm($dialog, $grid, $pjq, status)
		{

            if(status){
                $('#status').val(status);
            }else{
                $('#status').val('0');
            }

            if($('#introduction').val()==''){
				parent.$.messager.alert('提示', '请输入项目简介', 'error');
				return false;
			}
			if (editor.isEmpty()) {
				parent.$.messager.alert('提示', '请输入项目内容', 'error');
				return false;
			}
			if($('input[name="project.projectPic"]').val()==undefined){
				parent.$.messager.alert('提示', '请上传封面图片', 'error');
				return false;
			}


            if($('#hasEndTime').combobox('getValue') == '1'){
                if($('#endTime').datetimebox('getValue') && $('#endTime').datetimebox('getValue') != ''){
                    var endTime = $('#endTime').datetimebox('getValue');
                    var endDate = new Date(Date.parse(endTime.replace(/-/g, "/")));
                    if(endDate <= new Date()){
                        parent.$.messager.alert('提示', '结束时间不可早于当前时间', 'error');
                        return false;
                    }
                }else{
                    parent.$.messager.alert('提示', '请设置结束时间', 'error');
                    return false;
                }

            }

			if ($('form').form('validate'))
			{
                //富文本是否转编码 lixun 2017.5.5
                if( '<%=isRichTextConvert%>' == '1' )
                {
                    $("#content").val(strToBase64($("#content").val()));
                }
				$.ajax({
					url : '${pageContext.request.contextPath}/project/projectAction!save.action',
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
		<form method="post">
			<fieldset>
				<legend>
					基本信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							捐赠项目
						</th>
						<td>
							<input name="project.projectName" class="easyui-validatebox" data-options="required:true,validType:'customRequired'"/>
							<input name="project.status" id="status" type="hidden"/>
						</td>
					</tr>
					<tr>
					<tr>
						<th>
							捐赠种类
						</th>
						<td colspan="3">
							<select id="project.projectType" name="project.projectType"  class="easyui-combogrid" style="width:150px" data-options="
                                panelWidth: 200,
                                multiple: true,
                                required:true,
                                idField: 'one',
                                textField: 'two',
                                url: '${pageContext.request.contextPath}/project/projectAction!doNotNeedSecurity_getALLDonateType.action',
                                method: 'get',
                                columns: [[
                                    {field:'one',checkbox:true},
                                    {field:'two',title:'标签名称',width:80}
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
					</tr>
					<tr>
						<th>
							是否推荐项目
						</th>
						<td>
							<select name="project.isCommand" class="easyui-combobox"
									data-options="editable:false">
								<option value="1">是</option>
								<option value="0" selected>否</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							排序
						</th>
						<td>
							<input name="project.seq" class="easyui-validatebox" value="1" data-options="required:true" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></input>
						</td>
					</tr>
					<tr>
						<th>
							目标金额
						</th>
						<td>
							<select name="project.hasTarget" class="easyui-combobox"
									data-options="editable:false,
									onSelect: function(value){
										if(value.value == '1'){
											$('#targetDiv').show();
											$('#target').val(100.00);
										}else{
											$('#targetDiv').hide();
											$('#target').val('');
										}
									}">
								<option value="0">不设目标金额</option>
								<option value="1">指定目标金额</option>
							</select>
							<span id="targetDiv" style="display: none">¥<input name="project.target" id="target" type="number" step="0.01" class="easyui-validatebox"/></span>
						</td>
					</tr>
					<%--<tr>
						<th>
							开始时间
						</th>
						<td>
							<input id="startTime" name="project.startTime" data-options="editable:false" class="easyui-datetimebox"/>
						</td>
					</tr>--%>
					<tr>
						<th>
							结束时间
						</th>
						<td>
							<select name="project.hasEndTime" id="hasEndTime" class="easyui-combobox"
									data-options="editable:false,
									onSelect: function(value){
										if(value.value == '1'){
											$('#endDiv').show();
											$('#endTime').datetimebox('setValue', formatterDate(new Date()));
										}else{
											$('#endDiv').hide();
											$('#endTime').datetimebox('clear');
										}
									}">
								<option value="0">长期有效</option>
								<option value="1">指定截止时间</option>
							</select>
							<span id="endDiv" style="display:none;" >
								<input id="endTime" name="project.endTime" class="easyui-datetimebox" data-options="editable:false" />
							</span>
						</td>
					</tr>
					<tr>
						<th>
							项目简介
						</th>
						<td>
							<textarea id="introduction" rows="5" cols="100" name="project.introduction"></textarea>
						</td>
					</tr>
					<tr>
						<th>
							项目内容
						</th>
						<td colspan="3">
							<textarea id="content" name="project.content"
								style="width: 700px; height: 300px;"></textarea>
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
							封面图片
						</th>
						<td colspan="3">
							<div id="projectPic"></div>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>

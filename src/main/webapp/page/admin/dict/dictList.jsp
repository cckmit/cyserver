<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority"%>
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
		<style>
			.ob1{
				top: -120px;
				left: 100px;
			}
		</style>
		<script type="text/javascript">
			var dictGrid;
			var dictKey;
			$(function() {
				dictKey = parent.$('#dictTree').tree('getSelected').id;
				$('#dictTypeId').attr('value',dictKey);
				dictGrid = $('#dictGrid')
						.datagrid(
								{
									url : '${pageContext.request.contextPath}/dict/dictAction!getDict.action',
									fit : true,
                                    iconCls:'icon-edit',
									queryParams:{'id':dictKey},
									method:'post',
									border : false,
									striped : true,
									rownumbers : true,
									pagination : false,
									singleSelect : true,
									columns : [ [
											{
												width : '300',
												title : '名称',
												field : 'dictName'
											},
											{
												width : '300',
												title : '值',
												field : 'dictValue'
											},
											{
												width : '300',
												title : '字典图标',
												field : 'dictImage',
                                                formatter : function(value, row) {
                                                    var str = '';
                                                    if (value && value != '') {
                                                  	  str += '<img width="80px" height="80" src="'+value+'">';
                                                    }
                                                    return str;
                                                }
											},
											{
												title : '操作',
												field : 'action',
												width : '150',
												formatter : function(value, row, index) {
                                                    var str = '';
                                                    <authority:authority authorizationCode="编辑字典" userRoles="${sessionScope.user.userRoles}">
                                                    str += '<a href="javascript:void(0)" onclick="editFun(\'' + row.dictId + '\');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                                                    </authority:authority>

                                                    <authority:authority authorizationCode="删除属性值" userRoles="${sessionScope.user.userRoles}">
                                                    str += '<a href="javascript:void(0)" onclick="removeFun(\''
                                                        + row.dictId
                                                        + '\');"><img class="iconImg ext-icon-note_delete"/>删除</a>';
                                                    </authority:authority>
                                                    return str;

												}
											}
											] ],
                                        toolbar : '#toolbar',
                                    onBeforeLoad : function(param) {
                                        parent.parent.$.messager.progress({
                                            text : '数据加载中....'
                                        });
                                    },
                                    onLoadSuccess : function(data) {
                                        $('.iconImg').attr('src', pixel_0);
                                        parent.parent.$.messager.progress('close');
                                    }
								});
			});

			function addDictValue(){
				if ($('#dictForm').form('validate')) {
					$.ajax({
						url:'${pageContext.request.contextPath}/dict/dictAction!addDict.action',
						data :$('#dictForm').serialize(),
						dataType:'json',
						success : function(result){  
							if(result.success){
								$('#dictForm')[0].reset();
							 	$('#dictGrid').datagrid('reload');
							 	parent.parent.$.messager.alert('提示', result.msg, 'info');
							}else{
								parent.parent.$.messager.alert('提示', result.msg, 'error');
							}
				          },
							beforeSend:function(){
								parent.parent.$.messager.progress({
									text : '数据提交中....'
								});
							},
							complete:function(){
								parent.parent.$.messager.progress('close');
							}
					});
				}
			}

            var editFun = function (id) {
                var dialog = parent.WidescreenModalDialog({
                    title: '编辑字典',
                    iconCls: 'ext-icon-note_edit',
                    url: '${pageContext.request.contextPath}/page/admin/dict/editDict.jsp?id=' + id,
                    buttons: [{
                        text: '保存',
                        iconCls: 'ext-icon-save',
                        handler: function () {
                            dialog.find('iframe').get(0).contentWindow.submitForm(dialog, dictGrid, parent.$);
                        }
                    }]
                });
            };

			function removeFun(dictId){
				parent.parent.$.messager.confirm('确认', '您确定要删除此记录？', function(r) {
					if (r) {
						$.ajax({
							url:'${pageContext.request.contextPath}/dict/dictAction!deleteDict.action',
							data :{'id':dictId},
							dataType:'json',
							success : function(result){  
								if(result.success){
								 	$('#dictGrid').datagrid('reload');
								 	parent.parent.$.messager.alert('提示', result.msg, 'info');
								}else{
									parent.parent.$.messager.alert('提示', result.msg, 'error');
								}
					          },
								beforeSend:function(){
									parent.parent.$.messager.progress({
										text : '数据提交中....'
									});
								},
								complete:function(){
									parent.parent.$.messager.progress('close');
								}
						});
					}
				});
			}

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
                            $('#pagePic').append('<img src="'+msg.url+'" width="120px" height="120px"/><div class="bb001 ob1" onclick="removeSchoolLogo(this)"><input type="hidden" name="dictObj.dictImage" value="'+msg.url+'"/></div>');
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
		</script>
	</head>

	<body class="easyui-layout" data-options="fit:true,border:false">
		<div id="toolbar" style="display: none;">
			<form id="dictForm">
				<br>
				<fieldset style="width: 400px">
					<legend>新增字典表单</legend>
				<table class="ta001" >
					<tr>
						<th>
							字典名称：
						</th>
						<td>
							<input name="dictObj.dictTypeId" id="dictTypeId" style="width: 150px;" type="hidden"/>
							<input name="dictObj.dictName" id="dictName" style="width: 150px;" class="easyui-validatebox"
								data-options="required:true,validType:'customRequired'"/>
						</td>
					</tr>
					<tr>
						<th>
							字典值：
						</th>
						<td width="200px">
							<input name="dictObj.dictValue" id="dictValue" style="width: 150px;" class="easyui-validatebox"
								   data-options="required:true,validType:'customRequired'"/>
						</td>
					</tr>

					<tr>
						<th>
							上传图标：
						</th>
						<td width="200px">
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
					<authority:authority authorizationCode="新增属性值" userRoles="${sessionScope.user.userRoles}">
						<a href="javascript:void(0);" class="easyui-linkbutton"
						   data-options="iconCls:'ext-icon-note_add',plain:true"
						   onclick="addDictValue();">新增属性值</a>
					</authority:authority>

				</fieldset><br>

			</form>

		</div>

		<div data-options="region:'center',fit:true,border:false">
			<%--<div style="margin:10px 0">
				<a href="#" class="easyui-linkbutton" onclick="insert()">Insert Row</a>
			</div>--%>
			<table id="dictGrid"></table>
		</div>
	</body>
</html>

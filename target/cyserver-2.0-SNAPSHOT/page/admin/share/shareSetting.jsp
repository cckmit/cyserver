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
	var picCount = 0;//计算页面图片数量
	var editor;
	KindEditor.ready(function(K) {
		editor = K.create('#introduction',{
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
				    picCount++;//
					$('#pagePic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeSchoolLogo(this)"></div><input type="hidden" name="share.pictureUrls" value="'+msg.url+'"/></div>');
					if (picCount>=6) {
                        $("#pic_upload_button").prop('disabled', 'disabled');
                    }
				} else {
					$.messager.alert('提示', msg.message, 'error');
				}
			}
		});
	});
	function removeSchoolLogo(pagePic) {
		$(pagePic).parent().remove();
		picCount--;
		$("#pic_upload_button").prop('disabled', false);
	}
	$(function() {
		$.ajax({
			url : '${pageContext.request.contextPath}/share/shareAction!initShare.action',
					dataType : 'json',
					success : function(result) {
						if (result != null && result.id != undefined) {
							$('form').form('load', {
								'share.schoolName' : result.schoolName,
								'share.iosUrl' : result.iosUrl,
								'share.androidUrl' : result.androidUrl,
								'share.iosPackageSize' : result.iosPackageSize,
                                'share.iosDownloads' : result.iosDownloads,
                                'share.androidPackageSize' : result.androidPackageSize,
                                'share.androidDownloads' : result.androidDownloads,
								'share.pagePictures' : result.pictureUrls
							});
							editor.html(result.introduce);
							if(result.fileList != null || $.trim(result.fileList) != '' ) {
								var pics = result.fileList
								for (var i in pics) {
									$('#pagePic').append('<div style="float:left;width:180px;"><img src="' + pics[i].picUrl + '" width="150px" height="150px"/><div class="bb001" onclick="removeSchoolLogo(this)"></div><input type="hidden" name="share.pictureUrls" value="' + pics[i].picUrl + '"/></div>');
									picCount++;
								}
							}
						}
					},
					beforeSend : function() {
						parent.$.messager.progress({
							text : '数据加载中....'
						});
					},
					complete : function() {
						parent.$.messager.progress('close');
					}
				});
	});
	function save($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			if($('input[name="share.pictureUrls"]').val()==undefined){
				parent.$.messager.alert('提示', '请上传图片', 'error');
				return false;
			}
			if (picCount<3||picCount>6){
                parent.$.messager.alert('提示', '页面图片最少3张，最多6张', 'error');
                return false;
			}
			if($('#introduction').val()==''){
				$.messager.alert('提示', '请输入产品介绍', 'error');
				return false;
			}
			$ .ajax({
				url : '${pageContext.request.contextPath}/share/shareAction!save.action',
				data : $('form').serialize(),
				dataType : 'json',
				success : function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg,
								'info');
					} else {
						parent.$.messager.alert('提示', result.msg,
								'error');
					}
				},
				beforeSend : function() {
					parent.$.messager.progress({
						text : '数据提交中....'
					});
				},
				complete : function() {
					parent.$.messager.progress('close');
				}
			});
		}
	};
</script>
</head>

<body>
	<form method="post" class="form">
		<input name="share.id" type="hidden" />
		<fieldset>
			<legend> 分享基本信息 </legend>
			<table class="ta001" style="margin-left: 20px">
				<tr>
					<th>学校名称</th>
					<td><input name="share.schoolName"
							   class="easyui-validatebox"
							   style="width: 300px;" required/>
					</td>
				</tr>

			</table>
			<br>
			<fieldset>
				<legend> ios 基本信息</legend>
				<table class="ta001">
					<tr>
						<th style="width: 110px">ios下载地址</th>
						<td><input name="share.iosUrl"
								   class="easyui-validatebox"
								   style="width: 300px;" required/>
						</td>
					</tr>
					<tr>
						<th>ios 安装包大小</th>
						<td><input name="share.iosPackageSize"
								   class="easyui-validatebox"
								   style="width: 300px;" required />
						</td>
					</tr>
					<tr>
						<th>ios 下载量</th>
						<td><input name="share.iosDownloads"
								   class="easyui-validatebox"
								   style="width: 300px;" required/>
						</td>
					</tr>
				</table>
			</fieldset>
			<br>
			<fieldset>
				<legend> android 基本信息</legend>
				<table class="ta001">
					<tr>
						<th style="width: 110px">android下载地址</th>
						<td><input name="share.androidUrl"
								   class="easyui-validatebox"
								   style="width: 300px;" required/>
						</td>
					</tr>
					<tr>
						<th>android 安装包大小</th>
						<td><input name="share.androidPackageSize"
								   class="easyui-validatebox"
								   style="width: 300px;" required />
						</td>
					</tr>
					<tr>
						<th>android 下载量</th>
						<td><input name="share.androidDownloads"
								   class="easyui-validatebox"
								   style="width: 300px;" disabled/>
						</td>
					</tr>
				</table>
			</fieldset>
		</fieldset>
		<br>
		<fieldset>
			<legend> 页面图片 </legend>
			<table class="ta001">
				<tr>
					<th>图片上传</th>
					<td>
						<input type="button"  id="pic_upload_button" value="请选择图片" />
						<span style="color: red">(最少3张，最多6张)</span>
					</td>
				</tr>
				<tr>
					<th>
						页面图片
					</th>
					<td colspan="3">
						<div id="pagePic" class="container" style="padding: 20px"></div>
						<%--<input type="hidden" name="share.pagePictures" id="pagePic" value="" />--%>
					</td>
				</tr>
			</table>
		</fieldset>
		<br>
		<fieldset>
			<legend> 产品介绍 </legend>
			<table class="ta001">
				<tr>
					<th>产品介绍</th>
					<td>
						<textarea id="introduction" rows="8" cols="100" name="share.introduce" style="width: 700px; height: 300px;" ></textarea>
					</td>
				</tr>
			</table>
		</fieldset>
		<table align="center">
			<tr>
				<td><a href="javascript:void(0);" class="easyui-linkbutton"
					data-options="iconCls:'ext-icon-save',plain:true" onclick="save();">保存</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>

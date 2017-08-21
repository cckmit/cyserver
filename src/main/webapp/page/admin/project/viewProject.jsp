<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		KindEditor.ready(function(K) {
			var editor=K.create('#content',{
				items : [
					'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', '|', 'emoticons', 'image', 'link','preview','fullscreen'
				 ],
		    	 uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
		         afterChange:function(){
			        	this.sync();
			        }
		    });
			editor.readonly();
		});
		$(function() {
			var projectId=$('#projectId').val();
			if (projectId > 0) {
				$.ajax({  
					url:'${pageContext.request.contextPath}/project/projectAction!getById.action',
					data :{'id':projectId},
					dataType:'json',
					success : function(result){  
						if (result.projectId != undefined) {
							$('form').form('load', {
								'project.projectName' : result.projectName,
								'project.seq' : result.seq,
								'project.introduction' : result.introduction,
								'creator' : result.user!=undefined?result.user.userName:'',
								'project.createTime' : result.createTime,
								'project.donationMoney':result.donationMoney,
                                'project.startTime': result.startTime,
                                'project.hasTarget': result.hasTarget !=undefined && result.hasTarget !='' ?result.hasTarget:0,
                                'project.hasEndTime': result.hasEndTime,
                                'project.projectType': result.projectType,
                                'project.isCommand': result.isCommand
							});
                            if(result.hasTarget && result.hasTarget == '1'){
                                $('#targetDiv').show();
                                $('#target').val(result.target);
                                $('#target').validatebox({required:true});
                                if(result.rateOfProgress){
                                    $('#ropTr').show();
                                    $('#rateOfProgress').text(result.rateOfProgress+'%');
								}
                            }else{
                                $('#hasTargetDiv').show();
                                $('#hasTarget').combobox('disable');

                            }
                            if(result.hasEndTime && result.hasEndTime == '1'){
                                $('#endDiv').show();
                                $('#endTime').datetimebox('setValue', result.endTime);
                            }else{
                                $('#hasEndDiv').show();
							}
							KindEditor.ready(function(K) {
								K.insertHtml('#content', result.content);
							});
							if(result.projectPic!=undefined){
								$('#projectPic').append('<div style="float:left;width:180px;"><img src="'+result.projectPic+'" width="150px" height="150px"/><input type="hidden" name="project.projectPic" value="'+result.projectPic+'"/></div>');
							}
							if(result.projectType && result.projectType!=''){
								$('#projectType').text(result.projectType)
							}

							$('#evm').html('<img style="width:150px;margin:5px" src="${pageContext.request.contextPath}/project/projectAction!doNotNeedSessionAndSecurity_getErWeiMa.action?projectId='+projectId+'" />');
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
		})
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
							<input name="project.projectId" id="projectId" type="hidden" value="${param.id}">
							<input name="project.projectName" class="easyui-validatebox" disabled="disabled"/>
						</td>
					</tr>
					<tr>
						<th>
							项目类型
						</th>
						<td >
							<div id="projectType"></div>
						</td>
					</tr>
					<tr>
						<th>
							金额
						</th>
						<td>
							<input name="project.donationMoney" class="easyui-validatebox" disabled="disabled"/>
						</td>
					</tr>
					<tr>
						<th>
							是否推荐项目
						</th>
						<td>
							<select name="project.isCommand" class="easyui-combobox"
									data-options="editable:false" disabled="disabled">
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							排序
						</th>
						<td>
							<input name="project.seq" class="easyui-validatebox" disabled="disabled"></input>
						</td>
					</tr>
					<tr>
						<th>
							创建人
						</th>
						<td>
							<input name="creator" class="easyui-validatebox" disabled="disabled"></input>
						</td>
					</tr>
					<%--<tr>
						<th>
							创建时间
						</th>
						<td>
							<input name="project.createTime" class="easyui-validatebox" disabled="disabled"></input>
						</td>
					</tr>--%>
					<tr>
						<th>
							目标金额
						</th>
						<td>
							<span id="hasTargetDiv" style="display: none">
								<select id="hasTarget" name="project.hasTarget" class="easyui-combobox"
										data-options="editable:false,
										onSelect: function(value){
											if(value.value == '1'){
												$('#targetDiv').show();
												$('#target').validatebox({required:true});
											}else{
												$('#targetDiv').hide();
												$('#target').validatebox({required:false});
											}
										}">
									<option value="0">不设目标金额</option>
									<option value="1">指定目标金额</option>
								</select>
							</span>
							<span id="targetDiv" style="display: none">¥<input name="project.target" id="target" type="number" step="0.01" class="easyui-validatebox" disabled/></span>
						</td>
					</tr>
					<tr id="ropTr" style="display: none">
						<th>完成进度</th>
						<td id="rateOfProgress"></td>
					</tr>
					<tr>
						<th>
							开始时间
						</th>
						<td>
							<input id="startTime" name="project.startTime" data-options="editable:false" class="easyui-datetimebox" disabled/>
						</td>
					</tr>
					<tr>
						<th>
							结束时间
						</th>
						<td>
							<span id="hasEndDiv" style="display: none">
								<select name="project.hasEndTime" class="easyui-combobox"
										data-options="editable:false,
										onSelect: function(value){
											if(value.value == '1'){
												$('#endDiv').show();
												$('#endTime').datetimebox('setValue', formatterDate(new Date()));
											}else{
												$('#endDiv').hide();
											}
										}" disabled>
									<option value="0">长期有效</option>
									<option value="1">指定截止时间</option>
								</select>
							</span>

							<span id="endDiv" style="display:none;" >
								<input id="endTime" name="project.endTime" class="easyui-datetimebox" data-options="editable:false" disabled />
							</span>
						</td>
					</tr>
					<tr>
						<th>
							项目简介
						</th>
						<td>
							<textarea rows="5" disabled="disabled" cols="100" name="project.introduction"></textarea>
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
							封面图片
						</th>
						<td colspan="3">
							<div id="projectPic"></div>
						</td>
					</tr>
					<tr>
						<th>
							二维码
						</th>
						<td colspan="3" id="evm">

						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</body>
</html>

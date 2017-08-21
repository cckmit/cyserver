<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.cy.system.Global" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
var industry_code;
$(function() {
	$.ajax({
		url : '${pageContext.request.contextPath}/alumni/alumniAction!doNotNeedSecurity_getByAlumniId.action',
		data : $('form').serialize(),
		dataType : 'json',
		success : function(result) {
			//console.log(JSON.stringify(result));
			if (result.alumniId != undefined) {
				$('form').form('load', {
					'alumni.alumniName' : result.alumniName,
					'alumni.presidentName' : result.presidentName,
					'alumni.address' : result.address,
					'alumni.telephone' : result.telephone,
					'alumni.email' : result.email,
				});
				industry_code= result.industryCode ;
				editor.html(result.introduction);
				$('#parent').text(result.parent);
				$('#industryCode').text(result.industry);
				$('#region').text(result.region);
				$('#mainName').text(result.mainType);

				if(result.mainId == 1 || result.mainId == 99) {
					$("#alumniName").attr("readonly","readonly") ;
				}

//				$('#presidentName').val(result.presidentName);
//				$('#address').val(result.address);
//				$('#telephone').val(result.telephone);
//				$('#email').val(result.email);

				if(result.parent == '学院分会'){
					$('#deptr2').show();
					if(result.academyName != undefined){
						$('#aluAcade').text(result.academyName);
					}
				}
				/*if(result.isUsed == '审核中'){
					$('#isUsed').prop({checked:true});
				}*/
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
});

function submitForm($dialog, $grid, $tree, $pjq) {


	if($('#tr1').is(":hidden")&&$('#province').combobox('getValue')==''){
		$pjq.messager.alert('提示', '请选择省份', 'info');
		return;
	}
	$('#industry').val(industry_code);
	if($('#isUsed').val() == 'on'){
		$('#isUsed').val('1');
	}else{
		$('#isUsed').val('0');
	}
	if ($('form').form('validate')) {
	//	alert(JSON.stringify( $('form').serialize()));
        //富文本是否转编码 lixun 2017.5.5
        if( '<%=isRichTextConvert%>' == '1' )
        {
            $("#introduction").val(strToBase64($("#introduction").val()));
        }
		$.ajax({
				url : '${pageContext.request.contextPath}/alumni/alumniAction!update.action',
				data : $('form').serialize(),
				dataType : 'json',
				success : function(result) {
					if (result.success) {
						$grid.datagrid('reload');
						$tree.tree('reload');
						$dialog.dialog('destroy');
						$pjq.messager.alert('提示', result.msg, 'info');
					} else {
						$pjq.messager.alert('提示', result.msg, 'error');
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
}
</script>
</head>
<body>
	<form method="post">
		<fieldset>
			<legend></legend>
			<table class="ta001">
				<tr>
					<th>上级分会</th>
					<td>
						<span id="parent" name="parent"></span>
					</td>
				</tr>
				<tr>
					<th>分会类型</th>
					<td >
						<span id="mainName"></span>
					</td>
				</tr>
				<tr id="deptr1" style="display: none">
					<th>
						所属院系
					</th>
					<td >
						<select name="alumni.xueyuanId"  id="xueyuanId" class="easyui-combotree"
								data-options="
								editable:false,
								idField:'id',
								state:'open',
								textField:'text',
								parentField:'pid',
								url:'${pageContext.request.contextPath}/dept/deptAction!getCurrDeptTree.action?noMatch=1',
								icons:[{
									iconCls:'icon-clear',
									handler: function(e){
										$('#xueyuanId').combotree('clear');
										$('#alumniName').val('');
									}
								}],
								onSelect: function(node){
									$('#alumniName').val(node.text);
								},
								onBeforeSelect: function(node){
									var isLeaf = $(this).tree('isLeaf', node.target);
									if (!isLeaf) {
										$.messager.show({
											msg: '请选择院系！'
										});
										return false;
									}
                                }"
								style="width: 200px;">
						</select>
						&nbsp;<a href="javascript:void(0)" onclick="$('#deptr2').hide();$('#deptr1').show()">取消更改</a>
					</td>
				</tr>
				<tr id="deptr2" style="display: none">
					<th>
						所属院系
					</th>
					<td>
						<span id="aluAcade" ></span> &nbsp;<a href="javascript:void(0)" onclick="$('#deptr2').hide();$('#deptr1').show()">更改</a>
					</td>
				</tr>
				<tr>
					<th>组织名称</th>
					<td><input name="alumni.alumniName" id="alumniName" class="easyui-validatebox"
						style="width: 150px;"
						data-options="required:true,validType:'customRequired'" />
						<input name="alumni.alumniId" value="${param.id}" type="hidden">
					</td>
				</tr>
				<tr id="rg1">
					<th>所属地区</th>
					<td>
						<span id="region" name="region"></span>&nbsp;
						<a href="javascript:void(0)" onclick="$('#rg1').hide();$('#rg2').show()">更改</a>
					</td>
				</tr>
				<tr id="rg2" style="display: none;">
					<th>所属地区</th>
					<td><input class="easyui-combobox" name="province" id="province"
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
			                	$('#area').combobox('clear');	
								$('#area').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/city/cityAction!doNotNeedSecurity_getCity2ComboBox.action?provinceId='+rec.id; 
							$('#city').combobox('clear');	
							$('#city').combobox('reload', url);
							$('#area').combobox('clear');	
							$('#area').combobox('loadData',[]);
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
			                	$('#area').combobox('clear');	
								$('#area').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/area/areaAction!doNotNeedSecurity_getArea2ComboBox.action?cityId='+rec.id; 
							$('#area').combobox('clear');	
							$('#area').combobox('reload', url);
						}
                    	">
                    	&nbsp; <input class="easyui-combobox" name="area" id="area"
						data-options="
	                    method:'post',
	                    valueField:'areaName',
	                    textField:'areaName',
	                    editable:false,
	                    prompt:'县(区)',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#area').combobox('clear');
			                }
			            }]
                    	">
                    	&nbsp;<a href="javascript:void(0)" onclick="$('#rg2').hide();$('#rg1').show()">取消更改</a>
					</td>
				</tr> 
				<tr id="is1">
					<th>所属行业</th>
					<td>
						<span id="industryCode"></span>&nbsp;
						<a href="javascript:void(0)" onclick="$('#is1').hide();$('#is2').show()">更改</a>

					</td>
				</tr>
				<tr id="is2" style="display: none;">
					<th>所属行业</th>
					<td>
						<input name="alumni.industry" id="industry" type="hidden"/>
					<input class="easyui-combobox" name="industry1" id="industry1"
						data-options="
	                    url:'${pageContext.request.contextPath}/industry/industryAction!doNotNeedSecurity_getIndustry2ComboBox.action?parentCode=-1',
	                    method:'post',
	                    valueField:'value',
	                    textField:'value',
	                    editable:false,
	                    prompt:'行业',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	industry_code = '';
			                	$('#industry1').combobox('clear');
			                	$('#industry2').combobox('clear');
			                	$('#industry2').combobox('loadData',[]);
			                	$('#industry3').combobox('clear');	
								$('#industry3').combobox('loadData',[]);
								$('#industry4').combobox('clear');	
								$('#industry4').combobox('loadData',[]);			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/industry/industryAction!doNotNeedSecurity_getIndustry2ComboBox.action?parentCode='+rec.code; 
							industry_code = rec.code;
							$('#industry2').combobox('clear');	
							$('#industry2').combobox('reload', url);
							$('#industry3').combobox('clear');	
							$('#industry3').combobox('loadData',[]);
							$('#industry4').combobox('clear');	
							$('#industry4').combobox('loadData',[]);
						}
                    	">
						&nbsp; <input class="easyui-combobox" name="industry2" id="industry2"
						data-options="
	                    method:'post',
	                    valueField:'value',
	                    textField:'value',
	                    editable:false,
	                    prompt:'行业',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#industry2').combobox('clear');
			                	$('#industry3').combobox('clear');
			                	$('#industry3').combobox('loadData',[]);
			                	$('#industry4').combobox('clear');	
								$('#industry4').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/industry/industryAction!doNotNeedSecurity_getIndustry2ComboBox.action?parentCode='+rec.code; 
							industry_code = rec.code;
							$('#industry3').combobox('clear');	
							$('#industry3').combobox('reload', url);
							$('#industry4').combobox('clear');	
							$('#industry4').combobox('loadData',[]);
						}
                    	">
                    	&nbsp; <input class="easyui-combobox" name="industry3" id="industry3"
						data-options="
	                    method:'post',
	                    valueField:'value',
	                    textField:'value',
	                    editable:false,
	                    prompt:'行业',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#industry3').combobox('clear');
			                	$('#industry4').combobox('clear');	
								$('#industry4').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/industry/industryAction!doNotNeedSecurity_getIndustry2ComboBox.action?parentCode='+rec.code; 
							industry_code = rec.code;
							$('#industry4').combobox('clear');	
							$('#industry4').combobox('reload', url);
						}
                    	">
                    	&nbsp; <input class="easyui-combobox" name="industry4" id="industry4"
						data-options="
	                    method:'post',
	                    valueField:'value',
	                    textField:'value',
	                    editable:false,
	                    prompt:'行业',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#industry4').combobox('clear');
			                }
			            }]
                    	">
                    	&nbsp;<a href="javascript:void(0)" onclick="$('#is2').hide();$('#is1').show()">取消更改</a>
					</td>
				</tr>
				<tr style="display: none">
					<td></td>
					<td>
						<%--<input name="alumni.isUsed" id="isUsed" type="checkbox" />提交审核--%>
						<input name="alumni.isUsed" value="2"/>
					</td>
				</tr>

				<tr>
				    <th>联系人姓名</th>

					<td>
						<input  name="alumni.presidentName" id="presidentName" class="easyui-validatebox"
							   style="width: 150px;"
							   data-options="required:true,validType:'customRequired'" />
						<input name="alumni.status" type="hidden" value="1" />
						<input name="alumni.checkFlag" type="hidden" value="1" />
					</td>

				</tr>

				<tr id="">
				    <th>联系人电话</th>
				    <td>
						<input name="alumni.telephone" id="telephone" class="easyui-validatebox"
							   style="width: 150px;"
							   data-options="required:true,validType:'customRequired'" />
						<input name="alumni.status" type="hidden" value="1" />
						<input name="alumni.checkFlag" type="hidden" value="1" />

				    </td>
				</tr>

				<tr>
				     <th>联系人地址</th>
				     <td>
					     <input id="address" name="alumni.address" >
				     </td>
			    </tr>

				<tr>
				     <th>联系人邮箱</th>
				     <td>
						 <input id="email" name="alumni.email" class="easyui-validatebox"
								style="width: 150px;"
								data-options="required:true,validType:'customRequired'" />
				     </td>
			    </tr>

				<tr>
					<th>简介</th>
					<td>
						<textarea id="introduction" rows="8" cols="100" name="alumni.introduction" style="width: 700px; height: 300px;" ></textarea>
					</td>
				</tr>

			</table>
		</fieldset>
	</form>
</body>
</html>

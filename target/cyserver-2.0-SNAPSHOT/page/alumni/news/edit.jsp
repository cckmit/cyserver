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

	//编辑器里面的内容图片上传
	KindEditor.ready(function(K) {
		K.create('#content',{
			 fontSizeTable:['9px', '10px', '11px', '12px', '13px', '14px', '15px', '16px', '17px', '18px', '19px', '20px', '22px', '24px', '28px', '32px'],
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
					$('#newsPic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeNewsPic(this)"></div><input type="hidden" name="news.pic" value="'+msg.url+'"/></div>');
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

	
	function submitForm($dialog, $grid, $pjq){
	
		if($('#introduction').val()==''){
			parent.$.messager.alert('提示', '请输入新闻简介', 'error');
			return false;
		}
		if($('#newsUrl').val()==''){
			parent.$.messager.alert('提示', '请输入新闻网页链接', 'error');
			return false;
		}
		if($('input[name="news.pic"]').val()==undefined){
			parent.$.messager.alert('提示', '请上传活动封面图片', 'error');
			return false;
		}
		
		//手机1级栏目
		var type1 = $("#newsType1").val();
		//手机2级栏目
		var type2 = $("#newsType2").val();
		//网页1级栏目
		var webtype1 = $("#webNewsType1").val();
		//网页2级栏目
		var webtype2 = $("#webNewsType2").val();
		
		//if(type1==0 && webtype1==0){
		//	parent.$.messager.alert('提示', '请选择手机或网页新闻的一级栏目', 'error');
		//	return false;
		//}
		if(webtype1==0){
			parent.$.messager.alert('提示', '请选择网页新闻的一级栏目', 'error');
			return false;
		}	
		if((type1 > 0) && (type2==0) && $("#newsType2 option").length>1  ){
			parent.$.messager.alert('提示', '请选择手机新闻二级栏目', 'error');
			return false;
		}
		if((webtype1 > 0) && (webtype2==0) && $("#webNewsType2 option").length>1  ){
			parent.$.messager.alert('提示', '请选择网页新闻二级栏目', 'error');
			return false;
		}
			
		if ($('form').form('validate'))
		{
			$.ajax({
				url : '${pageContext.request.contextPath}/mobile/news/newsAction!updatex.action',
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
	
	
	/**--新闻一级栏目类别修改事件--**/
	function selectNewsType1(){
		if($("#newsType1").val()=="0"){
			$("#newsType2").empty();
			var html = "<option value='0' >请选择</option>";
			$("#newsType2").append(html);
			return;
		}
		//通过ajax取2级栏目
		var params = "parent_id="+$("#newsType1").val()+"&type=1";
		$.ajax({
             type: "GET",
             url: "${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action",
             data: params,
             dataType: "json",
             success: function(data){
             	$("#newsType2").empty(); 
             	var html = "<option value='0' >请选择</option>";
             	for(var i=0;i<data.length;i++){
             		var obj = data[i];
             		html += "<option value=\"" +obj.id+  "\">" + obj.name + "</option>";
             	}
             	$("#newsType2").append(html);
             }
     	});	
	}
	/**--网页新闻一级栏目修改事件--**/
	function selectWebNewsType1(){
		if($("#webNewsType1").val()=="0"){
			$("#webNewsType2").empty();
			var html = "<option value='0' >请选择</option>";
			$("#webNewsType2").append(html);
			return;
		}
		//通过ajax取2级栏目
		var params = "parent_id="+$("#webNewsType1").val()+"&type=1";
		$.ajax({
             type: "GET",
             url: "${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getWebNewsTypeByParent.action",
             data: params,
             dataType: "json",
             success: function(data){
             	$("#webNewsType2").empty(); 
             	var html = "<option value='0' >请选择</option>";
             	for(var i=0;i<data.length;i++){
             		var obj = data[i];
             		html += "<option value=\"" +obj.id+  "\">" + obj.name + "</option>";
             	}
             	$("#webNewsType2").append(html);
             }
     	});	
	}
	
	
	
</script>
</head>
  
<body>
<form method="post" id="editNewsForm">
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
					<input name="news.newsId" type="hidden" id="newsId" 
							value="${news.newsId}">
					<input name="news.title" class="easyui-validatebox"
						style="width: 700px;"
						data-options="required:true,validType:'customRequired'"
						maxlength="30" value="${news.title}"/>
					<input name="news.category" value="${news.category}" type="hidden">
				</td>
			</tr>
			<tr>
				<th>
					新闻简介
				</th>
				<td colspan="3">
					<textarea id="introduction" rows="7" cols="100"
						name="news.introduction">${news.introduction}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					网页链接
				</th>
				<td colspan="3">
					<textarea id="newsUrl" rows="3" cols="100" name="news.newsUrl">${news.newsUrl}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					频道
				</th>
				<td colspan="3">
					<input name="news.channelName" class="easyui-combobox" style="width: 150px;" value="${news.channelName}"
						data-options="editable:false,required:true,
							        valueField: 'channelName',
							        textField: 'channelName',
							        url: '${pageContext.request.contextPath}/newsChannel/newsChannelAction!doNotNeedSecurity_initType.action'" />
				</td>
			</tr>
			<tr>
				<th>
					兴趣标签
				</th>
				<td colspan="3">
				
					<select id="newstype" name="news.type"  class="easyui-combogrid" style="width:150px" data-options=" 
							panelWidth: 200,
							multiple: true,
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
							onLoadSuccess:function(data){
								$('#newstype').combogrid('setValues', ${news.typeStr});
							}
						">
					</select>
				</td>
			</tr>
		
			<tr style="display:none;">
				<th>
					手机新闻栏目
				</th>
				<td colspan="3">
					一级栏目
					<select id="newsType1" name="type1" onchange="selectNewsType1();">
						<option value="0">请选择</option>
						<c:forEach var="type" items="${list1}">
							<option value="${type.id }" <c:if test="${pCategory==type.id}">selected="selected"</c:if> >${type.name }</option>
						</c:forEach>
					</select>
					二级栏目
					<select id="newsType2" name="type2">
						<option value="0">请选择</option>
						<c:forEach var="type" items="${list2}">
						<option value="${type.id }" <c:if test="${news.category==type.id}">selected="selected"</c:if>>${type.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					网页新闻栏目
				</th>
				<td colspan="3">
					一级栏目
					<select id="webNewsType1" name="webType1" onchange="selectWebNewsType1();">
						<option value="0">请选择</option>
						<c:forEach var="type" items="${listweb1}">
							<option value="${type.id }" <c:if test="${pCategoryWeb==type.id}">selected="selected"</c:if> >${type.name }</option>
						</c:forEach>
					</select>
					二级栏目
					<select id="webNewsType2" name="webType2">
						<option value="0">请选择</option>
						<c:forEach var="type" items="${listweb2}">
						<option value="${type.id }" <c:if test="${news.categoryWeb==type.id}">selected="selected"</c:if>>${type.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			
			<tr>
				<th>
					地方
				</th>
				<td colspan="3">
					<input readonly="readonly" id=cityName name="news.cityName" value="${news.cityName}" />
				</td>
			</tr>
			<tr>
				<th>
					时间
				</th>
				<td colspan="3">
					<input name="news.createTime" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false" value="${createTime}" />
				</td>
			</tr>	
			<tr>
				<th>
					新闻内容
				</th>
				<td colspan="3">
					<textarea id="content" name="news.content"
						style="width: 700px; height: 300px;">${news.content}</textarea>
				</td>
			</tr>
			
			<tr>
				<th>
					新闻封面上传
				</th>
				<td colspan="3">
					<c:choose>
						<c:when test="${news.pic!=null and news.pic!=''}">
							<input type="button" id="news_upload_button" value="上传图片" disabled="disabled">
						</c:when>
						<c:otherwise>
							<input type="button" id="news_upload_button" value="上传图片">
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th>
					新闻封面图片
				</th>
				<td colspan="3">
					<div id="newsPic">
						<c:if test="${news.pic!=null and news.pic!=''}">
							<div style="float:left;width:180px;"><img src="${news.pic}" width="150px" height="150px"/><div class="bb001" onclick="removeNewsPic(this)"></div><input type="hidden" name="news.pic" value="${news.pic}"/></div>
						</c:if>
					</div>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
</body>
</html>
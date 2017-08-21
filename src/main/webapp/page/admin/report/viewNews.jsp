<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
long check = WebUtil.toLong(request.getParameter("check")) ;
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

	$(function(){
        //富文本
        window.um = UM.getEditor('content',{readonly: true});

		$.ajax({
			url : '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getUserDeptId.action',
			success : function(result){
				if(result == 1){
					$('#sourcetr').hide();
				}
			}
		});
		var jsonstr = [
			{
				"id": "10",
				"name": "手机"
			},
			{
				"id": "30",
				"name": "微信"
			},
			{
				"id": "20",
				"name": "网页"
			}
		];
		$('#qudao').combogrid("grid").datagrid("loadData", jsonstr);
		if('${news.channel}' != '' && '${news.channel}' != null){
			$('#qudao').combogrid('setValues', '${news.channel}'.replace(/\s/g, ''));
		}
		//栏目
		var sHtml = '';
		var lines = '${news.channels}'.split( ',' );
		for( var i = 0; i < lines.length; ++i )
		{
			var duan = lines[i].split( '_' );
			sHtml = sHtml + '<tr><th>' + duan[0] + '新闻栏目</th><td colspan="3">' + duan[1] + '</td></tr> ';
		}
		$('#trQudao').after( sHtml );
	});
	function submitForm($dialog, $grid, $pjq){

		var json = {"bussId":$('#bussId').val(),
					"status":$('#status').combobox('getValue'),
					"handleOpinion": $('#handleOpinion').val()
		};
		$.ajax({
			url : "${pageContext.request.contextPath}/mobile/news/newsAction!saveCheck.action",
			data : json,
			dataType : 'json',
			success : function(result)
			{
				if (result.success)
				{
					$grid.datagrid('reload');
					$dialog.dialog('destroy');
					window.parent.refreshMsgNum();
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
	/**
	 * 审批
	 * @param $dialog
	 * @param $grid
	 * @param $pjq
     */
	function submitForm($dialog, $grid, $pjq ,status){
		var json = {"bussId":$('#bussId').val(),
					"status":status
		};
		$.ajax({
			url : "${pageContext.request.contextPath}/mobile/news/newsAction!saveCheck.action",
			data : json,
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


</script>
</head>
  
  <body>
<form method="post" id="viewNewsForm">
	<input name="checkNews.newsId" type="hidden" id="bussId"
		   value="${news.newsId}">
	<% if(check==1){ %>
	<fieldset>
		<legend>
			审核信息
		</legend>
		<table class="ta001">
			<tr>
				<th>审核状态</th>
				<td>
					<select class="easyui-combobox" data-options="editable:false" name="checkNews.status" id = "status" style="width: 150px;">
						<option value="20">通过</option>
						<option value="30">不通过</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>审核意见</th>
				<td>
					<textarea rows="5" cols="80" name="checkNews.handleOpinion" id="handleOpinion"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	</br>
	<%} %>
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
					<input name="news.title" class="easyui-validatebox" disabled="disabled"
						style="width: 700px;"
						data-options="required:true,validType:'customRequired'"
						maxlength="30" value="${news.title}"/>
				</td>
			</tr>
			<tr>
				<th>
					新闻简介
				</th>
				<td colspan="3">
					<textarea id="introduction" rows="7" cols="100" disabled="disabled"
						name="news.introduction">${news.introduction}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					网页链接
				</th>
				<td colspan="3">
					<textarea id="newsUrl" rows="3" cols="100" name="news.newsUrl" disabled="disabled">${news.newsUrl}</textarea>
				</td>
			</tr>
			<tr>
				<th>
					频道
				</th>
				<td colspan="3">
					<input name="news.channelName" class="easyui-combobox" style="width: 150px;" value="${news.channelName}" disabled="disabled"
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
					${news.type}
				</td>
			</tr>

			<tr id="sourcetr">
				<th>
					栏目来源
				</th>
				<td>
					<select id="source" name="news.source" class="easyui-combobox" style="width: 150px" data-options="
						editable:false,required:true,panelHeight:50,
						onLoadSuccess:function(){
								if( ${news.source} == 2 ){
									$('#source' ).combobox( 'setValue', 2 );
								}else{
									$('#source' ).combobox( 'setValue', 1 );
								}
							}
						" disabled>
						<option value="1">总会</option>
						<option value="2">本会</option>
					</select>
				</td>
			</tr>
			

			<tr id="trQudao">
				<th>渠道</th>
				<td colspan="3">
					<select  id="qudao" name="news.channel" class="easyui-combogrid" style="width:150px" data-options="
							panelWidth: 200,
							multiple: true,
							required:true,
							idField: 'id',
							textField: 'name',
							columns: [[
								{field:'id',checkbox:true},
								{field:'name',title:'标签名称',width:80}
							]],
							fitColumns: true,
							editable:false,
							panelHeight:110
							" disabled>
					</select>
				</td>
			</tr>
			<!--
			<tr>
				<th>
					新闻栏目
				</th>
				<td colspan="3" id="newsChannels">

				</td>
			</tr>
			-->
			<%--<tr>
				<th>
					网页新闻栏目
				</th>
				<td colspan="3">
					${news.categoryWebName}
				</td>
			</tr>
			<tr>
				<th>
					微信新闻栏目
				</th>
				<td colspan="3">
					${news.categoryWechatName}
				</td>
			</tr>--%>
			<c:if test="${news.origin==1 || news.originP==1 || news.originWeb==1 || news.originWebP==1}">
			<tr>
				<th>
					所属组织
				</th>
				<td colspan="3">
					${news.dept_name}
				</td>
			</tr>
			</c:if>
			
			<c:if test="${news.cityName!=null && news.cityName!=''}">
			<tr>
				<th>所属地区</th>
				<td colspan="3">
					${news.cityName}
				</td>
			</tr>
			</c:if>
			
			<%--<tr>--%>
				<%--<th rowspan="2">--%>
					<%--浏览权限--%>
				<%--</th>--%>
				<%--<td >--%>
				    <%--校友会群：${news.alumniidStr}--%>
				    <%----%>
				<%--</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2">--%>
				 <%--行业群：${news.profession}--%>

				<%--</td>--%>
			<%--</tr>--%>
			
			<tr>
				<th>
					时间
				</th>
				<td colspan="3">
					${news.createDate}
				</td>
			</tr>
			
			<tr>
				<th>
					新闻内容
				</th>
				<td colspan="3">
					<script id="content" name="news.content" type="text/plain" style="width:700px;height:300px;">
						${news.content}
					</script>
				</td>
			</tr>
			
			<tr>
				<th>
					新闻封面图片
				</th>
				<td colspan="3">
					<div id="newsPic">
						<c:if test="${news.pic!=null and news.pic!=''}">
							<div style="float:left;width:180px;"><img src="${news.pic}" width="150px" height="150px"/><div class="bb001"></div><input type="hidden" name="news.pic" value="${news.pic}"/></div>
						</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<th>
					新闻状态
				</th>
				<td colspan="3">
					${news.status}
				</td>
			</tr>
			<tr>
				<th>
					审批意见
				</th>
				<td colspan="3">
					${news.opinions}
				</td>
			</tr>
		</table>
	</fieldset>
</form>
  </body>
</html>
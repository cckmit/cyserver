<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
%>
<%
	String sign = (String)getServletContext().getAttribute("sign");
	String schoolSign = (String)getServletContext().getAttribute("schoolSign");
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
	</head>
	<script>
	$(function(){
		var msgType=$("input[name='msgType']:checked").val();
		$("#msgType1").val(msgType);
		if(msgType=='普通短信'){
			if(!$("#template").is(":hidden")) 
			{ 
				$("#template").hide();
			}
			if($("#general").is(":hidden")) 
			{ 
				$("#general").show();
			}
		}else{
			if($("#template").is(":hidden")) 
			{ 
				$("#template").show();
			}
			if(!$("#general").is(":hidden")) 
			{ 
				$("#general").hide();
			}
		}
		var smslength=$("#comment").val().length+$("#deptAbb").val().length+2;
		$('#CurWordNum').text(smslength);
		$('#comment').bind('focus keyup input paste',function(){  //采用几个事件来触发（已增加鼠标粘贴事件）
			var smslength=$("#comment").val().length+$("#deptAbb").val().length+2;
			if($("input[name=check1]").is(":checked")){
				smslength=smslength+3;
			}
			$('#CurWordNum').text(smslength);
			if(smslength>0&&smslength%70==0){
				$('#CurLineNum').text(Math.floor(smslength/70));
			}
			else if(smslength==0||smslength%70>0){
				$('#CurLineNum').text(Math.floor(smslength/70)+1);
			}
		});
		$('#deptAbb').bind('focus keyup input paste',function(){  //采用几个事件来触发（已增加鼠标粘贴事件）
			var smslength=$("#comment").val().length+$("#deptAbb").val().length+2;
			if($("input[name=check1]").is(":checked")){
				smslength=smslength+3;
			}
			$('#CurWordNum').text(smslength);
			if(smslength>0&&smslength%70==0){
				$('#CurLineNum').text(Math.floor(smslength/70));
			}
			else if(smslength==0||smslength%70>0){
				$('#CurLineNum').text(Math.floor(smslength/70)+1);
			}
		});
	});
	
	function doSend(){
		$('#toAddress').combogrid('setValue',$('#toAddress').combogrid('getText'));
		var msgType=$("input[name='msgType']:checked").val();
		if(msgType=='普通短信'){
    		if($('#comment').val()==''){
    			parent.$.messager.alert('错误', '请填写短信内容', 'error');
    			parent.$.messager.progress('close');
    			return false;
    		}
    	}else{
    		if($('#smsTemplate').combobox('getValue')==''){
    			parent.$.messager.alert('错误', '请选择短信模板', 'error');
    			parent.$.messager.progress('close');
    			return false;
    		}
    		var flag= true
    		$("input[name='msgParam']").each(function(){
				if ($(this).val() == '')
					{
						flag = false;
					}
				});
				if (!flag)
				{
					parent.$.messager.alert('错误', '请填写短信模板参数', 'error');
					parent.$.messager.progress('close');
					return false;
				}
			}
		if ($('form').form('validate')) {
			$.ajax({
				url : '${pageContext.request.contextPath}/msgSend/msgSendAction!addMsgSend.action',
				data :$('form').serialize(),
				dataType : 'json',
				success : function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
					} else {
						parent.$.messager.alert('提示', result.msg, 'error');
					}
				},
				beforeSend:function(){
					parent.$.messager.progress({
						text : '数据提交中....'
					});
				},
				complete:function(){
					parent.$.messager.progress('close');
				}
			});
		}
	}

	function doCancel()
	{
		$("#peopleNum").text("0");
		$("#telphone").text("");
		$("#telphone1").text("");
	}

	function f1()
	{
		var number = parseInt($("#CurWordNum").text());
		if ($("input[name=check1]").is(":checked"))
		{
			$('#CurWordNum').text(number + 3);
			if ((number + 3) % 70 == 0)
			{
				$('#CurLineNum').text(Math.floor((number + 3) / 70));
			} else
			{
				$('#CurLineNum').text(Math.floor((number + 3) / 70) + 1);
			}
		} else
		{
			$('#CurWordNum').text(number - 3);
			if ((number - 3) % 70 == 0)
			{
				$('#CurLineNum').text(Math.floor((number - 3) / 70));
			} else
			{
				$('#CurLineNum').text(Math.floor((number - 3) / 70) + 1);
			}
		}
	}

	function addParam(number)
	{
		removeParam();
		if ($("#templateContent").is(":hidden"))
		{
			$("#templateContent").show();
		}
		if (number != 0)
		{
			if ($("#templateParam").is(":hidden"))
			{
				$("#templateParam").show();
			}
			text = '';
			for ( var i = 0; i < number; i++)
			{
				text += "<input name='msgParam' type='text' value=''/><br/><br/>"
			}
			$('#smsParam').html(text);
		} else
		{
			if (!$("#templateParam").is(":hidden"))
			{
				$("#templateParam").hide();
			}
		}
	}
	function removeParam()
	{
		if (!$("#templateContent").is(":hidden"))
		{
			$("#templateContent").hide();
		}
		if (!$("#templateParam").is(":hidden"))
		{
			$("#templateParam").hide();
		}
		$("input[name='msgParam']").remove();
	}
	function changeType()
	{
		var msgType = $('input[name="msgType"]:checked').val();
		$("#msgType1").val(msgType);
		if (msgType == '普通短信')
		{
			if (!$("#template").is(":hidden"))
			{
				$("#template").hide();
			}
			if ($("#general").is(":hidden"))
			{
				$("#general").show();
			}
			$('#smsTemplate').combobox('clear');
			$('#smsTemplateContent').val('');
			removeParam();
		} else
		{
			if ($("#template").is(":hidden"))
			{
				$("#template").show();
			}
			if (!$("#general").is(":hidden"))
			{
				$("#general").hide();
			}
			$('#comment').val('');
			$('#CurLineNum').text("1");
			var smslength = $("#comment").val().length + $("#deptAbb").val().length + 2;
			$('#CurWordNum').text(smslength);
		}
	}
</script>
	<body class="easyui-layout" data-options="fit:true,border:false">
		<form id="msgForm" action="" method="post">
			<table class="ta001" >
				<tr>
					<th>
						接收人：
					</th>
					<td>
						<select id="toAddress" name="msgSend.telphone"  class="easyui-combogrid" style="width:725px" data-options=" 
								required:true,
								validType:'customRequired',
								multiple: true,
								idField: 'telId',
								textField: 'telId',
								url: '${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_getAllUserList.action',
								method: 'get',
								columns: [[
									{field:'userId',checkbox:true},
									{field:'userName',title:'用户姓名'},
									{field:'birthday',title:'生日'},
									{field:'fullName',title:'所属'},
									{field:'telId',title:'电话号码'}
								]],
								fitColumns: true,
								editable:true
							">
						</select>
					</td>
				</tr>
				<tr >
					<th >
						短信类型：
					</th>
					<td >
						<input name="msgType" type="radio" value="普通短信" checked="checked" onchange="changeType()" style="width: 15px;"/>普通短信
						<input name="msgType" type="radio" value="模板短信" onchange="changeType()" style="width: 15px;"/>模板短信
						<input name="msgType1" id="msgType1" type="hidden">
					</td>
				</tr>
				<tr id="template">
					<th >
						短信模板：
					</th>
					<td >
							<input id="smsTemplate" class="easyui-combobox"
								data-options="editable:false,valueField:'msgTemplateId',textField:'msgTemplateTitle',url:'${pageContext.request.contextPath}/msgTemplate/msgTemplateAction!doNotNeedSessionAndSecurity_getAll.action'
	    					,onSelect:function(rec){$('#smsTemplateContent').val(rec.msgTemplateContent);addParam(rec.msgTemplateParamNumber);}">
							<a href="javascript:void(0)" onclick="$('#smsTemplate').combobox('clear');$('#smsTemplateContent').val('');removeParam()">清空</a>
					</td>
				</tr>
				<tr  id="templateContent" style="display: none">
					<th >
						模板内容：
					</th>
					<td >
							<textarea rows="4" cols="88" name="msgTemplateContent" id="smsTemplateContent" readonly="readonly"></textarea>
					</td>
				</tr>
				<tr  id="templateParam" style="display: none">
					<td align="right" >
						短信参数：
					</td>
					<td >
						<div id="smsParam" style="margin-left: 5px; margin-bottom: 5px; margin-top: 5px;">

						</div>
					</td>
				</tr>
				<tr id="general">
					<th >
						短信内容：
					</th>
					<td height="80" >
							<textarea id="comment" name="msgSend.content" cols="88" rows="4"></textarea>
							<br>
							当前短信字数：
							<i id="CurWordNum" style="font-size: 20px; color: red;">0</i>个 当前短信条数：
							<i id="CurLineNum" style="font-size: 20px; color: red;">1</i>条&nbsp;&nbsp;&nbsp;
							<i style="color: red;">短信字数计算规则：短信内容与 '【签名】'之和</i>
					</td>
				</tr>
				<%--<tr >
					<th>
						添加称呼：
					</th>
					<td >
							<input name="check1" type="checkbox" onchange="f1()" style="width: 15px;"/>
							<i style="color: red;">示例:张三:xxxx</i>
							<input id="check" name="msgSendModel.check" value="0" type="hidden" />
							<input id="groups" name="msgSendModel.group" type="hidden">
					</td>
				</tr>
				--%>
				<tr>
					<th>
						前置签名：
					</th>
					<td >
							<input id="deptAbb" readonly="readonly" value="<%=sign%>" class="easyui-validatebox" data-options="required:true" maxlength="30" />
					</td>
				</tr>
				<tr>
					<th>
						后置签名：
					</th>
					<td >
							<input id="deptAbb" readonly="readonly" value="<%=schoolSign%>" class="easyui-validatebox" data-options="required:true" maxlength="30" />
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="发送短信">
							<a href="javascript:void(0)" onclick="doSend()" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">发送</a>&nbsp;&nbsp;
						</authority:authority>	
					</td>
				</tr>
			</table>
		</form>
		
	</body>
</html>
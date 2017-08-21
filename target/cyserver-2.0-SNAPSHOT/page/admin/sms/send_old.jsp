<%@page import="com.cy.system.Global"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@ taglib uri="/authority" prefix="authority"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
%>
<%
	String sign = Global.sign;
	
	//var params = "userInfo.userName="+userName+"&schoolId="+schoolId+"&departId="+departId+"&classId="+classId+"&userInfo.majorId="+majorId;
	String userName = WebUtil.isEmpty(request.getParameter("userName"))?"":request.getParameter("userName") ;
	String schoolId = WebUtil.isEmpty(request.getParameter("schoolId"))?"":request.getParameter("schoolId") ;
	String departId = WebUtil.isEmpty(request.getParameter("departId"))?"":request.getParameter("departId") ;
	String classId = WebUtil.isEmpty(request.getParameter("classId"))?"":request.getParameter("classId") ;
	String majorId = WebUtil.isEmpty(request.getParameter("majorId"))?"":request.getParameter("majorId") ;
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
		<script type="text/javascript" src="<%=path %>/jslib/valiDate.js"></script>
		<jsp:include page="../../../inc.jsp"></jsp:include>
	</head>
	<script>
	var userName = "<%=userName %>";
	var schoolId = "<%=schoolId %>";
	var departId = "<%=departId %>";
	var classId = "<%=classId %>";
	var majorId = "<%=majorId %>";
	//通过ajax查询
	var params = "userInfo.userName="+userName+"&schoolId="+schoolId+"&departId="+departId+"&classId="+classId+"&userInfo.majorId="+majorId;
	var url ="<%=path%>/userInfo/userInfoAction!doNotNeedSecurity_getAllUserList_dataGrid.action";
	
	$.ajax({
		type: "post",
		url:url,
		data: params,
		dataType: "json",
		success: function (data) {
	    	var list = data;
	    	var phoneNumText = "";

	    	for(var i=0;i<list.length;i++){
	    		var obj = list[i];
	    		if(list.length == 1){
	    			phoneNumText = phoneNumText + obj.telId;
	    		}else if(list.length > 1 && i<list.length-1 ){
	    			phoneNumText = phoneNumText + obj.telId+",";
	    		}else{
	    			phoneNumText = phoneNumText + obj.telId;
	    		}
	    	}
	    	$("#toAddress").val(phoneNumText);
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
			$('#CurWordNum').text(smslength);
			if(smslength>0&&smslength%67==0){
				$('#CurLineNum').text(Math.floor(smslength/67));
			}
			else if(smslength==0||smslength%67>0){
				$('#CurLineNum').text(Math.floor(smslength/67)+1);
			}
		});
		$('#deptAbb').bind('focus keyup input paste',function(){  //采用几个事件来触发（已增加鼠标粘贴事件）
			var smslength=$("#comment").val().length+$("#deptAbb").val().length+2;
			$('#CurWordNum').text(smslength);
			if(smslength>0&&smslength%67==0){
				$('#CurLineNum').text(Math.floor(smslength/67));
			}
			else if(smslength==0||smslength%67>0){
				$('#CurLineNum').text(Math.floor(smslength/67)+1);
			}
		});
	});
	
	function doSend(){
		var msgType=$("input[name='msgType']:checked").val();
		
		if($("#toAddress").val()==null || $("#toAddress").val() == ""){
				parent.$.messager.alert('错误', '请填写收信人', 'error');
    			return false;
		}
		
		if(msgType=='普通短信'){
    		if($('#comment').val()==''){
    			parent.$.messager.alert('错误', '请填写短信内容', 'error');
    			return false;
    		}
    	}else{
    		if($('#smsTemplate').combobox('getValue')==''){
    			parent.$.messager.alert('错误', '请选择短信模板', 'error');
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
					return false;
				}
		}
		
		//核对手机号
		var phoneNumText = $("#toAddress").val();
		if(Validate.isNull(phoneNumText)){
			parent.$.messager.alert('错误', '手机号不能为空', 'error');
			return false;
		}
		
		var phoneList = phoneNumText.split(",");
		for(var i=0;i<phoneList.length;i++){
			var phone = phoneList[i];
			if(!Validate.isPhone(phone)){
				parent.$.messager.alert('错误', phone+'手机号非法', 'error');
				return;
			}
		}
		
		if ($('form').form('validate')) {
			$.ajax({
				url : '${pageContext.request.contextPath}/msgSend/msgSendAction!addMsgSend1.action',
				data :$('form').serialize(),
				dataType : 'json',
				success : function(result) {
					if (result.success) {
						parent.parent.$.messager.alert('提示', result.msg, 'info');
					} else {
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
			var smslength = $("#comment").val().length + $("#deptAbb").val().length + 2+$("#deptAbb1").val().length + 2;
			$('#CurWordNum').text(smslength);
		}
	}
</script>
	<body>
		<form id="msgForm" action="" method="post">
		<fieldset>
				<legend>
					基本信息
				</legend>
			<table class="ta001" >
				<tr>
					<th>
						接收人：
					</th>
					<td>
						<!--
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
						-->
						<textarea id="toAddress" name="msgSend.telphone"  style="width:725px;height: 100px"></textarea>
						
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
	    					,onSelect:function(rec){$('#smsTemplateContent').val(rec.msgTemplateContent);addParam(rec.msgTemplateParamNumber);}
	    					,prompt:'--请选择--',
	    					icons:[{
								iconCls:'icon-clear',
								handler: function(e){
									$('#smsTemplate').combobox('clear');$('#smsTemplateContent').val('');removeParam();
								}
							}]
	    					">
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
						短信签名：
					</th>
					<td >
							<input id="deptAbb" readonly="readonly" value="<%=sign%>" class="easyui-validatebox" data-options="required:true" maxlength="30" />
					</td>
				</tr>
			</table>
			</fieldset>
		</form>
	</body>
</html>
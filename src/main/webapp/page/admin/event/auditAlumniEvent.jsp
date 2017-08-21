<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
        $(function () {
            if ($('#eventId').val() != null && $.trim($('#eventId').val()) != '') {
                $.ajax({
                    url: '${pageContext.request.contextPath}/event/eventAction!getByIdAlumni.action',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'event.id': result.id,
                                'event.title': result.title,
                                'event.place': result.place,
                                'event.region': result.region,
                                'event.type': result.type,
                                'event.category': result.category,
                                'event.alumniName': result.alumniName,
                                'event.startTime': result.startTime,
                                'event.endTime': result.endTime,
                                'event.signupStartTime': result.signupStartTime,
                                'event.signupEndTime': result.signupEndTime,
                                'event.minPeople': result.minPeople + '',
                                'event.maxPeople': result.maxPeople,
                                'event.needSignIn': result.needSignIn + '',
                                'event.signInCode': result.signInCode,
                                'event.content': result.content,
                                'event.needNotification': result.needNotification + '',
                                'event.notification': result.notification,
                                'event.auditStatus':'1'
                            });
                            
                            if(result.pic != null && result.pic != '') {
                            	$('#eventPic').append('<div style="float:left;width:180px;"><img src="'+result.pic+'" width="150px" height="150px"/><input type="hidden" name="event.pic" id="eu" value="'+result.pic+'"/></div>');
                            }
                        }
                    },
                    beforeSend: function () {
                        parent.$.messager.progress({
                            text: '数据加载中....'
                        });
                    },
                    complete: function () {
                        parent.$.messager.progress('close');
                    }
                });
            }
            
            $('#eventForm .ta001 :input[name^=event]').attr('disabled', true);
            $('#auditOpinion').attr('disabled', false);
        });
        
        var submitForm = function ($dialog, $grid, $pjq) {
            if ($('form').form('validate')) {
				
                $.ajax({
                    url: '${pageContext.request.contextPath}/event/eventAction!auditAlumni.action',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.success) {
                            $grid.datagrid('reload');
                            $dialog.dialog('destroy');
                            $pjq.messager.alert('提示', result.msg, 'info');
                        } else {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    },
                    beforeSend: function () {
                        parent.$.messager.progress({
                            text: '数据提交中....'
                        });
                    },
                    complete: function () {
                        parent.$.messager.progress('close');
                    }
                });
            }
        };
    </script>
</head>

<body>
<form method="post" id="eventForm" class="form">
    <input name="event.id" type="hidden" id="eventId" value="${param.id}">
    <input name="event.type" type="hidden" value="">
    <fieldset>
		<legend>
			审核信息
		</legend>
		<table class="ta001">
			<tr>
				<th>审核状态</th>
				<td>
					<select id="auditStatus" class="easyui-combobox" data-options="editable:false" name="event.auditStatus" style="width: 150px;">
						<option value="1">通过</option>
						<option value="2">不通过</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>审核意见</th>
				<td>
					<textarea id="auditOpinion" rows="5" cols="80" name="event.auditOpinion"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	
    <fieldset>
		<legend>
			活动基本信息
		</legend>
		<table class="ta001">
			<tr>
				<th>
					活动标题
				</th>
				<td colspan="3">
					<input name="event.title" style="width: 500px;"/>
				</td>
			</tr>
			<tr>
				<th>
					活动地点
				</th>
				<td colspan="3">
					<input name="event.place" style="width: 500px;"/>
				</td>
			</tr>
			<tr>
				<th>
					活动地域
				</th>
				<td colspan="3">
					<input name="event.region" style="width: 500px;"/>
				</td>
			</tr>
			<tr>
				<th>
					发起组织
				</th>
				<td colspan="3">
					<input name="event.alumniName" style="width: 500px;" />
				</td>
			</tr>
			<tr>
				<th>
					活动类别
				</th>
				<td colspan="3">
					<input name="event.category" class="easyui-combobox" style="width: 200px;" disabled="disabled"
						data-options="  
						valueField: 'dictName',  
						textField: 'dictName',  
						editable:false,
						url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('活动类别') 
					" />
				</td>
			</tr>
			
			<tr>
				<th>
					报名开始
				</th>
				<td>
					<input name="event.signupStartTime" id="signupStartTime" class="easyui-datetimebox" disabled="disabled"
						data-options="editable:false" style="width: 200px;" />
				</td>
				<th>
					报名截止
				</th>
				<td>
					<input name="event.signupEndTime" id="signupEndTime" class="easyui-datetimebox" disabled="disabled"
						data-options="editable:false" style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>
					开始时间
				</th>
				<td>
					<input name="event.startTime" id="startTime" class="easyui-datetimebox" disabled="disabled"
						data-options="editable:false" style="width: 200px;" />
				</td>
				<th>
					结束时间
				</th>
				<td>
					<input name="event.endTime" id="endTime" class="easyui-datetimebox" disabled="disabled"
						data-options="editable:false" style="width: 200px;" />
				</td>
			</tr>
			
			<tr>
				<!--<th>
					人数下限
				</th>
				<td>
					<input name="event.minPeople" class="easyui-validatebox"  style="width: 150px;" value="0" />
				</td>
				-->
				<input name="event.minPeople" type="hidden" value="0">
				<th>
					人数上限
				</th>
				<td>
					<input name="event.maxPeople" class="easyui-validatebox"  style="width: 150px;" value="0" />
					&nbsp;&nbsp;&nbsp;&nbsp;( 0表示无限制 )
				</td>
			</tr>
			<tr>
				<th>
					需要签到
				</th>
				<td>
					<select name="event.needSignIn" class="easyui-combobox" style="width: 155px;" data-options="editable:false" disabled="disabled">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
				</td>
				
				<th>
					签到码
				</th>
				<td>
					<input name="event.signInCode" style="width: 150px;"/>
				</td>
			</tr>
			<tr>
				<th>
					活动介绍
				</th>
				<td colspan="3">
					<textarea id="content" rows="7" cols="100"
						name="event.content"></textarea>
				</td>
			</tr>

			<tr>
				<th>
					活动海报图片
				</th>
				<td colspan="3">
					<div id="eventPic"></div>
				</td>
			</tr>
		</table>
	</fieldset>
	
	<fieldset>
		<legend>
			活动通知
		</legend>
		<table class="ta001">
			<tr>
				<th>
					发送通知
				</th>
				<td colspan="3">
					<select name="event.needNotification" class="easyui-combobox" style="width: 155px;" data-options="editable:false" disabled="disabled">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
				</td>
			</tr>
			<tr>
				<th>
					通知内容
				</th>
				<td colspan="3">
					<textarea id="notification" rows="7" cols="100"
						name="event.notification"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
</body>
</html>

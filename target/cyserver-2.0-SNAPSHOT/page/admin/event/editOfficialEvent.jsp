<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.cy.system.Global" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/Base64RichText.js"></script>

	<script type="text/javascript">
    	var editor;

        $(function () {
			//富文本
            editor = UM.getEditor('content');

            if ($('#eventId').val() != null && $.trim($('#eventId').val()) != '') {
                $.ajax({
                    url: '${pageContext.request.contextPath}/event/eventAction!getByIdOfficial.action',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {

                        if (result.id != undefined) {

                            $('form').form('load', {
                                'event.id': result.id,
                                'event.title': result.title,
                                'event.place': result.place,
                                'event.type': result.type,
                                'event.category': result.category,
                                'event.organizer': result.organizer,
                                'event.dept_id': result.dept_id,
								'event.dept_name': result.alumniName,
                                'event.startTime': result.startTime,
                                'event.endTime': result.endTime,
                                'event.signupStartTime': result.signupStartTime,
                                'event.signupEndTime': result.signupEndTime,
                                'event.minPeople': result.minPeople + '',
                                'event.maxPeople': result.maxPeople,
                                'event.needSignIn': result.needSignIn + '',
                                'event.signInCode': result.signInCode + '',
                                'event.content': result.content,
                                'event.needNotification': result.needNotification + '',
                                'event.notification': result.notification,
								'event.needAuth':result.needAuth
                            });
							$('#dept_name').val( result.alumniName );


                            if(result.pic != null && result.pic != '') {
                            	$('#eventPic').append(
                            			'<div style="float:left;width:180px;">' +
										'<img src="'+result.pic+'" width="150px" height="150px"/>' +
										'<div class="bb001" onclick="removeeventPic(this)">' +
										'</div>' +
										'<input type="hidden" name="event.pic" id="eu" value="'+result.pic+'"/>' +
										'</div>');
                            	$("#event_upload_button").prop('disabled', 'disabled');
                            }

                            editor.setContent(result.content);
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


            var button = $("#event_upload_button"), interval;
			new AjaxUpload(button, {
				action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
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
						$('#eventPic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeeventPic(this)"></div><input type="hidden" name="event.pic" value="'+msg.url+'"/></div>');
						$("#event_upload_button").prop('disabled', 'disabled');
					} else {
						$.messager.alert('提示', msg.message, 'error');
					}
				}
			});

            if('${sessionScope.user.role.systemAdmin}' == '1') {
				$("#dept").hide();
			}

        });
		/*$(function () {

			if ($('#eventId').val() > 0) {
				$.ajax({
					url: '${pageContext.request.contextPath}/event/eventAction!getByIdOfficial.action',
					data: $('form').serialize(),
					dataType: 'json',
					success: function (result) {

						if (result.id != undefined) {

							$('form').form('load', {
								'event.id': result.id,
								'event.title': result.title,
								'event.place': result.place,
								'event.type': result.type,
								'event.category': result.category,
								'event.organizer': result.organizer,
								'event.dept_id': result.dept_id,
								'event.dept_name': result.alumniName,
								'event.startTime': result.startTime,
								'event.endTime': result.endTime,
								'event.signupStartTime': result.signupStartTime,
								'event.signupEndTime': result.signupEndTime,
								'event.minPeople': result.minPeople + '',
								'event.maxPeople': result.maxPeople,
								'event.needSignIn': result.needSignIn + '',
								'event.signInCode': result.signInCode + '',
								'event.content': result.content,
								'event.needNotification': result.needNotification + '',
								'event.notification': result.notification
							});

							if(result.pic != null && result.pic != '') {
								$('#eventPic').append('<div style="float:left;width:180px;"><img src="'+result.pic+'" width="150px" height="150px"/><div class="bb001" onclick="removeeventPic(this)"></div><input type="hidden" name="event.pic" id="eu" value="'+result.pic+'"/></div>');
								$("#event_upload_button").prop('disabled', 'disabled');
							}

							editor.html(result.content);
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

			var button = $("#event_upload_button"), interval;
			new AjaxUpload(button, {
				action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
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
						$('#eventPic').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeeventPic(this)"></div><input type="hidden" name="event.pic" value="'+msg.url+'"/></div>');
						$("#event_upload_button").prop('disabled', 'disabled');
					} else {
						$.messager.alert('提示', msg.message, 'error');
					}
				}
			});

			if('${sessionScope.user.role.systemAdmin}' == '1' ) {
				$("#dept").hide();
			}
		});*/
        
        function removeeventPic(eventPic) {
			$(eventPic).parent().remove();
			$("#event_upload_button").prop('disabled', false);
		}
		
        var submitForm = function ($dialog, $grid, $pjq) {

            if ($('form').form('validate')) {
            	if(editor.getContent().trim()==''){
					parent.$.messager.alert('提示', '请输入活动介绍', 'error');
					return false;
				}

				var startTime = new Date(Date.parse($('#startTime').datetimebox('getValue')));
				var endTime = new Date(Date.parse($('#endTime').datetimebox('getValue')));
				var signupStartTime = new Date(Date.parse($('#signupStartTime').datetimebox('getValue')));
				var signupEndTime = new Date(Date.parse($('#signupEndTime').datetimebox('getValue')));
				if(startTime > endTime) {
					parent.$.messager.alert('提示', '活动开始时间必须早于结束时间', 'error');
					return false;
				}
				if(signupStartTime > signupEndTime) {
					parent.$.messager.alert('提示', '报名开始时间必须早于截止时间', 'error');
					return false;
				}
				if(signupStartTime > startTime) {
					parent.$.messager.alert('提示', '报名开始时间必须早于活动开始时间', 'error');
					return false;
				}
				if(signupEndTime > endTime) {
					parent.$.messager.alert('提示', '报名截止时间必须早于活动结束时间', 'error');
					return false;
				}
				
				var needNotification = $('#needNotification').val();
		        if(needNotification == 'true' && $('#notification').val().trim()=='') {
		         	parent.$.messager.alert('提示', '请输入通知内容', 'error');
		         	return false;
		        }
                var resultStr = $('form').serialize();
                //富文本是否转编码 lixun 2017.5.5
                if( '<%=isRichTextConvert%>' == '1' )
                {
                    var submitArray = resultStr.split('&');
                    resultStr = '';
                    for(var i in submitArray){
                        var item = submitArray[i];
                        if(item.indexOf('event.content') >= 0){
                            item = 'event.content=' + encodeURIComponent(strToBase64(editor.getContent()));
                        }
                        if(i > 0){
                            resultStr += '&';
                        }
                        resultStr += item;
                    }
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/event/eventAction!update.action',
                    data: resultStr,
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

		$('#startTime').datebox({

			formatter: function(date){ return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate(); },

			parser: function(date){ return new Date(Date.parse(date.replace(/-/g,"/"))); }

		});
    </script>
</head>

<body>
<form method="post" id="eventForm" class="form">
    <input name="event.id" type="hidden" id="eventId" value="${param.id}">
    <input name="event.type" type="hidden" value="">
    <input name="event.signInCode" type="hidden" value="">
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
					<input name="event.title" class="easyui-validatebox"
						style="width: 500px;"
						data-options="required:true,validType:'customRequired'"
						maxlength="30" />
				</td>
			</tr>
			<tr>
				<th>
					活动地点
				</th>
				<td colspan="3">
					<input name="event.place" class="easyui-validatebox"
						style="width: 500px;"
						data-options="required:true,validType:'customRequired'"
						maxlength="100" />
				</td>
			</tr>
			<tr>
				<th>
					主办方
				</th>
				<td colspan="3">
					<input name="event.organizer" style="width: 500px;" />
				</td>
			</tr>
			<tr id="dept">
				<th>
					创建组织
				</th>
				<td colspan="3">
					<!--
					<input name="event.dept_id" class="easyui-combobox" style="width: 200px;" id="select_dept"
						data-options="  
						valueField: 'deptId',  
						textField: 'deptName',  
						editable:false,
						url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getUserDepts.action'
					" />
					-->
					<input name="event.dept_id" type="hidden" id="dept_id" value="" />
					<input ame="event.dept_name" style="width: 200px;" id="dept_name" readonly="readonly" />

				</td>
			</tr>
			<tr>
				<th>
					活动类别
				</th>
				<td colspan="3">
					<input name="event.category" class="easyui-combobox" style="width: 200px;" 
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
					<input name="event.signupStartTime" id="signupStartTime" class="easyui-datetimebox " 
						data-options="editable:false,required:true,validType:'customRequired'" style="width: 200px;" />
				</td>
				<th>
					报名截止
				</th>
				<td>
					<input name="event.signupEndTime" id="signupEndTime" class="easyui-datetimebox " 
						data-options="editable:false,required:true,validType:'customRequired'" style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<th>
					开始时间
				</th>
				<td>
					<input name="event.startTime" id="startTime" class="easyui-datetimebox " 
						data-options="editable:false,required:true,validType:'customRequired'" style="width: 200px;" />
				</td>
				<th>
					结束时间
				</th>
				<td>
					<input name="event.endTime" id="endTime" class="easyui-datetimebox " 
						data-options="editable:false,required:true,validType:'customRequired'" style="width: 200px;" />
				</td>
			</tr>
			
			<tr>
				<!--<th>
					人数下限
				</th>
				<td>
					<input name="event.minPeople" class="easyui-validatebox" data-options="validType:'tel'" style="width: 150px;" value="0" />
				</td>
				-->
				<input name="event.minPeople" type="hidden" value="0">
				<th>
					人数上限
				</th>
				<td>
					<input name="event.maxPeople" class="easyui-validatebox" data-options="validType:'tel'" style="width: 150px;" value="0" />
					&nbsp;&nbsp;&nbsp;&nbsp;( 0表示无限制 )
				</td>
			</tr>
			<tr>
				<th>
					需要签到
				</th>
				<td colspan="3">
					<select name="event.needSignIn" class="easyui-combobox" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
				</td>
			</tr>
			<tr>
				<th>
					需要认证
				</th>
				<td colspan="3">
					<select name="event.needAuth" class="easyui-combobox" style="width: 155px;" data-options="editable:false">
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					活动介绍
				</th>
				<td colspan="3">
					<script id="content" name="event.content" type="text/plain" style="width:700px;height:300px;">
					</script>
				</td>
			</tr>
			
			<tr>
				<th>
					活动海报上传
				</th>
				<td colspan="3">
					<input type="button" id="event_upload_button" value="上传图片">
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
					<select name="event.needNotification" id="needNotification" style="width: 155px;" data-options="editable:false">
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

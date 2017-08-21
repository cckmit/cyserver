<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title></title>
		<jsp:include page="../../../inc.jsp"></jsp:include>
		<script type="text/javascript">
            var btn ;
			var submitForm = function($dialog, $grid, $pjq,$btn) {
                btn = $btn ;
				if ($('form').form('validate')) {
					var deptIdPair = {oldDeptId:$("#oldDeptId").val(),newDeptId:$("#newDeptId").val()} ;
					var flag = $("#flag").val() ;
					if(flag == '1') {
						moveUserInfoFromDeptToOtherDept($dialog, $grid, $pjq,$btn,deptIdPair) ;
					} else {
						isSameOfTwoDept($dialog, $grid, $pjq,$btn,deptIdPair) ;
					}
					/*$.ajax({
						url : '${pageContext.request.contextPath}/dept/deptAction!moveUserInfoFromDeptToOtherDept.action',
						data : $('form').serialize(),
						dataType : 'json',
						success : function(result) {
							if (result.success) {
								$grid.datagrid('reload');
								$grid.datagrid('unselectAll');
								$dialog.dialog('destroy');
								$pjq.messager.alert('提示', result.msg, 'info');
							} else {
								$pjq.messager.alert('提示', result.msg, 'error');
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
					});*/
				}
			} ;

			/**
			 * 判断两班级中相同的数据
			 */
			function isSameOfTwoDept($dialog, $grid, $pjq,$btn,deptIdPair ) {
				$.ajax({
					url : '${pageContext.request.contextPath}/deptInfo/deptInfoAction!isSameOfTwoDept.action',
					data : deptIdPair,
					dataType : 'json',
					success : function(result) {
						$('#sameTable').html("");
						$('#similarTable').html("");
						$('#same').hide();
						$('#similar').hide();

                        $("#msg").show() ;
                        $("#msgDiv").html("") ;
                        $("#code").val(result.one) ;
						if(result.one == "0" || result.one == 0 ) {
//							$pjq.messager.alert('提示', "两个班级没有相同数据，可以迁移", 'info');
                            $("#msgDiv").html("两个班级没有相同数据，可以迁移") ;
                            $("#flag").val("1") ;
                            $btn.innerHTML = '<span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">迁移数据</span><span class="l-btn-icon ext-icon-save">&nbsp;</span></span>' ;

						} else {
                            if(result.one == "1" || result.one == 1 ) {
                                $("#msgDiv").html("<span style='color: #FF0000;'>错误：两班级具有相同校友，不能迁移！</span>") ;
                            } else if(result.one == "2" || result.one == 2 ) {
                                $("#msgDiv").html("<span style='color: #d7ba42;'>警告：两班级具有相同姓名的校友，请确认是否是同一校友，谨慎迁移！</span>") ;
                                $("#flag").val("1") ;
                                $btn.innerHTML = '<span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">迁移数据</span><span class="l-btn-icon ext-icon-save">&nbsp;</span></span>' ;
                            } else if(result.one == "3" || result.one == 3 ) {
                                $("#msgDiv").html("<span style='color: #FF0000;'>错误：源班级下没有校友数据，无需迁移！</span>") ;
                            } else if(result.one == "9" || result.one == 9 ) {
                                $("#msgDiv").html("<span style='color: #FF0000;'>错误：其他错误，不能迁移！</span>") ;
                            }

							if(result.two && result.two.one && result.two.one.length > 0 ) {
								var str = "<tr>\
										<th style='text-align: center;'>序号</th>\
										<th style='text-align: center;'>姓名-学号</th>\
										<th style='text-align: center;'>消息</th>\
										<th style='text-align: center;'>状态</th>\
										</tr>";
								for (var i = 0; i < result.two.one.length; i++) {
									str += "<tr>\
										<td>"
											+ (i+1) +
										"</td>\
										<td>"
											+ result.two.one[i].two.one +
										"</td>\
										<td>"
											+ result.two.one[i].two.two +
										"</td>\
										<td style='color: #FF0000;'>错误</td>\
										</tr>";
								}
								$('#sameTable').html(str);
								if (str != "") $('#same').show();
							}
							if(result.two && result.two.two && result.two.two.length > 0 ) {
								var str = "<tr>\
										<th style='text-align: center;'>序号</th>\
										<th style='text-align: center;'>姓名-学号</th>\
										<th style='text-align: center;'>消息</th>\
										<th style='text-align: center;'>状态</th>\
										</tr>";
								for (var i = 0; i < result.two.two.length; i++) {
									str += "<tr>\
										<td>"
											+ (i+1) +
										"</td>\
										<td>"
											+ result.two.two[i].two.one +
										"</td>\
										<td>"
											+ result.two.two[i].two.two +
										"</td>\
										<td style='color: #d7ba42;'>警告</td>\
										</tr>";
								}
								$('#similarTable').html(str);
								if (str != "") $('#similar').show();

							}

						}
//						if (result.success) {
//							$grid.datagrid('reload');
//							$grid.datagrid('unselectAll');
//							$dialog.dialog('destroy');
//							$pjq.messager.alert('提示', result.msg, 'info');
//						} else {
//							$pjq.messager.alert('提示', result.msg, 'error');
//						}
					},
					beforeSend:function(){
						parent.$.messager.progress({
							text : '监测两班级中校友数据....'
						});
					},
					complete:function(){
						parent.$.messager.progress('close');
					}
				});
			}

			/**
			 * 迁移班级下校友数据到另一个班级下
			 * @param deptIdPair
             */
			function moveUserInfoFromDeptToOtherDept($dialog, $grid, $pjq,$btn,deptIdPair ) {

                var code = $("#code").val() ;
                var flag = $("#flag").val() ;
                if(flag != "1" && flag != 1) return ;

                var msg = "确认迁移数据？" ;
                if(code == "1" || code == 1 ) {
                    $("#msgDiv").html("<span style='color: #FF0000;'>错误：两班级具有相同校友，不能迁移！</span>") ;
                    parent.$.messager.alert('提示','两班级具有相同校友，不能迁移！','error');
                    return ;
                } else if(code == "2" || code == 2 ) {
                    $("#msgDiv").html("<span style='color: #d7ba42;'>警告：两班级具有相同姓名的校友，请确认是否是同一校友，谨慎迁移！</span>") ;
                    msg = "已确认两班级具有相同姓名的校友数据均为不同的校友，继续迁移！" ;
                } else if(code == "3" || code == 3 ) {
                    $("#msgDiv").html("<span style='color: #FF0000;'>错误：源班级下没有校友数据，无需迁移！</span>") ;
                    parent.$.messager.alert('提示','源班级下没有校友数据，无需迁移！','error');
                    return ;
                } else if(code == "9" || code == 9 ) {
                    $("#msgDiv").html("<span style='color: #FF0000;'>错误：其他错误，不能迁移！</span>") ;
                    parent.$.messager.alert('提示','其他错误，不能迁移！','error');
                    return ;
                } else if(code != "0" && code != 0) {
                    parent.$.messager.alert('提示','其他错误，不能迁移！','error');
                    return ;
                }

                $.messager.confirm('确认', msg, function(r) {
                    if (r) {

                        $.ajax({
                            url : '${pageContext.request.contextPath}/deptInfo/deptInfoAction!moveUserInfoFromDeptToOtherDept.action',
                            data : deptIdPair,
                            dataType : 'json',
                            success : function(result) {
//                                alert(JSON.stringify(result));
                                if (result.success) {
                                    $grid.datagrid('reload');
//                                    $grid.datagrid('unselectAll');
                                    $dialog.dialog('destroy');
                                    $pjq.messager.alert('提示', result.msg, 'info');
                                } else {
                                    $pjq.messager.alert('提示', result.msg, 'error');
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
                });

			}

			function init() {
			    $("#flag").val("0");
			    $("#code").val("");
                if(btn) {
                    btn.innerHTML = '<span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">监测数据</span><span class="l-btn-icon ext-icon-save">&nbsp;</span></span>';
                }
                $("#same").hide();
                $("#similar").hide();
                $("#msg").hide();
                $("#sameTable").html("");
                $("#similarTable").html("");
                $("#msgDiv").html("");
            }
			
		</script>
	</head>

	<body>
		<form method="post" id="addDeptForm">
			<input id="oldDeptId" name="oldDeptId" type="hidden" value="${param.oldDeptId}">
			<input id="newDeptId" name="newDeptId" type="hidden">
			<input id="flag" name="flag" type="hidden" value="0">
			<input id="code" name="code" type="hidden">

			<fieldset>
				<legend>
					迁移数据
				</legend>
				<table class="ta001">
					<tr>
						<th>
							源班级
						</th>
						<td>
							${param.deptName}
						</td>
					</tr>
					<tr>
						<th>
							目标班级
						</th>
						<td>

							<table>

								<tr>
									<th align="right" >学校</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<%--
                                        <input name="queryMap.schoolId" id="schoolId" type="hidden">
                                        <input name="queryMap.collegeId" id="collegeId" type="hidden">
                                        <input name="queryMap.gradeId" id="gradeId" type="hidden">
                                        <input name="queryMap.classId" id="classId" type="hidden">
                                        --%>
										<input id="schoolName" name="queryMap.schoolName" type="hidden"/>
										<input id="collegeName" name="queryMap.collegeName" type="hidden"/>
										<input id="gradeName" name="queryMap.gradeName" type="hidden"/>
										<input id="className" name="queryMap.className" type="hidden"/>
										<input id="school" class="easyui-combobox" style="width: 130px;"
											   data-options="
												valueField: 'deptId',
												textField: 'deptName',
												editable:false,
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
									                $('#school').combobox('clear');
									                $('#depart').combobox('clear');
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#major').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('loadData',[]);
													$('#major').combobox('loadData',[]);
													$('#depart').combobox('loadData',[]);

													$('#schoolName').prop('value','');
													$('#collegeName').prop('value','');
													$('#gradeName').prop('value','');
													$('#className').prop('value','');
													<%--$('#schoolId').prop('value','');
													$('#collegeId').prop('value','');
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');--%>
									                }
									            }],
												url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDepart.action',
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getByParentId.action?deptId='+rec.deptId;
													$('#depart').combobox('clear');
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#major').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('loadData',[]);
													$('#major').combobox('loadData',[]);
													$('#depart').combobox('reload', url);

													$('#schoolName').prop('value',rec.deptName);
													$('#collegeName').prop('value','');
													$('#gradeName').prop('value','');
													$('#className').prop('value','');
													init();
													<%--$('#schoolId').prop('value',rec.deptId);
													$('#collegeId').prop('value','');
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');--%>
										}" />
									</td>
									<th align="right" >院系</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="depart" class="easyui-combobox" style="width: 130px;"
											   data-options="
												valueField: 'deptId',
												textField: 'deptName',
												editable:false,
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
									                $('#depart').combobox('clear');
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#major').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('loadData',[]);
													$('#major').combobox('loadData',[]);

													$('#collegeName').prop('value','');
													$('#gradeName').prop('value','');
													$('#className').prop('value','');
													<%--$('#collegeId').prop('value','');
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');--%>
									                }
									            }],
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
													var url1= '${pageContext.request.contextPath}/major/majorAction!doNotNeedSecurity_getMajor.action?deptId='+rec.deptId;
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#classes').combobox('loadData',[]);
													$('#grade').combobox('reload', url);
													$('#major').combobox('clear');
													$('#major').combobox('reload', url1);

													$('#collegeName').prop('value',rec.deptName);
													$('#gradeName').prop('value','');
													$('#className').prop('value','');
													init();
													<%--$('#collegeId').prop('value',rec.deptId);
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');--%>
										}" />
									</td>
									<th align="right" >年级</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="grade" class="easyui-combobox" style="width: 130px;"
											   data-options="
												valueField: 'deptId',
												textField: 'deptName',
												editable:false,
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#grade').combobox('clear');
													$('#classes').combobox('clear');
													$('#classes').combobox('loadData',[]);

													$('#gradeName').prop('value','');
													$('#className').prop('value','');
													<%--$('#gradeId').prop('value','');
													$('#classId').prop('value','');--%>
									                }
									            }],
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
													$('#classes').combobox('clear');
													$('#classes').combobox('reload', url);

													$('#gradeName').prop('value',rec.deptName);
													$('#className').prop('value','');
													init();
													<%--$('#gradeId').prop('value',rec.deptId);
													$('#classId').prop('value','');--%>
										}" />
									</td>
									<th align="right" >班级</th>
									<td>
										<div class="datagrid-btn-separator"></div>
									</td>
									<td>
										<input id="classes" class="easyui-combobox" style="width: 130px;"
											   data-options="
												editable:false,
												valueField:'deptId',
												textField:'deptName',
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#classes').combobox('clear');

													$('#className').prop('value','');
													<%--$('#classId').prop('value','');--%>
									                }
									            }],
												onSelect: function(rec){
												    $('#className').prop('value',rec.deptName);
													$('#newDeptId').val(rec.deptId) ;
													init();
												}
												"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>



		<fieldset id="msg" style="display:none;">
			<legend>监测结果</legend>
			<div id="msgDiv"></div>
		</fieldset>

		<fieldset id="same" style="display:none;color: red;">
			<legend>相同校友数据</legend>
			<table class="ta001" id="sameTable" >
				<tr>
					<th>序号</th>
					<th>姓名+学号</th>
					<th>消息</th>
					<th>状态</th>
				</tr>
			</table>
		</fieldset>
		<fieldset id="similar" style="display:none;color: #d7ba42;">
			<legend>相似校友数据</legend>
			<table class="ta001" id="similarTable" >

			</table>
		</fieldset>
	</body>
</html>

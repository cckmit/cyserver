<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC>
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

            $.extend($.fn.validatebox.defaults.rules, {
                money:{
                    validator:function(value){
                        var flag = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/.test(value);
                        return  flag;
                    },
                    message:'请输入大于零的正整数或者小数,小数点后保留1~2位'
				}
			});

            var submitForm =function ($dialog, $grid, $pjq)
		{
			if ($('form').form('validate')) {
//				console.log($('form').serialize());
              /*  var donationType = $("#donationType").combobox('getValue');
                var type1 = $("#newsType1").val();*/
				$.ajax({
					url : '${pageContext.request.contextPath}/donation/donationAction!save.action',
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
		function searchFun(){
				$('#cc').combogrid('grid').datagrid('load',serializeObject($('#searchForm')));
		}
</script>
	</head>

	<body>
		<form method="post">
			<fieldset>
				<legend>
					捐赠基本信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							捐赠项目
						</th>
						<td colspan="3">
							<input id="projectId" name="donation.projectId" class="easyui-combobox" style="width:300px;"
											data-options="editable:false,
									        required:true,
									        valueField: 'projectId',
									        textField: 'projectName',
									        url: '${pageContext.request.contextPath}/project/projectAction!doNotNeedSecurity_getAll.action'"/>
						</td>
					</tr>
					<tr>
						<th>
							捐增类型
						</th>
						<td >
							<select id="donationType" name="donation.donationType" class="easyui-combobox"  data-options="
								editable:false,required:true,
								onSelect: function(value){
									if(value.value == 10){
										$('#type').hide();
										$('#item').hide();
										$('#itemN').hide();
										$('#itemType').combobox({ required: false });
										$('#itemType').combobox('setValue','');
										$('#itemName').validatebox({required: false});
										$('#itemName').prop('value','');
										$('#itemNum').validatebox({required: false});
										$('#itemNum').prop('value','');
										$('#money').validatebox({required: false});
										$('#money').prop('value','');
										$('#pay').show();
										$('#method').show();
										<%--$('#payMethod').combobox({ required: true });--%>
										$('#payMethod').combobox('setValue','40');
										$('#payMode').combobox({ required: true });
										$('#payMoney').validatebox({required:true});
									}else if(value.value == 20){
										$('#method').hide();
										<%--$('#payMethod').combobox({ required: false });--%>
										$('#payMethod').combobox('setValue','');
										$('#pay').hide();
										$('#payMode').combobox({ required: false });
										$('#payMode').combobox('setValue','');
										$('#payMoney').validatebox({required:false});
										$('#payMoney').prop('value','');
										$('#type').show();
										$('#item').show();
										$('#itemN').show();
										$('#itemType').combobox({ required: true });
										$('#itemName').validatebox({required: true});
										$('#itemNum').validatebox({required: true});
										$('#money').validatebox({required: true});
									}
							}">
								<option></option>
								<option value="10">捐款</option>
								<option value="20">捐物</option>
							</select>
						</td>
						<th>
							捐赠时间
						</th>
						<td colspan="3">
							<input name="donation.donationTime" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false,required:true" />
						</td>
					</tr>

					<tr id="type" hidden>
						<th>
							物品类型
						</th>
						<td colspan="3">
							<input id="itemType" name="donation.itemType" class="easyui-combobox"
								   data-options="editable:false,
									        required:true,
									        valueField: 'dictValue',
									        textField: 'dictName',
									        url: '${pageContext.request.contextPath}/donation/donationAction!doNotNeedSecurity_itemType.action'"/>
						</td>
					</tr>
					<tr id="itemN" hidden>
						<th>
							物品名称
						</th>
						<td colspan="3">
							<input id="itemName" name="donation.itemName" style="width: 150px;" class="easyui-validatebox" required="required"></input>
						</td>
					</tr>
					<tr id="item" hidden>

						<th>
							物品数量
						</th>
						<td>
							<input id="itemNum" name="donation.itemNum" style="width: 150px;" class="easyui-validatebox" required="required"></input>
						</td>
						<th>
							物品总价值/元
						</th>
						<td>
							<input id="money" name="donation.money"  style="width: 150px;" class="easyui-validatebox" data-options="validType:'price',required:'true'"></input>
						</td>
					</tr>
					<tr id="pay" hidden>
						<th>
							支付金额/元
						</th>
						<td>
							<input id="payMoney" name="donation.payMoney" data-options="validType:'money',required:'true'" style="width: 150px;" ></input>
						</td>
							<th>
								支付方式
							</th>
							<td>
								<input id="payMode" name="donation.payMode" class="easyui-combobox"
									   data-options="editable:false,
									        required:true,
									        valueField: 'dictValue',
									        textField: 'dictName',
									        url: '${pageContext.request.contextPath}/donation/donationAction!doNotNeedSecurity_payModel.action'"/>
							</td>
					</tr>
					<tr id="method" hidden>
						<th>
							支付途径
						</th>
						<td colspan="3">
							<select id="payMethod" class="easyui-combobox"  data-options="editable:false" name="donation.payMethod" >
								<option value="10">手机APP</option>
								<option value="20">网站</option>
								<option value="30">微信</option>
								<option value="40" selected>线下</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							是否公开留言
						</th>
						<td>
							<input type="radio" name="donation.messageIsOpen" value="1" style="width: 20px;" >公开
							<input type="radio" name="donation.messageIsOpen" value="0" checked style="width: 20px;" >隐藏
						</td>
					</tr>
					<tr>
						<th>
							捐赠留言
						</th>
						<td colspan="3">
							<textarea rows="5" cols="60" name="donation.message"></textarea>
						</td>
					</tr>
					<tr>
						<th>
							备注
						</th>
						<td colspan="3">
							<textarea rows="5" cols="60" name="donation.remark"></textarea>
						</td>
					</tr>
				</table>
			</fieldset>
			<br/>
			<fieldset>
				<legend>
					捐赠方信息
				</legend>
				<table class="ta001">
					<tr>
						<th>
							捐赠方类型
						</th>
						<td colspan="3">
							<input id="donorType" name="donation.donorType" class="easyui-combobox"
								   data-options="editable:false,
									        required:true,
									        valueField: 'dictValue',
									        textField: 'dictName',
									        url: '${pageContext.request.contextPath}/donation/donationAction!doNotNeedSecurity_donorType.action',
									        onSelect: function(value){
												if(value.dictValue == 10){
													$('#f').show();
													$('#x_name').validatebox({required: true});
													$('#cc').combogrid({required: true});

													$('#x_name').prop('value','');
													$('#departName').prop('value','');
													$('#gradeName').prop('value','');
													$('#className').prop('value','');
													$('#sex').prop('value','');
													$('#telId').prop('value','');
													$('#email').prop('value','');
													$('#mailingAddress').prop('value','');
													$('#userId').prop('value','');
												}else {
													$('#f').hide();
													$('#flag').combobox('setValue','0');
													$('#x_name').attr('readonly',false);
													$('#x_name').validatebox({required: true});
													$('#donor2').hide();
													$('#donor1').show();
													$('#donorName1').removeAttr('disabled');
													$('#donorName1').validatebox({required: true});
													$('#cc').combogrid({required: false});
													<%--$('#cc').attr('disabled',true);--%>
													$('#cc').combogrid({disabled:true});

													$('#x_name').prop('value','');
													$('#departName').prop('value','');
													$('#gradeName').prop('value','');
													$('#className').prop('value','');
													$('#sex').prop('value','');
													$('#telId').prop('value','');
													$('#email').prop('value','');
													$('#mailingAddress').prop('value','');
													$('#userId').prop('value','');
												}
											}"/>
						</td>
					</tr>
					<tr id="f" hidden>
						<th>
							是否校友
						</th>
						<td colspan="3">
							<select  id="flag" name="donation.flag" class="easyui-combobox"  data-options="
								editable:false,required:true,
								onSelect: function(value){
									if(value.value == 1){
										$('#cc').combogrid({required: true});
										$('#cc').combogrid({disabled:false});
										$('#x_name').attr('readonly',true);
										$('#x_name').validatebox({required: false});
										$('#donor1').hide();
										$('#donor2').show();
										$('#donorName1').attr('disabled',true);
										$('#donorName1').validatebox({required: false});
										$('#x_name').prop('value','');
										$('#telId').prop('value','');


									}else if(value.value == 0){
										$('#x_name').attr('readonly',false);
										$('#x_name').validatebox({required: true});
										$('#donor1').show();
										$('#donorName1').removeAttr('disabled');
										$('#donorName1').validatebox({required: true});
										$('#donor2').hide();
										$('#cc').combogrid({required: false});
										<%--$('#cc').attr('disabled',true);--%>
										$('#cc').combogrid({disabled:true});

										$('#x_name').prop('value','');
										$('#departName').prop('value','');
						            	$('#gradeName').prop('value','');
						            	$('#className').prop('value','');
						            	$('#sex').prop('value','');
						            	$('#telId').prop('value','');
						            	$('#email').prop('value','');
						            	$('#mailingAddress').prop('value','');
						            	$('#userId').prop('value','');
									}
							}"><option></option>
								<option value="0">社会人士</option>
								<option value="1">校友</option>

							</select>
						</td>
					</tr>
					<tr id="donor1" >
						<th>
							捐赠方名称
						</th>
						<td colspan="3">
							<input id="donorName1"  name="donation.donorName" class="easyui-validatebox" required="required"></input>
						</td>
					</tr>
					<tr id="donor2" hidden>
						<th>
							捐赠方名称
						</th>
						<td colspan="3">
							<select name="donation.donorName" class="easyui-combogrid" id="cc" style="width:250px;"
						        data-options="
						        	required:false,
						        	editable:false,
						            panelWidth:800,
						            idField:'userName',
						            textField:'userName',
						            pagination : true,
						            url:'${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_dataGridFor.action',
						            columns:[[
						                {field:'userName',title:'姓名',width:100,align:'center'},
						                {field:'userId',title:'学号',width:120,align:'center'},
						                {field:'fullName',title:'所属机构',width:500,align:'center'}
						            ]],
						            toolbar: $('#toolbar'),
						            onSelect:function(rowIndex, rowData){
						            	$('#departName').prop('value',rowData.departName);
						            	$('#gradeName').prop('value',rowData.gradeName);
						            	$('#className').prop('value',rowData.className);
						            	$('#sex').prop('value',rowData.sex);
						            	$('#telId').prop('value',rowData.telId);
						            	$('#email').prop('value',rowData.email);
						            	$('#mailingAddress').prop('value',rowData.mailingAddress);
						            	$('#userId').prop('value',rowData.userId);
						            	$('#x_name').prop('value',rowData.userName);
						            }
						        "></select>
						</td>
					</tr>
					<tr>
						<th>
							是否匿名
						</th>
						<td colspan="3">
							<input type="radio" name="donation.anonymous" value="0" style="width: 20px;" >公开
							<input type="radio" name="donation.anonymous" value="1"  style="width: 20px;" >匿名
						</td>
					</tr>
					<tr>
						<div id="x">
						<th>
							联系人
						</th>
						<td>
							<input id="x_name" name="donation.x_name" class="easyui-validatebox" ></input>
						</td>
						</div>
						<th>
							联系电话
						</th>
						<td>
							<input id="telId" name="donation.x_phone"  type="text"></input>
						</td>
					</tr>
					<tr>
						<th>
							院系
						</th>
						<td>
							<input id="userId" name="donation.userId" type="hidden"/>
							<%--<input id="departName" name="donation.x_depart" readonly="true" type="text"></input>--%>
							<input id="departName" name="donation.x_depart" class="easyui-validatebox" readonly="readonly" ></input>
						</td>
						<th>
							年级
						</th>
						<td>
							<input id="gradeName" name="donation.x_grade" readonly="true" type="text"></input>
						</td>

					</tr>
					<tr>
						<th>
							班级
						</th>
						<td>
							<input id="className" name="donation.x_clazz"  readonly="true" type="text"></input>
						</td>
						<th>
							性别
						</th>
						<td>
							<input id="sex"  name="donation.x_sex"  readonly="true" type="text"></input>
						</td>
					</tr>
					<tr>
						<th>
							邮箱
						</th>
						<td>
							<input id="email" name="donation.x_email" readonly="true" type="text"></input>
						</td>
						<th>
							联系地址
						</th>
						<td>
							<input id="mailingAddress" name="donation.x_address" readonly="true" type="text"></input>
						</td>
					</tr>
				</table>
			</fieldset>
			<br/>
			<fieldset>
				<legend>
					状态
				</legend>
				<table class="ta001">
					<tr>
						<th>
							是否需要发票
						</th>
						<td >
							<select class="easyui-combobox" data-options="editable:false" required="true" name="donation.needInvoice" style="width: 150px;">
								<option></option>
								<option value="0">不需要</option>
								<option value="1">需要</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							确认状态
						</th>
						<td>
							<select class="easyui-combobox"  data-options="editable:false,required:true" name="donation.confirmStatus" style="width: 150px;">
								<option></option>
								<option value="20">等待校方确认接受</option>
								<option value="30">校方已确认接受</option>
								<option value="40">已寄出发票和捐赠证书</option>
								<option value="45">已寄出捐赠证书</option>
							</select>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
		<div id="toolbar" style="display: none;">
			<table>
				<tr>
					<td>
						<form id="searchForm">
							<table>
								<tr>
									<th>
										学号：
									</th>
									<td>
										<input name="userInfo.checkFlag" type="hidden" value="1"/>
										<input name="userInfo.userId" style="width: 120px;" />
									</td>
									<th>
										姓名：
									</th>
									<td>
										<input name="userInfo.userName" style="width: 120px;" />
									</td>

									<th>
										电话号码：
									</th>
									<td>
										<input name="userInfo.telId" style="width: 120px;" />
									</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'ext-icon-zoom',plain:true"
											onclick="searchFun();">查询</a>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>

<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
            if ($('#registerId').val() > 0) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/register/registerAction!getById.action',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'register.id': result.id,
                                'register.name': result.name,
                                'register.gender': result.gender + '',
                                'register.birthday': result.birthday,
                                'register.provice': result.provice,
                                'register.city': result.provice,
                                'register.industry': result.industry,
                                'register.work': result.work,
                                'register.post': result.post,
                                'register.degree': result.degree,
                                'register.inYear': result.inYear,
                                'register.leaveYear': result.leaveYear,
                                'register.number': result.number,
                                'register.school': result.school,
                                'register.department': result.department,
                                'register.major': result.major,
                                'register.address': result.address,
                                'register.zipCode': result.zipCode,
                                'register.officePhone': result.officePhone,
                                'register.familyPhone': result.familyPhone,
                                'register.mobile': result.mobile,
                                'register.email': result.email,
                                'register.visitHistoryMuseum': result.visitHistoryMuseum + '',
                                'register.visitMoneyMuseum': result.visitMoneyMuseum + '',
                                'register.visitCollege': result.visitCollege + '',
                                'register.selfDrive': result.selfDrive + '',
                                'register.licensePlate': result.licensePlate,
                                'register.withFamily': result.withFamily + '',
                                'register.adultNumber': result.adultNumber==0?'':result.adultNumber,
                                'register.childrenNumber': result.childrenNumber==0?'':result.childrenNumber,
                                'register.userId': result.userId
                            });
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
        });
        var submitForm = function ($dialog, $grid, $pjq) {
            if ($('form').form('validate')) {
                var url;
                if ($('#registerId').val() > 0) {
                    url = '${pageContext.request.contextPath}/register/registerAction!update.action';
                } else {
                    url = '${pageContext.request.contextPath}/register/registerAction!save.action';
                }
                $.ajax({
                    url: url,
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
<form method="post" id="registerForm" class="form">
    <input name="register.id" type="hidden" id="registerId" value="${param.id}">
    <input id="userId" name="register.userId" type="hidden"/>
    <fieldset>
        <legend>
            基本信息
        </legend>
        <table class="ta001">
        <tr>
                <th>
                    姓名
                </th>
                <td>
                    <select readonly="readonly" name="register.name" class="easyui-combogrid" id="cc" style="width:155px;"
						        data-options="
						        	required:true,
						        	validType:'customRequired',
						        	editable:false,
						            panelWidth:600,
						            idField:'userName',
						            textField:'userName',
						            pagination : true,
						            url:'${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_dataGridFor.action',
						            columns:[[
						                {field:'userName',title:'姓名',width:100,align:'center'},
						                {field:'fullName',title:'所属机构',width:500,align:'center'}
						            ]],
						            toolbar: $('#toolbar'),
						            onSelect:function(rowIndex, rowData){
						            	$('#depart').prop('value',rowData.departName);
						            	$('#grade').prop('value',rowData.gradeName);
						            	$('#classes').prop('value',rowData.className);
						            	$('#studentnumber').prop('value',rowData.studentnumber);
						            	$('#sex').combobox('setValue', rowData.sex=='男'?'0':'1');
						            	$('#birthday').datebox('setValue',rowData.birthday);  
						            	$('#telId').prop('value',rowData.telId);
						            	$('#email').prop('value',rowData.email);
						            	$('#mailingAddress').prop('value',rowData.mailingAddress);
						            	$('#userId').prop('value',rowData.userId);
						            }
						        "></select>
                </td>
                <th>
                    性别
                </th>
                <td>
                    <select readonly="readonly" id="sex" name="register.gender" class="easyui-combobox" style="width: 155px;" data-options="editable:false">
                        <option value="0">男</option>
                        <option value="1">女</option>
                    </select>
                </td>
                <th>
                    生日
                </th>
       
       
                <td>
                    <input readonly="readonly" id="birthday" name="register.birthday" class="easyui-datebox" data-options="editable:false" style="width:155px;" />
                </td>
        </tr>
       <tr>
                <th>
                    所在地
                </th>
                <td>
                    <select readonly="readonly" id="location" name="register.city"  class="easyui-combogrid" style="width:155px" data-options=" 
						panelWidth: 180,
						multiple: false,
						idField: 'cityName',
						textField: 'cityName',
						url: '${pageContext.request.contextPath}/page/admin/alumniCard/alumniCardAction!doNotNeedSecurity_getNationalOfCity.action',
						method: 'get',
						columns: [[
							{field:'cityName',title:'城市名称'}
						]],
						fitColumns: true,
						editable:false
					">
					</select>
                </td>

                <th>
                    所属行业
                </th>
                <td>
                    <input readonly="readonly" name="register.industry" class="easyui-validatebox"/>
                </td>
                <th>
                    工作单位
                </th>
                <td>
                    <input readonly="readonly" name="register.work" class="easyui-validatebox"/>
                </td>
       </tr>
       <tr>
                <th>
                    职务
                </th>
                <td colspan="5">
                    <input readonly="readonly" name="register.post" class="easyui-validatebox"/>
                </td>
                
            </tr>
        </table>
    </fieldset>
    <br>
    <fieldset>
        <legend>教育信息</legend>
        <table class="ta001">
            <tr>
                <th>
                    学校
                </th>
                <td>
                    <input readonly="readonly" id="school" name="register.school" class="easyui-validatebox"/>
                </td>
                <th>
                    所在院系
                </th>
                <td>
					<input readonly="readonly" id="depart" name="register.department" class="easyui-validatebox"/>
                </td>
                <th>
                    入校年份
                </th>
                <td>
					<input readonly="readonly" id="grade" name="register.inYear" class="easyui-validatebox"/>
                </td>
            </tr>
            <tr>
                <th>
                    班级
                </th>
                <td>
                    <input readonly="readonly" id="classes" name="register.major" class="easyui-validatebox"/>
                </td>
            	<th>
                    学号
                </th>
                <td>
                    <input readonly="readonly" id="studentnumber" name="register.number" class="easyui-validatebox"/>
                </td>
                <th>
                    离校年份
                </th>
                <td>
                    <select readonly="readonly" name="register.leaveYear" class="easyui-combobox" style="width:155px" 
						data-options="editable:false">
					<option value="">&nbsp;</option>
					<c:forEach var="i" begin="1900" end="2100" varStatus="status">
					<option value="${status.index}">${status.index}年</option>
					</c:forEach>
					</select>
                </td>
            </tr>
            <tr>
                <th>
                    学历层次
                </th>
                <td colspan="5">
                    <input readonly="readonly" name="register.degree" class="easyui-combobox" style="width: 155px;"
                           data-options="
						valueField: 'dictName',
						textField: 'dictName',
						editable:false,
						url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('学历')
					" />
                </td>
            </tr>
        </table>
    </fieldset>
    <br>
    <fieldset>
        <legend>联系信息</legend>
        <table class="ta001">
            <tr>
                <th>
                    通讯地址
                </th>
                <td>
                    <input readonly="readonly" id="mailingAddress" name="register.address" class="easyui-validatebox"/>
                </td>
                <th>
                    邮编
                </th>
                <td>
                    <input readonly="readonly" name="register.zipCode" class="easyui-validatebox"/>
                </td>
                <th>
                    办公电话
                </th>
                <td>
                    <input readonly="readonly" name="register.officePhone" class="easyui-validatebox"/>
                </td>
            </tr>
            <tr>
                <th>
                    家庭电话
                </th>
                <td>
                    <input readonly="readonly" name="register.familyPhone" class="easyui-validatebox"/>
                </td>
                <th>
                    手机号
                </th>
                <td>
                    <input readonly="readonly" id="telId" name="register.mobile" class="easyui-validatebox"
                           data-options="validType:'telePhone'"/>
                </td>
                <th>
                    邮箱
                </th>
                <td>
                    <input readonly="readonly" id="email" name="register.email" class="easyui-validatebox"
                           data-options="validType:'email'"/>
                </td>
            </tr>
        </table>
    </fieldset>
    <br>
    <fieldset>
        <legend>活动信息</legend>
        <table class="ta001">
            <tr>
                <th>
                    是否参观校史馆
                </th>
                <td>
                    <select class="easyui-combobox" name="register.visitHistoryMuseum" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
                <th>
                    是否参观博物馆
                </th>
                <td>
                    <select class="easyui-combobox" name="register.visitMoneyMuseum" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
                <th>
                    是否游园
                </th>
                <td>
                    <select class="easyui-combobox" name="register.visitCollege" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                    是否自驾车
                </th>
                <td>
                    <select class="easyui-combobox" name="register.selfDrive" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
                <th>
                    车牌号
                </th>
                <td>
                    <input name="register.licensePlate" class="easyui-validatebox"/>
                </td>

            
            	<th>
                    是否有家人随行
                </th>
                <td>
                    <select class="easyui-combobox" name="register.withFamily" style="width: 155px;" data-options="editable:false">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select>
                </td>
         </tr>
            <tr>
                <th>
                    成人人数
                </th>
                <td>
                    <input name="register.adultNumber" class="easyui-validatebox"
                           data-options="validType:'tel'"/>
                </td>
                <th>
                    儿童人数
                </th>
                <td colspan="3">
                    <input name="register.childrenNumber" class="easyui-validatebox"
                           data-options="validType:'tel'"/>
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
								姓名：
							</th>
							<td>
								<input name="userInfo.userName" style="width: 150px;" />
							</td>
							<th>
								电话号码：
							</th>
							<td>
								<input name="userInfo.telId" style="width: 150px;" />
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

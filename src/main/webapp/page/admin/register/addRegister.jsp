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
        var submitForm = function ($dialog, $grid, $pjq) {
            if ($('form').form('validate')) {
                var url;
                url = '${pageContext.request.contextPath}/register/registerAction!save.action';
                
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
        
        function searchFun(){
			$('#cc').combogrid('grid').datagrid('load',serializeObject($('#searchForm')));
		}
    </script>
</head>

<body>
<form method="post" id="registerForm" class="form">
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
                    <select name="register.name" class="easyui-combogrid" id="cc" style="width:155px;"
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
						            
						            	//基本信息
						            	$('#sex').combobox('setValue', rowData.sex=='男'?'0':'1');
						            	$('#birthday').datebox('setValue',rowData.birthday);
						            	$('#provice').prop('value',rowData.residentialArea);
						            	$('#industry').prop('value',rowData.industryType);
						            	$('#work').prop('value',rowData.workUnit);
						            	$('#post').prop('value',rowData.position);
						            	//教育信息
						            	$('#school').prop('value',rowData.schoolName);
						            	$('#depart').prop('value',rowData.departName);
						            	$('#grade').prop('value',rowData.entranceTime);
						            	$('#major').prop('value',rowData.majorName);
						            	$('#studentnumber').prop('value',rowData.studentnumber);
						            	$('#leaveYear').prop('value',rowData.graduationTime);
						            	$('#degree').prop('value',rowData.studentType);
						            	//联系信息
						            	$('#mailingAddress').prop('value',rowData.mailingAddress);
						            	$('#officePhone').prop('value',rowData.workTel);
						            	$('#familyPhone').prop('value',rowData.residentialTel);
						            	$('#telId').prop('value',rowData.telId);
						            	$('#email').prop('value',rowData.email);
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
					<input readonly="readonly" id="provice" name="register.provice" class="easyui-validatebox"/>

                </td>

                <th>
                    所属行业
                </th>
                <td>
                    <input readonly="readonly" id="industry" name="register.industry" class="easyui-validatebox"/>
                </td>
                <th>
                    工作单位
                </th>
                <td>
                    <input readonly="readonly" id="work" name="register.work" class="easyui-validatebox"/>
                </td>
       </tr>
       <tr>
                <th>
                    职务
                </th>
                <td colspan="5">
                    <input readonly="readonly" id="post" name="register.post" class="easyui-validatebox"/>
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
                    <input readonly="readonly" id="school" name="register.school" readonly="readonly" class="easyui-validatebox"/>
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
                    专业
                </th>
                <td>
                    <input readonly="readonly" id="major" name="register.major" class="easyui-validatebox"/>
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
					<input readonly="readonly" id="leaveYear" name="register.leaveYear" class="easyui-validatebox"/>
                </td>

            </tr>
            <tr>
                <th>
                    学历层次
                </th>
                <td colspan="5">
                    <input readonly="readonly" id="degree" name="register.degree" style="width: 155px;" />
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
                    <input readonly="readonly" id="officePhone" name="register.officePhone" class="easyui-validatebox"/>
                </td>
            </tr>
            <tr>
                <th>
                    家庭电话
                </th>
                <td>
                    <input readonly="readonly" id="familyPhone" name="register.familyPhone" class="easyui-validatebox"/>
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

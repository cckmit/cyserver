<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/authority" prefix="authority"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
        var donationGrid;
        $(function() {
            donationGrid = $('#donationGrid').datagrid({
                url : '${pageContext.request.contextPath}/donation/donationAction!dataGrid.action',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'donationId',
                columns : [[
                    {
                        field : 'userId',
                        checkbox : true
                    },
                    {
                        width : '100',
                        title : '捐赠方',
                        field : 'donorName',
                        align : 'center'
                    },
                    {
                        width : '100',
                        title : '捐赠方类型',
                        field : 'donorType',
                        align : 'center',
                        formatter:function (value) {
                            if (value ==30){
                                return "团体";
                            }else if (value ==20){
                                return "单位";
                            }else {
                                return "个人";
							}
                        }
                    },
                    {
                        width : '60',
                        title : '捐赠类型',
                        field : 'donationType',
                        align : 'center',
						formatter:function (value) {
							if (value ==10){
							    return "<font style='color: coral;'>捐款</font>";
							}else {
							    return "<font style='color: #0b93d5;'>捐物</font>";
							}
                        }
                    },
                    {
                        width : '70',
                        title : '联系人',
                        field : 'x_name',
                        align : 'center'
                    },
                    {
                        width : '60',
                        title : '是否匿名',
                        field : 'anonymous',
                        align : 'center',
                        formatter : function(value, row) {
                            if(value==1){
                                return "<font style='color: grey;'>匿名</font>";
                            }else {
                                return "<font style='color: green;'>公开</font>";
                            }
                        }
                    },
                    {
                        width : '80',
                        title : '是否是校友',
                        field : 'flag',
                        align : 'center',
                        formatter : function(value, row) {
                            if(value==0){
                                return "<font style='color:gray;'>社会人士</font>";
                            }/*else if(value==1&&row.userId==''){
                                return "<font style='color: red;'>待核校友</font>";
                            }*/else{
                                return "<font style='color: green;'>校友</font>";
                            }
                        }
                    },
                   /* {
                        width : '300',
                        title : '学习经历',
                        field : 'x_fullname',
                        align : 'center',
                        formatter: function (value, rows) {
                            var result = '';
                            if(value){
                                var tmp = value.split('_');
                                for(var i in tmp){
                                    result += tmp[i]+'</br>';
                                }
                            }else if(rows.x_school){
                                result = rows.x_school;
                                if(rows.x_depart){
                                    result += (','+rows.x_depart);
								}
								if(rows.x_grade){
                                    result += (','+rows.x_grade);
								}
								if(rows.x_clazz){
                                    result += (','+rows.x_clazz);
                                }
							}
                            return result;
                        }
                    },
                    {
                        width : '250',
                        title : '订单编号',
                        field : 'orderNo',
                        align : 'center'
                    },*/
                    {
                        width : '120',
                        title : '捐赠项目',
                        field : 'projectName',
                        align : 'center',
                        formatter : function(value, row) {
                            if(row.project!=undefined){
                                return row.project.projectName;
                            }else{
                                return "";
                            }
                        }
                    },
                    {
                        width : '120',
                        title : '捐赠金额/物品价值',
                        field : 'money',
                        align : 'center'
                    },
                    {
                        width : '120',
                        title : '捐赠时间',
                        field : 'donationTime',
                        align : 'center'
                    },
                    {
                        width : '60',
                        title : '入口类型',
                        field : 'payType',
                        align : 'center',
						formatter:function (value) {
							var payType = '';
                            switch (value){
								case '10': payType='校友会公众号';break;
								case '15': payType='校友会网站';break;
								case '20': payType='基金会公众号';break;
								case '25': payType = '基金会网站';break;
								case '30': payType = '后台添加';break;
								case '40': payType = '历史数据导入';break;
								case '50': payType = '基金会系统同步';break;
								default:payType = '';
							}
							return payType;
                        }
                    },
                    {
                        width : '60',
                        title : '支付方式',
                        field : 'payMode',
                        align : 'center',
						formatter:function (value) {
                            var payMode = '';
                            switch (value){
                                case '10': payMode='支付宝支付';break;
                                case '20': payMode='微信支付';break;
                                case '30': payMode = '线下现金';break;
                                case '40': payMode = '线下网银';break;
                                case '50': payMode = '银联';break;
                                case '60': payMode = 'paypal';break;
                                case '70': payMode = '其它';break;
                                default:payMode = '';
                            }
                            return payMode;
                        }
                    },
                    {
                        width : '60',
                        title : '支付途径',
                        field : 'payMethod',
                        align : 'center',
                        formatter:function (value) {
                            var payMethod = '';
                            switch (value){
                                case '10': payMethod='手机APP';break;
                                case '20': payMethod='网站';break;
                                case '30': payMethod = '微信';break;
                                case '40': payMethod = '线下';break;
                                default:payMethod = '';
                            }
                            return payMethod;
                        }
                    },
                    {
                        width : '150',
                        title : '确认状态',
                        field : 'confirmStatus',
                        align : 'center',
                        formatter : function(value, row) {
                            var content = '';
                            switch (value){
                                case 10 : content = '等待寄出货物（捐款）';break;
                                case 20 : content = '等待确认';break;
                                case 30 : content = '<font style="color: green;">已确认</font>';break;
                                case 40 : content = '已寄出发票和捐赠证书';break;
                                case 45 : content = '已寄出捐赠证书';
                            }
                            return content;
                        }
                    },
                    {
                        width : '80',
                        title : '确认者',
                        field : 'confirmerName',
                        align : 'center',
						formatter: function (value, row) {
							if(row.confirmStatus >= 30){
							    return value;
							}else{
							    return '';
							}
                        }
                    },
                    {
                        width : '80',
                        title : '留言是否公开',
                        field : 'messageIsOpen',
                        align : 'center',
                        formatter : function(value, row) {
                            if(value=='0'){
                                return "<font style='color: grey;'>未公开</font>";
                            }else {
                                return "<font style='color: green;'>公开</font>";
                            }
                        }
                    },
					{
                        width : '80',
                        title : '是否留言',
                        field : 'messageIsNotEmpty',
                        align : 'center',
                        formatter : function(value) {
                            if(value=='1'){
                                return "<font style='color: green;'>有留言</font>";
                            }else {
                                return "<font style='color: grey;'>未留言</font>";
                            }
                        }
					},
                    {
                        title : '操作',
                        field : 'action',
                        width : '300px',
                        formatter : function(value, row) {
                            var str = '';
                            <authority:authority authorizationCode="查看捐赠信息" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="showFun(' + row.donationId + ', ' +row.messageIsOpen +');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="编辑捐赠信息" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="updateFun(' + row.donationId + ');"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
                            if(row.confirmStatus == '20' || row.confirmStatus == '10'){
                                <authority:authority authorizationCode="编辑捐赠信息" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="editFun(' + row.donationId + ');"><img class="iconImg ext-icon-note_edit"/>捐赠确认</a>&nbsp;';
                                </authority:authority>
                            }else if(row.confirmStatus == '30'){
                                <authority:authority authorizationCode="编辑捐赠信息" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="sendCF(' + row.donationId + ');"><img class="iconImg ext-icon-note_edit"/>证书邮寄</a>&nbsp;';
                                </authority:authority>
                            }
                            if(row.confirmStatus >=30){
                                <authority:authority authorizationCode="编辑捐赠信息" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="rebulidCertificate(' + row.donationId + ');"><img class="iconImg ext-icon-note_edit"/>重新生成证书</a>&nbsp;';
                                </authority:authority>
                            }

                            if(row.messageIsNotEmpty == '1'){
                                if(row.messageIsOpen == 1){
                                    <authority:authority authorizationCode="查看捐赠信息" userRoles="${sessionScope.user.userRoles}">
                                    str += '<a href="javascript:void(0)" onclick="openOrCancleMessage(' + row.donationId + ', ' +row.messageIsOpen +');"><img class="iconImg ext-icon-note_delete"/>取消公开留言</a>&nbsp;';
                                    </authority:authority>
								}else {
                                    <authority:authority authorizationCode="查看捐赠信息" userRoles="${sessionScope.user.userRoles}">
                                    str += '<a href="javascript:void(0)" onclick="openOrCancleMessage(' + row.donationId + ', ' +row.messageIsOpen +');"><img class="iconImg ext-icon-note"/>公开留言</a>&nbsp;';
                                    </authority:authority>
								}
							}
                            return str;
                        }
                    }
                ]],
                toolbar : '#toolbar',
                onBeforeLoad : function(param) {
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                onLoadSuccess : function(data) {
                    $('.iconImg').attr('src', pixel_0);
                    parent.$.messager.progress('close');
                }
            });
        });

        function addFun() {
            var dialog = parent.WidescreenModalDialog({
                title : '新增捐赠信息',
                iconCls : 'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/donation/addDonation.jsp',
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, donationGrid, parent.$);
                    }
                } ]
            });
        }

        function openOrCancleMessage(id, messageIsOpen) {
            var text = '公开';
            var openMessage = 1;
            if(messageIsOpen == 1){
                text = '取消公开';
                openMessage = 0;
			}
            parent.$.messager.confirm('确认', '确定'+text+'此留言？', function(r){
                if(r){
                    $.ajax({
                        url : '${pageContext.request.contextPath}/donation/donationAction!update.action',
                        data : {
                            'donation.donationId':id,
                            'donation.messageIsOpen':openMessage
                        },
                        dataType : 'json',
                        success : function(data)
                        {
                            if (data.success)
                            {
                                $("#donationGrid").datagrid('reload');
                                $("#donationGrid").datagrid('unselectAll');
                                parent.$.messager.alert('提示', data.msg, 'info');
                            } else
                            {
                                parent.$.messager.alert('错误', data.msg, 'error');
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
			});
        }

        function showFun(id, messageIsOpen) {
            var text = '公开留言';
            if(messageIsOpen == 1){
                text = '取消公开留言';
			}
            var dialog = parent.WidescreenModalDialog({
                title : '查看捐赠信息',
                iconCls : 'ext-icon-note',
                url : '${pageContext.request.contextPath}/page/admin/donation/viewDonation.jsp?id=' + id,
                buttons :[{
                    text : text,
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.openMessage(dialog, donationGrid, parent.$, messageIsOpen);
                    }
                }]
            });
        }

        function editFun(id) {
            var dialog = parent.WidescreenModalDialog({
                title : '编辑捐赠信息',
                iconCls : 'ext-icon-note_edit',
                url : '${pageContext.request.contextPath}/page/admin/donation/editDonation.jsp?id=' + id,
                buttons : [ {
                    text : '确认接收',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.comfirmStatu(dialog, donationGrid, parent.$);
                    }
                },{
                    text : '寄出证书发票',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.deliveryConfirm(dialog, donationGrid, parent.$);
                    }
                }]
            });
        }
        function updateFun(id) {
            var dialog = parent.modalDialog({
                title : '编辑捐赠信息',
                iconCls : 'ext-icon-note_edit',
                url : '${pageContext.request.contextPath}/page/admin/donation/updateDonation.jsp?id=' + id+'&flag=1',
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, donationGrid , parent.$);
                    }
                }]
            });
        }
        function sendCF(id) {
            var dialog = parent.WidescreenModalDialog({
                title : '编辑捐赠信息',
                iconCls : 'ext-icon-note_edit',
                url : '${pageContext.request.contextPath}/page/admin/donation/editDonation.jsp?id=' + id,
                buttons : [
                    {
                        text : '寄出证书发票',
                        iconCls : 'ext-icon-save',
                        handler : function() {
                            dialog.find('iframe').get(0).contentWindow.deliveryConfirm(dialog, donationGrid, parent.$);
                        }
                    }]
            });
        }

        function rebulidCertificate(dId) {
            parent.$.messager.confirm('确认', '确定重新生成捐赠证书？', function(r)
            {
                if(r){
                    $.ajax({
                        url : '${pageContext.request.contextPath}/donation/donationAction!doNotNeedSecurity_createCertificate.action',
                        data : {
                            "id" : dId
                        },
                        dataType : 'json',
                        success : function(data)
                        {
                            if (data.success)
                            {
                                $("#donationGrid").datagrid('reload');
                                $("#donationGrid").datagrid('unselectAll');
                                parent.$.messager.alert('提示', data.msg, 'info');
                            } else
                            {
                                parent.$.messager.alert('错误', data.msg, 'error');
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
            });
        }

        function removeFun()
        {
            var rows = $('#donationGrid').datagrid('getChecked');
            var ids = [];
            if (rows.length > 0)
            {
                parent.$.messager.confirm('确认', '确定删除吗？', function(r)
                {
                    if (r)
                    {
                        for ( var i = 0; i < rows.length; i++)
                        {
                            ids.push(rows[i].donationId);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/donation/donationAction!delete.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data)
                            {
                                if (data.success)
                                {
                                    $("#donationGrid").datagrid('reload');
                                    $("#donationGrid").datagrid('unselectAll');
                                    parent.$.messager.alert('提示', data.msg, 'info');
                                } else
                                {
                                    parent.$.messager.alert('错误', data.msg, 'error');
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
                });
            } else
            {
                parent.$.messager.alert('提示', '请选择要删除的记录！', 'error');
            }
        }

        /**--查询--**/
        function searchFun(){
            $('#donationGrid').datagrid('load',serializeObject($('#searchForm')));
        }


        /**--重置--**/
        function resetT(){
            $('#school').combobox('clear');
            $('#depart').combobox('clear');
            $('#grade').combobox('clear');
            $('#classes').combobox('clear');
            $('#major').combobox('clear');
            $('#studentType').combobox('clear');
            $('#classes').combobox('loadData',[]);
            $('#grade').combobox('loadData',[]);
            $('#major').combobox('loadData',[]);
            $('#depart').combobox('loadData',[]);
            $('#searchForm')[0].reset();
            $('#schoolId').prop('value','');
            $('#departId').prop('value','');
            $('#gradeId').prop('value','');
            $('#classId').prop('value','');
            $('#donorName').val('');
            $('#donorType').combobox('clear');
            $('#donationType').combobox('clear');
            $('#itemType').combobox('clear');
            $('#flag').combobox('clear');
            $('#needInvoice').combobox('clear');
            $('#payStatus').combobox('clear');
            $('#confirmStatus').combobox('clear');
        }

	</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
<div id="toolbar" style="display: none;">
	<table>
		<tr>
			<td>
				<form id="searchForm">
					<table>
						<%--<tr>
                            <th align="right" width="30px;">学校</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input name="schoolId" id="schoolId" type="hidden">
                                <input name="departId" id="departId" type="hidden">
                                <input name="gradeId" id="gradeId" type="hidden">
                                <input name="classId" id="classId" type="hidden">
                                <input name="majorId" id="majorId" type="hidden">
                                <input id="school" class="easyui-combobox" style="width: 150px;"
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
                                            $('#schoolId').prop('value','');
                                            $('#departId').prop('value','');
                                            $('#gradeId').prop('value','');
                                            $('#classId').prop('value','');
                                            }
                                        }],
                                        url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDepart.action',
                                        onSelect: function(rec){
                                            var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
                                            $('#depart').combobox('clear');
                                            $('#grade').combobox('clear');
                                            $('#classes').combobox('clear');
                                            $('#major').combobox('clear');
                                            $('#classes').combobox('loadData',[]);
                                            $('#grade').combobox('loadData',[]);
                                            $('#major').combobox('loadData',[]);
                                            $('#depart').combobox('reload', url);
                                            $('#schoolId').prop('value',rec.deptId);
                                            $('#departId').prop('value','');
                                            $('#gradeId').prop('value','');
                                            $('#classId').prop('value','');
                                }" />
                            </td>

                            <!-- 这里添加院系（程辉） -->
                            <th align="right" width="30px;">院系</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="depart" class="easyui-combobox" style="width: 150px;"
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
                                            $('#departId').prop('value','');
                                            $('#gradeId').prop('value','');
                                            $('#classId').prop('value','');
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
                                            $('#departId').prop('value',rec.deptId);
                                            $('#gradeId').prop('value','');
                                            $('#classId').prop('value','');
                                }" />
                            </td>


                            <th align="right" width="30px;">年级</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="grade" class="easyui-combobox" style="width: 150px;"
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
                                            $('#gradeId').prop('value','');
                                            $('#classId').prop('value','');
                                            }
                                        }],
                                        onSelect: function(rec){
                                            var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
                                            $('#classes').combobox('clear');
                                            $('#classes').combobox('reload', url);
                                            $('#gradeId').prop('value',rec.deptId);
                                            $('#classId').prop('value','');
                                }" />
                            </td>
                            <th align="right" width="30px;">班级</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="classes" class="easyui-combobox" style="width: 150px;"
                                    data-options="
                                        editable:false,
                                        valueField:'deptId',
                                        textField:'deptName',
                                        prompt:'--请选择--',
                                        icons:[{
                                            iconCls:'icon-clear',
                                            handler: function(e){
                                            $('#classes').combobox('clear');
                                            $('#classId').prop('value','');
                                            }
                                        }],
                                        onSelect: function(rec){
                                            $('#classId').prop('value',rec.deptId);
                                        }
                                        "/>
                            </td>
                            <th align="right" width="30px;">专业</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="major" name="donation.majorId" class="easyui-combobox" style="width: 150px;"
                                    data-options="
                                        valueField: 'majorId',
                                        textField: 'majorName',
                                        prompt:'--请选择--',
                                        icons:[{
                                            iconCls:'icon-clear',
                                            handler: function(e){
                                            $('#major').combobox('clear');
                                            }
                                        }],
                                        editable:false" />
                            </td>
                        </tr>--%>
						<tr>

							<th align="right" width="50px;">学习经历</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<input name="groupName" style="width: 150px;" class="easyui-validatebox"/>
							</td>
							<th align="right" width="60px;">金额范围</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td colspan="4">
								<input name="startMoney" style="width: 150px;" class="easyui-validatebox" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></input> - <input name="endMoney" style="width: 150px;" class="easyui-validatebox" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></input>
							</td>
							<th align="right" width="60px;">时间范围</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td colspan="5">
								<input name="startTime" style="width: 150px;" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()"/> - <input name="endTime"  style="width: 150px;" class="easyui-validatebox" readonly="readonly" onClick="WdatePicker()"/>
							</td>
						</tr>
						<tr>
							<th align="right" width="30px;">捐赠项目</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<%--<input id="projectId" name="donation.projectId" class="easyui-combobox" style="width: 150px;"
									   data-options="editable:false,
									        valueField: 'projectId',
									        textField: 'projectName',
									        prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#projectId').combobox('clear');
									                }
									            }],  
									        url: '${pageContext.request.contextPath}/project/projectAction!doNotNeedSecurity_getAll.action'"/>
							--%>
								<input name="donation.project.projectName" style="width: 150px;" class="easyui-validatebox"/>
							</td>
							<th align="right" width="30px;">确认状态</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<select class="easyui-combobox" data-options="editable:false" id="confirmStatus" name="donation.confirmStatus" style="width: 150px;">
									<option value="">全部</option>
									<option value="10">等待寄出货物（捐款）</option>
									<option value="20">等待确认</option>
									<option value="30">已确认</option>
									<option value="40">已寄出发票和捐赠证书</option>
									<option value="45">已寄出捐赠证书</option>
								</select>
							</td>
							<th align="right" width="60px;">支付状态</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<select class="easyui-combobox" data-options="editable:false" id="payStatus" name="payStatus" style="width: 150px;">
									<option value="">全部</option>
									<option value="0">未支付</option>
									<option value="1">已支付</option>
								</select>
							</td>
							<%--<th align="right" width="30px;">姓名</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<input name="donation.userInfo.userName" style="width: 150px;" class="easyui-validatebox"/>
							</td>--%>
							<th align="right" width="30px;">需要发票</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<select class="easyui-combobox" data-options="editable:false" id="needInvoice" name="needInvoice" style="width: 150px;">
									<option value="">全部</option>
									<option value="0">不需要</option>
									<option value="1">需要</option>
								</select>
							</td>
						</tr>
						<tr>
							<th align="right" width="30px;">捐赠方</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<input id="donorName" name="donation.donorName" style="width: 150px;" class="easyui-validatebox"/>
							</td>
							<th>
								捐赠方类型
							</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td >
								<input id="donorType" name="donation.donorType" class="easyui-combobox" style="width: 150px;"
									   data-options="editable:false,
									        valueField: 'dictValue',
									        textField: 'dictName',
									        prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#donorType').combobox('clear');
									                }
									            }],
									        url: '${pageContext.request.contextPath}/donation/donationAction!doNotNeedSecurity_donorType.action'
									  "/>
							</td>
							<th align="right" width="30px;">捐赠类型</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<select class="easyui-combobox" data-options="editable:false,
										 prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#donationType').combobox('clear');
									                }
									            }]" id="donationType" name="donation.donationType" style="width: 150px;">
									<option value="">全部</option>
									<option value="10">捐款</option>
									<option value="20">捐物</option>
								</select>
							</td>
							<th align="right" width="30px;">物品类型</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<input id="itemType" name="donation.itemType" class="easyui-combobox" style="width: 150px;"
									   data-options="editable:false,
									        valueField: 'dictValue',
									        textField: 'dictName',
									         prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#itemType').combobox('clear');
									                }
									            }],
									        url: '${pageContext.request.contextPath}/donation/donationAction!doNotNeedSecurity_itemType.action'"/>
							</td>

						</tr>
						<tr>
							<th align="right" width="30px;">是否校友</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<select class="easyui-combobox"
										data-options="editable:false,
										 prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#flag').combobox('clear');
									                }
									            }]"
										id="flag" name="flag" style="width: 150px;">
									<option value="">全部</option>
									<option value="0">社会人士</option>
									<option value="1">校友</option>
								</select>
							</td>
							<th align="right" width="30px;">是否有留言</th>
							<td>
								<div class="datagrid-btn-separator"></div>
							</td>
							<td>
								<select class="easyui-combobox"
										data-options="editable:false,
										 prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#messageIsNotEmpty').combobox('clear');
									                }
									            }]"
										id="messageIsNotEmpty" name="messageIsNotEmpty" style="width: 150px;">
									<option value="">全部</option>
									<option value="1">有留言</option>
									<option value="0">未留言</option>
								</select>
							</td>
							<td colspan="3">
								<a href="javascript:void(0);" class="easyui-linkbutton"
								   data-options="iconCls:'icon-search',plain:true"
								   onclick="searchFun();">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true" onclick="resetT()">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td>
							<authority:authority authorizationCode="新增捐赠信息" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton"  data-options="iconCls:'ext-icon-note_add',plain:true" onclick="addFun();">新增</a>
							</authority:authority>
							<authority:authority authorizationCode="删除捐赠信息" userRoles="${sessionScope.user.userRoles}">
								<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-note_delete',plain:true" onclick="removeFun();">删除</a>
							</authority:authority>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<div data-options="region:'center',fit:true,border:false">
	<table id="donationGrid"></table>
</div>
</body>
</html>

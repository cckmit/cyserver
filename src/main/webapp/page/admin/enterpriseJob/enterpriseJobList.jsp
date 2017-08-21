<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
        var grid;
        $(function(){
            grid=$('#enterpriseGrid').datagrid({
                url : '${pageContext.request.contextPath}/enterpriseJob/enterpriseJobAction!dataGraid.action',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'id',checkbox : true},
                    {field:'enterpriseName',title:'企业名称',width:150,align:'center'},
                    {field:'name',title:'岗位名称',width:100,align:'center'},
                    {field:'locationDesc',title:'位置描述',width:230,align:'center'},
                    {field:'education',title:'学历要求',width:100,align:'center'},
                    {field:'recruitersNum',title:'招聘人数',width:100,align:'center'},
                    {
                        width: '100',
                        title: '状态',
                        field: 'status',
                        formatter: function (value) {
                            if(value == '10') {
                                return '<font color="green">开启</font>';
                            } else if(value == '20') {
                                return '<font color="red">关闭</font>';
                            } else {
                                return "";
                            }
                        }
                    },
                    {
                        width: '100',
                        title: '审核状态',
                        field: 'auditStatus',
                        formatter: function (value) {
                            if(value == '0'){
                                return '<font color="green">通过</font>'
                            }else{
                                return '<font color="red">不通过</font>'
                            }
                        }
                    },
                    {field:'typeName',title:'岗位类型',width:100,align:'center'},
                    {field:'operator',title:'操作',align:'center',width:"300px",
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看企业信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewEnterprise(\'' + row.id + '\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="编辑企业信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="editEnterprise(\'' + row.id + '\')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="编辑企业信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="aduitEnterprise(\'' + row.id + '\')"><img class="iconImg ext-icon-note_edit"/>审核</a>&nbsp;';
                            </authority:authority>
                            if(row.status == '20'){
                                <authority:authority authorizationCode="编辑企业信息" userRoles="${sessionScope.user.userRoles}">
                                content += '<a href="javascript:void(0)" onclick="checkProject(\''+row.id+'\', 10);"><img class="iconImg icon-redo"/>开启</a>&nbsp;';
                                </authority:authority>
                            }else if(row.status=='10'){
                                <authority:authority authorizationCode="编辑企业信息" userRoles="${sessionScope.user.userRoles}">
                                content += '<a href="javascript:void(0)" onclick="checkProject(\''+row.id+'\', 20);"><img class="iconImg ext-icon-note_delete"/>关闭</a>&nbsp;';
                                </authority:authority>
                            }
                            return content;
                        }}
                ]],
                toolbar : '#newsToolbar',
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

        function searchEnterprise(){
            if ($('#searchEnterpriseForm').form('validate')) {
                $('#enterpriseGrid').datagrid('load',serializeObject($('#searchEnterpriseForm')));
            }
        }


        function addEnterprise() {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '新增',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/enterpriseJob/addEnterpriseJob.jsp',
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }

        /**
         * 编辑
         */
        function editEnterprise(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '编辑',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/enterpriseJob/editEnterpriseJob.jsp?id='+id,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }

        /**
         * 审核
         */
        function aduitEnterprise(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '审核',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/enterpriseJob/aduitEnterpriseJob.jsp?id='+id,
                buttons : [ {
                    text : '审核',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }

        function checkProject(id, status)
        {
            var content = '';
            if(status == 10){
                content = '开启';
            }else if(status == 20){
                content = '关闭';
            }

            parent.$.messager.confirm('确认', '确定'+content+'吗？', function(r)
            {
                if(r){
                    var jsonStr =
                    {
                        "enterpriseJob.id" : id,
                        "enterpriseJob.status" : status
                    };
                    $.ajax({
                        url : '${pageContext.request.contextPath}/enterpriseJob/enterpriseJobAction!update.action',
                        data : jsonStr,
                        dataType : 'json',
                        success : function(data)
                        {
                            if (data.success)
                            {
                                $("#enterpriseGrid").datagrid('reload');
                                $("#enterpriseGrid").datagrid('unselectAll');
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



        /**
         * 查看
         */
        function viewEnterprise(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/enterpriseJob/viewEnterpriseJob.jsp?id=' + id
            });
        }
        function removeEnterprise(){

            var rows = $("#enterpriseGrid").datagrid('getChecked');
            var ids = [];
            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/enterpriseJob/enterpriseJobAction!delete.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#enterpriseGrid").datagrid('reload');
                                    $("#enterpriseGrid").datagrid('unselectAll');
                                    $.messager.alert('提示',data.msg,'info');
                                }
                                else{
                                    $.messager.alert('错误', data.msg, 'error');
                                }
                            },
                            beforeSend:function(){
                                $.messager.progress({
                                    text : '数据提交中....'
                                });
                            },
                            complete:function(){
                                $.messager.progress('close');
                            }
                        });
                    }
                });
            } else {
                $.messager.alert('提示', '请选择要删除的记录！', 'error');
            }
        }

        function resetT() {
            $('#name').val('');
            $('#enterpriseId').combobox('clear');
            $('#auditStatus').combobox('clear');
            $('#status').combobox('clear');
            $('#education').combobox('clear');

        }
    </script>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="newsToolbar" style="display: none;">
        <table>
            <tr>
                <td>
                    <form id="searchEnterpriseForm">
                        <table>
                            <tr>
                                <th  align="right" class="enterSelect">
                                    企业名称
                                </th>
                                <td class="enterSelect">
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td class="enterSelect">
                                    <input id="enterpriseId" name="enterpriseJob.enterpriseId" class="easyui-combobox" style="width: 150px;" value=""
                                           data-options="editable:false,
							        valueField: 'id',
							        textField: 'name',
							        url: '${pageContext.request.contextPath}/enterprise/enterpriseAction!doNotNeedSecurity_getEnterpriseList.action'" />
                                </td>
                                <th>
                                    岗位名称
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="enterpriseJob.name" id="name" style="width: 150px;" />
                                </td>

                                <th>
                                    审核状态
                                </th>
                                <td >
                                    <select id="auditStatus" name="enterpriseJob.auditStatus" class="easyui-combobox"
                                            style="width: 150px;" data-options="editable:false">
                                        <option value="0">通过</option>
                                        <option value="1">不通过</option>
                                    </select>
                                </td>
                                <th>
                                    状态
                                </th>
                                <td >
                                    <select id="status" class="easyui-combobox" data-options="editable:false,"  name="enterpriseJob.status" style="width: 150px;">
                                        <option value="" ></option>
                                        <option value="10" >开启</option>
                                        <option value="20" >关闭</option>
                                    </select>
                                </td>

                                <th>学历要求</th>
                                <td >
                                    <input id="education" class="easyui-combobox" style="width: 150px;" name="enterpriseJob.education" value=""
                                           data-options="
								valueField: 'dictName',
								textField: 'dictName',
								editable:false,
								url: '${pageContext.request.contextPath}/dicttype/dictTypeAction!doNotNeedSecurity_getDict.action?dictTypeName='+encodeURI('学历')
							" />
                                </td>

                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchEnterprise();">查询</a>&nbsp;
                                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
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
                                <authority:authority authorizationCode="新增企业信息" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_add',plain:true"
                                       onclick="addEnterprise();">新增</a>
                                </authority:authority>
                            </td>
                            <td>
                                <authority:authority authorizationCode="删除企业信息" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_delete',plain:true"
                                       onclick="removeEnterprise();">删除</a>
                                </authority:authority>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'center',fit:true,border:false">
        <table id="enterpriseGrid" ></table>
    </div>
</div>
</body>
</html>
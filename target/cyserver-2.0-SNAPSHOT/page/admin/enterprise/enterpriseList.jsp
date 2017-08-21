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
                url : '${pageContext.request.contextPath}/enterprise/enterpriseAction!dataGraid.action',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'id',checkbox : true},
                    {field:'name',title:'企业名称',width:150,align:'center'},
                    {field:'slogan',title:'标语',width:100,align:'center'},
                    {field:'typeName',title:'企业类型',width:100,align:'center'},
                    {field:'mainBusiness',title:'主营业务',width:100,align:'center'},
                    {field:'address',title:'企业地址',width:100,align:'center'},
                    {field:'website',title:'网址',width:100,align:'center'},
                    {field:'serviceArea',title:'业务范围',width:100,align:'center'},
                    {field:'linkman',title:'联系人名称',width:100,align:'center'},
                    {field:'contactNumber',title:'联系电话',width:100,align:'center'},
                    {field:'operator',title:'操作',align:'center',width:"300px",
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看企业信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewEnterprise(\'' + row.id + '\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="编辑企业信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="editEnterprise(\'' + row.id + '\')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="查看企业信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewEnterpriseProduct(\'' + row.id + '\',\'' + row.name + '\')"><img class="iconImg ext-icon-note_edit"/>查看产品</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="查看企业信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewEnterpriseTeam(\'' + row.id + '\',\'' + row.name + '\')"><img class="iconImg ext-icon-note_edit"/>查看团队成员</a>&nbsp;';
                            </authority:authority>
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
                url : '${pageContext.request.contextPath}/page/admin/enterprise/addEnterprise.jsp',
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
                url : '${pageContext.request.contextPath}/page/admin/enterprise/editEnterprise.jsp?id='+id,
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
         * 查看
         */
        function viewEnterprise(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/enterprise/viewEnterprise.jsp?id=' + id
            });
        }
        /**
         * 查看产品
         */
        function viewEnterpriseProduct(id,name) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/enterprise/enterpriseProducts.jsp?enterpriseId=' + id + '&enterpriseName='+name
            });
        }

        /**
         * 查看组织成员
         */
        function viewEnterpriseTeam(id,name) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/enterprise/enterpriseTeam.jsp?enterpriseId=' + id + '&enterpriseName='+name
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
                            url : '${pageContext.request.contextPath}/enterprise/enterpriseAction!delete.action',
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

        function resetT(){
            $('#name').val('');
            $('#type').combobox('clear');
            $('#contactNumber').val('');
            $('#address').val('');
            $('#linkman').val('');
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

                                <th>
                                    企业名称
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="enterprise.name" id="name" style="width: 150px;" />
                                </td>
                                <th>
                                    联系人名称
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="enterprise.linkman" id="linkman" style="width: 150px;" />
                                </td>
                                <th>
                                    联系电话
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="enterprise.contactNumber" id="contactNumber" style="width: 150px;" />
                                </td>

                                <th  align="right">
                                    企业类型
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input id="type" name="enterprise.type" class="easyui-combobox" style="width: 150px;" value=""
                                           data-options="editable:false,
							        valueField: 'dictValue',
							        textField: 'dictName',
							        url: '${pageContext.request.contextPath}/enterprise/enterpriseAction!doNotNeedSecurity_getType.action'" />
                                </td>
                                <th>
                                    企业地址
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="enterprise.address" id="address" style="width: 150px;" />
                                    <%--<input name="enterprise.latitude" id="latitude" type="hidden"  value="39.814976"/>
                                    <input name="enterprise.longitude" id="longitude" type="hidden"  value="116.363178"/>--%>
                                    <input name="orderFlag" id="orderFlag" type="hidden"  value="1"/>
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
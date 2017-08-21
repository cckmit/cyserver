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
            grid=$('#ActivityPrizeGrid').datagrid({
                url : '${pageContext.request.contextPath}/activityPrize/activityPrizeAction!dataGraid.action',
                queryParams:{"activityPrize.activityId":"${param.id}"},
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'id',checkbox : true},
                    {field:'name',title:'奖项名称',width:150,align:'center'},
                    {field:'num',title:'奖品数量',width:100,align:'center'},
                    {field:'surplusNum',title:'奖品剩余数量',width:100,align:'center'},
                    {field:'prizeName',title:'奖品名称',width:100,align:'center'},
                    {field:'sort',title:'排序',width:100,align:'center'},

                    {field:'operator',title:'操作',align:'center',width:"300px",
                        formatter: function(value,row,index){
                            var content="";
                           <authority:authority authorizationCode="编辑奖项信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewActivityPrize(\'' + row.id + '\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="编辑奖项信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="editActivityPrize(\'' + row.id + '\')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
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

        function searchActivityPrize(){
            if ($('#searchActivityPrizeForm').form('validate')) {
                $('#ActivityPrizeGrid').datagrid('load',serializeObject($('#searchActivityPrizeForm')));
            }
        }


        function addactivityPrize() {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '新增',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/activityPrize/addActivityPrize.jsp',
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
        function editActivityPrize(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '编辑',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/activityPrize/addActivityPrize.jsp?id='+id,
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
        function viewActivityPrize(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/activityPrize/viewActivityPrize.jsp?id=' + id
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


        function removeActivityPrize(){

            var rows = $("#ActivityPrizeGrid").datagrid('getChecked');
            var ids = [];
            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/activityPrize/activityPrizeAction!delete.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#ActivityPrizeGrid").datagrid('reload');
                                    $("#ActivityPrizeGrid").datagrid('unselectAll');
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
                    <form id="searchActivityPrizeForm">
                        <table>
                            <tr>

                                <th>
                                    奖项名称
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="activityPrize.name" id="name" style="width: 150px;" />
                                </td>

                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchActivityPrize();">查询</a>&nbsp;
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
                                       onclick="addactivityPrize();">新增</a>
                                </authority:authority>
                            </td>
                            <td>
                                <authority:authority authorizationCode="删除企业信息" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_delete',plain:true"
                                       onclick="removeActivityPrize();">删除</a>
                                </authority:authority>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'center',fit:true,border:false">
        <table id="ActivityPrizeGrid" ></table>
    </div>
</div>
</body>
</html>
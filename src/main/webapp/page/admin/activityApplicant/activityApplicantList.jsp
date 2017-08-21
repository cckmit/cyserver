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
            grid=$('#activityApplicantGrid').datagrid({
                url : '${pageContext.request.contextPath}/activityApplicant/activityApplicantAction!dataGraid.action',
                fit : true,
                border : false,
                queryParams:{"activityApplicant.activityId":"${param.id}"},
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'id',checkbox : true},
                    {field:'name',title:'姓名',width:150,align:'center'},
                    {field:'telephone',title:'手机号',width:100,align:'center'},
                    {field:'isWinning',title:'是否已中奖',width:100,align:'center',
                        formatter : function(value, row) {
                            if (value == '1') {
                                return "<span style='color: green'>已中奖</span>"
                            } else {
                                return "<span style='color:grey'>未中奖</span>"
                            }
                        }
                    },
                    {field:'operator',title:'操作',align:'center',width:"300px",
                        formatter: function(value,row,index){

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

        function searchActivityApplicant(){
            if ($('#searchActivityApplicantForm').form('validate')) {
                $('#activityApplicantGrid').datagrid('load',serializeObject($('#searchActivityApplicantForm')));
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


        function resetT(){
            $('#name').val('');
            $('#telephone').val('');
        }
    </script>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="newsToolbar" style="display: none;">
        <table>
            <tr>
                <td>
                    <form id="searchActivityApplicantForm">
                        <table>
                            <tr>

                                <th>
                                    姓名
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input type="hidden" name="activityApplicant.activityId" value="${param.id}"/>
                                    <input name="activityApplicant.name" id="name" style="width: 150px;" />
                                </td>
                                <th>
                                    手机号
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="activityApplicant.telephone"  id="telephone" style="width: 150px;" />
                                </td>
                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchActivityApplicant();">查询</a>&nbsp;
                                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
                                </td>
                            </tr>
                        </table>
                    </form>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'center',fit:true,border:false">
        <table id="activityApplicantGrid" ></table>
    </div>
</div>
</body>
</html>
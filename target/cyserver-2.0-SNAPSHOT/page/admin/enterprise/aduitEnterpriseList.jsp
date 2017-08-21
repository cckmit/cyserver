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
                url : '${pageContext.request.contextPath}/cloudEnterprise/cloudEnterpriseAction!dataGraid.action',
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
                    {
                        width: '100',
                        title: '认证状态',
                        field: 'status',
                        formatter: function (value,row,index) {
                            var content="";
                            if(value == 10 ) {
                                return "<span style='color: red;'>待认证</span>";
                            } else if(value == 20 ) {
                                return "<span style='color: green;'>认证通过</span>";
                            } else if(value == 30 ) {
                                return "<span style='color: black;'>认证不通过</span>";
                            }
                        }
                    },
                    {field:'operator',title:'操作',align:'center',width:"150px",
                        formatter: function(value,row,index){
                            var content="";
                            if(row.status == 10 ) {
                                <authority:authority authorizationCode="查看企业信息" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="viewEnterprise(\'' + row.id + '\')"><img class="iconImg ext-icon-note"/>认证</a>&nbsp;';
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



        /**
         * 认证企业
         */
        function viewEnterprise(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '认证',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/enterprise/viewAduitEnterprise.jsp?id=' + id,
                buttons: [{
                    text: '通过',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$,20);
                    }
                },
                    {
                        text: '不通过',
                        iconCls: 'ext-icon-save',
                        handler: function () {
                            dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$,30);
                        }
                    }
                ]
            });
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
                                    <input name="cloudEnterprise.name" id="name" style="width: 150px;" />
                                </td>
                                <th>
                                    联系人名称
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="cloudEnterprise.linkman" id="linkman" style="width: 150px;" />
                                </td>
                                <th>
                                    联系电话
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="cloudEnterprise.contactNumber" id="contactNumber" style="width: 150px;" />
                                </td>

                                <th  align="right">
                                    企业类型
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="cloudEnterprise.type" id="type" style="width: 150px;" />
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
        </table>
    </div>
    <div data-options="region:'center',fit:true,border:false">
        <table id="enterpriseGrid" ></table>
    </div>
</div>
</body>
</html>
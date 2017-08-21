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
        var enterpriseId = '';
        var enterpriseName = '';
        if('${param.enterpriseName}'){
            enterpriseId = '${param.enterpriseId}';
            enterpriseName = '${param.enterpriseName}';
            $('.enterSelect').hide();
        }
        var grid;
        $(function(){
            grid=$('#enterpriseGrid').datagrid({
                url : '${pageContext.request.contextPath}/cloudEnterprise/cloudEnterprisePositionAction!dataGraid.action?enterpriseId='+enterpriseId,
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
                    {field:'recruiterNumber',title:'招聘人数',width:100,align:'center'},
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
                    {field:'jobType',title:'岗位类型',width:100,align:'center'},
                    {field:'operator',title:'操作',align:'center',width:"300px",
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看企业信息" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewEnterprise(\'' + row.id + '\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
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


        /**
         * 查看
         */
        function viewEnterprise(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/cloudEnterprise/viewCloudEnterprisePosition.jsp?id=' + id
            });
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
                                <td >
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input id="enterpriseId" name="cloudEnterprisePosition.enterpriseId" style="width: 155px;"/>
                                </td>
                                <th>
                                    岗位名称
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="cloudEnterprisePosition.name" id="name" style="width: 150px;" />
                                </td>

                                <th>
                                    状态
                                </th>
                                <td >
                                    <select id="status" class="easyui-combobox" data-options="editable:false,"  name="cloudEnterprisePosition.status" style="width: 150px;">
                                        <option value="" ></option>
                                        <option value="10" >开启</option>
                                        <option value="20" >关闭</option>
                                    </select>
                                </td>

                                <th>学历要求</th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="cloudEnterprisePosition.education" id="education" style="width: 150px;" />
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
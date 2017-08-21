<%--
  Created by IntelliJ IDEA.
  User: cha0res
  Date: 1/15/17
  Time: 5:48 PM
  To change this template use File | Settings | File Templates.
--%>
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
        var projectId = ${param.projectId};
        var donationGrid;
        $(function() {
            donationGrid = $('#donationGrid').datagrid({
                url : '${pageContext.request.contextPath}/donation/donationAction!dataGrid.action?donation.payStatus=1&donation.projectId='+projectId,
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
                        title : '姓名',
                        field : 'x_name',
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
                        width : '80',
                        title : '是否是校友',
                        field : 'flag',
                        align : 'center',
                        formatter : function(value, row) {
                            if(value==1&&row.userId!=''){
                                return "<font style='color: green;'>校友</font>";
                            }else if(value==1&&row.userId==''){
                                return "<font style='color: red;'>待核校友</font>";
                            }
                            else{
                                return "<font style='color: gray;'>非校友</font>";
                            }
                        }
                    },{
                        width : '300',
                        title : '学习经历',
                        field : 'x_fullname',
                        align : 'center',
                        formatter: function (value) {
                            var result = '';
                            if(value){
                                var tmp = value.split('_');
                                for(var i in tmp){
                                    result += tmp[i]+'</br>';
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
                    },
                    {
                        width : '80',
                        title : '捐赠金额',
                        field : 'money',
                        align : 'center'
                    },
                    {
                        width : '150',
                        title : '捐赠时间',
                        field : 'donationTime',
                        align : 'center'
                    },
                    {
                        width : '150',
                        title : '支付时间',
                        field : 'payTime',
                        align : 'center'
                    },{
                        width : '120',
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
                        title : '操作',
                        field : 'action',
                        width : '120',
                        formatter : function(value, row) {
                            var str = '';
                            <authority:authority authorizationCode="查看捐赠信息" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="showFun(' + row.donationId + ');"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            if(row.confirmStatus == '20'){
                                <authority:authority authorizationCode="编辑捐赠信息" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="editFun(' + row.donationId + ');"><img class="iconImg ext-icon-note_edit"/>捐赠确认</a>&nbsp;';
                                </authority:authority>
                            }else if(row.confirmStatus == '30'){
                                <authority:authority authorizationCode="编辑捐赠信息" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="sendCF(' + row.donationId + ');"><img class="iconImg ext-icon-note_edit"/>证书邮寄</a>&nbsp;';
                                </authority:authority>
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


        function showFun(id) {
            var dialog = parent.WidescreenModalDialog({
                title : '查看捐赠信息',
                iconCls : 'ext-icon-note',
                url : '${pageContext.request.contextPath}/page/admin/donation/viewDonation.jsp?id=' + id
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


        /**--查询--**/
        function searchFun(){
            $('#donationGrid').datagrid('load',serializeObject($('#searchForm')));
        }


        /**--重置--**/
        function resetT(){
            $('#studentType').combobox('clear');
            $('#searchForm')[0].reset();
            $('#schoolId').prop('value','');
            $('#departId').prop('value','');
            $('#gradeId').prop('value','');
            $('#classId').prop('value','');
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
                        <tr>
                            <th align="right" width="30px;">姓名</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input name="donation.userInfo.userName" style="width: 150px;" class="easyui-validatebox" />
                            </td>
                            <th align="right" width="60px;">确认状态</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <select class="easyui-combobox" data-options="editable:false" name="donation.confirmStatus" style="width: 150px;">
                                    <option value="">全部</option>
                                    <option value="0">未确认</option>
                                    <option value="1">已确认</option>
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
    </table>
</div>
<div data-options="region:'center',fit:true,border:false">
    <table id="donationGrid"></table>
</div>
</body>
</html>


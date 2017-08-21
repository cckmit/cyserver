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
        var  statuses = ${param.statuses};
        var grid;
        $(function(){
            grid=$('#entrepreneurGrid').datagrid({
                url : '${pageContext.request.contextPath}/cloudEnterprise/cloudEntrepreneurAction!dataGraid.action?statuses='+${param.statuses},
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
//                    {field:'id',checkbox : true},
                    {field:'teamName',title:'企业家名称',width:120,align:'center',
                        formatter:function (value,row) {
                            if (value && value!=''){
                                return '<a href="javascript:void(0)" onclick="viewTeam(\'' + row.teamId + '\')">'+value+'</a>'
                            }
                        }
                    },
                    {field:'type',title:'申请校友类型',width:100,align:'center',
                        formatter:function (value,row,index) {
                            var content = '' ;
                            if (value == 10 || value == '10') {
                                content = "正式校友" ;
                            } else if (value == 20 || value == '20') {
                                content = "名誉校友" ;
                            }
                            return content ;
                        }
                    },
                    {field:'position',title:'职位',width:100,align:'center'},
                    {field:'college',title:'毕业院校',width:100,align:'center'},
                    {field:'grade',title:'院系',width:100,align:'center'},
                    {field:'clbum',title:'班级',width:100,align:'center'},
                    {field:'telephone',title:'手机号',width:100,align:'center'},
                    {field:'enterpriseName',title:'公司名称',width:150,align:'center',
                        formatter:function (value,row) {
                            if (value && value!=''){
                                return '<a href="javascript:void(0)" onclick="viewEnterprise(\'' + row.enterpriseId + '\')">'+value+'</a>'
                            }
                        }
                    },
                    {field:'sysName',title:'企业管理员',width:200,align:'center',
                        formatter: function(value,row,index){
                            var content = '' ;
                            if (value != undefined && value != "undefined") {
                                content += value ;
                            }
                            if (value != undefined && value != "undefined" && row.sysPhone != undefined && row.sysPhone != "undefined") {
                                content += "|";
                            }
                            if (row.sysPhone != undefined && row.sysPhone != "undefined") {
                                content += row.sysPhone ;
                            }
                            return content;
                        }
                    },
                    {field:'status',title:'状态',width:80,align:'center',
                        formatter:function (value,row,index) {
                            var content = '' ;
                            if (value == 10 || value == '10') {
                                content = "待审核";
                            } else if (value == 15 || value == '15') {
                                content = "已剔除" ;
                            } else if (value == 20 || value == '20') {
                                content = "正式校友" ;
                            } else if (value == 25 || value == '25') {
                                content = "名誉校友" ;
                            } else if (value == 30 || value == '30') {
                                content = "审核不通过" ;
                            }
                            return content ;
                        }
                    },
                    {field:'operator',title:'操作',align:'center',width:180,
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看企业信息" userRoles="${sessionScope.user.userRoles}">
                            if (row.status && row.status =="10" ) {
                                if(row.type && row.type =="10"){
                                    content += '<a href="javascript:void(0)" onclick="matchAlumni(\'' + row.id + '\',\''+row.teamName+'\')"><img class="iconImg ext-icon-note_edit"/>匹配校友档案</a>&nbsp;';
                                }else if (row.type && row.type =="20"){
                                    content += '<a href="javascript:void(0)" onclick="auditEntrepreneur(\'' + row.id + '\',\'25\')"><img class="iconImg ext-icon-note_add"/>一键通过</a>&nbsp;';
                                }
                                content += '<a href="javascript:void(0)" onclick="auditEntrepreneur(\'' + row.id + '\',\'30\')"><img class="iconImg ext-icon-note_edit"/>狠心拒绝</a>&nbsp;';
                            }else if(row.status && (row.status =="15" || row.status =="30")){
                                content += '<a href="javascript:void(0)" onclick="deleteEntrepreneur(\'' + row.id + '\')"><img class="iconImg ext-icon-note_delete"/>删除</a>&nbsp;';
                            }else if (row.status && (row.status =="20" ||row.status =="25")){
                                content += '<a href="javascript:void(0)" onclick="relieveEntrepreneur(\'' + row.id + '\')"><img class="iconImg ext-icon-note_delete"/>剔除</a>&nbsp;';
                            }
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

        function auditEntrepreneur(entrepreneurId,status){
            var msg = "" ;
            if (status == '25') {
                msg = "确定认证校友为名誉校友吗？" ;
            } else if (status == '30') {
                msg = "确定不同意申请吗？" ;
            }
            $.messager.confirm('确认', msg, function(r){
                if (r){
                    $.ajax({
                        url :  '${pageContext.request.contextPath}/cloudEnterprise/cloudEntrepreneurAction!audit.action',
                        data : {"cloudEntrepreneur.id" : entrepreneurId,"cloudEntrepreneur.status":status},
                        dataType : 'json',
                        success : function(data){
                            if (data.success){
                                $.messager.alert('提示', data.msg, 'info');

                                $("#entrepreneurGrid").datagrid('reload');
                                $("#entrepreneurGrid").datagrid('unselectAll');
                            } else{
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        },
                        error:function(e){
                            $.messager.alert('提示', "审核失败", 'error');
                        },
                        beforeSend : function(){
                            parent.parent.$.messager.progress({
                                text : '数据提交中....'
                            });
                        },
                        complete : function(){
                            parent.parent.$.messager.progress('close');
                        }
                    });
                }
            });
        }

        function relieveEntrepreneur(entrepreneurId,status){
            $.messager.confirm('确认', "剔除后将不能恢复，您确认要将该企业家从该校校友校友企业家库中剔除吗？", function(r){
                if (r){
                    $.ajax({
                        url :  '${pageContext.request.contextPath}/cloudEnterprise/cloudEntrepreneurAction!relieveEntrepreneur.action',
                        data : {"cloudEntrepreneur.id" : entrepreneurId},
                        dataType : 'json',
                        success : function(data){
                            if (data.success){
                                $.messager.alert('提示', data.msg, 'info');

                                $("#entrepreneurGrid").datagrid('reload');
                                $("#entrepreneurGrid").datagrid('unselectAll');
                            } else{
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        },
                        error:function(e){
                            $.messager.alert('提示', "剔除失败", 'error');
                        },
                        beforeSend : function(){
                            parent.parent.$.messager.progress({
                                text : '数据提交中....'
                            });
                        },
                        complete : function(){
                            parent.parent.$.messager.progress('close');
                        }
                    });
                }
            });
        }


        function searchEnterprise(){
            if ($('#searchEnterpriseForm').form('validate')) {
                $('#entrepreneurGrid').datagrid('load',serializeObject($('#searchEnterpriseForm')));
            }
        }


        function matchAlumni(id,name) {
            var dialog = parent.WidescreenModalDialog({
                title: '匹配校友',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/cloudEnterprise/userInfoList.jsp?fullName='+name,
                buttons : [ {
                    text : '通过',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.auditFun(dialog, grid, parent.$, id,"20");
                    }
                }, {
                    text : '不通过',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.auditFun(dialog, grid, parent.$, id,"30");
                    }
                }],
                onClose:function(){
                    $("#entrepreneurGrid").datagrid('reload');
                }
            });
        }

        function updateStatus(id,status) {
            if (id && id !='' && status && status !=''){
                $.ajax({
                    url : '${pageContext.request.contextPath}/cloudEnterprise/cloudEntrepreneurAction!updateStatus.action',
                    data : {
                        'cloudEntrepreneur.id' :id,
                        'cloudEntrepreneur.status' :status
                    },
                    dataType : 'json',
                    success : function(data) {
                        if(data.success){
                            $("#entrepreneurGrid").datagrid('reload');
                            $("#entrepreneurGrid").datagrid('unselectAll');
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
            }else {
                $.messager.alert('提示',"数据异常，请刷新页面重新操作！");
            }
        }

        /**
         * 删除企业家
         * @param id
         */
        function deleteEntrepreneur(id) {
            $.messager.confirm('确认', '确定删除吗？', function(r) {
                if (r) {
                    $.ajax({
                        url : '${pageContext.request.contextPath}/cloudEnterprise/cloudEntrepreneurAction!delete.action',
                        data : {
                            "cloudEntrepreneur.id" : id,
                            "cloudEntrepreneur.status":status
                        },
                        dataType : 'json',
                        success : function(data) {
                            if(data.success){
                                $("#entrepreneurGrid").datagrid('reload');
                                $("#entrepreneurGrid").datagrid('unselectAll');
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

            })
        }

        /**
         * 查看企业家详情
         * @param id
         */
        function viewTeam(id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看企业家详情',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/cloudEnterprise/cloudEnterpriseTeamForm.jsp?action=1&id=' + id
            });
        }

        function viewEnterprise(id) {
            var dialog = parent.WidescreenModalDialog({
                title: '查看企业详情',
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/cloudEnterprise/viewCloudEnterprise.jsp?enterpriseId=' + id
            });
        }

        function resetT(){
            $('#teamName').val('');
            $('#status').combobox('clear');
            $('#college').val('');
            $('#telephone').val('');
            $('#enterpriseName').val('');
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
                                    企业家姓名
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="cloudEntrepreneur.teamName" id="teamName" style="width: 150px;" />
                                </td>

                                <th  align="right">
                                     毕业院校
                                </th>
                                <td>
                                    <input name="cloudEntrepreneur.college" id="college" style="width: 150px;" />
                                </td>
                                <th>
                                    手机号
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="cloudEntrepreneur.telephone" id="telephone" style="width: 150px;" />
                                </td>
                                <th>
                                    公司名称
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="cloudEntrepreneur.enterpriseName" id="enterpriseName" style="width: 150px;" />
                                </td>


                                <c:if test="${param.statuses ne \"'20,25'\"}">
                                    <th>
                                        审核状态
                                    </th>
                                    <td>
                                        <div class="datagrid-btn-separator"></div>
                                    </td>
                                    <td>
                                        <select class="easyui-combobox" data-options="editable:false" name="cloudEntrepreneur.status"  id="status" style="width: 150px;">
                                            <option value="">全部</option>
                                            <option value="10">待审核</option>
                                            <option value="15">已剔除</option>
                                            <option value="30">不通过</option>
                                        </select>
                                    </td>
                                </c:if>
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
        <table id="entrepreneurGrid" ></table>
    </div>
</div>
</body>
</html>
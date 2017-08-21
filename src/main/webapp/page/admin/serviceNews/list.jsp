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
            grid=$('#newsGrid').datagrid({
                url : '${pageContext.request.contextPath}/serviceNews/serviceNewsAction!dataGraidServiceNews.action',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'id',checkbox : true},
                    {field:'title',title:'标题',width:150,align:'center'},
                    {
                        width: '80',
                        title: '所属服务',
                        field: 'serviceName',
                        align: 'center',
                        formatter : function(value, row) {
                            if(value == undefined || value =='' || value == null){
                                return "---";
                            }
                            else{
                                return value;
                            }
                        }
                    },
                    {field:'channelName',title:'新闻栏目',width:80,align:'center',
                    },
                    {field:'topnews',title:'幻灯片',width:60,align:'center',
                        formatter: function(value,row,index){
                            if(value==100){
                                return "√";
                            }else{
                                return "×";
                            }
                        }
                    },
                    {field:'createDate',title:'创建时间',width:130,align:'center'},
                    {field:'status', title:'状态', width:100, align:'center',
                        formatter: function (value) {
                            switch (value){
                                case '10' : return "新增";
                                case '20' : return "审核通过";
                                case '30' : return "审核不通过";
                                case '40' : return "<span style='color:red;'>下线</span>";
                                default: return "未知状态";
                            }
                        }
                    },
                    {field:'operator',title:'操作',width:180,
                        formatter: function(value,row){
                            var content="";
                            <authority:authority authorizationCode="查看服务新闻" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewNews(\''+row.id+'\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="修改服务新闻" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="editNews(\''+row.id+'\')"><img class="iconImg ext-icon-note_edit"/>编辑</a>&nbsp;';
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

        function searchNews(){
            if ($('#searchNewsForm').form('validate')) {
                $('#newsGrid').datagrid('load',serializeObject($('#searchNewsForm')));
            }
        }


        function addNews() {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '新增',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/serviceNews/add.jsp',
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
         * 编辑新闻
         */
        function editNews(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '编辑',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/serviceNews/edit.jsp?id='+id,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$);
                    }
                } ]
            });
        }

        /**--将地方新闻转为总会新闻--**/
        function convertNews(id){
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '转为总会新闻',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSecurity_initNewsUpdate.action?id='+id+"&convert=1",
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
         * 查看新闻
         */
        function viewNews(id) {
            var dialog = parent.modalDialog({
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/serviceNews/view.jsp?id='+ id
            });
        }


        function removeNews(){
            var rows = $("#newsGrid").datagrid('getChecked');
            var ids = [];
            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/serviceNews/serviceNewsAction!deleteServiceNews.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#newsGrid").datagrid('reload');
                                    $("#newsGrid").datagrid('unselectAll');
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

        function setMobTypeList(isRmv){
            var rows = $("#newsGrid").datagrid('getChecked');
            var str1="";
            var str2="";

            if(isRmv){
                str1="设置";
            }else{
                str1="取消";
            }
            var tmpAlert = "确定要"+str1+"所选记录为"+str2+"幻灯片新闻吗?";
            var ids = [];
            if (rows.length > 0) {
                $.messager.confirm('确认', tmpAlert, function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/serviceNews/serviceNewsAction!setMobTypeList.action',
                            data : {
                                ids : ids.join(','),
                                isRmv : isRmv
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#newsGrid").datagrid('reload');
                                    $("#newsGrid").datagrid('unselectAll');
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
                $.messager.alert('提示', '请选择要设置的记录！', 'error');
            }
        }
        function resetT(){
            $('#title').val('');
            $('#serviceId').combobox("clear");
            $('#channel').combobox("clear");
            $('#channel').combobox("loadData",[]);
        }
    </script>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="newsToolbar" style="display: none;">
        <table>
            <tr>
                <td>
                    <form id="searchNewsForm">
                        <table>
                            <tr>
                                <th>
                                    标题
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="serviceNews.title"  id="title" style="width: 150px;" />
                                </td>

                                <th>
                                    所属服务
                                </th>
                                <td>
                                    <input name="serviceNews.serviceId" id="serviceId" class="easyui-combobox" style="width: 150px;" value=""
                                           data-options="editable:false,
                                            valueField: 'id',
                                            textField: 'serviceName',
                                            url: '${pageContext.request.contextPath}/mobile/schoolServ/schoolServAction!doNotNeedSessionAndSecurity_getServiceList.action',
                                            onSelect: function(value){
                                                $('#channel').combobox('clear');
                                                $('#channel').combobox('loadData', []);
                                                var url = '';
                                                var serviceId=value.id;
                                                url = '${pageContext.request.contextPath}/serviceNewsType/serviceNewsTypeAction!doNotNeedSessionAndSecurity_serviceNewsTypeTree.action?serviceId='+serviceId;
                                                $('#channel').combobox('reload',url);
                                            }"
                                    >
                                </td>

                                <th>
                                    所属栏目
                                </th>
                                <td>
                                    <input name="serviceNews.channel" id="channel" class="easyui-combobox" style="width: 150px;" value=""
                                           data-options="editable:false,
							        valueField: 'id',
							        textField: 'name',
							        ">

                                </td>
                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchNews();">查询</a>&nbsp;
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
                                <authority:authority authorizationCode="新增服务新闻" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_add',plain:true"
                                       onclick="addNews();">新增</a>
                                </authority:authority>
                            </td>
                            <td>
                                <authority:authority authorizationCode="删除服务新闻" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_delete',plain:true"
                                       onclick="removeNews();">删除</a>
                                </authority:authority>
                            </td>
                            <td>
                                <authority:authority authorizationCode="设置幻灯片" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-standard-film-add',plain:true"
                                       onclick="setMobTypeList(true);">设置幻灯片</a>
                                </authority:authority>
                            </td>
                            <td>
                                <authority:authority authorizationCode="设置幻灯片" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-standard-film-delete',plain:true"
                                       onclick="setMobTypeList(false);">取消幻灯片</a>
                                </authority:authority>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'center',fit:true,border:false">
        <table id="newsGrid" ></table>
    </div>
</div>
</body>
</html>
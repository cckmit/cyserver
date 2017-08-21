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
            grid=$('#notifyGrid').datagrid({
                url : '${pageContext.request.contextPath}/notify/notifyAction!dataGrid.action',
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
                        title: '推送手段',
                        field: 'way',
                        align: 'center',
                        formatter: function(value,row,index){
                            if(value==10){
                                return "app";
                            }
                            if(value==20){
                                return "短信";
                            }
                        }

                    },
                    {field:'content',title:'通知内容',width:250,align:'center',
                    },
                    {field:'channel',title:'推送方式',width:60,align:'center',
                        formatter: function(value,row,index){
                            if(value==10){
                                return "群推";
                            }
                            if(value==20){
                                return "个推";
                            }
                        }
                    },
                    {field:'createDate',title:'创建时间',width:130,align:'center'},
                    /*{field:'status', title:'状态', width:100, align:'center',
                        formatter: function (value) {
                            switch (value){
                                case '10' : return "新增";
                                case '20' : return "审核通过";
                                case '30' : return "审核不通过";
                                case '40' : return "<span style='color:red;'>下线</span>";
                                default: return "未知状态";
                            }
                        }
                    },*/
                    {field:'operator',title:'操作',width:180,
                        formatter: function(value,row){
                            var content="";
                            <authority:authority authorizationCode="查看推送" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewNews(\''+row.id+'\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
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



        /**
         * 查看推送
         */
        function viewNews(id) {
            var dialog = parent.modalDialog({
                title : '查看推送',
                iconCls:'ext-icon-note_add',
                url :'${pageContext.request.contextPath}/notify/notifyAction!getById.action?notifyId='+id,
            });
        }


        function removeNotify(){
            var rows = $("#notifyGrid").datagrid('getChecked');
            var ids = [];
            if (rows.length > 0) {
                $.messager.confirm('确认', '确定删除吗？', function(r) {
                    if (r) {
                        for ( var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/notify/notifyAction!delete.action',
                            data : {
                                ids : ids.join(',')
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#notifyGrid").datagrid('reload');
                                    $("#notifyGrid").datagrid('unselectAll');
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
                           <%-- <tr>
                                <th>
                                    标题
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="serviceNews.title"  id="title" style="width: 150px;" />
                                </td>



                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchNews();">查询</a>&nbsp;
                                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
                                </td>
                            </tr>--%>
                        </table>
                    </form>
                </td>
            </tr>
            <tr>
                <td>
                    <table>
                        <tr>
                            <%--<td>
                                <authority:authority authorizationCode="新增服务新闻" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_add',plain:true"
                                       onclick="addNews();">新增</a>
                                </authority:authority>
                            </td>--%>
                            <td>
                                <authority:authority authorizationCode="删除推送" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_delete',plain:true"
                                       onclick="removeNotify();">删除</a>
                                </authority:authority>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'center',fit:true,border:false">
        <table id="notifyGrid" ></table>
    </div>
</div>
</body>
</html>
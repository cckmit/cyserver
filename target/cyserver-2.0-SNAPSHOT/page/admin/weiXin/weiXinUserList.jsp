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
            grid=$('#weiXinUserGrid').datagrid({
                url : '${pageContext.request.contextPath}/weiXin/weiXinUserAction!dataGraid.action',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'id',checkbox : true},
                    {field:'nickname',title:'微信昵称',width:100,align:'center'},
                    {field:'userName',title:'手机用户',width:150,align:'center'},
                    {field:'openid',title:'微信用户唯一标识',width:100,align:'center'},
                    {field:'sex',title:'性别',width:60,align:'center',
                        formatter:function (value,row,index) {
                            if(value==1){
                                return "男";
                            }else if(value==2){
                                return "女";
                            }else if(value==30){
                                return "其它";
                            }
                        }
                    },
                    {field:'language',title:'语言',width:100,align:'center'},
                    {field:'city',title:'城市',width:100,align:'center'},
                    {field:'country',title:'国家',width:100,align:'center'},
                    {field:'isFollow',title:'是否关注',width:100,align:'center',
                        formatter:function (value,row,index) {
                            if(value==0){
                                return "未关注";
                            }else if(value==1){
                                return "已关注";
                            }else {
                                return "数据异常";
                            }
                        }
                    },
                    {field:'accountName',title:'微信公众号名称',width:100,align:'center'},
                    {field:'updateDate',title:'变更时间',width:100,align:'center'},
                    {field:'operator',title:'操作',width:180,
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看微信用户" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="viewAssociation(\'' + row.id + '\')"><img class="iconImg ext-icon-note"/>查看</a>&nbsp;';
                            </authority:authority>
                            if (row.accountNum  && row.accountNum != ''){
                                <authority:authority authorizationCode="解除绑定" userRoles="${sessionScope.user.userRoles}">
                                content+='<a href="javascript:void(0)" onclick="removeBinding(\''+row.id+'\')"><img class="iconImg ext-icon-note_edit"/>解除绑定</a>&nbsp;';
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

        function searchAssociation(){
            if ($('#searchAssociationForm').form('validate')) {
                $('#weiXinUserGrid').datagrid('load',serializeObject($('#searchAssociationForm')));
            }
        }


        /**
         * 查看用户信息
         */
        function viewAssociation(id) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/weiXin/weiXinUserView.jsp?id=' + id
            });
        }

        function resetT(){
            $('#nickname').val('');
        }
        
        function removeBinding(id) {
            if (id){
                $.messager.confirm("确认","确定要解除绑定吗",function (r) {
                    if (r){
                        $.ajax({
                                url:'${pageContext.request.contextPath}/weiXin/weiXinUserAction!removeBinding.action',
                                data:{'weiXinUserId': id},
                                dataType : 'json',
                                success : function(data) {
                                    if(data.success){
                                        $("#weiXinUserGrid").datagrid('reload');
                                        $("#weiXinUserGrid").datagrid('unselectAll');
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
        }
    </script>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="newsToolbar" style="display: none;">
        <table>
            <tr>
                <td>
                    <form id="searchAssociationForm">
                        <table>
                            <tr>

                                <th>
                                    微信昵称
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="weiXinUser.nickname" id="nickname" style="width: 150px;" />
                                </td>
                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchAssociation();">查询</a>&nbsp;
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
        <table id="weiXinUserGrid" ></table>
    </div>
</div>
</body>
</html>
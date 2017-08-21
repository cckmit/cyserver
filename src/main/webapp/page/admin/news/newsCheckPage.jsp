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

    <title>新闻审核</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
</head>
<script type="text/javascript">
    var grid;
    $(function(){
        grid=$('#newsGrid').datagrid({
            url : '${pageContext.request.contextPath}/mobile/news/newsAction!dataGrid.action?isFromCheckPage=1',
            fit : true,
            border : false,
            fitColumns : true,
            striped : true,
            rownumbers : true,
            pagination : true,
            idField : 'newsId',
            columns:[[
                {field:'newsId',checkbox : true},
                {field:'title',title:'标题',width:150,align:'center'},
                {field:'channelName',title:'频道',width:60,align:'center'},
                {field:'type',title:'兴趣标签',width:80,align:'center'},
                {
                    width: '80',
                    title: '所属组织',
                    field: 'dept_name',
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
                {field:'channels',title:'新闻栏目',width:80,align:'left',

                    formatter: function(value,row,index){
                        if( value == undefined ) return "";
                        return "(" + value.replace( /_/g, ")[").replace( /,/g, "]<br>(" ) + "]";

                    }
                },
                {field:'createDate',title:'时间',width:130,align:'center'},
                {field:'status', title:'状态', width:100, align:'center',
                    formatter: function (value) {
                        switch (value){
                            case '10' : return "审核中";
                            case '15' : return "重新审核";
                            case '20' : return "已通过";
                            case '30' : return "<span style='color:red;'>未通过</span>";
                            case '40' : return "<span style='color:red;'>已下线</span>";
                            default: return "未知状态";
                        }
                    }
                },
                {field:'operator',title:'操作',width:180,
                    formatter: function(value,row,index){
                        var content="";
                        if((row.status==10 || row.status==15) && row.approveDeptId == row.uDeptId){
                            <authority:authority authorizationCode="新闻审核" userRoles="${sessionScope.user.userRoles}">
                            content+='<a href="javascript:void(0)" onclick="checkNews('+row.newsId+')"><img class="iconImg ext-icon-note"/>审核</a>&nbsp;';
                            </authority:authority>
                        }
                        return content;
                    }}
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


    //审核新闻
    function checkNews(id) {
        var dialog = parent.modalDialog({
            width : 1000,
            height : 600,
            title : '审核',
            iconCls:'ext-icon-note_add',
            url : "${pageContext.request.contextPath}/mobile/news/newsAction!getById.action?id=" + id/* + "&check=1"*/,
            buttons : [ {
                text : '审核通过',
                iconCls : 'ext-icon-save',
                handler : function() {
                    dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, "20");
                    window.parent.refreshMsgNum();
                }
            }, {
                text : '审核不通过',
                iconCls : 'ext-icon-save',
                handler : function() {
                    dialog.find('iframe').get(0).contentWindow.submitForm(dialog, grid, parent.$, "30");
                    window.parent.refreshMsgNum();
                }
            }]
        });
    }

    function multiLineCheck(status){
        var rows = $("#newsGrid").datagrid('getChecked');
        var ids = [];
        if (rows.length > 0)
        {
            for ( var i = 0; i < rows.length; i++) {
                ids.push( rows[i].newsId );
                if(rows[i].status != 10 && rows[i].status != 15){
                    $.messager.alert('提示', '您选择了已审核或已下线的新闻', 'error');
                    return;
                }
            }
            //弹框
            ids = ids.join( ',' );
            var msg = '' ;
            if (status == '30') {
                msg = "是否批量批准新闻通过?" ;
            } else {
                msg = "是否批量"
            }
            parent.$.messager.confirm('确认', '确定批量审核这些新闻吗？', function(r) {
                if (r) {
                    bulkCheckOrDropNews(ids,status,'0') ;
                }
            }) ;

        }
        else
        {
            $.messager.alert('提示', '请选择要批量审批的新闻', 'error');
        }

    }
    function multiLineOffline(){
        var rows = $("#newsGrid").datagrid('getChecked');
        var ids = [];
        if (rows.length > 0)
        {
            for ( var i = 0; i < rows.length; i++) {
                ids.push( rows[i].newsId );
                if(rows[i].status != '20'){
                    $.messager.alert('提示', '您选择了未审核或已下线的新闻', 'error');
                    return;
                }
            }

            //弹框
            ids = ids.join( ',' );
            parent.$.messager.confirm('确认', '确定下线这些新闻吗？', function(r) {
                if (r) {
                    bulkCheckOrDropNews(ids, '40', '0');
                }
            }) ;
        }else{
            $.messager.alert('提示', '请选择批量下线的新闻', 'error');
        }
    }
    //批量下线审核
    function bulkCheckOrDropNews( ids ,status ,drop){
        //批量审批
        if(drop == '0' && (status == '20' || status == '30')){
            var json = {"ids":ids,
                "status":status};

            $.ajax({
                url : "${pageContext.request.contextPath}/mobile/news/newsAction!multiLineCheck.action",
                data : json,
                dataType : 'json',
                success : function(result)
                {
                    if (result.success){
                        grid.datagrid('reload');
                        grid.datagrid('unselectAll');
                        parent.$.messager.alert('提示', result.msg, 'info');
                    }else{
                        parent.$.messager.alert('提示', result.msg, 'error');
                    }
                },
                beforeSend : function()
                {
                    parent.$.messager.progress({
                        text : '数据提交中....'
                    });
                },
                complete : function()
                {
                    parent.$.messager.progress('close');
                }
            });
        }
    }

    //搜索
    function searchNews(){
        if ($('#searchNewsForm').form('validate')) {
            $('#newsGrid').datagrid('load',serializeObject($('#searchNewsForm')));
        }
    }
    //清空搜索框
    function resetT(){
        $('#title').val('');
        $('#deptName').val('');
        $('#qudao').combobox("clear");
        $('#newsType').combotree("clear");
        $('#newsType').combotree("loadData",[]);
    }
</script>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="toolbar" style="display: none;">
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
                                    <input name="news.title"  id="title" style="width: 150px;" />
                                </td>


                                <th>
                                    所属组织
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="news.dept_name" id="deptName" style="width: 150px;" />
                                </td>

                                </td>

                                <th>
                                    新闻渠道
                                </th>
                                <td>
                                    <select id="qudao" class="easyui-combobox" style="width: 150px" name="news.channel" data-options="
					              	editable:false,panelHeight:80,
						          onSelect: function(value){
							       $('#newsType').combotree('clear');
							         sChannel = value.value;
						        	var url = '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?channel='+sChannel;
						          	$('#newsType').combotree('reload',url);
					               },
					               onLoadSuccess : function() {
										$('#qudao').combobox('clear');
									}
					                   	" >
                                        <option value="10">手机</option>
                                        <option value="20">网页</option>
                                        <option value="30">微信</option>
                                    </select>

                                </td>


                                <th>
                                    新闻栏目
                                </th>
                                <td colspan="3" >
                                    <select name="news.category"  id="newsType" class="easyui-combotree"
                                            data-options="
								editable:false,
								idField:'id',
								state:'open',
								textField:'text',
								parentField:'pid',
								"
                                            style="width: 150px;">
                                    </select>

                                </td>

                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchNews();">查询</a>&nbsp;
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true"
                                       onclick="resetT()">重置</a>
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
                                <authority:authority authorizationCode="新闻批量审核" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_edit',plain:true"
                                       onclick="multiLineCheck('20');">批量通过</a>
                                </authority:authority>
                            </td>
                            <td>
                                <authority:authority authorizationCode="新闻批量审核" userRoles="${sessionScope.user.userRoles}">
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'ext-icon-note_edit',plain:true"
                                       onclick="multiLineCheck('30');">批量不通过</a>
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

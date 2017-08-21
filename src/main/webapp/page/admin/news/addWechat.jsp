<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@page import="com.cy.util.WebUtil" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">

    <title></title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2$('form').serialize(),keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var ids = '${param.ids}'
        if (ids != null || ids != '') {
            var grid;
            $(function () {
                grid = $('#newsGrid').datagrid({
                    url: '${pageContext.request.contextPath}/mobile/news/newsAction!dataGridNewsById.action?ids=' + ids,
                    fit: true,
                    border: false,
                    fitColumns: true,
                    striped: true,
                    rownumbers: true,
                    pagination: true,
                    idField: 'newsId',
                    columns: [[
//                    {field: 'newsId', checkbox: true},
                        {field: 'title', title: '标题', width: 150, align: 'center'},
                        {field: 'channelName', title: '频道', width: 60, align: 'center'},
                        {field: 'type', title: '兴趣标签', width: 80, align: 'center'},
                        {field: 'mainName', title: '新闻来源', width: 60, align: 'center'},
                        /*{field:'origin',title:'新闻来源',width:60,align:'center',
                         formatter: function(value,row,index){
                         if(row.origin==1 || row.originP==1 || row.originWeb==1 || row.originWebP==1){
                         return "总会";
                         }else if(row.origin==2 || row.originP==2 || row.originWeb==2 || row.originWebP==2){
                         }else if(row.origin==2 || row.originP==2 || row.originWeb==2 || row.originWebP==2){
                         }else if(row.origin==2 || row.originP==2 || row.originWeb==2 || row.originWebP==2){
                         return "地方";
                         }
                         }
                         },*/
                        {
                            width: '80',
                            title: '所属组织',
                            field: 'dept_name',
                            align: 'center',
                            formatter: function (value, row) {
                                if (value == undefined || value == '' || value == null) {
                                    return "---";
                                }
                                else {
                                    return value;
                                }
                                /*if(row.origin==1 || row.originP==1 || row.originWeb==1 || row.originWebP==1) {
                                 return row.dept_name;
                                 } else if(row.origin==2 || row.originP==2 || row.originWeb==2 || row.originWebP==2) {
                                 return "---";
                                 }*/
                            }
                        },
                        {
                            width: '80',
                            title: '审核组织',
                            field: 'approveDeptName',
                            align: 'center',
                        },
                        {
                            field: 'channels', title: '新闻栏目', width: 80, align: 'left',

                            formatter: function (value, row, index) {
                                if (value == undefined) return "";
                                return "(" + value.replace(/_/g, ")[").replace(/,/g, "]<br>(") + "]";
                                //alert( value + typeof(value) );

                            }
                        },

                        {
                            field: 'topnews', title: '手机幻灯片', width: 60, align: 'center',
                            formatter: function (value, row, index) {
                                if (value == 100) {
                                    return "√";
                                } else {
                                    return "×";
                                }
                            }
                        },
                        {
                            field: 'topnewsWeb', title: '网页幻灯片', width: 60, align: 'center',
                            formatter: function (value, row, index) {
                                if (value == 100) {
                                    return "√";
                                } else {
                                    return "×";
                                }
                            }
                        },
                        {field: 'createDate', title: '时间', width: 130, align: 'center'},
                        {
                            field: 'status', title: '状态', width: 100, align: 'center',
                            formatter: function (value) {
                                switch (value) {
                                    case '10' :
                                        return "审核中";
                                    case '15' :
                                        return "重新审核";
                                    case '20' :
                                        return "已通过";
                                    case '30' :
                                        return "<span style='color:red;'>未通过</span>";
                                    case '40' :
                                        return "<span style='color:red;'>已下线</span>";
                                    default:
                                        return "未知状态";
                                }
                            }
                        }
                    ]],
                    toolbar: '#newsToolbar',
                    onBeforeLoad: function (param) {
                        parent.$.messager.progress({
                            text: '数据加载中....'
                        });
                    },
                    onLoadSuccess: function (data) {
                        $('.iconImg').attr('src', pixel_0);
                        parent.$.messager.progress('close');

                    }
                });
            });
        }else {
            $.messager.alert('提示', '您没有选择推送新闻', 'error');
        }
        function submitForm($dialog, ids, $pjq) {
            var accountId = $('#accountId').combobox("getValue");
            if (accountId == undefined || accountId == null || accountId == '') {
                $.messager.alert('提示', '请选择推送的公众号', 'error');
                return false;
            }
            $.ajax({
                url: '${pageContext.request.contextPath}/mobile/news/newsAction!pushNewsToWeiXin.action',
                data: {'ids': ids, 'accountId': accountId},
                dataType: 'json',
                success: function (result) {
                    if (result.success) {

                        $dialog.dialog('destroy');
                        $pjq.messager.alert('提示', result.msg, 'info');
                    } else {
                        $pjq.messager.alert('提示', result.msg, 'error');
                    }
                },
                beforeSend: function () {
                    $pjq.messager.progress({
                        text: '数据提交中....'
                    });
                },
                complete: function () {
                    $pjq.messager.progress('close');
                }
            });
        }
        ;


    </script>
</head>

<body>
<form method="post" id="addNewsForm">

    <fieldset>
        <legend>
            公众号
        </legend>
        <table class="ta001">
            <tr>
                <th>
                    公众号列表
                </th>
                <td colspan="3">
                    <select id="accountId" class="easyui-combobox" name="accountId" data-options="
                                editable:false,
                                valueField:'id',
                                state:'open',
                                textField:'accountName',
                                url: '${pageContext.request.contextPath}/weiXin/weiXinAccountAction!doNotNeedSessionAndSecurity_getList.action',
                                "
                            style="width: 150px;">
                    </select>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:false,border:false">
        <table id="newsGrid"></table>
    </div>
</div>
<table>

</table>
</body>
</html>
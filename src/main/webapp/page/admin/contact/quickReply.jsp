<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/authority" prefix="authority"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title></title>
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var eventGrid;
        var categoryStr;
        $(function () {
            eventGrid = $('#eventGrid').datagrid({
                url: '${pageContext.request.contextPath}/contact/contactAction!getList.action?category=2&checkPage=1',
                nowrap: true,
                fit: true,
                border: false,
                striped: true,
                rownumbers: true,
                singleSelect:true,
                pagination: true,
                idField: 'id',
                columns: [[
                    {
                        width: '150',
                        title: '标题',
                        field: 'title',
                    },
                    {
                        width: '250',
                        title: '内容',
                        field: 'content'
                    },
                    {
                        width: '80',
                        title: '发表人',
                        field: 'accountNum',
                        formatter : function(value, row) {
                            if(row.userProfile!=undefined){
                                return row.userProfile.name;
                            }else{
                                return "";
                            }
                        }
                    },
                    {
                        width: '120',
                        title: '发表时间',
                        field: 'createTime'
                    },
                    {
                        title: '操作',
                        field: 'action',
                        width: '150',
                        formatter: function (value, row) {
                            var str = '';

                            if(row.status == 1 || row.status == 2) {
                            } else {
                                str += '<a href="javascript:void(0)" onclick="replyFun(' + row.id + ');"><img class="iconImg ext-icon-micro"/>回复</a>&nbsp;';
                            }

                            return str;
                        }
                    }
                ]],
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

            var category = $('#category').val();
            categoryStr = '联系学校';
        });


        var replyFun = function (id) {
            var dialog = parent.modalDialog({
                title: '官方回复',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/contact/replyContact.jsp?id=' + id,
                buttons: [{
                    text: '保存',
                    iconCls: 'ext-icon-save',
                    handler: function () {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, eventGrid, parent.$);
                    }
                }]
            });
        };

        var showFun = function (id) {
            var dialog = parent.modalDialog({
                title: '查看' + categoryStr,
                iconCls: 'ext-icon-note',
                url: '${pageContext.request.contextPath}/page/admin/contact/viewContact.jsp?id=' + id
            });
        };

    </script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">
<div data-options="region:'center',fit:true,border:false">
    <table id="eventGrid"></table>
</div>
</body>
</html>
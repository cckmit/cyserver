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
        $(function () {
            eventGrid = $('#eventGrid').datagrid({
                url: '${pageContext.request.contextPath}/event/eventAction!getSignupPeople.action?event.id='+$('#eventId').val(),
                fit: true,
                border: false,
                fitColumns: true,
                striped: true,
                rownumbers: true,
                pagination: true,
                singleSelect: true,
                columns: [[
                    {
                        width: '70',
                        title: '姓名',
                        field: 'name'                        
                    },
                    {
                        width: '40',
                        title: '性别',
                        field: 'sex',
                        formatter : function(value, row) {
                        	if(row.sex == 0) {
                        		return "男";
                        	} else if(row.sex == 1) {
                        		return "女";
                        	} else {
                        		return "";
                        	}
						}                        
                    },
                    {
                        width: '80',
                        title: '电话',
                        field: 'phoneNum'                        
                    },
                    {
                        width: '120',
                        title: '电子邮箱',
                        field: 'email'                        
                    },
                    {
						width : '340',
						title : '学习经历',
						field : 'fullName',
						align : 'center',
						formatter : function(value, row)
						{
							var text='';
							if(value != null) {
								var array = value.split(',');
								for(var i=0;i<array.length;i++){
									if(i==array.length-1){
										text+=array[i];
									}
									else{
										text+=array[i]+ "<br />";
									}
								}
							}
							return text;
						}
					},
                    {
                        width : '80',
                        title : '是否签到',
                        field : 'isSignIn',
                        align : 'center',
                        formatter : function(value)
                        {
                            var text='';
                            if(value == "1") {
                                text = '<font color="green">已签到</font>';
                            }else{
                                text = '未签到';
                            }
                            return text;
                        }
                    },
                    {
                        width : '120',
                        title : '签到时间',
                        field : 'signInTime',
                        align : 'center'
                    }
                    ]],
                toolbar : '#signerToolbar',
                onBeforeLoad: function (param) {
                    parent.$.messager.progress({
                        text: '数据加载中....'
                    });
                },
                onLoadSuccess: function (data) {
                    console.log(data);
                    $('.iconImg').attr('src', pixel_0);
                    parent.$.messager.progress('close');
                }
            });
        });

        function searchSigner(){
            if ($('#searchSignersForm').form('validate')) {
                $('#eventGrid').datagrid('load',serializeObject($('#searchSignersForm')));
            }
        }
        function resetT(){
            $('#name').val('');
            $('#isSignIn').combobox("setValue", "");
        }
        
    </script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false">

<input name="event.id" type="hidden" id="eventId" value="${param.id}">
<div id="signerToolbar" style="display: none;">
    <table>
        <tr>
            <td>
                <form id="searchSignersForm">
                    <table>
                        <tr>
                            <th>
                                姓名
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input name="name" id="name" style="width: 150px;" />
                            </td>

                            <th>
                                是否签到
                            </th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <select name="isSignIn" id="isSignIn" class="easyui-combobox"
                                        style="width: 150px;" data-options="editable:false">
                                    <option value="" selected>全部</option>
                                    <option value="1">已签到</option>
                                    <option value="0">未签到</option>
                                </select>
                            </td>
                            <td>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchSigner();">查询</a>&nbsp;
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
    <table id="eventGrid"></table>
</div>
</body>
</html>
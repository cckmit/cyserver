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
        var id = "${param.id}";
        var setPos = "${param.setPos}";
        $(function () {
            if(setPos && setPos == '1'){
                $('#setType').show();
                $('#posName').hide();
            }
            $.ajax({
                url : "${pageContext.request.contextPath}/association/associationMemberAction!getById.action?associationMember.id="+id,
                dataType : 'json',
                success : function(result)
                {
                    if (result){
                        $('#selectType').combobox("setValue",result.position?result.position:"");
                        $('#name').text(result.name?result.name:"");
                        $('#sex').text(result.sex?(result.sex=="1"?"女":"男"):"");
                        $('#positionName').text(result.positionName?result.positionName:"");
                        $('#address').text(result.address?result.address:"");
                        $('#sign').text(result.sign?result.sign:"");
                        $('#birthday').text(result.birthday?result.birthday:"");
                        $('#hobby').text(result.hobby?result.hobby:"");
                        $('#groupName').html(result.groupName?result.groupName.replace(/_/g,"</br>"):"");
                    }
                },
                beforeSend : function()
                {
                    parent.$.messager.progress({
                        text : '数据获取中....'
                    });
                },
                complete : function()
                {
                    parent.$.messager.progress('close');
                }
            });
        });

        var submitCheck = function($dialog, $grid, $pjq, status){
            var json = {"associationMember.id":id,
                "associationMember.status": status
                };
            $.ajax({
                url : "${pageContext.request.contextPath}/association/associationMemberAction!update.action",
                data : json,
                dataType : 'json',
                success : function(result)
                {
                    if (result.success){
                        $grid.datagrid('reload');
                        $grid.datagrid('unselectAll');
                        $dialog.dialog('destroy');
                        $pjq.messager.alert('提示', '操作成功', 'info');
                    }else{
                        $pjq.messager.alert('提示', '操作失败', 'error');
                    }
                },
                beforeSend : function()
                {
                    parent.$.messager.progress({
                        text : '数据提交中....'
                    });
                },
                complete : function(data)
                {
                    parent.$.messager.progress('close');
                }
            });
        }

        var subPosition = function($dialog, $grid, $pjq){
            var json = {"associationMember.id":id,
                "associationMember.position": $('#selectType').combobox('getValue')
            };
            $.ajax({
                url : "${pageContext.request.contextPath}/association/associationMemberAction!update.action",
                data : json,
                dataType : 'json',
                success : function(result)
                {
                    if (result.success){
                        $grid.datagrid('reload');
                        $grid.datagrid('unselectAll');
                        $dialog.dialog('destroy');
                        $pjq.messager.alert('提示', '操作成功', 'info');
                    }else{
                        $pjq.messager.alert('提示', '操作失败', 'error');
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


    </script>
</head>

<body>
    <form method="post" class="form">
        <legend>成员信息</legend>
        <table class="ta001" >
            <tr id="setType" style="display: none">
                <th>设置职位</th>
                <td>
                    <input  class="easyui-combobox" id="selectType" style="width: 150px;" value=""
                            data-options="editable:false, required:true,
							        valueField: 'dictValue',
							        textField: 'dictName',
							        url: '${pageContext.request.contextPath}/association/associationMemberAction!doNotNeedSecurity_getMemberType.action'" />
                </td>
            </tr>
            <tr>
                <th>姓名</th>
                <td id="name">
                </td>
            </tr>
            <tr>
                <th>性别</th>
                <td id="sex">
                </td>
            </tr>
            <tr id="posName">
                <th>职位名称</th>
                <td id="positionName">
                </td>
            </tr>
            <tr>
                <th>地址</th>
                <td id="address">
                </td>
            </tr>
            <tr>
                <th>个性签名</th>
                <td id="sign">
                </td>
            </tr>
            <tr>
                <th>生日</th>
                <td id="birthday">
                </td>
            </tr>
            <tr>
                <th>兴趣爱好</th>
                <td id="hobby">
                </td>
            </tr>
            <tr>
                <th>学习经历</th>
                <td id="groupName">
                </td>
            </tr>
            <tr>
                <th>备注</th>
                <td id="remarks">
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
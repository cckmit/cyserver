<%--
  Created by IntelliJ IDEA.
  User: cha0res
  Date: 4/25/17
  Time: 5:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title></title>
    <jsp:include page="../../../inc.jsp"></jsp:include>
</head>

<script>
    var userId = '${param.userId}';
    var userName = '${param.userName}';
    var oldClassId = userId.substring(0, 16);
    var focus = 0;
    var btn;
    function changeClass($dialog, $grid, $pjq, $btn){
        btn = $btn;
        var newClassId = $('#classes').combobox('getValue');
        var newClassName = $('#classes').attr('fullname');

        if(!newClassId || newClassId == ''){
            parent.$.messager.alert('提示', '请选择要转移的班级', 'error');
            return false;
        }
        if(newClassId != '' && oldClassId == newClassId){
            parent.$.messager.alert('提示', '请选择非源班级', 'error');
            return false;
        }

        $.messager.confirm('确认', '确定将校友「'+userName+'」移到「'+newClassName+'」？', function(r){
            if(r){
                $.ajax({
                    url : '${pageContext.request.contextPath}/userInfo/userInfoAction!doNotNeedSecurity_moveToOtherClass.action',
                    data : {
                        focus: focus,
                        userId : userId,
                        deptId : newClassId
                    },
                    dataType : 'json',
                    success : function(result)
                    {
                        if (result.success)
                        {
                            var code = result.obj.code;

                            if(code == 0){
                                $grid.datagrid('reload');
                                $dialog.dialog('destroy');
                                $pjq.messager.alert('提示', '迁移成功', 'info');
                            }else if(code == -1){
                                parent.$.messager.alert('提示', '校友或目标班级已不存在', 'error');
                            }else if(code == 999){
                                parent.$.messager.alert('提示', '目标班级已达最大人数限制', 'error');
                            }else if(code == 1 || code == 2){
                                if(code == 2){
                                    parent.$.messager.alert('提示', '监测到目标班级有绑定相同用户的校友数据,无法继续迁移', 'error');
                                    btn.innerHTML = '<span class=\'l-btn-left l-btn-icon-left\'><span class=\'l-btn-text\'>无法迁移</span><span class=\'l-btn-icon ext-icon-save\'>&nbsp;</span></span>';
                                }else{
                                    btn.innerHTML = '<span class=\'l-btn-left l-btn-icon-left\'><span class=\'l-btn-text\'>继续迁移</span><span class=\'l-btn-icon ext-icon-save\'>&nbsp;</span></span>';
                                    focus = 1;
                                }
                                $('#tiShi').show();
                                var list = result.obj.list;
                                var sameStr = '<tr>' +
                                    '<td>姓名</td>'+
                                    '<td>性别</td>'+
                                    '<td>学号</td>'+
                                    '<td>手机号</td>'+
                                    '<td>邮箱</td>'+
                                    '<td>证件号</td>'+
                                    '<td>相同处</td>'+
                                    '</tr>';
                                if(list && list.length > 0){
                                    sameStr += '<tr>'+
                                        '<td><a href="javascript:void(0)" onclick="viewFun(\'' + list[0].userId + '\');">'+list[0].userName+'</a></td>'+
                                        '<td>'+list[0].sex+'</td>'+
                                        '<td>'+list[0].studentnumber+'</td>'+
                                        '<td>'+list[0].telId+'</td>'+
                                        '<td>'+list[0].email+'</td>'+
                                        '<td>'+list[0].card+'</td>'+
                                        '<td><font color="rad">'+list[0].remarks+'</font></td>';
                                }
                                $('#tiShiList').html(sameStr);
                            }

                        }
                        else
                        {
                            $pjq.messager.alert('提示', result.msg, 'error');
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
        });
    }

     function viewFun(id) {
        var dialog = parent.parent.WidescreenModalDialog({
            title : '查看校友',
            iconCls : 'ext-icon-note',
            url : '${pageContext.request.contextPath}/page/admin/userinfo/viewUserInfo.jsp?id=' + id
        });
    }

    function init() {
        $('#tiShi').hide();
        $('#tiShiList').empty();
        if(focus == 1){
            focus = 0;
            btn.innerHTML = '<span class=\'l-btn-left l-btn-icon-left\'><span class=\'l-btn-text\'>迁移数据</span><span class=\'l-btn-icon ext-icon-save\'>&nbsp;</span></span>';
        }
    }
</script>
<body>
    <form method="post" >
        <fieldset>
            <legend>
                迁移数据
            </legend>
            <table class="ta001">
                <tr>
                    <th>
                        校友名
                    </th>
                    <td>
                        ${param.userName}
                    </td>
                </tr>
                <tr>
                    <th>
                        源班级
                    </th>
                    <td>
                        ${param.oldClassName}
                    </td>
                </tr>
                <tr>
                    <th>
                        目标班级
                    </th>
                    <td>

                        <table>

                            <tr>
                                <th align="right" >学校</th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input id="school" class="easyui-combobox" style="width: 130px;"
                                           data-options="
                                                    valueField: 'deptId',
                                                    textField: 'deptName',
                                                    editable:false,
                                                    prompt:'--请选择--',
                                                    icons:[{
                                                        iconCls:'icon-clear',
                                                        handler: function(e){
                                                        $('#school').combobox('clear');
                                                        $('#depart').combobox('clear');
                                                        $('#grade').combobox('clear');
                                                        $('#classes').combobox('clear');
                                                        $('#major').combobox('clear');
                                                        $('#classes').combobox('loadData',[]);
                                                        $('#grade').combobox('loadData',[]);
                                                        $('#major').combobox('loadData',[]);
                                                        $('#depart').combobox('loadData',[]);
                                                        }
                                                    }],
                                                    url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDepart.action',
                                                    onSelect: function(rec){
                                                        var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getByParentId.action?deptId='+rec.deptId;
                                                        $('#depart').combobox('clear');
                                                        $('#grade').combobox('clear');
                                                        $('#classes').combobox('clear');
                                                        $('#major').combobox('clear');
                                                        $('#classes').combobox('loadData',[]);
                                                        $('#grade').combobox('loadData',[]);
                                                        $('#major').combobox('loadData',[]);
                                                        $('#depart').combobox('reload', url);
                                                        init();

                                            }" />
                                </td>
                                <th align="right" >院系</th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input id="depart" class="easyui-combobox" style="width: 130px;"
                                           data-options="
                                                    valueField: 'deptId',
                                                    textField: 'deptName',
                                                    editable:false,
                                                    prompt:'--请选择--',
                                                    icons:[{
                                                        iconCls:'icon-clear',
                                                        handler: function(e){
                                                        $('#depart').combobox('clear');
                                                        $('#grade').combobox('clear');
                                                        $('#classes').combobox('clear');
                                                        $('#major').combobox('clear');
                                                        $('#classes').combobox('loadData',[]);
                                                        $('#grade').combobox('loadData',[]);
                                                        $('#major').combobox('loadData',[]);
                                                        }
                                                    }],
                                                    onSelect: function(rec){
                                                        var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
                                                        var url1= '${pageContext.request.contextPath}/major/majorAction!doNotNeedSecurity_getMajor.action?deptId='+rec.deptId;
                                                        $('#grade').combobox('clear');
                                                        $('#classes').combobox('clear');
                                                        $('#classes').combobox('loadData',[]);
                                                        $('#grade').combobox('reload', url);
                                                        $('#major').combobox('clear');
                                                        $('#major').combobox('reload', url1);
                                                        init();
                                            }" />
                                </td>
                                <th align="right" >年级</th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input id="grade" class="easyui-combobox" style="width: 130px;"
                                           data-options="
                                                    valueField: 'deptId',
                                                    textField: 'deptName',
                                                    editable:false,
                                                    prompt:'--请选择--',
                                                    icons:[{
                                                        iconCls:'icon-clear',
                                                        handler: function(e){
                                                        $('#grade').combobox('clear');
                                                        $('#classes').combobox('clear');
                                                        $('#classes').combobox('loadData',[]);
                                                        }
                                                    }],
                                                    onSelect: function(rec){
                                                        var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
                                                        $('#classes').combobox('clear');
                                                        $('#classes').combobox('reload', url);
                                                        init();

                                            }" />
                                </td>
                                <th align="right" >班级</th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input id="classes" class="easyui-combobox" style="width: 130px;"
                                           data-options="
                                                    editable:false,
                                                    valueField:'deptId',
                                                    textField:'deptName',
                                                    prompt:'--请选择--',
                                                    icons:[{
                                                        iconCls:'icon-clear',
                                                        handler: function(e){
                                                        $('#classes').combobox('clear');
                                                        }
                                                    }],
                                                    onSelect: function(rec){
                                                       $('#classes').attr('fullName', rec.fullName);
                                                       init();
                                                    }
                                                    "/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </fieldset>
        <fieldset id="tiShi" style="display: none;">
            <legend>
                提示
            </legend>
            <span style="font-size: 13px">在目标班级监测到相似校友数据，若想强制迁移，请点击右下角<font color="#1e90ff">「继续迁移」</font>按钮，<font color="red">此操作可能造成数据冗余，请三思!!!</font></span>
            <table class="ta001" id="tiShiList">

            </table>
        </fieldset>
    </form>
</body>
</html>

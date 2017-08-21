<%--
  Created by IntelliJ IDEA.
  User: liuzhen
  Date: 7/21/16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>

<%--访问路径: localhost:8080/cy/page/admin--%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                        + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">

    <title>院系管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        var deptDataGrid;
        var lastSchool ;
        var index ;
        $(function()
        {
            deptDataGrid = $('#deptDataGrid').datagrid({
//              对这个树表属性的定义
                title:'院系归属',
                url:'${pageContext.request.contextPath}/dept/deptAction!getDeptGridByAttribution.action',
                fit : true,
//                fitColumns : true,
                pagination : true,
                border : false,
                striped : true,
                rownumbers : true,
                idField : 'collegeId',

                columns:[[{
                        field : 'collegeId',
                        checkbox : true
                    },
                    {
                        title:'学校编号',
                        hidden: true ,
                        field:'schoolId'
                    },
                    {
                        title:'年级编号',
                        hidden: true ,
                        field:'gradeIds'
                    },
                    {
                        title:'归属学院',
                        hidden: true ,
                        field:'belongDeptId'
                    },
                    {
                        hidden: true ,
                        title:'是否现有',
                        field:'isCurrent'
                    },
                    {
                        width:'150',
                        title:'学校',
                        field:'schoolName',
                        formatter:function(value,row)
                        {
                            var str = '';
                            var school = row.schoolName ;
                            if(index == 0 || school != lastSchool) {
                                str = school ;
                                lastSchool = school ;
                            }
                            index++;
                            return str ;
                        }
                    },
                    {
                        width:'100',
                        title:'学院',
                        field:'collegeName',
                        formatter:function(value,row)
                        {
                            var collegeName = row.collegeName ;
                            var str = collegeName;
                            var belongDeptId = row.belongDeptId ;
                            var isCurrent = row.isCurrent ;
                            if(isCurrent != '1' && (belongDeptId == null || belongDeptId == '')) {
                                str = "<span style='color:red;'>" + collegeName + "</span>" ;
                            }
                            return str ;
                        }
                    },
                    {
                        width:'250',
                        title:'年级',
                        field:'gradeNames'
                    },
                    {
                        width:'80',
                        title:'是否现有',
                        field:'isCurrentNum',
                        formatter:function(value,row)
                        {
                            var str = '';
                            var isCurrent = row.isCurrent ;
                            if(isCurrent == '1') {
                                str = '是'
                            }
                            return str ;
                        }
                    },
                    {
                        width:'250',
                        title:'归属学院',
                        field:'belongDeptName'
                    },
                    {
                        width:'200',
                        title:'操作',
                        field:'action',
                        formatter:function(value,row)
                        {
                            var deptId = row.collegeId ;
                            var deptName = row.collegeName ;
                            var str = '';
                            var isCurrent = row.isCurrent ;
                            if(isCurrent != '1') {
                                <authority:authority authorizationCode="设置现有" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="setBelongDept(\''+deptId+'\');">' +
                                        '<img class="iconImg ext-icon-note" />设置归属</a>&nbsp;';
                                </authority:authority>
                            } else {
                                <%--<authority:authority authorizationCode="设置现有" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="designatedManagementOrganization(\''+deptId+'\',\''+deptName+'\');">' +
                                        '<img class="iconImg ext-icon-note_edit" />指定管理组织</a>&nbsp;';
                                </authority:authority>--%>
                                <authority:authority authorizationCode="设置现有" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="shiftFromSourceBelongToGoalBelong(\''+deptId+'\',\''+deptName+'\');">' +
                                        '<img class="iconImg ext-icon-note_edit" />转移归属学院</a>&nbsp;';
                                </authority:authority>
                            }
                            return str;
                        }
                    }
                ]],

                toolbar:'#toolbar',

                onBeforeLoad:function(row,param)
                {
                    index = 0 ;
                    parent.$.messager.progress({
                        text: '数据加载中....'
                    });
                },
                onLoadSuccess:function(row,data)
                {
                    $('.iconImg').attr('src',pixel_0);
                    parent.$.messager.progress('close');

                }
            });
        });


        function setCurrentDept(isCurrent)
        {
            var rows = $("#deptDataGrid").datagrid('getChecked');
            var ids = [];
            if (rows.length > 0)
            {
                var msg = '' ;
                if(isCurrent == '1') {
                    msg = '确定设置成现有学院吗？' ;
                } else {
                    msg = "确定取消现有学院并删除其下所有归属机构?" ;
                }
                parent.$.messager.confirm('确认', msg, function(r)
                {
                    if (r)
                    {
                        for ( var i = 0; i < rows.length; i++)
                        {
                            ids.push(rows[i].collegeId);
                        }
                        $.ajax({
                            url : '${pageContext.request.contextPath}/dept/deptAction!setCurrentDept.action',
                            data : {
                                deptCurrIds : ids.join(','),
                                isCurrent : isCurrent
                            },
                            dataType : 'json',
                            success : function(data)
                            {
                                if (data.success)
                                {
                                    $("#deptDataGrid").datagrid('reload');
                                    $("#dept_belongDeptId").combotree('reload');
                                    if(isCurrent == '1') {
                                        insertAndBindingAll(ids.join(','), data.msg + ",");
                                    } else {
                                        unbindingAll(ids.join(','), data.msg + ",") ;
                                    }
                                    $("#deptDataGrid").datagrid('unselectAll');
                                } else
                                {
                                    parent.$.messager.alert('错误', data.msg, 'error');
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
            } else
            {
                parent.$.messager.alert('提示', '请选择要设置现有的记录！', 'error');
            }
        }

        /**
         * 设置归属院系
         * @param deptId
         */
        function setBelongDept(deptId)
        {
            var id = deptId ;
            var title = "设置归属" ;
            var dialog = parent.modalDialog({
                title : title,
                iconCls : 'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/deptInfo/setBelongDept.jsp?id=' + id ,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function()
                    {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, deptDataGrid, parent.$);
                    }
                } ]
            });
        }

        /**
         * 批量设置归属院系
         * @param deptId
         */
        function setBelongDeptAll()
        {
            var rows = $("#deptDataGrid").datagrid('getChecked');
            var ids = [];
            if (rows.length > 0)
            {
                parent.$.messager.confirm('确认', "确定要批量设置归属吗?", function(r)
                {
                    if (r)
                    {
                        for ( var i = 0; i < rows.length; i++)
                        {
                            var isCurrent = rows[i].isCurrent ;
                            if(isCurrent == '1') {
                                parent.$.messager.alert('提示',rows[i].collegeName + '是现有学院不能指定归属机构', 'error');
                                return ;
                            }
                            ids.push(rows[i].collegeId);
                        }

                        var deptIds = ids.join(',') ;
                        var title = "批量设置归属" ;
                        var dialog = parent.modalDialog({
                            title : title,
                            iconCls : 'ext-icon-note_add',
                            url : '${pageContext.request.contextPath}/page/admin/deptInfo/setBelongDept.jsp?deptIds=' + deptIds ,
                            buttons : [ {
                                text : '保存',
                                iconCls : 'ext-icon-save',
                                handler : function()
                                {
                                    dialog.find('iframe').get(0).contentWindow.submitForm(dialog, deptDataGrid, parent.$);
                                }
                            } ]
                        });
                    }
                });
            } else
            {
                parent.$.messager.alert('提示', '请选择要设置现有的记录！', 'error');
            }

        }

        /**
         * 编辑院系
         */
        function editDeptName(deptId,parentId){
//            var id = $('#deptTree').tree('getSelected').id;
//            var parentId = $('#deptTree').tree('getSelected').pid;
            var title ;
            if (deptId.length == 6)
            {
                title = '编辑学校';
            } else if (deptId.length == 10)
            {
                title = '编辑院系';
            } else if (deptId.length == 16)
            {
                title = '编辑班级';
            }
            var dialog = parent.modalDialog({
                title : title,
                iconCls : 'ext-icon-note_edit',
                url : '${pageContext.request.contextPath}/page/admin/deptInfo/editDept.jsp?id=' + deptId +'&pid='+parentId ,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function()
                    {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, deptDataGrid, parent.$);
                    }
                } ]
            });
        }

        /**
         * 转移归属于当前现有学院下所有学院的归属学院
         */
        function shiftFromSourceBelongToGoalBelong(deptId,deptName)
        {

            //2. 提示用户是否要删除
            parent.$.messager.confirm('确认','转移归属于当前现有学院'+deptName+'下所有学院的归属学院?',function(r)
            {
//                alert("r= "+r); // 弹出的对话框,点击"确定":r=true; 点击"取消":r=false
                //如果点击"确定", r= true ajax请求,进入deptInfoAction/deleteDeptInfo()
                if(r)
                {
                    var title = '转移归属' ;
                    var dialog = parent.modalDialog({
                        title : title,
                        iconCls : 'ext-icon-note_edit',
                        url : '${pageContext.request.contextPath}/page/admin/deptInfo/shiftBelongDept.jsp?deptId=' + deptId +'&deptName='+deptName ,
                        buttons : [ {
                            text : '保存',
                            iconCls : 'ext-icon-save',
                            handler : function()
                            {
                                dialog.find('iframe').get(0).contentWindow.submitForm(dialog, deptDataGrid, parent.$);
                            }
                        } ]
                    });

                }

            });
            //3. 删除

        }

        /**
         * 清除查询条件
         */
        function resetT(){
            $('#dept_belongDeptId').combobox('clear');
            $('#depart').combobox('clear');
            $('#dept_isCurrent').combobox('clear');
            $('#dept_hasBelong').combobox('clear');
            $('#schoolName').val('');
            $('#collegeName').val('');
            $('#gradeName').val('');
            $('#className').val('');
            $('#adminNames').val('');

            $('#school').combobox('clear');
            $('#depart').combobox('clear');



//            $('#city').combobox('loadData',[]);
//            $('#area').combobox('loadData',[]);
        }


        /**
         * 创建绑定学院组织(批量)
         */
        var insertAndBindingAll = function(ids,msg) {
            if(ids == null || ids.length <= 0) {
                parent.$.messager.alert('提示', "未传学院编号", 'error');
                return ;
            }

//            parent.$.messager.confirm('确认',msg + '是否给现有学院创建学院分会?',function(r)
//            {
////                alert("r= "+r); // 弹出的对话框,点击"确定":r=true; 点击"取消":r=false
//                //如果点击"确定", r= true ajax请求,进入deptInfoAction/deleteDeptInfo()
//                if(r)
//                {
//                    for(var i = 0 ; i < ids.length ; i++) {
//                        insertAndBinding(ids[i]) ;
//                    }
//                }
//            });
            $.ajax({
                url : '${pageContext.request.contextPath}/alumni/alumniAction!insertAndBinding.action',
                data : {xueyuanId:ids},
                dataType : 'json',
                success : function(result) {
                    if (result.success) {
                        $('#deptDataGrid').datagrid('reload');
                        parent.$.messager.alert('提示', msg + result.msg, 'info');
                    } else {
                        parent.$.messager.alert('提示', msg + result.msg, 'error');
                    }
                }
            });
        }

        /**
         * 解除学院与学院分会的绑定成功(批量)
         */
        var unbindingAll = function(ids,msg) {
            if(ids == null || ids.length <= 0) {
                parent.$.messager.alert('提示', "未传学院编号", 'error');
                return ;
            }
            $.ajax({
                url : '${pageContext.request.contextPath}/alumni/alumniAction!unbinding.action',
                data : {xueyuanId:ids},
                dataType : 'json',
                success : function(result) {
                    if (result.success) {
                        $('#deptDataGrid').datagrid('reload');
                        parent.$.messager.alert('提示', msg + result.msg, 'info');
                    } else {
                        parent.$.messager.alert('提示', msg + result.msg, 'error');
                    }
                },
                beforeSend:function(){
                    parent.$.messager.progress({
                        text : '数据提交中....'
                    });
                },
                complete:function(){
                    parent.$.messager.progress('close');
                }
            });
        }


        /**
         * 条件查询
         */
        function searchDept()
        {
            $('#deptDataGrid').datagrid('load', serializeObject($('#searchForm')));
        }

        $('#cc').combobox({
            url:'combobox_data.json',
            valueField:'id',
            textField:'text'
        });

        var findTreegrid = function () {
            var dialog = parent.modalDialog({
                title: '院系归属树型结构图',
                iconCls: 'ext-icon-note_add',
                url: '${pageContext.request.contextPath}/page/admin/deptAttributiontree.jsp',
            });
        };
    </script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false" >
    <div id="toolbar" style="display: none;">
            <table>
                <tr>

                    <td>
                        <form id="searchForm">
                            <table>
                            <tr>
                                <th>是否现有</th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <select id="dept_isCurrent" class="easyui-combobox" data-options="editable:false" name="dept.isCurrent" style="width: 150px;">
                                        <option value="">--请选择--</option>
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select>
                                </td>
                                <th>是否具有归属</th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <select id="dept_hasBelong" class="easyui-combobox" data-options="editable:false" name="dept.hasBelong" style="width: 150px;">
                                        <option value="">--请选择--</option>
                                        <option value="1">有</option>
                                        <option value="0">无</option>
                                    </select>
                                </td>
                                <th>现有机构</th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <select id="dept_belongDeptId" name="dept.belongDeptId"
                                            class="easyui-combotree"
                                            data-options="
                                            editable:false,
                                            idField:'id',
                                            state:'open',
                                            textField:'text',
                                            parentField:'pid',
                                            url:'${pageContext.request.contextPath}/dept/deptAction!getCurrDeptTree.action',
                                            onBeforeSelect: function(node) {
                                                // 判断是否是叶子节点
                                                var isLeaf = $(this).tree('isLeaf', node.target);
                                                if (!isLeaf) {
                                                    $.messager.show({
                                                        msg: '请选择叶子节点！'
                                                    });
                                                    // 返回false表示取消本次选择操作
                                                    return false;
                                                }
                                            }
                                        "
                                            style="width: 200px;">
                                    </select>
                                </td>


                                <th align="right" >学校</th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <%--
                                    <input name="queryMap.schoolId" id="schoolId" type="hidden">
                                    <input name="queryMap.collegeId" id="collegeId" type="hidden">
                                    <input name="queryMap.gradeId" id="gradeId" type="hidden">
                                    <input name="queryMap.classId" id="classId" type="hidden">
                                    --%>
                                    <input id="schoolName" name="queryMap.schoolName" type="hidden"/>
                                    <input id="collegeName" name="queryMap.collegeName" type="hidden"/>
                                    <input id="gradeName" name="queryMap.gradeName" type="hidden"/>
                                    <input id="className" name="queryMap.className" type="hidden"/>
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

													$('#schoolName').prop('value','');
													$('#collegeName').prop('value','');
													$('#gradeName').prop('value','');
													$('#className').prop('value','');
													<%--$('#schoolId').prop('value','');
													$('#collegeId').prop('value','');
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');--%>
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

													$('#schoolName').prop('value',rec.deptName);
													$('#collegeName').prop('value','');
													$('#gradeName').prop('value','');
													$('#className').prop('value','');
													<%--$('#schoolId').prop('value',rec.deptId);
													$('#collegeId').prop('value','');
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');--%>
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

													$('#collegeName').prop('value','');
													$('#gradeName').prop('value','');
													$('#className').prop('value','');
													<%--$('#collegeId').prop('value','');
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');--%>
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

													$('#collegeName').prop('value',rec.deptName);
													$('#gradeName').prop('value','');
													$('#className').prop('value','');
													<%--$('#collegeId').prop('value',rec.deptId);
													$('#gradeId').prop('value','');
													$('#classId').prop('value','');--%>
										}" />
                                </td>

                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchDept();">查询</a>
                                    <a href="javascript:void(0)" class="easyui-linkbutton"
                                       data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true"
                                       onclick="resetT();">重置</a>
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
                                <td colspan="4">
                                    <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="设置现有">
                                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-import_customer',plain:true" onclick="setCurrentDept('1');" style="margin-left: 5px; margin-top: 1px;">设置现有</a>
                                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-import_customer',plain:true" onclick="setCurrentDept('0');" style="margin-left: 5px; margin-top: 1px;">取消现有</a>
                                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-import_customer',plain:true" onclick="setBelongDeptAll();" style="margin-left: 5px; margin-top: 1px;">批量设置归属</a>
                                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-import_customer',plain:true" onclick="findTreegrid();" style="margin-left: 5px; margin-top: 1px;">查看树型结构</a>
                                    </authority:authority>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>

    <div data-options="region:'center', fit:true,border:false">
        <table id="deptDataGrid" data-options="fit:true,border:false"></table>
    </div>
</body>


</html>

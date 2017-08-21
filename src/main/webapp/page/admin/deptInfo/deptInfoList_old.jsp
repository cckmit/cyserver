<%--
  Created by IntelliJ IDEA.
  User: jiangling
  Date: 7/14/16
  Time: 4:28 PM
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
        var deptInfoTreeGrid;
        var collegeId ;
        var isCurrent ;
        var belongDeptId ;
        var belongDeptName ;
        $(function()
        {
            deptInfoTreeGrid = $('#deptInfoTreeGrid').treegrid({
//              对这个树表属性的定义
                title:'院系树表',
                url:'${pageContext.request.contextPath}/dept/deptAction!getDeptTreeGrid.action',
                fitColumns:true,
                idField:'deptId',
                treeField:'deptName',
                parentField:'parentId',
                rownumbers:true,
                pagination:false,

                frozenColumns:[[
                    {
                        width:'200',
                        title:'机构名称',
                        field:'deptName',
                        formatter:function(value,row)
                        {
                            var str = null ;
                            var deptId = row.deptId ;
                            var deptName = row.deptName ;
                            str = deptName ;
                            if (deptId != null && deptId.length == 10) {
                                collegeId = deptId ;
                                belongDeptId = row.belongDeptId ;
                                isCurrent = row.isCurrent ;
                                if((belongDeptId == null || belongDeptId == '') && isCurrent != '1') {
                                    str = "<span style='color: #ff0000;'>"+deptName+"</span>" ;
                                }
                            } else if(deptId != null && deptId.length > 10) {
                                if((belongDeptId == null || belongDeptId == '') && isCurrent != '1') {
                                    str = "<span style='color: #ff0000;'>"+deptName+"</span>" ;
                                }
                            }
                            return str;
                        }
                    }
                ]],
                columns:[[
                    {
                        width:'150',
                        title:'上级机构编码',
                        hidden: true ,
                        field:'parentId'
                    },
                    {
                        width:'250',
                        title:'院系机构路径',
                        field:'fullName'
                    },
//                    {
//                        width:'100',
//                        title:'机构层级',
//                        field:'level'
//                    },
                    {
                        width:'60',
                        title:'是否现有',
                        field:'isCurrent',
                        align: "center" ,
                        formatter:function (value,row) {
                            if (row.isCurrent == '1') {
                                return "√";
                            }
                        }
                    },
                    {
                        hidden:true,
                        field:'belongDeptId',
                        formatter:function(value,row)
                        {
                            var str = null ;
                            var deptId = row.deptId ;
                            if (deptId != null && deptId.length == 10) {
                                collegeId = deptId ;
                                belongDeptId = row.belongDeptId ;
                                isCurrent = row.isCurrent ;
                                str = belongDeptName ;
                            } else if(deptId != null && deptId.length > 10) {
                                str = belongDeptId ;
                            }
                            return str;
                        }
                    },
                    {
                        width:'240',
                        title:'归属机构',
                        field:'belongDeptName',
                        formatter:function(value,row)
                        {
                            var str = null ;
                            var deptId = row.deptId ;
                            if (deptId != null && deptId.length == 10) {
                                collegeId = deptId ;
                                belongDeptName = row.belongDeptName ;
                                str = belongDeptName ;
                            } else if(deptId != null && deptId.length > 10) {
                                str = belongDeptName ;
                            }
                            return str;
                        }
                    },
                    {
                        width:'200',
                        title:'操作',
                        field:'action',
                        formatter:function(value,row)
                        {
                            var deptId = row.deptId ;
                            var parentId = row.parentId ;
                            var str = '';
                            var length = 0 ;
                            if(deptId != undefined) {
                                length = deptId.length ;
                            }
                            <%--<authority:authority authorizationCode="查看机构详情" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="showDeptInfo(\''+deptId+'\');">' +
                                        '<img class="iconImg ext-icon-note" />查看</a>&nbsp;';
                            </authority:authority>--%>
                            <authority:authority authorizationCode="编辑机构" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="editDeptName(\''+deptId+'\',\''+parentId+'\');">' +
                                        '<img class="iconImg ext-icon-note" />编辑</a>&nbsp;';
                            </authority:authority>
                            <authority:authority authorizationCode="删除机构" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="removeDeptInfo(\''+deptId+'\');">' +
                                        '<img class="iconImg ext-icon-note_delete" />删除</a>';
                            </authority:authority>
                            if (length < 16) {
                                <authority:authority authorizationCode="新增机构" userRoles="${sessionScope.user.userRoles}">
                                    str += '<a href="javascript:void(0)" onclick="add(\''+deptId+'\');">' +
                                        '<img class="iconImg ext-icon-note_add" />添加</a>';
                                </authority:authority>
                            }
                            return str;
                        }
                    }
                ]],

                toolbar:'#toolbar',

                onBeforeLoad:function(row,param)
                {
                    parent.$.messager.progress({
                        text: '数据加载中....'
                    });
                },
                onLoadSuccess:function(row,data)
                {
//                    alert(JSON.stringify(data));
                    $('.iconImg').attr('src',pixel_0);
                    parent.$.messager.progress('close');

                }
            });
        });

        /**
         * 添加院系
         * @param deptId
         */
        function add(deptId)
        {
            var id = deptId ;
            var title;
            if (id.length == 6 )
            {
                title = '新增院系';
            }
            if (id.length == 10)
            {
                title = '新增年级';
            }
            if (id.length == 14)
            {
                title = '新增班级';
            }
            var dialog = parent.modalDialog({
                title : title,
                iconCls : 'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/deptInfo/addDept.jsp?id=' + id ,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function()
                    {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, deptInfoTreeGrid, parent.$);
                    }
                } ]
            });
        }

        /**
         * 编辑院系
         */
        function editDeptName(deptId,parentId){
//            var id = $('#deptTree').tree('getSelected').id;
//            var parentId = $('#deptTree').tree('getSelected').pid;
            if (deptId.length == 6)
            {
                title = '编辑学校';
            }
            if (deptId.length == 10)
            {
                title = '编辑院系';
            }
            if (deptId.length == 14)
            {
                title = '编辑年级';
            }
            if (deptId.length == 16)
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
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, deptInfoTreeGrid, parent.$);
                    }
                } ]
            });
        }

        //删除本行节点
        function removeDeptInfo(id)
        {
//            alert("id= "+id);
            //2. 提示用户是否要删除
            parent.$.messager.confirm('确认','您确定要删除此机构?',function(r)
            {
//                alert("r= "+r); // 弹出的对话框,点击"确定":r=true; 点击"取消":r=false
                //如果点击"确定", r= true ajax请求,进入deptInfoAction/deleteDeptInfo()
                if(r)
                {
                    $.ajax({
                        url : '${pageContext.request.contextPath}/dept/deptAction!delete.action',
                        data : {deptId: id},
                        dataType : 'json',
                        success : function(result)
                        {
                            if (result.success)
                            {
                                //??? refresh and reload; 是否要分开考虑学校(根节点)和
                                //子节点刷新方式
                                deptInfoTreeGrid.treegrid('reload');
                                $.messager.alert('提示',result.msg,'info');
                            } else
                            {
                                parent.$.messager.alert('提示',result.msg,'error');
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
            //3. 删除

        }

        /**
         * 导入院系
         */
        function importFun()
        {
            var dialog = parent.modalDialog({
                title : '导入院系',
                iconCls : 'ext-icon-import_customer',
                url : '${pageContext.request.contextPath}/page/admin/deptInfo/importDept.jsp',
                buttons : [ {
                    text : '确定',
                    iconCls : 'ext-icon-save',
                    handler : function()
                    {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, deptInfoTreeGrid, parent.$);
                    }
                } ]
            });
        }

        /**
         * 清除查询条件
         */
        function resetT(){
            $('#dept_belongDeptId').combobox('clear');
            $('#depart').combobox('clear');
            $('#dept_isCurrent').combobox('clear');
            $('#dept_hasBelong').combobox('clear');
            //$('#searchForm')[0].reset();
            $('#dept_schoolName').val('');
            $('#dept_departName').val('');
            $('#dept_grade').val('');
            $('#dept_classes').val('');

            $('#school').combobox('clear');
            $('#depart').combobox('clear');
            $('#grade').combobox('clear');
            $('#classes').combobox('clear');
        }

        /**
         * add by jiangling
         * 条件查询
         */
        function searchDept(){
            $('#deptInfoTreeGrid').treegrid('load', serializeObject($('#searchForm')));

        }

    </script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false" >
    <div id="toolbar" style="display: none;">
            <table>
                <%--<tr>
                    <td>
                        <form id="searchForm">
                            <table>
                            <tr>
                                <th>机构名称</th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="deptInfo.deptName" style="width: 150px;" />
                                </td>

                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchDeptInfo();">查询</a>
                                    <a href="javascript:void(0)" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="resetT();">重置</a>
                                </td>
                             </tr>
                            </table>
                        </form>
                    </td>
                </tr>--%>
                <tr>
                    <td>
                        <form id="searchForm">
                            <table>
                                <tr>
                                    <th align="right" >学校</th>
                                    <td>
                                        <div class="datagrid-btn-separator"></div>
                                    </td>
                                    <td>
                                        <input id="dept_schoolName" name="dept.schoolName" type="hidden"/>
                                        <input id="dept_departName" name="dept.departName" type="hidden"/>
                                        <input id="dept_grade" name="dept.grade" type="hidden"/>
                                        <input id="dept_classes" name="dept.classes" type="hidden"/>

                                        <input id="school" class="easyui-combobox" style="width: 150px;"
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

													$('#dept_schoolName').prop('value','');
													$('#dept_departName').prop('value','');
													$('#dept_grade').prop('value','');
													$('#dept_classes').prop('value','');
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

													$('#dept_schoolName').prop('value',rec.deptName);
													$('#dept_departName').prop('value','');
													$('#dept_grade').prop('value','');
													$('#dept_classes').prop('value','');
										}" />
                                    </td>
                                    <th align="right" >院系</th>
                                    <td>
                                        <div class="datagrid-btn-separator"></div>
                                    </td>
                                    <td>
                                        <input id="depart" class="easyui-combobox" style="width: 150px;"
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

													$('#dept_departName').prop('value','');
													$('#dept_grade').prop('value','');
													$('#dept_classes').prop('value','');
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

													$('#dept_departName').prop('value',rec.deptName);
													$('#dept_grade').prop('value','');
													$('#dept_classes').prop('value','');
										}" />
                                    </td>
                                    <th align="right" >年级</th>
                                    <td>
                                        <div class="datagrid-btn-separator"></div>
                                    </td>
                                    <td>
                                        <input id="grade" class="easyui-combobox" style="width: 150px;"
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

													$('#dept_grade').prop('value','');
													$('#dept_classes').prop('value','');
									                }
									            }],
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
													$('#classes').combobox('clear');
													$('#classes').combobox('reload', url);

													$('#dept_grade').prop('value',rec.deptName.substring(0,4));
													$('#dept_classes').prop('value','');
										}" />
                                    </td>
                                    <th align="right" >班级</th>
                                    <td>
                                        <div class="datagrid-btn-separator"></div>
                                    </td>
                                    <td>
                                        <input id="classes" class="easyui-combobox" style="width: 150px;"
                                               data-options="
												editable:false,
												valueField:'deptId',
												textField:'deptName',
												prompt:'--请选择--',
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
													$('#classes').combobox('clear');

													$('#dept_classes').prop('value','');
									                }
									            }],
												onSelect: function(rec){
												    $('#dept_classes').prop('value',rec.deptName);
												}
												"/>
                                    </td>
                                   <%-- <th align="right" width="80px;">学校</th>
                                    <td>
                                        <div class="datagrid-btn-separator"></div>
                                    </td>
                                    <td>
                                        <input id="dept_schoolName" name="dept.schoolName" style="width: 150px;">
                                    </td>
                                    <th align="right" width="80px;">院系</th>
                                    <td>
                                        <div class="datagrid-btn-separator"></div>
                                    </td>
                                    <td>
                                        <input id="dept_departName" name="dept.departName" style="width: 150px;">
                                    </td>
                                    <th align="right" width="80px;">年级</th>
                                    <td>
                                        <div class="datagrid-btn-separator"></div>
                                    </td>
                                    <td>
                                        <input id="dept_grade" name="dept.grade" style="width: 150px;">
                                    </td>
                                    <th align="right" width="80px;">班级</th>
                                    <td>
                                        <div class="datagrid-btn-separator"></div>
                                    </td>
                                    <td>
                                        <input id="dept_classes" name="dept.classes" style="width: 150px;">
                                    </td>--%>
                                </tr>
                                <tr>
                                    <th align="right" width="80px;">是否现有</th>
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
                                    <%--<th>是否具有归属</th>--%>
                                    <%--<td>--%>
                                    <%--<div class="datagrid-btn-separator"></div>--%>
                                    <%--</td>--%>
                                    <%--<td>--%>
                                    <%--<select id="dept_hasBelong" class="easyui-combobox" data-options="editable:false" name="dept.hasBelong" style="width: 150px;">--%>
                                    <%--<option value="">--请选择--</option>--%>
                                    <%--<option value="1">有</option>--%>
                                    <%--<option value="0">无</option>--%>
                                    <%--</select>--%>
                                    <%--</td>--%>
                                    <th align="right" width="80px;">现有机构</th>
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
                                                style="width: 150px;">
                                        </select>
                                    </td>
                                    <td colspan="3" align="right">
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
                                    <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="院系导入">
                                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-import_customer',plain:true" onclick="importFun();" style="margin-left: 5px; margin-top: 1px;">院系导入</a>
                                    </authority:authority>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>

    <div data-options="region:'center', fit:true,border:false">
        <table id="deptInfoTreeGrid" data-options="fit:true,border:false"></table>
    </div>
</body>


</html>

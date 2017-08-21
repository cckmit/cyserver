<%--
  Created by IntelliJ IDEA.
  User: liuzhen
  Date: 7/26/16
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

    <title>班级管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script src="http://www.jeasyui.com/easyui/datagrid-detailview.js" type="text/javascript"></script>
    <script type="text/javascript">
        var deptDataGrid;
        var lastSchool ;
        var lastCollege ;
        var lastGrade ;
        var index ;
        $(function()
        {
            deptDataGrid = $('#deptDataGrid').datagrid({
//                view: detailview,//注意1
//              对这个树表属性的定义
                title:'班级列表',
                url:'${pageContext.request.contextPath}/dept/deptAction!dataGridClass.action',
                fit : true,
                singleSelect:true,
//                fitColumns : true,
                pagination : true,
                border : false,
                striped : true,
                rownumbers : true,
                idField : 'deptId',

                columns:[[
                    /*{
                     field : 'deptId',
                     checkbox : true
                     },*/
                    {
                        field:'adminIds',
                        hidden:true
                    },
                    {
                        field:'fullName',
                        hidden:true
                    },
                    {
                        width:'20',
                        align:'center',
                        field:'hasAdmin',
                        formatter:function(value,row)
                        {
                            var adminNames = row.adminNames ;
                            var str = "<img src='${pageContext.request.contextPath}/images/icons/star.png' width='15' height='15' style='margin-top:3px;'>";
                            var adminIds = row.adminIds ;
                            if(adminIds == null || $.trim(adminIds) == '') {
                                str = "" ;
                            }
                            return str ;
                        }
                    },
                    {
                        width:'150',
                        title:'学校名称',
                        field:'schoolName',
                        formatter:function(value,row)
                        {
                            var str = '';
                            var school = row.schoolName ;
                            if(index == 0 || school != lastSchool) {
                                str = school ;
                                lastSchool = school ;
                                lastCollege = null ;
                                lastGrade = null ;
                            }
                            index++;
                            return str ;
                        }
                    },
                    {
                        width:'150',
                        title:'学院名称',
                        field:'collegeName',
                        formatter:function(value,row)
                        {
                            var str = '';
                            var college = row.collegeName ;
                            if(index == 0 || college != lastCollege) {
                                str = college ;
                                lastCollege = college ;
                                lastGrade = null ;
                            }
                            index++;
                            return str ;
                        }
                    },
                    {
                        width:'150',
                        title:'年级',
                        field:'gradeName',
                        formatter:function(value,row)
                        {
                            var str = '';
                            var grade = row.gradeName ;
                            if(index == 0 || grade != lastGrade) {
                                str = grade ;
                                lastGrade = grade ;
                            }
                            index++;
                            return str ;
                        }
                    },
                    {
                        width:'150',
                        title:'班级名称',
                        field:'deptName'
                    },
                    {
                        width:'150',
                        title:'管理员名称',
                        field:'adminNames'
                    },
                    {
                        width:'200',
                        title:'操作',
                        field:'action',
                        formatter:function(value,row)
                        {
                            var deptId = row.deptId ;
                            var fullName = row.fullName ;
                            var str ;
                            str = '<a href="javascript:void(0)" onclick="setClassManager(\''+deptId+'\',\''+fullName+'\')">' +
                                '<img class="iconImg ext-icon-note" />指定为班级管理员</a>&nbsp;';
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

        /**
         * 设置班级管理员
         * @param deptId
         */
        function setClassManager(deptId,deptName)
        {
            var id = deptId ;
            var title = "设置班级管理员 ->" + deptName ;
            var dialog = parent.modalDialog({
                title : title,
                iconCls : 'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/deptInfo/userInfoList.jsp?classId=' + id ,
                buttons : [ {
                    text : '指定管理员',
                    iconCls : 'ext-icon-save',
                    handler : function()
                    {
                        dialog.find('iframe').get(0).contentWindow.updateClassAdmin(dialog, deptDataGrid, parent.$, '1');
                    }
                },{
                    text : '取消管理员',
                    iconCls : 'ext-icon-save',
                    handler : function()
                    {
                        dialog.find('iframe').get(0).contentWindow.updateClassAdmin(dialog, deptDataGrid, parent.$, '0');
                    }
                } ]
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


        /**
         * 重置
         */
        function resetT(){
            $('#schoolName').val('');
            $('#collegeName').val('');
            $('#gradeName').val('');
            $('#className').val('');
            $('#adminNames').val('');

            $('#school').combobox('clear');
            $('#depart').combobox('clear');
            $('#grade').combobox('clear');
            $('#classes').combobox('clear');
        }
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

													$('#gradeName').prop('value','');
													$('#className').prop('value','');
													<%--$('#gradeId').prop('value','');
													$('#classId').prop('value','');--%>
									                }
									            }],
												onSelect: function(rec){
													var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
													$('#classes').combobox('clear');
													$('#classes').combobox('reload', url);

													$('#gradeName').prop('value',rec.deptName);
													$('#className').prop('value','');
													<%--$('#gradeId').prop('value',rec.deptId);
													$('#classId').prop('value','');--%>
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

													$('#className').prop('value','');
													<%--$('#classId').prop('value','');--%>
									                }
									            }],
												onSelect: function(rec){
												    $('#className').prop('value',rec.deptName);
													<%--$('#classId').prop('value',rec.deptId);--%>
												}
												"/>
                            </td>
                           <%-- <th align="right">管理员姓名</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="adminNames" name="queryMap.adminNames" style="width: 130px;" />
                            </td>--%>
                            <td colspan="3" align="right">
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchDept();">查询</a>
                                <a href="javascript:void(0)" class="easyui-linkbutton"
                                   data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true"
                                   onclick="resetT();">重置</a>
                            </td>
                        </tr>
                        <%--
                        <tr>
                            <th align="right">学校名称</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="schoolName" name="queryMap.schoolName" style="width: 150px;" />
                            </td>
                            <th align="right">学院名称</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="collegeName" name="queryMap.collegeName" style="width: 150px;" />
                            </td>
                            <th align="right">年级名称</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="gradeName" name="queryMap.gradeName" style="width: 150px;" />
                            </td>
                        </tr>

                        <tr>
                            <th align="right">班级名称</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="className" name="queryMap.className" style="width: 150px;" />
                            </td>
                            <th align="right">管理员名称</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="adminNames" name="queryMap.adminNames" style="width: 150px;" />
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
                        --%>

                    </table>
                </form>
            </td>
        </tr>
    </table>
</div>

<div data-options="region:'center', fit:true,border:false">
    <table id="deptDataGrid" data-options="fit:true,border:false"></table>
</div>
</body>


</html>

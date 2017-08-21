<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
        /* 初始化左树 */
        var deptTree;

        function clearHiddenInput(){
            $("#dept_id").val("");
//            $("#dept_schoolId").val("");
//            $("#dept_departId").val("");
//            $("#dept_gradeId").val("");
//            $("#dept_classId").val("");
        }

        $(function() {
            deptTree = $('#deptTree').tree({
                url : '${pageContext.request.contextPath}/deptInfo/deptInfoAction!getDeptAttrTreeByUserDept.action',
                state: closed,
                animate : true,
                onClick : function(node){
                    // 清除表单中所有筛选条件
                    clearHiddenInput();// 隐藏条件
                    resetT();// 可见条件

                    var params = {};
                    $("#dept_pid").val(node.id) ;

                    searchDept();
                },
                onBeforeLoad : function(node, param) {
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                onLoadSuccess : function(node, data){
                    parent.$.messager.progress('close');
                }
            });
        });

        /* 初始化右表 */
        var deptInfoGrid;
        var collegeId ;
        var isCurrent ;
        var belongDeptId ;
        var belongDeptName ;
        $(function() {
            deptInfoGrid = $('#deptInfoGrid').datagrid({
                url:'${pageContext.request.contextPath}/dept/deptAction!getDeptGrid.action',
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'deptId',

                columns:[[
                    {
                        width:'150',
                        title:'上级机构编码',
                        hidden: true ,
                        field:'parentId'
                    },
                    {
                        width:'200',
                        title:'机构名称',
                        field:'deptName'
                    },
                    {
                        width:'250',
                        title:'院系机构路径',
                        field:'fullName'
                    },
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
                        width:'120',
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
                        width:'120',
                        title:'排序序号',
                        field:'sort'
                    },
                    {
                        width:'240px',
                        title:'操作',
                        field:'action',
                        formatter:function(value,row)
                        {
                            var deptId = row.deptId ;
                            var parentId = row.parentId ;
                            var deptName = row.deptName ;
                            var fullName = row.fullName ;
                            var str = '';
                            var length = 0 ;
                            if(deptId != undefined) {
                                length = deptId.length ;
                            }
                            <%--<authority:authority authorizationCode="查看机构详情" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="showDeptInfo(\''+deptId+'\');">' +
                                        '<img class="iconImg ext-icon-note" />查看</a>&nbsp;';
                            </authority:authority>--%>
                            <authority:authority authorizationCode="删除机构" userRoles="${sessionScope.user.userRoles}">
                            str += '<a href="javascript:void(0)" onclick="removeDeptInfo(\''+deptId+'\');">' +
                                    '<img class="iconImg ext-icon-note_delete" />删除</a>';
                            </authority:authority>
                            if(length != 14){
                                <authority:authority authorizationCode="编辑机构" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="editDeptName(\''+deptId+'\',\''+parentId+'\');">' +
                                        '<img class="iconImg ext-icon-note" />编辑</a>&nbsp;';
                                </authority:authority>
                            }
                            if (length < 16) {
                                <authority:authority authorizationCode="新增机构" userRoles="${sessionScope.user.userRoles}">
                                    str += '<a href="javascript:void(0)" onclick="add(\''+deptId+'\');">' +
                                        '<img class="iconImg ext-icon-note_add" />添加</a>';
                                </authority:authority>
                            }
                            if(length > 14) {
                                <authority:authority authorizationCode="编辑机构" userRoles="${sessionScope.user.userRoles}">
                                str += '<a href="javascript:void(0)" onclick="moveUserInfoFromDeptToOtherDept(\''+deptId+'\',\''+fullName+'\');">' +
                                        '<img class="iconImg ext-icon-note" />迁移校友数据</a>&nbsp;';
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
                    $('.iconImg').attr('src',pixel_0);
                    parent.$.messager.progress('close');

                }
            });
        });


        /**
         * 迁移班级下校友数据到另一个班级下
         */
        function moveUserInfoFromDeptToOtherDept(deptId,deptName)
        {

            //2. 提示用户是否要删除
            parent.$.messager.confirm('确认','确定要转移当前班级「'+deptName+'」下所有校友数据?',function(r)
            {
//                alert("r= "+r); // 弹出的对话框,点击"确定":r=true; 点击"取消":r=false
                //如果点击"确定", r= true ajax请求,进入deptInfoAction/deleteDeptInfo()
                if(r)
                {
                    var title = '迁移校友数据' ;
                    var dialog = parent.modalDialog({
                        title : title,
                        iconCls : 'ext-icon-note_edit',
                        url : '${pageContext.request.contextPath}/page/admin/deptInfo/moveUserInfoFromDeptToOtherDept.jsp?oldDeptId=' + deptId +'&deptName='+deptName ,
                        buttons : [ {
                            text : '监测数据',
                            iconCls : 'ext-icon-save',
                            handler : function()
                            {
                                dialog.find('iframe').get(0).contentWindow.submitForm(dialog, deptInfoGrid, parent.$,this);
                            }
                        } ]
                });

                }

            });
            //3. 删除

        }



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
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, deptInfoGrid, parent.$);
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
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, deptInfoGrid, parent.$);
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
                                deptInfoGrid.datagrid('reload');
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
         * 条件查询
         */
        function searchDept(){
            $('#deptInfoGrid').datagrid('load', serializeObject($('#searchForm')));
        }

        /**
         * 清除查询条件
         */
        function resetT(){
            $('#dept_belongDeptId').combobox('clear');
            $('#dept_isCurrent').combobox('clear');
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
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, deptInfoGrid, parent.$);
                    }
                } ]
            });
        }
    </script>
</head>

<body class="easyui-layout" data-options="fit:true,border:false" >
    <%-- left tree --%>
    <div data-options="region:'west',border:1" width="18%">
        <ul id="deptTree"></ul>
    </div>

    <%-- right up searchbar --%>
    <div id="toolbar">
        <table>
            <tr>
                <td>
                    <form id="searchForm">
                        <input id="dept_pid" name="dept.deptPid" type="hidden"/>

                        <table>
                            <tr>
                                <th align="right">是否现有</th>
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
                                <th align="right">现有机构</th>
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
                    <authority:authority userRoles="${sessionScope.user.userRoles}" authorizationCode="院系导入">
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-import_customer',plain:true" onclick="importFun();" style="margin-left: 5px; margin-top: 1px;">院系导入</a>
                    </authority:authority>
                </td>
            </tr>
        </table>
    </div>

    <%-- right down table --%>
    <div  data-options="region:'center',fit:false,border:false" width="82%">
        <table id="deptInfoGrid"></table>
    </div>
</body>


</html>

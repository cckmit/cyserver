<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">

    <title>主页-统计</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <%
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        String easyuiTheme = "metro-blue";//指定如果用户未选择样式，那么初始化一个默认样式
        if (cookieMap.containsKey("easyuiTheme")) {
            Cookie cookie = (Cookie) cookieMap.get("easyuiTheme");
            easyuiTheme = cookie.getValue();
        }
    %>
    <link href="${pageContext.request.contextPath}/jslib/easyui/themes/<%=easyuiTheme%>/easyui.css" rel="stylesheet" type="text/css">

    <link href="${pageContext.request.contextPath}/css/icon.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/jslib/easyui/themes/icon.css" rel="stylesheet" type="text/css">

    <style type="text/css">
        *{
            margin: 0;
            padding: 0;
            list-style: none;
        }

        body{
            margin: 0;
            padding: 0;
            background-color: #ffffff;
        }

        ul,li {
            position: relative;
            list-style: none;
            display: block;
        }

        .mainWrap {
            position: relative;
            width: 100%;
            height: 100%;
            border: 0;
            border-collapse: separate;
            border-spacing: 10px;
            vertical-align: top;
        }
        .mainWrap td.panel,
        .mainWrap td.panel10 {
            position: relative;
            overflow: hidden;
            border: solid 1px #C3D9E0;
            vertical-align: top;
        }

        .panel10 {
            padding: 10px;
        }

        .wh60 {
            width: 60%;
            height: 100%;
        }

        .wh40 {
            width: 40%;
            height: 100%;
        }

        .wh100 {
            width: 100%;
            height: 100%;
        }

        .cy-tabs {
            margin: 0;
            padding: 0;
            width:100%;
        }

        .cy-tabs>ul {
            list-style: none;
            margin: 0;
            padding: 0;
        }
        .cy-tabs>ul>li {
            float: left;
            line-height: 24px;
            margin-bottom: -1px;
            margin-right: 5px;
            padding: 0 10px;
            font-size: 12px;
            border: 1px solid #C3D9E0;
            border-bottom: none;
            z-index: 2;
        }

        .tab-nav {
            background-color: #EEF5FF;
            cursor:pointer;
        }

        .tab-nav-action {
            background-color: #FFFFFF;
            cursor:pointer;
        }

        .tabs-body {
            float: left;
            width: 100%;
            position: relative;
        }

        .tabs-body .tabs-content {
            position: relative;
            height: 534px;
            padding:10px;
            border: 1px solid #C3D9E0;
            overflow: hidden;
            z-index: 1;
        }
    </style>

    <!-- jquery文件引入 -->
    <script src="${pageContext.request.contextPath}/jslib/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jslib/ExtJquery.js"></script>
    <!-- easyui文件引入 -->
    <script src="${pageContext.request.contextPath}/jslib/easyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/jslib/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <!-- My97DatePicker文件引入 -->
    <script src="${pageContext.request.contextPath}/jslib/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <!-- ECharts文件引入 -->
    <script src="${pageContext.request.contextPath}/jslib/echarts/dist/echarts.js"></script>
    <script src="${pageContext.request.contextPath}/page/admin/analysis/js/userInfoMapChart.js"></script>
    <script src="${pageContext.request.contextPath}/page/admin/analysis/js/deptUserChart.js"></script>
    <script src="${pageContext.request.contextPath}/page/admin/analysis/js/authUserChart.js"></script>
    <script src="${pageContext.request.contextPath}/page/admin/analysis/js/alumniMapChart.js"></script>
    <script src="${pageContext.request.contextPath}/page/admin/analysis/js/miningChart.js"></script>
    <script src="${pageContext.request.contextPath}/page/admin/analysis/js/userInfoSummayChart.js"></script>
    <script src="${pageContext.request.contextPath}/page/admin/analysis/js/alumniAnaysMapChart.js"></script>

    <script type="text/javascript">
        var main4Loaded = false;

        var actionName = "analysisAction";
        var actionUrl = "${pageContext.request.contextPath}/analysis/";
        var actionFullPath = actionUrl + actionName;
        var grid;

        $(function(){
            userInfoMapEchart("main1");
            miningEchart("main2");
            userInfoSummayEchart("main3");
            $("#cy-tabs li").bind("click", function () {
                var index = $(this).index();
                var divs = $("#tabs-body > div");
                $(this).parent().children("li").attr("class", "tab-nav");//将所有选项置为未选中
                $(this).attr("class", "tab-nav-action"); //设置当前选中项为选中样式
                divs.hide();//隐藏所有选中项内容
                divs.eq(index).show(); //显示选中项对应内容
                if(index == 1 && !main4Loaded){
                    alumniCountAnaysMapEchart("main4");
                    main4Loaded = true;
                }
            });

            // dataGrid表格
            grid=$('#dataGrid').datagrid({
                url: actionFullPath + '!doNotNeedSecurity_countAnalysisUserInfoDataGrid.action',
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'schoolName',title:'学校',width:30,align:'center'},
                    {field:'collegeName',title:'学院',width:30,align:'center'},
                    {field:'gradeName',title:'年级',width:30,align:'center'},
                    {field:'className',title:'班级',width:30,align:'center'},
                    {field:'majorName',title:'专业',width:30,align:'center'},
//		        {field:'total',title:'正式校友数',width:30,align:'center'},
                    {field:'checkFlagCount',title:'正式校友数',width:30,align:'center'},
                    {field:'authCount',title:'被认证校友数',width:30,align:'center'},
                    {field:'miningCount',title:'被挖掘校友数',width:30,align:'center'}
                ]],
                toolbar : '#toolbar',
                onBeforeLoad : function(param) {
//                        parent.$.messager.progress({
//                            text : '数据加载中....'
//                        });
                },
                onLoadSuccess : function(data) {
                    var groupType = $("#groupType").val() ;
                    if(groupType == '1'){
                        $("#dataGrid").datagrid("hideColumn", "collegeName"); // 设置隐藏列
                        $("#dataGrid").datagrid("hideColumn", "gradeName"); // 设置隐藏列
                        $("#dataGrid").datagrid("hideColumn", "className"); // 设置隐藏列
                        $("#dataGrid").datagrid("hideColumn", "majorName"); // 设置隐藏列
                    } else if(groupType == '2'){
                        $("#dataGrid").datagrid("showColumn", "collegeName"); // 设置隐藏列
                        $("#dataGrid").datagrid("hideColumn", "gradeName"); // 设置隐藏列
                        $("#dataGrid").datagrid("hideColumn", "className"); // 设置隐藏列
                        $("#dataGrid").datagrid("hideColumn", "majorName"); // 设置隐藏列
                    } else if(groupType == '3'){
                        $("#dataGrid").datagrid("showColumn", "collegeName"); // 设置隐藏列
                        $("#dataGrid").datagrid("showColumn", "gradeName"); // 设置隐藏列
                        $("#dataGrid").datagrid("hideColumn", "className"); // 设置隐藏列
                        $("#dataGrid").datagrid("hideColumn", "majorName"); // 设置隐藏列
                    } else if(groupType == '4'){
                        $("#dataGrid").datagrid("showColumn", "collegeName"); // 设置隐藏列
                        $("#dataGrid").datagrid("showColumn", "gradeName"); // 设置隐藏列
                        $("#dataGrid").datagrid("showColumn", "className"); // 设置隐藏列
                        $("#dataGrid").datagrid("hideColumn", "majorName"); // 设置隐藏列
                    } else if(groupType == '5'){
                        $("#dataGrid").datagrid("showColumn", "collegeName"); // 设置隐藏列
                        $("#dataGrid").datagrid("showColumn", "majorName"); // 设置隐藏列
                        $("#dataGrid").datagrid("hideColumn", "gradeName"); // 设置隐藏列
                        $("#dataGrid").datagrid("hideColumn", "className"); // 设置隐藏列
                    }

                    parent.$.messager.progress('close');
                }
            });

            initFoot();
        });

        window.onresize = function () {
            initFoot();
        }
        function initFoot(){
            var windowHeight = $(window).height() ;
            var main2Height = $("#main2").height();
            var main3Height = $("#main3").height();
            var tabsBodyHeight = $("#tabs-body").height();

//                alert("window:"+windowHeight+" main2Height:"+main2Height+" main3Height:"+main3Height+" tabsBodyHeight:"+tabsBodyHeight);
        }

        function searchData(){
            $('#dataGrid').datagrid('load',serializeObject($('#searchForm')));
        }

        /**--重置--**/
        function resetT(){

            $('#deptId').prop('value','');
            $('#groupType').prop('value','1');

            $('#school').combobox('clear');
            $('#depart').combobox('clear');
            $('#grade').combobox('clear');
            $('#classes').combobox('clear');

            $('#major').combobox('clear');
            $('#studentType').combobox('clear');
            $('#classes').combobox('loadData',[]);
            $('#grade').combobox('loadData',[]);
            $('#major').combobox('loadData',[]);
            $('#depart').combobox('loadData',[]);
            $('#searchForm')[0].reset();


            $('#schoolId').prop('value','');
            $('#departId').prop('value','');
            $('#gradeId').prop('value','');
            $('#classId').prop('value','');

            $('#name').prop('value','');
            $('#sex').combobox('clear');
            $('#location').combobox('clear');

        }

    </script>

</head>

<body>

<table class="mainWrap">
    <tr>
        <td rowspan="3" class="wh60 panel" style="padding: 0;border: none;">
            <div class="cy-tabs">
                <ul id="cy-tabs">
                    <li class="tab-nav-action">各省【校友】统计</li>
                    <li class="tab-nav">各省【校友会】统计</li>
                </ul>
            </div>
            <div id="tabs-body" class="tabs-body">
                <div class="tabs-content" style="display:block">
                    <div id="main1" style="height: 100%;"></div>
                </div>
                <div class="tabs-content" style="display:none">
                    <div id="main4" style="height: 100%;"></div>
                </div>
            </div>
        </td>
        <td style="height: 14px;"></td>
    </tr>
    <tr>
        <td class="wh40 panel10">
            <div id="main2" style="min-height: 300px;"></div>
        </td>
    </tr>
    <tr>
        <td class="wh40 panel10">
            <div id="main3" style="height: 200px;"></div>
        </td>
    </tr>
    <tr>
        <td colspan="2" class="wh100 panel">
            <div id="toolbar" style="display: none;">
                <form id="searchForm">
                    <input name="analysisMap.groupType" id="groupType" type="hidden" value="1">
                    <input name="analysisMap.deptId" id="deptId" type="hidden">
                    <input name="analysisMap.majorId" id="majorId" type="hidden">
                    <table>
                        <tr>
                            <th align="right" width="35">学校</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input name="schoolId" id="schoolId" type="hidden">
                                <input name="departId" id="departId" type="hidden">
                                <input name="gradeId" id="gradeId" type="hidden">
                                <input name="classId" id="classId" type="hidden">
                                <input id="school" class="easyui-combobox" style="width: 150px;"
                                       data-options="
                                            valueField: 'deptId',
                                            textField: 'deptName',
                                            editable:false,
                                            prompt:'--请选择--',
                                            icons:[{
                                                iconCls:'icon-clear',
                                                handler: function(e){
                                                $('#deptId').prop('value','');
                                                $('#groupType').prop('value','1');

                                                $('#school').combobox('clear');
                                                $('#depart').combobox('clear');
                                                $('#grade').combobox('clear');
                                                $('#classes').combobox('clear');
                                                $('#major').combobox('clear');
                                                $('#classes').combobox('loadData',[]);
                                                $('#grade').combobox('loadData',[]);
                                                $('#major').combobox('loadData',[]);
                                                $('#depart').combobox('loadData',[]);
                                                $('#schoolId').prop('value','');
                                                $('#departId').prop('value','');
                                                $('#gradeId').prop('value','');
                                                $('#classId').prop('value','');
                                                $('#majorId').prop('value','');
                                                }
                                            }],
                                            url: '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getDepart.action',
                                            onSelect: function(rec){
                                                var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
                                                $('#deptId').prop('value',rec.deptId);
                                                $('#groupType').prop('value','2');

                                                $('#depart').combobox('clear');
                                                $('#grade').combobox('clear');
                                                $('#classes').combobox('clear');
                                                $('#major').combobox('clear');
                                                $('#classes').combobox('loadData',[]);
                                                $('#grade').combobox('loadData',[]);
                                                $('#major').combobox('loadData',[]);
                                                $('#depart').combobox('reload', url);
                                                $('#schoolId').prop('value',rec.deptId);
                                                $('#departId').prop('value','');
                                                $('#gradeId').prop('value','');
                                                $('#classId').prop('value','');
                                                $('#majorId').prop('value','');
                                    }" />
                            </td>

                            <th align="right" width="35">院系</th>
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
                                                    $('#deptId').prop('value',$('#schoolId').val());
                                                    $('#groupType').prop('value','2');

                                                    $('#depart').combobox('clear');
                                                    $('#grade').combobox('clear');
                                                    $('#classes').combobox('clear');
                                                    $('#major').combobox('clear');
                                                    $('#classes').combobox('loadData',[]);
                                                    $('#grade').combobox('loadData',[]);
                                                    $('#major').combobox('loadData',[]);
                                                    $('#departId').prop('value','');
                                                    $('#gradeId').prop('value','');
                                                    $('#classId').prop('value','');
                                                    $('#majorId').prop('value','');
                                                }
                                            }],
                                            onSelect: function(rec){
                                                var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;
                                                var url1= '${pageContext.request.contextPath}/major/majorAction!doNotNeedSecurity_getMajor.action?deptId='+rec.deptId;
                                                $('#deptId').prop('value',rec.deptId);
                                                $('#groupType').prop('value','3');

                                                $('#grade').combobox('clear');
                                                $('#classes').combobox('clear');
                                                $('#classes').combobox('loadData',[]);
                                                $('#grade').combobox('reload', url);
                                                $('#major').combobox('clear');
                                                $('#major').combobox('reload', url1);
                                                $('#departId').prop('value',rec.deptId);
                                                $('#gradeId').prop('value','');
                                                $('#classId').prop('value','');
                                                $('#majorId').prop('value','');
                                    }" />
                            </td>

                            <th align="right" width="35">专业</th>
                            <td>
                                <div class="datagrid-btn-separator"></div>
                            </td>
                            <td>
                                <input id="major" name="majorId" class="easyui-combobox" style="width: 150px;"
                                       data-options="
                                            valueField: 'majorId',
                                            textField: 'majorName',
                                            prompt:'--请选择--',
                                            icons:[{
                                                iconCls:'icon-clear',
                                                handler: function(e){
                                                    $('#deptId').prop('value',$('#departId').val());
                                                    $('#groupType').prop('value','3');
                                                    $('#major').combobox('clear');
                                                    $('#majorId').prop('value','');
                                                }
                                            }],
                                            onSelect: function(rec){
                                                $('#majorId').prop('value',rec.majorId);
                                                $('#groupType').prop('value','5');

                                                $('#gradeId').prop('value','');
                                                $('#classId').prop('value','');
                                                $('#grade').combobox('clear');
                                            },
                                            editable:false" />
                            </td>
                            <th align="right" width="35">年级</th>
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
                                                    $('#deptId').prop('value',$('#departId').val());
                                                    $('#groupType').prop('value','3');

                                                    $('#grade').combobox('clear');
                                                    $('#classes').combobox('clear');
                                                    $('#classes').combobox('loadData',[]);
                                                    $('#gradeId').prop('value','');
                                                    $('#classId').prop('value','');
                                                }
                                            }],
                                            onSelect: function(rec){
                                                var url = '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSecurity_getByParentId.action?deptId='+rec.deptId;

                                                $('#deptId').prop('value',rec.deptId);
                                                $('#groupType').prop('value','4');

                                                $('#classes').combobox('clear');
                                                $('#classes').combobox('reload', url);
                                                $('#gradeId').prop('value',rec.deptId);
                                                $('#classId').prop('value','');

                                                $('#major').combobox('clear');
                                                $('#majorId').prop('value','');
                                    }" />
                            </td>
                            <td align="center" >
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'icon-search',plain:true"
                                   onclick="searchData();">查询</a>
                                <a href="javascript:void(0);" class="easyui-linkbutton"
                                   data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true"
                                   onclick="resetT()">重置</a>
                            </td>

                        </tr>

                        <tr>
                        </tr>
                    </table>
                </form>
            </div>
            <table id="dataGrid" style="min-height: 300px;"></table>
        </td>
    </tr>
</table>
</body>
</html>

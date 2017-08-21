<%@ page language="java" pageEncoding="UTF-8" %>


<% String accountNum = request.getParameter("accountNum"); %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../head.jsp"/>

    <title>专业信息采集</title>
</head>
<body>

<script type="text/javascript">
    $(function () {
        var nodes = [{'select': $('#schoolName'), 'url': '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getDepart.action'},
            {'select': $('#facultyName'), 'url': '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getByParentId.action?deptId='},
            {'select': $('#gradeName'), 'url': '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getByParentId.action?deptId='},
            {'select': $('#className'), 'url': '${pageContext.request.contextPath}/dept/deptAction!doNotNeedSessionAndSecurity_getByParentId.action?deptId='}];

        var closure = function(i){
            var node  = nodes[i];
            node['select'].change(function(){
                var val = $(this).val();
                $.getJSON(nodes[i+1]['url'] + val, function(items){
                    for(var j = i + 1; j < nodes.length; j++){
                        nodes[j]['select'].empty();
                    }
                    for(var j = 0; j < items.length; j++){
                        nodes[i+1]['select'].append('<option value="' + items[j]['deptId'] + '">' + items[j]['deptName'] + '</option>');
                    }
                    nodes[i+1]['select'].trigger('change');
                });
            });
        }

        for(var i = 0; i < nodes.length - 1; i++){
            closure(i); //closure to keep i value
        }

        $.getJSON(nodes[0]['url'], function(items){
            for(var i = 0; i < items.length; i++){
                nodes[0]['select'].append('<option value="' + items[i]['deptId'] + '">' + items[i]['deptName'] + '</option>')
            }
            nodes[0]['select'].trigger('change');
        });


    });

    function subResult() {
        var schoolName = document.getElementById('schoolName');
        var facultyName = document.getElementById('facultyName');
        var gradeName = document.getElementById('gradeName');
        var specialtyName = document.getElementById('specialtyName');
        var className = document.getElementById('className');

        if (schoolName.value == '') {
            alert("请输入学校名称");
            return;
        }
        else if (facultyName.value == '') {
            alert("请输入院系名称");
            return;
        }
        else if (gradeName.value == '') {
            alert("请输入年级");
            return;
        }
        else if (specialtyName.value == '') {
            alert("请输入专业名称");
            return;
        }
        else if (className.value == '') {
            alert("请输入班级");
            return;
        }
        else {

            $.ajax({
                url: '${pageContext.request.contextPath}/majormng/majorMngAction!doNotNeedSessionAndSecurity_specialtyCollectionForApp.action',
                data: $('#subForm').serialize(),
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        alert(data.msg);
                    }
                    else {
                        alert(data.msg);
                    }
                }

            });

        }


    }

</script>

<div id="posts">
    <form id="subForm">
        <div id="header">
            <h1>补充班级信息</h1>
            <a href="javascript:void(0);" onclick="subResult();" class="btn-post">提交</a>
        </div>
        <div class="class-info-select first-row">
            <h2>请输入学校名称</h2>
            <select name="formData.schoolName" id="schoolName" style="width: 150px;" maxlength="120"></select>
        </div>
        <div class="class-info-select">
            <h2>请输入院系名称</h2>
            <select name="formData.facultyName" id="facultyName" style="width: 150px;" maxlength="120"></select>
</div>
<div class="class-info-select">
    <h2>请选择您就读时年级</h2>
    <select name="formData.gradeName" id="gradeName" style="width: 150px;" maxlength="120"></select>
</div>
<div class="class-info-select">
    <h2>请选择您就读时的班级</h2>
    <select name="formData.className" id="className" style="width: 150px;" maxlength="120"></select>
</div>
<div class="class-info-select">
    <h2>请输入专业名称</h2>
    <input name="formData.specialtyName" id="specialtyName" style="width: 150px;" maxlength="120"/>
</div>
<input type="hidden" value="<%= accountNum %>" name="formData.accountNum"/>
</form>
</div>

</body>
</html>
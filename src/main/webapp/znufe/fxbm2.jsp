<%@ page language="java" import="java.util.*,com.cy.system.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//只有财大才能访问该页面
String deptNo = Global.deptNo;
if(!deptNo.equals("000090")){
	//return;
}


%>




<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>财大返校报名</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/jumbotron-narrow.css" rel="stylesheet">
    <!--[if lt IE 9]><script src="js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.min.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
    
    <jsp:include page="../inc.jsp"></jsp:include>
    <script>
    
		function searchFun(){
				$('#cc').combogrid('grid').datagrid('load',serializeObject($('#searchForm')));
		}
		
		function saveTo(){
			var namex = $("#namex").val();
			$("#school").val($("#schoolName").find("option:selected").text());
			$("#depart").val($("#facultyName").find("option:selected").text());
			$("#grade").val($("#gradeName").find("option:selected").text());
			$("#clazz").val($("#className").find("option:selected").text());
			
			if(namex == null || namex == ""){
				alert("请填写姓名");
			}else{
				if($("#save").hasClass("active")){
					alert("请不要重复提交");
				}else{
					$("#save").addClass("active");
					$("#form").attr("action","<%=path%>/znufe/znufeAction!doNotNeedSessionAndSecurity_registerSave.action");
					$("#form").submit();
				}
			}
		}
		
		
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
	                nodes[0]['select'].append('<option value="' + items[i]['deptId'] + '">' + items[i]['deptName'] + '</option>');
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
    
  </head>

  <body>

    <div class="container">
      <div class="page-header">
        <h1>
          返校报名
        </h1>
      </div>

      <div class="jumbotron">
        <h1>感谢您，亲爱的校友</h1>
        <p class="lead">欢迎您进行返校报名，中南财经政法大学校友会非常感谢您！</p>
      </div>

      <form id="form" class="form-horizontal" method="post">

        <div class="form-group">
          <label for="name" class="col-sm-2 control-label">姓名</label>
          <div class="col-sm-10"> 
				<input id="namex" name="register.x_name" value="" />
          </div>
        </div>
        
        <div class="form-group" id="sex">
          <label for="sex" class="col-sm-2 control-label">性别</label>
          <div class="col-sm-10">
            <select name="register.x_sex">
            	<option value="男">男</option>
            	<option value="女">女</option>
            </select>
          </div>
        </div>
		
		<div class="form-group">
          <label for="sex" class="col-sm-2 control-label">学校</label>
          <div class="col-sm-10">
            <select id="schoolName" style="width: 150px;" maxlength="120"></select>
          	<input id="school" name="register.x_school" type="hidden" />
          </div>
        </div>

        <div class="form-group">
          <label for="" class="col-sm-2 control-label">院系</label>
          <div class="col-sm-10">
            <select id="facultyName" style="width: 150px;" maxlength="120"></select>
          	<input id="depart" name="register.x_depart" type="hidden" />
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">年级</label>
          <div class="col-sm-10">
            <select id="gradeName" style="width: 150px;" maxlength="120"></select>
          	<input id="grade" name="register.x_grade" type="hidden" />
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">班级</label>
          <div class="col-sm-10">
            <select  id="className" style="width: 150px;" maxlength="120"></select>
          	<input id="clazz" name="register.x_clazz" type="hidden" />
          </div>
        </div>
        
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">联系电话</label>
          <div class="col-sm-10">
            <input id="telId" name="register.x_phone" type="text"></input>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">邮箱</label>
          <div class="col-sm-10">
            <input id="email" name="register.x_email" type="text"></input>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">联系地址</label>
          <div class="col-sm-10">
            <input id="mailingAddress" name="register.x_address" type="text"></input>
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">是否参观校史馆</label>
          <div class="col-sm-10">
            <select name="register.visitHistoryMuseum" style="width: 155px;" data-options="editable:false">
            	<option value="true">是</option>
            	<option value="false">否</option>
            </select>
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">是否参观博物馆</label>
          <div class="col-sm-10">
            <select name="register.visitMoneyMuseum" style="width: 155px;" data-options="editable:false">
            	<option value="true">是</option>
            	<option value="false">否</option>
            </select>
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">是否游园</label>
          <div class="col-sm-10">
            <select name="register.visitCollege" style="width: 155px;" data-options="editable:false">
            	<option value="true">是</option>
            	<option value="false">否</option>
            </select>
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">是否自驾车</label>
          <div class="col-sm-10">
            <select name="register.selfDrive" style="width: 155px;" data-options="editable:false">
            	<option value="true">是</option>
            	<option value="false">否</option>
            </select>
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">车牌号</label>
          <div class="col-sm-10">
            <input name="register.licensePlate" class="easyui-validatebox"/>
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">是否有家人随行</label>
          <div class="col-sm-10">
            <select name="register.withFamily" style="width: 155px;" data-options="editable:false">
            	<option value="true">是</option>
            	<option value="false">否</option>
            </select>
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">成人人数</label>
          <div class="col-sm-10">
            <input name="register.adultNumber" class="easyui-validatebox" data-options="validType:'tel'" />
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">儿童数</label>
          <div class="col-sm-10">
            <input name="register.childrenNumber" class="easyui-validatebox" data-options="validType:'tel'" />
          </div>
        </div>

        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <button id="save" onclick="saveTo();" type="button" class="btn btn-primary">提交</button>
          </div>
        </div>
      </form>

      <footer class="footer">
        <p>&copy; 中南财经政法大学 2015</p>
      </footer>

    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
    
    
    <div id="toolbar" style="display: none;">
			<table>
				<tr>
					<td>
						<form id="searchForm">
							<table>
								<tr>
									<th>
										姓名：
									</th>
									<td>
										<input name="userInfo.userName" style="width: 150px;" />
									</td>
									<th>
										电话号码：
									</th>
									<td>
										<input name="userInfo.telId" style="width: 150px;" />
									</td>
									<td>
										<a href="javascript:void(0);" class="easyui-linkbutton"
											data-options="iconCls:'ext-icon-zoom',plain:true"
											onclick="searchFun();">查询</a>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</div>
    
  </body>
</html>

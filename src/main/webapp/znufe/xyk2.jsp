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
    <title>财大校友卡申请</title>
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
					$("#form").attr("action","<%=path%>/znufe/znufeAction!doNotNeedSessionAndSecurity_alumniCardSave.action");
					$("#form").submit();
				}
			}
		}
		
		$(function() {
			uploadPic("#personal_upload_button", "formData1.personalPic", "#personalPic");
			uploadPic("#credentials_upload_button", "formData1.credentialsPic", "#credentialsPic");
		});
	
	
		function uploadPic(upload_button_name, picName, picDivName)
		{
			var button = $(upload_button_name), interval;
			new AjaxUpload(button, 
			{
				action : '/cy_v1/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
				name : 'upload',
				onSubmit : function(file, ext) 
				{
					if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext))) 
					{
						$.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
						return false;
					}
					$.messager.progress({text : '图片正在上传,请稍后....'});
				},
				onComplete : function(file, response) 
				{
					$.messager.progress('close');
					var resp = $.parseJSON(response);
					
					if (resp.error == 0) 
					{
						$(picDivName).append(
							'<div style="float:left;width:180px;">'+
							'<img src="'+resp.url+'" width="150px" height="150px"/>'+
							'<div class="bb001" onclick="removePic(this,\'' + upload_button_name + '\')">'+
							'</div>'+
							'<input type="hidden" name="'+picName+'" value="'+resp.url+'"/></div>'
						);
	
						
						$(upload_button_name).prop('disabled', 'disabled');
					} 
					else 
					{
						$.messager.alert('提示', msg.message, 'error');
					}
				}
			});
		
		}

		function removePic(pic, upload_button_name) 
		{
			$(pic).parent().remove();
			$(upload_button_name).prop('disabled', false);
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
          校友卡申请
        </h1>
      </div>

      <div class="jumbotron">
        <h1>感谢您，亲爱的校友</h1>
        <p class="lead">欢迎您进行校友卡申请，中南财经政法大学校友会非常感谢您！</p>
      </div>

      <form id="form" class="form-horizontal" method="post">

        <div class="form-group">
          <label for="name" class="col-sm-2 control-label">姓名</label>
          <div class="col-sm-10"> 
            <input id="namex" name="formData1.x_name" value="" />
          </div>
        </div>
        
        
        <div class="form-group" id="sex">
          <label for="sex" class="col-sm-2 control-label">性别</label>
          <div class="col-sm-10">
            <select name="formData1.x_sex">
            	<option value="男">男</option>
            	<option value="女">女</option>
            </select>
          </div>
        </div>

        <div class="form-group">
          <label for="sex" class="col-sm-2 control-label">学校</label>
          <div class="col-sm-10">
            <select id="schoolName" style="width: 150px;" maxlength="120"></select>
          	<input id="school" name="formData1.x_school" type="hidden" />
          </div>
        </div>

        <div class="form-group">
          <label for="" class="col-sm-2 control-label">院系</label>
          <div class="col-sm-10">
            <select id="facultyName" style="width: 150px;" maxlength="120"></select>
          	<input id="depart" name="formData1.x_depart" type="hidden" />
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">年级</label>
          <div class="col-sm-10">
            <select id="gradeName" style="width: 150px;" maxlength="120"></select>
          	<input id="grade" name="formData1.x_grade" type="hidden" />
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">班级</label>
          <div class="col-sm-10">
            <select  id="className" style="width: 150px;" maxlength="120"></select>
          	<input id="clazz" name="formData1.x_clazz" type="hidden" />
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">联系电话</label>
          <div class="col-sm-10">
            <input id="telId" name="formData1.x_phone" type="text"></input>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">邮箱</label>
          <div class="col-sm-10">
            <input id="email" name="formData1.x_email" type="text"></input>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">联系地址</label>
          <div class="col-sm-10">
            <input id="mailingAddress" name="formData1.x_address" type="text"></input>
          </div>
        </div>
        
        

        <div class="form-group">
          <label for="" class="col-sm-2 control-label">建议</label>
          <div class="col-sm-10">
            <textarea name="formData1.suggest" rows="10" cols="50"></textarea>
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">取卡方式</label>
          <div class="col-sm-10">
            <select name="formData1.takeWay" style="width:155px" 
					data-options="required:true, editable:false">
				<option value="自取">自取</option>
				<option value="邮寄">邮寄</option>
			</select>
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">个人照片</label>
          <div class="col-sm-10">
            <input type="button" id="personal_upload_button" value="上传个人照片" />
          </div>
          <div id="personalPic"></div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">证件照片</label>
          <div class="col-sm-10">
            <input type="button" id="credentials_upload_button" value="上传证件照片" />
          </div>
          <div id="credentialsPic"></div>
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

    </div>
    
    <!-- /container -->
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
    
  </body>
</html>

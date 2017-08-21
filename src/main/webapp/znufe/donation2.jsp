<%@ page language="java" import="java.util.*,com.cy.system.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//只有财大才能访问该页面
%>




<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>中南财经政法大学校友捐赠</title>
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
			var money = $("#money").val();
			var projectId = $("#projectId").combobox("getValue");
			var userId = $("#userId").val();
			var namex = $("#namex").val();
			var telId=$("#telId").val();
			var email=$("#email").val();
			if(telId == null || telId == ""){
				alert("请填写电话号码");
				return;
			} 
			if(email == null || email == ""){
				alert("请填写电子邮箱");
				return;
			} 
			if(money == null || money == ""){
				alert("金额不能为空");
			}else if(isNaN(money) || money <= 0){
				alert("金额必须为大于0的合法数字");
			}else if(projectId == null || projectId == ""){
				alert("请选择捐赠项目");
			}else if( (userId == null || userId == "")&&(namex==null || namex=="") ){
				alert("请填写姓名");
			}else{
				$("#form").attr("action","<%=path%>/znufe/znufeAction!doNotNeedSessionAndSecurity_donationSave.action");
				$("#form").submit();
			}
		}
		
		function changeA(){
        	if($('#anonymous').is(':checked')){
        		$('#anonymous0').prop('value','1')
        	}else{
        		$('#anonymous0').prop('value','0')
        	}
        }
		
    </script>
    
  </head>

  <body>

    <div class="container">
      <div class="page-header">
        <h1>
          校友捐赠
        </h1>
      </div>

      <div class="jumbotron">
        <h1>感谢您，亲爱的校友</h1>
        <p class="lead">欢迎您进行网上捐赠，中南财经政法大学校友会非常感谢您的捐赠！</p>
      </div>

      <form id="form" class="form-horizontal" method="post">
      	<input name="donation.flag" type="hidden"  value="0">
        <div class="form-group">
          <label for="payMoney" class="col-sm-2 control-label">支付金额</label>
          <div class="col-sm-10">
            <input type="text" class="easyui-validatebox" name="donation.money"  id="money" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" id="payMoney" placeholder="请填写数字">
         	<input class="aweform" type="hidden" name="donation.payMethod" value="网站"/>
          </div>
        </div>
        <div class="form-group">
          <label for="project" class="col-sm-2 control-label">捐赠项目</label>
          <div class="col-sm-10">
            <input id="projectId" name="donation.projectId" class="easyui-combobox" style="width: 300px;"
				data-options="editable:false,
				required:true,
				valueField: 'projectId',
				textField: 'projectName',
				url: '<%=path%>/project/projectAction!doNotNeedSessionAndSecurity_getAll.action'"/>
          </div>
        </div>
        <div class="form-group">
          <label for="address" class="col-sm-2 control-label">捐赠留言</label>
          <div class="col-sm-10">
            <input type="text" name="donation.message" id="message" class="form-control">
          </div>
        </div>
        <div class="form-group">
          <label for="address" class="col-sm-2 control-label">备注</label>
          <div class="col-sm-10">
            <input type="text" id="remark" name="donation.remark"  class="form-control"> 
          </div>
        </div>
        <div class="form-group">
            <label for="anonymous" class="col-sm-2 control-label">匿名显示</label>
            <div class="col-sm-10">
               <input class="aweform" type="checkbox" id="anonymous"  onclick="changeA()"/>
               <input class="aweform" type="hidden" id="anonymous0" name="donation.anonymous"/>
            </div>
        </div>
        <div class="form-group" id="name2" >
          <label for="name" class="col-sm-2 control-label">姓名</label>
          <div class="col-sm-10">
          <input id="namex" name="donation.x_name" value="" class="easyui-validatebox" data-options="required:true"/>
          </div>
       	</div>

        <div class="form-group" id="sex">
          <label for="sex" class="col-sm-2 control-label">性别</label>
          <div class="col-sm-10">
            <select name="donation.x_sex">
            	<option value="男">男</option>
            	<option value="女">女</option>
            </select>
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">联系电话</label>
          <div class="col-sm-10">
            <input id="telId" name="donation.x_phone" type="text" class="easyui-validatebox" data-options="required:true"></input>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">电子邮箱</label>
          <div class="col-sm-10">
            <input id="email" name="donation.x_email" type="text" class="easyui-validatebox" data-options="required:true"></input>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">联系地址</label>
          <div class="col-sm-10">
            <input id="mailingAddress" name="donation.x_address" type="text"></input>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">工作单位</label>
          <div class="col-sm-10">
            <input id="workunit" name="donation.x_workunit" type="text"></input>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">职务</label>
          <div class="col-sm-10">
            <input id="position" name="donation.x_position" type="text"></input>
          </div>
        </div>

        <div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <button id="save" onclick="saveTo();" type="button" class="btn btn-primary">马上捐赠</button>
          </div>
        </div>
        
      </form>

      <footer class="footer">
        <p>&copy; 中南财经政法大学 2015</p>
      </footer>

    </div> <!-- /container -->


    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>

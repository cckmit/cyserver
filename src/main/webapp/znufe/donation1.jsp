<%@ page language="java" import="java.util.*,com.cy.system.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
    	var query_user_url = "<%=path %>/userInfo/userInfoAction!doNotNeedSessionAndSecurity_dataGridFor.action";
		
		function searchFun(){
				$('#cc').combogrid('grid').datagrid('load',serializeObject($('#searchForm')));	
		}

		function saveTo(){
			var money = $("#money").val();
			var projectId = $("#projectId").combobox("getValue");
			var userId = $("#userId").val();
			var telId=$("#telId").val();
			var email=$("#email").val();
			if(money == null || money == ""){
				alert("金额不能为空");
				return;
			} 
			if(isNaN(money) || money <= 0){
				alert("金额必须为大于0的合法数字");
				return;
			}
			if(!/^(\d+\.\d{1,2}|\d+)$/.test(money)){
				alert("请输入正确的金额");
				return;
			}
			if(projectId == null || projectId == ""){
				alert("请选择捐赠项目");
				return;
			}
			if(telId == null || telId == ""){
				alert("请填写电话号码");
				return;
			} 
			if(email == null || email == ""){
				alert("请填写电子邮箱");
				return;
			} 
			if($('#auto').is(":visible")){
				if( (userId == null || userId == "")){
					alert("请选择姓名");
					return;
				}
			}else{
				var x_name=$('#x_name').val();
				var x_school=$('#x_school').val();
				var x_depart=$('#x_depart').val();
				var x_grade=$('#x_grade').val();
				var x_clazz=$('#x_clazz').val();
				var x_major=$('#x_major').val();
				if(x_name==null||x_name==''){
					alert("请填写姓名");
					return;
				}
				if(x_school==null||x_school==''){
					alert("请填写学校名称");
					return;
				}
				if(x_depart==null||x_depart==''){
					alert("请填写院系名称");
					return;
				}
				if(x_grade==null||x_grade==''){
					alert("请填写年级名称");
					return;
				}
				if(x_clazz==null||x_clazz==''){
					alert("请填写班级名称");
					return;
				}
				if(x_major==null||x_major==''){
					alert("请填写专业名称");
					return;
				}
			}
			
			$("#form").attr("action","<%=path%>/znufe/znufeAction!doNotNeedSessionAndSecurity_donationSave.action");
			$("#form").submit();
		}
    
    	function changeInput(){
    		$('#auto').hide();
    		$('#sd').show();
    		$('#x_name').prop('value','');
    		$('#x_school').prop('value','');
    		$('#x_depart').prop('value','');
    		$('#x_grade').prop('value','');
    		$('#x_clazz').prop('value','');
    		$('#x_major').prop('value','');
    		$('#telId').prop('value','');
    		$('#email').prop('value','');
    		$('#mailingAddress').prop('value','');
    		$('#workunit').prop('value','');
    		$('#position').prop('value','');
    		$('#userId').prop('value','');
    		$('#accountNum').prop('value','');
    	}
    	
    	function changeSelect(){
    		$('#auto').show();
    		$('#sd').hide();
    		$('#sex').prop('value','');
    		$('#schoolName').prop('value','');
    		$('#departName').prop('value','');
    		$('#gradeName').prop('value','');
    		$('#className').prop('value','');
    		$('#majorName').prop('value','');
    		$('#userId').prop('value','');
    		$('#cc').combogrid('clear');
    		$('#telId').prop('value','');
    		$('#email').prop('value','');
    		$('#mailingAddress').prop('value','');
    		$('#workunit').prop('value','');
    		$('#position').prop('value','');
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
      	<input name="donation.flag" type="hidden" value="1">
        <div class="form-group">
          <label for="payMoney" class="col-sm-2 control-label">支付金额</label>
          <div class="col-sm-10">
            <input type="text" class="easyui-validatebox" name="donation.money"  id="money" id="payMoney" placeholder="请填写数字">
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
									        url: '<%=path %>/project/projectAction!doNotNeedSessionAndSecurity_getAll.action'"/>
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
		<div id="auto">
        <div class="form-group" id="name">
          <label for="name" class="col-sm-2 control-label">姓名</label>
          <div class="col-sm-10">
            <select class="easyui-combogrid" id="cc" style="width:150px;" name="x_name"
						        data-options="
						        	required:true,
						        	editable:false,
						            panelWidth:600,
						            idField:'userName',
						            textField:'userName',
						            pagination : true,
						            url:query_user_url,
						            columns:[[
						                {field:'userName',title:'姓名',width:100,align:'center'},
						                {field:'fullName',title:'所属机构',width:450,align:'center'}
						            ]],
						            toolbar: $('#toolbar'),
						            onSelect:function(rowIndex, rowData){
						            	$('#departName').prop('value',rowData.departName);
						            	$('#gradeName').prop('value',rowData.gradeName);
						            	$('#className').prop('value',rowData.className);
						            	$('#sex').prop('value',rowData.sex);
						            	$('#telId').prop('value',rowData.telId);
						            	$('#email').prop('value',rowData.email);
						            	$('#mailingAddress').prop('value',rowData.mailingAddress);
						            	$('#workunit').prop('value',rowData.workUnit);
						            	$('#position').prop('value',rowData.position);
						            	$('#userId').prop('value',rowData.userId);
						            	$('#majorName').prop('value',rowData.majorName);
						            	$('#schoolName').prop('value',rowData.schoolName);
						            	$('#accountNum').prop('value',rowData.accountNum);
						            }
						        "></select>
			<a href="javascipt:void(0)" onclick="changeInput()">找不到名单?切换至手动输入</a>
			<input class="aweform" type="hidden" name="donation.payMethod" value="网站"/>
			<input class="aweform" type="hidden" id="accountNum" name="donation.accountNum"/>
          </div>
        </div>
        <div class="form-group">
          <label for="sex" class="col-sm-2 control-label">性别</label>
          <div class="col-sm-10">
            <input id="sex" name="x_sex" readonly="readonly" type="text"></input>
          </div>
        </div>
        
        <div class="form-group" id="school">
          <label for="" class="col-sm-2 control-label">学校</label>
          <div class="col-sm-10">
            <input id="userId" name="donation.userId" type="hidden"/>
			<input id="schoolName" disabled="disabled" type="text"></input>
          </div>
        </div>

        <div class="form-group" id="depart">
          <label for="" class="col-sm-2 control-label">院系</label>
          <div class="col-sm-10">
			<input id="departName" disabled="disabled" type="text"></input>
          </div>
        </div>
        
        <div class="form-group" id="grade">
          <label for="" class="col-sm-2 control-label">年级</label>
          <div class="col-sm-10">
            <input id="gradeName" disabled="disabled" type="text"></input>
          </div>
        </div>
        
        <div class="form-group" id="class">
          <label for="" class="col-sm-2 control-label">班级</label>
          <div class="col-sm-10">
            <input id="className" disabled="disabled" type="text"></input>
          </div>
        </div>
        
        <div class="form-group" id="major">
          <label for="" class="col-sm-2 control-label">专业</label>
          <div class="col-sm-10">
            <input id="majorName" disabled="disabled" type="text"></input>
          </div>
        </div>
        </div>
        
        <div id="sd" style="display: none;">
        	<div class="form-group">
          <label class="col-sm-2 control-label">姓名</label>
          <div class="col-sm-10">
            <input name="donation.x_name" id="x_name" type="text" class="easyui-validatebox" data-options="required:true"></input>
            <a href="javascipt:void(0)" onclick="changeSelect()">切换至自动选择</a>
          </div>
        </div>
        
        	 <div class="form-group">
          <label for="sex" class="col-sm-2 control-label">性别</label>
          <div class="col-sm-10">
            <select name="donation.x_sex" id="x_sex">
            	<option value="男" selected="selected">男</option>
            	<option value="女">女</option>
            </select>
          </div>
        </div>
        
        <div class="form-group" id="school">
          <label for="" class="col-sm-2 control-label">学校</label>
          <div class="col-sm-10">
			<input name="donation.x_school" type="text" id="x_school"></input>
          </div>
        </div>

        <div class="form-group" id="depart">
          <label for="" class="col-sm-2 control-label">院系</label>
          <div class="col-sm-10">
          	<input name="donation.x_depart" type="text" id="x_depart"></input>
          </div>
        </div>
        
        <div class="form-group" id="grade">
          <label for="" class="col-sm-2 control-label">年级</label>
          <div class="col-sm-10">
          	<input name="donation.x_grade" id="x_grade" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" />
			级
          </div>
        </div>
        
        <div class="form-group" id="class">
          <label for="" class="col-sm-2 control-label">班级</label>
          <div class="col-sm-10">
          	<input name="donation.x_clazz" type="text" id="x_clazz"></input>
          </div>
        </div>
        
        <div class="form-group" id="major">
          <label for="" class="col-sm-2 control-label">专业</label>
          <div class="col-sm-10">
          	<input name="donation.x_major" type="text" id="x_major"></input>
          </div>
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

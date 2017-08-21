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
    <title>中南财经政法大学返校聚会</title>
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
				if($('form').form('validate')){
					var userId = $("#userId").val();
					var telId=$("#telId").val();
					if(telId == null || telId == ""){
						alert("请填写电话号码");
						return;
					} 
					if($('#auto').is(":visible")){
						if( (userId == null || userId == "")){
							alert("请选择联系人姓名");
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
							alert("请填写联系人姓名");
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
						if($("#save").hasClass("active")){
							alert("请不要重复提交");
						}else{
							$("#save").addClass("active");
							$("#form").attr("action","<%=path%>/znufe/znufeAction!doNotNeedSessionAndSecurity_activitySave.action");
							$("#form").submit();
						}
				}
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
    
    </script>
    
  </head>

  <body>

    <div class="container">
      <div class="page-header">
        <h1>
         	 返校聚会
        </h1>
      </div>

      <div class="jumbotron">
        <h1>感谢您，亲爱的校友</h1>
        <p class="lead">欢迎您进行返校聚会，中南财经政法大学校友会欢迎您！</p>
      </div>

      <form id="form" class="form-horizontal" method="post">
<div id="auto">
        <div class="form-group">
          <label for="name" class="col-sm-2 control-label">联系人姓名</label>
          <div class="col-sm-10"> 
          	<input id="userId" name="activity.userId" type="hidden"/>
            <select class="easyui-combogrid" id="cc" style="width:150px;"
						        data-options="
						        	required:true,
						        	editable:false,
						            panelWidth:600,
						            idField:'userName',
						            textField:'userName',
						            pagination : true,
						            url:'<%=path %>/userInfo/userInfoAction!doNotNeedSessionAndSecurity_dataGridFor.action',
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
						            	$('#userId').prop('value',rowData.userId);
						            	$('#majorName').prop('value',rowData.majorName);
						            	$('#schoolName').prop('value',rowData.schoolName);
						            	$('#workunit').prop('value',rowData.workUnit);
						            	$('#position').prop('value',rowData.position);
						            }
						        "></select>
            <a href="javascipt:void(0)" onclick="changeInput()">找不到名单?切换至手动输入</a>
          </div>
        </div>
		<div class="form-group" id="school">
          <label for="" class="col-sm-2 control-label">学校</label>
          <div class="col-sm-10">
			<input id="schoolName" disabled="disabled" type="text"></input>
          </div>
        </div>

        <div class="form-group">
          <label for="" class="col-sm-2 control-label">院系</label>
          <div class="col-sm-10">
			<input id="departName" disabled="disabled" type="text"></input>
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">年级</label>
          <div class="col-sm-10">
            <input id="gradeName" disabled="disabled" type="text"></input>
          </div>
        </div>
        
        <div class="form-group">
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
            <input name="activity.contactPerson" id="x_name" type="text" class="easyui-validatebox"></input>
            <a href="javascipt:void(0)" onclick="changeSelect()">切换至自动选择</a>
          </div>
        </div>
        
        <div class="form-group" id="school">
          <label for="" class="col-sm-2 control-label">学校</label>
          <div class="col-sm-10">
			<input name="activity.school" type="text" id="x_school"></input>
          </div>
        </div>

        <div class="form-group" id="depart">
          <label for="" class="col-sm-2 control-label">院系</label>
          <div class="col-sm-10">
          	<input name="activity.department" type="text" id="x_depart"></input>
          </div>
        </div>
        
        <div class="form-group" id="grade">
          <label for="" class="col-sm-2 control-label">年级</label>
          <div class="col-sm-10">
          	<input name="activity.grade" id="x_grade" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" />
			级
          </div>
        </div>
        
        <div class="form-group" id="class">
          <label for="" class="col-sm-2 control-label">班级</label>
          <div class="col-sm-10">
          	<input name="activity.clazz" type="text" id="x_clazz"></input>
          </div>
        </div>
        
        <div class="form-group" id="major">
          <label for="" class="col-sm-2 control-label">专业</label>
          <div class="col-sm-10">
          	<input name="activity.major" type="text" id="x_major"></input>
          </div>
        </div>
        </div>
    
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">联系电话</label>
          <div class="col-sm-10">
            <input id="telId" name="activity.contactPhone" type="text" class="easyui-validatebox" data-options="required:true"></input>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">返校时间</label>
          <div class="col-sm-10">
				<input id="backTime" name="activity.backTime" style="width: 150px;" class="easyui-validatebox" data-options="required:true" readonly="readonly" onClick="WdatePicker()"/>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">返校人数</label>
          <div class="col-sm-10">
            <input id="backNumber" name="activity.backNumber" type="text" class="easyui-validatebox" data-options="required:true"></input>
          </div>
        </div>
        <fieldset>
		    <legend>
				聚会场地
		    </legend>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">是否需要聚会场地</label>
          <div class="col-sm-10">
             <select name="activity.needMeeting" style="width: 155px;">
            	<option value="true">是</option>
            	<option value="false">否</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">聚会校区</label>
          <div class="col-sm-10">
            <select name="activity.meetingArea" style="width: 155px;">
            	<option value="南湖">南湖</option>
            	<option value="首义">首义</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">借用时间</label>
          <div class="col-sm-10">
				<input id="meetingTime" name="activity.meetingTime" style="width: 150px;" class="easyui-validatebox" data-options="required:true" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',maxDate:'#F{$dp.$D(\'meetingEndTime\',{H:-1})}'})"/>-
				<input id="meetingEndTime" name="activity.meetingEndTime" style="width: 150px;" class="easyui-validatebox" data-options="required:true" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',minDate:'#F{$dp.$D(\'meetingTime\',{H:1})}'})"/>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">参加人数</label>
          <div class="col-sm-10">
            <input id="meetingNumber" name="activity.meetingNumber" type="text" class="easyui-validatebox" data-options="required:true" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></input>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">是否需要投影仪</label>
          <div class="col-sm-10">
             <select name="activity.needProjector" style="width: 155px;">
            	<option value="true">是</option>
            	<option value="false">否</option>
            </select>
          </div>
        </div>
        </fieldset>
        <br>
        <fieldset>
		    <legend>
				参观需求
		    </legend>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">是否参观</label>
          <div class="col-sm-10">
            <select name="activity.needVisit" style="width: 155px;" data-options="editable:false">
            	<option value="true">是</option>
            	<option value="false">否</option>
            </select>
          </div>
        </div>
        
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">参观地点</label>
          <div class="col-sm-10">
            <select name="activity.visitPlace" style="width: 155px;">
            	<option value="校史馆">校史馆</option>
            	<option value="钱币博物馆">钱币博物馆</option>
            </select>
          </div>
        </div>
         <div class="form-group">
          <label for="" class="col-sm-2 control-label">参观时间</label>
          <div class="col-sm-10">
				<input id="visitTime" name="activity.visitTime" style="width: 150px;" class="easyui-validatebox" data-options="required:true" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',maxDate:'#F{$dp.$D(\'visitEndTime\',{H:-1})}'})"/>-
				<input id="visitEndTime" name="activity.visitEndTime" style="width: 150px;" class="easyui-validatebox" data-options="required:true" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',minDate:'#F{$dp.$D(\'visitTime\',{H:1})}'})"/>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">参观人数</label>
          <div class="col-sm-10">
            <input name="activity.visitNumber" id="visitNumber" type="text" class="easyui-validatebox" data-options="required:true" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></input>
          </div>
        </div>
        </fieldset>
        <br>
        <fieldset>
		    <legend>
				就餐需求
		    </legend>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">是否就餐</label>
          <div class="col-sm-10">
            <select name="activity.needDinner" style="width: 155px;" data-options="editable:false">
            	<option value="true">是</option>
            	<option value="false">否</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">就餐校区</label>
          <div class="col-sm-10">
            <select name="activity.dinnerArea" style="width: 155px;">
            	<option value="南湖">南湖</option>
            	<option value="首义">首义</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">就餐人数</label>
          <div class="col-sm-10">
            <input name="activity.dinnerNumber" id="dinnerNumber" type="text" class="easyui-validatebox" data-options="required:true" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></input>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">就餐时间</label>
          <div class="col-sm-10">
				<input id="dinnerTime" name="activity.dinnerTime" style="width: 150px;" class="easyui-validatebox" data-options="required:true" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',maxDate:'#F{$dp.$D(\'dinnerEndTime\',{H:-1})}'})"/>-
				<input id="dinnerEndTime" name="activity.dinnerEndTime" style="width: 150px;" class="easyui-validatebox" data-options="required:true" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:00:00',minDate:'#F{$dp.$D(\'dinnerTime\',{H:1})}'})"/>
          
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">就餐标准</label>
          <div class="col-sm-10">
            <input name="activity.dinnerStandard" id="dinnerStandard" type="text" class="easyui-validatebox" data-options="required:true" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></input>元/人
          </div>
        </div>
        </fieldset>
        <br>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label">是否认捐</label>
          <div class="col-sm-10">
            <select name="activity.needSubscription" style="width: 155px;" data-options="editable:false">
            	<option value="true">是</option>
            	<option value="false">否</option>
            </select>
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
										<input id="query" name="isQuery" type="hidden" value="1" >
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

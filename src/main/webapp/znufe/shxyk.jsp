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
    <title>中南财经政法大学校园商户卡申请</title>
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
			if ($('form').form('validate'))
			{
				if($('input[name="formData2.personalPic"]').val()==undefined){
					alert('请上传个人照片');
					return false;
				}
				if($('input[name="formData2.credentialsPic"]').val()==undefined){
					alert('请上传证件照片');
					return false;
				}
				if($("#save").hasClass("active")){
						alert("请不要重复提交");
					}else{
						$("#save").addClass("active");
						$("#form").attr("action","<%=path%>/znufe/znufeAction!doNotNeedSessionAndSecurity_campusCardSave.action");
						$("#form").submit();
				}
			}
		}
		
		$(function() 
		{
			uploadPic("#personal_upload_button", "formData2.personalPic", "#personalPic");
			uploadPic("#credentials_upload_button", "formData2.credentialsPic", "#credentialsPic");
		});
	
	
		function uploadPic(upload_button_name, picName, picDivName)
		{
			var button = $(upload_button_name), interval;
			new AjaxUpload(button, 
			{
				action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSessionAndSecurity_fileUpload.action',
				name : 'upload',
				onSubmit : function(file, ext) 
				{
					if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext))) 
					{
						alert('您上传的图片格式不对，请重新选择！');
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
						alert(msg.message);
					}
				}
			});
		
		}
		function removePic(pic, upload_button_name) 
		{
			$(pic).parent().remove();
			$(upload_button_name).prop('disabled', false);
		}
    </script>
  </head>
  <body>
    <div class="container">
      <div class="page-header">
        <h1>
       		 校园商户卡申请
        </h1>
      </div>
      <div class="jumbotron">
        <h1>感谢您，亲爱的校友</h1>
        <p class="lead">欢迎您进行校园商户卡申请，中南财经政法大学校友会欢迎您！</p>
      </div>
      <form id="form" class="form-horizontal" method="post">
		<fieldset>
		    <legend>
				基本信息
		    </legend>
	<table class="ta001">
		<tr>
			<th>
				商户名称
			</th>
			<td>
				<input name="formData2.name" class="easyui-validatebox"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				营业范围
			</th>
			<td>					
				<input name="formData2.businessScope" class="easyui-validatebox"
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				所在地
			</th>
			<td>
				<input class="easyui-combobox" name="province" id="province"
						data-options="
						required:true,
	                    method:'post',
	                    url:'${pageContext.request.contextPath}/province/provinceAction!doNotNeedSessionAndSecurity_getProvince2ComboBox.action?countryId=1',
	                    valueField:'provinceName',
	                    textField:'provinceName',
	                    editable:false,
	                    prompt:'省',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#province').combobox('clear');
			                	$('#city').combobox('clear');
			                	$('#city').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/city/cityAction!doNotNeedSessionAndSecurity_getCity2ComboBox.action?provinceId='+rec.id; 
							$('#city').combobox('clear');	
							$('#city').combobox('reload', url);
						}
                    	">
                    	&nbsp; <input class="easyui-combobox" name="city" id="city"
						data-options="
	                    method:'post',
	                    valueField:'cityName',
	                    textField:'cityName',
	                    editable:false,
	                    prompt:'市',
	                    icons:[{
			                iconCls:'icon-clear',
			                handler: function(e){
			                	$('#city').combobox('clear');
			                }
			            }]
                    	">
			</td>
		</tr>
		<tr>
			<th>
				所属行业
			</th>
			<td>					
				<input name="formData2.industry" class="easyui-validatebox"
					
					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				法人代表
			</th>
			<td>
				<input name="formData2.legal" class="easyui-validatebox"

					data-options="required:true,validType:'userName'"
					maxlength="100" />
			</td>
			<th>
				联系电话
			</th>
			<td>
				<input name="formData2.unitTel" class="easyui-validatebox"

					data-options="required:true,validType:'telePhone'"
					maxlength="11" />
			</td>
		</tr>
		<tr>
			<th>
				登记机关
			</th>
			<td>
				<input name="formData2.registrationAuthority" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				营业执照号
			</th>
			<td>
				<input name="formData2.businessLicenseNo" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			
			<th>
				联系地址
			</th>
			<td>
				<input name="formData2.unitAddress" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
		</tr>
	</table>
	</fieldset>
	<br>
	<fieldset>
    <legend>
       	 优惠计划信息
    </legend>

	<table class="ta001">
		<tr>
			<th>
				折扣优惠
			</th>
			<td>
				<input name="formData2.discountPreferential" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				礼品优惠
			</th>
			<td>
				<input name="formData2.giftPreferential" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			
			<th>
				其它优惠
			</th>
			<td>
				<input name="formData2.otherPreferential" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
		</tr>
		<tr>
			<th>
				会员优惠
			</th>
			<td colspan="5">
				<input name="formData2.vipPreferential" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
		</tr>
		</table>
	</fieldset>
	
	<br>
	
	<fieldset>
    <legend>
            联系信息
    </legend>

	<table class="ta001">
	<tr>
			<th>
				联系人
			</th>
			<td>
				<input name="formData2.contact" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="100" />
			</td>
			<th>
				联系电话
			</th>
			<td>
				<input name="formData2.contactTel" class="easyui-validatebox"

					data-options="required:true,validType:'telePhone'"
					maxlength="11" />
			</td>
			
			<th>
				传真
			</th>
			<td>
				<input name="formData2.fax" class="easyui-validatebox"

					data-options="required:true,validType:'customRequired'"
					maxlength="13" />
			</td>
	</tr>
	
	<tr>
			
			
			<th>
				邮箱
			</th>
			<td colspan="5">
				<input name="formData2.emailBox" class="easyui-validatebox"

					data-options="required:true,validType:'email'"
					maxlength="100" />
			</td>
	</tr>
	
	</table>
	</fieldset>
	
	<br>
	
	<fieldset>
    <legend>
            图片信息
    </legend>

	<table class="ta001">
		
		<tr>
			<td colspan="2">
				<input type="button" id="personal_upload_button" value="上传个人照片">
			</td>
		</tr>
		
		<tr>
			<th>
				个人照片
			</th>
			<td>
				<div id="personalPic"></div>
			</td>
			
		</tr>
		
		<tr>
			<td colspan="2">
				<input type="button" id="credentials_upload_button" value="上传证件照片">
			</td>
		</tr>
		<tr>
			<th>
				证件照片
			</th>
			<td>
				<div id="credentialsPic"></div>
			</td>
		</tr>

	</table>
	</fieldset>
	
		<div class="form-group">
          <div class="col-sm-offset-2 col-sm-10">
            <button id="save" onclick="saveTo();" type="button" class="btn btn-primary">提交</button>
          </div>
        </div>
        
	  </form>

    
  </body>
</html>

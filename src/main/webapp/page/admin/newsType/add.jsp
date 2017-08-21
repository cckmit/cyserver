<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
long parent_id = WebUtil.toLong(request.getParameter("parent_id")) ;
String origin = request.getParameter("origin");
String parentName = request.getParameter("parent_name");
String channel = request.getParameter("channel");
String channelName = request.getParameter("channelName");
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../../inc.jsp"></jsp:include>
<script type="text/javascript">
	KindEditor.ready(function(K) {
		K.create('#newscontent',{
			 fontSizeTable:['9px', '10px', '11px', '12px', '13px', '14px', '15px', '16px', '17px', '18px', '19px', '20px', '22px', '24px', '28px', '32px'],
	    	 uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
	         afterChange:function(){
		        	this.sync();
		        }
	    });
	});
	
	function submitForm($dialog, $grid, $pjq)
	{
		if($('#origin').val()==2 && ($('#province').combobox('getText')=='' || $('#city').combobox('getText')=='')) {
			$.messager.alert('提示', '请选择地域！', 'error');
			return false;
		}
		if ($('form').form('validate')){
			$.ajax({
				url : '${pageContext.request.contextPath}/mobNewsType/mobNewsTypeAction!saveNewsType.action',
				data : $('form').serialize(),
				dataType : 'json',
				success : function(result)
				{
					if (result.success)
					{
						$grid.treegrid('reload');
						$dialog.dialog('destroy');
						$pjq.messager.alert('提示', result.msg, 'info');
					} else
					{
						$pjq.messager.alert('提示', result.msg, 'error');
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
	};
	
	/**--下拉框发生改变时--**/
	function selectType(value){
		if(value=="1"){
			$("#trUrl").css("display","none");
			$("#trNewsTitle").css("display","none");
			$("#trNewsContent").css("display","none");
			$("#url").val("");
		}else if(value=="2" || value=="4"){
			$("#trUrl").css("display","");
			$("#trNewsTitle").css("display","none");
			$("#trNewsContent").css("display","none");
		}else if(value=="3"){
			$("#trUrl").css("display","none");
			$("#trNewsTitle").css("display","");
			$("#trNewsContent").css("display","");
			$("#url").val("");
		}
	}
	function selectOrigin(){
		var value = $("#origin").val();
		if(value=="2"){
			$("#trArea").css("display","");
		}else{
			$("#trArea").css("display","none");
			$("#province").combobox("clear");
            $("#city").combobox("clear");
            $("#city").combobox("loadData",[]);
		}
	}
	$(function()
	{
		uploadPic("#pic_upload_button", "type.typePic", "#newsTypePic");

	});


	function uploadPic(upload_button_name, picName, picDivName)
	{
		var button = $(upload_button_name), interval;
		new AjaxUpload(button,
				{
					action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUpload.action',
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
</script>
</head>
  
<body>
<form method="post" id="addNewsForm">
<input id="parent_id" type="hidden" name="type.parent_id" value="<%=parent_id %>">
<fieldset>
		<legend>
			栏目信息
		</legend>
		<table class="ta001">
			<% if(parent_id==0){ %>
			<%--<tr>
				<th>
					渠道
				</th>
				<td colspan="3">
					<select id="channel" name="type.channel">
						<option value="10">手机</option>
						<option value="20">网页</option>
						<option value="30">微信</option>
					</select>
				</td>
			</tr>--%>

			<tr>
			<th>
				新闻渠道
			</th>
			<td colspan="3">
				<select id="channel"  name="type.channel" class="easyui-combobox" style="width: 150px;"
						data-options="
												valueField: 'dictValue',
												textField: 'dictName',
												editable:false,
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
									                $('#qudao').combobox('clear');
									                $('#newsType').combotree('clear');
													  $('#newsType').combotree('loadData',[]);

									                }
									            }],
												url: '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getDictValue.action?dictTypeValue='+ 110
												<%--onSelect: function(rec){
												    var sChannel = rec.dictValue;
													var url =  '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getNewsTypeByParent.action?channel='+sChannel;
													$('#newsType').combotree('clear');
													$('#newsType').combotree('reload', url);
										}--%>" >
				</select>
			</td>
			</tr>






			<%} %>
			<tr>
				<th>
					栏目名称
				</th>
				<td colspan="3">
					<input id="name" name="type.name" class="easyui-validatebox"
						style="width: 300px;"
						data-options="required:true,validType:'customRequired'"
						maxlength="30" value=""/>
				</td>
			</tr>

			<tr>
				<th>
					栏目标识
				</th>
				<td colspan="3">
					<input id="code" name="type.code" class="easyui-validatebox"
						   onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"
						   style="width: 300px;"
						   data-options="required:true,validType:'customRequired'"
						   maxlength="30" value=""/>
				</td>
			</tr>

			<%--<tr>
				<th>
					栏目类型
				</th>
				<td colspan="3">
					<select id="type" name="type.type" onchange="selectType();">
						<option value="1">新闻类别</option>
						<option value="2">链接</option>
						<option value="3">单页面</option>
					</select>
				</td>
			</tr>--%>




			<th >
				栏目类型
			</th>

			<td colspan="3">
				<input id="type" name="type.type" class="easyui-combobox" style="width: 150px;"
						data-options="
												valueField: 'dictValue',
												textField: 'dictName',
												editable:false,
							                    icons:[{
									                iconCls:'icon-clear',
									                handler: function(e){
									                $('#qudao').combobox('clear');
									                $('#newsType').combotree('clear');
													  $('#newsType').combotree('loadData',[]);

									                }
									            }],
												url: '${pageContext.request.contextPath}/mobile/news/newsAction!doNotNeedSessionAndSecurity_getDictValue.action?dictTypeValue='+ 119,
												onSelect: function(rec){
													selectType(rec.dictValue)
												}" />
			</td>





			<tr>
				<th>
					栏目级别
				</th>
				<td colspan="3">
					<% if(parent_id==0){ %>  一级栏目  <%} %>
					<% if(parent_id>0){ %>  二级栏目 <%} %>
				</td>
			</tr>

			<tr id="trUrl" style="display:none;">
				<th>
					URL(可为空)
				</th>
				<td colspan="3">
					<input id="url" style="width: 700px;" name="type.url" type="text"   value="">
				</td>
			</tr>
			<tr id="trNewsTitle" style="display:none;">
				<th>
					新闻标题
				</th>
				<td colspan="3">
					<input id="newstitle" style="width: 700px;" name="type.newsTitle" type="text" value="">
				</td>
			</tr>
			<tr id="trNewsContent" style="display:none;">
				<th>
					新闻内容
				</th>
				<td colspan="3">
					<textarea id="newscontent" name="type.newsContent"
						style="width: 700px; height: 300px;"></textarea>
				</td>
			</tr>
			
			<% if(parent_id==0){ %>
			<tr style="display:none;">
				<th>
					新闻来源
				</th>
				<td colspan="3">
					<select id="origin" name="type.origin" onchange="selectOrigin();">
						<option value="1">总会</option>
						<option value="2">地方</option>
					</select>
				</td>
			</tr>

			<tr id="trArea" style="display:none;">
				<th>
					地域
				</th>
				<td colspan="3">
					<input class="easyui-combobox" name="province" id="province"
						data-options="
	                    method:'post',
	                    url:'${pageContext.request.contextPath}/province/provinceAction!doNotNeedSecurity_getProvince2ComboBox.action?countryId=1',
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
							var url = '${pageContext.request.contextPath}/city/cityAction!doNotNeedSecurity_getCity2ComboBox.action?provinceId='+rec.id; 
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

			<%}else{ %>
			<tr>
				<th>
					新闻来源
				</th>
				<td colspan="3">
					<input type="hidden" name="type.origin" value="<%=origin %>" />
					<%if("1".equals(origin)){ %>总会新闻<%} %>
					<%if("2".equals(origin)){ %>地方新闻<%} %>
				</td>
			</tr>
			<tr>
				<th>
					上级栏目
				</th>
				<td colspan="3">
					<%=parentName%>
				</td>
			</tr>

			<tr>
				<th>
					渠道
				</th>
				<td colspan="3">
					<input type="hidden" name="type.channel" value="<%=channel %>" />
					<%=channelName%>
				</td>
			</tr>
			<%} %>
			 
			<tr>
				<th>
					导航显示
				</th>
				<td colspan="3">
					<select id="isNavigation" name="type.isNavigation">
						<option value="1">是</option>
						<option value="0">否</option>						
					</select>
				</td>
			</tr>
			<% if(parent_id==0){ %>
			<tr>
				<th>
					是否上首页
				</th>
				<td colspan="3" >
					<select id="isMain" name="type.isMain">
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</td>
			</tr>
			<%} %>
			<tr>
				<th>
					排序编号
				</th>
				<td colspan="3">
					<input id="orderNum" name="type.orderNum" class="easyui-validatebox"
						style="width: 150px;"
						data-options="required:true,validType:'tel'"
						maxlength="20" value="0"/>&nbsp;&nbsp;&nbsp;&nbsp;( 数字越小越靠前)
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
				<th>
					栏目图标
				</th>
				<td>
					<input type="button" id="pic_upload_button" value="栏目图标">
				</td>
				<td>
					<div id="newsTypePic"></div>
				</td>

			</tr>

		</table>
	</fieldset>
</form>
  </body>
</html>
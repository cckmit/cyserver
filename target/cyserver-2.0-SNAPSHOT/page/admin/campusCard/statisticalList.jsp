<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
	var actionName = "campusCardAction";
	var actionUrl = "${pageContext.request.contextPath}/page/admin/campusCard/";
	var actionFullPath = actionUrl + actionName;
	
	
	var grid;
	$(function(){
		grid=$('#dataGrid').datagrid({  
		    url: actionFullPath + '!statisticalDataGrid.action', 
		  	fit : true,
			border : false,
			fitColumns : true,
			striped : true,
			rownumbers : true,
			pagination : true,
		  	
		    columns:[[
		        {field:'itemName',title:'所在地',width:50,align:'center'}, 
		        {field:'industry',title:'所属行业',width:50,align:'center'}, 
		        {field:'results',title:'统计结果',width:50,align:'center',
		        
		        formatter: function(value,row,index){
		    			return value + "人";
					}	
		        
		        }
		    ]],
		    toolbar : '#toolbar',
		    onBeforeLoad : function(param) {
				parent.$.messager.progress({
					text : '数据加载中....'
				});
			},
			onLoadSuccess : function(data) {
				parent.$.messager.progress('close');
			}
		}); 
	});
	
	
	
	function searchData(){
		$('#dataGrid').datagrid('load',serializeObject($('#searchForm')));
	}
	
	
	
	
	
	
	
	
</script>
</head>
  
  <body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchForm">
						<table>
							<tr>
								<th align="right">
									所在地
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input class="easyui-combobox" name="province" id="province" style="width: 150px"
										data-options="
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
				                    	&nbsp; <input class="easyui-combobox" name="city" id="city" style="width: 150px"
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

								<th align="right">
									所属行业
								</th>
								<td>
									<div class="datagrid-btn-separator"></div>
								</td>
								<td>
									<input name="formData.industry" class="easyui-validatebox" style="width: 150px;"
					
										
										maxlength="100" />
								</td>
								<td>
									<a href="javascript:void(0);" class="easyui-linkbutton"
										data-options="iconCls:'icon-search',plain:true"
										onclick="searchData();">查询</a>
								</td>
								
							</tr>
							
							
						</table>
					</form>
				</td>
			</tr>
			
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="dataGrid"></table>
	</div>
</div>
  </body>
</html>
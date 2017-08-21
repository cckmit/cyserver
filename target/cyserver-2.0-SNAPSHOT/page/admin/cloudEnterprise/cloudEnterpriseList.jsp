<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="authority" uri="/authority"%>
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
        var  statuses = ${param.statuses};
        var grid;
        $(function(){
            grid=$('#enterpriseGrid').datagrid({
                url : '${pageContext.request.contextPath}/cloudEnterprise/cloudEnterpriseAction!dataGraid.action?statuses='+statuses,
                fit : true,
                border : false,
                fitColumns : true,
                striped : true,
                rownumbers : true,
                pagination : true,
                idField : 'id',
                columns:[[
                    {field:'id',checkbox : true},
                    {field:'name',title:'企业名称',width:150,align:'center'},
                    {field:'industry',title:'所属行业',width:150,align:'center'},
                    {field:'province',title:'所在省市',width:150,align:'center',
                        formatter:function (value,row) {
                            var content = '' ;
                            if (value != undefined && value != "undefined") {
                                content += value ;
                            }
                            if (value != undefined && value != "undefined" && row.city != undefined && row.city != "undefined") {
                                content += " | ";
                            }
                            if (row.city != undefined && row.city != "undefined") {
                                content += row.city ;
                            }
                            return content;
                        }
                    },
                    {field:'mainBusiness',title:'主营业务',width:100,align:'center'},
                    {field:'linkman',title:'联系人',width:100,align:'center'},
                    {field:'contactNumber',title:'联系电话',width:100,align:'center'},
                    {field:'status',title:'状态',width:100,align:'center',
                        formatter: function (value, row) {
                            str ="";
                           if (value ==10){
                               str = '<span style="color:red ">待审核</span>'
                           }else if(value ==20){
                               str ='<span style="color: green">通过</span>'
                           }else if (value ==30){
                               str ='<span style="color:red">不通过</span>'
                           }
                            return str;
                        }
                    },
                    {field:'countEntrepreneur',title:'是否有认证通过的校友企业家',width:160,align:'center',
                        formatter: function (value, row) {
                            var str = '无';
                            if(row.countEntrepreneur > 0) {
                                str = '<a href="javascript:void(0)" onclick="viewEnterpriseTeam(\'' + row.cloudId + '\')">' + row.countEntrepreneur + '</a>&nbsp;';
                            }
                            return str;
                        }
                    },
                    {field:'operator',title:'操作',align:'center',width:200,
                        formatter: function(value,row,index){
                            var content="";
                            <authority:authority authorizationCode="查看企业信息" userRoles="${sessionScope.user.userRoles}">
                           if (row.status ==10){
                               if (row.countEntrepreneur>0 ){
                                   content+='<a href="javascript:void(0)" onclick="certification(\'' + row.cloudId + '\',20,1)"><img class="iconImg ext-icon-note_edit"/>通过</a>&nbsp;/';
                                   content+='<a href="javascript:void(0)" onclick="certification(\'' + row.cloudId + '\',30,1)"><img class="iconImg ext-icon-note_delete"/>不通过</a>&nbsp;';
                               }else {
                                   content+='<span style="color: grey">通过</span>&nbsp;/&nbsp;';
                                   content+='<span style="color: grey">不通过</span>&nbsp;';
                               }
                           }else if(row.status ==20){
                               content+='<a href="javascript:void(0)" onclick="certification(\'' + row.cloudId + '\',30,2)"><img class="iconImg ext-icon-note_delete"/>解除校企关系</a>&nbsp;';
                           }
                            </authority:authority>
                            return content;
                        }}
                ]],
                toolbar : '#newsToolbar',
                onBeforeLoad : function(param) {
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                onLoadSuccess : function(data) {
                    $('.iconImg').attr('src', pixel_0);
                    parent.$.messager.progress('close');

                }
            });
        });

        function searchEnterprise(){
            if ($('#searchEnterpriseForm').form('validate')) {
                $('#enterpriseGrid').datagrid('load',serializeObject($('#searchEnterpriseForm')));
            }
        }

        function certification(id,status,flag) {
            if (id && id !='' && status && status !=''){
                var msg = "";
                if (flag && flag =='1' && status && status =="20" ){
                    msg ="确定通过该企业？"
                }else if (flag && flag =='1' && status && status =="30" ) {
                    msg ="确定不通过该企业？"
                }else if (flag && flag =='2'){
                    msg = "确定解除校企关系？"
                }
                $.messager.confirm('确认', msg, function(r){
                    if (r){
                        $.ajax({
                            url : '${pageContext.request.contextPath}/cloudEnterprise/cloudEnterpriseAction!certification.action',
                            data : {
                                'cloudEnterprise.cloudId' :id,
                                'cloudEnterprise.status' :status,
                                'flag':flag
                            },
                            dataType : 'json',
                            success : function(data) {
                                if(data.success){
                                    $("#enterpriseGrid").datagrid('reload');
                                    $("#enterpriseGrid").datagrid('unselectAll');
                                    $.messager.alert('提示',data.msg,'info');
                                }
                                else{
                                    $.messager.alert('错误', data.msg, 'error');
                                }
                            },
                            beforeSend:function(){
                                $.messager.progress({
                                    text : '数据提交中....'
                                });
                            },
                            complete:function(){
                                $.messager.progress('close');
                            }
                        });
                    }
                });
            }else {
                $.messager.alert('提示',"数据异常，请刷新页面重新操作！");
            }

        }

        function cancelRelationship(id) {

        }

        /**
         * 查看组织成员
         */
        function viewEnterpriseTeam(id,name) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/cloudEnterprise/cloudEnterpriseTeam.jsp?enterpriseId=' + id + '&enterpriseName='+name
            });
        }
        /**
         * 查看招聘信息
         */
        function viewEnterprisePosition(id,name) {
            var dialog = parent.modalDialog({
                width : 1000,
                height : 600,
                title : '查看',
                iconCls:'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/cloudEnterprise/viewCloudEnterpriseJobList.jsp?enterpriseId=' + id + '&enterpriseName='+name
            });
        }

        function resetT(){
            $('#name').val('');
            $('#industry').val('');
            $('#province').combobox('clear');
            $('#city').combobox('clear');
        }
    </script>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div id="newsToolbar" style="display: none;">
        <table>
            <tr>
                <td>
                    <form id="searchEnterpriseForm">
                        <table>
                            <tr>

                                <th>
                                    公司名称
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="cloudEnterprise.name" id="name" style="width: 150px;" />
                                </td>

                                <th>
                                    所属行业
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <input name="cloudEnterprise.industry" id="industry" style="width: 150px;" />
                                </td>
                                <th align="right">
                                    所在省市
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td colspan="8">
                                    <input class="easyui-combobox" name="cloudEnterprise.province" id="province" style="width: 150px;"
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
			                	$('#area').combobox('clear');
								$('#area').combobox('loadData',[]);
			                }
			            }],
			            onSelect: function(rec){
							var url = '${pageContext.request.contextPath}/city/cityAction!doNotNeedSecurity_getCity2ComboBox.action?provinceId='+rec.id;
							$('#city').combobox('clear');
							$('#city').combobox('reload', url);
							$('#area').combobox('clear');
							$('#area').combobox('loadData',[]);
						}
                    	">
                                    &nbsp; <input class="easyui-combobox" name="cloudEnterprise.city" id="city" style="width: 150px;"
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
			                	$('#area').combobox('clear');
								$('#area').combobox('loadData',[]);
			                }
			            }]
                    	">
                                </td>
                        <c:if test="${param.statuses ne \"'20'\" }">
                                <th>
                                    审核状态
                                </th>
                                <td>
                                    <div class="datagrid-btn-separator"></div>
                                </td>
                                <td>
                                    <select class="easyui-combobox" data-options="editable:false" name="cloudEnterprise.status"  id="status" style="width: 150px;">
                                        <option value="">全部</option>
                                        <option value="10">待审核</option>
                                        <option value="30">不通过</option>
                                    </select>
                                </td>
                        </c:if>
                                
                                <td>
                                    <a href="javascript:void(0);" class="easyui-linkbutton"
                                       data-options="iconCls:'icon-search',plain:true"
                                       onclick="searchEnterprise();">查询</a>&nbsp;
                                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'l-btn-icon ext-icon-huifu',plain:true" onclick="resetT()">重置</a>
                                </td>
                            </tr>
                        </table>
                    </form>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'center',fit:true,border:false">
        <table id="enterpriseGrid" ></table>
    </div>
</div>
</body>
</html>
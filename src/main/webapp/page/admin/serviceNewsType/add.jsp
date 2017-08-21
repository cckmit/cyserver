<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    long parentId = WebUtil.toLong(request.getParameter("parentId")) ;
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
                    url : '${pageContext.request.contextPath}/serviceNewsType/serviceNewsTypeAction!save.action',
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
        function selectType(){
            var value = $("#type").val();
            if(value=="1"){
                $("#trUrl").css("display","none");
                $("#trNewsTitle").css("display","none");
                $("#trNewsContent").css("display","none");
                $("#url").val("");
            }else if(value=="2"){
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
    </script>
</head>

<body>
<form method="post" id="addNewsForm">
    <input id="parent_id" type="hidden" name="type.parent_id" value="<%=parentId %>">
    <input id="type" type="hidden" name="type.type" value="1">
    <fieldset>
        <legend>
            栏目信息
        </legend>
        <table class="ta001">
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
            <%--<tr>
                <th>
                    栏目类型
                </th>
                <td colspan="3">
                    <select id="type" class="easyui-combobox" name="type.type" onchange="selectType();">
                        <option value="1">新闻类别</option>
                        <option value="2">链接</option>
                        <option value="3">单页面</option>
                    </select>
                </td>
            </tr>--%>
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
            <tr>
                <th>
                    渠道
                </th>
                <td colspan="3">
                    <select id="channel" class="easyui-combobox" name="type.channel">
                        <option value="10">手机</option>
                        <option value="20">网页</option>
                        <option value="30">微信</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>
                    所属服务
                </th>
                <td>
                    <input name="type.serviceId" id="serviceId" class="easyui-combobox" style="width: 150px;" value=""
                           data-options="editable:false,required:true,
							        valueField: 'id',
							        textField: 'serviceName',
							        url: '${pageContext.request.contextPath}/mobile/schoolServ/schoolServAction!doNotNeedSessionAndSecurity_getServiceList.action'" />
                </td>
            </tr>
            <tr>
                <th>
                    导航显示
                </th>
                <td colspan="3">
                    <select id="isNavigation" class="easyui-combobox" name="type.isNavigation">
                        <option value="1">是</option>
                        <option value="0">否</option>
                    </select>
                </td>
            </tr>
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
</form>
</body>
</html>
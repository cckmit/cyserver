<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.cy.util.WebUtil"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('#newscontent',{
                fontSizeTable:['9px', '10px', '11px', '12px', '13px', '14px', '15px', '16px', '17px', '18px', '19px', '20px', '22px', '24px', '28px', '32px'],
                uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
                afterChange:function(){
                    this.sync();
                }
            });
        });

        $(function () {
            if ($('#typeId').val() > 0) {
                $.ajax({
                    url: '${pageContext.request.contextPath}/wechatNewsType/wechatNewsTypeAction!getWeChatNewsType.action',
                    data: $('form').serialize(),
                    dataType: 'json',
                    success: function (result) {
                        if (result.id != undefined) {
                            $('form').form('load', {
                                'wechatNewsType.id': result.id,
                                'wechatNewsType.name': result.name,
                                'wechatNewsType.parentId': result.parentId,
                                'wechatNewsType.type': result.type,
                                'typeSel': result.type,
                                'wechatNewsType.url': result.url,
                                'wechatNewsType.origin': result.origin,
                                'wechatNewsType.isNavigation': result.isNavigation,
                                'wechatNewsType.orderNum': result.orderNum,
                                'wechatNewsType.newsTitle': result.newsTitle
                            });
                            editor.html(result.newsContent);

                            if(result.type == 2) {
                                $("#trUrl").css("display","");
                            } else if(result.type == 3) {
                                $("#trNewsTitle").css("display","");
                                $("#trNewsContent").css("display","");
                            }

                            if(result.origin == 2) {
                                $("#trArea").css("display","");
                            }

                            if(result.parentId == 0) {
                                $("#level").html('一级栏目');
                            } else if(result.parentId > 0) {
                                $("#level").html('二级栏目');
                                $("#trOrigin").css("display","none");
                                $("#trArea").css("display","none");
                            }

                            if(result.cityName != null && result.cityName != '') {
                                var rArr = result.cityName.split(' ');
                                if(rArr.length >= 1) {
                                    $('#province').combobox('select',rArr[0]);
                                }
                                if(rArr.length == 2) {
                                    $('#city').combobox('setValue',rArr[1]);
                                }
                            }
                        }
                    },
                    beforeSend: function () {
                        parent.$.messager.progress({
                            text: '数据加载中....'
                        });
                    },
                    complete: function () {
                        parent.$.messager.progress('close');


                    }
                });
            }

        });

        function submitForm($dialog, $grid, $pjq)
        {
            if($('#origin').val()==2 && ($('#province').combobox('getText')=='' || $('#city').combobox('getText')=='')) {
                $.messager.alert('提示', '请选择地域！', 'error');
                return false;
            }
            if ($('form').form('validate')){
                $.ajax({
                    url : '${pageContext.request.contextPath}/wechatNewsType/wechatNewsTypeAction!updateWeChatNewsType.action',
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
    <input name="wechatNewsType.id" type="hidden" id="typeId" value="${param.id}">
    <input id="parent_id" type="hidden" name="wechatNewsType.parentId" value="">
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
                    <input id="name" name="wechatNewsType.name" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="required:true,validType:'customRequired'"
                           maxlength="30" value=""/>
                </td>
            </tr>
            <tr>
                <th>
                    栏目级别
                </th>
                <td colspan="3" id="level">
                </td>
            </tr>
            <tr>
                <th>
                    栏目类型
                </th>
                <td colspan="3">
                    <input id="type" type="hidden" name="wechatNewsType.type" value="">
                    <select name="typeSel" onchange="selectType();" disabled="disabled">
                        <option value="1">新闻类别</option>
                        <option value="2">链接</option>
                        <option value="3">单页面</option>
                    </select>
                </td>
            </tr>
            <tr id="trUrl" style="display:none;">
                <th>
                    URL(可为空)
                </th>
                <td colspan="3">
                    <input id="url" style="width: 700px;" name="wechatNewsType.url" type="text"   value="">
                </td>
            </tr>

            <tr id="trNewsTitle" style="display:none;">
                <th>
                    新闻标题
                </th>
                <td colspan="3">
                    <input id="newstitle" style="width: 700px;" name="wechatNewsType.newsTitle" type="text" value="">
                </td>
            </tr>
            <tr id="trNewsContent" style="display:none;">
                <th>
                    新闻内容
                </th>
                <td colspan="3">
					<textarea id="newscontent" name="wechatNewsType.newsContent"
                              style="width: 700px; height: 300px;"></textarea>
                </td>
            </tr>

            <tr id="trOrigin" style="display:none;">
                <th>
                    新闻来源
                </th>
                <td colspan="3">
                    <select id="origin" name="wechatNewsType.origin" onchange="selectOrigin();">
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

            <tr>
                <th>
                    导航显示
                </th>
                <td colspan="3">
                    <select id="isNavigation" name="wechatNewsType.isNavigation">
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
                    <input id="orderNum" name="wechatNewsType.orderNum" class="easyui-validatebox"
                           style="width: 150px;"
                           data-options="required:true,validType:'tel'"
                           maxlength="20" value="" />&nbsp;&nbsp;&nbsp;&nbsp;( 数字越小越靠前)
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
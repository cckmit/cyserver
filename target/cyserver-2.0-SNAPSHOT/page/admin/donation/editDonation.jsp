<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
        var donationId='${param.id}';
        var editor;
        KindEditor.ready(function(K) {
            editor = K.create('#content',{
                items : [
                    'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                    'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                    'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                    'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
                    'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                    'anchor', 'link', 'unlink', '|', 'about'
                ],
                uploadJson : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadK.action',
                afterChange:function(){
                    this.sync();
                }
            });
            editor.readonly();
        });


        $(function() {
            if (donationId > 0) {
                $.ajax({
                    url:'${pageContext.request.contextPath}/donation/donationAction!doNotNeedSecurity_getById.action',
                    data :{'id':donationId},
                    dataType:'json',
                    success : function(result){
                        if (result.donationId != undefined) {
                            $('form').form('load', {
                                'donation.projectId' : result.projectId,
                                'donation.donationType' : result.donationType,
                                'donation.donationCourier' : result.donationCourier,
                                'donation.donationCourierNumber' : result.donationCourierNumber,
                                'donation.needInvoice' : result.needInvoice && result.needInvoice !=''?result.needInvoice:1,
                                'donation.money' : result.money,
                                'donation.payMoney' : result.payMoney,
                                'donation.message' : result.message,
                                'donation.remark' : result.remark,
                                'content' : result.content,
                                'departName' : result.x_depart,
                                'gradeName' : result.x_grade,
                                'className' : result.x_clazz,
                                'sex' : result.x_sex,
                                'telId' : result.x_phone,
                                'email' : result.x_email,
                                'mailingAddress' : result.x_address,
                                'donation.confirmStatus':result.confirmStatus,
                                'donation.payStatus':result.payStatus,
                                'payTime' : result.payTime,
                                'donation.payDetail':result.payDetail,
                                'donation.payType':result.payType,
                                'donation.payMode':result.payMode,
                                'donation.payMethod':result.payMethod,
                                'userName':result.x_name,
                                'orderNo':result.orderNo,
                                'schoolName':result.x_school,
                                'majorName':result.x_major,
                                'workunit':result.x_workunit,
                                'deliveryAddress':result.deliveryAddress,
                                'position':result.x_position,
                                'donation.donationTime':result.donationTime,
                                'confirmTime':result.confirmTime,
                                'confirmPeple':result.confirmerName,
                                'donation.donorType':result.donorType,
                                'donation.donorName':result.donorName,
                                'donation.itemType':result.itemType,
                                'donation.itemName':result.itemName,
                                'donation.itemNum':result.itemNum,
                                'donation.anonymous':result.anonymous && result.anonymous !=''?result.anonymous:0,
                                'donation.messageIsOpen':result.messageIsOpen
                            });

                            if(result.x_sex && result.x_sex != ''){
                                $('#sex').val(result.x_sex == 0?'男':'女');
                            }
                            if(result.flag==1){
                                $('#full').show();
                                $('#flag').prop('value','正式校友')
                            }
                            if(result.flag==0){
                                $('#flag').prop('value','社会人士')
                            }

                            if(result.project.projectPic!=undefined){
                                $('#pagePic').html('<img src="'+result.project.projectPic+'" width="150px" height="150px"/>');
                            }

                            if(result.needInvoice){
                                $('#needInvoice').html(result.needInvoice == '10'?"需要":"不需要");
                            }else{
                                $('#needInvoice').html("不需要");
                            }

                            if(result.x_fullname){
                                var pathname = result.x_fullname.split('_');
                                var fullname = '';
                                for( var i in pathname){
                                    fullname += pathname[i]+'<br>';
                                }
                                $('#x_fullname').html(fullname);
                            }else if(result.x_school){
                                var fullname = '';
                                if(result.x_depart){
                                    fullname += (','+result.x_depart);
                                }
                                if(result.x_grade){
                                    fullname += (','+result.x_grade);
                                }
                                if(result.x_clazz){
                                    fullname += (','+result.x_clazz);
                                }
                                $('#x_fullname').html(fullname);
                            }else if(result.inputClassInfo){
                                $('#x_fullname').html(result.inputClassInfo);
							}
                            editor.html(result.project.content);
                            if(result.certificatePic!=undefined){
                                $('#certificatePic').append('<div style="float:left;width:180px;"><img src="'+result.certificatePic+'" width="250px" height="340px"/><input type="hidden" name="project.projectPic" value="'+result.certificatePic+'"/></div>');
                            }

                            if (result.donationType == '10') {
                                $('#moneyName').html('捐赠金额/元');
                                $('#trmoney').show();
                                $('#payDetail').show();
                                $('#trpayTime').show();
                                $('#trpayStatus').show();
                                $('#cataloag').show();
                                $('#trdonationCourier').hide();
//								$('#trneedInvoice').hide();
                                $('#thing').hide();
                                $('#itemName').hide();
                            } else if (result.donationType == '20') {
                                $('#trmoney').hide();
                                $('#trdonationTime').hide();
                                $('#trpayTime').hide();
                                $('#trpayStatus').hide();
                                $('#cataloag').hide();
                                $('#trdonationCourier').show();
                                $('#trneedInvoice').show();
                            }
                        }
                    },
                    beforeSend:function(){
                        parent.$.messager.progress({
                            text : '数据加载中....'
                        });
                    },
                    complete:function(){
                        parent.$.messager.progress('close');
                    }
                });
            }
        });
        function searchFun(){
            $('#cc').combogrid('grid').datagrid('load',serializeObject($('#searchForm')));
        }
        function comfirmStatu($dialog, $grid, $pjq)
        {
            parent.$.messager.confirm('确认', '确认收到了捐赠？', function(r)
            {
                if(r){
                    var picUrl = '';
                    var deliveryAddress = '';
                    if($('#cfPic').val() != ''){
                        picUrl = $('#cfPic').val();
                    }
                    if($('#deliveryAddress').val() != ''){
                        deliveryAddress = $('#deliveryAddress').val();
                    }

                    $.ajax({
                        url : '${pageContext.request.contextPath}/donation/donationAction!update.action',
                        data : {
                            'donation.donationId':donationId,
                            'donation.confirmStatus':'30',
                            'donation.certificatePic':picUrl,
                            'donation.deliveryAddress':deliveryAddress
                        },
                        dataType : 'json',
                        success : function(result)
                        {
                            if (result.success)
                            {
                                $grid.datagrid('reload');
                                window.parent.refreshMsgNum();
                                $dialog.dialog('destroy');
                                $pjq.messager.alert('提示', result.msg, 'info');
                            } else
                            {
                                $pjq.messager.alert('提示', result.msg, 'error');
                            }
                        },
                        beforeSend : function()
                        {
                            $pjq.messager.progress({
                                text : '数据提交中....'
                            });
                        },
                        complete : function()
                        {
                            $pjq.messager.progress('close');
                        }
                    });
                }
            });
        }
        function deliveryConfirm($dialog, $grid, $pjq) {
            var picUrl = '';
            var deliveryAddress = '';
            if($('#cfPic').val()){
                picUrl = $('#cfPic').val();
            }
            if($('#deliveryAddress').val()){
                deliveryAddress = $('#deliveryAddress').val();
            }
            var dialog = parent.WidescreenModalDialog({
                width:'300px',
                height:'200px',
                title : '填写快递单号',
                iconCls : 'ext-icon-note',
                url : '${pageContext.request.contextPath}/page/admin/donation/deliveryInfo.jsp?id=' + donationId+'&certificatePic='+picUrl+'&deliveryAddress='+deliveryAddress,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm($dialog,$grid, $pjq, dialog);
                    }
                } ]
            });
        }

	</script>
</head>

<body>
<form method="post">
	<fieldset>
		<legend>
			捐赠项目信息
		</legend>
		<table class="ta001">
			<tr>
				<th>
					捐赠项目
				</th>
				<td colspan="3">
					<input id="donationId" name="donation.donationId" type="hidden" value="${param.id}">
					<input id="projectId" name="donation.projectId" class="easyui-combobox" style="width: 300px;" disabled="disabled"
						   data-options="editable:false,
									        required:true,
									        valueField: 'projectId',
									        textField: 'projectName',
									        url: '${pageContext.request.contextPath}/project/projectAction!doNotNeedSecurity_getAll.action'"/>
				</td>
			</tr>
			<tr>
				<th>
					项目内容
				</th>
				<td colspan="3">
							<textarea id="content" name="project.content"
									  style="width: 700px; height: 300px;"></textarea>
				</td>
			</tr>
			<tr>
				<th>
					页面图片
				</th>
				<td colspan="3">
					<div id="pagePic" class="container" style="padding: 20px"></div>
				</td>
			</tr>
		</table>
	</fieldset>
	<br>
	<fieldset>
		<legend>
			捐赠基本信息
		</legend>
		<table class="ta001">
			<tr>
				<th>
					订单编号
				</th>
				<td colspan="3">
					<input name="orderNo" disabled="disabled" style="width: 300px;"/>
				</td>
			</tr>

			<tr>
				<th>
					捐增类型
				</th>
				<td colspan="3">
					<select class="easyui-combobox" disabled="disabled" data-options="editable:false" name="donation.donationType" style="width: 300px;">
						<option value="10">捐款</option>
						<option value="20">捐物</option>
					</select>
				</td>
			</tr>
			<tr id="itemName">
				<th>
					物品名称
				</th>
				<td colspan="3">
					<input id="name" name="donation.itemName" disabled="disabled" style="width: 300px;"  class="easyui-validatebox" />
				</td>
			</tr>
			<tr id="thing">
				<th>
					物品类型
				</th>
				<td>
					<input id="itemType" name="donation.itemType" class="easyui-combobox" disabled="disabled"
						   data-options="editable:false,
									        required:true,
									        valueField: 'dictValue',
									        textField: 'dictName',
									        url: '${pageContext.request.contextPath}/donation/donationAction!doNotNeedSecurity_itemType.action'"/>

				</td>
				<th>
					物品数量
				</th>
				<td>
					<input name="donation.itemNum" disabled="disabled"  class="easyui-validatebox" />
				</td>
			</tr>

			<tr >
				<th>
					捐赠时间
				</th>
				<td>
					<input id="donationTime" name="donation.donationTime" class="easyui-datetimebox" style="width: 150px;" data-options="editable:false" disabled="disabled" />
				</td>
				<th id="moneyName">
					<%--捐赠金额/物品价值/元--%>
				</th>
				<td >
					<input id="money" name="donation.money" disabled="disabled" style="width: 150px;" class="easyui-validatebox" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				</td>
			</tr>
			<tr id="trpayTime">
				<th>
					支付金额/元
				</th>
				<td>
					<input name="donation.payMoney" disabled="disabled" style="width: 150px;" class="easyui-validatebox" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
				</td>
				<th>
					支付时间
				</th>
				<td>
					<input name="payTime" disabled="disabled"/>
				</td>
			</tr>
			<tr id="trpayStatus">
				<th>
					支付方式
				</th>
				<td>
					<input id="payMode" name="donation.payMode" class="easyui-combobox" disabled="disabled"
						   data-options="editable:false,
									        required:true,
									        valueField: 'dictValue',
									        textField: 'dictName',
									        url: '${pageContext.request.contextPath}/donation/donationAction!doNotNeedSecurity_payModel.action'"/>

				</td>
				<th>
					支付状态
				</th>
				<td>
					<select class="easyui-combobox" disabled="disabled" data-options="editable:false" name="donation.payStatus" style="width: 150px;">
						<option value="0">未支付</option>
						<option value="1">已支付</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>
					入口类型
				</th>
				<td>
					<select class="easyui-combobox" disabled="disabled" data-options="editable:false" name="donation.payType" style="width: 150px;">
						<option></option>
						<option value="10">校友会公众号</option>
						<option value="15">校友会网站</option>
						<option value="20">基金会公众号</option>
						<option value="25">基金会网站</option>
						<option value="30">后台添加</option>
						<option value="40">历史数据导入</option>
						<option value="50">基金会系统同步</option>
					</select>
				</td>
				<th>
					支付途径
				</th>
				<td>
					<select class="easyui-combobox" disabled="disabled" data-options="editable:false" name="donation.payMethod" style="width: 150px;">
						<option></option>
						<option value="10">手机APP</option>
						<option value="20">网站</option>
						<option value="30">微信</option>
						<option value="40">线下</option>
					</select>
				</td>
			</tr>
			<tr id="trdonationCourier">
				<th>
					捐物快递公司
				</th>
				<td>
					<input name="donation.donationCourier" disabled="disabled"/>
				</td>
				<th>
					捐物快递单号
				</th>
				<td>
					<input name="donation.donationCourierNumber" disabled="disabled" class="easyui-validatebox"/>
				</td>
			</tr>
			<%--<tr id="trneedInvoice">
                <th>
                    是否需要发票
                </th>
                <td>
                    <select class="easyui-combobox" disabled="disabled" data-options="editable:false" name="donation.needInvoice" style="width: 150px;">
                        <option value="10">需要</option>
                        <option value="20">不要</option>
                    </select>
                </td>
            </tr>--%>
			<tr>
				<th>
					是否公开留言
				</th>
				<td colspan="3">
					<select class="easyui-combobox" disabled="disabled" data-options="editable:false" name="donation.messageIsOpen">
						<option value="1">公开</option>
						<option value="0">未公开</option>
					</select>
				</td>
			</tr>
			<tr id="payDetail">
				<th>
					支付详情
				</th>
				<td colspan="3">
					<textarea rows="2" cols="100" name="donation.payDetail" disabled="disabled"></textarea>
				</td>
			</tr>
			<tr>
				<th>
					捐赠留言
				</th>
				<td colspan="3">
					<textarea rows="2" cols="100" name="donation.message" disabled="disabled"></textarea>
				</td>
			</tr>
			<tr>
				<th>
					备注
				</th>
				<td colspan="3">
					<textarea rows="2" cols="100" name="donation.remark" disabled="disabled"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	<br/>
	<fieldset>
		<legend>
			捐赠方信息
		</legend>
		<table class="ta001">
			<tr>
				<th>
					捐赠方类型
				</th>
				<td colspan="3">
					<input id="donorType" name="donation.donorType" class="easyui-combobox" disabled="disabled"
						   data-options="editable:false,
									        required:true,
									        valueField: 'dictValue',
									        textField: 'dictName',
									        url: '${pageContext.request.contextPath}/donation/donationAction!doNotNeedSecurity_donorType.action'"/>
				</td>
			</tr>
			<tr>
				<th>
					捐赠方
				</th>
				<td colspan="3">
					<input id="donorName" name="donation.donorName" disabled="disabled">
				</td>
			</tr>
			<tr>
				<th>
					是否匿名
				</th>
				<td>
					<input type="radio" name="donation.anonymous" value="0" style="width: 20px;" disabled>公开
					<input type="radio" name="donation.anonymous" value="1" style="width: 20px;" disabled>匿名
				</td>
				<th>
					是否需要发票
				</th>
				<td>
					<input type="radio" name="donation.needInvoice" value="0" style="width: 20px;" disabled>不需要
					<input type="radio" name="donation.needInvoice" value="1" style="width: 20px;" disabled>需要
				</td>
			</tr>

			<tr id="userName">
				<th>
					联系人
				</th>
				<td>
					<input  name="userName" disabled="disabled">
				</td>
				<th>
					联系电话
				</th>
				<td>
					<input id="telId" name="telId" disabled="disabled" type="text"/>
				</td>
			</tr>
			<tr>
				<th>
					是否校友
				</th>
				<td >
					<input name="flag" id="flag" disabled="disabled"/>
				</td>
				<th id="sex">
					性别
				</th>
				<td>
					<input  name="sex" disabled="disabled" type="text"/>
				</td>

			</tr>
			<tr>
				<th>
					工作单位
				</th>
				<td>
					<input name="workunit"  disabled="disabled" type="text"/>
				</td>
				<th>
					职务
				</th>
				<td>
					<input name="position" disabled="disabled" type="text"/>
				</td>
			</tr>
			<tr id="full" hidden>
				<th>
					学习经历
				</th>
				<td colspan="3" id="x_fullname">
				</td>
			</tr>
			<tr>
				<th>
					邮箱
				</th>
				<td colspan="3">
					<input id="email" name="email" style="width: 500px" disabled="disabled" type="text"/>
				</td>
			</tr>
			<tr>
				<th>
					联系地址
				</th>
				<td colspan="3">
					<input id="mailingAddress" name="mailingAddress" style="width: 500px" disabled="disabled" type="text"/>
				</td>
			</tr>
		</table>
	</fieldset>
	<br/>
	<fieldset>
		<legend>
			状态
		</legend>
		<table class="ta001">
			<tr>
				<th>
					确认状态
				</th>
				<td>
					<select class="easyui-combobox" disabled="disabled" data-options="editable:false" name="donation.confirmStatus" style="width: 150px;">
						<option value="10">等待寄出货物</option>
						<option value="20">等待校方确认接受</option>
						<option value="30">校方已确认接受</option>
						<option value="40">已寄出发票和捐赠证书</option>
						<option value="45">已寄出捐赠证书</option>
					</select>
				</td>
				<th>
					确认时间
				</th>
				<td>
					<input name="confirmTime" disabled="disabled" type="text"/>
				</td>
			</tr>
			<tr>
				<th>
					确认人
				</th>
				<td colspan="3">
					<input name="confirmPeple" disabled="disabled" type="text"/>
				</td>
			</tr>
			<tr>
				<th>
					证书图片
				</th>
				<td colspan="3">
					<div id="certificatePic"></div>
				</td>
			</tr>
		</table>
	</fieldset>
</form>
</body>
</html>

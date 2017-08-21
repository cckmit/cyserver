<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
        //封面图上传
        $(function() {
            var button = $("#logo_upload_button"), interval;
            new AjaxUpload(button, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadNews.action',
                name : 'upload',
                onSubmit : function(file, ext) {
                    if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext))) {
                        $.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
                        return false;
                    }
                    $.messager.progress({
                        text : '图片正在上传,请稍后....'
                    });
                },
                onComplete : function(file, response) {
                    $.messager.progress('close');
                    var msg = $.parseJSON(response);
                    if (msg.error == 0) {
                        $('#schoolLogo').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeSchoolLogo(this)"></div><input type="hidden" name="systemSetting.schoolLogo" value="'+msg.url+'"/></div>');
                        $("#logo_upload_button").prop('disabled', 'disabled');
                    } else {
                        $.messager.alert('提示', msg.message, 'error');
                    }
                }
            });
            var signetButton = $("#signet_upload_button"), interval;
            new AjaxUpload(signetButton, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadNews.action',
                name : 'upload',
                onSubmit : function(file, ext) {
                    if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext))) {
                        $.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
                        return false;
                    }
                    $.messager.progress({
                        text : '图片正在上传,请稍后....'
                    });
                },
                onComplete : function(file, response) {
                    $.messager.progress('close');
                    var msg = $.parseJSON(response);
                    if (msg.error == 0) {
                        $('#foundationSignet').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removeSignet(this)"></div><input type="hidden" name="systemSetting.foundationSignet" value="'+msg.url+'"/></div>');
                        $("#signet_upload_button").prop('disabled', 'disabled');
                    } else {
                        $.messager.alert('提示', msg.message, 'error');
                    }
                }
            });

            var fuLogoButton = $("#fu_logo_upload_button"), interval;
            new AjaxUpload(fuLogoButton, {
                action : '${pageContext.request.contextPath}/fileUpload/fileUploadAction!doNotNeedSecurity_fileUploadNews.action',
                name : 'upload',
                onSubmit : function(file, ext) {
                    if (!(ext && /^(jpg|jpeg|png|gif|bmp)$/.test(ext))) {
                        $.messager.alert('提示', '您上传的图片格式不对，请重新选择！', 'error');
                        return false;
                    }
                    $.messager.progress({
                        text : '图片正在上传,请稍后....'
                    });
                },
                onComplete : function(file, response) {
                    $.messager.progress('close');
                    var msg = $.parseJSON(response);
                    if (msg.error == 0) {
                        $('#foundationLogo').append('<div style="float:left;width:180px;"><img src="'+msg.url+'" width="150px" height="150px"/><div class="bb001" onclick="removefuLogo(this)"></div><input type="hidden" name="systemSetting.foundationLogo" value="'+msg.url+'"/></div>');
                        $("#fu_logo_upload_button").prop('disabled', 'disabled');
                    } else {
                        $.messager.alert('提示', msg.message, 'error');
                    }
                }
            });
        });

        function removeSchoolLogo(schoolLogo) {
            $(schoolLogo).parent().remove();
            $("#logo_upload_button").prop('disabled', false);
        }

        function removeSignet(signetPic) {
            $(signetPic).parent().remove();
            $("#signet_upload_button").prop('disabled', false);
        }

        function removefuLogo(fuLogo) {
            $(fuLogo).parent().remove();
            $("#fu_logo_upload_button").prop('disabled', false);
        }
        $(function() {
            $
                .ajax({
                    url : '${pageContext.request.contextPath}/systemSetting/systemSettingAction!initSystemSetting.action',
                    dataType : 'json',
                    success : function(result) {
                        if (result.systemId != undefined) {
                            $('form').form('load', {
                                'systemSetting.systemId' : result.systemId,
                                'systemSetting.smtpHost' : result.smtpHost,
                                'systemSetting.smtpPort' : result.smtpPort,
                                'systemSetting.email_account' : result.email_account,
                                'systemSetting.email_password' : result.email_password,
                                'systemSetting.download_app_url' : result.download_app_url,
                                'systemSetting.partner' : result.partner,
                                'systemSetting.seller_email' : result.seller_email,
                                'systemSetting.key' : result.key,
                                'systemSetting.private_key' : result.private_key,
                                'systemSetting.notify_url' : result.notify_url,
                                'systemSetting.return_url' : result.return_url,
                                'systemSetting.wap_merchant_url' : result.wap_merchant_url,
                                'systemSetting.wap_return_url' : result.wap_return_url,
                                'systemSetting.wap_notify_url' : result.wap_notify_url,
                                'systemSetting.exter_invoke_ip' : result.exter_invoke_ip,
                                'systemSetting.smsUrl' : result.smsUrl,
                                'systemSetting.smsAccount' : result.smsAccount,
                                'systemSetting.smsPassword' : result.smsPassword,
                                'systemSetting.sendType' : result.sendType,
                                'systemSetting.smsCodeTemplate' : result.smsCodeTemplate,
                                'systemSetting.smsVisitTemplate' : result.smsVisitTemplate,
                                'systemSetting.smsBirthdayTemplate' : result.smsBirthdayTemplate,
                                'systemSetting.wap_public_key':result.wap_public_key,
                                'systemSetting.web_homepage_api_url':result.web_homepage_api_url,
                                'systemSetting.is_new_type_open':result.is_new_type_open,

                                'systemSetting.sellerEmail' : result.sellerEmail,
                                'systemSetting.appId' : result.appId,
                                'systemSetting.inputCharset' : result.inputCharset,
                                'systemSetting.alipayPublicKey':result.alipayPublicKey,
                                'systemSetting.appPublicKey':result.appPublicKey,
                                'systemSetting.appPrivateKey':result.appPrivateKey,
                                'systemSetting.paymentType':result.paymentType,
                                'systemSetting.serverUrl':result.serverUrl,
                                'systemSetting.signUpText':result.signUpText,
                                'systemSetting.donateText':result.donateText,
                                'systemSetting.foundationName':result.foundationName,
								'systemSetting.foundationSmtpHost':result.foundationSmtpHost,
								'systemSetting.foundationSmtpPort':result.foundationSmtpPort,
								'systemSetting.foundationEmailAccount':result.foundationEmailAccount,
								'systemSetting.foundationEmailPassword':result.foundationEmailPassword,
								'systemSetting.is_audit_personal_activity':result.is_audit_personal_activity
                            });
                            if(result.schoolLogo != null || $.trim(result.schoolLogo) != '') {
                                $('#schoolLogo').append('<div style="float:left;width:180px;"><img src="'+result.schoolLogo+'" width="150px" height="150px"/><div class="bb001" onclick="removeSchoolLogo(this)"></div><input type="hidden" name="systemSetting.schoolLogo" value="'+result.schoolLogo+'"/></div>');
                                $("#logo_upload_button").prop('disabled', 'disabled');
                            }

                            if(result.foundationSignet != null || $.trim(result.foundationSignet) != '') {
                                $('#foundationSignet').append('<div style="float:left;width:180px;"><img src="'+result.foundationSignet+'" width="150px" height="150px"/><div class="bb001" onclick="removeSignet(this)"></div><input type="hidden" name="systemSetting.foundationSignet" value="'+result.foundationSignet+'"/></div>');
                                $("#signet_upload_button").prop('disabled', 'disabled');
                            }
                            if(result.foundationLogo != null || $.trim(result.foundationLogo) != '') {
                                $('#foundationLogo').append('<div style="float:left;width:180px;"><img src="'+result.foundationLogo+'" width="150px" height="150px"/><div class="bb001" onclick="removefuLogo(this)"></div><input type="hidden" name="systemSetting.foundationLogo" value="'+result.foundationLogo+'"/></div>');
                                $("#fu_logo_upload_button").prop('disabled', 'disabled');
                            }

                        }
                    },
                    beforeSend : function() {
                        parent.$.messager.progress({
                            text : '数据加载中....'
                        });
                    },
                    complete : function() {
                        parent.$.messager.progress('close');
                    }
                });
        });
        function save($dialog, $grid, $pjq) {
            if ($('form').form('validate')) {
                $ .ajax({
                    url : '${pageContext.request.contextPath}/systemSetting/systemSettingAction!save.action',
                    data : $('form').serialize(),
                    dataType : 'json',
                    success : function(result) {
                        if (result.success) {
                            parent.$.messager.alert('提示', result.msg,
                                'info');
                        } else {
                            parent.$.messager.alert('提示', result.msg,
                                'error');
                        }
                    },
                    beforeSend : function() {
                        parent.$.messager.progress({
                            text : '数据提交中....'
                        });
                    },
                    complete : function() {
                        parent.$.messager.progress('close');
                    }
                });
            }
        };
	</script>
</head>

<body>
<form method="post" class="form">
	<!-- 防止chrom自动填写账号密码-->
	<input class="easyui-validatebox" style="position: absolute; top:-1000px"/>
	<input class="easyui-validatebox" type="password" style="position: absolute; top:-1000px"/>
	<!--end 防止chrom自动填写账号密码-->

	<input name="systemSetting.systemId" type="hidden" />
	<fieldset>
		<legend> 学校LOGO </legend>
		<table class="ta001">
			<tr>
				<th>学校LOGO上传</th>
				<td>
					<input type="button" id="logo_upload_button" value="上传图片">
				</td>
			</tr>
			<tr>
				<th>学校LOGO图片</th>
				<td>
					<div id="schoolLogo">
					</div>
				</td>
			</tr>
		</table>
	</fieldset>
	<br>
	<fieldset>
		<legend> 邮件设置 </legend>
		<table class="ta001">
			<tr>
                <th >服务器</th>
                <td> <input
                    name="systemSetting.smtpHost" class="easyui-validatebox"
                    style="width: 300px;"  />
                </td>
            </tr>
            <tr>
                <th>端口</th>
                <td><input name="systemSetting.smtpPort"
                    class="easyui-validatebox"
                    style="width: 300px;" />
                </td>
            </tr>
			<tr>
				<th>账号</th>
				<td><input name="systemSetting.email_account"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="systemSetting.email_password"
						   class="easyui-validatebox"
						   type="password" style="width: 300px;" />
				</td>
			</tr>
		</table>
	</fieldset>
	<br>
	<fieldset>
		<legend> 基金会邮件设置 </legend>
		<table class="ta001">
			<tr>
				<th >服务器</th>
				<td> <input
						name="systemSetting.foundationSmtpHost" class="easyui-validatebox"
						style="width: 300px;"  />
				</td>
			</tr>
			<tr>
				<th>端口</th>
				<td><input name="systemSetting.foundationSmtpPort"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>账号</th>
				<td><input name="systemSetting.foundationEmailAccount"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="systemSetting.foundationEmailPassword"
						   class="easyui-validatebox"
						   type="password" style="width: 300px;" />
				</td>
			</tr>
		</table>
	</fieldset>
	<br>
	<fieldset>
		<legend> 云平台设置 </legend>
		<table class="ta001">
			<tr>
				<th >客户端ID</th>
				<td> <input
						name="systemSetting.clientId" class="easyui-validatebox"
						style="width: 300px;"  />
				</td>
			</tr>
			<tr>
				<th>密钥</th>
				<td><input name="systemSetting.clientSecret"
						   class="easyui-validatebox"
						   type="password" style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th >云平台账号</th>
				<td> <input
						name="systemSetting.cloudUserName" class="easyui-validatebox"
						style="width: 300px;"  />
				</td>
			</tr>
			<tr>
				<th>云平台密码</th>
				<td><input name="systemSetting.cloudUserPassword"
						   class="easyui-validatebox"
						   type="password" style="width: 300px;" />
				</td>
			</tr>
		</table>
	</fieldset>
	<br>
	<fieldset>
		<legend> 短信设置 </legend>
		<table class="ta001">
			<tr>
				<th>短信服务器地址</th>
				<td> <input
						name="systemSetting.smsUrl" class="easyui-validatebox"
						style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>账号</th>
				<td><input name="systemSetting.smsAccount"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="systemSetting.smsPassword"
						   class="easyui-validatebox"
						   type="password" style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>发送方式</th>
				<td>
					<!-- <input type="radio" name="systemSetting.sendType" value="HTTP" style="width: 20px;" checked="checked">HTTP -->
					<input type="radio" name="systemSetting.sendType" value="SDK" style="width: 20px;">SDK
					<input type="radio" name="systemSetting.sendType" value="CLOUD" style="width: 20px;">CLOUD
				</td>
			</tr>
			<tr>
				<th>网关签名</th>
				<td><input name="systemSetting.download_app_url"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
		</table>
	</fieldset>
	<br>
	<fieldset>
		<legend>支付宝设置 </legend>
		<table class="ta001">
			<tr>
				<th>AppID</th>
				<td><input name="systemSetting.appId"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>合作者身份ID</th>
				<td><input name="systemSetting.partner"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>收款支付宝账号</th>
				<td><input name="systemSetting.seller_email"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>支付类型</th>
				<td>
					<input type="radio" name="systemSetting.paymentType" value="1" checked  style="width: 20px;">即时到账
					<input type="radio" name="systemSetting.paymentType" value="4" style="width: 20px;">公益即时到账
				</td>
			</tr>
			<tr>
				<th>MD5私钥</th>
				<td><input name="systemSetting.key"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>RSA私钥</th>
				<td>
					<textarea rows="5" cols="100" name="systemSetting.appPrivateKey"></textarea>
				</td>
			</tr>
			<tr>
				<th>应用公钥</th>
				<td>
					<textarea rows="5" cols="100" name="systemSetting.appPublicKey"></textarea>
				</td>
			</tr>
			<tr>
				<th>支付宝公钥</th>
				<td>
					<textarea rows="5" cols="100" name="systemSetting.alipayPublicKey"></textarea>
				</td>
			</tr>
			<tr>
				<th>支付网管</th>
				<td><input name="systemSetting.serverUrl"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>PC异步通知页面</th>
				<td><input name="systemSetting.notify_url"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>PC同步通知页面</th>
				<td><input name="systemSetting.return_url"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>WAP中断操作页面</th>
				<td><input name="systemSetting.wap_merchant_url"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>WAP同步通知页面</th>
				<td><input name="systemSetting.wap_return_url"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>WAP异步通知页面</th>
				<td><input name="systemSetting.wap_notify_url"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>客户端IP地址</th>
				<td><input name="systemSetting.exter_invoke_ip"
						   class="easyui-validatebox"
						   style="width: 300px;" />
				</td>
			</tr>
		</table>
	</fieldset>
	<br>
	<fieldset>
		<legend> 短信模板设置 </legend>
		<table class="ta001">
			<tr>
				<th>验证码短信</th>
				<td>
					<textarea rows="5" cols="100" name="systemSetting.smsCodeTemplate"></textarea>
				</td>
			</tr>
			<tr>
				<th>邀请短信</th>
				<td>
					<textarea rows="5" cols="100" name="systemSetting.smsVisitTemplate"></textarea>
				</td>
			</tr>
			<tr>
				<th>生日祝福短信</th>
				<td>
					<textarea rows="5" cols="100" name="systemSetting.smsBirthdayTemplate"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend> 新闻设置 </legend>
		<table class="ta001">
			<tr>
				<th>否开通分会专栏</th>
				<td>
					<input type="radio" name="systemSetting.is_new_type_open" value="0" style="width: 20px;">不开通
					<input type="radio" name="systemSetting.is_new_type_open" value="1" style="width: 20px;">开通
					<input type="radio" name="systemSetting.is_new_type_open" value="2" style="width: 20px;">开通并合并
				</td>
			</tr>
			<tr>
				<th>推送新闻的地址</th>
				<td> <input
						name="systemSetting.web_homepage_api_url" class="easyui-validatebox"
						style="width: 300px;" />
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend> 活动设置 </legend>
		<table class="ta001">
			<tr>
				<th>是否审核个人活动</th>
				<td>
					<input type="radio" name="systemSetting.is_audit_personal_activity" value="0" style="width: 20px;">否
					<input type="radio" name="systemSetting.is_audit_personal_activity" value="1" style="width: 20px;">是
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend> 基金会信息 </legend>
		<table class="ta001">
			<tr>
				<th>基金会名称</th>
				<td>
					<input name="systemSetting.foundationName" class="easyui-validatebox" style="width: 300px;" />
				</td>
			</tr>
			<tr>
				<th>基金会Logo上传</th>
				<td>
					<input type="button" id="fu_logo_upload_button" value="上传图片">
				</td>
			</tr>
			<tr>
				<th>基金会Logo图片</th>
				<td>
					<div id="foundationLogo">
					</div>
				</td>
			</tr>
			<tr>
				<th>基金会印章上传</th>
				<td>
					<input type="button" id="signet_upload_button" value="上传图片">
				</td>
			</tr>
			<tr>
				<th>基金会印章图片</th>
				<td>
					<div id="foundationSignet">
					</div>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend> 声明内容 </legend>
		<table class="ta001">
			<tr>
				<th>注册声明</th>
				<td>
					<textarea rows="5" cols="100" name="systemSetting.signUpText"></textarea>
				</td>
			</tr>
			<tr>
				<th>捐赠声明</th>
				<td>
					<textarea rows="5" cols="100" name="systemSetting.donateText"></textarea>
				</td>
			</tr>
		</table>
	</fieldset>
	<br>
	<table align="center">
		<tr>
			<td><a href="javascript:void(0);" class="easyui-linkbutton"
				   data-options="iconCls:'ext-icon-save',plain:true" onclick="save();">保存</a>
			</td>
		</tr>
	</table>
</form>
</body>
</html>

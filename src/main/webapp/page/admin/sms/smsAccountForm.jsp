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
        var type = ${param.type};
        $(function () {
            if(type == '0'){
                getAlumniContacts();
            }
            else if (type == '1') {
                getSmsAccountInfo();
            }
        });

        // 获取组织联系人信息
        function getAlumniContacts() {
            $.ajax({
                url : '${pageContext.request.contextPath}/smsAccount/smsAccountAction!doNotNeedSecurity_getCurrentDeptInfo.action',
                dataType : 'json',
                success : function(result) {
                    if(result.presidentName && result.telephone && result.presidentName != '' && result.telephone != ''){
                        $('form').form('load', {
                            'smsAccount.appUser.name' :result.presidentName,
                            'smsAccount.appUser.phone':result.telephone,
                            'smsAccount.appUser.email':result.email,
                            'smsAccount.accountFronts':result.admin
                        });
                    }else{
                        alert("未获取联系人信息，请先完善");
                    }
                },
                complete:function(){
                    parent.$.messager.progress('close');
                }
            });
        }

        // 获取短信联系人信息
        function getSmsAccountInfo() {
            $('#account').prop('readonly', true);

            $.ajax({
                url : '${pageContext.request.contextPath}/smsAccount/smsAccountAction!getAccountInfoByCurrentDept.action',
                dataType : 'json',
                success : function(result) {
                    if(result && result.success && result.obj){
                        var formData = {
                            'smsAccount.account' : result.obj.account,
                            'smsAccount.password' : result.obj.password,
                            'smsAccount.sign' : result.obj.sign
                        };

                        if (result.obj.appUser) {
                            formData['smsAccount.appUser.name'] = result.obj.appUser.name;
                            formData['smsAccount.appUser.phone'] = result.obj.appUser.phone;
                            formData['smsAccount.appUser.email'] = result.obj.appUser.email;
                        }

                        $('form').form('load', formData);

                        $('#accountF').hide();
                        $('#editInfo').show();
                    }else{
                        $('#accountF').show();
                        $('#addInfo').show();
                    }
                },
                complete:function(){
                    parent.$.messager.progress('close');
                }
            });
        }

        // 提交表单
        function submitForm( $dialog, $pjq, w) {
            var actionUrl;

            if(type == '0' ){
                actionUrl = '${pageContext.request.contextPath}/smsAccount/smsAccountAction!save.action';
            }
            else if (type == '1'){
                actionUrl = '${pageContext.request.contextPath}/smsAccount/smsAccountAction!update.action';
            }

            if ($('form').form('validate')) {
                $.ajax({
                    url : actionUrl,
                    data : $('form').serialize(),
                    dataType : 'json',
                    success : function(result) {
                        if (result.success) {
                            $dialog.dialog('destroy');
                            w.showInfo();
                            $pjq.messager.alert('提示', result.msg, 'info');
                        } else {
                            $pjq.messager.alert('提示', result.msg, 'error');
                        }
                    },
                    beforeSend : function() {
                        $pjq.messager.progress({
                            text : '数据提交中....'
                        });
                    },
                    complete : function() {
                        $pjq.messager.progress('close');
                    }
                });
            }
        }
    </script>
</head>
<body>
    <form method="post" class="form">
        <fieldset>
            <legend>联系人信息</legend>
            <table class="ta001">
                <tr>
                    <th>姓名</th>
                    <td>
                        <input type="text" name="smsAccount.appUser.name" readonly />
                    </td>
                </tr>
                <tr>
                    <th>手机号</th>
                    <td>
                        <input type="text" name="smsAccount.appUser.phone" readonly />
                    </td>
                </tr>
                <tr>
                    <th>邮箱</th>
                    <td>
                        <input type="text" name="smsAccount.appUser.email" readonly />
                    </td>
                </tr>
            </table>
        </fieldset>

        <fieldset>
            <legend>帐号信息 </legend>
            <table class="ta001">
                <tr>
                    <th>帐号</th>
                    <td>
                        <input type="text" id="accountF" name="smsAccount.accountFronts" readonly/>
                        <input type="text" id="account" name="smsAccount.account" />
                    </td>
                </tr>
                <tr>
                    <th>密码</th>
                    <td>
                        <input type="text" name="smsAccount.password" />
                    </td>
                </tr>
                <tr>
                    <th>签名</th>
                    <td>
                        <input type="text" name="smsAccount.sign" />
                    </td>
                </tr>
            </table>
        </fieldset>
    </form>
</body>
</html>

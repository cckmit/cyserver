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

    <style type="text/css">
        fieldset { margin-bottom: 20px;}
    </style>
    <script type="text/javascript">
        $(function () {
            // 根据当前登录用户所在组织ID，获取短信云账号信息
            $.ajax({
                url: '${pageContext.request.contextPath}/smsAccount/smsAccountAction!findSmsCloudAccount.action',
                dataType : 'json',
                success : function (result) {
                    if(result && result.success) {
                        // 存在，展示账号、联系人、短信包等信息
                        showInfo();
                    }
                    else {
                        // 不存在，显示「创建账号」按钮
                        $('#addInfo').show();
                    }
                },
                beforeSend : function() {
                    parent.$.messager.progress({
                        text : '数据加載中....'
                    });
                },
                complete:function(){
                    parent.$.messager.progress('close');
                }
            });

            // 获取短信统计信息
            findCount("");
        });

        // 展示账号、联系人、短信包等信息
        function showInfo() {
            // 通过当前组织获取账户信息
            $.ajax({
                url : '${pageContext.request.contextPath}/smsAccount/smsAccountAction!getAccountInfoByCurrentDept.action',
                dataType : 'json',
                success : function(result) {
                    if(result && result.obj){
                        var formData = {
                            'smsAccount.account' : result.obj.account || '',
                            'smsAccount.password' : result.obj.password || '',
                            'smsAccount.sign' : result.obj.sign || '',
                            'smsAccount.currBuyWaterId' : result.obj.currBuyWaterId || '',
                            'smsAccount.nextBuyWaterId' : result.obj.nextBuyWaterId || ''
                        };

                        if (result.obj.appUser) {
                            formData["smsAccount.appUser.name"] = result.obj.appUser.name || '';
                            formData["smsAccount.appUser.phone"] = result.obj.appUser.phone || '';
                            formData["smsAccount.appUser.email"] = result.obj.appUser.email || '';
                        }

                        $('form').form('load', formData);

                        // 短信统计，剩余条数
                        if(result.obj.surplusNum && result.obj.surplusNum!= ''){
                            $('#surplusNum').text(result.obj.surplusNum);
                        }else{
                            $('#surplusNum').text('0');
                        }

                        // 展示编辑账号按钮
                        $('#editInfo').show();

                        // 启用充值按钮
                        $('#placeAnOrder').prop("disabled", false);
                    }else{
                        $('#placeAnOrder').prop("disabled", true);
                        $('#addInfo').show();
                    }
                },
                beforeSend : function() {
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                complete:function(){
                    parent.$.messager.progress('close');
                }
            });
        }

        // 弹出 编辑账号 窗口
        function editSmsAccount(type) {
            var editUrl = '${pageContext.request.contextPath}/page/admin/sms/smsAccountForm.jsp?type=' + type;
            var dialog = parent.WidescreenModalDialog
            ({
                title : '编辑短信服务账号',
                iconCls : 'ext-icon-note_add',
                url : editUrl,
                buttons : [ {
                    text : '保存',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, parent.$, window);
                    }
                } ]
            });
        }

        // 弹出 充值 按钮
        function order() {
            var dialog = parent.WidescreenModalDialog ({
                title : '充值',
                iconCls : 'ext-icon-note_add',
                url : '${pageContext.request.contextPath}/page/admin/sms/smsPlaceAnOrder.jsp',
                buttons : [ {
                    text : '提交订单',
                    iconCls : 'ext-icon-save',
                    handler : function() {
                        dialog.find('iframe').get(0).contentWindow.submitForm(dialog, parent.$);
                    }
                } ]
            });
        }

        // 获取短信统计信息
        function findCount(timeLine) {
            $.ajax({
                url: '${pageContext.request.contextPath}/smsCount/smsCountAction!doNotNeedSecurity_getCount.action?timeLine='+timeLine,
                dataType : 'json',
                success : function (result) {
                    if(result){
                        $('#total').text(result.total);
                        $('#success').text(result.success);
                        $('#failed').text(result.failed);
                    }
                },
                beforeSend : function() {
                    parent.$.messager.progress({
                        text : '数据加载中....'
                    });
                },
                complete:function(){
                    parent.$.messager.progress('close');
                }
            });
        }

        function setPacket( type, id ){
            var submitUrl = '${pageContext.request.contextPath}/smsAccount/smsAccountAction!setPackets.action';
            if(type == 0) {
                submitUrl = submitUrl + '?currBuyWaterId=' + id;
            }else{
                submitUrl = submitUrl + '?nextBuyWaterId=' + id;
            }

            $.messager.confirm('确认', '确定设置？', function(r) {
                if (r) {
                    $.ajax({
                        url : submitUrl,
                        dataType : 'json',
                        success : function(data) {
                            if(data.success){
                                $.messager.alert('提示',data.msg,'info');

                                var url = '${pageContext.request.contextPath}/smsBuyWater/smsBuyWaterAction!doNotNeedSecurity_getUseAblePackage.action?type=1&except=' + id;
                                if(type == 0) {
                                    $('#nextBuyWaterId').combobox('reload', url);
                                }else{
                                    $('#currBuyWaterId').combobox('reload', url);
                                }

                                showInfo();
                            }
                            else{
                                $.messager.alert('错误', data.msg, 'error');
                            }
                        },
                        beforeSend:function(){
                            $.messager.progress({
                                text : '数据提交中...'
                            });
                        },
                        complete:function(){
                            $.messager.progress('close');
                        }
                    });
                }
                else {
                    showInfo();
                }
            });
        }

    </script>
</head>
<body>
<form method="post" class="form">
    <fieldset>
        <legend>帐号信息</legend>
        <table class="ta001">
            <tr>
                <th>帐号</th>
                <td>
                    <input type="text" name="smsAccount.account" readonly />
                    <input type="button" id="addInfo" style="width: 100px; display: none" onclick="editSmsAccount('0');" value="创建帐号">
                    <input type="button" id="editInfo" style="width: 100px; display: none" onclick="editSmsAccount('1');" value="修改帐号">
                </td>
            </tr>
            <tr>
                <th>密码</th>
                <td>
                    <input type="text" name="smsAccount.password" readonly />
                </td>
            </tr>
            <tr>
                <th>签名</th>
                <td>
                    <input type="text" name="smsAccount.sign" readonly />
                </td>
            </tr>
            <tr>
                <th>当前短信包</th>
                <td>
                    <input id="currBuyWaterId" name="smsAccount.currBuyWaterId" class="easyui-combobox" style="width: 260px;"
                       data-options="
                                    valueField: 'id',
                                    textField: 'textField',
                                    editable:false,
                                    prompt:'--请选择--',
                                    icons:[{
                                        iconCls:'icon-clear',
                                        handler: function(e){
                                            $('#currBuyWaterId').combobox('clear');
                                            setPacket(0, '');
                                        }
                                    }],
                                    url: '${pageContext.request.contextPath}/smsBuyWater/smsBuyWaterAction!doNotNeedSecurity_getUseAblePackage.action?type=2&except=notNext',
                                    onSelect: function(rec){
                                        setPacket(0, rec.id);
                                    }
                    " />
                </td>
            </tr>
            <tr>
                <th>下一短信包</th>
                <td>
                    <input id="nextBuyWaterId" name="smsAccount.nextBuyWaterId" class="easyui-combobox" style="width: 260px;"
                           data-options="
                                    valueField: 'id',
                                    textField: 'textField',
                                    editable:false,
                                    prompt:'--请选择--',
                                    icons:[{
                                        iconCls:'icon-clear',
                                        handler: function(e){
                                            $('#nextBuyWaterId').combobox('clear');
                                            setPacket(1, '');
                                        }
                                    }],
                                    url: '${pageContext.request.contextPath}/smsBuyWater/smsBuyWaterAction!doNotNeedSecurity_getUseAblePackage.action?type=2&except=notCurr',
                                    onSelect: function(rec){
                                        setPacket(1, rec.id);
                                    }
                    " />
                    <input type="button" id="placeAnOrder" style="width: 100px" onclick="order()" value="充值" />
                </td>
            </tr>
        </table>
    </fieldset>

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
        <legend>统计信息 </legend>
        <table class="ta001">
            <tr>
                <th>时间范围</th>
                <td>
                    <select id="timeLine" class="easyui-combobox" data-options="
                            editable:false,panelHeight:150,
                            onSelect: function(value){
                                findCount(value.value);
						}">
                        <option value="">全部</option>
                        <option value="1">最近一周</option>
                        <option value="2">最近一个月</option>
                        <option value="3">最近三个月</option>
                        <option value="4">最近六个月</option>
                        <option value="5">最近一年</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>总发送条数</th>
                <td>
                    <span id="total"></span>
                </td>
            </tr>
            <tr>
                <th>发送成功</th>
                <td>
                    <span id="success"></span>
                </td>
            </tr>
            <tr>
                <th>发送失败</th>
                <td>
                    <span id="failed"></span>
                </td>
            </tr>
            <tr>
                <th>剩余短信条数</th>
                <td>
                    <span id="surplusNum"></span>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
